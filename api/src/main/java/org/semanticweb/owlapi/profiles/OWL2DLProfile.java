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

import static org.semanticweb.owlapi.model.parameters.Imports.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

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
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import com.google.common.base.Optional;

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

    @Nonnull
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
        Set<OWLProfileViolation> violations = new LinkedHashSet<>();
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
            OWLOntologyWalkerVisitor {

        private OWLObjectPropertyManager objectPropertyManager = null;
        @Nonnull
        private final OWLOntologyManager manager;
        @Nonnull
        private final Set<OWLProfileViolation> profileViolations = new HashSet<>();

        OWL2DLProfileObjectVisitor(@Nonnull OWLOntologyWalker walker,
                @Nonnull OWLOntologyManager manager) {
            super(walker);
            this.manager = manager;
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<>(profileViolations);
        }

        private OWLObjectPropertyManager getPropertyManager() {
            if (objectPropertyManager == null) {
                objectPropertyManager = new OWLObjectPropertyManager(manager,
                        getCurrentOntology());
            }
            return objectPropertyManager;
        }

        @Override
        public void visit(OWLDataOneOf node) {
            if (node.getValues().isEmpty()) {
                profileViolations.add(new EmptyOneOfAxiom(getCurrentOntology(),
                        getCurrentAxiom()));
            }
        }

        @Override
        public void visit(OWLDataUnionOf node) {
            if (node.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
        }

        @Override
        public void visit(OWLDataIntersectionOf node) {
            if (node.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), node));
            }
        }

        @Override
        public void visit(OWLObjectIntersectionOf ce) {
            if (ce.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), ce));
            }
        }

        @Override
        public void visit(OWLObjectOneOf ce) {
            if (ce.getIndividuals().isEmpty()) {
                profileViolations.add(new EmptyOneOfAxiom(getCurrentOntology(),
                        getCurrentAxiom()));
            }
        }

        @Override
        public void visit(OWLObjectUnionOf ce) {
            if (ce.getOperands().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), getCurrentAxiom(), ce));
            }
        }

        @Override
        public void visit(OWLEquivalentClassesAxiom axiom) {
            if (axiom.getClassExpressions().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), axiom, axiom));
            }
        }

        @Override
        public void visit(OWLDisjointClassesAxiom axiom) {
            if (axiom.getClassExpressions().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), axiom, axiom));
            }
        }

        @Override
        public void visit(OWLDisjointUnionAxiom axiom) {
            if (axiom.getClassExpressions().size() < 2) {
                profileViolations.add(new InsufficientOperands(
                        getCurrentOntology(), axiom, axiom));
            }
        }

        @Override
        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            if (axiom.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            if (axiom.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            if (axiom.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLHasKeyAxiom axiom) {
            if (axiom.getPropertyExpressions().size() < 1) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLSameIndividualAxiom axiom) {
            if (axiom.getIndividuals().size() < 2) {
                profileViolations.add(new InsufficientIndividuals(
                        getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLDifferentIndividualsAxiom axiom) {
            if (axiom.getIndividuals().size() < 2) {
                profileViolations.add(new InsufficientIndividuals(
                        getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLOntology ontology) {
            OWLOntologyID ontologyID = ontology.getOntologyID();
            if (!ontologyID.isAnonymous()) {
                if (ontologyID.getOntologyIRI().get().isReservedVocabulary()) {
                    profileViolations
                            .add(new UseOfReservedVocabularyForOntologyIRI(
                                    getCurrentOntology()));
                }
                Optional<IRI> versionIRI = ontologyID.getVersionIRI();
                if (versionIRI.isPresent()) {
                    if (versionIRI.get().isReservedVocabulary()) {
                        profileViolations
                                .add(new UseOfReservedVocabularyForVersionIRI(
                                        getCurrentOntology()));
                    }
                }
            }
            objectPropertyManager = null;
        }

        @Override
        public void visit(OWLClass ce) {
            if (!ce.isBuiltIn()) {
                if (ce.getIRI().isReservedVocabulary()) {
                    profileViolations
                            .add(new UseOfReservedVocabularyForClassIRI(
                                    getCurrentOntology(), getCurrentAxiom(), ce));
                }
            }
            if (!ce.isBuiltIn()
                    && !getCurrentOntology().isDeclared(ce, INCLUDED)) {
                profileViolations.add(new UseOfUndeclaredClass(
                        getCurrentOntology(), getCurrentAxiom(), ce));
            }
            if (getCurrentOntology().containsDatatypeInSignature(ce.getIRI(),
                    EXCLUDED)) {
                profileViolations.add(new DatatypeIRIAlsoUsedAsClassIRI(
                        getCurrentOntology(), getCurrentAxiom(), ce.getIRI()));
            }
        }

        @Override
        public void visit(OWLDatatype node) {
            // Each datatype MUST statisfy the following:
            // An IRI used to identify a datatype MUST
            // - Identify a datatype in the OWL 2 datatype map (Section 4.1
            // lists them), or
            // - Have the xsd: prefix, or
            // - Be rdfs:Literal, or
            // - Not be in the reserved vocabulary of OWL 2
            if (!OWL2Datatype.isBuiltIn(node.getIRI())) {
                if (!node.getIRI().getNamespace()
                        .equals(Namespaces.XSD.toString())) {
                    if (!node.isTopDatatype()) {
                        if (node.getIRI().isReservedVocabulary()) {
                            profileViolations.add(new UseOfUnknownDatatype(
                                    getCurrentOntology(), getCurrentAxiom(),
                                    node));
                        }
                    }
                }
                // We also have to declare datatypes that are not built in
                // bug?
                // if (!datatype.isTopDatatype() && datatype.isBuiltIn()
                // && getCurrentOntology().isDeclared(datatype, true)) {
                if (!node.isTopDatatype() && !node.isBuiltIn()
                        && !getCurrentOntology().isDeclared(node, INCLUDED)) {
                    profileViolations.add(new UseOfUndeclaredDatatype(
                            getCurrentOntology(), getCurrentAxiom(), node));
                }
            }
            if (getCurrentOntology().containsClassInSignature(node.getIRI(),
                    INCLUDED)) {
                profileViolations
                        .add(new DatatypeIRIAlsoUsedAsClassIRI(
                                getCurrentOntology(), getCurrentAxiom(), node
                                        .getIRI()));
            }
        }

        @Override
        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            if (axiom.getDatatype().getIRI().isReservedVocabulary()) {
                profileViolations
                        .add(new UseOfBuiltInDatatypeInDatatypeDefinition(
                                getCurrentOntology(), axiom));
            }
            // Check for cycles
            Set<OWLDatatype> datatypes = new HashSet<>();
            Set<OWLAxiom> axioms = new LinkedHashSet<>();
            axioms.add(axiom);
            getDatatypesInSignature(datatypes, axiom.getDataRange(), axioms);
            if (datatypes.contains(axiom.getDatatype())) {
                profileViolations.add(new CycleInDatatypeDefinition(
                        getCurrentOntology(), axiom));
            }
        }

        private void getDatatypesInSignature(Set<OWLDatatype> datatypes,
                OWLObject obj, Set<OWLAxiom> axioms) {
            for (OWLDatatype dt : obj.getDatatypesInSignature()) {
                assert dt != null;
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
        public void visit(OWLObjectProperty property) {
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
        }

        @Override
        public void visit(OWLDataProperty property) {
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
        }

        @Override
        public void visit(OWLAnnotationProperty property) {
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
        }

        @Override
        public void visit(OWLNamedIndividual individual) {
            if (!individual.isAnonymous()
                    && individual.getIRI().isReservedVocabulary()) {
                profileViolations
                        .add(new UseOfReservedVocabularyForIndividualIRI(
                                getCurrentOntology(), getCurrentAxiom(),
                                individual));
            }
        }

        @Override
        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            if (axiom.getSubProperty().isOWLTopDataProperty()) {
                profileViolations
                        .add(new UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLObjectMinCardinality ce) {
            if (getPropertyManager().isNonSimple(ce.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInCardinalityRestriction(
                                getCurrentOntology(), getCurrentAxiom(), ce));
            }
        }

        @Override
        public void visit(OWLObjectMaxCardinality ce) {
            if (getPropertyManager().isNonSimple(ce.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInCardinalityRestriction(
                                getCurrentOntology(), getCurrentAxiom(), ce));
            }
        }

        @Override
        public void visit(OWLObjectExactCardinality ce) {
            if (getPropertyManager().isNonSimple(ce.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInCardinalityRestriction(
                                getCurrentOntology(), getCurrentAxiom(), ce));
            }
        }

        @Override
        public void visit(OWLObjectHasSelf ce) {
            if (getPropertyManager().isNonSimple(ce.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInObjectHasSelf(
                                getCurrentOntology(), getCurrentAxiom(), ce));
            }
        }

        @Override
        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInFunctionalPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInIrreflexivePropertyAxiom(
                                getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations
                        .add(new UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom(
                                getCurrentOntology(), axiom));
            }
        }

        @Override
        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            if (axiom.getProperties().size() < 2) {
                profileViolations.add(new InsufficientPropertyExpressions(
                        getCurrentOntology(), axiom));
            }
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                assert prop != null;
                if (getPropertyManager().isNonSimple(prop)) {
                    profileViolations
                            .add(new UseOfNonSimplePropertyInDisjointPropertiesAxiom(
                                    getCurrentOntology(), axiom, prop));
                }
            }
        }

        @Override
        public void visit(OWLSubPropertyChainOfAxiom axiom) {
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
                return;
            }
            List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain();
            OWLObjectPropertyExpression first = chain.get(0);
            OWLObjectPropertyExpression last = chain.get(chain.size() - 1);
            assert last != null;
            // center part of the chain must be smaller in any case
            for (int i = 1; i < chain.size() - 1; i++) {
                OWLObjectPropertyExpression propB = chain.get(i);
                assert propB != null;
                if (getPropertyManager().isLessThan(superProp, propB)) {
                    profileViolations.add(new UseOfPropertyInChainCausesCycle(
                            getCurrentOntology(), axiom, propB));
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
        }
    }
}
