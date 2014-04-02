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
package org.semanticweb.owlapi.api.test.syntax;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.Factory;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyCreationIOException;
import org.semanticweb.owlapi.io.StringDocumentSource;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.io.UnparsableOntologyException;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

@SuppressWarnings("javadoc")
public class Utf8RoundTrip001TestCase {

    @Test
    public void testUTF8roundTrip() throws OWLOntologyStorageException,
            OWLOntologyCreationException {
        OWLOntologyManager manager = Factory.getManager();
        String onto = "Ontology(<http://protege.org/UTF8.owl>"
                + "Declaration(Class(<http://protege.org/UTF8.owl#A>))"
                + "AnnotationAssertion(<http://www.w3.org/2000/01/rdf-schema#label> <http://protege.org/UTF8.owl#A> "
                + "\"Chinese=處方\"^^<http://www.w3.org/2001/XMLSchema#string>))";
        ByteArrayInputStream in = new ByteArrayInputStream(onto.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        manager.saveOntology(manager.loadOntologyFromOntologyDocument(in), out);
    }

    @Test
    public void testInvalidUTF8roundTrip() {
        // this test checks for the condition described in issue #47
        // Input with character = 0240 (octal) should fail parsing but is read
        // in as an owl/xml file
        OWLOntologyManager manager = Factory.getManager();
        try {
            String onto = "<!DOCTYPE rdf:RDF [\n"
                    + "<!ENTITY xsd \"http://www.w3.org/2001/XMLSchema#\" >\n"
                    + "]>\n"
                    + "<rdf:RDF \n"
                    + "xml:base=\n"
                    + "\"http://www.example.org/ISA14#\" \n"
                    + "xmlns:owl =\"http://www.w3.org/2002/07/owl#\" \n"
                    + "xmlns:rdf =\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" \n"
                    + "xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" \n"
                    + "xmlns:xsd =\"http://www.w3.org/2001/XMLSchema#\" \n"
                    + "xmlns:ibs =\"http://www.example.org/ISA14#\" >\n"
                    + "<owl:Ontology rdf:about=\"#\" />\n"
                    + (char) Integer.valueOf("240", 8).intValue()
                    + "<owl:Class rdf:about=\"http://www.example.org/ISA14#Researcher\"/>\n"
                    + "</rdf:RDF>";
            ByteArrayInputStream in = new ByteArrayInputStream(onto.getBytes());
            manager.loadOntologyFromOntologyDocument(in);
        } catch (UnparsableOntologyException e) {
            // building with java 6 produces different exceptions
        } catch (OWLOntologyCreationIOException e) {
            // building with java 6 produces different exceptions
        } catch (OWLOntologyCreationException e) {
            // building with java 6 produces different exceptions
        }
    }

    @Test
    public void testPositiveUTF8roundTrip()
            throws OWLOntologyCreationException, OWLOntologyStorageException {
        String NS = "http://protege.org/UTF8.owl";
        OWLOntologyManager manager;
        OWLOntology ontology;
        manager = Factory.getManager();
        ontology = manager.createOntology(IRI(NS));
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLClass a = Class(IRI(NS + "#A"));
        manager.addAxiom(ontology, factory.getOWLDeclarationAxiom(a));
        OWLAnnotationAssertionAxiom axiom = factory
                .getOWLAnnotationAssertionAxiom(a.getIRI(), factory
                        .getOWLAnnotation(factory.getRDFSLabel(),
                                factory.getOWLLiteral("Chinese=處方")));
        manager.addAxiom(ontology, axiom);
        StringDocumentTarget ontFile = new StringDocumentTarget();
        manager.saveOntology(ontology, new OWLFunctionalSyntaxOntologyFormat(),
                ontFile);
        manager = Factory.getManager();
        ontology = manager
                .loadOntologyFromOntologyDocument(new StringDocumentSource(
                        ontFile.toString()));
    }
}
