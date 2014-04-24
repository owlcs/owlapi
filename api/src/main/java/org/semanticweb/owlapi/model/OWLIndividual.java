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

import javax.annotation.Nonnull;

/**
 * Represents a named or anonymous individual.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public interface OWLIndividual extends OWLObject, OWLPropertyAssertionObject {

    /**
     * Determines if this individual is an instance of
     * {@link org.semanticweb.owlapi.model.OWLNamedIndividual}. Note that this
     * method is the dual of {@link #isAnonymous()}.
     * 
     * @return {@code true} if this individual is an instance of
     *         {@link org.semanticweb.owlapi.model.OWLNamedIndividual} because
     *         it is a named individuals, otherwise {@code false}
     */
    boolean isNamed();

    /**
     * Determines if this object is an instance of
     * {@link org.semanticweb.owlapi.model.OWLAnonymousIndividual} Note that
     * this method is the dual of {@link #isNamed()}.
     * 
     * @return {@code true} if this object represents an anonymous individual (
     *         {@code OWLAnonymousIndividual)} or {@code false} if this object
     *         represents a named individual ( {@code OWLIndividual})
     */
    boolean isAnonymous();

    /**
     * Obtains this individual as a named individual if it is indeed named.
     * 
     * @return The individual as a named individual
     * @throws OWLRuntimeException
     *         if this individual is anonymous
     */
    @Nonnull
    OWLNamedIndividual asOWLNamedIndividual();

    /**
     * Obtains this individual an anonymous individual if it is indeed anonymous
     * 
     * @return The individual as an anonymous individual
     * @throws OWLRuntimeException
     *         if this individual is named
     */
    @Nonnull
    OWLAnonymousIndividual asOWLAnonymousIndividual();

    /**
     * Returns a string representation that can be used as the ID of this
     * individual. This is the toString representation of the node ID of this
     * individual
     * 
     * @return A string representing the toString of the node ID of this entity.
     */
    @Nonnull
    String toStringID();

    /**
     * @param visitor
     *        visitor
     */
    void accept(@Nonnull OWLIndividualVisitor visitor);

    /**
     * @param visitor
     *        visitor
     * @param <O>
     *        visitor return type
     * @return visitor ex type
     */
    @Nonnull
    <O> O accept(@Nonnull OWLIndividualVisitorEx<O> visitor);
}
