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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
public class StructuralTransformation implements Serializable {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    protected final OWLDataFactory df;
    private int nameCounter = 0;
    protected final Set<OWLEntity> signature = new HashSet<>();

    /**
     * @param dataFactory
     *        factory to use
     */
    public StructuralTransformation(@Nonnull OWLDataFactory dataFactory) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
    }

    @Nonnull
    protected OWLClass createNewName() {
        OWLClass cls = df.getOWLClass(IRI.create("http://www.semanticweb.org/ontology#", "X" + nameCounter));
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
        Set<OWLAxiom> transformedAxioms = new HashSet<>();
        for (OWLAxiom ax : axioms) {
            for (OWLAxiom transAx : ax.accept(rewriter)) {
                if (transAx instanceof OWLSubClassOfAxiom) {
                    AxiomFlattener flattener = new AxiomFlattener(df, ((OWLSubClassOfAxiom) transAx).getSuperClass());
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

    private class AxiomFlattener implements OWLClassExpressionVisitorEx<OWLClassExpression> {

        private final OWLDataFactory ldf;
        private final Set<OWLAxiom> axioms = new HashSet<>();
        private final OWLClassExpression rhs;

        AxiomFlattener(OWLDataFactory df, OWLClassExpression rhs) {
            ldf = df;
            this.rhs = rhs;
        }

        private OWLSubClassOfAxiom getSCA(@Nonnull OWLClass lhs, @Nonnull OWLClassExpression ce) {
            return ldf.getOWLSubClassOfAxiom(lhs, ce);
        }

        public Set<OWLAxiom> getAxioms() {
            axioms.clear();
            OWLClass lhs = ldf.getOWLThing();
            axioms.add(getSCA(lhs, rhs.accept(this)));
            return axioms;
        }

        @Override
        public OWLClassExpression visit(OWLClass ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLDataAllValuesFrom ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLDataExactCardinality ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLDataMaxCardinality ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLDataMinCardinality ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLDataSomeValuesFrom ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLDataHasValue ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectAllValuesFrom ce) {
            if (signature.containsAll(ce.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectAllValuesFrom(ce.getProperty(), name);
            } else {
                return ce;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectComplementOf ce) {
            // Should be a literal
            if (ce.getOperand().isAnonymous()) {
                throw new IllegalStateException("Negation of arbitrary class expressions not allowed");
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectExactCardinality ce) {
            if (signature.containsAll(ce.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectExactCardinality(ce.getCardinality(), ce.getProperty(), name);
            } else {
                return ce;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectIntersectionOf ce) {
            OWLClass name = createNewName();
            for (OWLClassExpression op : ce.getOperands()) {
                axioms.add(getSCA(name, op.accept(this)));
            }
            return name;
        }

        @Override
        public OWLClassExpression visit(OWLObjectMaxCardinality ce) {
            if (signature.containsAll(ce.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectMaxCardinality(ce.getCardinality(), ce.getProperty(), name);
            } else {
                return ce;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectMinCardinality ce) {
            if (signature.containsAll(ce.getFiller().getSignature())) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectMinCardinality(ce.getCardinality(), ce.getProperty(), name);
            } else {
                return ce;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectOneOf ce) {
            if (ce.getIndividuals().size() > 1) {
                throw new IllegalStateException("ObjectOneOf with more than one individual!");
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectHasSelf ce) {
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectSomeValuesFrom ce) {
            if (ce.getFiller().isAnonymous()) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectSomeValuesFrom(ce.getProperty(), name);
            } else {
                return ce;
            }
        }

        @Override
        public OWLClassExpression visit(OWLObjectUnionOf ce) {
            Set<OWLClassExpression> descs = new HashSet<>();
            for (OWLClassExpression op : ce.getOperands()) {
                OWLClassExpression flatOp = op.accept(this);
                if (flatOp.isAnonymous() || signature.contains(flatOp.asOWLClass())) {
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
        public OWLClassExpression visit(OWLObjectHasValue ce) {
            return ce;
        }
    }

    /**
     * Rewrites axioms into GCIs.<br>
     * For example: SubClassOf(A, C) becomes SubClassOf(TOP, not(A) or C)
     */
    private class AxiomRewriter implements OWLAxiomVisitorEx<Set<OWLAxiom>> {

        AxiomRewriter() {}

        @Nonnull
        private Set<OWLAxiom> subClassOf(@Nonnull OWLClassExpression sub, @Nonnull OWLClassExpression sup) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(
                sub), sup).getNNF()));
        }

        @Nonnull
        private Set<OWLAxiom> toSet(@Nonnull OWLAxiom ax) {
            return CollectionFactory.createSet(ax);
        }

        @Override
        public Set<OWLAxiom> visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLClassAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getIndividual()), axiom.getClassExpression());
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataPropertyAssertionAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataPropertyDomainAxiom axiom) {
            return subClassOf(df.getOWLDataSomeValuesFrom(axiom.getProperty(), df.getTopDatatype()), axiom.getDomain());
        }

        @Override
        public Set<OWLAxiom> visit(OWLDataPropertyRangeAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLDataAllValuesFrom(axiom.getProperty(),
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
            Set<OWLAxiom> axioms = new HashSet<>();
            List<OWLIndividual> individuals = new ArrayList<>(axiom.getIndividuals());
            for (int i = 0; i < individuals.size(); i++) {
                for (int j = i + 1; j < individuals.size(); j++) {
                    axioms.addAll(subClassOf(df.getOWLObjectOneOf(individuals.get(i)), df.getOWLObjectOneOf(individuals
                        .get(j))));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointClassesAxiom axiom) {
            // Explode
            return new HashSet<OWLAxiom>(axiom.asOWLSubClassOfAxioms());
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointDataPropertiesAxiom axiom) {
            // Explode
            Set<OWLAxiom> axioms = new HashSet<>();
            List<OWLDataPropertyExpression> props = new ArrayList<>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(props.get(i), props.get(j)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<>();
            List<OWLObjectPropertyExpression> props = new ArrayList<>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(props.get(i), props.get(j)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLDisjointUnionAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.addAll(df.getOWLEquivalentClassesAxiom(axiom.getOWLClass(), df.getOWLObjectUnionOf(axiom
                .getClassExpressions())).accept(this));
            axioms.addAll(df.getOWLDisjointClassesAxiom(axiom.getClassExpressions()).accept(this));
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLAnnotationAssertionAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLEquivalentClassesAxiom axiom) {
            return new HashSet<OWLAxiom>(axiom.asOWLSubClassOfAxioms());
        }

        @Override
        public Set<OWLAxiom> visit(OWLEquivalentDataPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<>();
            List<OWLDataPropertyExpression> props = new ArrayList<>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(props.get(i), props.get(j)));
                    axioms.add(df.getOWLDisjointDataPropertiesAxiom(props.get(j), props.get(i)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<>();
            List<OWLObjectPropertyExpression> props = new ArrayList<>(axiom.getProperties());
            for (int i = 0; i < props.size(); i++) {
                for (int j = i + 1; j < props.size(); j++) {
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(props.get(i), props.get(j)));
                    axioms.add(df.getOWLDisjointObjectPropertiesAxiom(props.get(j), props.get(i)));
                }
            }
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLFunctionalDataPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLDataMaxCardinality(1, axiom
                .getProperty())));
        }

        @Override
        public Set<OWLAxiom> visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLObjectMaxCardinality(1, axiom
                .getProperty())));
        }

        @Override
        public Set<OWLAxiom> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLObjectMaxCardinality(1, axiom.getProperty()
                .getInverseProperty())));
        }

        @Override
        public Set<OWLAxiom> visit(OWLInverseObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom.getFirstProperty(), axiom.getSecondProperty()
                .getInverseProperty()));
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom.getSecondProperty(), axiom.getFirstProperty()
                .getInverseProperty()));
            return axioms;
        }

        @Override
        public Set<OWLAxiom> visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            return toSet(axiom);
        }

        @Override
        public Set<OWLAxiom> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()), df.getOWLDataAllValuesFrom(axiom.getProperty(),
                df.getOWLDataComplementOf(df.getOWLDataOneOf(axiom.getObject()))));
        }

        @Override
        public Set<OWLAxiom> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()), df.getOWLObjectAllValuesFrom(axiom
                .getProperty(), df.getOWLObjectComplementOf(df.getOWLObjectOneOf(axiom.getObject()))));
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
            return subClassOf(df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing()), axiom.getDomain());
        }

        @Override
        public Set<OWLAxiom> visit(OWLObjectPropertyRangeAxiom axiom) {
            return toSet(df.getOWLSubClassOfAxiom(df.getOWLThing(), df.getOWLObjectAllValuesFrom(axiom.getProperty(),
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
            return CollectionFactory.emptySet();
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
