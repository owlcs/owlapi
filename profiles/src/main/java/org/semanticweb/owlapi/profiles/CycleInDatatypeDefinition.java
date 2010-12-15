package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class CycleInDatatypeDefinition extends OWLProfileViolation implements OWL2DLProfileViolation {

    public CycleInDatatypeDefinition(OWLOntology ontology, OWLAxiom axiom) {
        super(ontology, axiom);
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cycle in datatype definition");
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append("]");
        return sb.toString();
    }
}
