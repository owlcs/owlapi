package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.StringWriter;

import org.junit.Test;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.writer.OBOFormatWriter;

@SuppressWarnings("javadoc")
public class RoundTripSynonymTest extends RoundTripTest {

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("synonym_test.obo", false);
    }

    @Test
    public void testRoundTrip2() throws Exception {
        // XXX set this to true to reveal a roundtrip issue about annotated
        // literals
        roundTripOBOFile("synonym_test2.obo", false);
    }

    @Test
    public void testRequireEmptyXrefList() throws Exception {
        OBODoc obo = parseOBOFile("synonym_test.obo");
        // Get synonym clause with an empty xref list
        Frame frame = obo.getTermFrame("GO:0009579");
        assertNotNull(frame);
        // write frame
        StringWriter stringWriter = new StringWriter();
        BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
        OBOFormatWriter oboWriter = new OBOFormatWriter();
        oboWriter.write(frame, bufferedWriter, null);
        bufferedWriter.flush();
        // get written frame
        String line = stringWriter.getBuffer().toString();
        // check that written frame has line:
        // synonym: "photosynthetic membrane" RELATED []
        assertTrue(line
                .contains("\nsynonym: \"photosynthetic membrane\" RELATED []\n"));
    }
}
