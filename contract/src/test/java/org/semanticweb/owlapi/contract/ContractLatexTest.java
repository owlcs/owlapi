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

import java.io.OutputStream;
import java.io.Writer;

import org.junit.Test;
import org.semanticweb.owlapi.formats.LatexAxiomsListOntologyFormat;
import org.semanticweb.owlapi.formats.LatexOntologyFormat;
import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.latex.renderer.LatexBracketChecker;
import org.semanticweb.owlapi.latex.renderer.LatexOWLObjectRenderer;
import org.semanticweb.owlapi.latex.renderer.LatexObjectVisitor;
import org.semanticweb.owlapi.latex.renderer.LatexOntologyStorer;
import org.semanticweb.owlapi.latex.renderer.LatexRenderer;
import org.semanticweb.owlapi.latex.renderer.LatexWriter;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.util.ShortFormProvider;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractLatexTest {

    public void shouldTestLatexAxiomsListOntologyFormat() throws OWLException {
        LatexAxiomsListOntologyFormat testSubject0 = new LatexAxiomsListOntologyFormat();
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

    @Test
    public void shouldTestLatexBracketChecker() throws OWLException {
        boolean result0 = LatexBracketChecker.requiresBracket(Utils
                .mockAnonClass());
    }

    @Test
    public void shouldTestLatexObjectVisitor() throws OWLException {
        LatexObjectVisitor testSubject0 = new LatexObjectVisitor(
                mock(LatexWriter.class), mock(OWLDataFactory.class));
        testSubject0.setSubject(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        boolean result0 = testSubject0.isPrettyPrint();
        testSubject0.setPrettyPrint(false);
    }

    public void shouldTestLatexOntologyFormat() throws OWLException {
        LatexOntologyFormat testSubject0 = new LatexOntologyFormat();
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

    public void shouldTestLatexOntologyStorer() throws OWLException {
        LatexOntologyStorer testSubject0 = new LatexOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
    }

    @Test
    public void shouldTestLatexOWLObjectRenderer() throws OWLException {
        LatexOWLObjectRenderer testSubject0 = new LatexOWLObjectRenderer(
                mock(OWLDataFactory.class));
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
    }

    @Test
    public void shouldTestLatexRenderer() throws OWLException {
        LatexRenderer testSubject0 = new LatexRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
    }

    @Test
    public void shouldTestLatexWriter() throws OWLException {
        LatexWriter testSubject0 = new LatexWriter(mock(Writer.class));
        testSubject0.write(mock(Object.class));
        testSubject0.flush();
        testSubject0.writeSpace();
        testSubject0.writeOpenBrace();
        testSubject0.writeCloseBrace();
        testSubject0.writeNewLine();
    }
}
