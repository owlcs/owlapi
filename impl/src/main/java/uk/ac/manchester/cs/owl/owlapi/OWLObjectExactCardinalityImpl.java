package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLObjectExactCardinalityImpl extends OWLObjectCardinalityRestrictionImpl implements OWLObjectExactCardinality {

    public OWLObjectExactCardinalityImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, int cardinality, OWLClassExpression filler) {
        super(dataFactory, property, cardinality, filler);
    }


    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OBJECT_EXACT_CARDINALITY;
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectExactCardinality;
        }
        return false;
    }


    public OWLClassExpression asIntersectionOfMinMax() {
        OWLDataFactory df = getOWLDataFactory();
        return df.getOWLObjectIntersectionOf(df.getOWLObjectMinCardinality(getCardinality(), getProperty(), getFiller()), df.getOWLObjectMaxCardinality(getCardinality(), getProperty(), getFiller()));
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

}
