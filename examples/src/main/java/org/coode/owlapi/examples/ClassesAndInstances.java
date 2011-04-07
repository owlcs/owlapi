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
 * Copyright 2011, The University of Manchester
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

package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Dec-2009
 */
public class ClassesAndInstances {

    public static void main(String[] args) {
        try {
            // For more information on classes and instances see the OWL 2 Primer
            // http://www.w3.org/TR/2009/REC-owl2-primer-20091027/#Classes_and_Instances

            // In order to say that an individual is an instance of a class (in an ontology), we can add a ClassAssertion
            // to the ontology.

            // For example, suppose we wanted to specify that :Mary is an instance of the class :Person.
            // First we need to obtain the individual :Mary and the class :Person

            // Create an ontology manager to work with
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory dataFactory = manager.getOWLDataFactory();

            // The IRIs used here are taken from the OWL 2 Primer
            String base = "http://example.com/owl/families/";
            PrefixManager pm = new DefaultPrefixManager(base);

            // Get the reference to the :Person class (the full IRI will be <http://example.com/owl/families/Person>)
            OWLClass person = dataFactory.getOWLClass(":Person", pm);

            // Get the reference to the :Mary class (the full IRI will be <http://example.com/owl/families/Mary>)
            OWLNamedIndividual mary = dataFactory.getOWLNamedIndividual(":Mary", pm);

            // Now create a ClassAssertion to specify that :Mary is an instance of :Person
            OWLClassAssertionAxiom classAssertion = dataFactory.getOWLClassAssertionAxiom(person, mary);

            // We need to add the class assertion to the ontology that we want specify that :Mary is a :Person
            OWLOntology ontology = manager.createOntology(IRI.create(base));

            // Add the class assertion
            manager.addAxiom(ontology, classAssertion);

            // Dump the ontology to stdout
            manager.saveOntology(ontology, new SystemOutDocumentTarget());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not create the ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Could not save ontology: " + e.getMessage());
        }
    }
}
