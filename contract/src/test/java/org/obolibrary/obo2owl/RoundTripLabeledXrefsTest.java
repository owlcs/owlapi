package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

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
        List<Diff> diffs = OBODocDiffer.getDiffs(source, parsed);
        assertEquals(0, diffs.size());
    }
}
