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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

import java.util.HashSet;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.2.3
 */
@SuppressWarnings("javadoc")
public class MapperlessOntologyManagerTestCase extends TestBase {

    @Nonnull
    private static final IRI ONTOLOGY_IRI = IRI("http://test.com/ont");

    private static OWLOntologyManager createManager() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.getIRIMappers().clear();
        return manager;
    }

    @Test
    public void testCreateOntologyWithIRI()
        throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(ONTOLOGY_IRI);
        assertEquals(ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithAxioms()
        throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(new HashSet<OWLAxiom>());
        assertNotNull("ontology should not be null",
            manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithAxiomsAndIRI()
        throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(new HashSet<OWLAxiom>(),
            ONTOLOGY_IRI);
        assertEquals(ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithIdWithVersionIRI()
        throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        IRI versionIRI = IRI("http://version/1");
        OWLOntologyID id = new OWLOntologyID(optional(ONTOLOGY_IRI), optional(versionIRI));
        OWLOntology ontology = manager.createOntology(id);
        assertEquals(ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(versionIRI,
            ontology.getOntologyID().getVersionIRI().get());
        assertEquals(versionIRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithId() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntologyID id = new OWLOntologyID(optional(ONTOLOGY_IRI),
            emptyOptional(IRI.class));
        OWLOntology ontology = manager.createOntology(id);
        assertEquals(ONTOLOGY_IRI,
            ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }
}
