package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 25-Mar-2009
 */
public class OWLSubAnnotationPropertyOfAxiomImpl extends OWLAxiomImpl implements OWLSubAnnotationPropertyOfAxiom {

    private OWLAnnotationProperty subProperty;

    private OWLAnnotationProperty superProperty;


    public OWLSubAnnotationPropertyOfAxiomImpl(OWLDataFactory dataFactory, OWLAnnotationProperty subProperty, OWLAnnotationProperty superProperty, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subProperty = subProperty;
        this.superProperty = superProperty;
    }

    public OWLSubAnnotationPropertyOfAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLSubAnnotationPropertyOfAxiom(getSubProperty(), getSuperProperty(), mergeAnnos(annotations));
    }

    public OWLSubAnnotationPropertyOfAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLSubAnnotationPropertyOfAxiom(getSubProperty(), getSuperProperty());
    }

    public OWLAnnotationProperty getSubProperty() {
        return subProperty;
    }


    public OWLAnnotationProperty getSuperProperty() {
        return superProperty;
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public boolean isLogicalAxiom() {
        return false;
    }

    public boolean isAnnotationAxiom() {
        return true;
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.SUB_ANNOTATION_PROPERTY_OF;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLSubAnnotationPropertyOfAxiom other = (OWLSubAnnotationPropertyOfAxiom) object;
        int diff = subProperty.compareTo(other.getSubProperty());
        if (diff != 0) {
            return diff;
        }
        return superProperty.compareTo(other.getSuperProperty());
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLSubAnnotationPropertyOfAxiom)) {
            return false;
        }
        OWLSubAnnotationPropertyOfAxiom other = (OWLSubAnnotationPropertyOfAxiom) obj;
        return subProperty.equals(other.getSubProperty()) && superProperty.equals(other.getSuperProperty());
    }
}
