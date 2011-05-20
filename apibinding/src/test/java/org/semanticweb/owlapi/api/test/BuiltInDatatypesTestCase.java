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
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 */
public class BuiltInDatatypesTestCase extends TestCase {
	public void testBuiltInDatatypes() {
		try {
			OWL2Datatype dt = OWL2Datatype
					.getDatatype(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
			assertNotNull(dt);
			dt = OWL2Datatype.getDatatype(OWLRDFVocabulary.RDFS_LITERAL
					.getIRI());
			assertNotNull(dt);
			OWLDatatype datatype = OWLManager.getOWLDataFactory()
					.getOWLDatatype(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
			assertNotNull(datatype);
			OWL2Datatype test = datatype.getBuiltInDatatype();
			assertEquals(test, dt);
			if (datatype.isBuiltIn()) {
				System.out.println("[builtin"
						+ datatype.getBuiltInDatatype().getShortName() + "]");
			}
		} catch (RuntimeException e) {
			fail(e.getMessage());
		}
	}

	public void testFailure() {
		for (IRI type : OWL2Datatype.getDatatypeIRIs()) {
			OWLDatatype datatype = OWLManager.getOWLDataFactory()
					.getOWLDatatype(type);
			
			if (datatype.isBuiltIn()) {
				OWL2Datatype builtInDatatype = datatype.getBuiltInDatatype();
				assertNotNull(builtInDatatype);
			}
		}
	}
}
