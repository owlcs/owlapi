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

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * Represents an OWL 2 <a
 * href="http://www.w3.org/TR/owl2-syntax/#Ontologies">Ontology</a> in the OWL 2
 * specification. <br>
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
public interface OWLOntology extends OWLObject, HasAxioms, HasLogicalAxioms,
        HasAxiomsByType, HasContainsAxiom, HasAnnotations, HasDirectImports,
        HasImportsClosure, HasContainsEntityInSignature, HasOntologyID,
        HasGetEntitiesInSignature, OWLAxiomCollection, OWLSignature,
        OWLAxiomIndex {

    /**
     * accept for named object visitor
     * 
     * @param visitor
     *        the visitor
     */
    void accept(@Nonnull OWLNamedObjectVisitor visitor);

    /**
     * Accepts a visitor
     * 
     * @param <O>
     *        visitor return type
     * @param visitor
     *        The visitor
     * @return visitor return value
     */
    @Nonnull
    <O> O accept(@Nonnull OWLNamedObjectVisitorEx<O> visitor);

    /**
     * Gets the manager that created this ontology. The manager is used by
     * various methods on OWLOntology to resolve imports
     * 
     * @return The manager that created this ontology.
     */
    @Nonnull
    OWLOntologyManager getOWLOntologyManager();

    /**
     * Gets the identity of this ontology (i.e. ontology IRI + version IRI).
     * 
     * @return The ID of this ontology.
     */
    @Nonnull
    @Override
    OWLOntologyID getOntologyID();

    /**
     * Determines whether or not this ontology is anonymous. An ontology is
     * anonymous if it does not have an ontology IRI.
     * 
     * @return {@code true} if this ontology is anonymous, otherwise
     *         {@code false}
     */
    boolean isAnonymous();

    /**
     * Gets the annotations on this ontology.
     * 
     * @return A set of annotations on this ontology. The set returned will be a
     *         copy - modifying the set will have no effect on the annotations
     *         in this ontology, similarly, any changes that affect the
     *         annotations on this ontology will not change the returned set.
     */
    @Nonnull
    @Override
    Set<OWLAnnotation> getAnnotations();

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Imported ontologies
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the set of document IRIs that are directly imported by this
     * ontology. This corresponds to the IRIs defined by the
     * directlyImportsDocument association as discussed in Section 3.4 of the
     * OWL 2 Structural specification.
     * 
     * @return The set of directlyImportsDocument IRIs.
     * @throws UnknownOWLOntologyException
     *         If this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Nonnull
    Set<IRI> getDirectImportsDocuments();

    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related
     * to via the directlyImports relation. See Section 3.4 of the OWL 2
     * specification for the definition of the directlyImports relation. <br>
     * Note that there may be fewer ontologies in the set returned by this
     * method than there are IRIs in the set returned by the
     * {@link #getDirectImportsDocuments()} method. This will be the case if
     * some of the ontologies that are directly imported by this ontology are
     * not loaded for what ever reason.
     * 
     * @return A set of ontologies such that for this ontology O, and each
     *         ontology O' in the set, (O, O') is in the directlyImports
     *         relation.
     * @throws UnknownOWLOntologyException
     *         If this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Nonnull
    @Override
    Set<OWLOntology> getDirectImports();

    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related
     * to via the <em>transitive closure</em> of the <a
     * href="http://www.w3.org/TR/owl2-syntax/#Imports">directlyImports
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
    @Nonnull
    Set<OWLOntology> getImports();

    /**
     * Gets the set of <em>loaded</em> ontologies that this ontology is related
     * to via the <em>reflexive transitive closure</em> of the directlyImports
     * relation as defined in Section 3.4 of the OWL 2 Structural Specification.
     * (i.e. The set returned includes all ontologies returned by the
     * {@link #getImports()} method plus this ontology.)<br>
     * For example, if this ontology imports ontology B, and ontology B imports
     * ontology C, then this method will return the set consisting of this
     * ontology, ontology B and ontology C.
     * 
     * @return The set of ontologies in the reflexive transitive closure of the
     *         directlyImports relation.
     * @throws UnknownOWLOntologyException
     *         If this ontology is no longer managed by its manager because it
     *         was removed from the manager.
     */
    @Nonnull
    @Override
    Set<OWLOntology> getImportsClosure();

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
    @Nonnull
    Set<OWLImportsDeclaration> getImportsDeclarations();

    // /////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Methods to retrive class, property and individual axioms
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Determines if this ontology is empty - an ontology is empty if it does
     * not contain any axioms (i.e. {@link #getAxioms()} returns the empty set),
     * and it does not have any annotations (i.e. {@link #getAnnotations()}
     * returns the empty set).
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
    @Nonnull
    Set<OWLAxiom> getTBoxAxioms(@Nonnull Imports includeImportsClosure);

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
    @Nonnull
    Set<OWLAxiom> getABoxAxioms(@Nonnull Imports includeImportsClosure);

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
    @Nonnull
    Set<OWLAxiom> getRBoxAxioms(@Nonnull Imports includeImportsClosure);

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
    @Nonnull
    Set<OWLClassAxiom> getGeneralClassAxioms();

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // References/usage
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the entities that are in the signature of this ontology. The
     * signature of an ontology is the set of entities that are used to build
     * axioms and annotations in the ontology. (See <a href=
     * "http://www.w3.org/TR/owl2-syntax/#Entities.2C_Literals.2C_and_Anonymous_Individuals"
     * >The OWL 2 Structural Specification</a>)
     * 
     * @return A set of {@code OWLEntity} objects. The set that is returned is a
     *         copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     * @see #getClassesInSignature()
     * @see #getObjectPropertiesInSignature()
     * @see #getDataPropertiesInSignature()
     * @see #getIndividualsInSignature()
     */
    @Nonnull
    @Override
    Set<OWLEntity> getSignature();

    /**
     * Gets the entities that are in the signature of this ontology. The
     * signature of an ontology is the set of entities that are used to build
     * axioms and annotations in the ontology. (See <a href=
     * "http://www.w3.org/TR/owl2-syntax/#Entities.2C_Literals.2C_and_Anonymous_Individuals"
     * >The OWL 2 Structural Specification</a>)
     * 
     * @param includeImportsClosure
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
    @Nonnull
    Set<OWLEntity> getSignature(@Nonnull Imports includeImportsClosure);

    /**
     * Determines if this ontology declares an entity i.e. it contains a
     * declaration axiom for the specified entity.
     * 
     * @param owlEntity
     *        The entity to be tested for
     * @return {@code true} if the ontology contains a declaration for the
     *         specified entity, otherwise {@code false}.
     */
    boolean isDeclared(@Nonnull OWLEntity owlEntity);

    /**
     * Determines if this ontology or its imports closure declares an entity
     * i.e. contains a declaration axiom for the specified entity.
     * 
     * @param owlEntity
     *        The entity to be tested for
     * @param includeImportsClosure
     *        if INCLUDED, the imports closure is included.
     * @return {@code true} if the ontology or its imports closure contains a
     *         declaration for the specified entity, otherwise {@code false}.
     */
    boolean isDeclared(@Nonnull OWLEntity owlEntity,
            @Nonnull Imports includeImportsClosure);
}
