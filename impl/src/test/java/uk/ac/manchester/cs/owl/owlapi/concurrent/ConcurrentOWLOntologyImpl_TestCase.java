package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.mockito.Mockito.*;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nonnull;

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
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 03/04/15
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "javadoc", "null", "deprecation" })
public class ConcurrentOWLOntologyImpl_TestCase {

    @Mock
    private ReentrantReadWriteLock readWriteLock;
    @Mock
    private ReentrantReadWriteLock.ReadLock readLock;
    @Mock
    private ReentrantReadWriteLock.WriteLock writeLock;
    @Mock
    private OWLMutableOntology delegate;
    @Nonnull
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
    public void shouldDelegateTo_getAnnotations_withReadLock() {
        ontology.getAnnotations();
        readLock(i -> i.verify(delegate).getAnnotations());
    }

    @Test
    public void shouldDelegateTo_getSignature_withReadLock() {
        ontology.getSignature(INCLUDED);
        readLock(i -> i.verify(delegate).getSignature(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getSignature_withReadLock_2() {
        ontology.getSignature();
        readLock(i -> i.verify(delegate).getSignature());
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

    @Test
    public void shouldDelegateTo_getDirectImportsDocuments_withReadLock() {
        ontology.getDirectImportsDocuments();
        readLock(i -> i.verify(delegate).getDirectImportsDocuments());
    }

    @Test
    public void shouldDelegateTo_getDirectImports_withReadLock() {
        ontology.getDirectImports();
        readLock(i -> i.verify(delegate).getDirectImports());
    }

    @Test
    public void shouldDelegateTo_getImports_withReadLock() {
        ontology.getImports();
        readLock(i -> i.verify(delegate).getImports());
    }

    @Test
    public void shouldDelegateTo_getImportsClosure_withReadLock() {
        ontology.getImportsClosure();
        readLock(i -> i.verify(delegate).getImportsClosure());
    }

    @Test
    public void shouldDelegateTo_getImportsDeclarations_withReadLock() {
        ontology.getImportsDeclarations();
        readLock(i -> i.verify(delegate).getImportsDeclarations());
    }

    @Test
    public void shouldDelegateTo_getTBoxAxioms_withReadLock() {
        ontology.getTBoxAxioms(INCLUDED);
        readLock(i -> i.verify(delegate).getTBoxAxioms(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getABoxAxioms_withReadLock() {
        ontology.getABoxAxioms(INCLUDED);
        readLock(i -> i.verify(delegate).getABoxAxioms(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getRBoxAxioms_withReadLock() {
        ontology.getRBoxAxioms(INCLUDED);
        readLock(i -> i.verify(delegate).getRBoxAxioms(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getGeneralClassAxioms_withReadLock() {
        ontology.getGeneralClassAxioms();
        readLock(i -> i.verify(delegate).getGeneralClassAxioms());
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
    public void shouldDelegateTo_saveOntology_withReadLock()
        throws OWLOntologyStorageException {
        ontology.saveOntology(format, iri);
        readLock(i -> i.verify(delegate).saveOntology(format, iri));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_2()
        throws OWLOntologyStorageException {
        ontology.saveOntology(format, outputStream);
        readLock(i -> i.verify(delegate).saveOntology(format, outputStream));
    }

    @Mock
    OutputStream outputStream;
    @Mock
    OWLDocumentFormat format;

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_3()
        throws OWLOntologyStorageException {
        ontology.saveOntology(target);
        readLock(i -> i.verify(delegate).saveOntology(target));
    }

    @Mock
    OWLOntologyDocumentTarget target;

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_4()
        throws OWLOntologyStorageException {
        ontology.saveOntology(format, target);
        readLock(i -> i.verify(delegate).saveOntology(format, target));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_5()
        throws OWLOntologyStorageException {
        ontology.saveOntology();
        readLock(i -> i.verify(delegate).saveOntology());
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_6()
        throws OWLOntologyStorageException {
        ontology.saveOntology(iri);
        readLock(i -> i.verify(delegate).saveOntology(iri));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_7()
        throws OWLOntologyStorageException {
        ontology.saveOntology(outputStream);
        readLock(i -> i.verify(delegate).saveOntology(outputStream));
    }

    @Test
    public void shouldDelegateTo_saveOntology_withReadLock_8()
        throws OWLOntologyStorageException {
        ontology.saveOntology(format);
        readLock(i -> i.verify(delegate).saveOntology(format));
    }

    @Test
    public void shouldDelegateTo_getNestedClassExpressions_withReadLock() {
        ontology.getNestedClassExpressions();
        readLock(i -> i.verify(delegate).getNestedClassExpressions());
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
    public void shouldDelegateTo_getAnonymousIndividuals_withReadLock() {
        ontology.getAnonymousIndividuals();
        readLock(i -> i.verify(delegate).getAnonymousIndividuals());
    }

    @Test
    public void shouldDelegateTo_getClassesInSignature_withReadLock() {
        ontology.getClassesInSignature();
        readLock(i -> i.verify(delegate).getClassesInSignature());
    }

    @Test
    public void shouldDelegateTo_getObjectPropertiesInSignature_withReadLock() {
        ontology.getObjectPropertiesInSignature();
        readLock(i -> i.verify(delegate).getObjectPropertiesInSignature());
    }

    @Test
    public void shouldDelegateTo_getDataPropertiesInSignature_withReadLock() {
        ontology.getDataPropertiesInSignature();
        readLock(i -> i.verify(delegate).getDataPropertiesInSignature());
    }

    @Test
    public void shouldDelegateTo_getIndividualsInSignature_withReadLock() {
        ontology.getIndividualsInSignature();
        readLock(i -> i.verify(delegate).getIndividualsInSignature());
    }

    @Test
    public void shouldDelegateTo_getDatatypesInSignature_withReadLock() {
        ontology.getDatatypesInSignature();
        readLock(i -> i.verify(delegate).getDatatypesInSignature());
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertiesInSignature_withReadLock() {
        ontology.getAnnotationPropertiesInSignature();
        readLock(i -> i.verify(delegate).getAnnotationPropertiesInSignature());
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock() {
        ontology.getAxioms(datatype, INCLUDED);
        readLock(i -> i.verify(delegate).getAxioms(datatype, INCLUDED));
    }

    @Mock
    OWLDatatype datatype;

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_2() {
        ontology.getAxioms(owlClass, INCLUDED);
        readLock(i -> i.verify(delegate).getAxioms(owlClass, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_3() {
        ontology.getAxioms(objectProperty, INCLUDED);
        readLock(i -> i.verify(delegate).getAxioms(objectProperty, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_4() {
        ontology.getAxioms(dataProperty, INCLUDED);
        readLock(i -> i.verify(delegate).getAxioms(dataProperty, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_5() {
        ontology.getAxioms(individual, INCLUDED);
        readLock(i -> i.verify(delegate).getAxioms(individual, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_6() {
        ontology.getAxioms(annotationProperty, INCLUDED);
        readLock(
            i -> i.verify(delegate).getAxioms(annotationProperty, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_7() {
        ontology.getAxioms(INCLUDED);
        readLock(i -> i.verify(delegate).getAxioms(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_8() {
        ontology.getAxioms(AxiomType.SUBCLASS_OF, INCLUDED);
        readLock(
            i -> i.verify(delegate).getAxioms(AxiomType.SUBCLASS_OF, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock() {
        ontology.getAxiomCount(AxiomType.SUBCLASS_OF, INCLUDED);
        readLock(i -> i.verify(delegate).getAxiomCount(AxiomType.SUBCLASS_OF,
            INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_2() {
        ontology.getAxiomCount(INCLUDED);
        readLock(i -> i.verify(delegate).getAxiomCount(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getLogicalAxioms_withReadLock() {
        ontology.getLogicalAxioms(INCLUDED);
        readLock(i -> i.verify(delegate).getLogicalAxioms(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getLogicalAxiomCount_withReadLock() {
        ontology.getLogicalAxiomCount(INCLUDED);
        readLock(i -> i.verify(delegate).getLogicalAxiomCount(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock() {
        ontology.containsAxiom(axiom, INCLUDED,
            AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS);
        readLock(i -> i.verify(delegate).containsAxiom(axiom, INCLUDED,
            AxiomAnnotations.IGNORE_AXIOM_ANNOTATIONS));
    }

    @Mock
    OWLAxiom axiom;

    @Test
    public void shouldDelegateTo_getAxiomsIgnoreAnnotations_withReadLock() {
        ontology.getAxiomsIgnoreAnnotations(axiom, INCLUDED);
        readLock(i -> i.verify(delegate).getAxiomsIgnoreAnnotations(axiom,
            INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getReferencingAxioms_withReadLock() {
        ontology.getReferencingAxioms(primitive, INCLUDED);
        readLock(
            i -> i.verify(delegate).getReferencingAxioms(primitive, INCLUDED));
    }

    @Mock
    OWLPrimitive primitive;

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_9() {
        ontology.getAxioms();
        readLock(i -> i.verify(delegate).getAxioms());
    }

    @Test
    public void shouldDelegateTo_getLogicalAxioms_withReadLock_2() {
        ontology.getLogicalAxioms();
        readLock(i -> i.verify(delegate).getLogicalAxioms());
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_10() {
        ontology.getAxioms(AxiomType.SUBCLASS_OF);
        readLock(i -> i.verify(delegate).getAxioms(AxiomType.SUBCLASS_OF));
    }

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock_2() {
        ontology.containsAxiom(axiom);
        readLock(i -> i.verify(delegate).containsAxiom(axiom));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_11() {
        ontology.getAxioms(datatype, true);
        readLock(i -> i.verify(delegate).getAxioms(datatype, true));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_12() {
        ontology.getAxioms(owlClass, true);
        readLock(i -> i.verify(delegate).getAxioms(owlClass, true));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_13() {
        ontology.getAxioms(objectProperty, true);
        readLock(i -> i.verify(delegate).getAxioms(objectProperty, true));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_14() {
        ontology.getAxioms(dataProperty, true);
        readLock(i -> i.verify(delegate).getAxioms(dataProperty, true));
    }

    @Mock
    OWLDataProperty dataProperty;

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_15() {
        ontology.getAxioms(individual, true);
        readLock(i -> i.verify(delegate).getAxioms(individual, true));
    }

    @Mock
    OWLIndividual individual;

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_16() {
        ontology.getAxioms(annotationProperty, true);
        readLock(i -> i.verify(delegate).getAxioms(annotationProperty, true));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_17() {
        ontology.getAxioms(AxiomType.SUBCLASS_OF, true);
        readLock(
            i -> i.verify(delegate).getAxioms(AxiomType.SUBCLASS_OF, true));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_18() {
        ontology.getAxioms(true);
        readLock(i -> i.verify(delegate).getAxioms(true));
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_3() {
        ontology.getAxiomCount(true);
        readLock(i -> i.verify(delegate).getAxiomCount(true));
    }

    @Test
    public void shouldDelegateTo_getAxiomCount_withReadLock_4() {
        boolean arg1 = true;
        ontology.getAxiomCount(AxiomType.SUBCLASS_OF, arg1);
        readLock(
            i -> i.verify(delegate).getAxiomCount(AxiomType.SUBCLASS_OF, arg1));
    }

    @Test
    public void shouldDelegateTo_getLogicalAxioms_withReadLock_3() {
        ontology.getLogicalAxioms(true);
        readLock(i -> i.verify(delegate).getLogicalAxioms(true));
    }

    @Test
    public void shouldDelegateTo_getLogicalAxiomCount_withReadLock_2() {
        ontology.getLogicalAxiomCount(true);
        readLock(i -> i.verify(delegate).getLogicalAxiomCount(true));
    }

    @Test
    public void shouldDelegateTo_containsAxiom_withReadLock_3() {
        ontology.containsAxiom(axiom, true);
        readLock(i -> i.verify(delegate).containsAxiom(axiom, true));
    }

    @Test
    public void shouldDelegateTo_getAxiomsIgnoreAnnotations_withReadLock_2() {
        ontology.getAxiomsIgnoreAnnotations(axiom, true);
        readLock(
            i -> i.verify(delegate).getAxiomsIgnoreAnnotations(axiom, true));
    }

    @Test
    public void shouldDelegateTo_getReferencingAxioms_withReadLock_2() {
        ontology.getReferencingAxioms(primitive, true);
        readLock(i -> i.verify(delegate).getReferencingAxioms(primitive, true));
    }

    @Test
    public void shouldDelegateTo_containsAxiomIgnoreAnnotations_withReadLock() {
        ontology.containsAxiomIgnoreAnnotations(axiom, true);
        readLock(i -> i.verify(delegate).containsAxiomIgnoreAnnotations(axiom,
            true));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_19() {
        ontology.getAxioms(datatype);
        readLock(i -> i.verify(delegate).getAxioms(datatype));
    }

    @Mock
    OWLObjectPropertyExpression objectProperty;

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_20() {
        ontology.getAxioms(objectProperty);
        readLock(i -> i.verify(delegate).getAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_21() {
        ontology.getAxioms(dataProperty);
        readLock(i -> i.verify(delegate).getAxioms(dataProperty));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_22() {
        ontology.getAxioms(individual);
        readLock(i -> i.verify(delegate).getAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_23() {
        ontology.getAxioms(annotationProperty);
        readLock(i -> i.verify(delegate).getAxioms(annotationProperty));
    }

    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_24() {
        ontology.getAxioms(owlClass);
        readLock(i -> i.verify(delegate).getAxioms(owlClass));
    }

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
    public void shouldDelegateTo_getAxiomsIgnoreAnnotations_withReadLock_3() {
        ontology.getAxiomsIgnoreAnnotations(axiom);
        readLock(i -> i.verify(delegate).getAxiomsIgnoreAnnotations(axiom));
    }

    @Test
    public void shouldDelegateTo_getReferencingAxioms_withReadLock_3() {
        ontology.getReferencingAxioms(primitive);
        readLock(i -> i.verify(delegate).getReferencingAxioms(primitive));
    }

    @Test
    public void shouldDelegateTo_containsAxiomIgnoreAnnotations_withReadLock_2() {
        ontology.containsAxiomIgnoreAnnotations(axiom);
        readLock(i -> i.verify(delegate).containsAxiomIgnoreAnnotations(axiom));
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertiesInSignature_withReadLock_2() {
        ontology.getAnnotationPropertiesInSignature(INCLUDED);
        readLock(i -> i.verify(delegate)
            .getAnnotationPropertiesInSignature(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getIndividualsInSignature_withReadLock_2() {
        ontology.getIndividualsInSignature(INCLUDED);
        readLock(i -> i.verify(delegate).getIndividualsInSignature(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getDatatypesInSignature_withReadLock_2() {
        ontology.getDatatypesInSignature(INCLUDED);
        readLock(i -> i.verify(delegate).getDatatypesInSignature(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getClassesInSignature_withReadLock_2() {
        ontology.getClassesInSignature(INCLUDED);
        readLock(i -> i.verify(delegate).getClassesInSignature(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_2() {
        ontology.containsEntityInSignature(iri, INCLUDED);
        readLock(
            i -> i.verify(delegate).containsEntityInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_3() {
        ontology.containsEntityInSignature(iri);
        readLock(i -> i.verify(delegate).containsEntityInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_4() {
        ontology.containsEntityInSignature(entity, INCLUDED);
        readLock(i -> i.verify(delegate).containsEntityInSignature(entity,
            INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getObjectPropertiesInSignature_withReadLock_2() {
        ontology.getObjectPropertiesInSignature(INCLUDED);
        readLock(
            i -> i.verify(delegate).getObjectPropertiesInSignature(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getDataPropertiesInSignature_withReadLock_2() {
        ontology.getDataPropertiesInSignature(INCLUDED);
        readLock(
            i -> i.verify(delegate).getDataPropertiesInSignature(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getReferencedAnonymousIndividuals_withReadLock() {
        ontology.getReferencedAnonymousIndividuals(INCLUDED);
        readLock(i -> i.verify(delegate)
            .getReferencedAnonymousIndividuals(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock() {
        ontology.containsClassInSignature(iri);
        readLock(i -> i.verify(delegate).containsClassInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock_2() {
        ontology.containsClassInSignature(iri, INCLUDED);
        readLock(
            i -> i.verify(delegate).containsClassInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock() {
        ontology.containsObjectPropertyInSignature(iri);
        readLock(
            i -> i.verify(delegate).containsObjectPropertyInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock_2() {
        ontology.containsObjectPropertyInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsObjectPropertyInSignature(iri,
            INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock() {
        ontology.containsDataPropertyInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).containsDataPropertyInSignature(iri,
            INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock_2() {
        ontology.containsDataPropertyInSignature(iri);
        readLock(i -> i.verify(delegate).containsDataPropertyInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock() {
        ontology.containsAnnotationPropertyInSignature(iri);
        readLock(
            i -> i.verify(delegate).containsAnnotationPropertyInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock_2() {
        ontology.containsAnnotationPropertyInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate)
            .containsAnnotationPropertyInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsDatatypeInSignature_withReadLock() {
        ontology.containsDatatypeInSignature(iri, INCLUDED);
        readLock(
            i -> i.verify(delegate).containsDatatypeInSignature(iri, INCLUDED));
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
        readLock(i -> i.verify(delegate).containsIndividualInSignature(iri,
            INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getEntitiesInSignature_withReadLock() {
        ontology.getEntitiesInSignature(iri, INCLUDED);
        readLock(i -> i.verify(delegate).getEntitiesInSignature(iri, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getPunnedIRIs_withReadLock() {
        ontology.getPunnedIRIs(INCLUDED);
        readLock(i -> i.verify(delegate).getPunnedIRIs(INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsReference_withReadLock() {
        ontology.containsReference(entity, INCLUDED);
        readLock(i -> i.verify(delegate).containsReference(entity, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_containsReference_withReadLock_2() {
        ontology.containsReference(entity);
        readLock(i -> i.verify(delegate).containsReference(entity));
    }

    @Test
    public void shouldDelegateTo_getEntitiesInSignature_withReadLock_2() {
        ontology.getEntitiesInSignature(iri);
        readLock(i -> i.verify(delegate).getEntitiesInSignature(iri));
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertiesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getAnnotationPropertiesInSignature(arg0);
        readLock(
            i -> i.verify(delegate).getAnnotationPropertiesInSignature(arg0));
    }

    @Test
    public void shouldDelegateTo_getIndividualsInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getIndividualsInSignature(arg0);
        readLock(i -> i.verify(delegate).getIndividualsInSignature(arg0));
    }

    @Test
    public void shouldDelegateTo_getDatatypesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getDatatypesInSignature(arg0);
        readLock(i -> i.verify(delegate).getDatatypesInSignature(arg0));
    }

    @Test
    public void shouldDelegateTo_getClassesInSignature_withReadLock_3() {
        boolean arg0 = true;
        ontology.getClassesInSignature(arg0);
        readLock(i -> i.verify(delegate).getClassesInSignature(arg0));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_5() {
        boolean arg1 = true;
        ontology.containsEntityInSignature(iri, arg1);
        readLock(i -> i.verify(delegate).containsEntityInSignature(iri, arg1));
    }

    @Test
    public void shouldDelegateTo_containsEntityInSignature_withReadLock_6() {
        ontology.containsEntityInSignature(entity, true);
        readLock(
            i -> i.verify(delegate).containsEntityInSignature(entity, true));
    }

    @Test
    public void shouldDelegateTo_getObjectPropertiesInSignature_withReadLock_3() {
        ontology.getObjectPropertiesInSignature(true);
        readLock(i -> i.verify(delegate).getObjectPropertiesInSignature(true));
    }

    @Test
    public void shouldDelegateTo_getDataPropertiesInSignature_withReadLock_3() {
        ontology.getDataPropertiesInSignature(true);
        readLock(i -> i.verify(delegate).getDataPropertiesInSignature(true));
    }

    @Test
    public void shouldDelegateTo_getReferencedAnonymousIndividuals_withReadLock_2() {
        ontology.getReferencedAnonymousIndividuals(true);
        readLock(
            i -> i.verify(delegate).getReferencedAnonymousIndividuals(true));
    }

    @Test
    public void shouldDelegateTo_containsClassInSignature_withReadLock_3() {
        ontology.containsClassInSignature(iri, true);
        readLock(i -> i.verify(delegate).containsClassInSignature(iri, true));
    }

    @Test
    public void shouldDelegateTo_containsObjectPropertyInSignature_withReadLock_3() {
        boolean arg1 = true;
        ontology.containsObjectPropertyInSignature(iri, arg1);
        readLock(i -> i.verify(delegate).containsObjectPropertyInSignature(iri,
            arg1));
    }

    @Test
    public void shouldDelegateTo_containsDataPropertyInSignature_withReadLock_3() {
        boolean arg1 = true;
        ontology.containsDataPropertyInSignature(iri, arg1);
        readLock(
            i -> i.verify(delegate).containsDataPropertyInSignature(iri, arg1));
    }

    @Test
    public void shouldDelegateTo_containsAnnotationPropertyInSignature_withReadLock_3() {
        boolean arg1 = true;
        ontology.containsAnnotationPropertyInSignature(iri, arg1);
        readLock(i -> i.verify(delegate)
            .containsAnnotationPropertyInSignature(iri, arg1));
    }

    @Test
    public void shouldDelegateTo_containsDatatypeInSignature_withReadLock_3() {
        boolean arg1 = true;
        ontology.containsDatatypeInSignature(iri, arg1);
        readLock(
            i -> i.verify(delegate).containsDatatypeInSignature(iri, arg1));
    }

    @Test
    public void shouldDelegateTo_containsIndividualInSignature_withReadLock_3() {
        boolean arg1 = true;
        ontology.containsIndividualInSignature(iri, arg1);
        readLock(
            i -> i.verify(delegate).containsIndividualInSignature(iri, arg1));
    }

    @Test
    public void shouldDelegateTo_getEntitiesInSignature_withReadLock_3() {
        boolean arg1 = true;
        ontology.getEntitiesInSignature(iri, arg1);
        readLock(i -> i.verify(delegate).getEntitiesInSignature(iri, arg1));
    }

    @Test
    public void shouldDelegateTo_containsReference_withReadLock_3() {
        ontology.containsReference(entity, true);
        readLock(i -> i.verify(delegate).containsReference(entity, true));
    }

    @Test
    public void shouldDelegateTo_contains_withReadLock() {
        Object arg1 = new Object();
        ontology.contains(searchFilter, arg1, INCLUDED);
        readLock(
            i -> i.verify(delegate).contains(searchFilter, arg1, INCLUDED));
    }

    @Mock
    OWLAxiomSearchFilter searchFilter;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_25() {
        Class arg0 = OWLClass.class;
        Class arg1 = OWLClass.class;
        Navigation arg4 = Navigation.IN_SUB_POSITION;
        ontology.getAxioms(arg0, arg1, object, INCLUDED, arg4);
        readLock(i -> i.verify(delegate).getAxioms(arg0, arg1, object, INCLUDED,
            arg4));
    }

    @Mock
    OWLObject object;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void shouldDelegateTo_getAxioms_withReadLock_26() {
        Class arg0 = OWLClass.class;
        Navigation arg3 = Navigation.IN_SUB_POSITION;
        ontology.getAxioms(arg0, object, INCLUDED, arg3);
        readLock(
            i -> i.verify(delegate).getAxioms(arg0, object, INCLUDED, arg3));
    }

    @Test
    public void shouldDelegateTo_filterAxioms_withReadLock() {
        Object arg1 = new Object();
        ontology.filterAxioms(searchFilter, arg1, INCLUDED);
        readLock(
            i -> i.verify(delegate).filterAxioms(searchFilter, arg1, INCLUDED));
    }

    @Test
    public void shouldDelegateTo_getSubAnnotationPropertyOfAxioms_withReadLock() {
        ontology.getSubAnnotationPropertyOfAxioms(annotationProperty);
        readLock(i -> i.verify(delegate)
            .getSubAnnotationPropertyOfAxioms(annotationProperty));
    }

    @Mock
    OWLAnnotationProperty annotationProperty;

    @Test
    public void shouldDelegateTo_getAnnotationPropertyDomainAxioms_withReadLock() {
        ontology.getAnnotationPropertyDomainAxioms(annotationProperty);
        readLock(i -> i.verify(delegate)
            .getAnnotationPropertyDomainAxioms(annotationProperty));
    }

    @Test
    public void shouldDelegateTo_getAnnotationPropertyRangeAxioms_withReadLock() {
        ontology.getAnnotationPropertyRangeAxioms(annotationProperty);
        readLock(i -> i.verify(delegate)
            .getAnnotationPropertyRangeAxioms(annotationProperty));
    }

    @Test
    public void shouldDelegateTo_getDeclarationAxioms_withReadLock() {
        ontology.getDeclarationAxioms(entity);
        readLock(i -> i.verify(delegate).getDeclarationAxioms(entity));
    }

    @Test
    public void shouldDelegateTo_getAnnotationAssertionAxioms_withReadLock() {
        ontology.getAnnotationAssertionAxioms(subject);
        readLock(i -> i.verify(delegate).getAnnotationAssertionAxioms(subject));
    }

    @Mock
    OWLAnnotationSubject subject;

    @Test
    public void shouldDelegateTo_getSubClassAxiomsForSubClass_withReadLock() {
        ontology.getSubClassAxiomsForSubClass(owlClass);
        readLock(
            i -> i.verify(delegate).getSubClassAxiomsForSubClass(owlClass));
    }

    @Test
    public void shouldDelegateTo_getSubClassAxiomsForSuperClass_withReadLock() {
        ontology.getSubClassAxiomsForSuperClass(owlClass);
        readLock(
            i -> i.verify(delegate).getSubClassAxiomsForSuperClass(owlClass));
    }

    @Test
    public void shouldDelegateTo_getEquivalentClassesAxioms_withReadLock() {
        ontology.getEquivalentClassesAxioms(owlClass);
        readLock(i -> i.verify(delegate).getEquivalentClassesAxioms(owlClass));
    }

    @Mock
    OWLClass owlClass;

    @Test
    public void shouldDelegateTo_getDisjointClassesAxioms_withReadLock() {
        ontology.getDisjointClassesAxioms(owlClass);
        readLock(i -> i.verify(delegate).getDisjointClassesAxioms(owlClass));
    }

    @Test
    public void shouldDelegateTo_getDisjointUnionAxioms_withReadLock() {
        ontology.getDisjointUnionAxioms(owlClass);
        readLock(i -> i.verify(delegate).getDisjointUnionAxioms(owlClass));
    }

    @Test
    public void shouldDelegateTo_getHasKeyAxioms_withReadLock() {
        ontology.getHasKeyAxioms(owlClass);
        readLock(i -> i.verify(delegate).getHasKeyAxioms(owlClass));
    }

    @Test
    public void shouldDelegateTo_getObjectSubPropertyAxiomsForSubProperty_withReadLock() {
        ontology.getObjectSubPropertyAxiomsForSubProperty(objectProperty);
        readLock(i -> i.verify(delegate)
            .getObjectSubPropertyAxiomsForSubProperty(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getObjectSubPropertyAxiomsForSuperProperty_withReadLock() {
        ontology.getObjectSubPropertyAxiomsForSuperProperty(objectProperty);
        readLock(i -> i.verify(delegate)
            .getObjectSubPropertyAxiomsForSuperProperty(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getObjectPropertyDomainAxioms_withReadLock() {
        ontology.getObjectPropertyDomainAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getObjectPropertyDomainAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getObjectPropertyRangeAxioms_withReadLock() {
        ontology.getObjectPropertyRangeAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getObjectPropertyRangeAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getInverseObjectPropertyAxioms_withReadLock() {
        ontology.getInverseObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getInverseObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getEquivalentObjectPropertiesAxioms_withReadLock() {
        ontology.getEquivalentObjectPropertiesAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getEquivalentObjectPropertiesAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getDisjointObjectPropertiesAxioms_withReadLock() {
        ontology.getDisjointObjectPropertiesAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getDisjointObjectPropertiesAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getFunctionalObjectPropertyAxioms_withReadLock() {
        ontology.getFunctionalObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getFunctionalObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getInverseFunctionalObjectPropertyAxioms_withReadLock() {
        ontology.getInverseFunctionalObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getInverseFunctionalObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getSymmetricObjectPropertyAxioms_withReadLock() {
        ontology.getSymmetricObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getSymmetricObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getAsymmetricObjectPropertyAxioms_withReadLock() {
        ontology.getAsymmetricObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getAsymmetricObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getReflexiveObjectPropertyAxioms_withReadLock() {
        ontology.getReflexiveObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getReflexiveObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getIrreflexiveObjectPropertyAxioms_withReadLock() {
        ontology.getIrreflexiveObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getIrreflexiveObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getTransitiveObjectPropertyAxioms_withReadLock() {
        ontology.getTransitiveObjectPropertyAxioms(objectProperty);
        readLock(i -> i.verify(delegate)
            .getTransitiveObjectPropertyAxioms(objectProperty));
    }

    @Test
    public void shouldDelegateTo_getDataSubPropertyAxiomsForSubProperty_withReadLock() {
        ontology.getDataSubPropertyAxiomsForSubProperty(dataProperty);
        readLock(i -> i.verify(delegate)
            .getDataSubPropertyAxiomsForSubProperty(dataProperty));
    }

    @Test
    public void shouldDelegateTo_getDataSubPropertyAxiomsForSuperProperty_withReadLock() {
        ontology
            .getDataSubPropertyAxiomsForSuperProperty(dataPropertyExpression);
        readLock(i -> i.verify(delegate)
            .getDataSubPropertyAxiomsForSuperProperty(dataPropertyExpression));
    }

    @Mock
    OWLDataPropertyExpression dataPropertyExpression;

    @Test
    public void shouldDelegateTo_getDataPropertyDomainAxioms_withReadLock() {
        ontology.getDataPropertyDomainAxioms(dataProperty);
        readLock(
            i -> i.verify(delegate).getDataPropertyDomainAxioms(dataProperty));
    }

    @Test
    public void shouldDelegateTo_getDataPropertyRangeAxioms_withReadLock() {
        ontology.getDataPropertyRangeAxioms(dataProperty);
        readLock(
            i -> i.verify(delegate).getDataPropertyRangeAxioms(dataProperty));
    }

    @Test
    public void shouldDelegateTo_getEquivalentDataPropertiesAxioms_withReadLock() {
        ontology.getEquivalentDataPropertiesAxioms(dataProperty);
        readLock(i -> i.verify(delegate)
            .getEquivalentDataPropertiesAxioms(dataProperty));
    }

    @Test
    public void shouldDelegateTo_getDisjointDataPropertiesAxioms_withReadLock() {
        ontology.getDisjointDataPropertiesAxioms(dataProperty);
        readLock(i -> i.verify(delegate)
            .getDisjointDataPropertiesAxioms(dataProperty));
    }

    @Test
    public void shouldDelegateTo_getFunctionalDataPropertyAxioms_withReadLock() {
        ontology.getFunctionalDataPropertyAxioms(dataPropertyExpression);
        readLock(i -> i.verify(delegate)
            .getFunctionalDataPropertyAxioms(dataPropertyExpression));
    }

    @Test
    public void shouldDelegateTo_getClassAssertionAxioms_withReadLock() {
        ontology.getClassAssertionAxioms(classExpression);
        readLock(
            i -> i.verify(delegate).getClassAssertionAxioms(classExpression));
    }

    @Mock
    OWLClassExpression classExpression;

    @Test
    public void shouldDelegateTo_getClassAssertionAxioms_withReadLock_2() {
        ontology.getClassAssertionAxioms(individual);
        readLock(i -> i.verify(delegate).getClassAssertionAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getDataPropertyAssertionAxioms_withReadLock() {
        ontology.getDataPropertyAssertionAxioms(individual);
        readLock(
            i -> i.verify(delegate).getDataPropertyAssertionAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getObjectPropertyAssertionAxioms_withReadLock() {
        ontology.getObjectPropertyAssertionAxioms(individual);
        readLock(i -> i.verify(delegate)
            .getObjectPropertyAssertionAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getNegativeObjectPropertyAssertionAxioms_withReadLock() {
        ontology.getNegativeObjectPropertyAssertionAxioms(individual);
        readLock(i -> i.verify(delegate)
            .getNegativeObjectPropertyAssertionAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getNegativeDataPropertyAssertionAxioms_withReadLock() {
        ontology.getNegativeDataPropertyAssertionAxioms(individual);
        readLock(i -> i.verify(delegate)
            .getNegativeDataPropertyAssertionAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getSameIndividualAxioms_withReadLock() {
        ontology.getSameIndividualAxioms(individual);
        readLock(i -> i.verify(delegate).getSameIndividualAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getDifferentIndividualAxioms_withReadLock() {
        ontology.getDifferentIndividualAxioms(individual);
        readLock(
            i -> i.verify(delegate).getDifferentIndividualAxioms(individual));
    }

    @Test
    public void shouldDelegateTo_getDatatypeDefinitions_withReadLock() {
        ontology.getDatatypeDefinitions(datatype);
        readLock(i -> i.verify(delegate).getDatatypeDefinitions(datatype));
    }

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
