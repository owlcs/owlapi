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

import java.io.IOException;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParser;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParserAttributeNotFoundException;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParserElementNotFoundException;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParserException;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParserHandler;
import org.semanticweb.owlapi.owlxml.parser.TranslatedOWLOntologyChangeException;
import org.semanticweb.owlapi.owlxml.parser.TranslatedOWLParserException;
import org.semanticweb.owlapi.owlxml.parser.TranslatedUnloadableImportException;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlxmlparserTest {
    public void shouldTestOWLXMLParser() throws OWLException,
            OWLOntologyChangeException, IOException {
        OWLXMLParser testSubject0 = new OWLXMLParser();
        OWLOntologyFormat result1 = testSubject0.parse(
                new StringDocumentSource(""), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
    }

    @Test
    public void shouldTestOWLXMLParserAttributeNotFoundException()
            throws OWLException {
        OWLXMLParserAttributeNotFoundException testSubject0 = new OWLXMLParserAttributeNotFoundException(
                mock(OWLXMLParserHandler.class), "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLParserElementNotFoundException()
            throws OWLException {
        OWLXMLParserElementNotFoundException testSubject0 = new OWLXMLParserElementNotFoundException(
                mock(OWLXMLParserHandler.class), "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLXMLParserException() throws OWLException {
        OWLXMLParserException testSubject0 = new OWLXMLParserException(
                mock(OWLXMLParserHandler.class), "");
        String result0 = testSubject0.getMessage();
        int result1 = testSubject0.getLineNumber();
        int result2 = testSubject0.getColumnNumber();
        Throwable result4 = testSubject0.getCause();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedOWLOntologyChangeException()
            throws OWLException {
        TranslatedOWLOntologyChangeException testSubject0 = new TranslatedOWLOntologyChangeException(
                mock(OWLOntologyChangeException.class));
        OWLOntologyChangeException result0 = testSubject0.getCause();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedOWLParserException() throws OWLException {
        TranslatedOWLParserException testSubject0 = new TranslatedOWLParserException(
                mock(OWLParserException.class));
        OWLParserException result0 = testSubject0.getParserException();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestTranslatedUnloadableImportException()
            throws OWLException {
        TranslatedUnloadableImportException testSubject0 = new TranslatedUnloadableImportException(
                mock(UnloadableImportException.class));
        UnloadableImportException result0 = testSubject0
                .getUnloadableImportException();
        Throwable result1 = testSubject0.getCause();
        String result3 = testSubject0.getMessage();
        Exception result4 = testSubject0.getException();
        String result7 = testSubject0.getLocalizedMessage();
    }
}
