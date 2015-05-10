package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** Convert to OWLDataProperty interface. */
public interface AsOWLDataProperty {

    /**
     * A convenience method that determines if this entity is an
     * OWLDataProperty.
     * 
     * @return {@code true} if this entity is an OWLDataProperty, otherwise
     *         {@code false}
     */
    default boolean isOWLDataProperty() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLDataProperty (in
     * order to avoid explicit casting).
     * 
     * @return The entity as an OWLDataProperty.
     * @throws OWLRuntimeException
     *         if this entity is not an OWLDataProperty (check with the
     *         isOWLDataProperty method first).
     */
    @Nonnull
    default OWLDataProperty asOWLDataProperty() {
        if (isOWLDataProperty()) {
            return (OWLDataProperty) this;
        }
        throw new ClassCastException(getClass().getName()
            + "is not an OWLDataProperty");
    }
}
