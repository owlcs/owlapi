package org.semanticweb.owlapi.model;

/** An interface to objects that have a cardinality.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasCardinality {
    /** Gets the cardinality.
     * 
     * @return The cardinality. A non-negative integer. */
    int getCardinality();
}
