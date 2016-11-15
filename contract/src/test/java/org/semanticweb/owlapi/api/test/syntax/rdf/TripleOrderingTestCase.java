package org.semanticweb.owlapi.api.test.syntax.rdf;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormatFactory;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;

public class TripleOrderingTestCase extends TestBase {

	@Test
	public void testTurtleTriplesOrder() {
		String ontFirst = "<http://example.org/ont> a <http://www.w3.org/2002/07/owl#Ontology> ; <http://www.w3.org/2000/01/rdf-schema#label> \"An ontology\" . ";
		String labelFirst = "<http://example.org/ont> <http://www.w3.org/2000/01/rdf-schema#label> \"An ontology\" ; a <http://www.w3.org/2002/07/owl#Ontology> . ";
		
		OWLOntology ontology1 = this.loadOntologyFromString(ontFirst, new TurtleDocumentFormatFactory().createFormat());
		OWLOntology ontology2 = this.loadOntologyFromString(labelFirst, new TurtleDocumentFormatFactory().createFormat());
		assertEquals("Should both have a label annotation", ontology1.getAnnotations(), ontology2.getAnnotations());

		OWLOntology ontology3 = this.loadOntologyFromString(ontFirst, new RioTurtleDocumentFormatFactory().createFormat());
		OWLOntology ontology4 = this.loadOntologyFromString(labelFirst, new RioTurtleDocumentFormatFactory().createFormat());
		assertEquals("Should both have a label annotation", ontology3.getAnnotations(), ontology4.getAnnotations());
	}

}
