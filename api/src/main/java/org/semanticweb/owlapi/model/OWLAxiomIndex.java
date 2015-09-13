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

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.model.parameters.Navigation.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.model.parameters.Navigation;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/**
 * Axiom accessor methods - all OWLOntology methods that return sets of axioms
 * of a certain type or with a certain entity referred.
 * 
 * @author ignazio
 * @since 4.0.0
 */
public interface OWLAxiomIndex extends HasImportsClosure {

    /**
     * Generic search method: returns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
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
    @Deprecated
    default <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, OWLObject entity, Imports includeImports,
        Navigation forSubPosition) {
        return asSet(axioms(type, entity, includeImports, forSubPosition));
    }

    /**
     * Generic search method: returns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
     * @param type
     *        type of axioms
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param imports
     *        if INCLUDED, include imports closure.
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    default <T extends OWLAxiom> Stream<T> axioms(Class<T> type, OWLObject entity, Imports imports,
        Navigation forSubPosition) {
        return imports.stream(this).flatMap(o -> o.axioms(type, entity.getClass(), entity, forSubPosition));
    }

    /**
     * Generic search method: returns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
     * @param type
     *        type of axioms
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    @Deprecated
    default <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, OWLObject entity, Navigation forSubPosition) {
        return asSet(axioms(type, entity, forSubPosition));
    }

    /**
     * Generic search method: returns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
     * @param type
     *        type of axioms
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    default <T extends OWLAxiom> Stream<T> axioms(Class<T> type, OWLObject entity, Navigation forSubPosition) {
        return axioms(type, entity.getClass(), entity, forSubPosition);
    }

    /**
     * Generic filter type for further refining search by axiom type. The
     * returned axioms are both belonging to one of the types listed by the
     * filter and satisfying its pass condition.
     * 
     * @param <T>
     *        type of returned axioms
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
    @Deprecated
    default <T extends OWLAxiom> Collection<T> filterAxioms(OWLAxiomSearchFilter filter, Object key,
        Imports includeImportsClosure) {
        return asSet(axioms(filter, key, includeImportsClosure));
    }

    /**
     * Generic filter type for further refining search by axiom type. The
     * returned axioms are both belonging to one of the types listed by the
     * filter and satisfying its pass condition.
     * 
     * @param <T>
     *        type of returned axioms
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
    <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key, Imports includeImportsClosure);

    /**
     * Generic filter type for further refining search by axiom type. The
     * returned axioms are both belonging to one of the types listed by the
     * filter and satisfying its pass condition.
     * 
     * @param <T>
     *        type of returned axioms
     * @param filter
     *        the filter to match
     * @param key
     *        the key. Its type is generic and it is used only by the filter.
     * @return a collection of axioms matching the request. The axioms are
     *         collected from a set, therefore the collection contains no
     *         duplicates. It is a copy of the data.
     */
    @Deprecated
    default <T extends OWLAxiom> Collection<T> filterAxioms(OWLAxiomSearchFilter filter, Object key) {
        return asSet(axioms(filter, key));
    }

    /**
     * Generic filter type for further refining search by axiom type. The
     * returned axioms are both belonging to one of the types listed by the
     * filter and satisfying its pass condition.
     * 
     * @param <T>
     *        type of returned axioms
     * @param filter
     *        the filter to match
     * @param key
     *        the key. Its type is generic and it is used only by the filter.
     * @return a collection of axioms matching the request. The axioms are
     *         collected from a set, therefore the collection contains no
     *         duplicates. It is a copy of the data.
     */
    <T extends OWLAxiom> Stream<T> axioms(OWLAxiomSearchFilter filter, Object key);

    /**
     * Generic containment check type for further refining search by axiom type.
     * The method returns true if there is at least one result matching the
     * filter.
     * 
     * @param filter
     *        the filter to match
     * @param key
     *        the key. Its type is generic and it is used only by the filter.
     * @return true if there is at least one result matching the filter.
     */
    boolean contains(OWLAxiomSearchFilter filter, Object key);

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
    boolean contains(OWLAxiomSearchFilter filter, Object key, Imports includeImportsClosure);

    /**
     * Generic search method: resutns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
     * @param type
     *        type of axioms
     * @param explicitClass
     *        for overlapping indexes in the ontology internals, an explicit
     *        class rathet than the entity class might be necessary
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    @Deprecated
    default <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, Class<? extends OWLObject> explicitClass,
        OWLObject entity, Navigation forSubPosition) {
        return asSet(axioms(type, explicitClass, entity, forSubPosition));
    }

    /**
     * Generic search method: resutns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
     * @param type
     *        type of axioms
     * @param explicitClass
     *        for overlapping indexes in the ontology internals, an explicit
     *        class rathet than the entity class might be necessary
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    <T extends OWLAxiom> Stream<T> axioms(Class<T> type, Class<? extends OWLObject> explicitClass, OWLObject entity,
        Navigation forSubPosition);

    /**
     * Generic search method: resutns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
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
    @Deprecated
    default <T extends OWLAxiom> Set<T> getAxioms(Class<T> type, Class<? extends OWLObject> explicitClass,
        OWLObject entity, Imports includeImports, Navigation forSubPosition) {
        return asSet(axioms(type, explicitClass, entity, includeImports, forSubPosition));
    }

    /**
     * Generic search method: resutns all axioms which refer entity, are
     * instances of type, optionally including the imports closure in the
     * results.
     * 
     * @param <T>
     *        type of returned axioms
     * @param type
     *        type of axioms
     * @param explicitClass
     *        for overlapping indexes in the ontology internals, an explicit
     *        class rathet than the entity class might be necessary
     * @param entity
     *        referred entity (OWLPrimitive or property/class expression)
     * @param imports
     *        if INCLUDED, include imports closure.
     * @param forSubPosition
     *        for sub axioms (subclass, subproperty), the value specifies
     *        whether entity should appear as sub or super entity in the axioms
     *        returned. For axiom types that have no sub/super entites, this
     *        parameter is ignored.
     * @return set of axioms satisfying the conditions. The set is a copy of the
     *         data.
     */
    default <T extends OWLAxiom> Stream<T> axioms(Class<T> type, Class<? extends OWLObject> explicitClass,
        OWLObject entity, Imports imports, Navigation forSubPosition) {
        return imports.stream(this).flatMap(o -> o.axioms(type, explicitClass, entity, forSubPosition));
    }

    // Annotation axioms
    /**
     * Gets the {@code SubAnnotationPropertyOfAxiom}s where the specified
     * property is the sub-property.
     * 
     * @param subProperty
     *        The sub-property of the axioms to be retrieved.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSubAnnotationPropertyOfAxiom> getSubAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty) {
        return asSet(subAnnotationPropertyOfAxioms(subProperty));
    }

    /**
     * Gets the {@code SubAnnotationPropertyOfAxiom}s where the specified
     * property is the sub-property.
     * 
     * @param subProperty
     *        The sub-property of the axioms to be retrieved.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    Stream<OWLSubAnnotationPropertyOfAxiom> subAnnotationPropertyOfAxioms(OWLAnnotationProperty subProperty);

    /**
     * Gets the {@code OWLAnnotationPropertyDomainAxiom}s where the specified
     * property is the property in the domain axiom.
     * 
     * @param property
     *        The property that the axiom specifies a domain for.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLAnnotationPropertyDomainAxiom> getAnnotationPropertyDomainAxioms(OWLAnnotationProperty property) {
        return asSet(annotationPropertyDomainAxioms(property));
    }

    /**
     * Gets the {@code OWLAnnotationPropertyDomainAxiom}s where the specified
     * property is the property in the domain axiom.
     * 
     * @param property
     *        The property that the axiom specifies a domain for.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    Stream<OWLAnnotationPropertyDomainAxiom> annotationPropertyDomainAxioms(OWLAnnotationProperty property);

    /**
     * Gets the {@code OWLAnnotationPropertyRangeAxiom}s where the specified
     * property is the property in the range axiom.
     * 
     * @param property
     *        The property that the axiom specifies a range for.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLAnnotationPropertyRangeAxiom> getAnnotationPropertyRangeAxioms(OWLAnnotationProperty property) {
        return asSet(annotationPropertyRangeAxioms(property));
    }

    /**
     * Gets the {@code OWLAnnotationPropertyRangeAxiom}s where the specified
     * property is the property in the range axiom.
     * 
     * @param property
     *        The property that the axiom specifies a range for.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    Stream<OWLAnnotationPropertyRangeAxiom> annotationPropertyRangeAxioms(OWLAnnotationProperty property);

    // Various methods that provide axioms relating to specific entities that
    // allow frame style views to be composed for a particular entity. Such
    // functionality is useful for ontology editors and browsers.
    /**
     * Gets the declaration axioms for specified entity.
     * 
     * @param subject
     *        The entity that is the subject of the set of returned axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLDeclarationAxiom> getDeclarationAxioms(OWLEntity subject) {
        return getAxioms(OWLDeclarationAxiom.class, subject, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the declaration axioms for specified entity.
     * 
     * @param subject
     *        The entity that is the subject of the set of returned axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLDeclarationAxiom> declarationAxioms(OWLEntity subject) {
        return axioms(OWLDeclarationAxiom.class, subject, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the axioms that annotate the specified entity.
     * 
     * @param entity
     *        The entity whose annotations are to be retrieved.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject entity) {
        return asSet(annotationAssertionAxioms(entity, EXCLUDED));
    }

    /**
     * Gets the axioms that annotate the specified entity.
     * 
     * @param entity
     *        The entity whose annotations are to be retrieved.
     * @param imports
     *        imports included or excluded
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLAnnotationAssertionAxiom> getAnnotationAssertionAxioms(OWLAnnotationSubject entity,
        Imports imports) {
        return asSet(annotationAssertionAxioms(entity, imports));
    }

    /**
     * Gets the axioms that annotate the specified entity.
     * 
     * @param entity
     *        The entity whose annotations are to be retrieved.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(OWLAnnotationSubject entity) {
        return annotationAssertionAxioms(entity, EXCLUDED);
    }

    /**
     * Gets the axioms that annotate the specified entity.
     * 
     * @param entity
     *        The entity whose annotations are to be retrieved.
     * @param imports
     *        imports included or excluded
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLAnnotationAssertionAxiom> annotationAssertionAxioms(OWLAnnotationSubject entity,
        Imports imports) {
        return axioms(OWLAnnotationAssertionAxiom.class, OWLAnnotationSubject.class, entity, imports, IN_SUB_POSITION);
    }

    // Classes
    /**
     * Gets all of the subclass axioms where the left hand side (the subclass)
     * is equal to the specified class.
     * 
     * @param cls
     *        The class that is equal to the left hand side of the axiom
     *        (subclass).
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) {
        return asSet(subClassAxiomsForSubClass(cls));
    }

    /**
     * Gets all of the subclass axioms where the left hand side (the subclass)
     * is equal to the specified class.
     * 
     * @param cls
     *        The class that is equal to the left hand side of the axiom
     *        (subclass).
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSubClassOfAxiom> subClassAxiomsForSubClass(OWLClass cls) {
        return axioms(OWLSubClassOfAxiom.class, OWLClass.class, cls, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets all of the subclass axioms where the right hand side (the
     * superclass) is equal to the specified class.
     * 
     * @param cls
     *        The class
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) {
        return asSet(subClassAxiomsForSuperClass(cls));
    }

    /**
     * Gets all of the subclass axioms where the right hand side (the
     * superclass) is equal to the specified class.
     * 
     * @param cls
     *        The class
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSubClassOfAxiom> subClassAxiomsForSuperClass(OWLClass cls) {
        return axioms(OWLSubClassOfAxiom.class, OWLClass.class, cls, EXCLUDED, IN_SUPER_POSITION);
    }

    /**
     * Gets all of the equivalent axioms in this ontology that contain the
     * specified class as an operand.
     * 
     * @param cls
     *        The class to search
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(OWLClass cls) {
        return asSet(equivalentClassesAxioms(cls));
    }

    /**
     * Gets all of the equivalent axioms in this ontology that contain the
     * specified class as an operand.
     * 
     * @param cls
     *        The class to search
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLEquivalentClassesAxiom> equivalentClassesAxioms(OWLClass cls) {
        return axioms(OWLEquivalentClassesAxiom.class, OWLClass.class, cls, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the set of disjoint class axioms that contain the specified class as
     * an operand.
     * 
     * @param cls
     *        The class to search
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLDisjointClassesAxiom> getDisjointClassesAxioms(OWLClass cls) {
        return asSet(disjointClassesAxioms(cls));
    }

    /**
     * Gets the set of disjoint class axioms that contain the specified class as
     * an operand.
     * 
     * @param cls
     *        The class to search
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLDisjointClassesAxiom> disjointClassesAxioms(OWLClass cls) {
        return axioms(OWLDisjointClassesAxiom.class, OWLClass.class, cls, EXCLUDED, IN_SUB_POSITION);
    }

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
    @Deprecated
    default Set<OWLDisjointUnionAxiom> getDisjointUnionAxioms(OWLClass owlClass) {
        return asSet(disjointUnionAxioms(owlClass));
    }

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
    default Stream<OWLDisjointUnionAxiom> disjointUnionAxioms(OWLClass owlClass) {
        return axioms(OWLDisjointUnionAxiom.class, OWLClass.class, owlClass, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the has key axioms that have the specified class as their subject.
     * 
     * @param cls
     *        The subject of the has key axioms
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLHasKeyAxiom> getHasKeyAxioms(OWLClass cls) {
        return asSet(hasKeyAxioms(cls));
    }

    /**
     * Gets the has key axioms that have the specified class as their subject.
     * 
     * @param cls
     *        The subject of the has key axioms
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLHasKeyAxiom> hasKeyAxioms(OWLClass cls) {
        return axioms(OWLHasKeyAxiom.class, OWLClass.class, cls, EXCLUDED, IN_SUB_POSITION);
    }

    // Object properties
    /**
     * Gets the {@link OWLSubObjectPropertyOfAxiom} s where the sub-property is
     * equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
        OWLObjectPropertyExpression subProperty) {
        return asSet(objectSubPropertyAxiomsForSubProperty(subProperty));
    }

    /**
     * Gets the {@link OWLSubObjectPropertyOfAxiom} s where the sub-property is
     * equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsForSubProperty(
        OWLObjectPropertyExpression subProperty) {
        return axioms(OWLSubObjectPropertyOfAxiom.class, OWLObjectPropertyExpression.class, subProperty, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLSubObjectPropertyOfAxiom} s where the super-property
     * is equal to the specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSuperProperty(
        OWLObjectPropertyExpression superProperty) {
        return asSet(objectSubPropertyAxiomsForSuperProperty(superProperty));
    }

    /**
     * Gets the {@link OWLSubObjectPropertyOfAxiom} s where the super-property
     * is equal to the specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsForSuperProperty(
        OWLObjectPropertyExpression superProperty) {
        return axioms(OWLSubObjectPropertyOfAxiom.class, OWLObjectPropertyExpression.class, superProperty, EXCLUDED,
            IN_SUPER_POSITION);
    }

    /**
     * Gets the {@link OWLObjectPropertyDomainAxiom}s where the property is
     * equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        return asSet(objectPropertyDomainAxioms(property));
    }

    /**
     * Gets the {@link OWLObjectPropertyDomainAxiom}s where the property is
     * equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLObjectPropertyDomainAxiom> objectPropertyDomainAxioms(OWLObjectPropertyExpression property) {
        return axioms(OWLObjectPropertyDomainAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLObjectPropertyRangeAxiom} s where the property is
     * equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        return asSet(objectPropertyRangeAxioms(property));
    }

    /**
     * Gets the {@link OWLObjectPropertyRangeAxiom} s where the property is
     * equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLObjectPropertyRangeAxiom> objectPropertyRangeAxioms(OWLObjectPropertyExpression property) {
        return axioms(OWLObjectPropertyRangeAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

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
    @Deprecated
    default Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return asSet(inverseObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLInverseObjectPropertiesAxiom}s where the specified
     * property is contained in the set returned by
     * {@link OWLInverseObjectPropertiesAxiom#properties()} .
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLInverseObjectPropertiesAxiom> inverseObjectPropertyAxioms(OWLObjectPropertyExpression property) {
        return axioms(OWLInverseObjectPropertiesAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

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
    @Deprecated
    default Set<OWLEquivalentObjectPropertiesAxiom> getEquivalentObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(equivalentObjectPropertiesAxioms(property));
    }

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
    default Stream<OWLEquivalentObjectPropertiesAxiom> equivalentObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLEquivalentObjectPropertiesAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

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
    @Deprecated
    default Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(disjointObjectPropertiesAxioms(property));
    }

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
    default Stream<OWLDisjointObjectPropertiesAxiom> disjointObjectPropertiesAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLDisjointObjectPropertiesAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLFunctionalObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property functional.
     * 
     * @param property
     *        The property that is made functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(functionalObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLFunctionalObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property functional.
     * 
     * @param property
     *        The property that is made functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLFunctionalObjectPropertyAxiom> functionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLFunctionalObjectPropertyAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLInverseFunctionalObjectPropertyAxiom}s contained in
     * this ontology that make the specified object property inverse functional.
     * 
     * @param property
     *        The property that is made inverse functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLInverseFunctionalObjectPropertyAxiom> getInverseFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(inverseFunctionalObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLInverseFunctionalObjectPropertyAxiom}s contained in
     * this ontology that make the specified object property inverse functional.
     * 
     * @param property
     *        The property that is made inverse functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLInverseFunctionalObjectPropertyAxiom> inverseFunctionalObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLInverseFunctionalObjectPropertyAxiom.class, OWLObjectPropertyExpression.class, property,
            EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLSymmetricObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property symmetric.
     * 
     * @param property
     *        The property that is made symmetric by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(symmetricObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLSymmetricObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property symmetric.
     * 
     * @param property
     *        The property that is made symmetric by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSymmetricObjectPropertyAxiom> symmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLSymmetricObjectPropertyAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLAsymmetricObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property asymmetric.
     * 
     * @param property
     *        The property that is made asymmetric by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(asymmetricObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLAsymmetricObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property asymmetric.
     * 
     * @param property
     *        The property that is made asymmetric by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLAsymmetricObjectPropertyAxiom> asymmetricObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLAsymmetricObjectPropertyAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLReflexiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property reflexive.
     * 
     * @param property
     *        The property that is made reflexive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(reflexiveObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLReflexiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property reflexive.
     * 
     * @param property
     *        The property that is made reflexive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLReflexiveObjectPropertyAxiom> reflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLReflexiveObjectPropertyAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLIrreflexiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property irreflexive.
     * 
     * @param property
     *        The property that is made irreflexive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(irreflexiveObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLIrreflexiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property irreflexive.
     * 
     * @param property
     *        The property that is made irreflexive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLIrreflexiveObjectPropertyAxiom> irreflexiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLIrreflexiveObjectPropertyAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLTransitiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property transitive.
     * 
     * @param property
     *        The property that is made transitive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLTransitiveObjectPropertyAxiom> getTransitiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return asSet(transitiveObjectPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLTransitiveObjectPropertyAxiom}s contained in this
     * ontology that make the specified object property transitive.
     * 
     * @param property
     *        The property that is made transitive by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLTransitiveObjectPropertyAxiom> transitiveObjectPropertyAxioms(
        OWLObjectPropertyExpression property) {
        return axioms(OWLTransitiveObjectPropertyAxiom.class, OWLObjectPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    // Data properties
    /**
     * Gets the {@link OWLSubDataPropertyOfAxiom}s where the sub-property is
     * equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(OWLDataProperty subProperty) {
        return asSet(dataSubPropertyAxiomsForSubProperty(subProperty));
    }

    /**
     * Gets the {@link OWLSubDataPropertyOfAxiom}s where the sub-property is
     * equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsForSubProperty(OWLDataProperty subProperty) {
        return axioms(OWLSubDataPropertyOfAxiom.class, OWLDataPropertyExpression.class, subProperty, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLSubDataPropertyOfAxiom}s where the super-property is
     * equal to the specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
        OWLDataPropertyExpression superProperty) {
        return asSet(dataSubPropertyAxiomsForSuperProperty(superProperty));
    }

    /**
     * Gets the {@link OWLSubDataPropertyOfAxiom}s where the super-property is
     * equal to the specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsForSuperProperty(
        OWLDataPropertyExpression superProperty) {
        return axioms(OWLSubDataPropertyOfAxiom.class, OWLDataPropertyExpression.class, superProperty, EXCLUDED,
            IN_SUPER_POSITION);
    }

    /**
     * Gets the {@link OWLDataPropertyDomainAxiom}s where the property is equal
     * to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(OWLDataProperty property) {
        return asSet(dataPropertyDomainAxioms(property));
    }

    /**
     * Gets the {@link OWLDataPropertyDomainAxiom}s where the property is equal
     * to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLDataPropertyDomainAxiom> dataPropertyDomainAxioms(OWLDataProperty property) {
        return axioms(OWLDataPropertyDomainAxiom.class, OWLDataPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLDataPropertyRangeAxiom}s where the property is equal
     * to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(OWLDataProperty property) {
        return asSet(dataPropertyRangeAxioms(property));
    }

    /**
     * Gets the {@link OWLDataPropertyRangeAxiom}s where the property is equal
     * to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLDataPropertyRangeAxiom> dataPropertyRangeAxioms(OWLDataProperty property) {
        return axioms(OWLDataPropertyRangeAxiom.class, OWLDataPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLEquivalentDataPropertiesAxiom}s that make the
     * specified property equivalent to some other data property expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms make equivalent to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     * @deprecated use equivalentDataPropertiesAxioms
     */
    @Deprecated
    default Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(OWLDataProperty property) {
        return asSet(equivalentDataPropertiesAxioms(property));
    }

    /**
     * Gets the {@link OWLEquivalentDataPropertiesAxiom}s that make the
     * specified property equivalent to some other data property expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms make equivalent to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLEquivalentDataPropertiesAxiom> equivalentDataPropertiesAxioms(OWLDataProperty property) {
        return axioms(OWLEquivalentDataPropertiesAxiom.class, OWLDataPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLDisjointDataPropertiesAxiom}s that make the specified
     * property disjoint with some other data property expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms makes disjoint to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(OWLDataProperty property) {
        return asSet(disjointDataPropertiesAxioms(property));
    }

    /**
     * Gets the {@link OWLDisjointDataPropertiesAxiom}s that make the specified
     * property disjoint with some other data property expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms makes disjoint to some
     *        other property expressions.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLDisjointDataPropertiesAxiom> disjointDataPropertiesAxioms(OWLDataProperty property) {
        return axioms(OWLDisjointDataPropertiesAxiom.class, OWLDataPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLFunctionalDataPropertyAxiom}s contained in this
     * ontology that make the specified data property functional.
     * 
     * @param property
     *        The property that is made functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLFunctionalDataPropertyAxiom> getFunctionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        return asSet(functionalDataPropertyAxioms(property));
    }

    /**
     * Gets the {@link OWLFunctionalDataPropertyAxiom}s contained in this
     * ontology that make the specified data property functional.
     * 
     * @param property
     *        The property that is made functional by the axioms.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLFunctionalDataPropertyAxiom> functionalDataPropertyAxioms(OWLDataPropertyExpression property) {
        return axioms(OWLFunctionalDataPropertyAxiom.class, OWLDataPropertyExpression.class, property, EXCLUDED,
            IN_SUB_POSITION);
    }

    // Individuals
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
    @Deprecated
    default Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLIndividual individual) {
        return asSet(classAssertionAxioms(individual));
    }

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
    default Stream<OWLClassAssertionAxiom> classAssertionAxioms(OWLIndividual individual) {
        return axioms(OWLClassAssertionAxiom.class, OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

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
    @Deprecated
    default Set<OWLClassAssertionAxiom> getClassAssertionAxioms(OWLClassExpression ce) {
        return asSet(classAssertionAxioms(ce));
    }

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
    default Stream<OWLClassAssertionAxiom> classAssertionAxioms(OWLClassExpression ce) {
        return axioms(OWLClassAssertionAxiom.class, OWLClassExpression.class, ce, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLDataPropertyAssertionAxiom}s contained in this
     * ontology that have the specified {@code individual} as the subject of the
     * axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(OWLIndividual individual) {
        return asSet(dataPropertyAssertionAxioms(individual));
    }

    /**
     * Gets the {@link OWLDataPropertyAssertionAxiom}s contained in this
     * ontology that have the specified {@code individual} as the subject of the
     * axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLDataPropertyAssertionAxiom> dataPropertyAssertionAxioms(OWLIndividual individual) {
        return axioms(OWLDataPropertyAssertionAxiom.class, OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLObjectPropertyAssertionAxiom}s contained in this
     * ontology that have the specified {@code individual} as the subject of the
     * axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(OWLIndividual individual) {
        return asSet(objectPropertyAssertionAxioms(individual));
    }

    /**
     * Gets the {@link OWLObjectPropertyAssertionAxiom}s contained in this
     * ontology that have the specified {@code individual} as the subject of the
     * axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLObjectPropertyAssertionAxiom> objectPropertyAssertionAxioms(OWLIndividual individual) {
        return axioms(OWLObjectPropertyAssertionAxiom.class, OWLIndividual.class, individual, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLNegativeObjectPropertyAssertionAxiom} s contained in
     * this ontology that have the specified {@code individual} as the subject
     * of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLNegativeObjectPropertyAssertionAxiom> getNegativeObjectPropertyAssertionAxioms(
        OWLIndividual individual) {
        return asSet(negativeObjectPropertyAssertionAxioms(individual));
    }

    /**
     * Gets the {@link OWLNegativeObjectPropertyAssertionAxiom} s contained in
     * this ontology that have the specified {@code individual} as the subject
     * of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLNegativeObjectPropertyAssertionAxiom> negativeObjectPropertyAssertionAxioms(
        OWLIndividual individual) {
        return axioms(OWLNegativeObjectPropertyAssertionAxiom.class, OWLIndividual.class, individual, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLNegativeDataPropertyAssertionAxiom} s contained in
     * this ontology that have the specified {@code individual} as the subject
     * of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLNegativeDataPropertyAssertionAxiom> getNegativeDataPropertyAssertionAxioms(
        OWLIndividual individual) {
        return asSet(negativeDataPropertyAssertionAxioms(individual));
    }

    /**
     * Gets the {@link OWLNegativeDataPropertyAssertionAxiom} s contained in
     * this ontology that have the specified {@code individual} as the subject
     * of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLNegativeDataPropertyAssertionAxiom> negativeDataPropertyAssertionAxioms(
        OWLIndividual individual) {
        return axioms(OWLNegativeDataPropertyAssertionAxiom.class, OWLIndividual.class, individual, EXCLUDED,
            IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLSameIndividualAxiom}s contained in this ontology that
     * make the specified {@code individual} the same as some other individual.
     * 
     * @param individual
     *        The individual that the returned axioms make the same as some
     *        other individual.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLSameIndividualAxiom> getSameIndividualAxioms(OWLIndividual individual) {
        return asSet(sameIndividualAxioms(individual));
    }

    /**
     * Gets the {@link OWLSameIndividualAxiom}s contained in this ontology that
     * make the specified {@code individual} the same as some other individual.
     * 
     * @param individual
     *        The individual that the returned axioms make the same as some
     *        other individual.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    default Stream<OWLSameIndividualAxiom> sameIndividualAxioms(OWLIndividual individual) {
        return axioms(OWLSameIndividualAxiom.class, OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

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
    @Deprecated
    default Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(OWLIndividual individual) {
        return asSet(differentIndividualAxioms(individual));
    }

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
    default Stream<OWLDifferentIndividualsAxiom> differentIndividualAxioms(OWLIndividual individual) {
        return axioms(OWLDifferentIndividualsAxiom.class, OWLIndividual.class, individual, EXCLUDED, IN_SUB_POSITION);
    }

    /**
     * Gets the {@link OWLDatatypeDefinitionAxiom}s contained in this ontology
     * that provide a definition for the specified datatype.
     * 
     * @param datatype
     *        The datatype for which the returned axioms provide a definition.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    @Deprecated
    default Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(OWLDatatype datatype) {
        return asSet(datatypeDefinitions(datatype));
    }

    /**
     * Gets the {@link OWLDatatypeDefinitionAxiom}s contained in this ontology
     * that provide a definition for the specified datatype.
     * 
     * @param datatype
     *        The datatype for which the returned axioms provide a definition.
     * @return the axioms matching the search. The set is a copy of the data.
     */
    Stream<OWLDatatypeDefinitionAxiom> datatypeDefinitions(OWLDatatype datatype);
}
