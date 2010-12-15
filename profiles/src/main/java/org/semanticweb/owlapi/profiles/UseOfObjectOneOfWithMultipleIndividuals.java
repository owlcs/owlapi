package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfObjectOneOfWithMultipleIndividuals extends UseOfIllegalClassExpression implements OWL2ELProfileViolation {

    private OWLObjectOneOf oneOf;

    public UseOfObjectOneOfWithMultipleIndividuals(OWLOntology ontology, OWLAxiom axiom, OWLObjectOneOf oneOf) {
        super(ontology, axiom, oneOf);
        this.oneOf = oneOf;
    }

    @Override
	public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLObjectOneOf getOWLObjectOneOf() {
        return oneOf;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of ObjectOneOf with multiple individuals: ");
        sb.append(getAxiom());
        sb.append(" [in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
