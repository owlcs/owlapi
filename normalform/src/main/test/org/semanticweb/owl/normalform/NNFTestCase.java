package org.semanticweb.owl.normalform;

import junit.framework.TestCase;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.NNF;
import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;

import java.net.URI;
/*
 * Copyright (C) 2008, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 06-Jun-2008<br><br>
 */
public class NNFTestCase extends TestCase {

    private OWLDataFactory df = new OWLDataFactoryImpl();

    private OWLClass clsA = df.getOWLClass(URI.create("A"));

    private OWLClass clsB = df.getOWLClass(URI.create("B"));

    private OWLClass clsC = df.getOWLClass(URI.create("C"));

    private OWLClass clsD = df.getOWLClass(URI.create("D"));

    private OWLObjectProperty propP = df.getObjectProperty(URI.create("p"));

    private OWLIndividual indA = df.getIndividual(URI.create("a"));

    private OWLClassExpression getNNF(OWLClassExpression classExpression) {
        NNF nnf = new NNF(df);
        return classExpression.accept(nnf);
    }


    public void testNamedClass() {
        OWLClassExpression desc = clsA;
        OWLClassExpression nnf = clsA;
        OWLClassExpression comp = getNNF(desc);
        assertEquals(nnf, comp);
    }

    public void testObjectIntersectionOf() {
        OWLClassExpression desc = df.getObjectIntersectionOf(clsA, clsB);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectUnionOf(df.getObjectComplementOf(clsA), df.getObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectUnionOf() {
        OWLClassExpression desc = df.getObjectUnionOf(clsA, clsB);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectIntersectionOf(df.getObjectComplementOf(clsA), df.getObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testDoubleNegation() {
        OWLClassExpression desc = df.getObjectComplementOf(clsA);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = clsA;
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }


    public void testTripleNegation() {
        OWLClassExpression desc = df.getObjectComplementOf(df.getObjectComplementOf(clsA));
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectComplementOf(clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectSome() {
        OWLClassExpression desc = df.getObjectSomeValuesFrom(propP, clsA);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectAllValuesFrom(propP, df.getObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectAll() {
        OWLClassExpression desc = df.getObjectAllValuesFrom(propP, clsA);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectSomeValuesFrom(propP, df.getObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectHasValue() {
        OWLClassExpression desc = df.getObjectHasValue(propP, indA);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectAllValuesFrom(propP, df.getObjectComplementOf(df.getObjectOneOf(indA)));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectMin() {
        OWLClassExpression desc = df.getObjectMinCardinality(propP, 3, clsA);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectMaxCardinality(propP, 2, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectMax() {
        OWLClassExpression desc = df.getObjectMaxCardinality(propP, 3, clsA);
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectMinCardinality(propP, 4, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testNestedA() {
        OWLClassExpression fillerA = df.getObjectUnionOf(clsA, clsB);
        OWLClassExpression opA = df.getObjectSomeValuesFrom(propP, fillerA);
        OWLClassExpression opB = clsB;
        OWLClassExpression desc = df.getObjectUnionOf(opA, opB);
        OWLClassExpression nnf = df.getObjectIntersectionOf(df.getObjectComplementOf(clsB),
                df.getObjectAllValuesFrom(propP,
                        df.getObjectIntersectionOf(df.getObjectComplementOf(clsA),
                                df.getObjectComplementOf(clsB))));
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }

    public void testNestedB() {
        OWLClassExpression desc = df.getObjectIntersectionOf(df.getObjectIntersectionOf(clsA, clsB),
                df.getObjectComplementOf(df.getObjectUnionOf(clsC, clsD)));
        OWLClassExpression neg = df.getObjectComplementOf(desc);
        OWLClassExpression nnf = df.getObjectUnionOf(df.getObjectUnionOf(df.getObjectComplementOf(clsA),
                df.getObjectComplementOf(clsB)),
                df.getObjectUnionOf(clsC, clsD));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(comp, nnf);
    }
}
