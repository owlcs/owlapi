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

@SuppressWarnings("javadoc")
public interface Internals {
    /** a marker interface for objects that identify contained maps - so that
     * getting the keys of a specific map does not require a specific method for
     * each map nor does it require the map to be copied and returned */
    interface Pointer<K, V> {}

    /** a marker interface for objects that identify contained sets */
    interface SimplePointer<K> {}

    Pointer<AxiomType<?>, OWLAxiom> getAxiomsByType();

    <K, V extends OWLAxiom> boolean add(@Nonnull Pointer<K, V> pointer, @Nonnull K key,
            @Nonnull V value);

    void addGeneralClassAxioms(@Nonnull OWLClassAxiom ax);

    /** @param importDeclaration
     *            declaration to be added
     * @return true if the import declaration was not already present, false
     *         otherwise */
    boolean addImportsDeclaration(@Nonnull OWLImportsDeclaration importDeclaration);

    boolean addOntologyAnnotation(@Nonnull OWLAnnotation ann);

    void addPropertyChainSubPropertyAxioms(@Nonnull OWLSubPropertyChainOfAxiom ax);

    <K, V extends OWLAxiom> boolean
            contains(@Nonnull Pointer<K, V> pointer, @Nonnull K k);

    <K, V extends OWLAxiom> boolean contains(@Nonnull Pointer<K, V> pointer,
            @Nonnull K k, @Nonnull V v);

    @Nonnull
    Pointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom>
            getAnnotationAssertionAxiomsBySubject();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricPropertyAxiomsByProperty();

    int getAxiomCount();

    <T extends OWLAxiom> int getAxiomCount(@Nonnull AxiomType<T> axiomType);

    @Nonnull
    Set<OWLAxiom> getAxioms();

    @Nonnull
    Pointer<OWLClassExpression, OWLClassAssertionAxiom> getClassAssertionAxiomsByClass();

    @Nonnull
    Pointer<OWLIndividual, OWLClassAssertionAxiom> getClassAssertionAxiomsByIndividual();

    @Nonnull
    Pointer<OWLClass, OWLClassAxiom> getClassAxiomsByClass();

    @Nonnull
    Pointer<OWLIndividual, OWLDataPropertyAssertionAxiom>
            getDataPropertyAssertionsByIndividual();

    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom>
            getDataPropertyDomainAxiomsByProperty();

    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom>
            getDataPropertyRangeAxiomsByProperty();

    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByLHS();

    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByRHS();

    @Nonnull
    Pointer<OWLEntity, OWLDeclarationAxiom> getDeclarationsByEntity();

    @Nonnull
    Pointer<OWLIndividual, OWLDifferentIndividualsAxiom>
            getDifferentIndividualsAxiomsByIndividual();

    @Nonnull
    Pointer<OWLClass, OWLDisjointClassesAxiom> getDisjointClassesAxiomsByClass();

    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom>
            getDisjointDataPropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLClass, OWLDisjointUnionAxiom> getDisjointUnionAxiomsByClass();

    @Nonnull
    Pointer<OWLClass, OWLEquivalentClassesAxiom> getEquivalentClassesAxiomsByClass();

    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom>
            getFunctionalDataPropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxiomsByProperty();

    @Nonnull
    Set<OWLClassAxiom> getGeneralClassAxioms();

    @Nonnull
    Pointer<OWLClass, OWLHasKeyAxiom> getHasKeyAxiomsByClass();

    @Nonnull
    Set<OWLImportsDeclaration> getImportsDeclarations();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalPropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom>
            getInversePropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexivePropertyAxiomsByProperty();

    int getLogicalAxiomCount();

    @Nonnull
    Set<OWLLogicalAxiom> getLogicalAxioms();

    @Nonnull
    Pointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxiomsByIndividual();

    @Nonnull
    Pointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxiomsByIndividual();

    @Nonnull
    Pointer<OWLIndividual, OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionsByIndividual();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom>
            getObjectPropertyDomainAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom>
            getObjectPropertyRangeAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByLHS();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByRHS();

    @Nonnull
    Set<OWLAnnotation> getOntologyAnnotations();

    @Nonnull
    Pointer<OWLAnnotationProperty, OWLAxiom> getOwlAnnotationPropertyReferences();

    @Nonnull
    Pointer<OWLAnonymousIndividual, OWLAxiom> getOwlAnonymousIndividualReferences();

    @Nonnull
    Pointer<OWLClass, OWLAxiom> getOwlClassReferences();

    @Nonnull
    Pointer<OWLDataProperty, OWLAxiom> getOwlDataPropertyReferences();

    @Nonnull
    Pointer<OWLDatatype, OWLAxiom> getOwlDatatypeReferences();

    @Nonnull
    Pointer<OWLNamedIndividual, OWLAxiom> getOwlIndividualReferences();

    @Nonnull
    Pointer<OWLObjectProperty, OWLAxiom> getOwlObjectPropertyReferences();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom>
            getReflexivePropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLIndividual, OWLSameIndividualAxiom> getSameIndividualsAxiomsByIndividual();

    @Nonnull
    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByLHS();

    @Nonnull
    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByRHS();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom>
            getSymmetricPropertyAxiomsByProperty();

    @Nonnull
    Pointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom>
            getTransitivePropertyAxiomsByProperty();

    @Nonnull
    <K, V extends OWLAxiom> Set<V> getValues(@Nonnull Pointer<K, V> pointer,
            @Nonnull K key);

    @Nonnull
    <K, V extends OWLAxiom> boolean hasValues(@Nonnull Pointer<K, V> pointer,
            @Nonnull K key);

    @Nonnull
    <K, V extends OWLAxiom> Set<K> getKeyset(@Nonnull Pointer<K, V> pointer);

    @Nonnull
    <T extends OWLAxiom, K> Set<T> filterAxioms(
            @Nonnull OWLAxiomSearchFilter<T, K> filter, @Nonnull K key);

    boolean isDeclared(@Nonnull OWLDeclarationAxiom ax);

    boolean isEmpty();

    boolean addAxiom(@Nonnull OWLAxiom axiom);

    boolean removeAxiom(@Nonnull OWLAxiom axiom);

    void removeGeneralClassAxioms(@Nonnull OWLClassAxiom ax);

    /** @param importDeclaration
     *            declaration to be added
     * @return true if the import declaration was present, false otherwise */
    boolean removeImportsDeclaration(@Nonnull OWLImportsDeclaration importDeclaration);

    boolean removeOntologyAnnotation(@Nonnull OWLAnnotation ann);

    @Nonnull
    <K, V extends OWLAxiom> boolean remove(@Nonnull Pointer<K, V> pointer, @Nonnull K k,
            @Nonnull V v);

    void removePropertyChainSubPropertyAxioms(@Nonnull OWLSubPropertyChainOfAxiom ax);
}
