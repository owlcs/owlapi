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
package org.semanticweb.owlapi6.impl;

import static org.semanticweb.owlapi6.impl.InitVisitorFactory.ANNOTSUPERNAMED;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.CLASSCOLLECTIONS;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.CLASSEXPRESSIONS;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.CLASSSUBNAMED;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.CLASSSUPERNAMED;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.DPCOLLECTIONS;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.DPSUBNAMED;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.DPSUPERNAMED;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.ICOLLECTIONS;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.INDIVIDUALSUBNAMED;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.OPCOLLECTIONS;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.OPSUBNAMED;
import static org.semanticweb.owlapi6.impl.InitVisitorFactory.OPSUPERNAMED;
import static org.semanticweb.owlapi6.model.AxiomType.ANNOTATION_ASSERTION;
import static org.semanticweb.owlapi6.model.AxiomType.ASYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.CLASS_ASSERTION;
import static org.semanticweb.owlapi6.model.AxiomType.DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.model.AxiomType.DATA_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.model.AxiomType.DATA_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.model.AxiomType.DIFFERENT_INDIVIDUALS;
import static org.semanticweb.owlapi6.model.AxiomType.DISJOINT_CLASSES;
import static org.semanticweb.owlapi6.model.AxiomType.DISJOINT_DATA_PROPERTIES;
import static org.semanticweb.owlapi6.model.AxiomType.DISJOINT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.model.AxiomType.DISJOINT_UNION;
import static org.semanticweb.owlapi6.model.AxiomType.EQUIVALENT_CLASSES;
import static org.semanticweb.owlapi6.model.AxiomType.EQUIVALENT_DATA_PROPERTIES;
import static org.semanticweb.owlapi6.model.AxiomType.EQUIVALENT_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.model.AxiomType.FUNCTIONAL_DATA_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.HAS_KEY;
import static org.semanticweb.owlapi6.model.AxiomType.INVERSE_FUNCTIONAL_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.INVERSE_OBJECT_PROPERTIES;
import static org.semanticweb.owlapi6.model.AxiomType.IRREFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.LOGICAL_AXIOM_TYPES;
import static org.semanticweb.owlapi6.model.AxiomType.NEGATIVE_DATA_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.model.AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.model.AxiomType.OBJECT_PROPERTY_ASSERTION;
import static org.semanticweb.owlapi6.model.AxiomType.OBJECT_PROPERTY_DOMAIN;
import static org.semanticweb.owlapi6.model.AxiomType.OBJECT_PROPERTY_RANGE;
import static org.semanticweb.owlapi6.model.AxiomType.REFLEXIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.SAME_INDIVIDUAL;
import static org.semanticweb.owlapi6.model.AxiomType.SUBCLASS_OF;
import static org.semanticweb.owlapi6.model.AxiomType.SUB_DATA_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.SUB_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.SYMMETRIC_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.model.AxiomType.TRANSITIVE_OBJECT_PROPERTY;
import static org.semanticweb.owlapi6.utilities.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utility.CollectionFactory.createSyncSet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.EntityType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.IsAnonymous;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnnotationSubject;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLAxiomVisitor;
import org.semanticweb.owlapi6.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLClassAxiom;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi6.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi6.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi6.model.OWLImportsDeclaration;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi6.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLLogicalAxiom;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObject;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi6.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi6.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi6.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi6.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi6.model.parameters.Navigation;
import org.semanticweb.owlapi6.utilities.OWLAxiomSearchFilter;
import org.semanticweb.owlapi6.utility.AbstractCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ignazio
 */
public class Internals implements Serializable {

    protected static final Logger LOGGER = LoggerFactory.getLogger(Internals.class);
    //@formatter:off
protected transient MapPointer<OWLClassExpression, OWLClassAssertionAxiom>                          classAssertionAxiomsByClass                         = buildLazy(CLASS_ASSERTION, CLASSEXPRESSIONS, OWLClassAssertionAxiom.class);
protected transient MapPointer<OWLAnnotationSubject, OWLAnnotationAssertionAxiom>                   annotationAssertionAxiomsBySubject                  = buildLazy(ANNOTATION_ASSERTION, ANNOTSUPERNAMED, OWLAnnotationAssertionAxiom.class);
protected transient MapPointer<OWLClass, OWLSubClassOfAxiom>                                        subClassAxiomsBySubPosition                         = buildLazy(SUBCLASS_OF, CLASSSUBNAMED, OWLSubClassOfAxiom.class);
protected transient MapPointer<OWLClass, OWLSubClassOfAxiom>                                        subClassAxiomsBySuperPosition                       = buildLazy(SUBCLASS_OF, CLASSSUPERNAMED, OWLSubClassOfAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>            objectSubPropertyAxiomsBySubPosition                = buildLazy(SUB_OBJECT_PROPERTY, OPSUBNAMED, OWLSubObjectPropertyOfAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLSubObjectPropertyOfAxiom>            objectSubPropertyAxiomsBySuperPosition              = buildLazy(SUB_OBJECT_PROPERTY, OPSUPERNAMED, OWLSubObjectPropertyOfAxiom.class);
protected transient MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>                dataSubPropertyAxiomsBySubPosition                  = buildLazy(SUB_DATA_PROPERTY, DPSUBNAMED, OWLSubDataPropertyOfAxiom.class);
protected transient MapPointer<OWLDataPropertyExpression, OWLSubDataPropertyOfAxiom>                dataSubPropertyAxiomsBySuperPosition                = buildLazy(SUB_DATA_PROPERTY, DPSUPERNAMED, OWLSubDataPropertyOfAxiom.class);

protected transient MapPointer<OWLClass, OWLClassAxiom>                                             classAxiomsByClass                                  = buildClassAxiomByClass();
protected transient MapPointer<OWLClass, OWLEquivalentClassesAxiom>                                 equivalentClassesAxiomsByClass                      = buildLazy(EQUIVALENT_CLASSES, CLASSCOLLECTIONS, OWLEquivalentClassesAxiom.class);
protected transient MapPointer<OWLClass, OWLDisjointClassesAxiom>                                   disjointClassesAxiomsByClass                        = buildLazy(DISJOINT_CLASSES, CLASSCOLLECTIONS, OWLDisjointClassesAxiom.class);
protected transient MapPointer<OWLClass, OWLDisjointUnionAxiom>                                     disjointUnionAxiomsByClass                          = buildLazy(DISJOINT_UNION, CLASSCOLLECTIONS, OWLDisjointUnionAxiom.class);
protected transient MapPointer<OWLClass, OWLHasKeyAxiom>                                            hasKeyAxiomsByClass                                 = buildLazy(HAS_KEY, CLASSSUPERNAMED, OWLHasKeyAxiom.class);

protected transient MapPointer<OWLObjectPropertyExpression, OWLEquivalentObjectPropertiesAxiom>     equivalentObjectPropertyAxiomsByProperty            = buildLazy(EQUIVALENT_OBJECT_PROPERTIES, OPCOLLECTIONS, OWLEquivalentObjectPropertiesAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLDisjointObjectPropertiesAxiom>       disjointObjectPropertyAxiomsByProperty              = buildLazy(DISJOINT_OBJECT_PROPERTIES, OPCOLLECTIONS, OWLDisjointObjectPropertiesAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyDomainAxiom>           objectPropertyDomainAxiomsByProperty                = buildLazy(OBJECT_PROPERTY_DOMAIN, OPSUBNAMED, OWLObjectPropertyDomainAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLObjectPropertyRangeAxiom>            objectPropertyRangeAxiomsByProperty                 = buildLazy(OBJECT_PROPERTY_RANGE, OPSUBNAMED, OWLObjectPropertyRangeAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLFunctionalObjectPropertyAxiom>       functionalObjectPropertyAxiomsByProperty            = buildLazy(FUNCTIONAL_OBJECT_PROPERTY, OPSUBNAMED, OWLFunctionalObjectPropertyAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLInverseFunctionalObjectPropertyAxiom>inverseFunctionalPropertyAxiomsByProperty           = buildLazy(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, OPSUBNAMED, OWLInverseFunctionalObjectPropertyAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLSymmetricObjectPropertyAxiom>        symmetricPropertyAxiomsByProperty                   = buildLazy(SYMMETRIC_OBJECT_PROPERTY, OPSUBNAMED, OWLSymmetricObjectPropertyAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLAsymmetricObjectPropertyAxiom>       asymmetricPropertyAxiomsByProperty                  = buildLazy(ASYMMETRIC_OBJECT_PROPERTY, OPSUBNAMED, OWLAsymmetricObjectPropertyAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLReflexiveObjectPropertyAxiom>        reflexivePropertyAxiomsByProperty                   = buildLazy(REFLEXIVE_OBJECT_PROPERTY, OPSUBNAMED, OWLReflexiveObjectPropertyAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLIrreflexiveObjectPropertyAxiom>      irreflexivePropertyAxiomsByProperty                 = buildLazy(IRREFLEXIVE_OBJECT_PROPERTY, OPSUBNAMED, OWLIrreflexiveObjectPropertyAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLTransitiveObjectPropertyAxiom>       transitivePropertyAxiomsByProperty                  = buildLazy(TRANSITIVE_OBJECT_PROPERTY, OPSUBNAMED, OWLTransitiveObjectPropertyAxiom.class);
protected transient MapPointer<OWLObjectPropertyExpression, OWLInverseObjectPropertiesAxiom>        inversePropertyAxiomsByProperty                     = buildLazy(INVERSE_OBJECT_PROPERTIES, OPCOLLECTIONS, OWLInverseObjectPropertiesAxiom.class);

protected transient MapPointer<OWLDataPropertyExpression, OWLEquivalentDataPropertiesAxiom>         equivalentDataPropertyAxiomsByProperty              = buildLazy(EQUIVALENT_DATA_PROPERTIES, DPCOLLECTIONS, OWLEquivalentDataPropertiesAxiom.class);
protected transient MapPointer<OWLDataPropertyExpression, OWLDisjointDataPropertiesAxiom>           disjointDataPropertyAxiomsByProperty                = buildLazy(DISJOINT_DATA_PROPERTIES, DPCOLLECTIONS, OWLDisjointDataPropertiesAxiom.class);
protected transient MapPointer<OWLDataPropertyExpression, OWLDataPropertyDomainAxiom>               dataPropertyDomainAxiomsByProperty                  = buildLazy(DATA_PROPERTY_DOMAIN, DPSUBNAMED, OWLDataPropertyDomainAxiom.class);
protected transient MapPointer<OWLDataPropertyExpression, OWLDataPropertyRangeAxiom>                dataPropertyRangeAxiomsByProperty                   = buildLazy(DATA_PROPERTY_RANGE, DPSUBNAMED, OWLDataPropertyRangeAxiom.class);
protected transient MapPointer<OWLDataPropertyExpression, OWLFunctionalDataPropertyAxiom>           functionalDataPropertyAxiomsByProperty              = buildLazy(FUNCTIONAL_DATA_PROPERTY, DPSUBNAMED, OWLFunctionalDataPropertyAxiom.class);

protected transient MapPointer<OWLIndividual, OWLClassAssertionAxiom>                               classAssertionAxiomsByIndividual                    = buildLazy(CLASS_ASSERTION, INDIVIDUALSUBNAMED, OWLClassAssertionAxiom.class);
protected transient MapPointer<OWLIndividual, OWLObjectPropertyAssertionAxiom>                      objectPropertyAssertionsByIndividual                = buildLazy(OBJECT_PROPERTY_ASSERTION, INDIVIDUALSUBNAMED, OWLObjectPropertyAssertionAxiom.class);
protected transient MapPointer<OWLIndividual, OWLDataPropertyAssertionAxiom>                        dataPropertyAssertionsByIndividual                  = buildLazy(DATA_PROPERTY_ASSERTION, INDIVIDUALSUBNAMED, OWLDataPropertyAssertionAxiom.class);
protected transient MapPointer<OWLIndividual, OWLNegativeObjectPropertyAssertionAxiom>              negativeObjectPropertyAssertionAxiomsByIndividual   = buildLazy(NEGATIVE_OBJECT_PROPERTY_ASSERTION, INDIVIDUALSUBNAMED, OWLNegativeObjectPropertyAssertionAxiom.class);
protected transient MapPointer<OWLIndividual, OWLNegativeDataPropertyAssertionAxiom>                negativeDataPropertyAssertionAxiomsByIndividual     = buildLazy(NEGATIVE_DATA_PROPERTY_ASSERTION, INDIVIDUALSUBNAMED, OWLNegativeDataPropertyAssertionAxiom.class);
protected transient MapPointer<OWLIndividual, OWLDifferentIndividualsAxiom>                         differentIndividualsAxiomsByIndividual              = buildLazy(DIFFERENT_INDIVIDUALS, ICOLLECTIONS, OWLDifferentIndividualsAxiom.class);
protected transient MapPointer<OWLIndividual, OWLSameIndividualAxiom>                               sameIndividualsAxiomsByIndividual                   = buildLazy(SAME_INDIVIDUAL, ICOLLECTIONS, OWLSameIndividualAxiom.class);

protected  SetPointer<OWLImportsDeclaration>                        importsDeclarations                 = new SetPointer<>();
protected  SetPointer<OWLAnnotation>                                ontologyAnnotations                 = new SetPointer<>();
protected  SetPointer<OWLClassAxiom>                                generalClassAxioms                  = new SetPointer<>();
protected  SetPointer<OWLSubPropertyChainOfAxiom>                   propertyChainSubPropertyAxioms      = new SetPointer<>();

protected transient MapPointer<AxiomType<?>, OWLAxiom>              axiomsByType                        = build(OWLAxiom.class);

protected transient MapPointer<OWLClass, OWLAxiom>                  owlClassReferences                  = build(OWLAxiom.class);
protected transient MapPointer<OWLObjectProperty, OWLAxiom>         owlObjectPropertyReferences         = build(OWLAxiom.class);
protected transient MapPointer<OWLDataProperty, OWLAxiom>           owlDataPropertyReferences           = build(OWLAxiom.class);
protected transient MapPointer<OWLNamedIndividual, OWLAxiom>        owlIndividualReferences             = build(OWLAxiom.class);
protected transient MapPointer<OWLAnonymousIndividual, OWLAxiom>    owlAnonymousIndividualReferences    = build(OWLAxiom.class);
protected transient MapPointer<OWLDatatype, OWLAxiom>               owlDatatypeReferences               = build(OWLAxiom.class);
protected transient MapPointer<OWLAnnotationProperty, OWLAxiom>     owlAnnotationPropertyReferences     = build(OWLAxiom.class);
protected transient MapPointer<OWLEntity, OWLDeclarationAxiom>      declarationsByEntity                = build(OWLDeclarationAxiom.class);
protected transient EnumMap<InternalsPointers, MapPointer<?, ? extends OWLAxiom>> pointers              = map();
//@formatter:on
    @Nullable private List<OWLAxiom> axiomsForSerialization;
    private final AddAxiomVisitor addChangeVisitor = new AddAxiomVisitor();
    private final RemoveAxiomVisitor removeChangeVisitor = new RemoveAxiomVisitor();
    private final ReferencedAxiomsCollector refAxiomsCollector = new ReferencedAxiomsCollector();

    protected class SetPointer<K extends Serializable> implements Serializable {

        private final Set<K> set = createSyncSet();

        public boolean isEmpty() {
            return set.isEmpty();
        }

        public boolean add(K k) {
            return set.add(k);
        }

        public boolean remove(K k) {
            return set.remove(k);
        }

        public Stream<K> stream() {
            if (set.isEmpty()) {
                return Stream.empty();
            }
            List<K> toReturn = new ArrayList<>(set);
            try {
                toReturn.sort(null);
            } catch (IllegalArgumentException e) {
                // print a warning and leave the list unsorted
                LOGGER.warn("Misbehaving triple comparator, leaving triples unsorted", e);
            }
            return toReturn.stream();
        }
    }

    @SuppressWarnings("null")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        axiomsByType = build(OWLAxiom.class);
        owlClassReferences = build(OWLAxiom.class);
        owlObjectPropertyReferences = build(OWLAxiom.class);
        owlDataPropertyReferences = build(OWLAxiom.class);
        owlIndividualReferences = build(OWLAxiom.class);
        owlAnonymousIndividualReferences = build(OWLAxiom.class);
        owlDatatypeReferences = build(OWLAxiom.class);
        owlAnnotationPropertyReferences = build(OWLAxiom.class);
        declarationsByEntity = build(OWLDeclarationAxiom.class);
        classAssertionAxiomsByClass = buildLazy(CLASS_ASSERTION, CLASSEXPRESSIONS, OWLClassAssertionAxiom.class);
        annotationAssertionAxiomsBySubject = buildLazy(ANNOTATION_ASSERTION, ANNOTSUPERNAMED,
            OWLAnnotationAssertionAxiom.class);
        subClassAxiomsBySubPosition = buildLazy(SUBCLASS_OF, CLASSSUBNAMED, OWLSubClassOfAxiom.class);
        subClassAxiomsBySuperPosition = buildLazy(SUBCLASS_OF, CLASSSUPERNAMED, OWLSubClassOfAxiom.class);
        objectSubPropertyAxiomsBySubPosition = buildLazy(SUB_OBJECT_PROPERTY, OPSUBNAMED,
            OWLSubObjectPropertyOfAxiom.class);
        objectSubPropertyAxiomsBySuperPosition = buildLazy(SUB_OBJECT_PROPERTY, OPSUPERNAMED,
            OWLSubObjectPropertyOfAxiom.class);
        dataSubPropertyAxiomsBySubPosition = buildLazy(SUB_DATA_PROPERTY, DPSUBNAMED, OWLSubDataPropertyOfAxiom.class);
        dataSubPropertyAxiomsBySuperPosition = buildLazy(SUB_DATA_PROPERTY, DPSUPERNAMED,
            OWLSubDataPropertyOfAxiom.class);
        classAxiomsByClass = buildClassAxiomByClass();
        equivalentClassesAxiomsByClass = buildLazy(EQUIVALENT_CLASSES, CLASSCOLLECTIONS,
            OWLEquivalentClassesAxiom.class);
        disjointClassesAxiomsByClass = buildLazy(DISJOINT_CLASSES, CLASSCOLLECTIONS, OWLDisjointClassesAxiom.class);
        disjointUnionAxiomsByClass = buildLazy(DISJOINT_UNION, CLASSCOLLECTIONS, OWLDisjointUnionAxiom.class);
        hasKeyAxiomsByClass = buildLazy(HAS_KEY, CLASSSUPERNAMED, OWLHasKeyAxiom.class);
        equivalentObjectPropertyAxiomsByProperty = buildLazy(EQUIVALENT_OBJECT_PROPERTIES, OPCOLLECTIONS,
            OWLEquivalentObjectPropertiesAxiom.class);
        disjointObjectPropertyAxiomsByProperty = buildLazy(DISJOINT_OBJECT_PROPERTIES, OPCOLLECTIONS,
            OWLDisjointObjectPropertiesAxiom.class);
        objectPropertyDomainAxiomsByProperty = buildLazy(OBJECT_PROPERTY_DOMAIN, OPSUBNAMED,
            OWLObjectPropertyDomainAxiom.class);
        objectPropertyRangeAxiomsByProperty = buildLazy(OBJECT_PROPERTY_RANGE, OPSUBNAMED,
            OWLObjectPropertyRangeAxiom.class);
        functionalObjectPropertyAxiomsByProperty = buildLazy(FUNCTIONAL_OBJECT_PROPERTY, OPSUBNAMED,
            OWLFunctionalObjectPropertyAxiom.class);
        inverseFunctionalPropertyAxiomsByProperty = buildLazy(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, OPSUBNAMED,
            OWLInverseFunctionalObjectPropertyAxiom.class);
        symmetricPropertyAxiomsByProperty = buildLazy(SYMMETRIC_OBJECT_PROPERTY, OPSUBNAMED,
            OWLSymmetricObjectPropertyAxiom.class);
        asymmetricPropertyAxiomsByProperty = buildLazy(ASYMMETRIC_OBJECT_PROPERTY, OPSUBNAMED,
            OWLAsymmetricObjectPropertyAxiom.class);
        reflexivePropertyAxiomsByProperty = buildLazy(REFLEXIVE_OBJECT_PROPERTY, OPSUBNAMED,
            OWLReflexiveObjectPropertyAxiom.class);
        irreflexivePropertyAxiomsByProperty = buildLazy(IRREFLEXIVE_OBJECT_PROPERTY, OPSUBNAMED,
            OWLIrreflexiveObjectPropertyAxiom.class);
        transitivePropertyAxiomsByProperty = buildLazy(TRANSITIVE_OBJECT_PROPERTY, OPSUBNAMED,
            OWLTransitiveObjectPropertyAxiom.class);
        inversePropertyAxiomsByProperty = buildLazy(INVERSE_OBJECT_PROPERTIES, OPCOLLECTIONS,
            OWLInverseObjectPropertiesAxiom.class);
        equivalentDataPropertyAxiomsByProperty = buildLazy(EQUIVALENT_DATA_PROPERTIES, DPCOLLECTIONS,
            OWLEquivalentDataPropertiesAxiom.class);
        disjointDataPropertyAxiomsByProperty = buildLazy(DISJOINT_DATA_PROPERTIES, DPCOLLECTIONS,
            OWLDisjointDataPropertiesAxiom.class);
        dataPropertyDomainAxiomsByProperty = buildLazy(DATA_PROPERTY_DOMAIN, DPSUBNAMED,
            OWLDataPropertyDomainAxiom.class);
        dataPropertyRangeAxiomsByProperty = buildLazy(DATA_PROPERTY_RANGE, DPSUBNAMED, OWLDataPropertyRangeAxiom.class);
        functionalDataPropertyAxiomsByProperty = buildLazy(FUNCTIONAL_DATA_PROPERTY, DPSUBNAMED,
            OWLFunctionalDataPropertyAxiom.class);
        classAssertionAxiomsByIndividual = buildLazy(CLASS_ASSERTION, INDIVIDUALSUBNAMED, OWLClassAssertionAxiom.class);
        objectPropertyAssertionsByIndividual = buildLazy(OBJECT_PROPERTY_ASSERTION, INDIVIDUALSUBNAMED,
            OWLObjectPropertyAssertionAxiom.class);
        dataPropertyAssertionsByIndividual = buildLazy(DATA_PROPERTY_ASSERTION, INDIVIDUALSUBNAMED,
            OWLDataPropertyAssertionAxiom.class);
        negativeObjectPropertyAssertionAxiomsByIndividual = buildLazy(NEGATIVE_OBJECT_PROPERTY_ASSERTION,
            INDIVIDUALSUBNAMED, OWLNegativeObjectPropertyAssertionAxiom.class);
        negativeDataPropertyAssertionAxiomsByIndividual = buildLazy(NEGATIVE_DATA_PROPERTY_ASSERTION,
            INDIVIDUALSUBNAMED, OWLNegativeDataPropertyAssertionAxiom.class);
        differentIndividualsAxiomsByIndividual = buildLazy(DIFFERENT_INDIVIDUALS, ICOLLECTIONS,
            OWLDifferentIndividualsAxiom.class);
        sameIndividualsAxiomsByIndividual = buildLazy(SAME_INDIVIDUAL, ICOLLECTIONS, OWLSameIndividualAxiom.class);
        axiomsForSerialization.forEach(this::addAxiom);
        axiomsForSerialization = null;
        pointers = map();
    }

    protected Stream<OWLAnnotationProperty> annotationProperties() {
        return Stream.concat(owlAnnotationPropertyReferences.keySet(),
            ontologyAnnotations.stream().map(OWLAnnotation::getProperty));
    }

    /**
     * @param type
     *        entity type
     * @return true if there are entities of the specified type referred
     */
    public boolean anyEntities(EntityType<?> type) {
        if (EntityType.CLASS.equals(type)) {
            return !owlClassReferences.isEmpty();
        }
        if (EntityType.DATA_PROPERTY.equals(type)) {
            return !owlDataPropertyReferences.isEmpty();
        }
        if (EntityType.OBJECT_PROPERTY.equals(type)) {
            return !owlObjectPropertyReferences.isEmpty();
        }
        if (EntityType.ANNOTATION_PROPERTY.equals(type)) {
            return !owlAnnotationPropertyReferences.isEmpty();
        }
        if (EntityType.DATATYPE.equals(type)) {
            return !owlDatatypeReferences.isEmpty();
        }
        if (EntityType.NAMED_INDIVIDUAL.equals(type)) {
            return !owlIndividualReferences.isEmpty();
        }
        return false;
    }

    private EnumMap<InternalsPointers, MapPointer<?, ? extends OWLAxiom>> map() {
        EnumMap<InternalsPointers, MapPointer<?, ? extends OWLAxiom>> m = new EnumMap<>(InternalsPointers.class);
        m.put(InternalsPointers.AXIOMSBYTYPE, axiomsByType);
        m.put(InternalsPointers.OWLCLASSREFERENCES, owlClassReferences);
        m.put(InternalsPointers.OWLOBJECTPROPERTYREFERENCES, owlObjectPropertyReferences);
        m.put(InternalsPointers.OWLDATAPROPERTYREFERENCES, owlDataPropertyReferences);
        m.put(InternalsPointers.OWLINDIVIDUALREFERENCES, owlIndividualReferences);
        m.put(InternalsPointers.OWLANONYMOUSINDIVIDUALREFERENCES, owlAnonymousIndividualReferences);
        m.put(InternalsPointers.OWLDATATYPEREFERENCES, owlDatatypeReferences);
        m.put(InternalsPointers.OWLANNOTATIONPROPERTYREFERENCES, owlAnnotationPropertyReferences);
        m.put(InternalsPointers.DECLARATIONSBYENTITY, declarationsByEntity);
        m.put(InternalsPointers.CLASSASSERTIONAXIOMSBYCLASS, classAssertionAxiomsByClass);
        m.put(InternalsPointers.ANNOTATIONASSERTIONAXIOMSBYSUBJECT, annotationAssertionAxiomsBySubject);
        m.put(InternalsPointers.SUBCLASSAXIOMSBYSUBPOSITION, subClassAxiomsBySubPosition);
        m.put(InternalsPointers.SUBCLASSAXIOMSBYSUPERPOSITION, subClassAxiomsBySuperPosition);
        m.put(InternalsPointers.OBJECTSUBPROPERTYAXIOMSBYSUBPOSITION, objectSubPropertyAxiomsBySubPosition);
        m.put(InternalsPointers.OBJECTSUBPROPERTYAXIOMSBYSUPERPOSITION, objectSubPropertyAxiomsBySuperPosition);
        m.put(InternalsPointers.DATASUBPROPERTYAXIOMSBYSUBPOSITION, dataSubPropertyAxiomsBySubPosition);
        m.put(InternalsPointers.DATASUBPROPERTYAXIOMSBYSUPERPOSITION, dataSubPropertyAxiomsBySuperPosition);
        m.put(InternalsPointers.CLASSAXIOMSBYCLASS, classAxiomsByClass);
        m.put(InternalsPointers.EQUIVALENTCLASSESAXIOMSBYCLASS, equivalentClassesAxiomsByClass);
        m.put(InternalsPointers.DISJOINTCLASSESAXIOMSBYCLASS, disjointClassesAxiomsByClass);
        m.put(InternalsPointers.DISJOINTUNIONAXIOMSBYCLASS, disjointUnionAxiomsByClass);
        m.put(InternalsPointers.HASKEYAXIOMSBYCLASS, hasKeyAxiomsByClass);
        m.put(InternalsPointers.EQUIVALENTOBJECTPROPERTYAXIOMSBYPROPERTY, equivalentObjectPropertyAxiomsByProperty);
        m.put(InternalsPointers.DISJOINTOBJECTPROPERTYAXIOMSBYPROPERTY, disjointObjectPropertyAxiomsByProperty);
        m.put(InternalsPointers.OBJECTPROPERTYDOMAINAXIOMSBYPROPERTY, objectPropertyDomainAxiomsByProperty);
        m.put(InternalsPointers.OBJECTPROPERTYRANGEAXIOMSBYPROPERTY, objectPropertyRangeAxiomsByProperty);
        m.put(InternalsPointers.FUNCTIONALOBJECTPROPERTYAXIOMSBYPROPERTY, functionalObjectPropertyAxiomsByProperty);
        m.put(InternalsPointers.INVERSEFUNCTIONALPROPERTYAXIOMSBYPROPERTY, inverseFunctionalPropertyAxiomsByProperty);
        m.put(InternalsPointers.SYMMETRICPROPERTYAXIOMSBYPROPERTY, symmetricPropertyAxiomsByProperty);
        m.put(InternalsPointers.ASYMMETRICPROPERTYAXIOMSBYPROPERTY, asymmetricPropertyAxiomsByProperty);
        m.put(InternalsPointers.REFLEXIVEPROPERTYAXIOMSBYPROPERTY, reflexivePropertyAxiomsByProperty);
        m.put(InternalsPointers.IRREFLEXIVEPROPERTYAXIOMSBYPROPERTY, irreflexivePropertyAxiomsByProperty);
        m.put(InternalsPointers.TRANSITIVEPROPERTYAXIOMSBYPROPERTY, transitivePropertyAxiomsByProperty);
        m.put(InternalsPointers.INVERSEPROPERTYAXIOMSBYPROPERTY, inversePropertyAxiomsByProperty);
        m.put(InternalsPointers.EQUIVALENTDATAPROPERTYAXIOMSBYPROPERTY, equivalentDataPropertyAxiomsByProperty);
        m.put(InternalsPointers.DISJOINTDATAPROPERTYAXIOMSBYPROPERTY, disjointDataPropertyAxiomsByProperty);
        m.put(InternalsPointers.DATAPROPERTYDOMAINAXIOMSBYPROPERTY, dataPropertyDomainAxiomsByProperty);
        m.put(InternalsPointers.DATAPROPERTYRANGEAXIOMSBYPROPERTY, dataPropertyRangeAxiomsByProperty);
        m.put(InternalsPointers.FUNCTIONALDATAPROPERTYAXIOMSBYPROPERTY, functionalDataPropertyAxiomsByProperty);
        m.put(InternalsPointers.CLASSASSERTIONAXIOMSBYINDIVIDUAL, classAssertionAxiomsByIndividual);
        m.put(InternalsPointers.OBJECTPROPERTYASSERTIONSBYINDIVIDUAL, objectPropertyAssertionsByIndividual);
        m.put(InternalsPointers.DATAPROPERTYASSERTIONSBYINDIVIDUAL, dataPropertyAssertionsByIndividual);
        m.put(InternalsPointers.NEGATIVEOBJECTPROPERTYASSERTIONAXIOMSBYINDIVIDUAL,
            negativeObjectPropertyAssertionAxiomsByIndividual);
        m.put(InternalsPointers.NEGATIVEDATAPROPERTYASSERTIONAXIOMSBYINDIVIDUAL,
            negativeDataPropertyAssertionAxiomsByIndividual);
        m.put(InternalsPointers.DIFFERENTINDIVIDUALSAXIOMSBYINDIVIDUAL, differentIndividualsAxiomsByIndividual);
        m.put(InternalsPointers.SAMEINDIVIDUALSAXIOMSBYINDIVIDUAL, sameIndividualsAxiomsByIndividual);
        return m;
    }

    /**
     * Trims the capacity of the axiom indexes . An application can use this
     * operation to minimize the storage of the internals instance.
     */
    public void trimToSize() {
        // pointers.values().forEach(MapPointer::trimToSize);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        axiomsForSerialization = asList(axiomsByType.getAllValues());
        stream.defaultWriteObject();
    }

    /**
     * @param i
     *        iri
     * @return true if a class with this iri exists
     */
    public boolean containsClassInSignature(IRI i) {
        return owlClassReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if an object property with this iri exists
     */
    public boolean containsObjectPropertyInSignature(IRI i) {
        return owlObjectPropertyReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if a data property with this iri exists
     */
    public boolean containsDataPropertyInSignature(IRI i) {
        return owlDataPropertyReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if an annotation property with this iri exists
     */
    public boolean containsAnnotationPropertyInSignature(IRI i) {
        return owlAnnotationPropertyReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if a individual with this iri exists
     */
    public boolean containsIndividualInSignature(IRI i) {
        return owlIndividualReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if a datatype with this iri exists
     */
    public boolean containsDatatypeInSignature(IRI i) {
        return owlDatatypeReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if a class with this iri exists
     */
    public boolean containsClassInSignature(OWLClass i) {
        return owlClassReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if an object property with this iri exists
     */
    public boolean containsObjectPropertyInSignature(OWLObjectProperty i) {
        return owlObjectPropertyReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if a data property with this iri exists
     */
    public boolean containsDataPropertyInSignature(OWLDataProperty i) {
        return owlDataPropertyReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if an annotation property with this iri exists
     */
    public boolean containsAnnotationPropertyInSignature(OWLAnnotationProperty i) {
        return owlAnnotationPropertyReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if a individual with this iri exists
     */
    public boolean containsIndividualInSignature(OWLNamedIndividual i) {
        return owlIndividualReferences.containsReference(i);
    }

    /**
     * @param i
     *        iri
     * @return true if a datatype with this iri exists
     */
    public boolean containsDatatypeInSignature(OWLDatatype i) {
        return owlDatatypeReferences.containsReference(i);
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
    @SuppressWarnings({ "unchecked" })
    <T extends OWLObject, A extends OWLAxiom> Optional<MapPointer<T, A>> get(Class<T> type, Class<A> axiom,
        Navigation position) {
        if (OWLEntity.class.isAssignableFrom(type) && axiom.equals(OWLDeclarationAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) declarationsByEntity);
        }
        if (type.equals(OWLClass.class) && axiom.equals(OWLAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) owlClassReferences);
        }
        if (type.equals(OWLObjectProperty.class) && axiom.equals(OWLAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) owlObjectPropertyReferences);
        }
        if (type.equals(OWLDataProperty.class) && axiom.equals(OWLAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) owlDataPropertyReferences);
        }
        if (type.equals(OWLNamedIndividual.class) && axiom.equals(OWLAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) owlIndividualReferences);
        }
        if (type.equals(OWLAnonymousIndividual.class) && axiom.equals(OWLAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) owlAnonymousIndividualReferences);
        }
        if (type.equals(OWLDatatype.class) && axiom.equals(OWLAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) owlDatatypeReferences);
        }
        if (type.equals(OWLAnnotationProperty.class) && axiom.equals(OWLAxiom.class)) {
            return Optional.ofNullable((MapPointer<T, A>) owlAnnotationPropertyReferences);
        }
        if (type.equals(OWLClassExpression.class)) {
            return Optional.ofNullable((MapPointer<T, A>) classAssertionAxiomsByClass);
        }
        if (type.equals(OWLObjectPropertyExpression.class)) {
            if (axiom.equals(OWLSubObjectPropertyOfAxiom.class)) {
                if (position == Navigation.IN_SUPER_POSITION) {
                    return Optional.ofNullable((MapPointer<T, A>) objectSubPropertyAxiomsBySuperPosition);
                } else {
                    return Optional.ofNullable((MapPointer<T, A>) objectSubPropertyAxiomsBySubPosition);
                }
            }
            if (axiom.equals(OWLEquivalentObjectPropertiesAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) equivalentObjectPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLDisjointObjectPropertiesAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) disjointObjectPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLObjectPropertyDomainAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) objectPropertyDomainAxiomsByProperty);
            }
            if (axiom.equals(OWLObjectPropertyRangeAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) objectPropertyRangeAxiomsByProperty);
            }
            if (axiom.equals(OWLFunctionalObjectPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) functionalObjectPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLInverseFunctionalObjectPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) inverseFunctionalPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLSymmetricObjectPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) symmetricPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLAsymmetricObjectPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) asymmetricPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLReflexiveObjectPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) reflexivePropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLIrreflexiveObjectPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) irreflexivePropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLTransitiveObjectPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) transitivePropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLInverseObjectPropertiesAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) inversePropertyAxiomsByProperty);
            }
        }
        if (type.equals(OWLDataPropertyExpression.class)) {
            if (axiom.equals(OWLSubDataPropertyOfAxiom.class)) {
                if (position == Navigation.IN_SUPER_POSITION) {
                    return Optional.ofNullable((MapPointer<T, A>) dataSubPropertyAxiomsBySuperPosition);
                } else {
                    return Optional.ofNullable((MapPointer<T, A>) dataSubPropertyAxiomsBySubPosition);
                }
            }
            if (axiom.equals(OWLEquivalentDataPropertiesAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) equivalentDataPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLDisjointDataPropertiesAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) disjointDataPropertyAxiomsByProperty);
            }
            if (axiom.equals(OWLDataPropertyDomainAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) dataPropertyDomainAxiomsByProperty);
            }
            if (axiom.equals(OWLDataPropertyRangeAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) dataPropertyRangeAxiomsByProperty);
            }
            if (axiom.equals(OWLFunctionalDataPropertyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) functionalDataPropertyAxiomsByProperty);
            }
        }
        if (type.equals(OWLAnnotationSubject.class) || type.equals(IRI.class)) {
            return Optional.ofNullable((MapPointer<T, A>) annotationAssertionAxiomsBySubject);
        }
        if (type.equals(OWLIndividual.class)) {
            if (axiom.equals(OWLClassAssertionAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) classAssertionAxiomsByIndividual);
            }
            if (axiom.equals(OWLObjectPropertyAssertionAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) objectPropertyAssertionsByIndividual);
            }
            if (axiom.equals(OWLDataPropertyAssertionAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) dataPropertyAssertionsByIndividual);
            }
            if (axiom.equals(OWLNegativeObjectPropertyAssertionAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) negativeObjectPropertyAssertionAxiomsByIndividual);
            }
            if (axiom.equals(OWLNegativeDataPropertyAssertionAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) negativeDataPropertyAssertionAxiomsByIndividual);
            }
            if (axiom.equals(OWLDifferentIndividualsAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) differentIndividualsAxiomsByIndividual);
            }
            if (axiom.equals(OWLSameIndividualAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) sameIndividualsAxiomsByIndividual);
            }
        }
        if (type.equals(OWLClass.class)) {
            if (axiom.equals(OWLSubClassOfAxiom.class)) {
                if (position == Navigation.IN_SUPER_POSITION) {
                    return Optional.ofNullable((MapPointer<T, A>) subClassAxiomsBySuperPosition);
                } else {
                    return Optional.ofNullable((MapPointer<T, A>) subClassAxiomsBySubPosition);
                }
            }
            if (axiom.equals(OWLClassAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) classAxiomsByClass);
            }
            if (axiom.equals(OWLEquivalentClassesAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) equivalentClassesAxiomsByClass);
            }
            if (axiom.equals(OWLDisjointClassesAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) disjointClassesAxiomsByClass);
            }
            if (axiom.equals(OWLDisjointUnionAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) disjointUnionAxiomsByClass);
            }
            if (axiom.equals(OWLHasKeyAxiom.class)) {
                return Optional.ofNullable((MapPointer<T, A>) hasKeyAxiomsByClass);
            }
        }
        return Optional.empty();
    }

    protected <K, V extends OWLAxiom> MapPointer<K, V> build(Class<V> valueWithness) {
        return build(null, null, valueWithness);
    }

    protected <K, V extends OWLAxiom> MapPointer<K, V> buildLazy(AxiomType<?> t, OWLAxiomVisitorEx<?> v,
        Class<V> valueWithness) {
        return new MapPointer<>(t, v, false, this, valueWithness);
    }

    protected ClassAxiomByClassPointer buildClassAxiomByClass() {
        return new ClassAxiomByClassPointer(null, null, false, this);
    }

    protected <K, V extends OWLAxiom> MapPointer<K, V> build(@Nullable AxiomType<?> t, @Nullable OWLAxiomVisitorEx<?> v,
        Class<V> valueWithness) {
        return new MapPointer<>(t, v, true, this, valueWithness);
    }

    /**
     * @param axiom
     *        axiom to add
     * @return true if the axiom was not already included
     */
    public boolean addAxiom(final OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        if (axiomsByType.put(axiom.getAxiomType(), axiom)) {
            axiom.accept(addChangeVisitor);
            AbstractCollector referenceAdder = new AbstractCollector() {

                @Override
                public void visit(OWLClass ce) {
                    owlClassReferences.put(ce, axiom);
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
                public void visit(OWLNamedIndividual individual) {
                    owlIndividualReferences.put(individual, axiom);
                }

                @Override
                public void visit(OWLAnnotationProperty property) {
                    owlAnnotationPropertyReferences.put(property, axiom);
                }

                @Override
                public void visit(OWLDatatype node) {
                    owlDatatypeReferences.put(node, axiom);
                }

                @Override
                public void visit(OWLAnonymousIndividual individual) {
                    owlAnonymousIndividualReferences.put(individual, axiom);
                }
            };
            axiom.accept(referenceAdder);
            return true;
        }
        return false;
    }

    /**
     * @param axiom
     *        axiom to remove
     * @return true if removed
     */
    public boolean removeAxiom(final OWLAxiom axiom) {
        checkNotNull(axiom, "axiom cannot be null");
        if (getAxiomsByType().remove(axiom.getAxiomType(), axiom)) {
            axiom.accept(removeChangeVisitor);
            AbstractCollector referenceRemover = new AbstractCollector() {

                @Override
                public void visit(OWLClass ce) {
                    owlClassReferences.remove(ce, axiom);
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
                public void visit(OWLNamedIndividual individual) {
                    owlIndividualReferences.remove(individual, axiom);
                }

                @Override
                public void visit(OWLAnnotationProperty property) {
                    owlAnnotationPropertyReferences.remove(property, axiom);
                }

                @Override
                public void visit(OWLDatatype node) {
                    owlDatatypeReferences.remove(node, axiom);
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
    public <K> Collection<? extends OWLAxiom> filterAxioms(OWLAxiomSearchFilter filter, K key) {
        return getAxiomsByType().filterAxioms(filter, key);
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
    public <K> boolean contains(OWLAxiomSearchFilter filter, K key) {
        MapPointer<AxiomType<?>, OWLAxiom> types = getAxiomsByType();
        for (AxiomType<?> at : filter.getAxiomTypes()) {
            if (types.matchOnValues(at, t -> filter.pass(t, key))) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return stream of imports declaration
     */
    public Stream<OWLImportsDeclaration> getImportsDeclarations() {
        return importsDeclarations.stream();
    }

    /**
     * @param importDeclaration
     *        import declaration to remove
     * @return true if added
     */
    public boolean addImportsDeclaration(OWLImportsDeclaration importDeclaration) {
        return importsDeclarations.add(importDeclaration);
    }

    /**
     * @param importDeclaration
     *        import declaration to remove
     * @return true if removed
     */
    public boolean removeImportsDeclaration(OWLImportsDeclaration importDeclaration) {
        return importsDeclarations.remove(importDeclaration);
    }

    /**
     * @return iterable of annotations
     */
    Stream<OWLAnnotation> getOntologyAnnotations() {
        return ontologyAnnotations.stream();
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
    public static <K, V extends OWLAxiom> boolean contains(MapPointer<K, V> p, K k, V v) {
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
    public Stream<OWLAxiom> getAxioms() {
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
        return axiomsByType.countValues(axiomType);
    }

    /**
     * @return logical axioms
     */
    public Stream<OWLLogicalAxiom> getLogicalAxioms() {
        return LOGICAL_AXIOM_TYPES.stream().map(type -> axiomsByType.values(type, OWLLogicalAxiom.class))
            .flatMap(x -> x);
    }

    /**
     * @return logical axioms count
     */
    public int getLogicalAxiomCount() {
        int count = 0;
        for (AxiomType<?> type : LOGICAL_AXIOM_TYPES) {
            count += axiomsByType.countValues(type);
        }
        return count;
    }

    /**
     * @return copy of GCI axioms
     */
    public Stream<OWLClassAxiom> getGeneralClassAxioms() {
        // XXX watch out for performance issues
        return generalClassAxioms.stream().sorted();
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
    public boolean addPropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax) {
        return propertyChainSubPropertyAxioms.add(ax);
    }

    /**
     * @param ax
     *        axiom to remove
     * @return true if removed
     */
    public boolean removePropertyChainSubPropertyAxioms(OWLSubPropertyChainOfAxiom ax) {
        return propertyChainSubPropertyAxioms.remove(ax);
    }

    /**
     * @return map of axioms by type
     */
    public MapPointer<AxiomType<?>, OWLAxiom> getAxiomsByType() {
        return axiomsByType;
    }

    class AddAxiomVisitor implements OWLAxiomVisitor, Serializable {

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            if (axiom.getSubClass().isNamed()) {
                OWLClass subClass = (OWLClass) axiom.getSubClass();
                subClassAxiomsBySubPosition.put(subClass, axiom);
                classAxiomsByClass.put(subClass, axiom);
            } else {
                addGeneralClassAxioms(axiom);
            }
            if (axiom.getSuperClass().isNamed()) {
                subClassAxiomsBySuperPosition.put((OWLClass) axiom.getSuperClass(), axiom);
            }
        }

        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            negativeObjectPropertyAssertionAxiomsByIndividual.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            asymmetricPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            reflexivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            AtomicBoolean allAnon = new AtomicBoolean(true);
            // Index against each named class in the axiom
            axiom.classExpressions().filter(IsAnonymous::isNamed).forEach(desc -> visitDisjoint(axiom, allAnon, desc));
            if (allAnon.get()) {
                addGeneralClassAxioms(axiom);
            }
        }

        protected void visitDisjoint(OWLDisjointClassesAxiom axiom, AtomicBoolean allAnon, OWLClassExpression desc) {
            OWLClass cls = (OWLClass) desc;
            disjointClassesAxiomsByClass.put(cls, axiom);
            classAxiomsByClass.put(cls, axiom);
            allAnon.set(false);
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            dataPropertyDomainAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (axiom.getProperty().isOWLObjectProperty()) {
                objectPropertyDomainAxiomsByProperty.put(axiom.getProperty(), axiom);
            }
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> equivalentObjectPropertyAxiomsByProperty.put(p, axiom));
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            inversePropertyAxiomsByProperty.put(axiom.getFirstProperty(), axiom);
            inversePropertyAxiomsByProperty.put(axiom.getSecondProperty(), axiom);
        }

        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            negativeDataPropertyAssertionAxiomsByIndividual.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            axiom.individuals().forEach(ind -> differentIndividualsAxiomsByIndividual.put(ind, axiom));
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> disjointDataPropertyAxiomsByProperty.put(p, axiom));
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> disjointObjectPropertyAxiomsByProperty.put(p, axiom));
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            objectPropertyRangeAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            objectPropertyAssertionsByIndividual.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            functionalObjectPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            objectSubPropertyAxiomsBySubPosition.put(axiom.getSubProperty(), axiom);
            objectSubPropertyAxiomsBySuperPosition.put(axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            disjointUnionAxiomsByClass.put(axiom.getOWLClass(), axiom);
            classAxiomsByClass.put(axiom.getOWLClass(), axiom);
        }

        @Override
        public void visit(OWLDeclarationAxiom axiom) {
            declarationsByEntity.put(axiom.getEntity(), axiom);
        }

        @Override
        public void visit(OWLAnnotationAssertionAxiom axiom) {
            annotationAssertionAxiomsBySubject.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                hasKeyAxiomsByClass.put(axiom.getClassExpression().asOWLClass(), axiom);
            }
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            symmetricPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {
            dataPropertyRangeAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            functionalDataPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> equivalentDataPropertyAxiomsByProperty.put(p, axiom));
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            classAssertionAxiomsByIndividual.put(axiom.getIndividual(), axiom);
            if (axiom.getClassExpression().isNamed()) {
                classAssertionAxiomsByClass.put(axiom.getClassExpression(), axiom);
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            AtomicBoolean allAnon = new AtomicBoolean(true);
            axiom.classExpressions().filter(d -> !d.isAnonymous()).forEach(desc -> {
                equivalentClassesAxiomsByClass.put((OWLClass) desc, axiom);
                classAxiomsByClass.put((OWLClass) desc, axiom);
                allAnon.set(false);
            });
            if (allAnon.get()) {
                addGeneralClassAxioms(axiom);
            }
        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            dataPropertyAssertionsByIndividual.put(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            transitivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            irreflexivePropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            dataSubPropertyAxiomsBySubPosition.put(axiom.getSubProperty(), axiom);
            dataSubPropertyAxiomsBySuperPosition.put(axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            inverseFunctionalPropertyAxiomsByProperty.put(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            axiom.individuals().forEach(i -> sameIndividualsAxiomsByIndividual.put(i, axiom));
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            addPropertyChainSubPropertyAxioms(axiom);
        }
    }

    class RemoveAxiomVisitor implements OWLAxiomVisitor, Serializable {

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            if (!axiom.getSubClass().isAnonymous()) {
                OWLClass subClass = (OWLClass) axiom.getSubClass();
                subClassAxiomsBySubPosition.remove(subClass, axiom);
                classAxiomsByClass.remove(subClass, axiom);
            } else {
                removeGeneralClassAxioms(axiom);
            }
            if (!axiom.getSuperClass().isAnonymous()) {
                subClassAxiomsBySuperPosition.remove(axiom.getSuperClass().asOWLClass(), axiom);
            }
        }

        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            negativeObjectPropertyAssertionAxiomsByIndividual.remove(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            asymmetricPropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            reflexivePropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            AtomicBoolean allAnon = new AtomicBoolean(true);
            axiom.classExpressions().filter(IsAnonymous::isNamed).map(OWLClassExpression::asOWLClass).forEach(c -> {
                disjointClassesAxiomsByClass.remove(c, axiom);
                classAxiomsByClass.remove(c, axiom);
                allAnon.set(false);
            });
            if (allAnon.get()) {
                removeGeneralClassAxioms(axiom);
            }
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            dataPropertyDomainAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (axiom.getProperty().isOWLObjectProperty()) {
                objectPropertyDomainAxiomsByProperty.remove(axiom.getProperty(), axiom);
            }
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> equivalentObjectPropertyAxiomsByProperty.remove(p, axiom));
        }

        @Override
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            inversePropertyAxiomsByProperty.remove(axiom.getFirstProperty(), axiom);
            inversePropertyAxiomsByProperty.remove(axiom.getSecondProperty(), axiom);
        }

        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            negativeDataPropertyAssertionAxiomsByIndividual.remove(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            axiom.individuals().forEach(i -> differentIndividualsAxiomsByIndividual.remove(i, axiom));
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> disjointDataPropertyAxiomsByProperty.remove(p, axiom));
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> disjointObjectPropertyAxiomsByProperty.remove(p, axiom));
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            objectPropertyRangeAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            objectPropertyAssertionsByIndividual.remove(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            functionalObjectPropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            objectSubPropertyAxiomsBySubPosition.remove(axiom.getSubProperty(), axiom);
            objectSubPropertyAxiomsBySuperPosition.remove(axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            disjointUnionAxiomsByClass.remove(axiom.getOWLClass(), axiom);
            classAxiomsByClass.remove(axiom.getOWLClass(), axiom);
        }

        @Override
        public void visit(OWLDeclarationAxiom axiom) {
            declarationsByEntity.remove(axiom.getEntity(), axiom);
        }

        @Override
        public void visit(OWLAnnotationAssertionAxiom axiom) {
            annotationAssertionAxiomsBySubject.remove(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            if (!axiom.getClassExpression().isAnonymous()) {
                hasKeyAxiomsByClass.remove(axiom.getClassExpression().asOWLClass(), axiom);
            }
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            symmetricPropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLDataPropertyRangeAxiom axiom) {
            dataPropertyRangeAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            functionalDataPropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            axiom.properties().forEach(p -> equivalentDataPropertyAxiomsByProperty.remove(p, axiom));
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            classAssertionAxiomsByIndividual.remove(axiom.getIndividual(), axiom);
            if (!axiom.getClassExpression().isAnonymous()) {
                classAssertionAxiomsByClass.remove(axiom.getClassExpression(), axiom);
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            AtomicBoolean allAnon = new AtomicBoolean(true);
            axiom.classExpressions().filter(IsAnonymous::isNamed).map(OWLClassExpression::asOWLClass).forEach(c -> {
                equivalentClassesAxiomsByClass.remove(c, axiom);
                classAxiomsByClass.remove(c, axiom);
                allAnon.set(false);
            });
            if (allAnon.get()) {
                removeGeneralClassAxioms(axiom);
            }
        }

        @Override
        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            dataPropertyAssertionsByIndividual.remove(axiom.getSubject(), axiom);
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            transitivePropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            irreflexivePropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            dataSubPropertyAxiomsBySubPosition.remove(axiom.getSubProperty(), axiom);
            dataSubPropertyAxiomsBySuperPosition.remove(axiom.getSuperProperty(), axiom);
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            inverseFunctionalPropertyAxiomsByProperty.remove(axiom.getProperty(), axiom);
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            axiom.individuals().forEach(i -> sameIndividualsAxiomsByIndividual.remove(i, axiom));
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            removePropertyChainSubPropertyAxioms(axiom);
        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("Internals{(first 20 axioms) ");
        axiomsByType.getAllValues().limit(20).forEach(a -> b.append(a).append('\n'));
        b.append('}');
        return b.toString();
    }

    /**
     * @param owlEntity
     *        entity to describe
     * @return referencing axioms
     */
    public Stream<OWLAxiom> getReferencingAxioms(OWLEntity owlEntity) {
        return owlEntity.accept(refAxiomsCollector);
    }

    private class ReferencedAxiomsCollector implements OWLEntityVisitorEx<Stream<OWLAxiom>>, Serializable {

        ReferencedAxiomsCollector() {}

        @Override
        public Stream<OWLAxiom> visit(OWLClass cls) {
            return owlClassReferences.values(cls, OWLAxiom.class);
        }

        @Override
        public Stream<OWLAxiom> visit(OWLObjectProperty property) {
            return owlObjectPropertyReferences.values(property, OWLAxiom.class);
        }

        @Override
        public Stream<OWLAxiom> visit(OWLDataProperty property) {
            return owlDataPropertyReferences.values(property, OWLAxiom.class);
        }

        @Override
        public Stream<OWLAxiom> visit(OWLNamedIndividual individual) {
            return owlIndividualReferences.values(individual, OWLAxiom.class);
        }

        @Override
        public Stream<OWLAxiom> visit(OWLDatatype datatype) {
            return owlDatatypeReferences.values(datatype, OWLAxiom.class);
        }

        @Override
        public Stream<OWLAxiom> visit(OWLAnnotationProperty property) {
            return owlAnnotationPropertyReferences.values(property, OWLAxiom.class);
        }
    }
}
