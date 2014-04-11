package org.semanticweb.owlapi.model;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 * @param <T>
 *        subject type
 */
public interface HasSubject<T extends OWLObject> {

    /**
     * Gets the subject of this object.
     * 
     * @return The subject. Not {@code null}.
     */
    T getSubject();
}
