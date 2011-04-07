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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class TestImportByLocation extends TestCase {
	public void testImportOntologyByLocation() {
		File f = new File("a.owl");
		try {

			createOntologyFile(IRI.create("http://a.com"), f);
			OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
			OWLDataFactory df = mngr.getOWLDataFactory();
			// have to load an ontology for it to get a document IRI
			OWLOntology a = mngr.loadOntologyFromOntologyDocument(f);
			IRI locA = mngr.getOntologyDocumentIRI(a);
			System.out.println("locA = " + locA);
			IRI bIRI = IRI.create("http://b.com");
			OWLOntology b = mngr.createOntology(bIRI);
			// import from the document location of a.owl (rather than the ontology IRI)
			mngr.applyChange(new AddImport(b, df.getOWLImportsDeclaration(locA)));
			assertEquals(1, b.getImportsDeclarations().size());
			assertEquals(1, b.getImports().size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		f.delete();
	}

	private OWLOntology createOntologyFile(IRI iri, File f) throws Exception {
		OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
		OWLOntology a = mngr.createOntology(iri);
		System.out.println("saving to " + f.getAbsolutePath());
		OutputStream out = new FileOutputStream(f);
		mngr.saveOntology(a, out);
		return a;
	}
}
