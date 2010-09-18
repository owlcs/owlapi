package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class Test {
	public static void main(String[] args) throws Exception {
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLDataFactory f=m.getOWLDataFactory();
		OWLOntology o=m.createOntology();
	
	}
}
