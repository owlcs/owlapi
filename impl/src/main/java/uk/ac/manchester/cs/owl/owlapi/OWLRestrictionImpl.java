package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLRestriction;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLRestrictionImpl<R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>, F> extends OWLAnonymousClassExpressionImpl implements OWLRestriction<R, P, F> {

    private P property;


    public OWLRestrictionImpl(OWLDataFactory dataFactory, P property) {
        super(dataFactory);
        this.property = property;
    }


    public boolean isClassExpressionLiteral() {
        return false;
    }


    public P getProperty() {
        return property;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLRestriction)) {
                return false;
            }
            return ((OWLRestriction) obj).getProperty().equals(property);
        }
        return false;
    }
}
