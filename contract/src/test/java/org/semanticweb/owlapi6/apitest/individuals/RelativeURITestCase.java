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
package org.semanticweb.owlapi6.apitest.individuals;

import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Class;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.Declaration;
import static org.semanticweb.owlapi6.OWLFunctionalSyntaxFactory.IRI;

import java.util.Collections;

import org.junit.Test;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.AxiomsRoundTrippingBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.OWLRDFXMLParserException;
import org.semanticweb.owlapi6.rdf.rdfxml.parser.RDFXMLParser;

/**
 * @author Matthew Horridge, The University of Manchester, Information Management Group
 * @since 3.0.0
 */
public class RelativeURITestCase extends AxiomsRoundTrippingBase {

    public RelativeURITestCase() {
        super(() -> Collections.singletonList(
            Declaration(Class(IRI(df.getNextDocumentIRI(uriBase) + "/", "Office")))));
    }

    @Test
    public void shouldThrowMeaningfulException() {
        // on Java 6 for Mac the following assertion does not work: the root
        // exception does not have a message.
        // expectedException
        // .expectMessage(" reason is: Illegal character in fragment at index
        // 21: http://example.com/#1#2");
        OWLOntology ontology = getOWLOntology();
        assertThrowsWithMessage(
            "[line=1:column=375] IRI 'http://example.com/#1#2' cannot be resolved against current base IRI ",
            OWLRDFXMLParserException.class,
            () -> new StringDocumentSource(TestFiles.rdfContentForException,
                new RDFXMLDocumentFormat()).acceptParser(new RDFXMLParser(), ontology, config));
    }
}
