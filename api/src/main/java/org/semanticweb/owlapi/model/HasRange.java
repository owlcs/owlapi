package org.semanticweb.owlapi.model;

/** An interface to objects that have a range.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasRange<R extends OWLObject> {
    /** Gets the range.
     * 
     * @return The range. Not {@code null}. */
    R getRange();
}
