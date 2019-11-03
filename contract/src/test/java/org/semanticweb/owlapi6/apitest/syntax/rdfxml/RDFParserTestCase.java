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
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.AxiomType;
import org.semanticweb.owlapi6.model.OWLException;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLOntologyCreationException;
import org.semanticweb.owlapi6.rdf.rdfxml.renderer.RDFXMLStorerFactory;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class RDFParserTestCase extends TestBase {

    @Before
    public void setUpStorers() {
        // Use the reference implementation
        m.getOntologyStorers().set(new RDFXMLStorerFactory());
    }

    @Test
    public void testOWLAPI() throws Exception {
        parseFiles("/owlapi/");
    }

    private void parseFiles(String base) throws URISyntaxException, OWLOntologyCreationException {
        URL url = getClass().getResource(base);
        File file = new File(url.toURI());
        for (File testSuiteFolder : file.listFiles()) {
            if (testSuiteFolder.isDirectory()) {
                for (File ontologyFile : testSuiteFolder.listFiles()) {
                    if (ontologyFile.getName().endsWith(".rdf") || ontologyFile.getName().endsWith(".owlapi")) {
                        OWLOntology ont = m.loadOntologyFromOntologyDocument(ontologyFile);
                        m.removeOntology(ont);
                    }
                }
            }
        }
    }

    @Test
    public void shouldParseDataProperty() {
        OWLOntology o = loadOntologyFromString(TestFiles.parseDataProperty, new RDFXMLDocumentFormat());
        assertFalse(o.containsObjectPropertyInSignature(
            df.getIRI("http://www.loa-cnr.it/ontologies/Plans.owl#", "iteration-cardinality")));
    }

    @Test
    public void shouldLoadSubPropertiesAsObjectProperties() {
        OWLOntology o = loadOntologyFromString(TestFiles.subPropertiesAsObjectProperties, new RDFXMLDocumentFormat());
        assertEquals(0, o.axioms(AxiomType.SUB_ANNOTATION_PROPERTY_OF).count());
        assertEquals(1, o.axioms(AxiomType.SUB_OBJECT_PROPERTY).count());
    }

    @Test
    public void shouldRoundTripLhsSubsetOfRHS() throws OWLException {
        OWLOntology o = loadOntologyFromString(TestFiles.lhsSubsetofRhs, new FunctionalSyntaxDocumentFormat());
        OWLOntology o1 = roundTrip(o, new RDFXMLDocumentFormat());
        equal(o, o1);
    }
}
