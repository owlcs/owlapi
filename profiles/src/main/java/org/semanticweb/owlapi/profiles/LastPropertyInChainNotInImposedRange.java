package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubPropertyChainOfAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class LastPropertyInChainNotInImposedRange extends OWLProfileViolation implements OWL2ELProfileViolation {

    private OWLSubPropertyChainOfAxiom axiom;

    private OWLObjectPropertyRangeAxiom rangeAxiom;

    public LastPropertyInChainNotInImposedRange(OWLOntology ontology, OWLSubPropertyChainOfAxiom axiom, OWLObjectPropertyRangeAxiom rangeAxiom) {
        super(ontology, axiom);
        this.axiom = axiom;
        this.rangeAxiom = rangeAxiom;
    }

    public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLSubPropertyChainOfAxiom getOWLSubPropertyChainOfAxiom() {
        return axiom;
    }

    public OWLObjectPropertyRangeAxiom getOWLObjectPropertyRangeAxiom() {
        return rangeAxiom;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Last property in chain not in imposed data range: ");
        sb.append(axiom);
        sb.append(" for data range ");
        sb.append(rangeAxiom);
        return sb.toString();
    }
}
