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

import static org.semanticweb.owlapi.model.Imports.INCLUDED;
import static org.semanticweb.owlapi.vocab.OWL2Datatype.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
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
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
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
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitorEx;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 */
public class OWL2ELProfile implements OWLProfile {

    protected final static Set<IRI> allowedDatatypes = new HashSet<IRI>(
            Arrays.asList(RDF_PLAIN_LITERAL.getIRI(), RDF_XML_LITERAL.getIRI(),
                    RDFS_LITERAL.getIRI(), OWL_RATIONAL.getIRI(),
                    OWL_REAL.getIRI(), XSD_DECIMAL.getIRI(),
                    XSD_DECIMAL.getIRI(), XSD_INTEGER.getIRI(),
                    XSD_NON_NEGATIVE_INTEGER.getIRI(), XSD_STRING.getIRI(),
                    XSD_NORMALIZED_STRING.getIRI(), XSD_TOKEN.getIRI(),
                    XSD_NAME.getIRI(), XSD_NCNAME.getIRI(),
                    XSD_NMTOKEN.getIRI(), XSD_HEX_BINARY.getIRI(),
                    XSD_BASE_64_BINARY.getIRI(), XSD_ANY_URI.getIRI(),
                    XSD_DATE_TIME.getIRI(), XSD_DATE_TIME_STAMP.getIRI()));

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
        Set<OWLProfileViolation<?>> violations = new HashSet<OWLProfileViolation<?>>();
        violations.addAll(report.getViolations());
        OWLOntologyProfileWalker ontologyWalker = new OWLOntologyProfileWalker(
                ontology.getImportsClosure());
        OWL2ELProfileObjectVisitor visitor = new OWL2ELProfileObjectVisitor(
                ontologyWalker, ontology.getOWLOntologyManager());
        ontologyWalker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }

    protected class OWL2ELProfileObjectVisitor extends
            OWLOntologyWalkerVisitorEx<Object> {

        private final OWLOntologyManager man;
        private OWLObjectPropertyManager propertyManager;
        private final Set<OWLProfileViolation<?>> profileViolations = new HashSet<OWLProfileViolation<?>>();

        public OWL2ELProfileObjectVisitor(OWLOntologyWalker walker,
                OWLOntologyManager man) {
            super(walker);
            this.man = man;
        }

        public Set<OWLProfileViolation<?>> getProfileViolations() {
            return new HashSet<OWLProfileViolation<?>>(profileViolations);
        }

        private OWLObjectPropertyManager getPropertyManager() {
            if (propertyManager == null) {
                propertyManager = new OWLObjectPropertyManager(man,
                        getCurrentOntology());
            }
            return propertyManager;
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
        public Object visit(OWLObjectInverseOf property) {
            profileViolations.add(new UseOfObjectPropertyInverse(
                    getCurrentOntology(), getCurrentAxiom(), property));
            return null;
        }

        @Override
        public Object visit(OWLDataAllValuesFrom desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLDataExactCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLDataMaxCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLDataMinCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLObjectAllValuesFrom desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLObjectComplementOf desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLObjectExactCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLObjectMaxCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLObjectMinCardinality desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
            return null;
        }

        @Override
        public Object visit(OWLObjectOneOf desc) {
            if (desc.getIndividuals().size() != 1) {
                profileViolations
                        .add(new UseOfObjectOneOfWithMultipleIndividuals(
                                getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectUnionOf desc) {
            profileViolations.add(new UseOfIllegalClassExpression(
                    getCurrentOntology(), getCurrentAxiom(), desc));
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
            if (node.getValues().size() != 1) {
                profileViolations.add(new UseOfDataOneOfWithMultipleLiterals(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
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

        @Override
        public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLClassAssertionAxiom axiom) {
            return axiom.getClassExpression().accept(this);
        }

        @Override
        public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(OWLDisjointClassesAxiom axiom) {
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
        public Object visit(OWLInverseObjectPropertiesAxiom axiom) {
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
        public Object visit(OWLSymmetricObjectPropertyAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    axiom));
            return null;
        }

        @Override
        public Object visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(),
                    rule));
            return super.visit(rule);
        }

        @Override
        public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            Set<OWLObjectPropertyRangeAxiom> rangeAxioms = getCurrentOntology()
                    .getAxioms(AxiomType.OBJECT_PROPERTY_RANGE, INCLUDED);
            if (rangeAxioms.isEmpty()) {
                return Boolean.FALSE;
            }
            // Do we have a range restriction imposed on our super property?
            for (OWLObjectPropertyRangeAxiom rngAx : rangeAxioms) {
                if (getPropertyManager().isSubPropertyOf(
                        axiom.getSuperProperty(), rngAx.getProperty())) {
                    // Imposed range restriction!
                    OWLClassExpression imposedRange = rngAx.getRange();
                    // There must be an axiom that imposes a range on the last
                    // prop in the chain
                    List<OWLObjectPropertyExpression> chain = axiom
                            .getPropertyChain();
                    if (!chain.isEmpty()) {
                        OWLObjectPropertyExpression lastProperty = chain
                                .get(chain.size() - 1);
                        boolean rngPresent = false;
                        for (OWLOntology ont : getCurrentOntology()
                                .getImportsClosure()) {
                            for (OWLObjectPropertyRangeAxiom lastPropRngAx : ont
                                    .getObjectPropertyRangeAxioms(lastProperty)) {
                                if (lastPropRngAx.getRange().equals(
                                        imposedRange)) {
                                    // We're o.k.
                                    rngPresent = true;
                                    break;
                                }
                            }
                        }
                        if (!rngPresent) {
                            profileViolations
                                    .add(new LastPropertyInChainNotInImposedRange(
                                            getCurrentOntology(), axiom, rngAx));
                        }
                    }
                }
            }
            return null;
        }

        @Override
        public Object visit(OWLOntology ontology) {
            propertyManager = null;
            return null;
        }
    }
}
