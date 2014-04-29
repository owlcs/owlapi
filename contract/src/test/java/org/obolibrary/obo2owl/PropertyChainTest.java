package org.obolibrary.obo2owl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class PropertyChainTest extends OboFormatTestBasics {

    @Test
    public void testConvertXPs() {
        assertNotNull(parseOBOFile("chaintest.obo", true));
    }
}
