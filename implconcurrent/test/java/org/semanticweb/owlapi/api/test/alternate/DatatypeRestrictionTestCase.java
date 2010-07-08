package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class DatatypeRestrictionTestCase extends
		AbstractFileRoundTrippingTestCase {
	public void testCorrectAxioms() {
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
		OWLDataRange dr = getFactory()
				.getOWLDatatypeRestriction(
						getFactory().getIntegerOWLDatatype(),
						getFactory().getOWLFacetRestriction(
								OWLFacet.MIN_INCLUSIVE, 18),
						getFactory().getOWLFacetRestriction(
								OWLFacet.MAX_INCLUSIVE, 30));
		OWLDataProperty p = getOWLDataProperty("p");
		OWLDataPropertyRangeAxiom ax = getFactory()
				.getOWLDataPropertyRangeAxiom(p, dr);
		axioms.add(ax);
		axioms.add(getFactory().getOWLDeclarationAxiom(p));
		assertEquals(getOnt().getAxioms(), axioms);
	}

	protected void handleSaved(StringDocumentTarget target,
			OWLOntologyFormat format) {
		System.out.println(target);
	}

	protected String getFileName() {
		return "DatatypeRestriction.rdf";
	}
}
