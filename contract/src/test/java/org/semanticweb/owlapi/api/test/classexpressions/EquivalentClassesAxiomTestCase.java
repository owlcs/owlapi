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
package org.semanticweb.owlapi.api.test.classexpressions;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
public class EquivalentClassesAxiomTestCase extends TestBase {

    @Test
    public void testContainsNamedClass() {
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLClassExpression desc = ObjectSomeValuesFrom(propP, clsB);
        OWLClassExpression desc2 = ObjectSomeValuesFrom(propP, clsA);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(clsA, desc);
        assertTrue(ax.containsNamedEquivalentClass());
        OWLEquivalentClassesAxiom ax2 = EquivalentClasses(desc, desc2);
        assertFalse(ax2.containsNamedEquivalentClass());
    }

    @Test
    public void testGetNamedClasses() {
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLClassExpression desc = ObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(clsA, desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertEquals(1, clses.size());
        assertTrue(clses.contains(clsA));
    }

    @Test
    public void testGetNamedClassesWithNothing() {
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLClassExpression desc = ObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(OWLNothing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLThing());
        assertTrue(ax.containsOWLNothing());
    }

    @Test
    public void testGetNamedClassesWithThing() {
        OWLClass clsB = Class(iri("B"));
        OWLObjectProperty propP = ObjectProperty(iri("p"));
        OWLClassExpression desc = ObjectSomeValuesFrom(propP, clsB);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(OWLThing(), desc);
        Set<OWLClass> clses = ax.getNamedClasses();
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLNothing());
        assertTrue(ax.containsOWLThing());
    }

    @Test
    public void testSplit() {
        OWLClass clsA = Class(iri("A"));
        OWLClass clsB = Class(iri("B"));
        OWLClass clsC = Class(iri("C"));
        OWLEquivalentClassesAxiom ax = EquivalentClasses(clsA, clsB, clsC);
        Set<OWLSubClassOfAxiom> scas = ax.asOWLSubClassOfAxioms();
        assertEquals(6, scas.size());
        assertTrue(scas.contains(SubClassOf(clsA, clsB)));
        assertTrue(scas.contains(SubClassOf(clsB, clsA)));
        assertTrue(scas.contains(SubClassOf(clsA, clsC)));
        assertTrue(scas.contains(SubClassOf(clsC, clsA)));
        assertTrue(scas.contains(SubClassOf(clsB, clsC)));
        assertTrue(scas.contains(SubClassOf(clsC, clsB)));
    }
}
