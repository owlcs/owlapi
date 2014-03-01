package uk.ac.manchester.cs.owl.owlapi;

/**
 * @author ignazio
 * @param <T>
 *        collection type
 */
public interface CollectionContainerVisitor<T> {

    /**
     * @param c
     *        collection to visit
     */
    void visit(CollectionContainer<T> c);

    /**
     * @param c
     *        item to visit
     */
    void visitItem(T c);
}
