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

import java.io.Serializable;
import java.util.*;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.*;

import com.google.common.base.Joiner;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class DLExpressivityChecker implements OWLObjectVisitor {

    private final Set<Construct> constructs;
    private final List<OWLOntology> ontologies;

    /**
     * @return ordered constructs
     */
    public List<Construct> getConstructs() {
        return getOrderedConstructs();
    }

    /**
     * @param ontologies
     *        ontologies
     */
    public DLExpressivityChecker(Collection<OWLOntology> ontologies) {
        this.ontologies = new ArrayList<>(ontologies);
        constructs = new HashSet<>();
    }

    /**
     * @return DL name
     */
    public String getDescriptionLogicName() {
        return Joiner.on("").join(getOrderedConstructs());
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
        if (constructs.contains(AL) && constructs.contains(C) && constructs.contains(TRAN)) {
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
        ontologies.stream().flatMap(o -> o.logicalAxioms()).forEach(ax -> ax.accept(this));
        pruneConstructs();
        List<Construct> cons = new ArrayList<>(constructs);
        Collections.sort(cons, new ConstructComparator());
        return cons;
    }

    /**
     * A comparator that orders DL constucts to produce a traditional DL name.
     */
    private static class ConstructComparator implements Comparator<Construct>, Serializable {

        private final List<Construct> order = Arrays.asList(S, AL, C, U, E, R, H, O, I, N, Q, F, TRAN, D);

        ConstructComparator() {}

        @Override
        public int compare(@Nullable Construct o1, @Nullable Construct o2) {
            return order.indexOf(o1) - order.indexOf(o2);
        }
    }

    // Property expression
    @Override
    public void visit(OWLObjectInverseOf property) {
        constructs.add(I);
    }

    @Override
    public void visit(OWLDataProperty property) {
        constructs.add(D);
    }

    // Data stuff
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

    // class expressions
    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        constructs.add(AL);
        ce.operands().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        constructs.add(U);
        ce.operands().forEach(o -> o.accept(this));
    }

    private static boolean isTop(OWLClassExpression classExpression) {
        return classExpression.isOWLThing();
    }

    private boolean isAtomic(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            return false;
        }
        return !ontologies.stream().anyMatch(ont -> ont.axioms((OWLClass) classExpression, EXCLUDED).count() > 0);
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        if (isAtomic(ce)) {
            constructs.add(AL);
        } else {
            constructs.add(C);
        }
        ce.getOperand().accept(this);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        if (isTop(ce.getFiller())) {
            constructs.add(AL);
        } else {
            constructs.add(E);
        }
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        constructs.add(AL);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        constructs.add(O);
        constructs.add(E);
        ce.getProperty().accept(this);
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
    public void visit(OWLObjectMinCardinality ce) {
        checkCardinality(ce);
    }

    @Override
    public void visit(OWLObjectExactCardinality ce) {
        checkCardinality(ce);
    }

    @Override
    public void visit(OWLObjectMaxCardinality ce) {
        checkCardinality(ce);
    }

    @Override
    public void visit(OWLObjectHasSelf ce) {
        ce.getProperty().accept(this);
        constructs.add(R);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        constructs.add(U);
        constructs.add(O);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        constructs.add(E);
        ce.getFiller().accept(this);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataAllValuesFrom ce) {
        ce.getFiller().accept(this);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataHasValue ce) {
        constructs.add(D);
        ce.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataMinCardinality ce) {
        checkCardinality(ce);
    }

    @Override
    public void visit(OWLDataExactCardinality ce) {
        checkCardinality(ce);
    }

    @Override
    public void visit(OWLDataMaxCardinality ce) {
        checkCardinality(ce);
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
        axiom.classExpressions().forEach(o -> o.accept(this));
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
        axiom.properties().forEach(o -> o.accept(this));
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
        axiom.properties().forEach(prop -> prop.accept(this));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        constructs.add(R);
        axiom.properties().forEach(o -> o.accept(this));
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
        axiom.classExpressions().forEach(o -> o.accept(this));
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
        axiom.properties().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        axiom.classExpressions().forEach(o -> o.accept(this));
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
        axiom.getPropertyChain().forEach(o -> o.accept(this));
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        constructs.add(I);
    }

    /** Construct enum. */
    public enum Construct {
        //@formatter:off
        /** AL. */       AL("AL"),
        /** U. */        U("U"),
        /** C. */        C("C"),
        /** E. */        E("E"),
        /** N. */        N("N"),
        /** Q. */        Q("Q"),
        /** H. */        H("H"),
        /** I. */        I("I"),
        /** O. */        O("O"),
        /** F. */        F("F"),
        /** TRAN. */     TRAN("+"),
        /** D. */        D("(D)"),
        /** R. */        R("R"),
        /** S. */        S("S"),
        /** EL. */       EL("EL"),
        /** EL++. */     ELPLUSPLUS("EL++");
        //@formatter:on
        Construct(String s) {
            this.s = s;
        }

        private final String s;

        @Override
        public String toString() {
            return s;
        }
    }
}
