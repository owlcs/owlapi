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

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;
import org.semanticweb.owlapi.vocab.OWLFacet;

import com.google.common.collect.Sets;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class AxiomsRoundTrippingTestCase extends AxiomsRoundTrippingBase {

    private static final OWLClass clsA = Class(iri("A"));
    private static final OWLClass clsB = Class(iri("B"));
    private static final OWLDataProperty dp = DataProperty(iri("p"));
    private static final OWLDataProperty dq = DataProperty(iri("q"));
    private static final OWLObjectProperty op = ObjectProperty(iri("op"));
    private static final OWLObjectProperty oq = ObjectProperty(iri("oq"));
    private static final OWLDataProperty dpA = DataProperty(iri("dpropA"));
    private static final OWLDataProperty dpB = DataProperty(iri("dpropB"));
    private static final OWLDataProperty dpC = DataProperty(iri("dpropC"));
    private static final OWLObjectProperty propA = ObjectProperty(iri("propA"));
    private static final OWLObjectProperty propB = ObjectProperty(iri("propB"));
    private static final OWLObjectProperty propC = ObjectProperty(iri("propC"));
    private static final OWLObjectProperty propD = ObjectProperty(iri("propD"));
    private static final OWLAnnotationProperty apropA = AnnotationProperty(iri(
        "apropA"));
    private static final OWLAnnotationProperty apropB = AnnotationProperty(iri(
        "apropB"));
    private static final OWLNamedIndividual ind = NamedIndividual(iri("i"));
    private static final OWLNamedIndividual indj = NamedIndividual(iri("j"));

    public AxiomsRoundTrippingTestCase(AxiomBuilder f) {
        super(f);
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        return Arrays.asList(
            // ObjectPropertyChainRoundTripping
            () -> singleton(SubPropertyChainOf(Arrays.asList(propA, propB,
                propC), propD)),
            // AsymmetricPropertyRoundTrip
            () -> singleton(AsymmetricObjectProperty(op)),
            // AnonymousRootRoundTripping
            () -> singleton(DifferentIndividuals(createIndividual(),
                createIndividual(), createIndividual(), createIndividual(),
                createIndividual(), createIndividual(), createIndividual(),
                createIndividual(), createIndividual(), createIndividual())),
            // NestedClassExpressionRoundTripping
            () -> Sets.newHashSet(SubClassOf(clsA, ObjectSomeValuesFrom(op,
                ObjectSomeValuesFrom(op, clsB))), Declaration(clsA),
                Declaration(clsB)),
            // AxiomAnnotationsRoundTripping
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                set.add(Declaration(RDFSLabel()));
                OWLEntity entity = NamedIndividual(IRI(
                    "http://www.another.com/ont#peter"));
                set.add(Declaration(entity));
                HashSet<OWLAnnotation> annotations = Sets.newHashSet(Annotation(
                    RDFSLabel(), Literal("Annotation 1")), Annotation(
                        RDFSLabel(), Literal("Annotation 2")));
                OWLAnnotationAssertionAxiom ax = AnnotationAssertion(
                    RDFSLabel(), entity.getIRI(), Literal("X", "en"),
                    annotations);
                set.add(ax);
                return set;
            } ,
            // AxiomAnnotationsAndIdividualAnnotationsRoundTripping
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                set.add(Declaration(RDFSLabel()));
                OWLEntity entity = NamedIndividual(IRI(
                    "http://www.another.com/ont#peter"));
                Set<OWLAnnotation> entityAnnotations = Sets.newHashSet(
                    Annotation(RDFSLabel(), Literal("EntityAnnotation 1")),
                    Annotation(RDFSLabel(), Literal("EntityAnnotation 2")));
                set.add(Declaration(entity, entityAnnotations));
                Set<OWLAnnotation> annotations = Sets.newHashSet(Annotation(
                    RDFSLabel(), Literal("Annotation 1")), Annotation(
                        RDFSLabel(), Literal("Annotation 2")));
                OWLAnnotationAssertionAxiom ax = AnnotationAssertion(
                    RDFSLabel(), entity.getIRI(), Literal("X", "en"),
                    annotations);
                set.add(ax);
                return set;
            } ,
            // InversePropertiesAxiom2
            () -> singleton(InverseObjectProperties(oq, op)),
            // InversePropertiesAxiom
            () -> singleton(InverseObjectProperties(op, oq)),
            // AnnotationAssertionWithIRI
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(Declaration(clsA));
                IRI object = IRI("http://www.semanticweb.org/owlapi#object");
                axioms.add(AnnotationAssertion(apropA, clsA.getIRI(), object));
                return axioms;
            } ,
            // AnnotationOnAnnotation
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLAnnotation annoOuterOuter1 = Annotation(AnnotationProperty(
                    iri("myOuterOuterLabel1")), Literal("Outer Outer label 1"));
                OWLAnnotation annoOuterOuter2 = Annotation(AnnotationProperty(
                    iri("myOuterOuterLabel2")), Literal("Outer Outer label 2"));
                Set<OWLAnnotation> outerOuterAnnos = new HashSet<>();
                outerOuterAnnos.add(annoOuterOuter1);
                outerOuterAnnos.add(annoOuterOuter2);
                OWLAnnotation annoOuter = Annotation(AnnotationProperty(iri(
                    "myOuterLabel")), Literal("Outer label"), outerOuterAnnos);
                OWLAnnotation annoInner = Annotation(AnnotationProperty(iri(
                    "myLabel")), Literal("Label"), singleton(annoOuter));
                OWLAxiom ax = SubClassOf(Class(iri("A")), Class(iri("B")),
                    singleton(annoInner));
                axioms.add(ax);
                return axioms;
            } ,
            // OWLAnnotationPropertyDomain
            () -> singleton(AnnotationPropertyDomain(RDFSComment(), IRI(
                "http://ont.com#A"))),
            // OWLAnnotationPropertyRangeAxiom
            () -> singleton(AnnotationPropertyRange(RDFSComment(), IRI(
                "http://ont.com#A"))),
            // OWLSubAnnotationPropertyOfAxiom
            () -> singleton(SubAnnotationPropertyOf(apropA, RDFSLabel())),
            // DataMaxQualifiedCardinality
            () -> singleton(SubClassOf(clsA, DataMaxCardinality(3, dp,
                Integer()))),
            // DataMinQualifiedCardinality
            () -> singleton(SubClassOf(clsA, DataMinCardinality(3, dp,
                Integer()))),
            // DataQualifiedCardinality
            () -> singleton(SubClassOf(clsA, DataExactCardinality(3, dp,
                Integer()))),
            // DataUnionOfTestCase2
            () -> {
                OWLDatatype dt = Datatype(IRI("file:/c/test.owlapi#SSN"));
                OWLFacetRestriction fr = FacetRestriction(OWLFacet.PATTERN,
                    Literal("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
                OWLDataRange dr = DatatypeRestriction(Datatype(IRI(
                    "http://www.w3.org/2001/XMLSchema#string")), fr);
                OWLDataIntersectionOf disj1 = DataIntersectionOf(
                    DataComplementOf(dr), dt);
                // here I negate dr
                OWLDataIntersectionOf disj2 = DataIntersectionOf(
                    DataComplementOf(dt), dr);
                // here I negate dt
                OWLDataUnionOf union = DataUnionOf(disj1, disj2);
                OWLDataPropertyRangeAxiom ax = DataPropertyRange(dp, union);
                return singleton(ax);
            } ,
            // HasKeyAnnotated
            () -> {
                OWLAnnotation anno = Annotation(apropA, Literal("Test", ""));
                OWLHasKeyAxiom ax = HasKey(singleton(anno), clsA, propA, propB,
                    propC);
                return Sets.newHashSet(ax, Declaration(apropA), Declaration(
                    propA), Declaration(propB), Declaration(propC));
            } ,
            // LargeDisjointClassesAxiom
            () -> singleton(DisjointClasses(asSet(Stream.generate(
                () -> createClass()).limit(1000)))),
            // ObjectInverseOf
            () -> singleton(SubClassOf(Class(iri("B")), ObjectSomeValuesFrom(op
                .getInverseProperty(), clsA))),
            // SubDataPropertyOf
            () -> singleton(SubDataPropertyOf(dp, dq)),
            // OWLDataPropertyAssertion
            () -> singleton(DataPropertyAssertion(dp, ind, Literal(33.3))),
            // NegativeDataPropertyAssertion
            () -> Sets.newHashSet(NegativeDataPropertyAssertion(dp, ind,
                Literal(33.3)), NegativeDataPropertyAssertion(dp, ind, Literal(
                    "weasel", "")), NegativeDataPropertyAssertion(dp, ind,
                        Literal("weasel"))),
            // FunctionalDataProperty
            () -> singleton(FunctionalDataProperty(dp)),
            // DataPropertyDomain
            () -> singleton(DataPropertyDomain(dp, Class(iri("A")))),
            // DataPropertyRange
            () -> singleton(DataPropertyRange(dp, TopDatatype())),
            // DisjointDataPropertiesRoundTripping
            () -> Sets.newHashSet(DisjointDataProperties(dpA, dpB, dpC),
                Declaration(dpA), Declaration(dpB), Declaration(dpC)),
            // DisjointDataProperties
            () -> singleton(DisjointDataProperties(dpA, dpB)),
            // EquivalentDataProperties
            () -> singleton(EquivalentDataProperties(dp, dq)),
            // AsymmetricObjectProperty
            () -> singleton(AsymmetricObjectProperty(op)),
            // DatatypeDefinition
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLDatatype datatype = Datatype(IRI(
                    "http://www.ont.com/myont/mydatatype"));
                OWLDataRange dr = DataComplementOf(Integer());
                axioms.add(DatatypeDefinition(datatype, dr));
                axioms.add(Declaration(datatype));
                return axioms;
            } ,
            // DifferentIndividualsPairwise
            () -> Sets.newHashSet(DifferentIndividuals(ind, indj),
                DifferentIndividuals(ind, NamedIndividual(iri("k")))),
            // DifferentIndividuals
            () -> singleton(DifferentIndividuals(ind, indj, NamedIndividual(iri(
                "k")), NamedIndividual(iri("l")))),
            // DisjointObjectPropertiesRoundTripping
            () -> Sets.newHashSet(DisjointObjectProperties(propA, propB, propC),
                Declaration(propA), Declaration(propB), Declaration(propC)),
            // DisjointObjectProperties
            () -> singleton(DisjointObjectProperties(propA, propB)),
            // EquivalentObjectProperties
            () -> Sets.newHashSet(EquivalentObjectProperties(propA, propB),
                Declaration(propA), Declaration(propB)),
            // FunctionalObjectProperty
            () -> singleton(FunctionalObjectProperty(op)),
            // IRISubstring
            () -> {
                XMLWriterPreferences.getInstance().setUseNamespaceEntities(
                    true);
                return Sets.newHashSet(Declaration(clsA), AnnotationAssertion(
                    apropA, clsA.getIRI(), Literal("value1")),
                    AnnotationAssertion(apropB, clsA.getIRI(), Literal(
                        "value2")));
            } ,
            // InverseFunctionalObjectProperty
            () -> singleton(InverseFunctionalObjectProperty(op)),
            // IrreflexiveObjectProperty
            () -> singleton(IrreflexiveObjectProperty(op)),
            // LargeDifferentIndividuals
            () -> singleton(DifferentIndividuals(asSet(Stream.generate(
                () -> createIndividual()).limit(1000)))),
            // Literal
            () -> {
                OWLLiteral literalWithLang = Literal("abc", "en");
                OWLAnnotationAssertionAxiom ax = AnnotationAssertion(apropA,
                    clsA.getIRI(), literalWithLang);
                return Sets.newHashSet(ax, Declaration(clsA));
            } ,
            // NegativeObjectPropertyAssertion
            () -> {
                return singleton(NegativeObjectPropertyAssertion(op, ind,
                    indj));
            } ,
            // ObjectPropertyAssertion
            () -> singleton(ObjectPropertyAssertion(op, ind, indj)),
            // ObjectPropertyChainAnnotatedRoundTripping
            () -> {
                List<OWLObjectProperty> props = Arrays.asList(propA, propB,
                    propC);
                Set<OWLAnnotation> annos = Sets.newHashSet(Annotation(apropA,
                    Literal("Test", "en")), Annotation(apropB, Literal("Test",
                        "")));
                OWLAxiom ax = SubPropertyChainOf(props, propD, annos);
                return singleton(ax);
            } ,
            // ObjectPropertyDomain
            () -> singleton(ObjectPropertyDomain(op, clsA)),
            // ObjectPropertyRange
            () -> singleton(ObjectPropertyRange(op, clsA)),
            // PercentCharacterInEntityName
            () -> Sets.newHashSet(Declaration(Class(IRI(
                "http://www.test.com/ontology#Class%37A"))), Declaration(
                    ObjectProperty(IRI(
                        "http://www.test.com/ontology#prop%37A")))),
            // ReflexiveObjectProperty
            () -> singleton(ReflexiveObjectProperty(op)),
            // SWRLRuleAlternateNS
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                SWRLVariable varX = df.getSWRLVariable("http://www.owlapi#x");
                SWRLVariable varY = df.getSWRLVariable("http://www.owlapi#y");
                SWRLVariable varZ = df.getSWRLVariable("http://www.owlapi#z");
                Set<SWRLAtom> body = new HashSet<>();
                body.add(df.getSWRLClassAtom(Class(iri("A")), varX));
                SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(
                    ind);
                SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(
                    indj);
                body.add(df.getSWRLClassAtom(Class(iri("D")), indIArg));
                body.add(df.getSWRLClassAtom(Class(iri("B")), varX));
                SWRLVariable varQ = df.getSWRLVariable("http://www.owlapi#q");
                SWRLVariable varR = df.getSWRLVariable("http://www.owlapi#r");
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
                OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, Class(
                    iri("A")));
                head.add(df.getSWRLClassAtom(svf, varX));
                List<SWRLDArgument> args = new ArrayList<>();
                args.add(varQ);
                args.add(varR);
                args.add(litArg);
                head.add(df.getSWRLBuiltInAtom(IRI(
                    "http://www.owlapi#myBuiltIn"), args));
                axioms.add(df.getSWRLRule(body, head));
                return axioms;
            } ,
            // SWRLRule
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                SWRLVariable varX = df.getSWRLVariable("urn:swrl#x");
                SWRLVariable varY = df.getSWRLVariable("urn:swrl#y");
                SWRLVariable varZ = df.getSWRLVariable("urn:swrl#z");
                Set<SWRLAtom> body = new HashSet<>();
                body.add(df.getSWRLClassAtom(Class(iri("A")), varX));
                SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(
                    ind);
                SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(
                    indj);
                body.add(df.getSWRLClassAtom(Class(iri("D")), indIArg));
                body.add(df.getSWRLClassAtom(Class(iri("B")), varX));
                SWRLVariable varQ = df.getSWRLVariable("urn:swrl#q");
                SWRLVariable varR = df.getSWRLVariable("urn:swrl#r");
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
                OWLObjectSomeValuesFrom svf = ObjectSomeValuesFrom(op, Class(
                    iri("A")));
                head.add(df.getSWRLClassAtom(svf, varX));
                List<SWRLDArgument> args = new ArrayList<>();
                args.add(varQ);
                args.add(varR);
                args.add(litArg);
                head.add(df.getSWRLBuiltInAtom(IRI(
                    "http://www.owlapi#myBuiltIn"), args));
                axioms.add(df.getSWRLRule(body, head));
                return axioms;
            } ,
            // SameIndividuals
            () -> singleton(SameIndividual(ind, indj)),
            // StringLiteralWithNewLine
            () -> singleton(DataPropertyAssertion(dp, ind, Literal(
                "Test \"literal\"\nStuff"))),
            // StringLiteralWithQuotes
            () -> {
                return Sets.newHashSet(DataPropertyAssertion(dp, ind, Literal(
                    "Test \"literal\"")), DataPropertyAssertion(dp, ind,
                        Literal("Test 'literal'")), DataPropertyAssertion(dp,
                            ind, Literal("Test \"\"\"literal\"\"\"")));
            } ,
            // SubObjectPropertyOf
            () -> singleton(SubObjectPropertyOf(op, oq)),
            // SymmetricObjectProperty
            () -> singleton(SymmetricObjectProperty(op)),
            // TransitiveObjectProperty
            () -> singleton(TransitiveObjectProperty(op)),
            // TypedLiterals
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(DataPropertyAssertion(dp, ind, Literal(3)));
                axioms.add(DataPropertyAssertion(dp, ind, Literal(33.3)));
                axioms.add(DataPropertyAssertion(dp, ind, Literal(true)));
                axioms.add(DataPropertyAssertion(dp, ind, Literal(33.3f)));
                axioms.add(DataPropertyAssertion(dp, ind, Literal("33.3")));
                return axioms;
            } );
    }
}
