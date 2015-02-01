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

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class TestCornerCasesTestCase extends TestBase {

    @Test
    public void testFloatZeros() {
        // +0 and -0 are not equal
        OWLDatatype type = df.getFloatOWLDatatype();
        OWLLiteral lit1 = df.getOWLLiteral("0.0", type);
        OWLLiteral lit2 = df.getOWLLiteral("-0.0", type);
        assertFalse(lit1.equals(lit2));
    }

    @Test
    public void testIntegerRange() {
        String expected = "2147483648";
        OWLDatatype type = df.getIntegerOWLDatatype();
        OWLLiteral lit = df.getOWLLiteral(expected, type);
        assertEquals(expected, lit.getLiteral());
    }

    @Test
    public void testEnumInt() {
        OWLDatatype type = df.getIntegerOWLDatatype();
        df.getOWLLiteral("1000000000000000000000000000000000000000", type);
    }

    @Test
    public void testGetDataPropertyValues() {
        OWLDatatype type = df.getIntegerOWLDatatype();
        OWLLiteral lit1 = df.getOWLLiteral("01", type);
        OWLLiteral lit2 = df.getOWLLiteral("1", type);
        assertFalse(lit1.equals(lit2));
    }

    @Test
    public void testWebOnt() throws OWLOntologyCreationException {
        String s = "<!DOCTYPE rdf:RDF [\n"
                + "   <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\">\n"
                + "   <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
                + "]>\n"
                + "<rdf:RDF\n"
                + " xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + " xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + " xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
                + " xmlns:first=\"http://www.w3.org/2002/03owlt/oneOf/premises004#\"\n"
                + " xml:base=\"http://www.w3.org/2002/03owlt/oneOf/premises004\" >\n"
                + " <owl:Ontology/>\n"
                + " <owl:DatatypeProperty rdf:ID=\"p\">\n"
                + "  <rdfs:range>\n"
                + "   <owl:DataRange>\n"
                + "    <owl:oneOf>\n"
                + "     <rdf:List>\n"
                + "      <rdf:first rdf:datatype=\"&xsd;integer\">1</rdf:first>\n"
                + "      <rdf:rest>\n"
                + "       <rdf:List>\n"
                + "        <rdf:first rdf:datatype=\"&xsd;integer\">2</rdf:first>\n"
                + "        <rdf:rest>\n"
                + "         <rdf:List>\n"
                + "          <rdf:first rdf:datatype=\"&xsd;integer\">3</rdf:first>\n"
                + "          <rdf:rest>\n"
                + "           <rdf:List>\n"
                + "            <rdf:first rdf:datatype=\"&xsd;integer\">4</rdf:first>\n"
                + "            <rdf:rest rdf:resource=\"&rdf;nil\"/>\n"
                + "           </rdf:List>\n"
                + "          </rdf:rest>\n"
                + "         </rdf:List>\n"
                + "        </rdf:rest>\n"
                + "       </rdf:List>\n"
                + "      </rdf:rest>\n"
                + "     </rdf:List>\n"
                + "    </owl:oneOf>\n"
                + "   </owl:DataRange>\n"
                + "  </rdfs:range>\n"
                + "  <rdfs:range>\n"
                + "   <owl:DataRange>\n"
                + "    <owl:oneOf>\n"
                + "     <rdf:List>\n"
                + "      <rdf:first rdf:datatype=\"&xsd;integer\">4</rdf:first>\n"
                + "      <rdf:rest>\n"
                + "       <rdf:List>\n"
                + "        <rdf:first rdf:datatype=\"&xsd;integer\">5</rdf:first>\n"
                + "        <rdf:rest>\n"
                + "         <rdf:List>\n"
                + "          <rdf:first rdf:datatype=\"&xsd;integer\">6</rdf:first>\n"
                + "          <rdf:rest rdf:resource=\"&rdf;nil\"/>\n"
                + "         </rdf:List>\n"
                + "        </rdf:rest>\n"
                + "       </rdf:List>\n"
                + "      </rdf:rest>\n"
                + "     </rdf:List>\n"
                + "    </owl:oneOf>\n"
                + "   </owl:DataRange>\n"
                + "  </rdfs:range>\n"
                + " </owl:DatatypeProperty>\n"
                + " <owl:Thing rdf:ID=\"i\">\n"
                + "  <rdf:type>\n"
                + "   <owl:Restriction>\n"
                + "    <owl:onProperty rdf:resource=\"#p\"/>\n"
                + "    <owl:minCardinality rdf:datatype=\"&xsd;int\">1</owl:minCardinality>\n"
                + "   </owl:Restriction>\n" + "  </rdf:type>\n"
                + " </owl:Thing>\n" + "</rdf:RDF>";
        Set<String> expectedResult = new TreeSet<>();
        expectedResult
                .add("DataPropertyRange(<http://www.w3.org/2002/03owlt/oneOf/premises004#p> DataOneOf(\"1\"^^xsd:integer \"2\"^^xsd:integer \"3\"^^xsd:integer \"4\"^^xsd:integer ))");
        expectedResult
                .add("Declaration(DataProperty(<http://www.w3.org/2002/03owlt/oneOf/premises004#p>))");
        expectedResult
                .add("ClassAssertion(owl:Thing <http://www.w3.org/2002/03owlt/oneOf/premises004#i>)");
        expectedResult
                .add("DataPropertyRange(<http://www.w3.org/2002/03owlt/oneOf/premises004#p> DataOneOf(\"4\"^^xsd:integer \"5\"^^xsd:integer \"6\"^^xsd:integer ))");
        expectedResult
                .add("ClassAssertion(DataMinCardinality(1 <http://www.w3.org/2002/03owlt/oneOf/premises004#p> rdfs:Literal) <http://www.w3.org/2002/03owlt/oneOf/premises004#i>)");
        OWLOntology o = loadOntologyFromString(s);
        Set<String> result = new TreeSet<>();
        o.axioms().forEach(ax -> result.add(ax.toString()));
        if (!result.equals(expectedResult)) {
            Set<String> intersection = new TreeSet<>(result);
            intersection.retainAll(expectedResult);
            Set<String> s1 = new TreeSet<>(result);
            s1.removeAll(intersection);
            Set<String> s2 = new TreeSet<>(expectedResult);
            s2.removeAll(intersection);
        }
        assertEquals("Sets were supposed to be equal", result, expectedResult);
    }

    @Test
    public void testMinusInf() throws Exception {
        String input = "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
                + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
                + "Prefix(:=<http://test.org/test#>)\n"
                + "Ontology(\nDeclaration(NamedIndividual(:a))\n"
                + "Declaration(DataProperty(:dp))\n"
                + "Declaration(Class(:A))\n"
                + "SubClassOf(:A DataAllValuesFrom(:dp owl:real))"
                + "\nSubClassOf(:A \n"
                + "DataSomeValuesFrom(:dp DataOneOf(\"-INF\"^^xsd:float \"-0\"^^xsd:integer))"
                + "\n)\nClassAssertion(:A :a))";
        OWLOntology o = loadOntologyFromString(input);
        assertTrue(saveOntology(o).toString().contains("-INF"));
        OWLOntology o1 = roundTrip(o);
        assertEquals("Ontologies were supposed to be the same",
                o.getLogicalAxioms(), o1.getLogicalAxioms());
    }

    @Test
    public void testLargeInteger() throws Exception {
        String input = "Prefix(xsd:=<http://www.w3.org/2001/XMLSchema#>)\n"
                + "Prefix(owl:=<http://www.w3.org/2002/07/owl#>)\n"
                + "Prefix(:=<http://test.org/test#>)\n"
                + "Ontology(\nDeclaration(NamedIndividual(:a))\n"
                + "Declaration(DataProperty(:dp))\n"
                + "Declaration(Class(:A))\n"
                + "SubClassOf(:A DataAllValuesFrom(:dp owl:real))"
                + "\nSubClassOf(:A \n"
                + "DataSomeValuesFrom(:dp DataOneOf(\"-INF\"^^xsd:float \"-0\"^^xsd:integer))"
                + "\n)" + '\n' + "ClassAssertion(:A :a)" + "\n)";
        OWLOntology o = loadOntologyFromString(input);
        assertTrue(saveOntology(o).toString().contains("-INF"));
        OWLOntology o1 = roundTrip(o);
        assertEquals("Ontologies were supposed to be the same",
                o.getLogicalAxioms(), o1.getLogicalAxioms());
    }
}
