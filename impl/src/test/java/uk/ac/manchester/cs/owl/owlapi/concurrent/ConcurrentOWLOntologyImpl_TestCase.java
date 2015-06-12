package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.mockito.Mockito.*;

import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10/04/15
 */

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 03/04/15
 */
@SuppressWarnings("javadoc")
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
    private ConcurrentOWLOntologyImpl ontology;

    @Before
    public void setUp() {
        when(readWriteLock.readLock()).thenReturn(readLock);
        when(readWriteLock.writeLock()).thenReturn(writeLock);
        ontology = spy(new ConcurrentOWLOntologyImpl(delegate, readWriteLock));
    }

    @Test
    public void shouldDelegateTo_isEmpty_withReadLock() {
        ontology.isEmpty();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).isEmpty();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnnotations_withReadLock() {
        ontology.getAnnotations();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnnotations();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getSignature_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getSignature_withReadLock_2() {
        ontology.getSignature();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getSignature();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_setOWLOntologyManager_withReadLock() {
        OWLOntologyManager arg0 = mock(OWLOntologyManager.class);
        ontology.setOWLOntologyManager(arg0);
        InOrder inOrder = Mockito.inOrder(writeLock, delegate, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).setOWLOntologyManager(arg0);
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getOntologyID_withReadLock() {
        ontology.getOntologyID();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getOntologyID();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_isAnonymous_withReadLock() {
        ontology.isAnonymous();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).isAnonymous();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDirectImportsDocuments_withReadLock() {
        ontology.getDirectImportsDocuments();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDirectImportsDocuments();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDirectImports_withReadLock() {
        ontology.getDirectImports();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDirectImports();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getImports_withReadLock() {
        ontology.getImports();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getImports();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getImportsClosure_withReadLock() {
        ontology.getImportsClosure();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getImportsClosure();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getImportsDeclarations_withReadLock() {
        ontology.getImportsDeclarations();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getImportsDeclarations();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getTBoxAxioms_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getTBoxAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getTBoxAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getABoxAxioms_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getABoxAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getABoxAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getRBoxAxioms_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getRBoxAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getRBoxAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getGeneralClassAxioms_withReadLock() {
        ontology.getGeneralClassAxioms();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getGeneralClassAxioms();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_isDeclared_withReadLock() {
        OWLEntity arg0 = mock(OWLEntity.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.isDeclared(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).isDeclared(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_isDeclared_withReadLock_2() {
        OWLEntity arg0 = mock(OWLEntity.class);
        ontology.isDeclared(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).isDeclared(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock() throws OWLOntologyStorageException {
        OWLDocumentFormat arg0 = mock(OWLDocumentFormat.class);
        IRI arg1 = mock(IRI.class);
        ontology.saveOntology(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_2() throws OWLOntologyStorageException {
        OWLDocumentFormat arg0 = mock(OWLDocumentFormat.class);
        OutputStream arg1 = mock(OutputStream.class);
        ontology.saveOntology(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_3() throws OWLOntologyStorageException {
        OWLOntologyDocumentTarget arg0 = mock(OWLOntologyDocumentTarget.class);
        ontology.saveOntology(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_4() throws OWLOntologyStorageException {
        OWLDocumentFormat arg0 = mock(OWLDocumentFormat.class);
        OWLOntologyDocumentTarget arg1 = mock(OWLOntologyDocumentTarget.class);
        ontology.saveOntology(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_5() throws OWLOntologyStorageException {
        ontology.saveOntology();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_6() throws OWLOntologyStorageException {
        IRI arg0 = mock(IRI.class);
        ontology.saveOntology(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_7() throws OWLOntologyStorageException {
        OutputStream arg0 = mock(OutputStream.class);
        ontology.saveOntology(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_8() throws OWLOntologyStorageException {
        OWLDocumentFormat arg0 = mock(OWLDocumentFormat.class);
        ontology.saveOntology(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).saveOntology(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getNestedClassExpressions_withReadLock() {
        ontology.getNestedClassExpressions();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getNestedClassExpressions();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_isTopEntity_withReadLock() {
        ontology.isTopEntity();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).isTopEntity();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_isBottomEntity_withReadLock() {
        ontology.isBottomEntity();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).isBottomEntity();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock() {
        OWLEntity arg0 = mock(OWLEntity.class);
        ontology.containsEntityInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsEntityInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnonymousIndividuals_withReadLock() {
        ontology.getAnonymousIndividuals();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnonymousIndividuals();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getClassesInSignature_withReadLock() {
        ontology.getClassesInSignature();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getClassesInSignature();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectPropertiesInSignature_withReadLock() {
        ontology.getObjectPropertiesInSignature();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectPropertiesInSignature();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataPropertiesInSignature_withReadLock() {
        ontology.getDataPropertiesInSignature();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataPropertiesInSignature();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getIndividualsInSignature_withReadLock() {
        ontology.getIndividualsInSignature();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getIndividualsInSignature();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDatatypesInSignature_withReadLock() {
        ontology.getDatatypesInSignature();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDatatypesInSignature();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertiesInSignature_withReadLock() {
        ontology.getAnnotationPropertiesInSignature();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnnotationPropertiesInSignature();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock() {
        OWLDatatype arg0 = mock(OWLDatatype.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_2() {
        OWLClass arg0 = mock(OWLClass.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_3() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_4() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_5() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_6() {
        OWLAnnotationProperty arg0 = mock(OWLAnnotationProperty.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_7() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_8() {
        AxiomType arg0 = AxiomType.SUBCLASS_OF;
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock() {
        AxiomType arg0 = AxiomType.SUBCLASS_OF;
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxiomCount(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomCount(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_2() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getAxiomCount(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomCount(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getLogicalAxioms_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getLogicalAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getLogicalAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getLogicalAxiomCount_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getLogicalAxiomCount(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getLogicalAxiomCount(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        Imports arg1 = Imports.INCLUDED;
        AxiomAnnotations arg2 = AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS;
        ontology.containsAxiom(arg0, arg1, arg2);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAxiom(arg0, arg1, arg2);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomsIgnoreAnnotations_withReadLock() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getAxiomsIgnoreAnnotations(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomsIgnoreAnnotations(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getReferencingAxioms_withReadLock() {
        OWLPrimitive arg0 = mock(OWLPrimitive.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getReferencingAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getReferencingAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_9() {
        ontology.getAxioms();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getLogicalAxioms_withReadLock_2() {
        ontology.getLogicalAxioms();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getLogicalAxioms();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_10() {
        AxiomType arg0 = AxiomType.SUBCLASS_OF;
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock_2() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        ontology.containsAxiom(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAxiom(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_11() {
        OWLDatatype arg0 = mock(OWLDatatype.class);
        boolean arg1 = true;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_12() {
        OWLClass arg0 = mock(OWLClass.class);
        boolean arg1 = true;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_13() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        boolean arg1 = true;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_14() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        boolean arg1 = true;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_15() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        boolean arg1 = true;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_16() {
        OWLAnnotationProperty arg0 = mock(OWLAnnotationProperty.class);
        boolean arg1 = true;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_17() {
        AxiomType arg0 = AxiomType.SUBCLASS_OF;
        boolean arg1 = true;
        ontology.getAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_18() {
        boolean arg0 = true;
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_3() {
        boolean arg0 = true;
        ontology.getAxiomCount(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomCount(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_4() {
        AxiomType arg0 = AxiomType.SUBCLASS_OF;
        boolean arg1 = true;
        ontology.getAxiomCount(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomCount(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getLogicalAxioms_withReadLock_3() {
        boolean arg0 = true;
        ontology.getLogicalAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getLogicalAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getLogicalAxiomCount_withReadLock_2() {
        boolean arg0 = true;
        ontology.getLogicalAxiomCount(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getLogicalAxiomCount(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock_3() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        boolean arg1 = true;
        ontology.containsAxiom(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAxiom(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomsIgnoreAnnotations_withReadLock_2() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        boolean arg1 = true;
        ontology.getAxiomsIgnoreAnnotations(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomsIgnoreAnnotations(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getReferencingAxioms_withReadLock_2() {
        OWLPrimitive arg0 = mock(OWLPrimitive.class);
        boolean arg1 = true;
        ontology.getReferencingAxioms(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getReferencingAxioms(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAxiomIgnoreAnnotations_withReadLock() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        boolean arg1 = true;
        ontology.containsAxiomIgnoreAnnotations(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAxiomIgnoreAnnotations(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_19() {
        OWLDatatype arg0 = mock(OWLDatatype.class);
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_20() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_21() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_22() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_23() {
        OWLAnnotationProperty arg0 = mock(OWLAnnotationProperty.class);
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_24() {
        OWLClass arg0 = mock(OWLClass.class);
        ontology.getAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_5() {
        ontology.getAxiomCount();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomCount();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_6() {
        AxiomType arg0 = AxiomType.SUBCLASS_OF;
        ontology.getAxiomCount(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomCount(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getLogicalAxiomCount_withReadLock_3() {
        ontology.getLogicalAxiomCount();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getLogicalAxiomCount();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxiomsIgnoreAnnotations_withReadLock_3() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        ontology.getAxiomsIgnoreAnnotations(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxiomsIgnoreAnnotations(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getReferencingAxioms_withReadLock_3() {
        OWLPrimitive arg0 = mock(OWLPrimitive.class);
        ontology.getReferencingAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getReferencingAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAxiomIgnoreAnnotations_withReadLock_2() {
        OWLAxiom arg0 = mock(OWLAxiom.class);
        ontology.containsAxiomIgnoreAnnotations(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAxiomIgnoreAnnotations(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertiesInSignature_withReadLock_2() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getAnnotationPropertiesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnnotationPropertiesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getIndividualsInSignature_withReadLock_2() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getIndividualsInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getIndividualsInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDatatypesInSignature_withReadLock_2() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getDatatypesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDatatypesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getClassesInSignature_withReadLock_2() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getClassesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getClassesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsEntityInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsEntityInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        ontology.containsEntityInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsEntityInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_4() {
        OWLEntity arg0 = mock(OWLEntity.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsEntityInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsEntityInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectPropertiesInSignature_withReadLock_2() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getObjectPropertiesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectPropertiesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataPropertiesInSignature_withReadLock_2() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getDataPropertiesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataPropertiesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getReferencedAnonymousIndividuals_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getReferencedAnonymousIndividuals(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getReferencedAnonymousIndividuals(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock() {
        IRI arg0 = mock(IRI.class);
        ontology.containsClassInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsClassInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsClassInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsClassInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock() {
        IRI arg0 = mock(IRI.class);
        ontology.containsObjectPropertyInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsObjectPropertyInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsObjectPropertyInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsObjectPropertyInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsDataPropertyInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsDataPropertyInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        ontology.containsDataPropertyInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsDataPropertyInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock() {
        IRI arg0 = mock(IRI.class);
        ontology.containsAnnotationPropertyInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAnnotationPropertyInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsAnnotationPropertyInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAnnotationPropertyInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsDatatypeInSignature_withReadLock() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsDatatypeInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsDatatypeInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsDatatypeInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        ontology.containsDatatypeInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsDatatypeInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsIndividualInSignature_withReadLock() {
        IRI arg0 = mock(IRI.class);
        ontology.containsIndividualInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsIndividualInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsIndividualInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsIndividualInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsIndividualInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getEntitiesInSignature_withReadLock() {
        IRI arg0 = mock(IRI.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.getEntitiesInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getEntitiesInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getPunnedIRIs_withReadLock() {
        Imports arg0 = Imports.INCLUDED;
        ontology.getPunnedIRIs(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getPunnedIRIs(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsReference_withReadLock() {
        OWLEntity arg0 = mock(OWLEntity.class);
        Imports arg1 = Imports.INCLUDED;
        ontology.containsReference(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsReference(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsReference_withReadLock_2() {
        OWLEntity arg0 = mock(OWLEntity.class);
        ontology.containsReference(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsReference(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getEntitiesInSignature_withReadLock_2() {
        IRI arg0 = mock(IRI.class);
        ontology.getEntitiesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getEntitiesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertiesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getAnnotationPropertiesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnnotationPropertiesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getIndividualsInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getIndividualsInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getIndividualsInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDatatypesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getDatatypesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDatatypesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getClassesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getClassesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getClassesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_5() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.containsEntityInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsEntityInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_6() {
        OWLEntity arg0 = mock(OWLEntity.class);
        boolean arg1 = true;
        ontology.containsEntityInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsEntityInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectPropertiesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getObjectPropertiesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectPropertiesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataPropertiesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getDataPropertiesInSignature(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataPropertiesInSignature(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getReferencedAnonymousIndividuals_withReadLock_2() {
        boolean arg0 = true;
        ontology.getReferencedAnonymousIndividuals(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getReferencedAnonymousIndividuals(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.containsClassInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsClassInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.containsObjectPropertyInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsObjectPropertyInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.containsDataPropertyInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsDataPropertyInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.containsAnnotationPropertyInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsAnnotationPropertyInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsDatatypeInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.containsDatatypeInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsDatatypeInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsIndividualInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.containsIndividualInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsIndividualInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getEntitiesInSignature_withReadLock_3() {
        IRI arg0 = mock(IRI.class);
        boolean arg1 = true;
        ontology.getEntitiesInSignature(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getEntitiesInSignature(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_containsReference_withReadLock_3() {
        OWLEntity arg0 = mock(OWLEntity.class);
        boolean arg1 = true;
        ontology.containsReference(arg0, arg1);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).containsReference(arg0, arg1);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_contains_withReadLock() {
        OWLAxiomSearchFilter arg0 = mock(OWLAxiomSearchFilter.class);
        Object arg1 = mock(Object.class);
        Imports arg2 = Imports.INCLUDED;
        ontology.contains(arg0, arg1, arg2);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).contains(arg0, arg1, arg2);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_25() {
        Class arg0 = OWLClass.class;
        Class arg1 = OWLClass.class;
        OWLObject arg2 = mock(OWLObject.class);
        Imports arg3 = Imports.INCLUDED;
        Navigation arg4 = Navigation.IN_SUB_POSITION;
        ontology.getAxioms(arg0, arg1, arg2, arg3, arg4);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1, arg2, arg3, arg4);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_26() {
        Class arg0 = OWLClass.class;
        OWLObject arg1 = mock(OWLObject.class);
        Imports arg2 = Imports.INCLUDED;
        Navigation arg3 = Navigation.IN_SUB_POSITION;
        ontology.getAxioms(arg0, arg1, arg2, arg3);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAxioms(arg0, arg1, arg2, arg3);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_filterAxioms_withReadLock() {
        OWLAxiomSearchFilter arg0 = mock(OWLAxiomSearchFilter.class);
        Object arg1 = mock(Object.class);
        Imports arg2 = Imports.INCLUDED;
        ontology.filterAxioms(arg0, arg1, arg2);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).filterAxioms(arg0, arg1, arg2);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getSubAnnotationPropertyOfAxioms_withReadLock() {
        OWLAnnotationProperty arg0 = mock(OWLAnnotationProperty.class);
        ontology.getSubAnnotationPropertyOfAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getSubAnnotationPropertyOfAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertyDomainAxioms_withReadLock() {
        OWLAnnotationProperty arg0 = mock(OWLAnnotationProperty.class);
        ontology.getAnnotationPropertyDomainAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnnotationPropertyDomainAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertyRangeAxioms_withReadLock() {
        OWLAnnotationProperty arg0 = mock(OWLAnnotationProperty.class);
        ontology.getAnnotationPropertyRangeAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnnotationPropertyRangeAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDeclarationAxioms_withReadLock() {
        OWLEntity arg0 = mock(OWLEntity.class);
        ontology.getDeclarationAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDeclarationAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAnnotationAssertionAxioms_withReadLock() {
        OWLAnnotationSubject arg0 = mock(OWLAnnotationSubject.class);
        ontology.getAnnotationAssertionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAnnotationAssertionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getSubClassAxiomsForSubClass_withReadLock() {
        OWLClass arg0 = mock(OWLClass.class);
        ontology.getSubClassAxiomsForSubClass(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getSubClassAxiomsForSubClass(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getSubClassAxiomsForSuperClass_withReadLock() {
        OWLClass arg0 = mock(OWLClass.class);
        ontology.getSubClassAxiomsForSuperClass(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getSubClassAxiomsForSuperClass(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getEquivalentClassesAxioms_withReadLock() {
        OWLClass arg0 = mock(OWLClass.class);
        ontology.getEquivalentClassesAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getEquivalentClassesAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDisjointClassesAxioms_withReadLock() {
        OWLClass arg0 = mock(OWLClass.class);
        ontology.getDisjointClassesAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDisjointClassesAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDisjointUnionAxioms_withReadLock() {
        OWLClass arg0 = mock(OWLClass.class);
        ontology.getDisjointUnionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDisjointUnionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getHasKeyAxioms_withReadLock() {
        OWLClass arg0 = mock(OWLClass.class);
        ontology.getHasKeyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getHasKeyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectSubPropertyAxiomsForSubProperty_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getObjectSubPropertyAxiomsForSubProperty(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectSubPropertyAxiomsForSubProperty(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectSubPropertyAxiomsForSuperProperty_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getObjectSubPropertyAxiomsForSuperProperty(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectSubPropertyAxiomsForSuperProperty(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectPropertyDomainAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getObjectPropertyDomainAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectPropertyDomainAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectPropertyRangeAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getObjectPropertyRangeAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectPropertyRangeAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getInverseObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getInverseObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getInverseObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getEquivalentObjectPropertiesAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getEquivalentObjectPropertiesAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getEquivalentObjectPropertiesAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDisjointObjectPropertiesAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getDisjointObjectPropertiesAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDisjointObjectPropertiesAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getFunctionalObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getFunctionalObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getFunctionalObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getInverseFunctionalObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getInverseFunctionalObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getInverseFunctionalObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getSymmetricObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getSymmetricObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getSymmetricObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getAsymmetricObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getAsymmetricObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getAsymmetricObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getReflexiveObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getReflexiveObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getReflexiveObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getIrreflexiveObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getIrreflexiveObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getIrreflexiveObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getTransitiveObjectPropertyAxioms_withReadLock() {
        OWLObjectPropertyExpression arg0 = mock(OWLObjectPropertyExpression.class);
        ontology.getTransitiveObjectPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getTransitiveObjectPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataSubPropertyAxiomsForSubProperty_withReadLock() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        ontology.getDataSubPropertyAxiomsForSubProperty(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataSubPropertyAxiomsForSubProperty(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataSubPropertyAxiomsForSuperProperty_withReadLock() {
        OWLDataPropertyExpression arg0 = mock(OWLDataPropertyExpression.class);
        ontology.getDataSubPropertyAxiomsForSuperProperty(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataSubPropertyAxiomsForSuperProperty(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataPropertyDomainAxioms_withReadLock() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        ontology.getDataPropertyDomainAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataPropertyDomainAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataPropertyRangeAxioms_withReadLock() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        ontology.getDataPropertyRangeAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataPropertyRangeAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getEquivalentDataPropertiesAxioms_withReadLock() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        ontology.getEquivalentDataPropertiesAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getEquivalentDataPropertiesAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDisjointDataPropertiesAxioms_withReadLock() {
        OWLDataProperty arg0 = mock(OWLDataProperty.class);
        ontology.getDisjointDataPropertiesAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDisjointDataPropertiesAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getFunctionalDataPropertyAxioms_withReadLock() {
        OWLDataPropertyExpression arg0 = mock(OWLDataPropertyExpression.class);
        ontology.getFunctionalDataPropertyAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getFunctionalDataPropertyAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getClassAssertionAxioms_withReadLock() {
        OWLClassExpression arg0 = mock(OWLClassExpression.class);
        ontology.getClassAssertionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getClassAssertionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getClassAssertionAxioms_withReadLock_2() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getClassAssertionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getClassAssertionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDataPropertyAssertionAxioms_withReadLock() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getDataPropertyAssertionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDataPropertyAssertionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getObjectPropertyAssertionAxioms_withReadLock() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getObjectPropertyAssertionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getObjectPropertyAssertionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getNegativeObjectPropertyAssertionAxioms_withReadLock() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getNegativeObjectPropertyAssertionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getNegativeObjectPropertyAssertionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getNegativeDataPropertyAssertionAxioms_withReadLock() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getNegativeDataPropertyAssertionAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getNegativeDataPropertyAssertionAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getSameIndividualAxioms_withReadLock() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getSameIndividualAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getSameIndividualAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDifferentIndividualAxioms_withReadLock() {
        OWLIndividual arg0 = mock(OWLIndividual.class);
        ontology.getDifferentIndividualAxioms(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDifferentIndividualAxioms(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getDatatypeDefinitions_withReadLock() {
        OWLDatatype arg0 = mock(OWLDatatype.class);
        ontology.getDatatypeDefinitions(arg0);
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getDatatypeDefinitions(arg0);
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getOWLOntologyManager_withReadLock() {
        ontology.getOWLOntologyManager();
        InOrder inOrder = Mockito.inOrder(readLock, delegate, readLock);
        inOrder.verify(readLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).getOWLOntologyManager();
        inOrder.verify(readLock, times(1)).unlock();
        verify(writeLock, never()).lock();
        verify(writeLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_getOWLOntologyManager_withWriteLock() {
        OWLOntologyManager arg = mock(OWLOntologyManager.class);
        ontology.setOWLOntologyManager(arg);
        InOrder inOrder = Mockito.inOrder(writeLock, delegate, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).setOWLOntologyManager(arg);
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_applyChange_withWriteLock() {
        OWLOntologyChange arg = mock(OWLOntologyChange.class);
        ontology.applyChange(arg);
        InOrder inOrder = Mockito.inOrder(writeLock, delegate, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).applyChange(arg);
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_applyChanges_withWriteLock() {
        List<OWLOntologyChange> arg = Mockito.mock(List.class);
        ontology.applyChanges(arg);
        InOrder inOrder = Mockito.inOrder(writeLock, delegate, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).applyChanges(arg);
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_addAxioms_withWriteLock() {
        Set<OWLAxiom> arg = Mockito.mock(Set.class);
        ontology.addAxioms(arg);
        InOrder inOrder = Mockito.inOrder(writeLock, delegate, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).addAxioms(arg);
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }

    @Test
    public void shouldDelegateTo_addAxiom_withWriteLock() {
        OWLAxiom arg = Mockito.mock(OWLAxiom.class);
        ontology.addAxiom(arg);
        InOrder inOrder = Mockito.inOrder(writeLock, delegate, writeLock);
        inOrder.verify(writeLock, times(1)).lock();
        inOrder.verify(delegate, times(1)).addAxiom(arg);
        inOrder.verify(writeLock, times(1)).unlock();
        verify(readLock, never()).lock();
        verify(readLock, never()).unlock();
    }
}
