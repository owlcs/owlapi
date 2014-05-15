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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;

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
public class OWLDataVisitorExAdapter<O> extends
        OWLBaseVisitorExAdapter<O, OWLObject> implements OWLDataVisitorEx<O> {

    /**
     * @param defaultReturnValue
     *        default return value
     */
    public OWLDataVisitorExAdapter(@Nonnull O defaultReturnValue) {
        super(defaultReturnValue);
    }

    @Override
    public O visit(OWLDataComplementOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataIntersectionOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataOneOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDatatype node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDatatypeRestriction node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLDataUnionOf node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLFacetRestriction node) {
        return doDefault(node);
    }

    @Override
    public O visit(OWLLiteral node) {
        return doDefault(node);
    }
}
