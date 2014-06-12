package org.obolibrary.obo2owl;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.OBODoc;

@SuppressWarnings("javadoc")
public class RoundTripLabeledXrefsTest extends RoundTripTest {

    @Test
    public void testRoundTrip() throws Exception {
        OBODoc source = parseOBOFile("labeled_xrefs.obo");
        String written = renderOboToString(source);
        OBODoc parsed = parseOboToString(written);
        OBODocDiffer dd = new OBODocDiffer();
        List<Diff> diffs = dd.getDiffs(source, parsed);
        for (Diff diff : diffs) {
            System.out.println(diff);
        }
        assertEquals("Expected no diffs", 0, diffs.size());
    }
}
