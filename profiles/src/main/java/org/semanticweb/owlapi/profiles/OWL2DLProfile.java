package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.*;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Aug-2009
 */
public class OWL2DLProfile implements OWLProfile {

    /**
     * Gets the name of the profile.
     * @return A string that represents the name of the profile
     */
    public String getName() {
        return "OWL 2 DL";
    }

    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @param manager  A manager which can be used to obtain the imports closure
     *                 of the ontology
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     *         ontology is within this profile.
     */
    public OWLProfileReport checkOntology(OWLOntology ontology, OWLOntologyManager manager) {
        OWL2Profile owl2Profile = new OWL2Profile();
        OWLProfileReport report = owl2Profile.checkOntology(ontology, manager);
        Set<OWLProfileViolation> violations = new LinkedHashSet<OWLProfileViolation>();
        if(!report.isInProfile()) {
            //We won't be in the OWL 2 DL Profile then!
            violations.addAll(report.getViolations());
        }
        OWLOntologyWalker walker = new OWLOntologyWalker(manager.getImportsClosure(ontology));
        OWL2DLProfileObjectVisitor visitor = new OWL2DLProfileObjectVisitor(walker, manager);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }


    private class OWL2DLProfileObjectVisitor extends OWLOntologyWalkerVisitor {

        private OWLObjectPropertyManager objectPropertyManager = null;

        private OWLOntologyManager manager;

        private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();

        private OWL2DLProfileObjectVisitor(OWLOntologyWalker walker, OWLOntologyManager manager) {
            super(walker);
            this.manager = manager;
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<OWLProfileViolation>(profileViolations);
        }

        private OWLObjectPropertyManager getPropertyManager() {
            if (objectPropertyManager == null) {
                objectPropertyManager = new OWLObjectPropertyManager(manager, getCurrentOntology());
            }
            return objectPropertyManager;
        }

        public Object visit(OWLOntology ontology) {
            OWLOntologyID ontologyID = ontology.getOntologyID();
            if (!ontologyID.isAnonymous()) {
                if (ontologyID.getOntologyIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForOntologyIRI(getCurrentOntology()));
                }
                IRI versionIRI = ontologyID.getVersionIRI();
                if (versionIRI != null) {
                    if (versionIRI.isReservedVocabulary()) {
                        profileViolations.add(new UseOfReservedVocabularyForVersionIRI(getCurrentOntology()));
                    }
                }
            }
            objectPropertyManager = null;
            return super.visit(ontology);
        }

        public Object visit(OWLClass desc) {
            if (!desc.isBuiltIn()) {
                if (desc.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForClassIRI(getCurrentOntology(), getCurrentAxiom(), desc));
                }
            }
            if (!desc.isBuiltIn() && !getCurrentOntology().isDeclared(desc, true)) {
                profileViolations.add(new UseOfUndeclaredClass(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            if (getCurrentOntology().containsDatatypeReference(desc.getIRI())) {
                profileViolations.add(new UseOfDatatypeIRIForClassIRI(getCurrentOntology(), getCurrentAxiom(), desc.getIRI()));
            }
            return null;
        }

        public Object visit(OWLDatatype node) {
            if (!node.isBuiltIn()) {
                boolean defined = false;
                for (OWLOntology ont : getCurrentOntology().getOWLOntologyManager().getImportsClosure(getCurrentOntology())) {
                    if (ont.getDatatypeDefinitions(node).isEmpty()) {
                        defined = true;
                        break;
                    }
                }
//                if(!defined) {
//                    profileViolations.add(new UndeclaredDefinedDatatype(getCurrentOntology(), getCurrentAxiom(), node))
//                }
            }
            if (!node.isBuiltIn() && !getCurrentOntology().isDeclared(node, true)) {
                profileViolations.add(new UseOfUndeclaredDatatype(getCurrentOntology(), getCurrentAxiom(), node));

            }
            if (getCurrentOntology().containsClassReference(node.getIRI(), true)) {
                profileViolations.add(new UseOfDatatypeIRIForClassIRI(getCurrentOntology(), getCurrentAxiom(), node.getIRI()));
            }
            return null;
        }

        public Object visit(OWLDatatypeDefinitionAxiom axiom) {
            if (axiom.getDatatype().getIRI().isReservedVocabulary()) {
                profileViolations.add(new UseOfBuiltInDatatypeInDatatypeDefinition(getCurrentOntology(), axiom));
            }
            // Check for cycles
            Set<OWLDatatype> datatypes = new HashSet<OWLDatatype>();
            Set<OWLAxiom> axioms = new LinkedHashSet<OWLAxiom>();
            axioms.add(axiom);
            getDatatypesInSignature(datatypes, axiom.getDataRange(), axioms);
            if (datatypes.contains(axiom.getDatatype())) {
                profileViolations.add(new CycleInDatatypeDefinition(getCurrentOntology(), axioms));
            }
            return null;
        }

        private void getDatatypesInSignature(Set<OWLDatatype> datatypes, OWLObject obj, Set<OWLAxiom> axioms) {
            for (OWLDatatype dt : obj.getDatatypesInSignature()) {
                if (datatypes.add(dt)) {
                    for (OWLOntology ont : getCurrentOntology().getImportsClosure()) {
                        for (OWLDatatypeDefinitionAxiom ax : ont.getDatatypeDefinitions(dt)) {
                            axioms.add(ax);
                            getDatatypesInSignature(datatypes, ax.getDataRange(), axioms);
                        }
                    }
                }
            }
        }

        public Object visit(OWLObjectProperty property) {
            if (!property.isOWLTopObjectProperty() && !property.isOWLBottomObjectProperty()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForObjectPropertyIRI(getCurrentOntology(), getCurrentAxiom(), property));
                }
            }
            if (!property.isBuiltIn() && !getCurrentOntology().isDeclared(property, true)) {
                profileViolations.add(new UseOfUndeclaredObjectProperty(getCurrentOntology(), getCurrentAxiom(), property));
            }
            if (getCurrentOntology().containsDataPropertyReference(property.getIRI(), true)) {
                // TODO: Error
                System.out.println("Object property IRI is also used as a data property IRI");
            }
            if (getCurrentOntology().containsAnnotationPropertyReference(property.getIRI(), true)) {
                // TODO: Error
                System.out.println("Object property IRI is used as an annotation property IRI");
            }

            return null;
        }

        public Object visit(OWLDataProperty property) {
            if (!property.isOWLTopDataProperty() && !property.isOWLBottomDataProperty()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForDataPropertyIRI(getCurrentOntology(), getCurrentAxiom(), property));
                }
            }
            if (!property.isBuiltIn() && !getCurrentOntology().isDeclared(property, true)) {
                profileViolations.add(new UseOfUndeclaredDataProperty(getCurrentOntology(), getCurrentAxiom(), property));
            }

            if (getCurrentOntology().containsObjectPropertyReference(property.getIRI(), true)) {
                // TODO: Error
                System.out.println("Data property IRI is also used an an object property IRI");
            }

            if (getCurrentOntology().containsAnnotationPropertyReference(property.getIRI(), true)) {
                // TODO: Error
                System.out.println("Data property IRI is also used as an annotation property IRI");
            }
            return null;
        }

        public Object visit(OWLAnnotationProperty property) {
            if (!property.isBuiltIn()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForAnnotationPropertyIRI(getCurrentOntology(), getCurrentAxiom(), property));
                }
            }
            if (!property.isBuiltIn() && !getCurrentOntology().isDeclared(property)) {
                profileViolations.add(new UseOfUndeclaredAnnotationProperty(getCurrentOntology(), getCurrentAxiom(), property));
            }

            if (getCurrentOntology().containsObjectPropertyReference(property.getIRI(), true)) {
                // TODO: Error
                System.out.println("Annotation property IRI is also an object property IRI");
            }

            if (getCurrentOntology().containsDataPropertyReference(property.getIRI(), true)) {
                // TODO: Error
                System.out.println("Annotation property IRI is also a data property IRI");
            }
            return null;
        }

        public Object visit(OWLNamedIndividual individual) {
            if (!individual.isAnonymous() && individual.getIRI().isReservedVocabulary()) {
                profileViolations.add(new UseOfReservedVocabularyForIndividualIRI(getCurrentOntology(), getCurrentAxiom(), individual));

            }
            return null;
        }

        public Object visit(OWLSubDataPropertyOfAxiom axiom) {
            if (axiom.getSubProperty().isOWLTopDataProperty()) {
                profileViolations.add(new UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        public Object visit(OWLObjectMinCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInCardinalityRestriction(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        public Object visit(OWLObjectMaxCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInCardinalityRestriction(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        public Object visit(OWLObjectExactCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInCardinalityRestriction(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        public Object visit(OWLObjectHasSelf desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInObjectHasSelf(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInFunctionalPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInIrreflexivePropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                if (getPropertyManager().isNonSimple(prop)) {
                    profileViolations.add(new UseOfNonSimplePropertyInDisjointPropertiesAxiom(getCurrentOntology(), axiom, prop));
                }
            }
            return null;
        }

        public Object visit(OWLSubPropertyChainOfAxiom axiom) {
            if (axiom.getPropertyChain().size() > 2) {
                OWLObjectPropertyExpression superProp = axiom.getSuperProperty();
                if (!superProp.isOWLTopObjectProperty()) {
                    if (!axiom.isEncodingOfTransitiveProperty()) {
                        List<OWLObjectPropertyExpression> chain = axiom.getPropertyChain();
                        if (chain.get(0).equals(superProp)) {
                            for (int i = 1; i < chain.size(); i++) {
                                if (!getPropertyManager().isLessThan(chain.get(i), superProp)) {
                                    profileViolations.add(new UseOfPropertyInChainCausesCycle(getCurrentOntology(), axiom, chain.get(i)));
                                }
                            }
                        }
                        else if (chain.get(chain.size() - 1).equals(superProp)) {
                            for (int i = 0; i < chain.size() - 1; i++) {
                                if (!getPropertyManager().isLessThan(chain.get(i), superProp)) {
                                    profileViolations.add(new UseOfPropertyInChainCausesCycle(getCurrentOntology(), axiom, chain.get(i)));
                                }
                            }
                        }
                        for (OWLObjectPropertyExpression sub : axiom.getPropertyChain()) {
                            if (!getPropertyManager().isLessThan(sub, superProp)) {
                                profileViolations.add(new UseOfPropertyInChainCausesCycle(getCurrentOntology(), axiom, sub));
                            }
                        }

                    }
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {

        try {
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("http://protege.cim3.net/file/pub/ontologies/koala/koala.owl"));
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("http://protege.cim3.net/file/pub/ontologies/tambis/tambis-full.owl"));
            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("http://owl.cs.manchester.ac.uk/repository/download?ontology=http://ontology.dumontierlab.com/chemistry-complex&format=RDF/XML"));

            DefaultPrefixManager pm = new DefaultPrefixManager("http://owl.cs.manchester.ac.uk/repository/download?ontology=http://ontology.dumontierlab.com/chemistry-complex&format=RDF/XML");
//            DefaultPrefixManager pm = new DefaultPrefixManager("http://owl.cs.manchester.ac.uk/repository/download?ontology=http://ontology.dumontierlab.com/periodic-table-complex&format=RDF/XML");
            SimpleRenderer ren = new SimpleRenderer();
            ren.setShortFormProvider(pm);
            ToStringRenderer.getInstance().setRenderer(ren);
//            OWLOntology ont = man.loadOntologyFromPhysicalURI(URI.create("file:/Users/matthewhorridge/ontologies/thesaurus/thesaurus.owl"));
            System.out.println("Loaded ont");
            OWL2DLProfile profile = new OWL2DLProfile();
            long t0 = System.currentTimeMillis();
            profile.checkOntology(ont, man);
            long t1 = System.currentTimeMillis();
            System.out.println("Time: " + (t1 - t0));
        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

    }

}
