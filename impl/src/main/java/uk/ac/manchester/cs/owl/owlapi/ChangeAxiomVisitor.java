package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.model.AxiomType.*;

import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLDifferentIndividualsAxiom;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentDataPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseFunctionalObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLReflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLSameIndividualAxiom;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;
import org.semanticweb.owlapi.model.OWLSymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLTransitiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.SWRLRule;

public class ChangeAxiomVisitor implements OWLAxiomVisitor {

        private final boolean addAxiom;
        
        private final Internals oi;
        public ChangeAxiomVisitor( Internals oi, boolean add) {
			this.oi=oi;
			this.addAxiom=add;
		}

        public void visit(OWLSubClassOfAxiom axiom) {
            if (addAxiom) {
            	oi.addAxiomsByType(SUBCLASS_OF, axiom);
                if (!axiom.getSubClass().isAnonymous()) {
                    OWLClass subClass = (OWLClass) axiom.getSubClass();
                    oi.addToIndexedSet(subClass, oi.getSubClassAxiomsByLHS(), axiom);
                    oi.addToIndexedSet(subClass, oi.getClassAxiomsByClass(), axiom);
                }
                else {
                    oi.addGeneralClassAxioms(axiom);
                }
                if (!axiom.getSuperClass().isAnonymous()) {
                    oi.addToIndexedSet((OWLClass) axiom.getSuperClass(), oi.getSubClassAxiomsByRHS(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(SUBCLASS_OF, axiom);
                if (!axiom.getSubClass().isAnonymous()) {
                    OWLClass subClass = (OWLClass) axiom.getSubClass();
                    oi.removeAxiomFromSet(subClass, oi.getSubClassAxiomsByLHS(), axiom, true);
                    oi.removeAxiomFromSet(subClass, oi.getClassAxiomsByClass(), axiom, true);
                }
                else {
                    oi.removeGeneralClassAxioms(axiom);
                }
                if (!axiom.getSuperClass().isAnonymous()) {
                    oi.removeAxiomFromSet(axiom.getSuperClass().asOWLClass(), oi.getSubClassAxiomsByRHS(), axiom, true);
                }
            }
        }


        public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                oi.addToIndexedSet(axiom.getSubject(), oi.getNegativeObjectPropertyAssertionAxiomsByIndividual(), axiom);
                oi.addAxiomsByType(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiom);
            }
            else {
                oi.removeAxiomsByType(NEGATIVE_OBJECT_PROPERTY_ASSERTION, axiom);
                oi.removeAxiomFromSet(axiom.getSubject(), oi.getNegativeObjectPropertyAssertionAxiomsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLAsymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addToIndexedSet(axiom.getProperty(), oi.getAsymmetricPropertyAxiomsByProperty(), axiom);
                oi.addAxiomsByType(ASYMMETRIC_OBJECT_PROPERTY, axiom);
            }
            else {
                oi.removeAxiomsByType(ASYMMETRIC_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getAsymmetricPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addToIndexedSet(axiom.getProperty(), oi.getReflexivePropertyAxiomsByProperty(), axiom);
                oi.addAxiomsByType(REFLEXIVE_OBJECT_PROPERTY, axiom);
            }
            else {
                oi.removeAxiomsByType(REFLEXIVE_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getReflexivePropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLDisjointClassesAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DISJOINT_CLASSES, axiom);
                boolean allAnon = true;
                // Index against each named class in the axiom
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        OWLClass cls = (OWLClass) desc;
                        oi.addToIndexedSet(cls, oi.getDisjointClassesAxiomsByClass(), axiom);
                        oi.addToIndexedSet(cls, oi.getClassAxiomsByClass(), axiom);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    oi.addGeneralClassAxioms(axiom);
                }
            }
            else {
                oi.removeAxiomsByType(DISJOINT_CLASSES, axiom);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        OWLClass cls = (OWLClass) desc;
                        oi.removeAxiomFromSet(cls, oi.getDisjointClassesAxiomsByClass(), axiom, true);
                        oi.removeAxiomFromSet(cls, oi.getClassAxiomsByClass(), axiom, true);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    oi.removeGeneralClassAxioms(axiom);
                }
            }
        }


        public void visit(OWLDataPropertyDomainAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DATA_PROPERTY_DOMAIN, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getDataPropertyDomainAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(DATA_PROPERTY_DOMAIN, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getDataPropertyDomainAxiomsByProperty(), axiom, true);
            }
        }

        public void visit(OWLObjectPropertyDomainAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(OBJECT_PROPERTY_DOMAIN, axiom);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    oi.addToIndexedSet(axiom.getProperty(), oi.getObjectPropertyDomainAxiomsByProperty(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(OBJECT_PROPERTY_DOMAIN, axiom);
                if (axiom.getProperty() instanceof OWLObjectProperty) {
                    oi.removeAxiomFromSet(axiom.getProperty(), oi.getObjectPropertyDomainAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(EQUIVALENT_OBJECT_PROPERTIES, axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    oi.addToIndexedSet(prop, oi.getEquivalentObjectPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(EQUIVALENT_OBJECT_PROPERTIES, axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    oi.removeAxiomFromSet(prop, oi.getEquivalentObjectPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLInverseObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(INVERSE_OBJECT_PROPERTIES, axiom);
                oi.addToIndexedSet(axiom.getFirstProperty(), oi.getInversePropertyAxiomsByProperty(), axiom);
                oi.addToIndexedSet(axiom.getSecondProperty(), oi.getInversePropertyAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(INVERSE_OBJECT_PROPERTIES, axiom);
                oi.removeAxiomFromSet(axiom.getFirstProperty(), oi.getInversePropertyAxiomsByProperty(), axiom, false);
                oi.removeAxiomFromSet(axiom.getSecondProperty(), oi.getInversePropertyAxiomsByProperty(), axiom, false);
            }
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                oi.addToIndexedSet(axiom.getSubject(), oi.getNegativeDataPropertyAssertionAxiomsByIndividual(), axiom);
                oi.addAxiomsByType(NEGATIVE_DATA_PROPERTY_ASSERTION, axiom);
            }
            else {
                oi.removeAxiomsByType(NEGATIVE_DATA_PROPERTY_ASSERTION, axiom);
                oi.removeAxiomFromSet(axiom.getSubject(), oi.getNegativeDataPropertyAssertionAxiomsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLDifferentIndividualsAxiom axiom) {
            if (addAxiom) {
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    oi.addToIndexedSet(ind, oi.getDifferentIndividualsAxiomsByIndividual(), axiom);
                    oi.addAxiomsByType(DIFFERENT_INDIVIDUALS, axiom);
                }
            }
            else {
                oi.removeAxiomsByType(DIFFERENT_INDIVIDUALS, axiom);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    oi.removeAxiomFromSet(ind, oi.getDifferentIndividualsAxiomsByIndividual(), axiom, true);
                }
            }
        }


        public void visit(OWLDisjointDataPropertiesAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DISJOINT_DATA_PROPERTIES, axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    oi.addToIndexedSet(prop, oi.getDisjointDataPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(DISJOINT_DATA_PROPERTIES, axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    oi.removeAxiomFromSet(prop, oi.getDisjointDataPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DISJOINT_OBJECT_PROPERTIES, axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    oi.addToIndexedSet(prop, oi.getDisjointObjectPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(DISJOINT_OBJECT_PROPERTIES, axiom);
                for (OWLObjectPropertyExpression prop : axiom.getProperties()) {
                    oi.removeAxiomFromSet(prop, oi.getDisjointObjectPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLObjectPropertyRangeAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(OBJECT_PROPERTY_RANGE, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getObjectPropertyRangeAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(OBJECT_PROPERTY_RANGE, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getObjectPropertyRangeAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLObjectPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(OBJECT_PROPERTY_ASSERTION, axiom);
                oi.addToIndexedSet(axiom.getSubject(), oi.getObjectPropertyAssertionsByIndividual(), axiom);
            }
            else {
                oi.removeAxiomsByType(OBJECT_PROPERTY_ASSERTION, axiom);
                oi.removeAxiomFromSet(axiom.getSubject(), oi.getObjectPropertyAssertionsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(FUNCTIONAL_OBJECT_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getFunctionalObjectPropertyAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(FUNCTIONAL_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getFunctionalObjectPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLSubObjectPropertyOfAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(SUB_OBJECT_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getSubProperty(), oi.getObjectSubPropertyAxiomsByLHS(), axiom);
                oi.addToIndexedSet(axiom.getSuperProperty(), oi.getObjectSubPropertyAxiomsByRHS(), axiom);
            }
            else {
                oi.removeAxiomsByType(SUB_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getSubProperty(), oi.getObjectSubPropertyAxiomsByLHS(), axiom, true);
                oi.removeAxiomFromSet(axiom.getSuperProperty(), oi.getObjectSubPropertyAxiomsByRHS(), axiom, true);
            }
        }


        public void visit(OWLDisjointUnionAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DISJOINT_UNION, axiom);
                oi.addToIndexedSet(axiom.getOWLClass(), oi.getDisjointUnionAxiomsByClass(), axiom);
                oi.addToIndexedSet(axiom.getOWLClass(), oi.getClassAxiomsByClass(), axiom);
            }
            else {
                oi.removeAxiomsByType(DISJOINT_UNION, axiom);
                oi.removeAxiomFromSet(axiom.getOWLClass(), oi.getDisjointUnionAxiomsByClass(), axiom, true);
                oi.removeAxiomFromSet(axiom.getOWLClass(), oi.getClassAxiomsByClass(), axiom, true);
            }
        }


        public void visit(OWLDeclarationAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DECLARATION, axiom);
                oi.addDeclarationsByEntity(axiom.getEntity(), axiom);
            }
            else {
                oi.removeAxiomsByType(DECLARATION, axiom);
                oi.removeDeclarationsByEntity(axiom.getEntity(), axiom);
            }
        }


        public void visit(OWLAnnotationAssertionAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(ANNOTATION_ASSERTION, axiom);
                oi.addToIndexedSet(axiom.getSubject(), oi.getAnnotationAssertionAxiomsBySubject(), axiom);
            }
            else {
                oi.removeAxiomsByType(ANNOTATION_ASSERTION, axiom);
                oi.removeAxiomFromSet(axiom.getSubject(), oi.getAnnotationAssertionAxiomsBySubject(), axiom, true);
            }
        }

        public void visit(OWLAnnotationPropertyDomainAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(ANNOTATION_PROPERTY_DOMAIN, axiom);
            }
            else {
                oi.removeAxiomsByType(ANNOTATION_PROPERTY_DOMAIN, axiom);
            }
        }

        public void visit(OWLAnnotationPropertyRangeAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(ANNOTATION_PROPERTY_RANGE, axiom);
            }
            else {
                oi.removeAxiomsByType(ANNOTATION_PROPERTY_RANGE, axiom);
            }
        }

        public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(SUB_ANNOTATION_PROPERTY_OF, axiom);
            }
            else {
                oi.removeAxiomsByType(SUB_ANNOTATION_PROPERTY_OF, axiom);
            }
        }

        public void visit(OWLHasKeyAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(HAS_KEY, axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    oi.addToIndexedSet(axiom.getClassExpression().asOWLClass(), oi.getHasKeyAxiomsByClass(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(HAS_KEY, axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    oi.removeAxiomFromSet(axiom.getClassExpression().asOWLClass(), oi.getHasKeyAxiomsByClass(), axiom, true);
                }
            }
        }

        public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(SYMMETRIC_OBJECT_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getSymmetricPropertyAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(SYMMETRIC_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getSymmetricPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLDataPropertyRangeAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DATA_PROPERTY_RANGE, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getDataPropertyRangeAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(DATA_PROPERTY_RANGE, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getDataPropertyRangeAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLFunctionalDataPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(FUNCTIONAL_DATA_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getFunctionalDataPropertyAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(FUNCTIONAL_DATA_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getFunctionalDataPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(EQUIVALENT_DATA_PROPERTIES, axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    oi.addToIndexedSet(prop, oi.getEquivalentDataPropertyAxiomsByProperty(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(EQUIVALENT_DATA_PROPERTIES, axiom);
                for (OWLDataPropertyExpression prop : axiom.getProperties()) {
                    oi.removeAxiomFromSet(prop, oi.getEquivalentDataPropertyAxiomsByProperty(), axiom, true);
                }
            }
        }


        public void visit(OWLClassAssertionAxiom axiom) {
            if (addAxiom) {
                oi.addToIndexedSet(axiom.getIndividual(), oi.getClassAssertionAxiomsByIndividual(), axiom);
                oi.addAxiomsByType(CLASS_ASSERTION, axiom);
                if (!axiom.getClassExpression().isAnonymous()) {
                    oi.addToIndexedSet((OWLClass) axiom.getClassExpression(), oi.getClassAssertionAxiomsByClass(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(CLASS_ASSERTION, axiom);
                oi.removeAxiomFromSet(axiom.getIndividual(), oi.getClassAssertionAxiomsByIndividual(), axiom, true);
                if (!axiom.getClassExpression().isAnonymous()) {
                    oi.removeAxiomFromSet((OWLClass) axiom.getClassExpression(), oi.getClassAssertionAxiomsByClass(), axiom, true);
                }
            }
        }


        public void visit(OWLEquivalentClassesAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(EQUIVALENT_CLASSES, axiom);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        oi.addToIndexedSet((OWLClass) desc, oi.getEquivalentClassesAxiomsByClass(), axiom);
                        oi.addToIndexedSet((OWLClass) desc, oi.getClassAxiomsByClass(), axiom);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    oi.addGeneralClassAxioms(axiom);
                }
            }
            else {
                oi.removeAxiomsByType(EQUIVALENT_CLASSES, axiom);
                boolean allAnon = true;
                for (OWLClassExpression desc : axiom.getClassExpressions()) {
                    if (!desc.isAnonymous()) {
                        oi.removeAxiomFromSet((OWLClass) desc, oi.getEquivalentClassesAxiomsByClass(), axiom, true);
                        oi.removeAxiomFromSet((OWLClass) desc, oi.getClassAxiomsByClass(), axiom, true);
                        allAnon = false;
                    }
                }
                if (allAnon) {
                    oi.removeGeneralClassAxioms(axiom);
                }
            }
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(DATA_PROPERTY_ASSERTION, axiom);
                oi.addToIndexedSet(axiom.getSubject(), oi.getDataPropertyAssertionsByIndividual(), axiom);
            }
            else {
                oi.removeAxiomsByType(DATA_PROPERTY_ASSERTION, axiom);
                oi.removeAxiomFromSet(axiom.getSubject(), oi.getDataPropertyAssertionsByIndividual(), axiom, true);
            }
        }


        public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(TRANSITIVE_OBJECT_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getTransitivePropertyAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(TRANSITIVE_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getTransitivePropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(IRREFLEXIVE_OBJECT_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getIrreflexivePropertyAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(IRREFLEXIVE_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getIrreflexivePropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLSubDataPropertyOfAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(SUB_DATA_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getSubProperty(), oi.getDataSubPropertyAxiomsByLHS(), axiom);
                oi.addToIndexedSet(axiom.getSuperProperty(), oi.getDataSubPropertyAxiomsByRHS(), axiom);
            }
            else {
                oi.removeAxiomsByType(SUB_DATA_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getSubProperty(), oi.getDataSubPropertyAxiomsByLHS(), axiom, true);
                oi.removeAxiomFromSet(axiom.getSuperProperty(), oi.getDataSubPropertyAxiomsByRHS(), axiom, true);
            }
        }


        public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiom);
                oi.addToIndexedSet(axiom.getProperty(), oi.getInverseFunctionalPropertyAxiomsByProperty(), axiom);
            }
            else {
                oi.removeAxiomsByType(INVERSE_FUNCTIONAL_OBJECT_PROPERTY, axiom);
                oi.removeAxiomFromSet(axiom.getProperty(), oi.getInverseFunctionalPropertyAxiomsByProperty(), axiom, true);
            }
        }


        public void visit(OWLSameIndividualAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(SAME_INDIVIDUAL, axiom);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    oi.addToIndexedSet(ind, oi.getSameIndividualsAxiomsByIndividual(), axiom);
                }
            }
            else {
                oi.removeAxiomsByType(SAME_INDIVIDUAL, axiom);
                for (OWLIndividual ind : axiom.getIndividuals()) {
                    oi.removeAxiomFromSet(ind, oi.getSameIndividualsAxiomsByIndividual(), axiom, true);
                }
            }
        }


        public void visit(OWLSubPropertyChainOfAxiom axiom) {
            if (addAxiom) {
                oi.addAxiomsByType(SUB_PROPERTY_CHAIN_OF, axiom);
                oi.addPropertyChainSubPropertyAxioms(axiom);
            }
            else {
                oi.removeAxiomsByType(SUB_PROPERTY_CHAIN_OF, axiom);
                oi.removePropertyChainSubPropertyAxioms(axiom);
            }
        }


        public void visit(SWRLRule rule) {
            if (addAxiom) {
                oi.addAxiomsByType(SWRL_RULE, rule);
            }
            else {
                oi.removeAxiomsByType(SWRL_RULE, rule);
            }
        }


        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            // Just use general indexing (on the assumption that there won't be many
            // datatype definitions).  This could always be optimised at a later stage.
            if (addAxiom) {
                oi.addAxiomsByType(DATATYPE_DEFINITION, axiom);
            }
            else {
                oi.removeAxiomsByType(DATATYPE_DEFINITION, axiom);
            }
        }
    }