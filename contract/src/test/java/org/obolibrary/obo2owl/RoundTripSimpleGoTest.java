package org.obolibrary.obo2owl;

import org.junit.Test;

public class RoundTripSimpleGoTest extends RoundTripTest {

	@Test
	public void testRoundTrip() throws Exception {
		roundTripOBOFile("simplego.obo", true);
	}
	
}
