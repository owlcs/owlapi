package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 * An interface to an object that has a short form (human readable short name
 * e.g. owl:Class as opposed to the complete IRI).
 * </p>
 * 
 * @since 3.5
 */
public interface HasShortForm {

    /**
     * Gets the short form.
     * 
     * @return A string that represents the short form. Not {@code null}.
     */
    String getShortForm();
}
