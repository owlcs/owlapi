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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
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
import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import org.semanticweb.owlapi.formats.DLSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.KRSS2DocumentFormat;
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
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
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
    private static final String ONTOLOGY_IRI_TEST_CASE = "<?xml version=\"1.0\"?>\n"
        + "<!DOCTYPE rdf:RDF [\n" + "        <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n"
        + "        <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n"
        + "        <!ENTITY owl2xml \"http://www.w3.org/2006/12/owl2-xml#\" >\n"
        + "        <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n"
        + "        <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n"
        + "        ]>\n" + "<rdf:RDF xmlns=\"http://www.test.com/Ambiguous.owl#\"\n"
        + "         xml:base=\"http://www.test.com/Ambiguous.owl\"\n"
        + "         xmlns:owl2xml=\"http://www.w3.org/2006/12/owl2-xml#\"\n"
        + "         xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
        + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
        + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
        + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
        + "    <owl:Ontology rdf:about=\"http://www.test.com/wrong.owl\"/>\n"
        + "    <owl:OntologyProperty rdf:about=\"p\"/>\n"
        + "    <owl:Ontology rdf:about=\"http://www.test.com/right.owl\">\n" + "        <p>\n"
        + "            <owl:Ontology rdf:about=\"http://www.test.com/wrong.owl\"/>\n"
        + "        </p>\n" + "    </owl:Ontology>\n" + "</rdf:RDF>";

    static List<List<OWLAxiom>> literalWithEscapesNoRioRDFXMLTestCase() {
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
    void anonymousRoundTripTestCase(OWLDocumentFormat f) {
        OWLOntology ont1 = getOWLOntology();
        ont1.add(anonymousRoundTrip());
        roundTrip(ont1, f);
    }

    static List<OWLAxiom> anonymousRoundTrip() {
        OWLDataProperty dp = df.getOWLDataProperty("urn:test:anon#D");
        OWLObjectProperty op = df.getOWLObjectProperty("urn:test:anon#O");
        OWLAnnotationProperty ap = df.getOWLAnnotationProperty("urn:test:anon#A2");
        OWLAnonymousIndividual i = df.getOWLAnonymousIndividual("_:b0");
        OWLAnnotation sub1 = df.getOWLAnnotation(df.getRDFSComment(), df.getOWLLiteral("z1"));
        OWLAnnotation an1 = df.getOWLAnnotation(ap, i, Collections.singletonList(sub1));

        OWLClassExpression c1 = df.getOWLDataAllValuesFrom(dp, df.getBooleanOWLDatatype());
        OWLClassExpression c2 = df.getOWLObjectSomeValuesFrom(op, df.getOWLThing());

        return Collections
            .singletonList(df.getOWLSubClassOfAxiom(c1, c2, Collections.singletonList(an1)));
    }

    static List<List<OWLAxiom>> xmlAndFunctional() {
        return Arrays.asList(anonymousRoundTrip(), literalWithEscapes());
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameAnonOntology() {
        plainEqual(anonOntology(), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void anonymousOntologyAnnotationsTestCase(OWLDocumentFormat f) {
        roundTrip(anonOntology(), f);
    }

    protected OWLOntology anonOntology() {
        OWLOntology ont = getAnonymousOWLOntology();
        OWLAnnotationProperty prop = AnnotationProperty(
            IRI("http://www.semanticweb.org/ontologies/test/annotationont#", "prop"));
        OWLAnnotation annotation = df.getOWLAnnotation(prop, Literal(33));
        ont.applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.add(Declaration(prop));
        return ont;
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameBlankNodesTurtleDomain()
        throws OWLOntologyCreationException, URISyntaxException {
        plainEqual(blankNodesTurtleDomain(), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void fileRoudTripWithKnownInputFormatTestCase(OWLDocumentFormat f)
        throws URISyntaxException, OWLOntologyCreationException {
        roundTrip(blankNodesTurtleDomain(), f);
    }

    protected OWLOntology blankNodesTurtleDomain()
        throws URISyntaxException, OWLOntologyCreationException {
        URL resource = getClass().getResource('/' + "testBlankNodesDomain.ttl");
        IRI iri = IRI.create(resource.toURI());
        return m.loadOntologyFromOntologyDocument(
            new IRIDocumentSource(iri, new TurtleDocumentFormat(), null));
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameXmlLiteral() {
        plainEqual(loadOntology("XMLLiteral.rdf"), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void fileRoundTripNoRioRDFXMLTestCase(OWLDocumentFormat f) {
        if (RioRDFXMLDocumentFormat.class.isInstance(f)) {
            // XML literals managed differently in Rio
            return;
        }
        roundTrip(loadOntology("XMLLiteral.rdf"), f);
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
    void roundTripsRDFXMLAndFunctionalShouldBeSame(List<OWLAxiom> axioms) {
        plainEqual(o(axioms), false);
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingUsingEqualTestCase() {
        return Arrays.asList(anonymousIndividualRoundtrip(), anonymousIndividuals2(),
            anonymousIndividuals(), chainedAnonymousIndividuals(),
            classAssertionWithAnonymousIndividual(), differentIndividualsAnonymous(),
            differentIndividualsPairwiseAnonymous(),
            objectPropertyAssertionWithAnonymousIndividuals(), sameIndividualsAnonymous());
    }

    private static List<OWLAxiom> sameIndividualsAnonymous() {
        // Can't round trip more than two in RDF! Also, same
        // individuals axiom with anon individuals is not allowed
        // in OWL 2, but it should at least round trip
        return set(SameIndividual(AnonymousIndividual(), AnonymousIndividual()));
    }

    private static List<OWLAxiom> objectPropertyAssertionWithAnonymousIndividuals() {
        OWLIndividual subject = AnonymousIndividual();
        OWLIndividual object = AnonymousIndividual();
        OWLObjectProperty prop = ObjectProperty(iri("prop"));
        return set(ObjectPropertyAssertion(prop, subject, object), Declaration(prop));
    }

    private static List<OWLAxiom> differentIndividualsPairwiseAnonymous() {
        return set(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual()));
    }

    private static List<OWLAxiom> differentIndividualsAnonymous() {
        return set(DifferentIndividuals(AnonymousIndividual(), AnonymousIndividual(),
            AnonymousIndividual()));
    }

    private static List<OWLAxiom> classAssertionWithAnonymousIndividual() {
        OWLIndividual ind = AnonymousIndividual("a");
        OWLClass cls = Class(iri("A"));
        return set(ClassAssertion(A, i), Declaration(A));
    }

    private static List<OWLAxiom> chainedAnonymousIndividuals() {
        IRI annoPropIRI = IRI("http://owlapi.sourceforge.net/ontology#", "annoProp");
        OWLAnnotationProperty property = AnnotationProperty(annoPropIRI);
        IRI subject = IRI("http://owlapi.sourceforge.net/ontology#", "subject");
        OWLAnonymousIndividual individual1 = AnonymousIndividual();
        OWLAnonymousIndividual individual2 = AnonymousIndividual();
        OWLAnonymousIndividual individual3 = AnonymousIndividual();
        OWLAnnotationAssertionAxiom annoAssertion1 =
            AnnotationAssertion(property, subject, individual1);
        OWLAnnotationAssertionAxiom annoAssertion2 =
            AnnotationAssertion(property, individual1, individual2);
        OWLAnnotationAssertionAxiom annoAssertion3 =
            AnnotationAssertion(property, individual2, individual3);
        return set(Declaration(NamedIndividual(subject)), annoAssertion1, annoAssertion2,
            annoAssertion3);
    }

    private static List<OWLAxiom> anonymousIndividuals() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        OWLObjectProperty p = ObjectProperty(iri("p"));
        return set(ObjectPropertyAssertion(p, NamedIndividual(iri("i1")), ind),
            ObjectPropertyAssertion(p, ind, NamedIndividual(iri("i2"))));
    }

    private static List<OWLAxiom> anonymousIndividuals2() {
        // Originally submitted by Timothy Redmond
        String ns = "http://another.com/ont";
        OWLClass a = Class(IRI(ns + "#", "A"));
        OWLAnnotationProperty p = AnnotationProperty(IRI(ns + "#", "p"));
        OWLObjectProperty q = ObjectProperty(IRI(ns + "#", "q"));
        OWLAnonymousIndividual h = AnonymousIndividual();
        OWLAnonymousIndividual i = AnonymousIndividual();
        return set(AnnotationAssertion(p, a.getIRI(), h), ClassAssertion(a, h),
            ObjectPropertyAssertion(q, h, i),
            AnnotationAssertion(RDFSLabel(), h, Literal("Second", "en")));
    }

    private static List<OWLAxiom> anonymousIndividualRoundtrip() {
        OWLAnonymousIndividual ind = AnonymousIndividual();
        OWLClass cls = Class(iri("A"));
        OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
        OWLAnnotationAssertionAxiom ax = AnnotationAssertion(prop, cls.getIRI(), ind);
        OWLObjectProperty p = ObjectProperty(iri("p"));
        OWLAnonymousIndividual anon1 = AnonymousIndividual();
        OWLAnonymousIndividual anon2 = AnonymousIndividual();
        OWLNamedIndividual ind1 = NamedIndividual(iri("j"));
        OWLNamedIndividual ind2 = NamedIndividual(iri("i"));
        return set(ax, Declaration(cls), Declaration(p),
            df.getOWLObjectPropertyAssertionAxiom(p, ind1, ind2),
            df.getOWLObjectPropertyAssertionAxiom(p, anon1, anon1),
            df.getOWLObjectPropertyAssertionAxiom(p, anon2, ind2),
            df.getOWLObjectPropertyAssertionAxiom(p, ind2, anon2));
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
        FILE_ROUND_TRIP.forEach(file -> formats().forEach(f -> list.add(Arguments.of(file, f))));
        // use a different equal method
        Arrays
            .asList("extraBlankNodes.owl", "testBlankNodes2.ttl", "testBlankNodesAssertions.ttl",
                "owlxml_anonloop.owx")
            .forEach(file -> formats().forEach(f -> list.add(Arguments.of(file, f))));

        return list;
    }

    static List<Arguments> roundTrip() {
        List<Arguments> list = new ArrayList<>();
        literalWithEscapesNoRioRDFXMLTestCase()
            .forEach(axioms -> formats().forEach(f -> list.add(Arguments.of(axioms, f))));
        axiomsRoundTrippingTestCase()
            .forEach(axioms -> formats().forEach(f -> list.add(Arguments.of(axioms, f))));
        axiomsRoundTrippingWithEntitiesTestCase()
            .forEach(axioms -> formats().forEach(f -> list.add(Arguments.of(axioms, f))));
        relativeURITestCase()
            .forEach(axioms -> formats().forEach(f -> list.add(Arguments.of(axioms, f))));
        axiomsRoundTrippingNoManchesterSyntaxTestCase()
            .forEach(axioms -> formatsSkip(ManchesterSyntaxDocumentFormat.class)
                .forEach(f -> list.add(Arguments.of(axioms, f))));
        return list;
    }

    static List<Arguments> fileRoundTripNoManSyntax() {
        List<Arguments> list = new ArrayList<>();
        Arrays.asList("AnonymousInverses.rdf", "TestParser08.rdf", "nodeid.rdf")
            .forEach(file -> formatsSkip(ManchesterSyntaxDocumentFormat.class)
                .forEach(f -> list.add(Arguments.of(file, f))));
        return list;
    }

    @ParameterizedTest
    @MethodSource({"fileRoundTrip", "fileRoundTripNoManSyntax"})
    void fileRoundTripTestCase(String fileName, OWLDocumentFormat f) {
        roundTrip(loadOntology(fileName), f);
    }

    @Test
    void fileRoundTripSubClassOfUntypedOWLClassStrictTestCase()
        throws OWLOntologyCreationException {
        config = config.setStrict(true);
        URL url = getClass().getResource("/SubClassOfUntypedOWLClass.rdf");
        OWLOntology ont = m.loadOntologyFromOntologyDocument(
            new IRIDocumentSource(IRI.create(url), null, null), config.setReportStackTraces(true));
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
    // void testOBO(OWLOntology o) throws Exception {
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
    void roundTripFormats(List<OWLAxiom> axioms, OWLDocumentFormat f) {
        roundTripOntology(o(axioms), f);
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

    public void testKRSS2(OWLOntology o) {
        roundTripOntology(o, new KRSS2DocumentFormat());
    }

    public void testKRSS(OWLOntology o) {
        roundTripOntology(o, new KRSS2DocumentFormat());
    }

    public void testDLSyntax(OWLOntology o) {
        roundTripOntology(o, new DLSyntaxDocumentFormat());
    }

    protected OWLOntology createOntology(String fileName, OWLDocumentFormat f) {
        OWLOntology o = ontologyFromClasspathFile(fileName, f);
        if (logger.isTraceEnabled()) {
            logger.trace("ontology as parsed from input file:");
            o.axioms().forEach(ax -> logger.trace(ax.toString()));
        }
        return o;
    }

    @ParameterizedTest
    @MethodSource("formats")
    void literalWithEscapesTestCase(OWLDocumentFormat f) {
        if (RioRDFXMLDocumentFormat.class.isInstance(f)) {
            // Rio normalizes literals differently, got its own test
            return;
        }

        List<OWLAxiom> axioms = literalWithEscapes();
        OWLOntology o = getOWLOntology();
        o.add(axioms);
        roundTrip(o, f);
    }

    protected static List<OWLAxiom> literalWithEscapes() {
        OWLClass cls = Class(IRI("http://owlapi.sourceforge.net/ontology#", "A"));
        OWLAnnotationProperty prop =
            AnnotationProperty(IRI("http://owlapi.sourceforge.net/ontology#", "prop"));
        List<OWLAxiom> axioms = set(AnnotationAssertion(prop, cls.getIRI(), Literal("\\")),
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
    void ontologyAnnotationsTestCase(OWLDocumentFormat f) {
        if (RDFJsonDocumentFormat.class.isInstance(f)) {
            // XXX RDFJsonDocumentFormat ignored. The parser parses the annotation correctly but it
            // is not
            // associated to the ontology.
            return;
        }
        roundTrip(ontologyAnnotation(), f);
    }

    protected OWLOntology ontologyAnnotation() {
        OWLOntology ont = getOWLOntology();
        OWLAnnotationProperty prop = AnnotationProperty(
            IRI("http://www.semanticweb.org/ontologies/test/annotationont#", "prop"));
        OWLLiteral value = Literal(33);
        OWLAnnotation annotation = Annotation(prop, value);
        OWLAnnotation builtin = Annotation(df.getOWLVersionInfo(), df.getOWLLiteral("x"));
        ont.applyChange(new AddOntologyAnnotation(ont, annotation));
        ont.applyChange(new AddOntologyAnnotation(ont, builtin));
        ont.addAxiom(Declaration(prop));
        return ont;
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyAnnotation() {
        plainEqual(ontologyAnnotation(), true);
    }

    @Test
    void testCorrectOntologyIRI() {
        OWLOntology ont =
            loadOntologyFromString(ONTOLOGY_IRI_TEST_CASE, new RDFXMLDocumentFormat());
        OWLOntologyID id = ont.getOntologyID();
        assertEquals("http://www.test.com/right.owl", id.getOntologyIRI().get().toString());
    }

    @ParameterizedTest
    @MethodSource("formats")
    void ontologyIRITestCase(OWLDocumentFormat f) {
        roundTrip(loadOntologyFromString(ONTOLOGY_IRI_TEST_CASE, new RDFXMLDocumentFormat()), f);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyIRI() {
        plainEqual(loadOntologyFromString(ONTOLOGY_IRI_TEST_CASE, new RDFXMLDocumentFormat()),
            true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void ontologyVersionIRITestCase(OWLDocumentFormat f) {
        roundTrip(ontologyVersion(), f);
    }

    protected OWLOntology ontologyVersion() {
        IRI ontIRI = IRI("http://www.semanticweb.org/owlapi/", "ontology");
        IRI versionIRI = IRI("http://www.semanticweb.org/owlapi/ontology/", "version");
        OWLOntologyID ontologyID = new OWLOntologyID(optional(ontIRI), optional(versionIRI));
        return getOWLOntology(ontologyID);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSameOntologyVersion() {
        plainEqual(ontologyVersion(), true);
    }

    @ParameterizedTest
    @MethodSource("formats")
    void threeLayersOfAnnotationsTestCase(OWLDocumentFormat f) {
        if (ManchesterSyntaxDocumentFormat.class.isInstance(f)) {
            // not supported in Manchester syntax
            return;
        }
        roundTrip(threeLayersOfAnnotations(), f);
    }

    @Test
    void roundTripRDFXMLAndFunctionalShouldBeSamethreeLayersAnnotations() {
        plainEqual(threeLayersOfAnnotations(), true);
    }

    protected OWLOntology threeLayersOfAnnotations() {
        OWLOntology o = getOWLOntology(iri("urn:nested:", "ontology"));
        OWLClass dbxref = df.getOWLClass(iri("urn:obo:", "DbXref"));
        OWLClass definition = df.getOWLClass(iri("urn:obo:", "Definition"));
        OWLObjectProperty adjacent_to = df.getOWLObjectProperty(iri("urn:obo:", "adjacent_to"));
        OWLAnnotationProperty hasDefinition =
            df.getOWLAnnotationProperty(iri("urn:obo:", "hasDefinition"));
        OWLAnnotationProperty hasdbxref = df.getOWLAnnotationProperty(iri("urn:obo:", "hasDbXref"));
        OWLDataProperty hasuri = df.getOWLDataProperty(iri("urn:obo:", "hasURI"));
        OWLAnonymousIndividual ind1 = df.getOWLAnonymousIndividual();
        o.add(df.getOWLClassAssertionAxiom(dbxref, ind1));
        o.add(df.getOWLDataPropertyAssertionAxiom(hasuri, ind1,
            df.getOWLLiteral("urn:SO:SO_ke", OWL2Datatype.XSD_ANY_URI)));
        OWLAnonymousIndividual ind2 = df.getOWLAnonymousIndividual();
        o.add(df.getOWLClassAssertionAxiom(definition, ind2));
        o.add(df.getOWLAnnotationAssertionAxiom(hasdbxref, ind2, ind1));
        o.add(df.getOWLAnnotationAssertionAxiom(hasDefinition, adjacent_to.getIRI(), ind2));
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
    void roundTripOWLXMLToRioTurtleTestCase(OWLDocumentFormat f) {
        roundTrip(loadOntologyFromString(original, new OWLXMLDocumentFormat()), f);
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

    protected OWLOntology o(OWLAxiom a) {
        OWLOntology ont = getOWLOntology();
        ont.add(a);
        ont.unsortedSignature().filter(e -> !e.isBuiltIn() && !ont.isDeclared(e, INCLUDED))
            .forEach(e -> ont.add(Declaration(e)));
        return ont;
    }

    protected OWLOntology o(Collection<OWLAxiom> a) {
        OWLOntology ont = getOWLOntology();
        ont.add(a);
        ont.unsortedSignature().filter(e -> !e.isBuiltIn() && !ont.isDeclared(e, INCLUDED))
            .forEach(e -> ont.add(Declaration(e)));
        return ont;
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingNoManchesterSyntaxTestCase() {
        // no valid Manchester OWL Syntax roundtrip
        OWLObjectPropertyExpression p = ObjectProperty(iri("p")).getInverseProperty();
        OWLObjectPropertyExpression q = ObjectProperty(iri("q")).getInverseProperty();
        OWLClass clsA = Class(iri("A"));
        return Arrays.asList(set(AsymmetricObjectProperty(p)),
            set(EquivalentObjectProperties(p, q)), set(FunctionalObjectProperty(p)),
            set(InverseFunctionalObjectProperty(p)), set(IrreflexiveObjectProperty(p)),
            set(ObjectPropertyDomain(p, clsA)), set(ObjectPropertyRange(p, clsA)),
            set(ReflexiveObjectProperty(p)), set(SubObjectPropertyOf(p, q)),
            set(SymmetricObjectProperty(p)), set(TransitiveObjectProperty(p)));
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingTestCase() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        IRI iriA = iri("A");
        OWLClass clsA = Class(iriA);
        OWLClass clsB = Class(iri("B"));
        OWLDataProperty dp = DataProperty(iri("p"));
        OWLDataProperty dq = DataProperty(iri("q"));
        OWLObjectProperty op = ObjectProperty(iri("op"));
        OWLObjectProperty oq = ObjectProperty(iri("oq"));
        OWLDataProperty dpA = DataProperty(iri("dpropA"));
        OWLDataProperty dpB = DataProperty(iri("dpropB"));
        OWLDataProperty dpC = DataProperty(iri("dpropC"));
        OWLObjectProperty propA = ObjectProperty(iri("propA"));
        OWLObjectProperty propB = ObjectProperty(iri("propB"));
        OWLObjectProperty propC = ObjectProperty(iri("propC"));
        OWLObjectProperty propD = ObjectProperty(iri("propD"));
        OWLAnnotationProperty apropA = AnnotationProperty(iri("apropA"));
        OWLAnnotationProperty apropB = AnnotationProperty(iri("apropB"));
        OWLNamedIndividual ind = NamedIndividual(iri("i"));
        OWLNamedIndividual indj = NamedIndividual(iri("j"));
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

        List<List<OWLAxiom>> list = new ArrayList<>();
        list.add(swrlRuleAlternateNS(HTTP_WWW_OWLAPI, dp, op, ind, indj));
        list.add(swrlRule());
        list.add(set(SubPropertyChainOf(Arrays.asList(propA, propB, propC), propD)));
        list.add(set(AsymmetricObjectProperty(op)));
        list.add(set(DifferentIndividuals(createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual(), createIndividual(),
            createIndividual(), createIndividual(), createIndividual(), createIndividual())));
        list.add(set(SubClassOf(clsA, ObjectSomeValuesFrom(op, ObjectSomeValuesFrom(op, clsB))),
            Declaration(clsA), Declaration(clsB)));
        list.add(set(Declaration(RDFSLabel()), Declaration(peter),
            AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)));
        list.add(set(Declaration(RDFSLabel()), Declaration(peter, eAnn1, eAnn2),
            AnnotationAssertion(RDFSLabel(), peter.getIRI(), Literal("X", "en"), ann1, ann2)));
        list.add(set(InverseObjectProperties(oq, op)));
        list.add(set(InverseObjectProperties(op, oq)));
        list.add(set(Declaration(clsA), AnnotationAssertion(apropA, clsA.getIRI(),
            IRI("http://www.semanticweb.org/owlapi#", "object"))));
        list.add(set(SubClassOf(clsA, clsB, singleton(annoInner))));
        list.add(set(AnnotationPropertyDomain(RDFSComment(), iriA)));
        list.add(set(AnnotationPropertyRange(RDFSComment(), iriA)));
        list.add(set(SubAnnotationPropertyOf(apropA, RDFSLabel())));
        list.add(set(SubClassOf(clsA, DataMaxCardinality(3, dp, Integer()))));
        list.add(set(SubClassOf(clsA, DataMinCardinality(3, dp, Integer()))));
        list.add(set(SubClassOf(clsA, DataExactCardinality(3, dp, Integer()))));
        list.add(set(DataPropertyRange(dp, DataUnionOf(disj1, disj2))));
        list.add(set(
            HasKey(singleton(Annotation(apropA, Literal("Test", ""))), clsA, propA, propB, propC),
            Declaration(apropA), Declaration(propA), Declaration(propB), Declaration(propC)));
        list.add(
            set(DisjointClasses(asUnorderedSet(Stream.generate(() -> createClass()).limit(6000)))));
        list.add(set(SubClassOf(clsB, ObjectSomeValuesFrom(op.getInverseProperty(), clsA))));
        list.add(set(SubDataPropertyOf(dp, dq)));
        list.add(set(DataPropertyAssertion(dp, ind, Literal(33.3))));
        list.add(set(NegativeDataPropertyAssertion(dp, ind, Literal(33.3)),
            NegativeDataPropertyAssertion(dp, ind, Literal("weasel", "")),
            NegativeDataPropertyAssertion(dp, ind, Literal("weasel"))));
        list.add(set(FunctionalDataProperty(dp)));
        list.add(set(DataPropertyDomain(dp, Class(iri("A")))));
        list.add(set(DataPropertyRange(dp, TopDatatype())));
        list.add(set(DisjointDataProperties(dpA, dpB, dpC), Declaration(dpA), Declaration(dpB),
            Declaration(dpC)));
        list.add(set(DisjointDataProperties(dpA, dpB)));
        list.add(set(EquivalentDataProperties(dp, dq)));
        list.add(set(AsymmetricObjectProperty(op)));
        list.add(
            set(DatatypeDefinition(datatype, DataComplementOf(Integer())), Declaration(datatype)));
        list.add(set(DifferentIndividuals(ind, indj),
            DifferentIndividuals(ind, NamedIndividual(iri("k")))));
        list.add(set(
            DifferentIndividuals(ind, indj, NamedIndividual(iri("k")), NamedIndividual(iri("l")))));
        list.add(set(DisjointObjectProperties(propA, propB, propC), Declaration(propA),
            Declaration(propB), Declaration(propC)));
        list.add(set(DisjointObjectProperties(propA, propB)));
        list.add(
            set(EquivalentObjectProperties(propA, propB), Declaration(propA), Declaration(propB)));
        list.add(set(FunctionalObjectProperty(op)));
        list.add(set(InverseFunctionalObjectProperty(op)));
        list.add(set(IrreflexiveObjectProperty(op)));
        list.add(set(DifferentIndividuals(
            asUnorderedSet(Stream.generate(() -> createIndividual()).limit(1000)))));
        list.add(set(AnnotationAssertion(apropA, clsA.getIRI(), Literal("abc", "en")),
            Declaration(clsA)));
        list.add(set(AnnotationAssertion(apropA, iriA, Literal("abc", "en")),
            AnnotationAssertion(apropA, iriA, Literal("abcd", "")),
            AnnotationAssertion(apropA, iriA, Literal("abcde")),
            AnnotationAssertion(apropA, iriA, Literal("abcdef", OWL2Datatype.XSD_STRING)),
            Declaration(clsA)));
        list.add(set(NegativeObjectPropertyAssertion(op, ind, indj)));
        list.add(set(ObjectPropertyAssertion(op, ind, indj)));
        list.add(set(SubPropertyChainOf(Arrays.asList(propA, propB, propC), propD, set(
            Annotation(apropA, Literal("Test", "en")), Annotation(apropB, Literal("Test", ""))))));
        list.add(set(ObjectPropertyDomain(op, clsA)));
        list.add(set(ObjectPropertyRange(op, clsA)));
        list.add(set(Declaration(Class(IRI("http://www.test.com/ontology#", "Class%37A"))),
            Declaration(ObjectProperty(IRI("http://www.test.com/ontology#", "prop%37A")))));
        list.add(set(ReflexiveObjectProperty(op)));
        list.add(set(SameIndividual(ind, indj)));
        list.add(set(DataPropertyAssertion(dp, ind, Literal("Test \"literal\"\nStuff"))));
        list.add(set(DataPropertyAssertion(dp, ind, Literal("Test \"literal\"")),
            DataPropertyAssertion(dp, ind, Literal("Test 'literal'")),
            DataPropertyAssertion(dp, ind, Literal("Test \"\"\"literal\"\"\""))));
        list.add(set(SubObjectPropertyOf(op, oq)));
        list.add(set(SymmetricObjectProperty(op)));
        list.add(set(TransitiveObjectProperty(op)));
        list.add(set(DataPropertyAssertion(dp, ind, Literal(3)),
            DataPropertyAssertion(dp, ind, Literal(33.3)),
            DataPropertyAssertion(dp, ind, Literal(true)),
            DataPropertyAssertion(dp, ind, Literal(33.3f)),
            DataPropertyAssertion(dp, ind, Literal("33.3"))));
        return list;
    }

    protected static List<OWLAxiom> swrlRule() {
        String HTTP_WWW_OWLAPI = "http://www.owlapi#";
        OWLDataProperty dp = DataProperty(iri("p"));
        OWLObjectProperty op = ObjectProperty(iri("op"));
        OWLNamedIndividual ind = NamedIndividual(iri("i"));
        OWLNamedIndividual indj = NamedIndividual(iri("j"));

        String URN_SWRL_VAR = "urn:swrl:var#";
        SWRLVariable varX = df.getSWRLVariable(URN_SWRL_VAR, "x");
        SWRLVariable varY = df.getSWRLVariable(URN_SWRL_VAR, "y");
        SWRLVariable varZ = df.getSWRLVariable(URN_SWRL_VAR, "z");
        Set<SWRLAtom> body = new HashSet<>();
        body.add(df.getSWRLClassAtom(Class(iri("A")), varX));
        SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(ind);
        SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(indj);
        body.add(df.getSWRLClassAtom(Class(iri("D")), indIArg));
        body.add(df.getSWRLClassAtom(Class(iri("B")), varX));
        SWRLVariable varQ = df.getSWRLVariable(URN_SWRL_VAR, "q");
        SWRLVariable varR = df.getSWRLVariable(URN_SWRL_VAR, "r");
        body.add(df.getSWRLDataPropertyAtom(dp, varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = df.getSWRLLiteralArgument(lit);
        body.add(df.getSWRLDataPropertyAtom(dp, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(df.getSWRLClassAtom(Class(iri("C")), varX));
        head.add(df.getSWRLObjectPropertyAtom(op, varY, varZ));
        head.add(df.getSWRLSameIndividualAtom(varX, varY));
        head.add(df.getSWRLSameIndividualAtom(indIArg, indJArg));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, Class(iri("A")));
        head.add(df.getSWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = new ArrayList<>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(df.getSWRLBuiltInAtom(IRI(HTTP_WWW_OWLAPI, "myBuiltIn"), args));
        return set(df.getSWRLRule(body, head));
    }

    protected static List<OWLAxiom> swrlRuleAlternateNS(String HTTP_WWW_OWLAPI, OWLDataProperty dp,
        OWLObjectProperty op, OWLNamedIndividual ind, OWLNamedIndividual indj) {
        SWRLVariable varX = df.getSWRLVariable(HTTP_WWW_OWLAPI, "x");
        SWRLVariable varY = df.getSWRLVariable(HTTP_WWW_OWLAPI, "y");
        SWRLVariable varZ = df.getSWRLVariable(HTTP_WWW_OWLAPI, "z");
        Set<SWRLAtom> body = new HashSet<>();
        body.add(df.getSWRLClassAtom(Class(iri("A")), varX));
        SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(ind);
        SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(indj);
        body.add(df.getSWRLClassAtom(Class(iri("D")), indIArg));
        body.add(df.getSWRLClassAtom(Class(iri("B")), varX));
        SWRLVariable varQ = df.getSWRLVariable(HTTP_WWW_OWLAPI, "q");
        SWRLVariable varR = df.getSWRLVariable(HTTP_WWW_OWLAPI, "r");
        body.add(df.getSWRLDataPropertyAtom(dp, varX, varQ));
        OWLLiteral lit = Literal(33);
        SWRLLiteralArgument litArg = df.getSWRLLiteralArgument(lit);
        body.add(df.getSWRLDataPropertyAtom(dp, varY, litArg));
        Set<SWRLAtom> head = new HashSet<>();
        head.add(df.getSWRLClassAtom(Class(iri("C")), varX));
        head.add(df.getSWRLObjectPropertyAtom(op, varY, varZ));
        head.add(df.getSWRLSameIndividualAtom(varX, varY));
        head.add(df.getSWRLSameIndividualAtom(indIArg, indJArg));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(varX, varZ));
        head.add(df.getSWRLDifferentIndividualsAtom(indIArg, indJArg));
        OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, Class(iri("A")));
        head.add(df.getSWRLClassAtom(svf, varX));
        List<SWRLDArgument> args = new ArrayList<>();
        args.add(varQ);
        args.add(varR);
        args.add(litArg);
        head.add(df.getSWRLBuiltInAtom(IRI(HTTP_WWW_OWLAPI, "myBuiltIn"), args));
        return set(df.getSWRLRule(body, head));
    }

    static List<List<OWLAxiom>> axiomsRoundTrippingWithEntitiesTestCase() {
        IRI iriA = iri("A");
        OWLClass clsA = Class(iriA);
        OWLAnnotationProperty apropA = AnnotationProperty(iri("apropA"));
        OWLAnnotationProperty apropB = AnnotationProperty(iri("apropB"));
        return Collections.singletonList(
            set(Declaration(clsA), AnnotationAssertion(apropA, clsA.getIRI(), Literal("value1")),
                AnnotationAssertion(apropB, clsA.getIRI(), Literal("value2"))));
    }

    static List<List<OWLAxiom>> relativeURITestCase() {
        return Collections.singletonList(
            set(Declaration(Class(IRI(IRI.getNextDocumentIRI(uriBase) + "/", "Office")))));
    }

    @Test
    void shouldThrowMeaningfulException() {
        OWLOntology ontology = getOWLOntology();
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
