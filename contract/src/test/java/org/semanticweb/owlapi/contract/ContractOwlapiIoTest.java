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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.formats.RDFOntologyFormat;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.AbstractOWLRenderer;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.io.FileDocumentTarget;
import org.semanticweb.owlapi.io.IRIDocumentSource;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.io.OWLOntologyStorageIOException;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.io.OWLParserFactoryRegistry;
import org.semanticweb.owlapi.io.OWLRenderer;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OntologyIRIMappingNotFoundException;
import org.semanticweb.owlapi.io.RDFNode;
import org.semanticweb.owlapi.io.RDFOntologyHeaderStatus;
import org.semanticweb.owlapi.io.RDFParserMetaData;
import org.semanticweb.owlapi.io.RDFResourceParseError;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentTarget;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.io.WriterDocumentTarget;
import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.io.ZipDocumentTarget;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.ShortFormProvider;

@SuppressWarnings({ "unused", "javadoc", "resource" })
public class ContractOwlapiIoTest {
    @Test
    public void shouldTestAbstractOWLParser() throws OWLException,
            OWLOntologyChangeException, IOException {
        AbstractOWLParser testSubject0 = new AbstractOWLParser() {
            @Override
            public String getName() {
                return "mockparser";
            }

            @Override
            public OWLOntologyFormat parse(
                    OWLOntologyDocumentSource documentSource,
                    OWLOntology ontology) throws OWLParserException,
                    IOException, OWLOntologyChangeException,
                    UnloadableImportException {
                return null;
            }

            @Override
            public OWLOntologyFormat parse(
                    OWLOntologyDocumentSource documentSource,
                    OWLOntology ontology,
                    OWLOntologyLoaderConfiguration configuration)
                    throws OWLParserException, IOException,
                    OWLOntologyChangeException, UnloadableImportException {
                return null;
            }
        };
        OWLOntologyFormat result0 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
        OWLOntologyFormat result3 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result4 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
    }

    @Test
    public void shouldTestAbstractOWLRenderer() throws OWLException {
        AbstractOWLRenderer testSubject0 = new AbstractOWLRenderer() {
            @Override
            public void render(OWLOntology ontology, Writer writer)
                    throws OWLRendererException {}
        };
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
    }

    @Ignore
    @Test
    public void shouldTestFileDocumentSource() throws OWLException {
        FileDocumentSource testSubject0 = new FileDocumentSource(new File("."));
        InputStream result0 = testSubject0.getInputStream();
        boolean result1 = testSubject0.isReaderAvailable();
        if (result1) {
            Reader result2 = testSubject0.getReader();
        }
        boolean result3 = testSubject0.isInputStreamAvailable();
        IRI result4 = testSubject0.getDocumentIRI();
    }

    @Ignore
    @Test
    public void shouldTestFileDocumentTarget() throws OWLException, IOException {
        FileDocumentTarget testSubject0 = new FileDocumentTarget(new File("."));
        boolean result4 = testSubject0.isOutputStreamAvailable();
        if (result4) {
            OutputStream result0 = testSubject0.getOutputStream();
        }
        boolean result2 = testSubject0.isWriterAvailable();
        if (result2) {
            Writer result3 = testSubject0.getWriter();
        }
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        if (result5) {
            IRI result1 = testSubject0.getDocumentIRI();
        }
    }

    @Test
    public void shouldTestIRIDocumentSource() throws OWLException {
        IRIDocumentSource testSubject0 = new IRIDocumentSource(IRI("urn:aFake"));
        boolean result2 = testSubject0.isReaderAvailable();
        if (result2) {
            Reader result3 = testSubject0.getReader();
        }
        boolean result4 = testSubject0.isInputStreamAvailable();
        if (result4) {
            InputStream result1 = testSubject0.getInputStream();
            IRI result5 = testSubject0.getDocumentIRI();
        }
    }

    @Test
    public void shouldTestOntologyIRIMappingNotFoundException()
            throws OWLException {
        OntologyIRIMappingNotFoundException testSubject0 = new OntologyIRIMappingNotFoundException(
                IRI("urn:aFake"));
        IRI result0 = testSubject0.getOntologyIRI();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestOWLFunctionalSyntaxOntologyFormat()
            throws OWLException {
        OWLFunctionalSyntaxOntologyFormat testSubject0 = new OWLFunctionalSyntaxOntologyFormat();
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
    public void shouldTestInterfaceOWLObjectRenderer() throws OWLException {
        OWLObjectRenderer testSubject0 = mock(OWLObjectRenderer.class);
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
    }

    @Test
    public void shouldTestOWLOntologyCreationIOException() throws OWLException {
        OWLOntologyCreationIOException testSubject0 = new OWLOntologyCreationIOException(
                mock(IOException.class));
        Throwable result1 = testSubject0.getCause();
        String result2 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyDocumentSource()
            throws OWLException {
        OWLOntologyDocumentSource testSubject0 = mock(OWLOntologyDocumentSource.class);
        InputStream result0 = testSubject0.getInputStream();
        boolean result1 = testSubject0.isReaderAvailable();
        Reader result2 = testSubject0.getReader();
        boolean result3 = testSubject0.isInputStreamAvailable();
        IRI result4 = testSubject0.getDocumentIRI();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyDocumentTarget()
            throws OWLException, IOException {
        OWLOntologyDocumentTarget testSubject0 = mock(OWLOntologyDocumentTarget.class);
        OutputStream result0 = testSubject0.getOutputStream();
        IRI result1 = testSubject0.getDocumentIRI();
        boolean result2 = testSubject0.isWriterAvailable();
        Writer result3 = testSubject0.getWriter();
        boolean result4 = testSubject0.isOutputStreamAvailable();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
    }

    @Test
    public void shouldTestInterfaceOWLOntologyLoaderMetaData()
            throws OWLException {
        OWLOntologyLoaderMetaData testSubject0 = mock(OWLOntologyLoaderMetaData.class);
    }

    @Test
    public void shouldTestOWLOntologyStorageIOException() throws OWLException {
        OWLOntologyStorageIOException testSubject0 = new OWLOntologyStorageIOException(
                mock(IOException.class));
        IOException result0 = testSubject0.getIOException();
        Throwable result2 = testSubject0.getCause();
        String result5 = testSubject0.getMessage();
        String result6 = testSubject0.getLocalizedMessage();
    }

    @Test
    public void shouldTestInterfaceOWLParser() throws OWLException,
            OWLOntologyChangeException, IOException {
        OWLParser testSubject0 = mock(OWLParser.class);
        OWLOntologyFormat result0 = testSubject0.parse(IRI("urn:aFake"),
                Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result2 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
    }

    @Test
    public void shouldTestInterfaceOWLParserFactory() throws OWLException {
        OWLParserFactory testSubject0 = mock(OWLParserFactory.class);
        OWLParser result0 = testSubject0.createParser(Utils.getMockManager());
    }

    @Ignore
    @Test
    public void shouldTestOWLParserFactoryRegistry() throws OWLException {
        OWLParserFactoryRegistry testSubject0 = OWLParserFactoryRegistry
                .getInstance();
        OWLParserFactoryRegistry result0 = OWLParserFactoryRegistry
                .getInstance();
        testSubject0.clearParserFactories();
        List<OWLParserFactory> result1 = testSubject0.getParserFactories();
        testSubject0.registerParserFactory(mock(OWLParserFactory.class));
        testSubject0.unregisterParserFactory(mock(OWLParserFactory.class));
    }

    @Test
    public void shouldTestInterfaceOWLRenderer() throws OWLException {
        OWLRenderer testSubject0 = mock(OWLRenderer.class);
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
    }

    @Test
    public void shouldTestOWLXMLOntologyFormat() throws OWLException {
        OWLXMLOntologyFormat testSubject0 = new OWLXMLOntologyFormat();
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

    @Ignore
    @Test
    public void shouldTestRDFOntologyFormat() throws OWLException {
        RDFOntologyFormat testSubject0 = new RDFOntologyFormat() {
            private static final long serialVersionUID = 40000L;
        };
        RDFParserMetaData result0 = testSubject0.getOntologyLoaderMetaData();
        OWLOntologyLoaderMetaData result1 = testSubject0
                .getOntologyLoaderMetaData();
        boolean result2 = testSubject0.isAddMissingTypes();
        boolean result3 = RDFOntologyFormat.isMissingType(
                Utils.mockOWLEntity(), Utils.getMockOntology());
        testSubject0.setAddMissingTypes(false);
        testSubject0.addError(mock(RDFResourceParseError.class));
        String result4 = testSubject0.getPrefix("");
        IRI result5 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clear();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result6 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result7 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result8 = testSubject0.containsPrefixMapping("");
        String result9 = testSubject0.getDefaultPrefix();
        String result10 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result11 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result12 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result13 = testSubject0
                .asPrefixOWLOntologyFormat();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestRDFOntologyHeaderStatus() throws OWLException {
        RDFOntologyHeaderStatus testSubject0 = RDFOntologyHeaderStatus.PARSED_MULTIPLE_HEADERS;
        RDFOntologyHeaderStatus[] result0 = RDFOntologyHeaderStatus.values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestRDFParserMetaData() throws OWLException {
        RDFParserMetaData testSubject0 = new RDFParserMetaData(
                RDFOntologyHeaderStatus.PARSED_ZERO_HEADERS, 0,
                Utils.mockSet(mock(RDFTriple.class)));
        int result0 = testSubject0.getTripleCount();
        RDFOntologyHeaderStatus result1 = testSubject0.getHeaderState();
        Set<RDFTriple> result2 = testSubject0.getUnparsedTriples();
    }

    @Test
    public void shouldTestRDFResourceParseError() throws OWLException {
        RDFResourceParseError testSubject0 = new RDFResourceParseError(
                Utils.mockOWLEntity(), mock(RDFNode.class),
                Utils.mockSet(mock(RDFTriple.class)));
        OWLEntity result0 = testSubject0.getParserGeneratedErrorEntity();
        RDFNode result1 = testSubject0.getMainNode();
        Set<RDFTriple> result2 = testSubject0.getMainNodeTriples();
    }

    @Ignore
    @Test
    public void shouldTestRDFXMLOntologyFormat() throws OWLException {
        RDFXMLOntologyFormat testSubject0 = new RDFXMLOntologyFormat();
        RDFParserMetaData result1 = testSubject0.getOntologyLoaderMetaData();
        OWLOntologyLoaderMetaData result2 = testSubject0
                .getOntologyLoaderMetaData();
        boolean result3 = testSubject0.isAddMissingTypes();
        boolean result4 = RDFOntologyFormat.isMissingType(
                Utils.mockOWLEntity(), Utils.getMockOntology());
        testSubject0.setAddMissingTypes(false);
        testSubject0.addError(mock(RDFResourceParseError.class));
        String result5 = testSubject0.getPrefix("");
        IRI result6 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clear();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result7 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result8 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result9 = testSubject0.containsPrefixMapping("");
        String result10 = testSubject0.getDefaultPrefix();
        String result11 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result12 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result13 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result14 = testSubject0
                .asPrefixOWLOntologyFormat();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestStreamDocumentSource() throws OWLException {
        StreamDocumentSource testSubject0 = new StreamDocumentSource(
                mock(InputStream.class));
        StreamDocumentSource testSubject1 = new StreamDocumentSource(
                mock(InputStream.class), IRI("urn:aFake"));
        boolean result3 = testSubject0.isInputStreamAvailable();
        if (result3) {
            InputStream result0 = testSubject0.getInputStream();
        }
        boolean result1 = testSubject0.isReaderAvailable();
        if (result1) {
            Reader result2 = testSubject0.getReader();
        }
        IRI result4 = testSubject0.getDocumentIRI();
    }

    @Test
    public void shouldTestStreamDocumentTarget() throws OWLException {
        StreamDocumentTarget testSubject0 = new StreamDocumentTarget(
                mock(OutputStream.class));
        OutputStream result0 = testSubject0.getOutputStream();
        boolean result3 = testSubject0.isDocumentIRIAvailable();
        if (result3) {
            IRI result1 = testSubject0.getDocumentIRI();
        }
        boolean result4 = testSubject0.isOutputStreamAvailable();
        if (result4) {
            OutputStream result2 = testSubject0.getOutputStream();
        }
    }

    @Test
    public void shouldTestStringDocumentSource() throws OWLException {
        StringDocumentSource testSubject0 = new StringDocumentSource("");
        StringDocumentSource testSubject1 = new StringDocumentSource("",
                IRI("urn:aFake"));
        boolean result1 = testSubject0.isReaderAvailable();
        if (result1) {
            Reader result2 = testSubject0.getReader();
        }
        boolean result3 = testSubject0.isInputStreamAvailable();
        if (result3) {
            InputStream result0 = testSubject0.getInputStream();
        }
        IRI result4 = testSubject0.getDocumentIRI();
        IRI result5 = StringDocumentSource.getNextDocumentIRI();
    }

    @Test
    public void shouldTestStringDocumentTarget() throws OWLException {
        StringDocumentTarget testSubject0 = new StringDocumentTarget();
        boolean result5 = testSubject0.isOutputStreamAvailable();
        if (result5) {
            OutputStream result1 = testSubject0.getOutputStream();
        }
        boolean result3 = testSubject0.isWriterAvailable();
        if (result3) {
            Writer result4 = testSubject0.getWriter();
        }
        boolean result6 = testSubject0.isDocumentIRIAvailable();
        if (result6) {
            IRI result2 = testSubject0.getDocumentIRI();
        }
    }

    @Test
    public void shouldTestSystemOutDocumentTarget() throws OWLException,
            IOException {
        SystemOutDocumentTarget testSubject0 = new SystemOutDocumentTarget();
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        if (result5) {
            IRI result1 = testSubject0.getDocumentIRI();
        }
        boolean result2 = testSubject0.isWriterAvailable();
        if (result2) {
            Writer result3 = testSubject0.getWriter();
        }
        boolean result4 = testSubject0.isOutputStreamAvailable();
        if (result4) {
            OutputStream result0 = testSubject0.getOutputStream();
        }
    }

    @Ignore
    @Test
    public void shouldTestToStringRenderer() throws OWLException {
        ToStringRenderer testSubject0 = ToStringRenderer.getInstance();
        ToStringRenderer result0 = ToStringRenderer.getInstance();
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        testSubject0.setRenderer(mock(OWLObjectRenderer.class));
        String result2 = testSubject0.getRendering(mock(OWLObject.class));
    }

    @Test
    public void shouldTestWriterDocumentTarget() throws OWLException {
        WriterDocumentTarget testSubject0 = new WriterDocumentTarget(
                mock(Writer.class));
        boolean result2 = testSubject0.isWriterAvailable();
        if (result2) {
            Writer result3 = testSubject0.getWriter();
        }
        boolean result4 = testSubject0.isOutputStreamAvailable();
        if (result4) {
            OutputStream result0 = testSubject0.getOutputStream();
        }
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        if (result5) {
            IRI result1 = testSubject0.getDocumentIRI();
        }
    }

    @Test
    public void shouldTestXMLUtils() throws OWLException {
        XMLUtils testSubject0 = new XMLUtils();
        boolean result0 = XMLUtils.isXMLNameStartCharacter(0);
        boolean result1 = XMLUtils.isXMLNameChar(0);
        boolean result2 = XMLUtils.isNCNameStartChar(0);
        boolean result3 = XMLUtils.isNCNameChar(0);
        boolean result4 = XMLUtils.isNCName(mock(CharSequence.class));
        boolean result5 = XMLUtils.isQName(mock(CharSequence.class));
        boolean result6 = XMLUtils.hasNCNameSuffix(mock(CharSequence.class));
        int result7 = XMLUtils.getNCNameSuffixIndex(mock(CharSequence.class));
        String result8 = XMLUtils.getNCNameSuffix(mock(CharSequence.class));
        String result9 = XMLUtils.getNCNamePrefix(mock(CharSequence.class));
        String result10 = XMLUtils.escapeXML(mock(CharSequence.class));
    }

    @Ignore
    @Test
    public void shouldTestZipDocumentTarget() throws OWLException, IOException {
        ZipDocumentTarget testSubject0 = new ZipDocumentTarget(mock(File.class));
        boolean result2 = testSubject0.isWriterAvailable();
        if (result2) {
            Writer result3 = testSubject0.getWriter();
        }
        boolean result4 = testSubject0.isOutputStreamAvailable();
        if (result4) {
            OutputStream result0 = testSubject0.getOutputStream();
        }
        boolean result5 = testSubject0.isDocumentIRIAvailable();
        if (result5) {
            IRI result1 = testSubject0.getDocumentIRI();
        }
    }
}
