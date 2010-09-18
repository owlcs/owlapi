package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

public class OWLNamedObjectReferenceAdderImpl implements OWLNamedObjectReferenceAdder{
        private OWLAxiom axiom;
        
        private final Internals oi;
        public OWLNamedObjectReferenceAdderImpl(Internals oi) {
			this.oi=oi;
		}


        public void setAxiom(OWLAxiom axiom) {
            this.axiom = axiom;
        }


        public void visit(OWLClass owlClass) {
oi.addOwlClassReferences(owlClass, axiom);
        }


        public void visit(OWLObjectProperty property) {
            oi.addOwlObjectPropertyReferences(property, axiom); 
        }


        public void visit(OWLDataProperty property) {
            oi.addOwlDataPropertyReferences(property, axiom);
        }


        public void visit(OWLNamedIndividual owlIndividual) {
            oi.addOwlIndividualReferences(owlIndividual, axiom);
        }

        public void visit(OWLAnnotationProperty property) {
            oi.addOwlAnnotationPropertyReferences(property, axiom);
        }

        public void visit(OWLDatatype datatype) {
            oi.addOwlDatatypeReferences(datatype, axiom);
        }
    }