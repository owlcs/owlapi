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

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;

/** @author Peter Ansell p_ansell@yahoo.com */
@SuppressWarnings("javadoc")
public class MultipleOntologyLoadsTestCase {

    private IRI v2;
    private IRI v1;
    private IRI i139;
    private String input;
    private OWLOntologyManager manager;

    @Before
    public void setUp() {
        v2 = IRI("http://test.example.org/ontology/0139/version:2");
        v1 = IRI("http://test.example.org/ontology/0139/version:1");
        i139 = IRI("http://test.example.org/ontology/0139");
        input = "<?xml version=\"1.0\"?>\n"
                + "<rdf:RDF\n"
                + "    xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                + "    xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                + "    xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                + "    xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
                + "  <rdf:Description rdf:about=\"http://test.example.org/ontology/0139\">\n"
                + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\" />\n"
                + "    <owl:versionIRI rdf:resource=\"http://test.example.org/ontology/0139/version:1\" />\n"
                + "  </rdf:Description>  \n" + "</rdf:RDF>";
        manager = OWLManager.createOWLOntologyManager();
    }

    @Test
    public void testMultipleVersionLoadChangeIRI()
            throws OWLOntologyChangeException, OWLParserException, IOException,
            OWLOntologyCreationException {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser initialParser = new RDFXMLParser();
        initialParser.parse(new StringDocumentSource(input), initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
            fail("Did not receive expected OWLOntologyDocumentAlreadyExistsException");
        } catch (OWLOntologyAlreadyExistsException e) {
            assertEquals(new OWLOntologyID(i139, v2), e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadNoChange()
            throws OWLOntologyCreationException, OWLOntologyChangeException,
            OWLParserException, IOException {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(input), initialOntology);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        try {
            manager.createOntology(secondUniqueOWLOntologyID);
            fail("Did not receive expected OWLOntologyAlreadyExistsException");
        } catch (OWLOntologyAlreadyExistsException e) {
            assertEquals(new OWLOntologyID(i139, v1), e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs()
            throws OWLOntologyCreationException, OWLOntologyChangeException,
            OWLParserException, IOException {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(input), initialOntology);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI());
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(new StringDocumentSource(input), secondOntology);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI());
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime()
            throws OWLOntologyCreationException, OWLOntologyChangeException,
            OWLParserException, IOException {
        OWLOntology initialOntology = manager.createOntology();
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(input), initialOntology);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI());
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(new StringDocumentSource(input), secondOntology);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI());
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime()
            throws OWLOntologyCreationException, OWLOntologyChangeException,
            OWLParserException, IOException {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(input), initialOntology);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI());
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(new StringDocumentSource(input), secondOntology);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI());
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadChangeIRI()
            throws OWLOntologyChangeException, OWLParserException, IOException,
            OWLOntologyCreationException {
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = manager
                .createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        // the following throws the exception
        secondParser.parse(new StringDocumentSource(input), secondOntology);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI());
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    @Test
    public void testSingleVersionLoadNoChange()
            throws OWLOntologyCreationException, OWLOntologyChangeException,
            OWLParserException, IOException {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        OWLOntology initialOntology = manager
                .createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(input), initialOntology);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI());
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
    }
}
