package org.semanticweb.owlapi.impltest.concurrent;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.concurrent.locks.ReadWriteLock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.impl.concurrent.ConcurrentOWLOntologyBuilder;
import org.semanticweb.owlapi.impl.concurrent.ConcurrentOWLOntologyImpl;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyBuilder;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OntologyConfigurator;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
class ConcurrentOWLOntologyBuilder_TestCase {

    private ConcurrentOWLOntologyBuilder builder;
    private final OWLOntologyBuilder delegateBuilder = mock(OWLOntologyBuilder.class);
    private final ReadWriteLock readWriteLock = mock(ReadWriteLock.class);
    private final OWLOntologyManager manager = mock(OWLOntologyManager.class);
    private final OWLOntologyID ontologyId = mock(OWLOntologyID.class);
    private final OWLOntology ontology = mock(OWLOntology.class);
    private final OntologyConfigurator config = mock(OntologyConfigurator.class);

    @BeforeEach
    void setUp() {
        when(delegateBuilder.createOWLOntology(manager, ontologyId, config)).thenReturn(ontology);
        builder = new ConcurrentOWLOntologyBuilder(delegateBuilder, readWriteLock);
    }

    @Test
    void shouldCallDelegate() {
        builder.createOWLOntology(manager, ontologyId, config);
        verify(delegateBuilder, times(1)).createOWLOntology(manager, ontologyId, config);
    }

    @Test
    void shouldCreateWrappedOntology() throws NoSuchFieldException, SecurityException,
        IllegalArgumentException, IllegalAccessException {
        ConcurrentOWLOntologyImpl concurrentOntology =
            (ConcurrentOWLOntologyImpl) builder.createOWLOntology(manager, ontologyId, config);
        Field declaredField = ConcurrentOWLOntologyImpl.class.getDeclaredField("delegate");
        declaredField.setAccessible(true);
        assertSame(ontology, declaredField.get(concurrentOntology));
    }
}
