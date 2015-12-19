package org.semanticweb.owlapi.model;

/**
 * Interface for types with an index; this is used to group objects by type when
 * sorting.
 */
public interface HasIndex {

    /**
     * @return index for this type. This is not a hashcode for instances, rather
     *         a hashcode for the types.
     */
    int typeIndex();
}
