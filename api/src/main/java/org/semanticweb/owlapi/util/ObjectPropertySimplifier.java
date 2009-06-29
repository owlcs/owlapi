package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.*;
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
 *
 * This utility class can be used to obtain an object property expression in its simplest form.
 * Let P be an object property name and PE a property expression, then the simplification is
 * inductively defined as:
 *
 * simp(P) = P
 * simp(inv(P)) = inv(P)
 * simp(inv(inv(PE)) = simp(PE)
 */
public class ObjectPropertySimplifier {

    private OWLDataFactory dataFactory;

    private Simplifier simplifier;

    public ObjectPropertySimplifier(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
        this.simplifier = new Simplifier();

    }


    /**
     * Gets an object property expression in its simplest form.
     * @param prop The object property expression to be simplified.
     * @return The simplest form of the object property expression.
     */
    public OWLObjectPropertyExpression getSimplified(OWLObjectPropertyExpression prop) {
        simplifier.reset();
        prop.accept(simplifier);
        if(simplifier.isInverse()) {
            return dataFactory.getOWLObjectInverseOf(simplifier.getProperty());
        }
        else {
            return simplifier.getProperty();
        }
    }

    private static class Simplifier implements OWLPropertyExpressionVisitor {

        private OWLObjectProperty property;

        private int depth;

        public void reset() {
            depth = 0;
            property = null;
        }

        public OWLObjectProperty getProperty() {
            return property;
        }


        public int getDepth() {
            return depth;
        }

        public boolean isInverse() {
            return depth % 2 != 0;
        }


        public void visit(OWLObjectProperty property) {
            this.property = property;
        }


        public void visit(OWLObjectInverseOf property) {
            depth++;
            property.getInverse().accept(this);
        }


        public void visit(OWLDataProperty property) {
        }
    }
}
