package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

import junit.framework.TestCase;

public class TestPlainLiteral extends TestCase {
	public void testPlainLiteral() {
		IRI iri = IRI
				.create("http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral");
		assertTrue(iri.isPlainLiteral());
		assertNotNull(OWLManager.getOWLDataFactory().getRDFPlainLiteral());
		assertNotNull(OWL2Datatype.getDatatype(iri));
	}

	public void testPlainLiteralFromEvren() {
		OWLDataFactory factory = OWLManager.createOWLOntologyManager()
				.getOWLDataFactory();
		OWLDatatype node = factory.getRDFPlainLiteral();
		assertTrue(node.isBuiltIn());
		assertNotNull(node.getBuiltInDatatype());
	}
}
