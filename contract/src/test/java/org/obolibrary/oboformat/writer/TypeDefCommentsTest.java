package org.obolibrary.oboformat.writer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.OBODoc;

@SuppressWarnings("javadoc")
public class TypeDefCommentsTest extends OboFormatTestBasics {

    @Test
    public void writeTypeDefComments() throws Exception {
        OBODoc doc = parseOBOFile("typedef_comments.obo", true);
        String original = readResource("typedef_comments.obo");
        String written = renderOboToString(doc);
        assertEquals(original, written);
    }
}
