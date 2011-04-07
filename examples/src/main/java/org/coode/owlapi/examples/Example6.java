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
