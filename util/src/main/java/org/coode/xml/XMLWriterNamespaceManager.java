package org.coode.xml;

/**
 * Copyright (C) 2006, Matthew Horridge, University of Manchester
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 30-May-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * Developed as part of the CO-ODE project
 * http://www.co-ode.org
 */
public class XMLWriterNamespaceManager {

    private Map<String, String> prefixNamespaceMap;

    private Map<String, String> namespacePrefixMap;

    private Map<String, String> wellknownNamespaces;

    private String defaultNamespace;


    public XMLWriterNamespaceManager(String defaultNamespace) {
        prefixNamespaceMap = new HashMap<String, String>();
        namespacePrefixMap = new HashMap<String, String>();
        wellknownNamespaces = new HashMap<String, String>();
        this.defaultNamespace = defaultNamespace;
    }

    public void addWellKnownNamespace(String prefix, String namespace) {
        wellknownNamespaces.put(prefix, namespace);
    }

    public void setPrefix(String prefix, String namespace) {
        prefixNamespaceMap.put(prefix, namespace);
        namespacePrefixMap.put(namespace, prefix);
    }


    public String getPrefixForNamespace(String namespace) {
        return namespacePrefixMap.get(namespace);
    }

    public void setDefaultNamespace(String namespace) {
        defaultNamespace = namespace;
    }


    public String getNamespaceForPrefix(String prefix) {
        return prefixNamespaceMap.get(prefix);
    }

    public String getQName(String name) {
        if (name.startsWith(defaultNamespace)) {
            return name.substring(defaultNamespace.length(), name.length());
        }
        for (String ns : namespacePrefixMap.keySet()) {
            if (name.startsWith(ns)) {
                String localName = name.substring(ns.length(), name.length());
                return namespacePrefixMap.get(ns) + ":" + localName;
            }
        }
        return name;
    }


    public void createPrefixForNamespace(String namespace) {
        if (namespace.equals(defaultNamespace)) {
            return;
        }
        if (wellknownNamespaces.containsKey(namespace)) {
            setPrefix(wellknownNamespaces.get(namespace), namespace);
        }
        if (!namespacePrefixMap.containsKey(namespace)) {
            int counter = 1;
            while (prefixNamespaceMap.get("p" + counter) != null) {
                counter++;
            }
            setPrefix("p" + counter, namespace);
        }
    }


    public String getDefaultNamespace() {
        return defaultNamespace;
    }


    public Set<String> getPrefixes() {
        return new HashSet<String>(prefixNamespaceMap.keySet());
    }


    public Set<String> getNamespaces() {
        return new HashSet<String>(namespacePrefixMap.keySet());
    }


    public Map<String, String> getPrefixNamespaceMap() {
        return new HashMap<String, String>(prefixNamespaceMap);
    }


    /**
     * Search for a prefix other than "" for the default namespace
     * @return the first prefix found for the default namespace that is not ""
     */
    public String getDefaultPrefix() {
        for (String prefix : prefixNamespaceMap.keySet()) {
            if (!prefix.equals("")) { // if the default has a blank entry then skip it
                final String ns = prefixNamespaceMap.get(prefix);
                if (ns.equals(defaultNamespace)) {
                    return prefix;
                }
            }
        }
        return "";
    }
}
