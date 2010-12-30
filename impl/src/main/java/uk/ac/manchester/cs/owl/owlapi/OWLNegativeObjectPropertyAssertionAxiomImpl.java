package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNegativeObjectPropertyAssertionAxiom;
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
public class OWLNegativeObjectPropertyAssertionAxiomImpl extends OWLIndividualRelationshipAxiomImpl<OWLObjectPropertyExpression, OWLIndividual> implements OWLNegativeObjectPropertyAssertionAxiom {

    public OWLNegativeObjectPropertyAssertionAxiomImpl(OWLDataFactory dataFactory, OWLIndividual subject, OWLObjectPropertyExpression property, OWLIndividual object, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, subject, property, object, annotations);
    }

    public OWLNegativeObjectPropertyAssertionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(getProperty(), getSubject(), getObject());
    }

    public OWLNegativeObjectPropertyAssertionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLNegativeObjectPropertyAssertionAxiom(getProperty(), getSubject(), getObject(), mergeAnnos(annotations));
    }

    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        OWLDataFactory df = getOWLDataFactory();
        return df.getOWLSubClassOfAxiom(df.getOWLObjectOneOf(getSubject()), df.getOWLObjectComplementOf(df.getOWLObjectHasValue(getProperty(), getObject())));
    }

    /**
     * Determines whether this axiom contains anonymous individuals.  Anonymous individuals are not allowed in
     * negative object property assertions.
     * @return <code>true</code> if this axioms contains anonymous individual axioms
     */
    public boolean containsAnonymousIndividuals() {
        return getSubject().isAnonymous() || getObject().isAnonymous();
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLNegativeObjectPropertyAssertionAxiom;
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
        return AxiomType.NEGATIVE_OBJECT_PROPERTY_ASSERTION;
    }
}
