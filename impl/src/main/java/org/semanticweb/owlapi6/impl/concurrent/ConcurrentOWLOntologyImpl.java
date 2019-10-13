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
package org.semanticweb.owlapi6.impl.concurrent;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;

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

import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.impl.HasTrimToSize;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 08/04/15
 */

import org.semanticweb.owlapi6.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.ChangeReport;
import org.semanticweb.owlapi6.model.EntityType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLogicalAxiom;
import org.semanticweb.owlapi6.model.OWLMutableOntology;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNamedObjectVisitor;
import org.semanticweb.owlapi6.model.OWLNamedObjectVisitorEx;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLObjectVisitor;
import org.semanticweb.owlapi6.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyChange;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.OWLOntologyManager;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLPrimitive;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OntologyConfigurator;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.model.parameters.AxiomAnnotations;
import org.semanticweb.owlapi6.model.parameters.ChangeApplied;
import org.semanticweb.owlapi6.model.parameters.Imports;
import org.semanticweb.owlapi6.model.parameters.Navigation;
import org.semanticweb.owlapi6.utilities.OWLAxiomSearchFilter;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 03/04/15
 */
public class ConcurrentOWLOntologyImpl implements OWLMutableOntology, HasTrimToSize {

    private final OWLOntology delegate;
    private ReadWriteLock lock;

    /**
     * Constructs a ConcurrentOWLOntology that provides concurrent access to a
     * delegate {@link OWLOntology}.
     * 
     * @param delegate
     *        The delegate {@link OWLOntology}.
     * @param readWriteLock
     *        The {@link java.util.concurrent.locks.ReadWriteLock} that will
     *        provide the locking.
     * @throws java.lang.NullPointerException
     *         if any parameters are {@code null}.
     */
    @Inject
    public ConcurrentOWLOntologyImpl(OWLOntology delegate, ReadWriteLock readWriteLock) {
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
            ((HasTrimToSize) delegate).trimToSize();
        }
    }

    @Override
    public OntologyConfigurator getOntologyConfigurator() {
        return withReadLock(delegate::getOntologyConfigurator);
    }

    @Override
    public void setOntologyConfigurator(OntologyConfigurator configurator) {
        callWriteLock(() -> delegate.setOntologyConfigurator(configurator));
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
    public Stream<IRI> directImportsDocuments() {
        return withReadLock(delegate::directImportsDocuments);
    }

    @Override
    public Stream<OWLOntology> directImports() {
        return withReadLock(delegate::directImports);
    }

    @Override
    public Stream<OWLOntology> imports() {
        return withReadLock(delegate::imports);
    }

    @Override
    public Stream<OWLOntology> importsClosure() {
        return withReadLock(delegate::importsClosure);
    }

    @Override
    public boolean isEmpty() {
        return withBooleanReadLock(delegate::isEmpty);
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
    public void saveOntology(OWLDocumentFormat owlDocumentFormat) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat));
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, IRI iri) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, iri));
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, OutputStream outputStream)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, outputStream));
    }

    @Override
    public void saveOntology(OWLOntologyDocumentTarget owlOntologyDocumentTarget) throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlOntologyDocumentTarget));
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, OWLOntologyDocumentTarget owlOntologyDocumentTarget)
        throws OWLOntologyStorageException {
        callReadLock(() -> delegate.saveOntology(owlDocumentFormat, owlOntologyDocumentTarget));
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
    public String toSyntax(OWLDocumentFormat f, PrefixManager pm) {
        return withReadLock(() -> delegate.toSyntax(f, pm));
    }

    @Override
    public String toSyntax(OWLDocumentFormat f) {
        return withReadLock(() -> delegate.toSyntax(f));
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
    public boolean containsEntitiesOfTypeInSignature(EntityType<?> type, Imports includeImportsClosure) {
        return withBooleanReadLock(() -> delegate.containsEntitiesOfTypeInSignature(type, includeImportsClosure));
    }

    @Override
    public int getAxiomCount(Imports imports) {
        return withIntReadLock(() -> delegate.getAxiomCount(imports));
    }

    @Override
    public int getLogicalAxiomCount(Imports imports) {
        return withIntReadLock(() -> delegate.getLogicalAxiomCount(imports));
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
    public boolean containsAxiom(OWLAxiom owlAxiom, Imports imports, AxiomAnnotations axiomAnnotations) {
        return withBooleanReadLock(() -> delegate.containsAxiom(owlAxiom, imports, axiomAnnotations));
    }

    @Override
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom owlAxiom, Imports imports) {
        return withReadLock(() -> delegate.axiomsIgnoreAnnotations(owlAxiom, imports));
    }

    @Override
    public Stream<OWLAxiom> referencingAxioms(OWLPrimitive owlPrimitive, Imports imports) {
        return withReadLock(() -> delegate.referencingAxioms(owlPrimitive, imports));
    }

    @Override
    public Stream<OWLAxiom> axioms() {
        // XXX investigate locking access to streams
        return withReadLock(delegate::axioms);
    }

    @Override
    public Stream<OWLLogicalAxiom> logicalAxioms() {
        return withReadLock(delegate::logicalAxioms);
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(AxiomType<T> axiomType) {
        return withReadLock(() -> delegate.axioms(axiomType));
    }

    @Override
    public boolean containsAxiom(OWLAxiom owlAxiom) {
        return withBooleanReadLock(() -> delegate.containsAxiom(owlAxiom));
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
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom owlAxiom) {
        return withReadLock(() -> delegate.axiomsIgnoreAnnotations(owlAxiom));
    }

    @Override
    public Stream<OWLAxiom> referencingAxioms(OWLPrimitive owlPrimitive) {
        return withReadLock(() -> delegate.referencingAxioms(owlPrimitive));
    }

    @Override
    public Stream<OWLClassAxiom> axioms(OWLClass owlClass) {
        return withReadLock(() -> delegate.axioms(owlClass));
    }

    @Override
    public Stream<OWLObjectPropertyAxiom> axioms(OWLObjectPropertyExpression owlObjectPropertyExpression) {
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
    public Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals(Imports imports) {
        return withReadLock(() -> delegate.referencedAnonymousIndividuals(imports));
    }

    @Override
    public Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals() {
        return withReadLock(delegate::referencedAnonymousIndividuals);
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
        return withBooleanReadLock(() -> delegate.containsAnnotationPropertyInSignature(iri, imports));
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
    public Set<IRI> getPunnedIRIs(Imports imports) {
        return withReadLock(() -> delegate.getPunnedIRIs(imports));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> aClass, OWLObject owlObject, Imports imports,
        Navigation navigation) {
        return withReadLock(() -> delegate.axioms(aClass, owlObject, imports, navigation));
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
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> aClass, Class<? extends OWLObject> aClass1,
        OWLObject owlObject, Imports imports, Navigation navigation) {
        return withReadLock(() -> delegate.axioms(aClass, aClass1, owlObject, imports, navigation));
    }

    @Override
    public Stream<OWLAnnotationPropertyDomainAxiom>
        annotationPropertyDomainAxioms(OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.annotationPropertyDomainAxioms(owlAnnotationProperty));
    }

    @Override
    public Stream<OWLAnnotationPropertyRangeAxiom>
        annotationPropertyRangeAxioms(OWLAnnotationProperty owlAnnotationProperty) {
        return withReadLock(() -> delegate.annotationPropertyRangeAxioms(owlAnnotationProperty));
    }

    @Override
    public ChangeReport applyChange(OWLOntologyChange owlOntologyChange) {
        return withWriteLock(() -> getMutableOntology().applyChange(owlOntologyChange));
    }

    @Override
    public ChangeReport applyChanges(Collection<? extends OWLOntologyChange> list) {
        return withWriteLock(() -> getMutableOntology().applyChanges(list));
    }

    @Override
    public ChangeReport addAxiom(OWLAxiom owlAxiom) {
        return withWriteLock(() -> getMutableOntology().addAxiom(owlAxiom));
    }

    @Override
    public ChangeReport addAxioms(Collection<? extends OWLAxiom> set) {
        return withWriteLock(() -> getMutableOntology().addAxioms(set));
    }

    @Override
    public ChangeReport addAxioms(OWLAxiom... set) {
        return withWriteLock(() -> getMutableOntology().addAxioms(set));
    }

    @Override
    public ChangeReport add(OWLAxiom owlAxiom) {
        return withWriteLock(() -> getMutableOntology().add(owlAxiom));
    }

    @Override
    public ChangeReport add(Collection<? extends OWLAxiom> set) {
        return withWriteLock(() -> getMutableOntology().add(set));
    }

    @Override
    public ChangeReport add(OWLAxiom... set) {
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
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> type, Class<? extends OWLObject> explicitClass,
        OWLObject entity, Navigation forSubPosition) {
        return withReadLock(() -> delegate.axioms(type, explicitClass, entity, forSubPosition));
    }

    @Override
    public Stream<OWLSubAnnotationPropertyOfAxiom> subAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        return withReadLock(() -> delegate.subAnnotationPropertyOfAxioms(subProperty));
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> datatypeDefinitions(OWLDatatype datatype) {
        return withReadLock(() -> delegate.datatypeDefinitions(datatype));
    }

    @Override
    public ChangeReport removeAxiom(OWLAxiom axiom) {
        return withWriteLock(() -> delegate.removeAxiom(axiom));
    }

    @Override
    public ChangeReport removeAxioms(Collection<? extends OWLAxiom> axioms) {
        return withWriteLock(() -> delegate.removeAxioms(axioms));
    }

    @Override
    public ChangeReport removeAxioms(OWLAxiom... axioms) {
        return withWriteLock(() -> delegate.removeAxioms(axioms));
    }

    @Override
    public ChangeReport remove(OWLAxiom axiom) {
        return withWriteLock(() -> delegate.remove(axiom));
    }

    @Override
    public ChangeReport remove(Collection<? extends OWLAxiom> axioms) {
        return withWriteLock(() -> delegate.remove(axioms));
    }

    @Override
    public ChangeReport remove(OWLAxiom... axioms) {
        return withWriteLock(() -> delegate.remove(axioms));
    }

    @Override
    public Stream<OWLDisjointObjectPropertiesAxiom>
        disjointObjectPropertiesAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.disjointObjectPropertiesAxioms(property));
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        return withReadLock(delegate::objectPropertiesInSignature);
    }

    @Override
    public Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(OWLAnnotationSubject entity) {
        return withReadLock(() -> delegate.annotationAssertionAxioms(entity));
    }

    @Override
    public Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(OWLAnnotationSubject entity, Imports imports) {
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
    public Stream<OWLAsymmetricObjectPropertyAxiom>
        asymmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.asymmetricObjectPropertyAxioms(property));
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> type, OWLObject entity, Navigation forSubPosition) {
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
    public Stream<OWLObjectPropertyAxiom> axioms(OWLObjectPropertyExpression property, Imports imports) {
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
    public Stream<OWLDataPropertyAssertionAxiom> dataPropertyAssertionAxioms(OWLIndividual individual) {
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
    public Stream<OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsForSubProperty(OWLDataProperty subProperty) {
        return withReadLock(() -> delegate.dataSubPropertyAxiomsForSubProperty(subProperty));
    }

    @Override
    public Stream<OWLSubDataPropertyOfAxiom>
        dataSubPropertyAxiomsForSuperProperty(OWLDataPropertyExpression superProperty) {
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
    public Stream<OWLDifferentIndividualsAxiom> differentIndividualAxioms(OWLIndividual individual) {
        return withReadLock(() -> delegate.differentIndividualAxioms(individual));
    }

    @Override
    public Stream<OWLDisjointClassesAxiom> disjointClassesAxioms(OWLClass cls) {
        return withReadLock(() -> delegate.disjointClassesAxioms(cls));
    }

    @Override
    public Stream<OWLDisjointDataPropertiesAxiom> disjointDataPropertiesAxioms(OWLDataProperty property) {
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
    public Stream<OWLEntity> entitiesInSignature(IRI iri) {
        return withReadLock(() -> delegate.entitiesInSignature(iri));
    }

    @Override
    public Stream<OWLEquivalentClassesAxiom> equivalentClassesAxioms(OWLClass cls) {
        return withReadLock(() -> delegate.equivalentClassesAxioms(cls));
    }

    @Override
    public Stream<OWLEquivalentDataPropertiesAxiom> equivalentDataPropertiesAxioms(OWLDataProperty property) {
        return withReadLock(() -> delegate.equivalentDataPropertiesAxioms(property));
    }

    @Override
    public Stream<OWLEquivalentObjectPropertiesAxiom>
        equivalentObjectPropertiesAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.equivalentObjectPropertiesAxioms(property));
    }

    @Override
    public Stream<OWLFunctionalDataPropertyAxiom> functionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        return withReadLock(() -> delegate.functionalDataPropertyAxioms(property));
    }

    @Override
    public Stream<OWLFunctionalObjectPropertyAxiom>
        functionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.functionalObjectPropertyAxioms(property));
    }

    @Override
    @Nullable
    public OWLDocumentFormat getFormat() {
        return withReadLock(delegate::getFormat);
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
    public Stream<OWLInverseFunctionalObjectPropertyAxiom>
        inverseFunctionalObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.inverseFunctionalObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLInverseObjectPropertiesAxiom> inverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.inverseObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLIrreflexiveObjectPropertyAxiom>
        irreflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.irreflexiveObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLLogicalAxiom> logicalAxioms(Imports imports) {
        return withReadLock(() -> delegate.logicalAxioms(imports));
    }

    @Override
    public Stream<OWLNegativeDataPropertyAssertionAxiom> negativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        return withReadLock(() -> delegate.negativeDataPropertyAssertionAxioms(individual));
    }

    @Override
    public Stream<OWLNegativeObjectPropertyAssertionAxiom>
        negativeObjectPropertyAssertionAxioms(OWLIndividual individual) {
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
    public Stream<OWLObjectPropertyAssertionAxiom> objectPropertyAssertionAxioms(OWLIndividual individual) {
        return withReadLock(() -> delegate.objectPropertyAssertionAxioms(individual));
    }

    @Override
    public Stream<OWLObjectPropertyDomainAxiom> objectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.objectPropertyDomainAxioms(property));
    }

    @Override
    public Stream<OWLObjectPropertyRangeAxiom> objectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.objectPropertyRangeAxioms(property));
    }

    @Override
    public Stream<OWLSubObjectPropertyOfAxiom>
        objectSubPropertyAxiomsForSubProperty(OWLObjectPropertyExpression subProperty) {
        return withReadLock(() -> delegate.objectSubPropertyAxiomsForSubProperty(subProperty));
    }

    @Override
    public Stream<OWLSubObjectPropertyOfAxiom>
        objectSubPropertyAxiomsForSuperProperty(OWLObjectPropertyExpression superProperty) {
        return withReadLock(() -> delegate.objectSubPropertyAxiomsForSuperProperty(superProperty));
    }

    @Override
    public Stream<OWLReflexiveObjectPropertyAxiom> reflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
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
    public Stream<OWLSymmetricObjectPropertyAxiom> symmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.symmetricObjectPropertyAxioms(property));
    }

    @Override
    public Stream<OWLTransitiveObjectPropertyAxiom>
        transitiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return withReadLock(() -> delegate.transitiveObjectPropertyAxioms(property));
    }

    @Override
    public PrefixManager getPrefixManager() {
        return withReadLock(delegate::getPrefixManager);
    }

    @Override
    public void setPrefixManager(PrefixManager prefixManager) {
        callWriteLock(() -> delegate.setPrefixManager(prefixManager));
    }

    @Override
    public String toFunctionalSyntax(PrefixManager pm) {
        return toSyntax(new FunctionalSyntaxDocumentFormat(), pm);
    }

    @Override
    public String toManchesterSyntax(PrefixManager pm) {
        return toSyntax(new ManchesterSyntaxDocumentFormat(), pm);
    }

    @Override
    public String toFunctionalSyntax() {
        return toSyntax(new FunctionalSyntaxDocumentFormat());
    }

    @Override
    public String toManchesterSyntax() {
        return toSyntax(new ManchesterSyntaxDocumentFormat());
    }

    @Override
    public ChangeApplied enactChange(OWLOntologyChange change) {
        return withWriteLock(() -> delegate.enactChange(change));
    }
}
