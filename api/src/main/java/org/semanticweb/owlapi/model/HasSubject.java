package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 */
public interface HasSubject<T extends OWLObject> {

    /**
     * Gets the subject of this object.
     * @return The subject.  Not {@code null}.
     */
    T getSubject();
}
