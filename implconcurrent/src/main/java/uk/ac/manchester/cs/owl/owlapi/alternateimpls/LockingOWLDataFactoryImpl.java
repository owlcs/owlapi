package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class LockingOWLDataFactoryImpl extends OWLDataFactoryImpl {
	private static LockingOWLDataFactoryImpl instance = new LockingOWLDataFactoryImpl();

	public static LockingOWLDataFactoryImpl getInstance() {
		return instance;
	}
	public LockingOWLDataFactoryImpl() {
		data=new FastLockingOWLDataFactoryInternals(this);
	}
}
