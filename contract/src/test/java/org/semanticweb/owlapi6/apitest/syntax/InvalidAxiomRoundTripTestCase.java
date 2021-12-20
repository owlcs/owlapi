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
package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.model.OWLAxiom;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLClassExpression;
import org.semanticweb.owlapi6.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi6.model.OWLOntology;

class InvalidAxiomRoundTripTestCase extends TestBase {

    OWLOntology o;

    @BeforeEach
    void setUpO() {
        o = createAnon();
    }

    private static void assertCorrectResult(OWLAxiom wrongAxiom, OWLAxiom validAxiom,
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
        OWLAxiom wrongAxiom = DifferentIndividuals(INDIVIDUALS.IT1);
        OWLAxiom validAxiom = DifferentIndividuals(INDIVIDUALS.IT2, INDIVIDUALS.IT3);
        // when
        o.add(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    void shouldRoundTripInvalidDisjointObjectProperties() {
        // given
        OWLAxiom wrongAxiom = DisjointObjectProperties(OBJPROPS.OPT1);
        OWLAxiom validAxiom = DisjointObjectProperties(OBJPROPS.OPT2, OBJPROPS.OPT3);
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
        // The implementation now checks for classes that only have a single
        // distinct element
        // Note: we cannot distinguish between a self-disjoint axiom and an
        // FSS/API etc created single element axiom.
        // but this is coding around a problem in the spec.
        checkSingletonDisjointFixup(CLASSES.CT1, DisjointClasses(CLASSES.CT1, CLASSES.CT1));
        OWLDisjointClassesAxiom singleClassDisjointAxiom = DisjointClasses(CLASSES.CT1);
        checkSingletonDisjointFixup(CLASSES.CT1, singleClassDisjointAxiom);
        OWLAxiom validAxiom = DisjointClasses(CLASSES.CT2, CLASSES.CT3);
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
        OWLAxiom wrongAxiom = DisjointDataProperties(DATAPROPS.DPT1);
        OWLAxiom validAxiom = DisjointDataProperties(DATAPROPS.DPT2, DATAPROPS.DPT3);
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
        OWLAxiom wrongAxiom = SameIndividual(INDIVIDUALS.IT1);
        OWLAxiom validAxiom = SameIndividual(INDIVIDUALS.IT2, INDIVIDUALS.IT3);
        // when
        o.add(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    void shouldRoundTripInvalidEquivalentClasses() {
        // given
        OWLAxiom wrongAxiom = EquivalentClasses(CLASSES.CT1);
        OWLAxiom validAxiom = EquivalentClasses(CLASSES.CT2, CLASSES.CT3);
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
        OWLAxiom wrongAxiom = EquivalentObjectProperties(OBJPROPS.OPT1);
        OWLAxiom validAxiom = EquivalentObjectProperties(OBJPROPS.OPT2, OBJPROPS.OPT3);
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
        OWLAxiom wrongAxiom = EquivalentDataProperties(DATAPROPS.DPT1);
        OWLAxiom validAxiom = EquivalentDataProperties(DATAPROPS.DPT2, DATAPROPS.DPT3);
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
