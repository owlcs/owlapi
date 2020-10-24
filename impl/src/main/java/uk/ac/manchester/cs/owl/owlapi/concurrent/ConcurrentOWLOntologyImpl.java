/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.ChangeDetails;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.HasAxiomsByType;
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
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 08/04/15
 */

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 03/04/15
 */
@SuppressWarnings({"deprecation"})
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
    public ConcurrentOWLOntologyImpl(OWLOntology delegate, ReadWriteLock readWriteLock) {
        this.delegate = verifyNotNull(delegate);
        lock = verifyNotNull(readWriteLock);
    }

    @Override
    public int typeIndex() {
        return delegate.typeIndex();
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
            ((HasTrimToSize) delegate).trimToSize();
        }
    }

    @Override
    public void accept(OWLNamedObjectVisitor owlNamedObjectVisitor) {
        delegate.accept(owlNamedObjectVisitor);
    }

    @Override
    public <O> O accept(OWLNamedObjectVisitorEx<O> owlNamedObjectVisitorEx) {
        return delegate.accept(owlNamedObjectVisitorEx);
    }

    @Override
    public int hashCode() {
        return withIntReadLock(delegate::hashCode);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return withBooleanReadLock(() -> delegate.equals(obj));
    }

    @Override
    public OWLOntologyManager getOWLOntologyManager() {
        return withReadLock(delegate::getOWLOntologyManager);
    }

    @Override
    public void setOWLOntologyManager(@Nullable OWLOntologyManager owlOntologyManager) {
        callWriteLock(() -> delegate.setOWLOntologyManager(owlOntologyManager));
    }

    @Override
    public OWLOntologyID getOntologyID() {
        return withReadLock(delegate::getOntologyID);
    }

    @Override
    public boolean isAnonymous() {
        return withBooleanReadLock(delegate::isAnonymous);
    }

    @Override
    public Set<OWLAnnotation> getAnnotations() {
        return withReadLock(delegate::getAnnotations);
    }

    @Override
    public Set<IRI> getDirectImportsDocuments() {
        return withReadLock(delegate::getDirectImportsDocuments);
    }

    @Override
    public Stream<IRI> directImportsDocuments() {
        return withReadLock(delegate::directImportsDocuments);
    }

    @Override
    public Set<OWLOntology> getDirectImports() {
        return withReadLock(delegate::getDirectImports);
    }

    @Override
    public Stream<OWLOntology> directImports() {
        return withReadLock(delegate::directImports);
    }

    @Override
    public Set<OWLOntology> getImports() {
        return withReadLock(delegate::getImports);
    }

    @Override
    public Stream<OWLOntology> imports() {
        return withReadLock(delegate::imports);
    }

    @Override
    public Set<OWLOntology> getImportsClosure() {
        return withReadLock(delegate::getImportsClosure);
    }

    @Override
    public Stream<OWLOntology> importsClosure() {
        return withReadLock(delegate::importsClosure);
    }

    @Override
    public Set<OWLImportsDeclaration> getImportsDeclarations() {
        return withReadLock(delegate::getImportsDeclarations);
    }

    @Override
    public boolean isEmpty() {
        return withBooleanReadLock(delegate::isEmpty);
    }

    @Override
    public Set<OWLAxiom> getTBoxAxioms(Imports imports) {
        return withReadLock(() -> delegate.getTBoxAxioms(imports));
    }

    @Override
    public Set<OWLAxiom> getABoxAxioms(Imports imports) {
        return withReadLock(() -> delegate.getABoxAxioms(imports));
    }

    @Override
    public Set<OWLAxiom> getRBoxAxioms(Imports imports) {
        return withReadLock(() -> delegate.getRBoxAxioms(imports));
    }

    @Override
    public Stream<OWLAxiom> tboxAxioms(Imports imports) {
        return withReadLock(() -> delegate.tboxAxioms(imports));
    }

    @Override
    public Stream<OWLAxiom> aboxAxioms(Imports imports) {
        return withReadLock(() -> delegate.aboxAxioms(imports));
    }

    @Override
    public Stream<OWLAxiom> rboxAxioms(Imports imports) {
        return withReadLock(() -> delegate.rboxAxioms(imports));
    }

    @Override
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return withReadLock(delegate::getGeneralClassAxioms);
    }

    @Override
    public Set<OWLEntity> getSignature() {
        return withReadLock(delegate::getSignature);
    }

    @Override
    public Set<OWLEntity> getSignature(Imports imports) {
        return withReadLock(() -> delegate.getSignature(imports));
    }

    @Override
    public Stream<OWLClassAxiom> generalClassAxioms() {
        return withReadLock(delegate::generalClassAxioms);
    }

    @Override
    public Stream<OWLEntity> signature() {
        return withReadLock(delegate::signature);
    }

    @Override
    public Stream<OWLEntity> signature(Imports imports) {
        return withReadLock(() -> delegate.signature(imports));
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity) {
        return withBooleanReadLock(() -> delegate.isDeclared(owlEntity));
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity, Imports imports) {
        return withBooleanReadLock(() -> delegate.isDeclared(owlEntity, imports));
    }

    @Override
    public void saveOntology() throws OWLOntologyStorageException {
        callReadLock(delegate::saveOntology);
    }

    @Override
    public void saveOntology(IRI iri) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(iri));
    }

    @Override
    public void saveOntology(OutputStream outputStream) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(outputStream));
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat));
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, IRI iri)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, iri));
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, OutputStream outputStream)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, outputStream));
    }

    @Override
    public void saveOntology(OWLOntologyDocumentTarget owlOntologyDocumentTarget)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlOntologyDocumentTarget));
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat,
        OWLOntologyDocumentTarget owlOntologyDocumentTarget) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, owlOntologyDocumentTarget));
    }

    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        return withReadLock(delegate::getNestedClassExpressions);
    }

    @Override
    public void accept(OWLObjectVisitor owlObjectVisitor) {
        delegate.accept(owlObjectVisitor);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> owlObjectVisitorEx) {
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
    public String toString() {
        return withReadLock(delegate::toString);
    }

    @Override
    public int compareTo(@Nullable OWLObject o) {
        return withIntReadLock(() -> delegate.compareTo(o));
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
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
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        return withReadLock(delegate::getAnonymousIndividuals);
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        return withReadLock(delegate::getClassesInSignature);
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        return withReadLock(delegate::getObjectPropertiesInSignature);
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        return withReadLock(delegate::getDataPropertiesInSignature);
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        return withReadLock(delegate::getIndividualsInSignature);
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        return withReadLock(delegate::getDatatypesInSignature);
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        return withReadLock(delegate::getAnnotationPropertiesInSignature);
    }

    @Override
    public Set<OWLAxiom> getAxioms(Imports imports) {
        return withReadLock(() -> delegate.getAxioms(imports));
    }

    @Override
    public int getAxiomCount(Imports imports) {
        return withIntReadLock(() -> delegate.getAxiomCount(imports));
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(Imports imports) {
        return withReadLock(() -> delegate.getLogicalAxioms(imports));
    }

    @Override
    public int getLogicalAxiomCount(Imports imports) {
        return withIntReadLock(() -> delegate.getLogicalAxiomCount(imports));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, Imports imports) {
        return withReadLock(() -> delegate.getAxioms(axiomType, imports));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(AxiomType<T> axiomType, Imports imports) {
        return withReadLock(() -> delegate.axioms(axiomType, imports));
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, Imports imports) {
        return withIntReadLock(() -> delegate.getAxiomCount(axiomType, imports));
    }

    @Override
    public boolean containsAxiom(OWLAxiom owlAxiom, Imports imports,
        AxiomAnnotations axiomAnnotations) {
        return withBooleanReadLock(
            () -> delegate.containsAxiom(owlAxiom, imports, axiomAnnotations));
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom owlAxiom, Imports imports) {
        return withReadLock(() -> delegate.getAxiomsIgnoreAnnotations(owlAxiom, imports));
    }

    @Override
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom owlAxiom, Imports imports) {
        return withReadLock(() -> delegate.axiomsIgnoreAnnotations(owlAxiom, imports));
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlPrimitive, Imports imports) {
        return withReadLock(() -> delegate.getReferencingAxioms(owlPrimitive, imports));
    }

    @Override
    public Stream<OWLAxiom> referencingAxioms(OWLPrimitive owlPrimitive, Imports imports) {
        return withReadLock(() -> delegate.referencingAxioms(owlPrimitive, imports));
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass owlClass, Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlClass, imports));
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression, Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlObjectPropertyExpression, imports));
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty owlDataProperty, Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlDataProperty, imports));
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual owlIndividual, Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlIndividual, imports));
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty owlAnnotationProperty,
        Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlAnnotationProperty, imports));
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype owlDatatype, Imports imports) {
        return withReadLock(() -> delegate.getAxioms(owlDatatype, imports));
    }

    @Override
    public Set<OWLAxiom> getAxioms() {
        return withReadLock(delegate::getAxioms);
    }

    @Override
    public Stream<OWLAxiom> axioms() {
        // XXX investigate locking access to streams
        return withReadLock(delegate::axioms);
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        return withReadLock(delegate::getLogicalAxioms);
    }

    @Override
    public Stream<OWLLogicalAxiom> logicalAxioms() {
        return withReadLock(delegate::logicalAxioms);
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        return withReadLock(() -> delegate.getAxioms(axiomType));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(AxiomType<T> axiomType) {
        return withReadLock(() -> delegate.axioms(axiomType));
    }

    @Override
    public boolean equalAxioms(HasAxiomsByType o) {
        return withBooleanReadLock(() -> delegate.equalAxioms(o));
    }

    @Override
    public boolean containsAxiom(OWLAxiom owlAxiom) {
        return withBooleanReadLock(() -> delegate.containsAxiom(owlAxiom));
    }

    @Override
    public Set<OWLAxiom> getAxioms(boolean b) {
        return withReadLock(() -> delegate.getAxioms(b));
    }

    @Override
    public int getAxiomCount(boolean b) {
        return withIntReadLock(() -> delegate.getAxiomCount(b));
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(boolean b) {
        return withReadLock(() -> delegate.getLogicalAxioms(b));
    }

    @Override
    public int getLogicalAxiomCount(boolean b) {
        return withIntReadLock(() -> delegate.getLogicalAxiomCount(b));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, boolean b) {
        return withReadLock(() -> delegate.getAxioms(axiomType, b));
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, boolean b) {
        return withIntReadLock(() -> delegate.getAxiomCount(axiomType, b));
    }

    @Override
    public boolean containsAxiom(OWLAxiom owlAxiom, boolean b) {
        return withBooleanReadLock(() -> delegate.containsAxiom(owlAxiom, b));
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom owlAxiom, boolean b) {
        return withBooleanReadLock(() -> delegate.containsAxiomIgnoreAnnotations(owlAxiom, b));
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom owlAxiom, boolean b) {
        return withReadLock(() -> delegate.getAxiomsIgnoreAnnotations(owlAxiom, b));
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlPrimitive, boolean b) {
        return withReadLock(() -> delegate.getReferencingAxioms(owlPrimitive, b));
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass owlClass, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlClass, b));
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlObjectPropertyExpression, b));
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty owlDataProperty, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlDataProperty, b));
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual owlIndividual, boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlIndividual, b));
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty owlAnnotationProperty,
        boolean b) {
        return withReadLock(() -> delegate.getAxioms(owlAnnotationProperty, b));
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype owlDatatype, boolean b) {
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
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
        return withIntReadLock(() -> delegate.getAxiomCount(axiomType));
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom owlAxiom) {
        return withBooleanReadLock(() -> delegate.containsAxiomIgnoreAnnotations(owlAxiom));
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom owlAxiom) {
        return withReadLock(() -> delegate.getAxiomsIgnoreAnnotations(owlAxiom));
    }

    @Override
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom owlAxiom) {
        return withReadLock(() -> delegate.axiomsIgnoreAnnotations(owlAxiom));
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlPrimitive) {
        return withReadLock(() -> delegate.getReferencingAxioms(owlPrimitive));
    }

    @Override
    public Stream<OWLAxiom> referencingAxioms(OWLPrimitive owlPrimitive) {
        return withReadLock(() -> delegate.referencingAxioms(owlPrimitive));
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.getAxioms(owlClass));
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(() -> delegate.getAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getAxioms(owlDataProperty));
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getAxioms(owlIndividual));
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.getAxioms(owlAnnotationProperty));
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype owlDatatype) {
        return withReadLock(() -> delegate.getAxioms(owlDatatype));
    }

    @Override
    public Stream<OWLClassAxiom> axioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.axioms(owlClass));
    }

    @Override
    public Stream<OWLObjectPropertyAxiom> axioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(() -> delegate.axioms(owlObjectPropertyExpression));
    }

    @Override
    public Stream<OWLDataPropertyAxiom> axioms(OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.axioms(owlDataProperty));
    }

    @Override
    public Stream<OWLIndividualAxiom> axioms(OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.axioms(owlIndividual));
    }

    @Override
    public Stream<OWLAnnotationAxiom> axioms(OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.axioms(owlAnnotationProperty));
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> axioms(OWLDatatype owlDatatype) {
        return withReadLock(() -> delegate.axioms(owlDatatype));
    }

    @Override
    public Set<OWLClass> getClassesInSignature(Imports imports) {
        return withReadLock(() -> delegate.getClassesInSignature(imports));
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(Imports imports) {
        return withReadLock(() -> delegate.getObjectPropertiesInSignature(imports));
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(Imports imports) {
        return withReadLock(() -> delegate.getDataPropertiesInSignature(imports));
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(Imports imports) {
        return withReadLock(() -> delegate.getIndividualsInSignature(imports));
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(Imports imports) {
        return withReadLock(() -> delegate.getReferencedAnonymousIndividuals(imports));
    }

    @Override
    public Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals(Imports imports) {
        return withReadLock(() -> delegate.referencedAnonymousIndividuals(imports));
    }

    @Override
    public Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals() {
        return withReadLock(delegate::referencedAnonymousIndividuals);
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(Imports imports) {
        return withReadLock(() -> delegate.getDatatypesInSignature(imports));
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(Imports imports) {
        return withReadLock(() -> delegate.getAnnotationPropertiesInSignature(imports));
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(owlEntity, imports));
    }

    @Override
    public boolean containsEntityInSignature(IRI iri, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(iri, imports));
    }

    @Override
    public boolean containsClassInSignature(IRI iri, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsClassInSignature(iri, imports));
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsObjectPropertyInSignature(iri, imports));
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsDataPropertyInSignature(iri, imports));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri, Imports imports) {
        return withBooleanReadLock(
            () -> delegate.containsAnnotationPropertyInSignature(iri, imports));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsDatatypeInSignature(iri, imports));
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsIndividualInSignature(iri, imports));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri) {
        return withBooleanReadLock(() -> delegate.containsDatatypeInSignature(iri));
    }

    @Override
    public boolean containsEntityInSignature(IRI iri) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(iri));
    }

    @Override
    public boolean containsClassInSignature(IRI iri) {
        return withBooleanReadLock(() -> delegate.containsClassInSignature(iri));
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri) {
        return withBooleanReadLock(() -> delegate.containsObjectPropertyInSignature(iri));
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri) {
        return withBooleanReadLock(() -> delegate.containsDataPropertyInSignature(iri));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri) {
        return withBooleanReadLock(() -> delegate.containsAnnotationPropertyInSignature(iri));
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri) {
        return withBooleanReadLock(() -> delegate.containsIndividualInSignature(iri));
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, Imports imports) {
        return withReadLock(() -> delegate.getEntitiesInSignature(iri, imports));
    }

    @Override
    public Set<IRI> getPunnedIRIs(Imports imports) {
        return withReadLock(() -> delegate.getPunnedIRIs(imports));
    }

    @Override
    public boolean containsReference(OWLEntity owlEntity, Imports imports) {
        return withBooleanReadLock(() -> delegate.containsReference(owlEntity, imports));
    }

    @Override
    public boolean containsReference(OWLEntity owlEntity) {
        return withBooleanReadLock(() -> delegate.containsReference(owlEntity));
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri) {
        return withReadLock(() -> delegate.getEntitiesInSignature(iri));
    }

    @Override
    public Stream<OWLEntity> entitiesInSignature(IRI iri) {
        return withReadLock(() -> delegate.entitiesInSignature(iri));
    }

    @Override
    public Set<OWLClass> getClassesInSignature(boolean b) {
        return withReadLock(() -> delegate.getClassesInSignature(b));
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean b) {
        return withReadLock(() -> delegate.getObjectPropertiesInSignature(b));
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean b) {
        return withReadLock(() -> delegate.getDataPropertiesInSignature(b));
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean b) {
        return withReadLock(() -> delegate.getIndividualsInSignature(b));
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(boolean b) {
        return withReadLock(() -> delegate.getReferencedAnonymousIndividuals(b));
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(boolean b) {
        return withReadLock(() -> delegate.getDatatypesInSignature(b));
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(boolean b) {
        return withReadLock(() -> delegate.getAnnotationPropertiesInSignature(b));
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity, boolean b) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(owlEntity, b));
    }

    @Override
    public boolean containsEntityInSignature(IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsEntityInSignature(iri, b));
    }

    @Override
    public boolean containsClassInSignature(IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsClassInSignature(iri, b));
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsObjectPropertyInSignature(iri, b));
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsDataPropertyInSignature(iri, b));
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsAnnotationPropertyInSignature(iri, b));
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsDatatypeInSignature(iri, b));
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri, boolean b) {
        return withBooleanReadLock(() -> delegate.containsIndividualInSignature(iri, b));
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, boolean b) {
        return withReadLock(() -> delegate.getEntitiesInSignature(iri, b));
    }

    @Override
    public boolean containsReference(OWLEntity owlEntity, boolean b) {
        return withBooleanReadLock(() -> delegate.containsReference(owlEntity, b));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> aClass, OWLObject owlObject,
        Imports imports, Navigation navigation) {
        return withReadLock(() -> delegate.getAxioms(aClass, owlObject, imports, navigation));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> aClass, OWLObject owlObject,
        Imports imports, Navigation navigation) {
        return withReadLock(() -> delegate.axioms(aClass, owlObject, imports, navigation));
    }

    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(
        OWLAxiomSearchFilter owlAxiomSearchFilter, Object o, Imports imports) {
        return withReadLock(() -> delegate.filterAxioms(owlAxiomSearchFilter, o, imports));
    }

    @Override
    public boolean contains(OWLAxiomSearchFilter owlAxiomSearchFilter, Object o, Imports imports) {
        return withBooleanReadLock(() -> delegate.contains(owlAxiomSearchFilter, o, imports));
    }

    @Override
    public boolean contains(OWLAxiomSearchFilter owlAxiomSearchFilter, Object o) {
        return withBooleanReadLock(() -> delegate.contains(owlAxiomSearchFilter, o));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> aClass,
        Class<? extends OWLObject> aClass1, OWLObject owlObject, Imports imports,
        Navigation navigation) {
        return withReadLock(
            () -> delegate.getAxioms(aClass, aClass1, owlObject, imports, navigation));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> aClass,
        Class<? extends OWLObject> aClass1, OWLObject owlObject, Imports imports,
        Navigation navigation) {
        return withReadLock(() -> delegate.axioms(aClass, aClass1, owlObject, imports, navigation));
    }

    @Override
    public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.getSubAnnotationPropertyOfAxioms(owlAnnotationProperty));
    }

    @Override
    public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(
            () -> delegate.getAnnotationPropertyDomainAxioms(owlAnnotationProperty));
    }

    @Override
    public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.getAnnotationPropertyRangeAxioms(owlAnnotationProperty));
    }

    @Override
    public Stream<OWLAnnotationPropertyDomainAxiom> annotationPropertyDomainAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.annotationPropertyDomainAxioms(owlAnnotationProperty));
    }

    @Override
    public Stream<OWLAnnotationPropertyRangeAxiom> annotationPropertyRangeAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.annotationPropertyRangeAxioms(owlAnnotationProperty));
    }

    @Override
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity owlEntity) {
        return withReadLock(() -> delegate.getDeclarationAxioms(owlEntity));
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
        OWLAnnotationSubject owlAnnotationSubject) {
        return withReadLock(() -> delegate.getAnnotationAssertionAxioms(owlAnnotationSubject));
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass owlClass) {
        return withReadLock(() -> delegate.getSubClassAxiomsForSubClass(owlClass));
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass owlClass) {
        return withReadLock(() -> delegate.getSubClassAxiomsForSuperClass(owlClass));
    }

    @Override
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.getEquivalentClassesAxioms(owlClass));
    }

    @Override
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.getDisjointClassesAxioms(owlClass));
    }

    @Override
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.getDisjointUnionAxioms(owlClass));
    }

    @Override
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.getHasKeyAxioms(owlClass));
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectSubPropertyAxiomsForSubProperty(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectSubPropertyAxiomsForSuperProperty(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectPropertyDomainAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getObjectPropertyRangeAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getInverseObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getEquivalentObjectPropertiesAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getDisjointObjectPropertiesAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getFunctionalObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getInverseFunctionalObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getSymmetricObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getAsymmetricObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getReflexiveObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getIrreflexiveObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        return withReadLock(
            () -> delegate.getTransitiveObjectPropertyAxioms(owlObjectPropertyExpression));
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(
        OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDataSubPropertyAxiomsForSubProperty(owlDataProperty));
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
        OWLDataPropertyExpression owlDataPropertyExpression) {
        return withReadLock(
            () -> delegate.getDataSubPropertyAxiomsForSuperProperty(owlDataPropertyExpression));
    }

    @Override
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
        OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDataPropertyDomainAxioms(owlDataProperty));
    }

    @Override
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
        OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDataPropertyRangeAxioms(owlDataProperty));
    }

    @Override
    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(
        OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getEquivalentDataPropertiesAxioms(owlDataProperty));
    }

    @Override
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
        OWLDataProperty owlDataProperty) {
        return withReadLock(() -> delegate.getDisjointDataPropertiesAxioms(owlDataProperty));
    }

    @Override
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
        OWLDataPropertyExpression owlDataPropertyExpression) {
        return withReadLock(
            () -> delegate.getFunctionalDataPropertyAxioms(owlDataPropertyExpression));
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getClassAssertionAxioms(owlIndividual));
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
        OWLClassExpression owlClassExpression) {
        return withReadLock(() -> delegate.getClassAssertionAxioms(owlClassExpression));
    }

    @Override
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
        OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getDataPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(
        OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getObjectPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
        OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getNegativeObjectPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(
        OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getNegativeDataPropertyAssertionAxioms(owlIndividual));
    }

    @Override
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getSameIndividualAxioms(owlIndividual));
    }

    @Override
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
        OWLIndividual owlIndividual) {
        return withReadLock(() -> delegate.getDifferentIndividualAxioms(owlIndividual));
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype owlDatatype) {
        return withReadLock(() -> delegate.getDatatypeDefinitions(owlDatatype));
    }

    @Override
    public ChangeApplied applyChange(OWLOntologyChange owlOntologyChange) {
        return withWriteLock(() -> getMutableOntology().applyChange(owlOntologyChange));
    }

    @Override
    public ChangeDetails applyChangesAndGetDetails(List<? extends OWLOntologyChange> list) {
        return withWriteLock(() -> getMutableOntology().applyChangesAndGetDetails(list));
    }

    @Override
    public ChangeApplied addAxiom(OWLAxiom owlAxiom) {
        return withWriteLock(() -> getMutableOntology().addAxiom(owlAxiom));
    }

    @Override
    public ChangeApplied addAxioms(Collection<? extends OWLAxiom> set) {
        return withWriteLock(() -> getMutableOntology().addAxioms(set));
    }

    @Override
    public ChangeApplied addAxioms(OWLAxiom... set) {
        return withWriteLock(() -> getMutableOntology().addAxioms(set));
    }

    @Override
    public ChangeApplied add(OWLAxiom owlAxiom) {
        return withWriteLock(() -> getMutableOntology().add(owlAxiom));
    }

    @Override
    public ChangeApplied add(Collection<? extends OWLAxiom> set) {
        return withWriteLock(() -> getMutableOntology().add(set));
    }

    @Override
    public ChangeApplied add(OWLAxiom... set) {
        return withWriteLock(() -> getMutableOntology().add(set));
    }

    private OWLMutableOntology getMutableOntology() {
        return (OWLMutableOntology) delegate;
    }

    @Override
    public Stream<OWLImportsDeclaration> importsDeclarations() {
        return withReadLock(delegate::importsDeclarations);
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key,
        Imports includeImportsClosure) {
        return withReadLock(() -> delegate.axioms(filter, key, includeImportsClosure));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key) {
        return withReadLock(() -> delegate.axioms(filter, key));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> type,
        Class<? extends OWLObject> explicitClass, OWLObject entity, Navigation forSubPosition) {
        return withReadLock(() -> delegate.axioms(type, explicitClass, entity, forSubPosition));
    }

    @Override
    public Stream<OWLSubAnnotationPropertyOfAxiom> subAnnotationPropertyOfAxioms(
        OWLAnnotationProperty subProperty) {
        return withReadLock(() -> delegate.subAnnotationPropertyOfAxioms(subProperty));
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> datatypeDefinitions(OWLDatatype datatype) {
        return withReadLock(() -> delegate.datatypeDefinitions(datatype));
    }

    @Override
    public ChangeApplied removeAxiom(OWLAxiom axiom) {
        return withWriteLock(() -> delegate.removeAxiom(axiom));
    }

    @Override
    public ChangeApplied removeAxioms(Collection<? extends OWLAxiom> axioms) {
        return withWriteLock(() -> delegate.removeAxioms(axioms));
    }

    @Override
    public ChangeApplied removeAxioms(OWLAxiom... axioms) {
        return withWriteLock(() -> delegate.removeAxioms(axioms));
    }

    @Override
    public ChangeApplied remove(OWLAxiom axiom) {
        return withWriteLock(() -> delegate.remove(axiom));
    }

    @Override
    public ChangeApplied remove(Collection<? extends OWLAxiom> axioms) {
        return withWriteLock(() -> delegate.remove(axioms));
    }

    @Override
    public ChangeApplied remove(OWLAxiom... axioms) {
        return withWriteLock(() -> delegate.remove(axioms));
    }

    @Override
    public ChangeApplied applyDirectChange(OWLOntologyChange change) {
        return withWriteLock(() -> delegate.applyDirectChange(change));
    }

    @Override
    public Stream<OWLDisjointObjectPropertiesAxiom> disjointObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.disjointObjectPropertiesAxioms(property));
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        return withReadLock(delegate::objectPropertiesInSignature);
    }

    @Override
    public Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(
        OWLAnnotationSubject entity) {
        return withReadLock(() -> delegate.annotationAssertionAxioms(entity));
    }

    @Override
    public Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(
        OWLAnnotationSubject entity, Imports imports) {
        return withReadLock(() -> delegate.annotationAssertionAxioms(entity, imports));
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature() {
        return withReadLock(delegate::annotationPropertiesInSignature);
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature(Imports imports) {
        return withReadLock(() -> delegate.annotationPropertiesInSignature(imports));
    }

    @Override
    public Stream<OWLAnnotation> annotations() {
        return withReadLock(delegate::annotations);
    }

    @Override
    public List<OWLAnnotation> annotationsAsList() {
        return withReadLock(delegate::annotationsAsList);
    }

    @Override
    public Stream<OWLAnnotation> annotations(OWLAnnotationProperty p) {
        return withReadLock(() -> delegate.annotations(p));
    }

    @Override
    public Stream<OWLAnnotation> annotations(Predicate<OWLAnnotation> p) {
        return withReadLock(() -> delegate.annotations(p));
    }

    @Override
    public Stream<OWLAnonymousIndividual> anonymousIndividuals() {
        return withReadLock(delegate::anonymousIndividuals);
    }

    @Override
    public Stream<OWLAsymmetricObjectPropertyAxiom> asymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.asymmetricObjectPropertyAxioms(property));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> type, OWLObject entity,
        Navigation forSubPosition) {
        return withReadLock(() -> delegate.axioms(type, entity, forSubPosition));
    }

    @Override
    public Stream<OWLAxiom> axioms(Imports imports) {
        return withReadLock(() -> delegate.axioms(imports));
    }

    @Override
    public Stream<OWLAnnotationAxiom> axioms(OWLAnnotationProperty property, Imports imports) {
        return withReadLock(() -> delegate.axioms(property, imports));
    }

    @Override
    public Stream<OWLClassAxiom> axioms(OWLClass cls, Imports imports) {
        return withReadLock(() -> delegate.axioms(cls, imports));
    }

    @Override
    public Stream<OWLDataPropertyAxiom> axioms(OWLDataProperty property, Imports imports) {
        return withReadLock(() -> delegate.axioms(property, imports));
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> axioms(OWLDatatype datatype, Imports imports) {
        return withReadLock(() -> delegate.axioms(datatype, imports));
    }

    @Override
    public Stream<OWLIndividualAxiom> axioms(OWLIndividual individual, Imports imports) {
        return withReadLock(() -> delegate.axioms(individual, imports));
    }

    @Override
    public Stream<OWLObjectPropertyAxiom> axioms(OWLObjectPropertyExpression property,
        Imports imports) {
        return withReadLock(() -> delegate.axioms(property, imports));
    }

    @Override
    public Stream<OWLClassAssertionAxiom> classAssertionAxioms(OWLClassExpression ce) {
        return withReadLock(() -> delegate.classAssertionAxioms(ce));
    }

    @Override
    public Stream<OWLClassAssertionAxiom> classAssertionAxioms(OWLIndividual individual) {
        return withReadLock(() -> delegate.classAssertionAxioms(individual));
    }

    @Override
    public Stream<OWLClass> classesInSignature() {
        return withReadLock(delegate::classesInSignature);
    }

    @Override
    public Stream<OWLClass> classesInSignature(Imports imports) {
        return withReadLock(() -> delegate.classesInSignature(imports));
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature() {
        return withReadLock(delegate::dataPropertiesInSignature);
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature(Imports imports) {
        return withReadLock(() -> delegate.dataPropertiesInSignature(imports));
    }

    @Override
    public Stream<OWLDataPropertyAssertionAxiom> dataPropertyAssertionAxioms(
        OWLIndividual individual) {
        return withReadLock(() -> delegate.dataPropertyAssertionAxioms(individual));
    }

    @Override
    public Stream<OWLDataPropertyDomainAxiom> dataPropertyDomainAxioms(OWLDataProperty property) {
        return withReadLock(() -> delegate.dataPropertyDomainAxioms(property));
    }

    @Override
    public Stream<OWLDataPropertyRangeAxiom> dataPropertyRangeAxioms(OWLDataProperty property) {
        return withReadLock(() -> delegate.dataPropertyRangeAxioms(property));
    }

    @Override
    public Stream<OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsForSubProperty(
        OWLDataProperty subProperty) {
        return withReadLock(() -> delegate.dataSubPropertyAxiomsForSubProperty(subProperty));
    }

    @Override
    public Stream<OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsForSuperProperty(
        OWLDataPropertyExpression superProperty) {
        return withReadLock(() -> delegate.dataSubPropertyAxiomsForSuperProperty(superProperty));
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        return withReadLock(delegate::datatypesInSignature);
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature(Imports imports) {
        return withReadLock(() -> delegate.datatypesInSignature(imports));
    }

    @Override
    public Stream<OWLDeclarationAxiom> declarationAxioms(OWLEntity subject) {
        return withReadLock(() -> delegate.declarationAxioms(subject));
    }

    @Override
    public Stream<OWLDifferentIndividualsAxiom> differentIndividualAxioms(
        OWLIndividual individual) {
        return withReadLock(() -> delegate.differentIndividualAxioms(individual));
    }

    @Override
    public Stream<OWLDisjointClassesAxiom> disjointClassesAxioms(OWLClass cls) {
        return withReadLock(() -> delegate.disjointClassesAxioms(cls));
    }

    @Override
    public Stream<OWLDisjointDataPropertiesAxiom> disjointDataPropertiesAxioms(
        OWLDataProperty property) {
        return withReadLock(() -> delegate.disjointDataPropertiesAxioms(property));
    }

    @Override
    public Stream<OWLDisjointUnionAxiom> disjointUnionAxioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.disjointUnionAxioms(owlClass));
    }

    @Override
    public Stream<OWLEntity> entitiesInSignature(IRI iri, Imports imports) {
        return withReadLock(() -> delegate.entitiesInSignature(iri, imports));
    }

    @Override
    public Stream<OWLEquivalentClassesAxiom> equivalentClassesAxioms(OWLClass cls) {
        return withReadLock(() -> delegate.equivalentClassesAxioms(cls));
    }

    @Override
    public Stream<OWLEquivalentDataPropertiesAxiom> equivalentDataPropertiesAxioms(
        OWLDataProperty property) {
        return withReadLock(() -> delegate.equivalentDataPropertiesAxioms(property));
    }

    @Override
    public Stream<OWLEquivalentObjectPropertiesAxiom> equivalentObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.equivalentObjectPropertiesAxioms(property));
    }

    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(OWLAxiomSearchFilter filter,
        Object key) {
        return withReadLock(() -> delegate.filterAxioms(filter, key));
    }

    @Override
    public Stream<OWLFunctionalDataPropertyAxiom> functionalDataPropertyAxioms(
        OWLDataPropertyExpression property) {
        return withReadLock(() -> delegate.functionalDataPropertyAxioms(property));
    }

    @Override
    public Stream<OWLFunctionalObjectPropertyAxiom> functionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.functionalObjectPropertyAxioms(property));
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
        OWLAnnotationSubject entity, Imports imports) {
        return withReadLock(() -> delegate.getAnnotationAssertionAxioms(entity, imports));
    }

    @Override
    public Set<OWLAnnotation> getAnnotations(OWLAnnotationProperty annotationProperty) {
        return withReadLock(() -> delegate.getAnnotations(annotationProperty));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> type,
        Class<? extends OWLObject> explicitClass, OWLObject entity, Navigation forSubPosition) {
        return withReadLock(() -> delegate.getAxioms(type, explicitClass, entity, forSubPosition));
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, OWLObject entity,
        Navigation forSubPosition) {
        return withReadLock(() -> delegate.getAxioms(type, entity, forSubPosition));
    }

    @Override
    @Nullable
    public OWLDocumentFormat getFormat() {
        return withReadLock(delegate::getFormat);
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals() {
        return withReadLock(delegate::getReferencedAnonymousIndividuals);
    }

    @Override
    public Stream<OWLHasKeyAxiom> hasKeyAxioms(OWLClass cls) {
        return withReadLock(() -> delegate.hasKeyAxioms(cls));
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature() {
        return withReadLock(delegate::individualsInSignature);
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature(Imports imports) {
        return withReadLock(() -> delegate.individualsInSignature(imports));
    }

    @Override
    public Stream<OWLInverseFunctionalObjectPropertyAxiom> inverseFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.inverseFunctionalObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLInverseObjectPropertiesAxiom> inverseObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.inverseObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLIrreflexiveObjectPropertyAxiom> irreflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.irreflexiveObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLLogicalAxiom> logicalAxioms(Imports imports) {
        return withReadLock(() -> delegate.logicalAxioms(imports));
    }

    @Override
    public Stream<OWLNegativeDataPropertyAssertionAxiom> negativeDataPropertyAssertionAxioms(
        OWLIndividual individual) {
        return withReadLock(() -> delegate.negativeDataPropertyAssertionAxioms(individual));
    }

    @Override
    public Stream<OWLNegativeObjectPropertyAssertionAxiom> negativeObjectPropertyAssertionAxioms(
        OWLIndividual individual) {
        return withReadLock(() -> delegate.negativeObjectPropertyAssertionAxioms(individual));
    }

    @Override
    public Stream<OWLClassExpression> nestedClassExpressions() {
        return withReadLock(delegate::nestedClassExpressions);
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature(Imports imports) {
        return withReadLock(() -> delegate.objectPropertiesInSignature(imports));
    }

    @Override
    public Stream<OWLObjectPropertyAssertionAxiom> objectPropertyAssertionAxioms(
        OWLIndividual individual) {
        return withReadLock(() -> delegate.objectPropertyAssertionAxioms(individual));
    }

    @Override
    public Stream<OWLObjectPropertyDomainAxiom> objectPropertyDomainAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.objectPropertyDomainAxioms(property));
    }

    @Override
    public Stream<OWLObjectPropertyRangeAxiom> objectPropertyRangeAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.objectPropertyRangeAxioms(property));
    }

    @Override
    public Stream<OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsForSubProperty(
        OWLObjectPropertyExpression subProperty) {
        return withReadLock(() -> delegate.objectSubPropertyAxiomsForSubProperty(subProperty));
    }

    @Override
    public Stream<OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsForSuperProperty(
        OWLObjectPropertyExpression superProperty) {
        return withReadLock(() -> delegate.objectSubPropertyAxiomsForSuperProperty(superProperty));
    }

    @Override
    public Stream<OWLReflexiveObjectPropertyAxiom> reflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.reflexiveObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLSameIndividualAxiom> sameIndividualAxioms(OWLIndividual individual) {
        return withReadLock(() -> delegate.sameIndividualAxioms(individual));
    }

    @Override
    public Stream<OWLSubClassOfAxiom> subClassAxiomsForSubClass(OWLClass cls) {
        return withReadLock(() -> delegate.subClassAxiomsForSubClass(cls));
    }

    @Override
    public Stream<OWLSubClassOfAxiom> subClassAxiomsForSuperClass(OWLClass cls) {
        return withReadLock(() -> delegate.subClassAxiomsForSuperClass(cls));
    }

    @Override
    public Stream<OWLSymmetricObjectPropertyAxiom> symmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.symmetricObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLTransitiveObjectPropertyAxiom> transitiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.transitiveObjectPropertyAxioms(property));
    }
}
