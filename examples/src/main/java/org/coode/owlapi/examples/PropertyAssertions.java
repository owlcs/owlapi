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
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 20-Dec-2009
 * </p>
 * This example shows how to specify various property assertions for individuals.
 */
public class PropertyAssertions {

    public static void main(String[] args) {
        try {
            // We can specify the properties of an individual using property assertions
            // Get a copy of an ontology manager
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            IRI ontologyIRI = IRI.create("http://example.com/owl/families/");
            OWLOntology ontology = manager.createOntology(ontologyIRI);

            // Get hold of a data factory from the manager and set up a prefix manager to make things easier
            OWLDataFactory factory = manager.getOWLDataFactory();
            PrefixManager pm = new DefaultPrefixManager(ontologyIRI.toString());

            // Let's specify the :John has a wife :Mary
            // Get hold of the necessary individuals and object property
            OWLNamedIndividual john = factory.getOWLNamedIndividual(":John", pm);
            OWLNamedIndividual mary = factory.getOWLNamedIndividual(":Mary", pm);
            OWLObjectProperty hasWife = factory.getOWLObjectProperty(":hasWife", pm);

            // To specify that :John is related to :Mary via the :hasWife property we create an object property
            // assertion and add it to the ontology
            OWLObjectPropertyAssertionAxiom propertyAssertion = factory.getOWLObjectPropertyAssertionAxiom(hasWife, john, mary);
            manager.addAxiom(ontology, propertyAssertion);

            // Now let's specify that :John is aged 51.
            // Get hold of a data property called :hasAge
            OWLDataProperty hasAge = factory.getOWLDataProperty(":hasAge", pm);

            // To specify that :John has an age of 51 we create a data property assertion and add it to the ontology
            OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(hasAge, john, 51);
            manager.addAxiom(ontology, dataPropertyAssertion);

            // Note that the above is a shortcut for creating a typed literal and specifying this typed literal as the
            // value of the property assertion.
            // That is,
            // Get hold of the xsd:integer datatype
            OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());
            // Create a typed literal.  We type the literal "51" with the datatype
            OWLLiteral literal = factory.getOWLLiteral("51", integerDatatype);
            // Create the property assertion and add it to the ontology
            OWLAxiom ax = factory.getOWLDataPropertyAssertionAxiom(hasAge, john, literal);
            manager.addAxiom(ontology, ax);

            // Dump the ontology to System.out
            manager.saveOntology(ontology, new SystemOutDocumentTarget());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Could not save ontology: " + e.getMessage());
        }
    }
}
