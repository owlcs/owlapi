package org.semanticweb.owlapi.model;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5
 */
public interface HasGetOntologyById {

    /**
     * Get the ontology with the specified Id.
     * 
     * @param ontologyID
     *        The Id. Not {@code null}.
     * @return The ontology with the specified Id, or {@code null} if there is
     *         no ontology with the specified Id.
     */
    OWLOntology getOntology(OWLOntologyID ontologyID);
}
