package uk.ac.manchester.owl.tutorial.examples;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import uk.ac.manchester.owl.tutorial.ClosureAxioms;
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
 * <p>This class demonstrates some aspects of the OWL API. It expects three
 * arguments:</p>
 * <ol>
 * <li>The URI of an ontology</li> 
 * 
 * <li>The URI of destination</li> 
 * 
 * <li>The URI of a class</li>
 * </ol>
 * <p>When executed, the class will find all subclass axioms that form part of the
 * definition of the given class. For each of these, if the superclass is a
 * conjunction of existential restrictions, then an additional subclass axiom
 * will be added to the ontology, "closing" the restrictions.</p>
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 24-April-2007<br>
 * <br>
 */
public class ClosureAxiomsExample {

    public static void usage() {
        System.out
                .println("Usage: ClosureAxiomsExample --input=URL --output=URL --class=URL");
    }

    public static void main(String[] args) {
        /* An example illustrating the addition of closure axioms. */


        try {
            LongOpt[] longopts = new LongOpt[11];
            String inputOntology = null;
            String outputOntology = null;
            String classToClose = null;

            longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, '?');
            longopts[1] = new LongOpt("input", LongOpt.REQUIRED_ARGUMENT, null,
                    'i');
            longopts[2] = new LongOpt("output", LongOpt.REQUIRED_ARGUMENT,
                    null, 'o');
            longopts[3] = new LongOpt("class", LongOpt.REQUIRED_ARGUMENT, null,
                    'c');

            Getopt g = new Getopt("", args, "?:i:o:c", longopts);
            int c;

            while ((c = g.getopt()) != -1) {
                switch (c) {
                case '?':
                    usage();
                    System.exit(0);
                case 'i':
                    /* input */
                    inputOntology = g.getOptarg();
                    break;
                case 'o':
                    /* output */
                    outputOntology = g.getOptarg();
                    break;
                case 'c':
                    /* class */
                    classToClose = g.getOptarg();
                    break;
                }
            }

            /* Create and Ontology Manager */
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            
            if (inputOntology == null || outputOntology == null
                    || classToClose == null) {
                usage();
                System.exit(1);
            }

            URI physicalURI = URI.create(inputOntology);
            URI classURI = URI.create(classToClose);
            URI outputURI = URI.create(outputOntology);
            
            /* Load an ontology */
            System.out.println("Loading: " + physicalURI);
            OWLOntology ontology = manager
                    .loadOntologyFromPhysicalURI(physicalURI);
            System.out.println("Ontology Loaded...");
            System.out.println("Logical URI : " + physicalURI);
            System.out.println("Physical URI: " + ontology.getURI());
            System.out.println("Format      : "
                    + manager.getOntologyFormat(ontology));

            ClosureAxioms closureAxioms = new ClosureAxioms(manager, ontology);

            OWLClass clazz = manager.getOWLDataFactory().getOWLClass(classURI);
            System.out.println("Class URI   : " + classURI);
            System.out.println(clazz);
            
            /* Add the closure axioms */
            closureAxioms.addClosureAxioms(clazz);
            
            /* Now save a copy to another location */
            System.out.println("Saving: " + outputURI);

            manager.saveOntology(ontology, outputURI);
            System.out.println("Ontology Saved...");
            System.out.println("Physical URI : " + outputURI);

            /* Remove the ontology from the manager */
            manager.removeOntology(ontology.getURI());
            System.out.println("Done");
        } catch (OWLException e) {
            e.printStackTrace();
        }
    }
}