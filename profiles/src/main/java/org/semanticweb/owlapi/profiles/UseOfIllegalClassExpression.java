package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 *
 * Indicates that a class expression in an axiom is not in the profile
 */
public class UseOfIllegalClassExpression extends OWLProfileViolation implements OWL2ELProfileViolation {

    private OWLClassExpression classExpression;

    public UseOfIllegalClassExpression(OWLOntology ontology, OWLAxiom axiom, OWLClassExpression classExpression) {
        super(ontology, axiom);
        this.classExpression = classExpression;
    }

    public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLClassExpression getOWLClassExpression() {
        return classExpression;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(classExpression.getClassExpressionType().getName());
        sb.append(" class expressions are not allowed in profile: ");
        sb.append(classExpression);
        sb.append("  [");
        sb.append(getAxiom());
        sb.append(" in  ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
