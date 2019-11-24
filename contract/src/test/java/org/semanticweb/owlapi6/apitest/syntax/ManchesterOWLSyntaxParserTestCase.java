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
package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.DisjointUnion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.C;
import static org.semanticweb.owlapi6.apitest.TestEntities.D;
import static org.semanticweb.owlapi6.apitest.TestEntities.DP;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apibinding.OWLManager;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.io.OWLParserException;
import org.semanticweb.owlapi6.manchestersyntax.parser.ManchesterOWLSyntaxParser;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDisjointUnionAxiom;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utility.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi6.utility.BidirectionalShortFormProvider;
import org.semanticweb.owlapi6.utility.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLFacet;
import org.semanticweb.owlapi6.vocab.XSDVocabulary;

public class ManchesterOWLSyntaxParserTestCase extends TestBase {

    private static final String URN_TEST = "urn:test#";
    private static final OWLClass MAN = Class(IRI("http://example.com/owl/families/", "Man"));
    private static final OWLClass PERSON = Class(IRI("http://example.com/owl/families/", "Person"));

    @Test
    public void shouldRoundtripAnnotationAssertionsWithAnnotations()
        throws OWLOntologyStorageException {
        OWLOntology o = loadOntologyFromString(TestFiles.annotatedAnnotationMansyntax,
            new ManchesterSyntaxDocumentFormat());
        OWLOntology o2 = roundTrip(o);
        equal(o, o2);
    }

    @Test
    public void shouldAcceptOPClassPunning() throws OWLOntologyStorageException {
        String input = "Prefix: : <http://x.org/>\n" + "Ontology: <http://x.org>\n"
            + "Individual: x\n" + "Class: creator_of\n" + "ObjectProperty: creator_of\n"
            + "Class: Test\n" + "  EquivalentClasses: creator_of value x, creator_of";
        OWLOntology o = loadOntologyFromString(input, new ManchesterSyntaxDocumentFormat());
        OWLOntology o2 = roundTrip(o);
        equal(o, o2);
    }

    @Test
    public void shouldRoundTrip() throws Exception {
        // given
        OWLOntology ontology = getOWLOntology();
        ontology.add(Declaration(DP));
        // when
        ontology = roundTrip(ontology);
        // then
        assertTrue(ontology.containsDataPropertyInSignature(DP.getIRI()));
    }

    @Test
    public void shouldRenderCorrectly() throws Exception {
        // given
        OWLOntology ontology = getOWLOntology();
        OWLObjectSomeValuesFrom r = df.getOWLObjectSomeValuesFrom(P, df.getOWLObjectUnionOf(B, C));
        OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(D, r);
        ontology.add(axiom);
        StringDocumentTarget target = saveOntology(ontology, new ManchesterSyntaxDocumentFormat());
        assertFalse(target.toString().contains(
            "((" + B.getIRI().toQuotedString() + " or " + C.getIRI().toQuotedString() + "))"));
    }

    @Test
    public void shouldNotAddDCToPrefixes() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.noDC, new ManchesterSyntaxDocumentFormat());
        assertFalse(o.getPrefixManager().containsPrefixMapping("dc:"));
    }

    @Test
    public void shouldAddDCToPrefixesWithoutDeclaration() {
        // DC was added by default to the prefixes whether it was used or not.
        // The behaviour was removed, but this would stop ontologies from
        // loading if they relied on the prefix appearing by default
        // Fix is to add it lazily if necessary
        OWLOntology o =
            loadOntologyFromString(TestFiles.lazyDC, new ManchesterSyntaxDocumentFormat());
        assertTrue(o.getPrefixManager().containsPrefixMapping("dc:"));
    }

    @Test
    public void shouldRoundtripDisjointUnion() throws Exception {
        OWLOntology o = getOWLOntology();
        OWLDisjointUnionAxiom axiom = DisjointUnion(A, B, C, D);
        o.add(axiom);
        o.add(Declaration(A));
        o.add(Declaration(B));
        o.add(Declaration(C));
        o.add(Declaration(D));
        OWLOntology roundtripped = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        assertEquals(asUnorderedSet(o.axioms()), asUnorderedSet(roundtripped.axioms()));
    }

    @Test(expected = OWLParserException.class)
    public void testManSyntaxEditorParser() {
        String expression = "yearValue some ";
        OWLOntology wine =
            loadOntologyFromString(TestFiles.manSyntaxParserTest, new RDFXMLDocumentFormat());
        List<OWLOntology> ontologies = asList(m.ontologies());
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(ontologies, wine.getPrefixManager());
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(expression);
        parser.setDefaultOntology(wine);
        parser.setOWLEntityChecker(new ShortFormEntityChecker(shortFormProvider));
        parser.parseClassExpression();
    }

    @Test
    public void shouldParseRuleInManSyntax() throws Exception {
        OWLOntology o =
            loadOntologyFromString(TestFiles.manSyntaxRule, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        assertEquals(asUnorderedSet(o.logicalAxioms()), asUnorderedSet(o1.logicalAxioms()));
    }

    @Test
    public void shouldParseRuleInManSimpleSyntax() throws Exception {
        OWLOntology o =
            loadOntologyFromString(TestFiles.manSyntaxInput, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        assertEquals(asUnorderedSet(o.logicalAxioms()), asUnorderedSet(o1.logicalAxioms()));
    }

    @Test
    public void shouldAnnotateAndRoundTrip() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.roundtripTest, new ManchesterSyntaxDocumentFormat());
        assertTrue(o.containsAxiom(Declaration(PERSON)));
        assertTrue(o.containsAxiom(Declaration(MAN)));
        assertTrue(o.containsAxiom(SubClassOf(MAN, PERSON)));
    }

    protected OWLDatatype dateTime;

    @Before
    public void setUpPAndDateTime() {
        dateTime = df.getOWLDatatype(XSDVocabulary.DATE_TIME);
    }

    @Test
    public void shouldParseCorrectly() {
        // given
        String text1 =
            "'GWAS study' and  has_publication_date some dateTime[< \"2009-01-01T00:00:00+00:00\"^^dateTime]";
        OWLClassExpression expected = df.getOWLObjectIntersectionOf(A,
            df.getOWLDataSomeValuesFrom(DP, df.getOWLDatatypeRestriction(dateTime,
                OWLFacet.MAX_EXCLUSIVE, df.getOWLLiteral("2009-01-01T00:00:00+00:00", dateTime))));
        // ontology creation including labels - this is the input ontology
        OWLOntology o = getOWLOntology();
        o.add(df.getOWLDeclarationAxiom(A), df.getOWLDeclarationAxiom(DP),
            df.getOWLDeclarationAxiom(dateTime), annotation(A, "'GWAS study'"),
            annotation(DP, "has_publication_date"), annotation(dateTime, "dateTime"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(asList(m.ontologies()), sfp);
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

    public OWLAxiom annotation(OWLEntity e, String s) {
        return df.getOWLAnnotationAssertionAxiom(e.getIRI(), df.getRDFSLabel(s));
    }

    @Test
    public void shouldDoPrecedenceWithParentheses() {
        // given
        String text1 = "(a and b) or c";
        OWLClass a = Class(IRI(URN_TEST, "a"));
        OWLClass b = Class(IRI(URN_TEST, "b"));
        OWLClass c = Class(IRI(URN_TEST, "c"));
        OWLClassExpression expected =
            df.getOWLObjectUnionOf(df.getOWLObjectIntersectionOf(a, b), c);
        ManchesterOWLSyntaxParser parser = setupPArser(text1, expected);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals("Expected " + expected + " actual " + dsvf, expected, dsvf);
    }

    @Test
    public void shouldParseCorrectlydecimal() {
        // given
        String text1 = "p some decimal[<=2.0, >= 1.0]";
        OWLDatatype decimal = df.getOWLDatatype(OWL2Datatype.XSD_DECIMAL);
        OWLFacetRestriction max =
            df.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, df.getOWLLiteral("2.0", decimal));
        OWLFacetRestriction min =
            df.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, df.getOWLLiteral("1.0", decimal));
        OWLClassExpression expected =
            df.getOWLDataSomeValuesFrom(DP, df.getOWLDatatypeRestriction(decimal, max, min));
        // ontology creation including labels - this is the input ontology
        OWLOntology o = getOWLOntology();
        o.add(df.getOWLDeclarationAxiom(DP), df.getOWLDeclarationAxiom(decimal),
            annotation(DP, "p"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(asList(m.ontologies()), sfp);
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
    public void shouldParseCorrectlydecimalNotSpecified() {
        // given
        OWLAxiom expected = df.getOWLDataPropertyRangeAxiom(DP,
            df.getOWLDataOneOf(df.getOWLLiteral("1.2", OWL2Datatype.XSD_DECIMAL)));
        String input =
            "Ontology:\n DataProperty: " + DP.getIRI().toQuotedString() + "\n Range: {1.2}";
        OWLOntology o = loadOntologyFromString(input, new ManchesterSyntaxDocumentFormat());
        o.logicalAxioms().forEach(ax -> assertEquals(expected, ax));
    }

    @Test
    public void shouldDoPrecedenceWithoutParentheses() {
        // given
        String text1 = "a and b or c";
        OWLClass a = Class(IRI(URN_TEST, "a"));
        OWLClass b = Class(IRI(URN_TEST, "b"));
        OWLClass c = Class(IRI(URN_TEST, "c"));
        OWLClassExpression expected =
            df.getOWLObjectUnionOf(df.getOWLObjectIntersectionOf(a, b), c);
        ManchesterOWLSyntaxParser parser = setupPArser(text1, expected);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals("Expected " + expected + " actual " + dsvf, expected, dsvf);
    }

    protected ManchesterOWLSyntaxParser setupPArser(String text1, OWLClassExpression expected) {
        OWLOntology o = getOWLOntology();
        o.add(df.getOWLDeclarationAxiom(A), df.getOWLDeclarationAxiom(B),
            df.getOWLDeclarationAxiom(C), df.getOWLDeclarationAxiom(D),
            df.getOWLSubClassOfAxiom(expected, D));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(asList(m.ontologies()), sfp);
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(text1);
        ShortFormEntityChecker owlEntityChecker = new ShortFormEntityChecker(shortFormProvider);
        parser.setOWLEntityChecker(owlEntityChecker);
        parser.setDefaultOntology(o);
        return parser;
    }

    @Test
    public void shouldNotFailOnAnnotations() throws Exception {
        OWLOntology o = loadOntologyFromString(TestFiles.annotationTestCase,
            new FunctionalSyntaxDocumentFormat());
        OWLOntology result = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        o.axioms().forEach(ax -> assertTrue(result.containsAxiom(ax)));
    }

    @Test
    public void shouldNotFailSubclass() {
        // given
        String in = "A SubClassOf B";
        OWLOntology o = getOWLOntology();
        o.add(df.getOWLDeclarationAxiom(A), df.getOWLDeclarationAxiom(B));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(asList(m.ontologies()), sfp);
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
    public void shouldNotFailOWLReal() throws OWLOntologyCreationException {
        String in = "p max 1 owl:real";
        OWLOntology o = m.createOntology();
        o.addAxiom(df.getOWLDeclarationAxiom(DP));
        o.addAxiom(df.getOWLDeclarationAxiom(B));
        // select a short form provider that uses annotations
        ShortFormProvider sfp =
            new AnnotationValueShortFormProvider(Arrays.asList(df.getRDFSLabel()),
                Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(asList(m.ontologies()), sfp);
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
}
