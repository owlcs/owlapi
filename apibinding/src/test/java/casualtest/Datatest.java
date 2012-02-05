package casualtest;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntologyManager;
@SuppressWarnings("javadoc")
public class Datatest extends TestCase {
	public void testData() throws Exception {
		String input = "<?xml version=\"1.0\"?>\n"
				+ "<rdf:RDF xmlns=\"http://dbpedia.org/ontology/\"\n"
				+ "xml:base=\"http://dbpedia.org/ontology/\"\n"
				+ "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
				+ "xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
				+ "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">\n"
				+ "<owl:Class rdf:about=\"http://dbpedia.org/ontology/Lake\" />\n"
				+ "<!-- <rdfs:Datatype rdf:about=\"http://dbpedia.org/datatype/squareKilometre\"/> -->\n"
				+ "<owl:DatatypeProperty rdf:about=\"http://dbpedia.org/ontology/Lake/areaOfCatchment\" >\n"
				+ "	<rdfs:domain rdf:resource=\"http://dbpedia.org/ontology/Lake\"/>\n"
				+ "	<rdfs:range rdf:resource=\"http://dbpedia.org/datatype/squareKilometre\"/>\n"
				+ "</owl:DatatypeProperty>\n" + "</rdf:RDF>";
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		m.saveOntology(
				m.loadOntologyFromOntologyDocument(new StringDocumentSource(input)),
				new OWLFunctionalSyntaxOntologyFormat(), new StringDocumentTarget());
	}
}
