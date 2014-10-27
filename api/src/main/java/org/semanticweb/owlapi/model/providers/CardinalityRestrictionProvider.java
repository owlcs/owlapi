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
package org.semanticweb.owlapi.model.providers;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/** Cardinality restriction provider interface. */
public interface CardinalityRestrictionProvider {

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        The property that the restriction acts along.
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLDataExactCardinality getOWLDataExactCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLDataPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        The property that the restriction acts along.
     * @param dataRange
     *        data range for restricition
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLDataExactCardinality getOWLDataExactCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange dataRange);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        The property that the restriction acts along.
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLDataMaxCardinality getOWLDataMaxCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLDataPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        The property that the restriction acts along.
     * @param dataRange
     *        data range for restriction
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLDataMaxCardinality getOWLDataMaxCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange dataRange);

    /**
     * @param cardinality
     *        Cannot be negative
     * @param property
     *        The property that the restriction acts along.
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLDataMinCardinality getOWLDataMinCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLDataPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        The property that the restriction acts along.
     * @param dataRange
     *        data range for restriction
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLDataMinCardinality getOWLDataMinCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLDataPropertyExpression property,
            @Nonnull OWLDataRange dataRange);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        The property that the restriction acts along.
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLObjectExactCardinality getOWLObjectExactCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        The property that the restriction acts along.
     * @param classExpression
     *        class expression for restriction
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLObjectExactCardinality getOWLObjectExactCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        The property that the restriction acts along.
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLObjectMinCardinality getOWLObjectMinCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        The property that the restriction acts along.
     * @param classExpression
     *        class expression for restriction
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLObjectMinCardinality getOWLObjectMinCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        The property that the restriction acts along.
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLObjectMaxCardinality getOWLObjectMaxCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLObjectPropertyExpression property);

    /**
     * @param cardinality
     *        Cannot be negative.
     * @param property
     *        The property that the restriction acts along.
     * @param classExpression
     *        class expression for restriction
     * @return an ExactCardinality on the specified property
     */
    @Nonnull
    OWLObjectMaxCardinality getOWLObjectMaxCardinality(
            @Nonnegative int cardinality,
            @Nonnull OWLObjectPropertyExpression property,
            @Nonnull OWLClassExpression classExpression);
}
