package org.obolibrary.cli;


import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.cli.OBORunnerConfiguration.ExpandMacrosModeOptions;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

public class OBORunnerConfigCLIReaderTest {

	@Test
	public void testReadConfig() {
		OBORunnerConfiguration config = OBORunnerConfigCLIReader.readConfig(new String[]{"-o","obi","-x","http://some.url"});
		assertTrue(config.isOboToOwl.getValue());
		assertEquals("obi", config.outFile.getValue());
		Collection<String> value = config.paths.getValue();
		assertEquals(1, value.size());
		assertEquals("http://some.url", value.toArray()[0]);
		assertTrue(config.isExpandMacros.getValue());
	}
	
	@Test
	public void testReadConfig2() {
		OBORunnerConfiguration config = OBORunnerConfigCLIReader.readConfig(new String[]{"-x","-xm","inplace"});
		assertTrue(config.isExpandMacros.getValue());
		assertEquals(ExpandMacrosModeOptions.INPLACE, config.expandMacrosMode.getValue());
	}

	@Test
	public void testReadConfig3() {
		OBORunnerConfiguration config = OBORunnerConfigCLIReader.readConfig(new String[]{"-o","obi","--to","RDF"});
		assertTrue(config.isOboToOwl.getValue());
		assertEquals("obi", config.outFile.getValue());
		OWLOntologyFormat format = config.format.getValue();
		assertEquals(RDFXMLOntologyFormat.class.getName(), format.getClass().getName());
		assertFalse(config.isExpandMacros.getValue());
		assertEquals(ExpandMacrosModeOptions.GCI, config.expandMacrosMode.getValue());
	}
}
