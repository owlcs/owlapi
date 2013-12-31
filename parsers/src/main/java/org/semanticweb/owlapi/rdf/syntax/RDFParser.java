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
 *
 *
 * Original code by Boris Motik.  The original code formed part of KAON
 * which is licensed under the LGPL License. The original package
 * name was edu.unika.aifb.rdf.api
 *
 */
package org.semanticweb.owlapi.rdf.syntax;

import static org.semanticweb.owlapi.rdf.util.RDFConstants.*;
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.LocatorImpl;

/** This class parses the RDF according to the syntax specified in <a
 * href="http://www.w3.org/TR/rdf-syntax-grammar/"
 * >http://www.w3.org/TR/rdf-syntax-grammar/</a>. */
public class RDFParser extends DefaultHandler {
    protected static final Locator s_nullDocumentLocator = new LocatorImpl();
    protected static final SAXParserFactory s_parserFactory = SAXParserFactory
            .newInstance();
    private Map<String, String> resolvedIRIs = new HashMap<String, String>();
    protected Map<String, IRI> uriCache = new HashMap<String, IRI>();
    static {
        s_parserFactory.setNamespaceAware(true);
    }
    /** Registered error handler. */
    protected ErrorHandler m_errorHandler;
    /** Stack of base IRIs. */
    protected LinkedList<IRI> m_baseIRIs = new LinkedList<IRI>();
    private Map<IRI, URI> m_baseURICache = new HashMap<IRI, URI>();
    /** IRI of the document being parsed. */
    protected IRI m_baseIRI;
    /** The stack of languages. */
    protected LinkedList<String> m_languages = new LinkedList<String>();
    /** The current language. */
    protected String m_language;
    /** Consumer receiving notifications about parsing events. */
    protected RDFConsumer m_consumer;
    /** Current parser's state. */
    protected State state;
    /** Stack of parser states. */
    protected List<State> m_states = new ArrayList<State>();
    /** Document locator. */
    protected Locator m_documentLocator;

    /** Creates a RDF parser. */
    public RDFParser() {}

    /** Parses RDF from given input source.
     * 
     * @param source
     *            specifies where RDF comes from
     * @param consumer
     *            receives notifications about RDF parsing events
     * @throws SAXException
     *             SAXException
     * @throws IOException
     *             IOException */
    public void parse(@Nonnull InputSource source, @Nonnull RDFConsumer consumer)
            throws SAXException, IOException {
        String systemID = checkNotNull(source, "source cannot be null").getSystemId();
        checkNotNull(consumer, "consumer cannot be null");
        try {
            m_documentLocator = s_nullDocumentLocator;
            if (systemID != null) {
                m_baseIRI = IRI.create(new URI(source.getSystemId()));
            } else {
                throw new SAXException(
                        "Supplied InputSource object myst have systemId property set, which is needed for IRI resolution.");
            }
            m_consumer = consumer;
            m_consumer.startModel(m_baseIRI.toString());
            SAXParser parser = s_parserFactory.newSAXParser();
            parser.parse(source, this);
            m_consumer.endModel();
        } catch (ParserConfigurationException e) {
            throw new SAXException("Parser coniguration exception", e);
        } catch (URISyntaxException e) {
            throw new SAXException("Invalid SystemID '" + systemID
                    + "'of the supplied input source.");
        } finally {
            state = null;
            m_states.clear();
            m_documentLocator = null;
            m_baseIRIs.clear();
        }
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        m_documentLocator = locator;
    }

    /** Sets the error handler.
     * 
     * @param errorHandler
     *            the error handler */
    public void setErrorHandler(@Nonnull ErrorHandler errorHandler) {
        m_errorHandler = checkNotNull(errorHandler, "errorHandler cannot be null");
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        if (m_errorHandler == null) {
            super.warning(e);
        } else {
            m_errorHandler.warning(e);
        }
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        if (m_errorHandler == null) {
            super.error(e);
        } else {
            m_errorHandler.error(e);
        }
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        if (m_errorHandler == null) {
            super.fatalError(e);
        } else {
            m_errorHandler.fatalError(e);
        }
    }

    @Override
    public void startDocument() {
        m_states.clear();
        pushState(new StartRDF(this));
    }

    @Override
    public void endDocument() throws SAXException {
        if (state != null) {
            throw new RDFParserException("RDF content not finished.", m_documentLocator);
        }
    }

    @Override
    public void startElement(String namespaceIRI, String localName, String qName,
            Attributes atts) throws SAXException {
        processXMLBase(atts);
        processXMLLanguage(atts);
        state.startElement(namespaceIRI, localName, qName, atts);
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        state.endElement(namespaceIRI, localName, qName);
        m_baseIRI = m_baseIRIs.remove(0);
        m_language = m_languages.remove(0);
    }

    @Override
    public void characters(char[] data, int start, int length) throws SAXException {
        state.characters(data, start, length);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        if ("include-rdf".equals(target)) {
            Map<String, String> arguments = parseStringArguments(data);
            if (arguments.size() > 2) {
                throw new RDFParserException(
                        "Incorrect number of arguments for 'include-rdf' processing instruction.",
                        m_documentLocator);
            }
            String logicalIRI = arguments.get("logicalIRI");
            String physicalIRI = arguments.get("physicalIRI");
            if (physicalIRI != null) {
                physicalIRI = resolveIRI(physicalIRI);
            }
            m_consumer.includeModel(logicalIRI, physicalIRI);
        } else if ("model-attribute".equals(target)) {
            Map<String, String> arguments = parseStringArguments(data);
            if (arguments.size() != 2) {
                throw new RDFParserException(
                        "Incorrect number of arguments for 'model-attribute' processing instruction.",
                        m_documentLocator);
            }
            String key = arguments.get("key");
            if (key == null) {
                throw new RDFParserException(
                        "Mising the 'key' argument for 'model-attribute' processing instruction.",
                        m_documentLocator);
            }
            String value = arguments.get("value");
            if (value == null) {
                throw new RDFParserException(
                        "Mising the 'value' argument for 'model-attribute' processing instruction.",
                        m_documentLocator);
            }
            m_consumer.addModelAttribte(key, value);
        }
    }

    /** Pushes a new state on the state stack.
     * 
     * @param s
     *            new state */
    protected void pushState(State s) {
        m_states.add(s);
        state = s;
    }

    /** Pops a state from the stack.
     * 
     * @throws SAXException
     *             SAXException */
    protected void popState() throws SAXException {
        int size = m_states.size();
        if (size == 0) {
            throw new RDFParserException("Internal exception: state stack is empty.",
                    m_documentLocator);
        }
        if (size == 1) {
            state = null;
        } else {
            state = m_states.get(size - 2);
        }
        m_states.remove(size - 1);
    }

    /** Checks if attribute list contains some of the unsupported attributes.
     * 
     * @param atts
     *            the attributes
     * @throws SAXException
     *             SAXException */
    protected void checkUnsupportedAttributes(@Nonnull Attributes atts)
            throws SAXException {
        checkNotNull(atts, "atts cannot be null");
        if (atts.getIndex(RDFNS, ATTR_ABOUT_EACH) != -1) {
            throw new RDFParserException("rdf:aboutEach attribute is not supported.",
                    m_documentLocator);
        }
        if (atts.getIndex(RDFNS, ATTR_ABOUT_EACH_PREFIX) != -1) {
            throw new RDFParserException(
                    "rdf:aboutEachPrefix attribute is not supported.", m_documentLocator);
        }
    }

    @Nonnull
    private IRI resolveFromDelegate(@Nonnull IRI iri, @Nonnull String value) {
        checkNotNull(iri, "iri cannot be null");
        checkNotNull(value, "value cannot be null");
        if (NodeID.isAnonymousNodeIRI(value)) {
            return IRI.create(value, null);
        }
        // cache the delegate URI if not there already
        if (!m_baseURICache.containsKey(m_baseIRI)) {
            m_baseURICache.put(m_baseIRI, m_baseIRI.toURI());
        }
        // get hold of the delegate URI
        URI delegateURI = m_baseURICache.get(m_baseIRI);
        // resolve against delegate
        return IRI.create(delegateURI.resolve(value));
    }

    /** Processes xml:base reference if there is one.
     * 
     * @param atts
     *            the attributes potentially containing xml:base declaration
     * @throws SAXException
     *             SAXException */
    protected void processXMLBase(@Nonnull Attributes atts) throws SAXException {
        checkNotNull(atts, "atts cannot be null");
        m_baseIRIs.add(0, m_baseIRI);
        String value = atts.getValue(XMLNS, "base");
        if (value != null) {
            try {
                m_baseIRI = resolveFromDelegate(m_baseIRI, value);
                resolvedIRIs.clear();
            } catch (IllegalArgumentException e) {
                RDFParserException exception = new RDFParserException("New base IRI '"
                        + value + "' cannot be resolved against curent base IRI "
                        + m_baseIRI.toString(), m_documentLocator);
                exception.initCause(e);
                throw exception;
            }
        }
    }

    /** Processes xml:language reference is there is one.
     * 
     * @param atts
     *            the attributes potentially containing xml:language declaration */
    protected void processXMLLanguage(@Nonnull Attributes atts) {
        checkNotNull(atts, "atts cannot be null");
        m_languages.add(0, m_language);
        String value = atts.getValue(XMLLANG);
        if (value != null) {
            m_language = value;
        }
    }

    /** Resolves an IRI with the current base.
     * 
     * @param uri
     *            the IRI being resolved
     * @return the resolved IRI
     * @throws SAXException
     *             SAXException */
    @Nonnull
    protected String resolveIRI(@Nonnull String uri) throws SAXException {
        checkNotNull(uri, "uri cannot be null");
        if (uri.length() == 0) {
            // MH - Fix for resolving a "This document" reference against base
            // IRIs.
            // XXX namespace?
            String base = m_baseIRI.toString();
            int hashIndex = base.indexOf("#");
            if (hashIndex != -1) {
                return base.substring(0, hashIndex);
            } else {
                return base;
            }
        } else {
            try {
                String resolved = resolvedIRIs.get(uri);
                if (resolved != null) {
                    return resolved;
                } else {
                    IRI theIRI = resolveFromDelegate(m_baseIRI, uri);
                    String u = theIRI.toString();
                    uriCache.put(u, theIRI);
                    resolvedIRIs.put(uri, u);
                    return u;
                }
            } catch (IllegalArgumentException e) {
                RDFParserException exception = new RDFParserException("IRI '" + uri
                        + "' cannot be resolved against curent base IRI "
                        + m_baseIRI.toString(), m_documentLocator);
                exception.initCause(e);
                throw exception;
            }
        }
    }

    /** Returns an absolute IRI from an ID.
     * 
     * @param id
     *            id
     * @return string for IRI
     * @throws SAXException
     *             SAXException */
    @Nonnull
    protected String getIRIFromID(@Nonnull String id) throws SAXException {
        return resolveIRI("#" + id);
    }

    /** Returns an absolute IRI from an about attribute.
     * 
     * @param about
     *            about
     * @return string for IRI
     * @throws SAXException
     *             SAXException */
    @Nonnull
    protected String getIRIFromAbout(@Nonnull String about) throws SAXException {
        return resolveIRI(about);
    }

    /** Returns an absolute IRI from a resource attribute.
     * 
     * @param resource
     *            resource
     * @return string for IRI
     * @throws SAXException
     *             SAXException */
    @Nonnull
    protected String getIRIFromResource(@Nonnull String resource) throws SAXException {
        return resolveIRI(resource);
    }

    /** Extracts the IRI of the resource from rdf:ID, rdf:nodeID or rdf:about
     * attribute. If no attribute is found, an IRI is generated.
     * 
     * @param atts
     *            atts
     * @return string for IRI
     * @throws SAXException
     *             SAXException */
    @Nonnull
    protected String getIDNodeIDAboutResourceIRI(@Nonnull Attributes atts)
            throws SAXException {
        checkNotNull(atts, "atts cannot be null");
        String result = null;
        String value = atts.getValue(RDFNS, ATTR_ID);
        if (value != null) {
            result = getIRIFromID(value);
        }
        value = atts.getValue(RDFNS, ATTR_ABOUT);
        if (value != null) {
            if (result != null) {
                throw new RDFParserException(
                        "Element cannot specify both rdf:ID and rdf:about attributes.",
                        m_documentLocator);
            }
            result = getIRIFromAbout(value);
        }
        value = atts.getValue(RDFNS, ATTR_NODE_ID);
        if (value != null) {
            if (result != null) {
                throw new RDFParserException(
                        "Element cannot specify both rdf:nodeID and rdf:ID or rdf:about attributes.",
                        m_documentLocator);
            }
            result = NodeID.getIRIFromNodeID(value);
        }
        if (result == null) {
            result = NodeID.nextAnonymousIRI();
        }
        return result;
    }

    /** Extracts the IRI of the resource from rdf:resource or rdf:nodeID
     * attribute. If no attribute is found, {@code null} is returned.
     * 
     * @param atts
     *            the attributes
     * @return the IRI of the resource or {@code null}
     * @throws SAXException
     *             SAXException */
    @Nullable
    protected String getNodeIDResourceResourceIRI(@Nonnull Attributes atts)
            throws SAXException {
        checkNotNull(atts, "atts cannot be null");
        String value = atts.getValue(RDFNS, ATTR_RESOURCE);
        if (value != null) {
            return getIRIFromResource(value);
        } else {
            value = atts.getValue(RDFNS, ATTR_NODE_ID);
            if (value != null) {
                return NodeID.getIRIFromNodeID(value);
            } else {
                return null;
            }
        }
    }

    /** Called when a statement with resource value is added to the model.
     * 
     * @param subject
     *            IRI of the subject resource
     * @param predicate
     *            IRI of the predicate resource
     * @param object
     *            IRI of the object resource
     * @param reificationID
     *            if not {@code null}, contains IRI of the resource that will
     *            wold the reified statement
     * @throws SAXException
     *             SAXException */
    protected void statementWithResourceValue(@Nonnull String subject,
            @Nonnull String predicate, @Nonnull String object,
            @Nullable String reificationID) throws SAXException {
        m_consumer.statementWithResourceValue(subject, predicate, object);
        if (reificationID != null) {
            m_consumer.statementWithResourceValue(reificationID, RDF_TYPE, RDF_STATEMENT);
            m_consumer.statementWithResourceValue(reificationID, RDF_SUBJECT, subject);
            m_consumer
                    .statementWithResourceValue(reificationID, RDF_PREDICATE, predicate);
            m_consumer.statementWithResourceValue(reificationID, RDF_OBJECT, object);
        }
    }

    /** Called when a statement with literal value is added to the model.
     * 
     * @param subject
     *            IRI of the subject resource
     * @param predicate
     *            IRI of the predicate resource
     * @param object
     *            literal object value
     * @param dataType
     *            the IRI of the literal's datatype (may be {@code null})
     * @param reificationID
     *            if not {@code null}, contains IRI of the resource that will
     *            wold the reified statement
     * @throws SAXException
     *             SAXException */
    protected void statementWithLiteralValue(@Nonnull String subject,
            @Nonnull String predicate, @Nonnull String object, @Nullable String dataType,
            @Nullable String reificationID) throws SAXException {
        m_consumer.statementWithLiteralValue(subject, predicate, object, m_language,
                dataType);
        if (reificationID != null) {
            m_consumer.statementWithResourceValue(reificationID, RDF_TYPE, RDF_STATEMENT);
            m_consumer.statementWithResourceValue(reificationID, RDF_SUBJECT, subject);
            m_consumer
                    .statementWithResourceValue(reificationID, RDF_PREDICATE, predicate);
            m_consumer.statementWithLiteralValue(reificationID, RDF_OBJECT, object,
                    m_language, dataType);
        }
    }

    /** Parses the propertyAttributes production.
     * 
     * @param subjectIRI
     *            IRI of the resource whose properties are being parsed
     * @param atts
     *            attributes
     * @param reificationManager
     *            the reification manager
     * @throws SAXException
     *             SAXException */
    protected void propertyAttributes(@Nonnull String subjectIRI,
            @Nonnull Attributes atts, @Nonnull ReificationManager reificationManager)
            throws SAXException {
        int length = atts.getLength();
        for (int i = 0; i < length; i++) {
            String nsIRI = atts.getURI(i);
            String localName = atts.getLocalName(i);
            if (!XMLNS.equals(nsIRI)
                    && !XMLLANG.equals(localName)
                    && !(RDFNS.equals(nsIRI) && (ATTR_ID.equals(localName)
                            || ATTR_NODE_ID.equals(localName)
                            || ATTR_ABOUT.equals(localName) || ELT_TYPE.equals(localName)
                            || ATTR_RESOURCE.equals(localName)
                            || ATTR_PARSE_TYPE.equals(localName)
                            || ATTR_ABOUT_EACH.equals(localName)
                            || ATTR_ABOUT_EACH_PREFIX.equals(localName) || ATTR_BAG_ID
                                .equals(localName)))) {
                String value = atts.getValue(i);
                String reificationID = reificationManager.getReificationID(null);
                statementWithLiteralValue(subjectIRI, nsIRI + localName, value, null,
                        reificationID);
            } else if (RDFNS.equals(nsIRI) && ELT_TYPE.equals(localName)) {
                String value = resolveIRI(atts.getValue(i));
                String reificationID = reificationManager.getReificationID(null);
                statementWithResourceValue(subjectIRI, nsIRI + localName, value,
                        reificationID);
            }
        }
    }

    /** Checks whether given characters contain only whitespace.
     * 
     * @param data
     *            the data being checked
     * @param start
     *            the start index (inclusive)
     * @param length
     *            the end index (non-inclusive)
     * @return {@code true} if characters contain whitespace */
    protected boolean isWhitespaceOnly(char[] data, int start, int length) {
        int end = start + length;
        for (int i = start; i < end; i++) {
            char c = data[i];
            if (c != '\n' && c != '\r' && c != '\t' && c != ' ') {
                return false;
            }
        }
        return true;
    }

    /** Checks whether given characters contain only whitespace.
     * 
     * @param buffer
     *            the data being checked
     * @return {@code true} if characters contain whitespace */
    protected boolean isWhitespaceOnly(StringBuilder buffer) {
        for (int i = 0; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (c != '\n' && c != '\r' && c != '\t' && c != ' ') {
                return false;
            }
        }
        return true;
    }

    /** Returns the reification manager for given attributes.
     * 
     * @param atts
     *            the attributes
     * @return the reification manager
     * @throws SAXException
     *             SAXException */
    protected ReificationManager getReificationManager(Attributes atts)
            throws SAXException {
        String bagIDAttr = atts.getValue(RDFNS, ATTR_BAG_ID);
        if (bagIDAttr == null) {
            return ReificationManager.INSTANCE;
        } else {
            String bagID = getIRIFromID(bagIDAttr);
            return new ReifiedStatementBag(bagID);
        }
    }

    /** Parses the string into a map of name-value pairs.
     * 
     * @param string
     *            string to be parsed
     * @return map of name-value pairs
     * @throws SAXException
     *             if there was an IOException this will be wrapped in a parse
     *             exception */
    protected Map<String, String> parseStringArguments(String string) throws SAXException {
        try {
            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(string));
            Map<String, String> result = new HashMap<String, String>();
            tokenizer.nextToken();
            while (tokenizer.ttype != StreamTokenizer.TT_EOF) {
                if (tokenizer.ttype != StreamTokenizer.TT_WORD) {
                    throw new RDFParserException(
                            "Invalid processing instruction argument.", m_documentLocator);
                }
                String name = tokenizer.sval;
                if ('=' != tokenizer.nextToken()) {
                    throw new RDFParserException("Expecting token =", m_documentLocator);
                }
                tokenizer.nextToken();
                if (tokenizer.ttype != '\"' && tokenizer.ttype != '\'') {
                    throw new RDFParserException(
                            "Invalid processing instruction argument.", m_documentLocator);
                }
                String value = tokenizer.sval;
                result.put(name, value);
                tokenizer.nextToken();
            }
            return result;
        } catch (IOException e) {
            RDFParserException exception = new RDFParserException("I/O error",
                    m_documentLocator);
            exception.initCause(e);
            throw exception;
        }
    }

    /** @param s
     *            string
     * @return iri */
    @Nonnull
    public IRI getIRI(@Nonnull String s) {
        return uriCache.get(checkNotNull(s, "s cannot be null"));
    }

    protected static class ReificationManager {
        public static final ReificationManager INSTANCE = new ReificationManager();

        public String getReificationID(String reificationID) throws SAXException {
            return reificationID;
        }
    }

    protected class ReifiedStatementBag extends ReificationManager {
        protected String m_uri;
        protected int m_elements;

        public ReifiedStatementBag(String uri) throws SAXException {
            m_uri = uri;
            m_elements = 0;
            statementWithResourceValue(m_uri, RDF_TYPE, RDF_BAG, null);
        }

        @Override
        public String getReificationID(String reificationID) throws SAXException {
            String resultIRI;
            if (reificationID == null) {
                resultIRI = NodeID.nextAnonymousIRI();
            } else {
                resultIRI = reificationID;
            }
            statementWithResourceValue(m_uri, RDFNS + "_" + ++m_elements, resultIRI, null);
            return resultIRI;
        }
    }
}
