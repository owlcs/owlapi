package org.coode.owlapi.examples;

//import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.Iterator;
import java.util.Set;
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
 * Date: 20-Jun-2007<br><br>
 *
 * An example which shows how to interact with a reasoner.  In this example
 * Pellet is used as the reasoner.  You must get hold of the pellet libraries
 * from pellet.owldl.com.
 */
public class Example8 {

    public static final String DOCUMENT_IRI = "http://owl.cs.manchester.ac.uk/repository/download?ontology=file:/Users/seanb/Desktop/Cercedilla2005/hands-on/people.owl&format=RDF/XML";

    public static void main(String[] args) {

        try {
            // Create our ontology manager in the usual way.
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // Load a copy of the people+pets ontology.  We'll load the ontology from the web (it's acutally located
            // in the TONES ontology repository).
            IRI docIRI = IRI.create(DOCUMENT_IRI);
            // We load the ontology from a document - our IRI points to it directly
            OWLOntology ont = manager.loadOntologyFromOntologyDocument(docIRI);
            System.out.println("Loaded " + ont.getOntologyID());


            // We need to create an instance of OWLReasoner.  An OWLReasoner provides the basic
            // query functionality that we need, for example the ability obtain the subclasses
            // of a class etc.  To do this we use a reasoner factory.

            // Create a reasoner factory.  In this case, we will use HermiT, but we could also
            // use FaCT++ (http://code.google.com/p/factplusplus/) or Pellet(http://clarkparsia.com/pellet)
            // Note that (as of 03 Feb 2010) FaCT++ and Pellet OWL API 3.0.0 compatible libraries are
            // expected to be available in the near future).

            // For now, we'll use HermiT
            // HermiT can be downloaded from http://hermit-reasoner.com
            // Make sure you get the HermiT library and add it to your class path.  You can then
            // instantiate the HermiT reasoner factory:
            // Comment out the first line below and uncomment the second line below to instantiate
            // the HermiT reasoner factory.  You'll also need to import the org.semanticweb.HermiT.Reasoner
            // package.
             OWLReasonerFactory reasonerFactory = null;
//            OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();

            // We'll now create an instance of an OWLReasoner (the implementation being provided by HermiT as
            // we're using the HermiT reasoner factory).  The are two categories of reasoner, Buffering and
            // NonBuffering.  In our case, we'll create the buffering reasoner, which is the default kind of reasoner.
            // We'll also attach a progress monitor to the reasoner.  To do this we set up a configuration that
            // knows about a progress monitor.

            // Create a console progress monitor.  This will print the reasoner progress out to the console.
            ConsoleProgressMonitor progressMonitor = new ConsoleProgressMonitor();
            // Specify the progress monitor via a configuration.  We could also specify other setup parameters in
            // the configuration, and different reasoners may accept their own defined parameters this way.
            OWLReasonerConfiguration config = new SimpleConfiguration(progressMonitor);
            // Create a reasoner that will reason over our ontology and its imports closure.  Pass in the configuration.
            OWLReasoner reasoner = reasonerFactory.createReasoner(ont, config);

            // Ask the reasoner to do all the necessary work now
            reasoner.prepareReasoner();

            // We can determine if the ontology is actually consistent (in this case, it should be).
            boolean consistent = reasoner.isConsistent();
            System.out.println("Consistent: " + consistent);
            System.out.println("\n");

            // We can easily get a list of unsatisfiable classes.  (A class is unsatisfiable if it
            // can't possibly have any instances).  Note that the getUnsatisfiableClasses method
            // is really just a convenience method for obtaining the classes that are equivalent
            // to owl:Nothing.  In our case there should be just one unsatisfiable class - "mad_cow"
            // We ask the reasoner for the unsatisfiable classes, which returns the bottom node
            // in the class hierarchy (an unsatisfiable class is a subclass of every class).
            Node<OWLClass> bottomNode = reasoner.getUnsatisfiableClasses();
            // This node contains owl:Nothing and all the classes that are equivalent to owl:Nothing -
            // i.e. the unsatisfiable classes.
            // We just want to print out the unsatisfiable classes excluding owl:Nothing, and we can
            // used a convenience method on the node to get these
            Set<OWLClass> unsatisfiable = bottomNode.getEntitiesMinusBottom();
            if (!unsatisfiable.isEmpty()) {
                System.out.println("The following classes are unsatisfiable: ");
                for(OWLClass cls : unsatisfiable) {
                    System.out.println("    " + cls);
                }
            }
            else {
                System.out.println("There are no unsatisfiable classes");
            }
            System.out.println("\n");

            // Now we want to query the reasoner for all descendants of vegetarian.  Vegetarians are defined in the
            // ontology to be animals that don't eat animals or parts of animals.
            OWLDataFactory fac = manager.getOWLDataFactory();
            // Get a reference to the vegetarian class so that we can as the reasoner about it.
            // The full IRI of this class happens to be: <http://owl.man.ac.uk/2005/07/sssw/people#vegetarian>
            OWLClass vegPizza = fac.getOWLClass(IRI.create("http://owl.man.ac.uk/2005/07/sssw/people#vegetarian"));

            // Now use the reasoner to obtain the subclasses of vegetarian.
            // We can ask for the direct subclasses of vegetarian or all of the (proper) subclasses of vegetarian.
            // In this case we just want the direct ones (which we specify by the "true" flag).
            NodeSet<OWLClass> subClses = reasoner.getSubClasses(vegPizza, true);

            // The reasoner returns a NodeSet, which represents a set of Nodes.
            // Each node in the set represents a subclass of vegetarian pizza.  A node of classes contains classes,
            // where each class in the node is equivalent. For example, if we asked for the
            // subclasses of some class A and got back a NodeSet containing two nodes {B, C} and {D}, then A would have
            // two proper subclasses.  One of these subclasses would be equivalent to the class D, and the other would
            // be the class that is equivalent to class B and class C.

            // In this case, we don't particularly care about the equivalences, so we will flatten this
            // set of sets and print the result
            Set<OWLClass> clses = subClses.getFlattened();
            System.out.println("Subclasses of vegetarian: ");
            for(OWLClass cls : clses) {
                System.out.println("    " + cls);
            }
            System.out.println("\n");

            // In this case, we should find that the classes, cow, sheep and giraffe are vegetarian.  Note that in this
            // ontology only the class cow had been stated to be a subclass of vegetarian.  The fact that sheep and
            // giraffe are subclasses of vegetarian was implicit in the ontology (through other things we had said)
            // and this illustrates why it is important to use a reasoner for querying an ontology.
            

            // We can easily retrieve the instances of a class.  In this example we'll obtain the instances of
            // the class pet.  This class has a full IRI of <http://owl.man.ac.uk/2005/07/sssw/people#pet>

            // We need to obtain a reference to this class so that we can ask the reasoner about it.
            OWLClass country = fac.getOWLClass(IRI.create("http://owl.man.ac.uk/2005/07/sssw/people#pet"));
            // Ask the reasoner for the instances of pet
            NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(country, true);
            // The reasoner returns a NodeSet again.  This time the NodeSet contains individuals.
            // Again, we just want the individuals, so get a flattened set.
            Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
            System.out.println("Instances of pet: ");
            for(OWLNamedIndividual ind : individuals) {
                System.out.println("    " + ind);
            }
            System.out.println("\n");

            // Again, it's worth noting that not all of the individuals that are returned were explicitly stated
            // to be pets.

            // Finally, we can ask for the property values (property assertions in OWL speak) for a given individual
            // and property.
            // Let's get the property values for the individual Mick, the full IRI of which is
            // <http://owl.man.ac.uk/2005/07/sssw/people#Mick>

            // Get a reference to the individual Mick
            OWLNamedIndividual mick = fac.getOWLNamedIndividual(IRI.create("http://owl.man.ac.uk/2005/07/sssw/people#Mick"));

            // Let's get the pets of Mick
            // Get hold of the has_pet property which has a full IRI of <http://owl.man.ac.uk/2005/07/sssw/people#has_pet>
            OWLObjectProperty hasPet = fac.getOWLObjectProperty(IRI.create("http://owl.man.ac.uk/2005/07/sssw/people#has_pet"));

            // Now ask the reasoner for the has_pet property values for Mick
            NodeSet<OWLNamedIndividual> petValuesNodeSet = reasoner.getObjectPropertyValues(mick, hasPet);
            Set<OWLNamedIndividual> values = petValuesNodeSet.getFlattened();
            System.out.println("The has_pet property values for Mick are: ");
            for(OWLNamedIndividual ind : values) {
                System.out.println("    " + ind);
            }

            // Notice that Mick has a pet Rex, which wasn't asserted in the ontology.


            // Finally, let's print out the class hierarchy.
            // Get hold of the top node in the class hierarchy (containing owl:Thing)
            // Now print the hierarchy out
            Node<OWLClass> topNode = reasoner.getTopClassNode();
            print(topNode, reasoner, 0);

        }
        catch(UnsupportedOperationException exception) {
            System.out.println("Unsupported reasoner operation.");
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load the pizza ontology: " + e.getMessage());
        }
    }


    private static void print(Node<OWLClass> parent, OWLReasoner reasoner, int depth) {
        // We don't want to print out the bottom node (containing owl:Nothing and unsatisfiable classes)
        // because this would appear as a leaf node everywhere
        if(parent.isBottomNode()) {
            return;
        }
        // Print an indent to denote parent-child relationships
        printIndent(depth);
        // Now print the node (containing the child classes)
        printNode(parent);
        for(Node<OWLClass> child : reasoner.getSubClasses(parent.getRepresentativeElement(), true)) {
            // Recurse to do the children.  Note that we don't have to worry about cycles as there
            // are non in the inferred class hierarchy graph - a cycle gets collapsed into a single
            // node since each class in the cycle is equivalent.
            print(child, reasoner, depth + 1);
        }
    }

    private static void printIndent(int depth) {
        for(int i = 0; i < depth; i++) {
            System.out.print("    ");
        }
    }

    private static DefaultPrefixManager pm = new DefaultPrefixManager("http://owl.man.ac.uk/2005/07/sssw/people#");

    private static void printNode(Node<OWLClass> node) {
        // Print out a node as a list of class names in curly brackets
        System.out.print("{");
        for(Iterator<OWLClass> it = node.getEntities().iterator(); it.hasNext(); ) {
            OWLClass cls = it.next();
            // User a prefix manager to provide a slightly nicer shorter name
            System.out.print(pm.getShortForm(cls));
            if (it.hasNext()) {
                System.out.print(" ");
            }
        }
        System.out.println("}");
    }
}
