package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLObjectCardinalityRestrictionImpl extends OWLCardinalityRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLClassExpression> implements OWLObjectCardinalityRestriction {

    protected OWLObjectCardinalityRestrictionImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, int cardinality, OWLClassExpression filler) {
        super(dataFactory, property, cardinality, filler);
    }


    public boolean isQualified() {
        return getFiller().isAnonymous() || !getFiller().isOWLThing();
    }

    public boolean isObjectRestriction() {
        return true;
    }

    public boolean isDataRestriction() {
        return false;
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectCardinalityRestriction;
        }
        return false;
    }
}
