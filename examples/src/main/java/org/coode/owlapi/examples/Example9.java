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
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Jun-2007<br><br>
 * <p/>
 * This example shows how to examine the restrictions on a class.
 */
public class Example9 {

    public static final String DOCUMENT_IRI = "http://www.co-ode.org/ontologies/pizza/2007/02/12/pizza.owl";

    public static void main(String[] args) {
        try {
            // Create our manager
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();

            // Load the pizza ontology
            OWLOntology ont = man.loadOntologyFromOntologyDocument(IRI.create(DOCUMENT_IRI));
            System.out.println("Loaded: " + ont.getOntologyID());

            // We want to examine the restrictions on margherita pizza.  To do this, we
            // need to obtain a reference to the margherita pizza class.  In this case, we
            // know the URI for margherita pizza (it happens to be the ontology URI - the base
            // URI plus #Margherita - note that this isn't always the case.  A class may have
            // a URI that bears no resemblance to the ontology URI which contains axioms about the
            // class).
            IRI margheritaPizzaIRI = IRI.create(ont.getOntologyID().getOntologyIRI() + "#Margherita");
            OWLClass margheritaPizza = man.getOWLDataFactory().getOWLClass(margheritaPizzaIRI);

            // Now we want to collect the properties which are used in existential restrictions on the
            // class.  To do this, we will create a utility class - RestrictionVisitor, which acts as
            // a filter for existential restrictions.  This uses the Visitor Pattern (google Visitor Design
            // Pattern for more information on this design pattern, or see http://en.wikipedia.org/wiki/Visitor_pattern)
            RestrictionVisitor restrictionVisitor = new RestrictionVisitor(Collections.singleton(ont));
            // In this case, restrictions are used as (anonymous) superclasses, so to get the restrictions on
            // margherita pizza we need to obtain the subclass axioms for margherita pizza.
            for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(margheritaPizza)) {
                OWLClassExpression superCls = ax.getSuperClass();
                // Ask our superclass to accept a visit from the RestrictionVisitor - if it is an
                // existential restiction then our restriction visitor will answer it - if not our
                // visitor will ignore it
                superCls.accept(restrictionVisitor);
            }
            // Our RestrictionVisitor has now collected all of the properties that have been restricted in existential
            // restrictions - print them out.
            System.out.println("Restricted properties for " + margheritaPizza + ": " + restrictionVisitor.getRestrictedProperties().size());
            for (OWLObjectPropertyExpression prop : restrictionVisitor.getRestrictedProperties()) {
                System.out.println("    " + prop);
            }

        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not load ontology: " + e.getMessage());
        }
    }


    /**
     * Visits existential restrictions and collects the properties which are restricted
     */
    private static class RestrictionVisitor extends OWLClassExpressionVisitorAdapter {

        private boolean processInherited = true;

        private Set<OWLClass> processedClasses;

        private Set<OWLObjectPropertyExpression> restrictedProperties;

        private Set<OWLOntology> onts;

        public RestrictionVisitor(Set<OWLOntology> onts) {
            restrictedProperties = new HashSet<OWLObjectPropertyExpression>();
            processedClasses = new HashSet<OWLClass>();
            this.onts = onts;
        }


        public void setProcessInherited(boolean processInherited) {
            this.processInherited = processInherited;
        }


        public Set<OWLObjectPropertyExpression> getRestrictedProperties() {
            return restrictedProperties;
        }


        public void visit(OWLClass desc) {
            if (processInherited && !processedClasses.contains(desc)) {
                // If we are processing inherited restrictions then
                // we recursively visit named supers.  Note that we
                // need to keep track of the classes that we have processed
                // so that we don't get caught out by cycles in the taxonomy
                processedClasses.add(desc);
                for (OWLOntology ont : onts) {
                    for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(desc)) {
                        ax.getSuperClass().accept(this);
                    }
                }
            }
        }


        public void reset() {
            processedClasses.clear();
            restrictedProperties.clear();
        }


        public void visit(OWLObjectSomeValuesFrom desc) {
            // This method gets called when a class expression is an
            // existential (someValuesFrom) restriction and it asks us to visit it
            restrictedProperties.add(desc.getProperty());
        }
    }

}
