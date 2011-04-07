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
import org.semanticweb.owlapi.model.*;

import java.net.URI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-May-2007<br><br>
 * <p/>
 * This example shows how add an object property assertion
 * (triple) of the form prop(subject, object) for example
 * hasPart(a, b)
 */
public class Example4 {

    public static void main(String[] args) {
        try {
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();

            String base = "http://www.semanticweb.org/ontologies/individualsexample";

            OWLOntology ont = man.createOntology(IRI.create(base));

            OWLDataFactory dataFactory = man.getOWLDataFactory();

            // In this case, we would like to state that matthew has a father
            // who is peter.
            // We need a subject and object - matthew is the subject and peter is the
            // object.  We use the data factory to obtain references to these individuals
            OWLIndividual matthew = dataFactory.getOWLNamedIndividual(IRI.create(base + "#matthew"));
            OWLIndividual peter = dataFactory.getOWLNamedIndividual(IRI.create(base + "#peter"));
            // We want to link the subject and object with the hasFather property, so use the data factory
            // to obtain a reference to this object property.
            OWLObjectProperty hasFather = dataFactory.getOWLObjectProperty(IRI.create(base + "#hasFather"));
            // Now create the actual assertion (triple), as an object property assertion axiom
            // matthew --> hasFather --> peter
            OWLObjectPropertyAssertionAxiom assertion = dataFactory.getOWLObjectPropertyAssertionAxiom(hasFather, matthew, peter);
            // Finally, add the axiom to our ontology and save
            AddAxiom addAxiomChange = new AddAxiom(ont, assertion);
            man.applyChange(addAxiomChange);
            // We can also specify that matthew is an instance of Person.  To do this we use a ClassAssertion axiom.
            // First we need a reference to the person class
            OWLClass personClass = dataFactory.getOWLClass(IRI.create(base + "#Person"));
            // Now we will create out Class Assertion to specify that matthew is an instance of Person (or rather that
            // Person has matthew as an instance)
            OWLClassAssertionAxiom ax = dataFactory.getOWLClassAssertionAxiom(personClass, matthew);
            // Add this axiom to our ontology.  We can use a short cut method - instead of creating the AddAxiom change
            // ourselves, it will be created automatically and the change applied
            man.addAxiom(ont, ax);
            // Save our ontology
            man.saveOntology(ont, IRI.create("file:/tmp/example.owl"));
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Could not save ontology: " + e.getMessage());
        }
    }
}
