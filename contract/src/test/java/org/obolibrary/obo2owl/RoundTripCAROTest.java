package org.obolibrary.obo2owl;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class RoundTripCAROTest extends RoundTripTest {

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("caro.obo", true);
    }
}
