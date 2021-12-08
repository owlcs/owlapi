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
package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.change.AddOntologyAnnotationData;
import org.semanticweb.owlapi.formats.RioTurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.2.0
 */
class AddOntologyAnnotationDataTestCase extends TestBase {

    private final OWLAnnotation mockAnnotation = mock(OWLAnnotation.class);
    private final OWLOntology mockOntology = mock(OWLOntology.class);

    private AddOntologyAnnotationData createData() {
        return new AddOntologyAnnotationData(mockAnnotation);
    }

    @Test
    void testEquals() {
        AddOntologyAnnotationData data1 = createData();
        AddOntologyAnnotationData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    void testGettersReturnNotNull() {
        AddOntologyAnnotationData data = createData();
        assertNotNull(data.getAnnotation());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    void testGettersEquals() {
        AddOntologyAnnotationData data = createData();
        assertEquals(mockAnnotation, data.getAnnotation());
    }

    @Test
    void testCreateOntologyChange() {
        AddOntologyAnnotationData data = createData();
        AddOntologyAnnotation change = data.createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockAnnotation, change.getAnnotation());
    }

    @Test
    void testOntologyChangeSymmetry() {
        AddOntologyAnnotationData data = createData();
        AddOntologyAnnotation change = new AddOntologyAnnotation(mockOntology, mockAnnotation);
        assertEquals(change.getChangeData(), data);
    }

    @Test
    void testTurtleTriplesOrder() {
        OWLOntology o1 = loadFrom(TestFiles.ontFirst, new TurtleDocumentFormat());
        OWLOntology o2 = loadFrom(TestFiles.labelFirst, new TurtleDocumentFormat());
        assertFalse(o1.annotationsAsList().isEmpty());
        assertEquals(o1.annotationsAsList(), o2.annotationsAsList());
        OWLOntology o3 = loadFrom(TestFiles.ontFirst, new RioTurtleDocumentFormat());
        OWLOntology o4 = loadFrom(TestFiles.labelFirst, new RioTurtleDocumentFormat());
        assertEquals(o3.annotationsAsList(), o4.annotationsAsList());
    }
}
