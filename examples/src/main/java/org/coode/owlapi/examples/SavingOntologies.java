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

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;

import java.io.File;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 20-Dec-2009
 * </p>
 * This example shows how an ontology can be saved in various formats to various locations and streams
 */
public class SavingOntologies {

    public static void main(String[] args) {
        try {
            // Get hold of an ontology manager
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // Let's load an ontology from the web.  We load the ontology from a document IRI
            IRI documentIRI = IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
            OWLOntology pizzaOntology = manager.loadOntologyFromOntologyDocument(documentIRI);
            System.out.println("Loaded ontology: " + pizzaOntology);

            // Now save a local copy of the ontology.  (Specify a path appropriate to your setup)
            File file = new File("/tmp/local.owl");
            manager.saveOntology(pizzaOntology, IRI.create(file.toURI()));

            // By default ontologies are saved in the format from which they were loaded.  In this case the
            // ontology was loaded from an rdf/xml file
            // We can get information about the format of an ontology from its manager
            OWLOntologyFormat format = manager.getOntologyFormat(pizzaOntology);
            System.out.println("    format: " + format);

            // We can save the ontology in a different format
            // Lets save the ontology in owl/xml format
            OWLXMLOntologyFormat owlxmlFormat = new OWLXMLOntologyFormat();
            // Some ontology formats support prefix names and prefix IRIs.  In our case we loaded the pizza ontology
            // from an rdf/xml format, which supports prefixes.  When we save the ontology in the new format we will
            // copy the prefixes over so that we have nicely abbreviated IRIs in the new ontology document
            if(format.isPrefixOWLOntologyFormat()) {
                owlxmlFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat());
            }
            manager.saveOntology(pizzaOntology, owlxmlFormat, IRI.create(file.toURI()));

            // We can also dump an ontology to System.out by specifying a different OWLOntologyOutputTarget
            // Note that we can write an ontology to a stream in a similar way using the StreamOutputTarget class
            OWLOntologyDocumentTarget documentTarget = new SystemOutDocumentTarget();
            // Try another format - The Manchester OWL Syntax
            ManchesterOWLSyntaxOntologyFormat manSyntaxFormat = new ManchesterOWLSyntaxOntologyFormat();
            if(format.isPrefixOWLOntologyFormat()) {
                manSyntaxFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat());
            }
            manager.saveOntology(pizzaOntology, manSyntaxFormat, new SystemOutDocumentTarget());


        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Could not save ontology: " + e.getMessage());
        }
    }
}
