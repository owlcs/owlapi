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
package org.semanticweb.owlapi.api.test.fileroundtrip;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.AbstractRoundTrippingTestCase;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
@SuppressWarnings("javadoc")
public class OntologyIRITestCase extends AbstractRoundTrippingTestCase {

    @Test
    public void testCorrectOntologyIRI() {
        OWLOntology ont = createOntology();
        OWLOntologyID id = ont.getOntologyID();
        assertEquals("http://www.test.com/right.owl", id.getOntologyIRI().get().toString());
    }

    @Override
    public void testFunctionalSyntax() {
        // XXX thi is failing for functional syntax
    }

    @Override
    public void roundTripRDFXMLAndFunctionalShouldBeSame() {
        // XXX functional won't work here
    }

    @Override
    protected OWLOntology createOntology() {
        return loadOntologyFromString("<?xml version=\"1.0\"?>\n" + "<!DOCTYPE rdf:RDF [\n"
                        + "        <!ENTITY owl \"http://www.w3.org/2002/07/owl#\" >\n"
                        + "        <!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n"
                        + "        <!ENTITY owl2xml \"http://www.w3.org/2006/12/owl2-xml#\" >\n"
                        + "        <!ENTITY rdfs \"http://www.w3.org/2000/01/rdf-schema#\" >\n"
                        + "        <!ENTITY rdf \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" >\n"
                        + "        ]>\n" + "<rdf:RDF xmlns=\"http://www.test.com/Ambiguous.owl#\"\n"
                        + "         xml:base=\"http://www.test.com/Ambiguous.owl\"\n"
                        + "         xmlns:owl2xml=\"http://www.w3.org/2006/12/owl2-xml#\"\n"
                        + "         xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"\n"
                        + "         xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"\n"
                        + "         xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
                        + "         xmlns:owl=\"http://www.w3.org/2002/07/owl#\">\n"
                        + "    <owl:Ontology rdf:about=\"http://www.test.com/wrong.owl\"/>\n"
                        + "    <owl:OntologyProperty rdf:about=\"p\"/>\n"
                        + "    <owl:Ontology rdf:about=\"http://www.test.com/right.owl\">\n"
                        + "        <p>\n"
                        + "            <owl:Ontology rdf:about=\"http://www.test.com/wrong.owl\"/>\n"
                        + "        </p>\n" + "    </owl:Ontology>\n" + "</rdf:RDF>",
                        new RDFXMLDocumentFormat());
    }
}
