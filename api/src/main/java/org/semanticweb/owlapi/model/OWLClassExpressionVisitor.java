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
package org.semanticweb.owlapi.model;

/**
 * An interface to objects that can visit
 * {@link org.semanticweb.owlapi.model.OWLClassExpression}s. (See the <a
 * href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 13-Nov-2006
 */
public interface OWLClassExpressionVisitor {

    /**
     * visit OWLClass type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLClass ce);

    /**
     * visit OWLObjectIntersectionOf type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectIntersectionOf ce);

    /**
     * visit OWLObjectUnionOf type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectUnionOf ce);

    /**
     * visit OWLObjectComplementOf type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectComplementOf ce);

    /**
     * visit OWLObjectSomeValuesFrom type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectSomeValuesFrom ce);

    /**
     * visit OWLObjectAllValuesFrom type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectAllValuesFrom ce);

    /**
     * visit OWLObjectHasValue type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectHasValue ce);

    /**
     * visit OWLObjectMinCardinality type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectMinCardinality ce);

    /**
     * visit OWLObjectExactCardinality type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectExactCardinality ce);

    /**
     * visit OWLObjectMaxCardinality type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectMaxCardinality ce);

    /**
     * visit OWLObjectHasSelf type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectHasSelf ce);

    /**
     * visit OWLObjectOneOf type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLObjectOneOf ce);

    /**
     * visit OWLDataSomeValuesFrom type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLDataSomeValuesFrom ce);

    /**
     * visit OWLDataAllValuesFrom type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLDataAllValuesFrom ce);

    /**
     * visit OWLDataHasValue type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLDataHasValue ce);

    /**
     * visit OWLDataMinCardinality type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLDataMinCardinality ce);

    /**
     * visit OWLDataExactCardinality type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLDataExactCardinality ce);

    /**
     * visit OWLDataMaxCardinality type
     * 
     * @param ce
     *        object to visit
     */
    void visit(OWLDataMaxCardinality ce);
}
