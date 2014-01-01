/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.model;

import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/** The Interface Internals. */
public interface Internals {
    /** a marker interface for objects that identify contained maps - so that
     * getting the keys of a specific map does not require a specific method for
     * each map nor does it require the map to be copied and returned.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type */
    interface Pointer<K, V> {}

    /** a marker interface for objects that identify contained sets.
     * 
     * @param <K>
     *            the key type */
    interface SimplePointer<K> {}

    /** Gets the axioms by type.
     * 
     * @return the axioms by type */
    Pointer<AxiomType<?>, OWLAxiom> getAxiomsByType();

    /** Adds the.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param pointer
     *            the pointer
     * @param key
     *            the key
     * @param value
     *            the value
     * @return true, if successful */
    <K, V extends OWLAxiom> boolean add(@Nonnull Pointer<K, V> pointer, @Nonnull K key,
            @Nonnull V value);

    /** Adds the general class axioms.
     * 
     * @param ax
     *            the ax */
    void addGeneralClassAxioms(@Nonnull OWLClassAxiom ax);

    /** Adds the imports declaration.
     * 
     * @param importDeclaration
     *            declaration to be added
     * @return true if the import declaration was not already present, false
     *         otherwise */
    boolean addImportsDeclaration(@Nonnull OWLImportsDeclaration importDeclaration);

    /** Adds the ontology annotation.
     * 
     * @param ann
     *            the ann
     * @return true, if successful */
    boolean addOntologyAnnotation(@Nonnull OWLAnnotation ann);

    /** Adds the property chain sub property axioms.
     * 
     * @param ax
     *            the ax */
    void addPropertyChainSubPropertyAxioms(@Nonnull OWLSubPropertyChainOfAxiom ax);

    /** Contains.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param pointer
     *            the pointer
     * @param k
     *            the k
     * @return true, if successful */
    <K, V extends OWLAxiom> boolean
            contains(@Nonnull Pointer<K, V> pointer, @Nonnull K k);

    /** Contains.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param pointer
     *            the pointer
     * @param k
     *            the k
     * @param v
     *            the v
     * @return true, if successful */
    <K, V extends OWLAxiom> boolean contains(@Nonnull Pointer<K, V> pointer,
            @Nonnull K k, @Nonnull V v);

    /** Gets the annotation assertion axioms by subject.
     * 
     * @return the annotation assertion axioms by subject */
    @Nonnull
    Pointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom>
            getAnnotationAssertionAxiomsBySubject();

    /** Gets the asymmetric property axioms by property.
     * 
     * @return the asymmetric property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricPropertyAxiomsByProperty();

    /** Gets the axiom count.
     * 
     * @return the axiom count */
    int getAxiomCount();

    /** Gets the axiom count.
     * 
     * @param <T>
     *            the generic type
     * @param axiomType
     *            the axiom type
     * @return the axiom count */
    <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType);

    /** Gets the axioms.
     * 
     * @return the axioms */
    @Nonnull
    Set<OWLAxiom> getAxioms();

    /** Gets the class assertion axioms by class.
     * 
     * @return the class assertion axioms by class */
    @Nonnull
    Pointer<OWLClassExpression, OWLClassAssertionAxiom> getClassAssertionAxiomsByClass();

    /** Gets the class assertion axioms by individual.
     * 
     * @return the class assertion axioms by individual */
    @Nonnull
    Pointer<OWLIndividual, OWLClassAssertionAxiom> getClassAssertionAxiomsByIndividual();

    /** Gets the class axioms by class.
     * 
     * @return the class axioms by class */
    @Nonnull
    Pointer<OWLClass, OWLClassAxiom> getClassAxiomsByClass();

    /** Gets the data property assertions by individual.
     * 
     * @return the data property assertions by individual */
    @Nonnull
    Pointer<OWLIndividual, OWLDataPropertyAssertionAxiom>
            getDataPropertyAssertionsByIndividual();

    /** Gets the data property domain axioms by property.
     * 
     * @return the data property domain axioms by property */
    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom>
            getDataPropertyDomainAxiomsByProperty();

    /** Gets the data property range axioms by property.
     * 
     * @return the data property range axioms by property */
    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom>
            getDataPropertyRangeAxiomsByProperty();

    /** Gets the data sub property axioms by lhs.
     * 
     * @return the data sub property axioms by lhs */
    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByLHS();

    /** Gets the data sub property axioms by rhs.
     * 
     * @return the data sub property axioms by rhs */
    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByRHS();

    /** Gets the declarations by entity.
     * 
     * @return the declarations by entity */
    @Nonnull
    Pointer<OWLEntity, OWLDeclarationAxiom> getDeclarationsByEntity();

    /** Gets the different individuals axioms by individual.
     * 
     * @return the different individuals axioms by individual */
    @Nonnull
    Pointer<OWLIndividual, OWLDifferentIndividualsAxiom>
            getDifferentIndividualsAxiomsByIndividual();

    /** Gets the disjoint classes axioms by class.
     * 
     * @return the disjoint classes axioms by class */
    @Nonnull
    Pointer<OWLClass, OWLDisjointClassesAxiom> getDisjointClassesAxiomsByClass();

    /** Gets the disjoint data property axioms by property.
     * 
     * @return the disjoint data property axioms by property */
    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom>
            getDisjointDataPropertyAxiomsByProperty();

    /** Gets the disjoint object property axioms by property.
     * 
     * @return the disjoint object property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertyAxiomsByProperty();

    /** Gets the disjoint union axioms by class.
     * 
     * @return the disjoint union axioms by class */
    @Nonnull
    Pointer<OWLClass, OWLDisjointUnionAxiom> getDisjointUnionAxiomsByClass();

    /** Gets the equivalent classes axioms by class.
     * 
     * @return the equivalent classes axioms by class */
    @Nonnull
    Pointer<OWLClass, OWLEquivalentClassesAxiom> getEquivalentClassesAxiomsByClass();

    /** Gets the equivalent data property axioms by property.
     * 
     * @return the equivalent data property axioms by property */
    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertyAxiomsByProperty();

    /** Gets the equivalent object property axioms by property.
     * 
     * @return the equivalent object property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertyAxiomsByProperty();

    /** Gets the functional data property axioms by property.
     * 
     * @return the functional data property axioms by property */
    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom>
            getFunctionalDataPropertyAxiomsByProperty();

    /** Gets the functional object property axioms by property.
     * 
     * @return the functional object property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxiomsByProperty();

    /** Gets the general class axioms.
     * 
     * @return the general class axioms */
    @Nonnull
    Set<OWLClassAxiom> getGeneralClassAxioms();

    /** Gets the checks for key axioms by class.
     * 
     * @return the checks for key axioms by class */
    @Nonnull
    Pointer<OWLClass, OWLHasKeyAxiom> getHasKeyAxiomsByClass();

    /** Gets the imports declarations.
     * 
     * @return the imports declarations */
    @Nonnull
    Set<OWLImportsDeclaration> getImportsDeclarations();

    /** Gets the inverse functional property axioms by property.
     * 
     * @return the inverse functional property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalPropertyAxiomsByProperty();

    /** Gets the inverse property axioms by property.
     * 
     * @return the inverse property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom>
            getInversePropertyAxiomsByProperty();

    /** Gets the irreflexive property axioms by property.
     * 
     * @return the irreflexive property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexivePropertyAxiomsByProperty();

    /** Gets the logical axiom count.
     * 
     * @return the logical axiom count */
    int getLogicalAxiomCount();

    /** Gets the logical axioms.
     * 
     * @return the logical axioms */
    @Nonnull
    Set<OWLLogicalAxiom> getLogicalAxioms();

    /** Gets the negative data property assertion axioms by individual.
     * 
     * @return the negative data property assertion axioms by individual */
    @Nonnull
    Pointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxiomsByIndividual();

    /** Gets the negative object property assertion axioms by individual.
     * 
     * @return the negative object property assertion axioms by individual */
    @Nonnull
    Pointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxiomsByIndividual();

    /** Gets the object property assertions by individual.
     * 
     * @return the object property assertions by individual */
    @Nonnull
    Pointer<OWLIndividual, OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionsByIndividual();

    /** Gets the object property domain axioms by property.
     * 
     * @return the object property domain axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom>
            getObjectPropertyDomainAxiomsByProperty();

    /** Gets the object property range axioms by property.
     * 
     * @return the object property range axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom>
            getObjectPropertyRangeAxiomsByProperty();

    /** Gets the object sub property axioms by lhs.
     * 
     * @return the object sub property axioms by lhs */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByLHS();

    /** Gets the object sub property axioms by rhs.
     * 
     * @return the object sub property axioms by rhs */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByRHS();

    /** Gets the ontology annotations.
     * 
     * @return the ontology annotations */
    @Nonnull
    Set<OWLAnnotation> getOntologyAnnotations();

    /** Gets the owl annotation property references.
     * 
     * @return the owl annotation property references */
    @Nonnull
    Pointer<OWLAnnotationProperty, OWLAxiom> getOwlAnnotationPropertyReferences();

    /** Gets the owl anonymous individual references.
     * 
     * @return the owl anonymous individual references */
    @Nonnull
    Pointer<OWLAnonymousIndividual, OWLAxiom> getOwlAnonymousIndividualReferences();

    /** Gets the owl class references.
     * 
     * @return the owl class references */
    @Nonnull
    Pointer<OWLClass, OWLAxiom> getOwlClassReferences();

    /** Gets the owl data property references.
     * 
     * @return the owl data property references */
    @Nonnull
    Pointer<OWLDataProperty, OWLAxiom> getOwlDataPropertyReferences();

    /** Gets the owl datatype references.
     * 
     * @return the owl datatype references */
    @Nonnull
    Pointer<OWLDatatype, OWLAxiom> getOwlDatatypeReferences();

    /** Gets the owl individual references.
     * 
     * @return the owl individual references */
    @Nonnull
    Pointer<OWLNamedIndividual, OWLAxiom> getOwlIndividualReferences();

    /** Gets the owl object property references.
     * 
     * @return the owl object property references */
    @Nonnull
    Pointer<OWLObjectProperty, OWLAxiom> getOwlObjectPropertyReferences();

    /** Gets the reflexive property axioms by property.
     * 
     * @return the reflexive property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom>
            getReflexivePropertyAxiomsByProperty();

    /** Gets the same individuals axioms by individual.
     * 
     * @return the same individuals axioms by individual */
    @Nonnull
    Pointer<OWLIndividual, OWLSameIndividualAxiom> getSameIndividualsAxiomsByIndividual();

    /** Gets the sub class axioms by lhs.
     * 
     * @return the sub class axioms by lhs */
    @Nonnull
    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByLHS();

    /** Gets the sub class axioms by rhs.
     * 
     * @return the sub class axioms by rhs */
    @Nonnull
    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByRHS();

    /** Gets the symmetric property axioms by property.
     * 
     * @return the symmetric property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom>
            getSymmetricPropertyAxiomsByProperty();

    /** Gets the transitive property axioms by property.
     * 
     * @return the transitive property axioms by property */
    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom>
            getTransitivePropertyAxiomsByProperty();

    /** Gets the values.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param pointer
     *            the pointer
     * @param key
     *            the key
     * @return the values */
    @Nonnull
    <K, V extends OWLAxiom> Set<V> getValues(@Nonnull Pointer<K, V> pointer,
            @Nonnull K key);

    /** Checks for values.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param pointer
     *            the pointer
     * @param key
     *            the key
     * @return true, if successful */
    @Nonnull
    <K, V extends OWLAxiom> boolean hasValues(@Nonnull Pointer<K, V> pointer,
            @Nonnull K key);

    /** Gets the keyset.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param pointer
     *            the pointer
     * @return the keyset */
    @Nonnull
    <K, V extends OWLAxiom> Set<K> getKeyset(@Nonnull Pointer<K, V> pointer);

    /** Filter axioms.
     * 
     * @param <T>
     *            the generic type
     * @param <K>
     *            the key type
     * @param filter
     *            the filter
     * @param key
     *            the key
     * @return the sets the */
    @Nonnull
    <T extends OWLAxiom, K> Set<T> filterAxioms(
            @Nonnull OWLAxiomSearchFilter<T, K> filter, @Nonnull K key);

    /** Checks if is declared.
     * 
     * @param ax
     *            the ax
     * @return true, if is declared */
    boolean isDeclared(@Nonnull OWLDeclarationAxiom ax);

    /** Checks if is empty.
     * 
     * @return true, if is empty */
    boolean isEmpty();

    /** Adds the axiom.
     * 
     * @param axiom
     *            the axiom
     * @return true, if successful */
    boolean addAxiom(@Nonnull OWLAxiom axiom);

    /** Removes the axiom.
     * 
     * @param axiom
     *            the axiom
     * @return true, if successful */
    boolean removeAxiom(@Nonnull OWLAxiom axiom);

    /** Removes the general class axioms.
     * 
     * @param ax
     *            the ax */
    void removeGeneralClassAxioms(@Nonnull OWLClassAxiom ax);

    /** Removes the imports declaration.
     * 
     * @param importDeclaration
     *            declaration to be added
     * @return true if the import declaration was present, false otherwise */
    boolean removeImportsDeclaration(@Nonnull OWLImportsDeclaration importDeclaration);

    /** Removes the ontology annotation.
     * 
     * @param ann
     *            the ann
     * @return true, if successful */
    boolean removeOntologyAnnotation(@Nonnull OWLAnnotation ann);

    /** Removes the.
     * 
     * @param <K>
     *            the key type
     * @param <V>
     *            the value type
     * @param pointer
     *            the pointer
     * @param k
     *            the k
     * @param v
     *            the v
     * @return true, if successful */
    @Nonnull
    <K, V extends OWLAxiom> boolean remove(@Nonnull Pointer<K, V> pointer, @Nonnull K k,
            @Nonnull V v);

    /** Removes the property chain sub property axioms.
     * 
     * @param ax
     *            the ax */
    void removePropertyChainSubPropertyAxioms(@Nonnull OWLSubPropertyChainOfAxiom ax);
}
