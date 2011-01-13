package org.semanticweb.owlapi.api.test;

import java.util.Set;

import junit.framework.TestCase;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;

public class ManchesterOWLSyntaxMissingCatchTestCase extends TestCase {
	public void testManSyntaxEditorParser() {
		String onto = "<?xml version=\"1.0\"?>"
				+ "<!DOCTYPE rdf:RDF ["
				+ "<!ENTITY vin  \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\" >"
				+ "<!ENTITY food \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#\" >"
				+ "<!ENTITY owl  \"http://www.w3.org/2002/07/owl#\" >"
				+ "<!ENTITY xsd  \"http://www.w3.org/2001/XMLSchema#\" >"
				+ "]>"
				+ "<rdf:RDF"
				+ "xmlns     = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\""
				+ "xmlns:vin = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\""
				+ "xml:base  = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\""
				+ "xmlns:food= \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#\""
				+ "xmlns:owl = \"http://www.w3.org/2002/07/owl#\""
				+ "xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\""
				+ "xmlns:rdfs= \"http://www.w3.org/2000/01/rdf-schema#\""
				+ "xmlns:xsd = \"http://www.w3.org/2001/XMLSchema#\">"
				+ "<owl:Ontology rdf:about=\"\"><rdfs:comment>An example OWL ontology</rdfs:comment>"
				+ "<rdfs:label>Wine Ontology</rdfs:label></owl:Ontology>"
				+ "<owl:Class rdf:ID=\"VintageYear\" />"
				+ "<owl:DatatypeProperty rdf:ID=\"yearValue\"><rdfs:domain rdf:resource=\"#VintageYear\" />    <rdfs:range  rdf:resource=\"&xsd;positiveInteger\" />"
				+ "</owl:DatatypeProperty></rdf:RDF>";
		try {
			String expression = "yearValue some ";
			final OWLOntologyManager mngr = OWLManager
					.createOWLOntologyManager();
			final OWLOntology wine = mngr
					.loadOntologyFromOntologyDocument(new StringDocumentSource(
							onto));
			//					loadOntologyFromOntologyDocument(IRI
			//							.create("http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine"));
			final Set<OWLOntology> ontologies = mngr.getOntologies();
			ShortFormProvider sfp = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
					mngr, wine);
			BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
					ontologies, sfp);
			ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
					mngr.getOWLDataFactory(), expression);
			parser.setDefaultOntology(wine);
			parser.setOWLEntityChecker(new ShortFormEntityChecker(
					shortFormProvider));
			OWLClassExpression cls = parser.parseClassExpression();
		} catch (UnparsableOntologyException e) {
		} catch (RuntimeException e) {
			e.printStackTrace();
			fail();
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
			e.printStackTrace();
		}
	}
}
