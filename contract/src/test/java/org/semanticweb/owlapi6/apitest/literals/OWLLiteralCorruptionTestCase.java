/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi6.apitest.literals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.apitest.TestEntities.DP;
import static org.semanticweb.owlapi6.apitest.TestFiles.closure;
import static org.semanticweb.owlapi6.apitest.TestFiles.correct;
import static org.semanticweb.owlapi6.apitest.TestFiles.literalMalformedXML;
import static org.semanticweb.owlapi6.apitest.TestFiles.literalXMl;
import static org.semanticweb.owlapi6.apitest.TestFiles.preamble;
import static org.semanticweb.owlapi6.apitest.TestFiles.roundtripPaddedLiterals;
import static org.semanticweb.owlapi6.apitest.TestFiles.wrong;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

public class OWLLiteralCorruptionTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";
    private static final OWLLiteral ONESHORT =
        df.getOWLLiteral("1", OWL2Datatype.XSD_SHORT.getDatatype(df));
    private static final OWLLiteral ZERO_ONE = df.getOWLLiteral("01", df.getIntegerOWLDatatype());
    private static final OWLLiteral ONE = df.getOWLLiteral("1", df.getIntegerOWLDatatype());

    @Test
    public void shouldroundTripLiteral() {
        String testString;
        StringBuilder sb = new StringBuilder(1000);
        int count = 17;
        while (count-- > 0) {
            sb.append("200 \u00B5Liters + character above U+0FFFF = ");
            sb.appendCodePoint(0x10192); // happens to be "ROMAN SEMUNCIA SIGN"
            sb.append('\n');
        }
        testString = sb.toString();
        OWLLiteral literal = Literal(testString);
        assertEquals("Out = in ? false", literal.getLiteral(), testString);
    }

    @Test
    public void shouldRoundTripXMLLiteral() throws OWLOntologyStorageException {
        OWLOntology o = getOWLOntology();
        OWLDataProperty p = df.getOWLDataProperty(df.getIRI(URN_TEST, "p"));
        OWLLiteral l = df.getOWLLiteral(literalXMl, OWL2Datatype.RDF_XML_LITERAL);
        OWLNamedIndividual i = df.getOWLNamedIndividual(df.getIRI(URN_TEST, "i"));
        o.add(df.getOWLDataPropertyAssertionAxiom(p, i, l));
        String string = saveOntology(o).toString();
        assertTrue(string.contains(literalXMl));
    }

    @Test
    public void shouldFailOnMalformedXMLLiteral() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        OWLDataProperty p = df.getOWLDataProperty(df.getIRI(URN_TEST, "p"));
        OWLLiteral l = df.getOWLLiteral(literalMalformedXML, OWL2Datatype.RDF_XML_LITERAL);
        OWLNamedIndividual i = df.getOWLNamedIndividual(df.getIRI(URN_TEST, "i"));
        o.add(df.getOWLDataPropertyAssertionAxiom(p, i, l));
        assertThrowsWithMessage("XML literal is not self contained",
            OWLOntologyStorageException.class, () -> saveOntology(o));
    }

    @Test
    public void shouldAcceptXMLLiteralWithDatatype() throws OWLOntologyStorageException {
        // A bug in OWLAPI means some incorrect XMLLiterals might have been
        // produced.
        // They should be understood in input and saved correctly on roundtrip
        String input = preamble + wrong + closure;
        OWLOntology o =
            loadOntologyFromString(input, df.generateDocumentIRI(), new RDFXMLDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(preamble + correct + closure,
            df.generateDocumentIRI(), new RDFXMLDocumentFormat());
        equal(o, o1);
        assertTrue(saveOntology(o, new RDFXMLDocumentFormat()).toString().contains(correct));
    }

    @Test
    public void shouldRoundtripPaddedLiterals()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = loadOntologyFromString(new StringDocumentSource(roundtripPaddedLiterals,
            "urn:test:test", new FunctionalSyntaxDocumentFormat(), null));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
        OWLNamedIndividual i =
            df.getOWLNamedIndividual(df.getIRI("http://www.semanticweb.org/owlapi/test#", "c"));
        assertTrue(o.containsAxiom(df.getOWLDataPropertyAssertionAxiom(DP, i, ZERO_ONE)));
        assertTrue(o.containsAxiom(df.getOWLDataPropertyAssertionAxiom(DP, i, ONE)));
        assertTrue(o.containsAxiom(df.getOWLDataPropertyAssertionAxiom(DP, i, ONESHORT)));
    }

    @Test
    public void shouldNotFindPaddedLiteralsEqualToNonPadded() {
        assertNotEquals(ZERO_ONE, ONE);
        assertNotEquals(ONE, ZERO_ONE);
    }
}
