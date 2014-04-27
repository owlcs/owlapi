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

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.DLExpressivityChecker.Construct.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

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
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class DLExpressivityChecker extends OWLObjectVisitorAdapter {

    private Set<Construct> constructs;
    private Set<OWLOntology> ontologies;

    /** @return ordered constructs */
    public List<Construct> getConstructs() {
        return getOrderedConstructs();
    }

    /**
     * @param ontologies
     *        ontologies
     */
    public DLExpressivityChecker(Set<OWLOntology> ontologies) {
        this.ontologies = ontologies;
        constructs = new HashSet<Construct>();
    }

    /** @return DL name */
    @SuppressWarnings("null")
    @Nonnull
    public String getDescriptionLogicName() {
        StringBuilder s = new StringBuilder();
        for (Construct c : getOrderedConstructs()) {
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

    // Property expression
    @Override
    public void visit(@SuppressWarnings("unused") OWLObjectInverseOf property) {
        constructs.add(I);
    }

    @Override
    public void visit(@SuppressWarnings("unused") OWLDataProperty property) {
        constructs.add(D);
    }

    // Data stuff
    @Override
    public void visit(@SuppressWarnings("unused") OWLDataComplementOf node) {
        constructs.add(D);
    }

    @Override
    public void visit(@SuppressWarnings("unused") OWLDataOneOf node) {
        constructs.add(D);
    }

    @Override
    public void visit(@SuppressWarnings("unused") OWLDatatypeRestriction node) {
        constructs.add(D);
    }

    @Override
    public void visit(@SuppressWarnings("unused") OWLLiteral node) {
        constructs.add(D);
    }

    @Override
    public void visit(@SuppressWarnings("unused") OWLFacetRestriction node) {
        constructs.add(D);
    }

    // class expressions
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

    private static boolean isTop(OWLClassExpression classExpression) {
        return classExpression.isOWLThing();
    }

    private boolean isAtomic(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            return false;
        } else {
            for (OWLOntology ont : ontologies) {
                if (!ont.getAxioms((OWLClass) classExpression, EXCLUDED)
                        .isEmpty()) {
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
    public void visit(@SuppressWarnings("unused") OWLObjectOneOf desc) {
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

    // Axioms
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
    public void visit(
            @SuppressWarnings("unused") OWLDifferentIndividualsAxiom axiom) {
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
    public void visit(
            @SuppressWarnings("unused") OWLSubDataPropertyOfAxiom axiom) {
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
    public void visit(@SuppressWarnings("unused") OWLSameIndividualAxiom axiom) {
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
    public void visit(
            @SuppressWarnings("unused") OWLInverseObjectPropertiesAxiom axiom) {
        constructs.add(I);
    }

    /** Construct enum */
    public enum Construct {
        //@formatter:off
        /** AL */       AL("AL"),
        /** U */        U("U"),
        /** C */        C("C"),
        /** E */        E("E"),
        /** N */        N("N"),
        /** Q */        Q("Q"),
        /** H */        H("H"),
        /** I */        I("I"),
        /** O */        O("O"),
        /** F */        F("F"),
        /** TRAN */     TRAN("+"),
        /** D */        D("(D)"),
        /** R */        R("R"),
        /** S */        S("S"),
        /** EL */       EL("EL"),
        /** EL++ */     ELPLUSPLUS("EL++");
        //@formatter:on
        Construct(String s) {
            this.s = s;
        }

        private String s;

        @Override
        public String toString() {
            return s;
        }
    }
}
