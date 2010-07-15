package uk.ac.manchester.cs.owl.owlapi.alternateimpls.owldatafactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

public class DataFactoryNoCache extends OWLDataFactoryImpl {
	private static DataFactoryNoCache instance = new DataFactoryNoCache();

	public static DataFactoryNoCache getInstance() {
		return instance;
	}

	public DataFactoryNoCache() {
		data = new InternalsNoCache(this);
	}
}
