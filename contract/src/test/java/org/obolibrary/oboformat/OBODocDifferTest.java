package org.obolibrary.oboformat;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.OBODoc;

@SuppressWarnings("javadoc")
public class OBODocDifferTest extends OboFormatTestBasics {

    @Test
    public void testIdentical() throws Exception {
        OBODoc obodoc1 = parseOBOFile("caro.obo");
        OBODoc obodoc2 = parseOBOFile("caro.obo");
        OBODocDiffer dd = new OBODocDiffer();
        List<Diff> diffs = dd.getDiffs(obodoc1, obodoc2);
        assertEquals(0, diffs.size());
    }

    @Test
    public void testDiff() throws Exception {
        OBODoc obodoc1 = parseOBOFile("caro.obo");
        OBODoc obodoc2 = parseOBOFile("caro_modified.obo");
        OBODocDiffer dd = new OBODocDiffer();
        List<Diff> diffs = dd.getDiffs(obodoc1, obodoc2);
        assertEquals(19, diffs.size());
    }
}
