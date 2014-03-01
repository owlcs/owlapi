package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings("javadoc")
public class OboEscapeCharsTest extends OboFormatTestBasics {

    @Test
    public void testEscapeChars() throws Exception {
        OBODoc obodoc = parseOBOFile("escape_chars_test.obo");
        assertEquals(3, obodoc.getTermFrames().size());
        Frame f1 = obodoc.getTermFrame("GO:0033942");
        assertEquals("GO:0033942", f1.getId());
        Clause nameClause = f1.getClause(OboFormatTag.TAG_NAME);
        assertEquals(
                "4-alpha-D-{(1->4)-alpha-D-glucano}trehalose trehalohydrolase activity",
                nameClause.getValue());
        Frame f2 = obodoc.getTermFrame("CL:0000096");
        assertEquals("CL:0000096", f2.getId());
        Clause defClause = f2.getClause(OboFormatTag.TAG_DEF);
        assertEquals("bla bla .\"", defClause.getValue());
        Clause commentClause = f2.getClause(OboFormatTag.TAG_COMMENT);
        assertEquals("bla bla bla.\nbla bla (bla).", commentClause.getValue());
    }

    @Test
    public void testRoundTripEscapeChars() throws Exception {
        OBODoc oboDoc = parseOBOFile("escape_chars_test.obo");
        String oboToString = renderOboToString(oboDoc);
        OBODoc oboDoc2 = parseOboToString(oboToString);
        assertNotNull("There was an error during parsing of the obodoc",
                oboDoc2);
        OBODocDiffer differ = new OBODocDiffer();
        List<Diff> diffs = differ.getDiffs(oboDoc, oboDoc2);
        assertEquals("Expected no diffs.", 0, diffs.size());
        String original = readResource("escape_chars_test.obo");
        assertEquals(original, oboToString);
    }
}
