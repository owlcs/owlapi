package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 * An interface to objects that have a direct set of imports.
 * </p>
 */
public interface HasDirectImports {

    /**
     * Gets the direct set of imported ontologies.
     * 
     * @return A (possibly empty) set of directly imported ontologies.
     */
    Set<OWLOntology> getDirectImports();
}
