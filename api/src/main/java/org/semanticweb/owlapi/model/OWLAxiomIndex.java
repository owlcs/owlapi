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

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/**
 * Axiom accessor methods - all OWLOntology methods that return sets of axioms
 * of a certain type or with a certain entity referred.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public interface OWLAxiomIndex {

    /**
     * Generic search method: resutns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param type
     *        type of axioms
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param includeImports
     *        if INCLUDED, include imports closure.
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    @Nonnull
    <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, OWLObject entity,
            @Nonnull Imports includeImports, @Nonnull Search forSubPosition);

    /**
     * Generic filter type for further refining search by axiom type. The
     * returned axioms are both belonging to one of the types listed by the
     * filter and satisfying its pass condition.
     * 
     * @param filter
     *        the filter to match
     * @param key
     *        the key. Its type is generic and it is used only by the filter.
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return a collection of axioms matching the request. The axioms are
     *         collected from a set, therefore the collection contains no
     *         duplicates. It is a copy of the data.
     */
    @Nonnull
    <T extends OWLAxiom> Collection<T> filterAxioms(
            OWLAxiomSearchFilter filter, Object key,
            @Nonnull Imports includeImportsClosure);

    /**
     * Generic containment check type for further refining search by axiom type.
     * The method returns true if there is at least one result matching the
     * filter.
     * 
     * @param filter
     *        the filter to match
     * @param key
     *        the key. Its type is generic and it is used only by the filter.
     * @param includeImportsClosure
     *        if INCLUDED, include imports closure.
     * @return true if there is at least one result matching the filter.
     */
    @Nonnull
    boolean contains(OWLAxiomSearchFilter filter, Object key,
            @Nonnull Imports includeImportsClosure);

    /**
     * Generic search method: resutns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param type
     *        type of axioms
     * @param explicitClass
     *        for overlapping indexes in the ontology internals, an explicit
     *        class rathet than the entity class might be necessary
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param includeImports
     *        if INCLUDED, include imports closure.
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    @Nonnull
    <T extends OWLAxiom> Set<T> getAxioms(Class<T> type,
            Class<? extends OWLObject> explicitClass, OWLObject entity,
            @Nonnull Imports includeImports, @Nonnull Search forSubPosition);

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Annotation axioms
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the {@code SubAnnotationPropertyOfAxiom}s where the specified
     * property is the sub-property.
     * 
     * @param subProperty
     *        The sub-property of the axioms to be retrieved.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(
            @Nonnull OWLAnnotationProperty subProperty);

    /**
     * Gets the {@code OWLAnnotationPropertyDomainAxiom}s where the specified
     * property is the property in the domain axiom.
     * 
     * @param property
     *        The property that the axiom specifies a domain for.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(
            @Nonnull OWLAnnotationProperty property);

    /**
     * Gets the {@code OWLAnnotationPropertyRangeAxiom}s where the specified
     * property is the property in the range axiom.
     * 
     * @param property
     *        The property that the axiom specifies a range for.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(
            @Nonnull OWLAnnotationProperty property);

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Various methods that provide axioms relating to specific entities that
    // allow
    // frame style views to be composed for a particular entity. Such
    // functionality is
    // useful for ontology editors and browsers.
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the declaration axioms for specified entity.
     * 
     * @param subject
     *        The entity that is the subject of the set of returned axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDeclarationAxiom> getDeclarationAxioms(@Nonnull OWLEntity subject);

    /**
     * Gets the axioms that annotate the specified entity.
     * 
     * @param entity
     *        The entity whose annotations are to be retrieved.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(
            @Nonnull OWLAnnotationSubject entity);

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Classes
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets all of the subclass axioms where the left hand side (the subclass)
     * is equal to the specified class.
     * 
     * @param cls
     *        The class that is equal to the left hand side of the axiom
     *        (subclass).
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(@Nonnull OWLClass cls);

    /**
     * Gets all of the subclass axioms where the right hand side (the
     * superclass) is equal to the specified class.
     * 
     * @param cls
     *        The class
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSubClassOfAxiom>
            getSubClassAxiomsForSuperClass(@Nonnull OWLClass cls);

    /**
     * Gets all of the equivalent axioms in this ontology that contain the
     * specified class as an operand.
     * 
     * @param cls
     *        The class to search
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(
            @Nonnull OWLClass cls);

    /**
     * Gets the set of disjoint class axioms that contain the specified class as
     * an operand.
     * 
     * @param cls
     *        The class to search
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDisjointClassesAxiom>
            getDisjointClassesAxioms(@Nonnull OWLClass cls);

    /**
     * Gets the set of disjoint union axioms that have the specified class as
     * the named class that is equivalent to the disjoint union of operands. For
     * example, if the ontology contained the axiom DisjointUnion(A, propP some
     * C, D, E) this axiom would be returned for class A (but not for D or E).
     * 
     * @param owlClass
     *        The class that indexes the axioms to be retrieved.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDisjointUnionAxiom>
            getDisjointUnionAxioms(@Nonnull OWLClass owlClass);

    /**
     * Gets the has key axioms that have the specified class as their subject.
     * 
     * @param cls
     *        The subject of the has key axioms
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLHasKeyAxiom> getHasKeyAxioms(@Nonnull OWLClass cls);

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object properties
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the {@link OWLSubObjectPropertyOfAxiom} s where the sub-property is
     * equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
            @Nonnull OWLObjectPropertyExpression subProperty);

    /**
     * Gets the {@link OWLSubObjectPropertyOfAxiom} s where the super-property
     * is equal to the specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSuperProperty(
                    @Nonnull OWLObjectPropertyExpression superProperty);

    /**
     * Gets the {@link OWLObjectPropertyDomainAxiom}s where the property is
     * equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLObjectPropertyRangeAxiom} s where the property is
     * equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLInverseObjectPropertiesAxiom}s where the specified
     * property is contained in the set returned by
     * {@link OWLInverseObjectPropertiesAxiom#getProperties()} .
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLEquivalentObjectPropertiesAxiom}s that make the
     * specified property equivalent to some other object property
     * expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms make equivalent to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertiesAxioms(
                    @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLDisjointObjectPropertiesAxiom}s that make the
     * specified property disjoint with some other object property
     * expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms makes disjoint to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLFunctionalObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property functional.
     * 
     * @param property
     *        The property that is made functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLInverseFunctionalObjectPropertyAxiom}s contained in
     * this ontology that make the specified object property inverse functional.
     * 
     * @param property
     *        The property that is made inverse functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLSymmetricObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property symmetric.
     * 
     * @param property
     *        The property that is made symmetric by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLAsymmetricObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property asymmetric.
     * 
     * @param property
     *        The property that is made asymmetric by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLReflexiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property reflexive.
     * 
     * @param property
     *        The property that is made reflexive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLIrreflexiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property irreflexive.
     * 
     * @param property
     *        The property that is made irreflexive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLTransitiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property transitive.
     * 
     * @param property
     *        The property that is made transitive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data properties
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the {@link OWLSubDataPropertyOfAxiom}s where the sub-property is
     * equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(
            @Nonnull OWLDataProperty subProperty);

    /**
     * Gets the {@link OWLSubDataPropertyOfAxiom}s where the super-property is
     * equal to the specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
            @Nonnull OWLDataPropertyExpression superProperty);

    /**
     * Gets the {@link OWLDataPropertyDomainAxiom}s where the property is equal
     * to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the {@link OWLDataPropertyRangeAxiom}s where the property is equal
     * to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the {@link OWLEquivalentDataPropertiesAxiom}s that make the
     * specified property equivalent to some other data property expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms make equivalent to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the {@link OWLDisjointDataPropertiesAxiom}s that make the specified
     * property disjoint with some other data property expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms makes disjoint to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the {@link OWLFunctionalDataPropertyAxiom}s contained in this
     * ontology that make the specified data property functional.
     * 
     * @param property
     *        The property that is made functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(
            @Nonnull OWLDataPropertyExpression property);

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Individuals
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the {@link OWLClassAssertionAxiom}s contained in this ontology that
     * make the specified {@code individual} an instance of some class
     * expression.
     * 
     * @param individual
     *        The individual that the returned axioms make an instance of some
     *        class expression.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link OWLClassAssertionAxiom}s contained in this ontology that
     * make the specified class expression, {@code ce}, a type for some
     * individual.
     * 
     * @param ce
     *        The class expression that the returned axioms make a type for some
     *        individual.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            @Nonnull OWLClassExpression ce);

    /**
     * Gets the {@link OWLDataPropertyAssertionAxiom}s contained in this
     * ontology that have the specified {@code individual} as the subject of the
     * axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link OWLObjectPropertyAssertionAxiom}s contained in this
     * ontology that have the specified {@code individual} as the subject of the
     * axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link OWLNegativeObjectPropertyAssertionAxiom} s contained in
     * this ontology that have the specified {@code individual} as the subject
     * of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxioms(
                    @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link OWLNegativeDataPropertyAssertionAxiom} s contained in
     * this ontology that have the specified {@code individual} as the subject
     * of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxioms(
                    @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link OWLSameIndividualAxiom}s contained in this ontology that
     * make the specified {@code individual} the same as some other individual.
     * 
     * @param individual
     *        The individual that the returned axioms make the same as some
     *        other individual.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link OWLDifferentIndividualsAxiom}s contained in this ontology
     * that make the specified {@code individual} different to some other
     * individual.
     * 
     * @param individual
     *        The individual that the returned axioms make the different as some
     *        other individual.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link OWLDatatypeDefinitionAxiom}s contained in this ontology
     * that provide a definition for the specified datatype.
     * 
     * @param datatype
     *        The datatype for which the returned axioms provide a definition.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Nonnull
    Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
            @Nonnull OWLDatatype datatype);
}
