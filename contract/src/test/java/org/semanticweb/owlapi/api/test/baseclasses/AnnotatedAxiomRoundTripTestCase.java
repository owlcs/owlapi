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

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Annotation;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.AsymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ClassAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalDataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.FunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.InverseFunctionalObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IrreflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Literal;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeDataPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NegativeObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyAssertion;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyDomain;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectPropertyRange;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ReflexiveObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubDataPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubObjectPropertyOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SubPropertyChainOf;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SymmetricObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TopDatatype;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.TransitiveObjectProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.formats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TrigDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.parameters.Imports;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class AnnotatedAxiomRoundTripTestCase extends TestBase {

    OWLOntology annotatedAxiom(Function<Set<OWLAnnotation>, OWLAxiom> function) {

        OWLLiteral lit = Literal("Test", "");
        OWLAnnotation anno1 = Annotation(propQ, lit);
        OWLAnnotation anno2 = Annotation(propP, lit);
        Set<OWLAnnotation> annos = new HashSet<>(Arrays.asList(anno1, anno2));
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAxiom ax = function.apply(annos);
        axioms.add(ax.getAnnotatedAxiom(annos));
        axioms.add(Declaration(propQ));
        axioms.add(Declaration(propP));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno1)));
        axioms.add(ax.getAnnotatedAxiom(singleton(anno2)));
        OWLOntology ont = createAnon();
        ont.getOWLOntologyManager().addAxioms(ont, axioms);
        ont.getSignature().stream()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, Imports.INCLUDED))
            .forEach(entity -> ont.getOWLOntologyManager().addAxiom(ont, Declaration(entity)));
        return ont;
    }

    static List<Function<Set<OWLAnnotation>, OWLAxiom>> annotatedAxiomRoundtripExceptManchesterSyntaxTestCase() {
        return Arrays.asList(a -> Declaration(P, a), a -> Declaration(DT, a),
            a -> Declaration(I, a), a -> Declaration(PD, a), a -> Declaration(AP, a),
            a -> Declaration(A, a));
    }

    static List<Function<Set<OWLAnnotation>, OWLAxiom>> annotatedAxiomRoundtripExceptRDFXMLAndFunctionalTestCase() {
        return Arrays.asList(a -> EquivalentClasses(a, A, B, C, D),
            a -> EquivalentDataProperties(a, DP, DQ, DR),
            a -> EquivalentObjectProperties(a, P, Q, R));
    }

    static List<Function<Set<OWLAnnotation>, OWLAxiom>> annotatedAxiomRountripTestCase() {
        return Arrays.asList(a -> AsymmetricObjectProperty(P, a), a -> ClassAssertion(A, I, a),
            a -> DataPropertyAssertion(DP, I, Literal("xyz"), a), a -> DataPropertyDomain(DP, A, a),
            a -> DataPropertyRange(DP, TopDatatype(), a), a -> DisjointClasses(a, A, B),
            a -> DisjointClasses(a, A, B, C, D), a -> DisjointDataProperties(a, DP, DQ),
            a -> DisjointDataProperties(a, DP, DQ, DR), a -> DisjointObjectProperties(a, P, Q),
            a -> DisjointObjectProperties(a, P, Q, R), a -> EquivalentClasses(a, A, B),
            a -> EquivalentDataProperties(a, DP, DQ), a -> EquivalentObjectProperties(a, P, Q),
            a -> FunctionalDataProperty(DP, a), a -> FunctionalObjectProperty(P, a),
            a -> InverseFunctionalObjectProperty(P, a), a -> IrreflexiveObjectProperty(P, a),
            a -> NegativeDataPropertyAssertion(DP, I, Literal("xyz"), a),
            a -> NegativeObjectPropertyAssertion(P, I, J, a),
            a -> ObjectPropertyAssertion(P, I, J, a), a -> ObjectPropertyDomain(P, A, a),
            a -> ObjectPropertyRange(P, A, a), a -> ReflexiveObjectProperty(P, a),
            a -> df.getOWLSubClassOfAxiom(A, B, a), a -> SubDataPropertyOf(DP, DQ, a),
            a -> SubObjectPropertyOf(P, Q, a), a -> SymmetricObjectProperty(P, a),
            a -> TransitiveObjectProperty(P, a), a -> SubPropertyChainOf(Arrays.asList(P, Q), R, a),
            a -> DifferentIndividuals(set(i, I, J), a), a -> DifferentIndividuals(set(I, J), a),
            a -> DifferentIndividuals(set(i, I, J), a), a -> DifferentIndividuals(set(I, J), a));
    }

    static List<Function<Set<OWLAnnotation>, OWLAxiom>> individualsAxiomAnnotatedTestCase() {
        return Arrays.asList(a -> df.getOWLSameIndividualAxiom(set(I, J), a),
            a -> df.getOWLDifferentIndividualsAxiom(set(I, J, indA), a),
            a -> df.getOWLSameIndividualAxiom(set(I, J), a));
    }

    static List<Arguments> annotatedOntologies() {
        List<Arguments> list = new ArrayList<>();
        List<OWLDocumentFormat> formats = Arrays.asList(new RDFXMLDocumentFormat(),
            new RioRDFXMLDocumentFormat(), new RDFJsonDocumentFormat(), new OWLXMLDocumentFormat(),
            new FunctionalSyntaxDocumentFormat(), new TurtleDocumentFormat(),
            new RioTurtleDocumentFormat(), new TrigDocumentFormat(), new RDFJsonLDDocumentFormat(),
            new NTriplesDocumentFormat(), new NQuadsDocumentFormat());
        Consumer<? super Function<Set<OWLAnnotation>, OWLAxiom>> action =
            x -> formats.forEach(format -> list.add(Arguments.of(x, format)));
        annotatedAxiomRountripTestCase().forEach(action);
        annotatedAxiomRoundtripExceptManchesterSyntaxTestCase().forEach(action);
        annotatedAxiomRoundtripExceptRDFXMLAndFunctionalTestCase().forEach(action);
        individualsAxiomAnnotatedTestCase().forEach(action);
        return list;
    }

    @ParameterizedTest
    @MethodSource({"annotatedOntologies"})
    void testRDFXML(Function<Set<OWLAnnotation>, OWLAxiom> function, OWLDocumentFormat format) {
        roundTripOntology(annotatedAxiom(function), format);
    }

    @ParameterizedTest
    @MethodSource({"annotatedAxiomRountripTestCase", "individualsAxiomAnnotatedTestCase",
        "annotatedAxiomRoundtripExceptRDFXMLAndFunctionalTestCase"})
    void testManchesterOWLSyntax(Function<Set<OWLAnnotation>, OWLAxiom> format) {
        roundTripOntology(annotatedAxiom(format), new ManchesterSyntaxDocumentFormat());
    }

    @ParameterizedTest
    @MethodSource({"annotatedAxiomRountripTestCase", "individualsAxiomAnnotatedTestCase",
        "annotatedAxiomRoundtripExceptManchesterSyntaxTestCase"})
    void roundTripRDFXMLAndFunctionalShouldBeSame(Function<Set<OWLAnnotation>, OWLAxiom> format) {
        OWLOntology o = annotatedAxiom(format);
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
        equal(o1, o2);
    }
}
