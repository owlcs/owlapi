/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.api.test.ontology;

import static org.junit.Assert.*;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractOWLAPITestCase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management
 *         Group, Date: 07-Sep-2008
 */
@SuppressWarnings("javadoc")
public class OntologyURITestCase extends AbstractOWLAPITestCase {

    @Test
    public void testOntologyID() {
        IRI iriA = IRI("http://www.another.com/ont");
        IRI iriB = IRI("http://www.another.com/ont/version");
        OWLOntologyID ontIDBoth = new OWLOntologyID(iriA, iriB);
        OWLOntologyID ontIDBoth2 = new OWLOntologyID(iriA, iriB);
        assertEquals(ontIDBoth, ontIDBoth2);
        OWLOntologyID ontIDURIOnly = new OWLOntologyID(iriA);
        assertFalse(ontIDBoth.equals(ontIDURIOnly));
        OWLOntologyID ontIDNoneA = new OWLOntologyID();
        OWLOntologyID ontIDNoneB = new OWLOntologyID();
        assertFalse(ontIDNoneA.equals(ontIDNoneB));
    }

    @Test
    public void testOntologyURI() throws Exception {
        IRI iri = IRI("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(iri);
        assertEquals(ont.getOntologyID().getOntologyIRI(), iri);
        assertTrue(getManager().contains(iri));
        assertTrue(getManager().getOntologies().contains(ont));
        OWLOntologyID ontID = new OWLOntologyID(iri);
        assertEquals(ont.getOntologyID(), ontID);
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testDuplicateOntologyURI() throws Exception {
        IRI uri = IRI("http://www.another.com/ont");
        getManager().createOntology(uri);
        getManager().createOntology(uri);
    }

    @Test
    public void testSetOntologyURI() throws Exception {
        IRI iri = IRI("http://www.another.com/ont");
        OWLOntology ont = getManager().createOntology(iri);
        IRI newIRI = IRI("http://www.another.com/newont");
        SetOntologyID sou = new SetOntologyID(ont, new OWLOntologyID(newIRI));
        getManager().applyChange(sou);
        assertFalse(getManager().contains(iri));
        assertTrue(getManager().contains(newIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), newIRI);
    }

    @Test
    public void testVersionURI() throws Exception {
        IRI ontIRI = IRI("http://www.another.com/ont");
        IRI verIRI = IRI("http://www.another.com/ont/versions/1.0.0");
        OWLOntology ont = getManager().createOntology(
                new OWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI(), verIRI);
    }

    @Test
    public void testNullVersionURI() throws Exception {
        IRI ontIRI = IRI("http://www.another.com/ont");
        IRI verIRI = null;
        OWLOntology ont = getManager().createOntology(
                new OWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI(), verIRI);
    }
}
