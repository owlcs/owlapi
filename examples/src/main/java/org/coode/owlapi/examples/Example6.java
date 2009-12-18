package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
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
 * Date: 24-May-2007<br><br>
 * <p/>
 * An example which shows how to create restrictions and add them
 * as superclasses of a class (i.e. "adding restrictions to classes")
 */
public class Example6 {

    public static void main(String[] args) {
        try {
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            String base = "http://org.semanticweb.restrictionexample";
            OWLOntology ont = man.createOntology(IRI.create(base));

            // In this example we will add an axiom to state that all Heads have
            // parts that are noses (in fact, here we merely state that a Head has
            // at least one nose!).  We do this by creating an existential (some) restriction
            // to describe the class of things which have a part that is a nose (hasPart some Nose),
            // and then we use this restriction in a subclass axiom to state that Head is a subclass
            // of things that have parts that are Noses SubClassOf(Head, hasPart some Nose) -- in
            // other words, Heads have parts that are noses!

            // First we need to obtain references to our hasPart property and our Nose class
            OWLDataFactory factory = man.getOWLDataFactory();
            OWLObjectProperty hasPart = factory.getOWLObjectProperty(IRI.create(base + "#hasPart"));
            OWLClass nose = factory.getOWLClass(IRI.create(base + "#Nose"));
            // Now create a restriction to describe the class of individuals that have at least one
            // part that is a kind of nose
            OWLClassExpression hasPartSomeNose = factory.getOWLObjectSomeValuesFrom(hasPart, nose);

            // Obtain a reference to the Head class so that we can specify that Heads have noses
            OWLClass head = factory.getOWLClass(IRI.create(base + "#Head"));
            // We now want to state that Head is a subclass of hasPart some Nose, to do this we
            // create a subclass axiom, with head as the subclass and "hasPart some Nose" as the
            // superclass (remember, restrictions are also classes - they describe classes of individuals
            // -- they are anonymous classes).
            OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(head, hasPartSomeNose);

            // Add the axiom to our ontology
            AddAxiom addAx = new AddAxiom(ont, ax);
            man.applyChange(addAx);


        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }
}
