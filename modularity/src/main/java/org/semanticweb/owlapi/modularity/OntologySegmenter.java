package org.semanticweb.owlapi.modularity;

import org.semanticweb.owlapi.model.*;

import java.util.Set;

/**
 * An interface for any class implementing ontology segmentation or modularisation.
 *
 * @author Thomas Schneider
 * @author School of Computer Science
 * @author University of Manchester
 */
public interface OntologySegmenter {

    /**
     * Returns a set of axioms that is a segment of the ontology associated with this segmenter.
     *
     * @param signature the seed signature (set of entities) for the segment
     * @return the segment as a set of axioms
     */
    public abstract Set<OWLAxiom> extract(Set<OWLEntity> signature);


    /**
     * Returns an ontology that is a segment of the ontology associated with this segmenter.
     *
     * @param signature the seed signature (set of entities) for the module
     * @param uri       the URI for the module
     * @return the module, having the specified URI
     * @throws OWLOntologyChangeException   if adding axioms to the module fails
     * @throws OWLOntologyCreationException if the module cannot be created
     */

    public abstract OWLOntology extractAsOntology(Set<OWLEntity> signature, IRI uri) throws OWLOntologyCreationException;
}
