package org.semanticweb.owlapi.model;

/**
 * An interface for things that have an exact mapping by name. This interface
 * exists to allow enumerations to be used easily in ConfigurationOptions.
 * 
 * @param <T>
 *        type for the interface
 */
public interface ByName<T> {

    /**
     * @param name
     *        the name to map to an object
     * @return the object associated with name
     */
    T byName(CharSequence name);
}
