package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLSubClassOfAxiomImpl extends OWLClassAxiomImpl implements OWLSubClassOfAxiom {

    private OWLClassExpression subClass;

    private OWLClassExpression superClass;


    public OWLSubClassOfAxiomImpl(OWLDataFactory dataFactory, OWLClassExpression subClass, OWLClassExpression superClass, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subClass = subClass;
        this.superClass = superClass;
    }

    public Set<OWLClassExpression> getClassExpressions() {
        Set<OWLClassExpression> classExpressions = new HashSet<OWLClassExpression>(3);
        classExpressions.add(subClass);
        classExpressions.add(superClass);
        return classExpressions;
    }

    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... desc) {
        Set<OWLClassExpression> classExpressions = getClassExpressions();
        for (OWLClassExpression ce : desc) {
            classExpressions.remove(ce);
        }
        return classExpressions;
    }

    public OWLSubClassOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass, mergeAnnos(annotations));
    }

    public OWLSubClassOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubClassOfAxiom(subClass, superClass);
    }

    public boolean contains(OWLClassExpression ce) {
        return subClass.equals(ce) || superClass.equals(ce);
    }

    public OWLClassExpression getSubClass() {
        return subClass;
    }


    public OWLClassExpression getSuperClass() {
        return superClass;
    }


    public boolean isGCI() {
        return subClass.isAnonymous();
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLSubClassOfAxiom)) {
            return false;
        }
        OWLSubClassOfAxiom other = (OWLSubClassOfAxiom) obj;
        return other.getSubClass().equals(subClass) && other.getSuperClass().equals(superClass);
    }

    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.SUBCLASS_OF;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLSubClassOfAxiom other = (OWLSubClassOfAxiom) object;
        int diff = subClass.compareTo(other.getSubClass());
        if (diff != 0) {
            return diff;
        }
        return superClass.compareTo(other.getSuperClass());
    }
}
