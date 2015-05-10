package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** Convert to OWLClass interface. */
public interface AsOWLClass {

    /**
     * A convenience method that determines if this entity is an OWLClass.
     * 
     * @return {@code true} if this entity is an OWLClass, otherwise
     *         {@code false}
     */
    default boolean isOWLClass() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLClass (in order to
     * avoid explicit casting).
     * 
     * @return The entity as an OWLClass.
     * @throws OWLRuntimeException
     *         if this entity is not an OWLClass (check with the isOWLClass
     *         method first).
     */
    @Nonnull
    default OWLClass asOWLClass() {
        if (isOWLClass()) {
            return (OWLClass) this;
        }
        throw new ClassCastException(getClass().getName()
            + "is not an OWLClass");
    }
}
