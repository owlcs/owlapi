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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
class OntologyURITestCase extends TestBase {

    private static final String ANOTHER_COM_ONT = "http://www.another.com/ont";

    protected IRI nextOnt() {
        return IRI.getNextDocumentIRI(ANOTHER_COM_ONT);
    }

    static final IRI version = iri(ANOTHER_COM_ONT + "/", "version");
    static final IRI onto = iri("http://www.another.com/", "ont");

    @Test
    void testNamedOntologyToString() {
        IRI ontIRI = iri("http://owlapi.sourceforge.net/", "ont");
        OWLOntology ont = create(ontIRI);
        String expected = "Ontology(" + ont.getOntologyID() + ") [Axioms: " + ont.getAxiomCount()
            + " Logical Axioms: " + ont.getLogicalAxiomCount() + "] First 20 axioms: {}";
        assertEquals(expected, ont.toString());
    }

    @Test
    void testOntologyID() {
        OWLOntologyID ontIDBoth = new OWLOntologyID(optional(onto), optional(version));
        OWLOntologyID ontIDBoth2 = new OWLOntologyID(optional(onto), optional(version));
        assertEquals(ontIDBoth, ontIDBoth2);
        OWLOntologyID ontIDURIOnly = new OWLOntologyID(optional(onto), emptyOptional(IRI.class));
        assertFalse(ontIDBoth.equals(ontIDURIOnly));
        OWLOntologyID ontIDNoneA = new OWLOntologyID();
        OWLOntologyID ontIDNoneB = new OWLOntologyID();
        assertFalse(ontIDNoneA.equals(ontIDNoneB));
    }

    @Test
    void testOntologyURI() {
        OWLOntology ont = create(onto);
        assertEquals(onto, ont.getOntologyID().getOntologyIRI().get());
        assertTrue(m.contains(onto));
        assertTrue(contains(m.ontologies(), ont));
        OWLOntologyID ontID = new OWLOntologyID(optional(onto), emptyOptional(IRI.class));
        assertEquals(ont.getOntologyID(), ontID);
    }

    @Test
    void testDuplicateOntologyURI() {
        IRI uri = nextOnt();
        create(uri);
        assertThrowsWithCause(OWLRuntimeException.class, OWLOntologyAlreadyExistsException.class,
            () -> create(uri));
    }

    @Test
    void testSetOntologyURI() {
        IRI iri = nextOnt();
        OWLOntology ont = create(iri);
        IRI newIRI = IRI.getNextDocumentIRI("http://www.another.com/newont");
        SetOntologyID sou =
            new SetOntologyID(ont, new OWLOntologyID(optional(newIRI), emptyOptional(IRI.class)));
        ont.applyChange(sou);
        assertFalse(m.contains(iri));
        assertTrue(m.contains(newIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), newIRI);
    }

    @Test
    void testVersionURI() {
        IRI ontIRI = nextOnt();
        IRI verIRI = IRI.getNextDocumentIRI("http://www.another.com/ont/versions/1.0.0");
        OWLOntology ont = create(new OWLOntologyID(optional(ontIRI), optional(verIRI)));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI().get(), verIRI);
    }

    @Test
    void testNullVersionURI() {
        IRI ontIRI = nextOnt();
        IRI verIRI = null;
        OWLOntology ont = create(new OWLOntologyID(optional(ontIRI), optional(verIRI)));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), ontIRI);
        assertFalse(ont.getOntologyID().getVersionIRI().isPresent());
    }
}
