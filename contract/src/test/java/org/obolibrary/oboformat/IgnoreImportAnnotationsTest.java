package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.QualifierValue;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/**
 * Test: silently ignore annotations of import statements in the header. See
 * bug/request: http://code.google.com/p/oboformat/issues/detail?id=19
 */
@SuppressWarnings("javadoc")
public class IgnoreImportAnnotationsTest extends OboFormatTestBasics {

    @Test
    public void testIgnoreAnnotations() throws Exception {
        OBODoc oboDoc = parseOBOFile("annotated_import.obo");
        Frame headerFrame = oboDoc.getHeaderFrame();
        Collection<Clause> imports = headerFrame
                .getClauses(OboFormatTag.TAG_IMPORT);
        assertEquals(1, imports.size());
        Clause clause = imports.iterator().next();
        Collection<QualifierValue> qualifierValues = clause
                .getQualifierValues();
        assertTrue(qualifierValues.isEmpty());
    }
}
