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
package org.semanticweb.owlapi.apitest.ontology;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.utilities.OWLAPIStreamUtils.asSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.documents.StringDocumentSource;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.parameters.OntologyCopy;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.0.0
 */
class MoveOntologyTestCase extends TestBase {

    private OWLOntology root;
    private OWLOntology imported;

    @BeforeEach
    void setUp() {
        create(IRIS.iriTest);
        IRI importedIRI = iri("urn:test#", "imported");
        imported = create(importedIRI);
        root = create(iri("urn:test#", "root"));
        root.applyChange(new AddImport(root, ImportsDeclaration(importedIRI)));
        m.setOntologyFormat(root, new RDFXMLDocumentFormat());
        m.setOntologyFormat(imported, new RDFXMLDocumentFormat());
        m1 = setupConcurrentManager();
    }

    @Test
    void testMove() {
        OWLOntology o =
            loadFrom(new StringDocumentSource(TestFiles.moveTest, new RDFXMLDocumentFormat()), m);
        OWLOntology copy = copy(o, OntologyCopy.MOVE);
        assertSame(o, copy);
        assertEquals(m1, copy.getOWLOntologyManager());
        assertFalse(m.contains(o));
        assertTrue(m1.contains(copy));
        assertEquals(asSet(o.annotations()), asSet(copy.annotations()));
        assertNull(m.getOntologyFormat(o));
    }

    @Test
    void testShallow() {
        OWLOntology o =
            loadFrom(new StringDocumentSource(TestFiles.moveTest, new RDFXMLDocumentFormat()), m);
        OWLOntology copy = copy(o, OntologyCopy.SHALLOW_COPY);
        assertEquals(m1, copy.getOWLOntologyManager());
        assertTrue(m.contains(o));
        assertTrue(m1.contains(copy));
        assertNotNull(m.getOntologyFormat(o));
        assertEquals(asSet(o.annotations()), asSet(copy.annotations()));
        assertEquals(asSet(o.importsDeclarations()), asSet(copy.importsDeclarations()));
    }

    @Test
    void testDeep() {
        OWLOntology o =
            loadFrom(new StringDocumentSource(TestFiles.moveTest, new RDFXMLDocumentFormat()), m);
        OWLOntology copy = copy(o, OntologyCopy.DEEP_COPY);
        assertEquals(m1, copy.getOWLOntologyManager());
        assertTrue(m.contains(o));
        assertTrue(m1.contains(copy));
        assertNotNull(m.getOntologyFormat(o));
        assertNotNull(m1.getOntologyFormat(o));
        assertEquals(asSet(o.annotations()), asSet(copy.annotations()));
        assertEquals(asSet(o.importsDeclarations()), asSet(copy.importsDeclarations()));
    }

    @Test
    void testMoveImportsClosure() {
        assertTrue(m.contains(root));
        assertTrue(m.contains(imported));
        OWLOntology copy = copy(root, OntologyCopy.MOVE_ONTOLOGY_CLOSURE);
        assertSame(root, copy);
        assertEquals(m1, copy.getOWLOntologyManager());
        assertEquals(m1, imported.getOWLOntologyManager());
        assertFalse(m.contains(root));
        assertFalse(m.contains(imported));
        assertTrue(m1.contains(copy));
        assertTrue(m1.contains(imported));
        assertEquals(asSet(root.annotations()), asSet(copy.annotations()));
        assertNull(m.getOntologyFormat(root));
        assertNull(m.getOntologyFormat(imported));
    }

    @Test
    void testShallowImportsClosure() {
        assertTrue(m.contains(root));
        assertTrue(m.contains(imported));
        OWLOntology copy = copy(root, OntologyCopy.SHALLOW_COPY_ONTOLOGY_CLOSURE);
        assertEquals(2, copy.importsClosure().count());
        copy.importsClosure().forEach(imp -> assertEquals(m1, imp.getOWLOntologyManager()));
        assertTrue(m.contains(root));
        assertTrue(m.contains(imported));
        assertTrue(m1.contains(copy));
        assertTrue(m1.contains(imported.getOntologyID().getOntologyIRI().orElse(null)));
        assertNotNull(m.getOntologyFormat(root));
        assertNotNull(m.getOntologyFormat(imported));
        assertEquals(asSet(root.annotations()), asSet(copy.annotations()));
        assertEquals(asSet(root.importsDeclarations()), asSet(copy.importsDeclarations()));
    }

    @Test
    void testDeepImportsClosure() {
        assertTrue(m.contains(root));
        assertTrue(m.contains(imported));
        OWLOntology copy = copy(root, OntologyCopy.DEEP_COPY_ONTOLOGY_CLOSURE);
        assertEquals(2, copy.importsClosure().count());
        copy.importsClosure()
            .forEach(importOntology -> assertEquals(m1, importOntology.getOWLOntologyManager()));
        assertTrue(m.contains(root));
        assertTrue(m.contains(imported));
        assertTrue(m1.contains(copy));
        assertTrue(m1.contains(imported.getOntologyID().getOntologyIRI().orElse(null)));
        assertNotNull(m.getOntologyFormat(root));
        assertNotNull(m.getOntologyFormat(imported));
        assertNotNull(m1.getOntologyFormat(root));
        OWLOntology ont = m1.getOntology(imported.getOntologyID().getOntologyIRI().orElse(null));
        assert ont != null;
        assertNotNull(m1.getOntologyFormat(ont));
        assertEquals(asSet(root.annotations()), asSet(copy.annotations()));
        assertEquals(asSet(root.importsDeclarations()), asSet(copy.importsDeclarations()));
    }

    protected OWLOntology copy(OWLOntology o, OntologyCopy copyParam) {
        try {
            return m1.copyOntology(o, copyParam);
        } catch (OWLOntologyCreationException ex) {
            throw new OWLRuntimeException(ex);
        }
    }
}
