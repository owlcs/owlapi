package org.semanticweb.owlapi.oboformat.parser;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;

public class OBOFormatParserTest {

	private static final String FILE_TEST_NAME1 = "example1.obo";
	
	@Test
	public void parseTestFile1() throws IOException {
		File oboFile = new File(this.getClass().getClassLoader().getResource(FILE_TEST_NAME1).getFile());
		assertNotNull(oboFile);
		
		OBOFormatParser parser = new OBOFormatParser();
		
		OBODoc oboDoc = parser.parse(oboFile);
		
		assertNotNull(oboDoc);
		oboDoc.check();
	}
}
