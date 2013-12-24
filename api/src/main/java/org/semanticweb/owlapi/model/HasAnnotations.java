package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to an object that has annotation.
 * </p>
 */
public interface HasAnnotations {

    /**
     * Gets the annotations on this object.
     *
     * @return A set of annotations on this object.  The set returned will be a copy - modifying the set will have
     *         no effect on the annotations in this object, similarly, any changes that affect the annotations on this
     *         object will not change the returned set.
     */
    Set<OWLAnnotation> getAnnotations();
}
