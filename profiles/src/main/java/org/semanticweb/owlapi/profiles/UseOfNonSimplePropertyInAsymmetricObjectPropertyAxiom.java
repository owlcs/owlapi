package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAsymmetricObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom extends OWLProfileViolation implements OWL2DLProfileViolation {

    public UseOfNonSimplePropertyInAsymmetricObjectPropertyAxiom(OWLOntology ontology, OWLAsymmetricObjectPropertyAxiom axiom) {
        super(ontology, axiom);
    }

    @Override
	public OWLAsymmetricObjectPropertyAxiom getAxiom() {
        return (OWLAsymmetricObjectPropertyAxiom) super.getAxiom();
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
