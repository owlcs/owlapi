package org.obolibrary.obo2owl;

import org.junit.Test;

public class RoundTripCardinality extends RoundTripTest {

	@Test
	public void roundTripCardinality() throws Exception {
		roundTripOBOFile("cardinality.obo", true);
	}
}
