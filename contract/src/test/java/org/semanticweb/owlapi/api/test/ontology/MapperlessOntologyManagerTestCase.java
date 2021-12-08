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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health Informatics Group
 * @since 3.2.3
 */
class MapperlessOntologyManagerTestCase extends TestBase {

    private static final IRI ONTOLOGY_IRI = iri("http://test.com/", "ont");

    private static OWLOntologyManager createManager() {
        OWLOntologyManager manager = setupManager();
        manager.getIRIMappers().clear();
        return manager;
    }

    @Test
    void testCreateOntologyWithIRI() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(ONTOLOGY_IRI);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    void testCreateOntologyWithAxioms() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(new HashSet<OWLAxiom>());
        assertNotNull(manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    void testCreateOntologyWithAxiomsAndIRI() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(new HashSet<OWLAxiom>(), ONTOLOGY_IRI);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    void testCreateOntologyWithIdWithVersionIRI() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        IRI versionIRI = iri("http://version/1", "");
        OWLOntologyID ontID = new OWLOntologyID(optional(ONTOLOGY_IRI), optional(versionIRI));
        OWLOntology ontology = manager.createOntology(ontID);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(versionIRI, ontology.getOntologyID().getVersionIRI().get());
        assertEquals(versionIRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    void testCreateOntologyWithId() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntologyID ontID = new OWLOntologyID(optional(ONTOLOGY_IRI), emptyOptional(IRI.class));
        OWLOntology ontology = manager.createOntology(ontID);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI().get());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }
}
