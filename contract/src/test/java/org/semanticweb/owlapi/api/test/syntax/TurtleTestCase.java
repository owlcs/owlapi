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
import static org.semanticweb.owlapi.model.parameters.Imports.EXCLUDED;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashSet;
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
import org.semanticweb.owlapi.io.AnonymousIndividualProperties;
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
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
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
    static final OWLClass taxTerm = Class(IRI("http://schema.wolterskluwer.de/", "TaxonomyTerm"));
    static final OWLAnnotationProperty broader =
        AnnotationProperty(IRI("http://www.w3.org/2004/02/skos/core#", "broader"));
    static final OWLClass concept = Class(IRI("http://www.w3.org/2004/02/skos/core#", "Concept"));
    static final IRI iri = iri("urn:test#", "literals");
    private final TurtleDocumentFormat tf = new TurtleDocumentFormat();
    static final IRI s = iri("urn:test#", "s");
    static String NS = "http://example.com/ontology";
    static IRI individualIRI = IRI.create("http://example.com/ontology/x,y");
    static OWLNamedIndividual individual = df.getOWLNamedIndividual(individualIRI);

    private static String normalize(String string) {
        return string.replaceAll("\r", "").replaceAll("\\n#.*", "").replaceAll("\\n+", "\n").trim();
    }

    @Test
    void shouldParseQuotedTripleQuotes() {
        // given
        String literal = "Diadenosine 5',5'''-P1,P4-tetraphosphate phosphorylase";
        String working = "@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#> .\n "
            + "@prefix foaf:    <http://xmlns.com/foaf/0.1/> .\n foaf:fundedBy "
            + "rdfs:label \"\"\"" + literal + "\"\"\"@en .";
        OWLAxiom expected = AnnotationAssertion(df.getRDFSLabel(),
            IRI("http://xmlns.com/foaf/0.1/fundedBy"), Literal(literal, "en"));
        // when
        OWLOntology o = loadOntologyFromString(working);
        // then
        assertEquals(expected, o.getAxioms().iterator().next());
    }

    @Test
    void shouldSaveIRIsWithCommasInTurtle() {
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(individual);
        o.getOWLOntologyManager().addAxiom(o, axiom);
        TurtleDocumentFormat turtleFormat = new TurtleDocumentFormat();
        turtleFormat.setDefaultPrefix("http://example.com/ontology/");
        String string = saveOntology(o, turtleFormat).toString();
        OWLOntology o1 = loadOntologyFromString(string, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveIRIsWithCommasInRioTurtle() {
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(individual);
        o.getOWLOntologyManager().addAxiom(o, axiom);
        RioTurtleDocumentFormat turtleFormat = new RioTurtleDocumentFormat();
        turtleFormat.setDefaultPrefix("http://example.com/ontology/");
        String string = saveOntology(o, turtleFormat).toString();
        OWLOntology o1 = loadOntologyFromString(string, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void shouldSaveBaseIRINotOntologyInTurtle() {
        String base = "urn:test:base#";
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLNamedIndividual ind = df.getOWLNamedIndividual(iri(base, "i"));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(ind);
        o.getOWLOntologyManager().addAxiom(o, axiom);
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setDefaultPrefix(base);
        String string = normalize(saveOntology(o, format).toString());
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
    void shouldSaveBaseIRINotOntologyInRioTurtle() {
        String base = "urn:test:base#";
        OWLOntology o = create(iri(TestFiles.NS, ""));
        OWLNamedIndividual ind = df.getOWLNamedIndividual(iri(base, "i"));
        OWLAxiom axiom = df.getOWLDeclarationAxiom(ind);
        o.getOWLOntologyManager().addAxiom(o, axiom);
        RioTurtleDocumentFormat format = new RioTurtleDocumentFormat();
        format.setDefaultPrefix(base);
        String string = normalize(saveOntology(o, format).toString());
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
    void irisWithQuotesInTurtle() {
        OWLOntology o = create(iri);
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(IRI.create("urn:test#s't"),
            df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(true))));
        OWLOntology o1 = roundTrip(o, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void irisWithQuotesInRioTurtle() {
        OWLOntology o = create(iri);
        m.addAxiom(o, df.getOWLAnnotationAssertionAxiom(IRI.create("urn:test#s't"),
            df.getOWLAnnotation(df.getRDFSLabel(), df.getOWLLiteral(true))));
        OWLOntology o1 = roundTrip(o, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    void testLoadingUTF8BOM() throws URISyntaxException {
        IRI uri = IRI.create(getClass().getResource("/ttl-with-bom.ttl").toURI());
        loadOntology(uri, m);
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
        o.getAnnotationAssertionAxioms(s)
            .forEach(ax -> assertEquals(literal, ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    void shouldParseOntologyThatworked() {
        // given
        OWLAxiom expected = AnnotationAssertion(df.getRDFSIsDefinedBy(),
            iri("http://xmlns.com/foaf/0.1/", "fundedBy"), iri("http://xmlns.com/foaf/0.1/", ""));
        // when
        OWLOntology o = loadOntologyFromString(TestFiles.workingOnto, new TurtleDocumentFormat());
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    @Test
    void shouldParseOntologyThatBroke() {
        // given
        OWLAxiom expected =
            df.getOWLAnnotationAssertionAxiom(df.getOWLAnnotationProperty(iri("urn:test/", "p")),
                iri("urn:test/", "r"), iri("urn:test/", ""));
        // when
        OWLOntology o = loadOntologyFromString(TestFiles.brokenOnto, new TurtleDocumentFormat());
        // then
        assertTrue(o.getAxioms().contains(expected));
    }

    @Test
    void shouldResolveAgainstBase() {
        // given
        // when
        OWLOntology o =
            loadOntologyFromString(TestFiles.resolveAgainstBase, new TurtleDocumentFormat());
        // then
        String axioms = o.getAxioms().toString();
        assertTrue(axioms.contains("http://test.org/a1"));
        assertTrue(axioms.contains("http://test.org/b1"));
        assertTrue(axioms.contains("http://test.org/c1"));
    }

    // test for 3543488
    @Test
    void shouldRoundTripTurtleWithsharedBnodes() {
        AnonymousIndividualProperties.setRemapAllAnonymousIndividualsIds(false);
        try {
            OWLOntology ontology =
                loadOntologyFromString(TestFiles.turtleWithShared, new TurtleDocumentFormat());
            OWLOntology onto2 = roundTrip(ontology, new TurtleDocumentFormat());
            equal(ontology, onto2);
        } finally {
            AnonymousIndividualProperties.resetToDefault();
        }
    }

    // test for 335
    @Test
    void shouldParseScientificNotation() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.scientificNotationPlus, new TurtleDocumentFormat());
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(areaTotal));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica,
            Literal("1.0E7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    void shouldParseScientificNotationWithMinus() {
        OWLOntology ontology = loadOntologyFromString(TestFiles.scientificNotationWithMinus,
            new TurtleDocumentFormat());
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(areaTotal));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica,
            Literal("1.0E-7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    void shouldParseScientificNotationWithMinusFromBug() {
        loadOntologyFromString(TestFiles.scientificNotation, new TurtleDocumentFormat());
    }

    @Test
    void shouldParseTwo() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.parseTwo, new TurtleDocumentFormat());
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(areaTotal));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica, Literal(1))));
    }

    @Test
    void shouldParseOne() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.parseOne, new TurtleDocumentFormat());
        assertTrue(ontology.getAnnotationPropertiesInSignature(EXCLUDED).contains(areaTotal));
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
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.axiomAnnotations, new TurtleDocumentFormat());
        OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
        Set<OWLSubClassOfAxiom> axioms = o.getAxioms(AxiomType.SUBCLASS_OF);
        assertEquals(1, axioms.size());
        OWLAnnotation next = axioms.iterator().next().getAnnotations().iterator().next();
        assertTrue(next.getValue() instanceof OWLAnonymousIndividual);
        OWLAnonymousIndividual ind = (OWLAnonymousIndividual) next.getValue();
        Set<OWLAxiom> anns = new HashSet<>();
        for (OWLAxiom ax : o.getAxioms()) {
            if (ax.getAnonymousIndividuals().contains(ind)) {
                anns.add(ax);
            }
        }
        assertEquals(3, anns.size());
    }

    @Test
    void shouldRoundTripAxiomAnnotationWithSlashOntologyIRI() {
        OWLOntology in =
            loadOntologyFromString(TestFiles.slashOntologyIRI, new TurtleDocumentFormat());
        String string = "urn:test#test.owl/";
        OWLOntology ontology = create(iri(string, ""));
        m.addAxiom(ontology, df.getOWLSubClassOfAxiom(df.getOWLClass(iri(string, "t")),
            df.getOWLClass(iri(string, "q"))));
        OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
        equal(o, in);
    }

    @Test
    void presentDeclaration() {
        // when
        OWLOntology o =
            loadOntologyFromString(TestFiles.presentDeclaration, new TurtleDocumentFormat());
        // then
        for (OWLLogicalAxiom ax : o.getLogicalAxioms()) {
            assertTrue(ax instanceof OWLObjectPropertyDomainAxiom);
        }
    }

    @Test
    void whenMissingClassDeclarationCausesIllegalPunningAssertionsShouldBeFixedAfterParsing() {
        // given
        String input =
            "<urn:test#fm2.owl> rdf:type owl:Ontology.\n <urn:test#numberOfPads> rdf:type owl:ObjectProperty ;\n rdfs:domain <urn:test#Settlement> .";
        // when
        OWLOntology o = loadOntologyFromString(input);
        // then
        // Parsed as annotation domain, fixed in post parsing to object property
        // domain
        for (OWLLogicalAxiom ax : o.getLogicalAxioms()) {
            assertTrue(ax instanceof OWLObjectPropertyDomainAxiom);
        }
    }

    @Test
    void shouldReloadSamePrefixAbbreviations() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.prefixAbbreviations, new RioTurtleDocumentFormat());
        assertTrue(saveOntology(o).toString().contains("ABA:10"));
    }

    @Test
    void shouldFindExpectedAxiomsForBlankNodes() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.axiomsForBlankNodes, new TurtleDocumentFormat());
        o.getAxioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(anonR, ax.getIndividual(),
                ax.getIndividual());
            assertTrue(o.containsAxiom(expected), expected + " not found");
        });
    }

    @Test
    void shouldAllowMultipleDotsInIRIs() {
        IRI test1 = IRI.create("http://www.semanticweb.org/ontology#A...");
        IRI test2 = IRI.create("http://www.semanticweb.org/ontology#A...B");
        OWLOntology o = create(IRI.create("http://www.semanticweb.org/ontology"));
        m.addAxiom(o, df.getOWLDeclarationAxiom(df.getOWLClass(test1)));
        m.addAxiom(o, df.getOWLDeclarationAxiom(df.getOWLClass(test2)));
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        format.setDefaultPrefix("http://www.semanticweb.org/ontology#");
        roundTrip(o, format);
    }

    @Test
    void shouldSaveWithCorrectPrefixes() {
        OWLOntology ont =
            loadOntologyFromString(TestFiles.correctPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLOntologyFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldSaveWithCorrectSlashPrefixes() {
        OWLOntology ont = loadOntologyFromString(TestFiles.slashPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ofmt.asPrefixOWLOntologyFormat().setPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldUseRightPrefixesWithPercentURLs() {
        PrefixManager basePrefix = new DefaultPrefixManager("http://www.example.com#");
        OWLOntology ontology = create(IRI.create("http://www.example.com"));
        OWLObjectProperty owlObjectP = df.getOWLObjectProperty("has%20space", basePrefix);
        OWLClass domain = df.getOWLClass("domain1", basePrefix);
        m.addAxiom(ontology, df.getOWLObjectPropertyDomainAxiom(owlObjectP, domain));

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
        OWLOntology o1 = loadOntologyFromFile(file, new TurtleDocumentFormat(), m1);
        OWLOntology o2 = loadOntologyFromFile(file, new RioTurtleDocumentFormat(), m1);
        equal(o1, o2);
    }

    @Test
    void shouldParseEscapedCharacters() {
        OWLOntology ont = loadOntologyFromFile(new File(RESOURCES, "noBaseEscapedSlashes.ttl"),
            new TurtleDocumentFormat(), m1);
        OWLOntology o1 = roundTrip(ont, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    void shouldParseWithBase() {
        OWLOntology ont = loadOntologyFromFile(new File(RESOURCES, "noBaseEscapedSlashes.ttl"),
            new RioTurtleDocumentFormat(), m1);
        OWLOntology o1 = roundTrip(ont, new RioTurtleDocumentFormat());
        equal(ont, o1);
    }
}
