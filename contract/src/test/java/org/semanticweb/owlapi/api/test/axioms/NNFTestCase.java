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
package org.semanticweb.owlapi.api.test.axioms;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.NNF;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public class NNFTestCase extends TestBase {

    @Test
    public void testPosOWLClass() {
        OWLClass cls = Class(iri("A"));
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegOWLClass() {
        OWLClassExpression cls = ObjectComplementOf(Class(iri("A")));
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testPosAllValuesFrom() {
        OWLClassExpression cls = ObjectAllValuesFrom(ObjectProperty(iri("p")), Class(iri("A")));
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegAllValuesFrom() {
        OWLObjectProperty property = ObjectProperty(iri("p"));
        OWLClass filler = Class(iri("A"));
        OWLObjectAllValuesFrom allValuesFrom = ObjectAllValuesFrom(property, filler);
        OWLClassExpression cls = allValuesFrom.getObjectComplementOf();
        OWLClassExpression nnf = ObjectSomeValuesFrom(property, filler.getObjectComplementOf());
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosSomeValuesFrom() {
        OWLClassExpression cls = ObjectSomeValuesFrom(ObjectProperty(iri("p")), Class(iri("A")));
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegSomeValuesFrom() {
        OWLObjectProperty property = ObjectProperty(iri("p"));
        OWLClass filler = Class(iri("A"));
        OWLObjectSomeValuesFrom someValuesFrom = ObjectSomeValuesFrom(property, filler);
        OWLClassExpression cls = ObjectComplementOf(someValuesFrom);
        OWLClassExpression nnf = ObjectAllValuesFrom(property, ObjectComplementOf(filler));
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectIntersectionOf() {
        OWLClassExpression cls = ObjectIntersectionOf(Class(iri("A")), Class(iri("B")), Class(iri("C")));
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectIntersectionOf() {
        OWLClassExpression cls = ObjectComplementOf(
                ObjectIntersectionOf(Class(iri("A")), Class(iri("B")), Class(iri("C"))));
        OWLClassExpression nnf = ObjectUnionOf(ObjectComplementOf(Class(iri("A"))), ObjectComplementOf(Class(iri("B"))),
                ObjectComplementOf(Class(iri("C"))));
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectUnionOf() {
        OWLClassExpression cls = ObjectUnionOf(Class(iri("A")), Class(iri("B")), Class(iri("C")));
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectUnionOf() {
        OWLClassExpression cls = ObjectComplementOf(ObjectUnionOf(Class(iri("A")), Class(iri("B")), Class(iri("C"))));
        OWLClassExpression nnf = ObjectIntersectionOf(ObjectComplementOf(Class(iri("A"))),
                ObjectComplementOf(Class(iri("B"))), ObjectComplementOf(Class(iri("C"))));
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectMinCardinality() {
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        OWLClassExpression filler = Class(iri("A"));
        OWLClassExpression cls = ObjectMinCardinality(3, prop, filler);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectMinCardinality() {
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        OWLClassExpression filler = Class(iri("A"));
        OWLClassExpression cls = ObjectMinCardinality(3, prop, filler).getObjectComplementOf();
        OWLClassExpression nnf = ObjectMaxCardinality(2, prop, filler);
        assertEquals(cls.getNNF(), nnf);
    }

    @Test
    public void testPosObjectMaxCardinality() {
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        OWLClassExpression filler = Class(iri("A"));
        OWLClassExpression cls = ObjectMaxCardinality(3, prop, filler);
        assertEquals(cls.getNNF(), cls);
    }

    @Test
    public void testNegObjectMaxCardinality() {
        OWLObjectProperty prop = ObjectProperty(iri("p"));
        OWLClassExpression filler = Class(iri("A"));
        OWLClassExpression cls = ObjectMaxCardinality(3, prop, filler).getObjectComplementOf();
        OWLClassExpression nnf = ObjectMinCardinality(4, prop, filler);
        assertEquals(cls.getNNF(), nnf);
    }

    private final @Nonnull OWLClass clsA = Class(IRI("A"));
    private final @Nonnull OWLClass clsB = Class(IRI("B"));
    private final @Nonnull OWLClass clsC = Class(IRI("C"));
    private final @Nonnull OWLClass clsD = Class(IRI("D"));
    private final @Nonnull OWLObjectProperty propP = ObjectProperty(IRI("p"));
    private final @Nonnull OWLNamedIndividual indA = NamedIndividual(IRI("a"));

    private static OWLClassExpression getNNF(OWLClassExpression classExpression) {
        NNF nnf = new NNF(df);
        return classExpression.accept(nnf.getClassVisitor());
    }

    @Test
    public void testNamedClass() {
        OWLClassExpression desc = clsA;
        OWLClassExpression nnf = clsA;
        OWLClassExpression comp = getNNF(desc);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectIntersectionOf() {
        OWLClassExpression desc = ObjectIntersectionOf(clsA, clsB);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectUnionOf(ObjectComplementOf(clsA), ObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectUnionOf() {
        OWLClassExpression desc = ObjectUnionOf(clsA, clsB);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectIntersectionOf(ObjectComplementOf(clsA), ObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testDoubleNegation() {
        OWLClassExpression desc = ObjectComplementOf(clsA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = clsA;
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testTripleNegation() {
        OWLClassExpression desc = ObjectComplementOf(ObjectComplementOf(clsA));
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectComplementOf(clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectSome() {
        OWLClassExpression desc = ObjectSomeValuesFrom(propP, clsA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectAllValuesFrom(propP, ObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectAll() {
        OWLClassExpression desc = ObjectAllValuesFrom(propP, clsA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectSomeValuesFrom(propP, ObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectHasValue() {
        OWLClassExpression desc = ObjectHasValue(propP, indA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectAllValuesFrom(propP, ObjectComplementOf(ObjectOneOf(indA)));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectMin() {
        OWLClassExpression desc = ObjectMinCardinality(3, propP, clsA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectMaxCardinality(2, propP, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectMax() {
        OWLClassExpression desc = ObjectMaxCardinality(3, propP, clsA);
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectMinCardinality(4, propP, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testNestedA() {
        OWLClassExpression fillerA = ObjectUnionOf(clsA, clsB);
        OWLClassExpression opA = ObjectSomeValuesFrom(propP, fillerA);
        OWLClassExpression opB = clsB;
        OWLClassExpression desc = ObjectUnionOf(opA, opB);
        OWLClassExpression nnf = ObjectIntersectionOf(ObjectComplementOf(clsB),
                ObjectAllValuesFrom(propP, ObjectIntersectionOf(ObjectComplementOf(clsA), ObjectComplementOf(clsB))));
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }

    @Test
    public void testNestedB() {
        OWLClassExpression desc = ObjectIntersectionOf(ObjectIntersectionOf(clsA, clsB),
                ObjectComplementOf(ObjectUnionOf(clsC, clsD)));
        OWLClassExpression neg = ObjectComplementOf(desc);
        OWLClassExpression nnf = ObjectUnionOf(ObjectUnionOf(ObjectComplementOf(clsA), ObjectComplementOf(clsB)),
                ObjectUnionOf(clsC, clsD));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }
}
