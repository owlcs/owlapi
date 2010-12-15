package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLDisjointObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfNonSimplePropertyInDisjointPropertiesAxiom extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLObjectPropertyExpression prop;

    public UseOfNonSimplePropertyInDisjointPropertiesAxiom(OWLOntology ontology, OWLDisjointObjectPropertiesAxiom axiom, OWLObjectPropertyExpression prop) {
        super(ontology, axiom);
        this.prop = prop;
    }

    public OWLObjectPropertyExpression getOWLObjectProperty() {
        return prop;
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
