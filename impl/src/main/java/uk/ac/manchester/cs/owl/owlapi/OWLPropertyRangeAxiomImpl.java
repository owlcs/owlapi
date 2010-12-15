package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLPropertyRangeAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLPropertyRangeAxiomImpl<P extends OWLPropertyExpression, R extends OWLPropertyRange> extends OWLUnaryPropertyAxiomImpl<P> implements OWLPropertyRangeAxiom<P, R> {

    private R range;


    public OWLPropertyRangeAxiomImpl(OWLDataFactory dataFactory, P property, R range, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, property, annotations);
        this.range = range;
    }


    public R getRange() {
        return range;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLPropertyRangeAxiom)) {
                return false;
            }
            return ((OWLPropertyRangeAxiom) obj).getRange().equals(range);
        }
        return false;
    }

    @Override
	final protected int compareObjectOfSameType(OWLObject object) {
        int diff = getProperty().compareTo(((OWLPropertyRangeAxiom) object).getProperty());
        if (diff != 0) {
            return diff;
        }
        return getRange().compareTo(((OWLPropertyRangeAxiom) object).getRange());
    }
}
