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
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.change.AddAxiomData;
import org.semanticweb.owlapi.change.OWLOntologyChangeData;
import org.semanticweb.owlapi.change.OWLOntologyChangeRecord;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.2.0
 */
@SuppressWarnings("javadoc")
public class OWLOntologyChangeRecordTestCase {

    private OWLOntologyID mockOntologyID;
    private OWLOntologyChangeData<OWLAxiom> mockChangeData;
    private OWLAxiom mockAxiom;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        mockOntologyID = new OWLOntologyID();
        mockChangeData = mock(OWLOntologyChangeData.class);
        mockAxiom = mock(OWLAxiom.class);
    }

    @Test
    public void testEquals() {
        OWLOntologyChangeRecord<OWLAxiom> record1 = new OWLOntologyChangeRecord<OWLAxiom>(
                mockOntologyID, mockChangeData);
        OWLOntologyChangeRecord<OWLAxiom> record2 = new OWLOntologyChangeRecord<OWLAxiom>(
                mockOntologyID, mockChangeData);
        assertEquals(record1, record2);
    }

    @Test
    public void testGettersNotNull() {
        OWLOntologyChangeRecord<OWLAxiom> record = new OWLOntologyChangeRecord<OWLAxiom>(
                mockOntologyID, mockChangeData);
        assertNotNull(record.getOntologyID());
    }

    @Test
    public void testGetterEqual() {
        OWLOntologyChangeRecord<OWLAxiom> record = new OWLOntologyChangeRecord<OWLAxiom>(
                mockOntologyID, mockChangeData);
        assertEquals(mockOntologyID, record.getOntologyID());
        assertEquals(mockChangeData, record.getData());
    }

    @Test(expected = UnknownOWLOntologyException.class)
    public void testCreateOntologyChange() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyChangeRecord<OWLAxiom> changeRecord = new OWLOntologyChangeRecord<OWLAxiom>(
                mockOntologyID, mockChangeData);
        changeRecord.createOntologyChange(manager);
    }

    @Test
    public void testCreateOntologyChangeEquals()
            throws OWLOntologyCreationException {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.createOntology();
        OWLOntologyID ontologyID = ontology.getOntologyID();
        AddAxiomData addAxiomData = new AddAxiomData(mockAxiom);
        OWLOntologyChangeRecord<OWLAxiom> changeRecord = new OWLOntologyChangeRecord<OWLAxiom>(
                ontologyID, addAxiomData);
        OWLOntologyChange<OWLAxiom> change = changeRecord
                .createOntologyChange(manager);
        assertNotNull(change);
        assertEquals(change.getOntology().getOntologyID(), ontologyID);
        assertEquals(mockAxiom, change.getAxiom());
    }
}
