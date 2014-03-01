package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;

/** Not enough operands. */
public class InsufficientOperands extends OWLProfileViolation implements
        OWL2DLProfileViolation, OWL2ELProfileViolation, OWL2ProfileViolation,
        OWL2QLProfileViolation, OWL2RLProfileViolation {

    private final OWLObject expression;

    /**
     * @param ontology
     *        ontology with violation
     * @param axiom
     *        axiom with violation
     * @param c
     *        object without enough operands
     */
    public InsufficientOperands(OWLOntology ontology, OWLAxiom axiom,
            OWLObject c) {
        super(ontology, axiom);
        expression = c;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Not enough operands; at least two needed: ");
        sb.append(expression);
        sb.append(" : ");
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
