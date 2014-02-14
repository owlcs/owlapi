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
package org.semanticweb.owlapi.api.test.syntax;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.search.Searcher.find;

import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.formats.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.formats.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

/** test for 3294629 - currently disabled. Not clear whether structure sharing is
 * allowed or disallowed. Data is equivalent, ontology annotations are not */
@SuppressWarnings("javadoc")
@Ignore
public class SharedBlankNodeTestCase extends TestBase {
    @Test
    public void shouldSaveOneIndividual() throws OWLOntologyStorageException,
            OWLOntologyCreationException {
        OWLOntology ontology = createOntology();
        testAnnotation(ontology);
        String s = saveOntology(ontology);
        ontology = loadOntologyFromString(s);
        testAnnotation(ontology);
    }

    public OWLOntology createOntology() throws OWLOntologyCreationException {
        String NS = "urn:test";
        OWLDataProperty P = DataProperty(IRI(NS + "#p"));
        OWLObjectProperty P1 = ObjectProperty(IRI(NS + "#p1"));
        OWLObjectProperty P2 = ObjectProperty(IRI(NS + "#p2"));
        OWLAnnotationProperty ann = AnnotationProperty(IRI(NS + "#ann"));
        OWLOntology ontology = m.createOntology(IRI(NS));
        OWLAnonymousIndividual i = AnonymousIndividual();
        m.addAxiom(ontology, Declaration(P));
        m.addAxiom(ontology, Declaration(P1));
        m.addAxiom(ontology, Declaration(P2));
        m.addAxiom(ontology, Declaration(ann));
        m.applyChange(new AddOntologyAnnotation(ontology, Annotation(ann, i)));
        OWLAxiom ass = DataPropertyAssertion(P, i, Literal("hello world"));
        OWLNamedIndividual ind = NamedIndividual(IRI(NS + "#test"));
        OWLAxiom ax1 = ObjectPropertyAssertion(P1, ind, i);
        OWLAxiom ax2 = ObjectPropertyAssertion(P2, ind, i);
        m.addAxiom(ontology, ass);
        m.addAxiom(ontology, ax1);
        m.addAxiom(ontology, ax2);
        return ontology;
    }

    public static String saveOntology(OWLOntology ontology)
            throws OWLOntologyStorageException {
        StringDocumentTarget target = new StringDocumentTarget();
        ontology.getOWLOntologyManager().saveOntology(ontology,
                new RDFXMLOntologyFormat(), target);
        return target.toString();
    }

    public static void displayOntology(OWLOntology ontology)
            throws OWLOntologyStorageException {
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLFunctionalSyntaxOntologyFormat format = new OWLFunctionalSyntaxOntologyFormat();
        manager.saveOntology(ontology, format, new SystemOutDocumentTarget());
    }

    public static void testAnnotation(OWLOntology ontology) {
        for (OWLAnnotation annotation : ontology.getAnnotations()) {
            OWLIndividual i = (OWLIndividual) annotation.getValue();
            System.out.println("Found individual " + i);
            System.out.println("Property values = "
                    + find().in(ontology).individual(i));
        }
    }
}
