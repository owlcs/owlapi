package org.semanticweb.owlapi.model;

import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Dec-2006<br><br>
 * <p/>
 * Annotations are used in the various types of annotation axioms, which
 * bind annotations to their subjects (i.e. axioms or declarations).
 * <p/>
 * An annotation is equal to another annotation if both objects have equal
 * annotation URIs and have equal annotation values
 */
public interface OWLAnnotation extends OWLObject {

    /**
     * Gets the property that this annotation acts along
     *
     * @return The annotation property
     */
    OWLAnnotationProperty getProperty();

    /**
     * Gets the annotation value.  The type of value will depend upon
     * the type of the annotation e.g. whether the annotation is an {@link org.semanticweb.owlapi.model.OWLLiteral},
     * an {@link org.semanticweb.owlapi.model.IRI} or an {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual}.
     * @see org.semanticweb.owlapi.model.OWLAnnotationValueVisitor
     * @see org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx
     * @return The annotation value.
     */
    OWLAnnotationValue getValue();


    /**
     * Determines if this annotation is an annotation used to deprecate an IRI.  This is the case if the annotation
     * property has an IRI of <code>owl:deprecated</code> and the value of the annotation is <code>"true"^^xsd:boolean</code>
     * @return <code>true</code> if this annotation is an annotation that can be used to deprecate an IRI, otherwise
     * <code>false</code>.
     */
    boolean isDeprecatedIRIAnnotation();


    /**
     * Gets the annotations on this annotation
     *
     * @return A (possibly empty) set of annotations that annotate this annotation
     */
    Set<OWLAnnotation> getAnnotations();

    /**
     * Gets an OWLAnnotation which is a copy of this annotation but which has the specified annotations.
     * @param annotations The annotations
     * @return A copy of this annotation with the specified annotations annotating it
     */
    OWLAnnotation getAnnotatedAnnotation(Set<OWLAnnotation> annotations);

    void accept(OWLAnnotationObjectVisitor visitor);

    <O> O accept(OWLAnnotationObjectVisitorEx<O> visitor);

}
