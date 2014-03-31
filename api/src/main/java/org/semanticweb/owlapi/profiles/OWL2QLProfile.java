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

import static org.semanticweb.owlapi.vocab.OWL2Datatype.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
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
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitorEx;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 */
public class OWL2QLProfile implements OWLProfile {

    protected final static Set<IRI> allowedDatatypes = new HashSet<IRI>(
            Arrays.asList(
            //@formatter:off
            RDF_PLAIN_LITERAL.getIRI(), 
            RDF_XML_LITERAL.getIRI(), 
            RDFS_LITERAL.getIRI(),
            OWL_REAL.getIRI(), 
            OWL_RATIONAL.getIRI(), 
            XSD_DECIMAL.getIRI(),
            XSD_INTEGER.getIRI(), 
            XSD_NON_NEGATIVE_INTEGER.getIRI(), 
            XSD_STRING.getIRI(),
            XSD_NORMALIZED_STRING.getIRI(), 
            XSD_TOKEN.getIRI(), 
            XSD_NAME.getIRI(),
            XSD_NCNAME.getIRI(), 
            XSD_NMTOKEN.getIRI(), 
            XSD_HEX_BINARY.getIRI(),
            XSD_BASE_64_BINARY.getIRI(), 
            XSD_ANY_URI.getIRI(), 
            XSD_DATE_TIME.getIRI(),
            XSD_DATE_TIME_STAMP.getIRI()
            //@formatter:on
            ));

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
     * Checks an ontology and its import closure to see if it is within this
     * profile.
     * 
     * @param ontology
     *        The ontology to be checked.
     * @return An {@code OWLProfileReport} that describes whether or not the
     *         ontology is within this profile.
     */
    @Override
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation<?>> violations = new HashSet<OWLProfileViolation<?>>();
        violations.addAll(report.getViolations());
        OWLOntologyProfileWalker walker = new OWLOntologyProfileWalker(
                ontology.getImportsClosure());
        OWL2QLObjectVisitor visitor = new OWL2QLObjectVisitor(walker);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }

    private class OWL2QLObjectVisitor extends
            OWLOntologyWalkerVisitorEx<Object> {

        private final Set<OWLProfileViolation<?>> profileViolations = new HashSet<OWLProfileViolation<?>>();

        OWL2QLObjectVisitor(OWLOntologyWalker walker) {
            super(walker);
        }

        public Set<OWLProfileViolation<?>> getProfileViolations() {
            return new HashSet<OWLProfileViolation<?>>(profileViolations);
        }

        @Override
        public Object visit(OWLDatatype node) {
            if (!allowedDatatypes.contains(node.getIRI())) {
                profileViolations.add(new UseOfIllegalDataRange(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLAnonymousIndividual individual) {
            profileViolations.add(new UseOfAnonymousIndividual(
                    getCurrentOntology(), getCurrentAxiom(), individual));
            return null;
        }

        @Override
        public Object visit(OWLHasKeyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLSubClassOfAxiom axiom) {
            if (!isOWL2QLSubClassExpression(axiom.getSubClass())) {
                profileViolations.add(new UseOfNonSubClassExpression(
                        getCurrentOntology(), axiom, axiom.getSubClass()));
            }
            if (!isOWL2QLSuperClassExpression(axiom.getSuperClass())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getSuperClass()));
            }
            return null;
        }

        @Override
        public Object visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLClassExpression ce : axiom.getClassExpressions()) {
                if (!isOWL2QLSubClassExpression(ce)) {
                    profileViolations.add(new UseOfNonSubClassExpression(
                            getCurrentOntology(), axiom, ce));
                }
            }
            return null;
        }

        @Override
        public Object visit(OWLDisjointClassesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLObjectPropertyDomainAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getDomain()));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectPropertyRangeAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getRange())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getRange()));
            }
            return null;
        }

        @Override
        public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLTransitiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLDataPropertyDomainAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getDomain()));
            }
            return null;
        }

        @Override
        public Object visit(OWLClassAssertionAxiom axiom) {
            if (axiom.getClassExpression().isAnonymous()) {
                profileViolations
                        .add(new UseOfNonAtomicClassExpression(
                                getCurrentOntology(), axiom, axiom
                                        .getClassExpression()));
            }
            return null;
        }

        @Override
        public Object visit(OWLSameIndividualAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLDisjointUnionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    rule));
            return null;
        }

        @Override
        public Object visit(OWLDataComplementOf node) {
            profileViolations.add(new UseOfIllegalDataRange(
                    getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
        public Object visit(OWLDataOneOf node) {
            profileViolations.add(new UseOfIllegalDataRange(
                    getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
        public Object visit(OWLDatatypeRestriction node) {
            profileViolations.add(new UseOfIllegalDataRange(
                    getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        @Override
        public Object visit(OWLDataUnionOf node) {
            profileViolations.add(new UseOfIllegalDataRange(
                    getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }
    }

    private static class OWL2QLSubClassExpressionChecker implements
            OWLClassExpressionVisitorEx<Boolean> {

        public OWL2QLSubClassExpressionChecker() {}

        @Override
        public Boolean visit(OWLClass desc) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectIntersectionOf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectUnionOf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectComplementOf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return Boolean.valueOf(desc.getFiller().isOWLThing());
        }

        @Override
        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectHasValue desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectMinCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectExactCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectMaxCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectHasSelf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectOneOf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLDataAllValuesFrom desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataHasValue desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataMinCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataExactCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataMaxCardinality desc) {
            return Boolean.FALSE;
        }
    }

    private final OWL2QLSubClassExpressionChecker subClassExpressionChecker = new OWL2QLSubClassExpressionChecker();

    protected boolean isOWL2QLSubClassExpression(OWLClassExpression ce) {
        return ce.accept(subClassExpressionChecker).booleanValue();
    }

    private class OWL2QLSuperClassExpressionChecker implements
            OWLClassExpressionVisitorEx<Boolean> {

        public OWL2QLSuperClassExpressionChecker() {}

        @Override
        public Boolean visit(OWLClass desc) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression ce : desc.getOperands()) {
                if (!ce.accept(this).booleanValue()) {
                    return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectUnionOf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectComplementOf desc) {
            return Boolean
                    .valueOf(isOWL2QLSubClassExpression(desc.getOperand()));
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return Boolean.valueOf(!desc.getFiller().isAnonymous());
        }

        @Override
        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectHasValue desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectMinCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectExactCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectMaxCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectHasSelf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLObjectOneOf desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLDataAllValuesFrom desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataHasValue desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataMinCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataExactCardinality desc) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLDataMaxCardinality desc) {
            return Boolean.FALSE;
        }
    }

    private final OWL2QLSuperClassExpressionChecker superClassExpressionChecker = new OWL2QLSuperClassExpressionChecker();

    /**
     * @param ce
     *        class
     * @return true if ce is superclass expression
     */
    public boolean isOWL2QLSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassExpressionChecker).booleanValue();
    }
}
