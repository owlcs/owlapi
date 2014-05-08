package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.Frame.FrameType;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParserException;

@SuppressWarnings({ "javadoc", "null" })
public class CurlyBracesInCommentsTest extends OboFormatTestBasics {

    @Test(expected = OBOFormatParserException.class)
    public void testCurlyBracesInComments() {
        /*
         * Expect an parser exception, as the comment line contains '{' and '}'.
         * This will lead the parser to try and parse it as a trailing
         * qualifier, which fails in this case.
         */
        parseOBOFile("fbbt_comment_test.obo");
    }

    @Test
    public void writeCurlyBracesInComments() throws Exception {
        OBODoc doc = new OBODoc();
        Frame h = new Frame(FrameType.HEADER);
        h.addClause(new Clause(OboFormatTag.TAG_ONTOLOGY, "test"));
        doc.setHeaderFrame(h);
        Frame t = new Frame(FrameType.TERM);
        String id = "TEST:0001";
        t.setId(id);
        t.addClause(new Clause(OboFormatTag.TAG_ID, id));
        String comment = "Comment with a '{' curly braces '}'";
        t.addClause(new Clause(OboFormatTag.TAG_COMMENT, comment));
        doc.addFrame(t);
        String oboString = renderOboToString(doc);
        assertTrue(oboString
                .contains("comment: Comment with a '\\{' curly braces '}'"));
        OBODoc doc2 = parseOboToString(oboString);
        assertEquals(comment,
                doc2.getTermFrame(id).getTagValue(OboFormatTag.TAG_COMMENT));
    }
}
