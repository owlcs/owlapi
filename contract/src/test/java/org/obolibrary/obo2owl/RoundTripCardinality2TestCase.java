package org.obolibrary.obo2owl;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class RoundTripCardinality2TestCase extends RoundTripTest {

    @Test
    public void roundTripCardinality() throws Exception {
        roundTripOBOFile("cardinality.obo", true);
    }
}
