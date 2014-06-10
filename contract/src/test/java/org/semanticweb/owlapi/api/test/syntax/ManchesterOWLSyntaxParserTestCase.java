package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;

@SuppressWarnings("javadoc")
public class ManchesterOWLSyntaxParserTestCase {

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        // given
        IRI iri = IRI("http://protege.org/ontologies" + "#p");
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager
                .createOntology(IRI("http://protege.org/ontologies"));
        manager.addAxiom(ontology, Declaration(DataProperty(iri)));
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology, target);
        String saved = target.toString();
        // when
        ontology = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(saved));
        // then
        assertTrue(ontology.containsDataPropertyInSignature(iri));
    }

    @Test
    public void shouldRenderCorrectly() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        // given
        OWLObjectProperty p = ObjectProperty(IRI("urn:test" + "#p"));
        OWLClass led = Class(IRI("urn:test" + "#led"));
        OWLClass crt = Class(IRI("urn:test" + "#crt"));
        OWLClass display = Class(IRI("urn:test" + "#display"));
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology ontology = manager
                .createOntology(IRI("http://protege.org/ontologies"));
        OWLDataFactory df = manager.getOWLDataFactory();
        OWLObjectSomeValuesFrom r = df.getOWLObjectSomeValuesFrom(p,
                df.getOWLObjectUnionOf(led, crt));
        OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(display, r);
        manager.addAxiom(ontology, axiom);
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology,
                new ManchesterOWLSyntaxOntologyFormat(), target);
        assertFalse(target.toString().contains(
                "((<urn:test#crt> or <urn:test#led>))"));
    }

    @Test
    public void shouldRoundtripDisjointUnion() throws Exception {
        OWLOntology o = Factory.getManager().createOntology();
        OWLClass a = Class(IRI("http://iri/#a"));
        OWLClass b = Class(IRI("http://iri/#b"));
        OWLClass c = Class(IRI("http://iri/#c"));
        OWLClass d = Class(IRI("http://iri/#d"));
        OWLDisjointUnionAxiom axiom = DisjointUnion(a, b, c, d);
        o.getOWLOntologyManager().addAxiom(o, axiom);
        o.getOWLOntologyManager().addAxiom(o, Declaration(a));
        o.getOWLOntologyManager().addAxiom(o, Declaration(b));
        o.getOWLOntologyManager().addAxiom(o, Declaration(c));
        o.getOWLOntologyManager().addAxiom(o, Declaration(d));
        StringDocumentTarget target = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o,
                new ManchesterOWLSyntaxOntologyFormat(), target);
        String string = target.toString();
        OWLOntology roundtripped = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(string));
        assertEquals(o.getAxioms(), roundtripped.getAxioms());
    }

    @Test(expected = ParserException.class)
    public void testManSyntaxEditorParser()
            throws OWLOntologyCreationException, ParserException {
        String onto = "<?xml version=\"1.0\"?>"
                + "<!DOCTYPE rdf:RDF ["
                + "<!ENTITY vin  \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\" >"
                + "<!ENTITY food \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#\" >"
                + "<!ENTITY owl  \"http://www.w3.org/2002/07/owl#\" >"
                + "<!ENTITY xsd  \"http://www.w3.org/2001/XMLSchema#\" >"
                + "]>"
                + "<rdf:RDF "
                + "xmlns     = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\" "
                + "xmlns:vin = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\" "
                + "xml:base  = \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/wine#\" "
                + "xmlns:food= \"http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#\" "
                + "xmlns:owl = \"http://www.w3.org/2002/07/owl#\" "
                + "xmlns:rdf = \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" "
                + "xmlns:rdfs= \"http://www.w3.org/2000/01/rdf-schema#\" "
                + "xmlns:xsd = \"http://www.w3.org/2001/XMLSchema#\">"
                + "<owl:Ontology rdf:about=\"\"><rdfs:comment>An example OWL ontology</rdfs:comment>"
                + "<rdfs:label>Wine Ontology</rdfs:label></owl:Ontology>"
                + "<owl:Class rdf:ID=\"VintageYear\" />"
                + "<owl:DatatypeProperty rdf:ID=\"yearValue\"><rdfs:domain rdf:resource=\"#VintageYear\" />    <rdfs:range  rdf:resource=\"&xsd;positiveInteger\" />"
                + "</owl:DatatypeProperty></rdf:RDF>";
        String expression = "yearValue some ";
        OWLOntologyManager mngr = Factory.getManager();
        OWLOntology wine = mngr
                .loadOntologyFromOntologyDocument(new StringDocumentSource(onto));
        Set<OWLOntology> ontologies = mngr.getOntologies();
        ShortFormProvider sfp = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
                wine.getOWLOntologyManager().getOntologyFormat(wine));
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                ontologies, sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                mngr.getOWLDataFactory(), expression);
        parser.setDefaultOntology(wine);
        parser.setOWLEntityChecker(new ShortFormEntityChecker(shortFormProvider));
        parser.parseClassExpression();
    }

    @Test
    public void shouldParseRuleInManSyntax()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        String inputManSyntax = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
                + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
                + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "Ontology: <http://www.owl-ontologies.com/Ontology1307394066.owl>\n"
                + "Datatype: xsd:decimal\n"
                + "Datatype: xsd:int\n"
                + "Datatype: xsd:dateTime\n"
                + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>\n"
                + "    Characteristics: \n"
                + "        Functional\n"
                + "    Range: \n"
                + "        xsd:int\n"
                + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasDate>\n"
                + "    Range: \n"
                + "        xsd:dateTime\n"
                + "Class: <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
                + "Individual: <http://www.owl-ontologies.com/Ontology1307394066.owl#p1>\n"
                + "    Types: \n"
                + "        <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
                + "Rule: \n"
                + "    xsd:decimal(?<urn:swrl#x>), <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>(?<urn:swrl#p>, ?<urn:swrl#x>) -> <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>(?<urn:swrl#p>)";
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(inputManSyntax));
        StringDocumentTarget t = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o,
                new ManchesterOWLSyntaxOntologyFormat(), t);
        OWLOntology o1 = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(t.toString()));
        o1.getOWLOntologyManager().saveOntology(o1,
                new ManchesterOWLSyntaxOntologyFormat(), t);
        assertEquals(o.getLogicalAxioms(), o1.getLogicalAxioms());
    }

    @Test
    public void shouldParseRuleInManSimpleSyntax()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        String inputManSyntax = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
                + "Prefix: rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
                + "Prefix: xml: <http://www.w3.org/XML/1998/namespace>\n"
                + "Prefix: xsd: <http://www.w3.org/2001/XMLSchema#>\n"
                + "Prefix: rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
                + "Ontology: <http://www.owl-ontologies.com/Ontology1307394066.owl>\n"
                + "Datatype: xsd:decimal\n"
                + "Datatype: xsd:int\n"
                + "Datatype: xsd:dateTime\n"
                + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>\n"
                + "    Characteristics: \n"
                + "        Functional\n"
                + "    Range: \n"
                + "        xsd:int\n"
                + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasDate>\n"
                + "    Range: \n"
                + "        xsd:dateTime\n"
                + "Class: <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
                + "Individual: <http://www.owl-ontologies.com/Ontology1307394066.owl#p1>\n"
                + "    Types: \n"
                + "        <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
                + "Rule: \n"
                + "    xsd:decimal(?x), <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>(?p, ?x) -> <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>(?p)";
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(inputManSyntax));
        StringDocumentTarget t = new StringDocumentTarget();
        o.getOWLOntologyManager().saveOntology(o,
                new ManchesterOWLSyntaxOntologyFormat(), t);
        OWLOntology o1 = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(t.toString()));
        o1.getOWLOntologyManager().saveOntology(o1,
                new ManchesterOWLSyntaxOntologyFormat(), t);
        assertEquals(o.getLogicalAxioms(), o1.getLogicalAxioms());
    }

    @Test
    public void shouldAnnotateAndRoundTrip()
            throws OWLOntologyCreationException {
        String input = "Prefix: : <http://example.com/owl/families/>\n"
                + "Ontology: <http://example.com/owl/families>\n"
                + "Class: Person\n"
                + "  Annotations:  rdfs:comment \"Represents the set of all people.\"\n"
                + "Class: Man\n"
                + "Annotations: rdfs:comment \"States that every man is a person.\"\n"
                + "  SubClassOf:  Person";
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        Set<OWLAxiom> axioms = o.getAxioms();
        OWLClass person = Class(IRI("http://example.com/owl/families/Person"));
        OWLClass m = Class(IRI("http://example.com/owl/families/Man"));
        assertTrue(axioms.contains(Declaration(person)));
        assertTrue(axioms.contains(Declaration(m)));
        assertTrue(axioms.contains(SubClassOf(m, person)));
    }

    public static final String NS = "http://protege.org/ontologies/Test.owl";
    OWLDataProperty p;
    OWLDatatype date_time;
    OWLDataFactory factory;

    @Before
    public void setUp() {
        factory = Factory.getFactory();
        p = DataProperty(IRI(NS + "#p"));
        date_time = factory.getOWLDatatype(XSDVocabulary.DATE_TIME.getIRI());
    }

    @Test
    public void shouldParseCorrectly() throws OWLOntologyCreationException {
        // given
        OWLClass a = Class(IRI(NS + "#A"));
        String text1 = "'GWAS study' and  has_publication_date some dateTime[< \"2009-01-01T00:00:00+00:00\"^^dateTime]";
        OWLClassExpression expected = factory
                .getOWLObjectIntersectionOf(a, factory
                        .getOWLDataSomeValuesFrom(p, factory
                                .getOWLDatatypeRestriction(date_time,
                                        OWLFacet.MAX_EXCLUSIVE,
                                        factory.getOWLLiteral(
                                                "2009-01-01T00:00:00+00:00",
                                                date_time))));
        // ontology creation including labels - this is the input ontology
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology o = manager.createOntology();
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(a));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(p));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(date_time));
        manager.addAxiom(o, annotation(a, "'GWAS study'"));
        manager.addAxiom(o, annotation(p, "has_publication_date"));
        manager.addAxiom(o, annotation(date_time, "dateTime"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(factory.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(),
                manager);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                manager.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                factory, text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(
                shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    public OWLAxiom annotation(final OWLEntity e, final String s) {
        return factory.getOWLAnnotationAssertionAxiom(
                e.getIRI(),
                factory.getOWLAnnotation(factory.getRDFSLabel(),
                        factory.getOWLLiteral(s)));
    }

    @Test
    public void shouldDoPrecedenceWithParentheses()
            throws OWLOntologyCreationException {
        // given
        OWLClass a = Class(IRI("urn:test#a"));
        OWLClass b = Class(IRI("urn:test#b"));
        OWLClass c = Class(IRI("urn:test#c"));
        OWLClass d = Class(IRI("urn:test#all"));
        String text1 = "(a and b) or c";
        OWLClassExpression expected = factory.getOWLObjectUnionOf(
                factory.getOWLObjectIntersectionOf(a, b), c);
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology o = manager.createOntology();
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(a));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(b));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(c));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(d));
        manager.addAxiom(o, factory.getOWLSubClassOfAxiom(expected, d));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(factory.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(),
                manager);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                manager.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                factory, text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(
                shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals("Expected " + expected + " actual " + dsvf, expected, dsvf);
    }

    @Test
    public void shouldParseCorrectlydecimal()
            throws OWLOntologyCreationException {
        // given
        String text1 = "p some decimal[<=2.0, >= 1.0]";
        OWLDatatype decimal = factory.getOWLDatatype(OWL2Datatype.XSD_DECIMAL
                .getIRI());
        OWLFacetRestriction max = factory.getOWLFacetRestriction(
                OWLFacet.MAX_INCLUSIVE, factory.getOWLLiteral("2.0", decimal));
        OWLFacetRestriction min = factory.getOWLFacetRestriction(
                OWLFacet.MIN_INCLUSIVE, factory.getOWLLiteral("1.0", decimal));
        OWLClassExpression expected = factory.getOWLDataSomeValuesFrom(p,
                factory.getOWLDatatypeRestriction(decimal, max, min));
        // ontology creation including labels - this is the input ontology
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology o = manager.createOntology();
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(p));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(decimal));
        manager.addAxiom(o, annotation(p, "p"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(factory.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(),
                manager);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                manager.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                factory, text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(
                shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    @Test
    public void shouldParseCorrectlydecimalNotSpecified()
            throws OWLOntologyCreationException {
        // given
        OWLAxiom expected = factory.getOWLDataPropertyRangeAxiom(factory
                .getOWLDataProperty(IRI.create("urn:a")), factory
                .getOWLDataOneOf(factory.getOWLLiteral("1.2",
                        OWL2Datatype.XSD_DECIMAL)));
        String input = "Ontology:\n" + "DataProperty: <urn:a>\n"
                + "Range: {1.2}";
        OWLOntology o = Factory.getManager().loadOntologyFromOntologyDocument(
                new StringDocumentSource(input));
        Set<OWLLogicalAxiom> axioms = o.getLogicalAxioms();
        for (OWLLogicalAxiom ax : axioms) {
            assertEquals(expected, ax);
        }
    }

    @Test
    public void shouldDoPrecedenceWithoutParentheses()
            throws OWLOntologyCreationException {
        // given
        OWLClass a = Class(IRI("urn:test#a"));
        OWLClass b = Class(IRI("urn:test#b"));
        OWLClass c = Class(IRI("urn:test#c"));
        OWLClass d = Class(IRI("urn:test#all"));
        String text1 = "a and b or c";
        OWLClassExpression expected = factory.getOWLObjectUnionOf(
                factory.getOWLObjectIntersectionOf(a, b), c);
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology o = manager.createOntology();
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(a));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(b));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(c));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(d));
        manager.addAxiom(o, factory.getOWLSubClassOfAxiom(expected, d));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(factory.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(),
                manager);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                manager.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                factory, text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(
                shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals("Expected " + expected + " actual " + dsvf, expected, dsvf);
    }

    @Test
    public void shouldNotFailOnAnnotations() throws Exception {
        String in = "Ontology(<http://x.org/>\n"
                + "Declaration(Class(<http://x.org/c>))\n"
                + "AnnotationAssertion(<http://x.org/p> <http://x.org/c> \"v1\")\n"
                + "AnnotationAssertion(<http://x.org/p> <http://x.org/c> \"orifice\")\n"
                + "AnnotationAssertion(Annotation(<http://x.org/p2> \"foo\") <http://x.org/p> <http://x.org/c> \"v1\")\n"
                + ")";
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology o = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(in));
        StringDocumentTarget target = new StringDocumentTarget();
        manager.saveOntology(o, new ManchesterOWLSyntaxOntologyFormat(), target);
        OWLOntology result = Factory.getManager()
                .loadOntologyFromOntologyDocument(
                        new StringDocumentSource(target.toString()));
        for (OWLAxiom ax : o.getAxioms()) {
            assertTrue(ax.toString(), result.containsAxiom(ax));
        }
    }

    @Test
    public void shouldNotFailSubclass() throws Exception {
        // given
        OWLClass a = Class(IRI("urn:test#A"));
        OWLClass b = Class(IRI("urn:test#B"));
        String in = "A SubClassOf B";
        OWLOntologyManager manager = Factory.getManager();
        OWLOntology o = manager.createOntology();
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(a));
        manager.addAxiom(o, factory.getOWLDeclarationAxiom(b));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(factory.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(),
                manager);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                manager.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                factory, in);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(
                shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLAxiom axiom = parser.parseAxiom();
        // then
        assertEquals(factory.getOWLSubClassOfAxiom(a, b), axiom);
    }
}
