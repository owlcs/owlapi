package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
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
