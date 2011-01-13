package org.semanticweb.owlapi.api.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class TestImportByLocation extends TestCase {
	public void testImportOntologyByLocation() {
		File f = new File("a.owl");
		try {

			createOntologyFile(IRI.create("http://a.com"), f);
			OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
			OWLDataFactory df = mngr.getOWLDataFactory();
			// have to load an ontology for it to get a document IRI
			OWLOntology a = mngr.loadOntologyFromOntologyDocument(f);
			IRI locA = mngr.getOntologyDocumentIRI(a);
			System.out.println("locA = " + locA);
			IRI bIRI = IRI.create("http://b.com");
			OWLOntology b = mngr.createOntology(bIRI);
			// import from the document location of a.owl (rather than the ontology IRI)
			mngr.applyChange(new AddImport(b, df.getOWLImportsDeclaration(locA)));
			assertEquals(1, b.getImportsDeclarations().size());
			assertEquals(1, b.getImports().size());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		f.delete();
	}

	private OWLOntology createOntologyFile(IRI iri, File f) throws Exception {
		OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
		OWLOntology a = mngr.createOntology(iri);
		System.out.println("saving to " + f.getAbsolutePath());
		OutputStream out = new FileOutputStream(f);
		mngr.saveOntology(a, out);
		return a;
	}
}
