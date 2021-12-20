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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.IRI;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi6.model.OWLOntologyID;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParser;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
class MultipleOntologyLoadsTestCase extends TestBase {

    static final IRI v2 = iri("http://test.example.org/ontology/0139/version:2", "");
    static final IRI v1 = iri("http://test.example.org/ontology/0139/version:1", "");
    static final IRI i139 = iri("http://test.example.org/ontology/0139", "");

    @Test
    void testMultipleVersionLoadChangeIRI() {
        secondOntology();
        OWLOntologyAlreadyExistsException ex = assertThrows(OWLOntologyAlreadyExistsException.class,
            () -> m.createOntology(OntologyID(i139, v2)));
        assertEquals(OntologyID(i139, v2), ex.getOntologyID());
    }

    @Test
    void testMultipleVersionLoadNoChange() {
        initialOntology(OntologyID(i139, v1));
        OWLOntologyAlreadyExistsException ex = assertThrows(OWLOntologyAlreadyExistsException.class,
            () -> m.createOntology(OntologyID(i139, v1)));
        assertEquals(OntologyID(i139, v1), ex.getOntologyID());
    }

    @Test
    void testMultipleVersionLoadsExplicitOntologyIDs() {
        assertI139V1(initialOntology(OntologyID(i139, v1)));
        assertI139V2(secondOntology());
    }

    protected void assertI139V2(OWLOntology secondOntology) {
        assertEquals(i139, secondOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(v2, secondOntology.getOntologyID().getVersionIRI().get());
    }

    protected void assertI139V1(OWLOntology initialOntology) {
        assertEquals(i139, initialOntology.getOntologyID().getOntologyIRI().get());
        assertEquals(v1, initialOntology.getOntologyID().getVersionIRI().get());
    }

    protected OWLOntology secondOntology() {
        return initialOntology(OntologyID(i139, v2));
    }

    protected OWLOntology initialOntology(OWLOntologyID initialUniqueOWLOntologyID) {
        OWLOntology initialOntology = create(initialUniqueOWLOntologyID);
        source().acceptParser(new RDFXMLParser(), initialOntology, config);
        return initialOntology;
    }

    protected StringDocumentSource source() {
        return new StringDocumentSource(TestFiles.INPUT, new RDFXMLDocumentFormat());
    }

    @Test
    void testMultipleVersionLoadsNoOntologyIDFirstTime() {
        OWLOntology initialOntology = createAnon();
        source().acceptParser(new RDFXMLParser(), initialOntology, config);
        assertI139V1(initialOntology);
        assertI139V2(secondOntology());
    }

    @Test
    void testMultipleVersionLoadsNoOntologyVersionIRIFirstTime() {
        assertI139V1(initialOntology(OntologyID(i139)));
        assertI139V2(secondOntology());
    }

    @Test
    void testSingleVersionLoadChangeIRI() {
        assertI139V2(secondOntology());
    }

    @Test
    void testSingleVersionLoadNoChange() {
        assertI139V1(initialOntology(OntologyID(i139, v1)));
    }
}
