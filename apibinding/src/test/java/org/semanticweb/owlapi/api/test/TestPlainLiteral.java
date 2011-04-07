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

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

public class TestPlainLiteral extends TestCase {
	public void testPlainLiteral() {
		IRI iri = IRI
				.create("http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral");
		assertTrue(iri.isPlainLiteral());
		assertNotNull(OWLManager.getOWLDataFactory().getRDFPlainLiteral());
		assertNotNull(OWL2Datatype.getDatatype(iri));
	}

	public void testPlainLiteralFromEvren() {
		OWLDataFactory factory = OWLManager.createOWLOntologyManager()
				.getOWLDataFactory();
		OWLDatatype node = factory.getRDFPlainLiteral();
		assertTrue(node.isBuiltIn());
		assertNotNull(node.getBuiltInDatatype());
	}

	public void testPlainLiteralSerialization() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology();
		OWLDataProperty p = m.getOWLDataFactory().getOWLDataProperty(
				IRI.create("urn:test#p"));
		OWLIndividual i = m.getOWLDataFactory().getOWLNamedIndividual(
				IRI.create("urn:test#ind"));
		OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
				OWL2Datatype.RDF_PLAIN_LITERAL);
		m.addAxiom(o,
				m.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(p, i, l));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.saveOntology(o, out);
		String expected = "<test:p>test</test:p>";
		assertTrue(out.toString().contains(expected));
	}

	public void testPlainLiteralSerializationComments() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology();
		OWLIndividual i = m.getOWLDataFactory().getOWLNamedIndividual(
				IRI.create("urn:test#ind"));
		OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
				OWL2Datatype.RDF_PLAIN_LITERAL);
		m.addAxiom(
				o,
				m.getOWLDataFactory().getOWLAnnotationAssertionAxiom(
						m.getOWLDataFactory().getOWLAnnotationProperty(
								OWLRDFVocabulary.RDFS_COMMENT.getIRI()),
						i.asOWLNamedIndividual().getIRI(), l));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.saveOntology(o, out);
		String expected = "<rdfs:comment>test</rdfs:comment>";
		assertTrue(out.toString().contains(expected));
	}

	public void testPlainLiteralSerializationComments2() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology();
		OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
				OWL2Datatype.RDF_PLAIN_LITERAL);
		OWLAnnotation a = m.getOWLDataFactory().getOWLAnnotation(
				m.getOWLDataFactory().getRDFSComment(), l);
		m.applyChange(new AddOntologyAnnotation(o, a));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.saveOntology(o, out);
		String expected = "<rdfs:comment>test</rdfs:comment>";
		System.out.println(out.toString());
		assertTrue(out.toString().contains(expected));
	}
}
