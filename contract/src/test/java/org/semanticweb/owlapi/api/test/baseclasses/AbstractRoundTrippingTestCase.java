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
package org.semanticweb.owlapi.api.test.baseclasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.semanticweb.owlapi.apitest.TestFilenames;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFXMLParserException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
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
            Declaration(A)));
    }

    protected static OWLAnnotationAssertionAxiom ann(String al) {
        return AnnotationAssertion(AP, A.getIRI(), Literal(al));
    }

    @ParameterizedTest
    @MethodSource("formats")
    void anonymousRoundTripTestCase(OWLDocumentFormat format) {
        OWLOntology ont1 = createAnon();
        ont1.add(anonymousRoundTrip());
        roundTrip(ont1, format);
    }

    static List<OWLAxiom> anonymousRoundTrip() {
        OWLAnnotation an1 = Annotation(RDFSComment(Literal("z1")), AP, AnonymousIndividual("_:b0"));
        OWLClassExpression c1 = DataAllValuesFrom(DP, Boolean());
        OWLClassExpression c2 = ObjectSomeValuesFrom(op1, OWLThing());

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
        OWLAnnotation annotation = Annotation(AP, Literal(33));
        ont.applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.add(Declaration(AP));
        return ont;
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameBlankNodesTurtleDomain() {
        plainEqual(blankNodesTurtleDomain(), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void fileRoudTripWithKnownInputFormatTestCase(OWLDocumentFormat format) {
        roundTrip(blankNodesTurtleDomain(), format);
    }

    protected OWLOntology blankNodesTurtleDomain() {
        try {
            URL resource = getClass().getResource('/' + TestFilenames.TEST_BLANK_NODES_DOMAIN_TTL);
            IRI iri = IRI.create(resource.toURI());
            return loadFrom(new IRIDocumentSource(iri, new TurtleDocumentFormat(), null));
        } catch (URISyntaxException ex) {
            throw new OWLRuntimeException(ex);
        }
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
        return l(ObjectPropertyAssertion(P, AnonymousIndividual(), AnonymousIndividual()),
            Declaration(P));
    }

    private static List<OWLAxiom> differentIndividualsPairwiseAnonymous() {
        return l(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual()));
    }

    private static List<OWLAxiom> differentIndividualsAnonymous() {
        return l(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual(),
            AnonymousIndividual()));
    }

    private static List<OWLAxiom> classAssertionWithAnonymousIndividual() {
        return l(ClassAssertion(A, AnonymousIndividual("a")), Declaration(A));
    }

    private static List<OWLAxiom> chainedAnonymousIndividuals() {
        OWLAnonymousIndividual ind1 = AnonymousIndividual();
        OWLAnonymousIndividual ind2 = AnonymousIndividual();
        OWLAnonymousIndividual ind3 = AnonymousIndividual();
        OWLAnnotationAssertionAxiom assertion1 = AnnotationAssertion(AP, subject.getIRI(), ind1);
        OWLAnnotationAssertionAxiom assertion2 = AnnotationAssertion(AP, ind1, ind2);
        OWLAnnotationAssertionAxiom assertion3 = AnnotationAssertion(AP, ind2, ind3);
        return l(Declaration(NamedIndividual(subject.getIRI())), assertion1, assertion2,
            assertion3);
    }

    private static List<OWLAxiom> anonymousIndividuals() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        return l(ObjectPropertyAssertion(P, I, ind), ObjectPropertyAssertion(P, ind, J));
    }

    private static List<OWLAxiom> anonymousIndividuals2() {
        // Originally submitted by Timothy Redmond
        OWLAnonymousIndividual hAnon = AnonymousIndividual();
        return l(AnnotationAssertion(AP, A.getIRI(), hAnon), ClassAssertion(A, hAnon),
            ObjectPropertyAssertion(Q, hAnon, AnonymousIndividual()),
            AnnotationAssertion(RDFSLabel(), hAnon, Literal("Second", "en")));
    }

    private static List<OWLAxiom> anonymousIndividualRoundtrip() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(AP, A.getIRI(), ind);
        OWLAnonymousIndividual anon1 = AnonymousIndividual();
        OWLAnonymousIndividual anon2 = AnonymousIndividual();
        return l(ax, Declaration(A), Declaration(P), ObjectPropertyAssertion(P, J, I),
            ObjectPropertyAssertion(P, anon1, anon1), ObjectPropertyAssertion(P, anon2, I),
            ObjectPropertyAssertion(P, I, anon2));
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
        FILE_ROUND_TRIP
            .forEach(file -> formats().forEach(format -> list.add(Arguments.of(file, format))));
        // use a different equal method
        Arrays
            .asList(TestFilenames.EXTRA_BLANK_NODES_OWL, TestFilenames.TEST_BLANK_NODES2_TTL,
                TestFilenames.TEST_BLANK_NODES_ASSERTIONS_TTL, TestFilenames.OWLXML_ANONLOOP_OWX)
            .forEach(file -> formats().forEach(format -> list.add(Arguments.of(file, format))));

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
                .forEach(format -> list.add(Arguments.of(axioms, format))));
        return list;
    }

    protected static void forEachFormat(List<Arguments> list, List<OWLAxiom> axioms) {
        formats().forEach(format -> list.add(Arguments.of(axioms, format)));
    }

    static List<Arguments> fileRoundTripNoManSyntax() {
        List<Arguments> list = new ArrayList<>();
        l(TestFilenames.ANONYMOUS_INVERSES_RDF, TestFilenames.TEST_PARSER08_RDF,
            TestFilenames.NODEID_RDF)
                .forEach(file -> formatsSkip(ManchesterSyntaxDocumentFormat.class)
                    .forEach(format -> list.add(Arguments.of(file, format))));
        return list;
    }

    @ParameterizedTest
    @MethodSource({"fileRoundTrip", "fileRoundTripNoManSyntax"})
    void fileRoundTripTestCase(String fileName, OWLDocumentFormat format) {
        roundTrip(load(fileName), format);
    }

    @Test
    void fileRoundTripSubClassOfUntypedOWLClassStrictTestCase() {
        config = config.setStrict(true);
        URL url = getClass().getResource("/" + TestFilenames.SUB_CLASS_OF_UNTYPED_OWL_CLASS_RDF);
        OWLOntology ont = loadFrom(new IRIDocumentSource(IRI.create(url), null, null),
            config.setReportStackTraces(true));
        assertEquals(0, ont.axioms(AxiomType.SUBCLASS_OF).count());
        OWLDocumentFormat format = ont.getFormat();
        assertTrue(format instanceof RDFXMLDocumentFormat);
        RDFXMLDocumentFormat rdfxmlFormat = (RDFXMLDocumentFormat) format;
        assertTrue(rdfxmlFormat.getOntologyLoaderMetaData().isPresent());
        Stream<RDFTriple> triples =
            rdfxmlFormat.getOntologyLoaderMetaData().get().getUnparsedTriples();
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
    // .replace("http://www.semanticweb.org/owlapi/test#",
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
        OWLClass cls = A;
        List<OWLAxiom> axioms =
            l(ann("\\"), ann("Start\\"), ann("\\End"), ann("Start\\End"), ann("\""), ann("Start\""),
                ann("\"End"), ann("Start\"End"), ann("<"), ann("Start<"), ann("<End"),
                ann("Start<End"), ann("\n"), ann("Start\n"), ann("\nEnd"), ann("Start\nEnd"),
                ann("\'"), ann("Start\'"), ann("\'End"), ann("Start\'End"), Declaration(cls));
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
        OWLLiteral value = Literal(33);
        OWLAnnotation annotation = Annotation(AP, value);
        OWLAnnotation builtin = Annotation(VersionInfo(), Literal("x"));
        ont.applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.applyChange(new AddOntologyAnnotation(ont, builtin));
        ont.addAxiom(Declaration(AP));
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
        OWLOntologyID ontologyID = new OWLOntologyID(optional(ontIRI), optional(versionIRI));
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
        OWLClass dbxref = Class(iri("urn:obo:", "DbXref"));
        OWLClass definition = Class(iri("urn:obo:", "Definition"));
        OWLObjectProperty adjacent_to = ObjectProperty(iri("urn:obo:", "adjacent_to"));
        OWLAnnotationProperty hasDefinition = AnnotationProperty(iri("urn:obo:", "hasDefinition"));
        OWLAnnotationProperty hasdbxref = AnnotationProperty(iri("urn:obo:", "hasDbXref"));
        OWLDataProperty hasuri = DataProperty(iri("urn:obo:", "hasURI"));
        OWLAnonymousIndividual ind1 = AnonymousIndividual();
        o.add(ClassAssertion(dbxref, ind1));
        o.add(
            DataPropertyAssertion(hasuri, ind1, Literal("urn:SO:SO_ke", OWL2Datatype.XSD_ANY_URI)));
        OWLAnonymousIndividual ind2 = AnonymousIndividual();
        o.add(ClassAssertion(definition, ind2));
        o.add(AnnotationAssertion(hasdbxref, ind2, ind1));
        o.add(AnnotationAssertion(hasDefinition, adjacent_to.getIRI(), ind2));
        return o;
    }

    //@formatter:off


    //@formatter:on
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
        OWLObjectPropertyExpression p = P.getInverseProperty();
        OWLObjectPropertyExpression q = Q.getInverseProperty();
        return l(l(AsymmetricObjectProperty(p)), l(EquivalentObjectProperties(p, q)),
            l(FunctionalObjectProperty(p)), l(InverseFunctionalObjectProperty(p)),
            l(IrreflexiveObjectProperty(p)), l(ObjectPropertyDomain(p, A)),
            l(ObjectPropertyRange(p, A)), l(ReflexiveObjectProperty(p)),
            l(SubObjectPropertyOf(p, q)), l(SymmetricObjectProperty(p)),
            l(TransitiveObjectProperty(p)));
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingTestCase() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        OWLEntity peter = NamedIndividual(iri("http://www.another.com/ont#", "peter"));
        OWLAnnotation ann1 = RDFSLabel("Annotation 1");
        OWLAnnotation ann2 = RDFSLabel("Annotation 2");
        OWLAnnotation eAnn1 = RDFSLabel("EntityAnnotation 1");
        OWLAnnotation eAnn2 = RDFSLabel("EntityAnnotation 2");
        OWLDatatype datatype = Datatype(iri("http://www.ont.com/myont/", "mydatatype"));
        OWLAnnotation annoOuterOuter1 = Annotation(AnnotationProperty(iri("myOuterOuterLabel1")),
            Literal("Outer Outer label 1"));
        OWLAnnotation annoOuterOuter2 = Annotation(AnnotationProperty(iri("myOuterOuterLabel2")),
            Literal("Outer Outer label 2"));
        OWLDatatype ssnDT = Datatype(iri("file:/c/test.owlapi#", "SSN"));
        OWLFacetRestriction fr =
            FacetRestriction(OWLFacet.PATTERN, Literal("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
        OWLDataRange dr =
            DatatypeRestriction(Datatype(iri("http://www.w3.org/2001/XMLSchema#", "string")), fr);
        OWLDataIntersectionOf disj1 = DataIntersectionOf(DataComplementOf(dr), ssnDT);
        OWLDataIntersectionOf disj2 = DataIntersectionOf(DataComplementOf(ssnDT), dr);
        OWLAnnotation annoOuter = Annotation(AnnotationProperty(iri("myOuterLabel")),
            Literal("Outer label"), annoOuterOuter1, annoOuterOuter2);
        OWLAnnotation annoInner =
            Annotation(AnnotationProperty(iri("myLabel")), Literal("Label"), annoOuter);

        List<List<OWLAxiom>> list = new ArrayList<>();
        list.add(swrlRuleAlternateNS(HTTP_WWW_OWLAPI, DP, op1, I, J));
        list.add(swrlRule());
        list.add(l(SubPropertyChainOf(l(t, u, w), z)));
        list.add(l(AsymmetricObjectProperty(op1)));
        list.add(l(DifferentIndividuals(createIndividual(), createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual())));
        list.add(l(SubClassOf(A, ObjectSomeValuesFrom(op1, ObjectSomeValuesFrom(op1, B))),
            Declaration(A), Declaration(B)));
        list.add(l(Declaration(RDFSLabel()), Declaration(peter),
            AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)));
        list.add(l(Declaration(RDFSLabel()), Declaration(peter, eAnn1, eAnn2),
            AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)));
        list.add(l(InverseObjectProperties(op2, op1)));
        list.add(l(InverseObjectProperties(op1, op2)));
        list.add(l(Declaration(A), AnnotationAssertion(propP, A.getIRI(),
            iri("http://www.semanticweb.org/owlapi#", "object"))));
        list.add(l(SubClassOf(annoInner, A, B)));
        list.add(l(AnnotationPropertyDomain(RDFSComment(), A.getIRI())));
        list.add(l(AnnotationPropertyRange(RDFSComment(), A.getIRI())));
        list.add(l(SubAnnotationPropertyOf(propP, RDFSLabel())));
        list.add(l(SubClassOf(A, DataMaxCardinality(3, DP, Integer()))));
        list.add(l(SubClassOf(A, DataMinCardinality(3, DP, Integer()))));
        list.add(l(SubClassOf(A, DataExactCardinality(3, DP, Integer()))));
        list.add(l(DataPropertyRange(DP, DataUnionOf(disj1, disj2))));
        list.add(l(HasKey(Annotation(propP, val), A, t, u, w), Declaration(propP), Declaration(t),
            Declaration(u), Declaration(w)));
        list.add(
            l(DisjointClasses(asUnorderedSet(Stream.generate(() -> createClass()).limit(6000)))));
        list.add(l(SubClassOf(B, ObjectSomeValuesFrom(op1.getInverseProperty(), A))));
        list.add(l(SubDataPropertyOf(DP, DQ)));
        list.add(l(DataPropertyAssertion(DP, I, Literal(33.3))));
        list.add(l(NegativeDataPropertyAssertion(DP, I, Literal(33.3)),
            NegativeDataPropertyAssertion(DP, I, Literal("weasel", "")),
            NegativeDataPropertyAssertion(DP, I, Literal("weasel"))));
        list.add(l(FunctionalDataProperty(DP)));
        list.add(l(DataPropertyDomain(DP, A)));
        list.add(l(DataPropertyRange(DP, TopDatatype())));
        list.add(l(DisjointDataProperties(dp1, dp2, dp3), Declaration(dp1), Declaration(dp2),
            Declaration(dp3)));
        list.add(l(DisjointDataProperties(dp1, dp2)));
        list.add(l(EquivalentDataProperties(DP, DQ)));
        list.add(l(AsymmetricObjectProperty(op1)));
        list.add(
            l(DatatypeDefinition(datatype, DataComplementOf(Integer())), Declaration(datatype)));
        list.add(l(DifferentIndividuals(I, J), DifferentIndividuals(I, k)));
        list.add(l(DifferentIndividuals(I, J, k, l)));
        list.add(
            l(DisjointObjectProperties(t, u, w), Declaration(t), Declaration(u), Declaration(w)));
        list.add(l(DisjointObjectProperties(t, u)));
        list.add(l(EquivalentObjectProperties(t, u), Declaration(t), Declaration(u)));
        list.add(l(FunctionalObjectProperty(op1)));
        list.add(l(InverseFunctionalObjectProperty(op1)));
        list.add(l(IrreflexiveObjectProperty(op1)));
        list.add(l(DifferentIndividuals(
            asUnorderedSet(Stream.generate(() -> createIndividual()).limit(1000)))));
        list.add(l(AnnotationAssertion(propP, A.getIRI(), Literal("abc", "en")), Declaration(A)));
        list.add(l(AnnotationAssertion(propP, A.getIRI(), Literal("abc", "en")),
            AnnotationAssertion(propP, A.getIRI(), Literal("abcd", "")),
            AnnotationAssertion(propP, A.getIRI(), Literal("abcde")),
            AnnotationAssertion(propP, A.getIRI(), Literal("abcdef", OWL2Datatype.XSD_STRING)),
            Declaration(A)));
        list.add(l(NegativeObjectPropertyAssertion(op1, I, J)));
        list.add(l(ObjectPropertyAssertion(op1, I, J)));
        list.add(l(SubPropertyChainOf(
            l(Annotation(propP, Literal("Test", "en")), Annotation(propQ, val)), l(t, u, w), z)));
        list.add(l(ObjectPropertyDomain(op1, A)));
        list.add(l(ObjectPropertyRange(op1, A)));
        list.add(l(Declaration(Class(iri("http://www.test.com/ontology#", "Class%37A"))),
            Declaration(ObjectProperty(iri("http://www.test.com/ontology#", "prop%37A")))));
        list.add(l(ReflexiveObjectProperty(op1)));
        list.add(l(SameIndividual(I, J)));
        list.add(l(DataPropertyAssertion(DP, I, Literal("Test \"literal\"\nStuff"))));
        list.add(l(DataPropertyAssertion(DP, I, Literal("Test \"literal\"")),
            DataPropertyAssertion(DP, I, Literal("Test 'literal'")),
            DataPropertyAssertion(DP, I, Literal("Test \"\"\"literal\"\"\""))));
        list.add(l(SubObjectPropertyOf(op1, op2)));
        list.add(l(SymmetricObjectProperty(op1)));
        list.add(l(TransitiveObjectProperty(op1)));
        list.add(l(DataPropertyAssertion(DP, I, LIT_THREE),
            DataPropertyAssertion(DP, I, Literal(33.3)), DataPropertyAssertion(DP, I, LIT_TRUE),
            DataPropertyAssertion(DP, I, Literal(33.3f)),
            DataPropertyAssertion(DP, I, Literal("33.3"))));
        return list;
    }

    protected static List<OWLAxiom> swrlRule() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        String URN_SWRL_VAR = "urn:swrl:var#";
        SWRLVariable varX = SWRLVariable(URN_SWRL_VAR, "x");
        SWRLVariable varY = SWRLVariable(URN_SWRL_VAR, "y");
        SWRLVariable varZ = SWRLVariable(URN_SWRL_VAR, "z");
        Set<SWRLAtom> body = new HashSet<>();
        body.add(SWRLClassAtom(A, varX));
        SWRLIndividualArgument indIArg = SWRLIndividualArgument(I);
        SWRLIndividualArgument indJArg = SWRLIndividualArgument(J);
        body.add(SWRLClassAtom(D, indIArg));
        body.add(SWRLClassAtom(B, varX));
        SWRLVariable varQ = SWRLVariable(URN_SWRL_VAR, "q");
        SWRLVariable varR = SWRLVariable(URN_SWRL_VAR, "r");
        body.add(SWRLDataPropertyAtom(DP, varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = SWRLLiteralArgument(lit);
        body.add(SWRLDataPropertyAtom(DP, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(SWRLClassAtom(C, varX));
        head.add(SWRLObjectPropertyAtom(op1, varY, varZ));
        head.add(SWRLSameIndividualAtom(varX, varY));
        head.add(SWRLSameIndividualAtom(indIArg, indJArg));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op1, A);
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
        body.add(SWRLClassAtom(A, varX));
        SWRLIndividualArgument indIArg = SWRLIndividualArgument(ind);
        SWRLIndividualArgument indJArg = SWRLIndividualArgument(indj);
        body.add(SWRLClassAtom(D, indIArg));
        body.add(SWRLClassAtom(B, varX));
        SWRLVariable varQ = SWRLVariable(HTTP_WWW_OWLAPI, "q");
        SWRLVariable varR = SWRLVariable(HTTP_WWW_OWLAPI, "r");
        body.add(SWRLDataPropertyAtom(dp, varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = SWRLLiteralArgument(lit);
        body.add(SWRLDataPropertyAtom(dp, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(SWRLClassAtom(C, varX));
        head.add(SWRLObjectPropertyAtom(op, varY, varZ));
        head.add(SWRLSameIndividualAtom(varX, varY));
        head.add(SWRLSameIndividualAtom(indIArg, indJArg));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(varX, varZ));
        head.add(SWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, A);
        head.add(SWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = new ArrayList<>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(SWRLBuiltInAtom(iri(HTTP_WWW_OWLAPI, "myBuiltIn"), args));
        return l(SWRLRule(body, head));
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingWithEntitiesTestCase() {
        return l(l(Declaration(A), AnnotationAssertion(propP, A.getIRI(), Literal("value1")),
            AnnotationAssertion(propQ, A.getIRI(), Literal("value2"))));
    }

    static List<List<OWLAxiom>> relativeURITestCase() {
        return l(l(Declaration(Class(iri(IRI.getNextDocumentIRI(uriBase) + "/", "Office")))));
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
        assertThrows(OWLRDFXMLParserException.class,
            () -> parser.parse(new StringDocumentSource(TestFiles.rdfContentForException), ontology,
                config),
            "[line=1:column=378] IRI 'http://example.com/#1#2' cannot be resolved against current base IRI ");
    }
}
