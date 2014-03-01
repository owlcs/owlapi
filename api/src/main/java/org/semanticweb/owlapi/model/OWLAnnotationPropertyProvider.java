package org.semanticweb.owlapi.model;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 29/07/2013
 */
public interface OWLAnnotationPropertyProvider {

    /**
     * Gets an instance of {@link OWLAnnotationProperty} that has the specified
     * {@code IRI}.
     * 
     * @param iri
     *        The IRI. Not {@code null}.
     * @return An {@link OWLAnnotationProperty} that has the specified IRI. Not
     *         {@code null}.
     */
    OWLAnnotationProperty getOWLAnnotationProperty(IRI iri);
}
