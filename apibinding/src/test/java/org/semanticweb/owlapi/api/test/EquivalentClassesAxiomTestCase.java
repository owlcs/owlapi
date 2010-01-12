package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

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
public class EquivalentClassesAxiomTestCase extends AbstractOWLAPITestCase {

    public void testContainsNamedClass() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLClassExpression desc2 = getFactory().getOWLObjectSomeValuesFrom(propP, clsA);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clsA, desc);
        assertTrue(ax.containsNamedEquivalentClass());
        OWLEquivalentClassesAxiom ax2 = getFactory().getOWLEquivalentClassesAxiom(desc, desc2);
        assertFalse(ax2.containsNamedEquivalentClass());
    }


    public void testGetNamedClasses() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clsA, desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertEquals(clses.size(), 1);
        assertTrue(clses.contains(clsA));
    }

    public void testGetNamedClassesWithNothing() {
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(getFactory().getOWLNothing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLThing());
        assertTrue(ax.containsOWLNothing());
    }

    public void testGetNamedClassesWithThing() {
        OWLClass clsB = getOWLClass("B");
        OWLObjectProperty propP = getOWLObjectProperty("p");
        OWLClassExpression desc = getFactory().getOWLObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(getFactory().getOWLThing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLNothing());
        assertTrue(ax.containsOWLThing());
    }

    public void testSplit() {
        OWLClass clsA = getOWLClass("A");
        OWLClass clsB = getOWLClass("B");
        OWLClass clsC = getOWLClass("C");
        Set<OWLClass> clses = new HashSet<OWLClass>();
        clses.add(clsA);
        clses.add(clsB);
        clses.add(clsC);
        OWLEquivalentClassesAxiom ax = getFactory().getOWLEquivalentClassesAxiom(clses);
        Set<OWLSubClassOfAxiom> scas = ax.asOWLSubClassOfAxioms();
        assertEquals(scas.size(), 6);
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsA, clsB)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsB, clsA)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsA, clsC)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsC, clsA)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsB, clsC)));
        assertTrue(scas.contains(getFactory().getOWLSubClassOfAxiom(clsC, clsB)));
    }
}
