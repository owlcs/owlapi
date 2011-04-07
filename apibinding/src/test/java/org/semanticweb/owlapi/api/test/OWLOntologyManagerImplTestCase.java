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

import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.NonMappingOntologyIRIMapper;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 02-Jan-2007<br><br>
 */
public class OWLOntologyManagerImplTestCase extends TestCase {

    private OWLOntologyManager manager;


    @Override
	protected void setUp() throws Exception {
        super.setUp();
        manager = new OWLOntologyManagerImpl(new OWLDataFactoryImpl());
        manager.addOntologyFactory(new EmptyInMemOWLOntologyFactory());
        manager.addIRIMapper(new NonMappingOntologyIRIMapper());
    }

    public void testContains() throws Exception {
        OWLOntology ont = manager.createOntology(TestUtils.createIRI());
        assertTrue(manager.contains(ont.getOntologyID()));
        assertNotNull(manager.getOntology(ont.getOntologyID()));
        assertTrue(manager.getOntologies().contains(ont));
        assertNotNull(manager.getOntologyDocumentIRI(ont));
        manager.removeOntology(ont);
        assertFalse(manager.contains(ont.getOntologyID()));
    }

    public void testImports() throws Exception {
        OWLOntology ontA = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontB = manager.createOntology(TestUtils.createIRI());
        OWLImportsDeclaration decl = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        manager.applyChange(new AddImport(ontA, decl));
        assertTrue(manager.getDirectImports(ontA).contains(ontB));
        manager.removeOntology(ontB);
        assertFalse(manager.getDirectImports(ontA).contains(ontB));
    }

    public void testImportsClosure() throws OWLException {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontB = manager.createOntology(TestUtils.createIRI());
        OWLOntology ontC = manager.createOntology(TestUtils.createIRI());
        OWLImportsDeclaration declA = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID().getOntologyIRI());
        OWLImportsDeclaration declB = manager.getOWLDataFactory().getOWLImportsDeclaration(ontC.getOntologyID().getOntologyIRI());
        manager.applyChange(new AddImport(ontA, declA));
        manager.applyChange(new AddImport(ontB, declB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontA));
        assertTrue(manager.getImportsClosure(ontA).contains(ontB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontC));
        assertTrue(manager.getImportsClosure(ontB).contains(ontB));
        assertTrue(manager.getImportsClosure(ontB).contains(ontC));
    }

}
