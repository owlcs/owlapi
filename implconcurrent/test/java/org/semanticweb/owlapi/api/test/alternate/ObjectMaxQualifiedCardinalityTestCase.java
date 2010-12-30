package org.semanticweb.owlapi.api.test.alternate;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class ObjectMaxQualifiedCardinalityTestCase extends
		AbstractFileRoundTrippingTestCase {
	public void testCorrectAxioms() {
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
		OWLClass clsA = getOWLClass("A");
		OWLClass clsB = getOWLClass("B");
		OWLObjectProperty prop = getOWLObjectProperty("p");
		axioms.add(getFactory().getOWLDeclarationAxiom(prop));
		axioms.add(getFactory().getOWLSubClassOfAxiom(clsA,
				getFactory().getOWLObjectMaxCardinality(3, prop, clsB)));
		assertEquals(getOnt().getAxioms(), axioms);
	}

	@Override  @SuppressWarnings("unused")
	protected void handleSaved(StringDocumentTarget target,
			OWLOntologyFormat format) {
		System.out.println(target);
	}

	@Override
	protected String getFileName() {
		return "ObjectMaxQualifiedCardinality.rdf";
	}
}
