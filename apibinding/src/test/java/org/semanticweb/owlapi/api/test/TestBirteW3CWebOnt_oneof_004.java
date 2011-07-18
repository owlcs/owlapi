/*
 /*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.api.test;

import java.util.Set;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class TestBirteW3CWebOnt_oneof_004 extends TestCase {
	public void testWebOnt() throws Exception {
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
		Set<String> expectedResult = new TreeSet<String>();
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
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(s));
//		StringDocumentTarget t=new StringDocumentTarget();
//		m.saveOntology(o, t);
//		System.out.println(t);
//		for(OWLAxiom ax:o.getAxioms()) {
//			System.out.println(ax);
//		}
		Set<String> result=new TreeSet<String>();
		for (OWLAxiom ax : o.getAxioms()) {
			result.add(ax.toString());
		}
		if(!result.equals(expectedResult)) {
		Set<String> intersection=new TreeSet<String>(result);
		intersection.retainAll(expectedResult);
		Set<String> s1=new TreeSet<String>(result);
		s1.removeAll(intersection);
		Set<String> s2=new TreeSet<String>(expectedResult);
		s2.removeAll(intersection);
		System.out.println("results:\n"+s1.toString().replace("), ", "),\n"));
		System.out.println("expected results:\n"+s2.toString().replace("), ", "),\n"));
		}
		assertEquals("Sets were supposed to be equal",result, expectedResult);
	}
}
