package org.obolibrary.oboformat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.obolibrary.obo2owl.OWLAPIOwl2Obo;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

class RoundTripTestBasics extends OboFormatTestBasics {

    private static boolean compareOWLOntologiesPartial(OWLOntology oo, OWLOntology oo2,
        boolean isExpectRoundtrip, @Nullable Collection<OWLAxiom> untranslatableAxioms) {
        if (isExpectRoundtrip) {
            int untranslatedSize = 0;
            if (untranslatableAxioms != null) {
                untranslatedSize = untranslatableAxioms.size();
            }
            long expectedSize = oo.getAxiomCount();
            long foundSize = oo2.getAxiomCount();
            assertEquals(expectedSize, foundSize + untranslatedSize);
            return false;
        }
        return true;
    }

    public List<Diff> roundTripOBOFile(String fn, boolean isExpectRoundtrip) throws Exception {
        OBODoc obodoc = parseOBOFile(fn);
        return roundTripOBODoc(obodoc, isExpectRoundtrip);
    }

    public List<Diff> roundTripOBODoc(OBODoc obodoc, boolean isExpectRoundtrip) throws Exception {
        OWLOntology oo = convert(obodoc);
        StringDocumentTarget oo2 = new StringDocumentTarget();
        StringDocumentTarget oo1 = new StringDocumentTarget();
        oo.saveOntology(oo1);
        OBODoc obodoc2 = convert(oo);
        convert(obodoc2).saveOntology(oo2);
        String s1 = oo1.toString();
        String s2 = oo2.toString();
        assertEquals(s1, s2);
        obodoc2.check();
        List<Diff> diffs = OBODocDiffer.getDiffs(obodoc, obodoc2);
        if (isExpectRoundtrip) {
            assertEquals(0, diffs.size(), "Expected no diffs but " + diffs);
        }
        return diffs;
    }

    public boolean roundTripOWLFile(String fn, boolean isExpectRoundtrip)
        throws IOException, OWLOntologyCreationException {
        OWLOntology oo = parseOWLFile(fn);
        return roundTripOWLOOntology(oo, isExpectRoundtrip);
    }

    public boolean roundTripOWLOOntology(OWLOntology oo, boolean isExpectRoundtrip)
        throws IOException {
        OWLAPIOwl2Obo bridge = new OWLAPIOwl2Obo(m1);
        OBODoc obodoc = bridge.convert(oo);
        writeOBO(obodoc);
        obodoc.check();
        OWLOntology oo2 = convert(obodoc);
        writeOWL(oo2);
        boolean ok = compareOWLOntologiesPartial(oo, oo2, isExpectRoundtrip,
            bridge.getUntranslatableAxioms());
        return ok || !isExpectRoundtrip;
    }
}
