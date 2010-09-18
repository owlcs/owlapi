package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
////////////////////////////////////////////////////////////////////////////////////////////////
//
// An inner helper class that removes the appropriate references indexes for a given axiom
//
////////////////////////////////////////////////////////////////////////////////////////////////


public class OWLNamedObjectReferenceRemoverImpl implements OWLNamedObjectReferenceRemover {

    private OWLAxiom axiom;
    
    private final Internals oi;

    public OWLNamedObjectReferenceRemoverImpl(Internals oi) {
		this.oi=oi;
	}

    public void setAxiom(OWLAxiom axiom) {
        this.axiom = axiom;
    }


    public void visit(OWLClass owlClass) {
        oi.removeOwlClassReferences(owlClass, axiom);
    }


    public void visit(OWLObjectProperty property) {
        oi.removeOwlObjectPropertyReferences(property, axiom);
    }


    public void visit(OWLDataProperty property) {
    	oi.removeOwlDataPropertyReferences(property, axiom);
    }


    public void visit(OWLNamedIndividual owlIndividual) {
    	oi.removeOwlIndividualReferences(owlIndividual, axiom);
    }

    public void visit(OWLAnnotationProperty property) {
    	oi.removeOwlAnnotationPropertyReferences(property, axiom);
    }

    public void visit(OWLDatatype datatype) {
    	oi.removeOwlDatatypeReferences(datatype, axiom);
    }
}
