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

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitorEx;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 * @param <O>
 *        return type
 */
public class OWLClassExpressionVisitorExAdapter<O> implements
        OWLClassExpressionVisitorEx<O> {

    private O object;

    /** constructor for null default value. */
    public OWLClassExpressionVisitorExAdapter() {
        this(null);
    }

    /**
     * adapter with object as default value
     * 
     * @param object
     *        default return value
     */
    public OWLClassExpressionVisitorExAdapter(O object) {
        this.object = object;
    }

    /**
     * override to change default behaviour.
     * 
     * @param c
     *        visited axiom
     * @return default return value;
     */
    protected O handleDefault(@SuppressWarnings("unused") OWLClassExpression c) {
        return object;
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLClass ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectIntersectionOf ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectUnionOf ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectComplementOf ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectSomeValuesFrom ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectAllValuesFrom ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLObjectHasValue ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectMinCardinality ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectExactCardinality ce) {
        return handleDefault(ce);
    }

    @Nullable
    @Override
    public O visit(@Nonnull OWLObjectMaxCardinality ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLObjectHasSelf ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLObjectOneOf ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLDataSomeValuesFrom ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLDataAllValuesFrom ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLDataHasValue ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLDataMinCardinality ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLDataExactCardinality ce) {
        return handleDefault(ce);
    }

    @Override
    public O visit(@Nonnull OWLDataMaxCardinality ce) {
        return handleDefault(ce);
    }
}
