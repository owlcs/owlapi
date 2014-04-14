/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.model.Imports.EXCLUDED;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.formats.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.mansyntax.renderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;
import org.semanticweb.owlapi.mansyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings("javadoc")
public class ManchesterOWLSyntaxParserTestCase extends TestBase {

    @Test
    public void shouldRoundTrip() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        // given
        IRI iri = IRI("http://protege.org/ontologies" + "#p");
        OWLOntology ontology = m
                .createOntology(IRI("http://protege.org/ontologies"));
        m.addAxiom(ontology, Declaration(DataProperty(iri)));
        // when
        ontology = roundTrip(ontology);
        // then
        assertTrue(ontology.containsDataPropertyInSignature(iri, EXCLUDED));
    }

    @Test
    public void shouldRenderCorrectly() throws OWLOntologyCreationException,
            OWLOntologyStorageException {
        // given
        OWLObjectProperty prop = ObjectProperty(IRI("urn:test#p"));
        OWLClass led = Class(IRI("urn:test#led"));
        OWLClass crt = Class(IRI("urn:test#crt"));
        OWLClass display = Class(IRI("urn:test#display"));
        OWLOntology ontology = m
                .createOntology(IRI("http://protege.org/ontologies"));
        OWLObjectSomeValuesFrom r = df.getOWLObjectSomeValuesFrom(prop,
                df.getOWLObjectUnionOf(led, crt));
        OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(display, r);
        m.addAxiom(ontology, axiom);
        StringDocumentTarget target = saveOntology(ontology,
                new ManchesterOWLSyntaxOntologyFormat());
        assertFalse(target.toString().contains(
                "((<urn:test#crt> or <urn:test#led>))"));
    }

    @Test
    public void shouldRoundtripDisjointUnion() throws Exception {
        OWLOntology o = m.createOntology();
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
        OWLOntology roundtripped = roundTrip(o,
                new ManchesterOWLSyntaxOntologyFormat());
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
        OWLOntology wine = loadOntologyFromString(onto);
        Set<OWLOntology> ontologies = m.getOntologies();
        ShortFormProvider sfp = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
                wine.getOWLOntologyManager().getOntologyFormat(wine));
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                ontologies, sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                m.getOWLDataFactory(), expression);
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
                + "Datatype: xsd:decimal\n Datatype: xsd:int\n Datatype: xsd:dateTime\n"
                + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>\n Characteristics: \n Functional\n Range: \n xsd:int\n"
                + "DataProperty: <http://www.owl-ontologies.com/Ontology1307394066.owl#hasDate>\n Range: \n xsd:dateTime\n"
                + "Class: <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
                + "Individual: <http://www.owl-ontologies.com/Ontology1307394066.owl#p1>\n Types: \n <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>\n"
                + "Rule: \n xsd:decimal(?<urn:swrl#x>), <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>(?<urn:swrl#p>, ?<urn:swrl#x>) -> <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>(?<urn:swrl#p>)";
        OWLOntology o = loadOntologyFromString(inputManSyntax);
        OWLOntology o1 = roundTrip(o, new ManchesterOWLSyntaxOntologyFormat());
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
        OWLOntology o = loadOntologyFromString(inputManSyntax);
        OWLOntology o1 = roundTrip(o, new ManchesterOWLSyntaxOntologyFormat());
        assertEquals(o.getLogicalAxioms(), o1.getLogicalAxioms());
    }

    @Test
    public void shouldAnnotateAndRoundTrip()
            throws OWLOntologyCreationException {
        String input = "Prefix: : <http://example.com/owl/families/>\n"
                + "Ontology: <http://example.com/owl/families>\n"
                + "Class: Person\n Annotations:  rdfs:comment \"Represents the set of all people.\"\n"
                + "Class: Man\n Annotations: rdfs:comment \"States that every man is a person.\"\n SubClassOf:  Person";
        OWLOntology o = loadOntologyFromString(input);
        Set<OWLAxiom> axioms = o.getAxioms();
        OWLClass person = Class(IRI("http://example.com/owl/families/Person"));
        OWLClass man = Class(IRI("http://example.com/owl/families/Man"));
        assertTrue(axioms.contains(Declaration(person)));
        assertTrue(axioms.contains(Declaration(man)));
        assertTrue(axioms.contains(SubClassOf(man, person)));
    }

    public static final String NS = "http://protege.org/ontologies/Test.owl";
    OWLDataProperty p;
    OWLDatatype date_time;

    @Before
    public void setUpPAndDateTime() {
        p = DataProperty(IRI(NS + "#p"));
        date_time = df.getOWLDatatype(XSDVocabulary.DATE_TIME.getIRI());
    }

    @Test
    public void shouldParseCorrectly() throws OWLOntologyCreationException {
        // given
        OWLClass a = Class(IRI(NS + "#A"));
        String text1 = "'GWAS study' and  has_publication_date some dateTime[< \"2009-01-01T00:00:00+00:00\"^^dateTime]";
        OWLClassExpression expected = df.getOWLObjectIntersectionOf(a, df
                .getOWLDataSomeValuesFrom(p, df.getOWLDatatypeRestriction(
                        date_time, OWLFacet.MAX_EXCLUSIVE, df.getOWLLiteral(
                                "2009-01-01T00:00:00+00:00", date_time))));
        // ontology creation including labels - this is the input ontology
        OWLOntology o = m.createOntology();
        m.addAxiom(o, df.getOWLDeclarationAxiom(a));
        m.addAxiom(o, df.getOWLDeclarationAxiom(p));
        m.addAxiom(o, df.getOWLDeclarationAxiom(date_time));
        m.addAxiom(o, annotation(a, "'GWAS study'"));
        m.addAxiom(o, annotation(p, "has_publication_date"));
        m.addAxiom(o, annotation(date_time, "dateTime"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                m.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                df, text1);
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
        return df.getOWLAnnotationAssertionAxiom(e.getIRI(),
                df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(s)));
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
        OWLClassExpression expected = df.getOWLObjectUnionOf(
                df.getOWLObjectIntersectionOf(a, b), c);
        OWLOntology o = m.createOntology();
        m.addAxiom(o, df.getOWLDeclarationAxiom(a));
        m.addAxiom(o, df.getOWLDeclarationAxiom(b));
        m.addAxiom(o, df.getOWLDeclarationAxiom(c));
        m.addAxiom(o, df.getOWLDeclarationAxiom(d));
        m.addAxiom(o, df.getOWLSubClassOfAxiom(expected, d));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                m.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                df, text1);
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
        OWLDatatype decimal = df.getOWLDatatype(OWL2Datatype.XSD_DECIMAL
                .getIRI());
        OWLFacetRestriction max = df.getOWLFacetRestriction(
                OWLFacet.MAX_INCLUSIVE, df.getOWLLiteral("2.0", decimal));
        OWLFacetRestriction min = df.getOWLFacetRestriction(
                OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral("1.0", decimal));
        OWLClassExpression expected = df.getOWLDataSomeValuesFrom(p,
                df.getOWLDatatypeRestriction(decimal, max, min));
        // ontology creation including labels - this is the input ontology
        OWLOntology o = m.createOntology();
        m.addAxiom(o, df.getOWLDeclarationAxiom(p));
        m.addAxiom(o, df.getOWLDeclarationAxiom(decimal));
        m.addAxiom(o, annotation(p, "p"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                m.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                df, text1);
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
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(df
                .getOWLDataProperty(IRI.create("urn:a")), df.getOWLDataOneOf(df
                .getOWLLiteral("1.2", OWL2Datatype.XSD_DECIMAL)));
        String input = "Ontology:\n DataProperty: <urn:a>\n Range: {1.2}";
        OWLOntology o = loadOntologyFromString(input);
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
        OWLClassExpression expected = df.getOWLObjectUnionOf(
                df.getOWLObjectIntersectionOf(a, b), c);
        OWLOntology o = m.createOntology();
        m.addAxiom(o, df.getOWLDeclarationAxiom(a));
        m.addAxiom(o, df.getOWLDeclarationAxiom(b));
        m.addAxiom(o, df.getOWLDeclarationAxiom(c));
        m.addAxiom(o, df.getOWLDeclarationAxiom(d));
        m.addAxiom(o, df.getOWLSubClassOfAxiom(expected, d));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = new AnnotationValueShortFormProvider(
                Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>> emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider = new BidirectionalShortFormProviderAdapter(
                m.getOntologies(), sfp);
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                df, text1);
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
                + "AnnotationAssertion(Annotation(<http://x.org/p2> \"foo\") <http://x.org/p> <http://x.org/c> \"v1\"))";
        OWLOntology o = loadOntologyFromString(in);
        OWLOntology result = roundTrip(o,
                new ManchesterOWLSyntaxOntologyFormat());
        for (OWLAxiom ax : o.getAxioms()) {
            assertTrue(result.containsAxiom(ax));
        }
    }
}
