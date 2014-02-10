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
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassExpressionParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxClassFrameParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxFramesParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxInlineAxiomParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyHeader;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyParser;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxTokenizer;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxTokenizer.Token;
import org.coode.owlapi.manchesterowlsyntax.OntologyAxiomPair;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.OWLOntologyChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.formats.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractManchesterowlsyntaxTest {
    @Test
    public void shouldTestManchesterOWLSyntax() throws OWLException {
        ManchesterOWLSyntax testSubject0 = ManchesterOWLSyntax.AND;
        ManchesterOWLSyntax[] result1 = ManchesterOWLSyntax.values();
        boolean result3 = testSubject0.isFrameKeyword();
        boolean result4 = testSubject0.isSectionKeyword();
        boolean result5 = testSubject0.isAxiomKeyword();
        boolean result6 = testSubject0.isClassExpressionConnectiveKeyword();
        boolean result7 = testSubject0.isClassExpressionQuantiferKeyword();
        String result8 = testSubject0.name();
        int result13 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestManchesterOWLSyntaxClassExpressionParser()
            throws OWLException {
        new ManchesterOWLSyntaxClassExpressionParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestManchesterOWLSyntaxClassFrameParser()
            throws OWLException {
        new ManchesterOWLSyntaxClassFrameParser(mock(OWLDataFactory.class),
                mock(OWLEntityChecker.class));
    }

    @Ignore
    @Test
    public void shouldTestManchesterOWLSyntaxEditorParser()
            throws OWLException, ParserException {
        ManchesterOWLSyntaxEditorParser testSubject0 = new ManchesterOWLSyntaxEditorParser(
                new OWLOntologyLoaderConfiguration(),
                mock(OWLDataFactory.class), "");
        new ManchesterOWLSyntaxEditorParser(mock(OWLDataFactory.class), "");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        OWLClassExpression result12 = testSubject0.parseClassExpression();
        Set<OntologyAxiomPair> result13 = testSubject0.parseClassFrameEOF();
        testSubject0.setOWLOntologyChecker(mock(OWLOntologyChecker.class));
        Set<OntologyAxiomPair> result52 = testSubject0.parseFrames();
        testSubject0.setDefaultOntology(Utils.getMockOntology());
        Set<OWLOntology> mock = Utils.mockSet(Utils.getMockOntology());
        ManchesterOWLSyntaxOntologyFormat result105 = testSubject0
                .parseOntology(Utils.getMockOntology());
    }

    @Test
    public void shouldTestManchesterOWLSyntaxFramesParser()
            throws OWLException, ParserException {
        ManchesterOWLSyntaxFramesParser testSubject0 = new ManchesterOWLSyntaxFramesParser(
                mock(OWLDataFactory.class), mock(OWLEntityChecker.class));
        Set<OntologyAxiomPair> result0 = testSubject0.parse("");
        Object result1 = testSubject0.parse("");
        testSubject0.setOWLEntityChecker(mock(OWLEntityChecker.class));
        testSubject0.setOWLOntologyChecker(mock(OWLOntologyChecker.class));
        testSubject0.setDefaultOntology(Utils.getMockOntology());
    }

    @Test
    public void shouldTestManchesterOWLSyntaxInlineAxiomParser()
            throws OWLException {
        new ManchesterOWLSyntaxInlineAxiomParser(mock(OWLDataFactory.class),
                mock(OWLEntityChecker.class));
    }

    @Test
    public void shouldTestManchesterOWLSyntaxOntologyFormat()
            throws OWLException {
        ManchesterOWLSyntaxOntologyFormat testSubject0 = new ManchesterOWLSyntaxOntologyFormat();
        String result1 = testSubject0.getPrefix("");
        IRI result2 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clear();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result3 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result4 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result5 = testSubject0.containsPrefixMapping("");
        String result6 = testSubject0.getDefaultPrefix();
        String result7 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result8 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result9 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result10 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result11 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestManchesterOWLSyntaxOntologyHeader()
            throws OWLException {
        Set<OWLAnnotation> mock1 = Utils.mockSet(mock(OWLAnnotation.class));
        Set<OWLImportsDeclaration> mock2 = Utils
                .mockSet(mock(OWLImportsDeclaration.class));
        ManchesterOWLSyntaxOntologyHeader testSubject0 = new ManchesterOWLSyntaxOntologyHeader(
                IRI("urn:aFake"), IRI("urn:aFake"), mock1, mock2);
        Collection<OWLAnnotation> result0 = testSubject0.getAnnotations();
        OWLOntologyID result1 = testSubject0.getOntologyID();
        Collection<OWLImportsDeclaration> result2 = testSubject0
                .getImportsDeclarations();
    }

    public void shouldTestManchesterOWLSyntaxOntologyParser()
            throws OWLException, OWLOntologyChangeException, IOException {
        ManchesterOWLSyntaxOntologyParser testSubject0 = new ManchesterOWLSyntaxOntologyParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result2 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
    }

    @Test
    public void shouldTestManchesterOWLSyntaxTokenizer() throws OWLException {
        ManchesterOWLSyntaxTokenizer testSubject0 = new ManchesterOWLSyntaxTokenizer(
                "");
        List<Token> result0 = testSubject0.tokenize();
    }

    @Test
    public void shouldTestOntologyAxiomPair() throws OWLException {
        OntologyAxiomPair testSubject0 = new OntologyAxiomPair(
                Utils.getMockOntology(), mock(OWLAxiom.class));
        OWLOntology result1 = testSubject0.getOntology();
        OWLAxiom result2 = testSubject0.getAxiom();
    }
}
