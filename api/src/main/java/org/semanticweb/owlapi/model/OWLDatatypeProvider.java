package org.semanticweb.owlapi.model;

import java.io.Serializable;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface OWLDatatypeProvider extends Serializable {

    /**
     * Gets an instance of {@link OWLDatatype} that has the specified
     * {@code IRI}.
     * 
     * @param iri
     *        The IRI. Not {@code null}.
     * @return An {@link OWLDatatype} that has the specified IRI. Not
     *         {@code null}.
     */
    OWLDatatype getOWLDatatype(IRI iri);
}
