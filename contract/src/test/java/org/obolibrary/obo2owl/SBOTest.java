package org.obolibrary.obo2owl;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntology;

public class SBOTest extends OboFormatTestBasics {

	@Test
	public void testConvertXPs() throws Exception {
		OWLOntology owlOnt = 
			convertOBOFile("http://www.ebi.ac.uk/sbo/exports/Main/SBO_OBO.obo");
		assertNotNull(owlOnt);
	}
	
	private OWLOntology convertOBOFile(String fn) throws Exception {
		return convert(parseOBOURL(fn), "cell");
	}

}
