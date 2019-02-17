package org.semanticweb.owlapi6.impl;

/**
 * Created by ses on 10/10/14.
 */
@FunctionalInterface
public interface HasTrimToSize {

    /**
     * Trim the capacity of the axiom indexes. An application can use this operation to minimize the
     * storage of the index instance.
     */
    void trimToSize();
}
