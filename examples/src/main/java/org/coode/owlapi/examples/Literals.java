package org.coode.owlapi.examples;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
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
 */
public class Literals {

    public static void main(String[] args) {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory factory = manager.getOWLDataFactory();

        // Get an untyped string literal
        OWLStringLiteral literal1 = factory.getOWLStringLiteral("My string literal");

        // Get an untyped string literal with a language tag
        OWLStringLiteral literal2 = factory.getOWLStringLiteral("My string literal", "en");

        // Typed literals are literals that are typed with a datatype

        // Create a typed literal to represent the integer 33
        OWLDatatype integerDatatype = factory.getOWLDatatype(OWL2Datatype.XSD_INTEGER.getIRI());
        OWLTypedLiteral literal3 = factory.getOWLTypedLiteral("33", integerDatatype);

        // There is are short cut methods on OWLDataFactory for creating typed literals with common datatypes
        // Internallym these methods create literals as above

        // Create a typed literal to represent the integer 33
        OWLTypedLiteral literal4 = factory.getOWLTypedLiteral(33);

        // Create a typed literal to represent the double 33.3
        OWLTypedLiteral literal5 = factory.getOWLTypedLiteral(33.3);

        // Create a typed literal to represent the boolean value true
        OWLTypedLiteral literal6 = factory.getOWLTypedLiteral(true);

    }
}
