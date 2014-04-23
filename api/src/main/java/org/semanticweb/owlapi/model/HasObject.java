package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 * An interface to objects that have objects (e.g. property assertion axioms).
 * </p>
 * 
 * @param <O>
 *        object type
 */
public interface HasObject<O> {

    /**
     * Gets the object.
     * 
     * @return The object. Not {@code null}.
     */
    O getObject();
}
