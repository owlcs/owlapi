package org.semanticweb.owlapi.apitest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.impl.OWLOntologyManagerImpl;
import org.semanticweb.owlapi.impl.concurrent.ConcurrentOWLOntologyImpl;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
public class OWLManagerTestCase {

    private OWLOntologyManager manager;
    private OWLOntology ontology;

    @Before
    public void setUp() throws Exception {
        manager = OWLManager.createOWLOntologyManager();
        ontology = manager.createOntology();
    }

    @Test
    public void shouldCreateOntologyWithCorrectManager() {
        assertEquals(manager, ontology.getOWLOntologyManager());
    }

    @Test
    public void shouldCreateConcurrentOntologyByDefault() {
        assertTrue(ConcurrentOWLOntologyImpl.class.isInstance(ontology));
    }

    @Test
    public void shouldShareReadWriteLockOnConcurrentManager() throws Exception {
        // Nasty, but not sure of another way to do this without exposing it in
        // the interface
        manager = OWLManager.createConcurrentOWLOntologyManager();
        ontology = manager.createOntology();
        Field ontologyLockField = ConcurrentOWLOntologyImpl.class.getDeclaredField("lock");
        ontologyLockField.setAccessible(true);
        Field ontologyManagerField = OWLOntologyManagerImpl.class.getDeclaredField("lock");
        ontologyManagerField.setAccessible(true);
        assertEquals(ontologyManagerField.get(manager), ontologyLockField.get(ontology));
    }
}
