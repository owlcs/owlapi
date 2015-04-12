package org.semanticweb.owlapi.io;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.Test;

/**
 * Created by ses on 3/12/15.
 */
@SuppressWarnings("javadoc")
public class XZStreamDocumentSourceTest {

    @Test
    public void testReadKoalaDoc() throws IOException {
        XZStreamDocumentSource source = new XZStreamDocumentSource(
                getClass().getResourceAsStream("/koala.owl.xz"));

        assertTrue("input stream available",source.isInputStreamAvailable());
        assertTrue("input reader available",source.isReaderAvailable());
        BufferedReader in = new BufferedReader(source.getReader());

        int lineCount=0;
        int koalaCount=0;
        String line=null;
        while((line = in.readLine())!= null) {
            lineCount++;
            if(line.contains("Koala")) {
                koalaCount++;
            }
        }
        assertEquals("should have 3 Koalas",3,koalaCount);
        assertEquals("should have 250 lines",250,lineCount);
        in.close();
    }
}
