package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings("javadoc")
public class DbXrefCommentsTest extends OboFormatTestBasics {

    @Test
    public void testDbXrefCommentsRoundtrip() throws Exception {
        OBODoc obodoc = parseOBOFile("db_xref_comments.obo");
        Frame frame = obodoc.getTermFrame("MOD:00516");
        assertNotNull(frame);
        Clause defClause = frame.getClause(OboFormatTag.TAG_DEF);
        assertNotNull(defClause);
        Collection<Xref> xrefs = defClause.getXrefs();
        assertEquals(2, xrefs.size());
        Iterator<Xref> iterator = xrefs.iterator();
        Xref xref1 = iterator.next();
        assertEquals("RESID:AA0151", xref1.getIdref());
        String annotation = xref1.getAnnotation();
        assertEquals("variant", annotation);
        Xref xref2 = iterator.next();
        assertEquals("UniMod:148", xref2.getIdref());
        String original = readResource("db_xref_comments.obo");
        String renderedOboString = renderOboToString(obodoc);
        assertEquals(original, renderedOboString);
    }
}
