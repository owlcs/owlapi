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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.ext.DeclHandler;

/**
 * @author ignazio
 * @since 4.0.0
 */
public final class SAXParsers {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SAXParsers.class);

    private SAXParsers() {}

    /**
     * @return a new factory, set up to be namespace aware, non validating and
     *         not loading external dtds.
     */
    public static SAXParserFactory initFactory() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        try {
            factory.setFeature(
                    "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                    false);
            factory.setFeature("http://xml.org/sax/features/validation", false);
        } catch (ParserConfigurationException | SAXException e) {
            throw new OWLRuntimeException(e);
        }
        factory.setNamespaceAware(true);
        return factory;
    }

    /**
     * @param handler
     *        declaration handler, optional. Used for entity detection for reuse
     *        in parser output.
     * @return new SaxParser, intialized with optional declaration handler and
     *         larger entity expansion limit
     */
    public static SAXParser initParserWithOWLAPIStandards(DeclHandler handler) {
        try {
            SAXParser parser = initFactory().newSAXParser();
            try {
                parser.setProperty(
                        "http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit",
                        "100000000");
            } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
                LOGGER.warn("http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit not supported by parser type "
                        + parser.getClass().getName());
                try {
                    parser.setProperty("entityExpansionLimit", "100000000");
                } catch (SAXNotRecognizedException | SAXNotSupportedException ex) {
                    LOGGER.warn("entityExpansionLimit not supported by parser type "
                            + parser.getClass().getName());
                }
            }
            if (handler != null) {
                try {
                    parser.setProperty(
                            "http://xml.org/sax/properties/declaration-handler",
                            handler);
                } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
                    LOGGER.warn("http://xml.org/sax/properties/declaration-handler not supported by parser type "
                            + parser.getClass().getName()
                            + ": entity declarations will not be roundtripped.");
                }
            }
            return parser;
        } catch (ParserConfigurationException | SAXException e) {
            throw new OWLRuntimeException(e);
        }
    }
}
