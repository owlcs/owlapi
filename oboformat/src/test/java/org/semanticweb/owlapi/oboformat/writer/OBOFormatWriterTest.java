package org.semanticweb.owlapi.oboformat.writer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.writer.OBOFormatWriter;

public class OBOFormatWriterTest {

	private static final String FILE_TEST_NAME1 = "example1.obo";

	@Test
	public void testOBOFormatRoundTrip() throws IOException {
		File oboFile = new File(this.getClass().getClassLoader().getResource(FILE_TEST_NAME1).getFile());

		assertNotNull(oboFile);
		OBOFormatParser parser = new OBOFormatParser();
		OBODoc oboDoc = parser.parse(oboFile);
		assertNotNull(oboDoc);

		OBOFormatWriter writer = new OBOFormatWriter();
		StringWriter stringWriter = new StringWriter();
		writer.write(oboDoc, stringWriter);
		
		final List<String> outputLines = Arrays.asList(stringWriter.toString().split("\n"));
		try (Stream<String> stream = Files.lines(oboFile.toPath(), StandardCharsets.UTF_8)) {
			stream.forEach(s -> assertTrue(outputLines.contains(s),
					String.format("'%s' doesn't exist in the output file.", s)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
