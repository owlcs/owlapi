package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfNonSuperClassExpression extends OWLProfileViolation implements OWL2QLProfileViolation, OWL2RLProfileViolation {

    private OWLClassExpression classExpression;

    public UseOfNonSuperClassExpression(OWLOntology ontology, OWLAxiom axiom, OWLClassExpression classExpression) {
        super(ontology, axiom);
        this.classExpression = classExpression;
    }

    public OWLClassExpression getOWLClassExpression() {
        return classExpression;
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
        sb.append("Use of non-superclass expression in position that requires a superclass expression: ");
        sb.append(classExpression);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
