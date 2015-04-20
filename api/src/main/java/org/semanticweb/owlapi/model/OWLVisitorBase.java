package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/**
 * Base interface for visitors.
 */
public interface OWLVisitorBase {

    /**
     * @param object
     *        object to visit
     * @deprecated use doDefault() instead
     */
    @Deprecated
    default void getDefaultReturnValue(@Nonnull Object object) {
        doDefault(object);
    }

    /**
     * Default action for the visitor.
     * 
     * @param object
     *        The object that was visited.
     */
    default void doDefault(@SuppressWarnings("unused") @Nonnull Object object) {}

    /**
     * default behaviour. Override this method to change the behaviour of all
     * non overridden methods.
     * 
     * @param c
     *        default parameter
     * @deprecated use doDefault instead.
     */
    @Deprecated
    default void handleDefault(Object c) {
        doDefault(c);
    }
}
