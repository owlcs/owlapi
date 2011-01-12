package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDisjointUnionAxiomImpl extends OWLClassAxiomImpl implements OWLDisjointUnionAxiom {

    private OWLClass owlClass;

    private Set<OWLClassExpression> classExpressions;

    public OWLDisjointUnionAxiomImpl(OWLDataFactory dataFactory, OWLClass owlClass, Set<? extends OWLClassExpression> classExpressions, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.owlClass = owlClass;
        this.classExpressions = new TreeSet<OWLClassExpression>(classExpressions);
    }

    public Set<OWLClassExpression> getClassExpressions() {
        return CollectionFactory.getCopyOnRequestSet(classExpressions);
    }

    public OWLDisjointUnionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDisjointUnionAxiom(getOWLClass(), getClassExpressions());
    }

    public OWLDisjointUnionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDisjointUnionAxiom(getOWLClass(), getClassExpressions(), mergeAnnos(annotations));
    }

    public OWLClass getOWLClass() {
        return owlClass;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLDisjointUnionAxiom)) {
                return false;
            }
            return ((OWLDisjointUnionAxiom) obj).getOWLClass().equals(owlClass);
        }
        return false;
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
        return AxiomType.DISJOINT_UNION;
    }

    public OWLEquivalentClassesAxiom getOWLEquivalentClassesAxiom() {
        return getOWLDataFactory().getOWLEquivalentClassesAxiom(owlClass, getOWLDataFactory().getOWLObjectUnionOf(getClassExpressions()));
    }

    public OWLDisjointClassesAxiom getOWLDisjointClassesAxiom() {
        return getOWLDataFactory().getOWLDisjointClassesAxiom(getClassExpressions());
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLDisjointUnionAxiom other = (OWLDisjointUnionAxiom) object;
        int diff = owlClass.compareTo(other.getOWLClass());
        if (diff != 0) {
            return diff;
        }
        return compareSets(classExpressions, other.getClassExpressions());
    }
}
