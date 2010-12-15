package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;

public abstract class OWLQuantifiedDataRestrictionImpl extends OWLQuantifiedRestrictionImpl<OWLDataRange, OWLDataPropertyExpression, OWLDataRange> {

    public OWLQuantifiedDataRestrictionImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression property, OWLDataRange filler) {
        super(dataFactory, property, filler);
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        @SuppressWarnings("unchecked") OWLQuantifiedRestriction<OWLDataRange, OWLDataPropertyExpression, OWLDataRange> other = (OWLQuantifiedRestriction<OWLDataRange, OWLDataPropertyExpression, OWLDataRange>) object;
        OWLDataPropertyExpression p1 = this.getProperty();
        OWLDataPropertyExpression p2 = other.getProperty();
        int diff = p1.compareTo(p2);
        if (diff != 0) {
            return diff;
        }
        return getFiller().compareTo(other.getFiller());
    }

}
