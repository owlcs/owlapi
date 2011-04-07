/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
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
 * Copyright 2011, University of Manchester
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

package org.semanticweb.owlapi.api.test;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22/12/2010
 */
public class AnnotationShortFormProviderTestCase extends AbstractOWLAPITestCase {


    public void testLiteralWithoutLanguageValue() throws Exception {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        PrefixManager pm = new DefaultPrefixManager("http://org.semanticweb.owlapi/ont#");
        OWLAnnotationProperty prop = AnnotationProperty("prop", pm);
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        String shortForm = "MyLabel";
        Ontology(
                man,
                AnnotationAssertion(prop, root.getIRI(), Literal(shortForm))
        );
        List<OWLAnnotationProperty> props = Arrays.asList(prop);
        Map<OWLAnnotationProperty, List<String>> langMap = Collections.emptyMap();
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(props, langMap, man);
        assertEquals(sfp.getShortForm(root), shortForm);
    }

    public void testLiteralWithLanguageValue() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        PrefixManager pm = new DefaultPrefixManager("http://org.semanticweb.owlapi/ont#");
        OWLAnnotationProperty prop = AnnotationProperty("prop", pm);
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        String label1 = "MyLabel";
        String label2 = "OtherLabel";
        Ontology(man, AnnotationAssertion(prop, root.getIRI(), Literal(label1, "ab")), AnnotationAssertion(prop, root.getIRI(), Literal(label2, "xy")));
        List<OWLAnnotationProperty> props = Arrays.asList(prop);
        Map<OWLAnnotationProperty, List<String>> langMap = new HashMap<OWLAnnotationProperty, List<String>>();
        langMap.put(prop, Arrays.asList("ab", "xy"));
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(props, langMap, man);
        assertEquals(sfp.getShortForm(root), label1);

        Map<OWLAnnotationProperty, List<String>> langMap2 = new HashMap<OWLAnnotationProperty, List<String>>();
        langMap2.put(prop, Arrays.asList("xy", "ab"));
        AnnotationValueShortFormProvider sfp2 = new AnnotationValueShortFormProvider(props, langMap2, man);
        assertEquals(sfp2.getShortForm(root), label2);
    }

    public void testIRIValue() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        PrefixManager pm = new DefaultPrefixManager("http://org.semanticweb.owlapi/ont#");
        OWLAnnotationProperty prop = AnnotationProperty("prop", pm);
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        Ontology(
                man,
                AnnotationAssertion(prop, root.getIRI(), IRI("http://org.semanticweb.owlapi/ont#myIRI"))
        );
        List<OWLAnnotationProperty> props = Arrays.asList(prop);
        Map<OWLAnnotationProperty, List<String>> langMap = Collections.emptyMap();
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(props, langMap, man);
        assertEquals(sfp.getShortForm(root), "myIRI");
    }



}
