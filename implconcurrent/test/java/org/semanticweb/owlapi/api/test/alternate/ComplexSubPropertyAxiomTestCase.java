package org.semanticweb.owlapi.api.test.alternate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 02-Feb-2009
 */
public class ComplexSubPropertyAxiomTestCase extends
		AbstractFileRoundTrippingTestCase {
	public void testContains() {
		Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
		OWLObjectProperty propP = getOWLObjectProperty("p");
		OWLObjectProperty propQ = getOWLObjectProperty("q");
		OWLObjectProperty propR = getOWLObjectProperty("r");
		List<OWLObjectProperty> chain = new ArrayList<OWLObjectProperty>();
		chain.add(propP);
		chain.add(propQ);
		axioms.add(getFactory().getOWLSubPropertyChainOfAxiom(chain, propR));
		assertEquals(getOnt().getAxioms(), axioms);
	}

	@Override  @SuppressWarnings("unused")
	protected void handleSaved(StringDocumentTarget target,
			OWLOntologyFormat format) {
		System.out.println(target);
	}

	@Override
	protected String getFileName() {
		return "ComplexSubProperty.rdf";
	}
}
