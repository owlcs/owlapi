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
public abstract class AbstractOWLPropertyCharacteristicTestCase<P extends OWLPropertyExpression> extends AbstractOWLDataFactoryTest {


    public void testCreation() throws Exception {
        OWLPropertyAxiom axiom = createOWLPropertyAxiom(createProperty());
        assertNotNull(axiom);
    }


    public void testEqualsPositive() throws Exception {
        P prop = createProperty();
        OWLPropertyAxiom axiomA = createOWLPropertyAxiom(prop);
        OWLPropertyAxiom axiomB = createOWLPropertyAxiom(prop);
        assertEquals(axiomA, axiomB);
    }


    public void testEqualsNegative() throws Exception {
        OWLPropertyAxiom axiomA = createOWLPropertyAxiom(createProperty());
        OWLPropertyAxiom axiomB = createOWLPropertyAxiom(createProperty());
        assertNotEquals(axiomA, axiomB);
    }

    protected abstract P createProperty() throws Exception;

    protected abstract OWLPropertyAxiom createOWLPropertyAxiom(P property) throws OWLException;


    public void testHashCode() throws Exception {
        P prop = createProperty();
        OWLPropertyAxiom axiomA = createOWLPropertyAxiom(prop);
        OWLPropertyAxiom axiomB = createOWLPropertyAxiom(prop);
        assertEquals(axiomA.hashCode(), axiomB.hashCode());
    }
}
