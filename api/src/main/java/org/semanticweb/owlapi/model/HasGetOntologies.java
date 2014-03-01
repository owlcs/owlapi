package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 24/02/2014
 */
public interface HasGetOntologies {

    /**
     * Gets the (possibly empty) set of
     * {@link org.semanticweb.owlapi.model.OWLOntology} objects contained within
     * this object.
     * 
     * @return The set of ontologies. Possibly empty. Not {@code null}.
     */
    Set<OWLOntology> getOntologies();
}
