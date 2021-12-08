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

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.util.ArrayList;
import java.util.Collection;
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
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class AnnotatedAxiomRoundTripTestCase extends TestBase {

    OWLOntology annotatedAxiom(Function<Collection<OWLAnnotation>, OWLAxiom> function) {

        OWLAnnotation anno1 = Annotation(propQ, val);
        OWLAnnotation anno2 = Annotation(propP, val);
        List<OWLAnnotation> annos = l(anno1, anno2);
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAxiom ax = function.apply(annos);
        axioms.add(ax.getAnnotatedAxiom(annos));
        axioms.add(Declaration(propQ));
        axioms.add(Declaration(propP));
        axioms.add(ax.getAnnotatedAxiom(l(anno1)));
        axioms.add(ax.getAnnotatedAxiom(l(anno2)));
        OWLOntology ont = createAnon();
        ont.add(axioms);
        ont.unsortedSignature()
            .filter(entity -> !entity.isBuiltIn() && !ont.isDeclared(entity, INCLUDED))
            .forEach(entity -> ont.add(Declaration(entity)));
        return ont;
    }

    static List<Function<Collection<OWLAnnotation>, OWLAxiom>> annotatedAxiomRoundtripExceptManchesterSyntaxTestCase() {
        return l(a -> Declaration(a, P), a -> Declaration(a, DT), a -> Declaration(a, I),
            a -> Declaration(a, PD), a -> Declaration(a, AP), a -> Declaration(a, A));
    }

    static List<Function<Collection<OWLAnnotation>, OWLAxiom>> annotatedAxiomRoundtripExceptRDFXMLAndFunctionalTestCase() {
        return l(a -> EquivalentClasses(a, A, B, C, D),
            a -> EquivalentDataProperties(a, DP, DQ, DR),
            a -> EquivalentObjectProperties(a, P, Q, R));
    }

    static List<Function<Collection<OWLAnnotation>, OWLAxiom>> annotatedAxiomRountripTestCase() {
        return l(a -> AsymmetricObjectProperty(a, P), a -> ClassAssertion(a, A, I),
            a -> DataPropertyAssertion(a, DP, I, Literal("xyz")), a -> DataPropertyDomain(a, DP, A),
            a -> DataPropertyRange(a, DP, TopDatatype()), a -> DisjointClasses(a, A, B),
            a -> DisjointClasses(a, A, B, C, D), a -> DisjointDataProperties(a, DP, DQ),
            a -> DisjointDataProperties(a, DP, DQ, DR), a -> DisjointObjectProperties(a, P, Q),
            a -> DisjointObjectProperties(a, P, Q, R), a -> EquivalentClasses(a, A, B),
            a -> EquivalentDataProperties(a, DP, DQ), a -> EquivalentObjectProperties(a, P, Q),
            a -> FunctionalDataProperty(a, DP), a -> FunctionalObjectProperty(a, P),
            a -> InverseFunctionalObjectProperty(a, P), a -> IrreflexiveObjectProperty(a, P),
            a -> NegativeDataPropertyAssertion(a, DP, I, Literal("xyz")),
            a -> NegativeObjectPropertyAssertion(a, P, I, J),
            a -> ObjectPropertyAssertion(a, P, I, J), a -> ObjectPropertyDomain(a, P, A),
            a -> ObjectPropertyRange(a, P, A), a -> ReflexiveObjectProperty(a, P),
            a -> SubClassOf(a, A, B), a -> SubDataPropertyOf(a, DP, DQ),
            a -> SubObjectPropertyOf(a, P, Q), a -> SymmetricObjectProperty(a, P),
            a -> TransitiveObjectProperty(a, P), a -> SubPropertyChainOf(a, l(P, Q), R),
            a -> DifferentIndividuals(a, i, I, J), a -> DifferentIndividuals(a, I, J),
            a -> DifferentIndividuals(a, i, I, J), a -> DifferentIndividuals(a, I, J));
    }

    static List<Function<Collection<OWLAnnotation>, OWLAxiom>> individualsAxiomAnnotatedTestCase() {
        return l(a -> SameIndividual(a, I, J), a -> DifferentIndividuals(a, I, J, indA),
            a -> SameIndividual(a, I, J));
    }

    static List<Arguments> annotatedOntologies() {
        List<Arguments> list = new ArrayList<>();
        List<OWLDocumentFormat> formats = l(new RDFXMLDocumentFormat(),
            new RioRDFXMLDocumentFormat(), new RDFJsonDocumentFormat(), new OWLXMLDocumentFormat(),
            new FunctionalSyntaxDocumentFormat(), new TurtleDocumentFormat(),
            new RioTurtleDocumentFormat(), new TrigDocumentFormat(), new RDFJsonLDDocumentFormat(),
            new NTriplesDocumentFormat(), new NQuadsDocumentFormat());
        Consumer<? super Function<Collection<OWLAnnotation>, OWLAxiom>> action =
            in -> formats.forEach(format -> list.add(Arguments.of(in, format)));
        annotatedAxiomRountripTestCase().forEach(action);
        annotatedAxiomRoundtripExceptManchesterSyntaxTestCase().forEach(action);
        annotatedAxiomRoundtripExceptRDFXMLAndFunctionalTestCase().forEach(action);
        individualsAxiomAnnotatedTestCase().forEach(action);
        return list;
    }

    @ParameterizedTest
    @MethodSource({"annotatedOntologies"})
    void testRDFXML(Function<Collection<OWLAnnotation>, OWLAxiom> function,
        OWLDocumentFormat format) {
        roundTripOntology(annotatedAxiom(function), format);
    }

    @ParameterizedTest
    @MethodSource({"annotatedAxiomRountripTestCase", "individualsAxiomAnnotatedTestCase",
        "annotatedAxiomRoundtripExceptRDFXMLAndFunctionalTestCase"})
    void testManchesterOWLSyntax(Function<Collection<OWLAnnotation>, OWLAxiom> function) {
        roundTripOntology(annotatedAxiom(function), new ManchesterSyntaxDocumentFormat());
    }

    @ParameterizedTest
    @MethodSource({"annotatedAxiomRountripTestCase", "individualsAxiomAnnotatedTestCase",
        "annotatedAxiomRoundtripExceptManchesterSyntaxTestCase"})
    void roundTripRDFXMLAndFunctionalShouldBeSame(
        Function<Collection<OWLAnnotation>, OWLAxiom> format) {
        OWLOntology o = annotatedAxiom(format);
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        OWLOntology o2 = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        equal(o, o1);
        equal(o1, o2);
    }
}
