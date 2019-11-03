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
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi6.apitest.TestEntities.areaTotal;
import static org.semanticweb.owlapi6.apitest.TestEntities.southAfrica;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestEntities;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.FileDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.documents.StringDocumentTarget;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLAnnotation;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLIndividual;
import org.semanticweb.owlapi6.model.OWLLiteral;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyStorageException;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi6.utilities.PrefixManagerImpl;
import org.semanticweb.owlapi6.vocab.OWL2Datatype;

public class TurtleTestCase extends TestBase {

    private static final OWLObjectProperty anonR = ObjectProperty(
        df.getIRI("http://www.derivo.de/ontologies/examples/anonymous-individuals#", "r"));
    private static final OWLClass taxTerm =
        Class(IRI("http://schema.wolterskluwer.de/", "TaxonomyTerm"));
    private static final OWLAnnotationProperty broader =
        AnnotationProperty(IRI("http://www.w3.org/2004/02/skos/core#", "broader"));
    private static final OWLClass concept =
        Class(IRI("http://www.w3.org/2004/02/skos/core#", "Concept"));
    private static final IRI indXY = df.getIRI(TestFiles.XY);
    private final TurtleDocumentFormat tf = new TurtleDocumentFormat();
    private final IRI s = df.getIRI("urn:test#", "s");
    private final String iri = "urn:test:literals";

    @Test
    public void shouldSaveIRIsWithCommasInTurtle()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(df.getIRI(TestFiles.NS));
        OWLNamedIndividual individual = df.getOWLNamedIndividual(indXY);
        OWLAxiom axiom = df.getOWLDeclarationAxiom(individual);
        o.add(axiom);
        StringDocumentTarget t = new StringDocumentTarget();
        o.getPrefixManager().withDefaultPrefix(TestFiles.PREFIX);
        o.saveOntology(new TurtleDocumentFormat(), t);
        String string = t.toString();
        OWLOntology o1 = loadOntologyFromString(string, new TurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    public void shouldSaveIRIsWithCommasInRioTurtle()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology o = m.createOntology(df.getIRI(TestFiles.NS));
        OWLNamedIndividual individual = df.getOWLNamedIndividual(indXY);
        OWLAxiom axiom = df.getOWLDeclarationAxiom(individual);
        o.add(axiom);
        StringDocumentTarget t = new StringDocumentTarget();
        o.getPrefixManager().withDefaultPrefix(TestFiles.PREFIX);
        o.saveOntology(new RioTurtleDocumentFormat(), t);
        String string = t.toString();
        OWLOntology o1 = loadOntologyFromString(string, new RioTurtleDocumentFormat());
        equal(o, o1);
    }

    @Test
    public void testLoadingUTF8BOM() throws Exception {
        IRI uri = df.getIRI(getClass().getResource("/ttl-with-bom.ttl").toURI());
        m.loadOntologyFromOntologyDocument(uri);
    }

    @Test
    public void shouldParseFixedQuotesLiterals1() throws OWLOntologyCreationException {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.quotes1, iri, tf, null));
        o.annotationAssertionAxioms(s)
            .forEach(ax -> assertEquals(" ''' ", ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    public void shouldParseFixedQuotesLiterals6() throws OWLOntologyCreationException {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.quotes6, iri, tf, null));
        o.annotationAssertionAxioms(s).forEach(
            ax -> assertEquals("3'''-acetate; [cut]", ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    public void shouldParseFixedQuotesLiterals2() throws OWLOntologyCreationException {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.quotes2, iri, tf, null));
        o.annotationAssertionAxioms(s)
            .forEach(ax -> assertEquals(" \"\"\" ", ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    public void shouldParseFixedQuotesLiterals3() throws OWLOntologyCreationException {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.quotes3, iri, tf, null));
        o.annotationAssertionAxioms(s)
            .forEach(ax -> assertEquals(" \"\"a ", ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    public void shouldParseFixedQuotesLiterals4() throws OWLOntologyCreationException {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.quotes4, iri, tf, null));
        o.annotationAssertionAxioms(s)
            .forEach(ax -> assertEquals("\"\"\"", ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    public void shouldParseFixedQuotesLiterals5() throws OWLOntologyCreationException {
        OWLOntology o =
            loadOntologyFromString(new StringDocumentSource(TestFiles.quotes5, iri, tf, null));
        o.annotationAssertionAxioms(s)
            .forEach(ax -> assertEquals("\"\"a", ((OWLLiteral) ax.getValue()).getLiteral()));
    }

    @Test
    public void shouldParseOntologyThatworked() {
        // given
        OWLAxiom expected = AnnotationAssertion(df.getRDFSIsDefinedBy(),
            IRI("http://xmlns.com/foaf/0.1/", "fundedBy"), IRI("http://xmlns.com/foaf/0.1/", ""));
        // when
        OWLOntology o = loadOntologyFromString(TestFiles.workingOnto, new TurtleDocumentFormat());
        // then
        assertTrue(o.containsAxiom(expected));
    }

    @Test
    public void shouldParseOntologyThatBroke() {
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
    public void shouldResolveAgainstBase() {
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
    public void shouldRoundTripTurtleWithsharedBnodes() throws Exception {
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
    public void shouldParseScientificNotation() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.scientificNotationPlus, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica,
            Literal("1.0E7", OWL2Datatype.XSD_DOUBLE))));
    }

    @Test
    public void shouldParseScientificNotationWithMinus() {
        OWLOntology ontology = loadOntologyFromString(TestFiles.scientificNotationWithMinus,
            new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology
            .containsAxiom(AnnotationAssertion(areaTotal, southAfrica, TestEntities.oneMillionth)));
    }

    @Test
    public void shouldParseScientificNotationWithMinusFromBug() {
        loadOntologyFromString(TestFiles.scientificNotation, new TurtleDocumentFormat());
    }

    @Test
    public void shouldParseTwo() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.parseTwo, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(AnnotationAssertion(areaTotal, southAfrica, Literal(1))));
    }

    @Test
    public void shouldParseOne() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.parseOne, new TurtleDocumentFormat());
        assertTrue(ontology.annotationPropertiesInSignature().anyMatch(ap -> ap.equals(areaTotal)));
        assertTrue(ontology.containsAxiom(
            AnnotationAssertion(areaTotal, southAfrica, Literal("1.0", OWL2Datatype.XSD_DECIMAL))));
    }

    @Test
    public void shouldParseEmptySpaceInBnode() {
        OWLOntology ontology =
            loadOntologyFromString(TestFiles.emptySpaceInBnode, new TurtleDocumentFormat());
        OWLIndividual i =
            NamedIndividual(IRI("http://taxonomy.wolterskluwer.de/practicearea/10112", ""));
        assertTrue(ontology.containsAxiom(ClassAssertion(concept, i)));
        assertTrue(ontology.containsAxiom(ClassAssertion(taxTerm, i)));
        assertTrue(ontology.containsEntityInSignature(broader));
    }

    @Test
    public void shouldRoundTripAxiomAnnotation() throws Exception {
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
    public void shouldRoundTripAxiomAnnotationWithSlashOntologyIRI() throws Exception {
        OWLOntology in =
            loadOntologyFromString(TestFiles.slashOntologyIRI, new TurtleDocumentFormat());
        String string = "urn:test#test.owl/";
        OWLOntology ontology = getOWLOntology(df.getIRI(string, ""));
        ontology.add(
            df.getOWLSubClassOfAxiom(df.getOWLClass(string, "t"), df.getOWLClass(string, "q")));
        OWLOntology o = roundTrip(ontology, new TurtleDocumentFormat());
        equal(o, in);
    }

    @Test
    public void presentDeclaration() {
        // when
        OWLOntology o =
            loadOntologyFromString(TestFiles.presentDeclaration, new TurtleDocumentFormat());
        // then
        o.logicalAxioms().forEach(ax -> assertTrue(ax instanceof OWLObjectPropertyDomainAxiom));
    }

    @Test
    public void missingDeclaration() {
        // when
        OWLOntology o =
            loadOntologyFromString(TestFiles.missingDeclaration, new TurtleDocumentFormat());
        // then
        o.logicalAxioms()
            .forEach(ax -> assertTrue(ax.toString(), ax instanceof OWLObjectPropertyDomainAxiom));
    }

    @Test
    public void shouldReloadSamePrefixAbbreviations() throws OWLOntologyStorageException {
        OWLOntology o =
            loadOntologyFromString(TestFiles.prefixAbbreviations, new RioTurtleDocumentFormat());
        StringDocumentTarget t = saveOntology(o);
        assertTrue(t.toString().contains("ABA:10"));
    }

    @Test
    public void shouldFindExpectedAxiomsForBlankNodes() {
        OWLOntology o =
            loadOntologyFromString(TestFiles.axiomsForBlankNodes, new TurtleDocumentFormat());
        o.axioms(AxiomType.CLASS_ASSERTION).forEach(ax -> {
            OWLAxiom expected = df.getOWLObjectPropertyAssertionAxiom(anonR, ax.getIndividual(),
                ax.getIndividual());
            assertTrue(expected + " not found", o.containsAxiom(expected));
        });
    }

    @Test
    public void shouldAllowMultipleDotsInIRIs()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        IRI test1 = df.getIRI("http://www.semanticweb.org/ontology#A...");
        IRI test2 = df.getIRI("http://www.semanticweb.org/ontology#A...B");
        OWLOntology o = m.createOntology(df.getIRI("http://www.semanticweb.org/ontology"));
        o.addAxiom(df.getOWLDeclarationAxiom(df.getOWLClass(test1)));
        o.addAxiom(df.getOWLDeclarationAxiom(df.getOWLClass(test2)));
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        o.getPrefixManager().withDefaultPrefix("http://www.semanticweb.org/ontology#");
        roundTrip(o, format);
    }

    @Test
    public void shouldSaveWithCorrectPrefixes() throws OWLOntologyStorageException {
        OWLOntology ont =
            loadOntologyFromString(TestFiles.correctPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ont.getPrefixManager().withPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    public void shouldSaveWithCorrectSlashPrefixes() throws OWLOntologyStorageException {
        OWLOntology ont = loadOntologyFromString(TestFiles.slashPrefix, new TurtleDocumentFormat());
        OWLDocumentFormat ofmt = new TurtleDocumentFormat();
        ont.getPrefixManager().withPrefix("OBO", "http://purl.obolibrary.org/obo/");
        StringDocumentTarget result = saveOntology(ont, ofmt);
        OWLOntology o1 = loadOntologyFromString(result, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    public void shouldUseRightPrefixesWithPercentURLs()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        PrefixManager basePrefix =
            new PrefixManagerImpl().withDefaultPrefix("http://www.example.com#");
        OWLOntology ontology = m.createOntology(df.getIRI("http://www.example.com"));
        OWLObjectProperty owlObjectP = df.getOWLObjectProperty("has%20space", basePrefix);
        OWLClass domain = df.getOWLClass("domain1", basePrefix);
        ontology.add(df.getOWLObjectPropertyDomainAxiom(owlObjectP, domain));
        TurtleDocumentFormat turtle = new TurtleDocumentFormat();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ontology.saveOntology(turtle, out);
        String string = out.toString();
        assertTrue(string,
            string.contains("<http://www.example.com#has%20space> rdf:type owl:ObjectProperty"));
    }

    @Test
    public void sameFileShouldParseToSameOntology() throws OWLOntologyCreationException {
        File file = new File(RESOURCES, "noBaseEscapedSlashes.ttl");
        OWLOntology o1 = m1.loadOntologyFromOntologyDocument(
            new FileDocumentSource(file, new TurtleDocumentFormat()));
        OWLOntology o2 = m1.loadOntologyFromOntologyDocument(
            new FileDocumentSource(file, new RioTurtleDocumentFormat()));
        equal(o1, o2);
    }

    @Test
    public void shouldParseEscapedCharacters()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ont = m1.loadOntologyFromOntologyDocument(new FileDocumentSource(
            new File(RESOURCES, "noBaseEscapedSlashes.ttl"), new TurtleDocumentFormat()));
        OWLOntology o1 = roundTrip(ont, new TurtleDocumentFormat());
        equal(ont, o1);
    }

    @Test
    public void shouldParseWithBase()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        OWLOntology ont = m1.loadOntologyFromOntologyDocument(new FileDocumentSource(
            new File(RESOURCES, "noBaseEscapedSlashes.ttl"), new RioTurtleDocumentFormat()));
        OWLOntology o1 = roundTrip(ont, new RioTurtleDocumentFormat());
        equal(ont, o1);
    }
}
