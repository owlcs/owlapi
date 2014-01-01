package org.semanticweb.owlapi.model;

import javax.annotation.Nonnull;

/** @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 07/08/2013 */
public interface OWLEntityByTypeProvider {
    /** Gets an entity that has the specified IRI and is of the specified type.
     * 
     * @param entityType
     *            The type of the entity that will be returned. Not {@code null}
     *            .
     * @param iri
     *            The IRI of the entity that will be returned. Not {@code null}.
     * @param <E>
     *            type class
     * @return An entity that has the same IRI as this entity and is of the
     *         specified type. Not {@code null}. */
    @Nonnull
    <E extends OWLEntity> E getOWLEntity(@Nonnull EntityType<E> entityType,
            @Nonnull IRI iri);
}
