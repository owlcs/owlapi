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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class TurtleTestCase extends TestBase {

    static final OWLObjectProperty anonR =
        ObjectProperty(iri("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
    static final OWLClass taxTerm = Class(iri("http://schema.wolterskluwer.de/", "TaxonomyTerm"));
    static final OWLAnnotationProperty broader =
        AnnotationProperty(iri("http://www.w3.org/2004/02/skos/core#", "broader"));
    static final OWLClass concept = Class(iri("http://www.w3.org/2004/02/skos/core#", "Concept"));
    static final IRI iri = iri("urn:test#", "literals");
    private final TurtleDocumentFormat tf = new TurtleDocumentFormat();
    static String NS = "http://example.com/ontology";
    static IRI individualIRI = iri("http://example.com/ontology/x,", "y");
    static OWLNamedIndividual individual = NamedIndividual(individualIRI);

    private static String normalize(String string) {
        return string.replaceAll("\r", "").replaceAll("\\n#.*", "").replaceAll("\\n+", "\n").trim();
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
        assertEquals(expected, o.getAxioms().iterator().next());
    }

    @Test
    void shouldSaveIRIsWithCommasInTurtle() {
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLAxiom axiom = Declaration(individual);
        o.add(axiom);
        TurtleDocumentFormat turtleFormat = new TurtleDocumentFormat();
        turtleFormat.setDefaultPrefix("http://example.com/ontology/");
        String string = saveOntology(o, turtleFormat).toString();
        OWLOntology o1 = loadFrom(string, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveIRIsWithCommasInRioTurtle() {
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLAxiom axiom = Declaration(individual);
        o.add(axiom);
        RioTurtleDocumentFormat turtleFormat = new RioTurtleDocumentFormat();
        turtleFormat.setDefaultPrefix("http://example.com/ontology/");
        String string = saveOntology(o, turtleFormat).toString();
        OWLOntology o1 = loadFrom(string, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveBaseIRINotOntologyInTurtle() {
        String base = "urn:test:base#";
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLNamedIndividual ind = NamedIndividual(iri(base, "i"));
        OWLAxiom axiom = Declaration(ind);
        o.addAxiom(axiom);
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setDefaultPrefix(base);
        String string = normalize(saveOntology(o, format).toString());
        assertEquals(TestFiles.SaveBaseIRINotOntologyInTurtle, string);
        OWLOntology o1 = loadFrom(string, format);
        equal(o, o1);
    }

    @Test
    void shouldSaveBaseIRINotOntologyInRioTurtle() {
        String base = "urn:test:base#";
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLNamedIndividual ind = NamedIndividual(iri(base, "i"));
        OWLAxiom axiom = Declaration(ind);
        o.getOWLOntologyManager().addAxiom(o, axiom);
        RioTurtleDocumentFormat format = new RioTurtleDocumentFormat();
        format.setDefaultPrefix(base);
        String string = normalize(saveOntology(o, format).toString());
        assertEquals(TestFiles.SaveBaseIRINotOntologyInRioTurtle, string);
        OWLOntology o1 = loadFrom(string, format);
        equal(o, o1);
    }

    @Test
    void irisWithQuotesInTurtle() {
        OWLOntology o = create(iri);
        m.addAxiom(o, AnnotationAssertion(RDFSLabel(), iri("urn:test#s'", "t"), LIT_TRUE));
        OWLOntology o1 = roundTrip(o, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void irisWithQuotesInRioTurtle() {
        OWLOntology o = create(iri);
        m.addAxiom(o, AnnotationAssertion(RDFSLabel(), iri("urn:test#s'", "t"), LIT_TRUE));
        OWLOntology o1 = roundTrip(o, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void testLoadingUTF8BOM() throws URISyntaxException {
        IRI uri = IRI.create(getClass().getResource("/ttl-with-bom.ttl").toURI());
        loadFrom(uri);
    }

    static Stream<Arguments> tests() {
        return Stream.of(Arguments.of(TestFiles.quotes1, " ''' "),
            Arguments.of(TestFiles.quotes6, "3'''-acetate; [cut]"),
            Arguments.of(TestFiles.quotes2, " \"\"\" "), Arguments.of(TestFiles.quotes3, " \"\"a "),
            Arguments.of(TestFiles.quotes4, "\"\"\""), Arguments.of(TestFiles.quotes5, "\"\"a"));
    }

    @ParameterizedTest
    @MethodSource("tests")
    void shouldParseFixedQuotesLiterals(String in, String litForm) {
        OWLOntology o = loadFrom(in, tf);
        o.annotationAssertionAxioms(org.semanticweb.owlapi.api.test.baseclasses.DF.S.getIRI())
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
        OWLAxiom expected = AnnotationAssertion(AP, R.getIRI(), iri(R.getIRI().getNamespace(), ""));
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
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica,
            Literal("1.0E7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    void shouldParseScientificNotationWithMinus() {
        OWLOntology ontology =
            loadFrom(TestFiles.scientificNotationWithMinus, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(areaTotal::equals));
        assertTrue(
            ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica, oneMillionth)));
    }

    @Test
    void shouldParseScientificNotationWithMinusFromBug() {
        loadFrom(TestFiles.scientificNotation, new TurtleDocumentFormat());
    }

    @Test
    void shouldParseTwo() {
        OWLOntology ontology = loadFrom(TestFiles.parseTwo, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica, LIT_ONE)));
    }

    @Test
    void shouldParseOne() {
        OWLOntology ontology = loadFrom(TestFiles.parseOne, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(
            AnnotationAssertion(areaTotal, southAfrica, Literal("1.0", OWL2Datatype.XSD_DECIMAL))));
    }

    @Test
    void shouldParseEmptySpaceInBnode() {
        OWLOntology ontology = loadFrom(TestFiles.emptySpaceInBnode, new TurtleDocumentFormat());
        OWLIndividual ind =
            NamedIndividual(iri("http://taxonomy.wolterskluwer.de/practicearea/", "10112"));
        assertTrue(ontology.containsAxiom(ClassAssertion(concept, ind)));
        assertTrue(ontology.containsAxiom(ClassAssertion(taxTerm, ind)));
        assertTrue(ontology.containsEntityInSignature(broader));
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
        String string = "urn:test#test.owl/";
        OWLOntology ontology = create(iri(string, ""));
        ontology.add(SubClassOf(Class(string, "t"), Class(string, "q")));
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
                ObjectPropertyAssertion(anonR, ax.getIndividual(), ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        });
    }

    @Test
    void shouldAllowMultipleDotsInIRIs() {
        IRI test1 = iri("http://www.semanticweb.org/ontology#A...", "");
        IRI test2 = iri("http://www.semanticweb.org/ontology#A...B", "");
        OWLOntology o = create(iri("http://www.semanticweb.org/", "ontology"));
        o.addAxiom(Declaration(Class(test1)));
        o.addAxiom(Declaration(Class(test2)));
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setDefaultPrefix("http://www.semanticweb.org/ontology#");
        roundTrip(o, format);
    }

    @Test
    void shouldSaveWithCorrectPrefixes() {
        OWLOntology ont = loadFrom(TestFiles.correctPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLDocumentFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadFrom(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldSaveWithCorrectSlashPrefixes() {
        OWLOntology ont = loadFrom(TestFiles.slashPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLDocumentFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadFrom(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldUseRightPrefixesWithPercentURLs() {
        PrefixManager basePrefix = new DefaultPrefixManager("http://www.example.com#");
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
