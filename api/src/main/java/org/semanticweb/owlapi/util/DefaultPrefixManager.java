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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.util.*;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * @author Matthew Horridge, The University Of Manchester, Information
 *         Management Group
 * @since 2.2.0
 */
public class DefaultPrefixManager implements PrefixManager, ShortFormProvider, IRIShortFormProvider {

    private @Nonnull Map<String, String> prefix2NamespaceMap;
    private final @Nonnull Map<String, String> reverseprefix2NamespaceMap = new HashMap<>();
    private @Nonnull StringComparator comparator;

    /**
     * @param defaultPrefix
     *        default prefix
     */
    public DefaultPrefixManager(@Nullable String defaultPrefix) {
        this(null, null, defaultPrefix);
    }

    /**
     * @param pm
     *        the prefix manager to copy
     * @param c
     *        comparator to sort prefixes
     * @param defaultPrefix
     *        default prefix
     */
    public DefaultPrefixManager(@Nullable PrefixManager pm, @Nullable StringComparator c,
        @Nullable String defaultPrefix) {
        comparator = c == null ? new StringLengthComparator() : c;
        prefix2NamespaceMap = new TreeMap<>(comparator);
        setupDefaultPrefixes();
        if (pm != null) {
            copyPrefixesFrom(pm);
        }
        if (defaultPrefix != null) {
            setDefaultPrefix(defaultPrefix);
        }
    }

    /**
     * Default constructor setting the comparator to string lenght comparator.
     */
    public DefaultPrefixManager() {
        this(null, null, null);
    }

    @Override
    public StringComparator getPrefixComparator() {
        return comparator;
    }

    @Override
    public void setPrefixComparator(StringComparator comparator) {
        checkNotNull(comparator, "comparator cannot be null");
        this.comparator = comparator;
        Map<String, String> p = prefix2NamespaceMap;
        prefix2NamespaceMap = new TreeMap<>(comparator);
        prefix2NamespaceMap.putAll(p);
    }

    @Override
    public void clear() {
        prefix2NamespaceMap.clear();
        reverseprefix2NamespaceMap.clear();
    }

    @Override
    public Stream<String> prefixNames() {
        return prefix2NamespaceMap.keySet().stream();
    }

    private void setupDefaultPrefixes() {
        setPrefix("owl:", Namespaces.OWL.toString());
        setPrefix("rdfs:", Namespaces.RDFS.toString());
        setPrefix("rdf:", Namespaces.RDF.toString());
        setPrefix("xsd:", Namespaces.XSD.toString());
        setPrefix("xml:", Namespaces.XML.toString());
    }

    @Override
    public void setDefaultPrefix(String defaultPrefix) {
        checkNotNull(defaultPrefix, "defaultPrefix cannot be null");
        setPrefix(":", defaultPrefix);
    }

    @Override
    public @Nullable String getPrefixIRI(IRI iri) {
        String prefix = reverseprefix2NamespaceMap.get(iri.getNamespace());
        if (prefix == null) {
            return null;
        }
        return iri.prefixedBy(prefix);
    }

    @Override
    public @Nullable String getDefaultPrefix() {
        return prefix2NamespaceMap.get(":");
    }

    @Override
    public boolean containsPrefixMapping(String prefixName) {
        return prefix2NamespaceMap.get(prefixName) != null;
    }

    @Override
    public void copyPrefixesFrom(PrefixManager from) {
        copyPrefixesFrom(from.getPrefixName2PrefixMap());
    }

    @Override
    public void copyPrefixesFrom(Map<String, String> from) {
        from.forEach((k, v) -> setPrefix(k, v));
    }

    @Override
    public IRI getIRI(String prefixIRI) {
        if (prefixIRI.startsWith("<")) {
            return IRI.create(prefixIRI.substring(1, prefixIRI.length() - 1));
        }
        int sep = prefixIRI.indexOf(':');
        if (sep == -1) {
            if (getDefaultPrefix() == null) {
                return IRI.create(prefixIRI);
            }
            return IRI.create(getDefaultPrefix() + prefixIRI);
        } else {
            String prefixName = prefixIRI.substring(0, sep + 1);
            if (!containsPrefixMapping(prefixName)) {
                throw new OWLRuntimeException("Prefix not registered for prefix name: " + prefixName);
            }
            String prefix = getPrefix(prefixName);
            String localName = prefixIRI.substring(sep + 1);
            return IRI.create(prefix, localName);
        }
    }

    @Override
    public Map<String, String> getPrefixName2PrefixMap() {
        return Collections.unmodifiableMap(prefix2NamespaceMap);
    }

    @Override
    public @Nullable String getPrefix(String prefixName) {
        return prefix2NamespaceMap.get(prefixName);
    }

    @Override
    public void setPrefix(String inputPrefixName, String prefix) {
        checkNotNull(inputPrefixName, "prefixName cannot be null");
        checkNotNull(prefix, "prefix cannot be null");
        String _prefixName = inputPrefixName;
        if (!_prefixName.endsWith(":")) {
            _prefixName += ":";
        }
        prefix2NamespaceMap.put(_prefixName, prefix);
        reverseprefix2NamespaceMap.put(prefix, _prefixName);
    }

    @Override
    public void unregisterNamespace(String namespace) {
        List<String> toRemove = new ArrayList<>();
        prefix2NamespaceMap.forEach((k, v) -> {
            if (v.equals(namespace)) {
                toRemove.add(k);
            }
        });
        reverseprefix2NamespaceMap.remove(namespace);
        prefix2NamespaceMap.keySet().removeAll(toRemove);
    }

    @Override
    public String getShortForm(IRI iri) {
        String sf = getPrefixIRI(iri);
        if (sf == null) {
            return iri.toQuotedString();
        } else {
            return sf;
        }
    }

    @Override
    public String getShortForm(OWLEntity entity) {
        return getShortForm(entity.getIRI());
    }

    @Override
    public void dispose() {}
}
