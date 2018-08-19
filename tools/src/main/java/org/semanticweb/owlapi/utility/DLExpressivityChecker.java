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
package org.semanticweb.owlapi.utility;

import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.AL;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.C;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.D;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.E;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.F;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.H;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.I;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.N;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.O;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.Q;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.R;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.S;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.TRAN;
import static org.semanticweb.owlapi.utility.DLExpressivityChecker.Construct.U;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
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
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class DLExpressivityChecker implements OWLObjectVisitor {
    /**
     * Construct enum.
     */
    public enum Construct {
        //@formatter:off
        /** Atomic negations. */
        ATOMNEG     ("NEG"),
        /** Concept intersections. */
        CINT        ("CINT"),
        /** Universal restrictions. */
        UNIVRESTR   ("UNIVRESTR"),
        /** Limited existential quantifications (Top only). */
        LIMEXIST    ("LIMEXIST"),
        /** Role restrictions. */
        RRESTR      ("RRESTR"),
        /** FL0 - A sub-language of FL-, which is obtained by disallowing limited existential quantification. */
        FL0         ("FL0", CINT, UNIVRESTR),
        /** FL- - A sub-language of FL, which is obtained by disallowing role restriction. This is equivalent to AL without atomic negation. */
        FLMINUS     ("FL-", CINT, UNIVRESTR, LIMEXIST),
        /**
         * FL - Frame based description language, allows:
         * <ul>
         * <li>Concept intersection</li>
         * <li>Universal restrictions</li>
         * <li>Limited existential quantification</li>
         * <li>Role restriction</li></ul>
         */
        FL          ("FL", CINT, UNIVRESTR, LIMEXIST, RRESTR),
        /** AL - Attributive language. This is the base language which allows:
         * <ul>
         * <li>Atomic negation (negation of concept names that do not appear on the left-hand side of axioms)</li>
         * <li>Concept intersection</li>
         * <li>Universal restrictions</li>
         * <li>Limited existential quantification</li></ul>*/
        AL          ("AL",ATOMNEG, CINT, UNIVRESTR, LIMEXIST),
        /** U - Concept union. */
        U           ("U"),
        /** C - Complex concept negation. */
        C           ("C"),
        /** E - Full existential qualification (existential restrictions that have fillers other than top. */
        E           ("E"),
        /** N - Cardinality restrictions (owl:cardinality, owl:maxCardinality), a special case of counting quantification. */
        N           ("N"),
        /** Q - Qualified cardinality restrictions (available in OWL 2, cardinality restrictions that have fillers other than top). */
        Q           ("Q"),
        /** H - Role hierarchy (subproperties: rdfs:subPropertyOf). */
        H           ("H"),
        /** I - Inverse properties. */
        I           ("I"),
        /** O - Nominals. (Enumerated classes of object value restrictions: owl:oneOf, owl:hasValue). */
        O           ("O"),
        /** F - Functional properties, a special case of uniqueness quantification. */
        F           ("F"),
        /** TRAN - Transitive roles. */
        TRAN        ("+"),
        /** D - Use of datatype properties, data values or data types. */
        D           ("(D)"),
        /** R - Limited complex role inclusion axioms; reflexivity and irreflexivity; role disjointness. */
        R           ("R"),
        /** S - An abbreviation for ALC with transitive roles. */
        S           ("S"),
        /** EL - Existential language, allows:
         * <ul>
         * <li>Concept intersection</li>
         * <li>Existential restrictions (of full existential quantification)</li></ul> */
        EL          ("EL", CINT, E),
        /** EL++ - Alias for ELRO. */
        ELPLUSPLUS ("EL++", CINT, E, R, O);
        
        private final String s;
        //@formatter:on
        private Construct[] components;

        Construct(String s, Construct... components) {
            this.s = s;
            this.components = components;
        }

        @Override
        public String toString() {
            return s;
        }
    }

    private static final List<Construct> order =
        Arrays.asList(S, AL, C, U, E, R, H, O, I, N, Q, F, TRAN, D);
    /**
     * A comparator that orders DL constucts to produce a traditional DL name.
     */
    private static final Comparator<Construct> constructComparator =
        Comparator.comparing(order::indexOf);
    private final Set<Construct> constructs;
    private final List<OWLOntology> ontologies;

    /**
     * @param ontologies ontologies
     */
    public DLExpressivityChecker(Collection<OWLOntology> ontologies) {
        this.ontologies = new ArrayList<>(ontologies);
        constructs = new HashSet<>();
    }

    private static boolean isTop(OWLClassExpression classExpression) {
        return classExpression.isOWLThing();
    }

    /**
     * @return ordered constructs
     */
    public List<Construct> getConstructs() {
        return getOrderedConstructs();
    }

    /**
     * @return DL name
     */
    public String getDescriptionLogicName() {
        return getOrderedConstructs().stream().map(Object::toString).collect(Collectors.joining());
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
        ontologies.stream().flatMap(OWLOntology::logicalAxioms).forEach(ax -> ax.accept(this));
        pruneConstructs();
        return asList(constructs.stream().sorted(constructComparator));
    }

    private boolean isAtomic(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            return false;
        }
        return ontologies.stream()
            .noneMatch(ont -> ont.axioms((OWLClass) classExpression, EXCLUDED).count() > 0);
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
}
