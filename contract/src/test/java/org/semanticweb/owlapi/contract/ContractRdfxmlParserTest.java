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
package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import org.junit.Test;
import org.semanticweb.owlapi.formats.RDFOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.IRIProvider;
import org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer;
import org.semanticweb.owlapi.rdf.rdfxml.parser.TranslatedOntologyChangeException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.TranslatedUnloadableImportException;
import org.semanticweb.owlapi.rdf.rdfxml.parser.TriplePatternMatcher;
import org.xml.sax.SAXException;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractRdfxmlParserTest {

    @Test
    public void shouldTestInterfaceIRIProvider() throws OWLException {
        IRIProvider testSubject0 = mock(IRIProvider.class);
        IRI result0 = testSubject0.getIRI("");
    }

    @Test
    public void shouldTestOWLRDFConsumer() throws OWLException, SAXException {
        OWLRDFConsumer testSubject0 = new OWLRDFConsumer(
                Utils.getMockOntology(), new OWLOntologyLoaderConfiguration());
        testSubject0.setOntologyFormat(mock(RDFOntologyFormat.class));
        testSubject0.startModel(IRI.create(""));
        testSubject0.endModel();
        testSubject0.addModelAttribte("", "");
        testSubject0.includeModel("", "");
        testSubject0.logicalURI(IRI.create(""));
        testSubject0.statementWithLiteralValue("", "", "", "", "");
        testSubject0.statementWithResourceValue("", "", "");
    }

    @Test
    public void shouldTestTranslatedOntologyChangeException()
            throws OWLException {
        TranslatedOntologyChangeException testSubject0 = new TranslatedOntologyChangeException(
                mock(OWLOntologyChangeException.class));
        OWLOntologyChangeException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedUnloadedImportException()
            throws OWLException {
        TranslatedUnloadableImportException testSubject0 = new TranslatedUnloadableImportException(
                mock(UnloadableImportException.class));
        UnloadableImportException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceTriplePatternMatcher() throws OWLException {
        TriplePatternMatcher testSubject0 = mock(TriplePatternMatcher.class);
        boolean result0 = testSubject0.matches(Utils.mockOWLRDFConsumer(),
                IRI("urn:aFake"));
        OWLObject result1 = testSubject0.createObject(Utils
                .mockOWLRDFConsumer());
    }
}
