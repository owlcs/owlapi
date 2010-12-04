package org.semanticweb.owlapi.api.test;

import java.io.ByteArrayOutputStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

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

	public void testPlainLiteralSerialization() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology();
		OWLDataProperty p = m.getOWLDataFactory().getOWLDataProperty(
				IRI.create("urn:test#p"));
		OWLIndividual i = m.getOWLDataFactory().getOWLNamedIndividual(
				IRI.create("urn:test#ind"));
		OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
				OWL2Datatype.RDF_PLAIN_LITERAL);
		m.addAxiom(o,
				m.getOWLDataFactory().getOWLDataPropertyAssertionAxiom(p, i, l));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.saveOntology(o, out);
		String expected = "<test:p>test</test:p>";
		assertTrue(out.toString().contains(expected));
	}

	public void testPlainLiteralSerializationComments() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology();
		OWLIndividual i = m.getOWLDataFactory().getOWLNamedIndividual(
				IRI.create("urn:test#ind"));
		OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
				OWL2Datatype.RDF_PLAIN_LITERAL);
		m.addAxiom(
				o,
				m.getOWLDataFactory().getOWLAnnotationAssertionAxiom(
						m.getOWLDataFactory().getOWLAnnotationProperty(
								OWLRDFVocabulary.RDFS_COMMENT.getIRI()),
						i.asOWLNamedIndividual().getIRI(), l));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.saveOntology(o, out);
		String expected = "<rdfs:comment>test</rdfs:comment>";
		assertTrue(out.toString().contains(expected));
	}

	public void testPlainLiteralSerializationComments2() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology();
		OWLLiteral l = m.getOWLDataFactory().getOWLLiteral("test",
				OWL2Datatype.RDF_PLAIN_LITERAL);
		OWLAnnotation a = m.getOWLDataFactory().getOWLAnnotation(
				m.getOWLDataFactory().getRDFSComment(), l);
		m.applyChange(new AddOntologyAnnotation(o, a));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		m.saveOntology(o, out);
		String expected = "<rdfs:comment>test</rdfs:comment>";
		System.out.println(out.toString());
		assertTrue(out.toString().contains(expected));
	}
}
