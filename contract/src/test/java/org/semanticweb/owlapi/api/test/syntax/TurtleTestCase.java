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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import org.semanticweb.owlapi.io.FileDocumentSource;
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
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

class TurtleTestCase extends TestBase {

    private static final OWLObjectProperty anonR =
        ObjectProperty(iri("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
    private static final OWLClass taxTerm =
        Class(IRI("http://schema.wolterskluwer.de/", "TaxonomyTerm"));
    private static final OWLAnnotationProperty broader =
        AnnotationProperty(IRI("http://www.w3.org/2004/02/skos/core#", "broader"));
    static final OWLClass concept = Class(IRI("http://www.w3.org/2004/02/skos/core#", "Concept"));
    private final IRI iri = iri("urn:test#", "literals");
    private final TurtleDocumentFormat tf = new TurtleDocumentFormat();
    private final IRI s = iri("urn:test#", "s");
    String NS = "http://example.com/ontology";
    IRI individualIRI = IRI.create("http://example.com/ontology/x,y");
    OWLNamedIndividual individual = df.getOWLNamedIndividual(individualIRI);

    private static String normalize(String s) {
        return s.replaceAll("\r", "").replaceAll("\\n#.*", "").replaceAll("\\n+", "\n").trim();
    }

    @Test
    void shouldSaveIRIsWithCommasInTurtle()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(iri(TestFiles.NS));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(individual);
        o.add(axiom);
        StringDocumentTarget t = new StringDocumentTarget();
        TurtleDocumentFormat turtleFormat = new TurtleDocumentFormat();
        turtleFormat.setDefaultPrefix("http://example.com/ontology/");
        o.saveOntology(turtleFormat, t);
        String string = t.toString();
        OWLOntology o1 = loadOntologyFromString(string, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveIRIsWithCommasInRioTurtle()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(iri(TestFiles.NS));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(individual);
        o.add(axiom);
        StringDocumentTarget t = new StringDocumentTarget();
        RioTurtleDocumentFormat turtleFormat = new RioTurtleDocumentFormat();
        turtleFormat.setDefaultPrefix("http://example.com/ontology/");
        o.saveOntology(turtleFormat, t);
        String string = t.toString();
        OWLOntology o1 = loadOntologyFromString(string, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveBaseIRINotOntologyInTurtle()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String base = "urn:test:base#";
        OWLOntology o = m.createOntology(iri(TestFiles.NS, ""));
        OWLNamedIndividual ind = df.getOWLNamedIndividual(iri(base, "i"));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(ind);
        o.addAxiom(axiom);
        StringDocumentTarget t = new StringDocumentTarget();
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setDefaultPrefix(base);
        o.saveOntology(format, t);
        String string = normalize(t.toString());
        assertEquals(
            "@prefix : <urn:test:base#> .\n" + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "@base <urn:test:base#> .\n"
                + "<http://example.com/ontology> rdf:type owl:Ontology .\n"
                + ":i rdf:type owl:NamedIndividual .",
            string);
        OWLOntology o1 = loadOntologyFromString(string, format);
        equal(o, o1);
    }

    @Test
    void shouldSaveBaseIRINotOntologyInRioTurtle()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        String base = "urn:test:base#";
        OWLOntology o = m.createOntology(iri(TestFiles.NS, ""));
        OWLNamedIndividual ind = df.getOWLNamedIndividual(iri(base, "i"));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(ind);
        o.getOWLOntologyManager().addAxiom(o, axiom);
        StringDocumentTarget t = new StringDocumentTarget();
        RioTurtleDocumentFormat format = new RioTurtleDocumentFormat();
        format.setDefaultPrefix(base);
        o.saveOntology(format, t);
        String string = normalize(t.toString());
        assertEquals(
            "@base <urn:test:base#> .\n" + "@prefix : <urn:test:base#> .\n"
                + "@prefix owl: <http://www.w3.org/2002/07/owl#> .\n"
                + "@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .\n"
                + "@prefix xml: <http://www.w3.org/XML/1998/namespace> .\n"
                + "@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .\n"
                + "@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .\n"
                + "<http://example.com/ontology> a owl:Ontology .\n" + ":i a owl:NamedIndividual .",
            string);
        OWLOntology o1 = loadOntologyFromString(string, format);
        equal(o, o1);
    }

    @Test
    void irisWithQuotesInTurtle() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology(iri);
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(IRI.create("urn:test#s't"),
            df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(true))));
        OWLOntology o1 = roundTrip(o, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void irisWithQuotesInRioTurtle() throws OWLOntologyCreationException {
        OWLOntology o = m.createOntology(iri);
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(IRI.create("urn:test#s't"),
            df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(true))));
        OWLOntology o1 = roundTrip(o, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void testLoadingUTF8BOM() throws Exception {
        IRI uri = IRI.create(getClass().getResource("/ttl-with-bom.ttl").toURI());
        m.loadOntologyFromOntologyDocument(uri);
    }

    static Stream<Arguments> tests() {
        return Stream.of(Arguments.of(TestFiles.quotes1, " ''' "),
            Arguments.of(TestFiles.quotes6, "3'''-acetate; [cut]"),
            Arguments.of(TestFiles.quotes2, " \"\"\" "), Arguments.of(TestFiles.quotes3, " \"\"a "),
            Arguments.of(TestFiles.quotes4, "\"\"\""), Arguments.of(TestFiles.quotes5, "\"\"a"));
    }

    @ParameterizedTest
    @MethodSource("tests")
    void shouldParseFixedQuotesLiterals(String in, String literal) {
        OWLOntology o = loadOntologyFromString(in, tf);
        o.annotationAssertionAxioms(s)
            .forEach(ax -> assertEquals(literal, ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    void shouldParseOntologyThatworked() {
        // given
        OWLAxiom expected = AnnotationAssertion(df.getRDFSIsDefinedBy(),
            IRI("http://xmlns.com/foaf/0.1/", "fundedBy"), IRI("http://xmlns.com/foaf/0.1/", ""));
        // when
        OWLOntology o = loadOntologyFromString(TestFiles.workingOnto, new TurtleDocumentFormat());
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    void shouldParseOntologyThatBroke() {
        // given
        OWLAxiom expected =
            df.getOWLAnnotationAssertionAxiom(df.getOWLAnnotationProperty("urn:test/", "p"),
                IRI("urn:test/", "r"), IRI("urn:test/", ""));
        // when
        OWLOntology o = loadOntologyFromString(TestFiles.brokenOnto, new TurtleDocumentFormat());
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    void shouldResolveAgainstBase() {
        // given
        // when
        OWLOntology o =
            loadOntologyFromString(TestFiles.resolveAgainstBase, new TurtleDocumentFormat());
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
            OWLOntology ontology =
                loadOntologyFromString(TestFiles.turtleWithShared, new TurtleDocumentFormat());
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
            loadOntologyFromString(TestFiles.scientificNotationPlus, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica,
            Literal("1.0E7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    void shouldParseScientificNotationWithMinus() {
        OWLOntology ontology = loadOntologyFromString(TestFiles.scientificNotationWithMinus,
            new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(
            ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica, oneMillionth)));
    }

    @Test
    void shouldParseScientificNotationWithMinusFromBug() {
        loadOntologyFromString(TestFiles.scientificNotation, new TurtleDocumentFormat());
    }

    @Test
    void shouldParseTwo() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.parseTwo, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica, Literal(1))));
    }

    @Test
    void shouldParseOne() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.parseOne, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(
            AnnotationAssertion(areaTotal, southAfrica, Literal("1.0", OWL2Datatype.XSD_DECIMAL))));
    }

    @Test
    void shouldParseEmptySpaceInBnode() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.emptySpaceInBnode, new TurtleDocumentFormat());
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
            OWLOntology ontology =
                loadOntologyFromString(TestFiles.axiomAnnotations, new TurtleDocumentFormat());
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
        OWLOntology in =
            loadOntologyFromString(TestFiles.slashOntologyIRI, new TurtleDocumentFormat());
        String string = "urn:test#test.owl/";
        OWLOntology ontology = getOWLOntology(iri(string, ""));
        ontology.add(
            df.getOWLSubClassOfAxiom(df.getOWLClass(string, "t"), df.getOWLClass(string, "q")));
        OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
        equal(o, in);
    }

    @Test
    void presentDeclaration() {
        // when
        OWLOntology o =
            loadOntologyFromString(TestFiles.presentDeclaration, new TurtleDocumentFormat());
        // then
        o.logicalAxioms().forEach(ax -> assertTrue(ax instanceof OWLObjectPropertyDomainAxiom));
    }

    @Test
    void missingDeclaration() {
        // when
        OWLOntology o =
            loadOntologyFromString(TestFiles.missingDeclaration, new TurtleDocumentFormat());
        // then
        o.logicalAxioms()
            .forEach(ax -> assertTrue(ax instanceof OWLObjectPropertyDomainAxiom, ax.toString()));
    }

    @Test
    void shouldReloadSamePrefixAbbreviations() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.prefixAbbreviations, new RioTurtleDocumentFormat());
        StringDocumentTarget t = saveOntology(o);
        assertTrue(t.toString().contains("ABA:10"));
    }

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.axiomsForBlankNodes, new TurtleDocumentFormat());
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(anonR, ax.getIndividual(),
                ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        });
    }

    @Test
    void shouldAllowMultipleDotsInIRIs() throws OWLOntologyCreationException {
        IRI test1 = IRI.create("http://www.semanticweb.org/ontology#A...");
        IRI test2 = IRI.create("http://www.semanticweb.org/ontology#A...B");
        OWLOntology o = m.createOntology(IRI.create("http://www.semanticweb.org/ontology"));
        o.addAxiom(df.getOWLDeclarationAxiom(df.getOWLClass(test1)));
        o.addAxiom(df.getOWLDeclarationAxiom(df.getOWLClass(test2)));
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setDefaultPrefix("http://www.semanticweb.org/ontology#");
        roundTrip(o, format);
    }

    @Test
    void shouldSaveWithCorrectPrefixes() {
        OWLOntology ont =
            loadOntologyFromString(TestFiles.correctPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLDocumentFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldSaveWithCorrectSlashPrefixes() {
        OWLOntology ont = loadOntologyFromString(TestFiles.slashPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLDocumentFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldUseRightPrefixesWithPercentURLs()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        PrefixManager basePrefix = new DefaultPrefixManager("http://www.example.com#");
        OWLOntology ontology = m.createOntology(IRI.create("http://www.example.com"));
        OWLObjectProperty owlObjectP = df.getOWLObjectProperty("has%20space", basePrefix);
        OWLClass domain = df.getOWLClass("domain1", basePrefix);
        ontology.add(df.getOWLObjectPropertyDomainAxiom(owlObjectP, domain));
        TurtleDocumentFormat turtle = new TurtleDocumentFormat();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ontology.saveOntology(turtle, out);
        String string = out.toString();
        assertTrue(
            string.contains("<http://www.example.com#has%20space> rdf:type owl:ObjectProperty"),
            string);
    }

    @Test
    void sameFileShouldParseToSameOntology() throws OWLOntologyCreationException {
        File file = new File(RESOURCES, "noBaseEscapedSlashes.ttl");
        OWLOntology o1 = m1.loadOntologyFromOntologyDocument(
            new FileDocumentSource(file, new TurtleDocumentFormat()));
        OWLOntology o2 = m1.loadOntologyFromOntologyDocument(
            new FileDocumentSource(file, new RioTurtleDocumentFormat()));
        equal(o1, o2);
    }

    @Test
    void shouldParseEscapedCharacters() throws OWLOntologyCreationException {
        OWLOntology ont = m1.loadOntologyFromOntologyDocument(new FileDocumentSource(
            new File(RESOURCES, "noBaseEscapedSlashes.ttl"), new TurtleDocumentFormat()));
        OWLOntology o1 = roundTrip(ont, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldParseWithBase() throws OWLOntologyCreationException {
        OWLOntology ont = m1.loadOntologyFromOntologyDocument(new FileDocumentSource(
            new File(RESOURCES, "noBaseEscapedSlashes.ttl"), new RioTurtleDocumentFormat()));
        OWLOntology o1 = roundTrip(ont, new RioTurtleDocumentFormat());
        equal(ont, o1);
    }
}
