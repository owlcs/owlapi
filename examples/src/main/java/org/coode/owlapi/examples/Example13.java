/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;

import java.net.URI;
import java.util.Collections;


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
