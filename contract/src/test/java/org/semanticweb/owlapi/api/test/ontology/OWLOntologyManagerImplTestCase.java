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

import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
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
@SuppressWarnings({ "javadoc", })
public class OWLOntologyManagerImplTestCase {

    private OWLOntologyManager manager;

    @Before
    public void setUp() {
        manager = new OWLOntologyManagerImpl(new OWLDataFactoryImpl(), new ReentrantReadWriteLock());
        OWLOntologyBuilder builder = new OWLOntologyBuilder() {

            @Nonnull
            @Override
            public OWLOntology createOWLOntology(@Nonnull OWLOntologyManager manager,
                @Nonnull OWLOntologyID ontologyID) {
                return new OWLOntologyImpl(manager, ontologyID);
            }
        };
        manager.getOntologyFactories().add(new OWLOntologyFactoryImpl(builder));
    }

    @Test
    public void testContains() throws OWLOntologyCreationException {
        OWLOntology ont = manager.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        assertTrue(manager.contains(ont.getOntologyID()));
        assertNotNull("ontology should not be null", manager.getOntology(ont.getOntologyID()));
        assertTrue(manager.getOntologies().contains(ont));
        assertNotNull("IRI should not be null", manager.getOntologyDocumentIRI(ont));
        manager.removeOntology(ont);
        assertFalse(manager.contains(ont.getOntologyID()));
    }

    @Test
    public void testImports() throws OWLOntologyCreationException {
        OWLOntology ontA = manager.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        OWLOntology ontB = manager.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        OWLImportsDeclaration decl = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID()
            .getOntologyIRI().get());
        manager.applyChange(new AddImport(ontA, decl));
        assertTrue(manager.getDirectImports(ontA).contains(ontB));
        manager.removeOntology(ontB);
        assertFalse(manager.getDirectImports(ontA).contains(ontB));
    }

    @Test
    public void testImportsClosure() throws OWLException {
        // OntA -> OntB -> OntC (-> means imports)
        OWLOntology ontA = manager.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        OWLOntology ontB = manager.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        OWLOntology ontC = manager.createOntology(OWLOntologyDocumentSourceBase.getNextDocumentIRI("urn:testontology"));
        OWLImportsDeclaration declA = manager.getOWLDataFactory().getOWLImportsDeclaration(ontB.getOntologyID()
            .getOntologyIRI().get());
        OWLImportsDeclaration declB = manager.getOWLDataFactory().getOWLImportsDeclaration(ontC.getOntologyID()
            .getOntologyIRI().get());
        manager.applyChange(new AddImport(ontA, declA));
        manager.applyChange(new AddImport(ontB, declB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontA));
        assertTrue(manager.getImportsClosure(ontA).contains(ontB));
        assertTrue(manager.getImportsClosure(ontA).contains(ontC));
        assertTrue(manager.getImportsClosure(ontB).contains(ontB));
        assertTrue(manager.getImportsClosure(ontB).contains(ontC));
    }

    @Test
    public void testImportsLoad() throws OWLException {
        OWLOntology ontA = manager.createOntology(IRI.create("a"));
        assertTrue(ontA.getDirectImports().size() == 0);
        IRI b = IRI.create("b");
        OWLImportsDeclaration declB = manager.getOWLDataFactory().getOWLImportsDeclaration(b);
        manager.applyChange(new AddImport(ontA, declB));
        Set<IRI> directImportsDocuments = ontA.getDirectImportsDocuments();
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        OWLOntology ontB = manager.createOntology(b);
        directImportsDocuments = ontA.getDirectImportsDocuments();
        assertEquals(1, directImportsDocuments.size());
        assertTrue(directImportsDocuments.contains(b));
        assertEquals(1, ontA.getDirectImports().size());
        assertTrue(ontA.getDirectImports().contains(ontB));
    }
}
