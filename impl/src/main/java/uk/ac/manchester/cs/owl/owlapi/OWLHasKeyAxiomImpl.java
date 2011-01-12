package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLHasKeyAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 17-Jan-2009
 */
public class OWLHasKeyAxiomImpl extends OWLLogicalAxiomImpl implements OWLHasKeyAxiom {

    private OWLClassExpression expression;

    private Set<OWLPropertyExpression> propertyExpressions;

    public OWLHasKeyAxiomImpl(OWLDataFactory dataFactory, OWLClassExpression expression, Set<? extends OWLPropertyExpression> propertyExpressions, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.expression = expression;
        this.propertyExpressions = new TreeSet<OWLPropertyExpression>(propertyExpressions);
    }

    public OWLHasKeyAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLHasKeyAxiom(getClassExpression(), getPropertyExpressions());
    }

    public OWLHasKeyAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLHasKeyAxiom(getClassExpression(), getPropertyExpressions(), mergeAnnos(annotations));
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.HAS_KEY;
    }

    public boolean isLogicalAxiom() {
        return true;
    }

    public OWLClassExpression getClassExpression() {
        return expression;
    }

    public Set<OWLPropertyExpression> getPropertyExpressions() {
        return CollectionFactory.getCopyOnRequestSet(propertyExpressions);
    }

    public Set<OWLDataPropertyExpression> getDataPropertyExpressions() {
        Set<OWLDataPropertyExpression> props = new TreeSet<OWLDataPropertyExpression>();
        for (OWLPropertyExpression prop : propertyExpressions) {
            if (prop.isDataPropertyExpression()) {
                props.add((OWLDataPropertyExpression) prop);
            }
        }
        return props;
    }

    public Set<OWLObjectPropertyExpression> getObjectPropertyExpressions() {
        Set<OWLObjectPropertyExpression> props = new TreeSet<OWLObjectPropertyExpression>();
        for (OWLPropertyExpression prop : propertyExpressions) {
            if (prop.isObjectPropertyExpression()) {
                props.add((OWLObjectPropertyExpression) prop);
            }
        }
        return props;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLHasKeyAxiom other = (OWLHasKeyAxiom) object;
        int diff = expression.compareTo(other.getClassExpression());
        if (diff != 0) {
            return diff;
        }
        return compareSets(propertyExpressions, other.getPropertyExpressions());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLHasKeyAxiom)) {
            return false;
        }
        OWLHasKeyAxiom other = (OWLHasKeyAxiom) obj;
        return expression.equals(other.getClassExpression()) && propertyExpressions.equals(other.getPropertyExpressions()) && other.getAnnotations().equals(getAnnotations());
    }
}
