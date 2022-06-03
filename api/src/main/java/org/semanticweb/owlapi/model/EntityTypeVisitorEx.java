package org.semanticweb.owlapi.model;

import java.util.Optional;

/**
 * Value visitor for EntityType
 *
 * @param <T> type to return
 * @author ignazio
 */
public interface EntityTypeVisitorEx<T> {

    /**
     * @param entity entity to visit
     * @return return value
     */
    default Optional<T> visit(EntityType<?> entity) {
        if (entity.equals(EntityType.CLASS)) {
            return visitClass();
        }
        if (entity.equals(EntityType.OBJECT_PROPERTY)) {
            return visitObjectProperty();
        }
        if (entity.equals(EntityType.DATA_PROPERTY)) {
            return visitDataProperty();
        }
        if (entity.equals(EntityType.ANNOTATION_PROPERTY)) {
            return visitAnnotationProperty();
        }
        if (entity.equals(EntityType.NAMED_INDIVIDUAL)) {
            return visitNamedIndividual();
        }
        if (entity.equals(EntityType.DATATYPE)) {
            return visitDatatype();
        }
        return doDefault();
    }

    /**
     * @return default value if no values are matched
     */
    default Optional<T> doDefault() {
        return Optional.empty();
    }

    /**
     * @return value for class
     */
    Optional<T> visitClass();

    /**
     * @return value for object property
     */
    Optional<T> visitObjectProperty();

    /**
     * @return value for data property
     */
    Optional<T> visitDataProperty();

    /**
     * @return value for annotation property
     */
    Optional<T> visitAnnotationProperty();

    /**
     * @return value for individual
     */
    Optional<T> visitNamedIndividual();

    /**
     * @return value for datatype
     */
    Optional<T> visitDatatype();
}
