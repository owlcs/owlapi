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
import static org.semanticweb.owlapi.util.Construct.*;
import static org.semanticweb.owlapi.util.Languages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
public class DLExpressivityChecker extends OWLObjectVisitorAdapter {

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
    public DLExpressivityChecker(Set<OWLOntology> ontologies) {
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
            ontologies.stream().flatMap(o->o.getLogicalAxioms().stream()).forEach(ax -> ax.accept(this));
            }
        Construct.trim(constructs);
        return constructs;
        }

    private void addConstruct(Construct c) {
        if (constructs == null) {
            constructs = new TreeSet<>();
        }
        // Rr+I = R + I
        if (c == ROLE_INVERSE && constructs.contains(ROLE_REFLEXIVITY_CHAINS)) {
        constructs.add(c);
            constructs.remove(ROLE_REFLEXIVITY_CHAINS);
            constructs.add(ROLE_COMPLEX);
        } else if (c == ROLE_REFLEXIVITY_CHAINS && constructs.contains(ROLE_INVERSE)) {
            constructs.add(ROLE_COMPLEX);
        } else {
            constructs.add(c);
        }
    }

    private boolean isAtomic(OWLClassExpression classExpression) {
        if (classExpression.isAnonymous()) {
            return false;
        }
        return ontologies.stream()
            .noneMatch(ont -> ont.getAxioms((OWLClass) classExpression, EXCLUDED).size() > 0);
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
        addConstruct(ROLE_INVERSE);
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
        addConstruct(CONCEPT_INTERSECTION);
        ce.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectUnionOf ce) {
        addConstruct(CONCEPT_UNION);
        ce.getOperandsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectComplementOf ce) {
        if (isAtomic(ce)) {
            addConstruct(ATOMIC_NEGATION);
        } else {
            addConstruct(CONCEPT_COMPLEX_NEGATION);
        }
        ce.getOperand().accept(this);
    }

    @Override
    public void visit(OWLObjectSomeValuesFrom ce) {
        if (isTop(ce.getFiller())) {
            addConstruct(LIMITED_EXISTENTIAL);
        } else {
            addConstruct(FULL_EXISTENTIAL);
        }
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectAllValuesFrom ce) {
        addConstruct(UNIVERSAL_RESTRICTION);
        ce.getProperty().accept(this);
        ce.getFiller().accept(this);
    }

    @Override
    public void visit(OWLObjectHasValue ce) {
        addConstruct(NOMINALS);
        addConstruct(FULL_EXISTENTIAL);
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
        addConstruct(ROLE_COMPLEX);
    }

    @Override
    public void visit(OWLObjectOneOf ce) {
        addConstruct(CONCEPT_UNION);
        addConstruct(NOMINALS);
    }

    @Override
    public void visit(OWLDataSomeValuesFrom ce) {
        addConstruct(FULL_EXISTENTIAL);
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
        addConstruct(ROLE_COMPLEX);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        addConstruct(ROLE_REFLEXIVITY_CHAINS);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointClassesAxiom axiom) {
        addConstruct(CONCEPT_COMPLEX_NEGATION);
        axiom.getClassExpressionsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLDataPropertyDomainAxiom axiom) {
        addConstruct(ROLE_DOMAIN_RANGE);
        addConstruct(D);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        addConstruct(ROLE_DOMAIN_RANGE);
        axiom.getDomain().accept(this);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        addConstruct(ROLE_HIERARCHY);
        axiom.getProperties().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDifferentIndividualsAxiom axiom) {
        addConstruct(CONCEPT_UNION);
        addConstruct(NOMINALS);
        addConstruct(CONCEPT_COMPLEX_NEGATION);
    }

    @Override
    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        addConstruct(D);
        axiom.getProperties().forEach(prop -> prop.accept(this));
    }

    @Override
    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        addConstruct(ROLE_COMPLEX);
        axiom.getProperties().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        addConstruct(ROLE_DOMAIN_RANGE);
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
        addConstruct(ROLE_HIERARCHY);
        axiom.getSubProperty().accept(this);
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLDisjointUnionAxiom axiom) {
        addConstruct(CONCEPT_UNION);
        addConstruct(CONCEPT_COMPLEX_NEGATION);
        axiom.getClassExpressions().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        addConstruct(ROLE_INVERSE);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLDataPropertyRangeAxiom axiom) {
        addConstruct(ROLE_DOMAIN_RANGE);
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
        addConstruct(ROLE_HIERARCHY);
        addConstruct(D);
        axiom.getProperties().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLClassAssertionAxiom axiom) {
        axiom.getClassExpression().accept(this);
    }

    @Override
    public void visit(OWLEquivalentClassesAxiom axiom) {
        axiom.getClassExpressionsAsList().forEach(o -> o.accept(this));
    }

    @Override
    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        addConstruct(D);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        addConstruct(ROLE_TRANSITIVE);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        addConstruct(ROLE_COMPLEX);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSubDataPropertyOfAxiom axiom) {
        addConstruct(ROLE_HIERARCHY);
        addConstruct(D);
    }

    @Override
    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        addConstruct(ROLE_INVERSE);
        addConstruct(F);
        axiom.getProperty().accept(this);
    }

    @Override
    public void visit(OWLSameIndividualAxiom axiom) {
        addConstruct(NOMINALS);
    }

    @Override
    public void visit(OWLSubPropertyChainOfAxiom axiom) {
        addConstruct(ROLE_REFLEXIVITY_CHAINS);
        axiom.getPropertyChain().forEach(o -> o.accept(this));
        axiom.getSuperProperty().accept(this);
    }

    @Override
    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        addConstruct(ROLE_INVERSE);
    }
}
