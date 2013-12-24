package org.semanticweb.owlapi.model;

import java.io.Serializable;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 07/08/2013
 */
public interface OWLNamedIndividualProvider extends Serializable {

    /**
     * Gets an instance of {@link OWLNamedIndividual} that has the specified {@code IRI}.
     * @param iri The IRI.  Not {@code null}.
     * @return An {@link OWLNamedIndividual} that has the specified IRI.  Not {@code null}.
     */
    OWLNamedIndividual getOWLNamedIndividual(IRI iri);
}
