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
package org.semanticweb.owlapi.api.test.syntax.rdfxml;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;

/**
 * Tests the loading of a single ontology multiple times, using the same ontologyIRI in the
 * {@link OWLOntologyID} as that used in the actual ontology that is being imported.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings({"javadoc"})
public class MultipleOntologyLoadsTestCase extends TestBase {

    private static final IRI CREATEV1 = IRI("http://test.example.org/ontology/0139/version:1", "");
    private static final IRI CREATEV2 = IRI("http://test.example.org/ontology/0139/version:2", "");
    private static final IRI CREATE0139 = IRI("http://test.example.org/ontology/0139", "");

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadChangeIRI() throws Exception {
        // given
        OWLOntologyDocumentSource initialDocumentSource = getDocumentSource();
        OWLOntologyID expected = new OWLOntologyID(optional(CREATE0139), optional(CREATEV2));
        OWLOntologyID initialUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV2));
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        parseOnto(initialDocumentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV2));
        // when
        try {
            getOWLOntology(secondUniqueOWLOntologyID);
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
        OWLOntologyID expected = new OWLOntologyID(optional(CREATE0139), optional(CREATEV1));
        OWLOntologyID initialUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV1));
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        parseOnto(documentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV1));
        // when
        try {
            getOWLOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            // then
            assertEquals(expected, e.getOntologyID());
            throw e;
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs() throws Exception {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV1));
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV2));
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
    public void testMultipleVersionLoadsNoOntologyIDFirstTime() throws Exception {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV2));
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
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime() throws Exception {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), emptyOptional(IRI.class));
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV2));
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
    public void testSingleVersionLoadChangeIRI() throws Exception {
        // given
        OWLOntologyDocumentSource secondDocumentSource = getDocumentSource();
        OWLOntologyID secondUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV2));
        // when
        OWLOntology secondOntology = getOWLOntology(secondUniqueOWLOntologyID);
        parseOnto(secondDocumentSource, secondOntology);
        // then
        assertEquals(CREATE0139, secondOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(CREATEV2, secondOntology.getOntologyID().getVersionIRI().get());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws Exception {
        // given
        OWLOntologyDocumentSource documentSource = getDocumentSource();
        OWLOntologyID initialUniqueOWLOntologyID =
                        new OWLOntologyID(optional(CREATE0139), optional(CREATEV1));
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
