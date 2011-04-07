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
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Jun-2007<br><br>
 * <p/>
 * This example shows how to create and read annotations.
 */
public class Example10 {

    public static final String DOCUMENT_IRI = "http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl";


    public static void main(String[] args) {
        try {
            // Create our manager
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();

            // Load the pizza ontology
            OWLOntology ont = man.loadOntologyFromOntologyDocument(IRI.create(DOCUMENT_IRI));
            System.out.println("Loaded: " + ont.getOntologyID());

            // We want to add a comment to the pizza class.
            // First, we need to obtain a reference to the pizza class
            OWLDataFactory df = man.getOWLDataFactory();
            OWLClass pizzaCls = df.getOWLClass(IRI.create(ont.getOntologyID().getOntologyIRI().toString() + "#Pizza"));

            // Now we create the content of our comment.  In this case we simply want a plain string literal.
            // We'll attach a language to the comment to specify that our comment is written in English (en).
            OWLAnnotation commentAnno = df.getOWLAnnotation(
                    df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI()),
                    df.getOWLLiteral("A class which represents pizzas", "en"));

            // Specify that the pizza class has an annotation - to do this we attach an entity annotation using
            // an entity annotation axiom (remember, classes are entities)
            OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(pizzaCls.getIRI(), commentAnno);

            // Add the axiom to the ontology
            man.applyChange(new AddAxiom(ont, ax));

            // Now lets add a version info annotation to the ontology.  There is no 'standard' OWL 1.1 annotation
            // object for this, like there is for comments and labels, so the creation of the annotation is a bit
            // more involved.
            // First we'll create a constant for the annotation value.  Version info should probably contain a
            // version number for the ontology, but in this case, we'll add some text to describe why the version
            // has been updated
            OWLLiteral lit = df.getOWLLiteral("Added a comment to the pizza class");
            // The above constant is just a plain literal containing the version info text/comment
            // we need to create an annotation, which pairs a URI with the constant
            OWLAnnotation anno = df.getOWLAnnotation(df.getOWLAnnotationProperty(OWLRDFVocabulary.OWL_VERSION_INFO.getIRI()), lit);
            // Now we can add this as an ontology annotation
            // Apply the change in the usual way
            man.applyChange(new AddOntologyAnnotation(ont, anno));

            ///////////////////////////////////////////////////////////////////////////////////////////////////////

            // The pizza ontology has labels attached to most classes which are translations of class names into
            // Portuguese (pt) we can access these and print them out.
            // At this point, it is worth noting that constants can be typed or untyped.  If constants are untyped
            // then they can have language tags, which are optional - typed constant cannot have language tags.

            // For each class in the ontology, we retrieve its annotations and sift through them.  If the
            // annotation annotates the class with a constant which is untyped then we check the language
            // tag to see if it is Portugeuse.

            // Firstly, get the annotation property for rdfs:label
            OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());

            for (OWLClass cls : ont.getClassesInSignature()) {
                // Get the annotations on the class that use the label property
                for (OWLAnnotation annotation : cls.getAnnotations(ont, label)) {
                    if (annotation.getValue() instanceof OWLLiteral) {
                        OWLLiteral val = (OWLLiteral) annotation.getValue();
                        if (val.hasLang("pt")) {
                            System.out.println(cls + " -> " + val.getLiteral());
                        }
                    }
                }
            }
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not create ontology");
        }
    }
}
