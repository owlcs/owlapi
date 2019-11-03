package org.semanticweb.owlapi6.impltest.concurrent;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.semanticweb.owlapi6.model.parameters.Imports.INCLUDED;

import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.semanticweb.owlapi6.impl.concurrent.ConcurrentOWLOntologyImpl;
import org.semanticweb.owlapi6.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.ChangeReport;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLMutableOntology;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLOntologyChange;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLPrimitive;
import org.semanticweb.owlapi6.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi6.utilities.OWLAxiomSearchFilter;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 03/04/15
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcurrentOWLOntologyImpl_TestCase {

    @Mock
    private ReentrantReadWriteLock readWriteLock;
    @Mock
    private ReentrantReadWriteLock.ReadLock readLock;
    @Mock
    private ReentrantReadWriteLock.WriteLock writeLock;
    @Mock
    private OWLMutableOntology delegate;
    @Mock
    private IRI iri;
    private ConcurrentOWLOntologyImpl ontology;

    interface TestConsumer {

        void consume(InOrder i) throws Exception;
    }

    @Before
    public void setUp() {
        when(readWriteLock.readLock()).thenReturn(readLock);
        when(readWriteLock.writeLock()).thenReturn(writeLock);
        when(delegate.applyChanges(anyList()))
            .thenReturn(new ChangeReport(Collections.emptyList(), Collections.emptyList()));
        ontology = spy(new ConcurrentOWLOntologyImpl(delegate, readWriteLock));
    }

    private void readLock(TestConsumer f) {
        InOrder order = Mockito.inOrder(readLock, delegate, readLock);
        order.verify(readLock, times(1)).lock();
        try {
            f.consume(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    private void writeLock(TestConsumer f) {
        InOrder order = Mockito.inOrder(writeLock, delegate, writeLock);
        order.verify(writeLock, times(1)).lock();
        try {
            f.consume(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
        order.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_isEmpty_withReadLock() {
        ontology.isEmpty();
        readLock(i -> i.verify(delegate).isEmpty());
    }

    @Test
    public void shouldDelegateTo_setOWLOntologyManager_withReadLock() {
        ontology.setOWLOntologyManager(manager);
        writeLock(i -> i.verify(delegate).setOWLOntologyManager(manager));
    }

    @Mock
    OWLOntologyManager manager;

    @Test
    public void shouldDelegateTo_getOntologyID_withReadLock() {
        ontology.getOntologyID();
        readLock(i -> i.verify(delegate).getOntologyID());
    }

    @Test
    public void shouldDelegateTo_isAnonymous_withReadLock() {
        ontology.isAnonymous();
        readLock(i -> i.verify(delegate).isAnonymous());
    }

    @Mock
    OWLEntity entity;

    @Test
    public void shouldDelegateTo_isDeclared_withReadLock() {
        ontology.isDeclared(entity, INCLUDED);
        readLock(i -> i.verify(delegate).isDeclared(entity, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_isDeclared_withReadLock_2() {
        ontology.isDeclared(entity);
        readLock(i -> i.verify(delegate).isDeclared(entity));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock() throws OWLOntologyStorageException {
        ontology.saveOntology(format, iri);
        readLock(i -> i.verify(delegate).saveOntology(format, iri));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_2() throws OWLOntologyStorageException {
        ontology.saveOntology(format, outputStream);
        readLock(i -> i.verify(delegate).saveOntology(format, outputStream));
    }

    @Mock
    OutputStream outputStream;
    @Mock
    OWLDocumentFormat format;

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_3() throws OWLOntologyStorageException {
        ontology.saveOntology(target);
        readLock(i -> i.verify(delegate).saveOntology(target));
    }

    @Mock
    OWLOntologyDocumentTarget target;

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_4() throws OWLOntologyStorageException {
        ontology.saveOntology(format, target);
        readLock(i -> i.verify(delegate).saveOntology(format, target));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_5() throws OWLOntologyStorageException {
        ontology.saveOntology();
        readLock(i -> i.verify(delegate).saveOntology());
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_6() throws OWLOntologyStorageException {
        ontology.saveOntology(iri);
        readLock(i -> i.verify(delegate).saveOntology(iri));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_7() throws OWLOntologyStorageException {
        ontology.saveOntology(outputStream);
        readLock(i -> i.verify(delegate).saveOntology(outputStream));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_8() throws OWLOntologyStorageException {
        ontology.saveOntology(format);
        readLock(i -> i.verify(delegate).saveOntology(format));
    }

    @Test
    public void shouldDelegateTo_isTopEntity_withReadLock() {
        ontology.isTopEntity();
        readLock(i -> i.verify(delegate).isTopEntity());
    }

    @Test
    public void shouldDelegateTo_isBottomEntity_withReadLock() {
        ontology.isBottomEntity();
        readLock(i -> i.verify(delegate).isBottomEntity());
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock() {
        ontology.containsEntityInSignature(entity);
        readLock(i -> i.verify(delegate).containsEntityInSignature(entity));
    }

    @Test
    public void shouldDelegateTo_getDataPropertiesInSignature_withReadLock() {
        ontology.dataPropertiesInSignature();
        readLock(i -> i.verify(delegate).dataPropertiesInSignature());
    }

    @Mock
    OWLDatatype datatype;

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock() {
        ontology.getAxiomCount(AxiomType.SUBCLASS_OF, INCLUDED);
        readLock(i -> i.verify(delegate).getAxiomCount(AxiomType.SUBCLASS_OF, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_2() {
        ontology.getAxiomCount(INCLUDED);
        readLock(i -> i.verify(delegate).getAxiomCount(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getLogicalAxiomCount_withReadLock() {
        ontology.getLogicalAxiomCount(INCLUDED);
        readLock(i -> i.verify(delegate).getLogicalAxiomCount(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock() {
        ontology.containsAxiom(axiom, INCLUDED, AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
        readLock(i -> i.verify(delegate).containsAxiom(axiom, INCLUDED,
            AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS));
    }

    @Mock
    OWLAxiom axiom;
    @Mock
    OWLPrimitive primitive;

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock_2() {
        ontology.containsAxiom(axiom);
        readLock(i -> i.verify(delegate).containsAxiom(axiom));
    }

    @Mock
    OWLDataProperty dataProperty;
    @Mock
    OWLIndividual individual;
    @Mock
    OWLObjectPropertyExpression objectProperty;

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_5() {
        ontology.getAxiomCount();
        readLock(i -> i.verify(delegate).getAxiomCount());
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_6() {
        ontology.getAxiomCount(AxiomType.SUBCLASS_OF);
        readLock(i -> i.verify(delegate).getAxiomCount(AxiomType.SUBCLASS_OF));
    }

    @Test
    public void shouldDelegateTo_getLogicalAxiomCount_withReadLock_3() {
        ontology.getLogicalAxiomCount();
        readLock(i -> i.verify(delegate).getLogicalAxiomCount());
    }

    @Test
    public void shouldDelegateTo_containsAxiomIgnoreAnnotations_withReadLock_2() {
        ontology.containsAxiomIgnoreAnnotations(axiom);
        readLock(i -> i.verify(delegate).containsAxiomIgnoreAnnotations(axiom));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_2() {
        ontology.containsEntityInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsEntityInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_3() {
        ontology.containsEntityInSignature(iri);
        readLock(i -> i.verify(delegate).containsEntityInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_4() {
        ontology.containsEntityInSignature(entity, INCLUDED);
        readLock(i -> i.verify(delegate).containsEntityInSignature(entity, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock() {
        ontology.containsClassInSignature(iri);
        readLock(i -> i.verify(delegate).containsClassInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock_2() {
        ontology.containsClassInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsClassInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock() {
        ontology.containsObjectPropertyInSignature(iri);
        readLock(i -> i.verify(delegate).containsObjectPropertyInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock_2() {
        ontology.containsObjectPropertyInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsObjectPropertyInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock() {
        ontology.containsDataPropertyInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsDataPropertyInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock_2() {
        ontology.containsDataPropertyInSignature(iri);
        readLock(i -> i.verify(delegate).containsDataPropertyInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock() {
        ontology.containsAnnotationPropertyInSignature(iri);
        readLock(i -> i.verify(delegate).containsAnnotationPropertyInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock_2() {
        ontology.containsAnnotationPropertyInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsAnnotationPropertyInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsDatatypeInSignature_withReadLock() {
        ontology.containsDatatypeInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsDatatypeInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsDatatypeInSignature_withReadLock_2() {
        ontology.containsDatatypeInSignature(iri);
        readLock(i -> i.verify(delegate).containsDatatypeInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsIndividualInSignature_withReadLock() {
        ontology.containsIndividualInSignature(iri);
        readLock(i -> i.verify(delegate).containsIndividualInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsIndividualInSignature_withReadLock_2() {
        ontology.containsIndividualInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsIndividualInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getPunnedIRIs_withReadLock() {
        ontology.getPunnedIRIs(INCLUDED);
        readLock(i -> i.verify(delegate).getPunnedIRIs(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_contains_withReadLock() {
        Object arg1 = new Object();
        ontology.contains(searchFilter, arg1, INCLUDED);
        readLock(i -> i.verify(delegate).contains(searchFilter, arg1, INCLUDED));
    }

    @Mock
    OWLAxiomSearchFilter searchFilter;
    @Mock
    OWLObject object;
    @Mock
    OWLAnnotationProperty annotationProperty;
    @Mock
    OWLAnnotationSubject subject;
    @Mock
    OWLClass owlClass;
    @Mock
    OWLDataPropertyExpression dataPropertyExpression;
    @Mock
    OWLClassExpression classExpression;

    @Test
    public void shouldDelegateTo_getOWLOntologyManager_withReadLock() {
        ontology.getOWLOntologyManager();
        readLock(i -> i.verify(delegate).getOWLOntologyManager());
    }

    @Test
    public void shouldDelegateTo_getOWLOntologyManager_withWriteLock() {
        ontology.setOWLOntologyManager(manager);
        writeLock(i -> i.verify(delegate).setOWLOntologyManager(manager));
    }

    @Test
    public void shouldDelegateTo_applyChange_withWriteLock() {
        ontology.applyChange(change);
        writeLock(i -> i.verify(delegate).applyChange(change));
    }

    @Mock
    OWLOntologyChange change;

    @Test
    public void shouldDelegateTo_applyChanges_withWriteLock() {
        ontology.applyChanges(list);
        writeLock(i -> i.verify(delegate).applyChanges(list));
    }

    @Mock
    List<OWLOntologyChange> list;

    @Test
    public void shouldDelegateTo_addAxioms_withWriteLock() {
        ontology.addAxioms(set);
        writeLock(i -> i.verify(delegate).addAxioms(set));
    }

    @Mock
    Set<OWLAxiom> set;

    @Test
    public void shouldDelegateTo_addAxiom_withWriteLock() {
        ontology.addAxiom(axiom);
        writeLock(i -> i.verify(delegate).addAxiom(axiom));
    }
}
