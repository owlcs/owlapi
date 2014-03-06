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
        HasGetEntitiesInSignature, OWLAxiomCollection, OWLSignature {

    /**
     * interim method to access ontology internals for searching purposes
     * 
     * @return internals for this ontology
     */
    @Nonnull
    Internals getInternals();

    /**
     * accept for named object visitor
     * 
     * @param visitor
     *        the visitor
     */
    void accept(@Nonnull OWLNamedObjectVisitor visitor);

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
    @Override
    @Nonnull
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
    Set<IRI> getDirectImportsDocuments() throws UnknownOWLOntologyException;

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
    @Override
    @Nonnull
    Set<OWLOntology> getDirectImports() throws UnknownOWLOntologyException;

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
    Set<OWLOntology> getImports() throws UnknownOWLOntologyException;

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
    @Override
    @Nonnull
    Set<OWLOntology> getImportsClosure() throws UnknownOWLOntologyException;

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
     *        if {@code true} then axioms of the specified type will also be
     *        retrieved from the imports closure of this ontology, if
     *        {@code false} then axioms of the specified type will only be
     *        retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    @Nonnull
    Set<OWLAxiom> getTBoxAxioms(boolean includeImportsClosure);

    /**
     * Gets the axioms that form the ABox for this ontology, i.e., the ones
     * whose type is in the AxiomType::ABoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if {@code true} then axioms of the specified type will also be
     *        retrieved from the imports closure of this ontology, if
     *        {@code false} then axioms of the specified type will only be
     *        retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    @Nonnull
    Set<OWLAxiom> getABoxAxioms(boolean includeImportsClosure);

    /**
     * Gets the axioms that form the RBox for this ontology, i.e., the ones
     * whose type is in the AxiomType::RBoxAxiomTypes.
     * 
     * @param includeImportsClosure
     *        if {@code true} then axioms of the specified type will also be
     *        retrieved from the imports closure of this ontology, if
     *        {@code false} then axioms of the specified type will only be
     *        retrieved from this ontology.
     * @return A set containing the axioms which are of the specified type. The
     *         set that is returned is a copy of the axioms in the ontology (and
     *         its imports closure) - it will not be updated if the ontology
     *         changes.
     */
    @Nonnull
    Set<OWLAxiom> getRBoxAxioms(boolean includeImportsClosure);

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
    @Override
    @Nonnull
    Set<OWLEntity> getSignature();

    /**
     * Gets the entities that are in the signature of this ontology. The
     * signature of an ontology is the set of entities that are used to build
     * axioms and annotations in the ontology. (See <a href=
     * "http://www.w3.org/TR/owl2-syntax/#Entities.2C_Literals.2C_and_Anonymous_Individuals"
     * >The OWL 2 Structural Specification</a>)
     * 
     * @param includeImportsClosure
     *        Specifies whether or not the returned set of entities should
     *        represent the signature of just this ontology, or the signature of
     *        the imports closure of this ontology.
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
    Set<OWLEntity> getSignature(boolean includeImportsClosure);

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
     *        {@code true} if the imports closure of this ontology should be
     *        examined, {@code false} if just this ontology should be examined.
     * @return {@code true} if the ontology or its imports closure contains a
     *         declaration for the specified entity, otherwise {@code false}.
     */
    boolean isDeclared(@Nonnull OWLEntity owlEntity,
            boolean includeImportsClosure);

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
     * @return A set of {@code OWLSubAnnotationPropertyOfAxiom}s such that the
     *         sub-property is equal to {@code subProperty}.
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
     * @return A set of {@code OWLAnnotationPropertyDomainAxiom}s such that the
     *         property is equal to {@code property}.
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
     * @return A set of {@code OWLAnnotationPropertyRangeAxiom}s such that the
     *         property is equal to {@code property}.
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
     * @return The set of declaration axioms. Note that this set will be a copy
     *         and will not be updated if the ontology changes. It is therefore
     *         safe to iterate over this set while making changes to the
     *         ontology.
     */
    @Nonnull
    Set<OWLDeclarationAxiom> getDeclarationAxioms(@Nonnull OWLEntity subject);

    /**
     * Gets the axioms that annotate the specified entity.
     * 
     * @param entity
     *        The entity whose annotations are to be retrieved.
     * @return The set of entity annotation axioms. Note that this set will be a
     *         copy and will not be updated if the ontology changes. It is
     *         therefore safe to iterate over this set while making changes to
     *         the ontology.
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
     * @return The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(@Nonnull OWLClass cls);

    /**
     * Gets all of the subclass axioms where the right hand side (the
     * superclass) is equal to the specified class.
     * 
     * @param cls
     *        The class
     * @return The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLSubClassOfAxiom>
            getSubClassAxiomsForSuperClass(@Nonnull OWLClass cls);

    /**
     * Gets all of the equivalent axioms in this ontology that contain the
     * specified class as an operand.
     * 
     * @param cls
     *        The class
     * @return A set of equivalent class axioms that contain the specified class
     *         as an operand. The set that is returned is a copy - it will not
     *         be updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLEquivalentClassesAxiom> getEquivalentClassesAxioms(
            @Nonnull OWLClass cls);

    /**
     * Gets the set of disjoint class axioms that contain the specified class as
     * an operand.
     * 
     * @param cls
     *        The class that should be contained in the set of disjoint class
     *        axioms that will be returned.
     * @return The set of disjoint axioms that contain the specified class. The
     *         set that is returned is a copy - it will not be updated if the
     *         ontology changes. It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
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
     * @return The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLDisjointUnionAxiom>
            getDisjointUnionAxioms(@Nonnull OWLClass owlClass);

    /**
     * Gets the has key axioms that have the specified class as their subject.
     * 
     * @param cls
     *        The subject of the has key axioms
     * @return The set of has key axioms that have cls as their subject. The set
     *         that is returned is a copy - it will not be updated if the
     *         ontology changes. It is therefore safe to apply changes to this
     *         ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLHasKeyAxiom> getHasKeyAxioms(@Nonnull OWLClass cls);

    // ////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Object properties
    //
    // ////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}
     * s where the sub-property is equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrived
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}s
     *         such that each axiom in the set is of the form
     *         {@code SubObjectPropertyOf(subProperty, pe)}. The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLSubObjectPropertyOfAxiom> getObjectSubPropertyAxiomsForSubProperty(
            @Nonnull OWLObjectPropertyExpression subProperty);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}
     * s where the super-property (returned by
     * {@link OWLSubObjectPropertyOfAxiom#getSuperProperty()}) is equal to the
     * specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrived
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom}s
     *         such that each axiom in the set is of the form
     *         {@code SubObjectPropertyOf(pe, superProperty)}. The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsForSuperProperty(
                    @Nonnull OWLObjectPropertyExpression superProperty);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom}s where
     * the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom#getProperty()}
     * ) is equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrived
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code ObjectPropertyDomain(pe, ce)}. The set that is returned is
     *         a copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     */
    @Nonnull
    Set<OWLObjectPropertyDomainAxiom> getObjectPropertyDomainAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom}
     * s where the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom#getProperty()}
     * ) is equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retieved
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom}s
     *         such that each axiom in the set is of the form
     *         {@code ObjectPropertyRange(property, ce)}. The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLObjectPropertyRangeAxiom> getObjectPropertyRangeAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLInverseObjectPropertiesAxiom}s where the specified
     * property is contained in the set returned by
     * {@link org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom#getProperties()}
     * .
     * 
     * @param property
     *        The property which is equal to the property of the retrieved
     *        axioms.
     * @return A set of {@link OWLInverseObjectPropertiesAxiom}s such that each
     *         axiom in the set is of the form
     *         {@code InverseObjectProperties(property, pe)} or
     *         {@code InverseObjectProperties(pe, property)}. The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLInverseObjectPropertiesAxiom> getInverseObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom}s
     * that make the specified property equivalent to some other object property
     * expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms make equivalent to some
     *        other property expressions. For each axiom retrieved the set of
     *        properties returned by
     *        {@link OWLEquivalentObjectPropertiesAxiom#getProperties()} will
     *        contain property.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code EquivalentObjectProperties(pe0, ..., property, ..., pen)}
     *         . The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertiesAxioms(
                    @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom}s
     * that make the specified property disjoint with some other object property
     * expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms makes disjoint to some
     *        other property expressions. For each axiom retrieved the set of
     *        properties returned by
     *        {@link OWLDisjointObjectPropertiesAxiom#getProperties()} will
     *        contain property.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code DisjointObjectProperties(pe0, ..., property, ..., pen)} .
     *         The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLDisjointObjectPropertiesAxiom> getDisjointObjectPropertiesAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom}s
     * contained in this ontology that make the specified object property
     * functional.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom#getProperty()}
     *        ) that is made functional by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code FunctionalObjectProperty(property)}.
     */
    @Nonnull
    Set<OWLFunctionalObjectPropertyAxiom> getFunctionalObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the {@link OWLInverseFunctionalObjectPropertyAxiom}s contained in
     * this ontology that make the specified object property inverse functional.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom#getProperty()}
     *        ) that is made inverse functional by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code InverseFunctionalObjectProperty(property)}.
     */
    @Nonnull
    Set<OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalObjectPropertyAxioms(
                    @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom}s
     * contained in this ontology that make the specified object property
     * symmetric.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom#getProperty()}
     *        ) that is made symmetric by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code SymmetricObjectProperty(property)}.
     */
    @Nonnull
    Set<OWLSymmetricObjectPropertyAxiom> getSymmetricObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom}s
     * contained in this ontology that make the specified object property
     * asymmetric.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom#getProperty()}
     *        ) that is made asymmetric by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code AsymmetricObjectProperty(property)}.
     */
    @Nonnull
    Set<OWLAsymmetricObjectPropertyAxiom> getAsymmetricObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom}s
     * contained in this ontology that make the specified object property
     * reflexive.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom#getProperty()}
     *        ) that is made reflexive by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code ReflexiveObjectProperty(property)}.
     */
    @Nonnull
    Set<OWLReflexiveObjectPropertyAxiom> getReflexiveObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom}s
     * contained in this ontology that make the specified object property
     * irreflexive.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom#getProperty()}
     *        ) that is made irreflexive by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code IrreflexiveObjectProperty(property)}.
     */
    @Nonnull
    Set<OWLIrreflexiveObjectPropertyAxiom> getIrreflexiveObjectPropertyAxioms(
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom}s
     * contained in this ontology that make the specified object property
     * transitive.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom#getProperty()}
     *        ) that is made transitive by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code TransitiveObjectProperty(property)}.
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
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s
     * where the sub-property is equal to the specified property.
     * 
     * @param subProperty
     *        The property which is equal to the sub property of the retrived
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s
     *         such that each axiom in the set is of the form
     *         {@code SubDataPropertyOf(subProperty, pe)}. The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSubProperty(
            @Nonnull OWLDataProperty subProperty);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s
     * where the super-property (returned by
     * {@link OWLSubDataPropertyOfAxiom#getSuperProperty()}) is equal to the
     * specified property.
     * 
     * @param superProperty
     *        The property which is equal to the super-property of the retrived
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom}s
     *         such that each axiom in the set is of the form
     *         {@code SubDataPropertyOf(pe, superProperty)}. The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
     */
    @Nonnull
    Set<OWLSubDataPropertyOfAxiom> getDataSubPropertyAxiomsForSuperProperty(
            @Nonnull OWLDataPropertyExpression superProperty);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom}s
     * where the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom#getProperty()}
     * ) is equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retrived
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom}s
     *         such that each axiom in the set is of the form
     *         {@code DataPropertyDomain(pe, ce)}. The set that is returned is a
     *         copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     */
    @Nonnull
    Set<OWLDataPropertyDomainAxiom> getDataPropertyDomainAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom}s
     * where the property (returned by
     * {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom#getProperty()}
     * ) is equal to the specified property.
     * 
     * @param property
     *        The property which is equal to the property of the retieved
     *        axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom}s
     *         such that each axiom in the set is of the form
     *         {@code DataPropertyRange(property, ce)}. The set that is returned
     *         is a copy - it will not be updated if the ontology changes. It is
     *         therefore safe to apply changes to this ontology while iterating
     *         over this set.
     */
    @Nonnull
    Set<OWLDataPropertyRangeAxiom> getDataPropertyRangeAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom}s
     * that make the specified property equivalent to some other data property
     * expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms make equivalent to some
     *        other property expressions. For each axiom retrieved the set of
     *        properties returned by
     *        {@link OWLEquivalentDataPropertiesAxiom#getProperties()} will
     *        contain property.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code EquivalentDataProperties(pe0, ..., property, ..., pen)} .
     *         The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLEquivalentDataPropertiesAxiom> getEquivalentDataPropertiesAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom}s that
     * make the specified property disjoint with some other data property
     * expression(s).
     * 
     * @param property
     *        The property that the retrieved axioms makes disjoint to some
     *        other property expressions. For each axiom retrieved the set of
     *        properties returned by
     *        {@link OWLDisjointDataPropertiesAxiom#getProperties()} will
     *        contain property.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code DisjointDataProperties(pe0, ..., property, ..., pen)} .
     *         The set that is returned is a copy - it will not be updated if
     *         the ontology changes. It is therefore safe to apply changes to
     *         this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLDisjointDataPropertiesAxiom> getDisjointDataPropertiesAxioms(
            @Nonnull OWLDataProperty property);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom}s
     * contained in this ontology that make the specified data property
     * functional.
     * 
     * @param property
     *        The property (returned by
     *        {@link org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom#getProperty()}
     *        ) that is made functional by the axioms.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code FunctionalDataProperty(property)}. The set that is
     *         returned is a copy - it will not be updated if the ontology
     *         changes. It is therefore safe to apply changes to this ontology
     *         while iterating over this set.
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
     * Gets the {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s
     * contained in this ontology that make the specified {@code individual} an
     * instance of some class expression.
     * 
     * @param individual
     *        The individual that the returned axioms make an instance of some
     *        class expression.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s such
     *         that each axiom in the set is of the form
     *         {@code ClassAssertion(ce, individual)} (for each axiom
     *         {@link OWLClassAssertionAxiom#getIndividual()} is equal to
     *         {@code individual}). The set that is returned is a copy - it will
     *         not be updated if the ontology changes. It is therefore safe to
     *         apply changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s
     * contained in this ontology that make the specified class expression,
     * {@code ce}, a type for some individual.
     * 
     * @param ce
     *        The class expression that the returned axioms make a type for some
     *        individual.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLClassAssertionAxiom}s such
     *         that each axiom in the set is of the form
     *         {@code ClassAssertion(ce, ind)} (for each axiom
     *         {@link OWLClassAssertionAxiom#getClassExpression()} is equal to
     *         {@code ce}). The set that is returned is a copy - it will not be
     *         updated if the ontology changes. It is therefore safe to apply
     *         changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLClassAssertionAxiom> getClassAssertionAxioms(
            @Nonnull OWLClassExpression ce);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom}s
     * contained in this ontology that have the specified {@code individual} as
     * the subject of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code DataPropertyAssertion(dp, individual, l)} (for each axiom
     *         {@link OWLDataPropertyAssertionAxiom#getSubject()} is equal to
     *         {@code individual}). The set that is returned is a copy - it will
     *         not be updated if the ontology changes. It is therefore safe to
     *         apply changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLDataPropertyAssertionAxiom> getDataPropertyAssertionAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom}s
     * contained in this ontology that have the specified {@code individual} as
     * the subject of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code ObjectPropertyAssertion(dp, individual, obj)} (for each
     *         axiom {@link OWLObjectPropertyAssertionAxiom#getSubject()} is
     *         equal to {@code individual}). The set that is returned is a copy
     *         - it will not be updated if the ontology changes. It is therefore
     *         safe to apply changes to this ontology while iterating over this
     *         set.
     */
    @Nonnull
    Set<OWLObjectPropertyAssertionAxiom> getObjectPropertyAssertionAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom}
     * s contained in this ontology that have the specified {@code individual}
     * as the subject of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code NegativeObjectPropertyAssertion(dp, individual, obj)} (for
     *         each axiom
     *         {@link OWLNegativeObjectPropertyAssertionAxiom#getSubject()} is
     *         equal to {@code individual}). The set that is returned is a copy
     *         - it will not be updated if the ontology changes. It is therefore
     *         safe to apply changes to this ontology while iterating over this
     *         set.
     */
    @Nonnull
    Set<OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxioms(
                    @Nonnull OWLIndividual individual);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom}
     * s contained in this ontology that have the specified {@code individual}
     * as the subject of the axiom.
     * 
     * @param individual
     *        The individual that the returned axioms have as a subject.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code NegativeDataPropertyAssertion(dp, individual, obj)} (for
     *         each axiom
     *         {@link OWLNegativeDataPropertyAssertionAxiom#getSubject()} is
     *         equal to {@code individual}). The set that is returned is a copy
     *         - it will not be updated if the ontology changes. It is therefore
     *         safe to apply changes to this ontology while iterating over this
     *         set.
     */
    @Nonnull
    Set<OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxioms(
                    @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLSameIndividualAxiom}s
     * contained in this ontology that make the specified {@code individual} the
     * same as some other individual.
     * 
     * @param individual
     *        The individual that the returned axioms make the same as some
     *        other individual.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLSameIndividualAxiom}s such
     *         that each axiom in the set is of the form
     *         {@code SameIndividual(individual, ind, ...)} (for each axiom
     *         returned {@link OWLSameIndividualAxiom#getIndividuals()} contains
     *         {@code individual}. The set that is returned is a copy - it will
     *         not be updated if the ontology changes. It is therefore safe to
     *         apply changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLSameIndividualAxiom> getSameIndividualAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the
     * {@link org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom}s
     * contained in this ontology that make the specified {@code individual}
     * different to some other individual.
     * 
     * @param individual
     *        The individual that the returned axioms make the different as some
     *        other individual.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom}
     *         s such that each axiom in the set is of the form
     *         {@code DifferentIndividuals(individual, ind, ...)} (for each
     *         axiom returned
     *         {@link OWLDifferentIndividualsAxiom#getIndividuals()} contains
     *         {@code individual}. The set that is returned is a copy - it will
     *         not be updated if the ontology changes. It is therefore safe to
     *         apply changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLDifferentIndividualsAxiom> getDifferentIndividualAxioms(
            @Nonnull OWLIndividual individual);

    /**
     * Gets the {@link org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom}s
     * contained in this ontology that provide a definition for the specified
     * datatype.
     * 
     * @param datatype
     *        The datatype for which the returned axioms provide a definition.
     * @return A set of
     *         {@link org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom}s
     *         such that for each axiom in the set
     *         {@link OWLDatatypeDefinitionAxiom#getDatatype()} is equal to
     *         {@code datatype}. The set that is returned is a copy - it will
     *         not be updated if the ontology changes. It is therefore safe to
     *         apply changes to this ontology while iterating over this set.
     */
    @Nonnull
    Set<OWLDatatypeDefinitionAxiom> getDatatypeDefinitions(
            @Nonnull OWLDatatype datatype);
}
