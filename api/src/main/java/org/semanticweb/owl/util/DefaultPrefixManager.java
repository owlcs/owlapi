package org.semanticweb.owl.util;

import org.semanticweb.owl.model.PrefixManager;
import org.semanticweb.owl.vocab.Namespaces;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/*
 * Copyright (C) 2008, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 10-Sep-2008<br><br>
 */
public class DefaultPrefixManager implements PrefixManager {

    private String defaultNamespace;

    private Map<String, String> prefix2NamespaceMap;


    /**
     * Creates a namespace manager that does not have a default namespace.
     */
    public DefaultPrefixManager() {
        this(null);
    }

    public void clear() {
        // Clear the default namespace and map
        defaultNamespace = null;
        prefix2NamespaceMap.clear();
    }


    /**
     * Creates a namespace manager that has the specified default namespace.
     * @param defaultNamespace The namespace to be used as the default namespace.
     */
    public DefaultPrefixManager(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
        prefix2NamespaceMap = new HashMap<String, String>();
        prefix2NamespaceMap.put("", defaultNamespace);
        registerNamespace("owl", Namespaces.OWL.toString());
        registerNamespace("rdfs", Namespaces.RDFS.toString());
        registerNamespace("rdf", Namespaces.RDF.toString());
        registerNamespace("xsd", Namespaces.XSD.toString());
        registerNamespace("skos", Namespaces.SKOS.toString());
    }


    /**
     * Sets the default namespace.  This will also bind the empty string
     * prefix to this namespace.
     * @param defaultNamespace The namespace to be used as the default namespace.  Note that
     * the value may be <code>null</code> in order to clear the default namespace.
     */
    public void setDefaultNamespace(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
        prefix2NamespaceMap.put("", defaultNamespace);
    }


    public String getDefaultPrefix() {
        return defaultNamespace;
    }


    public boolean containsPrefixMapping(String prefix) {
        return prefix2NamespaceMap.containsKey(prefix) &&
                prefix2NamespaceMap.get(prefix) != null;
    }


    public URI getURI(String curie) {
        int sep = curie.indexOf(':');
        if(sep == -1) {
            if (getDefaultPrefix() != null) {
                return URI.create(getDefaultPrefix() + curie);
            }
            else {
                return URI.create(curie);
            }
        }
        else {
            return URI.create(getPrefix(curie.substring(0, sep)) + curie.substring(sep + 1));
        }
    }

    public Map<String, String> getPrefixName2PrefixMap() {
        return Collections.unmodifiableMap(prefix2NamespaceMap);
    }

    public String getPrefix(String prefix) {
        return prefix2NamespaceMap.get(prefix);
    }


    /**
     * Adds a prefix namespace mapping
     * @param prefix The prefix (must not be null)
     * @param namespace The namespace that the prefix points to
     */
    public void registerNamespace(String prefix, String namespace) {
        prefix2NamespaceMap.put(prefix, namespace);
        if(prefix.trim().length() == 0) {
            defaultNamespace = namespace;
        }
    }


    /**
     * Removes a previously registerd prefix namespace mapping
     * @param namespace The namespace to be removed.
     */
    public void unregisterNamespace(String namespace) {
        for(Iterator<String> it = prefix2NamespaceMap.values().iterator(); it.hasNext(); ) {
            if(it.next().equals(namespace)) {
                it.remove();
                return;
            }
        }
    }
}
