package uk.ac.manchester.cs.owl.owlapi.alternateimpls;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class SafeStrongRefsOWLDataFactoryImpl extends OWLDataFactoryImpl {
	private static SafeStrongRefsOWLDataFactoryImpl instance = new SafeStrongRefsOWLDataFactoryImpl();

	public static SafeStrongRefsOWLDataFactoryImpl getInstance() {
		return instance;
	}
	public SafeStrongRefsOWLDataFactoryImpl() {
		data=new ConcurrentHashMapStrongReferencesOWLDataFactoryInternalsImpl(this);
		
	}
}
