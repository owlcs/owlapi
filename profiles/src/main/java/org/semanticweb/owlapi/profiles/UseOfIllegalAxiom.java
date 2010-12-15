package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfIllegalAxiom extends OWLProfileViolation implements OWL2ELProfileViolation, OWL2QLProfileViolation, OWL2RLProfileViolation {

    public UseOfIllegalAxiom(OWLOntology ontology, OWLAxiom axiom) {
        super(ontology, axiom);
    }


    public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWL2QLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWL2RLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAxiom().getAxiomType());
        sb.append(" axioms are not allowed in profile. ");
        sb.append(getAxiom());
        sb.append(" [in ontology ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
