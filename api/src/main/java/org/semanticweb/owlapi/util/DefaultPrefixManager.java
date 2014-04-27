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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
public class DefaultPrefixManager implements PrefixManager, ShortFormProvider,
        IRIShortFormProvider {

    private static final long serialVersionUID = 40000L;
    @Nonnull
    private Map<String, String> prefix2NamespaceMap;
    @Nonnull
    private final Map<String, String> reverseprefix2NamespaceMap = new HashMap<String, String>();
    @Nonnull
    private Comparator<String> comparator;

    /**
     * @param pm
     *        the prefix manager to copy
     * @param c
     *        comparator to sort prefixes
     * @param defaultPrefix
     *        default prefix
     */
    public DefaultPrefixManager(@Nullable PrefixManager pm,
            @Nullable Comparator<String> c, @Nullable String defaultPrefix) {
        comparator = c == null ? new StringLengthComparator() : c;
        prefix2NamespaceMap = new TreeMap<String, String>(comparator);
        setupDefaultPrefixes();
        if (pm != null) {
            copyPrefixesFrom(pm);
        }
        if (defaultPrefix != null) {
            setDefaultPrefix(defaultPrefix);
        }
    }

    /**
     * default constructor setting the comparator to string lenght comparator
     */
    public DefaultPrefixManager() {
        this(null, null, null);
    }

    @Override
    public Comparator<String> getPrefixComparator() {
        return comparator;
    }

    @Override
    public void setPrefixComparator(Comparator<String> comparator) {
        checkNotNull(comparator, "comparator cannot be null");
        this.comparator = comparator;
        Map<String, String> p = prefix2NamespaceMap;
        prefix2NamespaceMap = new TreeMap<String, String>(comparator);
        prefix2NamespaceMap.putAll(p);
    }

    @Override
    public void clear() {
        prefix2NamespaceMap.clear();
        reverseprefix2NamespaceMap.clear();
    }

    @Override
    public Set<String> getPrefixNames() {
        return new HashSet<String>(prefix2NamespaceMap.keySet());
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
    public String getPrefixIRI(IRI iri) {
        String ns = iri.getNamespace();
        String prefix = reverseprefix2NamespaceMap.get(ns);
        if (prefix == null) {
            return null;
        }
        if (iri.getFragment().isEmpty()) {
            return prefix;
        }
        return prefix + iri.getFragment();
    }

    @Override
    public String getDefaultPrefix() {
        return prefix2NamespaceMap.get(":");
    }

    @Override
    public boolean containsPrefixMapping(String prefix) {
        return prefix2NamespaceMap.get(prefix) != null;
    }

    @SuppressWarnings("null")
    @Override
    public void copyPrefixesFrom(PrefixManager prefixManager) {
        for (String prefixName : prefixManager.getPrefixNames()) {
            String prefix = prefixManager.getPrefix(prefixName);
            setPrefix(prefixName, prefix);
        }
    }

    @SuppressWarnings("null")
    @Override
    public IRI getIRI(String curie) {
        if (curie.startsWith("<")) {
            return IRI.create(curie.substring(1, curie.length() - 1));
        }
        int sep = curie.indexOf(':');
        if (sep == -1) {
            if (getDefaultPrefix() != null) {
                return IRI.create(getDefaultPrefix() + curie);
            } else {
                return IRI.create(curie);
            }
        } else {
            String prefixName = curie.substring(0, sep + 1);
            if (!containsPrefixMapping(prefixName)) {
                throw new OWLRuntimeException(
                        "Prefix not registered for prefix name: " + prefixName);
            }
            String prefix = getPrefix(prefixName);
            String localName = curie.substring(sep + 1);
            return IRI.create(prefix, localName);
        }
    }

    @SuppressWarnings("null")
    @Override
    public Map<String, String> getPrefixName2PrefixMap() {
        return Collections.unmodifiableMap(prefix2NamespaceMap);
    }

    @Override
    public String getPrefix(String prefixName) {
        return prefix2NamespaceMap.get(prefixName);
    }

    @Override
    public void setPrefix(String prefixName, String prefix) {
        checkNotNull(prefixName, "prefixName cannot be null");
        checkNotNull(prefix, "prefix cannot be null");
        String _prefixName = prefixName;
        if (!_prefixName.endsWith(":")) {
            _prefixName = _prefixName + ":";
        }
        prefix2NamespaceMap.put(_prefixName, prefix);
        reverseprefix2NamespaceMap.put(prefix, _prefixName);
    }

    @Override
    public void unregisterNamespace(String namespace) {
        List<String> toRemove = new ArrayList<String>();
        for (Map.Entry<String, String> e : prefix2NamespaceMap.entrySet()) {
            if (e.getValue().equals(namespace)) {
                toRemove.add(e.getKey());
            }
        }
        reverseprefix2NamespaceMap.remove(namespace);
        for (String s : toRemove) {
            prefix2NamespaceMap.remove(s);
        }
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
