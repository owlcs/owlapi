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
package org.semanticweb.owlapi.profiles;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAtomicClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSubClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSuperClassExpression;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 */
public class OWL2QLProfile implements OWLProfile {

    protected static final Set<IRI> ALLOWED_DATATYPES =
        asUnorderedSet(OWL2Datatype.EL_DATATYPES.stream().map(i -> i.getIRI()));
    private final OWL2QLSuperClassExpressionChecker superClassExpressionChecker =
        new OWL2QLSuperClassExpressionChecker();
    private final OWL2QLSubClassExpressionChecker subClassExpressionChecker =
        new OWL2QLSubClassExpressionChecker();

    /**
     * Gets the name of the profile.
     *
     * @return A string that represents the name of the profile
     */
    @Override
    public String getName() {
        return "OWL 2 QL";
    }

    @Override
    public IRI getIRI() {
        return Profiles.OWL2_QL.getIRI();
    }

    /**
     * Checks an ontology and its import closure to see if it is within this profile.
     *
     * @param ontology The ontology to be checked.
     * @return An {@code OWLProfileReport} that describes whether or not the ontology is within this
     *         profile.
     */
    @Override
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<>();
        violations.addAll(report.getViolations());
        OWLOntologyProfileWalker walker = new OWLOntologyProfileWalker(ontology.importsClosure());
        OWL2QLObjectVisitor visitor = new OWL2QLObjectVisitor(walker);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }

    protected boolean isOWL2QLSubClassExpression(OWLClassExpression ce) {
        return ce.accept(subClassExpressionChecker).booleanValue();
    }

    /**
     * @param ce class
     * @return true if ce is superclass expression
     */
    public boolean isOWL2QLSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassExpressionChecker).booleanValue();
    }

    private static class OWL2QLSubClassExpressionChecker
        implements OWLClassExpressionVisitorEx<Boolean> {

        OWL2QLSubClassExpressionChecker() {}

        @Override
        public Boolean doDefault(OWLObject o) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLClass ce) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom ce) {
            return Boolean.valueOf(ce.getFiller().isOWLThing());
        }

        @Override
        public Boolean visit(OWLDataSomeValuesFrom ce) {
            return Boolean.TRUE;
        }
    }

    private class OWL2QLObjectVisitor extends OWLOntologyWalkerVisitor {

        private final Set<OWLProfileViolation> violations = new HashSet<>();

        OWL2QLObjectVisitor(OWLOntologyWalker walker) {
            super(walker);
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<>(violations);
        }

        @Override
        public void visit(OWLDatatype node) {
            if (!ALLOWED_DATATYPES.contains(node.getIRI())) {
                violations
                    .add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            }
        }

        @Override
        public void visit(OWLAnonymousIndividual individual) {
            violations.add(
                new UseOfAnonymousIndividual(getCurrentOntology(), getCurrentAxiom(), individual));
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            if (!isOWL2QLSubClassExpression(axiom.getSubClass())) {
                violations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom,
                    axiom.getSubClass()));
            }
            if (!isOWL2QLSuperClassExpression(axiom.getSuperClass())) {
                violations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom,
                    axiom.getSuperClass()));
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            axiom.classExpressions().filter(ce -> !isOWL2QLSubClassExpression(ce))
                .forEach(ce -> violations
                    .add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, ce)));
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            axiom.classExpressions().filter(ce -> !isOWL2QLSubClassExpression(ce))
                .forEach(ce -> violations
                    .add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, ce)));
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                violations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom,
                    axiom.getDomain()));
            }
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getRange())) {
                violations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom,
                    axiom.getRange()));
            }
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                violations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom,
                    axiom.getDomain()));
            }
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            if (axiom.getClassExpression().isAnonymous()) {
                violations.add(new UseOfNonAtomicClassExpression(getCurrentOntology(), axiom,
                    axiom.getClassExpression()));
            }
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(SWRLRule rule) {
            violations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
        }

        @Override
        public void visit(OWLDataComplementOf node) {
            violations
                .add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        }

        @Override
        public void visit(OWLDataOneOf node) {
            violations
                .add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        }

        @Override
        public void visit(OWLDatatypeRestriction node) {
            violations
                .add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        }

        @Override
        public void visit(OWLDataUnionOf node) {
            violations
                .add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
        }
    }

    private class OWL2QLSuperClassExpressionChecker
        implements OWLClassExpressionVisitorEx<Boolean> {

        OWL2QLSuperClassExpressionChecker() {}

        @Override
        public Boolean doDefault(OWLObject o) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLClass ce) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectIntersectionOf ce) {
            return Boolean.valueOf(!ce.operands().anyMatch(e -> e.accept(this) == Boolean.FALSE));
        }

        @Override
        public Boolean visit(OWLObjectComplementOf ce) {
            return Boolean.valueOf(isOWL2QLSubClassExpression(ce.getOperand()));
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom ce) {
            return Boolean.valueOf(!ce.getFiller().isAnonymous());
        }

        @Override
        public Boolean visit(OWLDataSomeValuesFrom ce) {
            return Boolean.TRUE;
        }
    }
}
