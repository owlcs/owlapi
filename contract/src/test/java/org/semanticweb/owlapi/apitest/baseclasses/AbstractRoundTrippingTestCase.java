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
package org.semanticweb.owlapi.apitest.baseclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.rdf.api.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.apitest.TestFilenames;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.documents.FileDocumentSource;
import org.semanticweb.owlapi.documents.IRIDocumentSource;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.documents.StringDocumentTarget;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFXMLParserException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rioformats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
class AbstractRoundTrippingTestCase extends TestBase {

    static List<List<OWLAxiom>> literalWithEscapesNoRioRDFXMLTestCase() {
        return l(l(ann("\\"), ann("Start\\"), ann("\\End"), ann("Start\\End"), ann("\""),
            ann("Start\""), ann("\"End"), ann("Start\"End"), ann("<"), ann("Start<"), ann("<End"),
            ann("Start<End"), ann("\'"), ann("Start\'"), ann("\'End"), ann("Start\'End"),
            Declaration(CLASSES.A)));
    }

    protected static OWLAnnotationAssertionAxiom ann(String al) {
        return AnnotationAssertion(ANNPROPS.AP, CLASSES.A.getIRI(), Literal(al));
    }

    @ParameterizedTest
    @MethodSource("formats")
    void anonymousRoundTripTestCase(OWLDocumentFormat format) {
        OWLOntology ont1 = createAnon();
        ont1.add(anonymousRoundTrip());
        roundTrip(ont1, format);
    }

    static List<OWLAxiom> anonymousRoundTrip() {
        OWLAnnotation an1 =
            Annotation(RDFSComment(Literal("z1")), ANNPROPS.AP, AnonymousIndividual("_:b0"));
        OWLClassExpression c1 = DataAllValuesFrom(DATAPROPS.DP, Boolean());
        OWLClassExpression c2 = ObjectSomeValuesFrom(OBJPROPS.op1, OWLThing());

        return l(SubClassOf(an1, c1, c2));
    }

    static List<List<OWLAxiom>> xmlAndFunctional() {
        return l(anonymousRoundTrip(), literalWithEscapes());
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameAnonOntology() {
        plainEqual(anonOntology(), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void anonymousOntologyAnnotationsTestCase(OWLDocumentFormat format) {
        roundTrip(anonOntology(), format);
    }

    protected OWLOntology anonOntology() {
        OWLOntology ont = createAnon();
        OWLAnnotation annotation = Annotation(ANNPROPS.AP, Literal(33));
        ont.applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.add(Declaration(ANNPROPS.AP));
        return ont;
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameBlankNodesTurtleDomain()
        throws URISyntaxException {
        plainEqual(blankNodesTurtleDomain(), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void fileRoudTripWithKnownInputFormatTestCase(OWLDocumentFormat format)
        throws URISyntaxException {
        roundTrip(blankNodesTurtleDomain(), format);
    }

    protected OWLOntology blankNodesTurtleDomain() throws URISyntaxException {
        URL resource = getClass().getResource('/' + TestFilenames.TEST_BLANK_NODES_DOMAIN_TTL);
        IRI iri = iri(resource.toURI());
        return loadFrom(new IRIDocumentSource(iri.toString(), new TurtleDocumentFormat(), null));
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameXmlLiteral() {
        plainEqual(load(TestFilenames.XML_LITERAL_RDF), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void fileRoundTripNoRioRDFXMLTestCase(OWLDocumentFormat format) {
        if (RioRDFXMLDocumentFormat.class.isInstance(format)) {
            // XML literals managed differently in Rio
            return;
        }
        roundTrip(load(TestFilenames.XML_LITERAL_RDF), format);
    }

    @ParameterizedTest
    @ValueSource(strings = {TestFilenames.ANONYMOUS_INVERSES_RDF, TestFilenames.TEST_PARSER08_RDF,
        TestFilenames.NODEID_RDF, TestFilenames.EXTRA_BLANK_NODES_OWL,
        TestFilenames.TEST_BLANK_NODES2_TTL, TestFilenames.TEST_BLANK_NODES_ASSERTIONS_TTL,
        TestFilenames.OWLXML_ANONLOOP_OWX})
    void roundTripsRDFXMLAndFunctionalShouldBeSame(String fileName) {
        plainEqual(load(fileName), false);
    }

    @ParameterizedTest
    @MethodSource({TestFilenames.AXIOMS_ROUND_TRIPPING_USING_EQUAL_TEST_CASE})
    void roundTripsRDFXMLAndFunctionalShouldBeSame(List<OWLAxiom> axioms) {
        plainEqual(o(axioms), false);
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingUsingEqualTestCase() {
        return l(anonymousIndividualRoundtrip(), anonymousIndividuals2(), anonymousIndividuals(),
            chainedAnonymousIndividuals(), classAssertionWithAnonymousIndividual(),
            differentIndividualsAnonymous(), differentIndividualsPairwiseAnonymous(),
            objectPropertyAssertionWithAnonymousIndividuals(), sameIndividualsAnonymous());
    }

    private static List<OWLAxiom> sameIndividualsAnonymous() {
        // Can't round trip more than two in RDF! Also, same
        // individuals axiom with anon individuals is not allowed
        // in OWL 2, but it should at least round trip
        return l(SameIndividual(AnonymousIndividual(), AnonymousIndividual()));
    }

    private static List<OWLAxiom> objectPropertyAssertionWithAnonymousIndividuals() {
        return l(ObjectPropertyAssertion(OBJPROPS.P, AnonymousIndividual(), AnonymousIndividual()),
            Declaration(OBJPROPS.P));
    }

    private static List<OWLAxiom> differentIndividualsPairwiseAnonymous() {
        return l(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual()));
    }

    private static List<OWLAxiom> differentIndividualsAnonymous() {
        return l(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual(),
            AnonymousIndividual()));
    }

    private static List<OWLAxiom> classAssertionWithAnonymousIndividual() {
        return l(ClassAssertion(CLASSES.A, AnonymousIndividual("a")), Declaration(CLASSES.A));
    }

    private static List<OWLAxiom> chainedAnonymousIndividuals() {
        OWLAnonymousIndividual ind1 = AnonymousIndividual();
        OWLAnonymousIndividual ind2 = AnonymousIndividual();
        OWLAnonymousIndividual ind3 = AnonymousIndividual();
        OWLAnnotationAssertionAxiom assertion1 =
            AnnotationAssertion(ANNPROPS.AP, INDIVIDUALS.subject.getIRI(), ind1);
        OWLAnnotationAssertionAxiom assertion2 = AnnotationAssertion(ANNPROPS.AP, ind1, ind2);
        OWLAnnotationAssertionAxiom assertion3 = AnnotationAssertion(ANNPROPS.AP, ind2, ind3);
        return l(Declaration(NamedIndividual(INDIVIDUALS.subject.getIRI())), assertion1, assertion2,
            assertion3);
    }

    private static List<OWLAxiom> anonymousIndividuals() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        return l(ObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.I, ind),
            ObjectPropertyAssertion(OBJPROPS.P, ind, INDIVIDUALS.J));
    }

    private static List<OWLAxiom> anonymousIndividuals2() {
        // Originally submitted by Timothy Redmond
        OWLAnonymousIndividual hAnon = AnonymousIndividual();
        return l(AnnotationAssertion(ANNPROPS.AP, CLASSES.A.getIRI(), hAnon),
            ClassAssertion(CLASSES.A, hAnon),
            ObjectPropertyAssertion(OBJPROPS.Q, hAnon, AnonymousIndividual()),
            AnnotationAssertion(RDFSLabel(), hAnon, Literal("Second", "en")));
    }

    private static List<OWLAxiom> anonymousIndividualRoundtrip() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(ANNPROPS.AP, CLASSES.A.getIRI(), ind);
        OWLAnonymousIndividual anon1 = AnonymousIndividual();
        OWLAnonymousIndividual anon2 = AnonymousIndividual();
        return l(ax, Declaration(CLASSES.A), Declaration(OBJPROPS.P),
            ObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.J, INDIVIDUALS.I),
            ObjectPropertyAssertion(OBJPROPS.P, anon1, anon1),
            ObjectPropertyAssertion(OBJPROPS.P, anon2, INDIVIDUALS.I),
            ObjectPropertyAssertion(OBJPROPS.P, INDIVIDUALS.I, anon2));
    }

    static final List<String> FILE_ROUND_TRIP = l(TestFilenames.ANNOTATED_PROPERTY_ASSERTIONS_RDF,
        TestFilenames.COMPLEX_SUB_PROPERTY_RDF, TestFilenames.DATA_ALL_VALUES_FROM_RDF,
        TestFilenames.CARDINALITYWITHWHITESPACE_OWL, TestFilenames.DATA_COMPLEMENT_OF_RDF,
        TestFilenames.DATA_HAS_VALUE_RDF, TestFilenames.DATA_INTERSECTION_OF_RDF,
        TestFilenames.DATA_MAX_CARDINALITY_RDF, TestFilenames.DATA_MIN_CARDINALITY_RDF,
        TestFilenames.DATA_ONE_OF_RDF, TestFilenames.DATA_SOME_VALUES_FROM_RDF,
        TestFilenames.DATA_UNION_OF_RDF, TestFilenames.DATATYPE_RESTRICTION_RDF,
        TestFilenames.TEST_DECLARATIONS_RDF, TestFilenames.DEPRECATED_RDF,
        TestFilenames.DISJOINT_CLASSES_RDF, TestFilenames.HAS_KEY_RDF, TestFilenames.INVERSE_OF_RDF,
        TestFilenames.OBJECT_ALL_VALUES_FROM_RDF, TestFilenames.OBJECT_CARDINALITY_RDF,
        TestFilenames.OBJECT_COMPLEMENT_OF_RDF, TestFilenames.OBJECT_HAS_SELF_RDF,
        TestFilenames.OBJECT_HAS_VALUE_RDF, TestFilenames.OBJECT_INTERSECTION_OF_RDF,
        TestFilenames.OBJECT_MAX_CARDINALITY_RDF,
        TestFilenames.OBJECT_MAX_QUALIFIED_CARDINALITY_RDF,
        TestFilenames.OBJECT_MIN_CARDINALITY_RDF,
        TestFilenames.OBJECT_MIN_QUALIFIED_CARDINALITY_RDF, TestFilenames.OBJECT_ONE_OF_RDF,
        TestFilenames.OBJECT_QUALIFIED_CARDINALITY_RDF, TestFilenames.OBJECT_SOME_VALUES_FROM_RDF,
        TestFilenames.OBJECT_UNION_OF_RDF, TestFilenames.PRIMER_FUNCTIONALSYNTAX_TXT,
        TestFilenames.PRIMER_OWLXML_XML, TestFilenames.PRIMER_RDFXML_XML,
        TestFilenames.RDFS_CLASS_RDF, TestFilenames.KOALA_OWL, TestFilenames.SUB_CLASS_OF_RDF,
        TestFilenames.TEST_PARSER06_RDF, TestFilenames.TEST_PARSER07_RDF,
        TestFilenames.TEST_PARSER10_RDF, TestFilenames.ANNOTATEDPROPERTYCHAIN_TTL_RDF,
        TestFilenames.UNTYPED_SUB_CLASS_OF_RDF, TestFilenames.SUB_CLASS_OF_UNTYPED_OWL_CLASS_RDF,
        TestFilenames.SUB_CLASS_OF_UNTYPED_SOME_VALUES_FROM_RDF);

    static List<String> fileRoundTripOnly() {
        return FILE_ROUND_TRIP;
    }

    static List<Arguments> fileRoundTrip() {
        List<Arguments> list = new ArrayList<>();
        FILE_ROUND_TRIP.forEach(file -> formats().forEach(format -> list.add(of(file, format))));
        // use a different equal method
        l(TestFilenames.EXTRA_BLANK_NODES_OWL, TestFilenames.TEST_BLANK_NODES2_TTL,
            TestFilenames.TEST_BLANK_NODES_ASSERTIONS_TTL, TestFilenames.OWLXML_ANONLOOP_OWX)
                .forEach(file -> formats().forEach(format -> list.add(of(file, format))));

        return list;
    }

    static List<Arguments> roundTrip() {
        List<Arguments> list = new ArrayList<>();
        literalWithEscapesNoRioRDFXMLTestCase().forEach(axioms -> forEachFormat(list, axioms));
        axiomsRoundTrippingTestCase().forEach(axioms -> forEachFormat(list, axioms));
        axiomsRoundTrippingWithEntitiesTestCase().forEach(axioms -> forEachFormat(list, axioms));
        relativeURITestCase().forEach(axioms -> forEachFormat(list, axioms));
        axiomsRoundTrippingNoManchesterSyntaxTestCase()
            .forEach(axioms -> formatsSkip(ManchesterSyntaxDocumentFormat.class)
                .forEach(format -> list.add(of(axioms, format))));
        return list;
    }

    protected static void forEachFormat(List<Arguments> list, List<OWLAxiom> axioms) {
        formats().forEach(format -> list.add(of(axioms, format)));
    }

    static List<Arguments> fileRoundTripNoManSyntax() {
        List<Arguments> list = new ArrayList<>();
        l(TestFilenames.ANONYMOUS_INVERSES_RDF, TestFilenames.TEST_PARSER08_RDF,
            TestFilenames.NODEID_RDF)
                .forEach(file -> formatsSkip(ManchesterSyntaxDocumentFormat.class)
                    .forEach(format -> list.add(of(file, format))));
        return list;
    }

    @ParameterizedTest
    @MethodSource({"fileRoundTrip", "fileRoundTripNoManSyntax"})
    void fileRoundTripTestCase(String fileName, OWLDocumentFormat format) {
        roundTrip(load(fileName), format);
    }

    @Test
    void fileRoundTripSubClassOfUntypedOWLClassStrictTestCase() throws URISyntaxException {
        config = config.setStrict(true).setReportStackTraces(true);
        FileDocumentSource documentSource = new FileDocumentSource(
            new File(getClass().getResource("/SubClassOfUntypedOWLClass.rdf").toURI()));
        OWLOntology ont = loadFrom(documentSource, config);
        assertEquals(0, ont.axioms(AxiomType.SUBCLASS_OF).count());
        OWLDocumentFormat format = ont.getFormat();
        assertTrue(format instanceof RDFXMLDocumentFormat);
        assertTrue(documentSource.getOntologyLoaderMetaData().isPresent());
        Stream<Triple> triples =
            documentSource.getOntologyLoaderMetaData().get().getUnparsedTriples();
        assertEquals(1, triples.count());
    }

    // ongoing work to use OBO as one of the roundtripping formats
    // @Test
    // void testOBO(OWLOntology o){
    // createOntology.applyChange(new SetOntologyID(o,
    // IRI.create("http://purl.obolibrary.org/obo/test.owl")));
    // StringDocumentTarget saveOntology =
    // saveOntology(o, new FunctionalSyntaxDocumentFormat());
    // String s = saveOntology.toString()
    // //
    // .replace(OWLAPI_TEST,
    // "http://purl.obolibrary.org/obo/test#")
    // //
    // .replace("http://www.semanticweb.org/ontologies/declarations#",
    // "http://purl.obolibrary.org/obo/test#")
    // //
    // .replace("http://www.semanticweb.org/ontologies/test/annotationont#",
    // "http://purl.obolibrary.org/obo/test#");
    // createOntology = loadFrom(s, new FunctionalSyntaxDocumentFormat());
    // createOntology.removeAxioms(asList(createOntology.axioms(AxiomType.CLASS_ASSERTION)
    // .filter(ax -> ax.getClassExpression().isOWLThing())));
    // OBODocumentFormat format = new OBODocumentFormat();
    // StringDocumentTarget target = saveOntology(createOntology, format);
    // OWLOntology o1 = loadFrom(target, format);
    // createOntology.removeAxioms(asList(createOntology.axioms(AxiomType.CLASS_ASSERTION).filter(
    // ax -> ax.getClassExpression().isOWLThing() || ax.getIndividual().isAnonymous())));
    // o1.removeAxioms(asList(o1.axioms(AxiomType.CLASS_ASSERTION).filter(
    // ax -> ax.getClassExpression().isOWLThing() || ax.getIndividual().isAnonymous())));
    // OWLAnnotationProperty version = AnnotationProperty(
    // "http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion");
    // OWLAnnotationProperty id =
    // AnnotationProperty("http://www.geneontology.org/formats/oboInOwl#id");
    // createOntology.remove(asList(createOntology.axioms(AxiomType.ANNOTATION_ASSERTION)));
    // o1.remove(asList(o1.axioms(AxiomType.ANNOTATION_ASSERTION)));
    // o1.applyChanges(asList(o1.annotations().filter(a -> a.getProperty().equals(version))
    // .map(a -> new RemoveOntologyAnnotation(o1, a))));
    // createOntology.remove(asList(createOntology.axioms(AxiomType.DECLARATION)));
    // o1.remove(asList(o1.axioms(AxiomType.DECLARATION)));
    // System.out.println("TestBase.roundTripOntology() ont1 " + createOntology.getOntologyID());
    // createOntology.axioms().forEach(System.out::println);
    // System.out.println("TestBase.roundTripOntology() \n" + target);
    // System.out.println("TestBase.roundTripOntology() ont2 " + o1.getOntologyID());
    // o1.axioms().forEach(System.out::println);
    //
    // equal(createOntology, o1);
    // }

    @ParameterizedTest
    @MethodSource("roundTrip")
    void roundTripFormats(List<OWLAxiom> axioms, OWLDocumentFormat format) {
        roundTripOntology(o(axioms), format);
    }

    @ParameterizedTest
    @MethodSource({"literalWithEscapesNoRioRDFXMLTestCase",
        "axiomsRoundTrippingNoManchesterSyntaxTestCase", "axiomsRoundTrippingTestCase",
        "axiomsRoundTrippingWithEntitiesTestCase", "relativeURITestCase", "xmlAndFunctional"})
    void roundTripRDFXMLAndFunctionalShouldBeSame(List<OWLAxiom> axioms) {
        plainEqual(o(axioms), true);
    }

    @ParameterizedTest
    @MethodSource("fileRoundTripOnly")
    @ValueSource(strings = {TestFilenames.ANONYMOUS_INVERSES_RDF, TestFilenames.TEST_PARSER08_RDF,
        TestFilenames.NODEID_RDF})
    void roundTripRDFXMLAndFunctionalShouldBeSame(String name) {
        plainEqual(load(name), true);
    }

    protected void plainEqual(OWLOntology ont, boolean compareInput) {
        OWLOntology o1 = roundTrip(ont, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(ont, new FunctionalSyntaxDocumentFormat());
        if (compareInput) {
            equal(ont, o1);
        }
        equal(o1, o2);
    }

    protected OWLOntology createOntology(String fileName, OWLDocumentFormat format) {
        OWLOntology o = ontologyFromClasspathFile(fileName, format);
        if (logger.isTraceEnabled()) {
            logger.trace("ontology as parsed from input file:");
            o.axioms().forEach(ax -> logger.trace(ax.toString()));
        }
        return o;
    }

    @ParameterizedTest
    @MethodSource("formats")
    void literalWithEscapesTestCase(OWLDocumentFormat format) {
        if (RioRDFXMLDocumentFormat.class.isInstance(format)) {
            // Rio normalizes literals differently, got its own test
            return;
        }

        List<OWLAxiom> axioms = literalWithEscapes();
        OWLOntology o = createAnon();
        o.add(axioms);
        roundTrip(o, format);
    }

    protected static List<OWLAxiom> literalWithEscapes() {
        List<OWLAxiom> axioms =
            l(ann("\\"), ann("Start\\"), ann("\\End"), ann("Start\\End"), ann("\""), ann("Start\""),
                ann("\"End"), ann("Start\"End"), ann("<"), ann("Start<"), ann("<End"),
                ann("Start<End"), ann("\n"), ann("Start\n"), ann("\nEnd"), ann("Start\nEnd"),
                ann("\'"), ann("Start\'"), ann("\'End"), ann("Start\'End"), Declaration(CLASSES.A));
        return axioms;
    }

    @ParameterizedTest
    @MethodSource("formats")
    void ontologyAnnotationsTestCase(OWLDocumentFormat format) {
        if (RDFJsonDocumentFormat.class.isInstance(format)) {
            // XXX RDFJsonDocumentFormat ignored. The parser parses the annotation correctly but it
            // is not
            // associated to the ontology.
            return;
        }
        roundTrip(ontologyAnnotation(), format);
    }

    protected OWLOntology ontologyAnnotation() {
        OWLOntology ont = createAnon();
        OWLAnnotation annotation = Annotation(ANNPROPS.AP, LITERALS.LIT_33);
        OWLAnnotation builtin = Annotation(VersionInfo(), Literal("x"));
        ont.applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.applyChange(new AddOntologyAnnotation(ont, builtin));
        ont.addAxiom(Declaration(ANNPROPS.AP));
        return ont;
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyAnnotation() {
        plainEqual(ontologyAnnotation(), true);
    }

    @Test
    void testCorrectOntologyIRI() {
        OWLOntology ont = loadFrom(TestFiles.ontologyIRI, new RDFXMLDocumentFormat());
        OWLOntologyID ontid = ont.getOntologyID();
        assertEquals("http://www.test.com/right.owl", ontid.getOntologyIRI().get().toString());
    }

    @ParameterizedTest
    @MethodSource("formats")
    void ontologyIRITestCase(OWLDocumentFormat format) {
        roundTrip(loadFrom(TestFiles.ontologyIRI, new RDFXMLDocumentFormat()), format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyIRI() {
        plainEqual(loadFrom(TestFiles.ontologyIRI, new RDFXMLDocumentFormat()), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void ontologyVersionIRITestCase(OWLDocumentFormat format) {
        roundTrip(ontologyVersion(), format);
    }

    protected OWLOntology ontologyVersion() {
        IRI ontIRI = iri("http://www.semanticweb.org/owlapi/", "ontology");
        IRI versionIRI = iri("http://www.semanticweb.org/owlapi/ontology/", "version");
        OWLOntologyID ontologyID = OntologyID(ontIRI, versionIRI);
        return create(ontologyID);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyVersion() {
        plainEqual(ontologyVersion(), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void threeLayersOfAnnotationsTestCase(OWLDocumentFormat format) {
        if (ManchesterSyntaxDocumentFormat.class.isInstance(format)) {
            // not supported in Manchester syntax
            return;
        }
        roundTrip(threeLayersOfAnnotations(), format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSamethreeLayersAnnotations() {
        plainEqual(threeLayersOfAnnotations(), true);
    }

    protected OWLOntology threeLayersOfAnnotations() {
        OWLOntology o = create(iri("urn:nested:", "ontology"));
        OWLAnonymousIndividual ind1 = AnonymousIndividual();
        o.add(ClassAssertion(CLASSES.dbxref, ind1));
        o.add(DataPropertyAssertion(DATAPROPS.hasuri, ind1,
            Literal("urn:SO:SO_ke", OWL2Datatype.XSD_ANY_URI)));
        OWLAnonymousIndividual ind2 = AnonymousIndividual();
        o.add(ClassAssertion(CLASSES.definition, ind2));
        o.add(AnnotationAssertion(ANNPROPS.hasdbxref, ind2, ind1));
        o.add(AnnotationAssertion(ANNPROPS.hasDefinition, OBJPROPS.adjacent_to.getIRI(), ind2));
        return o;
    }

    @ParameterizedTest
    @MethodSource("formats")
    void roundTripOWLXMLToRioTurtleTestCase(OWLDocumentFormat format) {
        roundTrip(loadFrom(TestFiles.original, new OWLXMLDocumentFormat()), format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOWLXMLToTurtle() {
        plainEqual(loadFrom(TestFiles.original, new OWLXMLDocumentFormat()), true);
    }

    @Test
    void shouldRoundTripThroughOWLXML() {
        OWLOntology ontology = original();
        StringDocumentTarget targetOWLXML = saveOntology(ontology, new OWLXMLDocumentFormat());
        OWLOntology o1 = loadFrom(targetOWLXML, new OWLXMLDocumentFormat());
        equal(ontology, o1);
    }

    protected OWLOntology original() {
        return loadFrom(TestFiles.original, new OWLXMLDocumentFormat());
    }

    @Test
    void shouldRoundTripThroughOWLXMLOrTurtle() {
        OWLOntology ontology = original();
        OWLOntology o1 = roundTrip(ontology, new RioTurtleDocumentFormat());
        equal(ontology, o1);
        OWLOntology o2 = roundTrip(o1, new OWLXMLDocumentFormat());
        equal(o2, o1);
    }

    @Test
    void shouldRoundTripThroughOWLXMLToTurtle() {
        OWLOntology ontology = original();
        StringDocumentTarget targetTTL = saveOntology(ontology, new TurtleDocumentFormat());
        StringDocumentTarget targetTTLFromTTL = saveOntology(ontology, new TurtleDocumentFormat());
        assertEquals(targetTTL.toString(), targetTTLFromTTL.toString());
    }

    @Test
    void shouldRoundTripThroughOWLXMLToRioTurtle() {
        OWLOntology ontology = original();
        StringDocumentTarget target1 = saveOntology(ontology, new RioTurtleDocumentFormat());
        StringDocumentTarget target2 = saveOntology(ontology, new RioTurtleDocumentFormat());
        assertEquals(target1.toString().replaceAll("_:genid[0-9]+", "_:genid"),
            target2.toString().replaceAll("_:genid[0-9]+", "_:genid"));
    }

    @Override
    protected OWLOntology o(OWLAxiom a) {
        OWLOntology ont = create();
        ont.add(a);
        ont.unsortedSignature()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, INCLUDED))
            .forEach(entity -> ont.add(Declaration(entity)));
        return ont;
    }

    @Override
    protected OWLOntology o(Collection<OWLAxiom> a) {
        OWLOntology ont = create();
        ont.add(a);
        ont.unsortedSignature()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, INCLUDED))
            .forEach(entity -> ont.add(Declaration(entity)));
        return ont;
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingNoManchesterSyntaxTestCase() {
        // no valid Manchester OWL Syntax roundtrip
        OWLObjectPropertyExpression p = OBJPROPS.P.getInverseProperty();
        OWLObjectPropertyExpression q = OBJPROPS.Q.getInverseProperty();
        return l(l(AsymmetricObjectProperty(p)), l(EquivalentObjectProperties(p, q)),
            l(FunctionalObjectProperty(p)), l(InverseFunctionalObjectProperty(p)),
            l(IrreflexiveObjectProperty(p)), l(ObjectPropertyDomain(p, CLASSES.A)),
            l(ObjectPropertyRange(p, CLASSES.A)), l(ReflexiveObjectProperty(p)),
            l(SubObjectPropertyOf(p, q)), l(SymmetricObjectProperty(p)),
            l(TransitiveObjectProperty(p)));
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingTestCase() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        OWLAnnotation ann1 = RDFSLabel("Annotation 1");
        OWLAnnotation ann2 = RDFSLabel("Annotation 2");
        OWLAnnotation eAnn1 = RDFSLabel("EntityAnnotation 1");
        OWLAnnotation eAnn2 = RDFSLabel("EntityAnnotation 2");
        OWLAnnotation annoOuterOuter1 = Annotation(AnnotationProperty(iri("myOuterOuterLabel1")),
            Literal("Outer Outer label 1"));
        OWLAnnotation annoOuterOuter2 = Annotation(AnnotationProperty(iri("myOuterOuterLabel2")),
            Literal("Outer Outer label 2"));
        OWLFacetRestriction fr =
            FacetRestriction(OWLFacet.PATTERN, Literal("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
        OWLDataRange dr =
            DatatypeRestriction(Datatype(iri("http://www.w3.org/2001/XMLSchema#", "string")), fr);
        OWLDataIntersectionOf disj1 = DataIntersectionOf(DataComplementOf(dr), DATATYPES.ssnDT);
        OWLDataIntersectionOf disj2 = DataIntersectionOf(DataComplementOf(DATATYPES.ssnDT), dr);
        OWLAnnotation annoOuter = Annotation(AnnotationProperty(iri("myOuterLabel")),
            Literal("Outer label"), annoOuterOuter1, annoOuterOuter2);
        OWLAnnotation annoInner =
            Annotation(AnnotationProperty(iri("myLabel")), Literal("Label"), annoOuter);

        List<List<OWLAxiom>> list = new ArrayList<>();
        list.add(swrlRuleAlternateNS(HTTP_WWW_OWLAPI, DATAPROPS.DP, OBJPROPS.op1, INDIVIDUALS.I,
            INDIVIDUALS.J));
        list.add(swrlRule());
        list.add(l(SubPropertyChainOf(l(OBJPROPS.t, OBJPROPS.u, OBJPROPS.w), OBJPROPS.z)));
        list.add(l(AsymmetricObjectProperty(OBJPROPS.op1)));
        list.add(l(DifferentIndividuals(createIndividual(), createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual())));
        list.add(l(
            SubClassOf(CLASSES.A,
                ObjectSomeValuesFrom(OBJPROPS.op1, ObjectSomeValuesFrom(OBJPROPS.op1, CLASSES.B))),
            Declaration(CLASSES.A), Declaration(CLASSES.B)));
        list.add(l(Declaration(RDFSLabel()), Declaration(INDIVIDUALS.peter), AnnotationAssertion(
            RDFSLabel(), INDIVIDUALS.peter.getIRI(), Literal("X", "en"), ann1, ann2)));
        list.add(l(Declaration(RDFSLabel()), Declaration(INDIVIDUALS.peter, eAnn1, eAnn2),
            AnnotationAssertion(RDFSLabel(), INDIVIDUALS.peter.getIRI(), Literal("X", "en"), ann1,
                ann2)));
        list.add(l(InverseObjectProperties(OBJPROPS.op2, OBJPROPS.op1)));
        list.add(l(InverseObjectProperties(OBJPROPS.op1, OBJPROPS.op2)));
        list.add(l(Declaration(CLASSES.A), AnnotationAssertion(ANNPROPS.propP, CLASSES.A.getIRI(),
            iri("http://www.semanticweb.org/owlapi#", "object"))));
        list.add(l(SubClassOf(annoInner, CLASSES.A, CLASSES.B)));
        list.add(l(AnnotationPropertyDomain(RDFSComment(), CLASSES.A.getIRI())));
        list.add(l(AnnotationPropertyRange(RDFSComment(), CLASSES.A.getIRI())));
        list.add(l(SubAnnotationPropertyOf(ANNPROPS.propP, RDFSLabel())));
        list.add(l(SubClassOf(CLASSES.A, DataMaxCardinality(3, DATAPROPS.DP, Integer()))));
        list.add(l(SubClassOf(CLASSES.A, DataMinCardinality(3, DATAPROPS.DP, Integer()))));
        list.add(l(SubClassOf(CLASSES.A, DataExactCardinality(3, DATAPROPS.DP, Integer()))));
        list.add(l(DataPropertyRange(DATAPROPS.DP, DataUnionOf(disj1, disj2))));
        list.add(l(
            HasKey(Annotation(ANNPROPS.propP, LITERALS.val), CLASSES.A, OBJPROPS.t, OBJPROPS.u,
                OBJPROPS.w),
            Declaration(ANNPROPS.propP), Declaration(OBJPROPS.t), Declaration(OBJPROPS.u),
            Declaration(OBJPROPS.w)));
        list.add(
            l(DisjointClasses(asUnorderedSet(Stream.generate(() -> createClass()).limit(6000)))));
        list.add(l(SubClassOf(CLASSES.B,
            ObjectSomeValuesFrom(OBJPROPS.op1.getInverseProperty(), CLASSES.A))));
        list.add(l(SubDataPropertyOf(DATAPROPS.DP, DATAPROPS.DQ)));
        list.add(l(DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal(33.3))));
        list.add(l(NegativeDataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal(33.3)),
            NegativeDataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal("weasel", "")),
            NegativeDataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal("weasel"))));
        list.add(l(FunctionalDataProperty(DATAPROPS.DP)));
        list.add(l(DataPropertyDomain(DATAPROPS.DP, CLASSES.A)));
        list.add(l(DataPropertyRange(DATAPROPS.DP, TopDatatype())));
        list.add(l(DisjointDataProperties(DATAPROPS.dp1, DATAPROPS.dp2, DATAPROPS.dp3),
            Declaration(DATAPROPS.dp1), Declaration(DATAPROPS.dp2), Declaration(DATAPROPS.dp3)));
        list.add(l(DisjointDataProperties(DATAPROPS.dp1, DATAPROPS.dp2)));
        list.add(l(EquivalentDataProperties(DATAPROPS.DP, DATAPROPS.DQ)));
        list.add(l(AsymmetricObjectProperty(OBJPROPS.op1)));
        list.add(l(DatatypeDefinition(DATATYPES.datatype, DataComplementOf(Integer())),
            Declaration(DATATYPES.datatype)));
        list.add(l(DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.J),
            DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.k)));
        list.add(
            l(DifferentIndividuals(INDIVIDUALS.I, INDIVIDUALS.J, INDIVIDUALS.k, INDIVIDUALS.l)));
        list.add(l(DisjointObjectProperties(OBJPROPS.t, OBJPROPS.u, OBJPROPS.w),
            Declaration(OBJPROPS.t), Declaration(OBJPROPS.u), Declaration(OBJPROPS.w)));
        list.add(l(DisjointObjectProperties(OBJPROPS.t, OBJPROPS.u)));
        list.add(l(EquivalentObjectProperties(OBJPROPS.t, OBJPROPS.u), Declaration(OBJPROPS.t),
            Declaration(OBJPROPS.u)));
        list.add(l(FunctionalObjectProperty(OBJPROPS.op1)));
        list.add(l(InverseFunctionalObjectProperty(OBJPROPS.op1)));
        list.add(l(IrreflexiveObjectProperty(OBJPROPS.op1)));
        list.add(l(DifferentIndividuals(
            asUnorderedSet(Stream.generate(() -> createIndividual()).limit(1000)))));
        list.add(l(AnnotationAssertion(ANNPROPS.propP, CLASSES.A.getIRI(), Literal("abc", "en")),
            Declaration(CLASSES.A)));
        list.add(l(AnnotationAssertion(ANNPROPS.propP, CLASSES.A.getIRI(), Literal("abc", "en")),
            AnnotationAssertion(ANNPROPS.propP, CLASSES.A.getIRI(), Literal("abcd", "")),
            AnnotationAssertion(ANNPROPS.propP, CLASSES.A.getIRI(), Literal("abcde")),
            AnnotationAssertion(ANNPROPS.propP, CLASSES.A.getIRI(),
                Literal("abcdef", OWL2Datatype.XSD_STRING)),
            Declaration(CLASSES.A)));
        list.add(l(NegativeObjectPropertyAssertion(OBJPROPS.op1, INDIVIDUALS.I, INDIVIDUALS.J)));
        list.add(l(ObjectPropertyAssertion(OBJPROPS.op1, INDIVIDUALS.I, INDIVIDUALS.J)));
        list.add(l(SubPropertyChainOf(
            l(Annotation(ANNPROPS.propP, Literal("Test", "en")),
                Annotation(ANNPROPS.propQ, LITERALS.val)),
            l(OBJPROPS.t, OBJPROPS.u, OBJPROPS.w), OBJPROPS.z)));
        list.add(l(ObjectPropertyDomain(OBJPROPS.op1, CLASSES.A)));
        list.add(l(ObjectPropertyRange(OBJPROPS.op1, CLASSES.A)));
        list.add(l(Declaration(Class(iri("http://www.test.com/ontology#", "Class%37A"))),
            Declaration(ObjectProperty(iri("http://www.test.com/ontology#", "prop%37A")))));
        list.add(l(ReflexiveObjectProperty(OBJPROPS.op1)));
        list.add(l(SameIndividual(INDIVIDUALS.I, INDIVIDUALS.J)));
        list.add(l(DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I,
            Literal("Test \"literal\"\nStuff"))));
        list.add(l(DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal("Test \"literal\"")),
            DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal("Test 'literal'")),
            DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I,
                Literal("Test \"\"\"literal\"\"\""))));
        list.add(l(SubObjectPropertyOf(OBJPROPS.op1, OBJPROPS.op2)));
        list.add(l(SymmetricObjectProperty(OBJPROPS.op1)));
        list.add(l(TransitiveObjectProperty(OBJPROPS.op1)));
        list.add(l(DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, LITERALS.LIT_THREE),
            DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal(33.3)),
            DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, LITERALS.LIT_TRUE),
            DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal(33.3f)),
            DataPropertyAssertion(DATAPROPS.DP, INDIVIDUALS.I, Literal("33.3"))));
        return list;
    }

    protected static List<OWLAxiom> swrlRule() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        String URN_SWRL_VAR = "urn:swrl:var#";
        SWRLVariable varX = SWRLVariable(URN_SWRL_VAR, "x");
        SWRLVariable varY = SWRLVariable(URN_SWRL_VAR, "y");
        SWRLVariable varZ = SWRLVariable(URN_SWRL_VAR, "z");
        Set<SWRLAtom> body = new HashSet<>();
        body.add(SWRLClassAtom(CLASSES.A, varX));
        SWRLIndividualArgument indIArg = SWRLIndividualArgument(INDIVIDUALS.I);
        SWRLIndividualArgument indJArg = SWRLIndividualArgument(INDIVIDUALS.J);
        body.add(SWRLClassAtom(CLASSES.D, indIArg));
        body.add(SWRLClassAtom(CLASSES.B, varX));
        SWRLVariable varQ = SWRLVariable(URN_SWRL_VAR, "q");
        SWRLVariable varR = SWRLVariable(URN_SWRL_VAR, "r");
        body.add(SWRLDataPropertyAtom(DATAPROPS.DP, varX, varQ));
        SWRLLiteralArgument litArg = SWRLLiteralArgument(LITERALS.LIT_33);
        body.add(SWRLDataPropertyAtom(DATAPROPS.DP, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(SWRLClassAtom(CLASSES.C, varX));
        head.add(SWRLObjectPropertyAtom(OBJPROPS.op1, varY, varZ));
        head.add(SWRLSameIndividualAtom(varX, varY));
        head.add(SWRLSameIndividualAtom(indIArg, indJArg));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(OBJPROPS.op1, CLASSES.A);
        head.add(SWRLClassAtom(svf, varX));
        head.add(SWRLBuiltInAtom(iri(HTTP_WWW_OWLAPI, "myBuiltIn"), varQ, varR, litArg));
        return l(SWRLRule(body, head));
    }

    protected static List<OWLAxiom> swrlRuleAlternateNS(String HTTP_WWW_OWLAPI, OWLDataProperty dp,
        OWLObjectProperty op, OWLNamedIndividual ind, OWLNamedIndividual indj) {
        SWRLVariable varX = SWRLVariable(HTTP_WWW_OWLAPI, "x");
        SWRLVariable varY = SWRLVariable(HTTP_WWW_OWLAPI, "y");
        SWRLVariable varZ = SWRLVariable(HTTP_WWW_OWLAPI, "z");
        Set<SWRLAtom> body = new HashSet<>();
        body.add(SWRLClassAtom(CLASSES.A, varX));
        SWRLIndividualArgument indIArg = SWRLIndividualArgument(ind);
        SWRLIndividualArgument indJArg = SWRLIndividualArgument(indj);
        body.add(SWRLClassAtom(CLASSES.D, indIArg));
        body.add(SWRLClassAtom(CLASSES.B, varX));
        SWRLVariable varQ = SWRLVariable(HTTP_WWW_OWLAPI, "q");
        SWRLVariable varR = SWRLVariable(HTTP_WWW_OWLAPI, "r");
        body.add(SWRLDataPropertyAtom(dp, varX, varQ));
        SWRLLiteralArgument litArg = SWRLLiteralArgument(LITERALS.LIT_33);
        body.add(SWRLDataPropertyAtom(dp, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(SWRLClassAtom(CLASSES.C, varX));
        head.add(SWRLObjectPropertyAtom(op, varY, varZ));
        head.add(SWRLSameIndividualAtom(varX, varY));
        head.add(SWRLSameIndividualAtom(indIArg, indJArg));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, CLASSES.A);
        head.add(SWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = new ArrayList<>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(SWRLBuiltInAtom(iri(HTTP_WWW_OWLAPI, "myBuiltIn"), args));
        return l(SWRLRule(body, head));
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingWithEntitiesTestCase() {
        return l(l(Declaration(CLASSES.A),
            AnnotationAssertion(ANNPROPS.propP, CLASSES.A.getIRI(), Literal("value1")),
            AnnotationAssertion(ANNPROPS.propQ, CLASSES.A.getIRI(), Literal("value2"))));
    }

    static List<List<OWLAxiom>> relativeURITestCase() {
        return l(l(Declaration(Class(iri(NextIRI(uriBase) + "/", "Office")))));
    }

    @Test
    void shouldThrowMeaningfulException() {
        OWLOntology ontology = createAnon();
        RDFXMLParser parser = new RDFXMLParser();
        // on Java 6 for Mac the following assertion does not work: the root
        // exception does not have a message.
        // expectedException
        // .expectMessage(" reason is: Illegal character in fragment at index
        // 21: http://example.com/#1#2");
        assertThrowsWithMessage(
            "[line=1:column=375] IRI 'http://example.com/#1#2' cannot be resolved against current base IRI ",
            OWLRDFXMLParserException.class,
            () -> new StringDocumentSource(TestFiles.rdfContentForException,
                new RDFXMLDocumentFormat()).acceptParser(parser, ontology, config));
    }
}
