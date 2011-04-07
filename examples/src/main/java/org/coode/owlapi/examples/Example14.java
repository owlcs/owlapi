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
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.OWLReasoner;


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 15-Oct-2008<br><br>
 */
public class Example14 {

    public static void main(String[] args) {
        try {
            // We will load the pizza ontology and query it using a reasoner
            IRI documentIRI = IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            OWLOntology ont = man.loadOntologyFromOntologyDocument(documentIRI);

            // For this particular ontology, we know that all class, properties names etc. have
            // URIs that is made up of the ontology IRI plus # plus the local name
            String prefix = ont.getOntologyID().getOntologyIRI() + "#";

            // Create a reasoner.  We will use Pellet in this case.  Make sure that the latest
            // version of the Pellet libraries are on the runtime class path
            OWLReasonerFactory reasonerFactory = null;
            // Uncomment the line below
//            reasonerFactory = new PelletReasonerFactory();
            OWLReasoner reasoner = reasonerFactory.createNonBufferingReasoner(ont);


            // Now we can query the reasoner, suppose we want to determine the properties that
            // instances of Marghertia pizza must have
            OWLClass margheritaPizza = man.getOWLDataFactory().getOWLClass(IRI.create(prefix + "Margherita"));
            printProperties(man, ont, reasoner, margheritaPizza);

            // Let's do the same for JalapenoPepperTopping
            OWLClass vegTopping = man.getOWLDataFactory().getOWLClass(IRI.create(prefix + "JalapenoPepperTopping"));
            printProperties(man, ont, reasoner, vegTopping);

            // We can also ask if the instances of a class must have a property
            OWLClass mozzarellaTopping = man.getOWLDataFactory().getOWLClass(IRI.create(prefix + "MozzarellaTopping"));
            OWLObjectProperty hasOrigin = man.getOWLDataFactory().getOWLObjectProperty(IRI.create(prefix + "hasCountryOfOrigin"));
            if (hasProperty(man, reasoner, mozzarellaTopping, hasOrigin)) {
                System.out.println("Instances of " + mozzarellaTopping + " have a country of origin");
            }
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Problem loading ontology: " + e.getMessage());
        }
    }

    /**
     * Prints out the properties that instances of a class expression must have
     * @param man The manager
     * @param ont The ontology
     * @param reasoner The reasoner
     * @param cls The class expression
     */
    private static void printProperties(OWLOntologyManager man, OWLOntology ont, OWLReasoner reasoner, OWLClass cls) {
        if (!ont.containsClassInSignature(cls.getIRI())) {
            throw new RuntimeException("Class not in signature of the ontology");
        }
        // Note that the following code could be optimised... if we find that instances of the specified class
        // do not have a property, then we don't need to check the sub properties of this property
        System.out.println("----------------------------------------------------------");
        System.out.println("Properties of " + cls);
        System.out.println("----------------------------------------------------------");
        for (OWLObjectPropertyExpression prop : ont.getObjectPropertiesInSignature()) {
            boolean sat = hasProperty(man, reasoner, cls, prop);
            if (sat) {
                System.out.println("Instances of " + cls + " necessarily have the property " + prop);
            }
        }
    }

    private static boolean hasProperty(OWLOntologyManager man, OWLReasoner reasoner, OWLClass cls, OWLObjectPropertyExpression prop) {
        // To test whether the instances of a class must have a property we create a some values
        // from restriction and then ask for the satisfiability of the class interesected with the complement
        // of this some values from restriction.  If the intersection is satisfiable then the instances of
        // the class don't have to have the property, otherwise, they do.
        OWLDataFactory dataFactory = man.getOWLDataFactory();
        OWLClassExpression restriction = dataFactory.getOWLObjectSomeValuesFrom(prop, dataFactory.getOWLThing());
        // Now we see if the intersection of the class and the complement of this restriction is satisfiable
        OWLClassExpression complement = dataFactory.getOWLObjectComplementOf(restriction);
        OWLClassExpression intersection = dataFactory.getOWLObjectIntersectionOf(cls, complement);
        return !reasoner.isSatisfiable(intersection);
    }
}
