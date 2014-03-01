package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/** Insufficient property expressions. */
public class InsufficientPropertyExpressions extends OWLProfileViolation
        implements OWL2DLProfileViolation, OWL2ELProfileViolation,
        OWL2ProfileViolation, OWL2QLProfileViolation, OWL2RLProfileViolation {

    /**
     * @param ontology
     *        ontology with violation
     * @param axiom
     *        axiom with violation
     */
    public InsufficientPropertyExpressions(OWLOntology ontology, OWLAxiom axiom) {
        super(ontology, axiom);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Not enough property expressions; at least two needed: ");
        sb.append(getAxiom());
        return sb.toString();
    }

    @Override
    public void accept(OWL2RLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWL2QLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWL2ELProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWL2DLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }
}
