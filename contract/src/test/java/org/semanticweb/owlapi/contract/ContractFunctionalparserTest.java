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
import java.io.InputStream;
import java.io.Reader;

import org.junit.Test;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.functional.parser.JJTOWLFunctionalSyntaxParserState;
import org.semanticweb.owlapi.functional.parser.Node;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParser;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParser;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserConstants;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserTokenManager;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxParserTreeConstants;
import org.semanticweb.owlapi.functional.parser.ParseException;
import org.semanticweb.owlapi.functional.parser.Token;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractFunctionalparserTest {

    public void shouldTestJJTOWLFunctionalSyntaxParserState() {
        JJTOWLFunctionalSyntaxParserState testSubject0 = new JJTOWLFunctionalSyntaxParserState();
        testSubject0.reset();
        boolean result0 = testSubject0.nodeCreated();
        Node result1 = testSubject0.rootNode();
        testSubject0.pushNode(mock(Node.class));
        Node result2 = testSubject0.popNode();
        Node result3 = testSubject0.peekNode();
        int result4 = testSubject0.nodeArity();
        testSubject0.clearNodeScope(mock(Node.class));
        testSubject0.openNodeScope(mock(Node.class));
        testSubject0.closeNodeScope(mock(Node.class), 0);
        testSubject0.closeNodeScope(mock(Node.class), false);
    }

    @Test
    public void shouldTestInterfaceNode() {
        Node testSubject0 = mock(Node.class);
        testSubject0.jjtOpen();
        testSubject0.jjtSetParent(mock(Node.class));
        testSubject0.jjtAddChild(mock(Node.class), 0);
        testSubject0.jjtClose();
        Node result0 = testSubject0.jjtGetParent();
        Node result1 = testSubject0.jjtGetChild(0);
        int result2 = testSubject0.jjtGetNumChildren();
    }

    public void shouldTestOWLFunctionalSyntaxOWLParser()
            throws OWLOntologyChangeException, UnloadableImportException,
            OWLParserException, IOException {
        OWLFunctionalSyntaxOWLParser testSubject0 = new OWLFunctionalSyntaxOWLParser();
        OWLOntologyFormat result0 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology());
        OWLOntologyFormat result1 = testSubject0.parse(
                mock(OWLOntologyDocumentSource.class), Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        OWLOntologyFormat result2 = testSubject0.parse(IRI.create("urn:aFake"),
                Utils.getMockOntology());
    }

    public void shouldTestOWLFunctionalSyntaxParser() throws ParseException,
            UnloadableImportException, OWLParserException, IOException {
        OWLFunctionalSyntaxParser testSubject0 = new OWLFunctionalSyntaxParser(
                mock(OWLFunctionalSyntaxParserTokenManager.class));
        new OWLFunctionalSyntaxParser(mock(InputStream.class));
        new OWLFunctionalSyntaxParser(mock(InputStream.class), "");
        new OWLFunctionalSyntaxParser(mock(Reader.class));
        OWLFunctionalSyntaxOntologyFormat result0 = testSubject0.parse();
        IRI result1 = testSubject0.getIRI("");
        testSubject0.ReInit(mock(InputStream.class));
        testSubject0.ReInit(mock(InputStream.class), "");
        testSubject0.ReInit(mock(OWLFunctionalSyntaxParserTokenManager.class));
        Token result80 = testSubject0.getNextToken();
        Token result82 = testSubject0.getToken(0);
        testSubject0.setIgnoreAnnotationsAndDeclarations(false);
        testSubject0.setUp(Utils.getMockOntology(),
                new OWLOntologyLoaderConfiguration());
        testSubject0.setPrefixes(new DefaultPrefixManager());
    }

    @Test
    public void shouldTestInterfaceOWLFunctionalSyntaxParserConstants() {
        OWLFunctionalSyntaxParserConstants testSubject0 = mock(OWLFunctionalSyntaxParserConstants.class);
    }

    @Test
    public void shouldTestInterfaceOWLFunctionalSyntaxParserTreeConstants() {
        OWLFunctionalSyntaxParserTreeConstants testSubject0 = mock(OWLFunctionalSyntaxParserTreeConstants.class);
    }
}
