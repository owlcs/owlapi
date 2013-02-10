package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.net.URISyntaxException;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

public class TurtleTestCase {
    @Test
    public void testLoadingUTF8BOM() throws URISyntaxException,
            OWLOntologyCreationException {
        IRI uri = IRI.create(getClass().getResource("/ttl-with-bom.ttl").toURI());
        Factory.getManager().loadOntologyFromOntologyDocument(uri);
    }

    @Test
    public void shouldParseOntologyThatworked() throws OWLOntologyCreationException {
        // given
        String working = "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n"
                + "foaf:fundedBy rdfs:isDefinedBy <http://xmlns.com/foaf/0.1/> .";
        OWLDataFactory df = Factory.getFactory();
        OWLAxiom expected = AnnotationAssertion(df.getRDFSIsDefinedBy(),
                IRI("http://xmlns.com/foaf/0.1/fundedBy"),
                IRI("http://xmlns.com/foaf/0.1/"));
        // when
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(working));
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    @Test
    public void shouldParseOntologyThatBroke() throws OWLOntologyCreationException {
        // given
        String input = "@prefix f:    <urn:test/> . f:r f:p f: .";
        OWLDataFactory df = Factory.getFactory();
        OWLAxiom expected = df.getOWLAnnotationAssertionAxiom(
                df.getOWLAnnotationProperty(IRI("urn:test/p")), IRI("urn:test/r"),
                IRI("urn:test/"));
        // when
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    // test for 3309666
    @Test
    public void shouldResolveAgainstBase() throws OWLOntologyCreationException {
        // given
        String input = "@base <http://test.org/path#> .\n" + "<a1> <b1> <c1> .";
        // when
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        // then
        String axioms = o.getAxioms().toString();
        assertTrue(axioms.contains("http://test.org/a1"));
        assertTrue(axioms.contains("http://test.org/b1"));
        assertTrue(axioms.contains("http://test.org/c1"));
    }

    // test for 3543488
    @Test
    public void shouldRoundTripTurtleWithsharedBnodes()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        String input = "@prefix ex: <http://example.com/test> .\n"
                + "ex:ex1 a ex:Something ; ex:prop1 _:a .\n"
                + "_:a a ex:Something1 ; ex:prop2 _:b .\n"
                + "_:b a ex:Something ; ex:prop3 _:a .";
        OWLOntology ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        StringDocumentTarget t = new StringDocumentTarget();
        TurtleOntologyFormat format = new TurtleOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto1 = t.toString();
        ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(t.toString()));
        t = new StringDocumentTarget();
        format = new TurtleOntologyFormat();
        ontology.getOWLOntologyManager().saveOntology(ontology, format, t);
        String onto2 = t.toString();
        assertEquals(onto1, onto2);
    }

    // test for 335
    @Test
    public void shouldParseScientificNotation() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1e+07 .";
        OWLOntology ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature().contains(p));
        IRI s = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, s, Literal(10000000))));
    }

    @Test
    public void shouldParse_one() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1 .";
        OWLOntology ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature().contains(p));
        IRI s = IRI("http://dbpedia.org/resource/South_Africa");
        System.out.println("TurtleSharedBlankNodeTestCase.shouldParseTen() "
                + ontology.getAxioms());
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, s, Literal(1))));
    }

    @Test
    public void shouldParseOne() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1.0.";
        OWLOntology ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature().contains(p));
        IRI s = IRI("http://dbpedia.org/resource/South_Africa");
        System.out.println("TurtleSharedBlankNodeTestCase.shouldParseTen() "
                + ontology.getAxioms());
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, s,
                Literal("1.0", OWL2Datatype.XSD_DECIMAL))));
    }
}
