package org.obolibrary.oboformat;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.OBODoc;

@SuppressWarnings("javadoc")
public class ChebiXRefTest extends OboFormatTestBasics {

    @Test
    public void testExpand() throws Exception {
        OBODoc obodoc = parseOBOFile("chebi_problematic_xref.obo");
        assertNotNull(obodoc);
    }
}
