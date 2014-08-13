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

/**
 * Ontology methods related to its signature. This interface differs from
 * OWLSignature because it uses boolean arguments to determine import closure
 * inclusion.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public interface OWLSignatureBooleanArgs extends HasGetEntitiesInSignature,
        HasClassesInSignature, HasObjectPropertiesInSignature,
        HasDataPropertiesInSignature, HasDatatypesInSignature,
        HasIndividualsInSignature, HasContainsEntityInSignature {

    /**
     * Gets the classes in the signature and optionally the imports closure.
     * 
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return the set of classes in the signature, optionally including the
     *         import closure. The set that is returned is a copy of the data.
     */
    @Deprecated
    @Nonnull
    Set<OWLClass> getClassesInSignature(boolean includeImportsClosure);

    /**
     * Gets the object properties in the signature and optionally the imports
     * closure.
     * 
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return the set of object properties in the signature, optionally
     *         including the import closure. The set that is returned is a copy
     *         of the data.
     */
    @Deprecated
    @Nonnull
    Set<OWLObjectProperty> getObjectPropertiesInSignature(
            boolean includeImportsClosure);

    /**
     * Gets the data properties in the signature and optionally the imports
     * closure.
     * 
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return the set of data properties in the signature, optionally including
     *         the import closure. The set that is returned is a copy of the
     *         data.
     */
    @Deprecated
    @Nonnull
    Set<OWLDataProperty> getDataPropertiesInSignature(
            boolean includeImportsClosure);

    /**
     * Gets the named individuals in the signature and optionally the imports
     * closure.
     * 
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return the set of individuals in the signature, optionally including the
     *         import closure. The set that is returned is a copy of the data.
     */
    @Deprecated
    @Nonnull
    Set<OWLNamedIndividual> getIndividualsInSignature(
            boolean includeImportsClosure);

    /**
     * Gets the referenced anonymous individuals in the signature and optionally
     * the imports closure.
     * 
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return The set of referenced anonymous individuals
     */
    @Deprecated
    @Nonnull
    Set<OWLAnonymousIndividual> getReferencedAnonymousIndividuals(
            boolean includeImportsClosure);

    /**
     * Gets the datatypes in the signature and optionally the imports closure.
     * 
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return the set of datatypes in the signature of this ontology,
     *         optionally including the import closure. The set that is returned
     *         is a copy of the data.
     */
    @Deprecated
    @Nonnull
    Set<OWLDatatype> getDatatypesInSignature(boolean includeImportsClosure);

    /**
     * Gets the annotation properties in the signature and optionally the
     * imports closure.
     * 
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return the set of annotation properties in the signature, optionally
     *         including the import closure. The set that is returned is a copy
     *         of the data.
     */
    @Deprecated
    @Nonnull
    Set<OWLAnnotationProperty> getAnnotationPropertiesInSignature(
            boolean includeImportsClosure);

    /**
     * Determines if the signature contains the specified entity.
     * 
     * @param owlEntity
     *        The entity
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return {@code true} if the signature or the import closure contains a
     *         reference to the specified entity.
     */
    @Deprecated
    boolean containsEntityInSignature(@Nonnull OWLEntity owlEntity,
            boolean includeImportsClosure);

    /**
     * Determines if the signature contains an entity with the specified IRI.
     * 
     * @param entityIRI
     *        The IRI to test for.
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if the signature or the import closure contains an entity
     *         with the specified IRI.
     */
    @Deprecated
    boolean containsEntityInSignature(@Nonnull IRI entityIRI,
            boolean includeImportsClosure);

    // Access by IRI
    /**
     * Determines if the signature contains an OWLClass that has the specified
     * IRI.
     * 
     * @param owlClassIRI
     *        The IRI of the class to check for
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if the signature or the import closure contains an entity
     *         with the specified IRI.
     */
    @Deprecated
    boolean containsClassInSignature(@Nonnull IRI owlClassIRI,
            boolean includeImportsClosure);

    /**
     * Determines if the signature contains an OWLObjectProperty that has the
     * specified IRI.
     * 
     * @param owlObjectPropertyIRI
     *        The IRI of the OWLObjectProperty to check for
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if the signature or the import closure contains an object
     *         property with the specified IRI.
     */
    @Deprecated
    boolean containsObjectPropertyInSignature(
            @Nonnull IRI owlObjectPropertyIRI, boolean includeImportsClosure);

    /**
     * Determines if the signature contains an OWLDataProperty that has the
     * specified IRI.
     * 
     * @param owlDataPropertyIRI
     *        The IRI of the OWLDataProperty to check for
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if the signature or the import closure contains a data
     *         property with the specified IRI.
     */
    @Deprecated
    boolean containsDataPropertyInSignature(@Nonnull IRI owlDataPropertyIRI,
            boolean includeImportsClosure);

    /**
     * Determines if the signature contains an OWLAnnotationProperty that has
     * the specified IRI.
     * 
     * @param owlAnnotationPropertyIRI
     *        The IRI of the OWLAnnotationProperty to check for
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if the signature or the import closure contains an
     *         annotation property with the specified IRI.
     */
    @Deprecated
    boolean
            containsAnnotationPropertyInSignature(
                    @Nonnull IRI owlAnnotationPropertyIRI,
                    boolean includeImportsClosure);

    /**
     * Determines if the signature contains an OWLDatatype that has the
     * specified IRI.
     * 
     * @param owlDatatypeIRI
     *        The IRI of the OWLDatatype to check for
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if the signature or the import closure contains a datatype
     *         with the specified IRI.
     */
    @Deprecated
    boolean containsDatatypeInSignature(@Nonnull IRI owlDatatypeIRI,
            boolean includeImportsClosure);

    /**
     * Determines if the signature contains an OWLNamedIndividual that has the
     * specified IRI.
     * 
     * @param owlIndividualIRI
     *        The IRI of the OWLNamedIndividual to check for
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if the signature or the import closure contains an
     *         individual with the specified IRI.
     */
    @Deprecated
    boolean containsIndividualInSignature(@Nonnull IRI owlIndividualIRI,
            boolean includeImportsClosure);

    /**
     * Gets the entities in the signature that have the specified IRI.
     * 
     * @param iri
     *        The IRI of the entitied to be retrieved.
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return the set of entities with the specified IRI, optionally including
     *         the ones in the import closure.
     */
    @Deprecated
    @Nonnull
    Set<OWLEntity> getEntitiesInSignature(@Nonnull IRI iri,
            boolean includeImportsClosure);

    /**
     * @param entity
     *        entyty to check
     * @param includeImportsClosure
     *        if true, include imports closure.
     * @return true if entity is referenced
     */
    @Deprecated
    boolean containsReference(@Nonnull OWLEntity entity,
            boolean includeImportsClosure);
}
