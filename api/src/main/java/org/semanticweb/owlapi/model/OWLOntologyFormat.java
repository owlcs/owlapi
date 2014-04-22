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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyLoaderMetaData;

/**
 * Represents the concrete representation format of an ontology. The equality of
 * an ontology format is defined by the equals and hashCode method (not its
 * identity).
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public abstract class OWLOntologyFormat implements Serializable {

    private static final long serialVersionUID = 40000L;
    private Map<Object, Object> paramaterMap;
    private OWLOntologyLoaderMetaData loaderMetaData = new NullLoaderMetaData();

    private Map<Object, Object> getParameterMap() {
        if (paramaterMap == null) {
            paramaterMap = new HashMap<Object, Object>();
        }
        return paramaterMap;
    }

    /**
     * @param key
     *        key for the new entry
     * @param value
     *        value for the new entry
     */
    public void setParameter(Object key, Object value) {
        getParameterMap().put(key, value);
    }

    /**
     * @param key
     *        key for the new entry
     * @param defaultValue
     *        value for the new entry
     * @return the value
     */
    public Object getParameter(Object key, Object defaultValue) {
        Object val = getParameterMap().get(key);
        if (val != null) {
            return val;
        } else {
            return defaultValue;
        }
    }

    /**
     * Determines if this format is an instance of a format that uses prefixes
     * to shorted IRIs.
     * 
     * @return {@code true} if this format is an instance of
     *         {@link org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat}
     *         other wise {@code false}.
     */
    public boolean isPrefixOWLOntologyFormat() {
        return this instanceof PrefixOWLOntologyFormat;
    }

    /**
     * If this format is an instance of
     * {@link org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat} then this
     * method will obtain it as a
     * {@link org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat}.
     * 
     * @return This format as a more specific
     *         {@link org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat}.
     * @throws ClassCastException
     *         if this format is not an instance of
     *         {@link org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat}
     */
    @Nonnull
    public PrefixOWLOntologyFormat asPrefixOWLOntologyFormat() {
        return (PrefixOWLOntologyFormat) this;
    }

    /**
     * If this format describes an ontology that was loaded from some ontology
     * document (rather than created programmatically) there may be some meta
     * data about the loading process. Subclasses of {@code OWLOntologyFormat}
     * will provide accessors etc. to details pertaining to the meta data about
     * loading.
     * 
     * @return An object containing the meta data about loading. Not
     *         {@code null}.
     */
    public OWLOntologyLoaderMetaData getOntologyLoaderMetaData() {
        return loaderMetaData;
    }

    /**
     * Sets the meta data for the ontology loader.
     * 
     * @param loaderMetaData
     *        The metadata. Must not be {@code null}.
     * @throws NullPointerException
     *         if the {@code loaderMetaData} is {@code null}.
     */
    public void setOntologyLoaderMetaData(
            OWLOntologyLoaderMetaData loaderMetaData) {
        this.loaderMetaData = loaderMetaData;
    }

    /**
     * @return A unique key for this format.
     */
    @Nonnull
    public abstract String getKey();

    /**
     * Determines whether this format contains textual output, as opposed to
     * binary output.
     * 
     * @return True if this format represents a textual format, as opposed to a
     *         binary format. Defaults to true if not overridden.
     */
    public boolean isTextual() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && (obj == this || obj.getClass().equals(getClass()));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public final String toString() {
        return getKey();
    }

    private static class NullLoaderMetaData implements
            OWLOntologyLoaderMetaData, Serializable {

        private static final long serialVersionUID = 40000L;

        public NullLoaderMetaData() {}
    }
}
