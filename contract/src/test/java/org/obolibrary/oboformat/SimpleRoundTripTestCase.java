package org.obolibrary.oboformat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.apitest.TestFilenames;

class SimpleRoundTripTestCase extends RoundTripTestBasics {

    @ParameterizedTest
    @ValueSource(strings = {TestFilenames.ROUNDTRIP_CARDINALITY_OBO, TestFilenames.CARO_OBO,
        TestFilenames.ROUNDTRIP_EQUIVALENT_TO_CHAIN_OBO, TestFilenames.MULTIPLE_DEF_XREF_TEST_OBO,
        TestFilenames.NAMESPACE_ID_RULE_OBO, TestFilenames.ROUNDTRIP_NON_STANDARD_SYNONYMS_OBO,
        TestFilenames.PROPERTY_VALUE_TEST_OBO, TestFilenames.PROPERTY_VALUE_QUALIFIER_TEST_OBO,
        TestFilenames.RELATIONSHIP_VS_PROPERTY_OBO, TestFilenames.SIMPLEGO_OBO,
        TestFilenames.SYNONYM_TEST_OBO, TestFilenames.VERSION_IRI_TEST_OBO,
        TestFilenames.XREF_ESCAPECOLON_OBO, TestFilenames.XREF_ANNOTATION_OBO,
        TestFilenames.RELATION_SHORTHAND_TEST_OBO, TestFilenames.DC_HEADER_TEST_OBO,
        /*
         * Round trip trailing qualifiers via obo2owl and owl2obo. Uses a slightly modified test
         * file. Idspace tags do not survive the obo2owl translation. They are only directives for
         * the translation.
         */
        TestFilenames.TRAILING_QUALIFIER_ROUNDTRIP_OBO})
    void roundTrip(String file) {
        roundTripOBOFile(file, true);
    }
}
