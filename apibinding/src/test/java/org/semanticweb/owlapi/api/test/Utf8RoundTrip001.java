package org.semanticweb.owlapi.api.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Utf8RoundTrip001 extends TestCase {
	public void testUTF8roundTrip() {
		try {
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			String onto = "Ontology(<http://protege.org/UTF8.owl>"
					+ "Declaration(Class(<http://protege.org/UTF8.owl#A>))"
					+ "AnnotationAssertion(<http://www.w3.org/2000/01/rdf-schema#label> <http://protege.org/UTF8.owl#A> "
					+ "\"Chinese=處方\"^^<http://www.w3.org/2001/XMLSchema#string>))";
			ByteArrayInputStream in = new ByteArrayInputStream(onto.getBytes());
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			manager.saveOntology(manager.loadOntologyFromOntologyDocument(in),
					out);
			System.out.println("Utf8RoundTrip001.testUTF8roundTrip()\n"+out.toString());
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
	}

	public void testPositiveUTF8roundTrip() {
		try {
			String NS = "http://protege.org/UTF8.owl";
			OWLOntologyManager manager;
			OWLOntology ontology;
			manager = OWLManager.createOWLOntologyManager();
			ontology = manager.createOntology(IRI.create(NS));
			OWLDataFactory factory = manager.getOWLDataFactory();
			OWLClass a = factory.getOWLClass(IRI.create(NS + "#A"));
			manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(a));
			OWLAnnotationAssertionAxiom axiom = factory
					.getOWLAnnotationAssertionAxiom(a.getIRI(), factory
							.getOWLAnnotation(factory.getRDFSLabel(),
									factory.getOWLLiteral("Chinese=處方")));
			manager.addAxiom(ontology, axiom);
			File ontFile = File.createTempFile("OWLApi-utf8", "owl");
			manager.saveOntology(ontology,
					new OWLFunctionalSyntaxOntologyFormat(),
					IRI.create(ontFile));
			System.out.println("Saved as " + ontFile);
			manager = OWLManager.createOWLOntologyManager();
			ontology = manager.loadOntologyFromOntologyDocument(ontFile);
		} catch (Throwable t) {
			t.printStackTrace();
			fail();
		}
	}
}
