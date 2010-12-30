package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLObjectPropertyAssertionAxiomImpl extends OWLIndividualRelationshipAxiomImpl<OWLObjectPropertyExpression, OWLIndividual> implements OWLObjectPropertyAssertionAxiom {

    public OWLObjectPropertyAssertionAxiomImpl(OWLDataFactory dataFactory, OWLIndividual subject, OWLObjectPropertyExpression property, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, subject, property, object, annotations);
    }

    public OWLObjectPropertyAssertionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(getProperty(), getSubject(), getObject());
    }

    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        return getOWLDataFactory().getOWLSubClassOfAxiom(getOWLDataFactory().getOWLObjectOneOf(getSubject()), getOWLDataFactory().getOWLObjectHasValue(getProperty(), getObject()));
    }

    public OWLObjectPropertyAssertionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(getProperty(), getSubject(), getObject(), mergeAnnos(annotations));
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectPropertyAssertionAxiom;
        }
        return false;
    }

    public OWLObjectPropertyAssertionAxiom getSimplified() {
        if (!getProperty().isAnonymous()) {
            return this;
        }
        else {
            OWLObjectInverseOf property = (OWLObjectInverseOf) getProperty();
            OWLObjectPropertyExpression invProp = property.getInverse();
            return getOWLDataFactory().getOWLObjectPropertyAssertionAxiom(invProp, getObject(), getSubject());
        }
    }

    public boolean isInSimplifiedForm() {
        return !getProperty().isAnonymous();
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
        return AxiomType.OBJECT_PROPERTY_ASSERTION;
    }
}
