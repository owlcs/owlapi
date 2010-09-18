package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // An inner helper class that adds the appropriate references indexes for a given axiom
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////


    public interface OWLNamedObjectReferenceAdder extends OWLEntityVisitor {        public void setAxiom(OWLAxiom axiom);}
    	