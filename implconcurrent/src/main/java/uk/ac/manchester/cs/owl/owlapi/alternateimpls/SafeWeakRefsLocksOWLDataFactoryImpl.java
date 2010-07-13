package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class SafeWeakRefsLocksOWLDataFactoryImpl extends OWLDataFactoryImpl {
	private static SafeWeakRefsLocksOWLDataFactoryImpl instance = new SafeWeakRefsLocksOWLDataFactoryImpl();

	public static SafeWeakRefsLocksOWLDataFactoryImpl getInstance() {
		return instance;
	}
	public SafeWeakRefsLocksOWLDataFactoryImpl() {
		//data=new ConcurrentHashMapStrongReferencesOWLDataFactoryInternalsImpl(this);
		data=new LockingWeakReferencesOwlDataFactoryInternalsImpl(this);
	}
}
