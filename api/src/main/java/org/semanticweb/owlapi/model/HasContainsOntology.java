package org.semanticweb.owlapi.model;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 24/02/2014
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
    boolean contains(OWLOntologyID id);
}
