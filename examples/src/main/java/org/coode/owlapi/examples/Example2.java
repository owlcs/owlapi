package org.coode.owlapi.examples;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.SimpleURIMapper;

import java.net.URI;
import java.util.Set;
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
 * Date: 11-Jan-2007<br><br>
 */
public class Example2 {

    public static void main(String[] args) {
        try {
            // We first need to obtain a copy of an OWLOntologyManager, which, as the
            // name suggests, manages a set of ontologies.  An ontology is unique within
            // an ontology manager.  To load multiple copies of an ontology, multiple managers
            // would have to be used.
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // All ontologies have a URI, which is used to identify the ontology.  You should
            // think of the ontology URI as the "name" of the ontology.  This URI frequently
            // resembles a Web address (i.e. http://...), but it is important to realise that
            // the ontology URI might not necessarily be resolvable.  In other words, we
            // can't necessarily get a document from the URI corresponding to the ontology
            // URI, which represents the ontology.
            // In order to have a concrete representation of an ontology (e.g. an RDF/XML
            // file), we MAP the ontology URI to a PHYSICAL URI.  We do this using a URIMapper

            // Let's create an ontology and name it "http://www.co-ode.org/ontologies/testont.owl"
            // We need to set up a mapping which points to a concrete file where the ontology will
            // be stored. (It's good practice to do this even if we don't intend to save the ontology).
            URI ontologyURI = URI.create("http://www.co-ode.org/ontologies/testont.owl");
            // Create a physical URI which can be resolved to point to where our ontology will be saved.
            URI physicalURI = URI.create("file:/tmp/MyOnt.owl");
            // Set up a mapping, which maps the ontology URI to the physical URI
            SimpleURIMapper mapper = new SimpleURIMapper(ontologyURI, physicalURI);
            manager.addURIMapper(mapper);

            // Now create the ontology - we use the ontology URI (not the physical URI)
            OWLOntology ontology = manager.createOntology(ontologyURI);
            // Now we want to specify that A is a subclass of B.  To do this, we add a subclass
            // axiom.  A subclass axiom is simply an object that specifies that one class is a
            // subclass of another class.
            // We need a data factory to create various object from.  Each ontology has a reference
            // to a data factory that we can use.
            OWLDataFactory factory = manager.getOWLDataFactory();
            // Get hold of references to class A and class B.  Note that the ontology does not
            // contain class A or classB, we simply get references to objects from a data factory that represent
            // class A and class B
            OWLClass clsA = factory.getOWLClass(URI.create(ontologyURI + "#A"));
            OWLClass clsB = factory.getOWLClass(URI.create(ontologyURI + "#B"));
            // Now create the axiom
            OWLAxiom axiom = factory.getOWLSubClassAxiom(clsA, clsB);
            // We now add the axiom to the ontology, so that the ontology states that
            // A is a subclass of B.  To do this we create an AddAxiom change object.
            AddAxiom addAxiom = new AddAxiom(ontology, axiom);
            // We now use the manager to apply the change
            manager.applyChange(addAxiom);

            // The ontology will now contain references to class A and class B - let's
            // print them out
            for(OWLClass cls : ontology.getReferencedClasses()) {
                System.out.println("Referenced class: " + cls);
            }
            // We should also find that B is a superclass of A
            Set<OWLDescription> superClasses = clsA.getSuperClasses(ontology);
            System.out.println("Superclasses of " + clsA + ":");
            for(OWLDescription desc : superClasses) {
                System.out.println(desc);
            }

            // Now save the ontology.  The ontology will be saved to the location where
            // we loaded it from, in the default ontology format
            manager.saveOntology(ontology);

        }
        catch (OWLException e) {
            e.printStackTrace();
        }
    }
}
