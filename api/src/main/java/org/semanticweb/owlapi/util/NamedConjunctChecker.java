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

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Feb-2007<br><br>
 * <p/>
 * A utility class which checks if a class expression has a named conjunct or
 * a specific named conjunct.
 */
public class NamedConjunctChecker {

    private OWLClass conjunct;

    private boolean found;

    private boolean collect;

    private Set<OWLClass> conjuncts;

    private NamedConjunctCheckerVisitor visitor;


    public NamedConjunctChecker() {
        visitor = new NamedConjunctCheckerVisitor();
        conjuncts = new HashSet<OWLClass>();
    }


    /**
     * Checks whether a named class is a conjunct in a given class expression.
     * For class expressions which aren't named classes or object intersections this
     * method will always return false.
     * @param conjunct The conjunct to check for
     * @param classExpression The expression to be checked
     */
    public boolean isNamedConjunct(OWLClass conjunct, OWLClassExpression classExpression) {
        reset();
        this.conjunct = conjunct;
        classExpression.accept(visitor);
        return found;
    }


    /**
     * Checks whether the specified expression has a named conjunct.  For
     * For class expressions which aren't named classes or object intersections this
     * method will always return false.
     * @param classExpression The expression to be checked.
     * @return <code>true</code> if the expression is in fact a named class (<code>OWLClass</code>)
     *         or if the expression is an intersection that has a named operand (included nested intersections),
     *         otherwise <code>false</code>
     */
    public boolean hasNamedConjunct(OWLClassExpression classExpression) {
        reset();
        conjunct = null;
        classExpression.accept(visitor);
        return found;
    }


    private void reset() {
        found = false;
        collect = false;
    }


    /**
     * Gets the named conjuncts for the specified expression.
     * @param classExpression The expression whose conjuncts are to be retrieved.
     * @return A set containing the named conjuncts of the specified expression.  If
     *         the expression is not a named class or an intersection then the set will
     *         definitely be empty.
     */
    public Set<OWLClass> getNamedConjuncts(OWLClassExpression classExpression) {
        conjuncts.clear();
        reset();
        collect = true;
        classExpression.accept(visitor);
        return conjuncts;
    }


    private class NamedConjunctCheckerVisitor extends OWLClassExpressionVisitorAdapter {

        public NamedConjunctCheckerVisitor() {
			// TODO Auto-generated constructor stub
		}


		@Override
		public void visit(OWLClass desc) {
            if (conjunct == null) {
                found = true;
                if (collect) {
                    conjuncts.add(desc);
                }
            }
            else if (desc.equals(conjunct)) {
                found = true;
                if (collect) {
                    conjuncts.add(desc);
                }
            }
        }


        @Override
		public void visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
                // Early termination if we have found a named conjunct
                // and we don't need to collect
                if (found && !collect) {
                    break;
                }
            }
        }
    }
}
