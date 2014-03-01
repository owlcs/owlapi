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
package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AddImport;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitorEx;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.model.RemoveImport;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;
import org.semanticweb.owlapi.model.SetOntologyID;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 12-Dec-2006
 * @param <O>
 *        returned type
 */
public class OWLOntologyChangeVisitorAdapterEx<O> implements
        OWLOntologyChangeVisitorEx<O> {

    private O defaultValue;

    /**
     * @param c
     *        default value
     */
    public OWLOntologyChangeVisitorAdapterEx(O c) {
        this.defaultValue = c;
    }

    /** default constructor */
    public OWLOntologyChangeVisitorAdapterEx() {
        this(null);
    }

    protected O handleDefault(@SuppressWarnings("unused") OWLOntologyChange c) {
        return defaultValue;
    }

    @Override
    public O visit(RemoveAxiom change) {
        return handleDefault(change);
    }

    @Override
    public O visit(SetOntologyID change) {
        return handleDefault(change);
    }

    @Override
    public O visit(AddAxiom change) {
        return handleDefault(change);
    }

    @Override
    public O visit(AddImport change) {
        return handleDefault(change);
    }

    @Override
    public O visit(RemoveImport change) {
        return handleDefault(change);
    }

    @Override
    public O visit(AddOntologyAnnotation change) {
        return handleDefault(change);
    }

    @Override
    public O visit(RemoveOntologyAnnotation change) {
        return handleDefault(change);
    }
}
