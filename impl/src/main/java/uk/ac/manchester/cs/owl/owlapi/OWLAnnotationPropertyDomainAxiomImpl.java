package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
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
public class OWLAnnotationPropertyDomainAxiomImpl extends OWLAxiomImpl implements OWLAnnotationPropertyDomainAxiom {

    private OWLAnnotationProperty property;

    private IRI domain;

    public OWLAnnotationPropertyDomainAxiomImpl(OWLDataFactory dataFactory, OWLAnnotationProperty property, IRI domain, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.domain = domain;
        this.property = property;
    }

    public OWLAnnotationPropertyDomainAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLAnnotationPropertyDomainAxiom(getProperty(), getDomain());
    }

    public OWLAnnotationPropertyDomainAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLAnnotationPropertyDomainAxiom(getProperty(), getDomain(), mergeAnnos(annotations));
    }

    public IRI getDomain() {
        return domain;
    }

    public OWLAnnotationProperty getProperty() {
        return property;
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_PROPERTY_DOMAIN;
    }

    public boolean isLogicalAxiom() {
        return false;
    }

    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationPropertyDomainAxiom other = (OWLAnnotationPropertyDomainAxiom) object;
        int diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return domain.compareTo(other.getDomain());
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
        if (!(obj instanceof OWLAnnotationPropertyDomainAxiom)) {
            return false;
        }
        OWLAnnotationPropertyDomainAxiom other = (OWLAnnotationPropertyDomainAxiom) obj;
        return property.equals(other.getProperty()) && domain.equals(other.getDomain()) && getAnnotations().equals(other.getAnnotations());
    }
}
