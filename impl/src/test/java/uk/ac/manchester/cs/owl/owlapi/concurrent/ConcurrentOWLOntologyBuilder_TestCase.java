package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10/04/15
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("javadoc")
public class ConcurrentOWLOntologyBuilder_TestCase {

    private ConcurrentOWLOntologyBuilder builder;

    @Mock
    private OWLOntologyBuilder delegateBuilder;

    @Mock
    private ReadWriteLock readWriteLock;

    @Mock
    private Lock readLock, writeLock;

    @SuppressWarnings("null")
    @Mock
    @Nonnull
    private OWLOntologyManager manager;

    @SuppressWarnings("null")
    @Mock
    @Nonnull
    private OWLOntologyID ontologyId;

    @Mock
    private OWLOntology ontology;

    @Before
    public void setUp() {
        when(delegateBuilder.createOWLOntology(manager, ontologyId)).thenReturn(ontology);
        when(ontology.getOntologyID()).thenReturn(ontologyId);
        when(readWriteLock.readLock()).thenReturn(readLock);
        when(readWriteLock.writeLock()).thenReturn(writeLock);
        builder = new ConcurrentOWLOntologyBuilder(delegateBuilder, readWriteLock);
    }

    @Test
    public void shouldCallDelegate() {
        builder.createOWLOntology(manager, ontologyId);
        verify(delegateBuilder, times(1)).createOWLOntology(manager, ontologyId);
    }

    @Test
    public void shouldCreateWrappedOntology() {
        OWLOntology concurrentOntology = builder.createOWLOntology(manager, ontologyId);
        assertThat(concurrentOntology, is(equalTo(ontology)));
    }
}
