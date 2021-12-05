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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnnotationPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AnonymousIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataComplementOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataExactCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataIntersectionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataMaxCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataMinCardinality;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataUnionOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Datatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DatatypeDefinition;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DatatypeRestriction;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FacetRestriction;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.HasKey;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Integer;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSComment;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.RDFSLabel;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubAnnotationPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubPropertyChainOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.createClass;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.createIndividual;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import org.semanticweb.owlapi.io.OWLOntologyDocumentSourceBase;
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
import org.semanticweb.owlapi.model.OWLIndividual;
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

    static List<Set<OWLAxiom>> literalWithEscapesNoRioRDFXMLTestCase() {
        OWLClass cls = Class(IRI("http://owlapi.sourceforge.net/ontology#", "A"));
        OWLAnnotationProperty prop =
            AnnotationProperty(IRI("http://owlapi.sourceforge.net/ontology#", "prop"));
        return Collections.singletonList(set(AnnotationAssertion(prop, cls.getIRI(), Literal("\\")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\\")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("\\" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\\" + "End")),

            AnnotationAssertion(prop, cls.getIRI(), Literal("\"")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\"")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("\"" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\"" + "End")),

            AnnotationAssertion(prop, cls.getIRI(), Literal("<")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "<")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("<" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "<" + "End")),

            AnnotationAssertion(prop, cls.getIRI(), Literal("\'")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\'")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("\'" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\'" + "End")),

            Declaration(cls)));
    }

    @ParameterizedTest
    @MethodSource("formats")
    void anonymousRoundTripTestCase(OWLDocumentFormat format) {
        OWLOntology ont1 = createAnon();
        ont1.getOWLOntologyManager().addAxioms(ont1, anonymousRoundTrip());
        roundTrip(ont1, format);
    }

    static Set<OWLAxiom> anonymousRoundTrip() {
        OWLDataProperty dp = df.getOWLDataProperty(iri("urn:test:anon#", "D"));
        OWLObjectProperty op = df.getOWLObjectProperty(iri("urn:test:anon#", "O"));
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty(iri("urn:test:anon#", "A2"));
        OWLAnnotation sub1 = df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral("z1"));
        OWLAnnotation an1 = df.getOWLAnnotation(ap, df.getOWLAnonymousIndividual("_:b0"),
            Collections.singleton(sub1));

        OWLClassExpression c1 = df.getOWLDataAllValuesFrom(dp, df.getBooleanOWLDatatype());
        OWLClassExpression c2 = df.getOWLObjectSomeValuesFrom(op, df.getOWLThing());

        return Collections.singleton(df.getOWLSubClassOfAxiom(c1, c2, Collections.singleton(an1)));
    }

    static List<Set<OWLAxiom>> xmlAndFunctional() {
        return Arrays.asList(anonymousRoundTrip(), literalWithEscapes());
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
        OWLAnnotationProperty prop = AnnotationProperty(
            IRI("http://www.semanticweb.org/ontologies/test/annotationont#", "prop"));
        OWLAnnotation annotation = df.getOWLAnnotation(prop, Literal(33));
        ont.getOWLOntologyManager().applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.getOWLOntologyManager().addAxiom(ont, Declaration(prop));
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
            URL resource = getClass().getResource('/' + "testBlankNodesDomain.ttl");
            IRI iri = IRI.create(resource.toURI());
            return loadOntologyFromSource(
                new IRIDocumentSource(iri, new TurtleDocumentFormat(), null));
        } catch (URISyntaxException ex) {
            throw new OWLRuntimeException(ex);
        }
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameXmlLiteral() {
        plainEqual(loadOntology("XMLLiteral.rdf"), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void fileRoundTripNoRioRDFXMLTestCase(OWLDocumentFormat format) {
        if (RioRDFXMLDocumentFormat.class.isInstance(format)) {
            // XML literals managed differently in Rio
            return;
        }
        roundTrip(loadOntology("XMLLiteral.rdf"), format);
    }

    @ParameterizedTest
    @ValueSource(
        strings = {"AnonymousInverses.rdf", "TestParser08.rdf", "nodeid.rdf", "extraBlankNodes.owl",
            "testBlankNodes2.ttl", "testBlankNodesAssertions.ttl", "owlxml_anonloop.owx"})
    void roundTripsRDFXMLAndFunctionalShouldBeSame(String fileName) {
        plainEqual(loadOntology(fileName), false);
    }

    @ParameterizedTest
    @MethodSource({"axiomsRoundTrippingUsingEqualTestCase"})
    void roundTripsRDFXMLAndFunctionalShouldBeSame(Set<OWLAxiom> axioms) {
        plainEqual(o(axioms), false);
    }

    static List<Set<OWLAxiom>> axiomsRoundTrippingUsingEqualTestCase() {
        return Arrays.asList(anonymousIndividualRoundtrip(), anonymousIndividuals2(),
            anonymousIndividuals(), chainedAnonymousIndividuals(),
            classAssertionWithAnonymousIndividual(), differentIndividualsAnonymous(),
            differentIndividualsPairwiseAnonymous(),
            objectPropertyAssertionWithAnonymousIndividuals(), sameIndividualsAnonymous());
    }

    private static Set<OWLAxiom> sameIndividualsAnonymous() {
        // Can't round trip more than two in RDF! Also, same
        // individuals axiom with anon individuals is not allowed
        // in OWL 2, but it should at least round trip
        return set(SameIndividual(AnonymousIndividual(), AnonymousIndividual()));
    }

    private static Set<OWLAxiom> objectPropertyAssertionWithAnonymousIndividuals() {
        OWLIndividual subject = AnonymousIndividual();
        OWLIndividual object = AnonymousIndividual();
        return set(ObjectPropertyAssertion(P, subject, object), Declaration(P));
    }

    private static Set<OWLAxiom> differentIndividualsPairwiseAnonymous() {
        return set(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual()));
    }

    private static Set<OWLAxiom> differentIndividualsAnonymous() {
        return set(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual(),
            AnonymousIndividual()));
    }

    private static Set<OWLAxiom> classAssertionWithAnonymousIndividual() {
        return set(ClassAssertion(A, AnonymousIndividual("a")), Declaration(A));
    }

    private static Set<OWLAxiom> chainedAnonymousIndividuals() {
        IRI subject = IRI("http://owlapi.sourceforge.net/ontology#", "subject");
        OWLAnonymousIndividual individual1 = AnonymousIndividual();
        OWLAnonymousIndividual individual2 = AnonymousIndividual();
        OWLAnonymousIndividual individual3 = AnonymousIndividual();
        OWLAnnotationAssertionAxiom annoAssertion1 = AnnotationAssertion(AP, subject, individual1);
        OWLAnnotationAssertionAxiom annoAssertion2 =
            AnnotationAssertion(AP, individual1, individual2);
        OWLAnnotationAssertionAxiom annoAssertion3 =
            AnnotationAssertion(AP, individual2, individual3);
        return set(Declaration(NamedIndividual(subject)), annoAssertion1, annoAssertion2,
            annoAssertion3);
    }

    private static Set<OWLAxiom> anonymousIndividuals() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        return set(ObjectPropertyAssertion(P, I, ind), ObjectPropertyAssertion(P, ind, J));
    }

    private static Set<OWLAxiom> anonymousIndividuals2() {
        // Originally submitted by Timothy Redmond
        OWLAnonymousIndividual h = AnonymousIndividual();
        return set(AnnotationAssertion(AP, A.getIRI(), h), ClassAssertion(A, h),
            ObjectPropertyAssertion(Q, h, AnonymousIndividual()),
            AnnotationAssertion(RDFSLabel(), h, Literal("Second", "en")));
    }

    private static Set<OWLAxiom> anonymousIndividualRoundtrip() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(AP, A.getIRI(), ind);
        OWLAnonymousIndividual anon1 = AnonymousIndividual();
        OWLAnonymousIndividual anon2 = AnonymousIndividual();
        return set(ax, Declaration(A), Declaration(P),
            df.getOWLObjectPropertyAssertionAxiom(P, J, I),
            df.getOWLObjectPropertyAssertionAxiom(P, anon1, anon1),
            df.getOWLObjectPropertyAssertionAxiom(P, anon2, I),
            df.getOWLObjectPropertyAssertionAxiom(P, I, anon2));
    }

    static final List<String> FILE_ROUND_TRIP = Arrays.asList("AnnotatedPropertyAssertions.rdf",
        "ComplexSubProperty.rdf", "DataAllValuesFrom.rdf", "cardinalitywithwhitespace.owl",
        "DataComplementOf.rdf", "DataHasValue.rdf", "DataIntersectionOf.rdf",
        "DataMaxCardinality.rdf", "DataMinCardinality.rdf", "DataOneOf.rdf",
        "DataSomeValuesFrom.rdf", "DataUnionOf.rdf", "DatatypeRestriction.rdf",
        "TestDeclarations.rdf", "Deprecated.rdf", "DisjointClasses.rdf", "HasKey.rdf",
        "InverseOf.rdf", "ObjectAllValuesFrom.rdf", "ObjectCardinality.rdf",
        "ObjectComplementOf.rdf", "ObjectHasSelf.rdf", "ObjectHasValue.rdf",
        "ObjectIntersectionOf.rdf", "ObjectMaxCardinality.rdf", "ObjectMaxQualifiedCardinality.rdf",
        "ObjectMinCardinality.rdf", "ObjectMinQualifiedCardinality.rdf", "ObjectOneOf.rdf",
        "ObjectQualifiedCardinality.rdf", "ObjectSomeValuesFrom.rdf", "ObjectUnionOf.rdf",
        "primer.functionalsyntax.txt", "primer.owlxml.xml", "primer.rdfxml.xml", "RDFSClass.rdf",
        "koala.owl", "SubClassOf.rdf", "TestParser06.rdf", "TestParser07.rdf", "TestParser10.rdf",
        "annotatedpropertychain.ttl.rdf", "UntypedSubClassOf.rdf", "SubClassOfUntypedOWLClass.rdf",
        "SubClassOfUntypedSomeValuesFrom.rdf");

    static List<String> fileRoundTripOnly() {
        return FILE_ROUND_TRIP;
    }

    static List<Arguments> fileRoundTrip() {
        List<Arguments> list = new ArrayList<>();
        FILE_ROUND_TRIP
            .forEach(file -> formats().forEach(format -> list.add(Arguments.of(file, format))));
        // use a different equal method
        Arrays
            .asList("extraBlankNodes.owl", "testBlankNodes2.ttl", "testBlankNodesAssertions.ttl",
                "owlxml_anonloop.owx")
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

    protected static void forEachFormat(List<Arguments> list, Set<OWLAxiom> axioms) {
        formats().forEach(format -> list.add(Arguments.of(axioms, format)));
    }

    static List<Arguments> fileRoundTripNoManSyntax() {
        List<Arguments> list = new ArrayList<>();
        Arrays.asList("AnonymousInverses.rdf", "TestParser08.rdf", "nodeid.rdf")
            .forEach(file -> formatsSkip(ManchesterSyntaxDocumentFormat.class)
                .forEach(format -> list.add(Arguments.of(file, format))));
        return list;
    }

    @ParameterizedTest
    @MethodSource({"fileRoundTrip", "fileRoundTripNoManSyntax"})
    void fileRoundTripTestCase(String fileName, OWLDocumentFormat format) {
        roundTrip(loadOntology(fileName), format);
    }

    @Test
    void fileRoundTripSubClassOfUntypedOWLClassStrictTestCase() {
        config = config.setStrict(true);
        URL url = getClass().getResource("/SubClassOfUntypedOWLClass.rdf");
        OWLOntology ont = loadOntologyFromSource(new IRIDocumentSource(IRI.create(url), null, null),
            config.setReportStackTraces(true));
        assertEquals(0, ont.getAxioms(AxiomType.SUBCLASS_OF).size());
        OWLDocumentFormat format = ont.getOWLOntologyManager().getOntologyFormat(ont);
        assertTrue(format instanceof RDFXMLDocumentFormat);
        RDFXMLDocumentFormat rdfxmlFormat = (RDFXMLDocumentFormat) format;
        Set<RDFTriple> triples = rdfxmlFormat.getOntologyLoaderMetaData().getUnparsedTriples();
        assertEquals(1, triples.size());
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
    // createOntology = loadOntologyFromString(s, new FunctionalSyntaxDocumentFormat());
    // createOntology.removeAxioms(asList(createOntology.axioms(AxiomType.CLASS_ASSERTION)
    // .filter(ax -> ax.getClassExpression().isOWLThing())));
    // OBODocumentFormat format = new OBODocumentFormat();
    // StringDocumentTarget target = saveOntology(createOntology, format);
    // OWLOntology o1 = loadOntologyFromString(target, format);
    // createOntology.removeAxioms(asList(createOntology.axioms(AxiomType.CLASS_ASSERTION).filter(
    // ax -> ax.getClassExpression().isOWLThing() || ax.getIndividual().isAnonymous())));
    // o1.removeAxioms(asList(o1.axioms(AxiomType.CLASS_ASSERTION).filter(
    // ax -> ax.getClassExpression().isOWLThing() || ax.getIndividual().isAnonymous())));
    // OWLAnnotationProperty version = df.getOWLAnnotationProperty(
    // "http://www.geneontology.org/formats/oboInOwl#hasOBOFormatVersion");
    // OWLAnnotationProperty id =
    // df.getOWLAnnotationProperty("http://www.geneontology.org/formats/oboInOwl#id");
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
    void roundTripFormats(Set<OWLAxiom> axioms, OWLDocumentFormat format) {
        roundTripOntology(o(axioms), format);
    }

    @ParameterizedTest
    @MethodSource({"literalWithEscapesNoRioRDFXMLTestCase",
        "axiomsRoundTrippingNoManchesterSyntaxTestCase", "axiomsRoundTrippingTestCase",
        "axiomsRoundTrippingWithEntitiesTestCase", "relativeURITestCase", "xmlAndFunctional"})
    void roundTripRDFXMLAndFunctionalShouldBeSame(Set<OWLAxiom> axioms) {
        plainEqual(o(axioms), true);
    }

    @ParameterizedTest
    @MethodSource("fileRoundTripOnly")
    @ValueSource(strings = {"AnonymousInverses.rdf", "TestParser08.rdf", "nodeid.rdf"})
    void roundTripRDFXMLAndFunctionalShouldBeSame(String name) {
        plainEqual(loadOntology(name), true);
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
            o.getAxioms().forEach(ax -> logger.trace(ax.toString()));
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

        Set<OWLAxiom> axioms = literalWithEscapes();
        OWLOntology o = createAnon();
        o.getOWLOntologyManager().addAxioms(o, axioms);
        roundTrip(o, format);
    }

    protected static Set<OWLAxiom> literalWithEscapes() {
        OWLClass cls = Class(IRI("http://owlapi.sourceforge.net/ontology#", "A"));
        OWLAnnotationProperty prop =
            AnnotationProperty(IRI("http://owlapi.sourceforge.net/ontology#", "prop"));
        Set<OWLAxiom> axioms = set(AnnotationAssertion(prop, cls.getIRI(), Literal("\\")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\\")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("\\" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\\" + "End")),

            AnnotationAssertion(prop, cls.getIRI(), Literal("\"")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\"")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("\"" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\"" + "End")),

            AnnotationAssertion(prop, cls.getIRI(), Literal("<")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "<")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("<" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "<" + "End")),

            AnnotationAssertion(prop, cls.getIRI(), Literal("\n")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\n")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("\n" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\n" + "End")),

            AnnotationAssertion(prop, cls.getIRI(), Literal("\'")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\'")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("\'" + "End")),
            AnnotationAssertion(prop, cls.getIRI(), Literal("Start" + "\'" + "End")),
            Declaration(cls));
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
        OWLAnnotationProperty prop = AnnotationProperty(
            IRI("http://www.semanticweb.org/ontologies/test/annotationont#", "prop"));
        OWLLiteral value = Literal(33);
        OWLAnnotation annotation = Annotation(prop, value);
        OWLAnnotation builtin = Annotation(df.getOWLVersionInfo(), df.getOWLLiteral("x"));
        ont.getOWLOntologyManager().applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.getOWLOntologyManager().applyChange(new AddOntologyAnnotation(ont, builtin));
        ont.getOWLOntologyManager().addAxiom(ont, Declaration(prop));
        return ont;
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyAnnotation() {
        plainEqual(ontologyAnnotation(), true);
    }

    @Test
    void testCorrectOntologyIRI() {
        OWLOntology ont = loadOntologyFromString(TestFiles.ontologyIRI, new RDFXMLDocumentFormat());
        OWLOntologyID id = ont.getOntologyID();
        assertEquals("http://www.test.com/right.owl", id.getOntologyIRI().get().toString());
    }

    @ParameterizedTest
    @MethodSource("formats")
    void ontologyIRITestCase(OWLDocumentFormat format) {
        roundTrip(loadOntologyFromString(TestFiles.ontologyIRI, new RDFXMLDocumentFormat()),
            format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyIRI() {
        plainEqual(loadOntologyFromString(TestFiles.ontologyIRI, new RDFXMLDocumentFormat()), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void ontologyVersionIRITestCase(OWLDocumentFormat format) {
        roundTrip(ontologyVersion(), format);
    }

    protected OWLOntology ontologyVersion() {
        IRI ontIRI = IRI("http://www.semanticweb.org/owlapi/", "ontology");
        IRI versionIRI = IRI("http://www.semanticweb.org/owlapi/ontology/", "version");
        OWLOntologyID ontologyID = new OWLOntologyID(ontIRI, versionIRI);
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
        OWLClass dbxref = df.getOWLClass(iri("urn:obo:", "DbXref"));
        OWLClass definition = df.getOWLClass(iri("urn:obo:", "Definition"));
        OWLObjectProperty adjacent_to = df.getOWLObjectProperty(iri("urn:obo:", "adjacent_to"));
        OWLAnnotationProperty hasDefinition =
            df.getOWLAnnotationProperty(iri("urn:obo:", "hasDefinition"));
        OWLAnnotationProperty hasdbxref = df.getOWLAnnotationProperty(iri("urn:obo:", "hasDbXref"));
        OWLDataProperty hasuri = df.getOWLDataProperty(iri("urn:obo:", "hasURI"));
        OWLAnonymousIndividual ind1 = df.getOWLAnonymousIndividual();
        o.getOWLOntologyManager().addAxiom(o, df.getOWLClassAssertionAxiom(dbxref, ind1));
        o.getOWLOntologyManager().addAxiom(o, df.getOWLDataPropertyAssertionAxiom(hasuri, ind1,
            df.getOWLLiteral("urn:SO:SO_ke", OWL2Datatype.XSD_ANY_URI)));
        OWLAnonymousIndividual ind2 = df.getOWLAnonymousIndividual();
        o.getOWLOntologyManager().addAxiom(o, df.getOWLClassAssertionAxiom(definition, ind2));
        o.getOWLOntologyManager().addAxiom(o,
            df.getOWLAnnotationAssertionAxiom(hasdbxref, ind2, ind1));
        o.getOWLOntologyManager().addAxiom(o,
            df.getOWLAnnotationAssertionAxiom(hasDefinition, adjacent_to.getIRI(), ind2));
        return o;
    }

    //@formatter:off
    private static final String original = "<?xml version=\"1.0\"?>\n" +
        "<Ontology xmlns=\"http://www.w3.org/2002/07/owl#\"\n" +
        "     xml:base=\"http://www.derivo.de/ontologies/examples/nested_annotations\"\n" +
        "     xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n" +
        "     xmlns:xml=\"http://www.w3.org/XML/1998/namespace\"\n" +
        "     xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n" +
        "     xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n" +
        "     ontologyIRI=\"http://www.derivo.de/ontologies/examples/nested_annotations\">\n" +
        "    <Prefix name=\"owl\" IRI=\"http://www.w3.org/2002/07/owl#\"/>\n" +
        "    <Prefix name=\"rdf\" IRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"/>\n" +
        "    <Prefix name=\"xml\" IRI=\"http://www.w3.org/XML/1998/namespace\"/>\n" +
        "    <Prefix name=\"xsd\" IRI=\"http://www.w3.org/2001/XMLSchema#\"/>\n" +
        "    <Prefix name=\"rdfs\" IRI=\"http://www.w3.org/2000/01/rdf-schema#\"/>\n" +
        "    <Declaration>\n" +
        "        <NamedIndividual IRI=\"#b\"/>\n" +
        "    </Declaration>\n" +
        "    <Declaration>\n" +
        "        <NamedIndividual IRI=\"#c\"/>\n" +
        "    </Declaration>\n" +
        "    <Declaration>\n" +
        "        <NamedIndividual IRI=\"#a\"/>\n" +
        "    </Declaration>\n" +
        "    <Declaration>\n" +
        "        <ObjectProperty IRI=\"#r\"/>\n" +
        "    </Declaration>\n" +
        "    <Declaration>\n" +
        "        <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" +
        "    </Declaration>\n" +
        "    <ObjectPropertyAssertion>\n" +
        "        <Annotation>\n" +
        "            <Annotation>\n" +
        "                <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" +
        "                <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">comment for one</Literal>\n" + 
        "            </Annotation>\n" +
        "            <AnnotationProperty abbreviatedIRI=\"rdfs:label\"/>\n" +
        "            <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">one</Literal>\n" + 
        "        </Annotation>\n" +
        "        <Annotation>\n" +
        "            <Annotation>\n" +
        "                <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" +
        "                <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">comment for two</Literal>\n" + 
        "            </Annotation>\n" +
        "            <AnnotationProperty abbreviatedIRI=\"rdfs:label\"/>\n" +
        "            <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">two</Literal>\n" + 
        "        </Annotation>\n" +
        "        <ObjectProperty IRI=\"#r\"/>\n" +
        "        <NamedIndividual IRI=\"#a\"/>\n" +
        "        <NamedIndividual IRI=\"#b\"/>\n" +
        "    </ObjectPropertyAssertion>\n" +
        "    <ObjectPropertyAssertion>\n" +
        "        <Annotation>\n" +
        "            <Annotation>\n" +
        "                <AnnotationProperty abbreviatedIRI=\"rdfs:commment\"/>\n" +
        "                <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">comment for three</Literal>\n" + 
        "            </Annotation>\n" +
        "            <AnnotationProperty abbreviatedIRI=\"rdfs:label\"/>\n" +
        "            <Literal datatypeIRI=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#PlainLiteral\">three</Literal>\n" + 
        "        </Annotation>\n" +
        "        <ObjectProperty IRI=\"#r\"/>\n" +
        "        <NamedIndividual IRI=\"#b\"/>\n" +
        "        <NamedIndividual IRI=\"#c\"/>\n" +
        "    </ObjectPropertyAssertion>\n" +
        "</Ontology>";

    //@formatter:on
    @ParameterizedTest
    @MethodSource("formats")
    void roundTripOWLXMLToRioTurtleTestCase(OWLDocumentFormat format) {
        roundTrip(loadOntologyFromString(original, new OWLXMLDocumentFormat()), format);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOWLXMLToTurtle() {
        plainEqual(loadOntologyFromString(original, new OWLXMLDocumentFormat()), true);
    }

    @Test
    void shouldRoundTripThroughOWLXML() {
        OWLOntology ontology = original();
        StringDocumentTarget targetOWLXML = saveOntology(ontology, new OWLXMLDocumentFormat());
        OWLOntology o1 = loadOntologyFromString(targetOWLXML, new OWLXMLDocumentFormat());
        equal(ontology, o1);
    }

    protected OWLOntology original() {
        return loadOntologyFromString(original, new OWLXMLDocumentFormat());
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

    static List<Set<OWLAxiom>> axiomsRoundTrippingNoManchesterSyntaxTestCase() {
        // no valid Manchester OWL Syntax roundtrip
        OWLObjectPropertyExpression p = P.getInverseProperty();
        OWLObjectPropertyExpression q = Q.getInverseProperty();
        return Arrays.asList(set(AsymmetricObjectProperty(p)),
            set(EquivalentObjectProperties(p, q)), set(FunctionalObjectProperty(p)),
            set(InverseFunctionalObjectProperty(p)), set(IrreflexiveObjectProperty(p)),
            set(ObjectPropertyDomain(p, A)), set(ObjectPropertyRange(p, A)),
            set(ReflexiveObjectProperty(p)), set(SubObjectPropertyOf(p, q)),
            set(SymmetricObjectProperty(p)), set(TransitiveObjectProperty(p)));
    }

    static List<Set<OWLAxiom>> axiomsRoundTrippingTestCase() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        OWLEntity peter = NamedIndividual(IRI("http://www.another.com/ont#", "peter"));
        OWLAnnotation ann1 = Annotation(RDFSLabel(), Literal("Annotation 1"));
        OWLAnnotation ann2 = Annotation(RDFSLabel(), Literal("Annotation 2"));
        OWLAnnotation eAnn1 = Annotation(RDFSLabel(), Literal("EntityAnnotation 1"));
        OWLAnnotation eAnn2 = Annotation(RDFSLabel(), Literal("EntityAnnotation 2"));
        OWLDatatype datatype = Datatype(IRI("http://www.ont.com/myont/", "mydatatype"));
        OWLAnnotation annoOuterOuter1 = Annotation(AnnotationProperty(iri("myOuterOuterLabel1")),
            Literal("Outer Outer label 1"));
        OWLAnnotation annoOuterOuter2 = Annotation(AnnotationProperty(iri("myOuterOuterLabel2")),
            Literal("Outer Outer label 2"));
        OWLDatatype dt = Datatype(IRI("file:/c/test.owlapi#", "SSN"));
        OWLFacetRestriction fr =
            FacetRestriction(OWLFacet.PATTERN, Literal("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
        OWLDataRange dr =
            DatatypeRestriction(Datatype(IRI("http://www.w3.org/2001/XMLSchema#", "string")), fr);
        OWLDataIntersectionOf disj1 = DataIntersectionOf(DataComplementOf(dr), dt);
        OWLDataIntersectionOf disj2 = DataIntersectionOf(DataComplementOf(dt), dr);
        OWLAnnotation annoOuter = Annotation(AnnotationProperty(iri("myOuterLabel")),
            Literal("Outer label"), annoOuterOuter1, annoOuterOuter2);
        OWLAnnotation annoInner =
            Annotation(AnnotationProperty(iri("myLabel")), Literal("Label"), annoOuter);

        List<Set<OWLAxiom>> list = new ArrayList<>();
        list.add(swrlRuleAlternateNS(HTTP_WWW_OWLAPI, DP, op1, I, J));
        list.add(swrlRule());
        list.add(set(SubPropertyChainOf(Arrays.asList(t, u, w), z)));
        list.add(set(AsymmetricObjectProperty(op1)));
        list.add(set(DifferentIndividuals(createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual(), createIndividual())));
        list.add(set(SubClassOf(A, ObjectSomeValuesFrom(op1, ObjectSomeValuesFrom(op1, B))),
            Declaration(A), Declaration(B)));
        list.add(set(Declaration(RDFSLabel()), Declaration(peter),
            AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)));
        list.add(set(Declaration(RDFSLabel()), Declaration(peter, set(eAnn1, eAnn2)),
            AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)));
        list.add(set(InverseObjectProperties(op2, op1)));
        list.add(set(InverseObjectProperties(op1, op2)));
        list.add(set(Declaration(A), AnnotationAssertion(propP, A.getIRI(),
            IRI("http://www.semanticweb.org/owlapi#", "object"))));
        list.add(set(SubClassOf(A, B, singleton(annoInner))));
        list.add(set(AnnotationPropertyDomain(RDFSComment(), A.getIRI())));
        list.add(set(AnnotationPropertyRange(RDFSComment(), A.getIRI())));
        list.add(set(SubAnnotationPropertyOf(propP, RDFSLabel())));
        list.add(set(SubClassOf(A, DataMaxCardinality(3, DP, Integer()))));
        list.add(set(SubClassOf(A, DataMinCardinality(3, DP, Integer()))));
        list.add(set(SubClassOf(A, DataExactCardinality(3, DP, Integer()))));
        list.add(set(DataPropertyRange(DP, DataUnionOf(disj1, disj2))));
        list.add(set(HasKey(singleton(Annotation(propP, Literal("Test", ""))), A, t, u, w),
            Declaration(propP), Declaration(t), Declaration(u), Declaration(w)));
        list.add(
            set(DisjointClasses(asUnorderedSet(Stream.generate(() -> createClass()).limit(6000)))));
        list.add(set(SubClassOf(B, ObjectSomeValuesFrom(op1.getInverseProperty(), A))));
        list.add(set(SubDataPropertyOf(DP, DQ)));
        list.add(set(DataPropertyAssertion(DP, I, Literal(33.3))));
        list.add(set(NegativeDataPropertyAssertion(DP, I, Literal(33.3)),
            NegativeDataPropertyAssertion(DP, I, Literal("weasel", "")),
            NegativeDataPropertyAssertion(DP, I, Literal("weasel"))));
        list.add(set(FunctionalDataProperty(DP)));
        list.add(set(DataPropertyDomain(DP, A)));
        list.add(set(DataPropertyRange(DP, TopDatatype())));
        list.add(set(DisjointDataProperties(dp1, dp2, dp3), Declaration(dp1), Declaration(dp2),
            Declaration(dp3)));
        list.add(set(DisjointDataProperties(dp1, dp2)));
        list.add(set(EquivalentDataProperties(DP, DQ)));
        list.add(set(AsymmetricObjectProperty(op1)));
        list.add(
            set(DatatypeDefinition(datatype, DataComplementOf(Integer())), Declaration(datatype)));
        list.add(set(DifferentIndividuals(I, J), DifferentIndividuals(I, k)));
        list.add(set(DifferentIndividuals(I, J, k, l)));
        list.add(
            set(DisjointObjectProperties(t, u, w), Declaration(t), Declaration(u), Declaration(w)));
        list.add(set(DisjointObjectProperties(t, u)));
        list.add(set(EquivalentObjectProperties(t, u), Declaration(t), Declaration(u)));
        list.add(set(FunctionalObjectProperty(op1)));
        list.add(set(InverseFunctionalObjectProperty(op1)));
        list.add(set(IrreflexiveObjectProperty(op1)));
        list.add(set(DifferentIndividuals(
            asUnorderedSet(Stream.generate(() -> createIndividual()).limit(1000)))));
        list.add(set(AnnotationAssertion(propP, A.getIRI(), Literal("abc", "en")), Declaration(A)));
        list.add(set(AnnotationAssertion(propP, A.getIRI(), Literal("abc", "en")),
            AnnotationAssertion(propP, A.getIRI(), Literal("abcd", "")),
            AnnotationAssertion(propP, A.getIRI(), Literal("abcde")),
            AnnotationAssertion(propP, A.getIRI(), Literal("abcdef", OWL2Datatype.XSD_STRING)),
            Declaration(A)));
        list.add(set(NegativeObjectPropertyAssertion(op1, I, J)));
        list.add(set(ObjectPropertyAssertion(op1, I, J)));
        list.add(set(SubPropertyChainOf(Arrays.asList(t, u, w), z, set(
            Annotation(propP, Literal("Test", "en")), Annotation(propQ, Literal("Test", ""))))));
        list.add(set(ObjectPropertyDomain(op1, A)));
        list.add(set(ObjectPropertyRange(op1, A)));
        list.add(set(Declaration(Class(IRI("http://www.test.com/ontology#", "Class%37A"))),
            Declaration(ObjectProperty(IRI("http://www.test.com/ontology#", "prop%37A")))));
        list.add(set(ReflexiveObjectProperty(op1)));
        list.add(set(SameIndividual(I, J)));
        list.add(set(DataPropertyAssertion(DP, I, Literal("Test \"literal\"\nStuff"))));
        list.add(set(DataPropertyAssertion(DP, I, Literal("Test \"literal\"")),
            DataPropertyAssertion(DP, I, Literal("Test 'literal'")),
            DataPropertyAssertion(DP, I, Literal("Test \"\"\"literal\"\"\""))));
        list.add(set(SubObjectPropertyOf(op1, op2)));
        list.add(set(SymmetricObjectProperty(op1)));
        list.add(set(TransitiveObjectProperty(op1)));
        list.add(set(DataPropertyAssertion(DP, I, Literal(3)),
            DataPropertyAssertion(DP, I, Literal(33.3)),
            DataPropertyAssertion(DP, I, Literal(true)),
            DataPropertyAssertion(DP, I, Literal(33.3f)),
            DataPropertyAssertion(DP, I, Literal("33.3"))));
        return list;
    }

    protected static Set<OWLAxiom> swrlRule() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        String URN_SWRL_VAR = "urn:swrl:var#";
        SWRLVariable varX = df.getSWRLVariable(iri(URN_SWRL_VAR, "x"));
        SWRLVariable varY = df.getSWRLVariable(iri(URN_SWRL_VAR, "y"));
        SWRLVariable varZ = df.getSWRLVariable(iri(URN_SWRL_VAR, "z"));
        Set<SWRLAtom> body = new HashSet<>();
        body.add(df.getSWRLClassAtom(A, varX));
        SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(I);
        SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(J);
        body.add(df.getSWRLClassAtom(D, indIArg));
        body.add(df.getSWRLClassAtom(B, varX));
        SWRLVariable varQ = df.getSWRLVariable(iri(URN_SWRL_VAR, "q"));
        SWRLVariable varR = df.getSWRLVariable(iri(URN_SWRL_VAR, "r"));
        body.add(df.getSWRLDataPropertyAtom(DP, varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = df.getSWRLLiteralArgument(lit);
        body.add(df.getSWRLDataPropertyAtom(DP, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(df.getSWRLClassAtom(C, varX));
        head.add(df.getSWRLObjectPropertyAtom(op1, varY, varZ));
        head.add(df.getSWRLSameIndividualAtom(varX, varY));
        head.add(df.getSWRLSameIndividualAtom(indIArg, indJArg));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op1, A);
        head.add(df.getSWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = Arrays.asList(varQ, varR, litArg);
        head.add(df.getSWRLBuiltInAtom(IRI(HTTP_WWW_OWLAPI, "myBuiltIn"), args));
        return set(df.getSWRLRule(body, head));
    }

    protected static Set<OWLAxiom> swrlRuleAlternateNS(String HTTP_WWW_OWLAPI, OWLDataProperty dp,
        OWLObjectProperty op, OWLNamedIndividual ind, OWLNamedIndividual indj) {
        SWRLVariable varX = df.getSWRLVariable(iri(HTTP_WWW_OWLAPI, "x"));
        SWRLVariable varY = df.getSWRLVariable(iri(HTTP_WWW_OWLAPI, "y"));
        SWRLVariable varZ = df.getSWRLVariable(iri(HTTP_WWW_OWLAPI, "z"));
        Set<SWRLAtom> body = new HashSet<>();
        body.add(df.getSWRLClassAtom(A, varX));
        SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(ind);
        SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(indj);
        body.add(df.getSWRLClassAtom(D, indIArg));
        body.add(df.getSWRLClassAtom(B, varX));
        SWRLVariable varQ = df.getSWRLVariable(iri(HTTP_WWW_OWLAPI, "q"));
        SWRLVariable varR = df.getSWRLVariable(iri(HTTP_WWW_OWLAPI, "r"));
        body.add(df.getSWRLDataPropertyAtom(dp, varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = df.getSWRLLiteralArgument(lit);
        body.add(df.getSWRLDataPropertyAtom(dp, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(df.getSWRLClassAtom(C, varX));
        head.add(df.getSWRLObjectPropertyAtom(op, varY, varZ));
        head.add(df.getSWRLSameIndividualAtom(varX, varY));
        head.add(df.getSWRLSameIndividualAtom(indIArg, indJArg));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, A);
        head.add(df.getSWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = new ArrayList<>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(df.getSWRLBuiltInAtom(IRI(HTTP_WWW_OWLAPI, "myBuiltIn"), args));
        return set(df.getSWRLRule(body, head));
    }

    static List<Set<OWLAxiom>> axiomsRoundTrippingWithEntitiesTestCase() {
        return Collections.singletonList(
            set(Declaration(A), AnnotationAssertion(propP, A.getIRI(), Literal("value1")),
                AnnotationAssertion(propQ, A.getIRI(), Literal("value2"))));
    }

    static List<Set<OWLAxiom>> relativeURITestCase() {
        return Collections.singletonList(set(Declaration(Class(
            IRI(OWLOntologyDocumentSourceBase.getNextDocumentIRI(uriBase) + "/", "Office")))));
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
