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
package org.semanticweb.owlapi.model;

import java.util.Set;

import javax.annotation.Nonnull;

/**
 * Represent a rule. A rule consists of a head and a body. Both the head and the
 * body consist of a conjunction of atoms.
 * 
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public interface SWRLRule extends OWLLogicalAxiom, SWRLObject {

    /**
     * Gets the atoms in the body of the rule.
     * 
     * @return A set of {@code SWRLAtom}s, which represent the atoms in the body
     *         of the rule.
     */
    @Nonnull
    Set<SWRLAtom> getBody();

    /**
     * Gets the atoms in the head of the rule.
     * 
     * @return A set of {@code SWRLAtom}s, which represent the atoms in the head
     *         of the rule
     */
    @Nonnull
    Set<SWRLAtom> getHead();

    /**
     * If this rule contains atoms that have predicates that are inverse object
     * properties, then this method creates and returns a rule where the
     * arguments of these atoms are fliped over and the predicate is the inverse
     * (simplified) property.
     * 
     * @return The rule such that any atoms of the form inverseOf(p)(x, y) are
     *         transformed to p(x, y).
     */
    @Nonnull
    SWRLRule getSimplified();

    /**
     * Gets the variables that appear in this rule.
     * 
     * @return A set of variables.
     */
    @Nonnull
    Set<SWRLVariable> getVariables();

    /**
     * Determines if this rule uses anonymous class expressions in class atoms.
     * 
     * @return {@code true} if this rule contains anonymous class expression in
     *         class atoms, otherwise {@code false}.
     */
    boolean containsAnonymousClassExpressions();

    /**
     * Gets the predicates of class atoms.
     * 
     * @return A set of class expressions that represent the class class
     *         expressions that are predicates of class atoms.
     */
    @Nonnull
    Set<OWLClassExpression> getClassAtomPredicates();

    @Override
    @Nonnull
    SWRLRule getAxiomWithoutAnnotations();
}
