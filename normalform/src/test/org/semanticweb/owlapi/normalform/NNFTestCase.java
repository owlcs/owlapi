/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.normalform;

import junit.framework.TestCase;

import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.NNF;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 06-Jun-2008<br><br>
 */
public class NNFTestCase extends TestCase {

    private OWLDataFactory df = new OWLDataFactoryImpl();

    private OWLClass clsA = df.getOWLClass(IRI.create("A"));

    private OWLClass clsB = df.getOWLClass(IRI.create("B"));

    private OWLClass clsC = df.getOWLClass(IRI.create("C"));

    private OWLClass clsD = df.getOWLClass(IRI.create("D"));

    private OWLObjectProperty propP = df.getOWLObjectProperty(IRI.create("p"));

    private OWLIndividual indA = df.getOWLNamedIndividual(IRI.create("a"));

    private OWLClassExpression getNNF(OWLClassExpression classExpression) {
        NNF nnf = new NNF(df);
        return classExpression.accept(nnf);
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
        OWLClassExpression desc = df.getOWLObjectIntersectionOf(clsA, clsB);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(clsA), df.getOWLObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectUnionOf() {
        OWLClassExpression desc = df.getOWLObjectUnionOf(clsA, clsB);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectIntersectionOf(df.getOWLObjectComplementOf(clsA), df.getOWLObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testDoubleNegation() {
        OWLClassExpression desc = df.getOWLObjectComplementOf(clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = clsA;
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }


    @Test
    public void testTripleNegation() {
        OWLClassExpression desc = df.getOWLObjectComplementOf(df.getOWLObjectComplementOf(clsA));
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectComplementOf(clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectSome() {
        OWLClassExpression desc = df.getOWLObjectSomeValuesFrom(propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectAllValuesFrom(propP, df.getOWLObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectAll() {
        OWLClassExpression desc = df.getOWLObjectAllValuesFrom(propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectSomeValuesFrom(propP, df.getOWLObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectHasValue() {
        OWLClassExpression desc = df.getOWLObjectHasValue(propP, indA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectAllValuesFrom(propP, df.getOWLObjectComplementOf(df.getOWLObjectOneOf(indA)));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectMin() {
        OWLClassExpression desc = df.getOWLObjectMinCardinality(3, propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectMaxCardinality(2, propP, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testObjectMax() {
        OWLClassExpression desc = df.getOWLObjectMaxCardinality(3, propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectMinCardinality(4, propP, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    @Test
    public void testNestedA() {
        OWLClassExpression fillerA = df.getOWLObjectUnionOf(clsA, clsB);
        OWLClassExpression opA = df.getOWLObjectSomeValuesFrom(propP, fillerA);
        OWLClassExpression opB = clsB;
        OWLClassExpression desc = df.getOWLObjectUnionOf(opA, opB);
        OWLClassExpression nnf = df.getOWLObjectIntersectionOf(df.getOWLObjectComplementOf(clsB),
                df.getOWLObjectAllValuesFrom(propP,
                        df.getOWLObjectIntersectionOf(df.getOWLObjectComplementOf(clsA),
                                df.getOWLObjectComplementOf(clsB))));
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }

    @Test
    public void testNestedB() {
        OWLClassExpression desc = df.getOWLObjectIntersectionOf(df.getOWLObjectIntersectionOf(clsA, clsB),
                df.getOWLObjectComplementOf(df.getOWLObjectUnionOf(clsC, clsD)));
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectUnionOf(df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(clsA),
                df.getOWLObjectComplementOf(clsB)),
                df.getOWLObjectUnionOf(clsC, clsD));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }
}
