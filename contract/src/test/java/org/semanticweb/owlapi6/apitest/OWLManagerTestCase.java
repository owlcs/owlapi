package org.semanticweb.owlapi6.apitest;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.impl.OWLOntologyManagerImpl;
import org.semanticweb.owlapi6.impl.concurrent.ConcurrentOWLOntologyImpl;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyManager;

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
        assertThat(ontology.getOWLOntologyManager(), is(manager));
    }

    @Test
    public void shouldCreateConcurrentOntologyByDefault() {
        assertThat(ontology, is(instanceOf(ConcurrentOWLOntologyImpl.class)));
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
        assertThat(ontologyLockField.get(ontology), is(ontologyManagerField.get(manager)));
    }
}
