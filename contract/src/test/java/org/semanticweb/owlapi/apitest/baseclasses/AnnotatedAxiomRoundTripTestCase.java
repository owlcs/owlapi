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
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.rioformats.NQuadsDocumentFormat;
import org.semanticweb.owlapi.rioformats.NTriplesDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.rioformats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioRDFXMLDocumentFormat;
import org.semanticweb.owlapi.rioformats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.rioformats.TrigDocumentFormat;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class AnnotatedAxiomRoundTripTestCase extends TestBase {

    OWLOntology annotatedAxiom(Function<Collection<OWLAnnotation>, OWLAxiom> function) {

        OWLAnnotation anno1 = Annotation(ANNPROPS.propQ, LITERALS.val);
        OWLAnnotation anno2 = Annotation(ANNPROPS.propP, LITERALS.val);
        List<OWLAnnotation> annos = l(anno1, anno2);
        Set<OWLAxiom> axioms = new HashSet<>();
        OWLAxiom ax = function.apply(annos);
        axioms.add(ax.getAnnotatedAxiom(annos));
        axioms.add(Declaration(ANNPROPS.propQ));
        axioms.add(Declaration(ANNPROPS.propP));
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
        return l(a -> Declaration(a, OBJPROPS.P), a -> Declaration(a, DATATYPES.DT),
            a -> Declaration(a, INDIVIDUALS.I), a -> Declaration(a, DATAPROPS.PD),
            a -> Declaration(a, ANNPROPS.AP), a -> Declaration(a, CLASSES.A));
    }

    static List<Function<Collection<OWLAnnotation>, OWLAxiom>> annotatedAxiomRoundtripExceptRDFXMLAndFunctionalTestCase() {
        return l(a -> EquivalentClasses(a, CLASSES.A, CLASSES.B, CLASSES.C, CLASSES.D),
            a -> EquivalentDataProperties(a, DATAPROPS.DP, DATAPROPS.DQ, DATAPROPS.DR),
            a -> EquivalentObjectProperties(a, OBJPROPS.P, OBJPROPS.Q, OBJPROPS.R));
    }

    static List<Function<Collection<OWLAnnotation>, OWLAxiom>> annotatedAxiomRountripTestCase() {
        return l(a -> AsymmetricObjectProperty(a, OBJPROPS.P),
            a -> ClassAssertion(a, CLASSES.A, INDIVIDUALS.I),
            a -> DataPropertyAssertion(a, DATAPROPS.DP, INDIVIDUALS.I, Literal("xyz")),
            a -> DataPropertyDomain(a, DATAPROPS.DP, CLASSES.A),
            a -> DataPropertyRange(a, DATAPROPS.DP, TopDatatype()),
            a -> DisjointClasses(a, CLASSES.A, CLASSES.B),
            a -> DisjointClasses(a, CLASSES.A, CLASSES.B, CLASSES.C, CLASSES.D),
            a -> DisjointDataProperties(a, DATAPROPS.DP, DATAPROPS.DQ),
            a -> DisjointDataProperties(a, DATAPROPS.DP, DATAPROPS.DQ, DATAPROPS.DR),
            a -> DisjointObjectProperties(a, OBJPROPS.P, OBJPROPS.Q),
            a -> DisjointObjectProperties(a, OBJPROPS.P, OBJPROPS.Q, OBJPROPS.R),
            a -> EquivalentClasses(a, CLASSES.A, CLASSES.B),
            a -> EquivalentDataProperties(a, DATAPROPS.DP, DATAPROPS.DQ),
            a -> EquivalentObjectProperties(a, OBJPROPS.P, OBJPROPS.Q),
            a -> FunctionalDataProperty(a, DATAPROPS.DP),
            a -> FunctionalObjectProperty(a, OBJPROPS.P),
            a -> InverseFunctionalObjectProperty(a, OBJPROPS.P),
            a -> IrreflexiveObjectProperty(a, OBJPROPS.P),
            a -> NegativeDataPropertyAssertion(a, DATAPROPS.DP, INDIVIDUALS.I, Literal("xyz")),
            a -> NegativeObjectPropertyAssertion(a, OBJPROPS.P, INDIVIDUALS.I, INDIVIDUALS.J),
            a -> ObjectPropertyAssertion(a, OBJPROPS.P, INDIVIDUALS.I, INDIVIDUALS.J),
            a -> ObjectPropertyDomain(a, OBJPROPS.P, CLASSES.A),
            a -> ObjectPropertyRange(a, OBJPROPS.P, CLASSES.A),
            a -> ReflexiveObjectProperty(a, OBJPROPS.P), a -> SubClassOf(a, CLASSES.A, CLASSES.B),
            a -> SubDataPropertyOf(a, DATAPROPS.DP, DATAPROPS.DQ),
            a -> SubObjectPropertyOf(a, OBJPROPS.P, OBJPROPS.Q),
            a -> SymmetricObjectProperty(a, OBJPROPS.P),
            a -> TransitiveObjectProperty(a, OBJPROPS.P),
            a -> SubPropertyChainOf(a, l(OBJPROPS.P, OBJPROPS.Q), OBJPROPS.R),
            a -> DifferentIndividuals(a, INDIVIDUALS.i, INDIVIDUALS.I, INDIVIDUALS.J),
            a -> DifferentIndividuals(a, INDIVIDUALS.I, INDIVIDUALS.J),
            a -> DifferentIndividuals(a, INDIVIDUALS.i, INDIVIDUALS.I, INDIVIDUALS.J),
            a -> DifferentIndividuals(a, INDIVIDUALS.I, INDIVIDUALS.J));
    }

    static List<Function<Collection<OWLAnnotation>, OWLAxiom>> individualsAxiomAnnotatedTestCase() {
        return l(a -> SameIndividual(a, INDIVIDUALS.I, INDIVIDUALS.J),
            a -> DifferentIndividuals(a, INDIVIDUALS.I, INDIVIDUALS.J, INDIVIDUALS.indA),
            a -> SameIndividual(a, INDIVIDUALS.I, INDIVIDUALS.J));
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
