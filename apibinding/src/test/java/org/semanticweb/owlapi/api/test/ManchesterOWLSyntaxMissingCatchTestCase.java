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

import junit.framework.TestCase;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;

public class ManchesterOWLSyntaxMissingCatchTestCase extends TestCase {
	public void testManSyntaxEditorParser() {
		String onto = "<?xml version=\"1.0\"?>"
				+ "<!DOCTYPE rdf:RDF ["
				+ "<!ENTITY vin  \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\" >"
				+ "<!ENTITY food \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#\" >"
				+ "<!ENTITY owl  \"http://www.w3.org/2002/07/owl#\" >"
				+ "<!ENTITY xsd  \"http://www.w3.org/2001/XMLSchema#\" >"
				+ "]>"
				+ "<rdf:RDF"
				+ "xmlns     = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\""
				+ "xmlns:vin = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\""
				+ "xml:base  = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\""
				+ "xmlns:food= \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#\""
				+ "xmlns:owl = \"http://www.w3.org/2002/07/owl#\""
				+ "xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
				+ "xmlns:rdfs= \"http://www.w3.org/2000/01/rdf-schema#\""
				+ "xmlns:xsd = \"http://www.w3.org/2001/XMLSchema#\">"
				+ "<owl:Ontology rdf:about=\"\"><rdfs:comment>An example OWL ontology</rdfs:comment>"
				+ "<rdfs:label>Wine Ontology</rdfs:label></owl:Ontology>"
				+ "<owl:Class rdf:ID=\"VintageYear\" />"
				+ "<owl:DatatypeProperty rdf:ID=\"yearValue\"><rdfs:domain rdf:resource=\"#VintageYear\" />    <rdfs:range  rdf:resource=\"&xsd;positiveInteger\" />"
				+ "</owl:DatatypeProperty></rdf:RDF>";
		try {
			String expression = "yearValue some ";
			final OWLOntologyManager mngr = OWLManager
					.createOWLOntologyManager();
			final OWLOntology wine = mngr
					.loadOntologyFromOntologyDocument(new StringDocumentSource(
							onto));
			//					loadOntologyFromOntologyDocument(IRI
			//							.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine"));
			final Set<OWLOntology> ontologies = mngr.getOntologies();
			ShortFormProvider sfp = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
					mngr, wine);
			BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
					ontologies, sfp);
			ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
					mngr.getOWLDataFactory(), expression);
			parser.setDefaultOntology(wine);
			parser.setOWLEntityChecker(new ShortFormEntityChecker(
					shortFormProvider));
			//OWLClassExpression cls = 
				parser.parseClassExpression();
		} catch (UnparsableOntologyException e) {
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
			e.printStackTrace();
		}
	}
}
