package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.profiles.OWL2RLProfile;
import org.semanticweb.owlapi.profiles.OWLProfileReport;
import org.semanticweb.owlapi.profiles.OWLProfileViolation;

import junit.framework.TestCase;

public class TestProfile extends TestCase {
	public void testOWLEL() throws Exception{
		String onto = "<?xml version=\"1.0\"?>\n"
				+ "<!DOCTYPE rdf:RDF [\n"
				+ "<!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n"
				+ "<!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n"
				+ "<!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n"
				+ "]>\n"
				+ "<rdf:RDF xmlns=\"http://xmlns.com/foaf/0.1/\"\n"
				+ "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
				+ "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
				+ "xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
				+ "<owl:Ontology rdf:about=\"http://ex.com\"/>\n"
				+ "<rdf:Property rdf:about=\"http://ex.com#p1\">\n"
				+ "<rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/>\n"
				+ "</rdf:Property>\n"
				+ "<rdf:Property rdf:about=\"http://ex.com#p2\">\n"
				+ "<rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#ObjectProperty\"/>\n"
				+ "<rdfs:subPropertyOf rdf:resource=\"http://ex.com#p1\"/>\n"
				+ "</rdf:Property>\n" + "</rdf:RDF>";
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntology o=m.loadOntologyFromOntologyDocument(new StringDocumentSource(onto));
		OWL2RLProfile p=new OWL2RLProfile();
		OWLProfileReport report=p.checkOntology(o);
		for(OWLProfileViolation v: report.getViolations()) {
			System.out.println("TestProfile.testOWLEL() "+v);
		}
	}
}
