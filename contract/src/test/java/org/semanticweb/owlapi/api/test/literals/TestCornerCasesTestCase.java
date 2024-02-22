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
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

class TestCornerCasesTestCase extends TestBase {

    @Test
    void testFloatZeros() {
        // +0 and -0 are not equal
        OWLDatatype type = df.getFloatOWLDatatype();
        OWLLiteral lit1 = df.getOWLLiteral("0.0", type);
        OWLLiteral lit2 = df.getOWLLiteral("-0.0", type);
        assertFalse(lit1.equals(lit2));
    }

    @Test
    void testIntegerRange() {
        String expected = "2147483648";
        OWLDatatype type = df.getIntegerOWLDatatype();
        OWLLiteral lit = df.getOWLLiteral(expected, type);
        assertEquals(expected, lit.getLiteral());
    }

    @Test
    void testIntegerWithBlank() {
        String expected = "";
        OWLDatatype type = df.getIntegerOWLDatatype();
        OWLLiteral lit = df.getOWLLiteral(expected, type);
        assertEquals(expected, lit.getLiteral());
    }

    @Test
    void testEnumInt() {
        OWLDatatype type = df.getIntegerOWLDatatype();
        df.getOWLLiteral("1000000000000000000000000000000000000000", type);
    }

    @Test
    void testGetDataPropertyValues() {
        OWLDatatype type = df.getIntegerOWLDatatype();
        OWLLiteral lit1 = df.getOWLLiteral("01", type);
        OWLLiteral lit2 = df.getOWLLiteral("1", type);
        assertFalse(lit1.equals(lit2));
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
        OWLOntology o = loadOntologyFromString(TestFiles.webOnt, new RDFXMLDocumentFormat());
        Set<String> result = new TreeSet<>();
        o.getAxioms().forEach(ax -> result.add(ax.toString()));
        if (!result.equals(expectedResult)) {
            Set<String> intersection = new TreeSet<>(result);
            intersection.retainAll(expectedResult);
            Set<String> s1 = new TreeSet<>(result);
            s1.removeAll(intersection);
            Set<String> s2 = new TreeSet<>(expectedResult);
            s2.removeAll(intersection);
        }
        assertEquals(result, expectedResult);
    }

    @Test
    void testMinusInf() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.minusInf, new FunctionalSyntaxDocumentFormat());
        assertTrue(saveOntology(o).toString().contains("-INF"));
        equal(o, roundTrip(o));
    }

    @Test
    void testLargeInteger() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.largeInteger, new FunctionalSyntaxDocumentFormat());
        assertTrue(saveOntology(o).toString().contains("-INF"));
        equal(o, roundTrip(o));
    }

    @Test
    void testCompareWithLargeInteger() {
        OWLLiteral smallInteger = df.getOWLLiteral(123);
        OWLLiteral largeInteger = df.getOWLLiteral("149597870700", df.getIntegerOWLDatatype());
        assertFalse(smallInteger.equals(largeInteger));
    }
}
