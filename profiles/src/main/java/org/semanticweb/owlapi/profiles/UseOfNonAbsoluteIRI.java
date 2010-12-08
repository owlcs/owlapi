package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfNonAbsoluteIRI extends OWLProfileViolation implements OWL2ProfileViolation {

    public UseOfNonAbsoluteIRI(OWLOntology ontology, OWLAxiom axiom) {
        super(ontology, axiom);
    }

    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }
}
