package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import java.util.HashSet;
import java.util.Set;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 12-Oct-2008<br><br>
 */
public class SubClassAxiomTestCase extends AbstractFileRoundTrippingTestCase {


    protected String getFileName() {
        return "SubClassOf.rdf";
    }


    public void testCorrectAxioms() {
        Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        axioms.add(getFactory().getOWLSubClassOfAxiom(clsA, clsB));
        assertEquals(getOnt().getAxioms(), axioms);
    }


    /**
     * Tests the isGCI method on OWLSubClassAxiom
     */
    public void testIsGCIMethod() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClass clsC = getOWLClass("C");
        OWLClassExpression desc = getFactory().getOWLObjectIntersectionOf(clsA, clsC);
        OWLSubClassOfAxiom ax1 = getFactory().getOWLSubClassOfAxiom(clsA, clsB);
        assertFalse(ax1.isGCI());
        OWLSubClassOfAxiom ax2 = getFactory().getOWLSubClassOfAxiom(desc, clsB);
        assertTrue(ax2.isGCI());

    }
}
