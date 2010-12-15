package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfAnonymousIndividual extends OWLProfileViolation implements OWL2ELProfileViolation, OWL2QLProfileViolation {

    private OWLAnonymousIndividual individual;

    public UseOfAnonymousIndividual(OWLOntology ontology, OWLAxiom axiom, OWLAnonymousIndividual individual) {
        super(ontology, axiom);
        this.individual = individual;
    }

    public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWL2QLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLAnonymousIndividual getOWLAnonymousIndividual() {
        return individual;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of anonymous individual: ");
        sb.append(individual);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
