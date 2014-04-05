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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
public class StructuralTransformation {

    protected final OWLDataFactory df;
    private int nameCounter = 0;
    protected final Set<OWLEntity> signature = new HashSet<OWLEntity>();

    /**
     * @param dataFactory
     *        factory to use
     */
    public StructuralTransformation(@Nonnull OWLDataFactory dataFactory) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
    }

    protected OWLClass createNewName() {
        OWLClass cls = df.getOWLClass(IRI.create(
                "http://www.semanticweb.org/ontology#", "X" + nameCounter));
        nameCounter++;
        return cls;
    }

    /**
     * @param axioms
     *        axioms to transform
     * @return transformed axioms
     */
    public Set<OWLAxiom> getTransformedAxioms(@Nonnull Set<OWLAxiom> axioms) {
        checkNotNull(axioms, "axioms cannot be null");
        signature.clear();
        for (OWLAxiom ax : axioms) {
            signature.addAll(ax.getSignature());
        }
        AxiomRewriter rewriter = new AxiomRewriter();
        Set<OWLAxiom> transformedAxioms = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : axioms) {
            for (OWLAxiom transAx : ax.accept(rewriter)) {
                if (transAx instanceof OWLSubClassOfAxiom) {
                    AxiomFlattener flattener = new AxiomFlattener(df,
                            ((OWLSubClassOfAxiom) transAx).getSuperClass());
                    Set<OWLAxiom> flattenedAxioms = flattener.getAxioms();
                    if (!flattenedAxioms.isEmpty()) {
                        transformedAxioms.addAll(flattenedAxioms);
                    } else {
                        transformedAxioms.add(transAx);
                    }
                } else {
                    transformedAxioms.add(transAx);
                }
            }
        }
        return transformedAxioms;
    }

    private class AxiomFlattener implements
            OWLClassExpressionVisitorEx<OWLClassExpression> {

        private final OWLDataFactory ldf;
        private final Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        private final OWLClassExpression rhs;

        public AxiomFlattener(OWLDataFactory df, OWLClassExpression rhs) {
            ldf = df;
            this.rhs = rhs;
        }

        private OWLSubClassOfAxiom getSCA(OWLClass lhs, OWLClassExpression ce) {
            return ldf.getOWLSubClassOfAxiom(lhs, ce);
        }

        public Set<OWLAxiom> getAxioms() {
            axioms.clear();
            OWLClass lhs = ldf.getOWLThing();
            OWLClassExpression rhs2 = rhs.accept(this);
            axioms.add(getSCA(lhs, rhs2));
            return axioms;
        }

        @Override
        public OWLClassExpression visit(OWLClass desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLDataAllValuesFrom desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLDataExactCardinality desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLDataMaxCardinality desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLDataMinCardinality desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLDataSomeValuesFrom desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLDataHasValue desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLObjectAllValuesFrom desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, desc.getFiller().accept(this)));
                return ldf.getOWLObjectAllValuesFrom(desc.getProperty(), name);
            } else {
                return desc;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectComplementOf desc) {
            // Should be a literal
            if (desc.getOperand().isAnonymous()) {
                throw new IllegalStateException(
                        "Negation of arbitrary class expressions not allowed");
            }
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLObjectExactCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, desc.getFiller().accept(this)));
                return ldf.getOWLObjectExactCardinality(desc.getCardinality(),
                        desc.getProperty(), name);
            } else {
                return desc;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectIntersectionOf desc) {
            OWLClass name = createNewName();
            for (OWLClassExpression op : desc.getOperands()) {
                OWLClassExpression flatOp = op.accept(this);
                axioms.add(getSCA(name, flatOp));
            }
            return name;
        }

        @Override
        public OWLClassExpression visit(OWLObjectMaxCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, desc.getFiller().accept(this)));
                return ldf.getOWLObjectMaxCardinality(desc.getCardinality(),
                        desc.getProperty(), name);
            } else {
                return desc;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectMinCardinality desc) {
            if (signature.containsAll(desc.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, desc.getFiller().accept(this)));
                return ldf.getOWLObjectMinCardinality(desc.getCardinality(),
                        desc.getProperty(), name);
            } else {
                return desc;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectOneOf desc) {
            if (desc.getIndividuals().size() > 1) {
                throw new IllegalStateException(
                        "ObjectOneOf with more than one individual!");
            }
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLObjectHasSelf desc) {
            return desc;
        }

        @Override
        public OWLClassExpression visit(OWLObjectSomeValuesFrom desc) {
            if (desc.getFiller().isAnonymous()) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, desc.getFiller().accept(this)));
                return ldf.getOWLObjectSomeValuesFrom(desc.getProperty(), name);
            } else {
                return desc;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectUnionOf desc) {
            Set<OWLClassExpression> descs = new HashSet<OWLClassExpression>();
            for (OWLClassExpression op : desc.getOperands()) {
                OWLClassExpression flatOp = op.accept(this);
                if (flatOp.isAnonymous()
                        || signature.contains(flatOp.asOWLClass())) {
                    OWLClass name = createNewName();
                    descs.add(name);
                    axioms.add(ldf.getOWLSubClassOfAxiom(name, flatOp));
                } else {
                    descs.add(flatOp);
                }
            }
            return ldf.getOWLObjectUnionOf(descs);
        }

        @Override
        public OWLClassExpression visit(OWLObjectHasValue desc) {
            return desc;
        }
    }

    /**
     * Rewrites axioms into GCIs.<br>
     * For example: SubClassOf(A, C) becomes SubClassOf(TOP, not(A) or C)
     */
    private class AxiomRewriter implements OWLAxiomVisitorEx<Set<OWLAxiom>> {

        public AxiomRewriter() {}

        private Set<OWLAxiom> subClassOf(OWLClassExpression sub,
                OWLClassExpression sup) {
            return Collections.singleton((OWLAxiom) df.getOWLSubClassOfAxiom(
                    df.getOWLThing(),
                    df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(sub),
                            sup).getNNF()));
        }

        private Set<OWLAxiom> toSet(OWLAxiom ax) {
            return Collections.singleton(ax);
        }

        @Override
        public Set<OWLAxiom> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLClassAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getIndividual()),
                    axiom.getClassExpression());
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataPropertyAssertionAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataPropertyDomainAxiom axiom) {
            return subClassOf(
                    df.getOWLDataSomeValuesFrom(axiom.getProperty(),
                            df.getTopDatatype()), axiom.getDomain());
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataPropertyRangeAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(
                    df.getOWLThing(),
                    df.getOWLDataAllValuesFrom(axiom.getProperty(),
                            axiom.getRange())));
        }

        @Override
        public Set<OWLAxiom> visit(OWLSubDataPropertyOfAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDeclarationAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDifferentIndividualsAxiom axiom) {
            // Explode into pairwise nominals?
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLIndividual> individuals = new ArrayList<OWLIndividual>(
                    axiom.getIndividuals());
            for (int i = 0; i < individuals.size(); i++) {
                for (int j = i + 1; j < individuals.size(); j++) {
                    axioms.addAll(subClassOf(
                            df.getOWLObjectOneOf(individuals.get(i)),
                            df.getOWLObjectOneOf(individuals.get(j))));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointClassesAxiom axiom) {
            // Explode
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(
                    axiom.getClassExpressions());
            for (int i = 0; i < classExpressions.size(); i++) {
                for (int j = i + 1; j < classExpressions.size(); j++) {
                    axioms.addAll(subClassOf(classExpressions.get(i),
                            classExpressions.get(j)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointDataPropertiesAxiom axiom) {
            // Explode
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLDataPropertyExpression> props = new ArrayList<OWLDataPropertyExpression>(
                    axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(
                            props.get(i), props.get(j)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLObjectPropertyExpression> props = new ArrayList<OWLObjectPropertyExpression>(
                    axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(
                            props.get(i), props.get(j)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointUnionAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            axioms.addAll(df.getOWLEquivalentClassesAxiom(axiom.getOWLClass(),
                    df.getOWLObjectUnionOf(axiom.getClassExpressions()))
                    .accept(this));
            axioms.addAll(df.getOWLDisjointClassesAxiom(
                    axiom.getClassExpressions()).accept(this));
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLAnnotationAssertionAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLClassExpression> classExpressions = new ArrayList<OWLClassExpression>(
                    axiom.getClassExpressions());
            for (int i = 0; i < classExpressions.size(); i++) {
                for (int j = i + 1; j < classExpressions.size(); j++) {
                    axioms.addAll(subClassOf(classExpressions.get(i),
                            classExpressions.get(j)));
                    axioms.addAll(subClassOf(classExpressions.get(j),
                            classExpressions.get(i)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLEquivalentDataPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLDataPropertyExpression> props = new ArrayList<OWLDataPropertyExpression>(
                    axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(
                            props.get(i), props.get(j)));
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(
                            props.get(j), props.get(i)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            List<OWLObjectPropertyExpression> props = new ArrayList<OWLObjectPropertyExpression>(
                    axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(
                            props.get(i), props.get(j)));
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(
                            props.get(j), props.get(i)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLFunctionalDataPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLDataMaxCardinality(1, axiom.getProperty())));
        }

        @Override
        public Set<OWLAxiom> visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                    df.getOWLObjectMaxCardinality(1, axiom.getProperty())));
        }

        @Override
        public Set<OWLAxiom>
                visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(), df
                    .getOWLObjectMaxCardinality(1, axiom.getProperty()
                            .getInverseProperty())));
        }

        @Override
        public Set<OWLAxiom> visit(OWLInverseObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom
                    .getFirstProperty(), axiom.getSecondProperty()
                    .getInverseProperty()));
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom
                    .getSecondProperty(), axiom.getFirstProperty()
                    .getInverseProperty()));
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()),
                    df.getOWLDataAllValuesFrom(axiom.getProperty(), df
                            .getOWLDataComplementOf(df.getOWLDataOneOf(axiom
                                    .getObject()))));
        }

        @Override
        public Set<OWLAxiom>
                visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()),
                    df.getOWLObjectAllValuesFrom(axiom.getProperty(), df
                            .getOWLObjectComplementOf(df
                                    .getOWLObjectOneOf(axiom.getObject()))));
        }

        @Override
        public Set<OWLAxiom> visit(OWLObjectPropertyAssertionAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLSubPropertyChainOfAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLObjectPropertyDomainAxiom axiom) {
            return subClassOf(
                    df.getOWLObjectSomeValuesFrom(axiom.getProperty(),
                            df.getOWLThing()), axiom.getDomain());
        }

        @Override
        public Set<OWLAxiom> visit(OWLObjectPropertyRangeAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(
                    df.getOWLThing(),
                    df.getOWLObjectAllValuesFrom(axiom.getProperty(),
                            axiom.getRange())));
        }

        @Override
        public Set<OWLAxiom> visit(OWLSubObjectPropertyOfAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLReflexiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLSameIndividualAxiom axiom) {
            return null;
        }

        @Override
        public Set<OWLAxiom> visit(OWLSubClassOfAxiom axiom) {
            return subClassOf(axiom.getSubClass(), axiom.getSuperClass());
        }

        @Override
        public Set<OWLAxiom> visit(OWLSymmetricObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLTransitiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(SWRLRule rule) {
            return toSet(rule);
        }

        @Override
        public Set<OWLAxiom> visit(OWLHasKeyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLAnnotationPropertyDomainAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLAnnotationPropertyRangeAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDatatypeDefinitionAxiom axiom) {
            return toSet(axiom);
        }
    }
}
