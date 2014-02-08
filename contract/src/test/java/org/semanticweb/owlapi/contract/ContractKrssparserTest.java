/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2014, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.contract;

import static org.mockito.Mockito.mock;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.IRI;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Set;

import org.coode.owl.krssparser.KRSSOWLParser;
import org.coode.owl.krssparser.KRSSOWLParserFactory;
import org.coode.owl.krssparser.KRSSParser;
import org.coode.owl.krssparser.KRSSParserConstants;
import org.coode.owl.krssparser.KRSSParserTokenManager;
import org.coode.owl.krssparser.NameResolverStrategy;
import org.coode.owl.krssparser.ParseException;
import org.coode.owl.krssparser.Token;
import org.coode.owl.krssparser.TokenMgrError;
import org.junit.Test;
import org.semanticweb.owlapi.formats.KRSSOntologyFormat;
import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
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

    @Test
    public void shouldTestKRSSOWLParserFactory() throws OWLException {
        KRSSOWLParserFactory testSubject0 = new KRSSOWLParserFactory();
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
    }

    public void shouldTestKRSSParser() throws OWLException, ParseException {
        KRSSParser testSubject0 = new KRSSParser(
                mock(KRSSParserTokenManager.class));
        new KRSSParser(mock(Reader.class));
        new KRSSParser(mock(InputStream.class), "");
        new KRSSParser(mock(InputStream.class));
        testSubject0.parse();
        IRI result0 = testSubject0.Name();
        OWLAxiom result1 = testSubject0.Instance();
        OWLClassExpression result2 = testSubject0.All();
        IRI result3 = testSubject0.getIRI("");
        testSubject0.setOntology(Utils.getMockOntology(),
                mock(OWLDataFactory.class));
        testSubject0.ReInit(mock(Reader.class));
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(InputStream.class), "");
        testSubject0.ReInit(mock(KRSSParserTokenManager.class));
        OWLAxiom result4 = testSubject0.TBoxStatement();
        OWLAxiom result5 = testSubject0.ABoxStatement();
        OWLAxiom result6 = testSubject0.DefinePrimitiveConcept();
        OWLAxiom result7 = testSubject0.DefineConcept();
        OWLAxiom result8 = testSubject0.DefinePrimitiveRole();
        OWLAxiom result9 = testSubject0.Range();
        OWLAxiom result10 = testSubject0.Transitive();
        OWLClassExpression result11 = testSubject0.ConceptName();
        OWLClassExpression result12 = testSubject0.ConceptExpression();
        OWLObjectProperty result13 = testSubject0.RoleName();
        OWLClassExpression result14 = testSubject0.And();
        OWLClassExpression result15 = testSubject0.Or();
        OWLClassExpression result16 = testSubject0.Not();
        OWLClassExpression result17 = testSubject0.Some();
        OWLClassExpression result18 = testSubject0.AtLeast();
        OWLClassExpression result19 = testSubject0.AtMost();
        OWLClassExpression result20 = testSubject0.Exactly();
        Set<OWLClassExpression> result21 = testSubject0.ConceptSet();
        int result22 = testSubject0.Integer();
        OWLAxiom result23 = testSubject0.Related();
        OWLAxiom result24 = testSubject0.Equal();
        OWLAxiom result25 = testSubject0.Distinct();
        OWLIndividual result26 = testSubject0.IndividualName();
        Token result27 = testSubject0.getNextToken();
        ParseException result28 = testSubject0.generateParseException();
        Token result29 = testSubject0.getToken(0);
        testSubject0.enable_tracing();
        testSubject0.disable_tracing();
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
        new ParseException();
        new ParseException("");
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
        new TokenMgrError("", 0);
        new TokenMgrError(false, 0, 0, 0, "", mock(char.class), 0);
        String result0 = testSubject0.getMessage();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getLocalizedMessage();
    }
}
