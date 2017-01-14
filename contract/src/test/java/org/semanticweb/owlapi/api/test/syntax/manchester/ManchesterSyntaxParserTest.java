package org.semanticweb.owlapi.api.test.syntax.manchester;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.manchestersyntax.parser.ManchesterOWLSyntaxClassExpressionParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManchesterSyntaxParserTest {

	static final Logger LOGGER = LoggerFactory.getLogger(ManchesterSyntaxParserTest.class);

	@Test
	public void testParseDataCardinalityExpression() throws OWLOntologyCreationException {
		OWLDataFactory factory = OWLManager.getOWLDataFactory();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLDataProperty hasAge = factory.getOWLDataProperty(IRI.create("http://example.org/hasAge"));
		OWLOntology ont = manager.createOntology();
		manager.addAxiom(ont, factory.getOWLDeclarationAxiom(hasAge));
		ShortFormProvider sfp = new SimpleShortFormProvider();
		BidirectionalShortFormProviderAdapter adapter = new BidirectionalShortFormProviderAdapter(manager.getOntologies(), sfp);
		OWLEntityChecker checker = new ShortFormEntityChecker(adapter);
		ManchesterOWLSyntaxClassExpressionParser parser = new ManchesterOWLSyntaxClassExpressionParser(factory, checker);
		
		// Currently succeeds
		assertEquals("Should parse data exact cardinality expression including data range", 
				factory.getOWLDataExactCardinality(1, hasAge, OWL2Datatype.XSD_INT), 
				parser.parse("hasAge exactly 1 xsd:int"));
		// Currently fails
		assertEquals("Should parse data exact cardinality expression without data range", 
				factory.getOWLDataExactCardinality(1, hasAge), 
				parser.parse("hasAge exactly 1"));

		// Currently succeeds
		assertEquals("Should parse data min cardinality expression including data range", 
				factory.getOWLDataMinCardinality(1, hasAge, OWL2Datatype.XSD_INT), 
				parser.parse("hasAge min 1 xsd:int"));
		// Currently fails
		assertEquals("Should parse data min cardinality expression without data range", 
				factory.getOWLDataMinCardinality(1, hasAge), 
				parser.parse("hasAge min 1"));

		// Currently succeeds
		assertEquals("Should parse data max cardinality expression including data range", 
				factory.getOWLDataMaxCardinality(1, hasAge, OWL2Datatype.XSD_INT), 
				parser.parse("hasAge max 1 xsd:int"));
		// Currently fails
		assertEquals("Should parse data max cardinality expression without data range", 
				factory.getOWLDataMaxCardinality(1, hasAge), 
				parser.parse("hasAge max 1"));
	}

}
