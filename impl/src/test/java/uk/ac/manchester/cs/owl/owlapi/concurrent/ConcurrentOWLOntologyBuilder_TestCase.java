package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
class ConcurrentOWLOntologyBuilder_TestCase {

    private ConcurrentOWLOntologyBuilder builder;
    private OWLOntologyBuilder delegateBuilder = mock(OWLOntologyBuilder.class);
    private ReadWriteLock readWriteLock = mock(ReadWriteLock.class);
    private Lock readLock = mock(Lock.class);
    private OWLOntologyManager manager = mock(OWLOntologyManager.class);
    private OWLOntologyID ontologyId = mock(OWLOntologyID.class);
    private OWLOntology ontology = mock(OWLOntology.class);

    @BeforeEach
    void setUp() {
        when(delegateBuilder.createOWLOntology(manager, ontologyId)).thenReturn(ontology);
        when(readWriteLock.readLock()).thenReturn(readLock);
        builder = new ConcurrentOWLOntologyBuilder(delegateBuilder, readWriteLock);
    }

    @Test
    void shouldCallDelegate() {
        builder.createOWLOntology(manager, ontologyId);
        verify(delegateBuilder, times(1)).createOWLOntology(manager, ontologyId);
    }

    @Test
    void shouldCreateWrappedOntology() {
        OWLOntology concurrentOntology = builder.createOWLOntology(manager, ontologyId);
        assertThat(concurrentOntology, is(equalTo(ontology)));
    }
}
