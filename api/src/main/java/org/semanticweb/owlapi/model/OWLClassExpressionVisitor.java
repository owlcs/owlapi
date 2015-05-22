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

/**
 * An interface to objects that can visit
 * {@link org.semanticweb.owlapi.model.OWLClassExpression}s. (See the
 * <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 * 
 * @author Matthew Horridge, The University Of Manchester Bio-Health Informatics
 *         Group
 * @since 2.0.0
 */
public interface OWLClassExpressionVisitor extends OWLClassVisitorBase {

    /**
     * visit OWLObjectIntersectionOf type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectIntersectionOf ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectUnionOf type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectUnionOf ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectComplementOf type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectComplementOf ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectSomeValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectSomeValuesFrom ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectAllValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectAllValuesFrom ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectHasValue type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectHasValue ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectMinCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectMinCardinality ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectExactCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectExactCardinality ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectMaxCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectMaxCardinality ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectHasSelf type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectHasSelf ce) {
        doDefault(ce);
    }

    /**
     * visit OWLObjectOneOf type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLObjectOneOf ce) {
        doDefault(ce);
    }

    /**
     * visit OWLDataSomeValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLDataSomeValuesFrom ce) {
        doDefault(ce);
    }

    /**
     * visit OWLDataAllValuesFrom type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLDataAllValuesFrom ce) {
        doDefault(ce);
    }

    /**
     * visit OWLDataHasValue type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLDataHasValue ce) {
        doDefault(ce);
    }

    /**
     * visit OWLDataMinCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLDataMinCardinality ce) {
        doDefault(ce);
    }

    /**
     * visit OWLDataExactCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLDataExactCardinality ce) {
        doDefault(ce);
    }

    /**
     * visit OWLDataMaxCardinality type
     * 
     * @param ce
     *        ce to visit
     */
    default void visit(OWLDataMaxCardinality ce) {
        doDefault(ce);
    }
}
