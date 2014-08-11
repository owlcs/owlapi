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

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
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
public interface OWLDocumentFormat extends Serializable {

    /**
     * Determines if untyped entities should automatically be typed (declared)
     * during rendering. (This is a hint to an RDF renderer - the reference
     * implementation will respect this).
     * 
     * @return {@code true} if untyped entities should automatically be typed
     *         during rendering, otherwise {@code false}.
     */
    boolean isAddMissingTypes();

    /**
     * Determines if untyped entities should automatically be typed during
     * rendering. By default this is true.
     * 
     * @param addMissingTypes
     *        {@code true} if untyped entities should automatically be typed
     *        during rendering, otherwise {@code false}.
     */
    void setAddMissingTypes(boolean addMissingTypes);

    /**
     * @param key
     *        key for the new entry
     * @param value
     *        value for the new entry
     */
    void setParameter(Serializable key, Serializable value);

    /**
     * @param key
     *        key for the new entry
     * @param defaultValue
     *        value for the new entry
     * @return the value
     */
    Serializable getParameter(Serializable key, Serializable defaultValue);

    /**
     * Determines if this format is an instance of a format that uses prefixes
     * to shorted IRIs.
     * 
     * @return {@code true} if this format is an instance of
     *         {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat} other
     *         wise {@code false}.
     */
    boolean isPrefixOWLOntologyFormat();

    /**
     * If this format is an instance of
     * {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat} then this
     * method will obtain it as a
     * {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat}.
     * 
     * @return This format as a more specific
     *         {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat}.
     * @throws ClassCastException
     *         if this format is not an instance of
     *         {@link org.semanticweb.owlapi.formats.PrefixDocumentFormat}
     */
    @Nonnull
    PrefixDocumentFormat asPrefixOWLOntologyFormat();

    /**
     * If this format describes an ontology that was loaded from some ontology
     * document (rather than created programmatically) there may be some meta
     * data about the loading process. Subclasses of {@code OWLOntologyFormat}
     * will provide accessors etc. to details pertaining to the meta data about
     * loading.
     * 
     * @return An object containing the meta data about loading. .
     */
    @Nonnull
    public OWLOntologyLoaderMetaData getOntologyLoaderMetaData();

    /**
     * Sets the meta data for the ontology loader.
     * 
     * @param loaderMetaData
     *        The metadata.
     * @throws NullPointerException
     *         if the {@code loaderMetaData} is {@code null}.
     */
    void setOntologyLoaderMetaData(
            @Nonnull OWLOntologyLoaderMetaData loaderMetaData);

    /**
     * @return A unique key for this format.
     */
    @Nonnull
    String getKey();

    /**
     * Determines whether this format contains textual output, as opposed to
     * binary output.
     * 
     * @return True if this format represents a textual format, as opposed to a
     *         binary format. Defaults to true if not overridden.
     */
    boolean isTextual();
}
