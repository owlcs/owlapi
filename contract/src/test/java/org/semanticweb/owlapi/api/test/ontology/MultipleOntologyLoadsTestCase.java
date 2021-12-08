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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.apitest.TestFiles;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class MultipleOntologyLoadsTestCase extends TestBase {

    static final IRI v2 = iri("http://test.example.org/ontology/0139/version:2", "");
    static final IRI v1 = iri("http://test.example.org/ontology/0139/version:1", "");
    static final IRI i139 = iri("http://test.example.org/ontology/0139", "");

    @Test
    void testMultipleVersionLoadChangeIRI() {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology initialOntology = create(initialUniqueOWLOntologyID);
        OWLParser initialParser = new RDFXMLParser();
        initialParser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE),
            initialOntology, config);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntologyAlreadyExistsException ex = assertThrows(OWLOntologyAlreadyExistsException.class,
            () -> m.createOntology(secondUniqueOWLOntologyID));
        assertEquals(new OWLOntologyID(i139, v2), ex.getOntologyID());
    }

    @Test
    void testMultipleVersionLoadNoChange() throws OWLOntologyCreationException {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        OWLOntology initialOntology = m.createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE), initialOntology,
            config);
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        OWLOntologyAlreadyExistsException ex = assertThrows(OWLOntologyAlreadyExistsException.class,
            () -> m.createOntology(secondUniqueOWLOntologyID));
        assertEquals(new OWLOntologyID(i139, v1), ex.getOntologyID());
    }

    @Test
    void testMultipleVersionLoadsExplicitOntologyIDs() throws OWLOntologyCreationException {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        OWLOntology initialOntology = m.createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE), initialOntology,
            config);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI().orElse(null));
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = m.createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE),
            secondOntology, config);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI().orElse(null));
    }

    @Test
    void testMultipleVersionLoadsNoOntologyIDFirstTime() throws OWLOntologyCreationException {
        OWLOntology initialOntology = createAnon();
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE), initialOntology,
            config);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI().orElse(null));
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = m.createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE),
            secondOntology, config);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI().orElse(null));
    }

    @Test
    void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime()
        throws OWLOntologyCreationException {
        Optional<IRI> empty = emptyOptional(IRI.class);
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(Optional.of(i139), empty);
        OWLOntology initialOntology = m.createOntology(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE), initialOntology,
            config);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI().orElse(null));
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = m.createOntology(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        secondParser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE),
            secondOntology, config);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI().orElse(null));
    }

    @Test
    void testSingleVersionLoadChangeIRI() {
        OWLOntologyID secondUniqueOWLOntologyID = new OWLOntologyID(i139, v2);
        OWLOntology secondOntology = create(secondUniqueOWLOntologyID);
        OWLParser secondParser = new RDFXMLParser();
        // the following throws the exception
        secondParser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE),
            secondOntology, config);
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI().orElse(null));
    }

    @Test
    void testSingleVersionLoadNoChange() {
        OWLOntologyID initialUniqueOWLOntologyID = new OWLOntologyID(i139, v1);
        OWLOntology initialOntology = create(initialUniqueOWLOntologyID);
        OWLParser parser = new RDFXMLParser();
        parser.parse(new StringDocumentSource(TestFiles.INPUT_VERSION_LOAD_CHANGE), initialOntology,
            config);
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI().orElse(null));
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI().orElse(null));
    }
}
