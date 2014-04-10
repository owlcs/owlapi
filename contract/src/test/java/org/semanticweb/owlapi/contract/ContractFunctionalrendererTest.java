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
import java.util.Set;

import org.junit.Test;
import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxOntologyStorer;
import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxRenderer;
import org.semanticweb.owlapi.functional.renderer.FunctionalSyntaxObjectRenderer;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractFunctionalrendererTest {

    public void shouldTestOWLFunctionalSyntaxOntologyStorer()
            throws OWLOntologyStorageException {
        OWLFunctionalSyntaxOntologyStorer testSubject0 = new OWLFunctionalSyntaxOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
    }

    @Test
    public void shouldTestOWLFunctionalSyntaxRenderer()
            throws OWLRendererException {
        OWLFunctionalSyntaxRenderer testSubject0 = new OWLFunctionalSyntaxRenderer();
        testSubject0.render(Utils.getMockOntology(), mock(Writer.class));
        testSubject0.render(Utils.getMockOntology(), mock(OutputStream.class));
    }

    public void shouldTestOWLObjectRenderer() {
        FunctionalSyntaxObjectRenderer testSubject0 = new FunctionalSyntaxObjectRenderer(
                Utils.getMockOntology(), mock(Writer.class));
        testSubject0.write(mock(OWLAnnotation.class));
        testSubject0.write(OWLXMLVocabulary.COMMENT, mock(OWLObject.class));
        Set<OWLAxiom> result0 = testSubject0.writeAnnotations(Utils
                .mockOWLEntity());
        testSubject0.writeAnnotations(mock(OWLAxiom.class));
        testSubject0.writeOpenBracket();
        testSubject0.writeSpace();
        testSubject0.writeCloseBracket();
        testSubject0.setPrefixManager(mock(DefaultPrefixManager.class));
        testSubject0.setFocusedObject(mock(OWLObject.class));
        testSubject0.writePrefix("", "");
        testSubject0.writePrefixes();
        Set<OWLAxiom> result1 = testSubject0.writeAxioms(Utils.mockOWLEntity());
        Set<OWLAxiom> result2 = testSubject0.writeDeclarations(Utils
                .mockOWLEntity());
        testSubject0.writeAxiomStart(OWLXMLVocabulary.COMMENT,
                mock(OWLAxiom.class));
        testSubject0.writeAxiomEnd();
        testSubject0.writePropertyCharacteristic(OWLXMLVocabulary.COMMENT,
                mock(OWLAxiom.class), mock(OWLPropertyExpression.class));
    }
}
