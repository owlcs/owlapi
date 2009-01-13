package org.semanticweb.owl.model;

import junit.framework.TestCase;

import java.net.URI;

import uk.ac.manchester.cs.owl.OWLDataFactoryImpl;
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
 *
 * The base for test cases that need a data factory.
 */
public abstract class AbstractOWLDataFactoryTest extends AbstractOWLTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    public static URI createURI() {
        return TestUtils.createURI();
    }

    public abstract void testCreation() throws Exception;

    public abstract void testEqualsPositive() throws Exception;

    public abstract void testEqualsNegative() throws Exception;

    public abstract void testHashCode() throws Exception;

    public static void assertNotEquals(Object objA, Object objB) {
        assertFalse(objA.equals(objB));
    }

    protected OWLObjectProperty createOWLObjectProperty() throws Exception {
        return getOWLDataFactory().getOWLObjectProperty(createURI());
    }

    protected OWLClass createOWLClass() throws Exception {
        return getOWLDataFactory().getOWLClass(createURI());
    }

    protected OWLIndividual createOWLIndividual() throws Exception {
        return getOWLDataFactory().getOWLIndividual(createURI());
    }

    protected OWLDataProperty createOWLDataProperty() throws OWLException {
        return getOWLDataFactory().getOWLDataProperty(createURI());
    }

    protected OWLDataType createOWLDataType() throws OWLException {
        return getOWLDataFactory().getOWLDataType(createURI());
    }

    protected OWLTypedConstant createOWLTypedConstant() throws OWLException {
        return getOWLDataFactory().getOWLTypedConstant("Test" + System.currentTimeMillis(), createOWLDataType());
    }


}
