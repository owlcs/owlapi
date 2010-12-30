package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLObjectPropertyRangeAxiomImpl extends OWLPropertyRangeAxiomImpl<OWLObjectPropertyExpression, OWLClassExpression> implements OWLObjectPropertyRangeAxiom {

    public OWLObjectPropertyRangeAxiomImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression property, OWLClassExpression range, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory, property, range, annotations);
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectPropertyRangeAxiom;
        }
        return false;
    }

    public OWLObjectPropertyRangeAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(getProperty(), getRange());
    }

    public OWLObjectPropertyRangeAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLObjectPropertyRangeAxiom(getProperty(), getRange(), mergeAnnos(annotations));
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
        return AxiomType.OBJECT_PROPERTY_RANGE;
    }


    public OWLSubClassOfAxiom asOWLSubClassOfAxiom() {
        OWLDataFactory df = getOWLDataFactory();
        OWLClassExpression sup = df.getOWLObjectAllValuesFrom(getProperty(), getRange());
        return df.getOWLSubClassOfAxiom(df.getOWLThing(), sup);
    }
}
