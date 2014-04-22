package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5
 */
public interface HasContainsOntology {

    /**
     * Determines if this object contains an ontology that has the specified
     * {@link org.semanticweb.owlapi.model.OWLOntologyID}.
     * 
     * @param id
     *        The {@link OWLOntologyID} to test for. Not {@code null}.
     * @return {@code true} if this object contains an ontology that has the
     *         specified Id, otherwise, {@code false}.
     */
    boolean contains(@Nonnull OWLOntologyID id);
}
