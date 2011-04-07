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
import org.semanticweb.owlapi.vocab.OWLFacet;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 24-May-2007<br><br>
 * <p/>
 * This example shows how to work with dataranges.  OWL 1.1 allows
 * data ranges to be created by taking a base datatype e.g. int, string etc.
 * and then by applying facets to restrict the datarange. For example,
 * int greater than 18
 */
public class Example7 {

    public static void main(String[] args) {
        try {
            OWLOntologyManager man = OWLManager.createOWLOntologyManager();
            String base = "http://org.semanticweb.datarangeexample";
            OWLOntology ont = man.createOntology(IRI.create(base));

            // We want to add an axiom to our ontology that states that adults
            // have an age greater than 18.  To do this, we will create a restriction
            // along a hasAge property, with a filler that corresponds to the set
            // of integers greater than 18.

            // First get a reference to our hasAge property
            OWLDataFactory factory = man.getOWLDataFactory();
            OWLDataProperty hasAge = factory.getOWLDataProperty(IRI.create(base + "hasAge"));
            // For completeness, we will make hasAge functional by adding an axiom to state this
            OWLFunctionalDataPropertyAxiom funcAx = factory.getOWLFunctionalDataPropertyAxiom(hasAge);
            man.applyChange(new AddAxiom(ont, funcAx));

            // Now create the data range which correponds to int greater than 18.  To do this, we
            // get hold of the int datatype and then restrict it with a minInclusive facet restriction.
            OWLDatatype intDatatype = factory.getIntegerOWLDatatype();
            // Create the value "18", which is an int.
            OWLLiteral eighteenConstant = factory.getOWLLiteral(18);
            // Now create our custom datarange, which is int greater than or equal to 18.  To do this,
            // we need the minInclusive facet
            OWLFacet facet = OWLFacet.MIN_INCLUSIVE;
            // Create the restricted data range by applying the facet restriction with a value of 18 to int
            OWLDataRange intGreaterThan18 = factory.getOWLDatatypeRestriction(intDatatype,
                    facet,
                    eighteenConstant);
            // Now we can use this in our datatype restriction on hasAge
            OWLClassExpression thingsWithAgeGreaterOrEqualTo18 = factory.getOWLDataSomeValuesFrom(hasAge, intGreaterThan18);
            // Now we want to say all adults have an age that is greater or equal to 18 - i.e. Adult is a subclass of
            // hasAge some int[>= 18]
            // Obtain a reference to the Adult class
            OWLClass adult = factory.getOWLClass(IRI.create(base + "#Adult"));
            // Now make adult a subclass of the things that have an age greater to or equal to 18
            OWLSubClassOfAxiom ax = factory.getOWLSubClassOfAxiom(adult, thingsWithAgeGreaterOrEqualTo18);
            // Add our axiom to the ontology
            man.applyChange(new AddAxiom(ont, ax));


        }
        catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

}
