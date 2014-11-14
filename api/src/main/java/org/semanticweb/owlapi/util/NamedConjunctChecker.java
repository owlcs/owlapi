/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;

/**
 * A utility class which checks if a class expression has a named conjunct or a
 * specific named conjunct.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class NamedConjunctChecker {

    @Nullable
    OWLClass conjunct;
    boolean found;
    boolean collect;
    @Nonnull
    final Set<OWLClass> conjuncts = new HashSet<>();
    @Nonnull
    private final NamedConjunctCheckerVisitor visitor = new NamedConjunctCheckerVisitor();

    /**
     * @return true ifa named class is a conjunct in a given class expression.
     *         For class expressions which aren't named classes or object
     *         intersections this method will always return false.
     * @param conj
     *        The conjunct to check for
     * @param classExpression
     *        The expression to be checked
     */
    public boolean isNamedConjunct(@Nonnull OWLClass conj,
            @Nonnull OWLClassExpression classExpression) {
        checkNotNull(conj, "conj cannot be null");
        checkNotNull(classExpression, "classExpression cannot be null");
        reset();
        conjunct = conj;
        classExpression.accept(visitor);
        return found;
    }

    /**
     * Checks whether the specified expression has a named conjunct. For For
     * class expressions which aren't named classes or object intersections this
     * method will always return false.
     * 
     * @param classExpression
     *        The expression to be checked.
     * @return {@code true} if the expression is in fact a named class (
     *         {@code OWLClass}) or if the expression is an intersection that
     *         has a named operand (included nested intersections), otherwise
     *         {@code false}
     */
    public boolean
            hasNamedConjunct(@Nonnull OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
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
     * 
     * @param classExpression
     *        The expression whose conjuncts are to be retrieved.
     * @return A set containing the named conjuncts of the specified expression.
     *         If the expression is not a named class or an intersection then
     *         the set will definitely be empty.
     */
    @Nonnull
    public Set<OWLClass> getNamedConjuncts(
            @Nonnull OWLClassExpression classExpression) {
        checkNotNull(classExpression, "classExpression cannot be null");
        conjuncts.clear();
        reset();
        collect = true;
        classExpression.accept(visitor);
        return conjuncts;
    }

    private class NamedConjunctCheckerVisitor implements
            OWLClassExpressionVisitor {

        NamedConjunctCheckerVisitor() {}

        @Override
        public void visit(OWLClass ce) {
            if (conjunct == null) {
                found = true;
                if (collect) {
                    conjuncts.add(ce);
                }
            } else if (ce.equals(conjunct)) {
                found = true;
                if (collect) {
                    conjuncts.add(ce);
                }
            }
        }

        @Override
        public void visit(OWLObjectIntersectionOf ce) {
            ce.operands().forEach(op -> {
                op.accept(this);
                if (found && !collect) {
                    return;
                }
            });
        }
    }
}
