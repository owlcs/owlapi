package org.semanticweb.owlapi.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Created by ses on 3/12/15.
 */
class XZStreamDocumentSourceTestCase {

    @Test
    void testReadKoalaDoc() throws IOException {
        XZStreamDocumentSource source =
            new XZStreamDocumentSource(getClass().getResourceAsStream("/koala.owl.xz"));
        Optional<Reader> reader = source.getReader();
        assertTrue(source.getInputStream().isPresent());
        assertTrue(reader.isPresent());
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
            assertEquals(3, koalaCount);
            assertEquals(250, lineCount);
        }
    }
}
