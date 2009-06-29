package org.semanticweb.owlapi.model;

/*
 * Copyright (C) 2008, University of Manchester
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
 * Information Management Group<br>
 * Date: 06-Jun-2008<br><br>
 */
public class ObjectPropertySimplifierTestCase extends AbstractOWLTestCase {

    public void testNamedSimplification() {
        OWLObjectProperty p = getOWLDataFactory().getOWLObjectProperty(IRI.create("p"));
        OWLObjectPropertyExpression exp = p.getSimplified();
        assertEquals(p, exp);
    }

    public void testInverseSimplification() {
        OWLObjectProperty p = getOWLDataFactory().getOWLObjectProperty(IRI.create("p"));
        OWLObjectPropertyExpression inv = getOWLDataFactory().getOWLObjectInverseOf(p);
        OWLObjectPropertyExpression exp = inv.getSimplified();
        assertEquals(inv, exp);
    }

    public void testInverseInverseSimplification() {
        OWLObjectProperty p = getOWLDataFactory().getOWLObjectProperty(IRI.create("p"));
        OWLObjectPropertyExpression inv = getOWLDataFactory().getOWLObjectInverseOf(p);
        OWLObjectPropertyExpression inv2 = getOWLDataFactory().getOWLObjectInverseOf(inv);
        OWLObjectPropertyExpression exp = inv2.getSimplified();
        assertEquals(p, exp);
    }

    public void testInverseInverseInverseSimplification() {
        OWLObjectProperty p = getOWLDataFactory().getOWLObjectProperty(IRI.create("p"));
        OWLObjectPropertyExpression inv = getOWLDataFactory().getOWLObjectInverseOf(p);
        OWLObjectPropertyExpression inv2 = getOWLDataFactory().getOWLObjectInverseOf(inv);
        OWLObjectPropertyExpression inv3 = getOWLDataFactory().getOWLObjectInverseOf(inv2);
        OWLObjectPropertyExpression exp = inv3.getSimplified();
        assertEquals(inv, exp);
    }
}
