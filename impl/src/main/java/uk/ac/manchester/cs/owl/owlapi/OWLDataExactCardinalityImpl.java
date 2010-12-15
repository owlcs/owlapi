package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDataExactCardinalityImpl extends OWLDataCardinalityRestrictionImpl implements OWLDataExactCardinality {

    public OWLDataExactCardinalityImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression property, int cardinality, OWLDataRange filler) {
        super(dataFactory, property, cardinality, filler);
    }


    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.DATA_EXACT_CARDINALITY;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLDataExactCardinality;
        }
        return false;
    }

    public void accept(OWLClassExpressionVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public OWLClassExpression asIntersectionOfMinMax() {
        return getOWLDataFactory().getOWLObjectIntersectionOf(getOWLDataFactory().getOWLDataMinCardinality(getCardinality(), getProperty(), getFiller()), getOWLDataFactory().getOWLDataMaxCardinality(getCardinality(), getProperty(), getFiller()));
    }
}
