package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfUndeclaredClass extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLClass cls;

    public UseOfUndeclaredClass(OWLOntology ontology, OWLAxiom axiom, OWLClass cls) {
        super(ontology, axiom);
        this.cls = cls;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLClass getOWLClass() {
        return cls;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of undeclared class: ");
        sb.append(cls);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
