package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

import java.net.URI;
import java.util.Collections;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Date: 02-Jun-2008<br><br>
 */
public class Example13 {

    public static void main(String[] args) {
        try {
            // This example shows how to use an ontology walker to walk the asserted structure
            // of an ontology.
            // Suppose we want to find the axioms that use a some values from (existential restriction)
            // we can use the walker to do this.

            // We'll use the pizza ontology as an example.  Load the ontology from the web:
            IRI documentIRI = IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            OWLOntology ont = man.loadOntologyFromOntologyDocument(documentIRI);

            // Create the walker.  Pass in the pizza ontology - we need to put it into a set
            // though, so we just create a singleton set in this case.
            OWLOntologyWalker walker = new OWLOntologyWalker(Collections.singleton(ont));

            // Now ask our walker to walk over the ontology.  We specify a visitor who gets visited
            // by the various objects as the walker encounters them.

            // We need to create out visitor.  This can be any ordinary visitor, but we will
            // extend the OWLOntologyWalkerVisitor because it provides a convenience method to
            // get the current axiom being visited as we go.
            // Create an instance and override the visit(OWLObjectSomeValuesFrom) method, because
            // we are interested in some values from restrictions.
            OWLOntologyWalkerVisitor<Object> visitor = new OWLOntologyWalkerVisitor<Object>(walker) {

                public Object visit(OWLObjectSomeValuesFrom desc) {
                    // Print out the restriction
                    System.out.println(desc);
                    // Print out the axiom where the restriction is used
                    System.out.println("         " + getCurrentAxiom());
                    System.out.println();
                    // We don't need to return anything here.
                    return null;
                }
            };

            // Now ask the walker to walk over the ontology structure using our visitor instance.
            walker.walkStructure(visitor);
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("There was a problem loading the ontology: " + e.getMessage());
        }
    }


}
