package org.obolibrary.obo2owl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;

public class RoundTripMultipleDefXrefTest extends RoundTripTest {

	@Test
	public void testRoundTrip() throws Exception {
		roundTripOBOFile("multiple_def_xref_test.obo", true);
	}
	
	@Test
	public void testDefinitions() throws Exception {
		OWLOntology owlOnt = convertOBOFile("multiple_def_xref_test.obo");
		int n = 0;
		for (OWLAxiom ax : owlOnt.getAxioms()) {
			//System.out.println(ax);
			for (OWLAnnotation ann : ax.getAnnotations()) {
				System.out.println(" NESTED:"+ann);
				OWLAnnotationProperty p = ann.getProperty();
				System.out.println(" P:"+p);
				if (p.getIRI().equals(IRI.create("http://www.geneontology.org/formats/oboInOwl#hasDbXref"))) {
					OWLLiteral v = (OWLLiteral)ann.getValue();
					System.out.println(" V:"+v);
					// expect this twice, as we have annotations on synonyms
					if (v.getLiteral().equals("BTO:0001750"))
						n++;
					if (v.getLiteral().equals("Wikipedia:Mandibular_condyle"))
						n++;
							
				}
				
			}
		}
		assertEquals(3, n);
	}
	
	private OWLOntology convertOBOFile(String fn) throws Exception {
		return convert(parseOBOFile(fn), fn);
	}

}
