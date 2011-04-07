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
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 20-Dec-2009
 * </p>
 * The examples here show how to load ontologies
 */
public class LoadingOntologies {

    public static void main(String[] args) {

        try {
            // Get hold of an ontology manager
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // Let's load an ontology from the web
            IRI iri = IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
            OWLOntology pizzaOntology = manager.loadOntologyFromOntologyDocument(iri);
            System.out.println("Loaded ontology: " + pizzaOntology);


            // Remove the ontology so that we can load a local copy.
            manager.removeOntology(pizzaOntology);

            // We can also load ontologies from files.  Download the pizza ontology from
            // http://www.co-ode.org/ontologies/pizza/pizza.owl and put it somewhere on your hard drive
            // Create a file object that points to the local copy
            File file = new File("/tmp/pizza.owl");

            // Now load the local copy
            OWLOntology localPizza = manager.loadOntologyFromOntologyDocument(file);
            System.out.println("Loaded ontology: " + localPizza);

            // We can always obtain the location where an ontology was loaded from
            IRI documentIRI = manager.getOntologyDocumentIRI(localPizza);
            System.out.println("    from: " + documentIRI);

            // Remove the ontology again so we can reload it later
            manager.removeOntology(pizzaOntology);


            // In cases where a local copy of one of more ontologies is used, an ontology IRI mapper can be used
            // to provide a redirection mechanism.  This means that ontologies can be loaded as if they were located
            // on the web.
            // In this example, we simply redirect the loading from http://www.co-ode.org/ontologies/pizza/pizza.owl
            // to our local copy above.
            manager.addIRIMapper(new SimpleIRIMapper(iri, IRI.create(file)));
            // Load the ontology as if we were loading it from the web (from its ontology IRI)
            IRI pizzaOntologyIRI = IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
            OWLOntology redirectedPizza = manager.loadOntology(pizzaOntologyIRI);
            System.out.println("Loaded ontology: " + redirectedPizza);
            System.out.println("    from: " + manager.getOntologyDocumentIRI(redirectedPizza));

            // Note that when imports are loaded an ontology manager will be searched for mappings
        }
        catch (OWLOntologyCreationIOException e) {
            // IOExceptions during loading get wrapped in an OWLOntologyCreationIOException
            IOException ioException = e.getCause();
            if (ioException instanceof FileNotFoundException) {
                System.out.println("Could not load ontology. File not found: " + ioException.getMessage());
            }
            else if (ioException instanceof UnknownHostException) {
                System.out.println("Could not load ontology. Unknown host: " + ioException.getMessage());
            }
            else {
                System.out.println("Could not load ontology: " + ioException.getClass().getSimpleName() + " " + ioException.getMessage());
            }
        }
        catch (UnparsableOntologyException e) {
            // If there was a problem loading an ontology because there are syntax errors in the document (file) that
            // represents the ontology then an UnparsableOntologyException is thrown
            System.out.println("Could not parse the ontology: " + e.getMessage());
            // A map of errors can be obtained from the exception
            Map<OWLParser, OWLParserException> exceptions = e.getExceptions();
            // The map describes which parsers were tried and what the errors were
            for (OWLParser parser : exceptions.keySet()) {
                System.out.println("Tried to parse the ontology with the " + parser.getClass().getSimpleName() + " parser");
                System.out.println("Failed because: " + exceptions.get(parser).getMessage());
            }
        }
        catch (UnloadableImportException e) {
            // If our ontology contains imports and one or more of the imports could not be loaded then an
            // UnloadableImportException will be thrown (depending on the missing imports handling policy)
            System.out.println("Could not load import: " + e.getImportsDeclaration());
            // The reason for this is specified and an OWLOntologyCreationException
            OWLOntologyCreationException cause = e.getOntologyCreationException();
            System.out.println("Reason: " + cause.getMessage());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
    }

}

