package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLLiteral;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 * @since 3.2
 */
public class RDFLiteral extends RDFNode {

    private OWLLiteral literal;

    public RDFLiteral(OWLLiteral literal) {
        this.literal = literal;
    }

    public OWLLiteral getLiteral() {
        return literal;
    }

    @Override
    public int hashCode() {
        return literal.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof RDFLiteral)) {
            return false;
        }
        RDFLiteral other = (RDFLiteral) o;
        return literal.equals(other.literal);
    }
}
