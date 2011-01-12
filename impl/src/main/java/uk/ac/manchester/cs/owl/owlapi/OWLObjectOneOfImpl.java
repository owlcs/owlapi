package uk.ac.manchester.cs.owl.owlapi;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLObjectOneOfImpl extends OWLAnonymousClassExpressionImpl implements OWLObjectOneOf {

    private Set<OWLIndividual> values;


    public OWLObjectOneOfImpl(OWLDataFactory dataFactory, Set<? extends OWLIndividual> values) {
        super(dataFactory);
        this.values = new HashSet<OWLIndividual>(values);
    }


    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OBJECT_ONE_OF;
    }


    public Set<OWLIndividual> getIndividuals() {
        return CollectionFactory.getCopyOnRequestSet(values);
    }


    public boolean isClassExpressionLiteral() {
        return false;
    }


    public OWLClassExpression asObjectUnionOf() {
        if (values.size() == 1) {
            return this;
        }
        else {
            Set<OWLClassExpression> ops = new HashSet<OWLClassExpression>();
            for (OWLIndividual ind : values) {
                ops.add(getOWLDataFactory().getOWLObjectOneOf(ind));
            }
            return getOWLDataFactory().getOWLObjectUnionOf(ops);
        }
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLObjectOneOf)) {
                return false;
            }
            return ((OWLObjectOneOf) obj).getIndividuals().equals(values);
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


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(values, ((OWLObjectOneOf) object).getIndividuals());
    }
}
