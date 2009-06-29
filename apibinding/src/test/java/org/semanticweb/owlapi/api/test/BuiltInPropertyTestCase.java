package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
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
 * Date: 09-Jun-2009
 */
public class BuiltInPropertyTestCase extends AbstractOWLAPITestCase {

    public void testTopObjectPropertyPositive() {
        OWLObjectPropertyExpression prop = getFactory().getOWLTopObjectProperty();
        assertTrue(prop.isOWLTopObjectProperty());
    }

    public void testBottomObjectPropertyPositive() {
        OWLObjectPropertyExpression prop = getFactory().getOWLBottomObjectProperty();
        assertTrue(prop.isOWLBottomObjectProperty());
    }


    public void testTopObjectPropertyNegative() {
        OWLObjectPropertyExpression prop = getOWLObjectProperty("prop");
        assertFalse(prop.isOWLTopObjectProperty());
    }

    public void testBottomObjectPropertyNegative() {
        OWLObjectPropertyExpression prop = getOWLObjectProperty("prop");
        assertFalse(prop.isOWLBottomObjectProperty());
    }

    public void testTopDataPropertyPositive() {
        OWLDataPropertyExpression prop = getFactory().getOWLTopDataProperty();
        assertTrue(prop.isOWLTopDataProperty());
    }

    public void testBottomDataPropertyPositive() {
        OWLDataPropertyExpression prop = getFactory().getOWLBottomDataProperty();
        assertTrue(prop.isOWLBottomDataProperty());
    }


    public void testTopDataPropertyNegative() {
        OWLDataPropertyExpression prop = getOWLDataProperty("prop");
        assertFalse(prop.isOWLTopDataProperty());
    }

    public void testBottomDataPropertyNegative() {
        OWLDataPropertyExpression prop = getOWLDataProperty("prop");
        assertFalse(prop.isOWLBottomDataProperty());
    }
}
