package org.semanticweb.owlapi.model;

/**
 * An interface to objects that have an {@link OWLOntologyID}.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface HasOntologyID {

    /**
     * Gets the {@link OWLOntologyID} belonging to this object.
     * 
     * @return The {@link OWLOntologyID}. Not {@code null}.
     */
    OWLOntologyID getOntologyID();
}
