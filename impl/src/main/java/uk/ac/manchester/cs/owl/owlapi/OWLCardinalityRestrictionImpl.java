package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLCardinalityRestrictionImpl<R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>, F extends OWLPropertyRange> extends OWLRestrictionImpl<R, P, F> implements OWLCardinalityRestriction<R, P, F> {

    private int cardinality;

    private F filler;


    protected OWLCardinalityRestrictionImpl(OWLDataFactory dataFactory, P property, int cardinality, F filler) {
        super(dataFactory, property);
        this.cardinality = cardinality;
        this.filler = filler;
    }


    public int getCardinality() {
        return cardinality;
    }


    public F getFiller() {
        return filler;
    }


    @Override
	public boolean equals(Object obj) {
            if(super.equals(obj)) {
                if(!(obj instanceof OWLCardinalityRestriction)) {
                    return false;
                }
                OWLCardinalityRestriction<R, P, F> other = (OWLCardinalityRestriction<R, P, F>) obj;
                return other.getCardinality() == cardinality &&
                        other.getFiller().equals(filler);
            }
        return false;
    }


    @Override
	final protected int compareObjectOfSameType(OWLObject object) {
        OWLCardinalityRestriction<R, P, F> other = (OWLCardinalityRestriction<R, P, F>) object;
        int diff = getProperty().compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        diff = getCardinality() - other.getCardinality();
        if (diff != 0) {
            return diff;
        }
        return getFiller().compareTo(other.getFiller());
    }
}
