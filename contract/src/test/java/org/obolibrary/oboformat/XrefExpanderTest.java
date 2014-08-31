package org.obolibrary.oboformat;

import static org.junit.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.XrefExpander;

@SuppressWarnings({ "javadoc", "null" })
public class XrefExpanderTest extends OboFormatTestBasics {

    @Test
    public void testExpand() {
        OBODoc obodoc = parseOBOFile("treat_xrefs_test.obo");
        XrefExpander x = new XrefExpander(obodoc);
        x.expandXrefs();
        OBODoc tdoc = obodoc.getImportedOBODocs().iterator().next();
        assertTrue(!tdoc.getTermFrames().isEmpty());
        Frame termFrame = tdoc.getTermFrame("ZFA:0001689");
        assert termFrame != null;
        assertEquals(2, termFrame.getClauses(OboFormatTag.TAG_INTERSECTION_OF)
                .size());
        termFrame = tdoc.getTermFrame("EHDAA:571");
        assert termFrame != null;
        assertEquals("UBERON:0002539",
                termFrame.getClause(OboFormatTag.TAG_IS_A).getValue());
        termFrame = tdoc.getTermFrame("UBERON:0006800");
        assert termFrame != null;
        assertEquals("CARO:0000008", termFrame.getClause(OboFormatTag.TAG_IS_A)
                .getValue());
    }

    @Test
    public void testExpandIntoSeparateBridges() {
        OBODoc obodoc = parseOBOFile("treat_xrefs_test.obo");
        XrefExpander x = new XrefExpander(obodoc, "bridge");
        x.expandXrefs();
        int n = 0;
        for (OBODoc tdoc : obodoc.getImportedOBODocs()) {
            Frame hf = tdoc.getHeaderFrame();
            if (hf == null) {
                continue;
            }
            Clause impClause = hf.getClause(OboFormatTag.TAG_ONTOLOGY);
            // if (impClause == null) {
            // continue;
            // }
            String tid = impClause.getValue(String.class)
                    .replace("bridge-", "");
            if (tid.equals("zfa")) {
                assertEquals(
                        2,
                        tdoc.getTermFrame("ZFA:0001689")
                                .getClauses(OboFormatTag.TAG_INTERSECTION_OF)
                                .size());
                Frame pf = tdoc.getTypedefFrame("part_of");
                assertEquals("BFO:0000050", pf.getClause(OboFormatTag.TAG_XREF)
                        .getValue().toString());
                n++;
            }
            if (tid.equals("ehdaa")) {
                assertEquals("UBERON:0002539", tdoc.getTermFrame("EHDAA:571")
                        .getClause(OboFormatTag.TAG_IS_A).getValue());
                n++;
            }
            if (tid.equals("caro")) {
                assertEquals(
                        "CARO:0000008",
                        tdoc.getTermFrame("UBERON:0006800")
                                .getClause(OboFormatTag.TAG_IS_A).getValue());
                n++;
            }
        }
        assertEquals(3, n);
        // assertTrue(frame.getClause("name").getValue().equals("x1"));
    }
    /*
     * @Test public void testUberonHeader() throws Exception { OBODoc obodoc =
     * parseOBOFile("uberon_header_test.obo"); XrefExpander x = new
     * XrefExpander(obodoc, "bridge"); x.expandXrefs(); }
     */
}
