package org.obolibrary.obo2owl;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class RoundTripPropertyValueTest extends RoundTripTest {

    @Test
    public void testRoundTrip() throws Exception {
        roundTripOBOFile("property_value_test.obo", true);
    }

    @Test
    public void testRoundTripWithQualifiers() throws Exception {
        roundTripOBOFile("property_value_qualifier_test.obo", true);
    }

    @Test
    public void testRoundTripHeader() throws Exception {
        roundTripOBOFile("dc_header_test.obo", true);
    }
}
