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
package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

class InvalidAxiomRoundTripTestCase extends TestBase {

    static final IRI t3 = iri("urn:tes#", "t3");
    static final IRI t2 = iri("urn:tes#", "t2");
    static final IRI t1 = iri("urn:tes#", "t1");
    OWLOntology o;

    @BeforeEach
    void setUpO() {
        o = createAnon();
    }

    static void assertCorrectResult(OWLAxiom wrongAxiom, OWLAxiom validAxiom,
        OWLOntology reloaded) {
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    private OWLOntology saveAndReload() {
        return roundTrip(o, new FunctionalSyntaxDocumentFormat());
    }

    @Test
    void shouldRoundTripInvalidDifferentIndividuals() {
        // given
        OWLNamedIndividual e1 = NamedIndividual(t1);
        OWLNamedIndividual e2 = NamedIndividual(t2);
        OWLNamedIndividual e3 = NamedIndividual(t3);
        // given
        OWLAxiom wrongAxiom = DifferentIndividuals(e1);
        OWLAxiom validAxiom = DifferentIndividuals(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    void shouldRoundTripInvalidDisjointObjectProperties() {
        // given
        OWLObjectProperty e1 = ObjectProperty(t1);
        OWLObjectProperty e2 = ObjectProperty(t2);
        OWLObjectProperty e3 = ObjectProperty(t3);
        // given
        OWLAxiom wrongAxiom = DisjointObjectProperties(e1);
        OWLAxiom validAxiom = DisjointObjectProperties(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    void shouldRoundTripInvalidDisjointClasses() {
        // given
        OWLClass e1 = Class(t1);
        OWLClass e2 = Class(t2);
        OWLClass e3 = Class(t3);
        // The implementation now checks for classes that only have a single
        // distinct element
        // Note: we cannot distinguish between a self-disjoint axiom and an
        // FSS/API etc created single element axiom.
        // but this is coding around a problem in the spec.
        checkSingletonDisjointFixup(e1, DisjointClasses(e1, e1));
        OWLDisjointClassesAxiom singleClassDisjointAxiom = DisjointClasses(e1);
        checkSingletonDisjointFixup(e1, singleClassDisjointAxiom);
        OWLAxiom validAxiom = DisjointClasses(e2, e3);
        // when
        o.add(singleClassDisjointAxiom, validAxiom);
        OWLOntology reloaded = roundTrip(o, new FunctionalSyntaxDocumentFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertTrue(reloaded.containsAxiom(singleClassDisjointAxiom));
        assertEquals(2, reloaded.getLogicalAxiomCount());
    }

    protected void checkSingletonDisjointFixup(OWLClass e1, OWLDisjointClassesAxiom wrongAxiom) {
        Set<OWLClassExpression> classExpressions = asUnorderedSet(wrongAxiom.classExpressions());
        assertEquals(2, classExpressions.size());
        assertTrue(classExpressions.contains(e1));
        if (!e1.isOWLThing()) {
            assertTrue(classExpressions.contains(OWLThing()));
        } else {
            assertTrue(classExpressions.contains(OWLNothing()));
        }
        assertTrue(wrongAxiom.isAnnotated());
    }

    @Test
    void shouldRoundTripInvalidDisjointDataProperties() {
        // given
        OWLDataProperty e1 = DataProperty(t1);
        OWLDataProperty e2 = DataProperty(t2);
        OWLDataProperty e3 = DataProperty(t3);
        // given
        OWLAxiom wrongAxiom = DisjointDataProperties(e1);
        OWLAxiom validAxiom = DisjointDataProperties(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    void shouldRoundTripInvalidSameIndividuals() {
        // given
        OWLNamedIndividual e1 = NamedIndividual(t1);
        OWLNamedIndividual e2 = NamedIndividual(t2);
        OWLNamedIndividual e3 = NamedIndividual(t3);
        // given
        OWLAxiom wrongAxiom = SameIndividual(e1);
        OWLAxiom validAxiom = SameIndividual(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    void shouldRoundTripInvalidEquivalentClasses() {
        // given
        OWLClass e1 = Class(t1);
        OWLClass e2 = Class(t2);
        OWLClass e3 = Class(t3);
        // given
        OWLAxiom wrongAxiom = EquivalentClasses(e1);
        OWLAxiom validAxiom = EquivalentClasses(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    void shouldRoundTripInvalidEquivalentObjectProperties() {
        // given
        OWLObjectProperty e1 = ObjectProperty(t1);
        OWLObjectProperty e2 = ObjectProperty(t2);
        OWLObjectProperty e3 = ObjectProperty(t3);
        // given
        OWLAxiom wrongAxiom = EquivalentObjectProperties(e1);
        OWLAxiom validAxiom = EquivalentObjectProperties(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    void shouldRoundTripInvalidEquivalentDataProperties() {
        // given
        OWLDataProperty e1 = DataProperty(t1);
        OWLDataProperty e2 = DataProperty(t2);
        OWLDataProperty e3 = DataProperty(t3);
        // given
        OWLAxiom wrongAxiom = EquivalentDataProperties(e1);
        OWLAxiom validAxiom = EquivalentDataProperties(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }
}
