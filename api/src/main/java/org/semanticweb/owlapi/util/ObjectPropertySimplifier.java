/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;


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

        public Simplifier() {
			// TODO Auto-generated constructor stub
		}

		public void reset() {
            depth = 0;
            property = null;
        }

        public OWLObjectProperty getProperty() {
            return property;
        }


//        public int getDepth() {
//            return depth;
//        }

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
