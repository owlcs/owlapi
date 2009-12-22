package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.SimpleIRIMapper;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 22-Dec-2009
 */
public class OWLOntologyManagerTestCase extends TestCase {

    public void testCreateAnonymousOntology() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology();
        assertNotNull(ontology);
        assertNotNull(ontology.getOntologyID());
        assertNull(ontology.getOntologyID().getDefaultDocumentIRI());
        assertNull(ontology.getOntologyID().getOntologyIRI());
        assertNull(ontology.getOntologyID().getVersionIRI());
        assertNotNull(manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateOntologyWithIRI() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        assertNotNull(ontology);
        assertNotNull(ontology.getOntologyID());
        assertEquals(ontologyIRI, ontology.getOntologyID().getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertNull(ontology.getOntologyID().getVersionIRI());
        assertEquals(ontologyIRI, manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateOntologyWithIRIAndVersionIRI() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
        IRI versionIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology/version");
        OWLOntology ontology = manager.createOntology(new OWLOntologyID(ontologyIRI, versionIRI));
        assertNotNull(ontology);
        assertNotNull(ontology.getOntologyID());
        assertEquals(versionIRI, ontology.getOntologyID().getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(versionIRI, ontology.getOntologyID().getVersionIRI());
        assertEquals(versionIRI, manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateOntologyWithIRIWithMapper() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
        IRI documentIRI = IRI.create("file:documentIRI");
        SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
        manager.addIRIMapper(mapper);
        OWLOntology ontology = manager.createOntology(ontologyIRI);
        assertNotNull(ontology);
        assertNotNull(ontology.getOntologyID());
        assertEquals(ontologyIRI, ontology.getOntologyID().getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertNull(ontology.getOntologyID().getVersionIRI());
        assertEquals(documentIRI, manager.getOntologyDocumentIRI(ontology));
    }

     public void testCreateOntologyWithIRIAndVersionIRIWithMapper() throws Exception {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
        IRI versionIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology/version");
        IRI documentIRI = IRI.create("file:documentIRI");
        SimpleIRIMapper mapper = new SimpleIRIMapper(versionIRI, documentIRI);
        manager.addIRIMapper(mapper);
        OWLOntology ontology = manager.createOntology(new OWLOntologyID(ontologyIRI, versionIRI));
        assertNotNull(ontology);
        assertNotNull(ontology.getOntologyID());
        assertEquals(versionIRI, ontology.getOntologyID().getDefaultDocumentIRI());
        assertEquals(ontologyIRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(versionIRI, ontology.getOntologyID().getVersionIRI());
        assertEquals(documentIRI, manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateDuplicateOntologyWithIRI() throws Exception {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
            OWLOntology ontology = manager.createOntology(ontologyIRI);
            manager.createOntology(ontologyIRI);
            fail("OWLOntologyAlreadyExistsException Not Thrown");
        }
        catch (OWLOntologyAlreadyExistsException e) {
            System.out.println("Caught OWLOntologyAlreadyExistsException: " + e.getMessage());
        }
    }

    public void testCreateDuplicateOntologyWithIRIAndVersionIRI() throws Exception {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
            IRI versionIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
            OWLOntology ontology = manager.createOntology(new OWLOntologyID(ontologyIRI, versionIRI));
            manager.createOntology(new OWLOntologyID(ontologyIRI, versionIRI));
            fail("OWLOntologyAlreadyExistsException Not Thrown");
        }
        catch (OWLOntologyAlreadyExistsException e) {
            System.out.println("Caught OWLOntologyAlreadyExistsException: " + e.getMessage());
        }
    }

    public void testCreateDuplicatedDocumentIRI() throws Exception {
        try {
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            IRI ontologyIRI = IRI.create("http://www.semanticweb.org/ontologies/ontology");
            IRI ontologyIRI2 = IRI.create("http://www.semanticweb.org/ontologies/ontology2");
            IRI documentIRI = IRI.create("file:documentIRI");
            manager.addIRIMapper(new SimpleIRIMapper(ontologyIRI, documentIRI));
            manager.addIRIMapper(new SimpleIRIMapper(ontologyIRI2, documentIRI));
            manager.createOntology(new OWLOntologyID(ontologyIRI));
            manager.createOntology(new OWLOntologyID(ontologyIRI2));
            fail("OWLOntologyDocumentAlreadyExistsException Not Thrown");
        }
        catch (OWLOntologyDocumentAlreadyExistsException e) {
            System.out.println("Caught OWLOntologyDocumentAlreadyExistsException: " + e.getMessage());
        }
    }
}
