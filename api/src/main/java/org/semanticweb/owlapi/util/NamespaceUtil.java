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
package org.semanticweb.owlapi.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Apr-2007<br>
 * <br>
 * <p/>
 * A utility class which can generate namespaces, local names and namespace
 * prefixes in accordance with the XML spec. */
@SuppressWarnings("javadoc")
public class NamespaceUtil {
    private final Map<String, String> namespace2PrefixMap;
    private final Map<String, String> standardNamespacePrefixMappings;
    private int candidateIndex = 1;

    public NamespaceUtil() {
        standardNamespacePrefixMappings = new HashMap<String, String>();
        standardNamespacePrefixMappings.put(DublinCoreVocabulary.NAME_SPACE, "dc");
        standardNamespacePrefixMappings.put(Namespaces.SKOS.toString(), "skos");
        namespace2PrefixMap = new HashMap<String, String>();
        namespace2PrefixMap.put(Namespaces.OWL.toString(), "owl");
        namespace2PrefixMap.put(Namespaces.RDFS.toString(), "rdfs");
        namespace2PrefixMap.put(Namespaces.RDF.toString(), "rdf");
        namespace2PrefixMap.put(Namespaces.XSD.toString(), "xsd");
    }

    /** @param ch
     *            character to check
     * @return true if ch is a letter */
    public static boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }

    /** @param ch
     *            character to check
     * @return true if ch is a digit */
    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }


    /** Gets a prefix for the given namespace. If a mapping has not been
     * specified then a prefix will be computed and stored for the specified
     * namespace.
     * 
     * @param namespace
     *            The namespace whose prefix is to be retrieved.
     * @return The prefix for the specified namespace. */
    public String getPrefix(String namespace) {
        String prefix = namespace2PrefixMap.get(namespace);
        if (prefix != null) {
            return prefix;
        }
        // We need to generate a candidate prefix
        prefix = generatePrefix(namespace);
        if (prefix == null) {
            // For some reason, we couldn't generate a decent prefix
            // Compute an auto generated prefix
            do {
                prefix = "p" + candidateIndex++;
            } while (namespace2PrefixMap.containsValue(prefix));
        }
        namespace2PrefixMap.put(namespace, prefix);
        return prefix;
    }

    public Map<String, String> getNamespace2PrefixMap() {
        return Collections.unmodifiableMap(namespace2PrefixMap);
    }

    /** Generates a candidate prefix for the specified namespace.
     * 
     * @param namespace
     *            The namespace that a prefix should be generated for. The
     *            implementation attempts to generate a prefix based on the
     *            namespace. If it cannot do this, a prefix of the form pn is
     *            generated, where n is an integer.
     * @return The generated prefix. Note that this method will not store the
     *         namespace -> prefix mapping. */
    public String generatePrefix(String namespace) {
        String prefix = standardNamespacePrefixMappings.get(namespace);
        if (prefix != null) {
            namespace2PrefixMap.put(namespace, prefix);
            return prefix;
        }
        int startIndex = -1;
        for (int i = namespace.length() - 1; i > -1; i--) {
            char curChar = namespace.charAt(i);
            boolean isStartChar = XMLUtils.isNCNameStartChar(curChar);
            if (isStartChar || startIndex == -1) {
                if (isStartChar) {
                    startIndex = i;
                }
            } else if (!XMLUtils.isNCNameChar(curChar)) {
                break;
            }
        }
        if (startIndex == -1) {
            return null;
        }
        int endIndex = startIndex + 1;
        for (int i = startIndex; endIndex < namespace.length() && i < namespace.length(); i++) {
            char curChar = namespace.charAt(endIndex);
            // We include any NCNameChar except a full stop (.) so
            // that if the URI looks like a file with an extension the
            // extension is removed.
            if (XMLUtils.isNCNameChar(curChar) && curChar != '.') {
                endIndex = i + 1;
            } else {
                break;
            }
        }
        String computedPrefix = namespace.substring(startIndex, endIndex);
        String candidatePrefix = computedPrefix;
        int index = 2;
        while (namespace2PrefixMap.containsValue(candidatePrefix)
                || standardNamespacePrefixMappings.containsValue(candidatePrefix)) {
            candidatePrefix = computedPrefix + index;
            index++;
        }
        return candidatePrefix;
    }

    /** Sets the prefix for the specified namespace. This will override any
     * computed prefix and take precedence over any computed prefix.
     * 
     * @param namespace
     *            The namespace whose prefix is to be set.
     * @param prefix
     *            The prefix for the namespace */
    public void setPrefix(String namespace, String prefix) {
        namespace2PrefixMap.put(namespace, prefix);
    }
}
