package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLQuantifiedRestrictionImpl<R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>, F extends OWLPropertyRange> extends OWLRestrictionImpl<R, P, F> implements OWLQuantifiedRestriction<R, P, F> {

    private F filler;


    public OWLQuantifiedRestrictionImpl(OWLDataFactory dataFactory, P property, F filler) {
        super(dataFactory, property);
        this.filler = filler;
    }


    public F getFiller() {
        return filler;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (obj instanceof OWLQuantifiedRestriction) {
                return ((OWLQuantifiedRestriction) obj).getFiller().equals(filler);
            }
        }
        return false;
    }
}
