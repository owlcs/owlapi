package org.obolibrary.oboformat.diff;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.model.Xref;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.parser.OBOFormatParser;

/**
 * Diffs two OBO Documents. Performs structural diffing only - does not use
 * reasoning (use OWLDiff or similar tools for this)
 * 
 * @author cjm
 */
public class OBODocDiffer {

    /**
     * @param doc1
     *        doc1
     * @param doc2
     *        doc2
     * @return list of diffs
     */
    public List<Diff> getDiffs(OBODoc doc1, OBODoc doc2) {
        List<Diff> diffs = new ArrayList<Diff>();
        diffs.addAll(getDiffs("Header", doc1.getHeaderFrame(),
                doc2.getHeaderFrame()));
        diffs.addAll(getDiffs("Term", doc1.getTermFrames(),
                doc2.getTermFrames()));
        diffs.addAll(getDiffs("Typedef", doc1.getTypedefFrames(),
                doc2.getTypedefFrames()));
        diffs.addAll(getDiffs("Instance", doc1.getInstanceFrames(),
                doc2.getInstanceFrames()));
        return diffs;
    }

    // FRAME LISTS
    private List<Diff> getDiffsAsym(String ftype, Collection<Frame> fl1,
            Collection<Frame> fl2, int n, boolean isCheckFrame) {
        List<Diff> diffs = new ArrayList<Diff>();
        Map<String, Frame> fm2 = new HashMap<String, Frame>();
        for (Frame f : fl2) {
            fm2.put(f.getId(), f);
        }
        for (Frame f1 : fl1) {
            if (fm2.containsKey(f1.getId())) {
                Frame f2 = fm2.get(f1.getId());
                if (isCheckFrame) {
                    // we only need to do this once
                    diffs.addAll(getDiffs(ftype, f1, f2));
                }
            } else {
                diffs.add(new Diff(ftype, "cannot find frame", f1, n));
            }
        }
        return diffs;
    }

    private List<Diff> getDiffs(String ftype, Collection<Frame> fl1,
            Collection<Frame> fl2) {
        List<Diff> diffs = getDiffsAsym(ftype, fl1, fl2, 1, true);
        diffs.addAll(getDiffsAsym(ftype, fl1, fl2, 2, false));
        return diffs;
    }

    // FRAMES
    private List<Diff> getDiffsAsym(String ftype, Frame f1, Frame f2, int n) {
        List<Diff> diffs = new ArrayList<Diff>();
        for (Clause c : f1.getClauses()) {
            boolean isMatched = false;
            for (Clause c2 : f2.getClauses()) {
                if (c.getTag().equals(c2.getTag())) {
                    if (c.equals(c2)) {
                        isMatched = true;
                        if (OboFormatTag.TAG_XREF.getTag().equals(c.getTag())) {
                            String a1 = c.getValue(Xref.class).getAnnotation();
                            String a2 = c2.getValue(Xref.class).getAnnotation();
                            isMatched = a1 == null && a2 == null || a1 != null
                                    && a1.equals(a2);
                        }
                        break;
                    }
                }
            }
            if (!isMatched) {
                diffs.add(new Diff(ftype, "cannot_match_clause", f1, f2, c, n));
            }
        }
        return diffs;
    }

    private List<Diff> getDiffs(String ftype, Frame f1, Frame f2) {
        List<Diff> diffs = getDiffsAsym(ftype, f1, f2, 1);
        diffs.addAll(getDiffsAsym(ftype, f2, f1, 2));
        return diffs;
    }

    /**
     * @param args
     *        args
     * @throws Exception
     *         Exception
     */
    public static void main(String[] args) throws Exception {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc1 = p.parse(args[0]);
        OBODoc obodoc2 = p.parse(args[1]);
        OBODocDiffer dd = new OBODocDiffer();
        List<Diff> diffs = dd.getDiffs(obodoc1, obodoc2);
        for (Diff d : diffs) {
            System.out.println(d);
        }
    }
}
