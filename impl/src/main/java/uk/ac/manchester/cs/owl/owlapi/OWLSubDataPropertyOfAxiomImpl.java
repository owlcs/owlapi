package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLSubDataPropertyOfAxiomImpl extends OWLSubPropertyAxiomImpl<OWLDataPropertyExpression> implements OWLSubDataPropertyOfAxiom {

    public OWLSubDataPropertyOfAxiomImpl(OWLDataFactory dataFactory, OWLDataPropertyExpression subProperty, OWLDataPropertyExpression superProperty, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, subProperty, superProperty, annotations);
    }


    public OWLSubDataPropertyOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubDataPropertyOfAxiom(getSubProperty(), getSuperProperty());
    }

    public OWLSubDataPropertyOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubDataPropertyOfAxiom(getSubProperty(), getSuperProperty(), mergeAnnos(annotations));
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLSubDataPropertyOfAxiom;
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

    public AxiomType<?> getAxiomType() {
        return AxiomType.SUB_DATA_PROPERTY;
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
