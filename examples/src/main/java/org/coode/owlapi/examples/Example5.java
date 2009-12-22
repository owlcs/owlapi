package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import java.util.Collections;
import java.net.URI;
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
