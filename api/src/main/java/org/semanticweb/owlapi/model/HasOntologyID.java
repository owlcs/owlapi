package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 29/07/2013
 * <p>
 *     An interface to objects that have an {@link OWLOntologyID}.
 * </p>
 */
public interface HasOntologyID {

    /**
     * Gets the {@link OWLOntologyID} belonging to this object.
     *
     * @return The {@link OWLOntologyID}.  Not {@code null}.
     */
    OWLOntologyID getOntologyID();
}
