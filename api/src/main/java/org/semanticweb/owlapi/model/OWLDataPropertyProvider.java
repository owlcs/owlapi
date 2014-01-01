package org.semanticweb.owlapi.model;

import java.io.Serializable;

import javax.annotation.Nonnull;

/** @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 07/08/2013 */
public interface OWLDataPropertyProvider extends Serializable {
    /** Gets an instance of {@link OWLDataProperty} that has the specified
     * {@code IRI}.
     * 
     * @param iri
     *            The IRI. Not {@code null}.
     * @return An {@link OWLDataProperty} that has the specified IRI. Not
     *         {@code null}. */
    @Nonnull
    OWLDataProperty getOWLDataProperty(@Nonnull IRI iri);
}
