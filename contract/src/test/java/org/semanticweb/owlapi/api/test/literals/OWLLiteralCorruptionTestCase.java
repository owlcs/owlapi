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
package org.semanticweb.owlapi.api.test.literals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class OWLLiteralCorruptionTestCase extends TestBase {

    static final String URN_TEST = "urn:test#";
    static final OWLLiteral ONESHORT =
        df.getOWLLiteral("1", OWL2Datatype.XSD_SHORT.getDatatype(df));
    static final OWLLiteral ZERO_ONE = df.getOWLLiteral("01", df.getIntegerOWLDatatype());
    static final OWLLiteral ONE = df.getOWLLiteral("1", df.getIntegerOWLDatatype());

    @Test
    void shouldroundTripLiteral() {
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
        assertEquals(literal.getLiteral(), testString, "Out = in ? false");
    }

    @Test
    void shouldRoundTripXMLLiteral() {
        OWLOntology o = getOWLOntology();
        OWLDataProperty p = df.getOWLDataProperty(iri(URN_TEST, "p"));
        OWLLiteral l = df.getOWLLiteral(TestFiles.literalXMl, OWL2Datatype.RDF_XML_LITERAL);
        OWLNamedIndividual i = df.getOWLNamedIndividual(iri(URN_TEST, "i"));
        o.add(df.getOWLDataPropertyAssertionAxiom(p, i, l));
        String string = saveOntology(o).toString();
        assertTrue(string.contains(TestFiles.literalXMl));
    }

    @Test
    void shouldFailOnMalformedXMLLiteral() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology();
        OWLDataProperty p = df.getOWLDataProperty(iri(URN_TEST, "p"));
        OWLLiteral l =
            df.getOWLLiteral(TestFiles.literalMalformedXML, OWL2Datatype.RDF_XML_LITERAL);
        OWLNamedIndividual i = df.getOWLNamedIndividual(iri(URN_TEST, "i"));
        o.add(df.getOWLDataPropertyAssertionAxiom(p, i, l));
        assertThrowsWithCauseMessage(OWLRuntimeException.class, OWLOntologyStorageException.class,
            "XML literal is not self contained", () -> saveOntology(o));
    }

    @Test
    void shouldAcceptXMLLiteralWithDatatype() {
        // A bug in OWLAPI means some incorrect XMLLiterals might have been
        // produced.
        // They should be understood in input and saved correctly on roundtrip
        String input = TestFiles.preamble + TestFiles.wrong + TestFiles.closure;
        OWLOntology o =
            loadOntologyFromString(input, IRI.generateDocumentIRI(), new RDFXMLDocumentFormat());
        OWLOntology o1 =
            loadOntologyFromString(TestFiles.preamble + TestFiles.correct + TestFiles.closure,
                IRI.generateDocumentIRI(), new RDFXMLDocumentFormat());
        equal(o, o1);
        assertTrue(
            saveOntology(o, new RDFXMLDocumentFormat()).toString().contains(TestFiles.correct));
    }

    @Test
    void shouldRoundtripPaddedLiterals() {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.roundtripPaddedLiterals,
                iri(URN_TEST, "test"), new FunctionalSyntaxDocumentFormat(), null));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
        OWLNamedIndividual i =
            df.getOWLNamedIndividual(iri("http://www.semanticweb.org/owlapi/test#", "c"));
        assertTrue(o.containsAxiom(df.getOWLDataPropertyAssertionAxiom(DP, i, ZERO_ONE)));
        assertTrue(o.containsAxiom(df.getOWLDataPropertyAssertionAxiom(DP, i, ONE)));
        assertTrue(o.containsAxiom(df.getOWLDataPropertyAssertionAxiom(DP, i, ONESHORT)));
    }

    @Test
    void shouldNotFindPaddedLiteralsEqualToNonPadded() {
        assertNotEquals(ZERO_ONE, ONE);
        assertNotEquals(ONE, ZERO_ONE);
    }
}
