package org.semanticweb.owlapi.model;

/** An interface to objects that have objects (e.g. property assertion axioms).
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasObject<O> {
    /** Gets the object.
     * 
     * @return The object. Not {@code null}. */
    O getObject();
}
