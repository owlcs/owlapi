package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Punning between properties is not allowed.
 * 
 * @author ignazio
 */
public class IllegalPunning extends OWLProfileViolation implements
        OWL2DLProfileViolation, OWL2ELProfileViolation, OWL2ProfileViolation,
        OWL2QLProfileViolation, OWL2RLProfileViolation {

    private IRI iri;

    /**
     * @param currentOntology
     *        current ontology
     * @param node
     *        current axiom
     * @param iri
     *        punning IRI
     */
    public IllegalPunning(OWLOntology currentOntology, OWLAxiom node, IRI iri) {
        super(currentOntology, node);
        this.iri = iri;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cannot pun between properties: " + iri.toQuotedString());
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
