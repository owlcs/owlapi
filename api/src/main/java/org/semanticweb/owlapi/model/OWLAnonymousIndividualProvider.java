package org.semanticweb.owlapi.model;

import java.io.Serializable;

/**
 * An interface to an object that can provide instances if
 * {@link OWLAnonymousIndividual}.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 * @since 3.5
 * @see OWLDataFactory
 */
public interface OWLAnonymousIndividualProvider extends Serializable {

    /**
     * Gets an {@link OWLAnonymousIndividual}. The {@link NodeID} for the
     * individual will be generated automatically. Successive invocations of
     * this method (on this object) will result in instances of
     * {@link OWLAnonymousIndividual} that do not have {@link NodeID}s that have
     * been used previously.
     * 
     * @return The instance of {@link OWLAnonymousIndividual}. Not {@code null}.
     * @see OWLAnonymousIndividualByIdProvider
     */
    OWLAnonymousIndividual getOWLAnonymousIndividual();
}
