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
package org.semanticweb.owlapi6.apitest.ontology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi6.utilities.OWLAPIStreamUtils.contains;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.model.SetOntologyID;

/**
 * @author Matthew Horridge, The University Of Manchester, Information Management Group
 * @since 2.2.0
 */
public class OntologyURITestCase extends TestBase {

    private static final IRI version = IRI("http://www.another.com/ont/", "version");
    private static final IRI onto = IRI("http://www.another.com/", "ont");

    @Test
    public void testNamedOntologyToString() throws OWLOntologyCreationException {
        IRI ontIRI = IRI("http://owlapi.sourceforge.net/", "ont");
        OWLOntology ont = m.createOntology(ontIRI);
        String s = ont.toString();
        String expected = "Ontology(" + ont.getOntologyID() + ") [Axioms: " + ont.getAxiomCount()
            + " Logical Axioms: " + ont.getLogicalAxiomCount() + "] First 20 axioms: {}";
        assertEquals(expected, s);
    }

    @Test
    public void testOntologyID() {
        OWLOntologyID ontIDBoth = df.getOWLOntologyID(onto, version);
        OWLOntologyID ontIDBoth2 = df.getOWLOntologyID(onto, version);
        assertEquals(ontIDBoth, ontIDBoth2);
        OWLOntologyID ontIDURIOnly = df.getOWLOntologyID(onto);
        assertFalse(ontIDBoth.equals(ontIDURIOnly));
        OWLOntologyID ontIDNoneA = df.getOWLOntologyID();
        OWLOntologyID ontIDNoneB = df.getOWLOntologyID();
        assertFalse(ontIDNoneA.equals(ontIDNoneB));
    }

    @Test
    public void testOntologyURI() {
        OWLOntology ont = getOWLOntology(onto);
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), onto);
        assertTrue(m.contains(onto));
        assertTrue(contains(m.ontologies(), ont));
        OWLOntologyID ontID = df.getOWLOntologyID(onto);
        assertEquals(ont.getOntologyID(), ontID);
    }

    @Test
    public void testSetOntologyURI() {
        IRI iri = nextOnt();
        OWLOntology ont = getOWLOntology(iri);
        IRI newIRI = df.getNextDocumentIRI("http://www.another.com/newont");
        SetOntologyID sou = new SetOntologyID(ont, df.getOWLOntologyID(newIRI));
        ont.applyChange(sou);
        assertFalse(m.contains(iri));
        assertTrue(m.contains(newIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), newIRI);
    }

    @Test
    public void testVersionURI() {
        IRI ontIRI = nextOnt();
        IRI verIRI = df.getNextDocumentIRI("http://www.another.com/ont/versions/1.0.0");
        OWLOntology ont = getOWLOntology(df.getOWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), ontIRI);
        assertEquals(ont.getOntologyID().getVersionIRI().get(), verIRI);
    }

    protected IRI nextOnt() {
        return df.getNextDocumentIRI("http://www.another.com/ont");
    }

    @Test
    public void testNullVersionURI() {
        IRI ontIRI = nextOnt();
        IRI verIRI = null;
        OWLOntology ont = getOWLOntology(df.getOWLOntologyID(ontIRI, verIRI));
        assertEquals(ont.getOntologyID().getOntologyIRI().get(), ontIRI);
        assertFalse(ont.getOntologyID().getVersionIRI().isPresent());
    }
}
