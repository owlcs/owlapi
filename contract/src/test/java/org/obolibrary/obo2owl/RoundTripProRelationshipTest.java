package org.obolibrary.obo2owl;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Scenario: Translate a OBO relationship using an annotation property in OWL
 * instead of an object property (is_metadata_tag: true). <br>
 * Problem: reverse translation of AA (R ID Y) to OBO, there are two possible
 * translations:
 * <ol>
 * <li>relationship: R Y</li>
 * <li>property_value: R Y</li>
 * </ol>
 * The safest option is the property value, as it will be ignored by OBO-Edit. <br>
 * Unfortunately, the Protein ontology (PRO) expects the 'only_in_taxon'
 * annotations as relationships.
 * 
 * @see RoundTripPropertyValueTest
 */
@SuppressWarnings("javadoc")
public class RoundTripProRelationshipTest extends RoundTripTest {

    /**
     * This test fails due to the non-deterministic translation from OWL2OBO
     * 
     * @throws Exception
     */
    @Ignore("This test fails due to the non-deterministic translation from OWL2OBO")
    @Test
    public
            void testRoundTripRelationship() throws Exception {
        roundTripOBOFile("rel-pv-roundtrip.obo", true);
    }
}
