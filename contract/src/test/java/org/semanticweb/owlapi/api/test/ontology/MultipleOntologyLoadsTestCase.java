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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.emptyOptional;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.optional;

import java.util.Optional;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
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
@SuppressWarnings("javadoc")
public class MultipleOntologyLoadsTestCase extends TestBase {

    private final Optional<IRI> v2 =
        optional(IRI.getNextDocumentIRI("http://test.example.org/ontology/0139/version:2"));
    private final Optional<IRI> v1 =
        optional(IRI.getNextDocumentIRI("http://test.example.org/ontology/0139/version:1"));
    private final Optional<IRI> i139 =
        optional(IRI.getNextDocumentIRI("http://test.example.org/ontology/0139"));
    private final String INPUT = "<?xml version=\"1.0\"?>\n"
        + "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
        + "  <rdf:Description rdf:about=\"" + i139.get().toString() + "\">\n"
        + "    <rdf:type rdf:resource=\"http://www.w3.org/2002/07/owl#Ontology\" />\n"
        + "    <owl:versionIRI rdf:resource=\"" + v1.get().toString() + "\" />\n"
        + "  </rdf:Description>  \n" + "</rdf:RDF>";

    @Test
    public void testMultipleVersionLoadChangeIRI() throws Exception {
        secondOntology();
        try {
            getOWLOntology(new OWLOntologyID(i139, v2));
            fail("Did not receive expected OWLOntologyDocumentAlreadyExistsException");
        } catch (OWLOntologyAlreadyExistsException e) {
            assertEquals(new OWLOntologyID(i139, v2), e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadNoChange() throws Exception {
        initialOntology(new OWLOntologyID(i139, v1));
        try {
            getOWLOntology(new OWLOntologyID(i139, v1));
            fail("Did not receive expected OWLOntologyAlreadyExistsException");
        } catch (OWLOntologyAlreadyExistsException e) {
            assertEquals(new OWLOntologyID(i139, v1), e.getOntologyID());
        }
    }

    @Test
    public void testMultipleVersionLoadsExplicitOntologyIDs() throws Exception {
        assertI139V1(initialOntology(new OWLOntologyID(i139, v1)));
        assertI139V2(secondOntology());
    }

    protected void assertI139V2(OWLOntology secondOntology) {
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI());
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI());
    }

    protected void assertI139V1(OWLOntology initialOntology) {
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI());
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI());
    }

    protected OWLOntology secondOntology() throws OWLOntologyCreationException {
        return initialOntology(new OWLOntologyID(i139, v2));
    }

    protected OWLOntology initialOntology(OWLOntologyID initialUniqueOWLOntologyID)
        throws OWLOntologyCreationException {
        OWLOntology initialOntology = getOWLOntology(initialUniqueOWLOntologyID);
        source().acceptParser(new RDFXMLParser(), initialOntology, config);
        return initialOntology;
    }

    protected StringDocumentSource source() {
        return new StringDocumentSource(INPUT, new RDFXMLDocumentFormat());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyIDFirstTime() throws Exception {
        OWLOntology initialOntology = getAnonymousOWLOntology();
        source().acceptParser(new RDFXMLParser(), initialOntology, config);
        assertI139V1(initialOntology);
        assertI139V2(secondOntology());
    }

    @Test
    public void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime() throws Exception {
        Optional<IRI> empty = emptyOptional(IRI.class);
        assertI139V1(initialOntology(new OWLOntologyID(i139, empty)));
        assertI139V2(secondOntology());
    }

    @Test
    public void testSingleVersionLoadChangeIRI() throws Exception {
        assertI139V2(secondOntology());
    }

    @Test
    public void testSingleVersionLoadNoChange() throws Exception {
        assertI139V1(initialOntology(new OWLOntologyID(i139, v1)));
    }
}
