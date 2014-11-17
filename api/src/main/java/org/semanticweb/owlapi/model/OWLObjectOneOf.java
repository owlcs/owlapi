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

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

/**
 * Represents an <a
 * href="http://www.w3.org/TR/owl2-syntax/#Enumeration_of_Individuals"
 * >ObjectOneOf</a> class expression in the OWL 2 Specification.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLObjectOneOf extends OWLAnonymousClassExpression {

    /**
     * Gets the individuals that are in the oneOf. These individuals represent
     * the exact instances (extension) of this class expression.
     * 
     * @return The individiauls that are the values of this {@code ObjectOneOf}
     *         class expression.
     */
    @Deprecated
    @Nonnull
    default Set<? extends OWLIndividual> getIndividuals() {
        return asSet(individuals());
    }

    /**
     * Gets the individuals that are in the oneOf. These individuals represent
     * the exact instances (extension) of this class expression.
     * 
     * @return The individiauls that are the values of this {@code ObjectOneOf}
     *         class expression.
     */
    @Nonnull
    Stream<? extends OWLIndividual> individuals();

    /**
     * Simplifies this enumeration to a union of singleton nominals.
     * 
     * @return This enumeration in a more standard DL form. simp({a}) = {a}
     *         simp({a0, ... , {an}) = unionOf({a0}, ... , {an})
     */
    @Nonnull
    OWLClassExpression asObjectUnionOf();

    @Override
    default void accept(@Nonnull OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(@Nonnull OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    default void accept(@Nonnull OWLClassExpressionVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    default <O> O accept(@Nonnull OWLClassExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }
}
