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
package org.semanticweb.owlapi.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_INCLUSIVE;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
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

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

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
    private static final String URN_TEST = "urn:test#";

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
    private final OWLDataFactory testSubject = new OWLDataFactoryImpl();
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
    OWLLiteral facetValue = testSubject.getOWLLiteral("3", D(IRI()));
    OWLClass clsE = testSubject.getOWLClass(URN_TEST, "E");
    OWLClass clsB = testSubject.getOWLClass(URN_TEST, "B");
    OWLClass clsC = testSubject.getOWLClass(URN_TEST, "C");
    OWLClass clsD = testSubject.getOWLClass(URN_TEST, "D");
    OWLClassExpression operandA = C(IRI());
    OWLClassExpression operandB = C(IRI());

    private OWLClass C(IRI in) {
        return testSubject.getOWLClass(in);
    }

    private OWLDatatype D(IRI in) {
        return testSubject.getOWLDatatype(in);
    }

    private OWLDataProperty DP(IRI in) {
        return testSubject.getOWLDataProperty(in);
    }

    private OWLIndividual I() {
        return testSubject.getOWLNamedIndividual(IRI());
    }

    private static IRI IRI() {
        return IRI.getNextDocumentIRI("urn:test#A");
    }

    private OWLLiteral Literal() {
        return testSubject.getOWLLiteral("A" + COUNTER.getAndIncrement());
    }

    private OWLObjectProperty OP(IRI in) {
        return testSubject.getOWLObjectProperty(in);
    }

    @Test
    void shouldFindInternalisedSame() {
        assertSameFromSupplier(testSubject::getRDFPlainLiteral);
        assertSameFromSupplier(testSubject::getTopDatatype);
        assertSameFromSupplier(testSubject::getBooleanOWLDatatype);
        assertSameFromSupplier(testSubject::getDoubleOWLDatatype);
        assertSameFromSupplier(testSubject::getFloatOWLDatatype);
        assertSameFromSupplier(testSubject::getRDFSLabel);
        assertSameFromSupplier(testSubject::getRDFSSeeAlso);
        assertSameFromSupplier(testSubject::getRDFSIsDefinedBy);
        assertSameFromSupplier(testSubject::getOWLVersionInfo);
        assertSameFromSupplier(testSubject::getOWLBackwardCompatibleWith);
        assertSameFromSupplier(testSubject::getOWLIncompatibleWith);
        assertSameFromSupplier(testSubject::getOWLDeprecated);
        assertSameFromSupplier(testSubject::getOWLThing);
        assertSameFromSupplier(testSubject::getOWLNothing);
        assertSameFromSupplier(testSubject::getOWLTopObjectProperty);
        assertSameFromSupplier(testSubject::getOWLBottomObjectProperty);
        assertSameFromSupplier(testSubject::getOWLTopDataProperty);
        assertSameFromSupplier(testSubject::getOWLBottomDataProperty);
    }

    @Test
    void shouldHaveSWRLRulesEqual() {
        SWRLVariable var = testSubject.getSWRLVariable(URN_TEST, "x");
        List<SWRLClassAtom> body1 =
            l(testSubject.getSWRLClassAtom(clsE, var), testSubject.getSWRLClassAtom(clsC, var));
        List<SWRLClassAtom> head1 =
            l(testSubject.getSWRLClassAtom(clsB, var), testSubject.getSWRLClassAtom(clsD, var));
        List<SWRLClassAtom> body2 =
            l(testSubject.getSWRLClassAtom(clsC, var), testSubject.getSWRLClassAtom(clsE, var));
        List<SWRLClassAtom> head2 =
            l(testSubject.getSWRLClassAtom(clsD, var), testSubject.getSWRLClassAtom(clsB, var));
        assertEqualsFromSuppliers(() -> testSubject.getSWRLRule(body1, head1),
            () -> testSubject.getSWRLRule(body2, head2));
    }

    @Test
    void shouldHaveSWRLRulesWithAnnotationsEqual() {
        Collection<OWLAnnotation> ann1 =
            l(testSubject.getRDFSComment("test1"), testSubject.getRDFSLabel("test2"));
        Collection<OWLAnnotation> ann2 =
            l(testSubject.getRDFSLabel("test2"), testSubject.getRDFSComment("test1"));
        SWRLVariable var = testSubject.getSWRLVariable(URN_TEST, "x");
        List<SWRLClassAtom> body1 =
            l(testSubject.getSWRLClassAtom(clsE, var), testSubject.getSWRLClassAtom(clsC, var));
        List<SWRLClassAtom> head1 =
            l(testSubject.getSWRLClassAtom(clsB, var), testSubject.getSWRLClassAtom(clsD, var));
        List<SWRLClassAtom> body2 =
            l(testSubject.getSWRLClassAtom(clsC, var), testSubject.getSWRLClassAtom(clsE, var));
        List<SWRLClassAtom> head2 =
            l(testSubject.getSWRLClassAtom(clsD, var), testSubject.getSWRLClassAtom(clsB, var));
        assertEqualsFromSuppliers(() -> testSubject.getSWRLRule(body1, head1, ann1),
            () -> testSubject.getSWRLRule(body2, head2, ann2));
    }

    @Test
    void testAsSubAxiomsEquivalentClasses() {
        assertEqualsFromSupplier(() -> C(iri));
        assertEqualsFromSupplier(() -> D(iri));
        assertEqualsFromSupplier(() -> DP(iri));
        assertEqualsFromSupplier(() -> OP(iri));
        assertEqualsFromSupplier(() -> testSubject.getOWLAnnotationProperty(iri));
        assertEqualsFromSupplier(() -> testSubject.getOWLAsymmetricObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLClassAssertionAxiom(right, a1));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataAllValuesFrom(dataProp, dtype));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataComplementOf(dtype));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLDataExactCardinality(cardinality, dataProp, dtype));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataExactCardinality(cardinality, dataProp,
            testSubject.getTopDatatype()));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataHasValue(dataProp, filler1));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLDataMaxCardinality(cardinality, dataProp, dtype));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataMaxCardinality(cardinality, dataProp,
            testSubject.getTopDatatype()));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLDataMinCardinality(cardinality, dataProp, dtype));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataMinCardinality(cardinality, dataProp,
            testSubject.getTopDatatype()));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataOneOf(a2, b2, c2));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLDataPropertyAssertionAxiom(dataProp, s1, o2));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataPropertyDomainAxiom(left, right));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataPropertyRangeAxiom(left, dtype));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataSomeValuesFrom(dataProp, dtype));
        assertEqualsFromSupplier(() -> testSubject.getOWLDatatypeRestriction(dtype,
            testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)));
        assertEqualsFromSupplier(() -> testSubject.getOWLDifferentIndividualsAxiom(a1, b1, c1));
        assertEqualsFromSupplier(() -> testSubject.getOWLDisjointClassesAxiom(a, b, c5));
        assertEqualsFromSupplier(() -> testSubject.getOWLDisjointDataPropertiesAxiom(a4, b4, c4));
        assertEqualsFromSupplier(() -> testSubject.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentClassesAxiom(a, b, c5));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentClassesAxiom(classExpressions));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentDataPropertiesAxiom(properties));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3));
        assertEqualsFromSupplier(() -> testSubject.getOWLFunctionalDataPropertyAxiom(dataProp));
        assertEqualsFromSupplier(() -> testSubject.getOWLFunctionalObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLInverseFunctionalObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLIrreflexiveObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLLiteral("3", dtype));
        assertEqualsFromSupplier(() -> testSubject.getOWLLiteral(TEST, LANG));
        assertEqualsFromSupplier(() -> testSubject.getOWLNamedIndividual(iri));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(dataProp, s1, o2));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, o1));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectAllValuesFrom(prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectComplementOf(operand));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLObjectExactCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectExactCardinality(cardinality, prop2,
            testSubject.getOWLThing()));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectHasSelf(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectHasValue(prop2, filler));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectIntersectionOf(a, b, c5));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLObjectMaxCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectMaxCardinality(cardinality, prop2,
            testSubject.getOWLThing()));
        assertEqualsFromSupplier(
            () -> testSubject.getOWLObjectMinCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectMinCardinality(cardinality, prop2,
            testSubject.getOWLThing()));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectOneOf(a1, b1, c1));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyAssertionAxiom(p3, s1, o1));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyDomainAxiom(p3, right));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyRangeAxiom(p3, right));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectSomeValuesFrom(prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectUnionOf(a, b, c5));
        assertEqualsFromSupplier(() -> testSubject.getOWLReflexiveObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLSameIndividualAxiom(a1, b1, c1));
        assertEqualsFromSupplier(() -> testSubject.getOWLSubDataPropertyOfAxiom(left, dataProp));
        assertEqualsFromSupplier(() -> testSubject.getOWLSubObjectPropertyOfAxiom(p3, prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLSymmetricObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLTransitiveObjectPropertyAxiom(prop2));
        assertNotEqualsFromSupplier(() -> C(IRI()));
        assertNotEqualsFromSupplier(() -> D(IRI()));
        assertNotEqualsFromSupplier(() -> DP(IRI()));
        assertNotEqualsFromSupplier(() -> OP(IRI()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLAnnotationProperty(IRI()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLAsymmetricObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLClassAssertionAxiom(C(IRI()), a1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLClassAssertionAxiom(right, I()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataAllValuesFrom(DP(IRI()), dtype));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataAllValuesFrom(dataProp, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataComplementOf(D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataExactCardinality(3, DP(IRI()),
            testSubject.getTopDatatype()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataExactCardinality(3, dataProp, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataHasValue(DP(IRI()), filler1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataHasValue(dataProp, Literal()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataMaxCardinality(3, DP(IRI()), testSubject.getTopDatatype()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataMaxCardinality(3, dataProp, D(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataMinCardinality(3, DP(IRI()), testSubject.getTopDatatype()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataMinCardinality(3, dataProp, D(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataPropertyAssertionAxiom(DP(IRI()), s1, o2));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataPropertyAssertionAxiom(dataProp, I(), o2));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataPropertyAssertionAxiom(dataProp, s1, Literal()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataPropertyDomainAxiom(DP(IRI()), right));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataPropertyDomainAxiom(left, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLDataPropertyRangeAxiom(DP(IRI()), dtype));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyRangeAxiom(left, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLFunctionalDataPropertyAxiom(DP(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLFunctionalObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLInverseFunctionalObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLIrreflexiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLLiteral("3", D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNamedIndividual(IRI()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(DP(IRI()), s1, o2));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(dataProp, I(), o2));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(dataProp, s1, Literal()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(OP(IRI()), s1, o1));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p3, I(), o1));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, I()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectAllValuesFrom(OP(IRI()), a));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectAllValuesFrom(prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectExactCardinality(3, OP(IRI()),
            testSubject.getOWLThing()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectExactCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectHasSelf(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectHasValue(OP(IRI()), filler));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectHasValue(prop2, I()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectMaxCardinality(3, OP(IRI()), testSubject.getOWLThing()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectMaxCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectMinCardinality(3, OP(IRI()), testSubject.getOWLThing()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectMinCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectPropertyAssertionAxiom(OP(IRI()), s1, o1));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectPropertyAssertionAxiom(p3, I(), o1));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectPropertyAssertionAxiom(p3, s1, I()));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectPropertyDomainAxiom(OP(IRI()), right));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectPropertyDomainAxiom(p3, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLObjectPropertyRangeAxiom(OP(IRI()), right));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyRangeAxiom(p3, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectSomeValuesFrom(OP(IRI()), a));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectSomeValuesFrom(prop2, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLReflexiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLSubDataPropertyOfAxiom(DP(IRI()), dataProp));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLSubDataPropertyOfAxiom(left, DP(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLSubObjectPropertyOfAxiom(OP(IRI()), prop2));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLSubObjectPropertyOfAxiom(p3, OP(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLSymmetricObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(
            () -> testSubject.getOWLTransitiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLDataExactCardinality(3, dataProp, testSubject.getTopDatatype()),
            () -> testSubject.getOWLDataExactCardinality(4, dataProp,
                testSubject.getTopDatatype()));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLDataMaxCardinality(3, dataProp, testSubject.getTopDatatype()),
            () -> testSubject.getOWLDataMaxCardinality(4, dataProp, testSubject.getTopDatatype()));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLDataMinCardinality(3, dataProp, testSubject.getTopDatatype()),
            () -> testSubject.getOWLDataMinCardinality(4, dataProp, testSubject.getTopDatatype()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataOneOf(a2, b2),
            () -> testSubject.getOWLDataOneOf(a2, b2, c2));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataSomeValuesFrom(DP(IRI()), dtype),
            () -> testSubject.getOWLDataSomeValuesFrom(DP(IRI()), dtype));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataSomeValuesFrom(dataProp, D(IRI())),
            () -> testSubject.getOWLDataSomeValuesFrom(dataProp, D(IRI())));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLDatatypeRestriction(dtype,
                testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)),
            () -> testSubject.getOWLDatatypeRestriction(dtype,
                testSubject.getOWLFacetRestriction(MIN_INCLUSIVE, facetValue)));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDifferentIndividualsAxiom(a1, b1),
            () -> testSubject.getOWLDifferentIndividualsAxiom(a1, b1, c1));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDisjointClassesAxiom(a, b),
            () -> testSubject.getOWLDisjointClassesAxiom(a, b, c5));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLDisjointDataPropertiesAxiom(a4, b4, c4),
            () -> testSubject.getOWLDisjointDataPropertiesAxiom(a4, b4));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3),
            () -> testSubject.getOWLDisjointObjectPropertiesAxiom(a3, b3));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLEquivalentClassesAxiom(a, b),
            () -> testSubject.getOWLEquivalentClassesAxiom(a, b, c5));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLEquivalentDataPropertiesAxiom(a4, b4),
            () -> testSubject.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3),
            () -> testSubject.getOWLEquivalentObjectPropertiesAxiom(a3, b3));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLLiteral("3", dtype),
            () -> testSubject.getOWLLiteral("4", dtype));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLLiteral(TEST, LANG),
            () -> testSubject.getOWLLiteral("OTHER", LANG));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLLiteral(TEST, LANG),
            () -> testSubject.getOWLLiteral(TEST, "OTHER_LANG"));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectComplementOf(operandA),
            () -> testSubject.getOWLObjectComplementOf(operandB));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLObjectExactCardinality(3, prop2, testSubject.getOWLThing()),
            () -> testSubject.getOWLObjectExactCardinality(4, prop2, testSubject.getOWLThing()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectIntersectionOf(a, b),
            () -> testSubject.getOWLObjectIntersectionOf(a, b, c5));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLObjectMaxCardinality(3, prop2, testSubject.getOWLThing()),
            () -> testSubject.getOWLObjectMaxCardinality(4, prop2, testSubject.getOWLThing()));
        assertNotEqualsFromSuppliers(
            () -> testSubject.getOWLObjectMinCardinality(3, prop2, testSubject.getOWLThing()),
            () -> testSubject.getOWLObjectMinCardinality(4, prop2, testSubject.getOWLThing()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectOneOf(a1, b1),
            () -> testSubject.getOWLObjectOneOf(a1, b1, c1));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectUnionOf(a, b, c5),
            () -> testSubject.getOWLObjectUnionOf(a, b));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLSameIndividualAxiom(a1, b1),
            () -> testSubject.getOWLSameIndividualAxiom(a1, b1, c1));
        assertSameFromSupplier(testSubject::getBooleanOWLDatatype);
        assertSameFromSupplier(testSubject::getDoubleOWLDatatype);
        assertSameFromSupplier(testSubject::getFloatOWLDatatype);
        assertSameFromSupplier(testSubject::getOWLBackwardCompatibleWith);
        assertSameFromSupplier(testSubject::getOWLBottomDataProperty);
        assertSameFromSupplier(testSubject::getOWLBottomObjectProperty);
        assertSameFromSupplier(testSubject::getOWLDeprecated);
        assertSameFromSupplier(testSubject::getOWLIncompatibleWith);
        assertSameFromSupplier(testSubject::getOWLNothing);
        assertSameFromSupplier(testSubject::getOWLThing);
        assertSameFromSupplier(testSubject::getOWLTopDataProperty);
        assertSameFromSupplier(testSubject::getOWLTopObjectProperty);
        assertSameFromSupplier(testSubject::getOWLVersionInfo);
        assertSameFromSupplier(testSubject::getRDFPlainLiteral);
        assertSameFromSupplier(testSubject::getRDFSIsDefinedBy);
        assertSameFromSupplier(testSubject::getRDFSLabel);
        assertSameFromSupplier(testSubject::getRDFSSeeAlso);
        assertSameFromSupplier(testSubject::getTopDatatype);
    }
}
