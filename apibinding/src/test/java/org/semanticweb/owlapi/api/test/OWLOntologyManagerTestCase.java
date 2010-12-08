package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyDocumentAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

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
