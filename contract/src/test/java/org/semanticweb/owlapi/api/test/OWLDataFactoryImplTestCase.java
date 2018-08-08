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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.semanticweb.owlapi.vocab.OWLFacet.MAX_EXCLUSIVE;
import static org.semanticweb.owlapi.vocab.OWLFacet.MIN_INCLUSIVE;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.Test;
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
@SuppressWarnings({"javadoc"})
public class OWLDataFactoryImplTestCase {
    private static void assertEqualsFromSupplier(Supplier<?> s) {
        Object s1 = s.get();
        Object s2 = s.get();
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertEqualsFromSuppliers(Supplier<?> sa, Supplier<?> sb) {
        Object s1 = sa.get();
        Object s2 = sb.get();
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertNotEqualsFromSupplier(Supplier<?> s) {
        Object s1 = s.get();
        Object s2 = s.get();
        assertNotEquals(s1, s2);
        assertNotEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertNotEqualsFromSuppliers(Supplier<?> sa, Supplier<?> sb) {
        Object s1 = sa.get();
        Object s2 = sb.get();
        assertNotEquals(s1, s2);
        assertNotEquals(s1.hashCode(), s2.hashCode());
    }

    private static void assertSameFromSupplier(Supplier<?> s) {
        assertSame(s.get(), s.get());
    }

    int cardinality = 3;
    private final OWLDataFactory testSubject = new OWLDataFactoryImpl();
    private final AtomicInteger counter = new AtomicInteger(1);
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
    OWLClassExpression c = C(IRI());
    OWLIndividual c1 = I();
    OWLLiteral c2 = Literal();
    OWLObjectProperty c3 = OP(IRI());
    OWLDataProperty c4 = DP(IRI());
    OWLDatatype dt = D(IRI());
    IRI iri = IRI();
    OWLDataProperty left = DP(IRI());
    OWLIndividual o1 = I();
    OWLLiteral o2 = Literal();
    OWLObjectProperty p3 = OP(IRI());
    OWLDataProperty prop = DP(IRI());
    OWLObjectProperty prop2 = OP(IRI());
    OWLClass right = C(IRI());
    OWLIndividual s1 = I();
    OWLClassExpression[] classExpressions = {C(IRI()), C(IRI())};
    OWLDataPropertyExpression[] properties = {a4, b4};
    OWLClassExpression operand = C(IRI());
    OWLLiteral filler1 = Literal();
    OWLIndividual filler = I();
    OWLLiteral facetValue = testSubject.getOWLLiteral("3", D(IRI()));
    OWLClass clsA = testSubject.getOWLClass("urn:test#", "A");
    OWLClass clsB = testSubject.getOWLClass("urn:test#", "B");
    OWLClass clsC = testSubject.getOWLClass("urn:test#", "C");
    OWLClass clsD = testSubject.getOWLClass("urn:test#", "D");
    OWLClassExpression operandA = C(IRI());
    OWLClassExpression operandB = C(IRI());
    
    private OWLClass C(IRI i) {
        return testSubject.getOWLClass(i);
    }

    private OWLDatatype D(IRI i) {
        return testSubject.getOWLDatatype(i);
    }

    private OWLDataProperty DP(IRI i) {
        return testSubject.getOWLDataProperty(i);
    }

    private OWLIndividual I() {
        return testSubject.getOWLNamedIndividual(IRI());
    }

    private static IRI IRI() {
        return IRI.getNextDocumentIRI("urn:test#A");
    }

    private OWLLiteral Literal() {
        return testSubject.getOWLLiteral("A" + counter.getAndIncrement());
    }

    private OWLObjectProperty OP(IRI i) {
        return testSubject.getOWLObjectProperty(i);
    }

    @Test
    public void shouldFindInternalisedSame() {
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
    public void shouldHaveSWRLRulesEqual() {
        SWRLVariable var = testSubject.getSWRLVariable("urn:test#", "x");
        List<SWRLClassAtom> body1 = Arrays.asList(testSubject.getSWRLClassAtom(clsA, var), testSubject.getSWRLClassAtom(clsC, var));
        List<SWRLClassAtom> head1 = Arrays.asList(testSubject.getSWRLClassAtom(clsB, var), testSubject.getSWRLClassAtom(clsD, var));
        List<SWRLClassAtom> body2 = Arrays.asList(testSubject.getSWRLClassAtom(clsC, var), testSubject.getSWRLClassAtom(clsA, var));
        List<SWRLClassAtom> head2 = Arrays.asList(testSubject.getSWRLClassAtom(clsD, var), testSubject.getSWRLClassAtom(clsB, var));
        assertEqualsFromSuppliers(() -> testSubject.getSWRLRule(body1, head1), () -> testSubject.getSWRLRule(body2, head2));
    }

    @Test
    public void shouldHaveSWRLRulesWithAnnotationsEqual() {
        Collection<OWLAnnotation> ann1 = Arrays.asList(testSubject.getRDFSComment("test1"), testSubject.getRDFSLabel("test2"));
        Collection<OWLAnnotation> ann2 = Arrays.asList(testSubject.getRDFSLabel("test2"), testSubject.getRDFSComment("test1"));
        SWRLVariable var = testSubject.getSWRLVariable("urn:test#", "x");
        List<SWRLClassAtom> body1 = Arrays.asList(testSubject.getSWRLClassAtom(clsA, var), testSubject.getSWRLClassAtom(clsC, var));
        List<SWRLClassAtom> head1 = Arrays.asList(testSubject.getSWRLClassAtom(clsB, var), testSubject.getSWRLClassAtom(clsD, var));
        List<SWRLClassAtom> body2 = Arrays.asList(testSubject.getSWRLClassAtom(clsC, var), testSubject.getSWRLClassAtom(clsA, var));
        List<SWRLClassAtom> head2 = Arrays.asList(testSubject.getSWRLClassAtom(clsD, var), testSubject.getSWRLClassAtom(clsB, var));
        assertEqualsFromSuppliers(() -> testSubject.getSWRLRule(body1, head1, ann1), () -> testSubject.getSWRLRule(body2, head2, ann2));
    }

    @Test
    public void testAsSubAxiomsEquivalentClasses() {
        assertEqualsFromSupplier(() -> C(iri));
        assertEqualsFromSupplier(() -> D(iri));
        assertEqualsFromSupplier(() -> DP(iri));
        assertEqualsFromSupplier(() -> OP(iri));
        assertEqualsFromSupplier(() -> testSubject.getOWLAnnotationProperty(iri));
        assertEqualsFromSupplier(() -> testSubject.getOWLAsymmetricObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLClassAssertionAxiom(right, a1));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataAllValuesFrom(prop, dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataComplementOf(dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataExactCardinality(cardinality, prop, dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataExactCardinality(cardinality, prop, testSubject.getTopDatatype()));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataHasValue(prop, filler1));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataMaxCardinality(cardinality, prop, dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataMaxCardinality(cardinality, prop, testSubject.getTopDatatype()));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataMinCardinality(cardinality, prop, dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataMinCardinality(cardinality, prop, testSubject.getTopDatatype()));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataOneOf(a2, b2, c2));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataPropertyAssertionAxiom(prop, s1, o2));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataPropertyDomainAxiom(left, right));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataPropertyRangeAxiom(left, dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLDataSomeValuesFrom(prop, dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLDatatypeRestriction(dt, testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)));
        assertEqualsFromSupplier(() -> testSubject.getOWLDifferentIndividualsAxiom(a1, b1, c1));
        assertEqualsFromSupplier(() -> testSubject.getOWLDisjointClassesAxiom(a, b, c));
        assertEqualsFromSupplier(() -> testSubject.getOWLDisjointDataPropertiesAxiom(a4, b4, c4));
        assertEqualsFromSupplier(() -> testSubject.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentClassesAxiom(a, b, c));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentClassesAxiom(classExpressions));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentDataPropertiesAxiom(properties));
        assertEqualsFromSupplier(() -> testSubject.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3));
        assertEqualsFromSupplier(() -> testSubject.getOWLFunctionalDataPropertyAxiom(prop));
        assertEqualsFromSupplier(() -> testSubject.getOWLFunctionalObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLInverseFunctionalObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLIrreflexiveObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLLiteral("3", dt));
        assertEqualsFromSupplier(() -> testSubject.getOWLLiteral("TEST", "LANG"));
        assertEqualsFromSupplier(() -> testSubject.getOWLNamedIndividual(iri));
        assertEqualsFromSupplier(() -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(prop, s1, o2));
        assertEqualsFromSupplier(() -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, o1));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectAllValuesFrom(prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectComplementOf(operand));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectExactCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectExactCardinality(cardinality, prop2, testSubject.getOWLThing()));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectHasSelf(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectHasValue(prop2, filler));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectIntersectionOf(a, b, c));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectMaxCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectMaxCardinality(cardinality, prop2, testSubject.getOWLThing()));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectMinCardinality(cardinality, prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectMinCardinality(cardinality, prop2, testSubject.getOWLThing()));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectOneOf(a1, b1, c1));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyAssertionAxiom(p3, s1, o1));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyDomainAxiom(p3, right));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyRangeAxiom(p3, right));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectSomeValuesFrom(prop2, a));
        assertEqualsFromSupplier(() -> testSubject.getOWLObjectUnionOf(a, b, c));
        assertEqualsFromSupplier(() -> testSubject.getOWLReflexiveObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLSameIndividualAxiom(a1, b1, c1));
        assertEqualsFromSupplier(() -> testSubject.getOWLSubDataPropertyOfAxiom(left, prop));
        assertEqualsFromSupplier(() -> testSubject.getOWLSubObjectPropertyOfAxiom(p3, prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLSymmetricObjectPropertyAxiom(prop2));
        assertEqualsFromSupplier(() -> testSubject.getOWLTransitiveObjectPropertyAxiom(prop2));
        assertNotEqualsFromSupplier(() -> C(IRI()));
        assertNotEqualsFromSupplier(() -> D(IRI()));
        assertNotEqualsFromSupplier(() -> DP(IRI()));
        assertNotEqualsFromSupplier(() -> OP(IRI()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLAnnotationProperty(IRI()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLAsymmetricObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLClassAssertionAxiom(C(IRI()), a1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLClassAssertionAxiom(right, I()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataAllValuesFrom(DP(IRI()), dt));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataAllValuesFrom(prop, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataComplementOf(D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataExactCardinality(3, DP(IRI()), testSubject.getTopDatatype()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataExactCardinality(3, prop, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataHasValue(DP(IRI()), filler1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataHasValue(prop, Literal()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataMaxCardinality(3, DP(IRI()), testSubject.getTopDatatype()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataMaxCardinality(3, prop, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataMinCardinality(3, DP(IRI()), testSubject.getTopDatatype()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataMinCardinality(3, prop, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyAssertionAxiom(DP(IRI()), s1, o2));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyAssertionAxiom(prop, I(), o2));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyAssertionAxiom(prop, s1, Literal()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyDomainAxiom(DP(IRI()), right));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyDomainAxiom(left, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyRangeAxiom(DP(IRI()), dt));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLDataPropertyRangeAxiom(left, D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLFunctionalDataPropertyAxiom(DP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLFunctionalObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLInverseFunctionalObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLIrreflexiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLLiteral("3", D(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNamedIndividual(IRI()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(DP(IRI()), s1, o2));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(prop, I(), o2));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNegativeDataPropertyAssertionAxiom(prop, s1, Literal()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(OP(IRI()), s1, o1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p3, I(), o1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p3, s1, I()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectAllValuesFrom(OP(IRI()), a));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectAllValuesFrom(prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectExactCardinality(3, OP(IRI()), testSubject.getOWLThing()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectExactCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectHasSelf(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectHasValue(OP(IRI()), filler));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectHasValue(prop2, I()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectMaxCardinality(3, OP(IRI()), testSubject.getOWLThing()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectMaxCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectMinCardinality(3, OP(IRI()), testSubject.getOWLThing()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectMinCardinality(3, prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyAssertionAxiom(OP(IRI()), s1, o1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyAssertionAxiom(p3, I(), o1));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyAssertionAxiom(p3, s1, I()));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyDomainAxiom(OP(IRI()), right));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyDomainAxiom(p3, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyRangeAxiom(OP(IRI()), right));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectPropertyRangeAxiom(p3, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectSomeValuesFrom(OP(IRI()), a));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLObjectSomeValuesFrom(prop2, C(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLReflexiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLSubDataPropertyOfAxiom(DP(IRI()), prop));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLSubDataPropertyOfAxiom(left, DP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLSubObjectPropertyOfAxiom(OP(IRI()), prop2));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLSubObjectPropertyOfAxiom(p3, OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLSymmetricObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSupplier(() -> testSubject.getOWLTransitiveObjectPropertyAxiom(OP(IRI())));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataExactCardinality(3, prop, testSubject.getTopDatatype()), () -> testSubject.getOWLDataExactCardinality(4, prop, testSubject.getTopDatatype()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataMaxCardinality(3, prop, testSubject.getTopDatatype()), () -> testSubject.getOWLDataMaxCardinality(4, prop, testSubject.getTopDatatype()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataMinCardinality(3, prop, testSubject.getTopDatatype()), () -> testSubject.getOWLDataMinCardinality(4, prop, testSubject.getTopDatatype()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataOneOf(a2, b2), () -> testSubject.getOWLDataOneOf(a2, b2, c2));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataSomeValuesFrom(DP(IRI()), dt), () -> testSubject.getOWLDataSomeValuesFrom(DP(IRI()), dt));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDataSomeValuesFrom(prop, D(IRI())), () -> testSubject.getOWLDataSomeValuesFrom(prop, D(IRI())));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDatatypeRestriction(dt,     testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue)), () -> testSubject.getOWLDatatypeRestriction(dt,     testSubject.getOWLFacetRestriction(MIN_INCLUSIVE, facetValue)));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDifferentIndividualsAxiom(a1, b1), () -> testSubject.getOWLDifferentIndividualsAxiom(a1, b1, c1));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDisjointClassesAxiom(a, b), () -> testSubject.getOWLDisjointClassesAxiom(a, b, c));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDisjointDataPropertiesAxiom(a4, b4, c4), () -> testSubject.getOWLDisjointDataPropertiesAxiom(a4, b4));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLDisjointObjectPropertiesAxiom(a3, b3, c3), () -> testSubject.getOWLDisjointObjectPropertiesAxiom(a3, b3));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLEquivalentClassesAxiom(a, b), () -> testSubject.getOWLEquivalentClassesAxiom(a, b, c));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLEquivalentDataPropertiesAxiom(a4, b4), () -> testSubject.getOWLEquivalentDataPropertiesAxiom(a4, b4, c4));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLEquivalentObjectPropertiesAxiom(a3, b3, c3), () -> testSubject.getOWLEquivalentObjectPropertiesAxiom(a3, b3));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLLiteral("3", dt), () -> testSubject.getOWLLiteral("4", dt));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLLiteral("TEST", "LANG"), () -> testSubject.getOWLLiteral("OTHER", "LANG"));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLLiteral("TEST", "LANG"), () -> testSubject.getOWLLiteral("TEST", "OTHER_LANG"));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectComplementOf(operandA), () -> testSubject.getOWLObjectComplementOf(operandB));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectExactCardinality(3, prop2, testSubject.getOWLThing()), () -> testSubject.getOWLObjectExactCardinality(4, prop2, testSubject.getOWLThing()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectIntersectionOf(a, b), () -> testSubject.getOWLObjectIntersectionOf(a, b, c));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectMaxCardinality(3, prop2, testSubject.getOWLThing()), () -> testSubject.getOWLObjectMaxCardinality(4, prop2, testSubject.getOWLThing()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectMinCardinality(3, prop2, testSubject.getOWLThing()), () -> testSubject.getOWLObjectMinCardinality(4, prop2, testSubject.getOWLThing()));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectOneOf(a1, b1), () -> testSubject.getOWLObjectOneOf(a1, b1, c1));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLObjectUnionOf(a, b, c), () -> testSubject.getOWLObjectUnionOf(a, b));
        assertNotEqualsFromSuppliers(() -> testSubject.getOWLSameIndividualAxiom(a1, b1), () -> testSubject.getOWLSameIndividualAxiom(a1, b1, c1));
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
