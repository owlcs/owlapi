package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfReservedVocabularyForIndividualIRI extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLNamedIndividual ind;

    public UseOfReservedVocabularyForIndividualIRI(OWLOntology ontology, OWLAxiom axiom, OWLNamedIndividual ind) {
        super(ontology, axiom);
        this.ind = ind;
    }

    public OWLNamedIndividual getOWLNamedIndividual() {
        return ind;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of reserved vocabulary for individual IRI: ");
        sb.append(ind);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
