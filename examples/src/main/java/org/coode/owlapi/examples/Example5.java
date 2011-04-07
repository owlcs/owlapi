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
import org.semanticweb.owlapi.util.OWLEntityRemover;

import java.util.Collections;
import java.net.URI;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-May-2007<br><br>
 * <p/>
 * An example which shows how to "delete" entities, in this case individuals,
 * from and ontology.
 */
public class Example5 {

    public static void main(String[] args) {
        try {
            // The pizza ontology contains several individuals that represent
            // countries, which describe the country of origin of various pizzas
            // and ingredients.  In this example we will delete them all.
            // First off, we start by loading the pizza ontology.
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            OWLOntology ont = man.loadOntologyFromOntologyDocument(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl"));

            // We can't directly delete individuals, properties or classes from an ontology because
            // ontologies don't directly contain entities -- they are merely referenced by the
            // axioms that the ontology contains.  For example, if an ontology contained a subclass axiom
            // SubClassOf(A, B) which stated A was a subclass of B, then that ontology would contain references
            // to classes A and B.  If we essentially want to "delete" classes A and B from this ontology we
            // have to remove all axioms that contain class A and class B in their SIGNATURE (in this case just one axiom
            // SubClassOf(A, B)).  To do this, we can use the OWLEntityRemove utility class, which will remove
            // an entity (class, property or individual) from a set of ontologies.

            // Create the entity remover - in this case we just want to remove the individuals from
            // the pizza ontology, so pass our reference to the pizza ontology in as a singleton set.
            OWLEntityRemover remover = new OWLEntityRemover(man, Collections.singleton(ont));
            System.out.println("Number of individuals: " + ont.getIndividualsInSignature().size());
            // Loop through each individual that is referenced in the pizza ontology, and ask it
            // to accept a visit from the entity remover.  The remover will automatically accumulate
            // the changes which are necessary to remove the individual from the ontologies (the pizza
            // ontology) which it knows about
            for (OWLNamedIndividual ind : ont.getIndividualsInSignature()) {
                ind.accept(remover);
            }
            // Now we get all of the changes from the entity remover, which should be applied to
            // remove all of the individuals that we have visited from the pizza ontology.  Notice that
            // "batch" deletes can essentially be performed - we simply visit all of the classes, properties
            // and individuals that we want to remove and then apply ALL of the changes after using the
            // entity remover to collect them
            man.applyChanges(remover.getChanges());
            System.out.println("Number of individuals: " + ont.getIndividualsInSignature().size());
            // At this point, if we wanted to reuse the entity remover, we would have to reset it
            remover.reset();
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
    }
}
