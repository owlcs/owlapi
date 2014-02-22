package org.semanticweb.owlapi.model;

/** An interface to an object that has a short form (human readable short name
 * e.g. owl:Class as opposed to the complete IRI).
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasShortForm {
    /** Gets the short form.
     * 
     * @return A string that represents the short form. Not {@code null}. */
    String getShortForm();
}
