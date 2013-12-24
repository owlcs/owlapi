package org.semanticweb.owlapi.model;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 07/08/2013
 */
public interface OWLEntityByTypeProvider {

    /**
     * Gets an entity that has the specified IRI and is of the specified type.
     * @param entityType The type of the entity that will be returned. Not {@code null}.
     * @param iri The IRI of the entity that will be returned. Not {@code null}.
     * @return An entity that has the same IRI as this entity and is of the
     *         specified type.  Not {@code null}.
     */
    <E extends OWLEntity> E getOWLEntity(EntityType<E> entityType, IRI iri);
}
