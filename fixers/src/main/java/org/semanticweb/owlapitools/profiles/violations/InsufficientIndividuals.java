package org.semanticweb.owlapitools.profiles.violations;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapitools.profiles.OWLProfileViolation;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitor;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitorEx;

/** @author ignazio */
public class InsufficientIndividuals extends OWLProfileViolation<Object> {
    /** @param currentOntology
     *            currentOntology
     * @param node
     *            node */
    public InsufficientIndividuals(OWLOntology currentOntology, OWLAxiom node) {
        super(currentOntology, node, null);
    }

    @Override
    public String toString() {
        return toString("Not enough individuals; at least two needed");
    }

    @Override
    public void accept(OWLProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLProfileViolationVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
