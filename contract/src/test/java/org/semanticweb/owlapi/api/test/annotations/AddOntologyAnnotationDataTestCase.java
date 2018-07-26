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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
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
@SuppressWarnings({"javadoc"})
public class AddOntologyAnnotationDataTestCase extends TestBase {

    private final OWLAnnotation mockAnnotation = mock(OWLAnnotation.class);
    private final OWLOntology mockOntology = mock(OWLOntology.class);

    private AddOntologyAnnotationData createData() {
        return new AddOntologyAnnotationData(mockAnnotation);
    }

    @Test
    public void testEquals() {
        AddOntologyAnnotationData data1 = createData();
        AddOntologyAnnotationData data2 = createData();
        assertEquals(data1, data2);
        assertEquals(data1.hashCode(), data2.hashCode());
    }

    @Test
    public void testGettersReturnNotNull() {
        AddOntologyAnnotationData data = createData();
        assertNotNull(data.getAnnotation());
        assertNotNull(data.createOntologyChange(mockOntology));
    }

    @Test
    public void testGettersEquals() {
        AddOntologyAnnotationData data = createData();
        assertEquals(mockAnnotation, data.getAnnotation());
    }

    @Test
    public void testCreateOntologyChange() {
        AddOntologyAnnotationData data = createData();
        AddOntologyAnnotation change = data.createOntologyChange(mockOntology);
        assertEquals(mockOntology, change.getOntology());
        assertEquals(mockAnnotation, change.getAnnotation());
    }

    @Test
    public void testOntologyChangeSymmetry() {
        AddOntologyAnnotationData data = createData();
        AddOntologyAnnotation change = new AddOntologyAnnotation(mockOntology, mockAnnotation);
        assertEquals(change.getChangeData(), data);
    }

    @Test
    public void testTurtleTriplesOrder() {
        String ontFirst = "<http://example.org/ont> a <http://www.w3.org/2002/07/owl#Ontology> ;"
                        + " <http://www.w3.org/2000/01/rdf-schema#label> \"An ontology\" . ";
        String labelFirst =
                        "<http://example.org/ont> <http://www.w3.org/2000/01/rdf-schema#label> \"An ontology\" ;"
                                        + " a <http://www.w3.org/2002/07/owl#Ontology> . ";
        OWLOntology ontology1 = loadOntologyFromString(ontFirst, new TurtleDocumentFormat());
        OWLOntology ontology2 = loadOntologyFromString(labelFirst, new TurtleDocumentFormat());
        assertEquals("Should both have a label annotation", asSet(ontology1.annotations()),
                        asSet(ontology2.annotations()));
        OWLOntology ontology3 = loadOntologyFromString(ontFirst, new RioTurtleDocumentFormat());
        OWLOntology ontology4 = loadOntologyFromString(labelFirst, new RioTurtleDocumentFormat());
        assertEquals("Should both have a label annotation", asSet(ontology3.annotations()),
                        asSet(ontology4.annotations()));
    }
}
