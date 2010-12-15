package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class DataComplementOfTestCase extends AbstractFileRoundTrippingTestCase {
	public void testCorrectAxioms() {
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
		OWLDataRange intdr = getFactory().getIntegerOWLDatatype();
		OWLDataRange complement = getFactory().getOWLDataComplementOf(intdr);
		OWLDataProperty p = getOWLDataProperty("p");
		OWLDataPropertyRangeAxiom ax = getFactory()
				.getOWLDataPropertyRangeAxiom(p, complement);
		axioms.add(ax);
		axioms.add(getFactory().getOWLDeclarationAxiom(p));
		assertEquals(getOnt().getAxioms(), axioms);
	}

	@Override
	protected void handleSaved(StringDocumentTarget target,
			OWLOntologyFormat format) {
		System.out.println(target);
	}

	@Override
	protected String getFileName() {
		return "DataComplementOf.rdf";
	}
}
