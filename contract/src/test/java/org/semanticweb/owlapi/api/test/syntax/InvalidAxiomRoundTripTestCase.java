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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DataProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DifferentIndividuals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.DisjointObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentClasses;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentDataProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.EquivalentObjectProperties;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.NamedIndividual;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLNothing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.OWLThing;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.ObjectProperty;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.SameIndividual;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asUnorderedSet;

import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDisjointClassesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings({"javadoc", "null"})
public class InvalidAxiomRoundTripTestCase extends TestBase {

    private @Nonnull OWLOntology o;

    @Before
    public void setUpO() {
        o = getOWLOntology();
    }

    private static void assertCorrectResult(OWLAxiom wrongAxiom, OWLAxiom validAxiom,
                    OWLOntology reloaded) {
        assertNotNull(reloaded);
        assertTrue(reloaded.containsAxiom(validAxiom));
        assertFalse(reloaded.containsAxiom(wrongAxiom));
        assertEquals(1, reloaded.getLogicalAxiomCount());
    }

    private OWLOntology saveAndReload() throws OWLOntologyStorageException {
        return roundTrip(o, new FunctionalSyntaxDocumentFormat());
    }

    @Test
    public void shouldRoundTripInvalidDifferentIndividuals() throws OWLOntologyStorageException {
        // given
        OWLNamedIndividual e1 = NamedIndividual(IRI("urn:tes#", "t1"));
        OWLNamedIndividual e2 = NamedIndividual(IRI("urn:tes#", "t2"));
        OWLNamedIndividual e3 = NamedIndividual(IRI("urn:tes#", "t3"));
        // given
        OWLAxiom wrongAxiom = DifferentIndividuals(e1);
        OWLAxiom validAxiom = DifferentIndividuals(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    public void shouldRoundTripInvalidDisjointObjectProperties()
                    throws OWLOntologyStorageException {
        // given
        OWLObjectProperty e1 = ObjectProperty(IRI("urn:tes#", "t1"));
        OWLObjectProperty e2 = ObjectProperty(IRI("urn:tes#", "t2"));
        OWLObjectProperty e3 = ObjectProperty(IRI("urn:tes#", "t3"));
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
    public void shouldRoundTripInvalidDisjointClasses() throws Exception {
        // given
        OWLClass e1 = Class(IRI("urn:tes#", "t1"));
        OWLClass e2 = Class(IRI("urn:tes#", "t2"));
        OWLClass e3 = Class(IRI("urn:tes#", "t3"));
        // The implementation now checks for classes that only have a single
        // distinct element
        // Note: we cannot distinguish between a self-disjoint axiom and an
        // FSS/API etc created single element axiom.
        // but this is coding around a problem in the spec.
        checkSingletonDisjointFixup(e1, DisjointClasses(e1, e1));
        checkSingletonDisjointFixup(OWLThing(), DisjointClasses(OWLThing(), OWLThing()));
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
        assertEquals("should have two members", 2, classExpressions.size());
        assertTrue("contains e1", classExpressions.contains(e1));
        if (!e1.isOWLThing()) {
            assertTrue("contains Thing", classExpressions.contains(OWLThing()));
        } else {
            assertTrue("contains Nothing", classExpressions.contains(OWLNothing()));
        }
        assertTrue("is annotated", wrongAxiom.isAnnotated());
    }

    @Test
    public void shouldRoundTripInvalidDisjointDataProperties() throws OWLOntologyStorageException {
        // given
        OWLDataProperty e1 = DataProperty(IRI("urn:tes#", "t1"));
        OWLDataProperty e2 = DataProperty(IRI("urn:tes#", "t2"));
        OWLDataProperty e3 = DataProperty(IRI("urn:tes#", "t3"));
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
    public void shouldRoundTripInvalidSameIndividuals() throws OWLOntologyStorageException {
        // given
        OWLNamedIndividual e1 = NamedIndividual(IRI("urn:tes#", "t1"));
        OWLNamedIndividual e2 = NamedIndividual(IRI("urn:tes#", "t2"));
        OWLNamedIndividual e3 = NamedIndividual(IRI("urn:tes#", "t3"));
        // given
        OWLAxiom wrongAxiom = SameIndividual(e1);
        OWLAxiom validAxiom = SameIndividual(e2, e3);
        // when
        o.add(wrongAxiom, validAxiom);
        // then
        assertCorrectResult(wrongAxiom, validAxiom, saveAndReload());
    }

    @Test
    public void shouldRoundTripInvalidEquivalentClasses() throws OWLOntologyStorageException {
        // given
        OWLClass e1 = Class(IRI("urn:tes#", "t1"));
        OWLClass e2 = Class(IRI("urn:tes#", "t2"));
        OWLClass e3 = Class(IRI("urn:tes#", "t3"));
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
    public void shouldRoundTripInvalidEquivalentObjectProperties()
                    throws OWLOntologyStorageException {
        // given
        OWLObjectProperty e1 = ObjectProperty(IRI("urn:tes#", "t1"));
        OWLObjectProperty e2 = ObjectProperty(IRI("urn:tes#", "t2"));
        OWLObjectProperty e3 = ObjectProperty(IRI("urn:tes#", "t3"));
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
    public void shouldRoundTripInvalidEquivalentDataProperties()
                    throws OWLOntologyStorageException {
        // given
        OWLDataProperty e1 = DataProperty(IRI("urn:tes#", "t1"));
        OWLDataProperty e2 = DataProperty(IRI("urn:tes#", "t2"));
        OWLDataProperty e3 = DataProperty(IRI("urn:tes#", "t3"));
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
