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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.vocab.Namespaces;

import com.google.common.base.Splitter;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class OntologyIRIShortFormProvider implements IRIShortFormProvider {

    /*
     *  IMPLEMENTATION NOTE.  I've avoided using Pattern (regexps) here so that this code can be compiled
     *  with the GWT compiler.
     */
    private static final String OWL_EXTENSION = ".owl";
    private static final String RDF_EXTENSION = ".rdf";
    private static final String XML_EXTENSION = ".xml";
    private static final String OBO_EXTENSION = ".obo";
    private static final String[] EXTENSIONS = { OWL_EXTENSION, RDF_EXTENSION, XML_EXTENSION, OBO_EXTENSION };
    private static final Map<IRI, String> WELL_KNOWN_SHORTFORMS = initWellKnownShortForms();

    private static Map<IRI, String> initWellKnownShortForms() {
        Map<IRI, String> map = new HashMap<>();
        for (Namespaces ns : Namespaces.values()) {
            String iriPrefix = ns.getPrefixIRI();
            String iri;
            if (iriPrefix.endsWith("#") || iriPrefix.endsWith("/")) {
                iri = iriPrefix.substring(0, iriPrefix.length() - 1);
            } else {
                iri = iriPrefix;
            }
            map.put(IRI.create(iri), ns.getPrefixName().toLowerCase());
            map.put(IRI.create(iri + '/'), ns.getPrefixName().toLowerCase());
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * @param ont
     *        ontology to use
     * @return short form of the ontology IRI
     */
    public String getShortForm(OWLOntology ont) {
        OWLOntologyID ontologyID = ont.getOntologyID();
        if (ontologyID.getOntologyIRI().isPresent()) {
            return getShortForm(verifyNotNull(ontologyID.getOntologyIRI().get()));
        } else {
            return ontologyID.toString();
        }
    }

    @Override
    public String getShortForm(IRI iri) {
        String wellKnownShortForm = getWellKnownShortForm(iri);
        if (wellKnownShortForm != null) {
            return wellKnownShortForm;
        }
        URI uri = iri.toURI();
        String path = uri.getPath();
        String shortForm = null;
        if (path != null && !path.isEmpty()) {
            String candidatePathElement = "";
            for (String tok : Splitter.on('/').split(path)) {
                if (isCandidatePathElement(tok)) {
                    candidatePathElement = stripExtensionIfPresent(tok);
                }
            }
            shortForm = candidatePathElement;
        } else if (uri.getHost() != null) {
            shortForm = iri.toString();
        } else {
            shortForm = iri.toString();
        }
        return shortForm;
    }

    private static @Nullable String getWellKnownShortForm(IRI iri) {
        String wellKnownShortForm = WELL_KNOWN_SHORTFORMS.get(iri);
        if (wellKnownShortForm != null) {
            return wellKnownShortForm;
        }
        return null;
    }

    /**
     * Removes commonly used file name extensions to make the resulting short
     * form look nicer.
     * 
     * @param shortForm
     *        The short form.
     * @return The short form with the extension removed if it was present, or
     *         the original short form if no extension was present.
     */
    private static String stripExtensionIfPresent(String shortForm) {
        String lowerCaseShortForm = shortForm.toLowerCase();
        for (String extension : EXTENSIONS) {
            if (lowerCaseShortForm.endsWith(extension)) {
                return shortForm.substring(0, shortForm.length() - extension.length());
            }
        }
        return shortForm;
    }

    /**
     * Determines if the specified path element is a candidate short form.
     * 
     * @param pathElement
     *        The path element to test. Not {@code null}.
     * @return {@code true} if the specified path element is a candidate short
     *         form, otherwise {@code false}.
     */
    private static boolean isCandidatePathElement(String pathElement) {
        return !pathElement.isEmpty() && !isVersionString(pathElement);
    }

    /**
     * Determines if the specified string is a version number string. A version
     * string is a sequence of digits and periods.
     * 
     * @param s
     *        The string to test for.
     * @return {@code true} if the string is a version string, otherwise
     *         {@code false}.
     */
    private static boolean isVersionString(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!isVersionStringChar(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the specified character is a version string character
     * (either a digit or a period).
     * 
     * @param ch
     *        The character to test for.
     * @return {@code true} of the specified char is a version string char,
     *         otherwise {@code false}.
     */
    private static boolean isVersionStringChar(char ch) {
        return isDigit(ch) || ch == '.' || ch == 'v';
    }

    /**
     * Determines if the specified char is a digit.
     * 
     * @param ch
     *        The char to test for.
     * @return {@code true} if the specified char is a digit, otherwise
     *         {@code false}.
     */
    private static boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
}
