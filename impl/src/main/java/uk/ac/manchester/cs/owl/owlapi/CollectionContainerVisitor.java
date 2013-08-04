package uk.ac.manchester.cs.owl.owlapi;

import javax.annotation.Nonnull;

@SuppressWarnings("javadoc")
public interface CollectionContainerVisitor<T> {
    void visit(@Nonnull CollectionContainer<T> c);

    void visitItem(@Nonnull T c);
}
