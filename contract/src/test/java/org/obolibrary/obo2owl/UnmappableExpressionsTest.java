package org.obolibrary.obo2owl;

import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import java.util.Collection;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/**
 * @author cjm unmappable expressions should be handled gracefully. in
 *         particular, there should be no single intersection_of tags See
 *         http://code.google.com/p/oboformat/issues/detail?id=13
 */
@SuppressWarnings("javadoc")
public class UnmappableExpressionsTest extends OboFormatTestBasics {

    @Test
    public void testConvert() throws Exception {
        OBODoc obodoc = convert(parseOWLFile("nesting.owl"));
        // checkOBODoc(obodoc);
        // ROUNDTRIP AND TEST AGAIN
        String file = writeOBO(obodoc, "nesting.obo");
        obodoc = parseOBOFile(new StringReader(file), false);
        checkOBODoc(obodoc);
    }

    private void checkOBODoc(OBODoc obodoc) {
        // OBODoc tests
        if (true) {
            Frame tf = obodoc.getTermFrame("x1"); // TODO - may change
            Collection<Clause> cs = tf
                    .getClauses(OboFormatTag.TAG_INTERSECTION_OF);
            assertTrue(cs.size() != 1); // there should NEVER be a situation
                                        // with single intersection tags
            // TODO - add validation step prior to saving
        }
    }
}
