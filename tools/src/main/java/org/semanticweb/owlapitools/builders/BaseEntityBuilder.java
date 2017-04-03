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
package org.semanticweb.owlapitools.builders;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.PrefixManager;

/**
 * Abstract builder for entities.
 *
 * @param <T> OWL type
 * @param <B> builder type
 * @author ignazio
 */
public abstract class BaseEntityBuilder<T extends OWLEntity, B> extends BaseBuilder<T, B> {

    @Nullable
    protected String string = null;
    @Nullable
    protected PrefixManager pm = null;
    @Nullable
    private IRI iri = null;

    /**
     * @param df data factory
     */
    @Inject
    public BaseEntityBuilder(OWLDataFactory df) {
        super(df);
    }

    /**
     * @param arg property iri
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public B withIRI(IRI arg) {
        iri = arg;
        return (B) this;
    }

    /**
     * @param arg prefix manager
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public B withPrefixManager(PrefixManager arg) {
        pm = arg;
        return (B) this;
    }

    /**
     * @param arg prefixed iri
     * @return builder
     */
    @SuppressWarnings("unchecked")
    public B withPrefixedIRI(String arg) {
        string = arg;
        return (B) this;
    }

    /**
     * @return iri
     */
    public IRI getIRI() {
        return verifyNotNull(iri);
    }

    /**
     * @return string
     */
    public String getString() {
        return verifyNotNull(string);
    }

    /**
     * @return prefix manager
     */
    public PrefixManager getPM() {
        return verifyNotNull(pm);
    }
}
