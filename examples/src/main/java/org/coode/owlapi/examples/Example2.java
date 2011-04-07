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
import org.semanticweb.owlapi.util.SimpleIRIMapper;

import java.net.URI;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Jan-2007<br><br>
 */
public class Example2 {

    public static void main(String[] args) {
        try {
            // Create the manager that we will use to load ontologies.
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();

            // Ontologies cna have an IRI, which is used to identify the ontology.  You should
            // think of the ontology IRI as the "name" of the ontology.  This IRI frequently
            // resembles a Web address (i.e. http://...), but it is important to realise that
            // the ontology IRI might not necessarily be resolvable.  In other words, we
            // can't necessarily get a document from the URL corresponding to the ontology
            // IRI, which represents the ontology.
            // In order to have a concrete representation of an ontology (e.g. an RDF/XML
            // file), we MAP the ontology IRI to a PHYSICAL URI.  We do this using an IRIMapper

            // Let's create an ontology and name it "http://www.co-ode.org/ontologies/testont.owl"
            // We need to set up a mapping which points to a concrete file where the ontology will
            // be stored. (It's good practice to do this even if we don't intend to save the ontology).
            IRI ontologyIRI = IRI.create("http://www.co-ode.org/ontologies/testont.owl");
            // Create the document IRI for our ontology
            IRI documentIRI = IRI.create("file:/tmp/MyOnt.owl");
            // Set up a mapping, which maps the ontology to the document IRI
            SimpleIRIMapper mapper = new SimpleIRIMapper(ontologyIRI, documentIRI);
            manager.addIRIMapper(mapper);

            // Now create the ontology - we use the ontology IRI (not the physical URI)
            OWLOntology ontology = manager.createOntology(ontologyIRI);
            // Now we want to specify that A is a subclass of B.  To do this, we add a subclass
            // axiom.  A subclass axiom is simply an object that specifies that one class is a
            // subclass of another class.
            // We need a data factory to create various object from.  Each manager has a reference
            // to a data factory that we can use.
            OWLDataFactory factory = manager.getOWLDataFactory();
            // Get hold of references to class A and class B.  Note that the ontology does not
            // contain class A or classB, we simply get references to objects from a data factory that represent
            // class A and class B
            OWLClass clsA = factory.getOWLClass(IRI.create(ontologyIRI + "#A"));
            OWLClass clsB = factory.getOWLClass(IRI.create(ontologyIRI + "#B"));
            // Now create the axiom
            OWLAxiom axiom = factory.getOWLSubClassOfAxiom(clsA, clsB);
            // We now add the axiom to the ontology, so that the ontology states that
            // A is a subclass of B.  To do this we create an AddAxiom change object.
            // At this stage neither classes A or B, or the axiom are contained in the ontology. We have to
            // add the axiom to the ontology.
            AddAxiom addAxiom = new AddAxiom(ontology, axiom);
            // We now use the manager to apply the change
            manager.applyChange(addAxiom);

            // The ontology will now contain references to class A and class B - that is, class A and class B
            // are contained within the SIGNATURE of the ontology let's print them out
            for (OWLClass cls : ontology.getClassesInSignature()) {
                System.out.println("Referenced class: " + cls);
            }
            // We should also find that B is an ASSERTED superclass of A
            Set<OWLClassExpression> superClasses = clsA.getSuperClasses(ontology);
            System.out.println("Asserted superclasses of " + clsA + ":");
            for (OWLClassExpression desc : superClasses) {
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
