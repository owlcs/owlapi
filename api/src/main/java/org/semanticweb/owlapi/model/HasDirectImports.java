package org.semanticweb.owlapi.model;

import java.util.Set;

/** An interface to objects that have a direct set of imports.
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasDirectImports {
    /** Gets the direct set of imported ontologies.
     * 
     * @return A (possibly empty) set of directly imported ontologies. */
    Set<OWLOntology> getDirectImports();
}
