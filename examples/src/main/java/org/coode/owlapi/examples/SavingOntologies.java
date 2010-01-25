package org.coode.owlapi.examples;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;

import java.io.File;
/*
 * Copyright (C) 2009, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

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
