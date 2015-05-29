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
package org.semanticweb.owlapi.datafactory;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.vocab.OWLFacet.*;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import org.junit.Test;
import org.semanticweb.owlapi.model.*;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

/**
 * The base for test cases that need a data factory.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
@SuppressWarnings("javadoc")
@ParametersAreNonnullByDefault
public class OWLDataFactoryTestCase {

    private final @Nonnull AtomicInteger counter = new AtomicInteger(1);
    private final @Nonnull OWLDataFactory testSubject = new OWLDataFactoryImpl();

    private static IRI IRI() {
        return IRI.getNextDocumentIRI("urn:test#A");
    }

    private OWLIndividual I() {
        return testSubject.getOWLNamedIndividual(IRI());
    }

    private OWLLiteral Literal() {
        return testSubject.getOWLLiteral("A" + counter.getAndIncrement());
    }

    private OWLClass C(IRI iri) {
        return testSubject.getOWLClass(iri);
    }

    private OWLObjectProperty OP(IRI iri) {
        return testSubject.getOWLObjectProperty(iri);
    }

    private OWLDataProperty DP(IRI iri) {
        return testSubject.getOWLDataProperty(iri);
    }

    private OWLDatatype D(IRI iri) {
        return testSubject.getOWLDatatype(iri);
    }

    @Test
    public void testEqualsPositiveOWLLiteral() {
        OWLDatatype dt = D(IRI());
        OWLLiteral conA = testSubject.getOWLLiteral("3", dt);
        OWLLiteral conB = testSubject.getOWLLiteral("3", dt);
        assertEquals(conA, conB);
    }

    @Test
    public void testEqualsNegativeOWLLiteral() {
        // Different datatypes - same literal
        OWLLiteral conA = testSubject.getOWLLiteral("3", D(IRI()));
        OWLLiteral conB = testSubject.getOWLLiteral("3", D(IRI()));
        assertFalse(conA.equals(conB));
        // Different literals - same datatype
        OWLDatatype dtC = D(IRI());
        OWLLiteral conC = testSubject.getOWLLiteral("3", dtC);
        OWLLiteral conD = testSubject.getOWLLiteral("4", dtC);
        assertFalse(conC.equals(conD));
    }

    @Test
    public void testHashCodeOWLLiteral() {
        OWLDatatype dt = D(IRI());
        OWLLiteral conA = testSubject.getOWLLiteral("3", dt);
        OWLLiteral conB = testSubject.getOWLLiteral("3", dt);
        assertEquals(conA.hashCode(), conB.hashCode());
    }

    @Test
    public void testCreationOWLLiteralLang() {
        assertNotNull(testSubject.getOWLLiteral("TEST", "LANG"));
    }

    @Test
    public void testEqualsPositiveOWLLiteralLang() {
        OWLLiteral conC = testSubject.getOWLLiteral("TEST", "LANG");
        OWLLiteral conD = testSubject.getOWLLiteral("TEST", "LANG");
        assertEquals(conC, conD);
    }

    @Test
    public void testEqualsNegativeOWLLiteralLang() {
        OWLLiteral conC = testSubject.getOWLLiteral("TEST", "LANG");
        OWLLiteral conD = testSubject.getOWLLiteral("TEST", "OTHER_LANG");
        assertFalse(conC.equals(conD));
        OWLLiteral conE = testSubject.getOWLLiteral("TEST", "LANG");
        OWLLiteral conF = testSubject.getOWLLiteral("OTHER", "LANG");
        assertFalse(conE.equals(conF));
    }

    @Test
    public void testHashCodeOWLLiteralLang() {
        OWLLiteral conA = testSubject.getOWLLiteral("TEST", "LANG");
        OWLLiteral conB = testSubject.getOWLLiteral("TEST", "LANG");
        assertEquals(conA.hashCode(), conB.hashCode());
    }

    @Test
    public void testCreationAxiomSameIndividual() {
        OWLObject obj = testSubject.getOWLSameIndividualAxiom(I(), I(), I());
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveAxiomSameIndividual() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLIndividual c = I();
        OWLObject objA = testSubject.getOWLSameIndividualAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLSameIndividualAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeAxiomSameIndividual() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLObject objA = testSubject.getOWLSameIndividualAxiom(a, b);
        OWLIndividual c = I();
        OWLObject objB = testSubject.getOWLSameIndividualAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeAxiomSameIndividual() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLIndividual c = I();
        OWLObject objA = testSubject.getOWLSameIndividualAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLSameIndividualAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationAxiomDifferentFrom() {
        OWLObject obj = testSubject.getOWLDifferentIndividualsAxiom(I(), I(), I());
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveAxiomDifferentFrom() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLIndividual c = I();
        OWLObject objA = testSubject.getOWLDifferentIndividualsAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDifferentIndividualsAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeAxiomDifferentFrom() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLObject objA = testSubject.getOWLDifferentIndividualsAxiom(a, b);
        OWLIndividual c = I();
        OWLObject objB = testSubject.getOWLDifferentIndividualsAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeAxiomDifferentFrom() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLIndividual c = I();
        OWLObject objA = testSubject.getOWLDifferentIndividualsAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDifferentIndividualsAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationDatatype() {
        OWLDatatype typeA = D(IRI());
        assertNotNull(typeA);
    }

    @Test
    public void testEqualsPositiveDatatype() {
        IRI iri = IRI();
        OWLDatatype typeA = D(iri);
        OWLDatatype typeB = D(iri);
        assertEquals(typeA, typeB);
    }

    @Test
    public void testEqualsNegativeDatatype() {
        OWLDatatype typeA = D(IRI());
        OWLDatatype typeB = D(IRI());
        assertFalse(typeA.equals(typeB));
    }

    @Test
    public void testHashCodeDatatype() {
        IRI iri = IRI();
        OWLDatatype typeA = D(iri);
        OWLDatatype typeB = D(iri);
        assertEquals(typeA.hashCode(), typeB.hashCode());
    }

    @Test
    public void testCreationDataRange() {
        OWLDatatype rng = D(IRI());
        OWLLiteral facetValue = testSubject.getOWLLiteral("3", D(IRI()));
        OWLDatatypeRestriction restRng = testSubject.getOWLDatatypeRestriction(rng,
                testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue));
        assertNotNull(restRng);
    }

    @Test
    public void testEqualsPositiveDataRange() {
        OWLDatatype rng = D(IRI());
        OWLLiteral facetValue = testSubject.getOWLLiteral("3", D(IRI()));
        OWLDatatypeRestriction restRngA = testSubject.getOWLDatatypeRestriction(rng,
                testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue));
        OWLDatatypeRestriction restRngB = testSubject.getOWLDatatypeRestriction(rng,
                testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue));
        assertEquals(restRngA, restRngB);
    }

    @Test
    public void testEqualsNegativeDataRange() {
        OWLDatatype rng = D(IRI());
        OWLLiteral facetValue = testSubject.getOWLLiteral("3", D(IRI()));
        OWLDatatypeRestriction restRngA = testSubject.getOWLDatatypeRestriction(rng,
                testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue));
        OWLDatatypeRestriction restRngB = testSubject.getOWLDatatypeRestriction(rng,
                testSubject.getOWLFacetRestriction(MIN_INCLUSIVE, facetValue));
        assertNotEquals(restRngA.toString() + "\n" + restRngB.toString(), restRngA, restRngB);
    }

    @Test
    public void testHashCodeDataRange() {
        OWLDatatype rng = D(IRI());
        OWLLiteral facetValue = testSubject.getOWLLiteral("3", D(IRI()));
        OWLDatatypeRestriction restRngA = testSubject.getOWLDatatypeRestriction(rng,
                testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue));
        OWLDatatypeRestriction restRngB = testSubject.getOWLDatatypeRestriction(rng,
                testSubject.getOWLFacetRestriction(MAX_EXCLUSIVE, facetValue));
        assertEquals(restRngA.hashCode(), restRngB.hashCode());
    }

    @Test
    public void testCreationIntersection() {
        OWLObject obj = testSubject.getOWLObjectIntersectionOf(C(IRI()), C(IRI()), C(IRI()));
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveIntersection() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLObjectIntersectionOf(a, b, c);
        OWLObject objB = testSubject.getOWLObjectIntersectionOf(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeIntersection() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLObject objA = testSubject.getOWLObjectIntersectionOf(a, b);
        OWLClassExpression c = C(IRI());
        OWLObject objB = testSubject.getOWLObjectIntersectionOf(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeIntersection() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLObjectIntersectionOf(a, b, c);
        OWLObject objB = testSubject.getOWLObjectIntersectionOf(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationDataOneOf() {
        OWLObject obj = testSubject.getOWLDataOneOf(Literal(), Literal(), Literal());
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveDataOneOf() {
        OWLLiteral a = Literal();
        OWLLiteral b = Literal();
        OWLLiteral c = Literal();
        OWLObject objA = testSubject.getOWLDataOneOf(a, b, c);
        OWLObject objB = testSubject.getOWLDataOneOf(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeDataOneOf() {
        OWLLiteral a = Literal();
        OWLLiteral b = Literal();
        OWLObject objA = testSubject.getOWLDataOneOf(a, b);
        OWLLiteral c = Literal();
        OWLObject objB = testSubject.getOWLDataOneOf(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeDataOneOf() {
        OWLLiteral a = Literal();
        OWLLiteral b = Literal();
        OWLLiteral c = Literal();
        OWLObject objA = testSubject.getOWLDataOneOf(a, b, c);
        OWLObject objB = testSubject.getOWLDataOneOf(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationDataComplementOf() {
        OWLDatatype dt = D(IRI());
        OWLDataComplementOf rng = testSubject.getOWLDataComplementOf(dt);
        assertNotNull(rng);
    }

    @Test
    public void testEqualsPositiveDataComplementOf() {
        OWLDatatype dt = D(IRI());
        OWLDataComplementOf rngA = testSubject.getOWLDataComplementOf(dt);
        OWLDataComplementOf rngB = testSubject.getOWLDataComplementOf(dt);
        assertEquals(rngA, rngB);
    }

    @Test
    public void testEqualsNegativeDataComplementOf() {
        OWLDatatype dtA = D(IRI());
        OWLDataComplementOf rngA = testSubject.getOWLDataComplementOf(dtA);
        OWLDatatype dtB = D(IRI());
        OWLDataComplementOf rngB = testSubject.getOWLDataComplementOf(dtB);
        assertFalse(rngA.equals(rngB));
    }

    @Test
    public void testHashCodeDataComplementOf() {
        OWLDatatype dt = D(IRI());
        OWLDataComplementOf rngA = testSubject.getOWLDataComplementOf(dt);
        OWLDataComplementOf rngB = testSubject.getOWLDataComplementOf(dt);
        assertEquals(rngA.hashCode(), rngB.hashCode());
    }

    @Test
    public void testEqualsPositiveDisjointClasses() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLDisjointClassesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDisjointClassesAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeDisjointClasses() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLObject objA = testSubject.getOWLDisjointClassesAxiom(a, b);
        OWLClassExpression c = C(IRI());
        OWLObject objB = testSubject.getOWLDisjointClassesAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeDisjointClasses() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLDisjointClassesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDisjointClassesAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationObjectUnionOf() {
        OWLObject obj = testSubject.getOWLObjectUnionOf(C(IRI()), C(IRI()), C(IRI()));
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveObjectUnionOf() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLObjectUnionOf(a, b, c);
        OWLObject objB = testSubject.getOWLObjectUnionOf(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeObjectUnionOf() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLObject objA = testSubject.getOWLObjectUnionOf(a, b);
        OWLClassExpression c = C(IRI());
        OWLObject objB = testSubject.getOWLObjectUnionOf(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeObjectUnionOf() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLObjectUnionOf(a, b, c);
        OWLObject objB = testSubject.getOWLObjectUnionOf(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationDisjointObjectProperties() {
        OWLObjectPropertyExpression[] properties = { OP(IRI()), OP(IRI()), OP(IRI()) };
        OWLObject obj = testSubject.getOWLDisjointObjectPropertiesAxiom(properties);
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveDisjointObjectProperties() {
        OWLObjectProperty a = OP(IRI());
        OWLObjectProperty b = OP(IRI());
        OWLObjectProperty c = OP(IRI());
        OWLObject objA = testSubject.getOWLDisjointObjectPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDisjointObjectPropertiesAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeDisjointObjectProperties() {
        OWLObjectProperty a = OP(IRI());
        OWLObjectProperty b = OP(IRI());
        OWLObject objA = testSubject.getOWLDisjointObjectPropertiesAxiom(a, b);
        OWLObjectProperty c = OP(IRI());
        OWLObject objB = testSubject.getOWLDisjointObjectPropertiesAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeDisjointObjectProperties() {
        OWLObjectProperty a = OP(IRI());
        OWLObjectProperty b = OP(IRI());
        OWLObjectProperty c = OP(IRI());
        OWLObject objA = testSubject.getOWLDisjointObjectPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDisjointObjectPropertiesAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testAsSubAxiomsEquivalentObjectProperties() {
        OWLObjectPropertyExpression[] properties = { OP(IRI()), OP(IRI()) };
        OWLEquivalentObjectPropertiesAxiom objA = testSubject.getOWLEquivalentObjectPropertiesAxiom(properties);
        assertEquals(2, objA.asSubObjectPropertyOfAxioms().size());
    }

    @Test
    public void testCreationEquivalentObjectProperties() {
        OWLObjectPropertyExpression[] properties = { OP(IRI()), OP(IRI()), OP(IRI()) };
        OWLObject obj = testSubject.getOWLEquivalentObjectPropertiesAxiom(properties);
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveEquivalentObjectProperties() {
        OWLObjectProperty a = OP(IRI());
        OWLObjectProperty b = OP(IRI());
        OWLObjectProperty c = OP(IRI());
        OWLObject objA = testSubject.getOWLEquivalentObjectPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLEquivalentObjectPropertiesAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeEquivalentObjectProperties() {
        OWLObjectProperty a = OP(IRI());
        OWLObjectProperty b = OP(IRI());
        OWLObject objA = testSubject.getOWLEquivalentObjectPropertiesAxiom(a, b);
        OWLObjectProperty c = OP(IRI());
        OWLObject objB = testSubject.getOWLEquivalentObjectPropertiesAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeEquivalentObjectProperties() {
        OWLObjectProperty a = OP(IRI());
        OWLObjectProperty b = OP(IRI());
        OWLObjectProperty c = OP(IRI());
        OWLObject objA = testSubject.getOWLEquivalentObjectPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLEquivalentObjectPropertiesAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationDisjointDataProperties() {
        OWLDataPropertyExpression[] properties = { DP(IRI()), DP(IRI()), DP(IRI()) };
        OWLObject obj = testSubject.getOWLDisjointDataPropertiesAxiom(properties);
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveDisjointDataProperties() {
        OWLDataProperty a = DP(IRI());
        OWLDataProperty b = DP(IRI());
        OWLDataProperty c = DP(IRI());
        OWLObject objA = testSubject.getOWLDisjointDataPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDisjointDataPropertiesAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeDisjointDataProperties() {
        OWLDataProperty a = DP(IRI());
        OWLDataProperty b = DP(IRI());
        OWLObject objA = testSubject.getOWLDisjointDataPropertiesAxiom(a, b);
        OWLDataProperty c = DP(IRI());
        OWLObject objB = testSubject.getOWLDisjointDataPropertiesAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeDisjointDataProperties() {
        OWLDataProperty a = DP(IRI());
        OWLDataProperty b = DP(IRI());
        OWLDataProperty c = DP(IRI());
        OWLObject objA = testSubject.getOWLDisjointDataPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLDisjointDataPropertiesAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testAsSubAxiomsEquivalentDataProperties() {
        OWLDataProperty a = DP(IRI());
        OWLDataProperty b = DP(IRI());
        OWLDataPropertyExpression[] properties = { a, b };
        OWLEquivalentDataPropertiesAxiom objA = testSubject.getOWLEquivalentDataPropertiesAxiom(properties);
        assertEquals(2, objA.asSubDataPropertyOfAxioms().size());
    }

    @Test
    public void testCreationEquivalentDataProperties() {
        OWLDataPropertyExpression[] properties = { DP(IRI()), DP(IRI()), DP(IRI()) };
        OWLObject obj = testSubject.getOWLEquivalentDataPropertiesAxiom(properties);
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveEquivalentDataProperties() {
        OWLDataProperty a = DP(IRI());
        OWLDataProperty b = DP(IRI());
        OWLDataProperty c = DP(IRI());
        OWLObject objA = testSubject.getOWLEquivalentDataPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLEquivalentDataPropertiesAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeEquivalentDataProperties() {
        OWLDataProperty a = DP(IRI());
        OWLDataProperty b = DP(IRI());
        OWLObject objA = testSubject.getOWLEquivalentDataPropertiesAxiom(a, b);
        OWLDataProperty c = DP(IRI());
        OWLObject objB = testSubject.getOWLEquivalentDataPropertiesAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeEquivalentDataProperties() {
        OWLDataProperty a = DP(IRI());
        OWLDataProperty b = DP(IRI());
        OWLDataProperty c = DP(IRI());
        OWLObject objA = testSubject.getOWLEquivalentDataPropertiesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLEquivalentDataPropertiesAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testAsSubAxiomsEquivalentClasses() {
        OWLClassExpression[] classExpressions = { C(IRI()), C(IRI()) };
        OWLEquivalentClassesAxiom objA = testSubject.getOWLEquivalentClassesAxiom(classExpressions);
        assertEquals(2, objA.asOWLSubClassOfAxioms().size());
    }

    @Test
    public void testCreationEquivalentClasses() {
        OWLClassExpression[] classExpressions = { C(IRI()), C(IRI()), C(IRI()) };
        OWLObject obj = testSubject.getOWLEquivalentClassesAxiom(classExpressions);
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveEquivalentClasses() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLEquivalentClassesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLEquivalentClassesAxiom(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeEquivalentClasses() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLObject objA = testSubject.getOWLEquivalentClassesAxiom(a, b);
        OWLClassExpression c = C(IRI());
        OWLObject objB = testSubject.getOWLEquivalentClassesAxiom(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeEquivalentClasses() {
        OWLClassExpression a = C(IRI());
        OWLClassExpression b = C(IRI());
        OWLClassExpression c = C(IRI());
        OWLObject objA = testSubject.getOWLEquivalentClassesAxiom(a, b, c);
        OWLObject objB = testSubject.getOWLEquivalentClassesAxiom(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationObjectComplementOf() {
        OWLClassExpression operand = C(IRI());
        OWLObjectComplementOf not = testSubject.getOWLObjectComplementOf(operand);
        assertNotNull(not);
    }

    @Test
    public void testEqualsPositiveObjectComplementOf() {
        OWLClassExpression operand = C(IRI());
        OWLObjectComplementOf notA = testSubject.getOWLObjectComplementOf(operand);
        OWLObjectComplementOf notB = testSubject.getOWLObjectComplementOf(operand);
        assertEquals(notA, notB);
    }

    @Test
    public void testEqualsNegativeObjectComplementOf() {
        OWLClassExpression operandA = C(IRI());
        OWLObjectComplementOf notA = testSubject.getOWLObjectComplementOf(operandA);
        OWLClassExpression operandB = C(IRI());
        OWLObjectComplementOf notB = testSubject.getOWLObjectComplementOf(operandB);
        assertFalse(notA.equals(notB));
    }

    @Test
    public void testHashCodeObjectComplementOf() {
        OWLClassExpression operand = C(IRI());
        OWLObjectComplementOf notA = testSubject.getOWLObjectComplementOf(operand);
        OWLObjectComplementOf notB = testSubject.getOWLObjectComplementOf(operand);
        assertEquals(notA.hashCode(), notB.hashCode());
    }

    @Test
    public void testCreationObjectOneOf() {
        OWLIndividual[] individuals = { I(), I(), I() };
        OWLObject obj = testSubject.getOWLObjectOneOf(individuals);
        assertNotNull(obj);
    }

    @Test
    public void testEqualsPositiveObjectOneOf() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLIndividual c = I();
        OWLObject objA = testSubject.getOWLObjectOneOf(a, b, c);
        OWLObject objB = testSubject.getOWLObjectOneOf(a, b, c);
        assertEquals(objA, objB);
    }

    @Test
    public void testEqualsNegativeObjectOneOf() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLObject objA = testSubject.getOWLObjectOneOf(a, b);
        OWLIndividual c = I();
        OWLObject objB = testSubject.getOWLObjectOneOf(a, b, c);
        assertFalse(objA.equals(objB));
    }

    @Test
    public void testHashCodeObjectOneOf() {
        OWLIndividual a = I();
        OWLIndividual b = I();
        OWLIndividual c = I();
        OWLObject objA = testSubject.getOWLObjectOneOf(a, b, c);
        OWLObject objB = testSubject.getOWLObjectOneOf(a, b, c);
        assertEquals(objA.hashCode(), objB.hashCode());
    }

    @Test
    public void testCreationObjectHasSelf() {
        OWLObjectProperty prop = OP(IRI());
        OWLObjectHasSelf restA = testSubject.getOWLObjectHasSelf(prop);
        assertNotNull(restA);
    }

    @Test
    public void testEqualsPositiveObjectHasSelf() {
        OWLObjectProperty prop = OP(IRI());
        OWLObjectHasSelf restA = testSubject.getOWLObjectHasSelf(prop);
        OWLObjectHasSelf restB = testSubject.getOWLObjectHasSelf(prop);
        assertEquals(restA, restB);
    }

    @Test
    public void testEqualsNegativeObjectHasSelf() {
        OWLObjectHasSelf restA = testSubject.getOWLObjectHasSelf(OP(IRI()));
        OWLObjectHasSelf restB = testSubject.getOWLObjectHasSelf(OP(IRI()));
        assertFalse(restA.equals(restB));
    }

    @Test
    public void testHashCodeObjectHasSelf() {
        OWLObjectProperty prop = OP(IRI());
        OWLObjectHasSelf restA = testSubject.getOWLObjectHasSelf(prop);
        OWLObjectHasSelf restB = testSubject.getOWLObjectHasSelf(prop);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testCreationDataExact() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataExactCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        assertNotNull(restA);
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataExactCardinality(cardinality, prop, dataRange);
        assertNotNull(restB);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testEqualsPositiveDataExact() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataExactCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataExactCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        assertEquals(restA, restB);
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restC = testSubject.getOWLDataExactCardinality(cardinality, prop, dataRange);
        OWLDataCardinalityRestriction restD = testSubject.getOWLDataExactCardinality(cardinality, prop, dataRange);
        assertEquals(restC, restD);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testEqualsNegativeDataExact() {
        OWLDataProperty prop = DP(IRI());
        // Different cardinality
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataExactCardinality(3, prop,
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataExactCardinality(4, prop,
                testSubject.getTopDatatype());
        assertFalse(restA.equals(restB));
        // Different property
        OWLDataCardinalityRestriction restC = testSubject.getOWLDataExactCardinality(3, DP(IRI()),
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restD = testSubject.getOWLDataExactCardinality(3, DP(IRI()),
                testSubject.getTopDatatype());
        assertFalse(restC.equals(restD));
        // Different filler
        OWLDataCardinalityRestriction restE = testSubject.getOWLDataExactCardinality(3, prop, D(IRI()));
        OWLDataCardinalityRestriction restF = testSubject.getOWLDataExactCardinality(3, prop, D(IRI()));
        assertFalse(restE.equals(restF));
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testHashCodeDataExact() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataExactCardinality(cardinality, prop, dataRange);
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataExactCardinality(cardinality, prop, dataRange);
        assertEquals(restA, restB);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testCreationDataMin() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMinCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        assertNotNull(restA);
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMinCardinality(cardinality, prop, dataRange);
        assertNotNull(restB);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testEqualsPositiveDataMin() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMinCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMinCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        assertEquals(restA, restB);
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restC = testSubject.getOWLDataMinCardinality(cardinality, prop, dataRange);
        OWLDataCardinalityRestriction restD = testSubject.getOWLDataMinCardinality(cardinality, prop, dataRange);
        assertEquals(restC, restD);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testEqualsNegativeDataMin() {
        OWLDataProperty prop = DP(IRI());
        // Different cardinality
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMinCardinality(3, prop,
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMinCardinality(4, prop,
                testSubject.getTopDatatype());
        assertFalse(restA.equals(restB));
        // Different property
        OWLDataCardinalityRestriction restC = testSubject.getOWLDataMinCardinality(3, DP(IRI()),
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restD = testSubject.getOWLDataMinCardinality(3, DP(IRI()),
                testSubject.getTopDatatype());
        assertFalse(restC.equals(restD));
        // Different filler
        OWLDataCardinalityRestriction restE = testSubject.getOWLDataMinCardinality(3, prop, D(IRI()));
        OWLDataCardinalityRestriction restF = testSubject.getOWLDataMinCardinality(3, prop, D(IRI()));
        assertFalse(restE.equals(restF));
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testHashCodeDataMin() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMinCardinality(cardinality, prop, dataRange);
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMinCardinality(cardinality, prop, dataRange);
        assertEquals(restA, restB);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testCreationDataMax() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMaxCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        assertNotNull(restA);
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMaxCardinality(cardinality, prop, dataRange);
        assertNotNull(restB);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testEqualsPositiveDataMax() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMaxCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMaxCardinality(cardinality, prop,
                testSubject.getTopDatatype());
        assertEquals(restA, restB);
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restC = testSubject.getOWLDataMaxCardinality(cardinality, prop, dataRange);
        OWLDataCardinalityRestriction restD = testSubject.getOWLDataMaxCardinality(cardinality, prop, dataRange);
        assertEquals(restC, restD);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testEqualsNegativeDataMax() {
        OWLDataProperty prop = DP(IRI());
        // Different cardinality
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMaxCardinality(3, prop,
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMaxCardinality(4, prop,
                testSubject.getTopDatatype());
        assertFalse(restA.equals(restB));
        // Different property
        OWLDataCardinalityRestriction restC = testSubject.getOWLDataMaxCardinality(3, DP(IRI()),
                testSubject.getTopDatatype());
        OWLDataCardinalityRestriction restD = testSubject.getOWLDataMaxCardinality(3, DP(IRI()),
                testSubject.getTopDatatype());
        assertFalse(restC.equals(restD));
        // Different filler
        OWLDataCardinalityRestriction restE = testSubject.getOWLDataMaxCardinality(3, prop, D(IRI()));
        OWLDataCardinalityRestriction restF = testSubject.getOWLDataMaxCardinality(3, prop, D(IRI()));
        assertFalse(restE.equals(restF));
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testHashCodeDataMax() {
        OWLDataProperty prop = DP(IRI());
        int cardinality = 3;
        OWLDataRange dataRange = D(IRI());
        OWLDataCardinalityRestriction restA = testSubject.getOWLDataMaxCardinality(cardinality, prop, dataRange);
        OWLDataCardinalityRestriction restB = testSubject.getOWLDataMaxCardinality(cardinality, prop, dataRange);
        assertEquals(restA, restB);
        assertEquals(restA.getProperty(), prop);
    }

    @Test
    public void testCreationClass() {
        assertNotNull(C(IRI()));
    }

    @Test
    public void testEqualsPositiveClass() {
        IRI iri = IRI();
        OWLEntity entityA = C(iri);
        OWLEntity entityB = C(iri);
        assertEquals(entityA, entityB);
    }

    @Test
    public void testEqualsNegativeClass() {
        OWLEntity entityA = C(IRI());
        OWLEntity entityB = C(IRI());
        assertFalse(entityA.equals(entityB));
    }

    @Test
    public void testHashCodeClass() {
        IRI iri = IRI();
        int hashCodeA = C(iri).hashCode();
        int hashCodeB = C(iri).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationData() {
        assertNotNull(DP(IRI()));
    }

    @Test
    public void testEqualsPositiveData() {
        IRI iri = IRI();
        OWLEntity entityA = DP(iri);
        OWLEntity entityB = DP(iri);
        assertEquals(entityA, entityB);
    }

    @Test
    public void testEqualsNegativeData() {
        OWLEntity entityA = DP(IRI());
        OWLEntity entityB = DP(IRI());
        assertFalse(entityA.equals(entityB));
    }

    @Test
    public void testHashCodeData() {
        IRI iri = IRI();
        int hashCodeA = DP(iri).hashCode();
        int hashCodeB = DP(iri).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationObject() {
        assertNotNull(OP(IRI()));
    }

    @Test
    public void testEqualsPositiveObject() {
        IRI iri = IRI();
        OWLEntity entityA = OP(iri);
        OWLEntity entityB = OP(iri);
        assertEquals(entityA, entityB);
    }

    @Test
    public void testEqualsNegativeObject() {
        OWLEntity entityA = OP(IRI());
        OWLEntity entityB = OP(IRI());
        assertFalse(entityA.equals(entityB));
    }

    @Test
    public void testHashCodeObject() {
        IRI iri = IRI();
        int hashCodeA = OP(iri).hashCode();
        int hashCodeB = OP(iri).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationAnnotationProperty() {
        assertNotNull(testSubject.getOWLAnnotationProperty(IRI()));
    }

    @Test
    public void testEqualsPositiveAnnotationProperty() {
        IRI iri = IRI();
        OWLEntity entityA = testSubject.getOWLAnnotationProperty(iri);
        OWLEntity entityB = testSubject.getOWLAnnotationProperty(iri);
        assertEquals(entityA, entityB);
    }

    @Test
    public void testEqualsNegativeAnnotationProperty() {
        OWLEntity entityA = testSubject.getOWLAnnotationProperty(IRI());
        OWLEntity entityB = testSubject.getOWLAnnotationProperty(IRI());
        assertFalse(entityA.equals(entityB));
    }

    @Test
    public void testHashCodeAnnotationProperty() {
        IRI iri = IRI();
        int hashCodeA = testSubject.getOWLAnnotationProperty(iri).hashCode();
        int hashCodeB = testSubject.getOWLAnnotationProperty(iri).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationNamedIndividual() {
        assertNotNull(testSubject.getOWLNamedIndividual(IRI()));
    }

    @Test
    public void testEqualsPositiveNamedIndividual() {
        IRI iri = IRI();
        OWLEntity entityA = testSubject.getOWLNamedIndividual(iri);
        OWLEntity entityB = testSubject.getOWLNamedIndividual(iri);
        assertEquals(entityA, entityB);
    }

    @Test
    public void testEqualsNegativeNamedIndividual() {
        OWLEntity entityA = testSubject.getOWLNamedIndividual(IRI());
        OWLEntity entityB = testSubject.getOWLNamedIndividual(IRI());
        assertFalse(entityA.equals(entityB));
    }

    @Test
    public void testHashCodeNamedIndividual() {
        IRI iri = IRI();
        int hashCodeA = testSubject.getOWLNamedIndividual(iri).hashCode();
        int hashCodeB = testSubject.getOWLNamedIndividual(iri).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testEqualsPositiveObjectHas() {
        OWLObjectProperty prop = OP(IRI());
        OWLIndividual filler = I();
        OWLRestriction restA = testSubject.getOWLObjectHasValue(prop, filler);
        OWLRestriction restB = testSubject.getOWLObjectHasValue(prop, filler);
        assertEquals(restA, restB);
    }

    @Test
    public void testEqualsNegativeObjectHas() {
        // Different filler
        OWLObjectProperty prop = OP(IRI());
        OWLRestriction restA = testSubject.getOWLObjectHasValue(prop, I());
        OWLRestriction restB = testSubject.getOWLObjectHasValue(prop, I());
        assertFalse(restA.equals(restB));
        // Different property
        OWLIndividual filler = I();
        OWLRestriction restC = testSubject.getOWLObjectHasValue(OP(IRI()), filler);
        OWLRestriction restD = testSubject.getOWLObjectHasValue(OP(IRI()), filler);
        assertFalse(restC.equals(restD));
    }

    @Test
    public void testHashCodeObjectHas() {
        OWLObjectProperty prop = OP(IRI());
        OWLIndividual filler = I();
        OWLRestriction restA = testSubject.getOWLObjectHasValue(prop, filler);
        OWLRestriction restB = testSubject.getOWLObjectHasValue(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testEqualsPositiveObjectSome() {
        OWLObjectProperty prop = OP(IRI());
        OWLClassExpression filler = C(IRI());
        OWLRestriction restA = testSubject.getOWLObjectSomeValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLObjectSomeValuesFrom(prop, filler);
        assertEquals(restA, restB);
    }

    @Test
    public void testEqualsNegativeObjectSome() {
        // Different filler
        OWLObjectProperty prop = OP(IRI());
        OWLRestriction restA = testSubject.getOWLObjectSomeValuesFrom(prop, C(IRI()));
        OWLRestriction restB = testSubject.getOWLObjectSomeValuesFrom(prop, C(IRI()));
        assertFalse(restA.equals(restB));
        // Different property
        OWLClassExpression filler = C(IRI());
        OWLRestriction restC = testSubject.getOWLObjectSomeValuesFrom(OP(IRI()), filler);
        OWLRestriction restD = testSubject.getOWLObjectSomeValuesFrom(OP(IRI()), filler);
        assertFalse(restC.equals(restD));
    }

    @Test
    public void testHashCodeObjectSome() {
        OWLObjectProperty prop = OP(IRI());
        OWLClassExpression filler = C(IRI());
        OWLRestriction restA = testSubject.getOWLObjectSomeValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLObjectSomeValuesFrom(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testEqualsPositiveObjectAll() {
        OWLObjectProperty prop = OP(IRI());
        OWLClassExpression filler = C(IRI());
        OWLRestriction restA = testSubject.getOWLObjectAllValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLObjectAllValuesFrom(prop, filler);
        assertEquals(restA, restB);
    }

    @Test
    public void testEqualsNegativeObjectAll() {
        // Different filler
        OWLObjectProperty prop = OP(IRI());
        OWLRestriction restA = testSubject.getOWLObjectAllValuesFrom(prop, C(IRI()));
        OWLRestriction restB = testSubject.getOWLObjectAllValuesFrom(prop, C(IRI()));
        assertFalse(restA.equals(restB));
        // Different property
        OWLClassExpression filler = C(IRI());
        OWLRestriction restC = testSubject.getOWLObjectAllValuesFrom(OP(IRI()), filler);
        OWLRestriction restD = testSubject.getOWLObjectAllValuesFrom(OP(IRI()), filler);
        assertFalse(restC.equals(restD));
    }

    @Test
    public void testHashCodeObjectAll() {
        OWLObjectProperty prop = OP(IRI());
        OWLClassExpression filler = C(IRI());
        OWLRestriction restA = testSubject.getOWLObjectAllValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLObjectAllValuesFrom(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testEqualsPositiveDataHas() {
        OWLDataProperty prop = DP(IRI());
        OWLLiteral filler = Literal();
        OWLRestriction restA = testSubject.getOWLDataHasValue(prop, filler);
        OWLRestriction restB = testSubject.getOWLDataHasValue(prop, filler);
        assertEquals(restA, restB);
    }

    @Test
    public void testEqualsNegativeDataHas() {
        // Different filler
        OWLDataProperty prop = DP(IRI());
        OWLRestriction restA = testSubject.getOWLDataHasValue(prop, Literal());
        OWLRestriction restB = testSubject.getOWLDataHasValue(prop, Literal());
        assertFalse(restA.equals(restB));
        // Different property
        OWLLiteral filler = Literal();
        OWLRestriction restC = testSubject.getOWLDataHasValue(DP(IRI()), filler);
        OWLRestriction restD = testSubject.getOWLDataHasValue(DP(IRI()), filler);
        assertFalse(restC.equals(restD));
    }

    @Test
    public void testHashCodeDataHas() {
        OWLDataProperty prop = DP(IRI());
        OWLLiteral filler = Literal();
        OWLRestriction restA = testSubject.getOWLDataHasValue(prop, filler);
        OWLRestriction restB = testSubject.getOWLDataHasValue(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testEqualsPositiveDataSome() {
        OWLDataProperty prop = DP(IRI());
        OWLDataRange filler = D(IRI());
        OWLRestriction restA = testSubject.getOWLDataSomeValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLDataSomeValuesFrom(prop, filler);
        assertEquals(restA, restB);
    }

    @Test
    public void testEqualsNegativeDataSome() {
        // Different filler
        OWLDataProperty prop = DP(IRI());
        OWLRestriction restA = testSubject.getOWLDataSomeValuesFrom(prop, D(IRI()));
        OWLRestriction restB = testSubject.getOWLDataSomeValuesFrom(prop, D(IRI()));
        assertFalse(restA.equals(restB));
        // Different property
        OWLDataRange filler = D(IRI());
        OWLRestriction restC = testSubject.getOWLDataSomeValuesFrom(DP(IRI()), filler);
        OWLRestriction restD = testSubject.getOWLDataSomeValuesFrom(DP(IRI()), filler);
        assertFalse(restC.equals(restD));
    }

    @Test
    public void testHashCodeDataSome() {
        OWLDataProperty prop = DP(IRI());
        OWLDataRange filler = D(IRI());
        OWLRestriction restA = testSubject.getOWLDataSomeValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLDataSomeValuesFrom(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testEqualsPositiveDataAll() {
        OWLDataProperty prop = DP(IRI());
        OWLDataRange filler = D(IRI());
        OWLRestriction restA = testSubject.getOWLDataAllValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLDataAllValuesFrom(prop, filler);
        assertEquals(restA, restB);
    }

    @Test
    public void testEqualsNegativeDataAll() {
        // Different filler
        OWLDataProperty prop = DP(IRI());
        OWLRestriction restA = testSubject.getOWLDataAllValuesFrom(prop, D(IRI()));
        OWLRestriction restB = testSubject.getOWLDataAllValuesFrom(prop, D(IRI()));
        assertFalse(restA.equals(restB));
        // Different property
        OWLDataRange filler = D(IRI());
        OWLRestriction restC = testSubject.getOWLDataAllValuesFrom(DP(IRI()), filler);
        OWLRestriction restD = testSubject.getOWLDataAllValuesFrom(DP(IRI()), filler);
        assertFalse(restC.equals(restD));
    }

    @Test
    public void testHashCodeDataAll() {
        OWLDataProperty prop = DP(IRI());
        OWLDataRange filler = D(IRI());
        OWLRestriction restA = testSubject.getOWLDataAllValuesFrom(prop, filler);
        OWLRestriction restB = testSubject.getOWLDataAllValuesFrom(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testCreationObjectAsymmetric() {
        OWLPropertyAxiom axiom = testSubject.getOWLAsymmetricObjectPropertyAxiom(OP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveObjectAsymmetric() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLAsymmetricObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLAsymmetricObjectPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectAsymmetric() {
        OWLPropertyAxiom axA = testSubject.getOWLAsymmetricObjectPropertyAxiom(OP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLAsymmetricObjectPropertyAxiom(OP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeObjectAsymmetric() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLAsymmetricObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLAsymmetricObjectPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationObjectInverseFunctional() {
        OWLPropertyAxiom axiom = testSubject.getOWLInverseFunctionalObjectPropertyAxiom(OP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveObjectInverseFunctional() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectInverseFunctional() {
        OWLPropertyAxiom axA = testSubject.getOWLInverseFunctionalObjectPropertyAxiom(OP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLInverseFunctionalObjectPropertyAxiom(OP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeObjectInverseFunctional() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLInverseFunctionalObjectPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationObjectIrreflexive() {
        OWLPropertyAxiom axiom = testSubject.getOWLIrreflexiveObjectPropertyAxiom(OP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveObjectIrreflexive() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLIrreflexiveObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLIrreflexiveObjectPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectIrreflexive() {
        OWLPropertyAxiom axA = testSubject.getOWLIrreflexiveObjectPropertyAxiom(OP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLIrreflexiveObjectPropertyAxiom(OP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeObjectIrreflexive() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLIrreflexiveObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLIrreflexiveObjectPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationObjectReflexive() {
        OWLPropertyAxiom axiom = testSubject.getOWLReflexiveObjectPropertyAxiom(OP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveObjectReflexive() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLReflexiveObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLReflexiveObjectPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectReflexive() {
        OWLPropertyAxiom axA = testSubject.getOWLReflexiveObjectPropertyAxiom(OP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLReflexiveObjectPropertyAxiom(OP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeObjectReflexive() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLReflexiveObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLReflexiveObjectPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationObjectSymmetric() {
        OWLPropertyAxiom axiom = testSubject.getOWLSymmetricObjectPropertyAxiom(OP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveObjectSymmetric() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLSymmetricObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLSymmetricObjectPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectSymmetric() {
        OWLPropertyAxiom axA = testSubject.getOWLSymmetricObjectPropertyAxiom(OP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLSymmetricObjectPropertyAxiom(OP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeObjectSymmetric() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLSymmetricObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLSymmetricObjectPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationObjectTransitive() {
        OWLPropertyAxiom axiom = testSubject.getOWLTransitiveObjectPropertyAxiom(OP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveObjectTransitive() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLTransitiveObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLTransitiveObjectPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectTransitive() {
        OWLPropertyAxiom axA = testSubject.getOWLTransitiveObjectPropertyAxiom(OP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLTransitiveObjectPropertyAxiom(OP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeObjectTransitive() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLTransitiveObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLTransitiveObjectPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationObjectFunctional() {
        OWLPropertyAxiom axiom = testSubject.getOWLFunctionalObjectPropertyAxiom(OP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveObjectFunctional() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLFunctionalObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLFunctionalObjectPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectFunctional() {
        OWLPropertyAxiom axA = testSubject.getOWLFunctionalObjectPropertyAxiom(OP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLFunctionalObjectPropertyAxiom(OP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeObjectFunctional() {
        OWLObjectProperty prop = OP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLFunctionalObjectPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLFunctionalObjectPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationDataFunctionalData() {
        OWLPropertyAxiom axiom = testSubject.getOWLFunctionalDataPropertyAxiom(DP(IRI()));
        assertNotNull(axiom);
    }

    @Test
    public void testEqualsPositiveDataFunctionalData() {
        OWLDataProperty prop = DP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLFunctionalDataPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLFunctionalDataPropertyAxiom(prop);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeDataFunctionalData() {
        OWLPropertyAxiom axA = testSubject.getOWLFunctionalDataPropertyAxiom(DP(IRI()));
        OWLPropertyAxiom axB = testSubject.getOWLFunctionalDataPropertyAxiom(DP(IRI()));
        assertFalse(axA.equals(axB));
    }

    @Test
    public void testHashCodeDataFunctionalData() {
        OWLDataProperty prop = DP(IRI());
        OWLPropertyAxiom axA = testSubject.getOWLFunctionalDataPropertyAxiom(prop);
        OWLPropertyAxiom axB = testSubject.getOWLFunctionalDataPropertyAxiom(prop);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationExactCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectExactCardinality(cardinality, prop,
                testSubject.getOWLThing());
        assertNotNull(restA);
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectExactCardinality(cardinality, prop, cls);
        assertNotNull(restB);
    }

    @Test
    public void testEqualsPositiveExactCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectExactCardinality(cardinality, prop,
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectExactCardinality(cardinality, prop,
                testSubject.getOWLThing());
        assertEquals(restA, restB);
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restC = testSubject.getOWLObjectExactCardinality(cardinality, prop, cls);
        OWLObjectCardinalityRestriction restD = testSubject.getOWLObjectExactCardinality(cardinality, prop, cls);
        assertEquals(restC, restD);
    }

    @Test
    public void testEqualsNegativeExactCard() {
        OWLObjectProperty prop = OP(IRI());
        // Different cardinality
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectExactCardinality(3, prop,
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectExactCardinality(4, prop,
                testSubject.getOWLThing());
        assertFalse(restA.equals(restB));
        // Different property
        OWLObjectCardinalityRestriction restC = testSubject.getOWLObjectExactCardinality(3, OP(IRI()),
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restD = testSubject.getOWLObjectExactCardinality(3, OP(IRI()),
                testSubject.getOWLThing());
        assertFalse(restC.equals(restD));
        // Different filler
        OWLObjectCardinalityRestriction restE = testSubject.getOWLObjectExactCardinality(3, prop, C(IRI()));
        OWLObjectCardinalityRestriction restF = testSubject.getOWLObjectExactCardinality(3, prop, C(IRI()));
        assertFalse(restE.equals(restF));
    }

    @Test
    public void testHashCodeExactCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectExactCardinality(cardinality, prop, cls);
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectExactCardinality(cardinality, prop, cls);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testCreationMaxCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMaxCardinality(cardinality, prop,
                testSubject.getOWLThing());
        assertNotNull(restA);
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMaxCardinality(cardinality, prop, cls);
        assertNotNull(restB);
    }

    @Test
    public void testEqualsPositiveMaxCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMaxCardinality(cardinality, prop,
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMaxCardinality(cardinality, prop,
                testSubject.getOWLThing());
        assertEquals(restA, restB);
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restC = testSubject.getOWLObjectMaxCardinality(cardinality, prop, cls);
        OWLObjectCardinalityRestriction restD = testSubject.getOWLObjectMaxCardinality(cardinality, prop, cls);
        assertEquals(restC, restD);
    }

    @Test
    public void testEqualsNegativeMaxCard() {
        OWLObjectProperty prop = OP(IRI());
        // Different cardinality
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMaxCardinality(3, prop,
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMaxCardinality(4, prop,
                testSubject.getOWLThing());
        assertFalse(restA.equals(restB));
        // Different property
        OWLObjectCardinalityRestriction restC = testSubject.getOWLObjectMaxCardinality(3, OP(IRI()),
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restD = testSubject.getOWLObjectMaxCardinality(3, OP(IRI()),
                testSubject.getOWLThing());
        assertFalse(restC.equals(restD));
        // Different filler
        OWLObjectCardinalityRestriction restE = testSubject.getOWLObjectMaxCardinality(3, prop, C(IRI()));
        OWLObjectCardinalityRestriction restF = testSubject.getOWLObjectMaxCardinality(3, prop, C(IRI()));
        assertFalse(restE.equals(restF));
    }

    @Test
    public void testHashCodeMaxCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMaxCardinality(cardinality, prop, cls);
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMaxCardinality(cardinality, prop, cls);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testCreationMinCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMinCardinality(cardinality, prop,
                testSubject.getOWLThing());
        assertNotNull(restA);
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMinCardinality(cardinality, prop, cls);
        assertNotNull(restB);
    }

    @Test
    public void testEqualsPositiveMinCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMinCardinality(cardinality, prop,
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMinCardinality(cardinality, prop,
                testSubject.getOWLThing());
        assertEquals(restA, restB);
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restC = testSubject.getOWLObjectMinCardinality(cardinality, prop, cls);
        OWLObjectCardinalityRestriction restD = testSubject.getOWLObjectMinCardinality(cardinality, prop, cls);
        assertEquals(restC, restD);
    }

    @Test
    public void testEqualsNegativeMinCard() {
        OWLObjectProperty prop = OP(IRI());
        // Different cardinality
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMinCardinality(3, prop,
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMinCardinality(4, prop,
                testSubject.getOWLThing());
        assertFalse(restA.equals(restB));
        // Different property
        OWLObjectCardinalityRestriction restC = testSubject.getOWLObjectMinCardinality(3, OP(IRI()),
                testSubject.getOWLThing());
        OWLObjectCardinalityRestriction restD = testSubject.getOWLObjectMinCardinality(3, OP(IRI()),
                testSubject.getOWLThing());
        assertFalse(restC.equals(restD));
        // Different filler
        OWLObjectCardinalityRestriction restE = testSubject.getOWLObjectMinCardinality(3, prop, C(IRI()));
        OWLObjectCardinalityRestriction restF = testSubject.getOWLObjectMinCardinality(3, prop, C(IRI()));
        assertFalse(restE.equals(restF));
    }

    @Test
    public void testHashCodeMinCard() {
        OWLObjectProperty prop = OP(IRI());
        int cardinality = 3;
        OWLClassExpression cls = C(IRI());
        OWLObjectCardinalityRestriction restA = testSubject.getOWLObjectMinCardinality(cardinality, prop, cls);
        OWLObjectCardinalityRestriction restB = testSubject.getOWLObjectMinCardinality(cardinality, prop, cls);
        assertEquals(restA.hashCode(), restB.hashCode());
    }

    @Test
    public void testCreationDisjointClasses() {
        OWLClassExpression[] classExpressions = { C(IRI()), C(IRI()), C(IRI()) };
        OWLObject obj = testSubject.getOWLDisjointClassesAxiom(classExpressions);
        assertNotNull(obj);
    }

    @Test
    public void testCreationNotDataRel() {
        assertNotNull(testSubject.getOWLNegativeDataPropertyAssertionAxiom(DP(IRI()), I(), Literal()));
    }

    @Test
    public void testEqualsPositiveNotDataRel() {
        OWLIndividual s = I();
        OWLDataProperty p = DP(IRI());
        OWLLiteral o = Literal();
        OWLIndividualAxiom axA = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, s, o);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeNotDataRel() {
        // Different subject
        OWLDataProperty p = DP(IRI());
        OWLLiteral o = Literal();
        OWLIndividualAxiom axA = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, I(), o);
        OWLIndividualAxiom axB = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, I(), o);
        assertFalse(axA.equals(axB));
        // Different property
        OWLIndividual s = I();
        OWLIndividualAxiom axiomC = testSubject.getOWLNegativeDataPropertyAssertionAxiom(DP(IRI()), s, o);
        OWLIndividualAxiom axiomD = testSubject.getOWLNegativeDataPropertyAssertionAxiom(DP(IRI()), s, o);
        assertFalse(axiomC.equals(axiomD));
        // Different object
        OWLIndividualAxiom axiomE = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, s, Literal());
        OWLIndividualAxiom axiomF = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, s, Literal());
        assertFalse(axiomE.equals(axiomF));
    }

    @Test
    public void testHashCodeNotDataRel() {
        OWLIndividual s = I();
        OWLDataProperty p = DP(IRI());
        OWLLiteral o = Literal();
        OWLIndividualAxiom axA = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLNegativeDataPropertyAssertionAxiom(p, s, o);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationDataRel() {
        assertNotNull(testSubject.getOWLDataPropertyAssertionAxiom(DP(IRI()), I(), Literal()));
    }

    @Test
    public void testEqualsPositiveDataRel() {
        OWLIndividual s = I();
        OWLDataProperty p = DP(IRI());
        OWLLiteral o = Literal();
        OWLIndividualAxiom axA = testSubject.getOWLDataPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLDataPropertyAssertionAxiom(p, s, o);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeDataRel() {
        // Different subject
        OWLDataProperty p = DP(IRI());
        OWLLiteral o = Literal();
        OWLIndividualAxiom axA = testSubject.getOWLDataPropertyAssertionAxiom(p, I(), o);
        OWLIndividualAxiom axB = testSubject.getOWLDataPropertyAssertionAxiom(p, I(), o);
        assertFalse(axA.equals(axB));
        // Different property
        OWLIndividual s = I();
        OWLIndividualAxiom axiomC = testSubject.getOWLDataPropertyAssertionAxiom(DP(IRI()), s, o);
        OWLIndividualAxiom axiomD = testSubject.getOWLDataPropertyAssertionAxiom(DP(IRI()), s, o);
        assertFalse(axiomC.equals(axiomD));
        // Different object
        OWLIndividualAxiom axiomE = testSubject.getOWLDataPropertyAssertionAxiom(p, s, Literal());
        OWLIndividualAxiom axiomF = testSubject.getOWLDataPropertyAssertionAxiom(p, s, Literal());
        assertFalse(axiomE.equals(axiomF));
    }

    @Test
    public void testHashCodeDataRel() {
        OWLIndividual s = I();
        OWLDataProperty p = DP(IRI());
        OWLLiteral o = Literal();
        OWLIndividualAxiom axA = testSubject.getOWLDataPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLDataPropertyAssertionAxiom(p, s, o);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationNotObjectRel() {
        assertNotNull(testSubject.getOWLNegativeObjectPropertyAssertionAxiom(OP(IRI()), I(), I()));
    }

    @Test
    public void testEqualsPositiveNotObjectRel() {
        OWLIndividual s = I();
        OWLObjectProperty p = OP(IRI());
        OWLIndividual o = I();
        OWLIndividualAxiom axA = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, s, o);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeNotObjectRel() {
        // Different subject
        OWLObjectProperty p = OP(IRI());
        OWLIndividual o = I();
        OWLIndividualAxiom axA = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, I(), o);
        OWLIndividualAxiom axB = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, I(), o);
        assertFalse(axA.equals(axB));
        // Different property
        OWLIndividual s = I();
        OWLIndividualAxiom axiomC = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(OP(IRI()), s, o);
        OWLIndividualAxiom axiomD = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(OP(IRI()), s, o);
        assertFalse(axiomC.equals(axiomD));
        // Different object
        OWLIndividualAxiom axiomE = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, s, I());
        OWLIndividualAxiom axiomF = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, s, I());
        assertFalse(axiomE.equals(axiomF));
    }

    @Test
    public void testHashCodeNotObjectRel() {
        OWLIndividual s = I();
        OWLObjectProperty p = OP(IRI());
        OWLIndividual o = I();
        OWLIndividualAxiom axA = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLNegativeObjectPropertyAssertionAxiom(p, s, o);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationObjectRel() {
        assertNotNull(testSubject.getOWLObjectPropertyAssertionAxiom(OP(IRI()), I(), I()));
    }

    @Test
    public void testEqualsPositiveObjectRel() {
        OWLIndividual s = I();
        OWLObjectProperty p = OP(IRI());
        OWLIndividual o = I();
        OWLIndividualAxiom axA = testSubject.getOWLObjectPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLObjectPropertyAssertionAxiom(p, s, o);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectRel() {
        // Different subject
        OWLObjectProperty p = OP(IRI());
        OWLIndividual o = I();
        OWLIndividualAxiom axA = testSubject.getOWLObjectPropertyAssertionAxiom(p, I(), o);
        OWLIndividualAxiom axB = testSubject.getOWLObjectPropertyAssertionAxiom(p, I(), o);
        assertFalse(axA.equals(axB));
        // Different property
        OWLIndividual s = I();
        OWLIndividualAxiom axiomC = testSubject.getOWLObjectPropertyAssertionAxiom(OP(IRI()), s, o);
        OWLIndividualAxiom axiomD = testSubject.getOWLObjectPropertyAssertionAxiom(OP(IRI()), s, o);
        assertFalse(axiomC.equals(axiomD));
        // Different object
        OWLIndividualAxiom axiomE = testSubject.getOWLObjectPropertyAssertionAxiom(p, s, I());
        OWLIndividualAxiom axiomF = testSubject.getOWLObjectPropertyAssertionAxiom(p, s, I());
        assertFalse(axiomE.equals(axiomF));
    }

    @Test
    public void testHashCodeObjectRel() {
        OWLIndividual s = I();
        OWLObjectProperty p = OP(IRI());
        OWLIndividual o = I();
        OWLIndividualAxiom axA = testSubject.getOWLObjectPropertyAssertionAxiom(p, s, o);
        OWLIndividualAxiom axB = testSubject.getOWLObjectPropertyAssertionAxiom(p, s, o);
        assertEquals(axA.hashCode(), axB.hashCode());
    }

    @Test
    public void testCreationDataPropertyDomain() {
        assertNotNull(testSubject.getOWLDataPropertyDomainAxiom(DP(IRI()), C(IRI())));
    }

    @Test
    public void testEqualsPositiveDataPropertyDomain() {
        OWLDataProperty left = DP(IRI());
        OWLClass right = C(IRI());
        OWLAxiom axA = testSubject.getOWLDataPropertyDomainAxiom(left, right);
        OWLAxiom axB = testSubject.getOWLDataPropertyDomainAxiom(left, right);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeDataPropertyDomain() {
        OWLDataProperty left = DP(IRI());
        OWLClass right = C(IRI());
        // Different left operand
        OWLAxiom axA = testSubject.getOWLDataPropertyDomainAxiom(DP(IRI()), right);
        OWLAxiom axB = testSubject.getOWLDataPropertyDomainAxiom(DP(IRI()), right);
        assertFalse(axA.equals(axB));
        // Different right operand
        OWLAxiom axiomC = testSubject.getOWLDataPropertyDomainAxiom(left, C(IRI()));
        OWLAxiom axiomD = testSubject.getOWLDataPropertyDomainAxiom(left, C(IRI()));
        assertFalse(axiomC.equals(axiomD));
    }

    @Test
    public void testHashCodeDataPropertyDomain() {
        OWLDataProperty left = DP(IRI());
        OWLClass right = C(IRI());
        int hashCodeA = testSubject.getOWLDataPropertyDomainAxiom(left, right).hashCode();
        int hashCodeB = testSubject.getOWLDataPropertyDomainAxiom(left, right).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationDataPropertyRange() {
        assertNotNull(testSubject.getOWLDataPropertyRangeAxiom(DP(IRI()), D(IRI())));
    }

    @Test
    public void testEqualsPositiveDataPropertyRange() {
        OWLDataProperty left = DP(IRI());
        OWLDatatype right = D(IRI());
        OWLAxiom axA = testSubject.getOWLDataPropertyRangeAxiom(left, right);
        OWLAxiom axB = testSubject.getOWLDataPropertyRangeAxiom(left, right);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeDataPropertyRange() {
        OWLDataProperty left = DP(IRI());
        OWLDatatype right = D(IRI());
        // Different left operand
        OWLAxiom axA = testSubject.getOWLDataPropertyRangeAxiom(DP(IRI()), right);
        OWLAxiom axB = testSubject.getOWLDataPropertyRangeAxiom(DP(IRI()), right);
        assertFalse(axA.equals(axB));
        // Different right operand
        OWLAxiom axiomC = testSubject.getOWLDataPropertyRangeAxiom(left, D(IRI()));
        OWLAxiom axiomD = testSubject.getOWLDataPropertyRangeAxiom(left, D(IRI()));
        assertFalse(axiomC.equals(axiomD));
    }

    @Test
    public void testHashCodeDataPropertyRange() {
        OWLDataProperty left = DP(IRI());
        OWLDatatype right = D(IRI());
        int hashCodeA = testSubject.getOWLDataPropertyRangeAxiom(left, right).hashCode();
        int hashCodeB = testSubject.getOWLDataPropertyRangeAxiom(left, right).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationSubDataPropertyOf() {
        assertNotNull(testSubject.getOWLSubDataPropertyOfAxiom(DP(IRI()), DP(IRI())));
    }

    @Test
    public void testEqualsPositiveSubDataPropertyOf() {
        OWLDataProperty left = DP(IRI());
        OWLDataProperty right = DP(IRI());
        OWLAxiom axA = testSubject.getOWLSubDataPropertyOfAxiom(left, right);
        OWLAxiom axB = testSubject.getOWLSubDataPropertyOfAxiom(left, right);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeSubDataPropertyOf() {
        OWLDataProperty left = DP(IRI());
        OWLDataProperty right = DP(IRI());
        // Different left operand
        OWLAxiom axA = testSubject.getOWLSubDataPropertyOfAxiom(DP(IRI()), right);
        OWLAxiom axB = testSubject.getOWLSubDataPropertyOfAxiom(DP(IRI()), right);
        assertFalse(axA.equals(axB));
        // Different right operand
        OWLAxiom axiomC = testSubject.getOWLSubDataPropertyOfAxiom(left, DP(IRI()));
        OWLAxiom axiomD = testSubject.getOWLSubDataPropertyOfAxiom(left, DP(IRI()));
        assertFalse(axiomC.equals(axiomD));
    }

    @Test
    public void testHashCodeSubDataPropertyOf() {
        OWLDataProperty left = DP(IRI());
        OWLDataProperty right = DP(IRI());
        int hashCodeA = testSubject.getOWLSubDataPropertyOfAxiom(left, right).hashCode();
        int hashCodeB = testSubject.getOWLSubDataPropertyOfAxiom(left, right).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationClassAssertion() {
        assertNotNull(testSubject.getOWLClassAssertionAxiom(C(IRI()), I()));
    }

    @Test
    public void testEqualsPositiveClassAssertion() {
        OWLIndividual left = I();
        OWLClass right = C(IRI());
        OWLAxiom axA = testSubject.getOWLClassAssertionAxiom(right, left);
        OWLAxiom axB = testSubject.getOWLClassAssertionAxiom(right, left);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeClassAssertion() {
        OWLIndividual left = I();
        OWLClass right = C(IRI());
        // Different left operand
        OWLAxiom axA = testSubject.getOWLClassAssertionAxiom(right, I());
        OWLAxiom axB = testSubject.getOWLClassAssertionAxiom(right, I());
        assertFalse(axA.equals(axB));
        // Different right operand
        OWLAxiom axiomC = testSubject.getOWLClassAssertionAxiom(C(IRI()), left);
        OWLAxiom axiomD = testSubject.getOWLClassAssertionAxiom(C(IRI()), left);
        assertFalse(axiomC.equals(axiomD));
    }

    @Test
    public void testHashCodeClassAssertion() {
        OWLIndividual left = I();
        OWLClass right = C(IRI());
        int hashCodeA = testSubject.getOWLClassAssertionAxiom(right, left).hashCode();
        int hashCodeB = testSubject.getOWLClassAssertionAxiom(right, left).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationObjectPropertyDomain() {
        assertNotNull(testSubject.getOWLObjectPropertyDomainAxiom(OP(IRI()), C(IRI())));
    }

    @Test
    public void testEqualsPositiveObjectPropertyDomain() {
        OWLObjectProperty left = OP(IRI());
        OWLClass right = C(IRI());
        OWLAxiom axA = testSubject.getOWLObjectPropertyDomainAxiom(left, right);
        OWLAxiom axB = testSubject.getOWLObjectPropertyDomainAxiom(left, right);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectPropertyDomain() {
        OWLObjectProperty left = OP(IRI());
        OWLClass right = C(IRI());
        // Different left operand
        OWLAxiom axA = testSubject.getOWLObjectPropertyDomainAxiom(OP(IRI()), right);
        OWLAxiom axB = testSubject.getOWLObjectPropertyDomainAxiom(OP(IRI()), right);
        assertFalse(axA.equals(axB));
        // Different right operand
        OWLAxiom axiomC = testSubject.getOWLObjectPropertyDomainAxiom(left, C(IRI()));
        OWLAxiom axiomD = testSubject.getOWLObjectPropertyDomainAxiom(left, C(IRI()));
        assertFalse(axiomC.equals(axiomD));
    }

    @Test
    public void testHashCodeObjectPropertyDomain() {
        OWLObjectProperty left = OP(IRI());
        OWLClass right = C(IRI());
        int hashCodeA = testSubject.getOWLObjectPropertyDomainAxiom(left, right).hashCode();
        int hashCodeB = testSubject.getOWLObjectPropertyDomainAxiom(left, right).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationObjectPropertyRange() {
        assertNotNull(testSubject.getOWLObjectPropertyRangeAxiom(OP(IRI()), C(IRI())));
    }

    @Test
    public void testEqualsPositiveObjectPropertyRange() {
        OWLObjectProperty left = OP(IRI());
        OWLClass right = C(IRI());
        OWLAxiom axA = testSubject.getOWLObjectPropertyRangeAxiom(left, right);
        OWLAxiom axB = testSubject.getOWLObjectPropertyRangeAxiom(left, right);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeObjectPropertyRange() {
        OWLObjectProperty left = OP(IRI());
        OWLClass right = C(IRI());
        // Different left operand
        OWLAxiom axA = testSubject.getOWLObjectPropertyRangeAxiom(OP(IRI()), right);
        OWLAxiom axB = testSubject.getOWLObjectPropertyRangeAxiom(OP(IRI()), right);
        assertFalse(axA.equals(axB));
        // Different right operand
        OWLAxiom axiomC = testSubject.getOWLObjectPropertyRangeAxiom(left, C(IRI()));
        OWLAxiom axiomD = testSubject.getOWLObjectPropertyRangeAxiom(left, C(IRI()));
        assertFalse(axiomC.equals(axiomD));
    }

    @Test
    public void testHashCodeObjectPropertyRange() {
        OWLObjectProperty left = OP(IRI());
        OWLClass right = C(IRI());
        int hashCodeA = testSubject.getOWLObjectPropertyRangeAxiom(left, right).hashCode();
        int hashCodeB = testSubject.getOWLObjectPropertyRangeAxiom(left, right).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }

    @Test
    public void testCreationSubObjectPropertyOf() {
        assertNotNull(testSubject.getOWLSubObjectPropertyOfAxiom(OP(IRI()), OP(IRI())));
    }

    @Test
    public void testEqualsPositiveSubObjectPropertyOf() {
        OWLObjectProperty left = OP(IRI());
        OWLObjectProperty right = OP(IRI());
        OWLAxiom axA = testSubject.getOWLSubObjectPropertyOfAxiom(left, right);
        OWLAxiom axB = testSubject.getOWLSubObjectPropertyOfAxiom(left, right);
        assertEquals(axA, axB);
    }

    @Test
    public void testEqualsNegativeSubObjectPropertyOf() {
        OWLObjectProperty left = OP(IRI());
        OWLObjectProperty right = OP(IRI());
        // Different left operand
        OWLAxiom axA = testSubject.getOWLSubObjectPropertyOfAxiom(OP(IRI()), right);
        OWLAxiom axB = testSubject.getOWLSubObjectPropertyOfAxiom(OP(IRI()), right);
        assertFalse(axA.equals(axB));
        // Different right operand
        OWLAxiom axiomC = testSubject.getOWLSubObjectPropertyOfAxiom(left, OP(IRI()));
        OWLAxiom axiomD = testSubject.getOWLSubObjectPropertyOfAxiom(left, OP(IRI()));
        assertFalse(axiomC.equals(axiomD));
    }

    @Test
    public void testHashCodeSubObjectPropertyOf() {
        OWLObjectProperty left = OP(IRI());
        OWLObjectProperty right = OP(IRI());
        int hashCodeA = testSubject.getOWLSubObjectPropertyOfAxiom(left, right).hashCode();
        int hashCodeB = testSubject.getOWLSubObjectPropertyOfAxiom(left, right).hashCode();
        assertEquals(hashCodeA, hashCodeB);
    }
}
