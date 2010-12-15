package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLLiteralArgumentImpl extends OWLObjectImpl implements SWRLLiteralArgument {

    private OWLLiteral literal;

    public SWRLLiteralArgumentImpl(OWLDataFactory dataFactory, OWLLiteral literal) {
        super(dataFactory);
        this.literal = literal;
    }


    public OWLLiteral getLiteral() {
        return literal;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(SWRLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLLiteralArgumentImpl)) {
            return false;
        }
        SWRLLiteralArgument other = (SWRLLiteralArgument) obj;
        return other.getLiteral().equals(getLiteral());
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return literal.compareTo(((SWRLLiteralArgument) object).getLiteral());
    }
}
