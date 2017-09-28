package org.semanticweb.owlapi.model;

import java.util.stream.Stream;

/**
 * An interface for objects that have a set of components - this is useful for
 * all those situations where a generic action has to be performed on all
 * components of an object, such as hashcode and equals computations, or
 * rendering in a syntax.
 */
public interface HasComponents {

    /**
     * @return components as a stream.
     * The stream is ordered (by visit order) but not sorted. Implementations that override 
     * components() must ensure the order is compatible with equals() and hashCode().
     */
    Stream<?> components();

    /**
     * @return components as a stream; for objects that can have annotations on them, annotation
     * streams appear first. This is useful in renderers.
     * The stream is ordered (by visit order) but not sorted. Implementations that override 
     * components() must ensure the order is compatible with equals() and hashCode().
     */
    default Stream<?> componentsAnnotationsFirst() {
        return components();
    }

    /**
     * @return components as a stream; for objects that can have annotations on them, these are
     * skipped. This is useful for comparing axioms without taking annotations into account. Note:
     * annotations on nested objects are not affected.
     * The stream is ordered (by visit order) but not sorted. Implementations that override 
     * components() must ensure the order is compatible with equals() and hashCode().
     */
    default Stream<?> componentsWithoutAnnotations() {
        return components();
    }
}
