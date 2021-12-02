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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointUnion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.manchestersyntax.renderer.ManchesterOWLSyntaxPrefixNameShortFormProvider;
import org.semanticweb.owlapi.manchestersyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

class ManchesterOWLSyntaxParserTestCase extends TestBase {

    static final String URN_TEST = "urn:test#";
    static final OWLClass MAN = Class(IRI("http://example.com/owl/families/", "Man"));
    static final OWLClass PERSON = Class(IRI("http://example.com/owl/families/", "Person"));
    static final OWLDatatype dateTime = df.getOWLDatatype(XSDVocabulary.DATE_TIME.getIRI());
    static final String opClassPunning =
        "Prefix: : <http://x.org/>\n" + "Ontology: <http://x.org>\n" + "Individual: x\n"
            + "Class: creator_of\n" + "ObjectProperty: creator_of\n" + "Class: Test\n"
            + "  EquivalentClasses: creator_of value x, creator_of";
    static final String manSyntaxParserRuleTest = "Prefix: owl: <http://www.w3.org/2002/07/owl#>\n"
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
        + "Rule: \n xsd:decimal(?<urn:swrl:var#x>), <http://www.owl-ontologies.com/Ontology1307394066.owl#hasAge>(?<urn:swrl:var#p>, ?<urn:swrl:var#x>) -> <http://www.owl-ontologies.com/Ontology1307394066.owl#Person>(?<urn:swrl:var#p>)";

    @Test
    void shouldRoundtripAnnotationAssertionsWithAnnotations() {
        OWLOntology o = loadOntologyFromString(TestFiles.annotatedAnnotationMansyntax,
            new ManchesterSyntaxDocumentFormat());
        OWLOntology o2 = roundTrip(o);
        equal(o, o2);
    }

    @Test
    void shouldAcceptOPClassPunning() {
        OWLOntology o =
            loadOntologyFromString(opClassPunning, new ManchesterSyntaxDocumentFormat());
        OWLOntology o2 = roundTrip(o);
        equal(o, o2);
    }

    @Test
    void shouldRoundTrip() {
        // given
        OWLOntology ontology = getOWLOntology(IRI("http://protege.org/ontologies"));
        m.addAxiom(ontology, Declaration(DP));
        // when
        ontology = roundTrip(ontology);
        // then
        assertTrue(ontology.containsDataPropertyInSignature(DP.getIRI(), EXCLUDED));
    }

    @Test
    void shouldRenderCorrectly() {
        // given
        OWLOntology o = getOWLOntology(IRI("http://protege.org/ontologies"));
        OWLObjectSomeValuesFrom r = df.getOWLObjectSomeValuesFrom(P, df.getOWLObjectUnionOf(B, C));
        OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(D, r);
        m.addAxiom(o, axiom);
        StringDocumentTarget target = saveOntology(o, new ManchesterSyntaxDocumentFormat());
        assertFalse(target.toString().contains(
            "((" + B.getIRI().toQuotedString() + " or " + C.getIRI().toQuotedString() + "))"));
    }

    @Test
    void shouldNotAddDCToPrefixes() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.noDC, new ManchesterSyntaxDocumentFormat());
        OWLDocumentFormat f = o.getOWLOntologyManager().getOntologyFormat(o);
        assertFalse(f.asPrefixOWLOntologyFormat().containsPrefixMapping("dc:"));
    }

    @Test
    void shouldAddDCToPrefixesWithoutDeclaration() {
        // DC was added by default to the prefixes whether it was used or not.
        // The behaviour was removed, but this would stop ontologies from
        // loading if they relied on the prefix appearing by default
        // Fix is to add it lazily if necessary
        OWLOntology o =
            loadOntologyFromString(TestFiles.lazyDC, new ManchesterSyntaxDocumentFormat());
        OWLDocumentFormat f = o.getOWLOntologyManager().getOntologyFormat(o);
        assertTrue(f.asPrefixOWLOntologyFormat().containsPrefixMapping("dc:"));
    }

    @Test
    void shouldRoundtripDisjointUnion() {
        OWLOntology o = o(DisjointUnion(A, B, C, D), Declaration(A), Declaration(B), Declaration(C),
            Declaration(D));
        OWLOntology roundtripped = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        assertEquals(o.getAxioms(), roundtripped.getAxioms());
    }

    @Test
    void testManSyntaxEditorParser() {
        String expression = "yearValue some ";
        OWLOntology wine =
            loadOntologyFromString(TestFiles.manSyntaxParserTest, new RDFXMLDocumentFormat());
        Set<OWLOntology> ontologies = m.getOntologies();
        ShortFormProvider sfp = new ManchesterOWLSyntaxPrefixNameShortFormProvider(
            wine.getOWLOntologyManager().getOntologyFormat(wine));
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(ontologies, sfp);
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(expression);
        parser.setDefaultOntology(wine);
        parser.setOWLEntityChecker(new ShortFormEntityChecker(shortFormProvider));
        assertThrows(ParserException.class, () -> parser.parseClassExpression());
    }

    @Test
    void shouldParseRuleInManSyntax() {
        OWLOntology o =
            loadOntologyFromString(manSyntaxParserRuleTest, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldParseRuleInManSimpleSyntax() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.manSyntaxInput, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        assertEquals(o.getLogicalAxioms(), o1.getLogicalAxioms());
    }

    @Test
    void shouldAnnotateAndRoundTrip() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.roundtripTest, new ManchesterSyntaxDocumentFormat());
        assertTrue(o.containsAxiom(Declaration(PERSON)));
        assertTrue(o.containsAxiom(Declaration(MAN)));
        assertTrue(o.containsAxiom(SubClassOf(MAN, PERSON)));
    }

    @Test
    void shouldParseCorrectly() {
        // given
        String text1 =
            "'GWAS study' and  has_publication_date some dateTime[< \"2009-01-01T00:00:00+00:00\"^^dateTime]";
        OWLClassExpression expected = df.getOWLObjectIntersectionOf(A,
            df.getOWLDataSomeValuesFrom(DP, df.getOWLDatatypeRestriction(dateTime,
                OWLFacet.MAX_EXCLUSIVE, df.getOWLLiteral("2009-01-01T00:00:00+00:00", dateTime))));
        // ontology creation including labels - this is the input ontology
        OWLOntology o = o(df.getOWLDeclarationAxiom(A), df.getOWLDeclarationAxiom(DP),
            df.getOWLDeclarationAxiom(dateTime), annotation(A, "'GWAS study'"),
            annotation(DP, "has_publication_date"), annotation(dateTime, "dateTime"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(m.getOntologies(), sfp);
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    @Nonnull
    OWLAxiom annotation(@Nonnull OWLEntity e, @Nonnull String s) {
        return df.getOWLAnnotationAssertionAxiom(e.getIRI(),
            df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(s)));
    }

    @Test
    void shouldDoPrecedenceWithParentheses() {
        // given
        String text1 = "(A and B) or C";
        OWLClassExpression expected =
            df.getOWLObjectUnionOf(df.getOWLObjectIntersectionOf(A, B), C);
        ManchesterOWLSyntaxParser parser = setupParser(text1, expected);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    @Test
    void shouldParseCorrectlydecimal() {
        // given
        String text1 = "p some decimal[<=2.0, >= 1.0]";
        OWLDatatype decimal = df.getOWLDatatype(OWL2Datatype.XSD_DECIMAL.getIRI());
        OWLFacetRestriction max =
            df.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, df.getOWLLiteral("2.0", decimal));
        OWLFacetRestriction min =
            df.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral("1.0", decimal));
        OWLClassExpression expected =
            df.getOWLDataSomeValuesFrom(DP, df.getOWLDatatypeRestriction(decimal, max, min));
        // ontology creation including labels - this is the input ontology
        OWLOntology o = o(df.getOWLDeclarationAxiom(DP), df.getOWLDeclarationAxiom(decimal),
            annotation(DP, "p"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(m.getOntologies(), sfp);
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    @Test
    void shouldParseCorrectlydecimalNotSpecified() {
        // given
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(DP,
            df.getOWLDataOneOf(df.getOWLLiteral("1.2", OWL2Datatype.XSD_DECIMAL)));
        String input =
            "Ontology:\n DataProperty: " + DP.getIRI().toQuotedString() + "\n Range: {1.2}";
        OWLOntology o = loadOntologyFromString(input);
        Set<OWLLogicalAxiom> axioms = o.getLogicalAxioms();
        for (OWLLogicalAxiom ax : axioms) {
            assertEquals(expected, ax);
        }
    }

    @Test
    void shouldDoPrecedenceWithoutParentheses() {
        // given
        String text1 = "A and B or C";
        OWLClassExpression expected =
            df.getOWLObjectUnionOf(df.getOWLObjectIntersectionOf(A, B), C);
        ManchesterOWLSyntaxParser parser = setupParser(text1, expected);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    protected ManchesterOWLSyntaxParser setupParser(String text1, OWLClassExpression expected) {
        OWLOntology o = o(df.getOWLDeclarationAxiom(A), df.getOWLDeclarationAxiom(B),
            df.getOWLDeclarationAxiom(C), df.getOWLDeclarationAxiom(D),
            df.getOWLSubClassOfAxiom(expected, D));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(m.getOntologies(), sfp);
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        return parser;
    }

    @Test
    void shouldNotFailOnAnnotations() {
        OWLOntology o = loadOntologyFromString(TestFiles.annotationTestCase,
            new FunctionalSyntaxDocumentFormat());
        OWLOntology result = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        for (OWLAxiom ax : o.getAxioms()) {
            assert ax != null;
            assertTrue(result.containsAxiom(ax));
        }
    }

    @Test
    void shouldNotFailSubclass() {
        // given
        String in = "A SubClassOf B";
        OWLOntology o = o(df.getOWLDeclarationAxiom(A), df.getOWLDeclarationAxiom(B));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(m.getOntologies(), sfp);
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(in);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLAxiom axiom = parser.parseAxiom();
        // then
        assertEquals(df.getOWLSubClassOfAxiom(A, B), axiom);
    }

    @Test
    void shouldNotFailOWLReal() {
        String in = "p max 1 owl:real";
        OWLOntology o = o(df.getOWLDeclarationAxiom(DP), df.getOWLDeclarationAxiom(B));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(m.getOntologies(), sfp);
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(in);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        // when
        // finally parse
        OWLClassExpression expected =
            df.getOWLDataMaxCardinality(1, DP, OWL2Datatype.OWL_REAL.getDatatype(df));
        OWLClassExpression cl = parser.parseClassExpression();
        // then
        assertEquals(cl, expected);
    }

    @Test
    public void shouldWorkWithAssertions() {
        OWLOntology o = o(df.getOWLDeclarationAxiom(C),
            df.getOWLClassAssertionAxiom(C, df.getOWLAnonymousIndividual()),
            df.getOWLClassAssertionAxiom(C, indA),
            df.getOWLClassAssertionAxiom(C, df.getOWLAnonymousIndividual()));
        roundTrip(o, new ManchesterSyntaxDocumentFormat());
    }

    @Test
    void shouldParseRuleInManSyntaxOldNamespace() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.manSyntaxRule, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        equal(o, o1);
    }
}
