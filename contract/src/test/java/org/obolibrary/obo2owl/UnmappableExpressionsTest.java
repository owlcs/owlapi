package org.obolibrary.obo2owl;

import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.util.Collection;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;

/**
 * @author cjm
 * 
 * unmappable expressions should be handled gracefully.
 * 
 * in particular, there should be no single intersection_of tags
 * 
 * See http://code.google.com/p/oboformat/issues/detail?id=13
 *
 */
public class UnmappableExpressionsTest extends OboFormatTestBasics {

	
	@Test
	public void testConvert() throws Exception {
		OBODoc obodoc = convert(parseOWLFile("nesting.owl"));
		for (Frame f : obodoc.getTermFrames()) {
			System.out.println(f);
		}
//		checkOBODoc(obodoc);

		// ROUNDTRIP AND TEST AGAIN
		File file = writeOBO(obodoc, "nesting.obo");
		
		obodoc = parseOBOFile(file);
		checkOBODoc(obodoc);
	}
	
	private void checkOBODoc(OBODoc obodoc) {
		// OBODoc tests
		 		
		if (true) {
			Frame tf = obodoc.getTermFrame("x1"); // TODO - may change
			Collection<Clause> cs = tf.getClauses(OboFormatTag.TAG_INTERSECTION_OF);
			assertTrue(cs.size() != 1); // there should NEVER be a situation with single intersection tags
			// TODO - add validation step prior to saving
		}

	}
	
}
