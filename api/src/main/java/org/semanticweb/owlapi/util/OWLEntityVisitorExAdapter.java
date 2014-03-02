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

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Provides a default implementation of {@code OWLObjectVisitorEx}. Only the
 * methods that need specific client implementation need be overridden. The
 * adapter can be set up to return a default value.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 * @param <O>
 *        visitor return type
 */
public class OWLEntityVisitorExAdapter<O> implements OWLEntityVisitorEx<O> {

    private O defaultReturnValue = null;

    /**
     * Gets the default return value for this visitor. A fixed value
     * (independent of the specified {@code OWLObject} {@code object}) can be
     * specified in the constructor {@link OWLObjectVisitorExAdapter}
     * 
     * @param object
     *        The object that was visited.
     * @return The default return value
     */
    protected O doDefault(@SuppressWarnings("unused") OWLEntity object) {
        return defaultReturnValue;
    }

    /** default constructor with null return value. */
    public OWLEntityVisitorExAdapter() {
        this(null);
    }

    /**
     * @param defaultReturnValue
     *        default return value
     */
    public OWLEntityVisitorExAdapter(O defaultReturnValue) {
        this.defaultReturnValue = defaultReturnValue;
    }

    @Override
    public O visit(OWLClass desc) {
        return doDefault(desc);
    }

    @Override
    public O visit(OWLDatatype node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataProperty property) {
        return doDefault(property);
    }

    @Override
    public O visit(OWLObjectProperty property) {
        return doDefault(property);
    }

    @Override
    public O visit(OWLNamedIndividual individual) {
        return doDefault(individual);
    }

    @Override
    public O visit(OWLAnnotationProperty property) {
        return doDefault(property);
    }
}
