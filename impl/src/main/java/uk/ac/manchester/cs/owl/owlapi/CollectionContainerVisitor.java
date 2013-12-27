package uk.ac.manchester.cs.owl.owlapi;

import javax.annotation.Nonnull;

/** @author ignazio
 * @param <T>
 *            collection type */
public interface CollectionContainerVisitor<T> {
    /** @param c
     *            collection to visit */
    void visit(@Nonnull CollectionContainer<T> c);

    /** @param c
     *            item to visit */
    void visitItem(@Nonnull T c);
}
