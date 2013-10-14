package org.obolibrary.oboformat;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.obolibrary.obo2owl.OboFormatTestBasics;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.FrameStructureException;
import org.obolibrary.oboformat.model.OBODoc;

public class SingleIntersectionOfTagTest extends OboFormatTestBasics {
	
	static boolean useSystemOut = false;
	
	@Test(expected=FrameStructureException.class)
	public void testParseOBOFile() throws Exception {
		OBODoc obodoc = parseOBOFile("single_intersection_of_tag_test.obo");
		if (useSystemOut) {
			System.out.println("F:" + obodoc);
		}
		assertTrue(obodoc.getTermFrames().size() == 2);
		Frame frame = obodoc.getTermFrames().iterator().next();
		assertNotNull(frame);
		
		renderOboToString(obodoc); // throws FrameStructureException
	}

}
