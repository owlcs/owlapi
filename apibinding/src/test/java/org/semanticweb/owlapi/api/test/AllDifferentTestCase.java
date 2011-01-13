package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AllDifferentTestCase extends TestCase {
	private String onto1 = "<?xml version=\"1.0\"?>\n"
			+ "<rdf:RDF xml:base = \"http://example.org/\" "
			+ "xmlns = \"http://example.org/\" xmlns:owl = \"http://www.w3.org/2002/07/owl#\" "
			+ "xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"> <owl:Ontology/>"
			+ "<owl:AllDifferent> "
			+ "<owl:distinctMembers rdf:parseType=\"Collection\"> "
			+ "<rdf:Description rdf:about=\"Peter\" /> "
			+ "<rdf:Description rdf:about=\"Peter_Griffin\" /> "
			+ "<rdf:Description rdf:about=\"Petre\" /> "
			+ "</owl:distinctMembers> </owl:AllDifferent> </rdf:RDF>";
	private String onto2 = "<?xml version=\"1.0\"?>\n"
			+ "<rdf:RDF xml:base = \"http://example.org/\" xmlns = \"http://example.org/\" "
			+ "xmlns:owl = \"http://www.w3.org/2002/07/owl#\" "
			+ "xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"><owl:Ontology/>"
			+ "<owl:AllDifferent><owl:members rdf:parseType=\"Collection\">"
			+ "<rdf:Description rdf:about=\"Peter\" />"
			+ "<rdf:Description rdf:about=\"Peter_Griffin\" />"
			+ "<rdf:Description rdf:about=\"Petre\" />"
			+ "</owl:members></owl:AllDifferent></rdf:RDF>";

	public void testDistinctMembers() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o1 = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(
						onto1));
		System.out.println("AllDifferentTestCase.testDistinctMembers() "
				+ o1.getLogicalAxiomCount());
		OWLOntology o2 = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(
						onto2));
		System.out.println("AllDifferentTestCase.testDistinctMembers() "
				+ o2.getLogicalAxiomCount());
		assertTrue(o1.getLogicalAxiomCount() == o2.getLogicalAxiomCount());
	}
}
