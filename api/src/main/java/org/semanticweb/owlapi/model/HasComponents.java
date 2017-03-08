package org.semanticweb.owlapi.model;

import java.util.stream.Stream;

/**
 * An interface for objects that have a set of components - this is useful for all those situations
 * where a generic action has to be performed on all components of an object, such as hashcode and
 * equals computations, or rendering in a syntax.
 */
public interface HasComponents {

    /**
     * @return components as a stream
     */
    Stream<?> components();

    /**
     * @return components as a stream; for objects that can have annotations on them, annotation
     * streams appear first. This is useful in renderers.
     */
    default Stream<?> componentsAnnotationsFirst() {
        return components();
    }

    /**
     * @return components as a stream; for objects that can have annotations on them, these are
     * skipped. This is useful for comparing axioms without taking annotations into account. Note:
     * annotations on nested objects are not affected.
     */
    default Stream<?> componentsWithoutAnnotations() {
        return components();
    }
}
