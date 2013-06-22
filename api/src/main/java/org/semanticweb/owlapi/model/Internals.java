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

import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

@SuppressWarnings("javadoc")
public interface Internals {
    /** a marker interface for objects that identify contained maps - so that
     * getting the keys of a specific map does not require a specific method for
     * each map nor does it require the map to be copied and returned */
    public interface Pointer<K, V> {}

    /** a marker interface for objects that identify contained sets */
    public interface SimplePointer<K> {}

    Pointer<AxiomType<?>, OWLAxiom> getAxiomsByType();

    <K, V extends OWLAxiom> boolean add(Pointer<K, V> pointer, K key, V value);

    void addGeneralClassAxioms(OWLClassAxiom ax);

    /** @param importDeclaration
     *            declaration to be added
     * @return true if the import declaration was not already present, false
     *         otherwise */
    boolean addImportsDeclaration(OWLImportsDeclaration importDeclaration);

    boolean addOntologyAnnotation(OWLAnnotation ann);

    void addPropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax);

    <K, V extends OWLAxiom> boolean contains(Pointer<K, V> pointer, K k);

    <K, V extends OWLAxiom> boolean contains(Pointer<K, V> pointer, K k, V v);

    Pointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom>
            getAnnotationAssertionAxiomsBySubject();

    Pointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricPropertyAxiomsByProperty();

    int getAxiomCount();

    <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType);

    Set<OWLAxiom> getAxioms();

    Pointer<OWLClassExpression, OWLClassAssertionAxiom> getClassAssertionAxiomsByClass();

    Pointer<OWLIndividual, OWLClassAssertionAxiom> getClassAssertionAxiomsByIndividual();

    Pointer<OWLClass, OWLClassAxiom> getClassAxiomsByClass();

    Pointer<OWLIndividual, OWLDataPropertyAssertionAxiom>
            getDataPropertyAssertionsByIndividual();

    Pointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom>
            getDataPropertyDomainAxiomsByProperty();

    Pointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom>
            getDataPropertyRangeAxiomsByProperty();

    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByLHS();

    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByRHS();

    Pointer<OWLEntity, OWLDeclarationAxiom> getDeclarationsByEntity();

    Pointer<OWLIndividual, OWLDifferentIndividualsAxiom>
            getDifferentIndividualsAxiomsByIndividual();

    Pointer<OWLClass, OWLDisjointClassesAxiom> getDisjointClassesAxiomsByClass();

    Pointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom>
            getDisjointDataPropertyAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertyAxiomsByProperty();

    Pointer<OWLClass, OWLDisjointUnionAxiom> getDisjointUnionAxiomsByClass();

    Pointer<OWLClass, OWLEquivalentClassesAxiom> getEquivalentClassesAxiomsByClass();

    Pointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertyAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertyAxiomsByProperty();

    Pointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom>
            getFunctionalDataPropertyAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxiomsByProperty();

    Set<OWLClassAxiom> getGeneralClassAxioms();

    Pointer<OWLClass, OWLHasKeyAxiom> getHasKeyAxiomsByClass();

    Set<OWLImportsDeclaration> getImportsDeclarations();

    Pointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalPropertyAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom>
            getInversePropertyAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexivePropertyAxiomsByProperty();

    int getLogicalAxiomCount();

    Set<OWLLogicalAxiom> getLogicalAxioms();

    Pointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxiomsByIndividual();

    Pointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxiomsByIndividual();

    Pointer<OWLIndividual, OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionsByIndividual();

    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom>
            getObjectPropertyDomainAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom>
            getObjectPropertyRangeAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByLHS();

    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByRHS();

    Set<OWLAnnotation> getOntologyAnnotations();

    Pointer<OWLAnnotationProperty, OWLAxiom> getOwlAnnotationPropertyReferences();

    Pointer<OWLAnonymousIndividual, OWLAxiom> getOwlAnonymousIndividualReferences();

    Pointer<OWLClass, OWLAxiom> getOwlClassReferences();

    Pointer<OWLDataProperty, OWLAxiom> getOwlDataPropertyReferences();

    Pointer<OWLDatatype, OWLAxiom> getOwlDatatypeReferences();

    Pointer<OWLNamedIndividual, OWLAxiom> getOwlIndividualReferences();

    Pointer<OWLObjectProperty, OWLAxiom> getOwlObjectPropertyReferences();

    Pointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom>
            getReflexivePropertyAxiomsByProperty();

    Pointer<OWLIndividual, OWLSameIndividualAxiom> getSameIndividualsAxiomsByIndividual();

    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByLHS();

    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByRHS();

    Pointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom>
            getSymmetricPropertyAxiomsByProperty();

    Pointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom>
            getTransitivePropertyAxiomsByProperty();

    <K, V extends OWLAxiom> Set<V> getValues(Pointer<K, V> pointer, K key);

    <K, V extends OWLAxiom> boolean hasValues(Pointer<K, V> pointer, K key);

    <K, V extends OWLAxiom> Set<K> getKeyset(Pointer<K, V> pointer);

    <T extends OWLAxiom, K> Set<T> filterAxioms(OWLAxiomSearchFilter<T, K> filter, K key);

    boolean isDeclared(OWLDeclarationAxiom ax);

    boolean isEmpty();

    boolean addAxiom(OWLAxiom axiom);

    boolean removeAxiom(OWLAxiom axiom);

    void removeGeneralClassAxioms(OWLClassAxiom ax);

    /** @param importDeclaration
     *            declaration to be added
     * @return true if the import declaration was present, false otherwise */
    boolean removeImportsDeclaration(OWLImportsDeclaration importDeclaration);

    boolean removeOntologyAnnotation(OWLAnnotation ann);

    <K, V extends OWLAxiom> boolean remove(Pointer<K, V> pointer, K k, V v);

    void removePropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax);
}
