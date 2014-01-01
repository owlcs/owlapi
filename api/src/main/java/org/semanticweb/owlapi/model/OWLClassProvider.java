package org.semanticweb.owlapi.model;

import java.io.Serializable;

import javax.annotation.Nonnull;

/** @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 07/08/2013 */
public interface OWLClassProvider extends Serializable {
    /** Gets an instance of {@link OWLClass} that has the specified {@code IRI}.
     * 
     * @param iri
     *            The IRI. Not {@code null}.
     * @return An {@link OWLClass} that has the specified IRI. Not {@code null}. */
    @Nonnull
    OWLClass getOWLClass(@Nonnull IRI iri);
}
