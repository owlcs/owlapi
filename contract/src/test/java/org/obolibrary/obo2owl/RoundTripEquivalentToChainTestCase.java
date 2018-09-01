package org.obolibrary.obo2owl;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class RoundTripEquivalentToChainTestCase extends RoundTripTest {

    @Test
    public void testTrailingQualifiers() throws Exception {
        roundTripOBOFile("roundtrip_equivalent_to_chain.obo", true);
    }
}
