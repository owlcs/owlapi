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
 * An interface to objects that can visit {@link org.semanticweb.owlapi.model.OWLClassExpression}s.
 * (See the <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 *
 * @param <O> visitor return type
 * @author Matthew Horridge, The University Of Manchester Bio-Health Informatics Group
 * @since 3.0.0
 */
public interface OWLClassExpressionVisitorEx<O> extends OWLObjectVisitorEx<O> {

    /**
     * @param ce OWLClass to visit
     * @return visitor value
     */
    default O visitClass(OWLClass ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectIntersectionOf to visit
     * @return visitor value
     */
    default O visitObjectIntersectionOf(OWLObjectIntersectionOf ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectUnionOf to visit
     * @return visitor value
     */
    default O visitObjectUnionOf(OWLObjectUnionOf ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectComplementOf to visit
     * @return visitor value
     */
    default O visitObjectComplementOf(OWLObjectComplementOf ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectSomeValuesFrom to visit
     * @return visitor value
     */
    default O visitObjectSomeValuesFrom(OWLObjectSomeValuesFrom ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectAllValuesFrom to visit
     * @return visitor value
     */
    default O visitObjectAllValuesFrom(OWLObjectAllValuesFrom ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectHasValue to visit
     * @return visitor value
     */
    default O visitObjectHasValue(OWLObjectHasValue ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectMinCardinality to visit
     * @return visitor value
     */
    default O visitObjectMinCardinality(OWLObjectMinCardinality ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectExactCardinality to visit
     * @return visitor value
     */
    default O visitObjectExactCardinality(OWLObjectExactCardinality ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectMaxCardinality to visit
     * @return visitor value
     */
    default O visitObjectMaxCardinality(OWLObjectMaxCardinality ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectHasSelf to visit
     * @return visitor value
     */
    default O visitObjectHasSelf(OWLObjectHasSelf ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLObjectOneOf to visit
     * @return visitor value
     */
    default O visitObjectOneOf(OWLObjectOneOf ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLDataSomeValuesFrom to visit
     * @return visitor value
     */
    default O visitDataSomeValuesFrom(OWLDataSomeValuesFrom ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLDataAllValuesFrom to visit
     * @return visitor value
     */
    default O visitDataAllValuesFrom(OWLDataAllValuesFrom ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLDataHasValue to visit
     * @return visitor value
     */
    default O visitDataHasValue(OWLDataHasValue ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLDataMinCardinality to visit
     * @return visitor value
     */
    default O visitDataMinCardinality(OWLDataMinCardinality ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLDataExactCardinality to visit
     * @return visitor value
     */
    default O visitDataExactCardinality(OWLDataExactCardinality ce) {
        return visit(ce);
    }

    /**
     * @param ce OWLDataMaxCardinality to visit
     * @return visitor value
     */
    default O visitDataMaxCardinality(OWLDataMaxCardinality ce) {
        return visit(ce);
    }
}
