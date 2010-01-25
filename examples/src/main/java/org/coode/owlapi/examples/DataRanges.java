package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.SystemOutDocumentTarget;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
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
 * Date: 20-Dec-2009
 * </p>
 * This example shows how to create dataranges
 */
public class DataRanges {

    public static void main(String[] args) {

        try {
            // OWLDataRange is the superclass of all data ranges in the OWL API.
            // Data ranges are used as the types of literals, as the ranges for data properties,
            // as filler for data reatrictions.

            // Get hold of a manager to work with
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLDataFactory factory = manager.getOWLDataFactory();

            // OWLDatatype represents named datatypes in OWL.  These are a bit like classes whose instances are
            // data values

            // OWLDatatype objects are obtained from a data factory.
            // The OWL2Datatype enum defines built in OWL 2 Datatypes

            // Get hold of the integer datatype
            OWLDatatype integer = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());

            // For common data types there are some convenience methods of OWLDataFactory.  For example

            OWLDatatype integerDatatype = factory.getIntegerOWLDatatype();
            OWLDatatype floatDatatype = factory.getFloatOWLDatatype();
            OWLDatatype doubleDatatype = factory.getDoubleOWLDatatype();
            OWLDatatype booleanDatatype = factory.getBooleanOWLDatatype();

            // The top datatype (analgous to owl:Thing) is rdfs:Literal, which can be obtained from the data factory
            OWLDatatype rdfsLiteral = factory.getTopDatatype();

            // Custom data ranges can be built up from these basic datatypes.  For example, it is possible to
            // restrict a datatype using facets from XML Schema Datatypes.  For example, lets create a data range
            // that describes integers that are greater or equal to 18
            // To do this, we restrict the xsd:integer datatype using the xsd:minInclusive facet with a value
            // of 18.
            // Get hold of a literal that is an integer value 18
            OWLTypedLiteral eighteen = factory.getOWLTypedLiteral(18);
            // Now create the restriction.  The OWLFacet enum provides an enumeration of the various facets that can be used
            OWLDatatypeRestriction integerGE18 = factory.getOWLDatatypeRestriction(integer, OWLFacet.MIN_INCLUSIVE, eighteen);

            // We could use this datatype in restriction, as the range of data properties etc.
            // For example, if we want to restrict the range of the :hasAge data property to 18 or more
            // we specify its range as this data range

            PrefixManager pm = new DefaultPrefixManager("http://www.semanticweb.org/ontologies/dataranges#");
            OWLDataProperty hasAge = factory.getOWLDataProperty(":hasAge", pm);
            OWLDataPropertyRangeAxiom rangeAxiom = factory.getOWLDataPropertyRangeAxiom(hasAge, integerGE18);

            OWLOntology ontology = manager.createOntology(IRI.create("http://www.semanticweb.org/ontologies/dataranges"));
            // Add the range axiom to our ontology
            manager.addAxiom(ontology, rangeAxiom);


            // For creating datatype restrictions on integers or doubles there are some convenience methods on OWLDataFactory
            // For example:
            // Create a data range of integers greater or equal to 60
            OWLDatatypeRestriction integerGE60 = factory.getOWLDatatypeMinInclusiveRestriction(60);
            // Create a data range of integers less than 16
            OWLDatatypeRestriction integerLT16 = factory.getOWLDatatypeMaxExclusiveRestriction(18);

            // In OWL 2 it is possible to represent the intersection, union and complement of data types
            // For example, we could create a union of data ranges of the data range
            // integer less than 16 or integer greater or equal to 60
            OWLDataUnionOf concessionaryAge = factory.getOWLDataUnionOf(integerLT16, integerGE60);

            // We can also coin names for custom data ranges.  To do this we use an OWLDatatypeDefintionAxiom
            // Get hold of a named datarange (datatype) that will be used to assign a name to our above datatype
            OWLDatatype concessionaryAgeDatatype = factory.getOWLDatatype(":ConcessionaryAge", pm);
            // Now create a datatype definition axiom
            OWLDatatypeDefinitionAxiom datatypeDef = factory.getOWLDatatypeDefinitionAxiom(concessionaryAgeDatatype, concessionaryAge);

            // Add the definition to our ontology
            manager.addAxiom(ontology, datatypeDef);

            // Dump our ontology
            manager.saveOntology(ontology, new SystemOutDocumentTarget());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Could not create ontology: " + e.getMessage());
        }
        catch (OWLOntologyStorageException e) {
            System.out.println("Could not save ontology: " + e.getMessage());
        }


    }
}
