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
package org.semanticweb.owlapi.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_INCLUSIVE;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.impl.OWLDataFactoryImpl;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLVariable;

/**
 * A test case to ensure that the reference implementation data factories do not create duplicate
 * objects for distinguished values (e.g. owl:Thing, rdfs:Literal etc.)
 *
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.2.0
 */
class OWLDataFactoryImplTestCase extends TestBase {
    private static final String LANG = "LANG";
    private static final String TEST = "TEST";

    private static void assertEqualsFromSupplier(Supplier<?> supplier) {
        Object s1 = supplier.get();
        Object s2 = supplier.get();
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertEqualsFromSuppliers(Supplier<?> sa, Supplier<?> sb) {
        Object s1 = sa.get();
        Object s2 = sb.get();
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertNotEqualsFromSupplier(Supplier<?> supplier) {
        Object s1 = supplier.get();
        Object s2 = supplier.get();
        assertNotEquals(s1, s2);
        assertNotEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertNotEqualsFromSuppliers(Supplier<?> sa, Supplier<?> sb) {
        Object s1 = sa.get();
        Object s2 = sb.get();
        assertNotEquals(s1, s2);
        assertNotEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertSameFromSupplier(Supplier<?> supplier) {
        assertSame(supplier.get(), supplier.get());
    }

    int cardinality = 3;
    private final OWLDataFactory subj = new OWLDataFactoryImpl();
    private static final AtomicInteger COUNTER = new AtomicInteger(1);
    OWLClassExpression a = C(IRI());
    OWLIndividual a1 = I();
    OWLLiteral a2 = Literal();
    OWLObjectProperty a3 = OP(IRI());
    OWLDataProperty a4 = DP(IRI());
    OWLClassExpression b = C(IRI());
    OWLIndividual b1 = I();
    OWLLiteral b2 = Literal();
    OWLObjectProperty b3 = OP(IRI());
    OWLDataProperty b4 = DP(IRI());
    OWLIndividual c1 = I();
    OWLLiteral c2 = Literal();
    OWLObjectProperty c3 = OP(IRI());
    OWLDataProperty c4 = DP(IRI());
    OWLClassExpression c5 = C(IRI());
    OWLDatatype dtype = D(IRI());
    IRI iri = IRI();
    OWLDataProperty left = DP(IRI());
    OWLIndividual o1 = I();
    OWLLiteral o2 = Literal();
    OWLObjectProperty p3 = OP(IRI());
    OWLDataProperty dataProp = DP(IRI());
    OWLObjectProperty prop2 = OP(IRI());
    OWLClass right = C(IRI());
    OWLIndividual s1 = I();
    OWLClassExpression[] classExpressions = {C(IRI()), C(IRI())};
    OWLDataPropertyExpression[] properties = {a4, b4};
    OWLClassExpression operand = C(IRI());
    OWLLiteral filler1 = Literal();
    OWLIndividual filler = I();
    OWLLiteral facetValue = subj.getOWLLiteral("3", D(IRI()));
    OWLClass clsE = subj.getOWLClass(URN_TEST, "E");
    OWLClass clsB = subj.getOWLClass(URN_TEST, "B");
    OWLClass clsC = subj.getOWLClass(URN_TEST, "C");
    OWLClass clsD = subj.getOWLClass(URN_TEST, "D");
    OWLClassExpression operandA = C(IRI());
    OWLClassExpression operandB = C(IRI());

    private OWLClass C(IRI in) {
        return subj.getOWLClass(in);
    }

    private OWLDatatype D(IRI in) {
        return subj.getOWLDatatype(in);
    }

    private OWLDataProperty DP(IRI in) {
        return subj.getOWLDataProperty(in);
    }

    private OWLIndividual I() {
        return subj.getOWLNamedIndividual(IRI());
    }

    private IRI IRI() {
        return subj.getNextDocumentIRI("urn:test#A");
    }

    private OWLLiteral Literal() {
        return subj.getOWLLiteral("A" + COUNTER.getAndIncrement());
    }

    private OWLObjectProperty OP(IRI in) {
        return subj.getOWLObjectProperty(in);
    }

    @Test
    void shouldFindInternalisedSame() {
        assertSameFromSupplier(subj::getRDFPlainLiteral);
        assertSameFromSupplier(subj::getTopDatatype);
        assertSameFromSupplier(subj::getBooleanOWLDatatype);
        assertSameFromSupplier(subj::getDoubleOWLDatatype);
        assertSameFromSupplier(subj::getFloatOWLDatatype);
        assertSameFromSupplier(subj::getRDFSLabel);
        assertSameFromSupplier(subj::getRDFSSeeAlso);
        assertSameFromSupplier(subj::getRDFSIsDefinedBy);
        assertSameFromSupplier(subj::getOWLVersionInfo);
        assertSameFromSupplier(subj::getOWLBackwardCompatibleWith);
        assertSameFromSupplier(subj::getOWLIncompatibleWith);
        assertSameFromSupplier(subj::getOWLDeprecated);
        assertSameFromSupplier(subj::getOWLThing);
        assertSameFromSupplier(subj::getOWLNothing);
        assertSameFromSupplier(subj::getOWLTopObjectProperty);
        assertSameFromSupplier(subj::getOWLBottomObjectProperty);
        assertSameFromSupplier(subj::getOWLTopDataProperty);
        assertSameFromSupplier(subj::getOWLBottomDataProperty);
    }

    @Test
    void shouldHaveSWRLRulesEqual() {
        SWRLVariable var = subj.getSWRLVariable(URN_TEST, "x");
        List<SWRLClassAtom> body1 =
            l(subj.getSWRLClassAtom(clsE, var), subj.getSWRLClassAtom(clsC, var));
        List<SWRLClassAtom> head1 =
            l(subj.getSWRLClassAtom(clsB, var), subj.getSWRLClassAtom(clsD, var));
        List<SWRLClassAtom> body2 =
            l(subj.getSWRLClassAtom(clsC, var), subj.getSWRLClassAtom(clsE, var));
        List<SWRLClassAtom> head2 =
            l(subj.getSWRLClassAtom(clsD, var), subj.getSWRLClassAtom(clsB, var));
        assertEqualsFromSuppliers(() -> subj.getSWRLRule(body1, head1),
            () -> subj.getSWRLRule(body2, head2));
    }

    @Test
    void shouldHaveSWRLRulesWithAnnotationsEqual() {
        Collection<OWLAnnotation> ann1 =
            l(subj.getRDFSComment("test1"), subj.getRDFSLabel("test2"));
        Collection<OWLAnnotation> ann2 =
            l(subj.getRDFSLabel("test2"), subj.getRDFSComment("test1"));
        SWRLVariable var = subj.getSWRLVariable(URN_TEST, "x");
        List<SWRLClassAtom> body1 =
            l(subj.getSWRLClassAtom(clsE, var), subj.getSWRLClassAtom(clsC, var));
        List<SWRLClassAtom> head1 =
            l(subj.getSWRLClassAtom(clsB, var), subj.getSWRLClassAtom(clsD, var));
        List<SWRLClassAtom> body2 =
            l(subj.getSWRLClassAtom(clsC, var), subj.getSWRLClassAtom(clsE, var));
        List<SWRLClassAtom> head2 =
            l(subj.getSWRLClassAtom(clsD, var), subj.getSWRLClassAtom(clsB, var));
        assertEqualsFromSuppliers(() -> subj.getSWRLRule(body1, head1, ann1),
            () -> subj.getSWRLRule(body2, head2, ann2));
    }

    @Test
    void testAsSubAxiomsEquivalentClasses() {
        s(() -> C(iri), () -> D(iri), () -> DP(iri), () -> OP(iri),
            () -> subj.getOWLAnnotationProperty(iri),
            () -> subj.getOWLAsymmetricObjectPropertyAxiom(prop2),
            () -> subj.getOWLClassAssertionAxiom(right, a1),
            () -> subj.getOWLDataAllValuesFrom(dataProp, dtype),
            () -> subj.getOWLDataComplementOf(dtype),
            () -> subj.getOWLDataHasValue(dataProp, filler1),
            () -> subj.getOWLDataOneOf(a2, b2, c2),
            () -> subj.getOWLDataPropertyAssertionAxiom(dataProp, s1, o2),
            () -> subj.getOWLDataPropertyDomainAxiom(left, right),
            () -> subj.getOWLDataPropertyRangeAxiom(left, dtype),
            () -> subj.getOWLDataSomeValuesFrom(dataProp, dtype),
            () -> subj.getOWLDatatypeRestriction(dtype,
                subj.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)),
            () -> subj.getOWLDifferentIndividualsAxiom(a1, b1, c1),
            () -> subj.getOWLDisjointClassesAxiom(a, b, c5),
            () -> subj.getOWLDisjointDataPropertiesAxiom(a4, b4, c4),
            () -> subj.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3),
            () -> subj.getOWLEquivalentClassesAxiom(a, b, c5),
            () -> subj.getOWLEquivalentClassesAxiom(classExpressions),
            () -> subj.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4),
            () -> subj.getOWLEquivalentDataPropertiesAxiom(properties),
            () -> subj.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3),
            () -> subj.getOWLFunctionalDataPropertyAxiom(dataProp),
            () -> subj.getOWLFunctionalObjectPropertyAxiom(prop2),
            () -> subj.getOWLInverseFunctionalObjectPropertyAxiom(prop2),
            () -> subj.getOWLIrreflexiveObjectPropertyAxiom(prop2),
            () -> subj.getOWLLiteral("3", dtype), () -> subj.getOWLLiteral(TEST, LANG),
            () -> subj.getOWLNamedIndividual(iri),
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(dataProp, s1, o2),
            () -> subj.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, o1),
            () -> subj.getOWLObjectAllValuesFrom(prop2, a),
            () -> subj.getOWLObjectComplementOf(operand), () -> subj.getOWLObjectHasSelf(prop2),
            () -> subj.getOWLObjectHasValue(prop2, filler),
            () -> subj.getOWLObjectIntersectionOf(a, b, c5),
            () -> subj.getOWLDataExactCardinality(cardinality, dataProp, dtype),
            () -> subj.getOWLDataExactCardinality(cardinality, dataProp, subj.getTopDatatype()),
            () -> subj.getOWLDataMaxCardinality(cardinality, dataProp, dtype),
            () -> subj.getOWLDataMaxCardinality(cardinality, dataProp, subj.getTopDatatype()),
            () -> subj.getOWLDataMinCardinality(cardinality, dataProp, dtype),
            () -> subj.getOWLDataMinCardinality(cardinality, dataProp, subj.getTopDatatype()),
            () -> subj.getOWLObjectExactCardinality(cardinality, prop2, a),
            () -> subj.getOWLObjectExactCardinality(cardinality, prop2, subj.getOWLThing()),
            () -> subj.getOWLObjectMaxCardinality(cardinality, prop2, a),
            () -> subj.getOWLObjectMaxCardinality(cardinality, prop2, subj.getOWLThing()),
            () -> subj.getOWLObjectMinCardinality(cardinality, prop2, a),
            () -> subj.getOWLObjectMinCardinality(cardinality, prop2, subj.getOWLThing()),
            () -> subj.getOWLObjectOneOf(a1, b1, c1),
            () -> subj.getOWLObjectPropertyAssertionAxiom(p3, s1, o1),
            () -> subj.getOWLObjectPropertyDomainAxiom(p3, right),
            () -> subj.getOWLObjectPropertyRangeAxiom(p3, right),
            () -> subj.getOWLObjectSomeValuesFrom(prop2, a),
            () -> subj.getOWLObjectUnionOf(a, b, c5),
            () -> subj.getOWLReflexiveObjectPropertyAxiom(prop2),
            () -> subj.getOWLSameIndividualAxiom(a1, b1, c1),
            () -> subj.getOWLSubDataPropertyOfAxiom(left, dataProp),
            () -> subj.getOWLSubObjectPropertyOfAxiom(p3, prop2),
            () -> subj.getOWLSymmetricObjectPropertyAxiom(prop2),
            () -> subj.getOWLTransitiveObjectPropertyAxiom(prop2))
                .forEach(OWLDataFactoryImplTestCase::assertEqualsFromSupplier);

        s(() -> C(IRI()), () -> D(IRI()), () -> DP(IRI()), () -> OP(IRI()),
            () -> subj.getOWLAnnotationProperty(IRI()),
            () -> subj.getOWLAsymmetricObjectPropertyAxiom(OP(IRI())),
            () -> subj.getOWLClassAssertionAxiom(C(IRI()), a1),
            () -> subj.getOWLClassAssertionAxiom(right, I()),
            () -> subj.getOWLDataAllValuesFrom(DP(IRI()), dtype),
            () -> subj.getOWLDataAllValuesFrom(dataProp, D(IRI())),
            () -> subj.getOWLDataComplementOf(D(IRI())),
            () -> subj.getOWLDataHasValue(DP(IRI()), filler1),
            () -> subj.getOWLDataHasValue(dataProp, Literal()),
            () -> subj.getOWLDataPropertyAssertionAxiom(DP(IRI()), s1, o2),
            () -> subj.getOWLDataPropertyAssertionAxiom(dataProp, I(), o2),
            () -> subj.getOWLDataPropertyAssertionAxiom(dataProp, s1, Literal()),
            () -> subj.getOWLDataPropertyDomainAxiom(DP(IRI()), right),
            () -> subj.getOWLDataPropertyDomainAxiom(left, C(IRI())),
            () -> subj.getOWLDataPropertyRangeAxiom(DP(IRI()), dtype),
            () -> subj.getOWLDataPropertyRangeAxiom(left, D(IRI())),
            () -> subj.getOWLFunctionalDataPropertyAxiom(DP(IRI())),
            () -> subj.getOWLFunctionalObjectPropertyAxiom(OP(IRI())),
            () -> subj.getOWLInverseFunctionalObjectPropertyAxiom(OP(IRI())),
            () -> subj.getOWLIrreflexiveObjectPropertyAxiom(OP(IRI())),
            () -> subj.getOWLLiteral("3", D(IRI())), () -> subj.getOWLNamedIndividual(IRI()),
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(DP(IRI()), s1, o2),
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(dataProp, I(), o2),
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(dataProp, s1, Literal()),
            () -> subj.getOWLNegativeObjectPropertyAssertionAxiom(OP(IRI()), s1, o1),
            () -> subj.getOWLNegativeObjectPropertyAssertionAxiom(p3, I(), o1),
            () -> subj.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, I()),
            () -> subj.getOWLObjectAllValuesFrom(OP(IRI()), a),
            () -> subj.getOWLObjectAllValuesFrom(prop2, C(IRI())),
            () -> subj.getOWLObjectHasSelf(OP(IRI())),
            () -> subj.getOWLObjectHasValue(OP(IRI()), filler),
            () -> subj.getOWLObjectHasValue(prop2, I()),
            () -> subj.getOWLObjectPropertyAssertionAxiom(OP(IRI()), s1, o1),
            () -> subj.getOWLObjectPropertyAssertionAxiom(p3, I(), o1),
            () -> subj.getOWLObjectPropertyAssertionAxiom(p3, s1, I()),
            () -> subj.getOWLObjectPropertyDomainAxiom(OP(IRI()), right),
            () -> subj.getOWLObjectPropertyDomainAxiom(p3, C(IRI())),
            () -> subj.getOWLObjectPropertyRangeAxiom(OP(IRI()), right),
            () -> subj.getOWLObjectPropertyRangeAxiom(p3, C(IRI())),
            () -> subj.getOWLObjectSomeValuesFrom(OP(IRI()), a),
            () -> subj.getOWLObjectSomeValuesFrom(prop2, C(IRI())),
            () -> subj.getOWLReflexiveObjectPropertyAxiom(OP(IRI())),
            () -> subj.getOWLSubDataPropertyOfAxiom(DP(IRI()), dataProp),
            () -> subj.getOWLSubDataPropertyOfAxiom(left, DP(IRI())),
            () -> subj.getOWLSubObjectPropertyOfAxiom(OP(IRI()), prop2),
            () -> subj.getOWLSubObjectPropertyOfAxiom(p3, OP(IRI())),
            () -> subj.getOWLSymmetricObjectPropertyAxiom(OP(IRI())),
            () -> subj.getOWLTransitiveObjectPropertyAxiom(OP(IRI())),
            () -> subj.getOWLObjectExactCardinality(3, OP(IRI()), subj.getOWLThing()),
            () -> subj.getOWLObjectExactCardinality(3, prop2, C(IRI())),
            () -> subj.getOWLObjectMaxCardinality(3, OP(IRI()), subj.getOWLThing()),
            () -> subj.getOWLObjectMaxCardinality(3, prop2, C(IRI())),
            () -> subj.getOWLObjectMinCardinality(3, OP(IRI()), subj.getOWLThing()),
            () -> subj.getOWLObjectMinCardinality(3, prop2, C(IRI())),
            () -> subj.getOWLDataExactCardinality(3, DP(IRI()), subj.getTopDatatype()),
            () -> subj.getOWLDataExactCardinality(3, dataProp, D(IRI())),
            () -> subj.getOWLDataMaxCardinality(3, DP(IRI()), subj.getTopDatatype()),
            () -> subj.getOWLDataMaxCardinality(3, dataProp, D(IRI())),
            () -> subj.getOWLDataMinCardinality(3, DP(IRI()), subj.getTopDatatype()),
            () -> subj.getOWLDataMinCardinality(3, dataProp, D(IRI())))
                .forEach(OWLDataFactoryImplTestCase::assertNotEqualsFromSupplier);

        Stream
            .of(s(() -> subj.getOWLDataOneOf(a2, b2), () -> subj.getOWLDataOneOf(a2, b2, c2)),
                s(() -> subj.getOWLDataSomeValuesFrom(DP(IRI()), dtype),
                    () -> subj.getOWLDataSomeValuesFrom(DP(IRI()), dtype)),
                s(() -> subj.getOWLDataSomeValuesFrom(dataProp, D(IRI())),
                    () -> subj.getOWLDataSomeValuesFrom(dataProp, D(IRI()))),
                s(() -> subj.getOWLDatatypeRestriction(dtype,
                    subj.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)),
                    () -> subj.getOWLDatatypeRestriction(dtype,
                        subj.getOWLFacetRestriction(MIN_INCLUSIVE, facetValue))),
                s(() -> subj.getOWLDifferentIndividualsAxiom(a1, b1),
                    () -> subj.getOWLDifferentIndividualsAxiom(a1, b1, c1)),
                s(() -> subj.getOWLDisjointClassesAxiom(a, b),
                    () -> subj.getOWLDisjointClassesAxiom(a, b, c5)),
                s(() -> subj.getOWLDisjointDataPropertiesAxiom(a4, b4, c4),
                    () -> subj.getOWLDisjointDataPropertiesAxiom(a4, b4)),
                s(() -> subj.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3),
                    () -> subj.getOWLDisjointObjectPropertiesAxiom(a3, b3)),
                s(() -> subj.getOWLEquivalentClassesAxiom(a, b),
                    () -> subj.getOWLEquivalentClassesAxiom(a, b, c5)),
                s(() -> subj.getOWLEquivalentDataPropertiesAxiom(a4, b4),
                    () -> subj.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4)),
                s(() -> subj.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3),
                    () -> subj.getOWLEquivalentObjectPropertiesAxiom(a3, b3)),
                s(() -> subj.getOWLLiteral("3", dtype), () -> subj.getOWLLiteral("4", dtype)),
                s(() -> subj.getOWLLiteral(TEST, LANG), () -> subj.getOWLLiteral("OTHER", LANG)),
                s(() -> subj.getOWLLiteral(TEST, LANG),
                    () -> subj.getOWLLiteral(TEST, "OTHER_LANG")),
                s(() -> subj.getOWLObjectComplementOf(operandA),
                    () -> subj.getOWLObjectComplementOf(operandB)),
                s(() -> subj.getOWLObjectIntersectionOf(a, b),
                    () -> subj.getOWLObjectIntersectionOf(a, b, c5)),
                s(() -> subj.getOWLObjectOneOf(a1, b1), () -> subj.getOWLObjectOneOf(a1, b1, c1)),
                s(() -> subj.getOWLObjectUnionOf(a, b, c5), () -> subj.getOWLObjectUnionOf(a, b)),
                s(() -> subj.getOWLSameIndividualAxiom(a1, b1),
                    () -> subj.getOWLSameIndividualAxiom(a1, b1, c1)),
                s(() -> subj.getOWLObjectExactCardinality(3, prop2, subj.getOWLThing()),
                    () -> subj.getOWLObjectExactCardinality(4, prop2, subj.getOWLThing())),
                s(() -> subj.getOWLObjectMaxCardinality(3, prop2, subj.getOWLThing()),
                    () -> subj.getOWLObjectMaxCardinality(4, prop2, subj.getOWLThing())),
                s(() -> subj.getOWLObjectMinCardinality(3, prop2, subj.getOWLThing()),
                    () -> subj.getOWLObjectMinCardinality(4, prop2, subj.getOWLThing())),
                s(() -> subj.getOWLDataExactCardinality(3, dataProp, subj.getTopDatatype()),
                    () -> subj.getOWLDataExactCardinality(4, dataProp, subj.getTopDatatype())),
                s(() -> subj.getOWLDataMaxCardinality(3, dataProp, subj.getTopDatatype()),
                    () -> subj.getOWLDataMaxCardinality(4, dataProp, subj.getTopDatatype())),
                s(() -> subj.getOWLDataMinCardinality(3, dataProp, subj.getTopDatatype()),
                    () -> subj.getOWLDataMinCardinality(4, dataProp, subj.getTopDatatype())))
            .forEach(l -> assertNotEqualsFromSuppliers(l.get(0), l.get(1)));
    }

    static List<Supplier<?>> s(Supplier<?>... suppliers) {
        return Arrays.asList(suppliers);
    }

    @Test
    void singletons() {
        s(subj::getBooleanOWLDatatype, subj::getDoubleOWLDatatype, subj::getFloatOWLDatatype,
            subj::getOWLBackwardCompatibleWith, subj::getOWLBottomDataProperty,
            subj::getOWLBottomObjectProperty, subj::getOWLDeprecated, subj::getOWLIncompatibleWith,
            subj::getOWLNothing, subj::getOWLThing, subj::getOWLTopDataProperty,
            subj::getOWLTopObjectProperty, subj::getOWLVersionInfo, subj::getRDFPlainLiteral,
            subj::getRDFSIsDefinedBy, subj::getRDFSLabel, subj::getRDFSSeeAlso,
            subj::getTopDatatype).forEach(OWLDataFactoryImplTestCase::assertSameFromSupplier);
    }
}
