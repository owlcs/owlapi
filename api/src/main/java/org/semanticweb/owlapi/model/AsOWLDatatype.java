package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** Convert to OWLDatatype interface. */
public interface AsOWLDatatype {

    /**
     * A convenience method that determines if this entity is an OWLDatatype.
     * 
     * @return {@code true} if this entity is an OWLDatatype, otherwise
     *         {@code false}
     */
    default boolean isOWLDatatype() {
        return false;
    }

    /**
     * A convenience method that obtains this entity as an OWLDatatype (in order
     * to avoid explicit casting).
     * 
     * @return The entity as an OWLDatatype.
     * @throws OWLRuntimeException
     *         if this entity is not an OWLDatatype (check with the
     *         isOWLDatatype method first).
     */
    @Nonnull
    default OWLDatatype asOWLDatatype() {
        if (isOWLDatatype()) {
            return (OWLDatatype) this;
        }
        throw new ClassCastException(getClass().getName()
            + "is not an OWLDatatype");
    }
}
