package uk.ac.manchester.owl.tutorial.examples;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.inference.OWLSatisfiabilityChecker;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.inference.dig11.DIGReasoner;
import uk.ac.manchester.owl.tutorial.Debugger;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;

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
 * 
 * <p>This class demonstrates some aspects of the OWL API. It expects three
 * arguments:</p>
 * <ol>
 * <li>The URI of an ontology</li>
 * 
 * <li>The URI of a reasoner</li>
 * 
 * <li>A location to place the results.</li>
 * </ol>
 * <p>When executed, the class will find all inconsistent classes. For each
 * inconsistent class, the set of support for the inconsistency will be
 * determined. A report will then be produced in the output file.</p>
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 24-April-2007<br>
 * <br>
 */
public class DebuggingExample {

    public static void usage() {
        System.out
                .println("Usage: DebuggingExample --input=URL --reasoner=URL --output=filename");
    }

    public static void main(String[] args) {
        /* An example illustrating use of the debugger */

        try {
            LongOpt[] longopts = new LongOpt[11];
            String inputOntology = null;
            String output = null;
            String reasoner = null;

            /* Set up options */
            longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, '?');
            longopts[1] = new LongOpt("input", LongOpt.REQUIRED_ARGUMENT, null,
                    'i');
            longopts[2] = new LongOpt("output", LongOpt.REQUIRED_ARGUMENT,
                    null, 'o');
            longopts[3] = new LongOpt("reasoner", LongOpt.REQUIRED_ARGUMENT,
                    null, 'r');

            Getopt g = new Getopt("", args, "?:i:o:r", longopts);
            int c;

            while ((c = g.getopt()) != -1) {
                switch (c) {
                case '?':
                    usage();
                    System.out.println("Usage Message!");
                    System.exit(0);
                case 'i':
                    /* input */
                    inputOntology = g.getOptarg();
                    break;
                case 'o':
                    /* output */
                    output = g.getOptarg();
                    break;
                case 'r':
                    /* reasoner */
                    reasoner = g.getOptarg();
                    break;
                }
            }

            /* Get an OWLManager */
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            if (inputOntology == null || output == null || reasoner == null) {
                usage();
                System.exit(1);
            }

            /* We load an ontology from a physical URI */

            URI physicalURI = URI.create(inputOntology);

            System.out.println("Loading: " + physicalURI);
            OWLOntology ontology = manager
                    .loadOntologyFromPhysicalURI(physicalURI);
            System.out.println("Ontology Loaded...");
            System.out.println("Logical URI : " + physicalURI);
            System.out.println("Physical URI: " + ontology.getURI());
            System.out.println("Format      : "
                    + manager.getOntologyFormat(ontology));

            /* Create a satisfiability checker */
            OWLSatisfiabilityChecker checker = new DIGReasoner(manager);
            URL rURL = new URL(reasoner);
            ((DIGReasoner) checker).getReasoner().setReasonerURL(rURL);

            System.out.println("Debugging...");
            
            /* Create a debugger */
            Debugger debugger = new Debugger(manager, ontology, checker);

            PrintWriter pw = new PrintWriter(new FileWriter(output));
            
            /* Report about debugging */
            debugger.report(pw);
            pw.close();

            /* Remove the ontology from the manager */
            manager.removeOntology(ontology.getURI());
            System.out.println("Done");
        } catch (OWLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}