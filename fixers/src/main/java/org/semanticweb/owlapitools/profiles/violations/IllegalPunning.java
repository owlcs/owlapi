package org.semanticweb.owlapitools.profiles.violations;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapitools.profiles.OWLProfileViolation;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitor;
import org.semanticweb.owlapitools.profiles.OWLProfileViolationVisitorEx;

/** Punning between properties is not allowed
 * 
 * @author ignazio */
public class IllegalPunning extends OWLProfileViolation<IRI> {
    /** @param currentOntology
     *            ontology
     * @param node
     *            axiom
     * @param iri
     *            iri */
    public IllegalPunning(OWLOntology currentOntology, OWLAxiom node, IRI iri) {
        super(currentOntology, node, iri);
    }

    @Override
    public String toString() {
        return toString("Cannot pun between properties: %s", getExpression()
                .toQuotedString());
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
