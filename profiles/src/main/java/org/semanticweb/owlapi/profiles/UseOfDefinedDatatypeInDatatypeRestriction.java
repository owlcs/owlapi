package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfDefinedDatatypeInDatatypeRestriction extends OWLProfileViolation implements OWL2ProfileViolation {

    private OWLDatatypeRestriction restriction;

    public UseOfDefinedDatatypeInDatatypeRestriction(OWLOntology ontology, OWLAxiom axiom, OWLDatatypeRestriction restriction) {
        super(ontology, axiom);
        this.restriction = restriction;
    }

    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLDatatypeRestriction getOWLDatatypeRestriction() {
        return restriction;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of defined datatype in datatype restriction: ");
        sb.append(getAxiom());
        sb.append(" [in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
