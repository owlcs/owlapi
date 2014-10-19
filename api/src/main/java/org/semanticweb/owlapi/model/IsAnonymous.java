package org.semanticweb.owlapi.model;

/** Anonymous or named object interface. */
public interface IsAnonymous {

    /**
     * @return {@code true} if this object is anonymous, {@code false}
     *         otherwise. For example, class expressions are anonymous while
     *         class entities are not (they have an IRI).
     */
    default boolean isAnonymous() {
        return false;
    }
}
