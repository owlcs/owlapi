package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 07/08/2013 */
public interface OWLAnnotationPropertyProvider {
    /** Gets an instance of {@link OWLAnnotationProperty} that has the specified
     * {@code IRI}.
     * 
     * @param iri
     *            The IRI. Not {@code null}.
     * @return An {@link OWLAnnotationProperty} that has the specified IRI. Not
     *         {@code null}. */
    @Nonnull
    OWLAnnotationProperty getOWLAnnotationProperty(@Nonnull IRI iri);
}
