package org.semanticweb.owlapitools.profiles.violations;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapitools.profiles.OWLProfileViolation;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitor;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitorEx;

/** @author ignazio */
public class InsufficientPropertyExpressions extends OWLProfileViolation<OWLObject> {
    /** @param ontology
     *            ontology
     * @param axiom
     *            axiom */
    public InsufficientPropertyExpressions(OWLOntology ontology, OWLAxiom axiom) {
        super(ontology, axiom, null);
    }

    @Override
    public String toString() {
        return toString("Not enough property expressions; at least two needed");
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
