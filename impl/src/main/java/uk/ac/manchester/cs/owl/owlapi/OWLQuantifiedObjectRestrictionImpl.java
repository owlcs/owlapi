package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;

public abstract class OWLQuantifiedObjectRestrictionImpl extends
		OWLQuantifiedRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> {

	public OWLQuantifiedObjectRestrictionImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, OWLClassExpression filler) {
		super(dataFactory, property, filler);
	}
	
	@Override
	protected int compareObjectOfSameType(OWLObject object) {
        @SuppressWarnings("unchecked")
		OWLQuantifiedRestriction<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> other = (OWLQuantifiedRestriction<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression>) object;
        int diff = getProperty().compareTo(other.getProperty());
        if(diff != 0) {
            return diff;
        }
        return getFiller().compareTo(other.getFiller());
    }

}
