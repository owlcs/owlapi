package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLSubObjectPropertyOfAxiomImpl extends OWLSubPropertyAxiomImpl<OWLObjectPropertyExpression> implements OWLSubObjectPropertyOfAxiom {

    public OWLSubObjectPropertyOfAxiomImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression subProperty, OWLObjectPropertyExpression superProperty, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, subProperty, superProperty, annotations);
    }

    public OWLSubObjectPropertyOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(getSubProperty(), getSuperProperty());
    }

    public OWLSubObjectPropertyOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubObjectPropertyOfAxiom(getSubProperty(), getSuperProperty(), mergeAnnos(annotations));
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLSubObjectPropertyOfAxiom;
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
        return AxiomType.SUB_OBJECT_PROPERTY;
    }
}
