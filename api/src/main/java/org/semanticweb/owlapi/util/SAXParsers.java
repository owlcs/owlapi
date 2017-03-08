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

import javax.annotation.Nullable;
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

    private static final String VALIDATION = "http://xml.org/sax/features/validation";
    private static final String LOAD_EXTERNAL_DTD =
        "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    private static final String ERROR_TEMPLATE =
        " not supported by parser type {}, error message: {}";
    private static final String DECLARATION_HANDLER =
        "http://xml.org/sax/properties/declaration-handler";
    private static final String EXPANSION_LIMIT = "entityExpansionLimit";
    private static final String ORACLE_EXPANSION_LIMIT =
        "http://www.oracle.com/xml/jaxp/properties/entityExpansionLimit";
    private static final String GENERAL_ENTITIES =
        "http://xml.org/sax/features/external-general-entities";
    private static final String PARAMETER_ENTITIES =
        "http://xml.org/sax/features/external-parameter-entities";
    private static final Logger LOGGER = LoggerFactory.getLogger(SAXParsers.class);

    private SAXParsers() {}

    /**
     * @return a new factory, set up to be namespace aware, non validating and not loading external
     * dtds.
     */
    public static SAXParserFactory initFactory() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        disableFeature(LOAD_EXTERNAL_DTD, factory);
        disableFeature(VALIDATION, factory);
        disableFeature(GENERAL_ENTITIES, factory);
        disableFeature(PARAMETER_ENTITIES, factory);
        factory.setNamespaceAware(true);
        return factory;
    }

    private static void disableFeature(String feature, SAXParserFactory factory) {
        try {
            factory.setFeature(feature, false);
        } catch (ParserConfigurationException | SAXException e) {
            LOGGER.warn(factory.getClass().getName() + " does not support " + feature, e);
        }
    }

    /**
     * @param handler declaration handler, optional. Used for entity detection for reuse in parser
     * output.
     * @param expansion entity expansion limit. See {@link org.semanticweb.owlapi.model.parameters.ConfigurationOptions}
     * for instructions on how to set the value.
     * @return new SaxParser, intialized with optional declaration handler and larger entity
     * expansion limit
     */
    public static SAXParser initParserWithOWLAPIStandards(@Nullable DeclHandler handler,
        String expansion) {
        try {
            SAXParser parser = initFactory().newSAXParser();
            if (!addOracleExpansionLimit(parser, expansion)) {
                addExpansionLimit(parser, expansion);
            }
            addHandler(handler, parser);
            return parser;
        } catch (ParserConfigurationException | SAXException e) {
            throw new OWLRuntimeException(e);
        }
    }

    protected static void addExpansionLimit(SAXParser parser, String expansion) {
        try {
            parser.setProperty(EXPANSION_LIMIT, expansion);
        } catch (SAXNotRecognizedException | SAXNotSupportedException ex) {
            LOGGER.warn(EXPANSION_LIMIT + ERROR_TEMPLATE, parser.getClass().getName(),
                ex.getMessage());
        }
    }

    protected static boolean addOracleExpansionLimit(SAXParser parser, String expansion) {
        try {
            parser.setProperty(ORACLE_EXPANSION_LIMIT, expansion);
            return true;
        } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
            LOGGER.warn(ORACLE_EXPANSION_LIMIT + ERROR_TEMPLATE, parser.getClass().getName(),
                e.getMessage());
            return false;
        }
    }

    protected static void addHandler(@Nullable DeclHandler handler, SAXParser parser) {
        if (handler != null) {
            try {
                parser.setProperty(DECLARATION_HANDLER, handler);
            } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
                LOGGER.warn(DECLARATION_HANDLER + ERROR_TEMPLATE
                        + " Entity declarations will not be roundtripped.",
                    parser.getClass().getName(), e.getMessage());
            }
        }
    }
}
