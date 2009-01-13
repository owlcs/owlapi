package uk.ac.manchester.owl.tutorial.examples;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import uk.ac.manchester.owl.tutorial.io.OWLTutorialSyntaxOntologyFormat;
import uk.ac.manchester.owl.tutorial.io.OWLTutorialSyntaxOntologyStorer;

import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

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
 * <p>Simple Rendering Example. Reads an ontology and then renders it.</p>
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 24-April-2007<br>
 * <br>
 */
public class RenderingExample {

    public static void main(String[] args) {
        // A simple example of how to load and save an ontology
        try {

            /* Command line arguments */
            LongOpt[] longopts = new LongOpt[11];
            String inputOntology = null;
            String outputOntology = null;

            longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, '?');
            longopts[1] = new LongOpt("input", LongOpt.REQUIRED_ARGUMENT, null,
                    'i');
            longopts[2] = new LongOpt("output", LongOpt.REQUIRED_ARGUMENT,
                    null, 'o');

            Getopt g = new Getopt("", args, "?:i:o", longopts);
            int c;

            while ((c = g.getopt()) != -1) {
                switch (c) {
                case '?':
                    System.out.println("RenderingExample --input=URL --output=URL");
                    System.exit(0);
                case 'i':
                    /* input */
                    inputOntology = g.getOptarg();
                    break;
                case 'o':
                    /* input */
                    outputOntology = g.getOptarg();
                    break;
                }
            }

            /* Get an Ontology Manager */
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            if (inputOntology == null || outputOntology == null) {
                System.out.println("RenderingExample --input=URL --output=URL");

                System.exit(1);
            }

            URI physicalURI = URI.create(inputOntology);
            URI outputURI = URI.create(outputOntology);

            /* Load an ontology from a physical URI */

            OWLOntology ontology = manager
                    .loadOntologyFromPhysicalURI(physicalURI);
            /* Report information about the ontology */
            System.out.println("Ontology Loaded...");
            System.out.println("Physical URI: " + physicalURI);
            System.out.println("Logical URI : " + ontology.getURI());
            System.out.println("Format      : "
                    + manager.getOntologyFormat(ontology));

            /* Register the ontology storer with the manager */
            manager.addOntologyStorer(new OWLTutorialSyntaxOntologyStorer());

            /* Save using a different format */

            System.out.println("Storing     : " + outputURI);
            manager.saveOntology(ontology,
                    new OWLTutorialSyntaxOntologyFormat(), outputURI);
            /* Remove the ontology from the manager */
            manager.removeOntology(ontology.getURI());
            System.out.println("Done");
            
        } catch (OWLException e) {
            e.printStackTrace();
        }
    }
}
