package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.FrameStructureException;
import org.obolibrary.oboformat.model.OBODoc;

@SuppressWarnings("javadoc")
public class MultipleCommentsTest extends OboFormatTestBasics {

    @Test(expected = FrameStructureException.class)
    public void testCheckForMultipleCommentsinFrame() throws Exception {
        OBODoc obodoc = parseOBOFile("multiple_comments_test.obo");
        assertEquals(1, obodoc.getTermFrames().size());
        Frame frame = obodoc.getTermFrames().iterator().next();
        assertNotNull(frame);
        renderOboToString(obodoc); // throws FrameStructureException
    }
}
