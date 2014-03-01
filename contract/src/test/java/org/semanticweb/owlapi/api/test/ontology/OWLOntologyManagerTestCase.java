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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyDocumentAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management
 *         Group, Date: 22-Dec-2009
 */
@SuppressWarnings("javadoc")
public class OWLOntologyManagerTestCase {

    @Test
    public void testCreateAnonymousOntology() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager.createOntology();
        assertNotNull("ontology should not be null", ontology);
        assertNotNull("ontology id should not be null",
                ontology.getOntologyID());
        assertNull(ontology.getOntologyID().getDefaultDocumentIRI());
        assertNull(ontology.getOntologyID().getOntologyIRI());
        assertNull(ontology.getOntologyID().getVersionIRI());
        assertNotNull("iri should not be null",
                manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithIRI() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        IRI ontologyIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        assertNotNull("ontology should not be null", ontology);
        assertNotNull("ontology id should not be null",
                ontology.getOntologyID());
        assertEquals(ontologyIRI, ontology.getOntologyID()
                .getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertNull(ontology.getOntologyID().getVersionIRI());
        assertEquals(ontologyIRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithIRIAndVersionIRI() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        IRI ontologyIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        IRI versionIRI = IRI("http://www.semanticweb.org/ontologies/ontology/version");
        OWLOntology ontology = manager.createOntology(new OWLOntologyID(
                ontologyIRI, versionIRI));
        assertNotNull("ontology should not be null", ontology);
        assertNotNull("ontology id should not be null",
                ontology.getOntologyID());
        assertEquals(versionIRI, ontology.getOntologyID()
                .getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(versionIRI, ontology.getOntologyID().getVersionIRI());
        assertEquals(versionIRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithIRIWithMapper() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        IRI ontologyIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        IRI documentIRI = IRI("file:documentIRI");
        SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
        manager.addIRIMapper(mapper);
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        assertNotNull("ontology should not be null", ontology);
        assertNotNull("ontology id should not be null",
                ontology.getOntologyID());
        assertEquals(ontologyIRI, ontology.getOntologyID()
                .getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertNull(ontology.getOntologyID().getVersionIRI());
        assertEquals(documentIRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test
    public void testCreateOntologyWithIRIAndVersionIRIWithMapper()
            throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        IRI ontologyIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        IRI versionIRI = IRI("http://www.semanticweb.org/ontologies/ontology/version");
        IRI documentIRI = IRI("file:documentIRI");
        SimpleIRIMapper mapper = new SimpleIRIMapper(versionIRI, documentIRI);
        manager.addIRIMapper(mapper);
        OWLOntology ontology = manager.createOntology(new OWLOntologyID(
                ontologyIRI, versionIRI));
        assertNotNull("ontology should not be null", ontology);
        assertNotNull("ontology id should not be null",
                ontology.getOntologyID());
        assertEquals(versionIRI, ontology.getOntologyID()
                .getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(versionIRI, ontology.getOntologyID().getVersionIRI());
        assertEquals(documentIRI, manager.getOntologyDocumentIRI(ontology));
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testCreateDuplicateOntologyWithIRI() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        IRI ontologyIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        manager.createOntology(ontologyIRI);
        manager.createOntology(ontologyIRI);
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testCreateDuplicateOntologyWithIRIAndVersionIRI()
            throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        IRI ontologyIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        IRI versionIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        manager.createOntology(new OWLOntologyID(ontologyIRI, versionIRI));
        manager.createOntology(new OWLOntologyID(ontologyIRI, versionIRI));
    }

    @Test(expected = OWLOntologyDocumentAlreadyExistsException.class)
    public void testCreateDuplicatedDocumentIRI() throws Exception {
        OWLOntologyManager manager = Factory.getManager();
        IRI ontologyIRI = IRI("http://www.semanticweb.org/ontologies/ontology");
        IRI ontologyIRI2 = IRI("http://www.semanticweb.org/ontologies/ontology2");
        IRI documentIRI = IRI("file:documentIRI");
        manager.addIRIMapper(new SimpleIRIMapper(ontologyIRI, documentIRI));
        manager.addIRIMapper(new SimpleIRIMapper(ontologyIRI2, documentIRI));
        manager.createOntology(new OWLOntologyID(ontologyIRI));
        manager.createOntology(new OWLOntologyID(ontologyIRI2));
    }
}
