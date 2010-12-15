package uk.ac.manchester.cs.owl.owlapi;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLObjectIntersectionOfImpl extends OWLNaryBooleanClassExpressionImpl implements OWLObjectIntersectionOf {

    public OWLObjectIntersectionOfImpl(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> operands) {
        super(dataFactory, operands);
    }

    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OBJECT_INTERSECTION_OF;
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectIntersectionOf;
        }
        return false;
    }

    public void accept(OWLClassExpressionVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    @Override
	public Set<OWLClassExpression> asConjunctSet() {
        Set<OWLClassExpression> conjuncts = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : getOperands()) {
            conjuncts.addAll(op.asConjunctSet());
        }
        return conjuncts;
    }


    @Override
    public boolean containsConjunct(OWLClassExpression ce) {
        if (ce.equals(this)) {
            return true;
        }
        for (OWLClassExpression op : getOperands()) {
            if (op.containsConjunct(ce)) {
                return true;
            }
        }
        return false;
    }
}
