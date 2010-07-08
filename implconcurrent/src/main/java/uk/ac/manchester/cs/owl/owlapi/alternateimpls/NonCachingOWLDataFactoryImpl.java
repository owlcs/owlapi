package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class NonCachingOWLDataFactoryImpl extends OWLDataFactoryImpl {
	private static NonCachingOWLDataFactoryImpl instance = new NonCachingOWLDataFactoryImpl();

	public static NonCachingOWLDataFactoryImpl getInstance() {
		return instance;
	}

	public NonCachingOWLDataFactoryImpl() {
		data = new NonCachingOWLDataFactoryInternals(this);
	}
}
