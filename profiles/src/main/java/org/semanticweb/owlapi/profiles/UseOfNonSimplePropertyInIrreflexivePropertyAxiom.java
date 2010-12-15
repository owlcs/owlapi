package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLIrreflexiveObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfNonSimplePropertyInIrreflexivePropertyAxiom extends OWLProfileViolation implements OWL2DLProfileViolation {

    public UseOfNonSimplePropertyInIrreflexivePropertyAxiom(OWLOntology ontology, OWLIrreflexiveObjectPropertyAxiom axiom) {
        super(ontology, axiom);
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of non-simple property in ");
        sb.append(getAxiom().getAxiomType().getName());
        sb.append(" axiom: [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
