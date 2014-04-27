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
package org.semanticweb.owlapi.api.test.annotations;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValueVisitorEx;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleIRIShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

/**
 * @author Matthew Horridge, The University of Manchester, Bio-Health
 *         Informatics Group
 * @since 3.1.0
 */
@SuppressWarnings("javadoc")
public class AnnotationShortFormProviderTestCase extends TestBase {

    @Nonnull
    PrefixManager pm = new DefaultPrefixManager(null, null,
            "http://org.semanticweb.owlapi/ont#");
    @Nonnull
    OWLAnnotationProperty prop = AnnotationProperty("prop", pm);
    @SuppressWarnings("null")
    @Nonnull
    List<OWLAnnotationProperty> props = Arrays.asList(prop);
    @Nonnull
    Map<OWLAnnotationProperty, List<String>> langMap = new HashMap<OWLAnnotationProperty, List<String>>();

    @Test
    public void testLiteralWithoutLanguageValue()
            throws OWLOntologyCreationException {
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        String shortForm = "MyLabel";
        Ontology(m,
                AnnotationAssertion(prop, root.getIRI(), Literal(shortForm)));
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(
                props, langMap, m);
        assertEquals(sfp.getShortForm(root), shortForm);
    }

    @Test
    public void testLiteralWithLanguageValue()
            throws OWLOntologyCreationException {
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        String label1 = "MyLabel";
        String label2 = "OtherLabel";
        Ontology(
                m,
                AnnotationAssertion(prop, root.getIRI(), Literal(label1, "ab")),
                AnnotationAssertion(prop, root.getIRI(), Literal(label2, "xy")));
        langMap.put(prop, Arrays.asList("ab", "xy"));
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(
                props, langMap, m);
        assertEquals(sfp.getShortForm(root), label1);
        Map<OWLAnnotationProperty, List<String>> langMap2 = new HashMap<OWLAnnotationProperty, List<String>>();
        langMap2.put(prop, Arrays.asList("xy", "ab"));
        AnnotationValueShortFormProvider sfp2 = new AnnotationValueShortFormProvider(
                props, langMap2, m);
        assertEquals(sfp2.getShortForm(root), label2);
    }

    @Test
    public void testIRIValue() throws OWLOntologyCreationException {
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        Ontology(
                m,
                AnnotationAssertion(prop, root.getIRI(),
                        IRI("http://org.semanticweb.owlapi/ont#myIRI")));
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(
                props, langMap, m);
        assertEquals(sfp.getShortForm(root), "myIRI");
    }

    @Test
    public void shouldWrapWithDoubleQuotes()
            throws OWLOntologyCreationException {
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        String shortForm = "MyLabel";
        Ontology(m,
                AnnotationAssertion(prop, root.getIRI(), Literal(shortForm)));
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(
                m, new SimpleShortFormProvider(),
                new SimpleIRIShortFormProvider(), props, langMap,
                new OWLAnnotationValueVisitorEx<String>() {

                    @Nonnull
                    @Override
                    public String visit(@Nonnull OWLLiteral literal) {
                        return "\"" + literal.getLiteral() + "\"";
                    }

                    @SuppressWarnings("unused")
                    @Override
                    public String visit(OWLAnonymousIndividual individual) {
                        return "";
                    }

                    @SuppressWarnings("unused")
                    @Override
                    public String visit(IRI iri) {
                        return "";
                    }
                });
        String shortForm2 = sfp.getShortForm(root);
        assertEquals(shortForm2, "\"" + shortForm + "\"");
    }
}
