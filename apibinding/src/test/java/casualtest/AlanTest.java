package casualtest;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;

@SuppressWarnings("javadoc")
public class AlanTest extends TestCase{
	public void testUndeclaredClasses() throws Exception{
		String input="<?xml version=\"1.0\"?>\n"+
"<rdf:RDF xmlns=\"http://purl.obolibrary.org/obo/obi/example/proplose.owl#\"\n"+
"xml:base=\"http://purl.obolibrary.org/obo/obi/example/proplose.owl\"\n"+
"xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"+
"xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"+
"xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"+
"<owl:Ontology rdf:about=\"http://purl.obolibrary.org/obo/obi/example/proplose.owl\"/>\n"+

"<owl:Class rdf:about=\"http://purl.obolibrary.org/obo/obi/proplose/a\">\n"+
"<rdfs:subClassOf>\n"+
"<owl:Restriction>\n"+
"<owl:onProperty rdf:resource=\"http://purl.obolibrary.org/obo/obi/proplose/p\"/>\n"+
"<owl:someValuesFrom rdf:resource=\"http://purl.obolibrary.org/obo/obi/proplose/b\"/>\n"+
"</owl:Restriction>\n"+
"</rdfs:subClassOf>\n"+
"</owl:Class>\n"+
"</rdf:RDF>";

		StringDocumentSource o=new StringDocumentSource(input);
		OWLOntologyManager m=OWLManager.createOWLOntologyManager();
		OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
		config=config.setStrict(true);
		OWLOntology onto=m.loadOntologyFromOntologyDocument(o, config);
//		for(OWLAxiom ax:onto.getAxioms()) {
//			System.out.println(ax);
//		}

		m.saveOntology(onto, new OWLFunctionalSyntaxOntologyFormat(), new StringDocumentTarget());
	}
}
