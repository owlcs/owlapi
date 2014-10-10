package uk.ac.manchester.cs.owl.owlapi;

/**
 * Created by ses on 10/10/14.
 */
public interface HasTrimToSize {

    /**
     * Trim the capacity of the axiom indexes. An application can use this
     * operation to minimize the storage of the index instance.
     */
    void trimToSize();
}
