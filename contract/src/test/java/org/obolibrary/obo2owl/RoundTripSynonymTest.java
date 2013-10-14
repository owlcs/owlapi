package org.obolibrary.obo2owl;

import org.junit.Test;

public class RoundTripSynonymTest extends RoundTripTest {

	@Test
	public void testRoundTrip() throws Exception {
		roundTripOBOFile("synonym_test.obo", true);		
	}
	
	
}
