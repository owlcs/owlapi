package org.semanticweb.owlapi.io;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import org.junit.Test;

/**
 * Created by ses on 3/12/15.
 */
@SuppressWarnings("javadoc")
public class XZStreamDocumentSourceTestCase {

    @Test
    public void testReadKoalaDoc() throws IOException {
        XZStreamDocumentSource source = new XZStreamDocumentSource(getClass()
            .getResourceAsStream("/koala.owl.xz"));
        Optional<Reader> reader = source.getReader();
        assertTrue("input stream available", source.getInputStream()
            .isPresent());
        assertTrue("input reader available", reader.isPresent());
        try (BufferedReader in = new BufferedReader(reader.get())) {
            int lineCount = 0;
            int koalaCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                lineCount++;
                if (line.contains("Koala")) {
                    koalaCount++;
                }
            }
            assertEquals("should have 3 Koalas", 3, koalaCount);
            assertEquals("should have 250 lines", 250, lineCount);
        }
    }
}
