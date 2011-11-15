package uk.ac.manchester.cs.owl.owlapi;

public interface CollectionContainerVisitor<T> {
	void visit(CollectionContainer<T> c);
	void visitItem(T c);
}
