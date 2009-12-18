package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
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
            OWLTypedLiteral eighteenConstant = factory.getOWLTypedLiteral(18);
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
