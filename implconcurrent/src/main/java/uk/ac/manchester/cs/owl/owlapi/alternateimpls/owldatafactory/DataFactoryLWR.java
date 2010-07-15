package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DataFactoryLWR extends OWLDataFactoryImpl {
	private static DataFactoryLWR instance = new DataFactoryLWR();

	public static DataFactoryLWR getInstance() {
		return instance;
	}
	public DataFactoryLWR() {
		//data=new ConcurrentHashMapStrongReferencesOWLDataFactoryInternalsImpl(this);
		data=new InternalsLWR(this);
	}
}
