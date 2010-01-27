package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
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
                    df.getOWLStringLiteral("A class which represents pizzas", "en"));

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
            OWLLiteral lit = df.getOWLTypedLiteral("Added a comment to the pizza class");
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
                        if (!val.isOWLTypedLiteral()) {
                            // The value isn't a typed constant, so we can safely obtain it
                            // as an OWLRDFTextLiteral and check the lang is Portuguese (pt)
                            if (val.asOWLStringLiteral().hasLang("pt")) {
                                System.out.println(cls + " -> " + val.getLiteral());
                            }
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
