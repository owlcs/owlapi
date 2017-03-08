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

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.profiles.violations.LastPropertyInChainNotInImposedRange;
import org.semanticweb.owlapi.profiles.violations.UseOfAnonymousIndividual;
import org.semanticweb.owlapi.profiles.violations.UseOfDataOneOfWithMultipleLiterals;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalClassExpression;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalDataRange;
import org.semanticweb.owlapi.profiles.violations.UseOfObjectOneOfWithMultipleIndividuals;
import org.semanticweb.owlapi.profiles.violations.UseOfObjectPropertyInverse;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 */
public class OWL2ELProfile implements OWLProfile {

    protected static final Set<IRI> ALLOWED_DATATYPES =
        asUnorderedSet(OWL2Datatype.EL_DATATYPES.stream().map(i -> i.getIRI()));

    @Override
    public String getName() {
        return "OWL 2 EL";
    }

    @Override
    public IRI getIRI() {
        return Profiles.OWL2_EL.getIRI();
    }

    @Override
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<>();
        violations.addAll(report.getViolations());
        OWLOntologyProfileWalker ontologyWalker =
            new OWLOntologyProfileWalker(ontology.importsClosure());
        OWL2ELProfileObjectVisitor visitor = new OWL2ELProfileObjectVisitor(ontologyWalker);
        ontologyWalker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }

    protected class OWL2ELProfileObjectVisitor extends OWLOntologyWalkerVisitor {

        private final Set<OWLProfileViolation> profileViolations = new HashSet<>();
        @Nullable
        private OWLObjectPropertyManager propertyManager;

        public OWL2ELProfileObjectVisitor(OWLOntologyWalker walker) {
            super(walker);
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<>(profileViolations);
        }

        private OWLObjectPropertyManager getPropertyManager() {
            if (propertyManager == null) {
                propertyManager = new OWLObjectPropertyManager(getCurrentOntology());
            }
            return verifyNotNull(propertyManager);
        }

        @Override
        public void visit(OWLDatatype node) {
            if (!ALLOWED_DATATYPES.contains(node.getIRI())) {
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
        public void visit(OWLObjectInverseOf property) {
            profileViolations.add(new UseOfObjectPropertyInverse(getCurrentOntology(),
                getCurrentAxiom(), property));
        }

        @Override
        public void visit(OWLDataAllValuesFrom ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLDataExactCardinality ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLDataMaxCardinality ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLDataMinCardinality ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLObjectAllValuesFrom ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLObjectComplementOf ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLObjectExactCardinality ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLObjectMaxCardinality ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLObjectMinCardinality ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLObjectOneOf ce) {
            if (ce.individuals().count() != 1) {
                profileViolations.add(new UseOfObjectOneOfWithMultipleIndividuals(
                    getCurrentOntology(), getCurrentAxiom(), ce));
            }
        }

        @Override
        public void visit(OWLObjectUnionOf ce) {
            profileViolations.add(new UseOfIllegalClassExpression(getCurrentOntology(),
                getCurrentAxiom(), ce));
        }

        @Override
        public void visit(OWLDataComplementOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(),
                node));
        }

        @Override
        public void visit(OWLDataOneOf node) {
            if (node.values().count() != 1) {
                profileViolations.add(new UseOfDataOneOfWithMultipleLiterals(getCurrentOntology(),
                    getCurrentAxiom(), node));
            }
        }

        @Override
        public void visit(OWLDatatypeRestriction node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(),
                node));
        }

        @Override
        public void visit(OWLDataUnionOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(),
                node));
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLClassAssertionAxiom axiom) {
            axiom.getClassExpression().accept(this);
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
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
        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
        }

        @Override
        public void visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            // Do we have a range restriction imposed on our super property?
            getCurrentOntology().axioms(AxiomType.OBJECT_PROPERTY_RANGE, INCLUDED)
                .forEach(rngAx -> {
                    if (getPropertyManager().isSubPropertyOf(axiom.getSuperProperty(),
                        rngAx.getProperty())) {
                        // Imposed range restriction!
                        OWLClassExpression imposedRange = rngAx.getRange();
                        // There must be an axiom that imposes a
                        // range on the last
                        // prop in the chain
                        List<OWLObjectPropertyExpression> chain =
                            axiom.getPropertyChain();
                        if (!chain.isEmpty()) {
                            OWLObjectPropertyExpression lastProperty =
                                chain.get(chain.size() - 1);
                            boolean rngPresent =
                                rangePresent(imposedRange, lastProperty);
                            if (!rngPresent) {
                                profileViolations.add(
                                    new LastPropertyInChainNotInImposedRange(
                                        getCurrentOntology(),
                                        axiom, rngAx));
                            }
                        }
                    }
                });
        }

        protected boolean rangePresent(OWLClassExpression imposedRange,
            OWLObjectPropertyExpression lastProperty) {
            return getCurrentOntology().importsClosure()
                .flatMap(o -> o.objectPropertyRangeAxioms(lastProperty))
                .anyMatch(l -> l.getRange().equals(imposedRange));
        }

        @Override
        public void visit(OWLOntology ontology) {
            propertyManager = null;
        }
    }
}
