package org.semanticweb.owlapi.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * Created by ses on 3/12/15.
 */
class XZStreamDocumentSourceTest {

    @Test
    void testReadKoalaDoc() throws IOException {
        XZStreamDocumentSource source =
            new XZStreamDocumentSource(getClass().getResourceAsStream("/koala.owl.xz"));

        assertTrue(source.isInputStreamAvailable());
        assertTrue(source.isReaderAvailable());
        BufferedReader in = new BufferedReader(source.getReader());

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
        in.close();
    }
}
