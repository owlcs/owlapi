package org.semanticweb.owlapi.model;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/02/2014
 * <p>
 * An interface to objects that provide an imports closure of themselves
 * </p>
 */
public interface HasImportsClosure {

    /**
     * Gets the imports closure
     * 
     * @return A set of ontology representing the imports closure of this object
     *         (includes this object). Not empty and not {@code null}.
     */
    Set<OWLOntology> getImportsClosure();
}
