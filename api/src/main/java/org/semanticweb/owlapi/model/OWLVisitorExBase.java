package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/**
 * Base interface for visitors that return a value.
 * 
 * @param <O>
 *        return value
 */
public interface OWLVisitorExBase<O> {

    /**
     * @param object
     *        object to visit
     * @return default value
     * @deprecated use doDefault() instead
     */
    @Deprecated
    @Nonnull
    default O getDefaultReturnValue(@Nonnull Object object) {
        return doDefault(object);
    }

    /**
     * Gets the default return value for this visitor. By default, the default
     * is {@code null}
     * 
     * @param object
     *        The object that was visited.
     * @return The default return value
     */
    @Nonnull
    default O doDefault(@SuppressWarnings("unused") @Nonnull Object object) {
        return null;
    }
}
