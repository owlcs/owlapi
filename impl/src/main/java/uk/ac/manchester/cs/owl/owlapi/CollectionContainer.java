package uk.ac.manchester.cs.owl.owlapi;

/**
 * @author ignazio
 * @param <T>
 *        collection type
 */
public interface CollectionContainer<T> {

    /**
     * @param t
     *        visitor to accept
     */
    void accept(CollectionContainerVisitor<T> t);
}
