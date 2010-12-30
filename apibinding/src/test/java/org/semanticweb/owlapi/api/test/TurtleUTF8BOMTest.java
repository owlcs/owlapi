package org.semanticweb.owlapi.api.test;

import java.net.URI;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;

import junit.framework.TestCase;


public class TurtleUTF8BOMTest extends TestCase{
	public void testLoadingUTF8BOM() {
		try {
		IRI uri = IRI.create(getClass().getResource("/ttl-with-bom.ttl" ).toURI());
		OWLManager.createOWLOntologyManager().loadOntologyFromOntologyDocument(uri);
		}catch (Exception e) {
			e.printStackTrace(System.out);
			fail(e.getMessage());
		}
	}
	
}
