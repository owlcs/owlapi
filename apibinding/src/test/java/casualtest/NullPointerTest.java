package casualtest;

import junit.framework.TestCase;

import org.coode.owlapi.obo.parser.OBOOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class NullPointerTest extends TestCase {
	public void testOntologyWithIRI() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology(IRI.create("urn:test:test"));
		m.saveOntology(o, new OBOOntologyFormat(),
				new StringDocumentTarget());
	}

	public void testOntologyWithNoIRI() throws Exception {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m.createOntology();
		m.saveOntology(o, new OBOOntologyFormat(),
				new StringDocumentTarget());
	}
}
