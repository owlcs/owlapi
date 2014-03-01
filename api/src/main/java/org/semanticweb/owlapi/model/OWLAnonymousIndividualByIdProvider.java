package org.semanticweb.owlapi.model;

import java.io.Serializable;

/**
 * An interface to an object that can provide instances of
 * {@link OWLAnonymousIndividual}.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface OWLAnonymousIndividualByIdProvider extends Serializable {

    /**
     * Gets an {@link OWLAnonymousIndividual} that has a specific {@link NodeID}
     * .
     * 
     * @param nodeId
     *        A String that represents the {@link NodeID} of the generated
     *        {@link OWLAnonymousIndividual}. Not {@code null}. Note:
     *        {@code nodeId} will be prefixed with "_:" if it is not specified
     *        with an "_:" prefix.
     * @return An instance of {@link OWLAnonymousIndividual}
     */
    OWLAnonymousIndividual getOWLAnonymousIndividual(String nodeId);
}
