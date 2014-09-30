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
package org.semanticweb.owlapi.rdf.rdfxml.renderer;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;

/**
 * Developed as part of the CO-ODE project http://www.co-ode.org .
 * 
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public class XMLWriterNamespaceManager {

    @Nonnull
    private final Map<String, String> prefixNamespaceMap = new HashMap<>();
    @Nonnull
    private final Map<String, String> namespacePrefixMap = new HashMap<>();
    @Nonnull
    private final Map<String, String> wellknownNamespaces = new HashMap<>();
    @Nonnull
    private String defaultNamespace;

    /**
     * @param defaultNamespace
     *        default namespace
     */
    public XMLWriterNamespaceManager(@Nonnull String defaultNamespace) {
        this.defaultNamespace = checkNotNull(defaultNamespace,
                "defaultNamespace cannot be null");
    }

    /**
     * @param prefix
     *        prefix
     * @param namespace
     *        namespace
     */
    public void addWellKnownNamespace(@Nonnull String prefix,
            @Nonnull String namespace) {
        wellknownNamespaces.put(checkNotNull(prefix, "prefix cannot be null"),
                checkNotNull(namespace, "namespace cannot be null"));
    }

    /**
     * @param prefix
     *        prefix
     * @param namespace
     *        namespace
     */
    public void setPrefix(@Nonnull String prefix, @Nonnull String namespace) {
        checkNotNull(prefix, "prefix cannot be null");
        checkNotNull(namespace, "namespace cannot be null");
        prefixNamespaceMap.put(prefix, namespace);
        namespacePrefixMap.put(namespace, prefix);
    }

    /**
     * @param namespace
     *        namespace
     * @return prefix for namespace, or null
     */
    @Nullable
    public String getPrefixForNamespace(@Nonnull String namespace) {
        return namespacePrefixMap.get(checkNotNull(namespace,
                "namespace cannot be null"));
    }

    /**
     * @param namespace
     *        namespace
     */
    public void setDefaultNamespace(@Nonnull String namespace) {
        defaultNamespace = checkNotNull(namespace, "namespace cannot be null");
    }

    /**
     * @param prefix
     *        prefix
     * @return namespace for prefix or null
     */
    @Nullable
    public String getNamespaceForPrefix(@Nonnull String prefix) {
        return prefixNamespaceMap.get(checkNotNull(prefix,
                "prefix cannot be null"));
    }

    /**
     * Gets a QName for a full URI.
     * 
     * @param name
     *        The name which represents the full name.
     * @return The QName representation or the input name if a QName could not
     *         be generated.
     */
    @Nullable
    public String getQName(@Nonnull String name) {
        checkNotNull(name, "name cannot be null");
        if (name.startsWith(defaultNamespace)) {
            return name.substring(defaultNamespace.length(), name.length());
        }
        for (String ns : namespacePrefixMap.keySet()) {
            if (name.startsWith(ns)) {
                String localName = name.substring(ns.length(), name.length());
                return namespacePrefixMap.get(ns) + ':' + localName;
            }
        }
        return name;
    }

    /**
     * Gets a QName for an IRI.
     * 
     * @param name
     *        The name which represents the full name.
     * @return The QName representation or {@code null} if a QName could not be
     *         generated.
     */
    public String getQName(@Nonnull IRI name) {
        if (name.getNamespace().equals(defaultNamespace)) {
            return name.prefixedBy("");
        }
        String candidate = namespacePrefixMap.get(name.getNamespace());
        if (candidate != null) {
            return name.prefixedBy(candidate + ':');
        }
        return name.toString();
    }

    /**
     * @param namespace
     *        namespace
     */
    public void createPrefixForNamespace(@Nonnull String namespace) {
        checkNotNull(namespace, "namespace cannot be null");
        if (namespace.equals(defaultNamespace)) {
            return;
        }
        String prefix = wellknownNamespaces.get(namespace);
        if (prefix != null) {
            setPrefix(prefix, namespace);
        }
        if (!namespacePrefixMap.containsKey(namespace)) {
            int counter = 1;
            while (prefixNamespaceMap.get("p" + counter) != null) {
                counter++;
            }
            setPrefix("p" + counter, namespace);
        }
    }

    /** @return default namespace */
    @Nonnull
    public String getDefaultNamespace() {
        return defaultNamespace;
    }

    /** @return iterable on prefixes */
    @Nonnull
    public Iterable<String> getPrefixes() {
        return prefixNamespaceMap.keySet();
    }

    /** @return iterable of namespaces */
    @Nonnull
    public Iterable<String> getNamespaces() {
        return namespacePrefixMap.keySet();
    }

    /**
     * Search for a prefix other than "" for the default namespace.
     * 
     * @return the first prefix found for the default namespace that is not ""
     */
    @Nonnull
    public String getDefaultPrefix() {
        for (String prefix : prefixNamespaceMap.keySet()) {
            if (!prefix.isEmpty()) {
                // if the default has a blank entry then skip it
                String ns = prefixNamespaceMap.get(prefix);
                if (ns.equals(defaultNamespace)) {
                    return prefix;
                }
            }
        }
        return "";
    }
}
