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
package org.semanticweb.owlapi.owlxml.parser;

import static org.semanticweb.owlapi.owlxml.parser.PARSER_OWLXMLVocabulary.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.vocab.OWLXMLVocabulary.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * A handler which knows about OWLXML.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
class OWLXMLParserHandler extends DefaultHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OWLXMLParserHandler.class);
    private final OWLOntologyManager owlOntologyManager;
    private final @Nonnull OWLOntology ontology;
    private final List<OWLElementHandler<?>> handlerStack;
    private final @Nonnull Map<String, PARSER_OWLXMLVocabulary> handlerMap = new HashMap<>();
    private final @Nonnull Map<String, String> prefixName2PrefixMap = new HashMap<>();
    private Locator locator;
    private final Stack<URI> bases;
    private final @Nonnull OWLOntologyLoaderConfiguration configuration;

    /**
     * @param ontology
     *        ontology to parse into
     */
    public OWLXMLParserHandler(OWLOntology ontology) {
        this(ontology, null, ontology.getOWLOntologyManager().getOntologyLoaderConfiguration());
    }

    /**
     * @param ontology
     *        ontology to add to
     * @param configuration
     *        load configuration
     */
    public OWLXMLParserHandler(OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) {
        this(ontology, null, configuration);
    }

    @Override
    public void setDocumentLocator(@Nullable Locator locator) {
        super.setDocumentLocator(locator);
        this.locator = checkNotNull(locator);
        try {
            String systemId = this.locator.getSystemId();
            if (systemId != null) {
                bases.push(new URI(systemId));
            }
        } catch (URISyntaxException e) {
            LOGGER.warn("Invalid system id uri", e);
        }
    }

    /**
     * @param ontology
     *        ontology to parse into
     * @param topHandler
     *        top level handler
     */
    public OWLXMLParserHandler(OWLOntology ontology, OWLElementHandler<?> topHandler) {
        this(ontology, topHandler, ontology.getOWLOntologyManager().getOntologyLoaderConfiguration());
    }

    /**
     * Creates an OWLXML handler with the specified top level handler. This
     * allows OWL/XML representations of axioms to be embedded in abitrary XML
     * documents e.g. DIG 2.0 documents. (The default handler behaviour expects
     * the top level element to be an Ontology element).
     * 
     * @param ontology
     *        The ontology object that the XML representation should be parsed
     *        into.
     * @param topHandler
     *        top level handler
     * @param configuration
     *        load configuration
     */
    public OWLXMLParserHandler(OWLOntology ontology, @Nullable OWLElementHandler<?> topHandler,
            OWLOntologyLoaderConfiguration configuration) {
        owlOntologyManager = ontology.getOWLOntologyManager();
        this.ontology = ontology;
        bases = new Stack<>();
        this.configuration = configuration;
        handlerStack = new ArrayList<>();
        prefixName2PrefixMap.put("owl:", Namespaces.OWL.toString());
        prefixName2PrefixMap.put("xsd:", Namespaces.XSD.toString());
        if (topHandler != null) {
            handlerStack.add(0, topHandler);
        }
        addFactory(PARSER_ONTOLOGY);
        addFactory(PARSER_ANNOTATION);
        addFactory(PARSER_LITERAL, "Constant");
        addFactory(PARSER_IMPORT, "Imports");
        addFactory(PARSER_CLASS, "OWLClass");
        addFactory(PARSER_ANNOTATION_PROPERTY);
        addFactory(PARSER_ANNOTATION_PROPERTY_DOMAIN);
        addFactory(PARSER_ANNOTATION_PROPERTY_RANGE);
        addFactory(PARSER_SUB_ANNOTATION_PROPERTY_OF);
        addFactory(PARSER_OBJECT_PROPERTY);
        addFactory(PARSER_OBJECT_INVERSE_OF);
        addFactory(PARSER_DATA_PROPERTY);
        addFactory(PARSER_NAMED_INDIVIDUAL, "Individual");
        addFactory(PARSER_DATA_COMPLEMENT_OF);
        addFactory(PARSER_DATA_ONE_OF);
        addFactory(PARSER_DATATYPE);
        addFactory(PARSER_DATATYPE_RESTRICTION);
        addFactory(PARSER_DATA_INTERSECTION_OF);
        addFactory(PARSER_DATA_UNION_OF);
        addFactory(PARSER_FACET_RESTRICTION);
        addFactory(PARSER_OBJECT_INTERSECTION_OF);
        addFactory(PARSER_OBJECT_UNION_OF);
        addFactory(PARSER_OBJECT_COMPLEMENT_OF);
        addFactory(PARSER_OBJECT_ONE_OF);
        // Object Restrictions
        addFactory(PARSER_OBJECT_SOME_VALUES_FROM);
        addFactory(PARSER_OBJECT_ALL_VALUES_FROM);
        addFactory(PARSER_OBJECT_HAS_SELF, "ObjectExistsSelf");
        addFactory(PARSER_OBJECT_HAS_VALUE);
        addFactory(PARSER_OBJECT_MIN_CARDINALITY);
        addFactory(PARSER_OBJECT_EXACT_CARDINALITY);
        addFactory(PARSER_OBJECT_MAX_CARDINALITY);
        // Data Restrictions
        addFactory(PARSER_DATA_SOME_VALUES_FROM);
        addFactory(PARSER_DATA_ALL_VALUES_FROM);
        addFactory(PARSER_DATA_HAS_VALUE);
        addFactory(PARSER_DATA_MIN_CARDINALITY);
        addFactory(PARSER_DATA_EXACT_CARDINALITY);
        addFactory(PARSER_DATA_MAX_CARDINALITY);
        // Axioms
        addFactory(PARSER_SUB_CLASS_OF);
        addFactory(PARSER_EQUIVALENT_CLASSES);
        addFactory(PARSER_DISJOINT_CLASSES);
        addFactory(PARSER_DISJOINT_UNION);
        addFactory(PARSER_UNION_OF);
        addFactory(PARSER_SUB_OBJECT_PROPERTY_OF);
        addFactory(PARSER_OBJECT_PROPERTY_CHAIN, "SubObjectPropertyChain");
        addFactory(PARSER_OBJECT_PROPERTY_CHAIN);
        addFactory(PARSER_EQUIVALENT_OBJECT_PROPERTIES);
        addFactory(PARSER_DISJOINT_OBJECT_PROPERTIES);
        addFactory(PARSER_OBJECT_PROPERTY_DOMAIN);
        addFactory(PARSER_OBJECT_PROPERTY_RANGE);
        addFactory(PARSER_INVERSE_OBJECT_PROPERTIES);
        addFactory(PARSER_FUNCTIONAL_OBJECT_PROPERTY);
        addFactory(PARSER_INVERSE_FUNCTIONAL_OBJECT_PROPERTY);
        addFactory(PARSER_SYMMETRIC_OBJECT_PROPERTY);
        addFactory(PARSER_ASYMMETRIC_OBJECT_PROPERTY);
        addFactory(PARSER_REFLEXIVE_OBJECT_PROPERTY);
        addFactory(PARSER_IRREFLEXIVE_OBJECT_PROPERTY);
        addFactory(PARSER_TRANSITIVE_OBJECT_PROPERTY);
        addFactory(PARSER_SUB_DATA_PROPERTY_OF);
        addFactory(PARSER_EQUIVALENT_DATA_PROPERTIES);
        addFactory(PARSER_DISJOINT_DATA_PROPERTIES);
        addFactory(PARSER_DATA_PROPERTY_DOMAIN);
        addFactory(PARSER_DATA_PROPERTY_RANGE);
        addFactory(PARSER_FUNCTIONAL_DATA_PROPERTY);
        addFactory(PARSER_SAME_INDIVIDUAL, "SameIndividuals");
        addFactory(PARSER_DIFFERENT_INDIVIDUALS);
        addFactory(PARSER_CLASS_ASSERTION);
        addFactory(PARSER_OBJECT_PROPERTY_ASSERTION);
        addFactory(PARSER_NEGATIVE_OBJECT_PROPERTY_ASSERTION);
        addFactory(PARSER_NEGATIVE_DATA_PROPERTY_ASSERTION);
        addFactory(PARSER_DATA_PROPERTY_ASSERTION);
        addFactory(PARSER_ANNOTATION_ASSERTION);
        addFactory(PARSER_ENTITY_ANNOTATION);
        addFactory(PARSER_DECLARATION);
        addFactory(PARSER_IRI_ELEMENT);
        addFactory(PARSER_ABBREVIATED_IRI_ELEMENT);
        addFactory(PARSER_ANONYMOUS_INDIVIDUAL);
        addFactory(PARSER_HAS_KEY);
        addFactory(PARSER_DATATYPE_DEFINITION);
        addFactory(PARSER_DL_SAFE_RULE);
        addFactory(PARSER_BODY);
        addFactory(PARSER_HEAD);
        addFactory(PARSER_VARIABLE);
        addFactory(PARSER_CLASS_ATOM);
        addFactory(PARSER_OBJECT_PROPERTY_ATOM);
        addFactory(PARSER_DATA_PROPERTY_ATOM);
        addFactory(PARSER_DATA_RANGE_ATOM);
        addFactory(PARSER_BUILT_IN_ATOM);
        addFactory(PARSER_DIFFERENT_INDIVIDUALS_ATOM);
        addFactory(PARSER_SAME_INDIVIDUAL_ATOM);
    }

    /**
     * @return config
     */
    public OWLOntologyLoaderConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the line number that the parser is at.
     * 
     * @return A positive integer that represents the line number or -1 if the
     *         line number is not known.
     */
    public int getLineNumber() {
        if (locator != null) {
            return locator.getLineNumber();
        }
        return -1;
    }

    /**
     * @return column number
     */
    public int getColumnNumber() {
        if (locator != null) {
            return locator.getColumnNumber();
        }
        return -1;
    }

    private final Map<String, IRI> iriMap = new HashMap<>();

    /**
     * @param iriStr
     *        iri
     * @return parsed, absolute iri
     */
    public IRI getIRI(String iriStr) {
        try {
            IRI iri = iriMap.get(iriStr);
            if (iri == null) {
                URI uri = new URI(iriStr);
                if (!uri.isAbsolute()) {
                    if (bases.isEmpty()) {
                        throw new OWLXMLParserException(this, "Unable to resolve relative URI");
                    }
                    iri = IRI.create(getBase() + iriStr);
                } else {
                    iri = IRI.create(uri);
                }
                iriMap.put(iriStr, iri);
            }
            return iri;
        } catch (URISyntaxException e) {
            throw new OWLParserException(e, getLineNumber(), getColumnNumber());
        }
    }

    private static String getNormalisedAbbreviatedIRI(String input) {
        if (input.indexOf(':') != -1) {
            return input;
        }
        return ':' + input;
    }

    /**
     * @param abbreviatedIRI
     *        short iri
     * @return extended iri
     */
    public IRI getAbbreviatedIRI(String abbreviatedIRI) {
        String normalisedAbbreviatedIRI = getNormalisedAbbreviatedIRI(abbreviatedIRI);
        int sepIndex = normalisedAbbreviatedIRI.indexOf(':');
        String prefixName = normalisedAbbreviatedIRI.substring(0, sepIndex + 1);
        String localName = normalisedAbbreviatedIRI.substring(sepIndex + 1);
        String base = prefixName2PrefixMap.get(prefixName);
        if (base == null) {
            throw new OWLXMLParserException(this, "Prefix name not defined: " + prefixName);
        }
        return getIRI(base + localName);
    }

    /**
     * @return prefix name to prefix
     */
    public Map<String, String> getPrefixName2PrefixMap() {
        return prefixName2PrefixMap;
    }

    private void addFactory(PARSER_OWLXMLVocabulary factory, String... legacyElementNames) {
        handlerMap.put(factory.getShortName(), factory);
        for (String elementName : legacyElementNames) {
            handlerMap.put(elementName, factory);
        }
    }

    /**
     * @return ontology
     */
    public OWLOntology getOntology() {
        return ontology;
    }

    /**
     * @return data factory
     */
    public OWLDataFactory getDataFactory() {
        return getOWLOntologyManager().getOWLDataFactory();
    }

    @Override
    public void startDocument() {}

    @Override
    public void endDocument() {}

    @Override
    public void characters(@Nullable char[] ch, int start, int length) throws SAXException {
        if (!handlerStack.isEmpty()) {
            try {
                OWLElementHandler<?> handler = handlerStack.get(0);
                if (handler.isTextContentPossible()) {
                    handler.handleChars(verifyNotNull(ch), start, length);
                }
            } catch (OWLRuntimeException e) {
                throw new SAXException(e);
            }
        }
    }

    @Override
    public void startElement(@Nullable String uri, @Nullable String localName, @Nullable String qName,
            @Nullable Attributes attributes) {
        if (localName == null || attributes == null) {
            // this should never happen, but DefaultHandler does not specify
            // these parameters as Nonnull
            return;
        }
        processXMLBase(attributes);
        if (PREFIX.getShortForm().equals(localName)) {
            String name = attributes.getValue(NAME_ATTRIBUTE.getShortForm());
            String iriString = attributes.getValue(IRI_ATTRIBUTE.getShortForm());
            if (name != null && iriString != null) {
                if (name.endsWith(":")) {
                    prefixName2PrefixMap.put(name, iriString);
                } else {
                    prefixName2PrefixMap.put(name + ':', iriString);
                }
            }
            return;
        }
        PARSER_OWLXMLVocabulary handlerFactory = handlerMap.get(localName);
        if (handlerFactory != null) {
            OWLElementHandler<?> handler = handlerFactory.createHandler(this);
            if (!handlerStack.isEmpty()) {
                handler.setParentHandler(handlerStack.get(0));
            }
            handlerStack.add(0, handler);
            for (int i = 0; i < attributes.getLength(); i++) {
                handler.attribute(attributes.getLocalName(i), attributes.getValue(i));
            }
            handler.startElement(localName);
        }
    }

    protected void processXMLBase(Attributes attributes) {
        String base = attributes.getValue(Namespaces.XML.toString(), "base");
        if (base != null) {
            bases.push(URI.create(base));
        } else {
            bases.push(bases.peek());
        }
    }

    /**
     * Return the base URI for resolution of relative URIs.
     * 
     * @return base URI or null if unavailable (xml:base not present and the
     *         document locator does not provide a URI)
     */
    public URI getBase() {
        return bases.peek();
    }

    @Override
    public void endElement(@Nullable String uri, @Nullable String localName, @Nullable String qName) {
        if (PREFIX.getShortForm().equals(localName)) {
            return;
        }
        if (!handlerStack.isEmpty()) {
            OWLElementHandler<?> handler = handlerStack.remove(0);
            handler.endElement();
        }
        bases.pop();
    }

    @Override
    public void startPrefixMapping(@Nullable String prefix, @Nullable String uri) {
        prefixName2PrefixMap.put(prefix, uri);
    }

    @Override
    public InputSource resolveEntity(@Nullable String publicId, @Nullable String systemId)
            throws IOException, SAXException {
        return super.resolveEntity(publicId, systemId);
    }

    /**
     * @return manager
     */
    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }
}
