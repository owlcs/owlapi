package org.semanticweb.owlapi.model;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;

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
            return visitCLASS();
        }
        if (entity.equals(EntityType.OBJECT_PROPERTY)) {
            return visitOBJECT_PROPERTY();
        }
        if (entity.equals(EntityType.DATA_PROPERTY)) {
            return visitDATA_PROPERTY();
        }
        if (entity.equals(EntityType.ANNOTATION_PROPERTY)) {
            return visitANNOTATION_PROPERTY();
        }
        if (entity.equals(EntityType.NAMED_INDIVIDUAL)) {
            return visitNAMED_INDIVIDUAL();
        }
        if (entity.equals(EntityType.DATATYPE)) {
            return visitDATATYPE();
        }
        return doDefault();
    }

    /**
     * @return default value if no values are matched
     */
    default Optional<T> doDefault() {
        return emptyOptional();
    }

    /**
     * @return value for class
     */
    Optional<T> visitCLASS();

    /**
     * @return value for object property
     */
    Optional<T> visitOBJECT_PROPERTY();

    /**
     * @return value for data property
     */
    Optional<T> visitDATA_PROPERTY();

    /**
     * @return value for annotation property
     */
    Optional<T> visitANNOTATION_PROPERTY();

    /**
     * @return value for individual
     */
    Optional<T> visitNAMED_INDIVIDUAL();

    /**
     * @return value for datatype
     */
    Optional<T> visitDATATYPE();
}
