package org.semanticweb.owlapi.api.test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.concurrent.ConcurrentOWLOntologyImpl;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
@SuppressWarnings("javadoc")
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
        assertThat(ontology.getOWLOntologyManager(), is(manager));
    }

    @Test
    public void shouldCreateConcurrentOntologyByDefault() {
        assertThat(ontology, is(instanceOf(ConcurrentOWLOntologyImpl.class)));
    }

    @Test
    public void shouldShareReadWriteLock() throws Exception {
        // Nasty, but not sure of another way to do this without exposing it in
        // the interface
        Field ontologyLockField = ConcurrentOWLOntologyImpl.class
            .getDeclaredField("readLock");
        ontologyLockField.setAccessible(true);
        Field ontologyManagerField = OWLOntologyManagerImpl.class
            .getDeclaredField("readLock");
        ontologyManagerField.setAccessible(true);
        assertThat(ontologyLockField.get(ontology),
            is(ontologyManagerField.get(manager)));
        ontologyLockField = ConcurrentOWLOntologyImpl.class
            .getDeclaredField("writeLock");
        ontologyLockField.setAccessible(true);
        ontologyManagerField = OWLOntologyManagerImpl.class
            .getDeclaredField("writeLock");
        ontologyManagerField.setAccessible(true);
        assertThat(ontologyLockField.get(ontology),
            is(ontologyManagerField.get(manager)));
    }
}
