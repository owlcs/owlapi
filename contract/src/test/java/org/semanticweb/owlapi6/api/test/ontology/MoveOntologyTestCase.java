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
package org.semanticweb.owlapi6.api.test.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.asSet;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.api.test.TestFiles;
import org.semanticweb.owlapi6.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.io.StringDocumentSource;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.parameters.OntologyCopy;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class MoveOntologyTestCase extends TestBase {

    @Before
    public void setUp() throws OWLOntologyCreationException {
        m.createOntology(df.getIRI("urn:test#", "test"));
    }

    @Test
    public void testMove() throws OWLOntologyCreationException {
        OWLOntology o = m
            .loadOntologyFromOntologyDocument(new StringDocumentSource(TestFiles.moveTest, new RDFXMLDocumentFormat()));
        OWLOntology copy = m1.copyOntology(o, OntologyCopy.MOVE);
        assertSame(o, copy);
        assertEquals(m1, copy.getOWLOntologyManager());
        assertFalse(m.contains(o));
        assertTrue(m1.contains(copy));
        assertEquals(asSet(o.annotations()), asSet(copy.annotations()));
        assertNull(m.getOntologyFormat(o));
    }

    @Test
    public void testShallow() throws OWLOntologyCreationException {
        OWLOntology o = m
            .loadOntologyFromOntologyDocument(new StringDocumentSource(TestFiles.moveTest, new RDFXMLDocumentFormat()));
        OWLOntology copy = m1.copyOntology(o, OntologyCopy.SHALLOW);
        assertEquals(m1, copy.getOWLOntologyManager());
        assertTrue(m.contains(o));
        assertTrue(m1.contains(copy));
        assertNotNull(m.getOntologyFormat(o));
        assertEquals(asSet(o.annotations()), asSet(copy.annotations()));
        assertEquals(asSet(o.importsDeclarations()), asSet(copy.importsDeclarations()));
    }

    @Test
    public void testDeep() throws OWLOntologyCreationException {
        OWLOntology o = m
            .loadOntologyFromOntologyDocument(new StringDocumentSource(TestFiles.moveTest, new RDFXMLDocumentFormat()));
        OWLOntology copy = m1.copyOntology(o, OntologyCopy.DEEP);
        assertEquals(m1, copy.getOWLOntologyManager());
        assertTrue(m.contains(o));
        assertTrue(m1.contains(copy));
        assertNotNull(m.getOntologyFormat(o));
        assertNotNull(m1.getOntologyFormat(o));
        assertEquals(asSet(o.annotations()), asSet(copy.annotations()));
        assertEquals(asSet(o.importsDeclarations()), asSet(copy.importsDeclarations()));
    }
}