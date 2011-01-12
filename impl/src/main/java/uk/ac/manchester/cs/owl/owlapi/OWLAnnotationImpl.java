package uk.ac.manchester.cs.owl.owlapi;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.util.CollectionFactory;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 19-Dec-2006<br><br>
 */
public class OWLAnnotationImpl extends OWLObjectImpl implements OWLAnnotation {

    private OWLAnnotationProperty property;

    private OWLAnnotationValue value;

    private Set<OWLAnnotation> annotations;

    public OWLAnnotationImpl(OWLDataFactory dataFactory, OWLAnnotationProperty property, OWLAnnotationValue value, Set<? extends OWLAnnotation> annotations) {
        super(dataFactory);
        this.property = property;
        this.value = value;
        this.annotations = CollectionFactory.getCopyOnRequestSet(new TreeSet<OWLAnnotation>(annotations));
    }

    public Set<OWLAnnotation> getAnnotations() {
        return annotations;
    }

    public OWLAnnotationProperty getProperty() {
        return property;
    }


    public OWLAnnotationValue getValue() {
        return value;
    }

    public OWLAnnotation getAnnotatedAnnotation(Set<OWLAnnotation> annotations) {
        if(annotations.isEmpty()) {
            return this;
        }
        Set<OWLAnnotation> merged = new HashSet<OWLAnnotation>(this.annotations);
        merged.addAll(annotations);
        return new OWLAnnotationImpl(getOWLDataFactory(), property, value, merged);
    }

    public boolean isComment() {
        return property.isComment();
    }


    public boolean isLabel() {
        return property.isLabel();
    }

    /**
     * Determines if this annotation is an annotation used to deprecate an IRI.  This is the case if the annotation
     * property has an IRI of <code>owl:deprecated</code> and the value of the annotation is <code>"true"^^xsd:boolean</code>
     * @return <code>true</code> if this annotation is an annotation that can be used to deprecate an IRI, otherwise
     *         <code>false</code>.
     */
    public boolean isDeprecatedIRIAnnotation() {
        return property.isDeprecated() && value instanceof OWLLiteral && ((OWLLiteral) value).isBoolean() && ((OWLLiteral) value).parseBoolean();
    }

    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (obj instanceof OWLAnnotation) {
                OWLAnnotation other = (OWLAnnotation) obj;
                return other.getProperty().equals(property) && other.getValue().equals(value) && other.getAnnotations().equals(annotations);
            }
        }
        return false;
    }

    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotation other = (OWLAnnotation) object;
        int diff = getProperty().compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        else {
            return getValue().compareTo(other.getValue());
        }
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(OWLAnnotationObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAnnotationObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
