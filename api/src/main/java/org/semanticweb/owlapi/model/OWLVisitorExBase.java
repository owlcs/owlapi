package org.semanticweb.owlapi.model;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Base interface for visitors that return a value.
 * 
 * @param <O>
 *        return value
 */
@ParametersAreNonnullByDefault
public interface OWLVisitorExBase<O> {

    /**
     * Gets the default return value for this visitor. By default, the default
     * is {@code null}
     * 
     * @param object
     *        The object that was visited.
     * @param <T>
     *        type visited
     * @return The default return value
     */
    @SuppressWarnings("null")
    default <T> O doDefault(@SuppressWarnings("unused") T object) {
        // no other way to provide a default implementation
        return null;
    }
}
