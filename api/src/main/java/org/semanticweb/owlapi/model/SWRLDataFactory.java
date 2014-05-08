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

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

/**
 * An interface to a factory that can create SWRL objects.
 * 
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public interface SWRLDataFactory {

    /**
     * Gets an anonymous SWRL Rule.
     * 
     * @param body
     *        The atoms that make up the body
     * @param head
     *        The atoms that make up the head
     * @return An anonymous rule with the specified body and head
     */
    @Nonnull
    SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> body,
            @Nonnull Set<? extends SWRLAtom> head);

    /**
     * Gets an anonymous SWRL Rule.
     * 
     * @param body
     *        The atoms that make up the body
     * @param head
     *        The atoms that make up the head
     * @param annotations
     *        The annotations for the rule (may be an empty set)
     * @return An anonymous rule with the specified body and head
     */
    @Nonnull
    SWRLRule getSWRLRule(@Nonnull Set<? extends SWRLAtom> body,
            @Nonnull Set<? extends SWRLAtom> head,
            @Nonnull Set<OWLAnnotation> annotations);

    /**
     * Gets a SWRL atom where the predicate is a class expression i.e. C(x)
     * where C is a class expression and x is either an individual or a variable
     * for an individual
     * 
     * @param predicate
     *        The class expression that represents the predicate of the atom
     * @param arg
     *        The argument (x)
     * @return The class atom with the specified class expression predicate and
     *         the specified argument.
     */
    @Nonnull
    SWRLClassAtom getSWRLClassAtom(@Nonnull OWLClassExpression predicate,
            @Nonnull SWRLIArgument arg);

    /**
     * Gets a SWRL atom where the predicate is a data range, i.e. D(x) where D
     * is an OWL data range and x is either a literal or variable for a literal
     * 
     * @param predicate
     *        The data range that represents the predicate of the atom
     * @param arg
     *        The argument (x)
     * @return An atom with the specified data range predicate and the specified
     *         argument
     */
    @Nonnull
    SWRLDataRangeAtom getSWRLDataRangeAtom(@Nonnull OWLDataRange predicate,
            @Nonnull SWRLDArgument arg);

    /**
     * Gets a SWRL object property atom, i.e. P(x, y) where P is an OWL object
     * property (expression) and x and y are are either individuals or variables
     * for individuals.
     * 
     * @param property
     *        The property (P) representing the atom predicate
     * @param arg0
     *        The first argument (x)
     * @param arg1
     *        The second argument (y)
     * @return A SWRLObjectPropertyAtom that has the specified predicate and the
     *         specified arguments
     */
    @Nonnull
    SWRLObjectPropertyAtom getSWRLObjectPropertyAtom(
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull SWRLIArgument arg0, @Nonnull SWRLIArgument arg1);

    /**
     * Gets a SWRL data property atom, i.e. R(x, y) where R is an OWL data
     * property (expression) and x and y are are either literals or variables
     * for literals
     * 
     * @param property
     *        The property (P) that represents the atom predicate
     * @param arg0
     *        The first argument (x)
     * @param arg1
     *        The second argument (y)
     * @return A SWRLDataPropertyAtom that has the specified predicate and the
     *         specified arguments
     */
    @Nonnull
    SWRLDataPropertyAtom getSWRLDataPropertyAtom(
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull SWRLIArgument arg0, @Nonnull SWRLDArgument arg1);

    /**
     * Creates a SWRL Built-In atom. Builtins have predicates that are
     * identified by IRIs. SWRL provides a core set of builtins, which are
     * described in the OWL API by the
     * {@link org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary}.
     * 
     * @param builtInIRI
     *        The builtin predicate IRI
     * @param args
     *        A non-empty set of SWRL Arguments.
     * @throws IllegalArgumentException
     *         if the list of arguments is empty
     * @return A SWRLBuiltInAtom whose predicate is identified by the specified
     *         builtInIRI and that has the specified arguments
     */
    @Nonnull
    SWRLBuiltInAtom getSWRLBuiltInAtom(@Nonnull IRI builtInIRI,
            @Nonnull List<SWRLDArgument> args);

    /**
     * Gets a SWRLVariable.
     * 
     * @param var
     *        The id (IRI) of the variable
     * @return A SWRLVariable that has the name specified by the IRI
     */
    @Nonnull
    SWRLVariable getSWRLVariable(@Nonnull IRI var);

    /**
     * Gets a SWRLIndividualArgument, which is used to wrap and OWLIndividual as
     * an argument for an atom.
     * 
     * @param individual
     *        The individual that is the object argument
     * @return A SWRLIndividualArgument that wraps the specified individual
     */
    @Nonnull
    SWRLIndividualArgument getSWRLIndividualArgument(
            @Nonnull OWLIndividual individual);

    /**
     * Gets a SWRLLiteralArgument, which is used to wrap an OWLLiteral to
     * provide an argument for an atom.
     * 
     * @param literal
     *        The constant that is the object argument
     * @return A SWRLLiteralArgument that wraps the specified literal
     */
    @Nonnull
    SWRLLiteralArgument getSWRLLiteralArgument(@Nonnull OWLLiteral literal);

    /**
     * @param arg0
     *        first individual
     * @param arg1
     *        second individual
     * @return a sameindividual atom
     */
    @Nonnull
    SWRLSameIndividualAtom getSWRLSameIndividualAtom(
            @Nonnull SWRLIArgument arg0, @Nonnull SWRLIArgument arg1);

    /**
     * @param arg0
     *        first individual
     * @param arg1
     *        second individual
     * @return a differentindividual atom
     */
    @Nonnull
    SWRLDifferentIndividualsAtom getSWRLDifferentIndividualsAtom(
            @Nonnull SWRLIArgument arg0, @Nonnull SWRLIArgument arg1);
}
