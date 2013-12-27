package uk.ac.manchester.cs.owl.owlapi;

import javax.annotation.Nonnull;

/** @author ignazio
 * @param <T>
 *            collection type */
public interface CollectionContainer<T> {
    /** @param t
     *            visitor to accept */
    void accept(@Nonnull CollectionContainerVisitor<T> t);
}
