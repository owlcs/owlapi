package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLDataCardinalityRestrictionImpl extends OWLCardinalityRestrictionImpl<OWLDataRange, OWLDataPropertyExpression, OWLDataRange> implements OWLDataCardinalityRestriction {

    protected OWLDataCardinalityRestrictionImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression property, int cardinality, OWLDataRange filler) {
        super(dataFactory, property, cardinality, filler);
    }


    public boolean isQualified() {
        return !getFiller().equals(getOWLDataFactory().getTopDatatype());
    }

    public boolean isObjectRestriction() {
        return false;
    }

    public boolean isDataRestriction() {
        return true;
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLDataCardinalityRestriction;
        }
        return false;
    }
}
