package uk.ac.manchester.cs.owl.owlapi;

import javax.annotation.Nonnull;

@SuppressWarnings("javadoc")
public interface CollectionContainer<T> {
    void accept(@Nonnull CollectionContainerVisitor<T> t);
}
