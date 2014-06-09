package org.obolibrary.obo2owl;

import org.junit.Test;

public class RoundTripRelationshipVsProperty extends RoundTripTest {

	@Test
	public void roundTripRelationshipVsProperty() throws Exception {
		roundTripOBOFile("relationship_vs_property.obo", true);
	}
}
