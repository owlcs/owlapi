package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLClassAtomImpl extends SWRLUnaryAtomImpl<SWRLIArgument> implements SWRLClassAtom {

    public SWRLClassAtomImpl(OWLDataFactory dataFactory, OWLClassExpression predicate, SWRLIArgument arg) {
        super(dataFactory, predicate, arg);
    }

    @Override
	public OWLClassExpression getPredicate() {
        return (OWLClassExpression) super.getPredicate();
    }

    public void accept(SWRLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLObjectVisitor visitor) {
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
        if (!(obj instanceof SWRLClassAtom)) {
            return false;
        }
        SWRLClassAtom other = (SWRLClassAtom) obj;
        return other.getArgument().equals(getArgument()) && other.getPredicate().equals(getPredicate());
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        SWRLClassAtom other = (SWRLClassAtom) object;
        int diff = getPredicate().compareTo(other.getPredicate());
        if (diff != 0) {
            return diff;
        }
        return getArgument().compareTo(other.getArgument());
    }
}
