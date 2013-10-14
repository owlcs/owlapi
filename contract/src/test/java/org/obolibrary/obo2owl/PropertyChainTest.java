package org.obolibrary.obo2owl;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

public class PropertyChainTest extends OboFormatTestBasics {

	@Test
	public void testConvertXPs() throws Exception {
		OWLOntology owlOnt = convertOBOFile("chaintest.obo");
		assertNotNull(owlOnt);
	}
	
	public OWLOntology convertOBOFile(String fn) throws Exception {
		return convert(parseOBOFile(fn, true), fn);
	}

}
