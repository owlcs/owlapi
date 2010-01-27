package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLOntologyMerger;

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
 * Date: 11-Apr-2008<br><br>
 *
 * This example shows how to merge to ontologies (by simply combining axioms from
 * one ontology into another ontology).
 */
public class Example12 {

    public static void main(String[] args) {
        try {
            // Just load two arbitrary ontologies for the purposes of this example
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            man.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));
            man.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/amino-acid/2006/05/18/amino-acid.owl"));
            // Create our ontology merger
            OWLOntologyMerger merger = new OWLOntologyMerger(man);
            // We merge all of the loaded ontologies.  Since an OWLOntologyManager is an OWLOntologySetProvider we
            // just pass this in.  We also need to specify the URI of the new ontology that will be created.
            IRI mergedOntologyIRI = IRI.create("http://www.semanticweb.com/mymergedont");
            OWLOntology merged = merger.createMergedOntology(man, mergedOntologyIRI);
            // Print out the axioms in the merged ontology.
            for(OWLAxiom ax : merged.getAxioms()) {
                System.out.println(ax);
            }
            // Save to RDF/XML
            man.saveOntology(merged, new RDFXMLOntologyFormat(), IRI.create("file:/tmp/mergedont.owlapi"));
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Problem saving ontology: " + e.getMessage());
        }
    }
}
