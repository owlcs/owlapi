package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings("javadoc")
public class IdSpaceTest extends OboFormatTestBasics {

    @Test
    public void testIdSpace() throws Exception {
        final OBODoc doc1 = parseOBOFile("idspace_test.obo");
        checkIdSpace(doc1);
        final String oboString = renderOboToString(doc1);
        assertTrue(oboString
                .contains("idspace: GO urn:lsid:bioontology.org:GO: \"gene ontology terms\""));
        final OBODoc doc2 = parseOboToString(oboString);
        checkIdSpace(doc2);
    }

    private void checkIdSpace(OBODoc doc) {
        Frame headerFrame = doc.getHeaderFrame();
        assertNotNull(headerFrame);
        Clause clause = headerFrame.getClause(OboFormatTag.TAG_IDSPACE);
        Collection<Object> values = clause.getValues();
        assertNotNull(values);
        assertEquals(3, values.size());
        Iterator<Object> it = values.iterator();
        assertEquals("GO", it.next());
        assertEquals("urn:lsid:bioontology.org:GO:", it.next());
        assertEquals("gene ontology terms", it.next());
    }
}
