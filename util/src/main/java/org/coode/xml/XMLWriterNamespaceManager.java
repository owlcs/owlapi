/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coode.xml;

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
