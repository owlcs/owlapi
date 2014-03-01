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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.DLExpressivityChecker.Construct.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 26-Feb-2007
 */
@SuppressWarnings({ "unused", "javadoc" })
public class DLExpressivityChecker implements OWLObjectVisitor {

    private Set<Construct> constructs;
    private Set<OWLOntology> ontologies;

    public List<Construct> getConstructs() throws OWLException {
        return getOrderedConstructs();
    }

    public enum Construct {
        AL("AL"),
        U("U"),
        C("C"),
        E("E"),
        N("N"),
        Q("Q"),
        H("H"),
        I("I"),
        O("O"),
        F("F"),
        TRAN("+"),
        D("(D)"),
        R("R"),
        S("S"),
        EL("EL"),
        ELPLUSPLUS("EL++");

        Construct(String s) {
            this.s = s;
        }

        private String s;

        @Override
        public String toString() {
            return s;
        }
    }

    public DLExpressivityChecker(Set<OWLOntology> ontologies) {
        this.ontologies = ontologies;
        constructs = new HashSet<Construct>();
    }

    public String getDescriptionLogicName() {
        List<Construct> orderedConstructs = getOrderedConstructs();
        StringBuilder s = new StringBuilder();
        for (Construct c : orderedConstructs) {
            s.append(c);
        }
        return s.toString();
    }

    private void pruneConstructs() {
        if (constructs.contains(AL)) {
            // AL + U + E can be represented using ALC
            if (constructs.contains(C)) {
                // Remove existential because this can be represented
                // with AL + Neg
                constructs.remove(E);
                // Remove out union (intersection + negation (demorgan))
                constructs.remove(U);
            } else if (constructs.contains(E) && constructs.contains(U)) {
                // Simplify to ALC
                constructs.add(AL);
                constructs.add(C);
                constructs.remove(E);
                constructs.remove(U);
            }
        }
        if (constructs.contains(N) || constructs.contains(Q)) {
            constructs.remove(F);
        }
        if (constructs.contains(Q)) {
            constructs.remove(N);
        }
        if (constructs.contains(AL) && constructs.contains(C)
                && constructs.contains(TRAN)) {
            constructs.remove(AL);
            constructs.remove(C);
            constructs.remove(TRAN);
            constructs.add(S);
        }
        if (constructs.contains(R)) {
            constructs.remove(H);
        }
    }

    private List<Construct> getOrderedConstructs() {
        constructs.clear();
        constructs.add(AL);
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getLogicalAxioms()) {
                ax.accept(this);
            }
        }
        pruneConstructs();
        List<Construct> cons = new ArrayList<Construct>(constructs);
        Collections.sort(cons, new ConstructComparator());
        return cons;
    }

    /** A comparator that orders DL constucts to produce a traditional DL name. */
    private static class ConstructComparator implements Comparator<Construct> {

        private final List<Construct> order = new ArrayList<Construct>();

        public ConstructComparator() {
            order.add(S);
            order.add(AL);
            order.add(C);
            order.add(U);
            order.add(E);
            order.add(R);
            order.add(H);
            order.add(O);
            order.add(I);
            order.add(N);
            order.add(Q);
            order.add(F);
            order.add(TRAN);
            order.add(D);
        }

        @Override
        public int compare(Construct o1, Construct o2) {
            return order.indexOf(o1) - order.indexOf(o2);
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Property expression
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void visit(OWLObjectProperty property) {}

    @Override
    public void visit(OWLObjectInverseOf property) {
        constructs.add(I);
    }

    @Override
    public void visit(OWLDataProperty property) {
        constructs.add(D);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Data stuff
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void visit(OWLDatatype node) {}

    @Override
    public void visit(OWLDataComplementOf node) {
        constructs.add(D);
    }

    @Override
    public void visit(OWLDataOneOf node) {
        constructs.add(D);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        constructs.add(D);
    }

    @Override
    public void visit(OWLLiteral node) {
        constructs.add(D);
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        constructs.add(D);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // class expressions
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void visit(OWLClass desc) {}

    @Override
    public void visit(OWLObjectIntersectionOf desc) {
        constructs.add(AL);
        for (OWLClassExpression operands : desc.getOperands()) {
            operands.accept(this);
        }
    }

    @Override
    public void visit(OWLObjectUnionOf desc) {
        constructs.add(U);
        for (OWLClassExpression operands : desc.getOperands()) {
            operands.accept(this);
        }
    }

    private boolean isTop(OWLClassExpression classExpression) {
        return classExpression.isOWLThing();
    }

    private boolean isAtomic(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            return false;
        } else {
            for (OWLOntology ont : ontologies) {
                if (!ont.getAxioms((OWLClass) classExpression).isEmpty()) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public void visit(OWLObjectComplementOf desc) {
        if (isAtomic(desc)) {
            constructs.add(AL);
        } else {
            constructs.add(C);
        }
        desc.getOperand().accept(this);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom desc) {
        if (isTop(desc.getFiller())) {
            constructs.add(AL);
        } else {
            constructs.add(E);
        }
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom desc) {
        constructs.add(AL);
        desc.getProperty().accept(this);
        desc.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasValue desc) {
        constructs.add(O);
        constructs.add(E);
        desc.getProperty().accept(this);
    }

    private void checkCardinality(OWLDataCardinalityRestriction restriction) {
        if (restriction.isQualified()) {
            constructs.add(Q);
        } else {
            constructs.add(N);
        }
        restriction.getFiller().accept(this);
        restriction.getProperty().accept(this);
    }

    private void checkCardinality(OWLObjectCardinalityRestriction restriction) {
        if (restriction.isQualified()) {
            constructs.add(Q);
        } else {
            constructs.add(N);
        }
        restriction.getFiller().accept(this);
        restriction.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectMinCardinality desc) {
        checkCardinality(desc);
    }

    @Override
    public void visit(OWLObjectExactCardinality desc) {
        checkCardinality(desc);
    }

    @Override
    public void visit(OWLObjectMaxCardinality desc) {
        checkCardinality(desc);
    }

    @Override
    public void visit(OWLObjectHasSelf desc) {
        desc.getProperty().accept(this);
        constructs.add(R);
    }

    @Override
    public void visit(OWLObjectOneOf desc) {
        constructs.add(U);
        constructs.add(O);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom desc) {
        constructs.add(E);
        desc.getFiller().accept(this);
        desc.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataAllValuesFrom desc) {
        desc.getFiller().accept(this);
        desc.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataHasValue desc) {
        constructs.add(D);
        desc.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataMinCardinality desc) {
        checkCardinality(desc);
    }

    @Override
    public void visit(OWLDataExactCardinality desc) {
        checkCardinality(desc);
    }

    @Override
    public void visit(OWLDataMaxCardinality desc) {
        checkCardinality(desc);
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // Axioms
    //
    // //////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void visit(OWLSubClassOfAxiom axiom) {
        axiom.getSubClass().accept(this);
        axiom.getSuperClass().accept(this);
    }

    @Override
    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
        constructs.add(R);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        constructs.add(R);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        constructs.add(C);
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        axiom.getDomain().accept(this);
        constructs.add(AL);
        constructs.add(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        constructs.add(AL);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        constructs.add(H);
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        constructs.add(U);
        constructs.add(O);
        constructs.add(C);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        constructs.add(D);
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        constructs.add(R);
        for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        constructs.add(AL);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        constructs.add(F);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        constructs.add(H);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        constructs.add(U);
        constructs.add(C);
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
    }

    @Override
    public void visit(OWLDeclarationAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {}

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        constructs.add(I);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        constructs.add(AL);
        constructs.add(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        constructs.add(F);
        constructs.add(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        constructs.add(H);
        constructs.add(D);
        for (OWLDataPropertyExpression prop : axiom.getProperties()) {
            prop.accept(this);
        }
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        for (OWLClassExpression desc : axiom.getClassExpressions()) {
            desc.accept(this);
        }
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        constructs.add(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        constructs.add(TRAN);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        constructs.add(R);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        constructs.add(H);
        constructs.add(D);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        constructs.add(I);
        constructs.add(F);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        constructs.add(O);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        constructs.add(R);
        for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
            prop.accept(this);
        }
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        constructs.add(I);
    }

    @Override
    public void visit(OWLDatatypeDefinitionAxiom axiom) {
        // TODO
    }

    @Override
    public void visit(OWLHasKeyAxiom axiom) {
        // TODO:
    }

    @Override
    public void visit(OWLDataIntersectionOf node) {
        // TODO:
    }

    @Override
    public void visit(OWLDataUnionOf node) {
        // TODO:
    }

    @Override
    public void visit(OWLNamedIndividual individual) {
        // TODO:
    }

    @Override
    public void visit(OWLAnnotationProperty property) {
        // TODO:
    }

    @Override
    public void visit(OWLAnnotation annotation) {
        // TODO:
    }

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
        // TODO:
    }

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
        // TODO:
    }

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
        // TODO:
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {
        // TODO:
    }

    @Override
    public void visit(IRI iri) {
        // TODO:
    }

    @Override
    public void visit(SWRLRule rule) {}

    @Override
    public void visit(SWRLVariable node) {}

    @Override
    public void visit(OWLOntology ontology) {}

    @Override
    public void visit(SWRLClassAtom node) {}

    @Override
    public void visit(SWRLDataRangeAtom node) {}

    @Override
    public void visit(SWRLObjectPropertyAtom node) {}

    @Override
    public void visit(SWRLDataPropertyAtom node) {}

    @Override
    public void visit(SWRLBuiltInAtom node) {}

    @Override
    public void visit(SWRLIndividualArgument node) {}

    @Override
    public void visit(SWRLLiteralArgument node) {}

    @Override
    public void visit(SWRLSameIndividualAtom node) {}

    @Override
    public void visit(SWRLDifferentIndividualsAtom node) {}
}
// @SuppressWarnings({ "unused", "javadoc" })
// public class DLExpressivityChecker implements OWLObjectVisitor {
// private final Set<Construct> constructs;
// private final Set<OWLOntology> ontologies;
//
// public List<Construct> getConstructs() throws OWLException {
// return getOrderedConstructs();
// }
//
// public enum Construct {
// /**
// * concept intersection
// * */
// AND("AND") {
// @Override
// public List<Construct> notInThisProfile() {
// return new ArrayList<Construct>(EnumSet.complementOf(EnumSet.of(AND)));
// }
// },
// /**
// * AND and Universal
// * */
// FL0("FL0") {
// @Override
// public List<Construct> notInThisProfile() {
// return new ArrayList<Construct>(
// EnumSet.complementOf(EnumSet.of(AND, FL0)));
// }
// },
// /**
// * FL0, limited existentials
// */
// FLMINUS("FL-") {
// @Override
// public List<Construct> notInThisProfile() {
// return new ArrayList<Construct>(EnumSet.complementOf(EnumSet.of(AND, FL0,
// AE)));
// }
// },
// /**
// * FL- and atomic negation
// */
// AL("AL"),
// /**
// * ALC, role hierarchies, transitivity
// */
// S("S"),
// /**
// * AND, existentials with complex fillers
// */
// EL("EL") {
// @Override
// public List<Construct> notInThisProfile() {
// return Arrays.asList(O, D, I, F, N, Q, AL, FL0, U, ELPLUSPLUS);
// }
// },
// /**
// * EL, nominals, datatypes, role inclusion
// */
// ELPLUSPLUS("EL++") {
// @Override
// public List<Construct> notInThisProfile() {
// return Arrays.asList(I, F, N, Q, AL, FL0, U);
// }
// },
// /**
// * complex negation
// */
// C("C"),
// /**
// * Concept union
// */
// U("U"),
// /**
// * Atomic existential
// */
// AE("AE"),
// /**
// * unrestricted existentials
// */
// E("E"),
// /**
// * role hierarchy
// */
// H("H"),
// /**
// * role chains
// */
// R("R"),
// /**
// * nominals
// */
// O("O"),
// /**
// * inverse properties
// */
// I("I"),
// /**
// * functional properties
// */
// F("F"),
// /**
// * unqualified number restrictions
// */
// N("N"),
// /**
// * qualified number restrictions
// */
// Q("Q"),
// /**
// * transitivity
// */
// TRAN("+"),
// /**
// * datatypes
// */
// D("(D)");
// Construct(String s) {
// this.s = s;
// }
//
// private final String s;
//
// @Override
// public String toString() {
// return s;
// }
//
// public List<Construct> notInThisProfile() {
// return Collections.emptyList();
// }
// }
//
// /**
// * @param ontologies
// * the import closure for the ontology to be checked
// */
// public DLExpressivityChecker(Set<OWLOntology> ontologies) {
// this.ontologies = ontologies;
// constructs = new HashSet<Construct>();
// }
//
// /**
// * @param o
// * the ontology to be checked
// */
// public DLExpressivityChecker(OWLOntology o) {
// this.ontologies = o.getImportsClosure();
// constructs = new HashSet<Construct>();
// }
//
// public String getDescriptionLogicName() {
// List<Construct> orderedConstructs = getOrderedConstructs();
// StringBuilder s = new StringBuilder();
// for (Construct c : orderedConstructs) {
// s.append(c);
// }
// return s.toString();
// }
//
// private void pruneConstructs() {
// List<Construct> notInThisProfile = EL.notInThisProfile();
// notInThisProfile.retainAll(constructs);
// if(notInThisProfile.isEmpty()) {
// constructs.clear();
// constructs.add(EL);
// return;
// }
// notInThisProfile = ELPLUSPLUS.notInThisProfile();
// notInThisProfile.retainAll(constructs);
// if(notInThisProfile.isEmpty()) {
// constructs.clear();
// constructs.add(ELPLUSPLUS);
// return;
// }
//
//
// if (constructs.contains(ELPLUSPLUS)) if (constructs.contains(AL)) {
// // AL + U + E can be represented using ALC
// if (constructs.contains(C)) {
// // Remove existential because this can be represented
// // with AL + Neg
// constructs.remove(E);
// // Remove out union (intersection + negation (demorgan))
// constructs.remove(U);
// } else if (constructs.contains(E) && constructs.contains(U)) {
// // Simplify to ALC
// constructs.add(AL);
// constructs.add(C);
// constructs.remove(E);
// constructs.remove(U);
// }
// }
// if (constructs.contains(N) || constructs.contains(Q)) {
// constructs.remove(F);
// }
// if (constructs.contains(Q)) {
// constructs.remove(N);
// }
// if (constructs.contains(AL) && constructs.contains(C)
// && constructs.contains(TRAN)) {
// constructs.remove(AL);
// constructs.remove(C);
// constructs.remove(TRAN);
// constructs.add(S);
// }
// if (constructs.contains(R)) {
// constructs.remove(H);
// }
// }
//
// private List<Construct> getOrderedConstructs() {
// constructs.clear();
// constructs.add(AND);
// for (OWLOntology ont : ontologies) {
// for (OWLAxiom ax : ont.getLogicalAxioms()) {
// ax.accept(this);
// }
// }
// pruneConstructs();
// List<Construct> cons = new ArrayList<Construct>(constructs);
// Collections.sort(cons, new ConstructComparator());
// return cons;
// }
//
// /**
// * A comparator that orders DL constucts to produce a traditional DL name.
// */
// private static class ConstructComparator implements Comparator<Construct>,
// Serializable {
// private static final long serialVersionUID = 30406L;
//
// public int compare(Construct o1, Construct o2) {
// return o1.ordinal() - o2.ordinal();
// }
// }
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// public void visit(OWLIndividual individual) {}
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// //
// // Property expression
// //
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// public void visit(OWLObjectProperty property) {}
//
// public void visit(OWLObjectInverseOf property) {
// constructs.add(I);
// }
//
// public void visit(OWLDataProperty property) {
// constructs.add(D);
// }
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// //
// // Data stuff
// //
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// public void visit(OWLDatatype node) {}
//
// public void visit(OWLDataComplementOf node) {
// constructs.add(D);
// }
//
// public void visit(OWLDataOneOf node) {
// constructs.add(D);
// }
//
// public void visit(OWLDatatypeRestriction node) {
// constructs.add(D);
// }
//
// public void visit(OWLLiteral node) {
// constructs.add(D);
// }
//
// public void visit(OWLFacetRestriction node) {
// constructs.add(D);
// }
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// //
// // class expressions
// //
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// public void visit(OWLClass desc) {}
//
// public void visit(OWLObjectIntersectionOf desc) {
// constructs.add(AND);
// for (OWLClassExpression operands : desc.getOperands()) {
// operands.accept(this);
// }
// }
//
// public void visit(OWLObjectUnionOf desc) {
// constructs.add(U);
// for (OWLClassExpression operands : desc.getOperands()) {
// operands.accept(this);
// }
// }
//
// public void visit(OWLObjectComplementOf desc) {
// if (!desc.isAnonymous()) {
// constructs.add(AL);
// } else {
// constructs.add(C);
// }
// desc.getOperand().accept(this);
// }
//
// public void visit(OWLObjectSomeValuesFrom desc) {
// if (desc.getFiller().isOWLThing()) {
// constructs.add(AE);
// } else {
// constructs.add(E);
// }
// desc.getProperty().accept(this);
// desc.getFiller().accept(this);
// }
//
// public void visit(OWLObjectAllValuesFrom desc) {
// if (!desc.getFiller().isOWLThing()) {
// constructs.add(FL0);
// }
// desc.getProperty().accept(this);
// desc.getFiller().accept(this);
// }
//
// public void visit(OWLObjectHasValue desc) {
// constructs.add(O);
// constructs.add(E);
// desc.getProperty().accept(this);
// }
//
// private void checkCardinality(OWLDataCardinalityRestriction restriction) {
// if (restriction.isQualified()) {
// constructs.add(Q);
// } else {
// constructs.add(N);
// }
// restriction.getFiller().accept(this);
// restriction.getProperty().accept(this);
// }
//
// private void checkCardinality(OWLObjectCardinalityRestriction restriction) {
// if (restriction.isQualified()) {
// constructs.add(Q);
// } else {
// constructs.add(N);
// }
// restriction.getFiller().accept(this);
// restriction.getProperty().accept(this);
// }
//
// public void visit(OWLObjectMinCardinality desc) {
// checkCardinality(desc);
// }
//
// public void visit(OWLObjectExactCardinality desc) {
// checkCardinality(desc);
// }
//
// public void visit(OWLObjectMaxCardinality desc) {
// checkCardinality(desc);
// }
//
// public void visit(OWLObjectHasSelf desc) {
// desc.getProperty().accept(this);
// constructs.add(R);
// }
//
// public void visit(OWLObjectOneOf desc) {
// if (desc.getIndividuals().size() > 1) {
// constructs.add(U);
// } else {
// constructs.add(ELPLUSPLUS);
// }
// constructs.add(O);
// }
//
// public void visit(OWLDataSomeValuesFrom desc) {
// constructs.add(E);
// desc.getFiller().accept(this);
// desc.getProperty().accept(this);
// }
//
// public void visit(OWLDataAllValuesFrom desc) {
// constructs.add(U);
// desc.getFiller().accept(this);
// desc.getProperty().accept(this);
// }
//
// public void visit(OWLDataHasValue desc) {
// constructs.add(D);
// desc.getProperty().accept(this);
// }
//
// public void visit(OWLDataMinCardinality desc) {
// checkCardinality(desc);
// }
//
// public void visit(OWLDataExactCardinality desc) {
// checkCardinality(desc);
// }
//
// public void visit(OWLDataMaxCardinality desc) {
// checkCardinality(desc);
// }
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// //
// // Axioms
// //
// ////////////////////////////////////////////////////////////////////////////////////////////////////////
// public void visit(OWLSubClassOfAxiom axiom) {
// axiom.getSubClass().accept(this);
// axiom.getSuperClass().accept(this);
// }
//
// public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
// constructs.add(R);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
// constructs.add(R);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLDisjointClassesAxiom axiom) {
// constructs.add(C);
// for (OWLClassExpression desc : axiom.getClassExpressions()) {
// desc.accept(this);
// }
// }
//
// public void visit(OWLDataPropertyDomainAxiom axiom) {
// constructs.add(D);
// axiom.getDomain().accept(this);
// }
//
// public void visit(OWLImportsDeclaration axiom) {}
//
// public void visit(OWLObjectPropertyDomainAxiom axiom) {
// axiom.getDomain().accept(this);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
// constructs.add(H);
// for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
// prop.accept(this);
// }
// }
//
// public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
// constructs.add(D);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLDifferentIndividualsAxiom axiom) {
// // constructs.add(U);
// // constructs.add(O);
// // constructs.add(C);
// }
//
// public void visit(OWLDisjointDataPropertiesAxiom axiom) {
// constructs.add(D);
// // for (OWLDataPropertyExpression prop : axiom.getProperties()) {
// // prop.accept(this);
// // }
// }
//
// public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
// constructs.add(R);
// for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
// prop.accept(this);
// }
// }
//
// public void visit(OWLObjectPropertyRangeAxiom axiom) {
// //constructs.add(AL);
// axiom.getRange().accept(this);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLObjectPropertyAssertionAxiom axiom) {
// //axiom.getProperty().accept(this);
// }
//
// public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
// constructs.add(F);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLSubObjectPropertyOfAxiom axiom) {
// constructs.add(H);
// axiom.getSubProperty().accept(this);
// axiom.getSuperProperty().accept(this);
// }
//
// public void visit(OWLDisjointUnionAxiom axiom) {
// constructs.add(U);
// constructs.add(C);
// for (OWLClassExpression desc : axiom.getClassExpressions()) {
// desc.accept(this);
// }
// }
//
// public void visit(OWLDeclarationAxiom axiom) {}
//
// public void visit(OWLAnnotationAssertionAxiom axiom) {}
//
// public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
// constructs.add(I);
// constructs.add(H);
// //axiom.getProperty().accept(this);
// }
//
// public void visit(OWLDataPropertyRangeAxiom axiom) {
// //constructs.add(AL);
// constructs.add(D);
// //axiom.getProperty().accept(this);
// }
//
// public void visit(OWLFunctionalDataPropertyAxiom axiom) {
// constructs.add(F);
// constructs.add(D);
// //axiom.getProperty().accept(this);
// }
//
// public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
// constructs.add(H);
// constructs.add(D);
// // for (OWLDataPropertyExpression prop : axiom.getProperties()) {
// // prop.accept(this);
// // }
// }
//
// public void visit(OWLClassAssertionAxiom axiom) {
// axiom.getClassExpression().accept(this);
// }
//
// public void visit(OWLEquivalentClassesAxiom axiom) {
// for (OWLClassExpression desc : axiom.getClassExpressions()) {
// desc.accept(this);
// }
// }
//
// public void visit(OWLDataPropertyAssertionAxiom axiom) {
// constructs.add(D);
// //axiom.getProperty().accept(this);
// }
//
// public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
// constructs.add(TRAN);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
// constructs.add(R);
// axiom.getProperty().accept(this);
// }
//
// public void visit(OWLSubDataPropertyOfAxiom axiom) {
// constructs.add(H);
// constructs.add(D);
// }
//
// public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
// constructs.add(I);
// constructs.add(F);
// //axiom.getProperty().accept(this);
// }
//
// public void visit(OWLSameIndividualAxiom axiom) {
// //constructs.add(O);
// }
//
// public void visit(OWLSubPropertyChainOfAxiom axiom) {
// constructs.add(R);
// for (OWLObjectPropertyExpression prop : axiom.getPropertyChain()) {
// prop.accept(this);
// }
// axiom.getSuperProperty().accept(this);
// }
//
// public void visit(OWLInverseObjectPropertiesAxiom axiom) {
// constructs.add(I);
// }
//
// public void visit(OWLDatatypeDefinitionAxiom axiom) {
// constructs.add(D);
// }
//
// public void visit(OWLHasKeyAxiom axiom) {
// constructs.add(ELPLUSPLUS);
// constructs.add(R);
// for (OWLObjectPropertyExpression prop : axiom.getObjectPropertyExpressions())
// {
// prop.accept(this);
// }
// for (OWLDataPropertyExpression prop : axiom.getDataPropertyExpressions()) {
// prop.accept(this);
// }
// }
//
// public void visit(OWLDataIntersectionOf node) {}
//
// public void visit(OWLDataUnionOf node) {}
//
// public void visit(OWLNamedIndividual individual) {}
//
// public void visit(OWLAnnotationProperty property) {}
//
// public void visit(OWLAnnotation annotation) {}
//
// public void visit(OWLAnnotationPropertyDomainAxiom axiom) {}
//
// public void visit(OWLAnnotationPropertyRangeAxiom axiom) {}
//
// public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {}
//
// public void visit(OWLAnonymousIndividual individual) {}
//
// public void visit(IRI iri) {}
//
// public void visit(SWRLRule rule) {}
//
// public void visit(SWRLVariable node) {}
//
// public void visit(OWLOntology ontology) {}
//
// public void visit(SWRLClassAtom node) {}
//
// public void visit(SWRLDataRangeAtom node) {}
//
// public void visit(SWRLObjectPropertyAtom node) {}
//
// public void visit(SWRLDataPropertyAtom node) {}
//
// public void visit(SWRLBuiltInAtom node) {}
//
// public void visit(SWRLIndividualArgument node) {}
//
// public void visit(SWRLLiteralArgument node) {}
//
// public void visit(SWRLSameIndividualAtom node) {}
//
// public void visit(SWRLDifferentIndividualsAtom node) {}
// }
