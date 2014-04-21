package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5
 */
public interface HasGetOntologies {

    /**
     * Gets the (possibly empty) set of
     * {@link org.semanticweb.owlapi.model.OWLOntology} objects contained within
     * this object.
     * 
     * @return The set of ontologies. Possibly empty. Not {@code null}.
     */
    @Nonnull
    Set<OWLOntology> getOntologies();
}
