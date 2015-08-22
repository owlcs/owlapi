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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.*;

import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings({ "javadoc" })
public class OWLOntologyManagerImplTestCase extends TestBase {

    private OWLOntologyManager manager;

    @Before
    public void setUpManager() {
        manager = new OWLOntologyManagerImpl(new OWLDataFactoryImpl(),
            new ReentrantReadWriteLock());
        manager.getOntologyFactories().add(new OWLOntologyFactoryImpl(
            (om, id) -> new OWLOntologyImpl(om, id)));
    }

    @Test
    public void testContains() throws OWLOntologyCreationException {
        OWLOntology ont = manager
            .createOntology(IRI.getNextDocumentIRI("urn:testontology"));
        assertTrue(manager.contains(ont.getOntologyID()));
        assertNotNull("ontology should not be null",
            manager.getOntology(ont.getOntologyID()));
        assertTrue(contains(manager.ontologies(), ont));
        assertNotNull("IRI should not be null",
            manager.getOntologyDocumentIRI(ont));
        manager.removeOntology(ont);
        assertFalse(manager.contains(ont.getOntologyID()));
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        OWLOntology ontA = manager
            .createOntology(IRI.getNextDocumentIRI("urn:testontology"));
        OWLOntology ontB = manager
            .createOntology(IRI.getNextDocumentIRI("urn:testontology"));
        OWLImportsDeclaration decl = manager.getOWLDataFactory()
            .getOWLImportsDeclaration(
                get(ontB.getOntologyID().getOntologyIRI()));
        manager.applyChange(new AddImport(ontA, decl));
        assertTrue(contains(manager.directImports(ontA), ontB));
        manager.removeOntology(ontB);
        assertFalse(contains(manager.directImports(ontA), ontB));
    }

    @Test
    public void testImportsClosure() throws OWLException {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = manager
            .createOntology(IRI.getNextDocumentIRI("urn:testontology"));
        OWLOntology ontB = manager
            .createOntology(IRI.getNextDocumentIRI("urn:testontology"));
        OWLOntology ontC = manager
            .createOntology(IRI.getNextDocumentIRI("urn:testontology"));
        OWLImportsDeclaration declA = manager.getOWLDataFactory()
            .getOWLImportsDeclaration(
                get(ontB.getOntologyID().getOntologyIRI()));
        OWLImportsDeclaration declB = manager.getOWLDataFactory()
            .getOWLImportsDeclaration(
                get(ontC.getOntologyID().getOntologyIRI()));
        manager.applyChanges(new AddImport(ontA, declA), new AddImport(ontB, declB));
        assertTrue(contains(manager.importsClosure(ontA), ontA));
        assertTrue(contains(manager.importsClosure(ontA), ontB));
        assertTrue(contains(manager.importsClosure(ontA), ontC));
        assertTrue(contains(manager.importsClosure(ontB), ontB));
        assertTrue(contains(manager.importsClosure(ontB), ontC));
    }

    @Test
    public void testImportsLoad() throws OWLException {
        OWLOntology ontA = manager.createOntology(IRI.create("a"));
        assertTrue(ontA.directImports().count() == 0);
        IRI b = IRI.create("b");
        OWLImportsDeclaration declB = manager.getOWLDataFactory()
            .getOWLImportsDeclaration(b);
        manager.applyChange(new AddImport(ontA, declB));
        Set<IRI> directImportsDocuments = asSet(ontA.directImportsDocuments());
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        OWLOntology ontB = manager.createOntology(b);
        directImportsDocuments = asSet(ontA.directImportsDocuments());
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        assertEquals(1, ontA.directImports().count());
        assertTrue(contains(ontA.directImports(), ontB));
    }
}
