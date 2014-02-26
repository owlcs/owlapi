package org.semanticweb.owlapi.registries;

/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2011, University of Manchester
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openrdf.rio.RDFFormat;
import org.semanticweb.owlapi.io.OWLParserFactory;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;

import com.github.ansell.abstractserviceloader.AbstractServiceLoader;

/**
 * The {@code OWLParserFactoryRegistry} provides a central point for the
 * registration of parser factories that create parsers to parse OWL ontologies.
 * The registry is typically used by at least one type of ontology factory for
 * loading ontologies whose concrete representations are contained in some kind
 * of document.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 15-Nov-2006
 */
public class OWLParserFactoryRegistry extends
        AbstractServiceLoader<Set<OWLOntologyFormatFactory>, OWLParserFactory> {

    private static final OWLParserFactoryRegistry instance = new OWLParserFactoryRegistry();
    private final List<OWLParserFactory> parserFactories = new ArrayList<OWLParserFactory>();

    /** Constructor for a parser factory registry */
    public OWLParserFactoryRegistry() {
        super(OWLParserFactory.class);
    }

    /** @return the parser factory registry */
    public static OWLParserFactoryRegistry getInstance() {
        return instance;
    }

    /** clear all registered parser factories. */
    public void clearParserFactories() {
        clear();
    }

    /** @return the list of parsers - changes will not be backed by the factory */
    public List<OWLParserFactory> getParserFactories() {
        ArrayList<OWLParserFactory> all = new ArrayList<OWLParserFactory>(
                getAll());
        ArrayList<OWLParserFactory> list = new ArrayList<OWLParserFactory>(
                all.size());
        // HACK: The execution strategy used by OWLAPI, where all parsers are
        // tried
        // and the first to succeed wins, is broken by the SPI random access, so
        // need
        // to at least make sure that the RDF/XML parser comes before the OWLXML
        // parser,
        // as they are both the same format and some RDF/XML documents seem to
        // be detected
        // as syntactically valid OWLXML
        for (OWLParserFactory nextFactory : all) {
            for (OWLOntologyFormatFactory nextSupportedFormat : nextFactory
                    .getSupportedFormats()) {
                if (nextSupportedFormat.handlesMimeType(RDFFormat.RDFXML
                        .getDefaultMIMEType())) {
                    list.add(nextFactory);
                } else if (nextSupportedFormat.handlesMimeType(RDFFormat.TURTLE
                        .getDefaultMIMEType())) {
                    list.add(nextFactory);
                }
                // Manchester syntax is being parsed validly by the OBO parser,
                // so make sure it
                // comes first
                else if (nextSupportedFormat.getKey().equals(
                        "Manchester OWL Syntax")) {
                    list.add(nextFactory);
                }
            }
        }
        // Then add all of the parsers that did not match above
        for (OWLParserFactory nextFactory : all) {
            for (OWLOntologyFormatFactory nextSupportedFormat : nextFactory
                    .getSupportedFormats()) {
                if (!nextSupportedFormat.handlesMimeType(RDFFormat.RDFXML
                        .getDefaultMIMEType())
                        && !nextSupportedFormat
                                .handlesMimeType(RDFFormat.TURTLE
                                        .getDefaultMIMEType())
                        && !nextSupportedFormat.getKey().equals(
                                "Manchester OWL Syntax")) {
                    list.add(nextFactory);
                }
            }
        }
        return list;
    }

    /**
     * Get an ontology parser factory using a mime type.
     * 
     * @param mimeType
     *        The MIME Type for the desired parser factory.
     * @return An ontology parser factory matching the given mime type or null
     *         if none were found in the registry.
     */
    public OWLParserFactory getParserFactory(String mimeType) {
        ArrayList<OWLParserFactory> all = new ArrayList<OWLParserFactory>(
                getAll());
        for (OWLParserFactory nextFactory : all) {
            for (OWLOntologyFormatFactory nextSupportedFormat : nextFactory
                    .getSupportedFormats()) {
                if (nextSupportedFormat.handlesMimeType(mimeType)) {
                    return nextFactory;
                }
            }
        }
        return null;
    }

    /**
     * Get an ontology parser factory using an {@link OWLOntologyFormatFactory}.
     * 
     * @param referenceFactory
     *        The {@link OWLOntologyFormatFactory} for the desired parser
     *        factory.
     * @return An ontology parser factory matching the given format or null if
     *         none were found in the registry.
     */
    public OWLParserFactory getParserFactory(
            OWLOntologyFormatFactory referenceFactory) {
        ArrayList<OWLParserFactory> all = new ArrayList<OWLParserFactory>(
                getAll());
        for (OWLParserFactory nextFactory : all) {
            if (nextFactory.getSupportedFormats().contains(referenceFactory)) {
                return nextFactory;
            }
        }
        return null;
    }

    /**
     * @param parserFactory
     *        the parser factory to register
     */
    public void registerParserFactory(OWLParserFactory parserFactory) {
        add(parserFactory);
    }

    /**
     * @param parserFactory
     *        the parser factory to remove
     */
    public void unregisterParserFactory(OWLParserFactory parserFactory) {
        remove(parserFactory);
    }

    @Override
    public Set<OWLOntologyFormatFactory> getKey(OWLParserFactory service) {
        return service.getSupportedFormats();
    }
}
