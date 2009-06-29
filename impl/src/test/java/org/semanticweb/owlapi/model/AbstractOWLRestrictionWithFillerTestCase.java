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
public abstract class AbstractOWLRestrictionWithFillerTestCase<P extends OWLPropertyExpression, F extends OWLObject> extends AbstractOWLRestrictionTestCase<P> {


    protected abstract OWLRestriction createRestriction(P prop, F filler) throws Exception;


    protected OWLRestriction createRestriction(P prop) throws Exception {
        return createRestriction(prop, createFiller());
    }


    protected abstract F createFiller() throws Exception;

    public void testCreation() throws Exception {
        assertNotNull(createRestriction(createProperty(), createFiller()));
    }


    public void testEqualsPositive() throws Exception {
        P prop = createProperty();
        F filler = createFiller();
        OWLRestriction restA = createRestriction(prop, filler);
        OWLRestriction restB = createRestriction(prop, filler);
        assertEquals(restA, restB);
    }


    public void testEqualsNegative() throws Exception {
        // Different filler
        P prop = createProperty();
        OWLRestriction restA = createRestriction(prop, createFiller());
        OWLRestriction restB = createRestriction(prop, createFiller());
        assertNotEquals(restA, restB);
        // Different property
        F filler = createFiller();
        OWLRestriction restC = createRestriction(createProperty(), filler);
        OWLRestriction restD = createRestriction(createProperty(), filler);
        assertNotEquals(restC, restD);
    }


    public void testHashCode() throws Exception {
        P prop = createProperty();
        F filler = createFiller();
        OWLRestriction restA = createRestriction(prop, filler);
        OWLRestriction restB = createRestriction(prop, filler);
        assertEquals(restA.hashCode(), restB.hashCode());
    }
}
