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

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectAllValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectComplementOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectHasValue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectIntersectionOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectMaxCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectMinCardinality;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectOneOf;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectUnionOf;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.C;
import static org.semanticweb.owlapi6.apitest.TestEntities.D;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;
import static org.semanticweb.owlapi6.apitest.TestEntities.indA;
import static org.semanticweb.owlapi6.apitest.TestEntities.notA;
import static org.semanticweb.owlapi6.apitest.TestEntities.notB;
import static org.semanticweb.owlapi6.apitest.TestEntities.notC;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi6.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi6.utility.NNF;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class NNFTestCase extends TestBase {

    @Test
    public void testPosOWLClass() {
        assertEquals(A.getNNF(), A);
    }

    @Test
    public void testNegOWLClass() {
        assertEquals(notA.getNNF(), notA);
    }

    @Test
    public void testPosAllValuesFrom() {
        OWLClassExpression cls = ObjectAllValuesFrom(P, A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegAllValuesFrom() {
        OWLObjectAllValuesFrom allValuesFrom = ObjectAllValuesFrom(P, A);
        OWLClassExpression cls = allValuesFrom.getObjectComplementOf();
        OWLClassExpression nnf = ObjectSomeValuesFrom(P, A.getObjectComplementOf());
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosSomeValuesFrom() {
        OWLClassExpression cls = ObjectSomeValuesFrom(P, A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegSomeValuesFrom() {
        OWLObjectSomeValuesFrom someValuesFrom = ObjectSomeValuesFrom(P, A);
        OWLClassExpression cls = ObjectComplementOf(someValuesFrom);
        OWLClassExpression nnf = ObjectAllValuesFrom(P, notA);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectIntersectionOf() {
        OWLClassExpression cls = ObjectIntersectionOf(A, B, C);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectIntersectionOf() {
        OWLClassExpression cls = ObjectComplementOf(ObjectIntersectionOf(A, B, C));
        OWLClassExpression nnf = ObjectUnionOf(notA, notB, notC);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectUnionOf() {
        OWLClassExpression cls = ObjectUnionOf(A, B, C);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectUnionOf() {
        OWLClassExpression cls = ObjectComplementOf(ObjectUnionOf(A, B, C));
        OWLClassExpression nnf = ObjectIntersectionOf(notA, notB, notC);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectMinCardinality() {
        OWLClassExpression cls = ObjectMinCardinality(3, P, A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectMinCardinality() {
        OWLClassExpression cls = ObjectMinCardinality(3, P, A).getObjectComplementOf();
        OWLClassExpression nnf = ObjectMaxCardinality(2, P, A);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectMaxCardinality() {
        OWLClassExpression cls = ObjectMaxCardinality(3, P, A);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectMaxCardinality() {
        OWLClassExpression cls = ObjectMaxCardinality(3, P, A).getObjectComplementOf();
        OWLClassExpression nnf = ObjectMinCardinality(4, P, A);
        assertEquals(cls.getNNF(), nnf);
    }

    private static OWLClassExpression getNNF(OWLClassExpression classExpression) {
        NNF nnf = new NNF(df);
        return classExpression.accept(nnf.getClassVisitor());
    }

    @Test
    public void testNamedClass() {
        assertEquals(A, getNNF(A));
    }

    @Test
    public void testObjectIntersectionOf() {
        OWLClassExpression desc = ObjectIntersectionOf(A, B);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectUnionOf(notA, notB);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectUnionOf() {
        OWLClassExpression desc = ObjectUnionOf(A, B);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectIntersectionOf(notA, notB);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testDoubleNegation() {
        OWLClassExpression neg = ObjectComplementOf(notA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(A, comp);
    }

    @Test
    public void testTripleNegation() {
        OWLClassExpression desc = ObjectComplementOf(notA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(notA, comp);
    }

    @Test
    public void testObjectSome() {
        OWLClassExpression desc = ObjectSomeValuesFrom(P, A);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectAllValuesFrom(P, notA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectAll() {
        OWLClassExpression desc = ObjectAllValuesFrom(P, A);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectSomeValuesFrom(P, notA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectHasValue() {
        OWLClassExpression desc = ObjectHasValue(P, indA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectAllValuesFrom(P, ObjectComplementOf(ObjectOneOf(indA)));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectMin() {
        OWLClassExpression desc = ObjectMinCardinality(3, P, A);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectMaxCardinality(2, P, A);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectMax() {
        OWLClassExpression desc = ObjectMaxCardinality(3, P, A);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectMinCardinality(4, P, A);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testNestedA() {
        OWLClassExpression fillerA = ObjectUnionOf(A, B);
        OWLClassExpression opA = ObjectSomeValuesFrom(P, fillerA);
        OWLClassExpression desc = ObjectUnionOf(opA, B);
        OWLClassExpression nnf =
            ObjectIntersectionOf(notB, ObjectAllValuesFrom(P, ObjectIntersectionOf(notA, notB)));
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }

    @Test
    public void testNestedB() {
        OWLClassExpression desc = ObjectIntersectionOf(ObjectIntersectionOf(A, B),
            ObjectComplementOf(ObjectUnionOf(C, D)));
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectUnionOf(ObjectUnionOf(notA, notB), ObjectUnionOf(C, D));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }
}
