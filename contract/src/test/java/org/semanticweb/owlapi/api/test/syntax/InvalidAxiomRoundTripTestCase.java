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

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.*;

@SuppressWarnings({ "javadoc", "null" })
public class InvalidAxiomRoundTripTestCase extends TestBase {

    @Nonnull
    private OWLOntology o;

    @Before
    public void setUpO() {
        o = getOWLOntology();
    }

    private static void assertCorrectResult(@Nonnull OWLAxiom wrongAxiom,
        @Nonnull OWLAxiom validAxiom, @Nonnull OWLOntology reloaded) {
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    private void addAxioms(@Nonnull OWLAxiom... axioms) {
        for (OWLAxiom ax : axioms) {
            o.getOWLOntologyManager().addAxiom(o, ax);
        }
    }

    private OWLOntology saveAndReload() throws OWLOntologyStorageException,
        OWLOntologyCreationException {
        return roundTrip(o, new FunctionalSyntaxDocumentFormat());
    }

    @Test
    public void shouldRoundTripInvalidDifferentIndividuals()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLNamedIndividual e1 = NamedIndividual(IRI("urn:test1"));
        OWLNamedIndividual e2 = NamedIndividual(IRI("urn:test2"));
        OWLNamedIndividual e3 = NamedIndividual(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DifferentIndividuals(e1);
        OWLAxiom validAxiom = DifferentIndividuals(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    public void shouldRoundTripInvalidDisjointObjectProperties()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLObjectProperty e1 = ObjectProperty(IRI("urn:test1"));
        OWLObjectProperty e2 = ObjectProperty(IRI("urn:test2"));
        OWLObjectProperty e3 = ObjectProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DisjointObjectProperties(e1);
        OWLAxiom validAxiom = DisjointObjectProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    public void shouldRoundTripInvalidDisjointClasses() throws Exception {
        // given
        OWLClass e1 = Class(IRI("urn:test1"));
        OWLClass e2 = Class(IRI("urn:test2"));
        OWLClass e3 = Class(IRI("urn:test3"));
        // The implementation now checks for classes that only have a single
        // distinct element
        // Note: we cannot distinguish between a self-disjoint axiom and an
        // FSS/API etc created single element axiom.
        // but this is coding around a problem in the spec.
        checkSingletonDisjointFixup(e1, DisjointClasses(e1, e1));
        checkSingletonDisjointFixup(OWLThing(), DisjointClasses(OWLThing(),
        OWLThing()));
        OWLDisjointClassesAxiom singleClassDisjointAxiom = DisjointClasses(e1);
        checkSingletonDisjointFixup(e1, singleClassDisjointAxiom);
        OWLAxiom validAxiom = DisjointClasses(e2, e3);
        // when
        addAxioms(singleClassDisjointAxiom, validAxiom);
        OWLOntology reloaded = roundTrip(o,
        new FunctionalSyntaxDocumentFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertTrue(reloaded.containsAxiom(singleClassDisjointAxiom));
        assertEquals(2, reloaded.getLogicalAxiomCount());
    }

    protected void checkSingletonDisjointFixup(OWLClass e1,
        OWLDisjointClassesAxiom wrongAxiom) {
        Set<OWLClassExpression> classExpressions = asSet(wrongAxiom
        .classExpressions());
        assertEquals("should have two members", 2, classExpressions.size());
        assertTrue("contains e1", classExpressions.contains(e1));
        if (!e1.isOWLThing()) {
            assertTrue("contains Thing", classExpressions.contains(OWLThing()));
        } else {
            assertTrue("contains Nothing", classExpressions.contains(
            OWLNothing()));
        }
        assertTrue("is annotated", wrongAxiom.isAnnotated());
    }

    @Test
    public void shouldRoundTripInvalidDisjointDataProperties()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLDataProperty e1 = DataProperty(IRI("urn:test1"));
        OWLDataProperty e2 = DataProperty(IRI("urn:test2"));
        OWLDataProperty e3 = DataProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DisjointDataProperties(e1);
        OWLAxiom validAxiom = DisjointDataProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    public void shouldRoundTripInvalidSameIndividuals()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLNamedIndividual e1 = NamedIndividual(IRI("urn:test1"));
        OWLNamedIndividual e2 = NamedIndividual(IRI("urn:test2"));
        OWLNamedIndividual e3 = NamedIndividual(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = SameIndividual(e1);
        OWLAxiom validAxiom = SameIndividual(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    public void shouldRoundTripInvalidEquivalentClasses()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLClass e1 = Class(IRI("urn:test1"));
        OWLClass e2 = Class(IRI("urn:test2"));
        OWLClass e3 = Class(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = EquivalentClasses(e1);
        OWLAxiom validAxiom = EquivalentClasses(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    public void shouldRoundTripInvalidEquivalentObjectProperties()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLObjectProperty e1 = ObjectProperty(IRI("urn:test1"));
        OWLObjectProperty e2 = ObjectProperty(IRI("urn:test2"));
        OWLObjectProperty e3 = ObjectProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = EquivalentObjectProperties(e1);
        OWLAxiom validAxiom = EquivalentObjectProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    @Test
    public void shouldRoundTripInvalidEquivalentDataProperties()
        throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLDataProperty e1 = DataProperty(IRI("urn:test1"));
        OWLDataProperty e2 = DataProperty(IRI("urn:test2"));
        OWLDataProperty e3 = DataProperty(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = EquivalentDataProperties(e1);
        OWLAxiom validAxiom = EquivalentDataProperties(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = saveAndReload();
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }
}
