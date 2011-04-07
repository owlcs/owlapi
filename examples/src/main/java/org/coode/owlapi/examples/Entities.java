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
import org.semanticweb.owlapi.util.DefaultPrefixManager;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Dec-2009
 * </p>
 * This example shows how to get access to objects that represent entities.
 */
public class Entities {

    public static void main(String[] args) {

        try {
            // In order to get access to objects that represent entities we need a data factory.
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // We can get a reference to a data factory from an OWLOntologyManager.
            OWLDataFactory factory = manager.getOWLDataFactory();

            // In OWL, entities are named objects that are used to build class expressions and axioms.  They
            // include classes, properties (object, data and annotation), named individuals and datatypes.
            // All entities may be obtained from an OWLDataFactory.

            // Let's create an object to represent a class.  In this case, we'll choose
            // http://www.semanticweb.org/owlapi/ontologies/ontology#A
            // as the IRI for our class.

            // There are two ways we can create classes (and other entities).

            // The first is by specifying the full IRI.  First we create an IRI object:
            IRI iri = IRI.create("http://www.semanticweb.org/owlapi/ontologies/ontology#A");
            // Now we create the class
            OWLClass clsAMethodA = factory.getOWLClass(iri);

            // The second is to use a prefix manager and specify abbreviated IRIs.  This is useful for creating
            // lots of entities with the same prefix IRIs.
            // First create our prefix manager and specify that the default prefix IRI (bound to the empty prefix name)
            // is http://www.semanticweb.org/owlapi/ontologies/ontology#
            PrefixManager pm = new DefaultPrefixManager("http://www.semanticweb.org/owlapi/ontologies/ontology#");
            // Now we use the prefix manager and just specify an abbreviated IRI
            OWLClass clsAMethodB = factory.getOWLClass(":A", pm);

            // Note that clsAMethodA will be equal to clsAMethodB because they are both OWLClass objects and have the
            // same IRI.

            // Creating entities in the above manner does not "add them to an ontology".  They are merely objects that
            // allow us to reference certain objects (classes etc.) for use in class expressions, and axioms (which can
            // be added to an ontology).

            // Lets create an ontology, and add a declaration axiom to the ontology that declares the above class
            OWLOntology ontology = manager.createOntology(IRI.create("http://www.semanticweb.org/owlapi/ontologies/ontology"));
            // We can add a declaration axiom to the ontology, that essentially adds the class to the signature of
            // our ontology.
            OWLDeclarationAxiom declarationAxiom = factory.getOWLDeclarationAxiom(clsAMethodA);
            manager.addAxiom(ontology, declarationAxiom);
            // Note that it isn't necessary to add declarations to an ontology in order to use an entity.  For some
            // ontology formats (e.g. the Manchester Syntax), declarations will automatically be added in the saved
            // version of the ontology.
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }

    }
}
