package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfObjectPropertyInverse extends OWLProfileViolation implements  OWL2ELProfileViolation {

    private OWLObjectPropertyExpression propertyExpression;

    public UseOfObjectPropertyInverse(OWLOntology ontology, OWLAxiom axiom, OWLObjectPropertyExpression propertyExpression) {
        super(ontology, axiom);
        this.propertyExpression = propertyExpression;
    }

    public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLObjectPropertyExpression getOWLPropertyExpression() {
        return propertyExpression;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of inverse property: ");
        sb.append(propertyExpression);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
