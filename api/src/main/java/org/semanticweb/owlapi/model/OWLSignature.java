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

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * Ontology methods related to its signature.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public interface OWLSignature extends HasGetEntitiesInSignature,
        HasClassesInSignature, HasObjectPropertiesInSignature,
        HasDataPropertiesInSignature, HasDatatypesInSignature,
        HasIndividualsInSignature, HasContainsEntityInSignature,
        HasImportsClosure {

    /**
     * Gets the classes in the signature and optionally the imports closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of classes in the signature, optionally including the
     *         import closure. The set that is returned is a copy of the data.
     */
    @Deprecated
    @Nonnull
    default Set<OWLClass> getClassesInSignature(@Nonnull Imports imports) {
        return asSet(classesInSignature(imports));
    }

    /**
     * Gets the classes in the signature and optionally the imports closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of classes in the signature, optionally including the
     *         import closure. The set that is returned is a copy of the data.
     */
    @Nonnull
    default Stream<OWLClass> classesInSignature(@Nonnull Imports imports) {
        return imports.stream(this).flatMap(o -> o.classesInSignature());
    }

    /**
     * Gets the object properties in the signature and optionally the imports
     * closure.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return the set of object properties in the signature, optionally
     *         including the import closure. The set that is returned is a copy
     *         of the data.
     */
    @Deprecated
    @Nonnull
    default Set<OWLObjectProperty> getObjectPropertiesInSignature(
            @Nonnull Imports includeImportsClosure) {
        return asSet(objectPropertiesInSignature(includeImportsClosure));
    }

    /**
     * Gets the object properties in the signature and optionally the imports
     * closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of object properties in the signature, optionally
     *         including the import closure. The set that is returned is a copy
     *         of the data.
     */
    @Nonnull
    default Stream<OWLObjectProperty> objectPropertiesInSignature(
            @Nonnull Imports imports) {
        return imports.stream(this).flatMap(
                o -> o.objectPropertiesInSignature());
    }

    /**
     * Gets the data properties in the signature and optionally the imports
     * closure.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return the set of data properties in the signature, optionally including
     *         the import closure. The set that is returned is a copy of the
     *         data.
     */
    @Deprecated
    @Nonnull
    default Set<OWLDataProperty> getDataPropertiesInSignature(
            @Nonnull Imports includeImportsClosure) {
        return asSet(dataPropertiesInSignature(includeImportsClosure));
    }

    /**
     * Gets the data properties in the signature and optionally the imports
     * closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of data properties in the signature, optionally including
     *         the import closure. The set that is returned is a copy of the
     *         data.
     */
    @Nonnull
    default Stream<OWLDataProperty> dataPropertiesInSignature(
            @Nonnull Imports imports) {
        return imports.stream(this).flatMap(o -> o.dataPropertiesInSignature());
    }

    /**
     * Gets the named individuals in the signature and optionally the imports
     * closure.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return the set of individuals in the signature, optionally including the
     *         import closure. The set that is returned is a copy of the data.
     */
    @Deprecated
    @Nonnull
    default Set<OWLNamedIndividual> getIndividualsInSignature(
            @Nonnull Imports includeImportsClosure) {
        return asSet(individualsInSignature(includeImportsClosure));
    }

    /**
     * Gets the named individuals in the signature and optionally the imports
     * closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of individuals in the signature, optionally including the
     *         import closure. The set that is returned is a copy of the data.
     */
    @Nonnull
    default Stream<OWLNamedIndividual> individualsInSignature(
            @Nonnull Imports imports) {
        return imports.stream(this).flatMap(o -> o.individualsInSignature());
    }

    /**
     * Gets the referenced anonymous individuals in the signature and optionally
     * the imports closure.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return The set of referenced anonymous individuals
     */
    @Deprecated
    @Nonnull
    default Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(
            @Nonnull Imports includeImportsClosure) {
        return asSet(referencedAnonymousIndividuals(includeImportsClosure));
    }

    /**
     * Gets the referenced anonymous individuals in the signature and optionally
     * the imports closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return The set of referenced anonymous individuals
     */
    @Nonnull
    default Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals(
            @Nonnull Imports imports) {
        return imports.stream(this).flatMap(
                o -> o.referencedAnonymousIndividuals());
    }

    /**
     * Gets the referenced anonymous individuals.
     * 
     * @return The set of referenced anonymous individuals
     */
    @Deprecated
    @Nonnull
    default Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals() {
        return asSet(referencedAnonymousIndividuals());
    }

    /**
     * Gets the referenced anonymous individuals.
     * 
     * @return The set of referenced anonymous individuals
     */
    @Nonnull
    Stream<OWLAnonymousIndividual> referencedAnonymousIndividuals();

    /**
     * Gets the datatypes in the signature and optionally the imports closure.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return the set of datatypes in the signature of this ontology,
     *         optionally including the import closure. The set that is returned
     *         is a copy of the data.
     */
    @Nonnull
    @Deprecated
    default Set<OWLDatatype> getDatatypesInSignature(
            @Nonnull Imports includeImportsClosure) {
        return asSet(datatypesInSignature(includeImportsClosure));
    }

    /**
     * Gets the datatypes in the signature and optionally the imports closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of datatypes in the signature of this ontology,
     *         optionally including the import closure. The set that is returned
     *         is a copy of the data.
     */
    @Nonnull
    default Stream<OWLDatatype> datatypesInSignature(@Nonnull Imports imports) {
        return imports.stream(this).flatMap(o -> o.datatypesInSignature());
    }

    /**
     * Gets the annotation properties in the signature and optionally the
     * imports closure.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return the set of annotation properties in the signature, optionally
     *         including the import closure. The set that is returned is a copy
     *         of the data.
     */
    @Deprecated
    @Nonnull
    default Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(
            @Nonnull Imports includeImportsClosure) {
        return asSet(annotationPropertiesInSignature(includeImportsClosure));
    }

    /**
     * Gets the annotation properties in the signature and optionally the
     * imports closure.
     * 
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of annotation properties in the signature, optionally
     *         including the import closure. The set that is returned is a copy
     *         of the data.
     */
    @Nonnull
    default Stream<OWLAnnotationProperty> annotationPropertiesInSignature(
            @Nonnull Imports imports) {
        return imports.stream(this).flatMap(
                o -> o.annotationPropertiesInSignature());
    }

    /**
     * Determines if the signature contains the specified entity.
     * 
     * @param owlEntity
     *        The entity
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return {@code true} if the signature or the import closure contains a
     *         reference to the specified entity.
     */
    default boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity,
            @Nonnull Imports imports) {
        return imports.stream(this).anyMatch(
                o -> o.containsEntityInSignature(owlEntity));
    }

    /**
     * Determines if the signature contains an entity with the specified IRI.
     * 
     * @param entityIRI
     *        The IRI to test for.
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return true if the signature or the import closure contains an entity
     *         with the specified IRI.
     */
    default boolean containsEntityInSignature(@Nonnull IRI entityIRI,
            @Nonnull Imports imports) {
        return imports.stream(this).anyMatch(
                o -> o.containsEntityInSignature(entityIRI));
    }

    // Access by IRI
    /**
     * Determines if the signature contains an OWLClass that has the specified
     * IRI.
     * 
     * @param owlClassIRI
     *        The IRI of the class to check for
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return true if the signature or the import closure contains an entity
     *         with the specified IRI.
     */
    boolean containsClassInSignature(@Nonnull IRI owlClassIRI,
            @Nonnull Imports includeImportsClosure);

    /**
     * Determines if the signature contains an OWLObjectProperty that has the
     * specified IRI.
     * 
     * @param owlObjectPropertyIRI
     *        The IRI of the OWLObjectProperty to check for
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return true if the signature or the import closure contains an object
     *         property with the specified IRI.
     */
    boolean containsObjectPropertyInSignature(
            @Nonnull IRI owlObjectPropertyIRI,
            @Nonnull Imports includeImportsClosure);

    /**
     * Determines if the signature contains an OWLDataProperty that has the
     * specified IRI.
     * 
     * @param owlDataPropertyIRI
     *        The IRI of the OWLDataProperty to check for
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return true if the signature or the import closure contains a data
     *         property with the specified IRI.
     */
    boolean containsDataPropertyInSignature(@Nonnull IRI owlDataPropertyIRI,
            @Nonnull Imports includeImportsClosure);

    /**
     * Determines if the signature contains an OWLAnnotationProperty that has
     * the specified IRI.
     * 
     * @param owlAnnotationPropertyIRI
     *        The IRI of the OWLAnnotationProperty to check for
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return true if the signature or the import closure contains an
     *         annotation property with the specified IRI.
     */
    boolean containsAnnotationPropertyInSignature(
            @Nonnull IRI owlAnnotationPropertyIRI,
            @Nonnull Imports includeImportsClosure);

    /**
     * Determines if the signature contains an OWLDatatype that has the
     * specified IRI.
     * 
     * @param owlDatatypeIRI
     *        The IRI of the OWLDatatype to check for
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return true if the signature or the import closure contains a datatype
     *         with the specified IRI.
     */
    boolean containsDatatypeInSignature(@Nonnull IRI owlDatatypeIRI,
            @Nonnull Imports includeImportsClosure);

    /**
     * Determines if the signature contains an OWLNamedIndividual that has the
     * specified IRI.
     * 
     * @param owlIndividualIRI
     *        The IRI of the OWLNamedIndividual to check for
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return true if the signature or the import closure contains an
     *         individual with the specified IRI.
     */
    boolean containsIndividualInSignature(@Nonnull IRI owlIndividualIRI,
            @Nonnull Imports includeImportsClosure);

    /**
     * Determines if the signature contains an OWLDatatype that has the
     * specified IRI.
     * 
     * @param owlDatatypeIRI
     *        The IRI of the OWLDatatype to check for
     * @return true if the signature or the import closure contains a datatype
     *         with the specified IRI.
     */
    boolean containsDatatypeInSignature(@Nonnull IRI owlDatatypeIRI);

    /**
     * Determines if the signature contains an entity with the specified IRI.
     * 
     * @param entityIRI
     *        The IRI to test for.
     * @return true if the signature or the import closure contains an entity
     *         with the specified IRI.
     */
    default boolean containsEntityInSignature(@Nonnull IRI entityIRI) {
        return containsClassInSignature(entityIRI)
                || containsObjectPropertyInSignature(entityIRI)
                || containsDataPropertyInSignature(entityIRI)
                || containsIndividualInSignature(entityIRI)
                || containsDatatypeInSignature(entityIRI)
                || containsAnnotationPropertyInSignature(entityIRI);
    }

    // Access by IRI
    /**
     * Determines if the signature contains an OWLClass that has the specified
     * IRI.
     * 
     * @param owlClassIRI
     *        The IRI of the class to check for
     * @return true if the signature or the import closure contains an entity
     *         with the specified IRI.
     */
    boolean containsClassInSignature(@Nonnull IRI owlClassIRI);

    /**
     * Determines if the signature contains an OWLObjectProperty that has the
     * specified IRI.
     * 
     * @param owlObjectPropertyIRI
     *        The IRI of the OWLObjectProperty to check for
     * @return true if the signature or the import closure contains an object
     *         property with the specified IRI.
     */
            boolean
            containsObjectPropertyInSignature(@Nonnull IRI owlObjectPropertyIRI);

    /**
     * Determines if the signature contains an OWLDataProperty that has the
     * specified IRI.
     * 
     * @param owlDataPropertyIRI
     *        The IRI of the OWLDataProperty to check for
     * @return true if the signature or the import closure contains a data
     *         property with the specified IRI.
     */
    boolean containsDataPropertyInSignature(@Nonnull IRI owlDataPropertyIRI);

    /**
     * Determines if the signature contains an OWLAnnotationProperty that has
     * the specified IRI.
     * 
     * @param owlAnnotationPropertyIRI
     *        The IRI of the OWLAnnotationProperty to check for
     * @return true if the signature or the import closure contains an
     *         annotation property with the specified IRI.
     */
    boolean containsAnnotationPropertyInSignature(
            @Nonnull IRI owlAnnotationPropertyIRI);

    /**
     * Determines if the signature contains an OWLNamedIndividual that has the
     * specified IRI.
     * 
     * @param owlIndividualIRI
     *        The IRI of the OWLNamedIndividual to check for
     * @return true if the signature or the import closure contains an
     *         individual with the specified IRI.
     */
    boolean containsIndividualInSignature(@Nonnull IRI owlIndividualIRI);

    /**
     * Gets the entities in the signature that have the specified IRI.
     * 
     * @param iri
     *        The IRI of the entitied to be retrieved.
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return the set of entities with the specified IRI, optionally including
     *         the ones in the import closure.
     */
    @Deprecated
    @Nonnull
    default Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri,
            @Nonnull Imports includeImportsClosure) {
        return asSet(entitiesInSignature(iri, includeImportsClosure));
    }

    /**
     * Gets the entities in the signature that have the specified IRI.
     * 
     * @param iri
     *        The IRI of the entitied to be retrieved.
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return the set of entities with the specified IRI, optionally including
     *         the ones in the import closure.
     */
    @Nonnull
    default Stream<OWLEntity> entitiesInSignature(@Nonnull IRI iri,
            @Nonnull Imports imports) {
        return imports.stream(this).flatMap(o -> o.entitiesInSignature(iri));
    }

    /**
     * Calculates the set of IRIs that are used for more than one entity type.
     * 
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return punned IRIs.
     */
    @Nonnull
    Set<IRI> getPunnedIRIs(@Nonnull Imports includeImportsClosure);

    /**
     * @param entity
     *        entyty to check
     * @param imports
     *        if INCLUDED, include imports closure.
     * @return true if entity is referenced
     */
    default boolean containsReference(@Nonnull OWLEntity entity,
            @Nonnull Imports imports) {
        return imports.stream(this).anyMatch(o -> o.containsReference(entity));
    }

    /**
     * @param entity
     *        entyty to check
     * @return true if entity is referenced
     */
    boolean containsReference(@Nonnull OWLEntity entity);
}
