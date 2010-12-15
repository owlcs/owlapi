package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLObjectHasValueImpl extends OWLValueRestrictionImpl<OWLClassExpression, OWLObjectPropertyExpression, OWLIndividual> implements OWLObjectHasValue {

    public OWLObjectHasValueImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, OWLIndividual value) {
        super(dataFactory, property, value);
    }

    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OBJECT_HAS_VALUE;
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectHasValue;
        }
        return false;
    }

    public boolean isObjectRestriction() {
        return true;
    }

    public boolean isDataRestriction() {
        return false;
    }

    public OWLClassExpression asSomeValuesFrom() {
        return getOWLDataFactory().getOWLObjectSomeValuesFrom(getProperty(), getOWLDataFactory().getOWLObjectOneOf(getValue()));
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
