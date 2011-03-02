package org.semanticweb.owlapi.api.test;

import java.util.Collections;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 17/02/2011
 */
public class MapperlessOntologyManagerTestCase extends TestCase {

    private static final IRI ONTOLOGY_IRI = IRI.create("http://test.com/ont");

    private OWLOntologyManager createManager() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        manager.clearIRIMappers();
        return manager;
    }

    public void testCreateOntologyWithIRI() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(ONTOLOGY_IRI);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateOntologyWithAxioms() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(Collections.<OWLAxiom>emptySet());
        assertNotNull(manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateOntologyWithAxiomsAndIRI() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntology ontology = manager.createOntology(Collections.<OWLAxiom>emptySet(), ONTOLOGY_IRI);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateOntologyWithIdWithVersionIRI() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        IRI versionIRI = IRI.create("http://version/1");
        OWLOntologyID id = new OWLOntologyID(ONTOLOGY_IRI, versionIRI);
        OWLOntology ontology = manager.createOntology(id);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(versionIRI, ontology.getOntologyID().getVersionIRI());
        assertEquals(versionIRI, manager.getOntologyDocumentIRI(ontology));
    }

    public void testCreateOntologyWithId() throws OWLOntologyCreationException {
        OWLOntologyManager manager = createManager();
        OWLOntologyID id = new OWLOntologyID(ONTOLOGY_IRI);
        OWLOntology ontology = manager.createOntology(id);
        assertEquals(ONTOLOGY_IRI, ontology.getOntologyID().getOntologyIRI());
        assertEquals(ONTOLOGY_IRI, manager.getOntologyDocumentIRI(ontology));
    }

}
