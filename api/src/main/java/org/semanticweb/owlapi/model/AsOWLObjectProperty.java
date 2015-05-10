package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** Convert to OWLObjectProperty interface. */
public interface AsOWLObjectProperty {

    /**
     * A convenience method that determines if this entity is an
     * OWLObjectProperty.
     * 
     * @return {@code true} if this entity is an OWLObjectProperty, otherwise
     *         {@code false}
     */
    default boolean isOWLObjectProperty() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLObjectProperty (in
     * order to avoid explicit casting).
     * 
     * @return The entity as an OWLObjectProperty.
     * @throws OWLRuntimeException
     *         if this entity is not an OWLObjectProperty (check with the
     *         isOWLObjectProperty method first).
     */
    @Nonnull
    default OWLObjectProperty asOWLObjectProperty() {
        if (isOWLObjectProperty()) {
            return (OWLObjectProperty) this;
        }
        throw new ClassCastException(getClass().getName()
            + "is not an OWLObjectProperty");
    }
}
