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
package org.semanticweb.owlapi6.apitest.dataproperties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.ObjectSomeValuesFrom;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.SubClassOf;
import static org.semanticweb.owlapi6.apitest.TestEntities.A;
import static org.semanticweb.owlapi6.apitest.TestEntities.B;
import static org.semanticweb.owlapi6.apitest.TestEntities.C;
import static org.semanticweb.owlapi6.apitest.TestEntities.P;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi6.model.OWLSubClassOfAxiom;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
public class EquivalentClassesAxiomTestCase extends TestBase {

    @Test
    public void testContainsNamedClass() {
        OWLClassExpression desc = ObjectSomeValuesFrom(P, B);
        OWLClassExpression desc2 = ObjectSomeValuesFrom(P, A);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, desc);
        assertTrue(ax.containsNamedEquivalentClass());
        OWLEquivalentClassesAxiom ax2 = EquivalentClasses(desc, desc2);
        assertFalse(ax2.containsNamedEquivalentClass());
    }

    @Test
    public void testGetNamedClasses() {
        OWLClassExpression desc = ObjectSomeValuesFrom(P, B);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, desc);
        Set<OWLClass> clses = asUnorderedSet(ax.namedClasses());
        assertEquals(1, clses.size());
        assertTrue(clses.contains(A));
    }

    @Test
    public void testGetNamedClassesWithNothing() {
        OWLClassExpression desc = ObjectSomeValuesFrom(P, B);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(OWLNothing(), desc);
        Set<OWLClass> clses = asUnorderedSet(ax.namedClasses());
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLThing());
        assertTrue(ax.containsOWLNothing());
    }

    @Test
    public void testGetNamedClassesWithThing() {
        OWLClassExpression desc = ObjectSomeValuesFrom(P, B);
        OWLEquivalentClassesAxiom ax = EquivalentClasses(OWLThing(), desc);
        Set<OWLClass> clses = asUnorderedSet(ax.namedClasses());
        assertTrue(clses.isEmpty());
        assertFalse(ax.containsOWLNothing());
        assertTrue(ax.containsOWLThing());
    }

    @Test
    public void testSplit() {
        OWLEquivalentClassesAxiom ax = EquivalentClasses(A, B, C);
        Collection<OWLSubClassOfAxiom> scas = ax.asOWLSubClassOfAxioms();
        assertEquals(6, scas.size());
        assertTrue(scas.contains(SubClassOf(A, B)));
        assertTrue(scas.contains(SubClassOf(B, A)));
        assertTrue(scas.contains(SubClassOf(A, C)));
        assertTrue(scas.contains(SubClassOf(C, A)));
        assertTrue(scas.contains(SubClassOf(B, C)));
        assertTrue(scas.contains(SubClassOf(C, B)));
    }
}
