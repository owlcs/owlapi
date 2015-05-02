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
     * @param object
     *        object to visit
     * @param <T>
     *        type visited
     * @return default value
     * @deprecated use doDefault() instead
     */
    @Deprecated
    default <T> O getDefaultReturnValue(T object) {
        return doDefault(object);
    }

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
    default <T> O doDefault(@SuppressWarnings("unused") T object) {
        return null;
    }
}
