package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntityVisitor;


public interface OWLNamedObjectReferenceRemover extends OWLEntityVisitor {

    public void setAxiom(OWLAxiom axiom);
}
