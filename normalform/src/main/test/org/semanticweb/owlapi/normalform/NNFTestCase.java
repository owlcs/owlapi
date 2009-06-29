package org.semanticweb.owlapi.normalform;

import junit.framework.TestCase;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.NNF;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
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

    private OWLDataFactory df = OWLDataFactoryImpl.getInstance();

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


    public void testNamedClass() {
        OWLClassExpression desc = clsA;
        OWLClassExpression nnf = clsA;
        OWLClassExpression comp = getNNF(desc);
        assertEquals(nnf, comp);
    }

    public void testObjectIntersectionOf() {
        OWLClassExpression desc = df.getOWLObjectIntersectionOf(clsA, clsB);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(clsA), df.getOWLObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectUnionOf() {
        OWLClassExpression desc = df.getOWLObjectUnionOf(clsA, clsB);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectIntersectionOf(df.getOWLObjectComplementOf(clsA), df.getOWLObjectComplementOf(clsB));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testDoubleNegation() {
        OWLClassExpression desc = df.getOWLObjectComplementOf(clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = clsA;
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }


    public void testTripleNegation() {
        OWLClassExpression desc = df.getOWLObjectComplementOf(df.getOWLObjectComplementOf(clsA));
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectComplementOf(clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectSome() {
        OWLClassExpression desc = df.getOWLObjectSomeValuesFrom(propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectAllValuesFrom(propP, df.getOWLObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectAll() {
        OWLClassExpression desc = df.getOWLObjectAllValuesFrom(propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectSomeValuesFrom(propP, df.getOWLObjectComplementOf(clsA));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectHasValue() {
        OWLClassExpression desc = df.getOWLObjectHasValue(propP, indA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectAllValuesFrom(propP, df.getOWLObjectComplementOf(df.getOWLObjectOneOf(indA)));
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectMin() {
        OWLClassExpression desc = df.getOWLObjectMinCardinality(3, propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectMaxCardinality(2, propP, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectMax() {
        OWLClassExpression desc = df.getOWLObjectMaxCardinality(3, propP, clsA);
        OWLClassExpression neg = df.getOWLObjectComplementOf(desc);
        OWLClassExpression nnf = df.getOWLObjectMinCardinality(4, propP, clsA);
        OWLClassExpression comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

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
