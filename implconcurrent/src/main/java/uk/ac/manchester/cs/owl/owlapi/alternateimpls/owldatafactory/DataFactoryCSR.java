package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DataFactoryCSR extends OWLDataFactoryImpl {
	private static DataFactoryCSR instance = new DataFactoryCSR();

	public static DataFactoryCSR getInstance() {
		return instance;
	}
	public DataFactoryCSR() {
		data=new InternalsCSR(this);
		
	}
}
