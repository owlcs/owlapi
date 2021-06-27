package org.obolibrary.oboformat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SimpleRoundTripTestCase extends RoundTripTestBasics {

    @ParameterizedTest
    @ValueSource(strings = {"roundtrip_cardinality.obo", "caro.obo",
        "roundtrip_equivalent_to_chain.obo", "multiple_def_xref_test.obo",
        "namespace-id-rule.obo", "roundtrip_non_standard_synonyms.obo",
        "property_value_test.obo", "property_value_qualifier_test.obo",
        "relationship_vs_property.obo", "simplego.obo", "synonym_test.obo",
        "version_iri_test.obo", "xref_escapecolon.obo", "xref_annotation.obo",
        "relation_shorthand_test.obo", "dc_header_test.obo",
        /**
         * Round trip trailing qualifiers via obo2owl and owl2obo. Uses a slightly modified test
         * file. Idspace tags do not survive the obo2owl translation. They are only directives for
         * the translation.
         *
         * @see TrailingQualifierTest
         */
        "trailing_qualifier_roundtrip.obo"})
    void roundTrip(String file) throws Exception {
        roundTripOBOFile(file, true);
    }
}
