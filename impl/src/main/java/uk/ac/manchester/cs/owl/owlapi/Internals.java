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
package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;

/** @author ignazio */
public interface Internals {

    /**
     * a marker interface for objects that identify contained maps - so that
     * getting the keys of a specific map does not require a specific method for
     * each map nor does it require the map to be copied and returned.
     * 
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     */
    public interface Pointer<K, V> {}

    /**
     * a marker interface for objects that identify contained sets.
     * 
     * @param <K>
     *        key type
     */
    public interface SimplePointer<K> {}

    /** @return axioms by type */
    Pointer<AxiomType<?>, OWLAxiom> getAxiomsByType();

    /**
     * @param pointer
     *        pointer to work on
     * @param key
     *        key
     * @param value
     *        value
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return true if added
     */
    <K, V extends OWLAxiom> boolean add(Pointer<K, V> pointer, K key, V value);

    /**
     * @param ax
     *        gci to add
     */
    void addGeneralClassAxioms(OWLClassAxiom ax);

    /**
     * @param importDeclaration
     *        declaration to be added
     * @return true if the import declaration was not already present, false
     *         otherwise
     */
    boolean addImportsDeclaration(OWLImportsDeclaration importDeclaration);

    /**
     * @param ann
     *        annotation to add
     * @return true if change made
     */
    boolean addOntologyAnnotation(OWLAnnotation ann);

    /**
     * @param ax
     *        axiom to add
     */
    void addPropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax);

    /**
     * @param pointer
     *        pointer to work on
     * @param k
     *        key
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return true if k contained
     */
    <K, V extends OWLAxiom> boolean contains(Pointer<K, V> pointer, K k);

    /**
     * @param pointer
     *        pointer to work on
     * @param k
     *        key
     * @param v
     *        value
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return true if k and v contained
     */
    <K, V extends OWLAxiom> boolean contains(Pointer<K, V> pointer, K k, V v);

    /** @return pointer */
    Pointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom>
            getAnnotationAssertionAxiomsBySubject();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricPropertyAxiomsByProperty();

    /** @return axiom count */
    int getAxiomCount();

    /**
     * @param axiomType
     *        axiom type
     * @param <T>
     *        axiom type
     * @return count of axioms
     */
    <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType);

    /** @return pointer */
    Set<OWLAxiom> getAxioms();

    /** @return pointer */
    Pointer<OWLClassExpression, OWLClassAssertionAxiom>
            getClassAssertionAxiomsByClass();

    /** @return pointer */
    Pointer<OWLIndividual, OWLClassAssertionAxiom>
            getClassAssertionAxiomsByIndividual();

    /** @return pointer */
    Pointer<OWLClass, OWLClassAxiom> getClassAxiomsByClass();

    /** @return pointer */
    Pointer<OWLIndividual, OWLDataPropertyAssertionAxiom>
            getDataPropertyAssertionsByIndividual();

    /** @return pointer */
    Pointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom>
            getDataPropertyDomainAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom>
            getDataPropertyRangeAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByLHS();

    /** @return pointer */
    Pointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByRHS();

    /** @return pointer */
    Pointer<OWLEntity, OWLDeclarationAxiom> getDeclarationsByEntity();

    /** @return pointer */
    Pointer<OWLIndividual, OWLDifferentIndividualsAxiom>
            getDifferentIndividualsAxiomsByIndividual();

    /** @return pointer */
    Pointer<OWLClass, OWLDisjointClassesAxiom>
            getDisjointClassesAxiomsByClass();

    /** @return pointer */
    Pointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom>
            getDisjointDataPropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLClass, OWLDisjointUnionAxiom> getDisjointUnionAxiomsByClass();

    /** @return pointer */
    Pointer<OWLClass, OWLEquivalentClassesAxiom>
            getEquivalentClassesAxiomsByClass();

    /** @return pointer */
    Pointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom>
            getFunctionalDataPropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxiomsByProperty();

    /** @return gci */
    Set<OWLClassAxiom> getGeneralClassAxioms();

    /** @return pointer */
    Pointer<OWLClass, OWLHasKeyAxiom> getHasKeyAxiomsByClass();

    /** @return imports declarations */
    Set<OWLImportsDeclaration> getImportsDeclarations();

    /** @return pointer */
            Pointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalPropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom>
            getInversePropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexivePropertyAxiomsByProperty();

    /** @return number of logical axioms */
    int getLogicalAxiomCount();

    /** @return logical axioms */
    Set<OWLLogicalAxiom> getLogicalAxioms();

    /** @return pointer */
    Pointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxiomsByIndividual();

    /** @return pointer */
    Pointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxiomsByIndividual();

    /** @return pointer */
    Pointer<OWLIndividual, OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionsByIndividual();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom>
            getObjectPropertyDomainAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom>
            getObjectPropertyRangeAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByLHS();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByRHS();

    /** @return ontology annotations */
    Set<OWLAnnotation> getOntologyAnnotations();

    /** @return pointer */
    Pointer<OWLAnnotationProperty, OWLAxiom>
            getOwlAnnotationPropertyReferences();

    /** @return pointer */
    Pointer<OWLAnonymousIndividual, OWLAxiom>
            getOwlAnonymousIndividualReferences();

    /** @return pointer */
    Pointer<OWLClass, OWLAxiom> getOwlClassReferences();

    /** @return pointer */
    Pointer<OWLDataProperty, OWLAxiom> getOwlDataPropertyReferences();

    /** @return pointer */
    Pointer<OWLDatatype, OWLAxiom> getOwlDatatypeReferences();

    /** @return pointer */
    Pointer<OWLNamedIndividual, OWLAxiom> getOwlIndividualReferences();

    /** @return pointer */
    Pointer<OWLObjectProperty, OWLAxiom> getOwlObjectPropertyReferences();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom>
            getReflexivePropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLIndividual, OWLSameIndividualAxiom>
            getSameIndividualsAxiomsByIndividual();

    /** @return pointer */
    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByLHS();

    /** @return pointer */
    Pointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByRHS();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom>
            getSymmetricPropertyAxiomsByProperty();

    /** @return pointer */
    Pointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom>
            getTransitivePropertyAxiomsByProperty();

    /**
     * @param pointer
     *        pointer to work on
     * @param key
     *        key
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return values for pointer
     */
    <K, V extends OWLAxiom> Set<V> getValues(Pointer<K, V> pointer, K key);

    /**
     * @param pointer
     *        pointer to work on
     * @param key
     *        key
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return true if there are values
     */
    <K, V extends OWLAxiom> boolean hasValues(Pointer<K, V> pointer, K key);

    /**
     * @param pointer
     *        pointer to work on
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return keyset
     */
    <K, V extends OWLAxiom> Set<K> getKeyset(Pointer<K, V> pointer);

    /**
     * @param filter
     *        filter for search
     * @param key
     *        key
     * @param <K>
     *        key type
     * @param <T>
     *        value type
     * @return set of objects selected
     */
    <T extends OWLAxiom, K> Set<T> filterAxioms(
            OWLAxiomSearchFilter<T, K> filter, K key);

    /**
     * @param ax
     *        axiom
     * @return true if declared
     */
    boolean isDeclared(OWLDeclarationAxiom ax);

    /** @return true if empty */
    boolean isEmpty();

    /**
     * @param axiom
     *        axiom to add
     * @return true if match added
     */
    boolean addAxiom(OWLAxiom axiom);

    /**
     * @param axiom
     *        axiom to remove
     * @return true if match removed
     */
    boolean removeAxiom(OWLAxiom axiom);

    /**
     * @param ax
     *        axiom to remove
     */
    void removeGeneralClassAxioms(OWLClassAxiom ax);

    /**
     * @param importDeclaration
     *        declaration to be added
     * @return true if the import declaration was present, false otherwise
     */
    boolean removeImportsDeclaration(OWLImportsDeclaration importDeclaration);

    /**
     * @param ann
     *        annotation to remove
     * @return true if match removed
     */
    boolean removeOntologyAnnotation(OWLAnnotation ann);

    /**
     * @param pointer
     *        pointer to work on
     * @param k
     *        key
     * @param v
     *        value
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @return true if match removed
     */
    <K, V extends OWLAxiom> boolean remove(Pointer<K, V> pointer, K k, V v);

    /**
     * @param ax
     *        chain to remove
     */
    void removePropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax);
}
