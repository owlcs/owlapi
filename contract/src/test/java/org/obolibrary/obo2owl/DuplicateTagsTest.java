package org.obolibrary.obo2owl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.semanticweb.owlapi.model.OWLOntology;

@SuppressWarnings("javadoc")
public class DuplicateTagsTest extends OboFormatTestBasics {

    @Test
    public void test() throws Exception {
        OWLOntology owl = parseOWLFile("duplicate-def.ofn");
        final List<Clause> duplicates = new ArrayList<>();
        OWLAPIOwl2Obo owl2Obo = new OWLAPIOwl2Obo(m) {

            @Override
            protected boolean handleDuplicateClause(Frame frame, Clause clause) {
                duplicates.add(clause);
                return super.handleDuplicateClause(frame, clause);
            }
        };
        OBODoc convert = owl2Obo.convert(owl);
        assertEquals(1, duplicates.size());
        // test that no exception is thrown during write.
        renderOboToString(convert);
    }
}
