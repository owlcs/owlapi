package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLDataRangeAtomImpl extends SWRLUnaryAtomImpl<SWRLDArgument> implements SWRLDataRangeAtom {

    public SWRLDataRangeAtomImpl(OWLDataFactory dataFactory, OWLDataRange predicate, SWRLDArgument arg) {
        super(dataFactory, predicate, arg);
    }

    @Override
	public OWLDataRange getPredicate() {
        return (OWLDataRange) super.getPredicate();
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

    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLDataRangeAtom)) {
            return false;
        }
        SWRLDataRangeAtom other = (SWRLDataRangeAtom) obj;
        return other.getArgument().equals(getArgument()) && other.getPredicate().equals(getPredicate());
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {

        SWRLDataRangeAtom other = (SWRLDataRangeAtom) object;
        int diff = getPredicate().compareTo(other.getPredicate());
        if (diff != 0) {
            return diff;
        }
        return getArgument().compareTo(other.getArgument());
    }
}
