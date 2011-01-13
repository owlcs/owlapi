package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class TestOWLClassExpressionsAndObjectOneOf extends TestCase{
	public void testAnonymous() throws Exception{
		String text="Prefix(:=<http://example.org/#>)\n Ontology(<http://example.org/>\n SubClassOf(\n:man\n ObjectSomeValuesFrom(\n :like\n ObjectOneOf(\n_:c\n)\n)\n)\n\n ClassAssertion(\n:car\n_:c\n)\n)";
		System.out
				.println(text);
		StringDocumentSource input=new StringDocumentSource(text);
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntology o=m.loadOntologyFromOntologyDocument(input);
		for(OWLAxiom ax:o.getAxioms()) {
			System.out
					.println("TestOWLClassExpressionsAndObjectOneOf.testAnonymous() "+ax);
		}
	}
	
}
