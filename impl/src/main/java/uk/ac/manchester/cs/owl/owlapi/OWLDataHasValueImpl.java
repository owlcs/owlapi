package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDataHasValueImpl extends OWLValueRestrictionImpl<OWLDataRange, OWLDataPropertyExpression, OWLLiteral> implements OWLDataHasValue {

    public OWLDataHasValueImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression property, OWLLiteral value) {
        super(dataFactory, property, value);
    }

    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.DATA_HAS_VALUE;
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
            return obj instanceof OWLDataHasValue;
        }
        return false;
    }


    public OWLClassExpression asSomeValuesFrom() {
        return getOWLDataFactory().getOWLDataSomeValuesFrom(getProperty(), getOWLDataFactory().getOWLDataOneOf(getValue()));
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
