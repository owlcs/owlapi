package uk.ac.manchester.owl.tutorial.examples;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Set;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.inference.OWLClassReasoner;
import org.semanticweb.owl.model.OWLAnnotation;
import org.semanticweb.owl.model.OWLClass;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.util.ToldClassHierarchyReasoner;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;

import uk.ac.manchester.cs.owl.inference.dig11.DIGReasoner;
import uk.ac.manchester.owl.tutorial.LabelExtractor;

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

    private OWLClassReasoner reasoner;
    private OWLOntology ontology;

    private PrintStream out;

    public SimpleHierarchyExample(OWLOntologyManager manager, String reasonerURL)
            throws OWLException, MalformedURLException {
        if (reasonerURL != null) {
            reasoner = new DIGReasoner(manager);
            URL rURL = new URL(reasonerURL);
            ((DIGReasoner) reasoner).getReasoner().setReasonerURL(rURL);
        } else {
            reasoner = new ToldClassHierarchyReasoner(manager);
        }
        out = System.out;
    }

    /**
     * Print the class hierarchy for the given ontology from this class down, assuming this class is at
     * the given level. Makes no attempt to deal sensibly with multiple
     * inheritance.
     */
    public void printHierarchy(OWLOntology ontology, OWLClass clazz) throws OWLException {
        reasoner.loadOntologies(Collections.singleton(ontology));
        this.ontology = ontology;
        printHierarchy( clazz, 0 );
        /* Now print out any unsatisfiable classes */
        for (OWLClass cl: ontology.getReferencedClasses()) {
            if (!reasoner.isSatisfiable(cl)) {
                out.println("XXX: " + labelFor(cl));
            }
        }
        reasoner.clearOntologies();
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
            return clazz.getURI().toString();
        }
    }
    
    /**
     * Print the class hierarchy from this class down, assuming this class is at
     * the given level. Makes no attempt to deal sensibly with multiple
     * inheritance.
     */
    public void printHierarchy(OWLClass clazz, int level)
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
            Set<Set<OWLClass>> children = reasoner.getSubClasses(clazz);
            for (Set<OWLClass> setOfClasses : children) {
                for (OWLClass child : setOfClasses) {
                    if (!child.equals(clazz)) {
                        printHierarchy(child, level + 1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        try {

            String reasonerURL = null;
            URI classURI = null;

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

            while ((c = g.getopt()) != -1) {
                switch (c) {
                case '?':
                    System.out.println("command [--reasoner=URL] [--class=URL] URL");
                    System.exit(0);
                case 'r':
                    /* Use a reasoner */
                    reasonerURL = g.getOptarg();
                    break;
                case 'c':
                    /* Class to start from */
                    classURI = new URI(g.getOptarg());
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
            System.out.println("Logical URI : " + ontology.getURI());
            System.out.println("Format      : "
                    + manager.getOntologyFormat(ontology));
            // / Create a new SimpleHierarchy object with the given reasoner.
            SimpleHierarchyExample simpleHierarchy = new SimpleHierarchyExample(
                    manager, reasonerURL);

	    // Get Thing
            if (classURI==null) {
                classURI = OWLRDFVocabulary.OWL_THING.getURI();
                
            }
            OWLClass clazz = manager.getOWLDataFactory().getOWLClass(classURI);
            System.out.println("Class       : " + classURI);

            // Print the hierarchy below thing
            simpleHierarchy.printHierarchy(ontology, clazz );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
