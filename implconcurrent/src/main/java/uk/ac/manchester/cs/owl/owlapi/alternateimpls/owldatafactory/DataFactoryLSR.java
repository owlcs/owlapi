package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DataFactoryLSR extends OWLDataFactoryImpl {
	private static DataFactoryLSR instance = new DataFactoryLSR();

	public static DataFactoryLSR getInstance() {
		return instance;
	}
	public DataFactoryLSR() {
		//data=new ConcurrentHashMapStrongReferencesOWLDataFactoryInternalsImpl(this);
		data=new InternalsLSR(this);
	}
}
