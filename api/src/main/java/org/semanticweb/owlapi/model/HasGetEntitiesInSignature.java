package org.semanticweb.owlapi.model;

import java.util.Set;

/** @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public interface HasGetEntitiesInSignature {
    /** @param entityIRI
     *            IRI for all entities to retrieve
     * @return entities with IRI equal to entityIRI */
    Set<OWLEntity> getEntitiesInSignature(IRI entityIRI);
}
