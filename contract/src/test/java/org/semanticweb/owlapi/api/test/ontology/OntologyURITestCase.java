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
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.contains;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.*;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
@SuppressWarnings("javadoc")
public class OntologyURITestCase extends TestBase {

    @Test
    public void testOntologyID() {
        IRI iriA = IRI("http://www.another.com/ont");
        IRI iriB = IRI("http://www.another.com/ont/version");
        OWLOntologyID ontIDBoth = new OWLOntologyID(optional(iriA),
            optional(iriB));
        OWLOntologyID ontIDBoth2 = new OWLOntologyID(optional(iriA),
            optional(iriB));
        assertEquals(ontIDBoth, ontIDBoth2);
        OWLOntologyID ontIDURIOnly = new OWLOntologyID(optional(iriA),
            emptyOptional(IRI.class));
        assertFalse(ontIDBoth.equals(ontIDURIOnly));
        OWLOntologyID ontIDNoneA = new OWLOntologyID();
        OWLOntologyID ontIDNoneB = new OWLOntologyID();
        assertFalse(ontIDNoneA.equals(ontIDNoneB));
    }

    @Test
    public void testOntologyURI() throws OWLOntologyCreationException {
        IRI iri = IRI("http://www.another.com/ont");
        OWLOntology ont = getOWLOntology(iri);
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), iri);
        assertTrue(m.contains(iri));
        assertTrue(contains(m.ontologies(), ont));
        OWLOntologyID ontID = new OWLOntologyID(optional(iri),
            emptyOptional(IRI.class));
        assertEquals(ont.getOntologyID(), ontID);
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testDuplicateOntologyURI() throws OWLOntologyCreationException {
        IRI uri = IRI.getNextDocumentIRI("http://www.another.com/ont");
        getOWLOntology(uri);
        getOWLOntology(uri);
    }

    @Test
    public void testSetOntologyURI() throws OWLOntologyCreationException {
        IRI iri = IRI.getNextDocumentIRI("http://www.another.com/ont");
        OWLOntology ont = getOWLOntology(iri);
        IRI newIRI = IRI.getNextDocumentIRI("http://www.another.com/newont");
        SetOntologyID sou = new SetOntologyID(ont,
            new OWLOntologyID(optional(newIRI), emptyOptional(IRI.class)));
        ont.applyChange(sou);
        assertFalse(m.contains(iri));
        assertTrue(m.contains(newIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), newIRI);
    }

    @Test
    public void testVersionURI() throws OWLOntologyCreationException {
        IRI ontIRI = IRI.getNextDocumentIRI("http://www.another.com/ont");
        IRI verIRI = IRI
            .getNextDocumentIRI("http://www.another.com/ont/versions/1.0.0");
        OWLOntology ont = getOWLOntology(
            new OWLOntologyID(optional(ontIRI), optional(verIRI)));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI().get(), verIRI);
    }

    @Test
    public void testNullVersionURI() throws OWLOntologyCreationException {
        IRI ontIRI = IRI.getNextDocumentIRI("http://www.another.com/ont");
        IRI verIRI = null;
        OWLOntology ont = getOWLOntology(
            new OWLOntologyID(optional(ontIRI), optional(verIRI)));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), ontIRI);
        assertFalse(ont.getOntologyID().getVersionIRI().isPresent());
    }
}
