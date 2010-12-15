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
public class UseOfNonAtomicClassExpression extends OWLProfileViolation implements OWL2QLProfileViolation {

    private OWLClassExpression classExpression;

    public UseOfNonAtomicClassExpression(OWLOntology ontology, OWLAxiom axiom, OWLClassExpression classExpression) {
        super(ontology, axiom);
        this.classExpression = classExpression;
    }

    public OWLClassExpression getOWLClassExpression() {
        return classExpression;
    }

    public void accept(OWL2QLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of non-atomic class expression: ");
        sb.append(classExpression);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
