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

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2DLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;

public class GenIdGalenTest extends TestCase {
	public void testGenIdGalenFragment() throws Exception {
		String test = "<?xml version=\"1.0\"?>\n"
				+ "	<rdf:RDF \n"
				+ "	     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
				+ "	     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
				+ "	     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
				+ "	     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
				+ "	    <owl:Ontology rdf:about=\"http://www.co-ode.org/ontologies/galen\"/>\n"
				+ "<owl:ObjectProperty rdf:about=\"http://www.co-ode.org/ontologies/galen#hasQuantity\"/>\n"
				+ "<owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#test\">\n"
				+ "<rdfs:subClassOf><owl:Restriction>\n"
				+ "<owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#hasQuantity\"/>\n"
				+ "<owl:someValuesFrom>\n"
				+ "<owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#anotherTest\"/>"
				+ "</owl:someValuesFrom>\n"
				+ "</owl:Restriction></rdfs:subClassOf></owl:Class>"
				+ "	</rdf:RDF>";
		String input = "<?xml version=\"1.0\"?>\n"
				+ "	<rdf:RDF xmlns=\"http://www.co-ode.org/ontologies/galen#\"\n"
				+ "	     xml:base=\"http://www.co-ode.org/ontologies/galen\"\n"
				+ "	     xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n"
				+ "	     xmlns:galen=\"http://www.co-ode.org/ontologies/galen#\"\n"
				+ "	     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
				+ "	     xmlns:owl2xml=\"http://www.w3.org/2006/12/owl2-xml#\"\n"
				+ "	     xmlns:jms=\"http://jena.hpl.hp.com/2003/08/jms#\"\n"
				+ "	     xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
				+ "	     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
				+ "	     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
				+ "	     xmlns:rss=\"http://purl.org/rss/1.0/\"\n"
				+ "	     xmlns:daml=\"http://www.daml.org/2001/03/daml+oil#\"\n"
				+ "	     xmlns:vcard=\"http://www.w3.org/2001/vcard-rdf/3.0#\">\n"
				+ "	    <owl:Ontology rdf:about=\"http://www.co-ode.org/ontologies/galen\"/>\n"
				+ "	    <owl:ObjectProperty rdf:about=\"http://www.co-ode.org/ontologies/galen#actsSpecificallyOn\"/>\n"
				+ "	    <owl:ObjectProperty rdf:about=\"http://www.co-ode.org/ontologies/galen#hasMass\"/>\n"
				+ "	    <owl:ObjectProperty rdf:about=\"http://www.co-ode.org/ontologies/galen#hasQuantity\"/>\n"
				+ "	    <owl:ObjectProperty rdf:about=\"http://www.co-ode.org/ontologies/galen#isEnactmentOf\"/>\n"
				+ "	    <owl:ObjectProperty rdf:about=\"http://www.co-ode.org/ontologies/galen#isWithReferenceTo\"/>\n"
				+ "	    <owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#DietarySalt\"/>\n"
				+ "	    <owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#HighSodiumDiet\"/>\n"
				+ "	    <owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#Mass\"/>\n"
				+ "	    <owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#Protocol\"/>\n"
				+ "	    <owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#VoluntaryEating\"/>\n"
				+ "	    <owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#genid22\"/>\n"
				+ "	    <owl:Class rdf:about=\"http://www.co-ode.org/ontologies/galen#performance\"/>\n"
				+ "	<owl:Class>\n"
				+ "	        <rdfs:subClassOf rdf:resource=\"http://www.co-ode.org/ontologies/galen#HighSodiumDiet\"/>\n"
				+ "	        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
				+ "	            <rdf:Description rdf:about=\"http://www.co-ode.org/ontologies/galen#Protocol\"/>\n"
				+ "	            <owl:Restriction>\n"
				+ "	                <owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#isWithReferenceTo\"/>\n"
				+ "	                <owl:someValuesFrom>\n"
				+ "	                    <owl:Class>\n"
				+ "	                        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
				+ "	                            <rdf:Description rdf:about=\"http://www.co-ode.org/ontologies/galen#performance\"/>\n"
				+ "	                            <owl:Restriction>\n"
				+ "	                                <owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#isEnactmentOf\"/>\n"
				+ "	                                <owl:someValuesFrom>\n"
				+ "	                                    <owl:Class>\n"
				+ "	                                        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
				+ "	                                            <rdf:Description rdf:about=\"http://www.co-ode.org/ontologies/galen#VoluntaryEating\"/>\n"
				+ "	                                            <owl:Restriction>\n"
				+ "	                                                <owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#actsSpecificallyOn\"/>\n"
				+ "	                                                <owl:someValuesFrom>\n"
				+ "	                                                    <owl:Class>\n"
				+ "	                                                        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
				+ "	                                                            <rdf:Description rdf:about=\"http://www.co-ode.org/ontologies/galen#DietarySalt\"/>\n"
				+ "	                                                            <owl:Restriction>\n"
				+ "	                                                                <owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#hasMass\"/>\n"
				+ "	                                                                <owl:someValuesFrom>\n"
				+ "	                                                                    <owl:Class>\n"
				+ "	                                                                        <owl:intersectionOf rdf:parseType=\"Collection\">\n"
				+ "	                                                                            <rdf:Description rdf:about=\"http://www.co-ode.org/ontologies/galen#Mass\"/>\n"
				+ "	                                                                            <owl:Restriction>\n"
				+ "	                                                                                <owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#hasQuantity\"/>\n"
				+ "	                                                                                <owl:someValuesFrom rdf:parseType=\"Collection\">\n"
				+ "	                                                                                    <owl:Restriction>\n"
				+ "	                                                                                        <owl:onProperty rdf:resource=\"http://www.co-ode.org/ontologies/galen#isSpecificStructuralComponentOf\"/>\n"
				+ "	                                                                                        <owl:someValuesFrom rdf:resource=\"http://www.co-ode.org/ontologies/galen#GreatToe\"/>\n"
				+ "	                                                                                    </owl:Restriction>\n"
				+ "	                                                                                </owl:someValuesFrom>\n"
				+ "	                                                                            </owl:Restriction>\n"
				+ "	</owl:intersectionOf>\n"
				+ "	                                                                    </owl:Class>\n"
				+ "	                                                                </owl:someValuesFrom>\n"
				+ "	                                                            </owl:Restriction>\n"
				+ "	                                                        </owl:intersectionOf>\n"
				+ "	                                                    </owl:Class>\n"
				+ "	                                                </owl:someValuesFrom>\n"
				+ "	                                            </owl:Restriction>\n"
				+ "	                                        </owl:intersectionOf>\n"
				+ "	                                    </owl:Class>\n"
				+ "	                                </owl:someValuesFrom>\n"
				+ "	                            </owl:Restriction>\n"
				+ "	                        </owl:intersectionOf>\n"
				+ "	                    </owl:Class>\n"
				+ "	                </owl:someValuesFrom>\n"
				+ "	            </owl:Restriction>\n"
				+ "	        </owl:intersectionOf>\n" + "	    </owl:Class>\n"
				+ "	</rdf:RDF>";
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(test));
		for(OWLAxiom ax:o.getAxioms()) {
			System.out.println(ax);
		}
//		m.saveOntology(o, new OWLFunctionalSyntaxOntologyFormat(),
//				new SystemOutDocumentTarget());
		OWL2DLProfile profile = new OWL2DLProfile();
		OWLProfileReport report = profile.checkOntology(o);
		System.out.println("GenIdGalenTest.testGenIdGalenFragment() \n"
				+ report);
	}
}
