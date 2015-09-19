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
package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.model.parameters.ChangeApplied;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * Represents an OWL 2
 * <a href="http://www.w3.org/TR/owl2-syntax/#Ontologies">Ontology</a> in the
 * OWL 2 specification. <br>
 * An {@code OWLOntology} consists of a possibly empty set of
 * {@link org.semanticweb.owlapi.model.OWLAxiom}s and a possibly empty set of
 * {@link OWLAnnotation}s. An ontology can have an ontology IRI which can be
 * used to identify the ontology. If it has an ontology IRI then it may also
 * have an ontology version IRI. Since OWL 2, an ontology need not have an
 * ontology IRI. (See the <a href="http://www.w3.org/TR/owl2-syntax/">OWL 2
 * Structural Specification</a> An ontology cannot be modified directly. Changes
 * must be applied via its {@code OWLOntologyManager}.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLOntology extends OWLObject, HasAnnotations, HasDirectImports, HasImportsClosure, HasOntologyID,
    OWLAxiomCollection, OWLAxiomCollectionBooleanArgs, OWLSignature, OWLSignatureBooleanArgs, OWLAxiomIndex,
    HasApplyChange, HasApplyChanges, HasDirectAddAxiom, HasDirectAddAxioms, HasDirectRemoveAxiom, HasDirectRemoveAxioms,
    HasApplyDirectChange, IsAnonymous {

    // Default implementation of these mutating methods is to do nothing.
    // Adding them to this interface allows access without casting, since
    // OWLOntology is the de facto standard used in the code and
    // OWLMutableOntology hardly appears.
    @Override
    default ChangeApplied applyChange(OWLOntologyChange change) {
        return getOWLOntologyManager().applyChange(change);
    }

    @Override
    default ChangeApplied applyDirectChange(OWLOntologyChange change) {
        return ChangeApplied.UNSUCCESSFULLY;
    }

    @Override
    default ChangeApplied applyChanges(List<? extends OWLOntologyChange> changes) {
        return getOWLOntologyManager().applyChanges(changes);
    }

    @Override
    default ChangeApplied addAxiom(OWLAxiom axiom) {
        return getOWLOntologyManager().addAxiom(this, axiom);
    }

    @Override
    default ChangeApplied addAxioms(Collection<? extends OWLAxiom> axioms) {
        return getOWLOntologyManager().addAxioms(this, axioms.stream());
    }

    @Override
    default ChangeApplied addAxioms(OWLAxiom... axioms) {
        return addAxioms(Arrays.asList(axioms));
    }

    @Override
    default ChangeApplied removeAxiom(OWLAxiom axiom) {
        return getOWLOntologyManager().removeAxioms(this, Stream.of(axiom));
    }

    @Override
    default ChangeApplied removeAxioms(Collection<? extends OWLAxiom> axioms) {
        return getOWLOntologyManager().removeAxioms(this, axioms.stream());
    }

    @Override
    default ChangeApplied removeAxioms(Stream<? extends OWLAxiom> axioms) {
        return getOWLOntologyManager().removeAxioms(this, axioms);
    }

    @Override
    default ChangeApplied removeAxioms(OWLAxiom... axioms) {
        return removeAxioms(Arrays.asList(axioms));
    }

    /**
     * accept for named object visitor
     * 
     * @param visitor
     *        the visitor
     */
    default void accept(OWLNamedObjectVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Accepts a visitor
     * 
     * @param <O>
     *        visitor return type
     * @param visitor
     *        The visitor
     * @return visitor return value
     */
    default <O> O accept(OWLNamedObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    /**
     * Gets the manager that manages this ontology. The manager is used by
     * various methods on OWLOntology to resolve imports
     * 
     * @return The manager for this ontology.
     */
    OWLOntologyManager getOWLOntologyManager();

    /**
     * @return ontology format for this ontology
     */
    default OWLDocumentFormat getFormat() {
        return getOWLOntologyManager().getOntologyFormat(this);
    }

    /**
     * Sets the manager for this ontology. This method is used when moving
     * ontologies from one manager to another and when removing an ontology form
     * a manager, and should be used by OWLOntologyManager implementations only.
     * 
     * @param manager
     *        the new manager for this ontology
     */
    void setOWLOntologyManager(@Nullable OWLOntologyManager manager);

    // Imported ontologies
    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related
     * to via the <em>transitive closure</em> of the
     * <a href="http://www.w3.org/TR/owl2-syntax/#Imports">directlyImports
     * relation</a>.<br>
     * For example, if this ontology imports ontology B, and ontology B imports
     * ontology C, then this method will return the set consisting of ontology B
     * and ontology C.
     * 
     * @return The set of ontologies that this ontology is related to via the
     *         transitive closure of the directlyImports relation. The set that
     *         is returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     * @throws UnknownOWLOntologyException
     *         if this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Deprecated
    default Set<OWLOntology> getImports() {
        return asSet(imports());
    }

    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related
     * to via the <em>transitive closure</em> of the
     * <a href="http://www.w3.org/TR/owl2-syntax/#Imports">directlyImports
     * relation</a>.<br>
     * For example, if this ontology imports ontology B, and ontology B imports
     * ontology C, then this method will return the set consisting of ontology B
     * and ontology C.
     * 
     * @return The set of ontologies that this ontology is related to via the
     *         transitive closure of the directlyImports relation. The set that
     *         is returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     * @throws UnknownOWLOntologyException
     *         if this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    Stream<OWLOntology> imports();

    /**
     * Gets the set of imports declarations for this ontology. The set returned
     * represents the set of IRIs that correspond to the set of IRIs in an
     * ontology's directlyImportsDocuments (see Section 3 in the OWL 2
     * structural specification).
     * 
     * @return The set of imports declarations that correspond to the set of
     *         ontology document IRIs that are directly imported by this
     *         ontology. The set that is returned is a copy - it will not be
     *         updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Deprecated
    default Set<OWLImportsDeclaration> getImportsDeclarations() {
        return asSet(importsDeclarations());
    }

    /**
     * Gets the set of imports declarations for this ontology. The set returned
     * represents the set of IRIs that correspond to the set of IRIs in an
     * ontology's directlyImportsDocuments (see Section 3 in the OWL 2
     * structural specification).
     * 
     * @return The set of imports declarations that correspond to the set of
     *         ontology document IRIs that are directly imported by this
     *         ontology. The set that is returned is a copy - it will not be
     *         updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    Stream<OWLImportsDeclaration> importsDeclarations();

    // Methods to retrive class, property and individual axioms
    /**
     * Determines if this ontology is empty - an ontology is empty if it does
     * not contain any axioms (i.e. {@link #axioms()} is empty), and it does not
     * have any annotations (i.e. {@link #annotations()} is empty).
     * 
     * @return {@code true} if the ontology is empty, otherwise {@code false}.
     */
    boolean isEmpty();

    /**
     * Gets the axioms that form the TBox for this ontology, i.e., the ones
     * whose type is in the AxiomType::TBoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, the imports closure is included.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    @Deprecated
    default Set<OWLAxiom> getTBoxAxioms(Imports includeImportsClosure) {
        return asSet(tboxAxioms(includeImportsClosure));
    }

    /**
     * Gets the axioms that form the TBox for this ontology, i.e., the ones
     * whose type is in the AxiomType::TBoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, the imports closure is included.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    Stream<OWLAxiom> tboxAxioms(Imports includeImportsClosure);

    /**
     * Gets the axioms that form the ABox for this ontology, i.e., the ones
     * whose type is in the AxiomType::ABoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, the imports closure is included.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    @Deprecated
    default Set<OWLAxiom> getABoxAxioms(Imports includeImportsClosure) {
        return asSet(aboxAxioms(includeImportsClosure));
    }

    /**
     * Gets the axioms that form the ABox for this ontology, i.e., the ones
     * whose type is in the AxiomType::ABoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, the imports closure is included.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    Stream<OWLAxiom> aboxAxioms(Imports includeImportsClosure);

    /**
     * Gets the axioms that form the RBox for this ontology, i.e., the ones
     * whose type is in the AxiomType::RBoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, the imports closure is included.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    @Deprecated
    default Set<OWLAxiom> getRBoxAxioms(Imports includeImportsClosure) {
        return asSet(rboxAxioms(includeImportsClosure));
    }

    /**
     * Gets the axioms that form the RBox for this ontology, i.e., the ones
     * whose type is in the AxiomType::RBoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, the imports closure is included.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    Stream<OWLAxiom> rboxAxioms(Imports includeImportsClosure);

    /**
     * Gets the set of general axioms in this ontology. This includes:
     * <ul>
     * <li>Subclass axioms that have a complex class as the subclass</li>
     * <li>Equivalent class axioms that don't contain any named classes (
     * {@code OWLClass}es)</li>
     * <li>Disjoint class axioms that don't contain any named classes (
     * {@code OWLClass}es)</li>
     * </ul>
     * 
     * @return The set that is returned is a copy of the axioms in the ontology
     *         - it will not be updated if the ontology changes. It is therefore
     *         safe to apply changes to this ontology while iterating over this
     *         set.
     */
    @Deprecated
    default Set<OWLClassAxiom> getGeneralClassAxioms() {
        return asSet(generalClassAxioms());
    }

    /**
     * Gets the set of general axioms in this ontology. This includes:
     * <ul>
     * <li>Subclass axioms that have a complex class as the subclass</li>
     * <li>Equivalent class axioms that don't contain any named classes (
     * {@code OWLClass}es)</li>
     * <li>Disjoint class axioms that don't contain any named classes (
     * {@code OWLClass}es)</li>
     * </ul>
     * 
     * @return The set that is returned is a copy of the axioms in the ontology
     *         - it will not be updated if the ontology changes. It is therefore
     *         safe to apply changes to this ontology while iterating over this
     *         set.
     */
    Stream<OWLClassAxiom> generalClassAxioms();

    // References/usage
    /**
     * Gets the entities that are in the signature of this ontology. The
     * signature of an ontology is the set of entities that are used to build
     * axioms and annotations in the ontology. (See <a href=
     * "http://www.w3.org/TR/owl2-syntax/#Entities.2C_Literals.2C_and_Anonymous_Individuals"
     * >The OWL 2 Structural Specification</a>)
     * 
     * @param imports
     *        if INCLUDED, the imports closure is included.
     * @return A set of {@code OWLEntity} objects. The set that is returned is a
     *         copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     * @see #getClassesInSignature()
     * @see #getObjectPropertiesInSignature()
     * @see #getDataPropertiesInSignature()
     * @see #getIndividualsInSignature()
     */
    @Deprecated
    default Set<OWLEntity> getSignature(Imports imports) {
        return asSet(signature(imports));
    }

    /**
     * Gets the entities that are in the signature of this ontology. The
     * signature of an ontology is the set of entities that are used to build
     * axioms and annotations in the ontology. (See <a href=
     * "http://www.w3.org/TR/owl2-syntax/#Entities.2C_Literals.2C_and_Anonymous_Individuals"
     * >The OWL 2 Structural Specification</a>)
     * 
     * @param imports
     *        if INCLUDED, the imports closure is included.
     * @return A set of {@code OWLEntity} objects. The set that is returned is a
     *         copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     * @see #classesInSignature()
     * @see #objectPropertiesInSignature()
     * @see #dataPropertiesInSignature()
     * @see #individualsInSignature()
     */
    default Stream<OWLEntity> signature(Imports imports) {
        return imports.stream(this).flatMap(o -> o.signature());
    }

    /**
     * Determines if this ontology declares an entity i.e. it contains a
     * declaration axiom for the specified entity.
     * 
     * @param owlEntity
     *        The entity to be tested for
     * @return {@code true} if the ontology contains a declaration for the
     *         specified entity, otherwise {@code false}.
     */
    boolean isDeclared(OWLEntity owlEntity);

    /**
     * Determines if this ontology or its imports closure declares an entity
     * i.e. contains a declaration axiom for the specified entity.
     * 
     * @param owlEntity
     *        The entity to be tested for
     * @param imports
     *        if INCLUDED, the imports closure is included.
     * @return {@code true} if the ontology or its imports closure contains a
     *         declaration for the specified entity, otherwise {@code false}.
     */
    default boolean isDeclared(OWLEntity owlEntity, Imports imports) {
        return imports.stream(this).anyMatch(o -> o.isDeclared(owlEntity));
    }

    /**
     * Saves the ontology. The ontology will be saved to the location that it
     * was loaded from, or if it was created programmatically, it will be saved
     * to the location specified by an ontology IRI mapper at creation time. The
     * ontology will be saved in the same format which it was loaded from, or
     * the default ontology format if the ontology was created programmatically.
     * 
     * @throws OWLOntologyStorageException
     *         An exception will be thrown if there is a problem with saving the
     *         ontology, or the ontology can't be saved in the format it was
     *         loaded from.
     */
    default void saveOntology() throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this);
    }

    /**
     * Saves the ontology, using the specified document IRI to determine
     * where/how the ontology should be saved.
     * 
     * @param documentIRI
     *        The document IRI where the ontology should be saved to
     * @throws OWLOntologyStorageException
     *         If the ontology cannot be saved
     */
    default void saveOntology(IRI documentIRI) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, documentIRI);
    }

    /**
     * Saves the ontology, to the specified output stream
     * 
     * @param outputStream
     *        The output stream where the ontology will be saved to
     * @throws OWLOntologyStorageException
     *         If there was a problem saving this ontology to the specified
     *         output stream
     */
    default void saveOntology(OutputStream outputStream) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, outputStream);
    }

    /**
     * Saves the ontology in the specified ontology format to its document URI.
     * 
     * @param ontologyFormat
     *        The format in which the ontology should be saved.
     * @throws OWLOntologyStorageException
     *         If the ontology cannot be saved.
     */
    default void saveOntology(OWLDocumentFormat ontologyFormat) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat);
    }

    /**
     * Saves the ontology to the specified document IRI in the specified
     * ontology format.
     * 
     * @param ontologyFormat
     *        The format in which to save the ontology
     * @param documentIRI
     *        The document IRI where the ontology should be saved to
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    default void saveOntology(OWLDocumentFormat ontologyFormat, IRI documentIRI) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat, documentIRI);
    }

    /**
     * Saves the ontology to the specified output stream in the specified
     * ontology format.
     * 
     * @param ontologyFormat
     *        The format in which to save the ontology
     * @param outputStream
     *        The output stream where the ontology will be saved to.
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    default void saveOntology(OWLDocumentFormat ontologyFormat, OutputStream outputStream)
        throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat, outputStream);
    }

    /**
     * Saves the ontology to the specified
     * {@link org.semanticweb.owlapi.io.OWLOntologyDocumentTarget}.
     * 
     * @param documentTarget
     *        The output target where the ontology will be saved to.
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    default void saveOntology(OWLOntologyDocumentTarget documentTarget) throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, documentTarget);
    }

    /**
     * Saves the ontology to the specified output target in the specified
     * ontology format.
     * 
     * @param ontologyFormat
     *        The output format in which to save the ontology
     * @param documentTarget
     *        The output target where the ontology will be saved to
     * @throws OWLOntologyStorageException
     *         If the ontology could not be saved.
     */
    default void saveOntology(OWLDocumentFormat ontologyFormat, OWLOntologyDocumentTarget documentTarget)
        throws OWLOntologyStorageException {
        getOWLOntologyManager().saveOntology(this, ontologyFormat, documentTarget);
    }

    @Override
    default void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
