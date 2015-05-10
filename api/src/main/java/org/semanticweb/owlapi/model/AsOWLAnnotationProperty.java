package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** Convert to OWLAnnotationProperty interface. */
public interface AsOWLAnnotationProperty {

    /**
     * A convenience method that determines if this entity is an
     * OWLAnnotationProperty.
     * 
     * @return {@code true} if this entity is an OWLAnnotationProperty,
     *         otherwise {@code false}
     */
    default boolean isOWLAnnotationProperty() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLAnnotationProperty
     * (in order to avoid explicit casting).
     * 
     * @return The entity as an OWLAnnotationProperty.
     * @throws OWLRuntimeException
     *         if this entity is not an OWLAnnotationProperty
     */
    @Nonnull
    default OWLAnnotationProperty asOWLAnnotationProperty() {
        if (isOWLAnnotationProperty()) {
            return (OWLAnnotationProperty) this;
        }
        throw new ClassCastException(getClass().getName()
            + "is not an OWLAnnotationProperty");
    }
}
