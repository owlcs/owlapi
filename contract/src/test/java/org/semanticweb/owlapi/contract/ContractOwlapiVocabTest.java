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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Test;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;
import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.BuiltInVocabulary;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWL2Datatype.Category;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.semanticweb.owlapi.vocab.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

@SuppressWarnings({ "unused", "javadoc" })
public class ContractOwlapiVocabTest {
    @Test
    public void shouldTestBuiltInVocabulary() throws OWLException {
        BuiltInVocabulary testSubject0 = BuiltInVocabulary.DUBLIN_CORE;
        BuiltInVocabulary[] result0 = BuiltInVocabulary.values();
        String result2 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestDublinCoreVocabulary() throws OWLException {
        DublinCoreVocabulary testSubject0 = DublinCoreVocabulary.CONTRIBUTOR;
        DublinCoreVocabulary[] result1 = DublinCoreVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        String result5 = testSubject0.getShortName();
        String result6 = testSubject0.getQName();
        String result7 = testSubject0.name();
        int result12 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestNamespaces() throws OWLException {
        Namespaces testSubject0 = Namespaces.OWL;
        Namespaces[] result1 = Namespaces.values();
        String result3 = testSubject0.name();
        int result8 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWL2Datatype() throws OWLException {
        OWL2Datatype testSubject0 = OWL2Datatype.OWL_RATIONAL;
        OWL2Datatype[] result0 = OWL2Datatype.values();
        boolean result2 = testSubject0.isFinite();
        IRI result3 = testSubject0.getIRI();
        boolean result4 = OWL2Datatype.isBuiltIn(IRI("urn:aFake"));
        if (result4) {
            OWL2Datatype result5 = OWL2Datatype.getDatatype(IRI("urn:aFake"));
        }
        String result7 = testSubject0.getShortName();
        Set<IRI> result8 = OWL2Datatype.getDatatypeIRIs();
        Pattern result9 = testSubject0.getPattern();
        Category result10 = testSubject0.getCategory();
        boolean result11 = testSubject0.isNumeric();
        Collection<OWLFacet> result12 = testSubject0.getFacets();
        boolean result13 = testSubject0.isInLexicalSpace("");
        String result14 = testSubject0.name();
        int result20 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWLFacet() throws OWLException {
        OWLFacet testSubject0 = OWLFacet.FRACTION_DIGITS;
        OWLFacet[] result1 = OWLFacet.values();
        IRI result3 = testSubject0.getIRI();
        String result4 = testSubject0.getShortName();
        Set<String> result5 = OWLFacet.getFacets();
        OWLFacet result6 = OWLFacet.getFacet(IRI("urn:aFake"));
        String result7 = testSubject0.getSymbolicForm();
        Set<IRI> result8 = OWLFacet.getFacetIRIs();
        OWLFacet result9 = OWLFacet.getFacetByShortName("");
        OWLFacet result10 = OWLFacet.getFacetBySymbolicName("");
        String result11 = testSubject0.name();
        int result16 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWLRDFVocabulary() throws OWLException {
        OWLRDFVocabulary testSubject0 = OWLRDFVocabulary.OWL_ALL_DIFFERENT;
        OWLRDFVocabulary[] result1 = OWLRDFVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        Namespaces result5 = testSubject0.getNamespace();
        String result6 = testSubject0.getShortName();
        String result7 = testSubject0.name();
        int result12 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestOWLXMLVocabulary() throws OWLException {
        OWLXMLVocabulary testSubject0 = OWLXMLVocabulary.ABBREVIATED_IRI_ATTRIBUTE;
        OWLXMLVocabulary[] result1 = OWLXMLVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        String result5 = testSubject0.getShortName();
        String result6 = testSubject0.name();
        int result11 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestPrefixOWLOntologyFormat() throws OWLException {
        PrefixOWLOntologyFormat testSubject0 = new PrefixOWLOntologyFormat();
        String result0 = testSubject0.getPrefix("");
        IRI result1 = testSubject0.getIRI("");
        testSubject0.setPrefix("", "");
        testSubject0.clear();
        testSubject0.copyPrefixesFrom(new DefaultPrefixManager());
        testSubject0.copyPrefixesFrom(mock(PrefixOWLOntologyFormat.class));
        Map<String, String> result2 = testSubject0.getPrefixName2PrefixMap();
        Set<String> result3 = testSubject0.getPrefixNames();
        testSubject0.setDefaultPrefix("");
        boolean result4 = testSubject0.containsPrefixMapping("");
        String result5 = testSubject0.getDefaultPrefix();
        String result6 = testSubject0.getPrefixIRI(IRI("urn:aFake"));
        testSubject0.setParameter(mock(Object.class), mock(Object.class));
        Object result7 = testSubject0.getParameter(mock(Object.class),
                mock(Object.class));
        boolean result8 = testSubject0.isPrefixOWLOntologyFormat();
        PrefixOWLOntologyFormat result9 = testSubject0
                .asPrefixOWLOntologyFormat();
        OWLOntologyLoaderMetaData result10 = testSubject0
                .getOntologyLoaderMetaData();
        testSubject0
                .setOntologyLoaderMetaData(mock(OWLOntologyLoaderMetaData.class));
    }

    @Test
    public void shouldTestSKOSVocabulary() throws OWLException {
        SKOSVocabulary testSubject0 = SKOSVocabulary.ALTLABEL;
        SKOSVocabulary[] result0 = SKOSVocabulary.values();
        Set<OWLClass> result2 = SKOSVocabulary
                .getClasses(mock(OWLDataFactory.class));
        IRI result3 = testSubject0.getIRI();
        EntityType<?> result4 = testSubject0.getEntityType();
        Set<OWLAnnotationProperty> result6 = SKOSVocabulary
                .getAnnotationProperties(mock(OWLDataFactory.class));
        String result7 = testSubject0.getLocalName();
        Set<OWLObjectProperty> result8 = SKOSVocabulary
                .getObjectProperties(mock(OWLDataFactory.class));
        Set<OWLDataProperty> result9 = SKOSVocabulary
                .getDataProperties(mock(OWLDataFactory.class));
        String result10 = testSubject0.name();
        int result16 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestSWRLBuiltInsVocabulary() throws OWLException {
        SWRLBuiltInsVocabulary testSubject0 = SWRLBuiltInsVocabulary.ABS;
        SWRLBuiltInsVocabulary[] result0 = SWRLBuiltInsVocabulary.values();
        IRI result2 = testSubject0.getIRI();
        String result4 = testSubject0.getShortName();
        int result5 = testSubject0.getMinArity();
        int result6 = testSubject0.getMaxArity();
        SWRLBuiltInsVocabulary result8 = SWRLBuiltInsVocabulary
                .getBuiltIn(IRI("urn:aFake"));
        String result10 = testSubject0.name();
        int result16 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestSWRLVocabulary() throws OWLException {
        SWRLVocabulary testSubject0 = SWRLVocabulary.ARGUMENT_1;
        SWRLVocabulary[] result0 = SWRLVocabulary.values();
        IRI result2 = testSubject0.getIRI();
        String result4 = testSubject0.getShortName();
        String result5 = testSubject0.name();
        int result11 = testSubject0.ordinal();
    }

    @Test
    public void shouldTestXSDVocabulary() throws OWLException {
        XSDVocabulary testSubject0 = XSDVocabulary.ANY_SIMPLE_TYPE;
        XSDVocabulary[] result1 = XSDVocabulary.values();
        IRI result3 = testSubject0.getIRI();
        String result4 = testSubject0.getShortName();
        String result5 = testSubject0.name();
        int result10 = testSubject0.ordinal();
    }
}
