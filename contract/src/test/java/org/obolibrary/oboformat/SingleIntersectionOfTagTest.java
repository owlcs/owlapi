package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.FrameStructureException;
import org.obolibrary.oboformat.model.OBODoc;

@SuppressWarnings("javadoc")
public class SingleIntersectionOfTagTest extends OboFormatTestBasics {

    @Test(expected = FrameStructureException.class)
    public void testParseOBOFile() throws Exception {
        OBODoc obodoc = parseOBOFile("single_intersection_of_tag_test.obo");
        assertEquals(2, obodoc.getTermFrames().size());
        Frame frame = obodoc.getTermFrames().iterator().next();
        assertNotNull(frame);
        renderOboToString(obodoc); // throws FrameStructureException
    }
}
