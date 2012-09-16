package org.semanticweb.owlapi.api.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** Test for 3440117 */
@SuppressWarnings("javadoc")
public class TurtleQNamesTestCase {
    @Test
    public void shouldParseOntologyThatworked() throws OWLOntologyCreationException,
    OWLOntologyStorageException {
        // given
        String working = "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n"
                + "foaf:fundedBy rdfs:isDefinedBy <http://xmlns.com/foaf/0.1/> .";
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        OWLAxiom expected = df.getOWLAnnotationAssertionAxiom(df.getRDFSIsDefinedBy(),
                IRI.create("http://xmlns.com/foaf/0.1/fundedBy"),
                IRI.create("http://xmlns.com/foaf/0.1/"));
        // when
        OWLOntology o = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(working));
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    @Test
    public void shouldParseOntologyThatBroke() throws OWLOntologyCreationException,
    OWLOntologyStorageException {
        // given
        String input = "@prefix f:    <urn:test/> . f:r f:p f: .";
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        OWLAxiom expected = df.getOWLAnnotationAssertionAxiom(
                df.getOWLAnnotationProperty(IRI.create("urn:test/p")),
                IRI.create("urn:test/r"), IRI.create("urn:test/"));
        // when
        OWLOntology o = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    // test for 3309666
    @Test
    public void shouldResolveAgainstBase() throws OWLOntologyCreationException,
    OWLOntologyStorageException {
        // given
        String input = "@base <http://test.org/path#> .\n" + "<a1> <b1> <c1> .";
        // when
        OWLOntology o = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(new StringDocumentSource(input));
        // then
        String axioms = o.getAxioms().toString();
        assertTrue(axioms.contains("http://test.org/path#a1"));
        assertTrue(axioms.contains("http://test.org/path#b1"));
        assertTrue(axioms.contains("http://test.org/path#c1"));
    }
}
