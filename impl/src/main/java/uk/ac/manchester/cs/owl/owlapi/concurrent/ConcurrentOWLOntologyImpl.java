package uk.ac.manchester.cs.owl.owlapi.concurrent;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 08/04/15
 */

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi.model.parameters.ChangeApplied;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import uk.ac.manchester.cs.owl.owlapi.HasTrimToSize;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 03/04/15
 */
public class ConcurrentOWLOntologyImpl implements OWLMutableOntology,HasTrimToSize {

    private final OWLOntology delegate;
    private final ReadWriteLock readWriteLock;
    private final Lock readLock;
    private final Lock writeLock;

    /**
     * Constructs a ConcurrentOWLOntology that provides concurrent access to a delegate {@link OWLOntology}.
     * @param delegate The delegate {@link OWLOntology}.
     * @param readWriteLock The {@link java.util.concurrent.locks.ReadWriteLock} that will provide the locking.
     * @throws java.lang.NullPointerException if any parameters are {@code null}.
     */
    @Inject
    public ConcurrentOWLOntologyImpl(@Nonnull OWLOntology delegate, @Nonnull ReadWriteLock readWriteLock) {
        this.delegate = verifyNotNull(delegate);
        this.readWriteLock = verifyNotNull(readWriteLock);
        this.readLock = verifyNotNull(readWriteLock).readLock();
        this.writeLock = verifyNotNull(readWriteLock).writeLock();
    }

    @Override
    public void trimToSize() {
        writeLock.lock();
        try {
            if (delegate instanceof HasTrimToSize) {
                HasTrimToSize trimmableDelegate = (HasTrimToSize) delegate;
                trimmableDelegate.trimToSize();
            }
        } finally {
            writeLock.unlock();
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
        readLock.lock();
        try {
            return delegate.hashCode();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean equals(Object obj) {
        readLock.lock();
        try {
            return delegate.equals(obj);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public OWLOntologyManager getOWLOntologyManager() {
        readLock.lock();
        try {
            return delegate.getOWLOntologyManager();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        writeLock.lock();
        try {
            delegate.setOWLOntologyManager(owlOntologyManager);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Nonnull
    public OWLOntologyID getOntologyID() {
        readLock.lock();
        try {
            return delegate.getOntologyID();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isAnonymous() {
        readLock.lock();
        try {
            return delegate.isAnonymous();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnnotation> getAnnotations() {
        readLock.lock();
        try {
            return delegate.getAnnotations();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<IRI> getDirectImportsDocuments() {
        readLock.lock();
        try {
            return delegate.getDirectImportsDocuments();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLOntology> getDirectImports() {
        readLock.lock();
        try {
            return delegate.getDirectImports();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLOntology> getImports() {
        readLock.lock();
        try {
            return delegate.getImports();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLOntology> getImportsClosure() {
        readLock.lock();
        try {
            return delegate.getImportsClosure();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        readLock.lock();
        try {
            return delegate.getImportsDeclarations();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        readLock.lock();
        try {
            return delegate.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getTBoxAxioms(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getTBoxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getABoxAxioms(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getABoxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getRBoxAxioms(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getRBoxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        readLock.lock();
        try {
            return delegate.getGeneralClassAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getSignature() {
        readLock.lock();
        try {
            return delegate.getSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getSignature(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isDeclared(@Nonnull OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.isDeclared(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isDeclared(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.isDeclared(owlEntity, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology() throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull IRI iri) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull OutputStream outputStream) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(outputStream);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat, @Nonnull IRI iri) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat, iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat, @Nonnull OutputStream outputStream) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat, outputStream);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull OWLOntologyDocumentTarget owlOntologyDocumentTarget) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlOntologyDocumentTarget);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(@Nonnull OWLDocumentFormat owlDocumentFormat, @Nonnull OWLOntologyDocumentTarget owlOntologyDocumentTarget) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat, owlOntologyDocumentTarget);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLClassExpression> getNestedClassExpressions() {
        readLock.lock();
        try {
            return delegate.getNestedClassExpressions();
        } finally {
            readLock.unlock();
        }
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
        readLock.lock();
        try {
            return delegate.isTopEntity();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isBottomEntity() {
        readLock.lock();
        try {
            return delegate.isBottomEntity();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public String toString() {
        readLock.lock();
        try {
            return delegate.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int compareTo(OWLObject o) {
        readLock.lock();
        try {
            return delegate.compareTo(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        readLock.lock();
        try {
            return delegate.getAnonymousIndividuals();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLClass> getClassesInSignature() {
        readLock.lock();
        try {
            return delegate.getClassesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.getObjectPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.getDataPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        readLock.lock();
        try {
            return delegate.getIndividualsInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDatatype> getDatatypesInSignature() {
        readLock.lock();
        try {
            return delegate.getDatatypesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxioms(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getAxiomCount(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLLogicalAxiom> getLogicalAxioms(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getLogicalAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getLogicalAxiomCount(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getLogicalAxiomCount(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(axiomType, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(axiomType, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom, @Nonnull Imports imports, @Nonnull AxiomAnnotations axiomAnnotations) {
        readLock.lock();
        try {
            return delegate.containsAxiom(owlAxiom, imports, axiomAnnotations);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxiomsIgnoreAnnotations(owlAxiom, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getReferencingAxioms(owlPrimitive, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlClass, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyAxiom> getAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlObjectPropertyExpression, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDataProperty, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlIndividual, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlAnnotationProperty, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDatatype, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxioms() {
        readLock.lock();
        try {
            return delegate.getAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        readLock.lock();
        try {
            return delegate.getLogicalAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType) {
        readLock.lock();
        try {
            return delegate.getAxioms(axiomType);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom) {
        readLock.lock();
        try {
            return delegate.containsAxiom(owlAxiom);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLAxiom> getAxioms(boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public int getAxiomCount(boolean b) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLLogicalAxiom> getLogicalAxioms(boolean b) {
        readLock.lock();
        try {
            return delegate.getLogicalAxioms(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public int getLogicalAxiomCount(boolean b) {
        readLock.lock();
        try {
            return delegate.getLogicalAxiomCount(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull AxiomType<T> axiomType, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(axiomType, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(axiomType, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsAxiom(@Nonnull OWLAxiom owlAxiom, boolean b) {
        readLock.lock();
        try {
            return delegate.containsAxiom(owlAxiom, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom, boolean b) {
        readLock.lock();
        try {
            return delegate.containsAxiomIgnoreAnnotations(owlAxiom, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxiomsIgnoreAnnotations(owlAxiom, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive, boolean b) {
        readLock.lock();
        try {
            return delegate.getReferencingAxioms(owlPrimitive, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlClass, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLObjectPropertyAxiom> getAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlObjectPropertyExpression, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDataProperty, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlIndividual, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlAnnotationProperty, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDatatype, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getAxiomCount() {
        readLock.lock();
        try {
            return delegate.getAxiomCount();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getLogicalAxiomCount() {
        readLock.lock();
        try {
            return delegate.getLogicalAxiomCount();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(axiomType);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom) {
        readLock.lock();
        try {
            return delegate.containsAxiomIgnoreAnnotations(owlAxiom);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(@Nonnull OWLAxiom owlAxiom) {
        readLock.lock();
        try {
            return delegate.getAxiomsIgnoreAnnotations(owlAxiom);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLPrimitive owlPrimitive) {
        readLock.lock();
        try {
            return delegate.getReferencingAxioms(owlPrimitive);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLClassAxiom> getAxioms(@Nonnull OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLObjectPropertyAxiom> getAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDataPropertyAxiom> getAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLIndividualAxiom> getAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLAnnotationAxiom> getAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    @Deprecated
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(@Nonnull OWLDatatype owlDatatype) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDatatype);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLClass> getClassesInSignature(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getClassesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getObjectPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDataProperty> getDataPropertiesInSignature(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getDataPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLNamedIndividual> getIndividualsInSignature(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getIndividualsInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getReferencedAnonymousIndividuals(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDatatype> getDatatypesInSignature(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getDatatypesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(owlEntity, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsClassInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsClassInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsObjectPropertyInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsDataPropertyInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsAnnotationPropertyInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDatatypeInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsDatatypeInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsIndividualInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsIndividualInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDatatypeInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.containsDatatypeInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsClassInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.containsClassInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.containsObjectPropertyInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.containsDataPropertyInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.containsAnnotationPropertyInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsIndividualInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.containsIndividualInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getEntitiesInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<IRI> getPunnedIRIs(@Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.getPunnedIRIs(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity owlEntity, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.containsReference(owlEntity, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsReference(@Nonnull OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.containsReference(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri) {
        readLock.lock();
        try {
            return delegate.getEntitiesInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLClass> getClassesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getClassesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getObjectPropertiesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getDataPropertiesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getIndividualsInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(boolean b) {
        readLock.lock();
        try {
            return delegate.getReferencedAnonymousIndividuals(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLDatatype> getDatatypesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getDatatypesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertiesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity, boolean b) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(owlEntity, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsEntityInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsClassInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsClassInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsObjectPropertyInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsObjectPropertyInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsDataPropertyInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsDataPropertyInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsAnnotationPropertyInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsAnnotationPropertyInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsDatatypeInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsDatatypeInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsIndividualInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsIndividualInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    @Nonnull
    public Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.getEntitiesInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Deprecated
    public boolean containsReference(@Nonnull OWLEntity owlEntity, boolean b) {
        readLock.lock();
        try {
            return delegate.containsReference(owlEntity, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull Class<T> aClass, @Nonnull OWLObject owlObject, @Nonnull Imports imports, @Nonnull Navigation navigation) {
        readLock.lock();
        try {
            return delegate.getAxioms(aClass, owlObject, imports, navigation);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Collection<T> filterAxioms(@Nonnull OWLAxiomSearchFilter owlAxiomSearchFilter, @Nonnull Object o, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.filterAxioms(owlAxiomSearchFilter, o, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(@Nonnull OWLAxiomSearchFilter owlAxiomSearchFilter, @Nonnull Object o, @Nonnull Imports imports) {
        readLock.lock();
        try {
            return delegate.contains(owlAxiomSearchFilter, o, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public <T extends OWLAxiom> Set<T> getAxioms(@Nonnull Class<T> aClass, @Nonnull Class<? extends OWLObject> aClass1, @Nonnull OWLObject owlObject, @Nonnull Imports imports, @Nonnull Navigation navigation) {
        readLock.lock();
        try {
            return delegate.getAxioms(aClass, aClass1, owlObject, imports, navigation);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getSubAnnotationPropertyOfAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertyDomainAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(@Nonnull OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertyRangeAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(@Nonnull OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.getDeclarationAxioms(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(@Nonnull OWLAnnotationSubject owlAnnotationSubject) {
        readLock.lock();
        try {
            return delegate.getAnnotationAssertionAxioms(owlAnnotationSubject);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(@Nonnull OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getSubClassAxiomsForSubClass(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(@Nonnull OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getSubClassAxiomsForSuperClass(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(@Nonnull OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getEquivalentClassesAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(@Nonnull OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getDisjointClassesAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(@Nonnull OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getDisjointUnionAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(@Nonnull OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getHasKeyAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectSubPropertyAxiomsForSubProperty(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectSubPropertyAxiomsForSuperProperty(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectPropertyDomainAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectPropertyRangeAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getInverseObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getEquivalentObjectPropertiesAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getDisjointObjectPropertiesAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getFunctionalObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getInverseFunctionalObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getSymmetricObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getAsymmetricObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getReflexiveObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getIrreflexiveObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(@Nonnull OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getTransitiveObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(@Nonnull OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDataSubPropertyAxiomsForSubProperty(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(@Nonnull OWLDataPropertyExpression owlDataPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getDataSubPropertyAxiomsForSuperProperty(owlDataPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDataPropertyDomainAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDataPropertyRangeAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getEquivalentDataPropertiesAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(@Nonnull OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDisjointDataPropertiesAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(@Nonnull OWLDataPropertyExpression owlDataPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getFunctionalDataPropertyAxioms(owlDataPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getClassAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(@Nonnull OWLClassExpression owlClassExpression) {
        readLock.lock();
        try {
            return delegate.getClassAssertionAxioms(owlClassExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getDataPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getObjectPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getNegativeObjectPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getNegativeDataPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getSameIndividualAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(@Nonnull OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getDifferentIndividualAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(@Nonnull OWLDatatype owlDatatype) {
        readLock.lock();
        try {
            return delegate.getDatatypeDefinitions(owlDatatype);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    @Nonnull
    public ChangeApplied applyChange(@Nonnull OWLOntologyChange owlOntologyChange) {
        writeLock.lock();
        try {
            return getMutableOntology().applyChange(owlOntologyChange);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Nonnull
    public ChangeApplied applyChanges(
        @Nonnull List<? extends OWLOntologyChange> list) {
        writeLock.lock();
        try {
            return getMutableOntology().applyChanges(list);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    @Nonnull
    public ChangeApplied addAxiom(@Nonnull OWLAxiom owlAxiom) {
        writeLock.lock();
        try {
            return getMutableOntology().addAxiom(owlAxiom);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied addAxioms(@Nonnull Set<? extends OWLAxiom> set) {
        writeLock.lock();
        try {
            return getMutableOntology().addAxioms(set);
        } finally {
            writeLock.unlock();
        }
    }

    private OWLMutableOntology getMutableOntology() {
        return (OWLMutableOntology) delegate;
    }
}
