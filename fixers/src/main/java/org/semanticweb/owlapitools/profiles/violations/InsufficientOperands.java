package org.semanticweb.owlapitools.profiles.violations;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapitools.profiles.OWLProfileViolation;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitor;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitorEx;

/** @author ignazio */
public class InsufficientOperands extends OWLProfileViolation<OWLObject> {
    /** @param currentOntology
     *            currentOntology
     * @param node
     *            node
     * @param c
     *            c */
    public InsufficientOperands(OWLOntology currentOntology, OWLAxiom node, OWLObject c) {
        super(currentOntology, node, c);
    }

    @Override
    public void accept(OWLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return toString("Not enough operands; at least two needed: %s", expression);
    }

    @Override
    public <O> O accept(OWLProfileViolationVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
