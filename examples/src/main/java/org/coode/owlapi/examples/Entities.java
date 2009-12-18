package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
/*
 * Copyright (C) 2009, University of Manchester
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
