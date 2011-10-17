package casualtest;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
@SuppressWarnings("javadoc")
public class TestDatatype extends TestCase {
	public void testDatatype() throws Exception {
		String premise = "<rdf:RDF\n"
				+ "xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
				+ "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
				+ "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
				+ "xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n"
				+ "xmlns:first=\"http://www.example.org/onto#\"\n"
				+ "xml:base=\"http://www.example.org/onto#\" >\n"
				+ "<owl:Ontology/>\n"
				+ "<rdfs:Datatype rdf:ID=\"DT_Restriction\">\n"
				+ "<owl:equivalentClass>"
				+ "<rdfs:Datatype>\n"
				+ "<owl:onDataType rdf:resource=\"http://www.w3.org/2001/XMLSchema#integer\"/>\n"
				+ "<owl:withRestrictions rdf:parseType=\"Collection\">\n"
				+ "<rdf:Description>\n"
				+ "<xsd:minExclusive rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">1</xsd:minExclusive>\n"
				+ "</rdf:Description>\n"
				+ "<rdf:Description>\n"
				+ "<xsd:maxInclusive rdf:datatype=\"http://www.w3.org/2001/XMLSchema#integer\">12</xsd:maxInclusive>\n"
				+ "</rdf:Description>\n" + "</owl:withRestrictions>\n"
				+ "</rdfs:Datatype>\n" + "</owl:equivalentClass>"
				+ "</rdfs:Datatype>\n" + "</rdf:RDF>";
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		OWLOntology o = m
				.loadOntologyFromOntologyDocument(new StringDocumentSource(
						premise));
		for (OWLAxiom ax : o.getAxioms()) {
			System.out.println(ax);
		}
		OWLDataFactory f = m.getOWLDataFactory();
		OWLDatatypeRestriction r = f.getOWLDatatypeRestriction(
				f.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI()),
				f.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, 1),
				f.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, 12));
		OWLDatatype type = f.getOWLDatatype(IRI
				.create("http://www.example.org/onto#DT_Restriction"));
		OWLOntology test = m.createOntology();
		m.addAxiom(test, f.getOWLDeclarationAxiom(type));
		m.addAxiom(test, f.getOWLDatatypeDefinitionAxiom(type, r));
		StringDocumentTarget t = new StringDocumentTarget();
		m.saveOntology(test, t);
		//System.out.println(t.toString());
		o = m.loadOntologyFromOntologyDocument(new StringDocumentSource(t
				.toString()));
//		for (OWLAxiom ax : o.getAxioms()) {
//			System.out.println(ax);
//		}
	}
}
