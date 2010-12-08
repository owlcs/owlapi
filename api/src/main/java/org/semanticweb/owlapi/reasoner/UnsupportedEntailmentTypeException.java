package org.semanticweb.owlapi.reasoner;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Mar-2009
 */
public class UnsupportedEntailmentTypeException extends OWLRuntimeException {

    private OWLAxiom axiom;


    public UnsupportedEntailmentTypeException(OWLAxiom axiom) {
        super("Cannot check entailment: " + axiom);
        this.axiom = axiom;
    }


    public OWLAxiom getAxiom() {
        return axiom;
    }
}
