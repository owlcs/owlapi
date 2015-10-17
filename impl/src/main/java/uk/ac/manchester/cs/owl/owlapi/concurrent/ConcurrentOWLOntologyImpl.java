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
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
@SuppressWarnings({ "deprecation" })
public class ConcurrentOWLOntologyImpl implements OWLMutableOntology, HasTrimToSize {

    private final @Nonnull OWLOntology delegate;
    private final @Nonnull Lock readLock;
    private final @Nonnull Lock writeLock;

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
        verifyNotNull(readWriteLock);
        readLock = readWriteLock.readLock();
        writeLock = readWriteLock.writeLock();
    }

    @Override
    public void trimToSize() {
        writeLock.lock();
        try {
            if (delegate instanceof HasTrimToSize) {
                ((HasTrimToSize) delegate).trimToSize();
            }
        } finally {
            writeLock.unlock();
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
        readLock.lock();
        try {
            return delegate.hashCode();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        readLock.lock();
        try {
            return delegate.equals(obj);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public OWLOntologyManager getOWLOntologyManager() {
        readLock.lock();
        try {
            return delegate.getOWLOntologyManager();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void setOWLOntologyManager(@Nullable OWLOntologyManager owlOntologyManager) {
        writeLock.lock();
        try {
            delegate.setOWLOntologyManager(owlOntologyManager);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
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
    public Set<OWLAnnotation> getAnnotations() {
        readLock.lock();
        try {
            return delegate.getAnnotations();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<IRI> getDirectImportsDocuments() {
        readLock.lock();
        try {
            return delegate.getDirectImportsDocuments();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<IRI> directImportsDocuments() {
        readLock.lock();
        try {
            return delegate.directImportsDocuments();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getDirectImports() {
        readLock.lock();
        try {
            return delegate.getDirectImports();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLOntology> directImports() {
        readLock.lock();
        try {
            return delegate.directImports();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getImports() {
        readLock.lock();
        try {
            return delegate.getImports();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLOntology> imports() {
        readLock.lock();
        try {
            return delegate.imports();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLOntology> getImportsClosure() {
        readLock.lock();
        try {
            return delegate.getImportsClosure();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLOntology> importsClosure() {
        readLock.lock();
        try {
            return delegate.importsClosure();
        } finally {
            readLock.unlock();
        }
    }

    @Override
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
    public Set<OWLAxiom> getTBoxAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.getTBoxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getABoxAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.getABoxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getRBoxAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.getRBoxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> tboxAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.tboxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> aboxAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.aboxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> rboxAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.rboxAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        readLock.lock();
        try {
            return delegate.getGeneralClassAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEntity> getSignature() {
        readLock.lock();
        try {
            return delegate.getSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEntity> getSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.getSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClassAxiom> generalClassAxioms() {
        readLock.lock();
        try {
            return delegate.generalClassAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLEntity> signature() {
        readLock.lock();
        try {
            return delegate.signature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLEntity> signature(Imports imports) {
        readLock.lock();
        try {
            return delegate.signature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.isDeclared(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isDeclared(OWLEntity owlEntity, Imports imports) {
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
    public void saveOntology(IRI iri) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OutputStream outputStream) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(outputStream);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, IRI iri) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat, iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, OutputStream outputStream)
        throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat, outputStream);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLOntologyDocumentTarget owlOntologyDocumentTarget) throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlOntologyDocumentTarget);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveOntology(OWLDocumentFormat owlDocumentFormat, OWLOntologyDocumentTarget owlOntologyDocumentTarget)
        throws OWLOntologyStorageException {
        readLock.lock();
        try {
            delegate.saveOntology(owlDocumentFormat, owlOntologyDocumentTarget);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClassExpression> getNestedClassExpressions() {
        readLock.lock();
        try {
            return delegate.getNestedClassExpressions();
        } finally {
            readLock.unlock();
        }
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
    public String toString() {
        readLock.lock();
        try {
            return delegate.toString();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int compareTo(@Nullable OWLObject o) {
        readLock.lock();
        try {
            return delegate.compareTo(o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnonymousIndividual> getAnonymousIndividuals() {
        readLock.lock();
        try {
            return delegate.getAnonymousIndividuals();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClass> getClassesInSignature() {
        readLock.lock();
        try {
            return delegate.getClassesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.getObjectPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.getDataPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature() {
        readLock.lock();
        try {
            return delegate.getIndividualsInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature() {
        readLock.lock();
        try {
            return delegate.getDatatypesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getAxiomCount(Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.getLogicalAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getLogicalAxiomCount(Imports imports) {
        readLock.lock();
        try {
            return delegate.getLogicalAxiomCount(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(axiomType, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(AxiomType<T> axiomType, Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(axiomType, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(axiomType, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiom(OWLAxiom owlAxiom, Imports imports, AxiomAnnotations axiomAnnotations) {
        readLock.lock();
        try {
            return delegate.containsAxiom(owlAxiom, imports, axiomAnnotations);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom owlAxiom, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxiomsIgnoreAnnotations(owlAxiom, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom owlAxiom, Imports imports) {
        readLock.lock();
        try {
            return delegate.axiomsIgnoreAnnotations(owlAxiom, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlPrimitive, Imports imports) {
        readLock.lock();
        try {
            return delegate.getReferencingAxioms(owlPrimitive, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> referencingAxioms(OWLPrimitive owlPrimitive, Imports imports) {
        readLock.lock();
        try {
            return delegate.referencingAxioms(owlPrimitive, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass owlClass, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlClass, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(OWLObjectPropertyExpression owlObjectPropertyExpression,
        Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlObjectPropertyExpression, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty owlDataProperty, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDataProperty, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual owlIndividual, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlIndividual, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty owlAnnotationProperty, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlAnnotationProperty, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype owlDatatype, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDatatype, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getAxioms() {
        readLock.lock();
        try {
            return delegate.getAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> axioms() {
        // XXX investigate locking access to streams
        readLock.lock();
        try {
            return delegate.axioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        readLock.lock();
        try {
            return delegate.getLogicalAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLLogicalAxiom> logicalAxioms() {
        readLock.lock();
        try {
            return delegate.logicalAxioms();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType) {
        readLock.lock();
        try {
            return delegate.getAxioms(axiomType);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(AxiomType<T> axiomType) {
        readLock.lock();
        try {
            return delegate.axioms(axiomType);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiom(OWLAxiom owlAxiom) {
        readLock.lock();
        try {
            return delegate.containsAxiom(owlAxiom);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getAxioms(boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getAxiomCount(boolean b) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLLogicalAxiom> getLogicalAxioms(boolean b) {
        readLock.lock();
        try {
            return delegate.getLogicalAxioms(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int getLogicalAxiomCount(boolean b) {
        readLock.lock();
        try {
            return delegate.getLogicalAxiomCount(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(AxiomType<T> axiomType, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(axiomType, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(axiomType, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiom(OWLAxiom owlAxiom, boolean b) {
        readLock.lock();
        try {
            return delegate.containsAxiom(owlAxiom, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom owlAxiom, boolean b) {
        readLock.lock();
        try {
            return delegate.containsAxiomIgnoreAnnotations(owlAxiom, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom owlAxiom, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxiomsIgnoreAnnotations(owlAxiom, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlPrimitive, boolean b) {
        readLock.lock();
        try {
            return delegate.getReferencingAxioms(owlPrimitive, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass owlClass, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlClass, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(OWLObjectPropertyExpression owlObjectPropertyExpression, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlObjectPropertyExpression, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty owlDataProperty, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDataProperty, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual owlIndividual, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlIndividual, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty owlAnnotationProperty, boolean b) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlAnnotationProperty, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype owlDatatype, boolean b) {
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
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
        readLock.lock();
        try {
            return delegate.getAxiomCount(axiomType);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAxiomIgnoreAnnotations(OWLAxiom owlAxiom) {
        readLock.lock();
        try {
            return delegate.containsAxiomIgnoreAnnotations(owlAxiom);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getAxiomsIgnoreAnnotations(OWLAxiom owlAxiom) {
        readLock.lock();
        try {
            return delegate.getAxiomsIgnoreAnnotations(owlAxiom);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> axiomsIgnoreAnnotations(OWLAxiom owlAxiom) {
        readLock.lock();
        try {
            return delegate.axiomsIgnoreAnnotations(owlAxiom);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAxiom> getReferencingAxioms(OWLPrimitive owlPrimitive) {
        readLock.lock();
        try {
            return delegate.getReferencingAxioms(owlPrimitive);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> referencingAxioms(OWLPrimitive owlPrimitive) {
        readLock.lock();
        try {
            return delegate.referencingAxioms(owlPrimitive);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClassAxiom> getAxioms(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectPropertyAxiom> getAxioms(OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataPropertyAxiom> getAxioms(OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLIndividualAxiom> getAxioms(OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationAxiom> getAxioms(OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getAxioms(OWLDatatype owlDatatype) {
        readLock.lock();
        try {
            return delegate.getAxioms(owlDatatype);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClassAxiom> axioms(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.axioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLObjectPropertyAxiom> axioms(OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.axioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDataPropertyAxiom> axioms(OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.axioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLIndividualAxiom> axioms(OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.axioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationAxiom> axioms(OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.axioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> axioms(OWLDatatype owlDatatype) {
        readLock.lock();
        try {
            return delegate.axioms(owlDatatype);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClass> getClassesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.getClassesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.getObjectPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.getDataPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.getIndividualsInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(Imports imports) {
        readLock.lock();
        try {
            return delegate.getReferencedAnonymousIndividuals(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals(Imports imports) {
        readLock.lock();
        try {
            return delegate.referencedAnonymousIndividuals(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals() {
        readLock.lock();
        try {
            return delegate.referencedAnonymousIndividuals();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.getDatatypesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(owlEntity, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsClassInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsClassInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsObjectPropertyInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsDataPropertyInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsAnnotationPropertyInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsDatatypeInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsIndividualInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.containsDatatypeInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsClassInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.containsClassInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.containsObjectPropertyInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.containsDataPropertyInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.containsAnnotationPropertyInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.containsIndividualInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.getEntitiesInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<IRI> getPunnedIRIs(Imports imports) {
        readLock.lock();
        try {
            return delegate.getPunnedIRIs(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsReference(OWLEntity owlEntity, Imports imports) {
        readLock.lock();
        try {
            return delegate.containsReference(owlEntity, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsReference(OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.containsReference(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.getEntitiesInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLEntity> entitiesInSignature(IRI iri) {
        readLock.lock();
        try {
            return delegate.entitiesInSignature(iri);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClass> getClassesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getClassesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectProperty> getObjectPropertiesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getObjectPropertiesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataProperty> getDataPropertiesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getDataPropertiesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLNamedIndividual> getIndividualsInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getIndividualsInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(boolean b) {
        readLock.lock();
        try {
            return delegate.getReferencedAnonymousIndividuals(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDatatype> getDatatypesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getDatatypesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(boolean b) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertiesInSignature(b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(OWLEntity owlEntity, boolean b) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(owlEntity, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsEntityInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsEntityInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsClassInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsClassInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsObjectPropertyInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsObjectPropertyInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDataPropertyInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsDataPropertyInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsAnnotationPropertyInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsAnnotationPropertyInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsDatatypeInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsDatatypeInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsIndividualInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.containsIndividualInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEntity> getEntitiesInSignature(IRI iri, boolean b) {
        readLock.lock();
        try {
            return delegate.getEntitiesInSignature(iri, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsReference(OWLEntity owlEntity, boolean b) {
        readLock.lock();
        try {
            return delegate.containsReference(owlEntity, b);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> aClass, OWLObject owlObject, Imports imports,
        Navigation navigation) {
        readLock.lock();
        try {
            return delegate.getAxioms(aClass, owlObject, imports, navigation);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> aClass, OWLObject owlObject, Imports imports,
        Navigation navigation) {
        readLock.lock();
        try {
            return delegate.axioms(aClass, owlObject, imports, navigation);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(OWLAxiomSearchFilter owlAxiomSearchFilter, Object o,
        Imports imports) {
        readLock.lock();
        try {
            return delegate.filterAxioms(owlAxiomSearchFilter, o, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(OWLAxiomSearchFilter owlAxiomSearchFilter, Object o, Imports imports) {
        readLock.lock();
        try {
            return delegate.contains(owlAxiomSearchFilter, o, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean contains(OWLAxiomSearchFilter owlAxiomSearchFilter, Object o) {
        readLock.lock();
        try {
            return delegate.contains(owlAxiomSearchFilter, o);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> aClass, Class<? extends OWLObject> aClass1,
        OWLObject owlObject, Imports imports, Navigation navigation) {
        readLock.lock();
        try {
            return delegate.getAxioms(aClass, aClass1, owlObject, imports, navigation);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> aClass, Class<? extends OWLObject> aClass1,
        OWLObject owlObject, Imports imports, Navigation navigation) {
        readLock.lock();
        try {
            return delegate.axioms(aClass, aClass1, owlObject, imports, navigation);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getSubAnnotationPropertyOfAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertyDomainAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.getAnnotationPropertyRangeAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationPropertyDomainAxiom> annotationPropertyDomainAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.annotationPropertyDomainAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationPropertyRangeAxiom> annotationPropertyRangeAxioms(
        OWLAnnotationProperty owlAnnotationProperty) {
        readLock.lock();
        try {
            return delegate.annotationPropertyRangeAxioms(owlAnnotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity owlEntity) {
        readLock.lock();
        try {
            return delegate.getDeclarationAxioms(owlEntity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject owlAnnotationSubject) {
        readLock.lock();
        try {
            return delegate.getAnnotationAssertionAxioms(owlAnnotationSubject);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getSubClassAxiomsForSubClass(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getSubClassAxiomsForSuperClass(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getEquivalentClassesAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getDisjointClassesAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getDisjointUnionAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.getHasKeyAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectSubPropertyAxiomsForSubProperty(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectSubPropertyAxiomsForSuperProperty(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectPropertyDomainAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getObjectPropertyRangeAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getInverseObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getEquivalentObjectPropertiesAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getDisjointObjectPropertiesAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getFunctionalObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getInverseFunctionalObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getSymmetricObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getAsymmetricObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getReflexiveObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getIrreflexiveObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(
        OWLObjectPropertyExpression owlObjectPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getTransitiveObjectPropertyAxioms(owlObjectPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDataSubPropertyAxiomsForSubProperty(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
        OWLDataPropertyExpression owlDataPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getDataSubPropertyAxiomsForSuperProperty(owlDataPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDataPropertyDomainAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDataPropertyRangeAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getEquivalentDataPropertiesAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty owlDataProperty) {
        readLock.lock();
        try {
            return delegate.getDisjointDataPropertiesAxioms(owlDataProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
        OWLDataPropertyExpression owlDataPropertyExpression) {
        readLock.lock();
        try {
            return delegate.getFunctionalDataPropertyAxioms(owlDataPropertyExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getClassAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClassExpression owlClassExpression) {
        readLock.lock();
        try {
            return delegate.getClassAssertionAxioms(owlClassExpression);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getDataPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getObjectPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
        OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getNegativeObjectPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(
        OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getNegativeDataPropertyAssertionAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getSameIndividualAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual owlIndividual) {
        readLock.lock();
        try {
            return delegate.getDifferentIndividualAxioms(owlIndividual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype owlDatatype) {
        readLock.lock();
        try {
            return delegate.getDatatypeDefinitions(owlDatatype);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ChangeApplied applyChange(OWLOntologyChange owlOntologyChange) {
        writeLock.lock();
        try {
            return getMutableOntology().applyChange(owlOntologyChange);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied applyChanges(List<? extends OWLOntologyChange> list) {
        writeLock.lock();
        try {
            return getMutableOntology().applyChanges(list);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied addAxiom(OWLAxiom owlAxiom) {
        writeLock.lock();
        try {
            return getMutableOntology().addAxiom(owlAxiom);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied addAxioms(Collection<? extends OWLAxiom> set) {
        writeLock.lock();
        try {
            return getMutableOntology().addAxioms(set);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied addAxioms(OWLAxiom... set) {
        writeLock.lock();
        try {
            return getMutableOntology().addAxioms(set);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied add(OWLAxiom owlAxiom) {
        writeLock.lock();
        try {
            return getMutableOntology().add(owlAxiom);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied add(Collection<? extends OWLAxiom> set) {
        writeLock.lock();
        try {
            return getMutableOntology().add(set);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied add(OWLAxiom... set) {
        writeLock.lock();
        try {
            return getMutableOntology().add(set);
        } finally {
            writeLock.unlock();
        }
    }

    private OWLMutableOntology getMutableOntology() {
        return (OWLMutableOntology) delegate;
    }

    @Override
    public Stream<OWLImportsDeclaration> importsDeclarations() {
        readLock.lock();
        try {
            return delegate.importsDeclarations();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key,
        Imports includeImportsClosure) {
        readLock.lock();
        try {
            return delegate.axioms(filter, key, includeImportsClosure);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key) {
        readLock.lock();
        try {
            return delegate.axioms(filter, key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> type, Class<? extends OWLObject> explicitClass,
        OWLObject entity, Navigation forSubPosition) {
        readLock.lock();
        try {
            return delegate.axioms(type, explicitClass, entity, forSubPosition);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSubAnnotationPropertyOfAxiom> subAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        readLock.lock();
        try {
            return delegate.subAnnotationPropertyOfAxioms(subProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> datatypeDefinitions(OWLDatatype datatype) {
        readLock.lock();
        try {
            return delegate.datatypeDefinitions(datatype);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public ChangeApplied removeAxiom(OWLAxiom axiom) {
        writeLock.lock();
        try {
            return delegate.removeAxiom(axiom);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied removeAxioms(Collection<? extends OWLAxiom> axioms) {
        writeLock.lock();
        try {
            return delegate.removeAxioms(axioms);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied removeAxioms(OWLAxiom... axioms) {
        writeLock.lock();
        try {
            return delegate.removeAxioms(axioms);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied remove(OWLAxiom axiom) {
        writeLock.lock();
        try {
            return delegate.remove(axiom);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied remove(Collection<? extends OWLAxiom> axioms) {
        writeLock.lock();
        try {
            return delegate.remove(axioms);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied remove(OWLAxiom... axioms) {
        writeLock.lock();
        try {
            return delegate.remove(axioms);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public ChangeApplied applyDirectChange(OWLOntologyChange change) {
        writeLock.lock();
        try {
            return delegate.applyDirectChange(change);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Stream<OWLDisjointObjectPropertiesAxiom> disjointObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.disjointObjectPropertiesAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.objectPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(OWLAnnotationSubject entity) {
        readLock.lock();
        try {
            return delegate.annotationAssertionAxioms(entity);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(OWLAnnotationSubject entity, Imports imports) {
        readLock.lock();
        try {
            return delegate.annotationAssertionAxioms(entity, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.annotationPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationProperty> annotationPropertiesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.annotationPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotation> annotations() {
        readLock.lock();
        try {
            return delegate.annotations();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotation> annotations(OWLAnnotationProperty p) {
        readLock.lock();
        try {
            return delegate.annotations(p);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotation> annotations(Predicate<OWLAnnotation> p) {
        readLock.lock();
        try {
            return delegate.annotations(p);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnonymousIndividual> anonymousIndividuals() {
        readLock.lock();
        try {
            return delegate.anonymousIndividuals();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAsymmetricObjectPropertyAxiom> asymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.asymmetricObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Stream<T> axioms(Class<T> type, OWLObject entity, Navigation forSubPosition) {
        readLock.lock();
        try {
            return delegate.axioms(type, entity, forSubPosition);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAxiom> axioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLAnnotationAxiom> axioms(OWLAnnotationProperty property, Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(property, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClassAxiom> axioms(OWLClass cls, Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(cls, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDataPropertyAxiom> axioms(OWLDataProperty property, Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(property, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDatatypeDefinitionAxiom> axioms(OWLDatatype datatype, Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(datatype, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLIndividualAxiom> axioms(OWLIndividual individual, Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(individual, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLObjectPropertyAxiom> axioms(OWLObjectPropertyExpression property, Imports imports) {
        readLock.lock();
        try {
            return delegate.axioms(property, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClassAssertionAxiom> classAssertionAxioms(OWLClassExpression ce) {
        readLock.lock();
        try {
            return delegate.classAssertionAxioms(ce);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClassAssertionAxiom> classAssertionAxioms(OWLIndividual individual) {
        readLock.lock();
        try {
            return delegate.classAssertionAxioms(individual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClass> classesInSignature() {
        readLock.lock();
        try {
            return delegate.classesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClass> classesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.classesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature() {
        readLock.lock();
        try {
            return delegate.dataPropertiesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDataProperty> dataPropertiesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.dataPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDataPropertyAssertionAxiom> dataPropertyAssertionAxioms(OWLIndividual individual) {
        readLock.lock();
        try {
            return delegate.dataPropertyAssertionAxioms(individual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDataPropertyDomainAxiom> dataPropertyDomainAxioms(OWLDataProperty property) {
        readLock.lock();
        try {
            return delegate.dataPropertyDomainAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDataPropertyRangeAxiom> dataPropertyRangeAxioms(OWLDataProperty property) {
        readLock.lock();
        try {
            return delegate.dataPropertyRangeAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsForSubProperty(OWLDataProperty subProperty) {
        readLock.lock();
        try {
            return delegate.dataSubPropertyAxiomsForSubProperty(subProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsForSuperProperty(
        OWLDataPropertyExpression superProperty) {
        readLock.lock();
        try {
            return delegate.dataSubPropertyAxiomsForSuperProperty(superProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature() {
        readLock.lock();
        try {
            return delegate.datatypesInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDatatype> datatypesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.datatypesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDeclarationAxiom> declarationAxioms(OWLEntity subject) {
        readLock.lock();
        try {
            return delegate.declarationAxioms(subject);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDifferentIndividualsAxiom> differentIndividualAxioms(OWLIndividual individual) {
        readLock.lock();
        try {
            return delegate.differentIndividualAxioms(individual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDisjointClassesAxiom> disjointClassesAxioms(OWLClass cls) {
        readLock.lock();
        try {
            return delegate.disjointClassesAxioms(cls);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDisjointDataPropertiesAxiom> disjointDataPropertiesAxioms(OWLDataProperty property) {
        readLock.lock();
        try {
            return delegate.disjointDataPropertiesAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLDisjointUnionAxiom> disjointUnionAxioms(OWLClass owlClass) {
        readLock.lock();
        try {
            return delegate.disjointUnionAxioms(owlClass);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLEntity> entitiesInSignature(IRI iri, Imports imports) {
        readLock.lock();
        try {
            return delegate.entitiesInSignature(iri, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLEquivalentClassesAxiom> equivalentClassesAxioms(OWLClass cls) {
        readLock.lock();
        try {
            return delegate.equivalentClassesAxioms(cls);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLEquivalentDataPropertiesAxiom> equivalentDataPropertiesAxioms(OWLDataProperty property) {
        readLock.lock();
        try {
            return delegate.equivalentDataPropertiesAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLEquivalentObjectPropertiesAxiom> equivalentObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.equivalentObjectPropertiesAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Collection<T> filterAxioms(OWLAxiomSearchFilter filter, Object key) {
        readLock.lock();
        try {
            return delegate.filterAxioms(filter, key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLFunctionalDataPropertyAxiom> functionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.functionalDataPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLFunctionalObjectPropertyAxiom> functionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.functionalObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject entity, Imports imports) {
        readLock.lock();
        try {
            return delegate.getAnnotationAssertionAxioms(entity, imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnnotation> getAnnotations(OWLAnnotationProperty annotationProperty) {
        readLock.lock();
        try {
            return delegate.getAnnotations(annotationProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, Class<? extends OWLObject> explicitClass,
        OWLObject entity, Navigation forSubPosition) {
        readLock.lock();
        try {
            return delegate.getAxioms(type, explicitClass, entity, forSubPosition);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, OWLObject entity, Navigation forSubPosition) {
        readLock.lock();
        try {
            return delegate.getAxioms(type, entity, forSubPosition);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public OWLDocumentFormat getFormat() {
        readLock.lock();
        try {
            return delegate.getFormat();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals() {
        readLock.lock();
        try {
            return delegate.getReferencedAnonymousIndividuals();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLHasKeyAxiom> hasKeyAxioms(OWLClass cls) {
        readLock.lock();
        try {
            return delegate.hasKeyAxioms(cls);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature() {
        readLock.lock();
        try {
            return delegate.individualsInSignature();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLNamedIndividual> individualsInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.individualsInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLInverseFunctionalObjectPropertyAxiom> inverseFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.inverseFunctionalObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLInverseObjectPropertiesAxiom> inverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.inverseObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLIrreflexiveObjectPropertyAxiom> irreflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.irreflexiveObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLLogicalAxiom> logicalAxioms(Imports imports) {
        readLock.lock();
        try {
            return delegate.logicalAxioms(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLNegativeDataPropertyAssertionAxiom> negativeDataPropertyAssertionAxioms(OWLIndividual individual) {
        readLock.lock();
        try {
            return delegate.negativeDataPropertyAssertionAxioms(individual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLNegativeObjectPropertyAssertionAxiom> negativeObjectPropertyAssertionAxioms(
        OWLIndividual individual) {
        readLock.lock();
        try {
            return delegate.negativeObjectPropertyAssertionAxioms(individual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLClassExpression> nestedClassExpressions() {
        readLock.lock();
        try {
            return delegate.nestedClassExpressions();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLObjectProperty> objectPropertiesInSignature(Imports imports) {
        readLock.lock();
        try {
            return delegate.objectPropertiesInSignature(imports);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLObjectPropertyAssertionAxiom> objectPropertyAssertionAxioms(OWLIndividual individual) {
        readLock.lock();
        try {
            return delegate.objectPropertyAssertionAxioms(individual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLObjectPropertyDomainAxiom> objectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.objectPropertyDomainAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLObjectPropertyRangeAxiom> objectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.objectPropertyRangeAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsForSubProperty(
        OWLObjectPropertyExpression subProperty) {
        readLock.lock();
        try {
            return delegate.objectSubPropertyAxiomsForSubProperty(subProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsForSuperProperty(
        OWLObjectPropertyExpression superProperty) {
        readLock.lock();
        try {
            return delegate.objectSubPropertyAxiomsForSuperProperty(superProperty);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLReflexiveObjectPropertyAxiom> reflexiveObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.reflexiveObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSameIndividualAxiom> sameIndividualAxioms(OWLIndividual individual) {
        readLock.lock();
        try {
            return delegate.sameIndividualAxioms(individual);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSubClassOfAxiom> subClassAxiomsForSubClass(OWLClass cls) {
        readLock.lock();
        try {
            return delegate.subClassAxiomsForSubClass(cls);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSubClassOfAxiom> subClassAxiomsForSuperClass(OWLClass cls) {
        readLock.lock();
        try {
            return delegate.subClassAxiomsForSuperClass(cls);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLSymmetricObjectPropertyAxiom> symmetricObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.symmetricObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Stream<OWLTransitiveObjectPropertyAxiom> transitiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        readLock.lock();
        try {
            return delegate.transitiveObjectPropertyAxioms(property);
        } finally {
            readLock.unlock();
        }
    }
}
