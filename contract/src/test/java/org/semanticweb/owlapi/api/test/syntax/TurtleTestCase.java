package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

@SuppressWarnings("javadoc")
public class TurtleTestCase {

    private IRI iri = IRI.create("urn:testliterals");
    private IRI s = IRI.create("urn:test#s");

    protected OWLOntology loadOntologyFromString(String input, IRI i)
            throws OWLOntologyCreationException {
        StringDocumentSource documentSource = new StringDocumentSource(input, i);
        OWLOntology ontology = OWLManager.createOWLOntologyManager()
                .loadOntologyFromOntologyDocument(documentSource);
        return ontology;
    }

    @Test
    public void shouldParseFixedQuotesLiterals1()
            throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(
                "<urn:test#s> <urn:test#p> ''' ''\\' ''' .", iri);
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals(" ''' ", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals2()
            throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(
                "<urn:test#s> <urn:test#p> \"\"\" \"\"\\\" \"\"\" .", iri);
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals(" \"\"\" ", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals3()
            throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(
                "<urn:test#s> <urn:test#p> \"\"\" \"\"\\u0061 \"\"\" .", iri);
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals(" \"\"a ", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals4()
            throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(
                "<urn:test#s> <urn:test#p> \"\"\"\"\"\\\"\"\"\" .", iri);
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals("\"\"\"", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void shouldParseFixedQuotesLiterals5()
            throws OWLOntologyCreationException {
        OWLOntology o = loadOntologyFromString(
                "<urn:test#s> <urn:test#p> \"\"\"\"\"\\u0061\"\"\" .", iri);
        for (OWLAnnotationAssertionAxiom ax : o.getAnnotationAssertionAxioms(s)) {
            assertEquals("\"\"a", ((OWLLiteral) ax.getValue()).getLiteral());
        }
    }

    @Test
    public void testLoadingUTF8BOM() throws URISyntaxException,
            OWLOntologyCreationException {
        IRI uri = IRI.create(getClass().getResource("/ttl-with-bom.ttl")
                .toURI());
        Factory.getManager().loadOntologyFromOntologyDocument(uri);
    }

    @Test
    public void shouldParseOntologyThatworked()
            throws OWLOntologyCreationException {
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
    public void shouldParseOntologyThatBroke()
            throws OWLOntologyCreationException {
        // given
        String input = "@prefix f:    <urn:test/> . f:r f:p f: .";
        OWLDataFactory df = Factory.getFactory();
        OWLAxiom expected = df.getOWLAnnotationAssertionAxiom(
                df.getOWLAnnotationProperty(IRI("urn:test/p")),
                IRI("urn:test/r"), IRI("urn:test/"));
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
        OWLOntology ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(
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
    public void shouldParseScientificNotation()
            throws OWLOntologyCreationException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1e+07 .";
        OWLOntology ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature().contains(p));
        IRI s = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, s,
                Literal("1.0E7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    public void shouldParse_one() throws OWLOntologyCreationException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1 .";
        OWLOntology ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature().contains(p));
        IRI s = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology
                .containsAxiom(AnnotationAssertion(p, s, Literal(1))));
    }

    @Test
    public void shouldParseOne() throws OWLOntologyCreationException {
        String input = "<http://dbpedia.org/resource/South_Africa> <http://dbpedia.org/ontology/areaTotal> 1.0.";
        OWLOntology ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        OWLAnnotationProperty p = AnnotationProperty(IRI("http://dbpedia.org/ontology/areaTotal"));
        assertTrue(ontology.getAnnotationPropertiesInSignature().contains(p));
        IRI s = IRI("http://dbpedia.org/resource/South_Africa");
        assertTrue(ontology.containsAxiom(AnnotationAssertion(p, s,
                Literal("1.0", OWL2Datatype.XSD_DECIMAL))));
    }

    @Test
    public void shouldParseEmptySpaceInBnode()
            throws OWLOntologyCreationException {
        String input = "<http://taxonomy.wolterskluwer.de/practicearea/10112>\n"
                + "      a       <http://schema.wolterskluwer.de/TaxonomyTerm> , <http://www.w3.org/2004/02/skos/core#Concept> ;\n"
                + "      <http://www.w3.org/2004/02/skos/core#broader>\n"
                + "              [] ;\n"
                + "      <http://www.w3.org/2004/02/skos/core#broader>\n"
                + "              [] .";
        OWLOntology ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        OWLIndividual i = NamedIndividual(IRI("http://taxonomy.wolterskluwer.de/practicearea/10112"));
        OWLAnnotationProperty ap = AnnotationProperty(IRI("http://www.w3.org/2004/02/skos/core#broader"));
        OWLClass c = Class(IRI("http://www.w3.org/2004/02/skos/core#Concept"));
        OWLClass term = Class(IRI("http://schema.wolterskluwer.de/TaxonomyTerm"));
        assertTrue(ontology.containsAxiom(ClassAssertion(c, i)));
        assertTrue(ontology.containsAxiom(ClassAssertion(term, i)));
        assertTrue(ontology.containsEntityInSignature(ap));
    }

    @Test
    public void shouldRoundTripAxiomAnnotation() throws Exception {
        String input = "@prefix : <urn:fm2#> .\n"
                + "@prefix fm:    <urn:fm2#> .\n"
                + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix prov: <urn:prov#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@base <urn:fm2> .\n"
                + "\n"
                + "<http://www.ida.org/fm2.owl> rdf:type owl:Ontology.\n"
                + ":prov rdf:type owl:AnnotationProperty .\n"
                + "\n"
                + ":Manage rdf:type owl:Class ; rdfs:subClassOf :ManagementType .\n"
                + "[ rdf:type owl:Axiom ;\n"
                + "  owl:annotatedSource :Manage ;\n"
                + "  owl:annotatedTarget :ManagementType ;\n"
                + "  owl:annotatedProperty rdfs:subClassOf ;\n"
                + "  :prov [\n"
                + "    prov:gen :FMDomain ;\n"
                + "    prov:att :DM .\n"
                + "  ]\n"
                + "] .\n"
                + ":ManagementType rdf:type owl:Class .\n"
                + ":DM rdf:type owl:NamedIndividual , prov:Person .\n"
                + ":FMDomain rdf:type owl:NamedIndividual , prov:Activity ; prov:ass :DM .";
        OWLOntology ontology = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(input));
        StringDocumentTarget t = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology,
                new TurtleOntologyFormat(), t);
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(t.toString()));
        t = new StringDocumentTarget();
        o.getOWLOntologyManager()
                .saveOntology(o, new TurtleOntologyFormat(), t);
        Set<OWLSubClassOfAxiom> axioms = o.getAxioms(AxiomType.SUBCLASS_OF);
        assertEquals(1, axioms.size());
        OWLAnnotation next = axioms.iterator().next().getAnnotations()
                .iterator().next();
        assertTrue(next.getValue() instanceof OWLAnonymousIndividual);
        OWLAnonymousIndividual ind = (OWLAnonymousIndividual) next.getValue();
        Set<OWLAxiom> anns = new HashSet<OWLAxiom>();
        for (OWLAxiom ax : o.getAxioms()) {
            if (ax.getAnonymousIndividuals().contains(ind)) {
                anns.add(ax);
            }
        }
        assertEquals(3, anns.size());
    }

    @Test
    public void presentDeclaration() throws Exception {
        // given
        String input = "<urn:test#Settlement> rdf:type owl:Class.\n"
                + "<urn:test#fm2.owl> rdf:type owl:Ontology.\n"
                + "<urn:test#numberOfPads> rdf:type owl:ObjectProperty ;\n"
                + "rdfs:domain <urn:test#Settlement> .";
        // when
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        // then
        for (OWLLogicalAxiom ax : o.getLogicalAxioms()) {
            assertTrue(ax instanceof OWLObjectPropertyDomainAxiom);
        }
    }

    @Test
    public void missingDeclaration() throws Exception {
        // given
        String input = "<urn:test#fm2.owl> rdf:type owl:Ontology.\n"
                + "<urn:test#numberOfPads> rdf:type owl:ObjectProperty ;\n"
                + "rdfs:domain <urn:test#Settlement> .";
        // when
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        // then
        for (OWLLogicalAxiom ax : o.getLogicalAxioms()) {
            assertTrue(ax instanceof OWLAnnotationAssertionAxiom);
        }
    }
}
