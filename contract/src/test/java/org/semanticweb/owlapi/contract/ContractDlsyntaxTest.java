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

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyDocumentTarget;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxHTMLOntologyFormat;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxHTMLOntologyStorer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyFormat;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyStorer;
import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxOntologyStorerBase;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractDlsyntaxTest {
    public void shouldTestDLSyntaxHTMLOntologyFormat() {
        DLSyntaxHTMLOntologyFormat testSubject0 = new DLSyntaxHTMLOntologyFormat();
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

    public void shouldTestDLSyntaxHTMLOntologyStorer()
            throws OWLOntologyStorageException {
        DLSyntaxHTMLOntologyStorer testSubject0 = new DLSyntaxHTMLOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
    }

    @Test
    public void shouldTestDLSyntaxObjectRenderer() {
        DLSyntaxObjectRenderer testSubject0 = new DLSyntaxObjectRenderer();
        String result0 = testSubject0.render(mock(OWLObject.class));
        testSubject0.setShortFormProvider(mock(ShortFormProvider.class));
        testSubject0.setFocusedObject(mock(OWLObject.class));
        boolean result1 = testSubject0.isFocusedObject(mock(OWLObject.class));
    }

    public void shouldTestDLSyntaxOntologyFormat() {
        DLSyntaxOntologyFormat testSubject0 = new DLSyntaxOntologyFormat();
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

    public void shouldTestDLSyntaxOntologyStorer()
            throws OWLOntologyStorageException {
        DLSyntaxOntologyStorer testSubject0 = new DLSyntaxOntologyStorer();
        boolean result0 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
    }

    public void shouldTestDLSyntaxOntologyStorerBase()
            throws OWLOntologyStorageException {
        DLSyntaxOntologyStorerBase testSubject0 = new DLSyntaxOntologyStorerBase() {
            private static final long serialVersionUID = 40000L;

            @Override
            public boolean canStoreOntology(OWLOntologyFormat ontologyFormat) {
                return false;
            }
        };
        testSubject0.storeOntology(Utils.getMockOntology(), IRI("urn:aFake"),
                mock(OWLOntologyFormat.class));
        testSubject0.storeOntology(Utils.getMockOntology(),
                mock(OWLOntologyDocumentTarget.class),
                mock(OWLOntologyFormat.class));
        boolean result1 = testSubject0
                .canStoreOntology(mock(OWLOntologyFormat.class));
    }
}
