package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class LexicalNotInLexicalSpace extends OWLProfileViolation implements OWL2ProfileViolation {

    private OWLLiteral literal;

    public LexicalNotInLexicalSpace(OWLOntology ontology, OWLAxiom axiom, OWLLiteral literal) {
        super(ontology, axiom);
        this.literal = literal;
    }

    public void accept(OWL2ProfileViolationVisitor visitor) {
        visitor.visit(this);
    }

    public OWLLiteral getLiteral() {
        return literal;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Literal lexical value not in lexical space: ");
        sb.append(literal);
        sb.append(" [");
        sb.append(getAxiom());
        sb.append(" in ");
        sb.append(getOntologyID());
        sb.append("]");
        return sb.toString();
    }
}
