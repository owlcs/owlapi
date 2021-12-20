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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

class TurtleTestCase extends TestBase {

    private final TurtleDocumentFormat tf = new TurtleDocumentFormat();

    private static String normalize(String string) {
        return string.replaceAll("\r", "").replaceAll("\\n#.*", "").replaceAll("\\n+", "\n").trim();
    }

    @Test
    void shouldSaveIRIsWithCommasInTurtle() {
        OWLOntology o = o(iri(TestFiles.NS, ""), Declaration(INDIVIDUALS.individual));
        o.getPrefixManager().withDefaultPrefix(TestFiles.PREFIX);
        String string = saveOntology(o, new TurtleDocumentFormat()).toString();
        OWLOntology o1 = loadFrom(string, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveIRIsWithCommasInRioTurtle() {
        OWLOntology o = o(iri(TestFiles.NS, ""), Declaration(INDIVIDUALS.individual));
        o.getPrefixManager().withDefaultPrefix(TestFiles.PREFIX);
        String string = saveOntology(o, new RioTurtleDocumentFormat()).toString();
        OWLOntology o1 = loadFrom(string, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveBaseIRINotOntologyInTurtle() {
        OWLOntology o = o(iri(TestFiles.NS, ""), Declaration(INDIVIDUALS.I));
        o.getPrefixManager().withDefaultPrefix(OWLAPI_TEST);
        String string = normalize(saveOntology(o, new TurtleDocumentFormat()).toString());
        assertEquals(TestFiles.SaveBaseIRINotOntologyInTurtle, string);
        OWLOntology o1 = loadFrom(string, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldParseQuotedTripleQuotes() {
        // given
        OWLAxiom expected =
            AnnotationAssertion(RDFSLabel(), iri("http://xmlns.com/foaf/0.1/", "fundedBy"),
                Literal(TestFiles.ParseQuotedTripleQuotesLitvalue, "en"));
        // when
        OWLOntology o = loadFrom(TestFiles.ParseQuotedTripleQuotesWorking);
        // then
        assertEquals(expected, o.axioms().iterator().next());
    }

    @Test
    void shouldSaveBaseIRINotOntologyInRioTurtle() {
        OWLOntology o = o(iri(TestFiles.NS, ""), Declaration(INDIVIDUALS.I));
        o.getPrefixManager().withDefaultPrefix(OWLAPI_TEST);
        String string = normalize(saveOntology(o, new RioTurtleDocumentFormat()).toString());
        assertEquals(TestFiles.SaveBaseIRINotOntologyInRioTurtle, string);
        OWLOntology o1 = loadFrom(string, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void irisWithQuotesInTurtle() {
        OWLOntology o = o(IRIS.iriLiterals,
            AnnotationAssertion(RDFSLabel(), iri("urn:test#s'", "t"), LITERALS.LIT_TRUE));
        OWLOntology o1 = roundTrip(o, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void irisWithQuotesInRioTurtle() {
        OWLOntology o = o(IRIS.iriLiterals,
            AnnotationAssertion(RDFSLabel(), iri("urn:test#s'", "t"), LITERALS.LIT_TRUE));
        OWLOntology o1 = roundTrip(o, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void testLoadingUTF8BOM() throws URISyntaxException {
        IRI uri = iri(getClass().getResource("/ttl-with-bom.ttl").toURI());
        loadFrom(uri);
    }

    static Stream<Arguments> tests() {
        return Stream.of(of(TestFiles.quotes1, " ''' "),
            of(TestFiles.quotes6, "3'''-acetate; [cut]"), of(TestFiles.quotes2, " \"\"\" "),
            of(TestFiles.quotes3, " \"\"a "), of(TestFiles.quotes4, "\"\"\""),
            of(TestFiles.quotes5, "\"\"a"));
    }

    @ParameterizedTest
    @MethodSource("tests")
    void shouldParseFixedQuotesLiterals(String in, String litForm) {
        OWLOntology o = loadFrom(in, tf);
        o.annotationAssertionAxioms(OBJPROPS.S.getIRI())
            .forEach(ax -> assertEquals(litForm, ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    void shouldParseOntologyThatworked() {
        // given
        OWLAxiom expected = AnnotationAssertion(RDFSIsDefinedBy(),
            iri("http://xmlns.com/foaf/0.1/", "fundedBy"), iri("http://xmlns.com/foaf/0.1/", ""));
        // when
        OWLOntology o = loadFrom(TestFiles.workingOnto, new TurtleDocumentFormat());
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    void shouldParseOntologyThatBroke() {
        // given
        OWLAxiom expected = AnnotationAssertion(ANNPROPS.AP, OBJPROPS.R.getIRI(),
            iri(OBJPROPS.R.getIRI().getNamespace(), ""));
        // when
        OWLOntology o = loadFrom(TestFiles.brokenOnto, new TurtleDocumentFormat());
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    void shouldResolveAgainstBase() {
        // given
        // when
        OWLOntology o = loadFrom(TestFiles.resolveAgainstBase, new TurtleDocumentFormat());
        // then
        String axioms = o.axioms().iterator().next().toString();
        assertTrue(axioms.contains("http://test.org/a1"));
        assertTrue(axioms.contains("http://test.org/b1"));
        assertTrue(axioms.contains("http://test.org/c1"));
    }

    // test for 3543488
    @Test
    void shouldRoundTripTurtleWithsharedBnodes() {
        masterConfigurator.withRemapAllAnonymousIndividualsIds(false);
        try {
            OWLOntology ontology = loadFrom(TestFiles.turtleWithShared, new TurtleDocumentFormat());
            OWLOntology onto2 = roundTrip(ontology, new TurtleDocumentFormat());
            equal(ontology, onto2);
        } finally {
            masterConfigurator.withRemapAllAnonymousIndividualsIds(true);
        }
    }

    // test for 335
    @Test
    void shouldParseScientificNotation() {
        OWLOntology ontology =
            loadFrom(TestFiles.scientificNotationPlus, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature()
            .anyMatch(ap -> ap.equals(ANNPROPS.areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(ANNPROPS.areaTotal, IRIS.southAfrica,
            Literal("1.0E7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    void shouldParseScientificNotationWithMinus() {
        OWLOntology ontology =
            loadFrom(TestFiles.scientificNotationWithMinus, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ANNPROPS.areaTotal::equals));
        assertTrue(ontology.containsAxiom(
            AnnotationAssertion(ANNPROPS.areaTotal, IRIS.southAfrica, LITERALS.oneMillionth)));
    }

    @Test
    void shouldParseScientificNotationWithMinusFromBug() {
        loadFrom(TestFiles.scientificNotation, new TurtleDocumentFormat());
    }

    @Test
    void shouldParseTwo() {
        OWLOntology ontology = loadFrom(TestFiles.parseTwo, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature()
            .anyMatch(ap -> ap.equals(ANNPROPS.areaTotal)));
        assertTrue(ontology.containsAxiom(
            AnnotationAssertion(ANNPROPS.areaTotal, IRIS.southAfrica, LITERALS.LIT_ONE)));
    }

    @Test
    void shouldParseOne() {
        OWLOntology ontology = loadFrom(TestFiles.parseOne, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature()
            .anyMatch(ap -> ap.equals(ANNPROPS.areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(ANNPROPS.areaTotal, IRIS.southAfrica,
            Literal("1.0", OWL2Datatype.XSD_DECIMAL))));
    }

    @Test
    void shouldParseEmptySpaceInBnode() {
        OWLOntology ontology = loadFrom(TestFiles.emptySpaceInBnode, new TurtleDocumentFormat());
        assertTrue(
            ontology.containsAxiom(ClassAssertion(CLASSES.concept, INDIVIDUALS.PRACTICE_IND)));
        assertTrue(
            ontology.containsAxiom(ClassAssertion(CLASSES.taxTerm, INDIVIDUALS.PRACTICE_IND)));
        assertTrue(ontology.containsEntityInSignature(ANNPROPS.broader));
    }

    @Test
    void shouldRoundTripAxiomAnnotation() {
        masterConfigurator.withRemapAllAnonymousIndividualsIds(false);
        try {
            OWLOntology ontology = loadFrom(TestFiles.axiomAnnotations, new TurtleDocumentFormat());
            OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
            equal(ontology, o);
            Set<OWLSubClassOfAxiom> axioms = asUnorderedSet(o.axioms(AxiomType.SUBCLASS_OF));
            assertEquals(1, axioms.size());
            OWLAnnotation next = axioms.iterator().next().annotations().iterator().next();
            assertTrue(next.getValue() instanceof OWLAnonymousIndividual);
            OWLAnonymousIndividual ind = (OWLAnonymousIndividual) next.getValue();
            Set<OWLAxiom> anns =
                asUnorderedSet(o.axioms().filter(ax -> contains(ax.anonymousIndividuals(), ind)));
            assertEquals(3, anns.size());
        } finally {
            masterConfigurator.withRemapAllAnonymousIndividualsIds(true);
        }
    }

    @Test
    void shouldRoundTripAxiomAnnotationWithSlashOntologyIRI() {
        OWLOntology in = loadFrom(TestFiles.slashOntologyIRI, new TurtleDocumentFormat());
        OWLOntology ontology = create(iri("urn:test#test.owl/", ""));
        ontology.add(SubClassOf(CLASSES.TEST_T, CLASSES.TEST_Q));
        OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
        equal(o, in);
    }

    @Test
    void presentDeclaration() {
        // when
        OWLOntology o = loadFrom(TestFiles.presentDeclaration, new TurtleDocumentFormat());
        // then
        o.logicalAxioms().forEach(ax -> assertTrue(ax instanceof OWLObjectPropertyDomainAxiom));
    }

    @Test
    void missingDeclaration() {
        // when
        OWLOntology o = loadFrom(TestFiles.missingDeclaration, new TurtleDocumentFormat());
        // then
        o.logicalAxioms()
            .forEach(ax -> assertTrue(ax instanceof OWLObjectPropertyDomainAxiom, ax.toString()));
    }

    @Test
    void shouldReloadSamePrefixAbbreviations() {
        OWLOntology o = loadFrom(TestFiles.prefixAbbreviations, new RioTurtleDocumentFormat());
        assertTrue(saveOntology(o).toString().contains("ABA:10"));
    }

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() {
        OWLOntology o = loadFrom(TestFiles.axiomsForBlankNodes, new TurtleDocumentFormat());
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected =
                ObjectPropertyAssertion(OBJPROPS.anonR, ax.getIndividual(), ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        });
    }

    @Test
    void shouldAllowMultipleDotsInIRIs() {
        OWLOntology o = create(iri("http://www.semanticweb.org/", "ontology"));
        o.addAxiom(Declaration(Class(iri("http://www.semanticweb.org/ontology#A...", ""))));
        o.addAxiom(Declaration(Class(iri("http://www.semanticweb.org/ontology#A...B", ""))));
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        o.getPrefixManager().withDefaultPrefix("http://www.semanticweb.org/ontology#");
        roundTrip(o, format);
    }

    @Test
    void shouldSaveWithCorrectPrefixes() {
        OWLOntology ont = loadFrom(TestFiles.correctPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ont.getPrefixManager().withPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadFrom(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldSaveWithCorrectSlashPrefixes() {
        OWLOntology ont = loadFrom(TestFiles.slashPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ont.getPrefixManager().withPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadFrom(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldUseRightPrefixesWithPercentURLs() {
        PrefixManager basePrefix =
            new PrefixManagerImpl().withDefaultPrefix("http://www.example.com#");
        OWLOntology ontology = create(iri("http://www.example.com", ""));
        OWLObjectProperty owlObjectP = ObjectProperty("has%20space", basePrefix);
        OWLClass domain = Class("domain1", basePrefix);
        ontology.add(ObjectPropertyDomain(owlObjectP, domain));
        TurtleDocumentFormat turtle = new TurtleDocumentFormat();
        StringDocumentTarget out = saveOntology(ontology, turtle);
        String string = out.toString();
        assertTrue(
            string.contains("<http://www.example.com#has%20space> rdf:type owl:ObjectProperty"),
            string);
    }

    @Test
    void sameFileShouldParseToSameOntology() {
        File file = new File(RESOURCES, "noBaseEscapedSlashes.ttl");
        OWLOntology o1 = loadFrom(file, new TurtleDocumentFormat(), m1);
        OWLOntology o2 = loadFrom(file, new RioTurtleDocumentFormat(), m1);
        equal(o1, o2);
    }

    @Test
    void shouldParseEscapedCharacters() {
        OWLOntology ont = loadFrom(new File(RESOURCES, "noBaseEscapedSlashes.ttl"),
            new TurtleDocumentFormat(), m1);
        OWLOntology o1 = roundTrip(ont, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldParseWithBase() {
        OWLOntology ont = loadFrom(new File(RESOURCES, "noBaseEscapedSlashes.ttl"),
            new RioTurtleDocumentFormat(), m1);
        OWLOntology o1 = roundTrip(ont, new RioTurtleDocumentFormat());
        equal(ont, o1);
    }
}
