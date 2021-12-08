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
package org.semanticweb.owlapi.api.test.dataproperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collection;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
class EquivalentClassesAxiomTestCase extends TestBase {

    @Test
    void testContainsNamedClass() {
        OWLClassExpression desc = ObjectSomeValuesFrom(P, B);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, desc);
        assertTrue(ax.containsNamedEquivalentClass());
        OWLEquivalentClassesAxiom ax2 = EquivalentClasses(desc, ObjectSomeValuesFrom(P, A));
        assertFalse(ax2.containsNamedEquivalentClass());
    }

    @Test
    void testGetNamedClasses() {
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, ObjectSomeValuesFrom(P, B));
        Set<OWLClass> clses = asUnorderedSet(ax.namedClasses());
        assertEquals(1, clses.size());
        assertTrue(clses.contains(A));
    }

    @Test
    void testGetNamedClassesWithNothing() {
        OWLEquivalentClassesAxiom ax = EquivalentClasses(OWLNothing(), ObjectSomeValuesFrom(P, B));
        Set<OWLClass> clses = asUnorderedSet(ax.namedClasses());
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLThing());
        assertTrue(ax.containsOWLNothing());
    }

    @Test
    void testGetNamedClassesWithThing() {
        OWLEquivalentClassesAxiom ax = EquivalentClasses(OWLThing(), ObjectSomeValuesFrom(P, B));
        Set<OWLClass> clses = asUnorderedSet(ax.namedClasses());
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLNothing());
        assertTrue(ax.containsOWLThing());
    }

    @Test
    void testSplit() {
        Collection<OWLSubClassOfAxiom> scas = EquivalentClasses(A, B, C).asOWLSubClassOfAxioms();
        assertEquals(6, scas.size());
        assertTrue(scas.contains(SubClassOf(A, B)));
        assertTrue(scas.contains(SubClassOf(B, A)));
        assertTrue(scas.contains(SubClassOf(A, C)));
        assertTrue(scas.contains(SubClassOf(C, A)));
        assertTrue(scas.contains(SubClassOf(B, C)));
        assertTrue(scas.contains(SubClassOf(C, B)));
    }
}
