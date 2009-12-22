package uk.ac.manchester.owl.owlapi.tutorial.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

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
 * <p>Simple example that reads an ontology then writes it out in OWL/XML</p>
 * <p/>
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Jan-2007<br>
 * <br>
 */
public class RDF2XML {

    public static void main(String[] args) {
        // A simple example of how to load and save an ontology
        try {
            /*
             * We first need to obtain a copy of an OWLOntologyManager, which,
             * as the name suggests, manages a set of ontologies. An ontology is
             * unique within an ontology manager. To load multiple copies of an
             * ontology, multiple managers would have to be used.
             */
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            /* Load an ontology from a document IRI */
            System.out.println("In : " + args[0]);
            IRI documentIRI = IRI.create(args[0]);
            // Now do the loading
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(documentIRI);
            /*
             * Now save a copy to another location in OWL/XML format (i.e.
             * disregard the format that the ontology was loaded in)
             */
            System.out.println("Out: " + args[1]);
            IRI documentIRI2 = IRI.create(args[1]);
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(), documentIRI2);
            /* Remove the ontology from the manager */
            manager.removeOntology(ontology);

        }
        catch (OWLException e) {
            e.printStackTrace();
        }
    }
}
