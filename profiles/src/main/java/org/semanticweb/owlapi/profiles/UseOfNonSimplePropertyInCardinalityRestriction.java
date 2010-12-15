package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfNonSimplePropertyInCardinalityRestriction extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLObjectCardinalityRestriction restriction;

    public UseOfNonSimplePropertyInCardinalityRestriction(OWLOntology ontology, OWLAxiom axiom, OWLObjectCardinalityRestriction restriction) {
        super(ontology, axiom);
        this.restriction = restriction;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLObjectCardinalityRestriction getOWLCardinalityRestriction() {
        return restriction;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of non-simple property in ");
        sb.append(restriction.getClassExpressionType().getName());
        sb.append(" restriction: ");
        sb.append(restriction);
        sb.append("  [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
