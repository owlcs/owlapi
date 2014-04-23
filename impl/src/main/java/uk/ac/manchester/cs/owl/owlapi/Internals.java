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
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.model.AxiomType.*;
import static org.semanticweb.owlapi.util.CollectionFactory.createSet;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static uk.ac.manchester.cs.owl.owlapi.InitVisitorFactory.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
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
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
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
import org.semanticweb.owlapi.model.OWLObject;
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
import org.semanticweb.owlapi.model.parameters.Search;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.OWLAxiomSearchFilter;
import org.semanticweb.owlapi.util.OWLAxiomVisitorAdapter;

/** @author ignazio */
public class Internals implements Serializable {

    private static final long serialVersionUID = 40000L;

    private class ReferenceChecker implements OWLEntityVisitorEx<Boolean>,
            Serializable {

        private static final long serialVersionUID = 40000L;

        public ReferenceChecker() {}

        @Override
        public Boolean visit(OWLClass cls) {
            return owlClassReferences.containsKey(cls);
        }

        @Override
        public Boolean visit(OWLObjectProperty property) {
            return owlObjectPropertyReferences.containsKey(property);
        }

        @Override
        public Boolean visit(OWLDataProperty property) {
            return owlDataPropertyReferences.containsKey(property);
        }

        @Override
        public Boolean visit(OWLNamedIndividual individual) {
            return owlIndividualReferences.containsKey(individual);
        }

        @Override
        public Boolean visit(OWLDatatype datatype) {
            return owlDatatypeReferences.containsKey(datatype);
        }

        @Override
        public Boolean visit(OWLAnnotationProperty property) {
            return owlAnnotationPropertyReferences.containsKey(property);
        }
    }

    protected class SetPointer<K> implements Serializable {

        private static final long serialVersionUID = 40000L;
        @Nonnull
        private final Set<K> set = createSet();

        public boolean isEmpty() {
            return set.isEmpty();
        }

        @Nonnull
        public Set<K> copy() {
            return CollectionFactory
                    .getCopyOnRequestSetFromMutableCollection(set);
        }

        @Nonnull
        public Iterable<K> iterable() {
            return set;
        }

        public boolean add(K k) {
            return set.add(k);
        }

        public boolean contains(K k) {
            return set.contains(k);
        }

        public boolean remove(K k) {
            return set.remove(k);
        }
    }

    //@formatter:off
    @Nonnull protected final MapPointer<OWLClassExpression, OWLClassAssertionAxiom>                          classAssertionAxiomsByClass                         = buildLazy(CLASS_ASSERTION, classexpressions);
    @Nonnull protected final MapPointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom>                   annotationAssertionAxiomsBySubject                  = buildLazy(ANNOTATION_ASSERTION, annotsupernamed);
    @Nonnull protected final MapPointer<OWLClass, OWLSubClassOfAxiom>                                        subClassAxiomsBySubPosition                                 = buildLazy(SUBCLASS_OF, classsubnamed);
    @Nonnull protected final MapPointer<OWLClass, OWLSubClassOfAxiom>                                        subClassAxiomsBySuperPosition                                 = buildLazy(SUBCLASS_OF, classsupernamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>            objectSubPropertyAxiomsBySubPosition                        = buildLazy(SUB_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>            objectSubPropertyAxiomsBySuperPosition                        = buildLazy(SUB_OBJECT_PROPERTY, opsupernamed);
    @Nonnull protected final MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>                dataSubPropertyAxiomsBySubPosition                          = buildLazy(SUB_DATA_PROPERTY, dpsubnamed);
    @Nonnull protected final MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>                dataSubPropertyAxiomsBySuperPosition                          = buildLazy(SUB_DATA_PROPERTY, dpsupernamed);

    @Nonnull protected final MapPointer<OWLClass, OWLClassAxiom>                                             classAxiomsByClass                                  = buildClassAxiomByClass();
    @Nonnull protected final MapPointer<OWLClass, OWLEquivalentClassesAxiom>                                 equivalentClassesAxiomsByClass                      = buildLazy(EQUIVALENT_CLASSES, classcollections);
    @Nonnull protected final MapPointer<OWLClass, OWLDisjointClassesAxiom>                                   disjointClassesAxiomsByClass                        = buildLazy(DISJOINT_CLASSES, classcollections);
    @Nonnull protected final MapPointer<OWLClass, OWLDisjointUnionAxiom>                                     disjointUnionAxiomsByClass                          = buildLazy(DISJOINT_UNION, classcollections);
    @Nonnull protected final MapPointer<OWLClass, OWLHasKeyAxiom>                                            hasKeyAxiomsByClass                                 = buildLazy(HAS_KEY, classsupernamed);

    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom>     equivalentObjectPropertyAxiomsByProperty            = buildLazy(EQUIVALENT_OBJECT_PROPERTIES, opcollections);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom>       disjointObjectPropertyAxiomsByProperty              = buildLazy(DISJOINT_OBJECT_PROPERTIES, opcollections);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom>           objectPropertyDomainAxiomsByProperty                = buildLazy(OBJECT_PROPERTY_DOMAIN, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom>            objectPropertyRangeAxiomsByProperty                 = buildLazy(OBJECT_PROPERTY_RANGE, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom>       functionalObjectPropertyAxiomsByProperty            = buildLazy(FUNCTIONAL_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom>inverseFunctionalPropertyAxiomsByProperty           = buildLazy(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom>        symmetricPropertyAxiomsByProperty                   = buildLazy(SYMMETRIC_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom>       asymmetricPropertyAxiomsByProperty                  = buildLazy(ASYMMETRIC_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom>        reflexivePropertyAxiomsByProperty                   = buildLazy(REFLEXIVE_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom>      irreflexivePropertyAxiomsByProperty                 = buildLazy(IRREFLEXIVE_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom>       transitivePropertyAxiomsByProperty                  = buildLazy(TRANSITIVE_OBJECT_PROPERTY, opsubnamed);
    @Nonnull protected final MapPointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom>        inversePropertyAxiomsByProperty                     = buildLazy(INVERSE_OBJECT_PROPERTIES, opcollections);

    @Nonnull protected final MapPointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom>         equivalentDataPropertyAxiomsByProperty              = buildLazy(EQUIVALENT_DATA_PROPERTIES, dpcollections);
    @Nonnull protected final MapPointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom>           disjointDataPropertyAxiomsByProperty                = buildLazy(DISJOINT_DATA_PROPERTIES, dpcollections);
    @Nonnull protected final MapPointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom>               dataPropertyDomainAxiomsByProperty                  = buildLazy(DATA_PROPERTY_DOMAIN, dpsubnamed);
    @Nonnull protected final MapPointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom>                dataPropertyRangeAxiomsByProperty                   = buildLazy(DATA_PROPERTY_RANGE, dpsubnamed);
    @Nonnull protected final MapPointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom>           functionalDataPropertyAxiomsByProperty              = buildLazy(FUNCTIONAL_DATA_PROPERTY, dpsubnamed);

    @Nonnull protected final MapPointer<OWLIndividual, OWLClassAssertionAxiom>                               classAssertionAxiomsByIndividual                    = buildLazy(CLASS_ASSERTION, individualsubnamed);
    @Nonnull protected final MapPointer<OWLIndividual, OWLObjectPropertyAssertionAxiom>                      objectPropertyAssertionsByIndividual                = buildLazy(OBJECT_PROPERTY_ASSERTION, individualsubnamed);
    @Nonnull protected final MapPointer<OWLIndividual, OWLDataPropertyAssertionAxiom>                        dataPropertyAssertionsByIndividual                  = buildLazy(DATA_PROPERTY_ASSERTION, individualsubnamed);
    @Nonnull protected final MapPointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom>              negativeObjectPropertyAssertionAxiomsByIndividual   = buildLazy(NEGATIVE_OBJECT_PROPERTY_ASSERTION, individualsubnamed);
    @Nonnull protected final MapPointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom>                negativeDataPropertyAssertionAxiomsByIndividual     = buildLazy(NEGATIVE_DATA_PROPERTY_ASSERTION, individualsubnamed);
    @Nonnull protected final MapPointer<OWLIndividual, OWLDifferentIndividualsAxiom>                         differentIndividualsAxiomsByIndividual              = buildLazy(DIFFERENT_INDIVIDUALS, icollections);
    @Nonnull protected final MapPointer<OWLIndividual, OWLSameIndividualAxiom>                               sameIndividualsAxiomsByIndividual                   = buildLazy(SAME_INDIVIDUAL, icollections);

    @Nonnull protected final SetPointer<OWLImportsDeclaration>               importsDeclarations                 = new SetPointer<OWLImportsDeclaration>();
    @Nonnull protected final SetPointer<OWLAnnotation>                       ontologyAnnotations                 = new SetPointer<OWLAnnotation>();
    @Nonnull protected final SetPointer<OWLClassAxiom>                       generalClassAxioms                  = new SetPointer<OWLClassAxiom>();
    @Nonnull protected final SetPointer<OWLSubPropertyChainOfAxiom>          propertyChainSubPropertyAxioms      = new SetPointer<OWLSubPropertyChainOfAxiom>();

    @Nonnull protected final MapPointer<AxiomType<?>, OWLAxiom>              axiomsByType                        = build();

    @Nonnull protected final MapPointer<OWLClass, OWLAxiom>                  owlClassReferences                  = build();
    @Nonnull protected final MapPointer<OWLObjectProperty, OWLAxiom>         owlObjectPropertyReferences         = build();
    @Nonnull protected final MapPointer<OWLDataProperty, OWLAxiom>           owlDataPropertyReferences           = build();
    @Nonnull protected final MapPointer<OWLNamedIndividual, OWLAxiom>        owlIndividualReferences             = build();
    @Nonnull protected final MapPointer<OWLAnonymousIndividual, OWLAxiom>    owlAnonymousIndividualReferences    = build();
    @Nonnull protected final MapPointer<OWLDatatype, OWLAxiom>               owlDatatypeReferences               = build();
    @Nonnull protected final MapPointer<OWLAnnotationProperty, OWLAxiom>     owlAnnotationPropertyReferences     = build();
    @Nonnull protected final MapPointer<OWLEntity, OWLDeclarationAxiom>      declarationsByEntity                = build();
//@formatter:on
    @Nonnull
    private final AddAxiomVisitor addChangeVisitor = new AddAxiomVisitor();
    @Nonnull
    private final RemoveAxiomVisitor removeChangeVisitor = new RemoveAxiomVisitor();
    @Nonnull
    private final ReferenceChecker refChecker = new ReferenceChecker();
    @Nonnull
    private final ReferencedAxiomsCollector refAxiomsCollector = new ReferencedAxiomsCollector();

    /**
     * @param type
     *        type of map key
     * @param axiom
     *        class of axiom indexed
     * @param <T>
     *        key type
     * @param <A>
     *        value type
     * @return map pointer matching the search, or null if there is not one
     */
    // not always not null, but supposed to
    @Nonnull
    <T extends OWLObject, A extends OWLAxiom> MapPointer<T, A> get(
            @Nonnull Class<T> type, @Nonnull Class<A> axiom) {
        return get(type, axiom, Search.IN_SUB_POSITION);
    }

    /**
     * @param type
     *        type of map key
     * @param axiom
     *        class of axiom indexed
     * @param position
     *        for axioms with a left/right distinction, IN_SUPER_POSITION means
     *        right index
     * @param <T>
     *        key type
     * @param <A>
     *        value type
     * @return map pointer matching the search, or null if there is not one
     */
    // not always not null, but supposed to be
    @Nonnull
    @SuppressWarnings("unchecked")
    <T extends OWLObject, A extends OWLAxiom> MapPointer<T, A> get(
            @Nonnull Class<T> type, @Nonnull Class<A> axiom, Search position) {
        if (OWLEntity.class.isAssignableFrom(type)
                && axiom.equals(OWLDeclarationAxiom.class)) {
            return (MapPointer<T, A>) declarationsByEntity;
        }
        if (type.equals(OWLClass.class) && axiom.equals(OWLAxiom.class)) {
            return (MapPointer<T, A>) owlClassReferences;
        }
        if (type.equals(OWLObjectProperty.class)
                && axiom.equals(OWLAxiom.class)) {
            return (MapPointer<T, A>) owlObjectPropertyReferences;
        }
        if (type.equals(OWLDataProperty.class) && axiom.equals(OWLAxiom.class)) {
            return (MapPointer<T, A>) owlDataPropertyReferences;
        }
        if (type.equals(OWLNamedIndividual.class)
                && axiom.equals(OWLAxiom.class)) {
            return (MapPointer<T, A>) owlIndividualReferences;
        }
        if (type.equals(OWLAnonymousIndividual.class)
                && axiom.equals(OWLAxiom.class)) {
            return (MapPointer<T, A>) owlAnonymousIndividualReferences;
        }
        if (type.equals(OWLDatatype.class) && axiom.equals(OWLAxiom.class)) {
            return (MapPointer<T, A>) owlDatatypeReferences;
        }
        if (type.equals(OWLAnnotationProperty.class)
                && axiom.equals(OWLAxiom.class)) {
            return (MapPointer<T, A>) owlAnnotationPropertyReferences;
        }
        if (type.equals(OWLClassExpression.class)) {
            return (MapPointer<T, A>) classAssertionAxiomsByClass;
        }
        if (type.equals(OWLObjectPropertyExpression.class)) {
            if (axiom.equals(OWLSubObjectPropertyOfAxiom.class)) {
                if (position == Search.IN_SUPER_POSITION) {
                    return (MapPointer<T, A>) objectSubPropertyAxiomsBySuperPosition;
                } else {
                    return (MapPointer<T, A>) objectSubPropertyAxiomsBySubPosition;
                }
            }
            if (axiom.equals(OWLEquivalentObjectPropertiesAxiom.class)) {
                return (MapPointer<T, A>) equivalentObjectPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLDisjointObjectPropertiesAxiom.class)) {
                return (MapPointer<T, A>) disjointObjectPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLObjectPropertyDomainAxiom.class)) {
                return (MapPointer<T, A>) objectPropertyDomainAxiomsByProperty;
            }
            if (axiom.equals(OWLObjectPropertyRangeAxiom.class)) {
                return (MapPointer<T, A>) objectPropertyRangeAxiomsByProperty;
            }
            if (axiom.equals(OWLFunctionalObjectPropertyAxiom.class)) {
                return (MapPointer<T, A>) functionalObjectPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLInverseFunctionalObjectPropertyAxiom.class)) {
                return (MapPointer<T, A>) inverseFunctionalPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLSymmetricObjectPropertyAxiom.class)) {
                return (MapPointer<T, A>) symmetricPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLAsymmetricObjectPropertyAxiom.class)) {
                return (MapPointer<T, A>) asymmetricPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLReflexiveObjectPropertyAxiom.class)) {
                return (MapPointer<T, A>) reflexivePropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLIrreflexiveObjectPropertyAxiom.class)) {
                return (MapPointer<T, A>) irreflexivePropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLTransitiveObjectPropertyAxiom.class)) {
                return (MapPointer<T, A>) transitivePropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLInverseObjectPropertiesAxiom.class)) {
                return (MapPointer<T, A>) inversePropertyAxiomsByProperty;
            }
        }
        if (type.equals(OWLDataPropertyExpression.class)) {
            if (axiom.equals(OWLSubDataPropertyOfAxiom.class)) {
                if (position == Search.IN_SUPER_POSITION) {
                    return (MapPointer<T, A>) dataSubPropertyAxiomsBySuperPosition;
                } else {
                    return (MapPointer<T, A>) dataSubPropertyAxiomsBySubPosition;
                }
            }
            if (axiom.equals(OWLEquivalentDataPropertiesAxiom.class)) {
                return (MapPointer<T, A>) equivalentDataPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLDisjointDataPropertiesAxiom.class)) {
                return (MapPointer<T, A>) disjointDataPropertyAxiomsByProperty;
            }
            if (axiom.equals(OWLDataPropertyDomainAxiom.class)) {
                return (MapPointer<T, A>) dataPropertyDomainAxiomsByProperty;
            }
            if (axiom.equals(OWLDataPropertyRangeAxiom.class)) {
                return (MapPointer<T, A>) dataPropertyRangeAxiomsByProperty;
            }
            if (axiom.equals(OWLFunctionalDataPropertyAxiom.class)) {
                return (MapPointer<T, A>) functionalDataPropertyAxiomsByProperty;
            }
        }
        if (type.equals(OWLAnnotationSubject.class) || type.equals(IRI.class)) {
            return (MapPointer<T, A>) annotationAssertionAxiomsBySubject;
        }
        if (type.equals(OWLIndividual.class)) {
            if (axiom.equals(OWLClassAssertionAxiom.class)) {
                return (MapPointer<T, A>) classAssertionAxiomsByIndividual;
            }
            if (axiom.equals(OWLObjectPropertyAssertionAxiom.class)) {
                return (MapPointer<T, A>) objectPropertyAssertionsByIndividual;
            }
            if (axiom.equals(OWLDataPropertyAssertionAxiom.class)) {
                return (MapPointer<T, A>) dataPropertyAssertionsByIndividual;
            }
            if (axiom.equals(OWLNegativeObjectPropertyAssertionAxiom.class)) {
                return (MapPointer<T, A>) negativeObjectPropertyAssertionAxiomsByIndividual;
            }
            if (axiom.equals(OWLNegativeDataPropertyAssertionAxiom.class)) {
                return (MapPointer<T, A>) negativeDataPropertyAssertionAxiomsByIndividual;
            }
            if (axiom.equals(OWLDifferentIndividualsAxiom.class)) {
                return (MapPointer<T, A>) differentIndividualsAxiomsByIndividual;
            }
            if (axiom.equals(OWLSameIndividualAxiom.class)) {
                return (MapPointer<T, A>) sameIndividualsAxiomsByIndividual;
            }
        }
        if (type.equals(OWLClass.class)) {
            if (axiom.equals(OWLSubClassOfAxiom.class)) {
                if (position == Search.IN_SUPER_POSITION) {
                    return (MapPointer<T, A>) subClassAxiomsBySuperPosition;
                } else {
                    return (MapPointer<T, A>) subClassAxiomsBySubPosition;
                }
            }
            if (axiom.equals(OWLClassAxiom.class)) {
                return (MapPointer<T, A>) classAxiomsByClass;
            }
            if (axiom.equals(OWLEquivalentClassesAxiom.class)) {
                return (MapPointer<T, A>) equivalentClassesAxiomsByClass;
            }
            if (axiom.equals(OWLDisjointClassesAxiom.class)) {
                return (MapPointer<T, A>) disjointClassesAxiomsByClass;
            }
            if (axiom.equals(OWLDisjointUnionAxiom.class)) {
                return (MapPointer<T, A>) disjointUnionAxiomsByClass;
            }
            if (axiom.equals(OWLHasKeyAxiom.class)) {
                return (MapPointer<T, A>) hasKeyAxiomsByClass;
            }
        }
        return null;
    }

    @Nonnull
    protected <K, V extends OWLAxiom> MapPointer<K, V> build() {
        return build(null, null);
    }

    @Nonnull
    protected <K, V extends OWLAxiom> MapPointer<K, V> buildLazy(
            AxiomType<?> t, OWLAxiomVisitorEx<?> v) {
        return new MapPointer<K, V>(t, v, false, this);
    }

    @Nonnull
    protected ClassAxiomByClassPointer buildClassAxiomByClass() {
        return new ClassAxiomByClassPointer(null, null, false, this);
    }

    @Nonnull
    protected <K, V extends OWLAxiom> MapPointer<K, V> build(AxiomType<?> t,
            OWLAxiomVisitorEx<?> v) {
        return new MapPointer<K, V>(t, v, true, this);
    }

    /**
     * @param axiom
     *        axiom to add
     * @return true if the axiom was not already included
     */
    public boolean addAxiom(@Nonnull final OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        if (getAxiomsByType().put(axiom.getAxiomType(), axiom)) {
            axiom.accept(addChangeVisitor);
            axiom.accept(new AbstractEntityRegistrationManager() {

                @Override
                public void visit(OWLClass owlClass) {
                    owlClassReferences.put(owlClass, axiom);
                }

                @Override
                public void visit(OWLObjectProperty property) {
                    owlObjectPropertyReferences.put(property, axiom);
                }

                @Override
                public void visit(OWLDataProperty property) {
                    owlDataPropertyReferences.put(property, axiom);
                }

                @Override
                public void visit(OWLNamedIndividual owlIndividual) {
                    owlIndividualReferences.put(owlIndividual, axiom);
                }

                @Override
                public void visit(OWLAnnotationProperty property) {
                    owlAnnotationPropertyReferences.put(property, axiom);
                }

                @Override
                public void visit(OWLDatatype datatype) {
                    owlDatatypeReferences.put(datatype, axiom);
                }

                @Override
                public void visit(OWLAnonymousIndividual individual) {
                    owlAnonymousIndividualReferences.put(individual, axiom);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * @param axiom
     *        axiom to remove
     * @return true if removed
     */
    public boolean removeAxiom(@Nonnull final OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        if (getAxiomsByType().remove(axiom.getAxiomType(), axiom)) {
            axiom.accept(removeChangeVisitor);
            AbstractEntityRegistrationManager referenceRemover = new AbstractEntityRegistrationManager() {

                @Override
                public void visit(OWLClass owlClass) {
                    owlClassReferences.remove(owlClass, axiom);
                }

                @Override
                public void visit(OWLObjectProperty property) {
                    owlObjectPropertyReferences.remove(property, axiom);
                }

                @Override
                public void visit(OWLDataProperty property) {
                    owlDataPropertyReferences.remove(property, axiom);
                }

                @Override
                public void visit(OWLNamedIndividual owlIndividual) {
                    owlIndividualReferences.remove(owlIndividual, axiom);
                }

                @Override
                public void visit(OWLAnnotationProperty property) {
                    owlAnnotationPropertyReferences.remove(property, axiom);
                }

                @Override
                public void visit(OWLDatatype datatype) {
                    owlDatatypeReferences.remove(datatype, axiom);
                }

                @Override
                public void visit(OWLAnonymousIndividual individual) {
                    owlAnonymousIndividualReferences.remove(individual, axiom);
                }
            };
            axiom.accept(referenceRemover);
            return true;
        }
        return false;
    }

    /**
     * @param e
     *        entity to check
     * @return true if the entity is declared in the ontology
     */
    public boolean isDeclared(OWLEntity e) {
        return declarationsByEntity.containsKey(e);
    }

    /**
     * @return true if empty
     */
    public boolean isEmpty() {
        return axiomsByType.isEmpty() && ontologyAnnotations.isEmpty();
    }

    /**
     * @param filter
     *        filter to satisfy
     * @param <K>
     *        key type
     * @param key
     *        key
     * @return set of values
     */
    @Nonnull
    public <K> Collection<OWLAxiom> filterAxioms(
            @Nonnull OWLAxiomSearchFilter filter, @Nonnull K key) {
        Collection<OWLAxiom> toReturn = new ArrayList<OWLAxiom>();
        for (AxiomType<?> at : filter.getAxiomTypes()) {
            for (OWLAxiom t : getAxiomsByType().getValues(at)) {
                if (filter.pass(t, key)) {
                    toReturn.add(t);
                }
            }
        }
        return toReturn;
    }

    /**
     * @param <K>
     *        key type
     * @param filter
     *        filter to satisfy
     * @param key
     *        key to match
     * @return true if the filter is matched at least once
     */
    public <K> boolean contains(@Nonnull OWLAxiomSearchFilter filter,
            @Nonnull K key) {
        for (AxiomType<?> at : filter.getAxiomTypes()) {
            for (OWLAxiom t : getAxiomsByType().getValues(at)) {
                if (filter.pass(t, key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param copy
     *        true if a copy of the set should be returned, false for a non
     *        defensive copy (to be used only by OWLImmutableOntologyImpl for
     *        iteration)
     * @return iterable of imports declaration
     */
    @Nonnull
    public Iterable<OWLImportsDeclaration> getImportsDeclarations(boolean copy) {
        if (!copy) {
            return importsDeclarations.iterable();
        }
        return importsDeclarations.copy();
    }

    /**
     * @param importDeclaration
     *        import declaration to remove
     * @return true if added
     */
    public boolean
            addImportsDeclaration(OWLImportsDeclaration importDeclaration) {
        return importsDeclarations.add(importDeclaration);
    }

    /**
     * @param importDeclaration
     *        import declaration to remove
     * @return true if removed
     */
    public boolean removeImportsDeclaration(
            OWLImportsDeclaration importDeclaration) {
        return importsDeclarations.remove(importDeclaration);
    }

    /**
     * @param copy
     *        true if a copy of the set should be returned, false for a non
     *        defensive copy (to be used only by OWLImmutableOntologyImpl for
     *        iteration)
     * @return iterable of annotations
     */
    @Nonnull
    Iterable<OWLAnnotation> getOntologyAnnotations(boolean copy) {
        if (!copy) {
            return ontologyAnnotations.iterable();
        }
        return ontologyAnnotations.copy();
    }

    /**
     * @param ann
     *        annotation to add
     * @return true if annotation added
     */
    public boolean addOntologyAnnotation(OWLAnnotation ann) {
        return ontologyAnnotations.add(ann);
    }

    /**
     * @param ann
     *        annotation to remove
     * @return true if annotation removed
     */
    public boolean removeOntologyAnnotation(OWLAnnotation ann) {
        return ontologyAnnotations.remove(ann);
    }

    /**
     * @param p
     *        pointer
     * @param <K>
     *        key type
     * @param <V>
     *        value type
     * @param k
     *        key
     * @param v
     *        value
     * @return true if the pair (key, value) is contained
     */
    public <K, V extends OWLAxiom> boolean contains(
            @Nonnull MapPointer<K, V> p, K k, V v) {
        return p.contains(k, v);
    }

    /**
     * @return count of all axioms
     */
    public int getAxiomCount() {
        return axiomsByType.size();
    }

    /**
     * Gets the axioms by type.
     * 
     * @return the axioms by type
     */
    @Nonnull
    public Set<OWLAxiom> getAxioms() {
        return axiomsByType.getAllValues();
    }

    /**
     * @param <T>
     *        axiom type
     * @param axiomType
     *        axiom type to count
     * @return axiom count
     */
    public <T extends OWLAxiom> int getAxiomCount(AxiomType<T> axiomType) {
        if (!axiomsByType.isInitialized()) {
            return 0;
        }
        Collection<OWLAxiom> collection = axiomsByType.getValues(axiomType);
        return collection.size();
    }

    /**
     * @return logical axioms
     */
    @Nonnull
    public Set<OWLLogicalAxiom> getLogicalAxioms() {
        Set<OWLLogicalAxiom> axioms = createSet();
        for (AxiomType<?> type : AXIOM_TYPES) {
            if (type.isLogical()) {
                Collection<OWLAxiom> axiomSet = axiomsByType.getValues(type);
                for (OWLAxiom ax : axiomSet) {
                    axioms.add((OWLLogicalAxiom) ax);
                }
            }
        }
        return axioms;
    }

    /**
     * @return logical axioms count
     */
    public int getLogicalAxiomCount() {
        int count = 0;
        for (AxiomType<?> type : AXIOM_TYPES) {
            if (type.isLogical()) {
                Collection<OWLAxiom> axiomSet = axiomsByType.getValues(type);
                count += axiomSet.size();
            }
        }
        return count;
    }

    /**
     * @return copy of GCI axioms
     */
    @Nonnull
    public Set<OWLClassAxiom> getGeneralClassAxioms() {
        return generalClassAxioms.copy();
    }

    /**
     * @param ax
     *        GCI axiom to add
     * @return true if axiom added
     */
    public boolean addGeneralClassAxioms(OWLClassAxiom ax) {
        return generalClassAxioms.add(ax);
    }

    /**
     * @param ax
     *        axiom to remove
     * @return true if removed
     */
    public boolean removeGeneralClassAxioms(OWLClassAxiom ax) {
        return generalClassAxioms.remove(ax);
    }

    /**
     * @param ax
     *        axiom to add
     * @return true if added
     */
    public boolean addPropertyChainSubPropertyAxioms(
            OWLSubPropertyChainOfAxiom ax) {
        return propertyChainSubPropertyAxioms.add(ax);
    }

    /**
     * @param ax
     *        axiom to remove
     * @return true if removed
     */
    public boolean removePropertyChainSubPropertyAxioms(
            OWLSubPropertyChainOfAxiom ax) {
        return propertyChainSubPropertyAxioms.remove(ax);
    }

    /**
     * @return map of axioms by type
     */
    @Nonnull
    public MapPointer<AxiomType<?>, OWLAxiom> getAxiomsByType() {
        return axiomsByType;
    }

    class AddAxiomVisitor extends OWLAxiomVisitorAdapter implements
            Serializable {

        private static final long serialVersionUID = 40000L;

        @Override
        public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
            if (!axiom.getSubClass().isAnonymous()) {
                OWLClass subClass = (OWLClass) axiom.getSubClass();
                subClassAxiomsBySubPosition.put(subClass, axiom);
                classAxiomsByClass.put(subClass, axiom);
            } else {
                addGeneralClassAxioms(axiom);
            }
            if (!axiom.getSuperClass().isAnonymous()) {
                subClassAxiomsBySuperPosition.put(
                        (OWLClass) axiom.getSuperClass(), axiom);
            }
        }

        @Override
        public void
                visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
            negativeObjectPropertyAssertionAxiomsByIndividual.put(
                    axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
            asymmetricPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
            reflexivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
            boolean allAnon = true;
            // Index against each named class in the axiom
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    OWLClass cls = (OWLClass) desc;
                    disjointClassesAxiomsByClass.put(cls, axiom);
                    classAxiomsByClass.put(cls, axiom);
                    allAnon = false;
                }
            }
            if (allAnon) {
                addGeneralClassAxioms(axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
            dataPropertyDomainAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
            if (axiom.getProperty() instanceof OWLObjectProperty) {
                objectPropertyDomainAxiomsByProperty.put(axiom.getProperty(),
                        axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                equivalentObjectPropertyAxiomsByProperty.put(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
            inversePropertyAxiomsByProperty
                    .put(axiom.getFirstProperty(), axiom);
            inversePropertyAxiomsByProperty.put(axiom.getSecondProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
            negativeDataPropertyAssertionAxiomsByIndividual.put(
                    axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                differentIndividualsAxiomsByIndividual.put(ind, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                disjointDataPropertyAxiomsByProperty.put(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                disjointObjectPropertyAxiomsByProperty.put(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
            objectPropertyRangeAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
            objectPropertyAssertionsByIndividual.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
            functionalObjectPropertyAxiomsByProperty.put(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
            objectSubPropertyAxiomsBySubPosition.put(axiom.getSubProperty(),
                    axiom);
            objectSubPropertyAxiomsBySuperPosition.put(
                    axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
            disjointUnionAxiomsByClass.put(axiom.getOWLClass(), axiom);
            classAxiomsByClass.put(axiom.getOWLClass(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDeclarationAxiom axiom) {
            declarationsByEntity.put(axiom.getEntity(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
            annotationAssertionAxiomsBySubject.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLHasKeyAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                hasKeyAxiomsByClass.put(
                        axiom.getClassExpression().asOWLClass(), axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
            symmetricPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
            dataPropertyRangeAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
            functionalDataPropertyAxiomsByProperty.put(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                equivalentDataPropertyAxiomsByProperty.put(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
            classAssertionAxiomsByIndividual.put(axiom.getIndividual(), axiom);
            if (!axiom.getClassExpression().isAnonymous()) {
                classAssertionAxiomsByClass.put(axiom.getClassExpression(),
                        axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
            boolean allAnon = true;
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    equivalentClassesAxiomsByClass.put((OWLClass) desc, axiom);
                    classAxiomsByClass.put((OWLClass) desc, axiom);
                    allAnon = false;
                }
            }
            if (allAnon) {
                addGeneralClassAxioms(axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
            dataPropertyAssertionsByIndividual.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
            transitivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
            irreflexivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
            dataSubPropertyAxiomsBySubPosition.put(axiom.getSubProperty(),
                    axiom);
            dataSubPropertyAxiomsBySuperPosition.put(axiom.getSuperProperty(),
                    axiom);
        }

        @Override
        public void
                visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
            inverseFunctionalPropertyAxiomsByProperty.put(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                sameIndividualsAxiomsByIndividual.put(ind, axiom);
            }
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            addPropertyChainSubPropertyAxioms(axiom);
        }
    }

    class RemoveAxiomVisitor extends OWLAxiomVisitorAdapter implements
            Serializable {

        private static final long serialVersionUID = 40000L;

        @Override
        public void visit(@Nonnull OWLSubClassOfAxiom axiom) {
            if (!axiom.getSubClass().isAnonymous()) {
                OWLClass subClass = (OWLClass) axiom.getSubClass();
                subClassAxiomsBySubPosition.remove(subClass, axiom);
                classAxiomsByClass.remove(subClass, axiom);
            } else {
                removeGeneralClassAxioms(axiom);
            }
            if (!axiom.getSuperClass().isAnonymous()) {
                subClassAxiomsBySuperPosition.remove(axiom.getSuperClass()
                        .asOWLClass(), axiom);
            }
        }

        @Override
        public void
                visit(@Nonnull OWLNegativeObjectPropertyAssertionAxiom axiom) {
            negativeObjectPropertyAssertionAxiomsByIndividual.remove(
                    axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLAsymmetricObjectPropertyAxiom axiom) {
            asymmetricPropertyAxiomsByProperty.remove(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLReflexiveObjectPropertyAxiom axiom) {
            reflexivePropertyAxiomsByProperty
                    .remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDisjointClassesAxiom axiom) {
            boolean allAnon = true;
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    OWLClass cls = (OWLClass) desc;
                    disjointClassesAxiomsByClass.remove(cls, axiom);
                    classAxiomsByClass.remove(cls, axiom);
                    allAnon = false;
                }
            }
            if (allAnon) {
                removeGeneralClassAxioms(axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDataPropertyDomainAxiom axiom) {
            dataPropertyDomainAxiomsByProperty.remove(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLObjectPropertyDomainAxiom axiom) {
            if (axiom.getProperty() instanceof OWLObjectProperty) {
                objectPropertyDomainAxiomsByProperty.remove(
                        axiom.getProperty(), axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLEquivalentObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                equivalentObjectPropertyAxiomsByProperty.remove(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLInverseObjectPropertiesAxiom axiom) {
            inversePropertyAxiomsByProperty.remove(axiom.getFirstProperty(),
                    axiom);
            inversePropertyAxiomsByProperty.remove(axiom.getSecondProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLNegativeDataPropertyAssertionAxiom axiom) {
            negativeDataPropertyAssertionAxiomsByIndividual.remove(
                    axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDifferentIndividualsAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                differentIndividualsAxiomsByIndividual.remove(ind, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDisjointDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                disjointDataPropertyAxiomsByProperty.remove(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDisjointObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                disjointObjectPropertyAxiomsByProperty.remove(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLObjectPropertyRangeAxiom axiom) {
            objectPropertyRangeAxiomsByProperty.remove(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLObjectPropertyAssertionAxiom axiom) {
            objectPropertyAssertionsByIndividual.remove(axiom.getSubject(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLFunctionalObjectPropertyAxiom axiom) {
            functionalObjectPropertyAxiomsByProperty.remove(
                    axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLSubObjectPropertyOfAxiom axiom) {
            objectSubPropertyAxiomsBySubPosition.remove(axiom.getSubProperty(),
                    axiom);
            objectSubPropertyAxiomsBySuperPosition.remove(
                    axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDisjointUnionAxiom axiom) {
            disjointUnionAxiomsByClass.remove(axiom.getOWLClass(), axiom);
            classAxiomsByClass.remove(axiom.getOWLClass(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDeclarationAxiom axiom) {
            declarationsByEntity.remove(axiom.getEntity(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLAnnotationAssertionAxiom axiom) {
            annotationAssertionAxiomsBySubject
                    .remove(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLHasKeyAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                hasKeyAxiomsByClass.remove(axiom.getClassExpression()
                        .asOWLClass(), axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLSymmetricObjectPropertyAxiom axiom) {
            symmetricPropertyAxiomsByProperty
                    .remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLDataPropertyRangeAxiom axiom) {
            dataPropertyRangeAxiomsByProperty
                    .remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLFunctionalDataPropertyAxiom axiom) {
            functionalDataPropertyAxiomsByProperty.remove(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLEquivalentDataPropertiesAxiom axiom) {
            for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                equivalentDataPropertyAxiomsByProperty.remove(prop, axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLClassAssertionAxiom axiom) {
            classAssertionAxiomsByIndividual.remove(axiom.getIndividual(),
                    axiom);
            if (!axiom.getClassExpression().isAnonymous()) {
                classAssertionAxiomsByClass.remove(axiom.getClassExpression(),
                        axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLEquivalentClassesAxiom axiom) {
            boolean allAnon = true;
            for (OWLClassExpression desc : axiom.getClassExpressions()) {
                if (!desc.isAnonymous()) {
                    equivalentClassesAxiomsByClass.remove((OWLClass) desc,
                            axiom);
                    classAxiomsByClass.remove((OWLClass) desc, axiom);
                    allAnon = false;
                }
            }
            if (allAnon) {
                removeGeneralClassAxioms(axiom);
            }
        }

        @Override
        public void visit(@Nonnull OWLDataPropertyAssertionAxiom axiom) {
            dataPropertyAssertionsByIndividual
                    .remove(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLTransitiveObjectPropertyAxiom axiom) {
            transitivePropertyAxiomsByProperty.remove(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLIrreflexiveObjectPropertyAxiom axiom) {
            irreflexivePropertyAxiomsByProperty.remove(axiom.getProperty(),
                    axiom);
        }

        @Override
        public void visit(@Nonnull OWLSubDataPropertyOfAxiom axiom) {
            dataSubPropertyAxiomsBySubPosition.remove(axiom.getSubProperty(),
                    axiom);
            dataSubPropertyAxiomsBySuperPosition.remove(
                    axiom.getSuperProperty(), axiom);
        }

        @Override
        public void
                visit(@Nonnull OWLInverseFunctionalObjectPropertyAxiom axiom) {
            inverseFunctionalPropertyAxiomsByProperty.remove(
                    axiom.getProperty(), axiom);
        }

        @Override
        public void visit(@Nonnull OWLSameIndividualAxiom axiom) {
            for (OWLIndividual ind : axiom.getIndividuals()) {
                sameIndividualsAxiomsByIndividual.remove(ind, axiom);
            }
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            removePropertyChainSubPropertyAxioms(axiom);
        }
    }

    @Nonnull
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("Internals{");
        for (OWLAxiom ax : getAxioms()) {
            b.append(ax).append("\n");
        }
        b.append("}");
        return b.toString();
    }

    /**
     * @param entity
     *        entity to check
     * @return true if reference is contained
     */
    public boolean containsReference(@Nonnull OWLEntity entity) {
        return entity.accept(refChecker);
    }

    /**
     * @param owlEntity
     *        entity to describe
     * @return referencing axioms
     */
    @Nonnull
    public Set<OWLAxiom> getReferencingAxioms(@Nonnull OWLEntity owlEntity) {
        return owlEntity.accept(refAxiomsCollector);
    }

    private final class ReferencedAxiomsCollector implements
            OWLEntityVisitorEx<Set<OWLAxiom>>, Serializable {

        private static final long serialVersionUID = 40000L;

        public ReferencedAxiomsCollector() {}

        @Override
        public Set<OWLAxiom> visit(OWLClass cls) {
            return owlClassReferences.getValues(cls);
        }

        @Override
        public Set<OWLAxiom> visit(OWLObjectProperty property) {
            return owlObjectPropertyReferences.getValues(property);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataProperty property) {
            return owlDataPropertyReferences.getValues(property);
        }

        @Override
        public Set<OWLAxiom> visit(OWLNamedIndividual individual) {
            return owlIndividualReferences.getValues(individual);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDatatype datatype) {
            return owlDatatypeReferences.getValues(datatype);
        }

        @Override
        public Set<OWLAxiom> visit(OWLAnnotationProperty property) {
            return owlAnnotationPropertyReferences.getValues(property);
        }
    }
}
