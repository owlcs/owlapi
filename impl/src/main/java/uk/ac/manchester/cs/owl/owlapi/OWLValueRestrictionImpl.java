package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLHasValueRestriction;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLValueRestrictionImpl<R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>, V extends OWLObject> extends OWLRestrictionImpl<R, P, P> implements OWLHasValueRestriction<R, P, V> {

    private V value;


    protected OWLValueRestrictionImpl(OWLDataFactory dataFactory, P property, V value) {
        super(dataFactory, property);
        this.value = value;
    }


    public V getValue() {
        return value;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLHasValueRestriction<?, ?, ?>)) {
                return false;
            }
            return ((OWLHasValueRestriction<?, ?, ?>) obj).getValue().equals(value);
        }
        return false;
    }


    @Override
	final protected int compareObjectOfSameType(OWLObject object) {
        OWLHasValueRestriction<?, ?, ?> other = (OWLHasValueRestriction<?, ?, ?>) object;
        int diff = getProperty().compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return value.compareTo(other.getValue());
    }
}
