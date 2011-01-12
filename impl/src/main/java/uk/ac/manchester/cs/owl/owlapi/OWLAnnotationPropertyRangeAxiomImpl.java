package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/**
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 17-Jan-2009
 */
public class OWLAnnotationPropertyRangeAxiomImpl extends OWLAxiomImpl implements OWLAnnotationPropertyRangeAxiom {

    private OWLAnnotationProperty property;

    protected IRI range;

    public OWLAnnotationPropertyRangeAxiomImpl(OWLDataFactory dataFactory, OWLAnnotationProperty property, IRI range, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.property = property;
        this.range = range;
    }

    public OWLAnnotationPropertyRangeAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLAnnotationPropertyRangeAxiom(getProperty(), getRange());
    }

    public OWLAnnotationPropertyRangeAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLAnnotationPropertyRangeAxiom(getProperty(), getRange(), mergeAnnos(annotations));
    }

    public OWLAnnotationProperty getProperty() {
        return property;
    }

    public IRI getRange() {
        return range;
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_PROPERTY_RANGE;
    }

    public boolean isLogicalAxiom() {
        return false;
    }

    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) object;
        int diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return range.compareTo(other.getRange());
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
        if (!(obj instanceof OWLAnnotationPropertyRangeAxiom)) {
            return false;
        }
        OWLAnnotationPropertyRangeAxiom other = (OWLAnnotationPropertyRangeAxiom) obj;
        return property.equals(other.getProperty()) && range.equals(other.getRange()) && getAnnotations().equals(other.getAnnotations());
    }
}
