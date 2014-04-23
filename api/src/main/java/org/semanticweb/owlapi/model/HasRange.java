package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 * An interface to objects that have a range.
 * </p>
 * 
 * @param <R>
 *        range type
 */
public interface HasRange<R extends OWLObject> {

    /**
     * Gets the range.
     * 
     * @return The range. Not {@code null}.
     */
    R getRange();
}
