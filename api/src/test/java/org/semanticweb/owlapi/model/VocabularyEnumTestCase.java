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
package org.semanticweb.owlapi.model;

import static java.util.Arrays.stream;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;
import static org.semanticweb.owlapi.vocab.Namespaces.DC;
import static org.semanticweb.owlapi.vocab.Namespaces.OWL;
import static org.semanticweb.owlapi.vocab.Namespaces.SKOS;
import static org.semanticweb.owlapi.vocab.Namespaces.SWRL;
import static org.semanticweb.owlapi.vocab.Namespaces.SWRLB;
import static org.semanticweb.owlapi.vocab.Namespaces.XSD;

import java.util.Collection;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.vocab.OWLXMLVocabulary;
import org.semanticweb.owlapi.vocab.SKOSVocabulary;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;
import org.semanticweb.owlapi.vocab.XSDVocabulary;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group
 * @since 3.5.0
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class VocabularyEnumTestCase {

    private final HasIRI iri;
    private final HasPrefixedName name;
    private final Namespaces expected;

    public VocabularyEnumTestCase(Object in, Namespaces expected) {
        iri = (HasIRI) in;
        name = (HasPrefixedName) in;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> getData() {
        return asList(concat(
            stream(DublinCoreVocabulary.values())
                .map(input -> new Object[]{input, DC}),
            stream(OWLRDFVocabulary.values())
                .map(input -> new Object[]{input, input.getNamespace()}),
            stream(OWLXMLVocabulary.values()).map(input -> new Object[]{input, OWL}),
            stream(SKOSVocabulary.values()).map(input -> new Object[]{input, SKOS}),
            stream(SWRLBuiltInsVocabulary.values())
                .map(input -> new Object[]{input, SWRLB}),
            stream(SWRLVocabulary.values()).map(input -> new Object[]{input, SWRL}),
            stream(XSDVocabulary.values()).map(input -> new Object[]{input, XSD})));
    }

    @SafeVarargs
    private static <T> Stream<T> concat(Stream<T>... values) {
        Stream<T> toReturn = values[0];
        for (int i = 1; i < values.length; i++) {
            toReturn = Stream.concat(toReturn, values[i]);
        }
        return toReturn;
    }

    @Test
    public void getPrefixedNameShouldStartWithDublinCorePrefixName() {
        assertTrue(name.getPrefixedName().startsWith(expected.getPrefixName()));
    }

    @Test
    public void getIRIShouldReturnAnIRIThatStartsWithDublinCorePrefix() {
        assertThat(iri.getIRI().getNamespace(), equalTo(expected.getPrefixIRI()));
    }
}
