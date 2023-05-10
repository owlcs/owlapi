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

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

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
        assertEqualsFromSupplier(() -> C(iri));
        assertEqualsFromSupplier(() -> D(iri));
        assertEqualsFromSupplier(() -> DP(iri));
        assertEqualsFromSupplier(() -> OP(iri));
        assertEqualsFromSupplier(() -> subj.getOWLAnnotationProperty(iri));
        assertEqualsFromSupplier(() -> subj.getOWLAsymmetricObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> subj.getOWLClassAssertionAxiom(right, a1));
        assertEqualsFromSupplier(() -> subj.getOWLDataAllValuesFrom(dataProp, dtype));
        assertEqualsFromSupplier(() -> subj.getOWLDataComplementOf(dtype));
        assertEqualsFromSupplier(
            () -> subj.getOWLDataExactCardinality(cardinality, dataProp, dtype));
        assertEqualsFromSupplier(
            () -> subj.getOWLDataExactCardinality(cardinality, dataProp, subj.getTopDatatype()));
        assertEqualsFromSupplier(() -> subj.getOWLDataHasValue(dataProp, filler1));
        assertEqualsFromSupplier(() -> subj.getOWLDataMaxCardinality(cardinality, dataProp, dtype));
        assertEqualsFromSupplier(
            () -> subj.getOWLDataMaxCardinality(cardinality, dataProp, subj.getTopDatatype()));
        assertEqualsFromSupplier(() -> subj.getOWLDataMinCardinality(cardinality, dataProp, dtype));
        assertEqualsFromSupplier(
            () -> subj.getOWLDataMinCardinality(cardinality, dataProp, subj.getTopDatatype()));
        assertEqualsFromSupplier(() -> subj.getOWLDataOneOf(a2, b2, c2));
        assertEqualsFromSupplier(() -> subj.getOWLDataPropertyAssertionAxiom(dataProp, s1, o2));
        assertEqualsFromSupplier(() -> subj.getOWLDataPropertyDomainAxiom(left, right));
        assertEqualsFromSupplier(() -> subj.getOWLDataPropertyRangeAxiom(left, dtype));
        assertEqualsFromSupplier(() -> subj.getOWLDataSomeValuesFrom(dataProp, dtype));
        assertEqualsFromSupplier(() -> subj.getOWLDatatypeRestriction(dtype,
            subj.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)));
        assertEqualsFromSupplier(() -> subj.getOWLDifferentIndividualsAxiom(a1, b1, c1));
        assertEqualsFromSupplier(() -> subj.getOWLDisjointClassesAxiom(a, b, c5));
        assertEqualsFromSupplier(() -> subj.getOWLDisjointDataPropertiesAxiom(a4, b4, c4));
        assertEqualsFromSupplier(() -> subj.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3));
        assertEqualsFromSupplier(() -> subj.getOWLEquivalentClassesAxiom(a, b, c5));
        assertEqualsFromSupplier(() -> subj.getOWLEquivalentClassesAxiom(classExpressions));
        assertEqualsFromSupplier(() -> subj.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4));
        assertEqualsFromSupplier(() -> subj.getOWLEquivalentDataPropertiesAxiom(properties));
        assertEqualsFromSupplier(() -> subj.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3));
        assertEqualsFromSupplier(() -> subj.getOWLFunctionalDataPropertyAxiom(dataProp));
        assertEqualsFromSupplier(() -> subj.getOWLFunctionalObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> subj.getOWLInverseFunctionalObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> subj.getOWLIrreflexiveObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> subj.getOWLLiteral("3", dtype));
        assertEqualsFromSupplier(() -> subj.getOWLLiteral(TEST, LANG));
        assertEqualsFromSupplier(() -> subj.getOWLNamedIndividual(iri));
        assertEqualsFromSupplier(
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(dataProp, s1, o2));
        assertEqualsFromSupplier(() -> subj.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, o1));
        assertEqualsFromSupplier(() -> subj.getOWLObjectAllValuesFrom(prop2, a));
        assertEqualsFromSupplier(() -> subj.getOWLObjectComplementOf(operand));
        assertEqualsFromSupplier(() -> subj.getOWLObjectExactCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(
            () -> subj.getOWLObjectExactCardinality(cardinality, prop2, subj.getOWLThing()));
        assertEqualsFromSupplier(() -> subj.getOWLObjectHasSelf(prop2));
        assertEqualsFromSupplier(() -> subj.getOWLObjectHasValue(prop2, filler));
        assertEqualsFromSupplier(() -> subj.getOWLObjectIntersectionOf(a, b, c5));
        assertEqualsFromSupplier(() -> subj.getOWLObjectMaxCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(
            () -> subj.getOWLObjectMaxCardinality(cardinality, prop2, subj.getOWLThing()));
        assertEqualsFromSupplier(() -> subj.getOWLObjectMinCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(
            () -> subj.getOWLObjectMinCardinality(cardinality, prop2, subj.getOWLThing()));
        assertEqualsFromSupplier(() -> subj.getOWLObjectOneOf(a1, b1, c1));
        assertEqualsFromSupplier(() -> subj.getOWLObjectPropertyAssertionAxiom(p3, s1, o1));
        assertEqualsFromSupplier(() -> subj.getOWLObjectPropertyDomainAxiom(p3, right));
        assertEqualsFromSupplier(() -> subj.getOWLObjectPropertyRangeAxiom(p3, right));
        assertEqualsFromSupplier(() -> subj.getOWLObjectSomeValuesFrom(prop2, a));
        assertEqualsFromSupplier(() -> subj.getOWLObjectUnionOf(a, b, c5));
        assertEqualsFromSupplier(() -> subj.getOWLReflexiveObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> subj.getOWLSameIndividualAxiom(a1, b1, c1));
        assertEqualsFromSupplier(() -> subj.getOWLSubDataPropertyOfAxiom(left, dataProp));
        assertEqualsFromSupplier(() -> subj.getOWLSubObjectPropertyOfAxiom(p3, prop2));
        assertEqualsFromSupplier(() -> subj.getOWLSymmetricObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> subj.getOWLTransitiveObjectPropertyAxiom(prop2));
        assertNotEqualsFromSupplier(() -> C(IRI()));
        assertNotEqualsFromSupplier(() -> D(IRI()));
        assertNotEqualsFromSupplier(() -> DP(IRI()));
        assertNotEqualsFromSupplier(() -> OP(IRI()));
        assertNotEqualsFromSupplier(() -> subj.getOWLAnnotationProperty(IRI()));
        assertNotEqualsFromSupplier(() -> subj.getOWLAsymmetricObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLClassAssertionAxiom(C(IRI()), a1));
        assertNotEqualsFromSupplier(() -> subj.getOWLClassAssertionAxiom(right, I()));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataAllValuesFrom(DP(IRI()), dtype));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataAllValuesFrom(dataProp, D(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataComplementOf(D(IRI())));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLDataExactCardinality(3, DP(IRI()), subj.getTopDatatype()));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataExactCardinality(3, dataProp, D(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataHasValue(DP(IRI()), filler1));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataHasValue(dataProp, Literal()));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLDataMaxCardinality(3, DP(IRI()), subj.getTopDatatype()));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataMaxCardinality(3, dataProp, D(IRI())));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLDataMinCardinality(3, DP(IRI()), subj.getTopDatatype()));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataMinCardinality(3, dataProp, D(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataPropertyAssertionAxiom(DP(IRI()), s1, o2));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataPropertyAssertionAxiom(dataProp, I(), o2));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLDataPropertyAssertionAxiom(dataProp, s1, Literal()));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataPropertyDomainAxiom(DP(IRI()), right));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataPropertyDomainAxiom(left, C(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataPropertyRangeAxiom(DP(IRI()), dtype));
        assertNotEqualsFromSupplier(() -> subj.getOWLDataPropertyRangeAxiom(left, D(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLFunctionalDataPropertyAxiom(DP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLFunctionalObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLInverseFunctionalObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLIrreflexiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLLiteral("3", D(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLNamedIndividual(IRI()));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(DP(IRI()), s1, o2));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(dataProp, I(), o2));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLNegativeDataPropertyAssertionAxiom(dataProp, s1, Literal()));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLNegativeObjectPropertyAssertionAxiom(OP(IRI()), s1, o1));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLNegativeObjectPropertyAssertionAxiom(p3, I(), o1));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, I()));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectAllValuesFrom(OP(IRI()), a));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectAllValuesFrom(prop2, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLObjectExactCardinality(3, OP(IRI()), subj.getOWLThing()));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectExactCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectHasSelf(OP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectHasValue(OP(IRI()), filler));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectHasValue(prop2, I()));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLObjectMaxCardinality(3, OP(IRI()), subj.getOWLThing()));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectMaxCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLObjectMinCardinality(3, OP(IRI()), subj.getOWLThing()));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectMinCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(
            () -> subj.getOWLObjectPropertyAssertionAxiom(OP(IRI()), s1, o1));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectPropertyAssertionAxiom(p3, I(), o1));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectPropertyAssertionAxiom(p3, s1, I()));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectPropertyDomainAxiom(OP(IRI()), right));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectPropertyDomainAxiom(p3, C(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectPropertyRangeAxiom(OP(IRI()), right));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectPropertyRangeAxiom(p3, C(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectSomeValuesFrom(OP(IRI()), a));
        assertNotEqualsFromSupplier(() -> subj.getOWLObjectSomeValuesFrom(prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLReflexiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLSubDataPropertyOfAxiom(DP(IRI()), dataProp));
        assertNotEqualsFromSupplier(() -> subj.getOWLSubDataPropertyOfAxiom(left, DP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLSubObjectPropertyOfAxiom(OP(IRI()), prop2));
        assertNotEqualsFromSupplier(() -> subj.getOWLSubObjectPropertyOfAxiom(p3, OP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLSymmetricObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> subj.getOWLTransitiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSuppliers(
            () -> subj.getOWLDataExactCardinality(3, dataProp, subj.getTopDatatype()),
            () -> subj.getOWLDataExactCardinality(4, dataProp, subj.getTopDatatype()));
        assertNotEqualsFromSuppliers(
            () -> subj.getOWLDataMaxCardinality(3, dataProp, subj.getTopDatatype()),
            () -> subj.getOWLDataMaxCardinality(4, dataProp, subj.getTopDatatype()));
        assertNotEqualsFromSuppliers(
            () -> subj.getOWLDataMinCardinality(3, dataProp, subj.getTopDatatype()),
            () -> subj.getOWLDataMinCardinality(4, dataProp, subj.getTopDatatype()));
        assertNotEqualsFromSuppliers(() -> subj.getOWLDataOneOf(a2, b2),
            () -> subj.getOWLDataOneOf(a2, b2, c2));
        assertNotEqualsFromSuppliers(() -> subj.getOWLDataSomeValuesFrom(DP(IRI()), dtype),
            () -> subj.getOWLDataSomeValuesFrom(DP(IRI()), dtype));
        assertNotEqualsFromSuppliers(() -> subj.getOWLDataSomeValuesFrom(dataProp, D(IRI())),
            () -> subj.getOWLDataSomeValuesFrom(dataProp, D(IRI())));
        assertNotEqualsFromSuppliers(
            () -> subj.getOWLDatatypeRestriction(dtype,
                subj.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)),
            () -> subj.getOWLDatatypeRestriction(dtype,
                subj.getOWLFacetRestriction(MIN_INCLUSIVE, facetValue)));
        assertNotEqualsFromSuppliers(() -> subj.getOWLDifferentIndividualsAxiom(a1, b1),
            () -> subj.getOWLDifferentIndividualsAxiom(a1, b1, c1));
        assertNotEqualsFromSuppliers(() -> subj.getOWLDisjointClassesAxiom(a, b),
            () -> subj.getOWLDisjointClassesAxiom(a, b, c5));
        assertNotEqualsFromSuppliers(() -> subj.getOWLDisjointDataPropertiesAxiom(a4, b4, c4),
            () -> subj.getOWLDisjointDataPropertiesAxiom(a4, b4));
        assertNotEqualsFromSuppliers(() -> subj.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3),
            () -> subj.getOWLDisjointObjectPropertiesAxiom(a3, b3));
        assertNotEqualsFromSuppliers(() -> subj.getOWLEquivalentClassesAxiom(a, b),
            () -> subj.getOWLEquivalentClassesAxiom(a, b, c5));
        assertNotEqualsFromSuppliers(() -> subj.getOWLEquivalentDataPropertiesAxiom(a4, b4),
            () -> subj.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4));
        assertNotEqualsFromSuppliers(() -> subj.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3),
            () -> subj.getOWLEquivalentObjectPropertiesAxiom(a3, b3));
        assertNotEqualsFromSuppliers(() -> subj.getOWLLiteral("3", dtype),
            () -> subj.getOWLLiteral("4", dtype));
        assertNotEqualsFromSuppliers(() -> subj.getOWLLiteral(TEST, LANG),
            () -> subj.getOWLLiteral("OTHER", LANG));
        assertNotEqualsFromSuppliers(() -> subj.getOWLLiteral(TEST, LANG),
            () -> subj.getOWLLiteral(TEST, "OTHER_LANG"));
        assertNotEqualsFromSuppliers(() -> subj.getOWLObjectComplementOf(operandA),
            () -> subj.getOWLObjectComplementOf(operandB));
        assertNotEqualsFromSuppliers(
            () -> subj.getOWLObjectExactCardinality(3, prop2, subj.getOWLThing()),
            () -> subj.getOWLObjectExactCardinality(4, prop2, subj.getOWLThing()));
        assertNotEqualsFromSuppliers(() -> subj.getOWLObjectIntersectionOf(a, b),
            () -> subj.getOWLObjectIntersectionOf(a, b, c5));
        assertNotEqualsFromSuppliers(
            () -> subj.getOWLObjectMaxCardinality(3, prop2, subj.getOWLThing()),
            () -> subj.getOWLObjectMaxCardinality(4, prop2, subj.getOWLThing()));
        assertNotEqualsFromSuppliers(
            () -> subj.getOWLObjectMinCardinality(3, prop2, subj.getOWLThing()),
            () -> subj.getOWLObjectMinCardinality(4, prop2, subj.getOWLThing()));
        assertNotEqualsFromSuppliers(() -> subj.getOWLObjectOneOf(a1, b1),
            () -> subj.getOWLObjectOneOf(a1, b1, c1));
        assertNotEqualsFromSuppliers(() -> subj.getOWLObjectUnionOf(a, b, c5),
            () -> subj.getOWLObjectUnionOf(a, b));
        assertNotEqualsFromSuppliers(() -> subj.getOWLSameIndividualAxiom(a1, b1),
            () -> subj.getOWLSameIndividualAxiom(a1, b1, c1));
        assertSameFromSupplier(subj::getBooleanOWLDatatype);
        assertSameFromSupplier(subj::getDoubleOWLDatatype);
        assertSameFromSupplier(subj::getFloatOWLDatatype);
        assertSameFromSupplier(subj::getOWLBackwardCompatibleWith);
        assertSameFromSupplier(subj::getOWLBottomDataProperty);
        assertSameFromSupplier(subj::getOWLBottomObjectProperty);
        assertSameFromSupplier(subj::getOWLDeprecated);
        assertSameFromSupplier(subj::getOWLIncompatibleWith);
        assertSameFromSupplier(subj::getOWLNothing);
        assertSameFromSupplier(subj::getOWLThing);
        assertSameFromSupplier(subj::getOWLTopDataProperty);
        assertSameFromSupplier(subj::getOWLTopObjectProperty);
        assertSameFromSupplier(subj::getOWLVersionInfo);
        assertSameFromSupplier(subj::getRDFPlainLiteral);
        assertSameFromSupplier(subj::getRDFSIsDefinedBy);
        assertSameFromSupplier(subj::getRDFSLabel);
        assertSameFromSupplier(subj::getRDFSSeeAlso);
        assertSameFromSupplier(subj::getTopDatatype);
    }
}
