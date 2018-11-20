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
import static org.semanticweb.owlapi.util.Construct.ATOMNEG;
import static org.semanticweb.owlapi.util.Construct.C;
import static org.semanticweb.owlapi.util.Construct.CINT;
import static org.semanticweb.owlapi.util.Construct.D;
import static org.semanticweb.owlapi.util.Construct.E;
import static org.semanticweb.owlapi.util.Construct.F;
import static org.semanticweb.owlapi.util.Construct.H;
import static org.semanticweb.owlapi.util.Construct.I;
import static org.semanticweb.owlapi.util.Construct.LIMEXIST;
import static org.semanticweb.owlapi.util.Construct.N;
import static org.semanticweb.owlapi.util.Construct.O;
import static org.semanticweb.owlapi.util.Construct.Q;
import static org.semanticweb.owlapi.util.Construct.R;
import static org.semanticweb.owlapi.util.Construct.RRESTR;
import static org.semanticweb.owlapi.util.Construct.TRAN;
import static org.semanticweb.owlapi.util.Construct.U;
import static org.semanticweb.owlapi.util.Construct.UNIVRESTR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

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
     * @return Collection of Languages that include all constructs used in the ontology. Each
     *         language returned allows for all constructs found and has no sublanguages that also
     *         allow for all constructs found. E.g., if FL is returned, FL0 and FLMNUS cannot be
     *         returned.
     */
    public Collection<Languages> expressibleInLanguages() {
        return Arrays.stream(Languages.values()).filter(this::minimal).collect(Collectors.toList());
    }

    /**
     * @param l language to check
     * @return true if l is minimal, i.e., all sublanguages of l cannot represent all the constructs
     *         found, but l can.
     */
    public boolean minimal(Languages l) {
        if (!l.components.containsAll(getOrderedConstructs())) {
            // not minimal because it does not cover the constructs found
            return false;
        }
        return Arrays.stream(Languages.values()).filter(p -> p.isSubLanguageOf(l))
            .noneMatch(this::minimal);
    }

    /**
     * @param l language to check
     * @return true if l is sufficient to express the ontology, i.e., if all constructs found in the
     *         ontology are included in the language
     */
    public boolean isWithin(Languages l) {
        return l.components.containsAll(getOrderedConstructs());
    }

    /**
     * @param c construct to check
     * @return true if the matched constructs contain c.
     */
    public boolean has(Construct c) {
        return getOrderedConstructs().contains(c);
    }

    private Set<Construct> constructs;
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

    /** @return ordered constructs */
    public List<Construct> getConstructs() {
        return new ArrayList<>(getOrderedConstructs());
    }

    /** @return DL name */
    @Nonnull
    public String getDescriptionLogicName() {
        return getOrderedConstructs().stream().map(Object::toString).collect(Collectors.joining());
    }

    private Set<Construct> getOrderedConstructs() {
        if (constructs == null) {
            constructs = new TreeSet<>();
            ontologies.stream().flatMap(o -> o.logicalAxioms()).forEach(ax -> ax.accept(this));
        }
        return constructs;
    }

    private void addConstruct(Construct c) {
        if (constructs == null) {
            constructs = new TreeSet<>();
        }
        constructs.add(c);
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
            addConstruct(Q);
        } else {
            addConstruct(N);
        }
        restriction.getFiller().accept(this);
        restriction.getProperty().accept(this);
    }

    private void checkCardinality(OWLObjectCardinalityRestriction restriction) {
        if (restriction.isQualified()) {
            addConstruct(Q);
        } else {
            addConstruct(N);
        }
        restriction.getFiller().accept(this);
        restriction.getProperty().accept(this);
    }

    // Property expression
    @Override
    public void visit(OWLObjectInverseOf property) {
        addConstruct(I);
    }

    @Override
    public void visit(OWLDataProperty property) {
        addConstruct(D);
    }

    // Data stuff
    @Override
    public void visit(OWLDataComplementOf node) {
        addConstruct(D);
    }

    @Override
    public void visit(OWLDataOneOf node) {
        addConstruct(D);
    }

    @Override
    public void visit(OWLDatatypeRestriction node) {
        addConstruct(D);
    }

    @Override
    public void visit(OWLLiteral node) {
        addConstruct(D);
    }

    @Override
    public void visit(OWLFacetRestriction node) {
        addConstruct(D);
    }

    // class expressions
    @Override
    public void visit(OWLObjectIntersectionOf ce) {
        addConstruct(CINT);
        ce.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        addConstruct(U);
        ce.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        if (isAtomic(ce)) {
            addConstruct(ATOMNEG);
        } else {
            addConstruct(C);
        }
        ce.getOperand().accept(this);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        if (isTop(ce.getFiller())) {
            addConstruct(LIMEXIST);
        } else {
            addConstruct(E);
        }
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        addConstruct(UNIVRESTR);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        addConstruct(O);
        addConstruct(E);
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
        addConstruct(R);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        addConstruct(U);
        addConstruct(O);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        addConstruct(E);
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
        addConstruct(D);
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
        addConstruct(R);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addConstruct(R);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        addConstruct(C);
        axiom.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addConstruct(RRESTR);
        addConstruct(D);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addConstruct(RRESTR);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        addConstruct(H);
        axiom.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        addConstruct(U);
        addConstruct(O);
        addConstruct(C);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        addConstruct(D);
        axiom.getOperandsAsList().forEach(prop -> prop.accept(this));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        addConstruct(R);
        axiom.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addConstruct(RRESTR);
        axiom.getRange().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        addConstruct(F);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubObjectPropertyOfAxiom axiom) {
        addConstruct(H);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        addConstruct(U);
        addConstruct(C);
        axiom.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        addConstruct(I);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addConstruct(RRESTR);
        addConstruct(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        addConstruct(F);
        addConstruct(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        addConstruct(H);
        addConstruct(D);
        axiom.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        axiom.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addConstruct(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        addConstruct(TRAN);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        addConstruct(R);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        addConstruct(H);
        addConstruct(D);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addConstruct(I);
        addConstruct(F);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        addConstruct(O);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        addConstruct(R);
        axiom.getPropertyChain().forEach(o -> o.accept(this));
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        addConstruct(I);
    }
}
