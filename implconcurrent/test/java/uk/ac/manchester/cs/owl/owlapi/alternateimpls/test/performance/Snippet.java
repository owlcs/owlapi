package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

public class Snippet extends TestCase{
	public void testEncoding(){
	
	try {
	OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
	OWLDataFactory df = mngr.getOWLDataFactory();
	
	IRI aIRI = IRI.create("http://a.com");
	OWLOntology a = mngr.createOntology(aIRI);
	
	OWLClass clsA = df.getOWLClass(IRI.create("http://a.com/°"));
	OWLClass clsB = df.getOWLClass(IRI.create("http://a.com/b"));
	mngr.applyChange(new AddAxiom(a, df.getOWLSubClassOfAxiom(clsA, clsB)));
	
	// save a.owl
	File f = new File("a.owl");
	System.out.println("saving to " + f.getAbsolutePath());
	OutputStream out = new FileOutputStream(f);
	mngr.saveOntology(a, out);
	}
	catch (Exception e) {
	e.printStackTrace();
	fail();
	}
	}
}

