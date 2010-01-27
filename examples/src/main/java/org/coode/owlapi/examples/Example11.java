package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.util.ArrayList;
import java.util.List;
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
 * Date: 10-Dec-2007<br><br>
 *
 * This example shows how to generate an ontology containing some inferred
 * information.
 */
public class Example11 {


    public static void main(String[] args) {
        try {
            // Create a reasoner factory.  In this case, we will use pellet, but we could also
            // use FaCT++ using the FaCTPlusPlusReasonerFactory.
            // Pellet requires the Pellet libraries  (pellet.jar, aterm-java-x.x.jar) and the
            // XSD libraries that are bundled with pellet: xsdlib.jar and relaxngDatatype.jar
            // make sure these jars are on the classpath
            OWLReasonerFactory reasonerFactory = null;
            // Uncomment the line below
//            reasonerFactory = new PelletReasonerFactory();


            // Load an example ontology - for the purposes of the example, we will just load
            // the pizza ontology.
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            OWLOntology ont = man.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));

            // Create the reasoner and classify the ontology
            OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ont);
            reasoner.prepareReasoner();

            // To generate an inferred ontology we use implementations of inferred axiom generators
            // to generate the parts of the ontology we want (e.g. subclass axioms, equivalent classes
            // axioms, class assertion axiom etc. - see the org.semanticweb.owlapi.util package for more
            // implementations).  
            // Set up our list of inferred axiom generators
            List<InferredAxiomGenerator<? extends OWLAxiom>> gens = new ArrayList<InferredAxiomGenerator<? extends OWLAxiom>>();
            gens.add(new InferredSubClassAxiomGenerator());

            // Put the inferred axioms into a fresh empty ontology - note that there
            // is nothing stopping us stuffing them back into the original asserted ontology
            // if we wanted to do this.
            OWLOntology infOnt = man.createOntology();

            // Now get the inferred ontology generator to generate some inferred axioms
            // for us (into our fresh ontology).  We specify the reasoner that we want
            // to use and the inferred axiom generators that we want to use.
            InferredOntologyGenerator iog = new InferredOntologyGenerator(reasoner, gens);
            iog.fillOntology(man, infOnt);

            // Save the inferred ontology. (Replace the URI with one that is appropriate for your setup)
            man.saveOntology(infOnt, IRI.create("file:///tmp/inferredont.owlapi"));
        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
        catch(OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }
}
