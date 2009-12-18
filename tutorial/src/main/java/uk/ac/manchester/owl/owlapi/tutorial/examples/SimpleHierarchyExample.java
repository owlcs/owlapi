package uk.ac.manchester.owl.owlapi.tutorial.examples;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import uk.ac.manchester.owl.owlapi.tutorial.LabelExtractor;

import gnu.getopt.LongOpt;
import gnu.getopt.Getopt;

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
 * <p>Simple example. Read an ontology, and display the class hierarchy. May use a
 * reasoner to calculate the hierarchy.</p>
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 17-03-2007<br>
 * <br>
 */
public class SimpleHierarchyExample {
    private static int INDENT = 4;

    private OWLReasonerFactory reasonerFactory;

    private OWLOntology ontology;

    private PrintStream out;

    public SimpleHierarchyExample(OWLOntologyManager manager, OWLReasonerFactory reasonerFactory)
            throws OWLException, MalformedURLException {
        this.reasonerFactory = reasonerFactory;
        out = System.out;
    }

    /**
     * Print the class hierarchy for the given ontology from this class down, assuming this class is at
     * the given level. Makes no attempt to deal sensibly with multiple
     * inheritance.
     */
    public void printHierarchy(OWLOntology ontology, OWLClass clazz) throws OWLException {
        OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);
        this.ontology = ontology;
        printHierarchy(reasoner, clazz, 0 );
        /* Now print out any unsatisfiable classes */
        for (OWLClass cl: ontology.getClassesInSignature()) {
            if (!reasoner.isSatisfiable(cl)) {
                out.println("XXX: " + labelFor(cl));
            }
        }
        reasoner.dispose();
    }
    
    private String labelFor( OWLClass clazz) {
        /*
         * Use a visitor to extract label annotations
         */
        
        LabelExtractor le = new LabelExtractor();
        Set<OWLAnnotation> annotations = clazz.getAnnotations(ontology);
        for (OWLAnnotation anno : annotations) {
            anno.accept(le);
        }
        /* Print out the label if there is one. If not, just use the class URI */
        if (le.getResult() != null) {
            return le.getResult().toString();
        } else {            
            return clazz.getIRI().toString();
        }
    }
    
    /**
     * Print the class hierarchy from this class down, assuming this class is at
     * the given level. Makes no attempt to deal sensibly with multiple
     * inheritance.
     */
    public void printHierarchy(OWLReasoner reasoner, OWLClass clazz, int level)
            throws OWLException {
        /*
         * Only print satisfiable classes -- otherwise we end up with bottom
         * everywhere
         */
        if (reasoner.isSatisfiable(clazz)) {
            for (int i = 0; i < level * INDENT; i++) {
                out.print(" ");
            }
            out.println(labelFor( clazz ));
            /* Find the children and recurse */
                for (OWLClass child : reasoner.getSubClasses(clazz, true).getFlattened()) {
                    if (!child.equals(clazz)) {
                        printHierarchy(reasoner, child, level + 1);
                    }
                }
        }
    }

    public static void main(String[] args) {
        try {

            /* Handle command line arguments */
            LongOpt[] longopts = new LongOpt[11];
            
            longopts[0] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, '?');
            longopts[1] = new LongOpt("reasoner", LongOpt.REQUIRED_ARGUMENT,
                    null, 'r');
            longopts[2] = new LongOpt("class", LongOpt.REQUIRED_ARGUMENT,
                    null, 'c');

            Getopt g = new Getopt("", args, "?:r:c", longopts);
            int c;
            // String arg;

            IRI classIRI = null;
            String reasonerFactoryClassName = null;
            while ((c = g.getopt()) != -1) {
                switch (c) {
                case '?':
                    System.out.println("command --reasonerFactoryClassName [--class=URL] URL");
                    System.exit(0);
                case 'r':
                    /* Use a reasoner */
                    reasonerFactoryClassName = g.getOptarg();
                    break;
                case 'c':
                    /* Class to start from */
                    classIRI = IRI.create(g.getOptarg());
                    break;
                }
            }

            int i = g.getOptind();

            // We first need to obtain a copy of an
            // OWLOntologyManager, which, as the name
            // suggests, manages a set of ontologies. 
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            
            // We load an ontology from the URI specified
            // on the command line
            if (args.length <= i) {
                System.out.println("No URI specified!");
                System.exit(0);
            }

            System.out.println(args[i]);
            URI physicalURI = URI.create(args[i]);
            // Now load the ontology.
            OWLOntology ontology = manager
                    .loadOntologyFromPhysicalURI(physicalURI);
            // Report information about the ontology
            System.out.println("Ontology Loaded...");
            System.out.println("Physical URI: " + physicalURI);
            System.out.println("Logical URI : " + ontology.getOntologyID());
            System.out.println("Format      : "
                    + manager.getOntologyFormat(ontology));
            // / Create a new SimpleHierarchy object with the given reasoner.
            SimpleHierarchyExample simpleHierarchy = new SimpleHierarchyExample(
                    manager, (OWLReasonerFactory) Class.forName(reasonerFactoryClassName).newInstance());

	        // Get Thing
            if (classIRI==null) {
                classIRI = OWLRDFVocabulary.OWL_THING.getIRI();
                
            }
            OWLClass clazz = manager.getOWLDataFactory().getOWLClass(classIRI);
            System.out.println("Class       : " + classIRI);

            // Print the hierarchy below thing
            simpleHierarchy.printHierarchy(ontology, clazz );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
