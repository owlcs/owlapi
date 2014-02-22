package org.semanticweb.owlapi.model;

import java.util.Set;

/** An interface to objects that provide an imports closure of themselves
 * 
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasImportsClosure {
    /** Gets the imports closure
     * 
     * @return A set of ontology representing the imports closure of this object
     *         (includes this object). Not empty and not {@code null}. */
    Set<OWLOntology> getImportsClosure();
}
