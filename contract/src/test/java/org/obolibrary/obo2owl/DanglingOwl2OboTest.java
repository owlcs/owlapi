package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings({ "javadoc", "null" })
public class DanglingOwl2OboTest extends OboFormatTestBasics {

    @Test
    public void testConversion() throws Exception {
        OBODoc doc = convert(parseOWLFile("dangling_owl2_obo_test.owl"));
        Frame f = doc.getTermFrame("UBERON:0000020");
        Clause rc = f.getClause(OboFormatTag.TAG_NAME);
        assertEquals("sense organ", rc.getValue());
        Collection<Clause> ics = f.getClauses(OboFormatTag.TAG_INTERSECTION_OF);
        assertEquals(2, ics.size());
        writeOBO(doc);
    }
}
