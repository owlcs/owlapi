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
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.RDFS_LABEL;

import java.util.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.XMLWriterPreferences;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class AxiomsRoundTrippingTestCase extends AxiomsRoundTrippingBase {

    private static final OWLDataProperty dp = DataProperty(iri("p"));
    private static final OWLDataProperty dq = DataProperty(iri("q"));
    private static final OWLObjectProperty op = ObjectProperty(iri("op"));
    private static final OWLObjectProperty oq = ObjectProperty(iri("oq"));
    private static final OWLObjectProperty propA = ObjectProperty(iri("propA"));
    private static final OWLObjectProperty propB = ObjectProperty(iri("propB"));
    private static final OWLObjectProperty propC = ObjectProperty(iri("propC"));
    private static final OWLObjectProperty propD = ObjectProperty(iri("propD"));

    public AxiomsRoundTrippingTestCase(AxiomBuilder f) {
        super(f);
    }

    @Parameters
    public static List<AxiomBuilder> getData() {
        return Arrays.asList(
            // ObjectPropertyChainRoundTripping
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                List<OWLObjectProperty> props = new ArrayList<>();
                props.add(propA);
                props.add(propB);
                props.add(propC);
                OWLAxiom ax = SubPropertyChainOf(props, propD);
                set.add(ax);
                return set;
            } ,
            // AsymmetricPropertyRoundTrip
            () -> {
                return Collections.<OWLAxiom> singleton(
                    AsymmetricObjectProperty(op));
            } ,
            // AnonymousRootRoundTripping
            () -> Collections.<OWLAxiom> singleton(DifferentIndividuals(
                createIndividual(), createIndividual(), createIndividual(),
                createIndividual(), createIndividual(), createIndividual(),
                createIndividual(), createIndividual(), createIndividual(),
                createIndividual())),
            // NestedClassExpressionRoundTripping
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                OWLObjectProperty prop = ObjectProperty(iri("propP"));
                OWLClass clsA = Class(iri("A"));
                OWLClass clsB = Class(iri("B"));
                OWLClassExpression desc = ObjectSomeValuesFrom(prop,
                    ObjectSomeValuesFrom(prop, clsB));
                OWLAxiom ax = SubClassOf(clsA, desc);
                set.add(ax);
                set.add(Declaration(clsA));
                set.add(Declaration(clsB));
                return set;
            } ,
            // AxiomAnnotationsRoundTripping
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                OWLAnnotationProperty prop = AnnotationProperty(RDFS_LABEL
                    .getIRI());
                set.add(Declaration(prop));
                Set<OWLAnnotation> annotations = new HashSet<>();
                for (int i = 0; i < 2; i++) {
                    OWLLiteral lit = Literal("Annotation " + (i + 1));
                    annotations.add(df.getOWLAnnotation(RDFSLabel(), lit));
                }
                OWLEntity entity = NamedIndividual(IRI(
                    "http://www.another.com/ont#peter"));
                set.add(Declaration(entity));
                OWLAnnotationAssertionAxiom ax = df
                    .getOWLAnnotationAssertionAxiom(prop, entity.getIRI(),
                        Literal("X", "en"), annotations);
                set.add(ax);
                return set;
            } ,
            // AxiomAnnotationsAndIdividualAnnotationsRoundTripping
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                set.add(Declaration(AnnotationProperty(RDFS_LABEL.getIRI())));
                OWLEntity entity = NamedIndividual(IRI(
                    "http://www.another.com/ont#peter"));
                Set<OWLAnnotation> entityAnnotations = new HashSet<>();
                for (int i = 0; i < 2; i++) {
                    OWLLiteral lit = Literal("EntityAnnotation " + (i + 1));
                    entityAnnotations.add(Annotation(RDFSLabel(), lit));
                }
                set.add(Declaration(entity, entityAnnotations));
                Set<OWLAnnotation> annotations = new HashSet<>();
                for (int i = 0; i < 2; i++) {
                    OWLLiteral lit = Literal("Annotation " + (i + 1));
                    annotations.add(Annotation(RDFSLabel(), lit));
                }
                OWLAnnotationAssertionAxiom ax = AnnotationAssertion(
                    AnnotationProperty(RDFS_LABEL.getIRI()), entity.getIRI(),
                    Literal("X", "en"), annotations);
                set.add(ax);
                return set;
            } ,
            // InversePropertiesAxiom2
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                set.add(InverseObjectProperties(oq, op));
                return set;
            } ,
            // InversePropertiesAxiom
            () -> {
                Set<OWLAxiom> set = new HashSet<>();
                set.add(InverseObjectProperties(op, oq));
                return set;
            } ,
            // AnnotationAssertionWithIRI
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLClass cls = Class(iri("ClsA"));
                axioms.add(Declaration(cls));
                IRI object = IRI("http://www.semanticweb.org/owlapi#object");
                OWLAnnotationProperty prop = AnnotationProperty(IRI(
                    "http://www.semanticweb.org/owlapi#prop"));
                axioms.add(AnnotationAssertion(prop, cls.getIRI(), object));
                return axioms;
            } ,
            // AnnotationOnAnnotation
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLAnnotation annoOuterOuter1 = df.getOWLAnnotation(
                    AnnotationProperty(iri("myOuterOuterLabel1")), Literal(
                        "Outer Outer label 1"));
                OWLAnnotation annoOuterOuter2 = df.getOWLAnnotation(
                    AnnotationProperty(iri("myOuterOuterLabel2")), Literal(
                        "Outer Outer label 2"));
                Set<OWLAnnotation> outerOuterAnnos = new HashSet<>();
                outerOuterAnnos.add(annoOuterOuter1);
                outerOuterAnnos.add(annoOuterOuter2);
                OWLAnnotation annoOuter = df.getOWLAnnotation(
                    AnnotationProperty(iri("myOuterLabel")), Literal(
                        "Outer label"), outerOuterAnnos);
                OWLAnnotation annoInner = df.getOWLAnnotation(
                    AnnotationProperty(iri("myLabel")), Literal("Label"),
                    singleton(annoOuter));
                OWLAxiom ax = df.getOWLSubClassOfAxiom(Class(iri("A")), Class(
                    iri("B")), singleton(annoInner));
                axioms.add(ax);
                return axioms;
            } ,
            // OWLAnnotationPropertyDomain
            () -> {
                OWLAnnotationProperty prop = RDFSComment();
                OWLAxiom ax = df.getOWLAnnotationPropertyDomainAxiom(prop, IRI(
                    "http://ont.com#A"));
                return singleton(ax);
            } ,
            // OWLAnnotationPropertyRangeAxiom
            () -> {
                OWLAnnotationProperty prop = RDFSComment();
                OWLAxiom ax = df.getOWLAnnotationPropertyRangeAxiom(prop, IRI(
                    "http://ont.com#A"));
                return singleton(ax);
            } ,
            // OWLSubAnnotationPropertyOfAxiom
            () -> {
                OWLAnnotationProperty subProp = AnnotationProperty(IRI(
                    "http://ont.com#myLabel"));
                OWLAnnotationProperty superProp = RDFSLabel();
                OWLAxiom ax = df.getOWLSubAnnotationPropertyOfAxiom(subProp,
                    superProp);
                return singleton(ax);
            } ,
            // DataMaxQualifiedCardinality
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLDataRange dr = Integer();
                OWLClass base = Class(iri("A"));
                axioms.add(SubClassOf(base, DataMaxCardinality(3, dp, dr)));
                return axioms;
            } ,
            // DataMinQualifiedCardinality
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLDataRange dr = Integer();
                OWLClass base = Class(iri("A"));
                axioms.add(SubClassOf(base, DataMinCardinality(3, dp, dr)));
                return axioms;
            } ,
            // DataQualifiedCardinality
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLDataRange dr = Integer();
                OWLClass base = Class(iri("A"));
                axioms.add(SubClassOf(base, DataExactCardinality(3, dp, dr)));
                return axioms;
            } ,
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
                OWLDataProperty prop = dp;
                OWLDataPropertyRangeAxiom ax = DataPropertyRange(prop, union);
                return singleton(ax);
            } ,
            // HasKeyAnnotated
            () -> {
                OWLAnnotationProperty ap = AnnotationProperty(IRI(
                    "http://annotation.com/annos#prop"));
                OWLLiteral val = Literal("Test", "");
                OWLAnnotation anno = Annotation(ap, val);
                Set<OWLAnnotation> annos = new HashSet<>();
                annos.add(anno);
                OWLClassExpression ce = Class(iri("A"));
                OWLObjectProperty p1 = ObjectProperty(iri("p1"));
                OWLObjectProperty p2 = ObjectProperty(iri("p2"));
                OWLObjectProperty p3 = ObjectProperty(iri("p3"));
                OWLHasKeyAxiom ax = HasKey(annos, ce, p1, p2, p3);
                Set<OWLAxiom> axs = new HashSet<>();
                axs.add(ax);
                axs.add(Declaration(ap));
                axs.add(Declaration(p1));
                axs.add(Declaration(p2));
                axs.add(Declaration(p3));
                return axs;
            } ,
            // LargeDisjointClassesAxiom
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                Set<OWLClass> clses = new HashSet<>();
                for (int i = 0; i < 1000; i++) {
                    clses.add(createClass());
                }
                axioms.add(DisjointClasses(clses));
                return axioms;
            } ,
            // ObjectInverseOf
            () -> {
                OWLClass clsA = Class(iri("A"));
                OWLObjectProperty prop = ObjectProperty(iri("prop"));
                OWLClassExpression ce = ObjectSomeValuesFrom(prop
                    .getInverseProperty(), clsA);
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(SubClassOf(Class(iri("B")), ce));
                return axioms;
            } ,
            // SubDataPropertyOf
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(SubDataPropertyOf(dp, dq));
                return axioms;
            } ,
            // OWLDataPropertyAssertion
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(DataPropertyAssertion(dp, NamedIndividual(iri("i")),
                    Literal(33.3)));
                return axioms;
            } ,
            // NegativeDataPropertyAssertion
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(NegativeDataPropertyAssertion(dp, NamedIndividual(
                    iri("i")), Literal(33.3)));
                axioms.add(NegativeDataPropertyAssertion(dp, NamedIndividual(
                    iri("i")), Literal("weasel", "")));
                axioms.add(NegativeDataPropertyAssertion(dp, NamedIndividual(
                    iri("i")), Literal("weasel")));
                return axioms;
            } ,
            // FunctionalDataProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(FunctionalDataProperty(dp));
                return axioms;
            } ,
            // DataPropertyDomain
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(DataPropertyDomain(dp, Class(iri("A"))));
                return axioms;
            } ,
            // DataPropertyRange
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(DataPropertyRange(dp, TopDatatype()));
                return axioms;
            } ,
            // DisjointDataPropertiesRoundTripping
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLDataProperty propA = DataProperty(iri("pA"));
                OWLDataProperty propB = DataProperty(iri("pB"));
                OWLDataProperty propC = DataProperty(iri("pC"));
                axioms.add(DisjointDataProperties(propA, propB, propC));
                axioms.add(Declaration(propA));
                axioms.add(Declaration(propB));
                axioms.add(Declaration(propC));
                return axioms;
            } ,
            // DisjointDataProperties
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLDataPropertyExpression propA = DataProperty(iri("propA"));
                OWLDataPropertyExpression propB = DataProperty(iri("propB"));
                axioms.add(DisjointDataProperties(propA, propB));
                return axioms;
            } ,
            // EquivalentDataProperties
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(EquivalentDataProperties(dp, dq));
                return axioms;
            } ,
            // AsymmetricObjectProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(AsymmetricObjectProperty(op));
                return axioms;
            } ,
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
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(DifferentIndividuals(NamedIndividual(iri("i")),
                    NamedIndividual(iri("j"))));
                axioms.add(DifferentIndividuals(NamedIndividual(iri("i")),
                    NamedIndividual(iri("k"))));
                return axioms;
            } ,
            // DifferentIndividuals
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(DifferentIndividuals(NamedIndividual(iri("i")),
                    NamedIndividual(iri("j")), NamedIndividual(iri("k")),
                    NamedIndividual(iri("l"))));
                return axioms;
            } ,
            // DisjointObjectPropertiesRoundTripping
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLObjectProperty propA = ObjectProperty(iri("pA"));
                OWLObjectProperty propB = ObjectProperty(iri("pB"));
                OWLObjectProperty propC = ObjectProperty(iri("pC"));
                axioms.add(DisjointObjectProperties(propA, propB, propC));
                axioms.add(Declaration(propA));
                axioms.add(Declaration(propB));
                axioms.add(Declaration(propC));
                return axioms;
            } ,
            // DisjointObjectProperties
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(DisjointObjectProperties(propA, propB));
                return axioms;
            } ,
            // EquivalentObjectProperties
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(EquivalentObjectProperties(propA, propB));
                axioms.add(Declaration(propA));
                axioms.add(Declaration(propB));
                return axioms;
            } ,
            // FunctionalObjectProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(FunctionalObjectProperty(op));
                return axioms;
            } ,
            // IRISubstring
            () -> {
                XMLWriterPreferences.getInstance().setUseNamespaceEntities(
                    true);
                IRI iriA = IRI(
                    "http://owlapi.sourceforge.net/properties#propA");
                IRI iriB = IRI(
                    "http://owlapi.sourceforge.net/properties2#propB");
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLClass clsA = Class(iri("A"));
                OWLAnnotationProperty propA = AnnotationProperty(iriA);
                OWLAnnotationProperty propB = AnnotationProperty(iriB);
                axioms.add(Declaration(clsA));
                axioms.add(AnnotationAssertion(propA, clsA.getIRI(), Literal(
                    "value1")));
                axioms.add(AnnotationAssertion(propB, clsA.getIRI(), Literal(
                    "value2")));
                return axioms;
            } ,
            // InverseFunctionalObjectProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(InverseFunctionalObjectProperty(op));
                return axioms;
            } ,
            // IrreflexiveObjectProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(IrreflexiveObjectProperty(op));
                return axioms;
            } ,
            // LargeDifferentIndividuals
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLNamedIndividual[] inds = new OWLNamedIndividual[1000];
                for (int i = 0; i < 1000; i++) {
                    inds[i] = createIndividual();
                }
                axioms.add(DifferentIndividuals(inds));
                return axioms;
            } ,
            // Literal
            () -> {
                OWLLiteral literalWithLang = Literal("abc", "en");
                OWLClass cls = Class(iri("A"));
                OWLAnnotationProperty prop = AnnotationProperty(iri("prop"));
                OWLAnnotationAssertionAxiom ax = AnnotationAssertion(prop, cls
                    .getIRI(), literalWithLang);
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(ax);
                axioms.add(Declaration(cls));
                return axioms;
            } ,
            // NegativeObjectPropertyAssertion
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLObjectProperty prop = ObjectProperty(iri("prop"));
                OWLIndividual subject = NamedIndividual(iri("iA"));
                OWLIndividual object = NamedIndividual(iri("iB"));
                axioms.add(NegativeObjectPropertyAssertion(prop, subject,
                    object));
                return axioms;
            } ,
            // ObjectPropertyAssertion
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(ObjectPropertyAssertion(op, NamedIndividual(iri(
                    "i")), NamedIndividual(iri("j"))));
                return axioms;
            } ,
            // ObjectPropertyChainAnnotatedRoundTripping
            () -> {
                List<OWLObjectProperty> props = new ArrayList<>();
                props.add(propA);
                props.add(propB);
                props.add(propC);
                Set<OWLAnnotation> annos = new HashSet<>();
                OWLAnnotationProperty annoPropA = AnnotationProperty(iri(
                    "annoPropA"));
                OWLAnnotationProperty annoPropB = AnnotationProperty(iri(
                    "annoPropB"));
                annos.add(Annotation(annoPropA, Literal("Test", "en")));
                annos.add(Annotation(annoPropB, Literal("Test", "")));
                OWLAxiom ax = SubPropertyChainOf(props, propD, annos);
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(ax);
                return axioms;
            } ,
            // ObjectPropertyDomain
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(ObjectPropertyDomain(op, Class(iri("A"))));
                return axioms;
            } ,
            // ObjectPropertyRange
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(ObjectPropertyRange(op, Class(iri("A"))));
                return axioms;
            } ,
            // PercentCharacterInEntityName
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(Declaration(Class(IRI(
                    "http://www.test.com/ontology#Class%37A"))));
                axioms.add(Declaration(ObjectProperty(IRI(
                    "http://www.test.com/ontology#prop%37A"))));
                return axioms;
            } ,
            // ReflexiveObjectProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(ReflexiveObjectProperty(op));
                return axioms;
            } ,
            // SWRLRuleAlternateNS
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                SWRLVariable varX = df.getSWRLVariable("http://www.owlapi#x");
                SWRLVariable varY = df.getSWRLVariable("http://www.owlapi#y");
                SWRLVariable varZ = df.getSWRLVariable("http://www.owlapi#z");
                Set<SWRLAtom> body = new HashSet<>();
                body.add(df.getSWRLClassAtom(Class(iri("A")), varX));
                SWRLIndividualArgument indIArg = df.getSWRLIndividualArgument(
                    NamedIndividual(iri("i")));
                SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(
                    NamedIndividual(iri("j")));
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
                    NamedIndividual(iri("i")));
                SWRLIndividualArgument indJArg = df.getSWRLIndividualArgument(
                    NamedIndividual(iri("j")));
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
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                // Can't round trip more than two in RDF!
                axioms.add(SameIndividual(NamedIndividual(iri("i")),
                    NamedIndividual(iri("j"))));
                return axioms;
            } ,
            // StringLiteralWithNewLine
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLNamedIndividual ind = NamedIndividual(iri("i"));
                OWLLiteral literal = Literal("Test \"literal\"\nStuff");
                axioms.add(DataPropertyAssertion(dp, ind, literal));
                return axioms;
            } ,
            // StringLiteralWithQuotes
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                OWLNamedIndividual ind = NamedIndividual(iri("i"));
                OWLLiteral literal = Literal("Test \"literal\"");
                axioms.add(DataPropertyAssertion(dp, ind, literal));
                OWLLiteral literal2 = Literal("Test 'literal'");
                axioms.add(DataPropertyAssertion(dp, ind, literal2));
                OWLLiteral literal3 = Literal("Test \"\"\"literal\"\"\"");
                axioms.add(DataPropertyAssertion(dp, ind, literal3));
                return axioms;
            } ,
            // SubObjectPropertyOf
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(SubObjectPropertyOf(op, oq));
                return axioms;
            } ,
            // SymmetricObjectProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(SymmetricObjectProperty(op));
                return axioms;
            } ,                // TransitiveObjectProperty
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                axioms.add(TransitiveObjectProperty(op));
                return axioms;
            } ,
            // TypedLiterals
            () -> {
                Set<OWLAxiom> axioms = new HashSet<>();
                addAxiomForLiteral(Literal(3), axioms);
                addAxiomForLiteral(Literal(33.3), axioms);
                addAxiomForLiteral(Literal(true), axioms);
                addAxiomForLiteral(Literal(33.3f), axioms);
                addAxiomForLiteral(Literal("33.3"), axioms);
                return axioms;
            } );
    }

    private static void addAxiomForLiteral(OWLLiteral lit,
        Set<OWLAxiom> axioms) {
        OWLNamedIndividual ind = NamedIndividual(iri("i"));
        axioms.add(DataPropertyAssertion(dp, ind, lit));
    }
}
