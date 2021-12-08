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

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class OWLLiteralCorruptionTestCase extends TestBase {

    static final String URN_TEST = "urn:test#";
    static final OWLLiteral ONESHORT = Literal("1", Datatype(OWL2Datatype.XSD_SHORT.getIRI()));
    static final OWLLiteral ZERO_ONE = Literal("01", Integer());
    static final OWLLiteral ONE = Literal("1", Integer());

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
        assertEquals(Literal(testString).getLiteral(), testString, "Out = in ? false");
    }

    @Test
    void shouldRoundTripXMLLiteral() {
        OWLLiteral xml = Literal(TestFiles.literalXMl, OWL2Datatype.RDF_XML_LITERAL);
        OWLOntology o = o(DataPropertyAssertion(DP, i, xml));
        String string = saveOntology(o).toString();
        assertTrue(string.contains(TestFiles.literalXMl));
    }

    @Test
    void shouldFailOnMalformedXMLLiteral() {
        OWLLiteral malformed = Literal(TestFiles.literalMalformedXML, OWL2Datatype.RDF_XML_LITERAL);
        OWLOntology o = o(DataPropertyAssertion(DP, I, malformed));
        assertThrowsWithCauseMessage(OWLRuntimeException.class, OWLOntologyStorageException.class,
            "XML literal is not self contained", () -> saveOntology(o));
    }

    @Test
    void shouldAcceptXMLLiteralWithDatatype() {
        // A bug in OWLAPI means some incorrect XMLLiterals might have been
        // produced.
        // They should be understood in input and saved correctly on roundtrip
        String input = TestFiles.preamble + TestFiles.wrong + TestFiles.closure;
        OWLOntology o = loadFrom(input, IRI.generateDocumentIRI(), new RDFXMLDocumentFormat());
        OWLOntology o1 = loadFrom(TestFiles.preamble + TestFiles.correct + TestFiles.closure,
            IRI.generateDocumentIRI(), new RDFXMLDocumentFormat());
        equal(o, o1);
        assertTrue(
            saveOntology(o, new RDFXMLDocumentFormat()).toString().contains(TestFiles.correct));
    }

    @Test
    void shouldRoundtripPaddedLiterals() {
        OWLOntology o = loadFrom(new StringDocumentSource(TestFiles.roundtripPaddedLiterals,
            iriTest, new FunctionalSyntaxDocumentFormat(), null));
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o2);
        assertTrue(o.containsAxiom(DataPropertyAssertion(DP, indC, ZERO_ONE)));
        assertTrue(o.containsAxiom(DataPropertyAssertion(DP, indC, ONE)));
        assertTrue(o.containsAxiom(DataPropertyAssertion(DP, indC, ONESHORT)));
    }

    @Test
    void shouldNotFindPaddedLiteralsEqualToNonPadded() {
        assertNotEquals(ZERO_ONE, ONE);
        assertNotEquals(ONE, ZERO_ONE);
    }
}
