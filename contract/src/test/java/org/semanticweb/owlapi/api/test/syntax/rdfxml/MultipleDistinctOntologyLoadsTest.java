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

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;

/**
 * Tests the loading of a single ontology multiple times, using a different
 * ontologyIRI in the OWLOntologyID as that used in the actual ontology that is
 * being imported.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class MultipleDistinctOntologyLoadsTest {

    private OWLOntologyManager manager;
    IRI jb = IRI("http://example.purl.org.au/domainontology/JB_000007");
    IRI v1 = IRI("http://test.example.org/ontology/0139/version:1");
    IRI v2 = IRI("http://test.example.org/ontology/0139/version:2");

    @Before
    public void setUp() {
        manager = OWLManager.createOWLOntologyManager();
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadChangeIRI() throws OWLException,
            OWLOntologyChangeException, IOException {
        OWLOntologyDocumentSource initialDocumentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(jb, v2);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser initialParser = new RDFXMLParser();
        initialParser.parse(initialDocumentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(jb, v2);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(new OWLOntologyID(jb, v2), e.getOntologyID());
            throw e;
        }
    }

    private OWLOntologyDocumentSource getDocument() {
        StreamDocumentSource initialDocumentSource = new StreamDocumentSource(
                this.getClass().getResourceAsStream(
                        "/owlapi/multipleOntologyLoadsTest.rdf"));
        return initialDocumentSource;
    }

    @Test(expected = OWLOntologyAlreadyExistsException.class)
    public void testMultipleVersionLoadNoChange() throws OWLException,
            OWLOntologyChangeException, IOException {
        OWLOntologyDocumentSource documentSource = getDocument();
        OWLOntologyID expected = new OWLOntologyID(jb, v1);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(jb, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(documentSource, initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(jb, v1);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
        } catch (OWLOntologyAlreadyExistsException e) {
            Assert.assertEquals(expected, e.getOntologyID());
            throw e;
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs()
            throws OWLException, OWLOntologyChangeException, IOException {
        OWLOntologyDocumentSource documentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(jb, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(jb, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        OWLOntologyDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(jb, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(jb, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime()
            throws OWLException, OWLOntologyChangeException, IOException {
        OWLOntologyDocumentSource documentSource = getDocument();
        OWLOntology initialOntology = manager.createOntology();
        OWLParser parser = new RDFXMLParser();
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(IRI("http://test.example.org/ontology/0139"),
                initialOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        OWLOntologyDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(jb, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(jb, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime()
            throws OWLException, OWLOntologyChangeException, IOException {
        OWLOntologyDocumentSource documentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(jb);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(jb, initialOntology.getOntologyID()
                .getOntologyIRI());
        // FIXME: versionIRI is null for some reason even though it was in the
        // document
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        OWLOntologyDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(jb, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        // NOTE: The following call throws the OWLOntologyRenameException before
        // the patch to TypeOntologyHandler and TPVersionIRIHandler
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(jb, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws OWLException,
            OWLOntologyChangeException, IOException {
        OWLOntologyDocumentSource secondDocumentSource = getDocument();
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(jb, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        // the following throws the exception
        secondParser.parse(secondDocumentSource, secondOntology);
        Assert.assertEquals(jb, secondOntology.getOntologyID().getOntologyIRI());
        Assert.assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws OWLException,
            OWLOntologyChangeException, IOException {
        OWLOntologyDocumentSource documentSource = getDocument();
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(jb, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(documentSource, initialOntology);
        Assert.assertEquals(jb, initialOntology.getOntologyID()
                .getOntologyIRI());
        Assert.assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
    }
}
