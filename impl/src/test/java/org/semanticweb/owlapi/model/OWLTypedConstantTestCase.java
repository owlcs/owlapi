package org.semanticweb.owlapi.model;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Bio-Health Informatics Group
 * Date: 25-Oct-2006
 */
public class OWLTypedConstantTestCase extends AbstractOWLDataFactoryTest {


    public void testCreation() throws Exception {
        OWLDatatype dt = getOWLDataFactory().getOWLDatatype(createIRI());
        OWLTypedLiteral conA = getOWLDataFactory().getOWLTypedLiteral("3", dt);
        assertNotNull(conA);
    }


    public void testEqualsPositive() throws Exception {
        OWLDatatype dt = getOWLDataFactory().getOWLDatatype(createIRI());
        OWLTypedLiteral conA = getOWLDataFactory().getOWLTypedLiteral("3", dt);
        OWLTypedLiteral conB = getOWLDataFactory().getOWLTypedLiteral("3", dt);
        assertEquals(conA, conB);
    }


    public void testEqualsNegative() throws Exception {
        // Different datatypes - same literal
        OWLDatatype dtA = getOWLDataFactory().getOWLDatatype(createIRI());
        OWLTypedLiteral conA = getOWLDataFactory().getOWLTypedLiteral("3", dtA);
        OWLDatatype dtB = getOWLDataFactory().getOWLDatatype(createIRI());
        OWLTypedLiteral conB = getOWLDataFactory().getOWLTypedLiteral("3", dtB);
        assertNotEquals(conA, conB);
        // Different literals - same datatype
        OWLDatatype dtC = getOWLDataFactory().getOWLDatatype(createIRI());
        OWLTypedLiteral conC = getOWLDataFactory().getOWLTypedLiteral("3", dtC);
        OWLTypedLiteral conD = getOWLDataFactory().getOWLTypedLiteral("4", dtC);
        assertNotEquals(conC, conD);
    }


    public void testHashCode() throws Exception {
        OWLDatatype dt = getOWLDataFactory().getOWLDatatype(createIRI());
        OWLTypedLiteral conA = getOWLDataFactory().getOWLTypedLiteral("3", dt);
        OWLTypedLiteral conB = getOWLDataFactory().getOWLTypedLiteral("3", dt);
        assertEquals(conA.hashCode(), conB.hashCode());
    }
}
