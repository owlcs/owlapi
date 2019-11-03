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
package org.semanticweb.owlapi6.apitest.syntax.rdfxml;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StreamDocumentSource;
import org.semanticweb.owlapi6.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParser;

/**
 * Tests the loading of a single ontology multiple times, using the same ontologyIRI in the
 * {@link OWLOntologyID} as that used in the actual ontology that is being imported.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class MultipleOntologyLoadsTestCase extends TestBase {

    private static final IRI CREATEV1 = IRI("http://test.example.org/ontology/0139/version:1", "");
    private static final IRI CREATEV2 = IRI("http://test.example.org/ontology/0139/version:2", "");
    private static final IRI CREATE0139 = IRI("http://test.example.org/ontology/0139", "");

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadChangeIRI() throws Exception {
        // given
        OWLOntologyDocumentSource initialDocumentSource = getDocumentSource();
        OWLOntologyID expected = df.getOWLOntologyID(CREATE0139, CREATEV2);
        OWLOntologyID initialUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV2);
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        parseOnto(initialDocumentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV2);
        // when
        try {
            m.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            // then
            assertEquals(expected, e.getOntologyID());
            throw e;
        }
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadNoChange() throws Exception {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID expected = df.getOWLOntologyID(CREATE0139, CREATEV1);
        OWLOntologyID initialUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV1);
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV1);
        // when
        try {
            m.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            // then
            assertEquals(expected, e.getOntologyID());
            throw e;
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs() {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV1);
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = getOWLOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI().get());
        assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI().get());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime() {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology initialOntology = getAnonymousOWLOntology();
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = getOWLOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI().get());
        assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI().get());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime() {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139);
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntology secondOntology = getOWLOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI().get());
        assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI().get());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() {
        // given
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV2);
        // when
        OWLOntology secondOntology = getOWLOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI().get());
    }

    @Test
    public void testSingleVersionLoadNoChange() {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID = df.getOWLOntologyID(CREATE0139, CREATEV1);
        // when
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        // then
        assertEquals(CREATE0139, initialOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV1, initialOntology.getOntologyID().getVersionIRI().get());
    }

    private void parseOnto(OWLOntologyDocumentSource initialDocumentSource,
        OWLOntology initialOntology) {
        initialDocumentSource.acceptParser(new RDFXMLParser(), initialOntology, config);
    }

    private OWLOntologyDocumentSource getDocumentSource() {
        return new StreamDocumentSource(
            getClass().getResourceAsStream("/owlapi/multipleOntologyLoadsTest.rdf"));
    }
}
