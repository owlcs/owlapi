package uk.ac.manchester.cs.owl.owlapi;

@SuppressWarnings("javadoc")
public interface CollectionContainer<T> {
    void accept(CollectionContainerVisitor<T> t);
}
