package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

public interface RaceCallback {
	void add();

	boolean failed();

	void diagnose();
	void race();
}
