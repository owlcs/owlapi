package org.semanticweb.owl.model;
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
public class OWLUntypedConstantTestCase extends AbstractOWLDataFactoryTest {

    public void testCreation() throws Exception {
        OWLUntypedConstant conA = getOWLDataFactory().getOWLUntypedConstant("TEST");
        assertNotNull(conA);
        OWLUntypedConstant conB = getOWLDataFactory().getOWLUntypedConstant("TEST", "LANG");
        assertNotNull(conB);
    }


    public void testEqualsPositive() throws Exception {
        OWLUntypedConstant conA = getOWLDataFactory().getOWLUntypedConstant("TEST");
        OWLUntypedConstant conB = getOWLDataFactory().getOWLUntypedConstant("TEST");
        assertEquals(conA, conB);
        OWLUntypedConstant conC = getOWLDataFactory().getOWLUntypedConstant("TEST", "LANG");
        OWLUntypedConstant conD = getOWLDataFactory().getOWLUntypedConstant("TEST", "LANG");
        assertEquals(conC, conD);
    }


    public void testEqualsNegative() throws Exception {
        OWLUntypedConstant conA = getOWLDataFactory().getOWLUntypedConstant("TEST");
        OWLUntypedConstant conB = getOWLDataFactory().getOWLUntypedConstant("OTHER");
        assertNotEquals(conA, conB);
        OWLUntypedConstant conC = getOWLDataFactory().getOWLUntypedConstant("TEST", "LANG");
        OWLUntypedConstant conD = getOWLDataFactory().getOWLUntypedConstant("TEST", "OTHER_LANG");
        assertNotEquals(conC, conD);
        OWLUntypedConstant conE = getOWLDataFactory().getOWLUntypedConstant("TEST", "LANG");
        OWLUntypedConstant conF = getOWLDataFactory().getOWLUntypedConstant("OTHER", "LANG");
        assertNotEquals(conE, conF);
    }


    public void testHashCode() throws Exception {
        OWLUntypedConstant conA = getOWLDataFactory().getOWLUntypedConstant("TEST", "LANG");
        OWLUntypedConstant conB = getOWLDataFactory().getOWLUntypedConstant("TEST", "LANG");
        assertEquals(conA.hashCode(), conB.hashCode());
    }
}
