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
package org.semanticweb.owlapi6.model;

import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.parameters.Imports;

/**
 * Represents an OWL 2
 * <a href="http://www.w3.org/TR/owl2-syntax/#Ontologies">Ontology</a> in the
 * OWL 2 specification. <br>
 * An {@code OWLOntology} consists of a possibly empty set of
 * {@link org.semanticweb.owlapi6.model.OWLAxiom}s and a possibly empty set of
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
    OWLAxiomCollection, OWLSignature, OWLAxiomIndex, HasApplyChange, HasApplyChanges, HasAddAxiom, HasAddAxioms,
    HasRemoveAxiom, HasRemoveAxioms, HasOntologyConfigurator, HasSaveOntology {

    @Override
    default boolean isAxiom() {
        return false;
    }

    @Override
    default boolean isIndividual() {
        return false;
    }

    @Override
    default boolean isOntology() {
        return true;
    }

    @Override
    default OWLObjectType type() {
        return OWLObjectType.ONTOLOGY;
    }

    // Default implementation of these mutating methods is to do nothing.
    // Adding them to this interface allows access without casting, since
    // OWLOntology is the de facto standard used in the code and
    // OWLMutableOntology hardly appears.
    @Override
    default ChangeReport addAxiom(OWLAxiom axiom) {
        return applyChanges(Collections.singletonList(new AddAxiom(this, axiom)));
    }

    @Override
    default ChangeReport addAxioms(Stream<? extends OWLAxiom> axioms) {
        return applyChanges(asList(axioms.map(ax -> new AddAxiom(this, ax))));
    }

    @Override
    default ChangeReport removeAxiom(OWLAxiom axiom) {
        return applyChanges(Collections.singletonList(new RemoveAxiom(this, axiom)));
    }

    @Override
    default ChangeReport removeAxioms(Stream<? extends OWLAxiom> axioms) {
        return applyChanges(asList(axioms.map(ax -> new RemoveAxiom(this, ax))));
    }

    @Override
    default ChangeReport applyChange(OWLOntologyChange change) {
        return applyChanges(Collections.singletonList(change));
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
    @Override
    OWLOntologyManager getOWLOntologyManager();

    /**
     * Sets the manager for this ontology. This method is used when moving
     * ontologies from one manager to another and when removing an ontology form
     * a manager, and should be used by OWLOntologyManager implementations only.
     *
     * @param manager
     *        the new manager for this ontology
     */
    void setOWLOntologyManager(@Nullable OWLOntologyManager manager);

    /**
     * @return ontology format for this ontology; can be null if the ontology
     *         has been created programmatically and not loaded/saved, so it
     *         does not have any format information associated.
     */
    @Nullable
    default OWLDocumentFormat getFormat() {
        return getOWLOntologyManager().getOntologyFormat(this);
    }

    /**
     * Gets the ontology format for this ontology, ensuring it is not null (an
     * error is thrown if the ontology has no format). Do not use this method to
     * check if an ontology has a format associated with it; prefer
     * {@link #getFormat()}.
     *
     * @return The format of the ontology
     */
    default OWLDocumentFormat getNonnullFormat() {
        return verifyNotNull(getFormat(), (Supplier<String>) () -> "There is no format specified for ontology "
            + getOntologyID() + ", the ontology format needs to be set before saving or specified in the save call");
    }

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
    Stream<OWLOntology> imports();

    /**
     * Gets the set of imports declarations for this ontology. The set returned
     * represents the set of IRIs that correspond to the set of IRIs in an
     * ontology's directlyImportsDocuments (see Section 3 in the OWL 2
     * structural specification).
     *
     * @return Sorted stream of imports declarations that correspond to the set
     *         of ontology document IRIs that are directly imported by this
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
     * @return The sorted set that is returned is a copy of the axioms in the
     *         ontology - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
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
     * @see #classesInSignature()
     * @see #objectPropertiesInSignature()
     * @see #dataPropertiesInSignature()
     * @see #individualsInSignature()
     */
    default Stream<OWLEntity> signature(Imports imports) {
        return imports.stream(this).flatMap(OWLOntology::signature);
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
    default Stream<OWLEntity> unsortedSignature(Imports imports) {
        return imports.stream(this).flatMap(OWLOntology::unsortedSignature);
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

    @Override
    default void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    /**
     * @param add
     *        true if missing declarations should be added. If false, no
     *        declarations will be added.
     * @return collection of IRIS used in illegal punnings
     */
    default Collection<IRI> determineIllegalPunnings(boolean add) {
        if (!add) {
            return Collections.emptySet();
        }
        return illegalPunnings(add, asList(this.unsortedSignature(Imports.INCLUDED)));
    }

    /**
     * @param add
     *        true if missing declarations should be added. If false, no
     *        declarations will be added.
     * @param signature
     *        signature to explore.
     * @return collection of IRIS used in illegal punnings
     */
    static Collection<IRI> illegalPunnings(boolean add, Collection<OWLEntity> signature) {
        if (!add) {
            return Collections.emptySet();
        }
        // determine what entities are illegally punned
        Map<IRI, List<EntityType<?>>> punnings = new HashMap<>();
        // disregard individuals as they do not give raise to illegal
        // punnings; only keep track of punned entities, ignore the rest
        Collection<IRI> punnedEntities = getPunnedIRIs(signature.stream().distinct());
        signature.stream().distinct().filter(e -> !e.isOWLNamedIndividual() && punnedEntities.contains(e.getIRI()))
            .forEach(e -> punnings.computeIfAbsent(e.getIRI(), x -> new ArrayList<>()).add(e.getEntityType()));
        return computeIllegals(punnings);
    }

    /**
     * @param punnings
     *        input punnings
     * @return illegal punnings
     */
    static Collection<IRI> computeIllegals(Map<IRI, List<EntityType<?>>> punnings) {
        Collection<IRI> illegals = new HashSet<>();
        punnings.forEach((i, puns) -> computeIllegal(illegals, i, puns));
        return illegals;
    }

    /**
     * @param illegals
     *        set of illegal punnings
     * @param i
     *        iri to checl
     * @param puns
     *        list of pun types
     */
    static void computeIllegal(Collection<IRI> illegals, IRI i, List<EntityType<?>> puns) {
        boolean hasObject = puns.contains(EntityType.OBJECT_PROPERTY);
        boolean hasAnnotation = puns.contains(EntityType.ANNOTATION_PROPERTY);
        boolean hasData = puns.contains(EntityType.DATA_PROPERTY);
        if (hasObject && hasAnnotation || hasData && hasAnnotation || hasData && hasObject
            || puns.contains(EntityType.DATATYPE) && puns.contains(EntityType.CLASS)) {
            illegals.add(i);
        }
    }

    /**
     * Calculates the set of IRIs that are used for more than one entity type.
     *
     * @param signature
     *        signature to explore.
     * @return punned IRIs.
     */
    static Set<IRI> getPunnedIRIs(Stream<OWLEntity> signature) {
        Set<IRI> punned = new HashSet<>();
        Set<IRI> test = new HashSet<>();
        Predicate<IRI> tested = e -> !test.add(e);
        signature.map(OWLEntity::getIRI).filter(tested).forEach(punned::add);
        if (punned.isEmpty()) {
            return Collections.emptySet();
        }
        return punned;
    }

    /**
     * @return prefix manager assocated with this ontology. An ontology always
     *         has a prefix manager instance.
     */
    PrefixManager getPrefixManager();

    /**
     * @param prefixManager
     *        replacement PrefixManager instance to be used for this ontology.
     */
    void setPrefixManager(PrefixManager prefixManager);
}
