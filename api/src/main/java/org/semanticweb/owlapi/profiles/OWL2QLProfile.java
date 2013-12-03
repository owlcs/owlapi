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
package org.semanticweb.owlapi.profiles;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
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
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorExAdapter;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

/** Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Jul-2009 */
@SuppressWarnings("javadoc")
public class OWL2QLProfile implements OWLProfile {
    final Set<IRI> allowedDatatypes = new HashSet<IRI>();

    public OWL2QLProfile() {
        allowedDatatypes.add(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDF_XML_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_REAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.OWL_RATIONAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DECIMAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NORMALIZED_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_TOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NCNAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NMTOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_HEX_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_BASE_64_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_ANY_URI.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME_STAMP.getIRI());
    }

    @Override
    public String getName() {
        return "OWL 2 QL";
    }

    @Override
    public IRI getIRI() {
        return OWL2_QL;
    }

    @Override
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
        violations.addAll(report.getViolations());
        OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
        OWL2QLObjectVisitor visitor = new OWL2QLObjectVisitor(walker);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }

    private class OWL2QLObjectVisitor extends OWLOntologyWalkerVisitor {
        private final Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();

        OWL2QLObjectVisitor(OWLOntologyWalker walker) {
            super(walker);
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<OWLProfileViolation>(profileViolations);
        }

        @Override
        public void visit(OWLDatatype node) {
            if (!allowedDatatypes.contains(node.getIRI())) {
                profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(),
                        getCurrentAxiom(), node));
            }
        }

        @Override
        public void visit(OWLAnonymousIndividual individual) {
            profileViolations.add(new UseOfAnonymousIndividual(getCurrentOntology(),
                    getCurrentAxiom(), individual));
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLSubClassOfAxiom axiom) {
            if (!isOWL2QLSubClassExpression(axiom.getSubClass())) {
                profileViolations.add(new UseOfNonSubClassExpression(
                        getCurrentOntology(), axiom, axiom.getSubClass()));
            }
            if (!isOWL2QLSuperClassExpression(axiom.getSuperClass())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getSuperClass()));
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            for (OWLClassExpression ce : axiom.getClassExpressions()) {
                if (!isOWL2QLSubClassExpression(ce)) {
                    profileViolations.add(new UseOfNonSubClassExpression(
                            getCurrentOntology(), axiom, ce));
                }
            }
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            for (OWLClassExpression ce : axiom.getClassExpressions()) {
                if (!isOWL2QLSubClassExpression(ce)) {
                    profileViolations.add(new UseOfNonSubClassExpression(
                            getCurrentOntology(), axiom, ce));
                }
            }
        }

        @Override
        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getDomain()));
            }
        }

        @Override
        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getRange())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getRange()));
            }
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLDataPropertyDomainAxiom axiom) {
            if (!isOWL2QLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(
                        getCurrentOntology(), axiom, axiom.getDomain()));
            }
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            if (axiom.getClassExpression().isAnonymous()) {
                profileViolations.add(new UseOfNonAtomicClassExpression(
                        getCurrentOntology(), axiom, axiom.getClassExpression()));
            }
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
        }

        @Override
        public void visit(OWLDataComplementOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(),
                    getCurrentAxiom(), node));
        }

        @Override
        public void visit(OWLDataOneOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(),
                    getCurrentAxiom(), node));
        }

        @Override
        public void visit(OWLDatatypeRestriction node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(),
                    getCurrentAxiom(), node));
        }

        @Override
        public void visit(OWLDataUnionOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(),
                    getCurrentAxiom(), node));
        }
    }

    private static class OWL2QLSubClassExpressionChecker extends
            OWLClassExpressionVisitorExAdapter<Boolean> {
        public OWL2QLSubClassExpressionChecker() {}

        @Override
        protected Boolean handleDefault(OWLClassExpression c) {
            return Boolean.FALSE;
        }

        @Override
        public Boolean visit(OWLClass desc) {
            return Boolean.TRUE;
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return Boolean.valueOf(desc.getFiller().isOWLThing());
        }

        @Override
        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return Boolean.TRUE;
        }
    }

    private final OWL2QLSubClassExpressionChecker subClassExpressionChecker = new OWL2QLSubClassExpressionChecker();

    protected boolean isOWL2QLSubClassExpression(OWLClassExpression ce) {
        return ce.accept(subClassExpressionChecker).booleanValue();
    }

    private class OWL2QLSuperClassExpressionChecker extends
            OWLClassExpressionVisitorExAdapter<Boolean> {
        public OWL2QLSuperClassExpressionChecker() {}

        @Override
        protected Boolean handleDefault(OWLClassExpression c) {
            return Boolean.FALSE;
        }

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
        public Boolean visit(OWLObjectComplementOf desc) {
            return Boolean.valueOf(isOWL2QLSubClassExpression(desc.getOperand()));
        }

        @Override
        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return Boolean.valueOf(!desc.getFiller().isAnonymous());
        }

        @Override
        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return Boolean.TRUE;
        }
    }

    private final OWL2QLSuperClassExpressionChecker superClassExpressionChecker = new OWL2QLSuperClassExpressionChecker();

    public boolean isOWL2QLSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassExpressionChecker).booleanValue();
    }
}
