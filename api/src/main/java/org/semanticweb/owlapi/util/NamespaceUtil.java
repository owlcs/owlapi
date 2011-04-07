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

import org.semanticweb.owlapi.vocab.DublinCoreVocabulary;
import org.semanticweb.owlapi.vocab.Namespaces;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 04-Apr-2007<br><br>
 * <p/>
 * A utility class which can generate namespaces, local names and
 * namespace prefixes in accordance with the XML spec.
 */
public class NamespaceUtil {

    private Map<String, String> namespace2PrefixMap;

    private Map<String, String> standardNamespacePrefixMappings;

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


    public static boolean isLetter(char ch) {
        return Character.isLetter(ch);
    }


    public static boolean isDigit(char ch) {
        return Character.isDigit(ch);
    }


    public static boolean isNCNameChar(char ch) {
        // No colon in an NCNameChar
        return isLetter(ch) || isDigit(ch) || ch == '.' || ch == '-' || ch == '_';
    }


    public static boolean isNCNameStartChar(char ch) {
        return isLetter(ch) || ch == '_';
    }


    /**
     * Splits a string into a namespace and local name.
     * @param s      The string to be split.
     * @param result May be <code>null</code>.  If not <code>null</code> the method will
     *               fill the array with the result and return the passed in array.  This allows a
     *               String array to be reused.  If this parameter is <code>null</code> then a new
     *               String array will be created to hold the result.  <b> The size of the array
     *               must be 2 </b>
     * @return The result of the split.  The first element corresponds to the namespace
     *         and the second element corresponds to the local name.  If the string could
     *         not be split into a namespace and local name then the first element will be
     *         an empty string and the second element will an empty string
     */
    public String [] split(String s, String [] result) {
        // We need to deal with escape sequences.  %20 is a space
        // and can be contained within a qname.
        String temp = s;
        temp = temp.replaceAll("\\%[0-9a-fA-F][0-9a-fA-F]", "---");
        int startIndex = s.length() - 1;
        for (int index = s.length() - 1; index > -1; index--) {
            char curChar = temp.charAt(index);
            if (isNCNameStartChar(curChar)) {
                startIndex = index;
            }
            else if (!isNCNameChar(curChar)) {
                break;
            }
        }
        String [] split;
        if (result != null) {
            split = result;
        }
        else {
            split = new String [2];
        }
        if(!isNCNameStartChar(s.charAt(startIndex))) {
            // Could not find split!
            split[0] = "";
            split[1] = "";
        }
        else {
            split[0] = s.substring(0, startIndex);
            split[1] = s.substring(startIndex, s.length());
        }
        return split;
    }


    /**
     * Gets a prefix for the given namespace.  If a mapping
     * has not been specified then a prefix will be computed
     * and stored for the specified namespace.
     * @param namespace The namespace whose prefix is to be
     *                  retrieved.
     * @return The prefix for the specified namespace.
     */
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


    /**
     * Generates a candidate prefix for the specified namespace.
     * @param namespace The namespace that a prefix should be generated
     *                  for.  The implementation attempts to generate a prefix based on
     *                  the namespace.  If it cannot do this, a prefix of the form pn
     *                  is generated, where n is an integer.
     * @return The generated prefix.  Note that this method will not store
     *         the namespace -> prefix mapping.
     */
    public String generatePrefix(String namespace) {

        String prefix = standardNamespacePrefixMappings.get(namespace);
        if (prefix != null) {
            namespace2PrefixMap.put(namespace, prefix);
            return prefix;
        }

        int startIndex = -1;
        for (int i = namespace.length() - 1; i > -1; i--) {
            char curChar = namespace.charAt(i);
            boolean isStartChar = isNCNameStartChar(curChar);
            if (isStartChar || startIndex == -1) {
                if (isStartChar) {
                    startIndex = i;
                }
            }
            else if (!isNCNameChar(curChar)) {
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
            if (isNCNameChar(curChar) && curChar != '.') {
                endIndex = i + 1;
            }
            else {
                break;
            }
        }
        String computedPrefix = namespace.substring(startIndex, endIndex);
        String candidatePrefix = computedPrefix;
        int index = 2;
        while (namespace2PrefixMap.containsValue(candidatePrefix) ||
               standardNamespacePrefixMappings.containsValue(candidatePrefix)) {
            candidatePrefix = computedPrefix + index;
            index++;
        }
        return candidatePrefix;
    }


    /**
     * Sets the prefix for the specified namespace.  This will override any
     * computed prefix and take precedence over any computed prefix.
     * @param namespace The namespace whose prefix is to be set.
     * @param prefix    The prefix for the namespace
     */
    public void setPrefix(String namespace, String prefix) {
        namespace2PrefixMap.put(namespace, prefix);
    }
}
