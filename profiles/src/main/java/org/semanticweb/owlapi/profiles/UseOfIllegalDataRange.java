package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfIllegalDataRange extends OWLProfileViolation implements OWL2ELProfileViolation, OWL2QLProfileViolation, OWL2RLProfileViolation {

    private OWLDataRange dataRange;

    public UseOfIllegalDataRange(OWLOntology ontology, OWLAxiom axiom, OWLDataRange dataRange) {
        super(ontology, axiom);
        this.dataRange = dataRange;
    }

    public OWLDataRange getOWLDataRange() {
        return dataRange;
    }

    public void accept(OWL2RLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWL2QLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of data range not in profile: ");
        sb.append(dataRange);
        sb.append("  [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
