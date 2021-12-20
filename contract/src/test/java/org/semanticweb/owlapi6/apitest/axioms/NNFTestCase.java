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
package org.semanticweb.owlapi6.apitest.axioms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.utility.NNF;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
class NNFTestCase extends TestBase {

    private static OWLClassExpression getNNF(OWLClassExpression classExpression) {
        NNF nnf = new NNF(df);
        return classExpression.accept(nnf.getClassVisitor());
    }

    @Test
    void testPosOWLClass() {
        assertEquals(CLASSES.A, CLASSES.A.getNNF());
    }

    @Test
    void testNegOWLClass() {
        assertEquals(notA, notA.getNNF());
    }

    @Test
    void testPosAllValuesFrom() {
        OWLClassExpression cls = ObjectAllValuesFrom(OBJPROPS.P, CLASSES.A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    void testNegAllValuesFrom() {
        OWLClassExpression cls = ObjectAllValuesFrom(OBJPROPS.P, CLASSES.A).getObjectComplementOf();
        OWLClassExpression nnf =
            ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.A.getObjectComplementOf());
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    void testPosSomeValuesFrom() {
        OWLClassExpression cls = ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    void testNegSomeValuesFrom() {
        OWLClassExpression cls = ObjectComplementOf(ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.A));
        OWLClassExpression nnf = ObjectAllValuesFrom(OBJPROPS.P, notA);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    void testPosObjectIntersectionOf() {
        OWLClassExpression cls = ObjectIntersectionOf(CLASSES.A, CLASSES.B, CLASSES.C);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    void testNegObjectIntersectionOf() {
        OWLClassExpression cls =
            ObjectComplementOf(ObjectIntersectionOf(CLASSES.A, CLASSES.B, CLASSES.C));
        OWLClassExpression nnf = ObjectUnionOf(notA, notB, notC);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    void testPosObjectUnionOf() {
        OWLClassExpression cls = ObjectUnionOf(CLASSES.A, CLASSES.B, CLASSES.C);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    void testNegObjectUnionOf() {
        OWLClassExpression cls = ObjectComplementOf(ObjectUnionOf(CLASSES.A, CLASSES.B, CLASSES.C));
        OWLClassExpression nnf = ObjectIntersectionOf(notA, notB, notC);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    void testPosObjectMinCardinality() {
        OWLClassExpression cls = ObjectMinCardinality(3, OBJPROPS.P, CLASSES.A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    void testNegObjectMinCardinality() {
        OWLClassExpression cls =
            ObjectMinCardinality(3, OBJPROPS.P, CLASSES.A).getObjectComplementOf();
        OWLClassExpression nnf = ObjectMaxCardinality(2, OBJPROPS.P, CLASSES.A);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    void testPosObjectMaxCardinality() {
        OWLClassExpression cls = ObjectMaxCardinality(3, OBJPROPS.P, CLASSES.A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    void testNegObjectMaxCardinality() {
        OWLClassExpression cls =
            ObjectMaxCardinality(3, OBJPROPS.P, CLASSES.A).getObjectComplementOf();
        OWLClassExpression nnf = ObjectMinCardinality(4, OBJPROPS.P, CLASSES.A);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    void testNamedClass() {
        assertEquals(CLASSES.A, getNNF(CLASSES.A));
    }

    @Test
    void testObjectIntersectionOf() {
        OWLClassExpression nnf = ObjectUnionOf(notA, notB);
        OWLClassExpression comp =
            getNNF(ObjectComplementOf(ObjectIntersectionOf(CLASSES.A, CLASSES.B)));
        assertEquals(nnf, comp);
    }

    @Test
    void testObjectUnionOf() {
        OWLClassExpression neg = ObjectComplementOf(ObjectUnionOf(CLASSES.A, CLASSES.B));
        OWLClassExpression nnf = ObjectIntersectionOf(notA, notB);
        assertEquals(nnf, getNNF(neg));
    }

    @Test
    void testDoubleNegation() {
        assertEquals(CLASSES.A, getNNF(ObjectComplementOf(notA)));
    }

    @Test
    void testTripleNegation() {
        OWLClassExpression desc = ObjectComplementOf(notA);
        assertEquals(notA, getNNF(ObjectComplementOf(desc)));
    }

    @Test
    void testObjectSome() {
        OWLClassExpression desc = ObjectSomeValuesFrom(OBJPROPS.P, CLASSES.A);
        OWLClassExpression nnf = ObjectAllValuesFrom(OBJPROPS.P, notA);
        assertEquals(nnf, getNNF(ObjectComplementOf(desc)));
    }

    @Test
    void testObjectAll() {
        OWLClassExpression desc = ObjectAllValuesFrom(OBJPROPS.P, CLASSES.A);
        OWLClassExpression nnf = ObjectSomeValuesFrom(OBJPROPS.P, notA);
        assertEquals(nnf, getNNF(ObjectComplementOf(desc)));
    }

    @Test
    void testObjectHasValue() {
        OWLClassExpression desc = ObjectHasValue(OBJPROPS.P, INDIVIDUALS.indA);
        OWLClassExpression nnf =
            ObjectAllValuesFrom(OBJPROPS.P, ObjectComplementOf(ObjectOneOf(INDIVIDUALS.indA)));
        assertEquals(nnf, getNNF(ObjectComplementOf(desc)));
    }

    @Test
    void testObjectMin() {
        OWLClassExpression desc = ObjectMinCardinality(3, OBJPROPS.P, CLASSES.A);
        OWLClassExpression nnf = ObjectMaxCardinality(2, OBJPROPS.P, CLASSES.A);
        assertEquals(nnf, getNNF(ObjectComplementOf(desc)));
    }

    @Test
    void testObjectMax() {
        OWLClassExpression desc = ObjectMaxCardinality(3, OBJPROPS.P, CLASSES.A);
        OWLClassExpression nnf = ObjectMinCardinality(4, OBJPROPS.P, CLASSES.A);
        assertEquals(nnf, getNNF(ObjectComplementOf(desc)));
    }

    @Test
    void testNestedA() {
        OWLClassExpression opA =
            ObjectSomeValuesFrom(OBJPROPS.P, ObjectUnionOf(CLASSES.A, CLASSES.B));
        OWLClassExpression desc = ObjectUnionOf(opA, CLASSES.B);
        OWLClassExpression nnf = ObjectIntersectionOf(notB,
            ObjectAllValuesFrom(OBJPROPS.P, ObjectIntersectionOf(notA, notB)));
        assertEquals(getNNF(ObjectComplementOf(desc)), nnf);
    }

    @Test
    void testNestedB() {
        OWLClassExpression desc = ObjectIntersectionOf(ObjectIntersectionOf(CLASSES.A, CLASSES.B),
            ObjectComplementOf(ObjectUnionOf(CLASSES.C, CLASSES.D)));
        OWLClassExpression nnf =
            ObjectUnionOf(ObjectUnionOf(notA, notB), ObjectUnionOf(CLASSES.C, CLASSES.D));
        assertEquals(getNNF(ObjectComplementOf(desc)), nnf);
    }
}
