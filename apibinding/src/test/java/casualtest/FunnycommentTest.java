package casualtest;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class FunnycommentTest extends TestCase {
	public void testUndeclaredClasses() throws Exception {
		String input = "<?xml version=\"1.0\"?>\n"
				+ "<rdf:RDF xmlns=\"http://purl.obolibrary.org/obo/obi/example/proplose.owl#\"\n"
				+ "xml:base=\"http://purl.obolibrary.org/obo/obi/example/proplose.owl\"\n"
				+ "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
				+ "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
				+ "xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
				+ "<owl:Ontology rdf:about=\"http://purl.obolibrary.org/obo/obi/example/proplose.owl\"/>\n"

				+ "<owl:Class rdf:about=\"http://purl.obolibrary.org/obo/obi/proplose/a--test\">\n"
				+ "</owl:Class>\n" + "</rdf:RDF>";

			StringDocumentSource o = new StringDocumentSource(input);
			OWLOntologyManager m = OWLManager.createOWLOntologyManager();

			OWLOntology onto = m.loadOntologyFromOntologyDocument(o);

			StringDocumentTarget documentTarget = new StringDocumentTarget();
			m.saveOntology(onto, documentTarget);
			m.removeOntology(onto);
			o = new StringDocumentSource(documentTarget.toString());
			onto = m.loadOntologyFromOntologyDocument(o);
	}
}
