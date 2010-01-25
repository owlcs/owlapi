package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;

import java.net.URI;
/*
 * Copyright (C) 2007, University of Manchester
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
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Jan-2007<br><br>
 */
public class Example1 {

    public static void main(String[] args) {
        try {
            // A simple example of how to load and save an ontology
            // We first need to obtain a copy of an OWLOntologyManager, which, as the
            // name suggests, manages a set of ontologies.  An ontology is unique within
            // an ontology manager.  Each ontology knows its ontology manager.
            // To load multiple copies of an ontology, multiple managers would have to be used.
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            // We load an ontology from a document IRI - in this case we'll load the pizza
            // ontology.
            IRI documentIRI = IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
            // Now ask the manager to load the ontology
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(documentIRI);
            // Print out all of the classes which are contained in the signature of the ontology.
            // These are the classes that are referenced by axioms in the ontology.
            for (OWLClass cls : ontology.getClassesInSignature()) {
                System.out.println(cls);
            }
            // Now save a copy to another location in OWL/XML format (i.e. disregard the
            // format that the ontology was loaded in).
            // (To save the file on windows use a URL such as  "file:/C:\\windows\\temp\\MyOnt.owlapi")
            IRI documentIRI2 = IRI.create("file:/tmp/MyOnt2.owl");
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(), documentIRI2);
            // Remove the ontology from the manager
            manager.removeOntology(ontology);
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("The ontology could not be created: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("The ontology could not be saved: " + e.getMessage());
        }
    }
}
