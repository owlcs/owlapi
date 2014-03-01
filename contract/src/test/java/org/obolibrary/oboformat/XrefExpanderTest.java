package org.obolibrary.oboformat;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.XrefExpander;

@SuppressWarnings("javadoc")
public class XrefExpanderTest extends OboFormatTestBasics {

    @Test
    public void testExpand() throws Exception {
        OBODoc obodoc = parseOBOFile("treat_xrefs_test.obo");
        XrefExpander x = new XrefExpander(obodoc);
        x.expandXrefs();
        OBODoc tdoc = obodoc.getImportedOBODocs().iterator().next();
        assertTrue(tdoc.getTermFrames().size() > 0);
        assertTrue(tdoc.getTermFrame("ZFA:0001689")
                .getClauses(OboFormatTag.TAG_INTERSECTION_OF).size() == 2);
        assertTrue(tdoc.getTermFrame("EHDAA:571")
                .getClause(OboFormatTag.TAG_IS_A).getValue()
                .equals("UBERON:0002539"));
        assertTrue(tdoc.getTermFrame("UBERON:0006800")
                .getClause(OboFormatTag.TAG_IS_A).getValue()
                .equals("CARO:0000008"));
    }

    @Test
    public void testExpandIntoSeparateBridges() throws Exception {
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
            if (impClause == null) {
                continue;
            }
            String tid = impClause.getValue(String.class)
                    .replace("bridge-", "");
            if (tid.equals("zfa")) {
                assertTrue(tdoc.getTermFrame("ZFA:0001689")
                        .getClauses(OboFormatTag.TAG_INTERSECTION_OF).size() == 2);
                Frame pf = tdoc.getTypedefFrame("part_of");
                assertTrue(pf.getClause(OboFormatTag.TAG_XREF).getValue()
                        .toString().equals("BFO:0000050"));
                n++;
            }
            if (tid.equals("ehdaa")) {
                assertTrue(tdoc.getTermFrame("EHDAA:571")
                        .getClause(OboFormatTag.TAG_IS_A).getValue()
                        .equals("UBERON:0002539"));
                n++;
            }
            if (tid.equals("caro")) {
                assertTrue(tdoc.getTermFrame("UBERON:0006800")
                        .getClause(OboFormatTag.TAG_IS_A).getValue()
                        .equals("CARO:0000008"));
                n++;
            }
        }
        assertTrue(n == 3);
        // assertTrue(frame.getClause("name").getValue().equals("x1"));
    }
    /*
     * @Test public void testUberonHeader() throws Exception { OBODoc obodoc =
     * parseOBOFile("uberon_header_test.obo"); XrefExpander x = new
     * XrefExpander(obodoc, "bridge"); x.expandXrefs(); }
     */
}
