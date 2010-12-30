package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLDataPropertyAssertionAxiomImpl extends OWLIndividualRelationshipAxiomImpl<OWLDataPropertyExpression, OWLLiteral> implements OWLDataPropertyAssertionAxiom {

    public OWLDataPropertyAssertionAxiomImpl(OWLDataFactory dataFactory, OWLIndividual subject, OWLDataPropertyExpression property, OWLLiteral value, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, subject, property, value, annotations);
    }

    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        return getOWLDataFactory().getOWLSubClassOfAxiom(getOWLDataFactory().getOWLObjectOneOf(getSubject()), getOWLDataFactory().getOWLDataHasValue(getProperty(), getObject()));
    }

    public OWLDataPropertyAssertionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLDataPropertyAssertionAxiom(getProperty(), getSubject(), getObject());
    }

    public OWLDataPropertyAssertionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLDataPropertyAssertionAxiom(getProperty(), getSubject(), getObject(), mergeAnnos(annotations));
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLDataPropertyAssertionAxiom;
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
        return AxiomType.DATA_PROPERTY_ASSERTION;
    }
}
