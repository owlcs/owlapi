package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DataFactoryFuture extends OWLDataFactoryImpl {
	private static DataFactoryFuture instance = new DataFactoryFuture();

	public static DataFactoryFuture getInstance() {
		return instance;
	}
	public DataFactoryFuture() {
		//data=new ConcurrentHashMapStrongReferencesOWLDataFactoryInternalsImpl(this);
		data=new InternalsFuture(this);
	}
}
