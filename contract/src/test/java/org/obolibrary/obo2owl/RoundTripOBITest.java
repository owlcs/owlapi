package org.obolibrary.obo2owl;

import static junit.framework.Assert.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.obolibrary.oboformat.diff.Diff;
import org.obolibrary.oboformat.diff.OBODocDiffer;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.semanticweb.owlapi.model.OWLOntology;

public class RoundTripOBITest extends RoundTripTest {

	@Test
	public void test() throws Exception {
		OWLOntology owlFile = parseOWLFile("obi.owl");
		OBODoc oboDoc = convert(owlFile);
		oboDoc.getHeaderFrame().addClause(new Clause(OboFormatTag.TAG_FORMAT_VERSION, "1.2"));
		String oboString = renderOboToString(oboDoc);
		OBODoc parsedOboDoc = parseOboToString(oboString);
		OBODocDiffer dd = new OBODocDiffer();
		List<Diff> diffs = dd.getDiffs(oboDoc, parsedOboDoc);
		for (Diff diff : diffs) {
			System.out.println(diff);
		}
		assertEquals("Expected no diffs", 0, diffs.size()); 
	}

}
