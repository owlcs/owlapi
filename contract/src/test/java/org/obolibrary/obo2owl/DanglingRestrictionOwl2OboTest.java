package org.obolibrary.obo2owl;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

@SuppressWarnings("javadoc")
public class DanglingRestrictionOwl2OboTest extends OboFormatTestBasics {

    @Test
    public void testConversion() throws Exception {
        // this is a test ontology that has had its imports axioms removed
        OBODoc doc = convert(parseOWLFile("dangling_restriction_test.owl"));
        Frame f = doc.getTermFrame("FUNCARO:0000014");
        Clause rc = f.getClause(OboFormatTag.TAG_NAME);
        assertTrue(rc.getValue().equals("digestive system"));
        Collection<Clause> isas = f.getClauses(OboFormatTag.TAG_IS_A);
        assertTrue(isas.size() == 1);
        Collection<Clause> rs = f.getClauses(OboFormatTag.TAG_RELATIONSHIP);
        assertTrue(rs.size() == 1);
        writeOBO(doc);
    }
}
