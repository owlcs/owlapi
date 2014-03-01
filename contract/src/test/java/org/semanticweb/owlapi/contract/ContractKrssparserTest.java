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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.junit.Test;
import org.semanticweb.owlapi.formats.KRSSOntologyFormat;
import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.krss1.parser.KRSSOWLParser;
import org.semanticweb.owlapi.krss1.parser.KRSSParser;
import org.semanticweb.owlapi.krss1.parser.KRSSParserConstants;
import org.semanticweb.owlapi.krss1.parser.KRSSParserTokenManager;
import org.semanticweb.owlapi.krss1.parser.NameResolverStrategy;
import org.semanticweb.owlapi.krss1.parser.ParseException;
import org.semanticweb.owlapi.krss1.parser.Token;
import org.semanticweb.owlapi.krss1.parser.TokenMgrError;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractKrssparserTest {

    public void shouldTestKRSSOntologyFormat() throws OWLException {
        KRSSOntologyFormat testSubject0 = new KRSSOntologyFormat();
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result1 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result2 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result3 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result4 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    public void shouldTestKRSSOWLParser() throws OWLException,
            OWLOntologyChangeException, IOException {
        KRSSOWLParser testSubject0 = new KRSSOWLParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result2 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
    }

    public void shouldTestKRSSParser() throws OWLException, ParseException {
        KRSSParser testSubject0 = new KRSSParser(
                mock(KRSSParserTokenManager.class));
        new KRSSParser(mock(Reader.class));
        new KRSSParser(mock(InputStream.class), "");
        new KRSSParser(mock(InputStream.class));
        testSubject0.parse();
        IRI result3 = testSubject0.getIRI("");
        testSubject0.setOntology(Utils.getMockOntology(),
                mock(OWLDataFactory.class));
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(InputStream.class), "");
        testSubject0.ReInit(mock(KRSSParserTokenManager.class));
        Token result27 = testSubject0.getNextToken();
        ParseException result28 = testSubject0.generateParseException();
        Token result29 = testSubject0.getToken(0);
    }

    @Test
    public void shouldTestInterfaceKRSSParserConstants() throws OWLException {
        KRSSParserConstants testSubject0 = mock(KRSSParserConstants.class);
    }

    @Test
    public void shouldTestNameResolverStrategy() throws OWLException {
        NameResolverStrategy testSubject0 = NameResolverStrategy.ADAPTIVE;
        NameResolverStrategy[] result0 = NameResolverStrategy.values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    public void shouldTestParseException() throws OWLException {
        ParseException testSubject0 = new ParseException(mock(Token.class),
                mock(int[][].class), new String[0]);
        Throwable result1 = testSubject0.getCause();
        String result4 = testSubject0.getMessage();
        String result5 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestToken() throws OWLException {
        Token testSubject0 = new Token();
        new Token(0);
        new Token(0, "");
        Object result1 = testSubject0.getValue();
        Token result2 = Token.newToken(0, "");
        Token result3 = Token.newToken(0);
    }

    public void shouldTestTokenMgrError() throws OWLException {
        TokenMgrError testSubject0 = new TokenMgrError();
        String result0 = testSubject0.getMessage();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getLocalizedMessage();
    }
}
