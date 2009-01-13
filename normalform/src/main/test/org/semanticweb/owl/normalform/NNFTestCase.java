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

    private OWLObjectProperty propP = df.getOWLObjectProperty(URI.create("p"));

    private OWLIndividual indA = df.getOWLIndividual(URI.create("a"));

    private OWLDescription getNNF(OWLDescription description) {
        NNF nnf = new NNF(df);
        return description.accept(nnf);
    }


    public void testNamedClass() {
        OWLDescription desc = clsA;
        OWLDescription nnf = clsA;
        OWLDescription comp = getNNF(desc);
        assertEquals(nnf, comp);
    }

    public void testObjectIntersectionOf() {
        OWLDescription desc = df.getOWLObjectIntersectionOf(clsA, clsB);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(clsA), df.getOWLObjectComplementOf(clsB));
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectUnionOf() {
        OWLDescription desc = df.getOWLObjectUnionOf(clsA, clsB);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectIntersectionOf(df.getOWLObjectComplementOf(clsA), df.getOWLObjectComplementOf(clsB));
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testDoubleNegation() {
        OWLDescription desc = df.getOWLObjectComplementOf(clsA);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = clsA;
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }


    public void testTripleNegation() {
        OWLDescription desc = df.getOWLObjectComplementOf(df.getOWLObjectComplementOf(clsA));
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectComplementOf(clsA);
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectSome() {
        OWLDescription desc = df.getOWLObjectSomeRestriction(propP, clsA);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectAllRestriction(propP, df.getOWLObjectComplementOf(clsA));
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectAll() {
        OWLDescription desc = df.getOWLObjectAllRestriction(propP, clsA);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectSomeRestriction(propP, df.getOWLObjectComplementOf(clsA));
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectHasValue() {
        OWLDescription desc = df.getOWLObjectValueRestriction(propP, indA);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectAllRestriction(propP, df.getOWLObjectComplementOf(df.getOWLObjectOneOf(indA)));
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectMin() {
        OWLDescription desc = df.getOWLObjectMinCardinalityRestriction(propP, 3, clsA);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectMaxCardinalityRestriction(propP, 2, clsA);
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testObjectMax() {
        OWLDescription desc = df.getOWLObjectMaxCardinalityRestriction(propP, 3, clsA);
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectMinCardinalityRestriction(propP, 4, clsA);
        OWLDescription comp = getNNF(neg);
        assertEquals(nnf, comp);
    }

    public void testNestedA() {
        OWLDescription fillerA = df.getOWLObjectUnionOf(clsA, clsB);
        OWLDescription opA = df.getOWLObjectSomeRestriction(propP, fillerA);
        OWLDescription opB = clsB;
        OWLDescription desc = df.getOWLObjectUnionOf(opA, opB);
        OWLDescription nnf = df.getOWLObjectIntersectionOf(df.getOWLObjectComplementOf(clsB),
                                                           df.getOWLObjectAllRestriction(propP,
                                                                                         df.getOWLObjectIntersectionOf(df.getOWLObjectComplementOf(clsA),
                                                                                                                       df.getOWLObjectComplementOf(clsB))));
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription comp = getNNF(neg);
        assertEquals(comp, nnf);
    }

    public void testNestedB() {
        OWLDescription desc = df.getOWLObjectIntersectionOf(df.getOWLObjectIntersectionOf(clsA, clsB),
                                                            df.getOWLObjectComplementOf(df.getOWLObjectUnionOf(clsC, clsD)));
        OWLDescription neg = df.getOWLObjectComplementOf(desc);
        OWLDescription nnf = df.getOWLObjectUnionOf(df.getOWLObjectUnionOf(df.getOWLObjectComplementOf(clsA),
                                                                                  df.getOWLObjectComplementOf(clsB)),
                                                    df.getOWLObjectUnionOf(clsC, clsD));
        OWLDescription comp = getNNF(neg);
        assertEquals(comp, nnf);
    }
}
