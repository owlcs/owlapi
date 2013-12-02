package org.obolibrary.obo2owl;

import static junit.framework.Assert.assertTrue;

import org.junit.Test;
import org.obolibrary.oboformat.model.Clause;
import org.obolibrary.oboformat.model.Frame;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatConstants.OboFormatTag;
import org.obolibrary.oboformat.writer.OBOFormatWriter;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;

public class DanglingRoundTripTest  extends OboFormatTestBasics {

	@Test
	public void testConvertXPs() throws Exception {
		OWLOntology owlOnt = convertOBOFile("dangling_roundtrip_test.obo");
		
        OWLAPIOwl2Obo revbridge = new OWLAPIOwl2Obo(OWLManager.createOWLOntologyManager());
		OBODoc d2 = revbridge.convert(owlOnt);
				
		Frame f = d2.getTermFrame("UBERON:0000020");
		System.out.println("F="+f);
		Clause rc = f.getClause(OboFormatTag.TAG_NAME);
		assertTrue(rc.getValue().equals("sense organ"));
		
		OBOFormatWriter w = new OBOFormatWriter();
		w.write(d2, "/tmp/z.obo");

	}
	
	private OWLOntology convertOBOFile(String fn) throws Exception {
		return convert(parseOBOFile(fn), fn);
	}
}
