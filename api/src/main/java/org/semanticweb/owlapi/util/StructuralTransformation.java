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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.add;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
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
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
public class StructuralTransformation implements Serializable {

    protected final OWLDataFactory df;
    protected final Set<OWLEntity> signature = new HashSet<>();
    private int nameCounter = 0;

    /**
     * @param dataFactory factory to use
     */
    public StructuralTransformation(OWLDataFactory dataFactory) {
        df = checkNotNull(dataFactory, "dataFactory cannot be null");
    }

    protected OWLClass createNewName() {
        OWLClass cls = df.getOWLClass("http://www.semanticweb.org/ontology#", "X" + nameCounter);
        nameCounter++;
        return cls;
    }

    /**
     * @param axioms axioms to transform
     * @return transformed axioms
     */
    public Set<OWLAxiom> getTransformedAxioms(Set<OWLAxiom> axioms) {
        checkNotNull(axioms, "axioms cannot be null");
        signature.clear();
        axioms.forEach(ax -> add(signature, ax.signature()));
        AxiomRewriter rewriter = new AxiomRewriter();
        Set<OWLAxiom> transformedAxioms = new HashSet<>();
        for (OWLAxiom ax : axioms) {
            for (OWLAxiom transAx : ax.accept(rewriter)) {
                if (transAx instanceof OWLSubClassOfAxiom) {
                    AxiomFlattener flattener =
                        new AxiomFlattener(df, ((OWLSubClassOfAxiom) transAx).getSuperClass());
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

        @Override
        public OWLClassExpression doDefault(OWLObject o) {
            return (OWLClassExpression) o;
        }

        private OWLSubClassOfAxiom getSCA(OWLClass lhs, OWLClassExpression ce) {
            return ldf.getOWLSubClassOfAxiom(lhs, ce);
        }

        public Set<OWLAxiom> getAxioms() {
            axioms.clear();
            OWLClass lhs = ldf.getOWLThing();
            axioms.add(getSCA(lhs, rhs.accept(this)));
            return axioms;
        }

        @Override
        public OWLClassExpression visit(OWLObjectAllValuesFrom ce) {
            if (signature.containsAll(asList(ce.getFiller().signature()))) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectAllValuesFrom(ce.getProperty(), name);
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectComplementOf ce) {
            // Should be a literal
            if (ce.getOperand().isAnonymous()) {
                throw new IllegalStateException(
                    "Negation of arbitrary class expressions not allowed");
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectExactCardinality ce) {
            if (signature.containsAll(asList(ce.getFiller().signature()))) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectExactCardinality(ce.getCardinality(), ce.getProperty(),
                    name);
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectIntersectionOf ce) {
            OWLClass name = createNewName();
            ce.operands().forEach(op -> axioms.add(getSCA(name, op.accept(this))));
            return name;
        }

        @Override
        public OWLClassExpression visit(OWLObjectMaxCardinality ce) {
            if (signature.containsAll(asList(ce.getFiller().signature()))) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectMaxCardinality(ce.getCardinality(), ce.getProperty(), name);
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectMinCardinality ce) {
            if (signature.containsAll(asList(ce.getFiller().signature()))) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectMinCardinality(ce.getCardinality(), ce.getProperty(), name);
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectOneOf ce) {
            if (ce.individuals().count() > 1) {
                throw new IllegalStateException("ObjectOneOf with more than one individual!");
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectSomeValuesFrom ce) {
            if (ce.getFiller().isAnonymous()) {
                OWLClass name = createNewName();
                axioms.add(getSCA(name, ce.getFiller().accept(this)));
                return ldf.getOWLObjectSomeValuesFrom(ce.getProperty(), name);
            }
            return ce;
        }

        @Override
        public OWLClassExpression visit(OWLObjectUnionOf ce) {
            Set<OWLClassExpression> descs = new HashSet<>();
            ce.operands().forEach(op -> visitOperand(descs, op));
            return ldf.getOWLObjectUnionOf(descs);
        }

        protected void visitOperand(Set<OWLClassExpression> descs, OWLClassExpression op) {
            OWLClassExpression flatOp = op.accept(this);
            if (flatOp.isAnonymous() || signature.contains(flatOp.asOWLClass())) {
                OWLClass name = createNewName();
                descs.add(name);
                axioms.add(ldf.getOWLSubClassOfAxiom(name, flatOp));
            } else {
                descs.add(flatOp);
            }
        }
    }

    /**
     * Rewrites axioms into GCIs.<br>
     * For example: SubClassOf(A, C) becomes SubClassOf(TOP, not(A) or C)
     */
    private class AxiomRewriter implements OWLAxiomVisitorEx<Collection<? extends OWLAxiom>> {

        AxiomRewriter() {}

        private Collection<OWLAxiom> subClassOf(OWLClassExpression sub, OWLClassExpression sup) {
            return doDefault(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(sub), sup).getNNF()));
        }

        private OWLAxiom subClassOfSingle(OWLClassExpression sub, OWLClassExpression sup) {
            return df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(sub), sup).getNNF());
        }

        @Override
        public Collection<OWLAxiom> doDefault(OWLObject o) {
            return Collections.singleton((OWLAxiom) o);
        }

        @Override
        public Collection<OWLAxiom> visit(OWLClassAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getIndividual()),
                axiom.getClassExpression());
        }

        @Override
        public Collection<OWLAxiom> visit(OWLDataPropertyDomainAxiom axiom) {
            return subClassOf(df.getOWLDataSomeValuesFrom(axiom.getProperty(), df.getTopDatatype()),
                axiom.getDomain());
        }

        @Override
        public Collection<OWLAxiom> visit(OWLDataPropertyRangeAxiom axiom) {
            return doDefault(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLDataAllValuesFrom(axiom.getProperty(), axiom.getRange())));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLDifferentIndividualsAxiom axiom) {
            return axiom.walkPairwise(
                (a, b) -> subClassOfSingle(df.getOWLObjectOneOf(a), df.getOWLObjectOneOf(b)));
        }

        @Override
        public Collection<? extends OWLAxiom> visit(OWLDisjointClassesAxiom axiom) {
            // Explode
            return axiom.asOWLSubClassOfAxioms();
        }

        @Override
        public Collection<OWLAxiom> visit(OWLDisjointUnionAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.addAll(df.getOWLEquivalentClassesAxiom(axiom.getOWLClass(),
                df.getOWLObjectUnionOf(axiom.classExpressions())).accept(this));
            axioms.addAll(df.getOWLDisjointClassesAxiom(axiom.classExpressions()).accept(this));
            return axioms;
        }

        @Override
        public Collection<? extends OWLAxiom> visit(OWLEquivalentClassesAxiom axiom) {
            return axiom.asOWLSubClassOfAxioms();
        }

        @Override
        public Collection<OWLAxiom> visit(OWLEquivalentDataPropertiesAxiom axiom) {
            return axiom.walkPairwise((a, b) -> df.getOWLEquivalentDataPropertiesAxiom(a, b));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            return axiom.walkPairwise((a, b) -> df.getOWLEquivalentObjectPropertiesAxiom(a, b));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLDisjointDataPropertiesAxiom axiom) {
            return axiom.walkPairwise((a, b) -> df.getOWLDisjointDataPropertiesAxiom(a, b));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLDisjointObjectPropertiesAxiom axiom) {
            return axiom.walkPairwise((a, b) -> df.getOWLDisjointObjectPropertiesAxiom(a, b));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLFunctionalDataPropertyAxiom axiom) {
            return doDefault(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLDataMaxCardinality(1, axiom.getProperty())));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLFunctionalObjectPropertyAxiom axiom) {
            return doDefault(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectMaxCardinality(1, axiom.getProperty())));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            return doDefault(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectMaxCardinality(1, axiom.getProperty().getInverseProperty())));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLInverseObjectPropertiesAxiom axiom) {
            Set<OWLAxiom> axioms = new HashSet<>();
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom.getFirstProperty(),
                axiom.getSecondProperty().getInverseProperty()));
            axioms.add(df.getOWLSubObjectPropertyOfAxiom(axiom.getSecondProperty(),
                axiom.getFirstProperty().getInverseProperty()));
            return axioms;
        }

        @Override
        public Collection<OWLAxiom> visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()),
                df.getOWLDataAllValuesFrom(axiom.getProperty(),
                    df.getOWLDataComplementOf(df.getOWLDataOneOf(axiom.getObject()))));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            return subClassOf(df.getOWLObjectOneOf(axiom.getSubject()),
                df.getOWLObjectAllValuesFrom(axiom.getProperty(),
                    df.getOWLObjectComplementOf(df.getOWLObjectOneOf(axiom.getObject()))));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLObjectPropertyDomainAxiom axiom) {
            return subClassOf(df.getOWLObjectSomeValuesFrom(axiom.getProperty(), df.getOWLThing()),
                axiom.getDomain());
        }

        @Override
        public Collection<OWLAxiom> visit(OWLObjectPropertyRangeAxiom axiom) {
            return doDefault(df.getOWLSubClassOfAxiom(df.getOWLThing(),
                df.getOWLObjectAllValuesFrom(axiom.getProperty(), axiom.getRange())));
        }

        @Override
        public Collection<OWLAxiom> visit(OWLSameIndividualAxiom axiom) {
            return Collections.emptySet();
        }

        @Override
        public Collection<OWLAxiom> visit(OWLSubClassOfAxiom axiom) {
            return subClassOf(axiom.getSubClass(), axiom.getSuperClass());
        }
    }
}
