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
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
public class OntologyMutationTestCase extends TestBase {

    @Test
    public void testAddAxiom() {
        OWLOntology ont = getOWLOntology("OntA");
        OWLAxiom ax = SubClassOf(Class(getIRI("A")), OWLThing());
        final List<OWLOntologyChange<?>> chgs = new ArrayList<OWLOntologyChange<?>>();
        ont.getOWLOntologyManager().addOntologyChangeListener(
                new OWLOntologyChangeListener() {

                    @Override
                    public void ontologiesChanged(
                            List<? extends OWLOntologyChange<?>> changes)
                            throws OWLException {
                        chgs.addAll(changes);
                    }
                });
        ont.getOWLOntologyManager().addAxiom(ont, ax);
        assertEquals(1, chgs.size());
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }

    @Test
    public void testAddAxioms() {
        OWLOntology ont = getOWLOntology("OntB");
        OWLAxiom ax = SubClassOf(Class(getIRI("A")), OWLThing());
        final List<OWLOntologyChange<?>> chgs = new ArrayList<OWLOntologyChange<?>>();
        ont.getOWLOntologyManager().addOntologyChangeListener(
                new OWLOntologyChangeListener() {

                    @Override
                    public void ontologiesChanged(
                            List<? extends OWLOntologyChange<?>> changes)
                            throws OWLException {
                        chgs.addAll(changes);
                    }
                });
        ont.getOWLOntologyManager().addAxioms(ont, Collections.singleton(ax));
        assertEquals(1, chgs.size());
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }

    @Test
    public void testApplyChange() {
        OWLOntology ont = getOWLOntology("OntC");
        OWLAxiom ax = SubClassOf(Class(getIRI("A")), OWLThing());
        final List<OWLOntologyChange<?>> chgs = new ArrayList<OWLOntologyChange<?>>();
        ont.getOWLOntologyManager().addOntologyChangeListener(
                new OWLOntologyChangeListener() {

                    @Override
                    public void ontologiesChanged(
                            List<? extends OWLOntologyChange<?>> changes)
                            throws OWLException {
                        chgs.addAll(changes);
                    }
                });
        ont.getOWLOntologyManager().applyChange(new AddAxiom(ont, ax));
        assertEquals(1, chgs.size());
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }

    @Test
    public void testApplyChanges() {
        OWLOntology ont = getOWLOntology("OntD");
        OWLAxiom ax = SubClassOf(Class(getIRI("A")), OWLThing());
        final List<OWLOntologyChange<?>> chgs = new ArrayList<OWLOntologyChange<?>>();
        ont.getOWLOntologyManager().addOntologyChangeListener(
                new OWLOntologyChangeListener() {

                    @Override
                    public void ontologiesChanged(
                            List<? extends OWLOntologyChange<?>> changes)
                            throws OWLException {
                        chgs.addAll(changes);
                    }
                });
        ont.getOWLOntologyManager().applyChanges(
                Arrays.asList(new AddAxiom(ont, ax)));
        assertEquals(1, chgs.size());
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }
}
