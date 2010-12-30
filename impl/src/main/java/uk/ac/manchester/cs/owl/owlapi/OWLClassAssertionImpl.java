package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
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
public class OWLClassAssertionImpl extends OWLIndividualAxiomImpl implements OWLClassAssertionAxiom {

    private OWLIndividual individual;

    private OWLClassExpression classExpression;


    public OWLClassAssertionImpl(OWLDataFactory dataFactory, OWLIndividual individual, OWLClassExpression classExpression, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.individual = individual;
        this.classExpression = classExpression;
    }

    public OWLClassAssertionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLClassAssertionAxiom(getClassExpression(), getIndividual());
    }

    public OWLClassAssertionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLClassAssertionAxiom(getClassExpression(), getIndividual(), mergeAnnos(annotations));
    }

    public OWLClassExpression getClassExpression() {
        return classExpression;
    }


    public OWLIndividual getIndividual() {
        return individual;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLClassAssertionAxiom)) {
                return false;
            }
            OWLClassAssertionAxiom other = (OWLClassAssertionAxiom) obj;
            return other.getIndividual().equals(individual) && other.getClassExpression().equals(classExpression);
        }
        return false;
    }

    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        return getOWLDataFactory().getOWLSubClassOfAxiom(getOWLDataFactory().getOWLObjectOneOf(getIndividual()), getClassExpression());
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
        return AxiomType.CLASS_ASSERTION;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLClassAssertionAxiom otherAx = (OWLClassAssertionAxiom) object;
        int diff = getIndividual().compareTo(otherAx.getIndividual());
        if (diff != 0) {
            return diff;
        }
        else {
            return getClassExpression().compareTo(otherAx.getClassExpression());
        }
    }

}
