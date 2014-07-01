/**
 * 
 */
package org.semanticweb.owlapi.rio.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.NTriplesOntologyFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDOntologyFormat;
import org.semanticweb.owlapi.formats.RDFJsonOntologyFormat;
import org.semanticweb.owlapi.formats.RioTurtleOntologyFormat;
import org.semanticweb.owlapi.formats.TrigOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings({ "javadoc" })
public class RioParseDocumentSourceTest extends TestBase {

	@Test
	public void testParse() throws Exception {
		for (int count = 0; count < 5; count++) {
			for (int i = 0; i < 5; i++) {
				OWLOntology ontology = loadOntologyFromString(new StringDocumentSource(
						"<urn:test:a1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#AnnotationProperty> .",
						IRI.create("urn:test:b1"),
						new NTriplesOntologyFormat(), RDFFormat.NTRIPLES
								.getDefaultMIMEType()));
				assertEquals(1, ontology.getAxiomCount());
			}
			for (int i = 0; i < 5; i++) {
				OWLOntology ontology = loadOntologyFromString(new StringDocumentSource(
						"<urn:test:a1> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/2002/07/owl#AnnotationProperty> .",
						IRI.create("urn:test:b1"),
						new RioTurtleOntologyFormat(), RDFFormat.TURTLE
								.getDefaultMIMEType()));
				assertEquals(1, ontology.getAxiomCount());
			}
			for (int i = 0; i < 5; i++) {
				OWLOntology ontology = loadOntologyFromString(new StringDocumentSource(
						"{ \"urn:test:a1\" : { \"http://www.w3.org/1999/02/22-rdf-syntax-ns#type\" : [ { \"value\" : \"http://www.w3.org/2002/07/owl#AnnotationProperty\", \"type\" : \"uri\" } ] } }",
						IRI.create("urn:test:b1"), new RDFJsonOntologyFormat(),
						RDFFormat.RDFJSON.getDefaultMIMEType()));
				assertEquals(1, ontology.getAxiomCount());
			}
			for (int i = 0; i < 5; i++) {
				OWLOntology ontology = loadOntologyFromString(new StringDocumentSource(
						"[ { \"@id\" : \"urn:test:a1\", \"@type\" : [ \"http://www.w3.org/2002/07/owl#AnnotationProperty\" ] } ]",
						IRI.create("urn:test:b1"),
						new RDFJsonLDOntologyFormat(), RDFFormat.JSONLD
								.getDefaultMIMEType()));
				assertEquals(1, ontology.getAxiomCount());
			}
			for (int i = 0; i < 5; i++) {
				OWLOntology ontology = loadOntologyFromString(new StringDocumentSource(
						"{ <urn:test:a1> a <http://www.w3.org/2002/07/owl#AnnotationProperty> . }",
						IRI.create("urn:test:b1"), new TrigOntologyFormat(),
						RDFFormat.TRIG.getDefaultMIMEType()));
				assertEquals(1, ontology.getAxiomCount());
			}
		}
	}

}
