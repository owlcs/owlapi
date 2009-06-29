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
public abstract class AbstractOWLObjectCardinalityRestrictionTestCase extends AbstractOWLDataFactoryTest {


    protected abstract OWLObjectCardinalityRestriction createRestriction(OWLObjectProperty prop, int cardinality) throws Exception;

    protected abstract OWLObjectCardinalityRestriction createRestriction(OWLObjectProperty prop, int cardinality, OWLClassExpression classExpression) throws Exception;

    public void testCreation() throws Exception {
        OWLObjectProperty prop = getOWLDataFactory().getOWLObjectProperty(createIRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = createRestriction(prop, cardinality);
        assertNotNull(restA);
        OWLClassExpression cls = getOWLDataFactory().getOWLClass(createIRI());
        OWLObjectCardinalityRestriction restB = createRestriction(prop, cardinality, cls);
        assertNotNull(restB);
    }


    public void testEqualsPositive() throws Exception {
        OWLObjectProperty prop = getOWLDataFactory().getOWLObjectProperty(createIRI());
        int cardinality = 3;
        OWLObjectCardinalityRestriction restA = createRestriction(prop, cardinality);
        OWLObjectCardinalityRestriction restB = createRestriction(prop, cardinality);
        assertEquals(restA, restB);
        OWLClassExpression cls = getOWLDataFactory().getOWLClass(createIRI());
        OWLObjectCardinalityRestriction restC = createRestriction(prop, cardinality, cls);
        OWLObjectCardinalityRestriction restD = createRestriction(prop, cardinality, cls);
        assertEquals(restC, restD);
    }


    public void testEqualsNegative() throws Exception {
        OWLObjectProperty prop = getOWLDataFactory().getOWLObjectProperty(createIRI());
        // Different cardinality
        OWLObjectCardinalityRestriction restA = createRestriction(prop, 3);
        OWLObjectCardinalityRestriction restB = createRestriction(prop, 4);
        assertNotEquals(restA, restB);
        // Different property
        OWLObjectCardinalityRestriction restC = createRestriction(getOWLDataFactory().getOWLObjectProperty(createIRI()), 3);
        OWLObjectCardinalityRestriction restD = createRestriction(getOWLDataFactory().getOWLObjectProperty(createIRI()), 3);
        assertNotEquals(restC, restD);
        // Different filler
        OWLObjectCardinalityRestriction restE = createRestriction(prop, 3, getOWLDataFactory().getOWLClass(createIRI()));
        OWLObjectCardinalityRestriction restF = createRestriction(prop, 3, getOWLDataFactory().getOWLClass(createIRI()));
        assertNotEquals(restE, restF);
    }


    public void testHashCode() throws Exception {
         OWLObjectProperty prop = getOWLDataFactory().getOWLObjectProperty(createIRI());
        int cardinality = 3;
        OWLClassExpression cls = getOWLDataFactory().getOWLClass(createIRI());
        OWLObjectCardinalityRestriction restA = createRestriction(prop, cardinality, cls);
        OWLObjectCardinalityRestriction restB = createRestriction(prop, cardinality, cls);
        assertEquals(restA.hashCode(), restB.hashCode());
    }
}
