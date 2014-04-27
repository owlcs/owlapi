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

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class InvalidAxiomRoundTripTestCase extends TestBase {

    @SuppressWarnings("null")
    @Nonnull
    private OWLOntology o;

    @Before
    public void setUpO() throws OWLOntologyCreationException {
        o = m.createOntology();
    }

    private static void assertCorrectResult(@Nonnull OWLAxiom wrongAxiom,
            @Nonnull OWLAxiom validAxiom, @Nonnull OWLOntology reloaded) {
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }

    private void addAxioms(@Nonnull OWLAxiom... axioms) {
        for (OWLAxiom ax : axioms) {
            assert ax != null;
            o.getOWLOntologyManager().addAxiom(o, ax);
        }
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
        assertCorrectResult(wrongAxiom, validAxiom,
                roundTrip(o, new OWLFunctionalSyntaxOntologyFormat()));
    }

    @Test
    public void shouldRoundTripInvalidDisjointClasses()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        // given
        OWLClass e1 = Class(IRI("urn:test1"));
        OWLClass e2 = Class(IRI("urn:test2"));
        OWLClass e3 = Class(IRI("urn:test3"));
        // given
        OWLAxiom wrongAxiom = DisjointClasses(e1);
        OWLAxiom validAxiom = DisjointClasses(e2, e3);
        // when
        addAxioms(wrongAxiom, validAxiom);
        OWLOntology reloaded = roundTrip(o,
                new OWLFunctionalSyntaxOntologyFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
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
        OWLOntology reloaded = roundTrip(o,
                new OWLFunctionalSyntaxOntologyFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
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
        OWLOntology reloaded = roundTrip(o,
                new OWLFunctionalSyntaxOntologyFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
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
        assertCorrectResult(wrongAxiom, validAxiom,
                roundTrip(o, new OWLFunctionalSyntaxOntologyFormat()));
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
        OWLOntology reloaded = roundTrip(o,
                new OWLFunctionalSyntaxOntologyFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
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
        OWLOntology reloaded = roundTrip(o,
                new OWLFunctionalSyntaxOntologyFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
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
        OWLOntology reloaded = roundTrip(o,
                new OWLFunctionalSyntaxOntologyFormat());
        // then
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertTrue(reloaded.getAxioms().size() == 1);
    }
}
