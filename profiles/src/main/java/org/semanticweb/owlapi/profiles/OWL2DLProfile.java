package org.semanticweb.owlapi.profiles;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.util.OWLObjectPropertyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

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
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     *         ontology is within this profile.
     */
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWL2Profile owl2Profile = new OWL2Profile();
        OWLProfileReport report = owl2Profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new LinkedHashSet<OWLProfileViolation>();
        if(!report.isInProfile()) {
            //We won't be in the OWL 2 DL Profile then!
            violations.addAll(report.getViolations());
        }
        OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
        OWL2DLProfileObjectVisitor visitor = new OWL2DLProfileObjectVisitor(walker, ontology.getOWLOntologyManager());
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }


    private static class OWL2DLProfileObjectVisitor extends OWLOntologyWalkerVisitor<Object> {

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

        @Override
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

        @Override
		public Object visit(OWLClass desc) {
            if (!desc.isBuiltIn()) {
                if (desc.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForClassIRI(getCurrentOntology(), getCurrentAxiom(), desc));
                }
            }
            if (!desc.isBuiltIn() && !getCurrentOntology().isDeclared(desc, true)) {
                profileViolations.add(new UseOfUndeclaredClass(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            if (getCurrentOntology().containsDatatypeInSignature(desc.getIRI())) {
                profileViolations.add(new DatatypeIRIAlsoUsedAsClassIRI(getCurrentOntology(), getCurrentAxiom(), desc.getIRI()));
            }
            return null;
        }

        @Override
		public Object visit(OWLDatatype datatype) {
            // Each datatype MUST statisfy the following:
            // An IRI used to identify a datatype MUST
            //     - Identify a datatype in the OWL 2 datatype map (Section 4.1 lists them), or
            //     - Have the xsd: prefix, or
            //     - Be rdfs:Literal, or
            //     - Not be in the reserved vocabulary of OWL 2
            if (!OWL2Datatype.isBuiltIn(datatype.getIRI())) {
                if (!datatype.getIRI().toString().startsWith(Namespaces.XSD.toString())) {
                    if (!datatype.isTopDatatype()) {
                        if (datatype.getIRI().isReservedVocabulary()) {
                            profileViolations.add(new UseOfUnknownDatatype(getCurrentOntology(), getCurrentAxiom(), datatype));
                        }
                    }
                }
                // We also have to declare datatypes that are not built in
                if (!datatype.isTopDatatype() && datatype.isBuiltIn() && getCurrentOntology().isDeclared(datatype, true)) {
                    profileViolations.add(new UseOfUndeclaredDatatype(getCurrentOntology(), getCurrentAxiom(), datatype));
                }
            }

            if (getCurrentOntology().containsClassInSignature(datatype.getIRI(), true)) {
                profileViolations.add(new DatatypeIRIAlsoUsedAsClassIRI(getCurrentOntology(), getCurrentAxiom(), datatype.getIRI()));
            }
            return null;
        }

        @Override
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
                profileViolations.add(new CycleInDatatypeDefinition(getCurrentOntology(), axiom));
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

        @Override
		public Object visit(OWLObjectProperty property) {
            if (!property.isOWLTopObjectProperty() && !property.isOWLBottomObjectProperty()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForObjectPropertyIRI(getCurrentOntology(), getCurrentAxiom(), property));
                }
            }
            if (!property.isBuiltIn() && !getCurrentOntology().isDeclared(property, true)) {
                profileViolations.add(new UseOfUndeclaredObjectProperty(getCurrentOntology(), getCurrentAxiom(), property));
            }
            if (getCurrentOntology().containsDataPropertyInSignature(property.getIRI(), true)) {
                // TODO: Error
            }
            if (getCurrentOntology().containsAnnotationPropertyInSignature(property.getIRI(), true)) {
                // TODO: Error
            }

            return null;
        }

        @Override
		public Object visit(OWLDataProperty property) {
            if (!property.isOWLTopDataProperty() && !property.isOWLBottomDataProperty()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForDataPropertyIRI(getCurrentOntology(), getCurrentAxiom(), property));
                }
            }
            if (!property.isBuiltIn() && !getCurrentOntology().isDeclared(property, true)) {
                profileViolations.add(new UseOfUndeclaredDataProperty(getCurrentOntology(), getCurrentAxiom(), property));
            }

            if (getCurrentOntology().containsObjectPropertyInSignature(property.getIRI(), true)) {
                // TODO: Error
            }

            if (getCurrentOntology().containsAnnotationPropertyInSignature(property.getIRI(), true)) {
                // TODO: Error
            }
            return null;
        }

        @Override
		public Object visit(OWLAnnotationProperty property) {
            if (!property.isBuiltIn()) {
                if (property.getIRI().isReservedVocabulary()) {
                    profileViolations.add(new UseOfReservedVocabularyForAnnotationPropertyIRI(getCurrentOntology(), getCurrentAxiom(), property));
                }
            }
            if (!property.isBuiltIn() && !getCurrentOntology().isDeclared(property, true)) {
                profileViolations.add(new UseOfUndeclaredAnnotationProperty(getCurrentOntology(), getCurrentAxiom(), getCurrentAnnotation(), property));
            }

            if (getCurrentOntology().containsObjectPropertyInSignature(property.getIRI(), true)) {
                // TODO: Error
//                System.out.println("Annotation property IRI is also an object property IRI");
            }

            if (getCurrentOntology().containsDataPropertyInSignature(property.getIRI(), true)) {
                // TODO: Error
//                System.out.println("Annotation property IRI is also a data property IRI");
            }
            return null;
        }

        @Override
		public Object visit(OWLNamedIndividual individual) {
            if (!individual.isAnonymous() && individual.getIRI().isReservedVocabulary()) {
                profileViolations.add(new UseOfReservedVocabularyForIndividualIRI(getCurrentOntology(), getCurrentAxiom(), individual));

            }
            return null;
        }

        @Override
		public Object visit(OWLSubDataPropertyOfAxiom axiom) {
            if (axiom.getSubProperty().isOWLTopDataProperty()) {
                profileViolations.add(new UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
		public Object visit(OWLObjectMinCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInCardinalityRestriction(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
		public Object visit(OWLObjectMaxCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInCardinalityRestriction(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
		public Object visit(OWLObjectExactCardinality desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInCardinalityRestriction(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
		public Object visit(OWLObjectHasSelf desc) {
            if (getPropertyManager().isNonSimple(desc.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInObjectHasSelf(getCurrentOntology(), getCurrentAxiom(), desc));
            }
            return null;
        }

        @Override
		public Object visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInFunctionalPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
		public Object visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInInverseFunctionalObjectPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
		public Object visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInIrreflexivePropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
		public Object visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (getPropertyManager().isNonSimple(axiom.getProperty())) {
                profileViolations.add(new UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom(getCurrentOntology(), axiom));
            }
            return null;
        }

        @Override
		public Object visit(OWLDisjointObjectPropertiesAxiom axiom) {
            for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                if (getPropertyManager().isNonSimple(prop)) {
                    profileViolations.add(new UseOfNonSimplePropertyInDisjointPropertiesAxiom(getCurrentOntology(), axiom, prop));
                }
            }
            return null;
        }

        @Override
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

}
