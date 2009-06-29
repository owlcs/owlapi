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
public abstract class AbstractOWLIndividualRelationshipAxiomTestCase<P extends OWLPropertyExpression, O extends OWLObject> extends AbstractOWLDataFactoryTest{

    protected abstract P createProperty() throws Exception;

    protected abstract O createObject() throws Exception;

    protected abstract OWLIndividualAxiom createAxiom(OWLIndividual subject, P property, O object) throws OWLException;

    public void testCreation() throws Exception {
        assertNotNull(createAxiom(createOWLIndividual(), createProperty(), createObject()));
    }


    public void testEqualsPositive() throws Exception {
        OWLIndividual subject = createOWLIndividual();
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(subject, property, object);
        OWLIndividualAxiom axiomB = createAxiom(subject, property, object);
        assertEquals(axiomA, axiomB);
    }


    public void testEqualsNegative() throws Exception {
        // Different subject
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(createOWLIndividual(), property, object);
        OWLIndividualAxiom axiomB = createAxiom(createOWLIndividual(), property, object);
        assertNotEquals(axiomA, axiomB);
        // Different property
        OWLIndividual subject = createOWLIndividual();
        OWLIndividualAxiom axiomC = createAxiom(subject, createProperty(), object);
        OWLIndividualAxiom axiomD = createAxiom(subject, createProperty(), object);
        assertNotEquals(axiomC, axiomD);
        // Different object
        OWLIndividualAxiom axiomE = createAxiom(subject, property, createObject());
        OWLIndividualAxiom axiomF = createAxiom(subject, property, createObject());
        assertNotEquals(axiomE, axiomF);
    }


    public void testHashCode() throws Exception {
        OWLIndividual subject = createOWLIndividual();
        P property = createProperty();
        O object = createObject();
        OWLIndividualAxiom axiomA = createAxiom(subject, property, object);
        OWLIndividualAxiom axiomB = createAxiom(subject, property, object);
        assertEquals(axiomA.hashCode(), axiomB.hashCode());
    }
}
