package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2006<br><br>
 */
public class OWLAnnotationAssertionAxiomImpl extends OWLAxiomImpl implements OWLAnnotationAssertionAxiom {

    private OWLAnnotationSubject subject;

    private OWLAnnotationProperty property;

    private OWLAnnotationValue value;

    public OWLAnnotationAssertionAxiomImpl(OWLDataFactory dataFactory, OWLAnnotationSubject subject, OWLAnnotationProperty property, OWLAnnotationValue value, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.subject = subject;
        this.property = property;
        this.value = value;
    }

    public OWLAnnotationAssertionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getOWLAnnotationAssertionAxiom(getProperty(), getSubject(), getValue());
    }

    /**
     * Determines if this annotation assertion deprecates the IRI that is the subject of the annotation.
     * @return <code>true</code> if this annotation assertion deprecates the subject IRI of the assertion, otherwise
     *         <code>false</code>.
     * @see {@link org.semanticweb.owlapi.model.OWLAnnotation#isDeprecatedIRIAnnotation()}
     */
    public boolean isDeprecatedIRIAssertion() {
        return property.isDeprecated() && getAnnotation().isDeprecatedIRIAnnotation();
    }

    public OWLAnnotationAssertionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getOWLAnnotationAssertionAxiom(getProperty(), getSubject(), getValue(), mergeAnnos(annotations));
    }

    public OWLAnnotationValue getValue() {
        return value;
    }

    public OWLAnnotationSubject getSubject() {
        return subject;
    }

    public OWLAnnotationProperty getProperty() {
        return property;
    }

    public OWLAnnotation getAnnotation() {
        return getOWLDataFactory().getOWLAnnotation(property, value);
    }

    public boolean isLogicalAxiom() {
        return false;
    }

    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationAssertionAxiom other = (OWLAnnotationAssertionAxiom) object;
        int diff = 0;
        diff = subject.compareTo(other.getSubject());
        if (diff != 0) {
            return diff;
        }
        diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return value.compareTo(other.getValue());
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_ASSERTION;
    }


    @Override
	public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OWLAnnotationAssertionAxiom)) {
            return false;
        }
        OWLAnnotationAssertionAxiom other = (OWLAnnotationAssertionAxiom) obj;
        return subject.equals(other.getSubject()) && property.equals(other.getProperty()) && value.equals(other.getValue()) && getAnnotations().equals(other.getAnnotations());
    }


}
