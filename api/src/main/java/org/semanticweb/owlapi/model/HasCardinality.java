package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 * An interface to objects that have a cardinality.
 * </p>
 */
public interface HasCardinality {

    /**
     * Gets the cardinality.
     * 
     * @return The cardinality. A non-negative integer.
     */
    int getCardinality();
}
