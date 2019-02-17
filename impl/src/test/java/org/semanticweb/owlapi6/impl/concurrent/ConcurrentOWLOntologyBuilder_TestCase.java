package org.semanticweb.owlapi6.impl.concurrent;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.semanticweb.owlapi6.impl.concurrent.ConcurrentOWLOntologyBuilder;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyBuilder;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OntologyConfigurator;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcurrentOWLOntologyBuilder_TestCase {

    private ConcurrentOWLOntologyBuilder builder;
    @Mock
    private OWLOntologyBuilder delegateBuilder;
    @Mock
    private ReadWriteLock readWriteLock;
    @Mock
    private Lock readLock;
    @Mock
    private OWLOntologyManager manager;
    @Mock
    private OWLOntologyID ontologyId;
    @Mock
    private OWLOntology ontology;
    @Mock
    private OntologyConfigurator config;

    @Before
    public void setUp() {
        when(delegateBuilder.createOWLOntology(manager, ontologyId, config)).thenReturn(ontology);
        when(readWriteLock.readLock()).thenReturn(readLock);
        builder = new ConcurrentOWLOntologyBuilder(delegateBuilder, readWriteLock);
    }

    @Test
    public void shouldCallDelegate() {
        builder.createOWLOntology(manager, ontologyId, config);
        verify(delegateBuilder, times(1)).createOWLOntology(manager, ontologyId, config);
    }

    @Test
    public void shouldCreateWrappedOntology() {
        OWLOntology concurrentOntology = builder.createOWLOntology(manager, ontologyId, config);
        assertThat(concurrentOntology, is(equalTo(ontology)));
    }
}
