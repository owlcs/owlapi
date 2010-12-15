package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class UseOfNonSimplePropertyInObjectHasSelf extends OWLProfileViolation implements OWL2DLProfileViolation {

    private OWLObjectHasSelf hasSelf;

    public UseOfNonSimplePropertyInObjectHasSelf(OWLOntology ontology, OWLAxiom axiom, OWLObjectHasSelf hasSelf) {
        super(ontology, axiom);
        this.hasSelf = hasSelf;
    }

    public OWLObjectHasSelf getOWLObjectHasSelf() {
        return hasSelf;
    }

    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Use of non-simple property in ");
        sb.append(ClassExpressionType.OBJECT_HAS_SELF.getName());
        sb.append(" restriction: ");
        sb.append(hasSelf);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
