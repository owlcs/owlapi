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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
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
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLEntity;
import org.semanticweb.owlapi6.model.OWLFacetRestriction;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.utilities.ShortFormProvider;
import org.semanticweb.owlapi6.utility.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi6.utility.BidirectionalShortFormProvider;
import org.semanticweb.owlapi6.utility.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;
import org.semanticweb.owlapi6.vocab.OWLFacet;

class ManchesterOWLSyntaxParserTestCase extends TestBase {

    @Test
    void shouldRoundtripAnnotationAssertionsWithAnnotations() {
        OWLOntology o =
            loadFrom(TestFiles.annotatedAnnotationMansyntax, new ManchesterSyntaxDocumentFormat());
        OWLOntology o2 = roundTrip(o);
        equal(o, o2);
    }

    @Test
    void shouldAcceptOPClassPunning() {
        OWLOntology o = loadFrom(TestFiles.opClassPunning, new ManchesterSyntaxDocumentFormat());
        OWLOntology o2 = roundTrip(o);
        equal(o, o2);
    }

    @Test
    void shouldRoundTrip() {
        // given
        OWLOntology ontology = create(iri("http://protege.org/ontologies", ""));
        ontology.add(Declaration(DATAPROPS.DP));
        // when
        ontology = roundTrip(ontology);
        // then
        assertTrue(ontology.containsDataPropertyInSignature(DATAPROPS.DP.getIRI()));
    }

    @Test
    void shouldRenderCorrectly() {
        // given
        OWLOntology o = create(iri("http://protege.org/ontologies", ""));
        OWLObjectSomeValuesFrom r =
            ObjectSomeValuesFrom(OBJPROPS.P, ObjectUnionOf(CLASSES.B, CLASSES.C));
        OWLSubClassOfAxiom axiom = SubClassOf(CLASSES.D, r);
        o.add(axiom);
        StringDocumentTarget target = saveOntology(o, new ManchesterSyntaxDocumentFormat());
        assertFalse(target.toString().contains("((" + CLASSES.B.getIRI().toQuotedString() + " or "
            + CLASSES.C.getIRI().toQuotedString() + "))"));
    }

    @Test
    void shouldNotAddDCToPrefixes() {
        OWLOntology o = loadFrom(TestFiles.noDC, new ManchesterSyntaxDocumentFormat());
        assertFalse(o.getPrefixManager().containsPrefixMapping("dc:"));
    }

    @Test
    void shouldAddDCToPrefixesWithoutDeclaration() {
        // DC was added by default to the prefixes whether it was used or not.
        // The behaviour was removed, but this would stop ontologies from
        // loading if they relied on the prefix appearing by default
        // Fix is to add it lazily if necessary
        OWLOntology o = loadFrom(TestFiles.lazyDC, new ManchesterSyntaxDocumentFormat());
        assertTrue(o.getPrefixManager().containsPrefixMapping("dc:"));
    }

    @Test
    void shouldRoundtripDisjointUnion() {
        OWLOntology o = o(DisjointUnion(CLASSES.A, CLASSES.B, CLASSES.C, CLASSES.D));
        OWLOntology roundtripped = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        assertEquals(asUnorderedSet(o.axioms()), asUnorderedSet(roundtripped.axioms()));
    }

    @Test
    void testManSyntaxEditorParser() {
        String expression = "yearValue some ";
        OWLOntology wine = loadFrom(TestFiles.manSyntaxParserTest, new RDFXMLDocumentFormat());
        List<OWLOntology> ontologies = asList(m.ontologies());
        BidirectionalShortFormProvider shortFormProvider =
            new BidirectionalShortFormProviderAdapter(ontologies, wine.getPrefixManager());
        ManchesterOWLSyntaxParser parser = OWLManager.createManchesterParser();
        parser.setStringToParse(expression);
        parser.setDefaultOntology(wine);
        parser.setOWLEntityChecker(new ShortFormEntityChecker(shortFormProvider));
        assertThrows(OWLParserException.class, () -> parser.parseClassExpression());
    }

    @Test
    void shouldParseRuleInManSyntax() {
        OWLOntology o =
            loadFrom(TestFiles.manSyntaxParserRuleTest, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldParseRuleInManSyntaxOldNamespace() {
        OWLOntology o = loadFrom(TestFiles.manSyntaxRule, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldParseRuleInManSimpleSyntax() {
        OWLOntology o = loadFrom(TestFiles.manSyntaxInput, new ManchesterSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        assertEquals(asUnorderedSet(o.logicalAxioms()), asUnorderedSet(o1.logicalAxioms()));
    }

    @Test
    void shouldAnnotateAndRoundTrip() {
        OWLOntology o = loadFrom(TestFiles.roundtripTest, new ManchesterSyntaxDocumentFormat());
        assertTrue(o.containsAxiom(Declaration(CLASSES.FAMILY_PERSON)));
        assertTrue(o.containsAxiom(Declaration(CLASSES.MAN)));
        assertTrue(o.containsAxiom(SubClassOf(CLASSES.MAN, CLASSES.FAMILY_PERSON)));
    }

    @Test
    void shouldParseCorrectly() {
        // given
        String text1 =
            "'GWAS study' and  has_publication_date some dateTime[< \"2009-01-01T00:00:00+00:00\"^^dateTime]";
        OWLClassExpression expected = ObjectIntersectionOf(CLASSES.A,
            DataSomeValuesFrom(DATAPROPS.DP,
                DatatypeRestriction(DATATYPES.dateTime, FacetRestriction(OWLFacet.MAX_EXCLUSIVE,
                    Literal("2009-01-01T00:00:00+00:00", DATATYPES.dateTime)))));
        // ontology creation including labels - this is the input ontology
        OWLOntology o = o(Declaration(CLASSES.A), Declaration(DATAPROPS.DP),
            Declaration(DATATYPES.dateTime), annotation(CLASSES.A, "'GWAS study'"),
            annotation(DATAPROPS.DP, "has_publication_date"),
            annotation(DATATYPES.dateTime, "dateTime"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = shortFormProvider();
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

    OWLAxiom annotation(OWLEntity entity, String litform) {
        return AnnotationAssertion(RDFSLabel(), entity.getIRI(), Literal(litform));
    }

    @Test
    void shouldDoPrecedenceWithParentheses() {
        // given
        String text1 = "(A and B) or C";
        OWLClassExpression expected =
            ObjectUnionOf(ObjectIntersectionOf(CLASSES.A, CLASSES.B), CLASSES.C);
        ManchesterOWLSyntaxParser parser = setupPArser(text1, expected);
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
        OWLDatatype decimal = Datatype(OWL2Datatype.XSD_DECIMAL.getIRI());
        OWLFacetRestriction max = FacetRestriction(OWLFacet.MAX_INCLUSIVE, Literal("2.0", decimal));
        OWLFacetRestriction min = FacetRestriction(OWLFacet.MIN_INCLUSIVE, Literal("1.0", decimal));
        OWLClassExpression expected =
            DataSomeValuesFrom(DATAPROPS.DP, DatatypeRestriction(decimal, max, min));
        // ontology creation including labels - this is the input ontology
        OWLOntology o =
            o(Declaration(DATAPROPS.DP), Declaration(decimal), annotation(DATAPROPS.DP, "p"));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = shortFormProvider();
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
    void shouldParseCorrectlydecimalNotSpecified() {
        // given
        OWLAxiom expected =
            DataPropertyRange(DATAPROPS.DP, DataOneOf(Literal("1.2", OWL2Datatype.XSD_DECIMAL)));
        String input = "Ontology:\n DataProperty: " + DATAPROPS.DP.getIRI().toQuotedString()
            + "\n Range: {1.2}";
        OWLOntology o = loadFrom(input, new ManchesterSyntaxDocumentFormat());
        o.logicalAxioms().forEach(ax -> assertEquals(expected, ax));
    }

    @Test
    void shouldDoPrecedenceWithoutParentheses() {
        // given
        String text1 = "A and B or C";
        OWLClassExpression expected =
            ObjectUnionOf(ObjectIntersectionOf(CLASSES.A, CLASSES.B), CLASSES.C);
        ManchesterOWLSyntaxParser parser = setupPArser(text1, expected);
        // when
        // finally parse
        OWLClassExpression dsvf = parser.parseClassExpression();
        // then
        assertEquals(expected, dsvf);
    }

    protected ManchesterOWLSyntaxParser setupPArser(String text1, OWLClassExpression expected) {
        OWLOntology o = o(SubClassOf(expected, CLASSES.D));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = shortFormProvider();
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
    void shouldNotFailOnAnnotations() {
        OWLOntology o =
            loadFrom(TestFiles.annotationTestCase, new FunctionalSyntaxDocumentFormat());
        OWLOntology result = roundTrip(o, new ManchesterSyntaxDocumentFormat());
        o.axioms().forEach(ax -> assertTrue(result.containsAxiom(ax)));
    }

    @Test
    void shouldNotFailSubclass() {
        // given
        String in = "A SubClassOf B";
        OWLOntology o = o(Declaration(CLASSES.A), Declaration(CLASSES.B));
        ShortFormProvider sfp = shortFormProvider();
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
        assertEquals(SubClassOf(CLASSES.A, CLASSES.B), axiom);
    }

    @Test
    void shouldNotFailOWLReal() {
        String in = "p max 1 owl:real";
        OWLOntology o = o(Declaration(DATAPROPS.DP), Declaration(CLASSES.B));
        // select a short form provider that uses annotations
        ShortFormProvider sfp = shortFormProvider();
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
            DataMaxCardinality(1, DATAPROPS.DP, Datatype(OWL2Datatype.OWL_REAL.getIRI()));
        OWLClassExpression cl = parser.parseClassExpression();
        // then
        assertEquals(cl, expected);
    }

    protected ShortFormProvider shortFormProvider() {
        return new AnnotationValueShortFormProvider(l(RDFSLabel()),
            Collections.<OWLAnnotationProperty, List<String>>emptyMap(), m);
    }

    @Test
    void shouldWorkWithAssertions() {
        OWLOntology o = o(Declaration(CLASSES.C), ClassAssertion(CLASSES.C, AnonymousIndividual()),
            ClassAssertion(CLASSES.C, INDIVIDUALS.indA),
            ClassAssertion(CLASSES.C, AnonymousIndividual()));
        roundTrip(o, new ManchesterSyntaxDocumentFormat());
    }
}
