package org.obolibrary.obo2owl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/** @author cjm */
@SuppressWarnings("javadoc")
public class SynonymTest extends OboFormatTestBasics {

    @Test
    public void testConvert() throws Exception {
        // PARSE TEST FILE
        assertNotNull(convert(parseOBOFile("synonym_test.obo")));
    }
}
