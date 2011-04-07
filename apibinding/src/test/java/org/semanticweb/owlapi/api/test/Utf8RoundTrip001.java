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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Utf8RoundTrip001 extends TestCase {
	public void testUTF8roundTrip() {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			String onto = "Ontology(<http://protege.org/UTF8.owl>"
					+ "Declaration(Class(<http://protege.org/UTF8.owl#A>))"
					+ "AnnotationAssertion(<http://www.w3.org/2000/01/rdf-schema#label> <http://protege.org/UTF8.owl#A> "
					+ "\"Chinese=處方\"^^<http://www.w3.org/2001/XMLSchema#string>))";
			ByteArrayInputStream in = new ByteArrayInputStream(onto.getBytes());
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			manager.saveOntology(manager.loadOntologyFromOntologyDocument(in),
					out);
			System.out.println("Utf8RoundTrip001.testUTF8roundTrip()\n"+out.toString());
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
	}

	public void testPositiveUTF8roundTrip() {
		try {
			String NS = "http://protege.org/UTF8.owl";
			OWLOntologyManager manager;
			OWLOntology ontology;
			manager = OWLManager.createOWLOntologyManager();
			ontology = manager.createOntology(IRI.create(NS));
			OWLDataFactory factory = manager.getOWLDataFactory();
			OWLClass a = factory.getOWLClass(IRI.create(NS + "#A"));
			manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(a));
			OWLAnnotationAssertionAxiom axiom = factory
					.getOWLAnnotationAssertionAxiom(a.getIRI(), factory
							.getOWLAnnotation(factory.getRDFSLabel(),
									factory.getOWLLiteral("Chinese=處方")));
			manager.addAxiom(ontology, axiom);
			File ontFile = File.createTempFile("OWLApi-utf8", "owl");
			manager.saveOntology(ontology,
					new OWLFunctionalSyntaxOntologyFormat(),
					IRI.create(ontFile));
			System.out.println("Saved as " + ontFile);
			manager = OWLManager.createOWLOntologyManager();
			ontology = manager.loadOntologyFromOntologyDocument(ontFile);
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
	}
}
