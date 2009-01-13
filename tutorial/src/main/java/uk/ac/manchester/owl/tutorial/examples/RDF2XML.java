package uk.ac.manchester.owl.tutorial.examples;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.io.OWLXMLOntologyFormat;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

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
 * 
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
            /* Load an ontology from a physical URI */
            System.out.println("In : " + args[0]);
            URI physicalURI = URI.create(args[0]);
            // Now do the loading
            OWLOntology ontology = manager
                    .loadOntologyFromPhysicalURI(physicalURI);
            /*
             * Now save a copy to another location in OWL/XML format (i.e.
             * disregard the format that the ontology was loaded in)
             */
            System.out.println("Out: " + args[1]);
            URI physicalURI2 = URI.create(args[1]);
            manager.saveOntology(ontology, new OWLXMLOntologyFormat(),
                    physicalURI2);
            /* Remove the ontology from the manager */
            manager.removeOntology(ontology.getURI());

        } catch (OWLException e) {
            e.printStackTrace();
        }
    }
}
