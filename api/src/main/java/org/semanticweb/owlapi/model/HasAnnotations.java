package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * An interface to an object that has annotation.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasAnnotations {

    /**
     * Gets the annotations on this object.
     * 
     * @return A set of annotations on this object. The set returned will be a
     *         copy, changes will not be reflected to the original set.
     */
    Set<OWLAnnotation> getAnnotations();
}
