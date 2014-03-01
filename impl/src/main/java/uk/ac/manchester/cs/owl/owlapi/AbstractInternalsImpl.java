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

import static org.semanticweb.owlapi.model.AxiomType.*;
import static uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.*;

import java.io.Serializable;
import java.util.Map;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.util.CollectionFactory;

/** this class encapsulates all lazily built indexes. */
public abstract class AbstractInternalsImpl implements Internals, Serializable {

    private static final long serialVersionUID = 30406L;

    protected <K, V extends OWLAxiom> MapPointer<K, V> build(AxiomType<?> t,
            OWLAxiomVisitorEx<?> v) {
        return new MapPointer<K, V>(t, v, true, this);
    }

    protected <K, V extends OWLAxiom> MapPointer<K, V> build() {
        return build(null, null);
    }

    protected <K, V extends OWLAxiom> MapPointer<K, V> buildLazy(
            AxiomType<?> t, OWLAxiomVisitorEx<?> v) {
        return new MapPointer<K, V>(t, v, false, this);
    }

    protected ClassAxiomByClassPointer buildClassAxiomByClass() {
        return new ClassAxiomByClassPointer(null, null, false, this);
    }

    protected final MapPointer<OWLClass, OWLClassAxiom> classAxiomsByClass = buildClassAxiomByClass();
    protected final MapPointer<OWLClass, OWLSubClassOfAxiom> subClassAxiomsByLHS = buildLazy(
            SUBCLASS_OF, classsubnamed);
    protected final MapPointer<OWLClass, OWLSubClassOfAxiom> subClassAxiomsByRHS = buildLazy(
            SUBCLASS_OF, classsupernamed);
    protected final MapPointer<OWLClass, OWLEquivalentClassesAxiom> equivalentClassesAxiomsByClass = buildLazy(
            EQUIVALENT_CLASSES, classcollections);
    protected final MapPointer<OWLClass, OWLDisjointClassesAxiom> disjointClassesAxiomsByClass = buildLazy(
            DISJOINT_CLASSES, classcollections);
    protected final MapPointer<OWLClass, OWLDisjointUnionAxiom> disjointUnionAxiomsByClass = buildLazy(
            DISJOINT_UNION, classcollections);
    protected final MapPointer<OWLClass, OWLHasKeyAxiom> hasKeyAxiomsByClass = buildLazy(
            HAS_KEY, classsupernamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsByLHS = buildLazy(
            SUB_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom> objectSubPropertyAxiomsByRHS = buildLazy(
            SUB_OBJECT_PROPERTY, opsupernamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom> equivalentObjectPropertyAxiomsByProperty = buildLazy(
            EQUIVALENT_OBJECT_PROPERTIES, opcollections);
    protected final MapPointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom> disjointObjectPropertyAxiomsByProperty = buildLazy(
            DISJOINT_OBJECT_PROPERTIES, opcollections);
    protected final MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom> objectPropertyDomainAxiomsByProperty = buildLazy(
            OBJECT_PROPERTY_DOMAIN, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom> objectPropertyRangeAxiomsByProperty = buildLazy(
            OBJECT_PROPERTY_RANGE, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom> functionalObjectPropertyAxiomsByProperty = buildLazy(
            FUNCTIONAL_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom> inverseFunctionalPropertyAxiomsByProperty = buildLazy(
            INVERSE_FUNCTIONAL_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom> symmetricPropertyAxiomsByProperty = buildLazy(
            SYMMETRIC_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom> asymmetricPropertyAxiomsByProperty = buildLazy(
            ASYMMETRIC_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom> reflexivePropertyAxiomsByProperty = buildLazy(
            REFLEXIVE_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom> irreflexivePropertyAxiomsByProperty = buildLazy(
            IRREFLEXIVE_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom> transitivePropertyAxiomsByProperty = buildLazy(
            TRANSITIVE_OBJECT_PROPERTY, opsubnamed);
    protected final MapPointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom> inversePropertyAxiomsByProperty = buildLazy(
            INVERSE_OBJECT_PROPERTIES, opcollections);
    protected final MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsByLHS = buildLazy(
            SUB_DATA_PROPERTY, dpsubnamed);
    protected final MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom> dataSubPropertyAxiomsByRHS = buildLazy(
            SUB_DATA_PROPERTY, dpsupernamed);
    protected final MapPointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom> equivalentDataPropertyAxiomsByProperty = buildLazy(
            EQUIVALENT_DATA_PROPERTIES, dpcollections);
    protected final MapPointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom> disjointDataPropertyAxiomsByProperty = buildLazy(
            DISJOINT_DATA_PROPERTIES, dpcollections);
    protected final MapPointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom> dataPropertyDomainAxiomsByProperty = buildLazy(
            DATA_PROPERTY_DOMAIN, dpsubnamed);
    protected final MapPointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom> dataPropertyRangeAxiomsByProperty = buildLazy(
            DATA_PROPERTY_RANGE, dpsubnamed);
    protected final MapPointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom> functionalDataPropertyAxiomsByProperty = buildLazy(
            FUNCTIONAL_DATA_PROPERTY, dpsubnamed);
    protected final MapPointer<OWLIndividual, OWLClassAssertionAxiom> classAssertionAxiomsByIndividual = buildLazy(
            CLASS_ASSERTION, individualsubnamed);
    protected final MapPointer<OWLClassExpression, OWLClassAssertionAxiom> classAssertionAxiomsByClass = buildLazy(
            CLASS_ASSERTION, classexpressions);
    protected final MapPointer<OWLIndividual, OWLObjectPropertyAssertionAxiom> objectPropertyAssertionsByIndividual = buildLazy(
            OBJECT_PROPERTY_ASSERTION, individualsubnamed);
    protected final MapPointer<OWLIndividual, OWLDataPropertyAssertionAxiom> dataPropertyAssertionsByIndividual = buildLazy(
            DATA_PROPERTY_ASSERTION, individualsubnamed);
    protected final MapPointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom> negativeObjectPropertyAssertionAxiomsByIndividual = buildLazy(
            NEGATIVE_OBJECT_PROPERTY_ASSERTION, individualsubnamed);
    protected final MapPointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom> negativeDataPropertyAssertionAxiomsByIndividual = buildLazy(
            NEGATIVE_DATA_PROPERTY_ASSERTION, individualsubnamed);
    protected final MapPointer<OWLIndividual, OWLDifferentIndividualsAxiom> differentIndividualsAxiomsByIndividual = buildLazy(
            DIFFERENT_INDIVIDUALS, icollections);
    protected final MapPointer<OWLIndividual, OWLSameIndividualAxiom> sameIndividualsAxiomsByIndividual = buildLazy(
            SAME_INDIVIDUAL, icollections);
    protected final MapPointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom> annotationAssertionAxiomsBySubject = buildLazy(
            ANNOTATION_ASSERTION, annotsupernamed);

    protected <K, V> Map<K, V> createMap() {
        return CollectionFactory.createMap();
    }

    @Override
    public MapPointer<OWLClass, OWLClassAxiom> getClassAxiomsByClass() {
        return classAxiomsByClass;
    }

    @Override
    public MapPointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByLHS() {
        return subClassAxiomsByLHS;
    }

    @Override
    public MapPointer<OWLClass, OWLSubClassOfAxiom> getSubClassAxiomsByRHS() {
        return subClassAxiomsByRHS;
    }

    @Override
    public MapPointer<OWLClass, OWLEquivalentClassesAxiom>
            getEquivalentClassesAxiomsByClass() {
        return equivalentClassesAxiomsByClass;
    }

    @Override
    public MapPointer<OWLClass, OWLDisjointClassesAxiom>
            getDisjointClassesAxiomsByClass() {
        return disjointClassesAxiomsByClass;
    }

    @Override
    public MapPointer<OWLClass, OWLDisjointUnionAxiom>
            getDisjointUnionAxiomsByClass() {
        return disjointUnionAxiomsByClass;
    }

    @Override
    public MapPointer<OWLClass, OWLHasKeyAxiom> getHasKeyAxiomsByClass() {
        return hasKeyAxiomsByClass;
    }

    @Override
    public MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByLHS() {
        return objectSubPropertyAxiomsByLHS;
    }

    @Override
    public MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>
            getObjectSubPropertyAxiomsByRHS() {
        return objectSubPropertyAxiomsByRHS;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom>
            getEquivalentObjectPropertyAxiomsByProperty() {
        return equivalentObjectPropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom>
            getDisjointObjectPropertyAxiomsByProperty() {
        return disjointObjectPropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom>
            getObjectPropertyDomainAxiomsByProperty() {
        return objectPropertyDomainAxiomsByProperty;
    }

    @Override
    public MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom>
            getObjectPropertyRangeAxiomsByProperty() {
        return objectPropertyRangeAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom>
            getFunctionalObjectPropertyAxiomsByProperty() {
        return functionalObjectPropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom>
            getInverseFunctionalPropertyAxiomsByProperty() {
        return inverseFunctionalPropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom>
            getSymmetricPropertyAxiomsByProperty() {
        return symmetricPropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom>
            getAsymmetricPropertyAxiomsByProperty() {
        return asymmetricPropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom>
            getReflexivePropertyAxiomsByProperty() {
        return reflexivePropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom>
            getIrreflexivePropertyAxiomsByProperty() {
        return irreflexivePropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom>
            getTransitivePropertyAxiomsByProperty() {
        return transitivePropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom>
            getInversePropertyAxiomsByProperty() {
        return inversePropertyAxiomsByProperty;
    }

    @Override
    public MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByLHS() {
        return dataSubPropertyAxiomsByLHS;
    }

    @Override
    public MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>
            getDataSubPropertyAxiomsByRHS() {
        return dataSubPropertyAxiomsByRHS;
    }

    @Override
    public
            MapPointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom>
            getEquivalentDataPropertyAxiomsByProperty() {
        return equivalentDataPropertyAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom>
            getDisjointDataPropertyAxiomsByProperty() {
        return disjointDataPropertyAxiomsByProperty;
    }

    @Override
    public MapPointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom>
            getDataPropertyDomainAxiomsByProperty() {
        return dataPropertyDomainAxiomsByProperty;
    }

    @Override
    public MapPointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom>
            getDataPropertyRangeAxiomsByProperty() {
        return dataPropertyRangeAxiomsByProperty;
    }

    @Override
    public
            MapPointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom>
            getFunctionalDataPropertyAxiomsByProperty() {
        return functionalDataPropertyAxiomsByProperty;
    }

    @Override
    public MapPointer<OWLIndividual, OWLClassAssertionAxiom>
            getClassAssertionAxiomsByIndividual() {
        return classAssertionAxiomsByIndividual;
    }

    @Override
    public MapPointer<OWLClassExpression, OWLClassAssertionAxiom>
            getClassAssertionAxiomsByClass() {
        return classAssertionAxiomsByClass;
    }

    @Override
    public MapPointer<OWLIndividual, OWLObjectPropertyAssertionAxiom>
            getObjectPropertyAssertionsByIndividual() {
        return objectPropertyAssertionsByIndividual;
    }

    @Override
    public MapPointer<OWLIndividual, OWLDataPropertyAssertionAxiom>
            getDataPropertyAssertionsByIndividual() {
        return dataPropertyAssertionsByIndividual;
    }

    @Override
    public MapPointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom>
            getNegativeObjectPropertyAssertionAxiomsByIndividual() {
        return negativeObjectPropertyAssertionAxiomsByIndividual;
    }

    @Override
    public MapPointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom>
            getNegativeDataPropertyAssertionAxiomsByIndividual() {
        return negativeDataPropertyAssertionAxiomsByIndividual;
    }

    @Override
    public MapPointer<OWLIndividual, OWLDifferentIndividualsAxiom>
            getDifferentIndividualsAxiomsByIndividual() {
        return differentIndividualsAxiomsByIndividual;
    }

    @Override
    public MapPointer<OWLIndividual, OWLSameIndividualAxiom>
            getSameIndividualsAxiomsByIndividual() {
        return sameIndividualsAxiomsByIndividual;
    }

    @Override
    public MapPointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom>
            getAnnotationAssertionAxiomsBySubject() {
        return annotationAssertionAxiomsBySubject;
    }
}
