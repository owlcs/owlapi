package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom extends OWLProfileViolation implements OWL2DLProfileViolation {

    public UseOfTopDataPropertyAsSubPropertyInSubPropertyAxiom(OWLOntology ontology, OWLSubDataPropertyOfAxiom axiom) {
        super(ontology, axiom);
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of owl:topDataProperty as sub-property in SubDataPropertyOf axiom: ");
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
