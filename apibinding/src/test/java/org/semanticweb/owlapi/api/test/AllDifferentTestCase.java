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
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AllDifferentTestCase extends TestCase {
	private String onto1 = "<?xml version=\"1.0\"?>\n"
			+ "<rdf:RDF xml:base = \"http://example.org/\" "
			+ "xmlns = \"http://example.org/\" xmlns:owl = \"http://www.w3.org/2002/07/owl#\" "
			+ "xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"> <owl:Ontology/>"
			+ "<owl:AllDifferent> "
			+ "<owl:distinctMembers rdf:parseType=\"Collection\"> "
			+ "<rdf:Description rdf:about=\"Peter\" /> "
			+ "<rdf:Description rdf:about=\"Peter_Griffin\" /> "
			+ "<rdf:Description rdf:about=\"Petre\" /> "
			+ "</owl:distinctMembers> </owl:AllDifferent> </rdf:RDF>";
	private String onto2 = "<?xml version=\"1.0\"?>\n"
			+ "<rdf:RDF xml:base = \"http://example.org/\" xmlns = \"http://example.org/\" "
			+ "xmlns:owl = \"http://www.w3.org/2002/07/owl#\" "
			+ "xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><owl:Ontology/>"
			+ "<owl:AllDifferent><owl:members rdf:parseType=\"Collection\">"
			+ "<rdf:Description rdf:about=\"Peter\" />"
			+ "<rdf:Description rdf:about=\"Peter_Griffin\" />"
			+ "<rdf:Description rdf:about=\"Petre\" />"
			+ "</owl:members></owl:AllDifferent></rdf:RDF>";

	public void testDistinctMembers() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o1 = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(
						onto1));
		System.out.println("AllDifferentTestCase.testDistinctMembers() "
				+ o1.getLogicalAxiomCount());
		OWLOntology o2 = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(
						onto2));
		System.out.println("AllDifferentTestCase.testDistinctMembers() "
				+ o2.getLogicalAxiomCount());
		assertTrue(o1.getLogicalAxiomCount() == o2.getLogicalAxiomCount());
	}
}
