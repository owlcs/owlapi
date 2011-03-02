package org.semanticweb.owlapi.api.test;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class TestUncheckedException extends TestCase{
	public void testLoad() throws Exception{
		try {
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		//OWLOntology o= 
			m.loadOntology(IRI.create("http://rest.bioontology.org/bioportal/virtual/download/1005"));
		}catch (RuntimeException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
