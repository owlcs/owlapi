package org.semanticweb.owlapi6.impltest.concurrent;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.semanticweb.owlapi6.impl.concurrent.ConcurrentOWLOntologyBuilder;
import org.semanticweb.owlapi6.impl.concurrent.ConcurrentOWLOntologyImpl;
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
        builder = new ConcurrentOWLOntologyBuilder(delegateBuilder, readWriteLock);
    }

    @Test
    public void shouldCallDelegate() {
        builder.createOWLOntology(manager, ontologyId, config);
        verify(delegateBuilder, times(1)).createOWLOntology(manager, ontologyId, config);
    }

    @Test
    public void shouldCreateWrappedOntology() throws NoSuchFieldException, SecurityException,
        IllegalArgumentException, IllegalAccessException {
        ConcurrentOWLOntologyImpl concurrentOntology =
            (ConcurrentOWLOntologyImpl) builder.createOWLOntology(manager, ontologyId, config);
        Field declaredField = ConcurrentOWLOntologyImpl.class.getDeclaredField("delegate");
        declaredField.setAccessible(true);
        assertSame(ontology, declaredField.get(concurrentOntology));
    }
}
