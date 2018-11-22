package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 08/04/15
 */

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.ChangeDetails;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLMutableOntology;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi.model.OWLNamedObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPrimitive;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.ChangeApplied;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

import uk.ac.manchester.cs.owl.owlapi.HasTrimToSize;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 03/04/15
 */
public class ConcurrentOWLOntologyImpl implements OWLMutableOntology, HasTrimToSize {

    private final OWLOntology delegate;
    private ReadWriteLock lock;

    /**
     * Constructs a ConcurrentOWLOntology that provides concurrent access to a delegate
     * {@link OWLOntology}.
     * 
     * @param delegate The delegate {@link OWLOntology}.
     * @param readWriteLock The {@link java.util.concurrent.locks.ReadWriteLock} that will provide
     *        the locking.
     * @throws java.lang.NullPointerException if any parameters are {@code null}.
     */
    @Inject
    public ConcurrentOWLOntologyImpl(@Nonnull OWLOntology delegate,
        @Nonnull ReadWriteLock readWriteLock) {
        this.delegate = verifyNotNull(delegate);
        lock = verifyNotNull(readWriteLock);
    }

    @Override
    public void setLock(ReadWriteLock lock) {
        this.lock = lock;
    }

    private <T> T withWriteLock(Supplier<T> t) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            return t.get();
        } finally {
            writeLock.unlock();
        }
    }

    private void callWriteLock(Runnable t) {
        Lock writeLock = lock.writeLock();
        writeLock.lock();
        try {
            t.run();
        } finally {
            writeLock.unlock();
        }
    }

    private <T> T withReadLock(Supplier<T> t) {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return t.get();
        } finally {
            readLock.unlock();
        }
    }

    private boolean withBooleanReadLock(BooleanSupplier t) {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return t.getAsBoolean();
        } finally {
            readLock.unlock();
        }
    }

    private int withIntReadLock(IntSupplier t) {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            return t.getAsInt();
        } finally {
            readLock.unlock();
        }
    }

    private interface Store {
        void store() throws OWLOntologyStorageException;
    }

    private void callReadLock(Store t) throws OWLOntologyStorageException {
        Lock readLock = lock.readLock();
        readLock.lock();
        try {
            t.store();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void trimToSize() {
        callWriteLock(this::trimToSizeInternal);
    }

    protected void trimToSizeInternal() {
        if (delegate instanceof HasTrimToSize) {
            HasTrimToSize trimmableDelegate = (HasTrimToSize) delegate;
            trimmableDelegate.trimToSize();
        }
    }

    @Override
    public void accept(@Nonnull OWLNamedObjectVisitor owlNamedObjectVisitor) {
        delegate.accept(owlNamedObjectVisitor);
    }

    @Override
    @Nonnull
    public <O> O accept(@Nonnull OWLNamedObjectVisitorEx<O> owlNamedObjectVisitorEx) {
        return delegate.accept(owlNamedObjectVisitorEx);
    }

    @Override
    public int hashCode() {
        return withIntReadLock(delegate::hashCode);
    }

    @Override
    public boolean equals(Object obj) {
        return withBooleanReadLock(() -> delegate.equals(obj));
    }

    @Override
    @Nonnull
    public OWLOntologyManager getOWLOntologyManager() {
        return withReadLock(delegate::getOWLOntologyManager);
    }

    @Override
    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        callWriteLock(() -> delegate.setOWLOntologyManager(owlOntologyManager));
    }

    @Override
    @Nonnull
    public OWLOntologyID getOntologyID() {
        return withReadLock(delegate::getOntologyID);
    }

    @Override
    public boolean isAnonymous() {
        return withBooleanReadLock(delegate::isAnonymous);
    }

    @Override
    @Nonnull
    public Set<OWLAnnotation> getAnnotations() {
        return withReadLock(delegate::getAnnotations);
    }

    @Override
    @Nonnull
    public Set<IRI> getDirectImportsDocuments() {
        return withReadLock(delegate::getDirectImportsDocuments);
    }

    @Override
    @Nonnull
    public Set<OWLOntology> getDirectImports() {
        return withReadLock(delegate::getDirectImports);
    }

    @Override
    @Nonnull
    public Set<OWLOntology> getImports() {
        return withReadLock(delegate::getImports);
    }

    @Override
    @Nonnull
    public Set<OWLOntology> getImportsClosure() {
        return withReadLock(delegate::getImportsClosure);
    }

    @Override
    @Nonnull
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return withReadLock(delegate::getImportsDeclarations);
    }

    @Override
    public boolean isEmpty() {
        return withBooleanReadLock(delegate::isEmpty);
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getTBoxAxioms(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getTBoxAxioms(imports));
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getABoxAxioms(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getABoxAxioms(imports));
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getRBoxAxioms(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getRBoxAxioms(imports));
    }

    @Override
    @Nonnull
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return withReadLock(delegate::getGeneralClassAxioms);
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getSignature() {
        return withReadLock(delegate::getSignature);
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getSignature(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getSignature(imports));
    }

    @Override
    public boolean isDeclared(@Nonnull OWLEntity owlEntity) {
        return withBooleanReadLock(() -> delegate.isDeclared(owlEntity));
    }

    @Override
    public boolean isDeclared(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.isDeclared(owlEntity, imports));
    }

    @Override
    public void saveOntology() throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology());
    }

    @Override
    public void saveOntology(@Nonnull IRI iri) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(iri));
    }

    @Override
    public void saveOntology(@Nonnull OutputStream outputStream)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(outputStream));
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat));
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat, @Nonnull IRI iri)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, iri));
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat,
        @Nonnull OutputStream outputStream) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, outputStream));
    }

    @Override
    public void saveOntology(@Nonnull OWLOntologyDocumentTarget owlOntologyDocumentTarget)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlOntologyDocumentTarget));
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat,
        @Nonnull OWLOntologyDocumentTarget owlOntologyDocumentTarget)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, owlOntologyDocumentTarget));
    }

    @Override
    @Nonnull
    public Set<OWLClassExpression> getNestedClassExpressions() {
        return withReadLock(delegate::getNestedClassExpressions);
    }

    @Override
    public void accept(@Nonnull OWLObjectVisitor owlObjectVisitor) {
        delegate.accept(owlObjectVisitor);
    }

    @Override
    @Nonnull
    public <O> O accept(@Nonnull OWLObjectVisitorEx<O> owlObjectVisitorEx) {
        return delegate.accept(owlObjectVisitorEx);
    }

    @Override
    public boolean isTopEntity() {
        return withBooleanReadLock(delegate::isTopEntity);
    }

    @Override
    public boolean isBottomEntity() {
        return withBooleanReadLock(delegate::isBottomEntity);
    }

    @Override
    @Nonnull
    public String toString() {
        return withReadLock(delegate::toString);
    }

    @Override
    public int compareTo(OWLObject o) {
        return withIntReadLock(() -> delegate.compareTo(o));
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(owlEntity));
    }

    @Override
    public boolean containsEntitiesOfTypeInSignature(EntityType<?> type) {
        return withBooleanReadLock(() -> delegate.containsEntitiesOfTypeInSignature(type));
    }

    @Override
    public boolean containsEntitiesOfTypeInSignature(EntityType<?> type,
        Imports includeImportsClosure) {
        return withBooleanReadLock(
            () -> delegate.containsEntitiesOfTypeInSignature(type, includeImportsClosure));
    }

    @Override
    @Nonnull
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return withReadLock(delegate::getAnonymousIndividuals);
    }

    @Override
    @Nonnull
    public Set<OWLClass> getClassesInSignature() {
        return withReadLock(delegate::getClassesInSignature);
    }

    @Override
    @Nonnull
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return withReadLock(delegate::getObjectPropertiesInSignature);
    }

    @Override
    @Nonnull
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return withReadLock(delegate::getDataPropertiesInSignature);
    }

    @Override
    @Nonnull
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return withReadLock(delegate::getIndividualsInSignature);
    }

    @Override
    @Nonnull
    public Set<OWLDatatype> getDatatypesInSignature() {
        return withReadLock(delegate::getDatatypesInSignature);
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        return withReadLock(delegate::getAnnotationPropertiesInSignature);
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxioms(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(imports));
    }

    @Override
    public int getAxiomCount(@Nonnull Imports imports) {
        return withIntReadLock(() -> delegate.getAxiomCount(imports));
    }

    @Override
    @Nonnull
    public Set<OWLLogicalAxiom> getLogicalAxioms(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getLogicalAxioms(imports));
    }

    @Override
    public int getLogicalAxiomCount(@Nonnull Imports imports) {
        return withIntReadLock(() -> delegate.getLogicalAxiomCount(imports));
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(axiomType, imports));
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType,
        @Nonnull Imports imports) {
        return withIntReadLock(() -> delegate.getAxiomCount(axiomType, imports));
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom, @Nonnull Imports imports,
        @Nonnull AxiomAnnotations axiomAnnotations) {
        return withBooleanReadLock(
            () -> delegate.containsAxiom(owlAxiom, imports, axiomAnnotations));
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxiomsIgnoreAnnotations(owlAxiom, imports));
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getReferencingAxioms(owlPrimitive, imports));
    }

    @Override
    @Nonnull
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass, @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlClass, imports));
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyAxiom> getAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlObjectPropertyExpression, imports));
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlDataProperty, imports));
    }

    @Override
    @Nonnull
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlIndividual, imports));
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlAnnotationProperty, imports));
    }

    @Override
    @Nonnull
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlDatatype, imports));
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxioms() {
        return withReadLock(delegate::getAxioms);
    }

    @Override
    @Nonnull
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        return withReadLock(delegate::getLogicalAxioms);
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType) {
        return withReadLock(() -> delegate.getAxioms(axiomType));
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom) {
        return withBooleanReadLock(() -> delegate.containsAxiom(owlAxiom));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLAxiom> getAxioms(boolean b) {
        return withReadLock(() -> delegate.getAxioms(b));
    }

    @Override
    @Deprecated
    public int getAxiomCount(boolean b) {
        return withIntReadLock(() -> delegate.getAxiomCount(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLLogicalAxiom> getLogicalAxioms(boolean b) {
        return withReadLock(() -> delegate.getLogicalAxioms(b));
    }

    @Override
    @Deprecated
    public int getLogicalAxiomCount(boolean b) {
        return withIntReadLock(() -> delegate.getLogicalAxiomCount(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType, boolean b) {
        return withReadLock(() -> delegate.getAxioms(axiomType, b));
    }

    @Override
    @Deprecated
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType, boolean b) {
        return withIntReadLock(() -> delegate.getAxiomCount(axiomType, b));
    }

    @Override
    @Deprecated
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom, boolean b) {
        return withBooleanReadLock(() -> delegate.containsAxiom(owlAxiom, b));
    }

    @Override
    @Deprecated
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom, boolean b) {
        return withBooleanReadLock(() -> delegate.containsAxiomIgnoreAnnotations(owlAxiom, b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom, boolean b) {
        return withReadLock(() -> delegate.getAxiomsIgnoreAnnotations(owlAxiom, b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive, boolean b) {
        return withReadLock(() -> delegate.getReferencingAxioms(owlPrimitive, b));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlClass, b));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLObjectPropertyAxiom> getAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlObjectPropertyExpression, b));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty,
        boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlDataProperty, b));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlIndividual, b));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty,
        boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlAnnotationProperty, b));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlDatatype, b));
    }

    @Override
    public int getAxiomCount() {
        return withIntReadLock(delegate::getAxiomCount);
    }

    @Override
    public int getLogicalAxiomCount() {
        return withIntReadLock(delegate::getLogicalAxiomCount);
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType) {
        return withIntReadLock(() -> delegate.getAxiomCount(axiomType));
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom) {
        return withBooleanReadLock(() -> delegate.containsAxiomIgnoreAnnotations(owlAxiom));
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom) {
        return withReadLock(() -> delegate.getAxiomsIgnoreAnnotations(owlAxiom));
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive) {
        return withReadLock(() -> delegate.getReferencingAxioms(owlPrimitive));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass) {
        return withReadLock(() -> delegate.getAxioms(owlClass));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLObjectPropertyAxiom> getAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(() -> delegate.getAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getAxioms(owlDataProperty));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.getAxioms(owlAnnotationProperty));
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype) {
        return withReadLock(() -> delegate.getAxioms(owlDatatype));
    }

    @Override
    @Nonnull
    public Set<OWLClass> getClassesInSignature(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getClassesInSignature(imports));
    }

    @Override
    @Nonnull
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getObjectPropertiesInSignature(imports));
    }

    @Override
    @Nonnull
    public Set<OWLDataProperty> getDataPropertiesInSignature(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getDataPropertiesInSignature(imports));
    }

    @Override
    @Nonnull
    public Set<OWLNamedIndividual> getIndividualsInSignature(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getIndividualsInSignature(imports));
    }

    @Override
    @Nonnull
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getReferencedAnonymousIndividuals(imports));
    }

    @Override
    @Nonnull
    public Set<OWLDatatype> getDatatypesInSignature(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getDatatypesInSignature(imports));
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getAnnotationPropertiesInSignature(imports));
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity,
        @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(owlEntity, imports));
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(iri, imports));
    }

    @Override
    public boolean containsClassInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsClassInSignature(iri, imports));
    }

    @Override
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsObjectPropertyInSignature(iri, imports));
    }

    @Override
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsDataPropertyInSignature(iri, imports));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri,
        @Nonnull Imports imports) {
        return withBooleanReadLock(
            () -> delegate.containsAnnotationPropertyInSignature(iri, imports));
    }

    @Override
    public boolean containsDatatypeInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsDatatypeInSignature(iri, imports));
    }

    @Override
    public boolean containsIndividualInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsIndividualInSignature(iri, imports));
    }

    @Override
    public boolean containsDatatypeInSignature(@Nonnull IRI iri) {
        return withBooleanReadLock(() -> delegate.containsDatatypeInSignature(iri));
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull IRI iri) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(iri));
    }

    @Override
    public boolean containsClassInSignature(@Nonnull IRI iri) {
        return withBooleanReadLock(() -> delegate.containsClassInSignature(iri));
    }

    @Override
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri) {
        return withBooleanReadLock(() -> delegate.containsObjectPropertyInSignature(iri));
    }

    @Override
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri) {
        return withBooleanReadLock(() -> delegate.containsDataPropertyInSignature(iri));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri) {
        return withBooleanReadLock(() -> delegate.containsAnnotationPropertyInSignature(iri));
    }

    @Override
    public boolean containsIndividualInSignature(@Nonnull IRI iri) {
        return withBooleanReadLock(() -> delegate.containsIndividualInSignature(iri));
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        return withReadLock(() -> delegate.getEntitiesInSignature(iri, imports));
    }

    @Override
    public Set<IRI> getPunnedIRIs(@Nonnull Imports imports) {
        return withReadLock(() -> delegate.getPunnedIRIs(imports));
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.containsReference(owlEntity, imports));
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity owlEntity) {
        return withBooleanReadLock(() -> delegate.containsReference(owlEntity));
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri) {
        return withReadLock(() -> delegate.getEntitiesInSignature(iri));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLClass> getClassesInSignature(boolean b) {
        return withReadLock(() -> delegate.getClassesInSignature(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean b) {
        return withReadLock(() -> delegate.getObjectPropertiesInSignature(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean b) {
        return withReadLock(() -> delegate.getDataPropertiesInSignature(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean b) {
        return withReadLock(() -> delegate.getIndividualsInSignature(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(boolean b) {
        return withReadLock(() -> delegate.getReferencedAnonymousIndividuals(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLDatatype> getDatatypesInSignature(boolean b) {
        return withReadLock(() -> delegate.getDatatypesInSignature(b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(boolean b) {
        return withReadLock(() -> delegate.getAnnotationPropertiesInSignature(b));
    }

    @Override
    @Deprecated
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity, boolean b) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(owlEntity, b));
    }

    @Override
    @Deprecated
    public boolean containsEntityInSignature(@Nonnull IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(iri, b));
    }

    @Override
    @Deprecated
    public boolean containsClassInSignature(@Nonnull IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsClassInSignature(iri, b));
    }

    @Override
    @Deprecated
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsObjectPropertyInSignature(iri, b));
    }

    @Override
    @Deprecated
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsDataPropertyInSignature(iri, b));
    }

    @Override
    @Deprecated
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsAnnotationPropertyInSignature(iri, b));
    }

    @Override
    @Deprecated
    public boolean containsDatatypeInSignature(@Nonnull IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsDatatypeInSignature(iri, b));
    }

    @Override
    @Deprecated
    public boolean containsIndividualInSignature(@Nonnull IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsIndividualInSignature(iri, b));
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri, boolean b) {
        return withReadLock(() -> delegate.getEntitiesInSignature(iri, b));
    }

    @Override
    @Deprecated
    public boolean containsReference(@Nonnull OWLEntity owlEntity, boolean b) {
        return withBooleanReadLock(() -> delegate.containsReference(owlEntity, b));
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull Class<T> aClass,
        @Nonnull OWLObject owlObject, @Nonnull Imports imports, @Nonnull Navigation navigation) {
        return withReadLock(() -> delegate.getAxioms(aClass, owlObject, imports, navigation));
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Collection<T> filterAxioms(
        @Nonnull OWLAxiomSearchFilter owlAxiomSearchFilter, @Nonnull Object o,
        @Nonnull Imports imports) {
        return withReadLock(() -> delegate.filterAxioms(owlAxiomSearchFilter, o, imports));
    }

    @Override
    public boolean contains(@Nonnull OWLAxiomSearchFilter owlAxiomSearchFilter, @Nonnull Object o,
        @Nonnull Imports imports) {
        return withBooleanReadLock(() -> delegate.contains(owlAxiomSearchFilter, o, imports));
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull Class<T> aClass,
        @Nonnull Class<? extends OWLObject> aClass1, @Nonnull OWLObject owlObject,
        @Nonnull Imports imports, @Nonnull Navigation navigation) {
        return withReadLock(
            () -> delegate.getAxioms(aClass, aClass1, owlObject, imports, navigation));
    }

    @Override
    @Nonnull
    public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(
        @Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.getSubAnnotationPropertyOfAxioms(owlAnnotationProperty));
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(
        @Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(
            () -> delegate.getAnnotationPropertyDomainAxioms(owlAnnotationProperty));
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(
        @Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.getAnnotationPropertyRangeAxioms(owlAnnotationProperty));
    }

    @Override
    @Nonnull
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(@Nonnull OWLEntity owlEntity) {
        return withReadLock(() -> delegate.getDeclarationAxioms(owlEntity));
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
        @Nonnull OWLAnnotationSubject owlAnnotationSubject) {
        return withReadLock(() -> delegate.getAnnotationAssertionAxioms(owlAnnotationSubject));
    }

    @Override
    @Nonnull
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(@Nonnull OWLClass owlClass) {
        return withReadLock(() -> delegate.getSubClassAxiomsForSubClass(owlClass));
    }

    @Override
    @Nonnull
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(@Nonnull OWLClass owlClass) {
        return withReadLock(() -> delegate.getSubClassAxiomsForSuperClass(owlClass));
    }

    @Override
    @Nonnull
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(@Nonnull OWLClass owlClass) {
        return withReadLock(() -> delegate.getEquivalentClassesAxioms(owlClass));
    }

    @Override
    @Nonnull
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(@Nonnull OWLClass owlClass) {
        return withReadLock(() -> delegate.getDisjointClassesAxioms(owlClass));
    }

    @Override
    @Nonnull
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(@Nonnull OWLClass owlClass) {
        return withReadLock(() -> delegate.getDisjointUnionAxioms(owlClass));
    }

    @Override
    @Nonnull
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(@Nonnull OWLClass owlClass) {
        return withReadLock(() -> delegate.getHasKeyAxioms(owlClass));
    }

    @Override
    @Nonnull
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectSubPropertyAxiomsForSubProperty(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectSubPropertyAxiomsForSuperProperty(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectPropertyDomainAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectPropertyRangeAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getInverseObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getEquivalentObjectPropertiesAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getDisjointObjectPropertiesAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getFunctionalObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getInverseFunctionalObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getSymmetricObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getAsymmetricObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getReflexiveObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getIrreflexiveObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(
        @Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getTransitiveObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(
        @Nonnull OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDataSubPropertyAxiomsForSubProperty(owlDataProperty));
    }

    @Override
    @Nonnull
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
        @Nonnull OWLDataPropertyExpression owlDataPropertyExpression) {
        return withReadLock(
            () -> delegate.getDataSubPropertyAxiomsForSuperProperty(owlDataPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
        @Nonnull OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDataPropertyDomainAxioms(owlDataProperty));
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
        @Nonnull OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDataPropertyRangeAxioms(owlDataProperty));
    }

    @Override
    @Nonnull
    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(
        @Nonnull OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getEquivalentDataPropertiesAxioms(owlDataProperty));
    }

    @Override
    @Nonnull
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
        @Nonnull OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDisjointDataPropertiesAxioms(owlDataProperty));
    }

    @Override
    @Nonnull
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
        @Nonnull OWLDataPropertyExpression owlDataPropertyExpression) {
        return withReadLock(
            () -> delegate.getFunctionalDataPropertyAxioms(owlDataPropertyExpression));
    }

    @Override
    @Nonnull
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
        @Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getClassAssertionAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
        @Nonnull OWLClassExpression owlClassExpression) {
        return withReadLock(() -> delegate.getClassAssertionAxioms(owlClassExpression));
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
        @Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getDataPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(
        @Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getObjectPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
        @Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getNegativeObjectPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(
        @Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getNegativeDataPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
        @Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getSameIndividualAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
        @Nonnull OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getDifferentIndividualAxioms(owlIndividual));
    }

    @Override
    @Nonnull
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
        @Nonnull OWLDatatype owlDatatype) {
        return withReadLock(() -> delegate.getDatatypeDefinitions(owlDatatype));
    }

    @Override
    @Nonnull
    public ChangeApplied applyChange(@Nonnull OWLOntologyChange owlOntologyChange) {
        return withWriteLock(() -> getMutableOntology().applyChange(owlOntologyChange));
    }

    @Override
    @Nonnull
    public ChangeApplied applyChanges(@Nonnull List<? extends OWLOntologyChange> list) {
        return withWriteLock(() -> getMutableOntology().applyChanges(list));
    }

    @Override
    public ChangeDetails applyChangesAndGetDetails(List<? extends OWLOntologyChange> list) {
        return withWriteLock(() -> getMutableOntology().applyChangesAndGetDetails(list));
    }

    @Override
    @Nonnull
    public ChangeApplied addAxiom(@Nonnull OWLAxiom owlAxiom) {
        return withWriteLock(() -> getMutableOntology().addAxiom(owlAxiom));
    }

    @Override
    public ChangeApplied addAxioms(@Nonnull Set<? extends OWLAxiom> set) {
        return withWriteLock(() -> getMutableOntology().addAxioms(set));
    }

    private OWLMutableOntology getMutableOntology() {
        return (OWLMutableOntology) delegate;
    }
}
