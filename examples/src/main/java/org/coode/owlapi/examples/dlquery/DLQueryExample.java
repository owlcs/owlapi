package org.coode.owlapi.examples.dlquery;

//import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import java.io.*;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-May-2010
 * <br>
 * An example that shows how to do a Protege like DLQuery.  The example contains several helper classes:<br>
 *
 *    DLQueryEngine - This takes a string representing a class expression built from the terms in the signature
 *                    of some ontology.
 *    DLQueryPrinter - This takes a string class expression and prints out the sub/super/equivalent classes and the
 *                     instances of the specified class expression.
 *    DLQueryParser - this parses the specified class expression string 
 */
public class DLQueryExample {

    private static final IRI ONTOLOGY_IRI = IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");

    public static void main(String[] args) {

        try {
            // Load an example ontology.  In this case, we'll just load the pizza ontology.
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ONTOLOGY_IRI);
            System.out.println("Loaded ontology: " + ontology.getOntologyID());
            // We need a reasoner to do our query answering
            OWLReasoner reasoner = createReasoner(ontology);

            // Entities are named using IRIs.  These are usually too long for use in user interfaces.  To solve this
            // problem, and so a query can be written using short class, property, individual names we use a short form
            // provider.  In this case, we'll just use a simple short form provider that generates short froms from IRI
            // fragments.
            ShortFormProvider shortFormProvider = new SimpleShortFormProvider();

            // Create the DLQueryPrinter helper class.  This will manage the parsing of input and printing of results
            DLQueryPrinter dlQueryPrinter = new DLQueryPrinter(reasoner, shortFormProvider);

            // Enter the query loop.  A user is expected to enter class expression on the command line.
            doQueryLoop(dlQueryPrinter);

        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
        catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
        }

    }

    private static void doQueryLoop(DLQueryPrinter dlQueryPrinter) throws IOException {
        while (true) {
            // Prompt the user to enter a class expression
            System.out.println("Please type a class expression in Manchester Syntax and press Enter (or press x to exit):");
            System.out.println("");
            String classExpression = readInput();
            // Check for exit condition
            if(classExpression.equalsIgnoreCase("x")) {
                break;
            }
            dlQueryPrinter.askQuery(classExpression.trim());
            System.out.println();
            System.out.println();
        }
    }

    private static String readInput() throws IOException {
        InputStream is = System.in;
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);
        return br.readLine();

    }

    private static OWLReasoner createReasoner(OWLOntology rootOntology) {
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
//        OWLReasonerFactory reasonerFactory = new Reasoner.ReasonerFactory();
        return reasonerFactory.createReasoner(rootOntology);
    }

}
