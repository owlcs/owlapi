package uk.ac.manchester.cs.owl.owlapi;

public interface CollectionContainer<T> {
	void accept(CollectionContainerVisitor<T> t);
}
