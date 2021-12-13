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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;

class TestCornerCasesTestCase extends TestBase {

    private static final String INF = "-INF";

    @Test
    void testFloatZeros() {
        // +0 and -0 are not equal
        assertFalse(Literal("0.0", Float()).equals(Literal("-0.0", Float())));
    }

    @Test
    void testIntegerRange() {
        String expected = "2147483648";
        assertEquals(expected, Literal(expected, Integer()).getLiteral());
    }

    @Test
    void testIntegerWithBlank() {
        String expected = "";
        assertEquals(expected, Literal(expected, Integer()).getLiteral());
    }

    @Test
    void testEnumInt() {
        Literal("1000000000000000000000000000000000000000", Integer());
    }

    @Test
    void testGetDataPropertyValues() {
        assertFalse(Literal("01", Integer()).equals(Literal("1", Integer())));
    }

    @Test
    void testWebOnt() {
        Set<String> expectedResult = new TreeSet<>();
        expectedResult.add(
            "DataPropertyRange(<http://www.w3.org/2002/03owlt/oneOf/premises004#p> DataOneOf(\"1\"^^xsd:integer \"2\"^^xsd:integer \"3\"^^xsd:integer \"4\"^^xsd:integer))");
        expectedResult
            .add("Declaration(DataProperty(<http://www.w3.org/2002/03owlt/oneOf/premises004#p>))");
        expectedResult
            .add("ClassAssertion(owl:Thing <http://www.w3.org/2002/03owlt/oneOf/premises004#i>)");
        expectedResult.add(
            "DataPropertyRange(<http://www.w3.org/2002/03owlt/oneOf/premises004#p> DataOneOf(\"4\"^^xsd:integer \"5\"^^xsd:integer \"6\"^^xsd:integer))");
        expectedResult.add(
            "ClassAssertion(DataMinCardinality(1 <http://www.w3.org/2002/03owlt/oneOf/premises004#p> rdfs:Literal) <http://www.w3.org/2002/03owlt/oneOf/premises004#i>)");
        OWLOntology o = loadFrom(TestFiles.webOnt, new RDFXMLDocumentFormat());
        assertEquals(str(expectedResult.stream()), str(o.axioms()));
    }

    @Test
    void testMinusInf() {
        OWLOntology o = loadFrom(TestFiles.minusInf, new FunctionalSyntaxDocumentFormat());
        assertTrue(saveOntology(o).toString().contains(INF));
        equal(o, roundTrip(o));
    }

    @Test
    void testLargeInteger() {
        OWLOntology o = loadFrom(TestFiles.largeInteger, new FunctionalSyntaxDocumentFormat());
        assertTrue(saveOntology(o).toString().contains(INF));
        equal(o, roundTrip(o));
    }
}
