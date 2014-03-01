package org.obolibrary.obo2owl;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class RoundTripNonStandardSynonyms extends RoundTripTest {

    @Test
    public void test() throws Exception {
        roundTripOBOFile("roundtrip_non_standard_synonyms.obo", true);
    }
}
