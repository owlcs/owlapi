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

import static org.semanticweb.owlapi.model.Imports.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.profiles.violations.CycleInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.DatatypeIRIAlsoUsedAsClassIRI;
import org.semanticweb.owlapi.profiles.violations.EmptyOneOfAxiom;
import org.semanticweb.owlapi.profiles.violations.IllegalPunning;
import org.semanticweb.owlapi.profiles.violations.InsufficientIndividuals;
import org.semanticweb.owlapi.profiles.violations.InsufficientOperands;
import org.semanticweb.owlapi.profiles.violations.InsufficientPropertyExpressions;
import org.semanticweb.owlapi.profiles.violations.UseOfBuiltInDatatypeInDatatypeDefinition;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInCardinalityRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInDisjointPropertiesAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInFunctionalPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInIrreflexivePropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfNonSimplePropertyInObjectHasSelf;
import org.semanticweb.owlapi.profiles.violations.UseOfPropertyInChainCausesCycle;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForAnnotationPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForClassIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForDataPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForIndividualIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForObjectPropertyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForOntologyIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfReservedVocabularyForVersionIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredAnnotationProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredClass;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDataProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredObjectProperty;
import org.semanticweb.owlapi.profiles.violations.UseOfUnknownDatatype;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitorEx;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 */
public class OWL2DLProfile implements OWLProfile {

    /**
     * Gets the name of the profile.
     * 
     * @return A string that represents the name of the profile
     */
    @Override
    public String getName() {
        return "OWL 2 DL";
    }

    @Override
    public IRI getIRI() {
        return Profiles.OWL2_DL.getIRI();
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
        OWL2Profile owl2Profile = new OWL2Profile();
        OWLProfileReport report = owl2Profile.checkOntology(ontology);
        Set<OWLProfileViolation<?>> violations = new LinkedHashSet<OWLProfileViolation<?>>();
        if (!report.isInProfile()) {
            // We won't be in the OWL 2 DL Profile then!
            violations.addAll(report.getViolations());
        }
        OWLOntologyProfileWalker walker = new OWLOntologyProfileWalker(
                ontology.getImportsClosure());
        OWL2DLProfileObjectVisitor visitor = new OWL2DLProfileObjectVisitor(
                walker, ontology.getOWLOntologyManager());
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }

    private static class OWL2DLProfileObjectVisitor extends
            OWLOntologyWalkerVisitorEx<Object> {

        private OWLObjectPropertyManager objectPropertyManager = null;
        private final OWLOntologyManager manager;
        private final Set<OWLProfileViolation<?>> profileViolations = new HashSet<OWLProfileViolation<?>>();

        OWL2DLProfileObjectVisitor(OWLOntologyWalker walker,
                OWLOntologyManager manager) {
            super(walker);
            this.manager = manager;
        }

        public Set<OWLProfileViolation<?>> getProfileViolations() {
            return new HashSet<OWLProfileViolation<?>>(profileViolations);
        }

        private OWLObjectPropertyManager getPropertyManager() {
            if (objectPropertyManager == null) {
                objectPropertyManager = new OWLObjectPropertyManager(manager,
                        getCurrentOntology());
            }
            return objectPropertyManager;
        }

        @Override
        public Object visit(OWLDataOneOf node) {
            if (node.getValues().isEmpty()) {
                profileViolations.add(new EmptyOneOfAxiom(getCurrentOntology(),
                        getCurrentAxiom()));
            }
            return null;
        }

        @Override
        public Object visit(OWLDataUnionOf node) {
            if (node.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLDataIntersectionOf node) {
            if (node.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectIntersectionOf node) {
            if (node.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectOneOf node) {
            if (node.getIndividuals().isEmpty()) {
                profileViolations.add(new EmptyOneOfAxiom(getCurrentOntology(),
                        getCurrentAxiom()));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectUnionOf node) {
            if (node.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLEquivalentClassesAxiom node) {
            if (node.getClassExpressions().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), node, node));
            }
            return null;
        }

        @Override
        public Object visit(OWLDisjointClassesAxiom node) {
            if (node.getClassExpressions().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), node, node));
            }
            return null;
        }

        @Override
        public Object visit(OWLDisjointUnionAxiom node) {
            if (node.getClassExpressions().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), node, node));
            }
            return null;
        }

        @Override
        public Object visit(OWLEquivalentObjectPropertiesAxiom node) {
            if (node.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLDisjointDataPropertiesAxiom node) {
            if (node.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLEquivalentDataPropertiesAxiom node) {
            if (node.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLHasKeyAxiom node) {
            if (node.getPropertyExpressions().size() < 1) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLSameIndividualAxiom node) {
            if (node.getIndividuals().size() < 2) {
                profileViolations.add(new InsufficientIndividuals(
                        getCurrentOntology(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLDifferentIndividualsAxiom node) {
            if (node.getIndividuals().size() < 2) {
                profileViolations.add(new InsufficientIndividuals(
                        getCurrentOntology(), node));
            }
            return null;
        }

        @Override
        public Object visit(OWLOntology ontology) {
            OWLOntologyID ontologyID = ontology.getOntologyID();
            if (!ontologyID.isAnonymous()) {
                if (ontologyID.getOntologyIRI().isReservedVocabulary()) {
                    profileViolations
                            .add(new UseOfReservedVocabularyForOntologyIRI(
                                    getCurrentOntology()));
                }
                IRI versionIRI = ontologyID.getVersionIRI();
                if (versionIRI != null) {
                    if (versionIRI.isReservedVocabulary()) {
                        profileViolations
                                .add(new UseOfReservedVocabularyForVersionIRI(
                                        getCurrentOntology()));
                    }
                }
            }
            objectPropertyManager = null;
            return super.visit(ontology);
        }

        @Override
        public Object visit(OWLClass desc) {
            if (!desc.isBuiltIn()) {
                if (desc.getIRI().isReservedVocabulary()) {
                    profileViolations
                            .add(new UseOfReservedVocabularyForClassIRI(
                                    getCurrentOntology(), getCurrentAxiom(),
                                    desc));
                }
            }
            if (!desc.isBuiltIn()
                    && !getCurrentOntology().isDeclared(desc, INCLUDED)) {
                profileViolations.add(new UseOfUndeclaredClass(
                        getCurrentOntology(), getCurrentAxiom(), desc));
            }
            if (getCurrentOntology().containsDatatypeInSignature(desc.getIRI(),
                    EXCLUDED)) {
                profileViolations
                        .add(new DatatypeIRIAlsoUsedAsClassIRI(
                                getCurrentOntology(), getCurrentAxiom(), desc
                                        .getIRI()));
            }
            return null;
        }

        @Override
        public Object visit(OWLDatatype datatype) {
            // Each datatype MUST statisfy the following:
            // An IRI used to identify a datatype MUST
            // - Identify a datatype in the OWL 2 datatype map (Section 4.1
            // lists them), or
            // - Have the xsd: prefix, or
            // - Be rdfs:Literal, or
            // - Not be in the reserved vocabulary of OWL 2
            if (!OWL2Datatype.isBuiltIn(datatype.getIRI())) {
                if (!datatype.getIRI().getNamespace()
                        .equals(Namespaces.XSD.toString())) {
                    if (!datatype.isTopDatatype()) {
                        if (datatype.getIRI().isReservedVocabulary()) {
                            profileViolations.add(new UseOfUnknownDatatype(
                                    getCurrentOntology(), getCurrentAxiom(),
                                    datatype));
                        }
                    }
                }
                // We also have to declare datatypes that are not built in
                // bug?
                // if (!datatype.isTopDatatype() && datatype.isBuiltIn()
                // && getCurrentOntology().isDeclared(datatype, true)) {
                if (!datatype.isTopDatatype() && !datatype.isBuiltIn()
                        && !getCurrentOntology().isDeclared(datatype, INCLUDED)) {
                    profileViolations.add(new UseOfUndeclaredDatatype(
                            getCurrentOntology(), getCurrentAxiom(), datatype));
                }
            }
            if (getCurrentOntology().containsClassInSignature(
                    datatype.getIRI(), INCLUDED)) {
                profileViolations.add(new DatatypeIRIAlsoUsedAsClassIRI(
                        getCurrentOntology(), getCurrentAxiom(), datatype
                                .getIRI()));
            }
            return null;
        }

        @Override
        public Object visit(OWLDatatypeDefinitionAxiom axiom) {
            if (axiom.getDatatype().getIRI().isReservedVocabulary()) {
                profileViolations
                        .add(new UseOfBuiltInDatatypeInDatatypeDefinition(
                                getCurrentOntology(), axiom));
            }
            // Check for cycles
            Set<OWLDatatype> datatypes = new HashSet<OWLDatatype>();
            Set<OWLAxiom> axioms = new LinkedHashSet<OWLAxiom>();
            axioms.add(axiom);
            getDatatypesInSignature(datatypes, axiom.getDataRange(), axioms);
            if (datatypes.contains(axiom.getDatatype())) {
                profileViolations.add(new CycleInDatatypeDefinition(
                        getCurrentOntology(), axiom));
            }
            return null;
        }

        private void getDatatypesInSignature(Set<OWLDatatype> datatypes,
                OWLObject obj, Set<OWLAxiom> axioms) {
            for (OWLDatatype dt : obj.getDatatypesInSignature()) {
                if (datatypes.add(dt)) {
                    for (OWLOntology ont : getCurrentOntology()
                            .getImportsClosure()) {
                        for (OWLDatatypeDefinitionAxiom ax : ont
                                .getDatatypeDefinitions(dt)) {
                            axioms.add(ax);
                            getDatatypesInSignature(datatypes,
                                    ax.getDataRange(), axioms);
                        }
                    }
                }
            }
        }

        @Override
        public Object visit(OWLObjectProperty property) {
            if (!property.isOWLTopObjectProperty()
                    && !property.isOWLBottomObjectProperty()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations
                            .add(new UseOfReservedVocabularyForObjectPropertyIRI(
                                    getCurrentOntology(), getCurrentAxiom(),
                                    property));
                }
            }
            if (!property.isBuiltIn()
                    && !getCurrentOntology().isDeclared(property, INCLUDED)) {
                profileViolations.add(new UseOfUndeclaredObjectProperty(
                        getCurrentOntology(), getCurrentAxiom(), property));
            }
            if (getCurrentOntology().containsDataPropertyInSignature(
                    property.getIRI(), INCLUDED)) {
                profileViolations.add(new IllegalPunning(getCurrentOntology(),
                        getCurrentAxiom(), property.getIRI()));
            }
            if (getCurrentOntology().containsAnnotationPropertyInSignature(
                    property.getIRI(), INCLUDED)) {
                profileViolations.add(new IllegalPunning(getCurrentOntology(),
                        getCurrentAxiom(), property.getIRI()));
            }
            return null;
        }

        @Override
        public Object visit(OWLDataProperty property) {
            if (!property.isOWLTopDataProperty()
                    && !property.isOWLBottomDataProperty()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations
                            .add(new UseOfReservedVocabularyForDataPropertyIRI(
                                    getCurrentOntology(), getCurrentAxiom(),
                                    property));
                }
            }
            if (!property.isBuiltIn()
                    && !getCurrentOntology().isDeclared(property, INCLUDED)) {
                profileViolations.add(new UseOfUndeclaredDataProperty(
                        getCurrentOntology(), getCurrentAxiom(), property));
            }
            if (getCurrentOntology().containsObjectPropertyInSignature(
                    property.getIRI(), INCLUDED)) {
                profileViolations.add(new IllegalPunning(getCurrentOntology(),
                        getCurrentAxiom(), property.getIRI()));
            }
            if (getCurrentOntology().containsAnnotationPropertyInSignature(
                    property.getIRI(), INCLUDED)) {
                profileViolations.add(new IllegalPunning(getCurrentOntology(),
                        getCurrentAxiom(), property.getIRI()));
            }
            return null;
        }

        @Override
        public Object visit(OWLAnnotationProperty property) {
            if (!property.isBuiltIn()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations
                            .add(new UseOfReservedVocabularyForAnnotationPropertyIRI(
                                    getCurrentOntology(), getCurrentAxiom(),
                                    property));
                }
            }
            if (!property.isBuiltIn()
                    && !getCurrentOntology().isDeclared(property, INCLUDED)) {
                profileViolations.add(new UseOfUndeclaredAnnotationProperty(
                        getCurrentOntology(), getCurrentAxiom(),
                        getCurrentAnnotation(), property));
            }
            if (getCurrentOntology().containsObjectPropertyInSignature(
                    property.getIRI(), INCLUDED)) {
                profileViolations.add(new IllegalPunning(getCurrentOntology(),
                        getCurrentAxiom(), property.getIRI()));
            }
            if (getCurrentOntology().containsDataPropertyInSignature(
                    property.getIRI(), INCLUDED)) {
                profileViolations.add(new IllegalPunning(getCurrentOntology(),
                        getCurrentAxiom(), property.getIRI()));
            }
            return null;
        }

        @Override
        public Object visit(OWLNamedIndividual individual) {
            if (!individual.isAnonymous()
                    && individual.getIRI().isReservedVocabulary()) {
                profileViolations
                        .add(new UseOfReservedVocabularyForIndividualIRI(
                                getCurrentOntology(), getCurrentAxiom(),
                                individual));
            }
            return null;
        }

        @Override
        public Object visit(OWLSubDataPropertyOfAxiom axiom) {
            if (axiom.getSubProperty().isOWLTopDataProperty()) {
                profileViolations
                        .add(new UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectMinCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInCardinalityRestriction(
                                getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectMaxCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInCardinalityRestriction(
                                getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectExactCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInCardinalityRestriction(
                                getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
        public Object visit(OWLObjectHasSelf desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInObjectHasSelf(
                                getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
        public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInFunctionalPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
        public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
        public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInIrreflexivePropertyAxiom(
                                getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
        public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
        public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
            if (axiom.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), axiom));
            }
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                if (getPropertyManager().isNonSimple(prop)) {
                    profileViolations
                            .add(new UseOfNonSimplePropertyInDisjointPropertiesAxiom(
                                    getCurrentOntology(), axiom, prop));
                }
            }
            return null;
        }

        @Override
        public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            // Restriction on the Property Hierarchy. A strict partial order
            // (i.e., an irreflexive and transitive relation) < on AllOPE(Ax)
            // exists that fulfills the following conditions:
            //
            // OP1 < OP2 if and only if INV(OP1) < OP2 for all object properties
            // OP1 and OP2 occurring in AllOPE(Ax).
            // If OPE1 < OPE2 holds, then OPE2 ->* OPE1 does not hold;
            // Each axiom in Ax of the form SubObjectPropertyOf(
            // ObjectPropertyChain( OPE1 ... OPEn ) OPE ) with n => 2 fulfills
            // the following conditions:
            // OPE is equal to owl:topObjectProperty, or [TOP]
            // n = 2 and OPE1 = OPE2 = OPE, or [TRANSITIVE_PROP]
            // OPEi < OPE for each 1 <= i <= n, or [ALL_SMALLER]
            // OPE1 = OPE and OPEi < OPE for each 2 <= i <= n, or [FIRST_EQUAL]
            // OPEn = OPE and OPEi < OPE for each 1 <= i <= n-1. [LAST_EQUAL]
            if (axiom.getPropertyChain().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), axiom));
            }
            OWLObjectPropertyExpression superProp = axiom.getSuperProperty();
            if (superProp.isOWLTopObjectProperty()
                    || axiom.isEncodingOfTransitiveProperty()) {
                // TOP or TRANSITIVE_PROP: no violation can occur
                return null;
            }
            List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain();
            final OWLObjectPropertyExpression first = chain.get(0);
            final OWLObjectPropertyExpression last = chain
                    .get(chain.size() - 1);
            // center part of the chain must be smaller in any case
            for (int i = 1; i < chain.size() - 1; i++) {
                if (getPropertyManager().isLessThan(superProp, chain.get(i))) {
                    profileViolations.add(new UseOfPropertyInChainCausesCycle(
                            getCurrentOntology(), axiom, chain.get(i)));
                }
            }
            if (first.equals(superProp)) {
                // first equals, last must be smaller
                if (getPropertyManager().isLessThan(superProp, last)) {
                    profileViolations.add(new UseOfPropertyInChainCausesCycle(
                            getCurrentOntology(), axiom, last));
                }
            } else {
                // first not equal, it must be smaller
                if (getPropertyManager().isLessThan(superProp, first)) {
                    profileViolations.add(new UseOfPropertyInChainCausesCycle(
                            getCurrentOntology(), axiom, first));
                }
            }
            if (last.equals(superProp)) {
                // last equals, first must be smaller
                if (getPropertyManager().isLessThan(superProp, first)) {
                    profileViolations.add(new UseOfPropertyInChainCausesCycle(
                            getCurrentOntology(), axiom, first));
                }
            } else {
                // last not equal, it must be smaller
                if (getPropertyManager().isLessThan(superProp, last)) {
                    profileViolations.add(new UseOfPropertyInChainCausesCycle(
                            getCurrentOntology(), axiom, last));
                }
            }
            // neither first and last equal: they both must be smaller, checked
            // already in the else branches
            return null;
        }
    }
}
