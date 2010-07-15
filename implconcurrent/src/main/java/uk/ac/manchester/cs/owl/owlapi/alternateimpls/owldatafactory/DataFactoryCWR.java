package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DataFactoryCWR extends OWLDataFactoryImpl {
	private static DataFactoryCWR instance = new DataFactoryCWR();

	public static DataFactoryCWR getInstance() {
		return instance;
	}
	public DataFactoryCWR() {
		data=new InternalsCWR(this);
		
	}
}
