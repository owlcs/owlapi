package org.obolibrary.obo2owl;

import org.junit.Test;
import org.obolibrary.oboformat.TrailingQualifierTest;

@SuppressWarnings("javadoc")
public class RoundTripTrailingQualifiers extends RoundTripTest {

    /**
     * Round trip trailing qualifiers via obo2owl and owl2obo. Uses a slightly
     * modified test file. Idspace tags do not survive the obo2owl translation.
     * They are only directives for the translation.
     * 
     * @see TrailingQualifierTest
     */
    @Test
    public void testTrailingQualifiers() throws Exception {
        roundTripOBOFile("trailing_qualifier_roundtrip.obo", false);
    }
}
