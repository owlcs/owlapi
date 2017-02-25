/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2011, Clark & Parsia, LLC
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package com.clarkparsia.owlapi.explanation;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Satisfiability converter.
 */
public class SatisfiabilityConverter {

    protected static final Logger LOGGER = LoggerFactory.getLogger(SatisfiabilityConverter.class);
    protected final OWLDataFactory factory;
    private final AxiomConverter converter;

    /**
     * Instantiates a new satisfiability converter.
     *
     * @param factory the factory to use
     */
    public SatisfiabilityConverter(OWLDataFactory factory) {
        this.factory = checkNotNull(factory, "factory cannot be null");
        converter = new AxiomConverter(factory);
    }

    /**
     * Convert.
     *
     * @param axiom axiom to convert
     * @return converted class expression
     */
    public OWLClassExpression convert(OWLAxiom axiom) {
        return checkNotNull(axiom, "axiom cannot be null").accept(converter);
    }
}
