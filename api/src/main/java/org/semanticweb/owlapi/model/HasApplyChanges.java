package org.semanticweb.owlapi.model;

import java.util.List;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5
 */
public interface HasApplyChanges {

    /**
     * Applies a list ontology changes to some ontologies.
     * 
     * @param changes
     *        The changes to be applied.
     * @return The changes that were actually applied.
     * @throws OWLOntologyChangeException
     *         If one or more of the changes could not be applied.
     */
    List<OWLOntologyChange<?>> applyChanges(
            List<? extends OWLOntologyChange<?>> changes);
}
