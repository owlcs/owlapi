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
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A prefix manager than can provide prefixes for prefix names.
 * 
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
public interface PrefixManager extends Serializable {

    /**
     * @return the prefix comparator currently used by the prefix manager
     */
    @Nonnull
    Comparator<String> getPrefixComparator();

    /**
     * @param comparator
     *        the comparator to use
     */
    void setPrefixComparator(@Nonnull Comparator<String> comparator);

    /**
     * Gets the default prefix. The default prefix is denoted by the prefix name
     * ":"
     * 
     * @return The default prefix, or {@code null} if there is no default
     *         prefix.
     */
    @Nullable
    String getDefaultPrefix();

    /**
     * Determines if this manager knows about a given prefix name and it
     * contains a (non-null) mapping for the prefix.
     * 
     * @param prefixName
     *        The prefix name to be tested for.
     * @return {@code true} if the manager knows about this prefix and there is
     *         a non-null mapping for this prefix.
     */
    boolean containsPrefixMapping(@Nonnull String prefixName);

    /**
     * Gets the prefix that is bound to a particular prefix name. Note that
     * specifying ":" corresponds to requesting the default prefix and will
     * return the same result as a call to the {@code getDefaultPrefix()}
     * method.
     * 
     * @param prefixName
     *        The prefix name. A string that represents a prefix name of the
     *        prefix to be retrieved. Note that specifying ":" is the same as
     *        asking for the default prefix (see the getDefaultPrefix() method).
     * @return The prefix, or {@code null} if there is no prefix name bound to
     *         this prefix, or the prefix name doesn't exist.
     */
    @Nullable
    String getPrefix(@Nonnull String prefixName);

    /**
     * Gets a map that maps prefix names to prefixes.
     * 
     * @return The map of prefix names to prefixes. Note that modifying the
     *         contents of this map will not change the prefix name - prefix
     *         mappings
     */
    @Nonnull
    Map<String, String> getPrefixName2PrefixMap();

    /**
     * Gets the URI for a given prefix IRI. The prefix IRI must have a prefix
     * name that is registered with this manager, or a runtime exception will be
     * thrown.
     * 
     * @param prefixIRI
     *        The Prefix IRI
     * @return The full IRI.
     * @throws OWLRuntimeException
     *         if the prefix name of the prefix IRI doesn't have a corresponding
     *         prefix managed by this manager.
     */
    @Nonnull
    IRI getIRI(@Nonnull String prefixIRI);

    /**
     * Gets the prefix IRI given a IRI (URI).
     * 
     * @param iri
     *        The IRI whose prefix it to be retrieved
     * @return The prefix IRI for this IRI, or {@code null} if a prefix IRI
     *         cannot be generated.
     */
    @Nullable
    String getPrefixIRI(@Nonnull IRI iri);

    /**
     * Gets the prefix names that have a mapping in this prefix manager.
     * 
     * @return The prefix names as a set of strings.
     */
    @Nonnull
    Set<String> getPrefixNames();

    /**
     * Sets the default namespace. This will also bind the prefix name ":" to
     * this prefix
     * 
     * @param defaultPrefix
     *        The namespace to be used as the default namespace. Note that the
     *        value may be {@code null} in order to clear the default namespace.
     */
    void setDefaultPrefix(@Nonnull String defaultPrefix);

    /**
     * Adds a prefix name to prefix mapping
     * 
     * @param prefixName
     *        name The prefix name (must end with a colon)
     * @param prefix
     *        The prefix.
     */
    void setPrefix(@Nonnull String prefixName, @Nonnull String prefix);

    /**
     * Copies the prefix from another prefix manager into this one
     * 
     * @param from
     *        The manager that the prefixes should be copied from
     */
    void copyPrefixesFrom(@Nonnull PrefixManager from);

    /**
     * Removes a previously registerd prefix namespace mapping
     * 
     * @param namespace
     *        The namespace to be removed.
     */
    void unregisterNamespace(@Nonnull String namespace);

    /** clear the map */
    void clear();
}
