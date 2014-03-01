package org.semanticweb.owlapi.model;

import java.io.Serializable;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface OWLNamedIndividualProvider extends Serializable {

    /**
     * Gets an instance of {@link OWLNamedIndividual} that has the specified
     * {@code IRI}.
     * 
     * @param iri
     *        The IRI. Not {@code null}.
     * @return An {@link OWLNamedIndividual} that has the specified IRI. Not
     *         {@code null}.
     */
    OWLNamedIndividual getOWLNamedIndividual(IRI iri);
}
