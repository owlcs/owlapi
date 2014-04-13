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
package org.semanticweb.owlapi.rdf.rdfxml.parser;

import static org.semanticweb.owlapi.rdf.rdfxml.parser.RDFConstants.*;
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
import javax.xml.parsers.SAXParserFactory;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.LocatorImpl;

/**
 * This class parses the RDF according to the syntax specified in <a
 * href="http://www.w3.org/TR/rdf-syntax-grammar/"
 * >http://www.w3.org/TR/rdf-syntax-grammar/</a>.
 */
public class RDFParser extends DefaultHandler implements IRIProvider {

    private static final String wrongResolve = "IRI '%s' cannot be resolved against current base IRI %s reason is: %s";
    protected static final Locator s_nullDocumentLocator = new LocatorImpl();
    protected static final SAXParserFactory s_parserFactory = initFactory();
    private Map<String, String> resolvedIRIs = new HashMap<String, String>();
    protected Map<String, IRI> uriCache = new HashMap<String, IRI>();

    static SAXParserFactory initFactory() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            factory.setFeature(
                    "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                    false);
            return factory;
        } catch (SAXNotRecognizedException e) {
            throw new OWLRuntimeException(e);
        } catch (SAXNotSupportedException e) {
            throw new OWLRuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    /** Registered error handler. */
    protected ErrorHandler m_errorHandler = new ErrorHandler() {

        @Override
        public void warning(SAXParseException exception) throws SAXException {}

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw exception;
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {}
    };
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

    /**
     * Parses RDF from given input source.
     * 
     * @param source
     *        specifies where RDF comes from
     * @param consumer
     *        receives notifications about RDF parsing events
     * @throws SAXException
     *         SAXException
     * @throws IOException
     *         IOException
     */
    public void
            parse(@Nonnull InputSource source, @Nonnull RDFConsumer consumer)
                    throws SAXException, IOException {
        InputSource s = checkNotNull(source, "source cannot be null");
        checkNotNull(consumer, "consumer cannot be null");
        String systemID = s.getSystemId();
        try {
            m_documentLocator = s_nullDocumentLocator;
            if (systemID != null) {
                m_baseIRI = IRI.create(new URI(source.getSystemId()));
            } else {
                throw new SAXException(
                        "Supplied InputSource object myst have systemId property set, which is needed for IRI resolution.");
            }
            m_consumer = consumer;
            m_consumer.startModel(m_baseIRI);
            s_parserFactory.newSAXParser().parse(source, this);
            m_consumer.endModel();
        } catch (ParserConfigurationException e) {
            throw new SAXException("Parser configuration exception", e);
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

    /**
     * Sets the error handler.
     * 
     * @param errorHandler
     *        the error handler
     */
    public void setErrorHandler(@Nonnull ErrorHandler errorHandler) {
        m_errorHandler = checkNotNull(errorHandler,
                "errorHandler cannot be null");
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        m_errorHandler.warning(e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        m_errorHandler.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        m_errorHandler.fatalError(e);
    }

    @Override
    public void startDocument() {
        m_states.clear();
        pushState(new StartRDF(this));
    }

    @Override
    public void endDocument() throws SAXException {
        verify(state != null, "RDF content not finished.");
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
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
    public void characters(char[] data, int start, int length)
            throws SAXException {
        state.characters(data, start, length);
    }

    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        if ("include-rdf".equals(target)) {
            Map<String, String> arguments = parseStringArguments(data);
            verify(arguments.size() > 2,
                    "Incorrect number of arguments for 'include-rdf' processing instruction.");
            String logicalIRI = arguments.get("logicalIRI");
            String physicalIRI = arguments.get("physicalIRI");
            if (physicalIRI != null) {
                physicalIRI = resolveIRI(physicalIRI);
            }
            m_consumer.includeModel(logicalIRI, physicalIRI);
        }
    }

    /**
     * Pushes a new state on the state stack.
     * 
     * @param s
     *        new state
     */
    public void pushState(State s) {
        m_states.add(s);
        state = s;
    }

    /**
     * Pops a state from the stack.
     */
    public void popState() {
        int size = m_states.size();
        verify(size == 0, "Internal exception: state stack is empty.");
        if (size == 1) {
            state = null;
        } else {
            state = m_states.get(size - 2);
        }
        m_states.remove(size - 1);
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

    /**
     * Processes xml:base reference if there is one.
     * 
     * @param atts
     *        the attributes potentially containing xml:base declaration
     */
    private void processXMLBase(@Nonnull Attributes atts) {
        checkNotNull(atts, "atts cannot be null");
        m_baseIRIs.add(0, m_baseIRI);
        String value = atts.getValue(XMLNS, "base");
        if (value != null) {
            try {
                m_baseIRI = resolveFromDelegate(m_baseIRI, value);
                resolvedIRIs.clear();
            } catch (IllegalArgumentException e) {
                throw new RDFParserException(e, String.format(wrongResolve,
                        value, m_baseIRI, e.getMessage()), m_documentLocator);
            }
        }
    }

    /**
     * Processes xml:language reference is there is one.
     * 
     * @param atts
     *        the attributes potentially containing xml:language declaration
     */
    private void processXMLLanguage(@Nonnull Attributes atts) {
        checkNotNull(atts, "atts cannot be null");
        m_languages.add(0, m_language);
        String value = atts.getValue(XMLLANG);
        if (value != null) {
            m_language = value;
        }
    }

    /**
     * Resolves an IRI with the current base.
     * 
     * @param uri
     *        the IRI being resolved
     * @return the resolved IRI
     */
    @Nonnull
    public String resolveIRI(@Nonnull String uri) {
        checkNotNull(uri, "uri cannot be null");
        if (uri.length() == 0) {
            // MH - Fix for resolving a "This document" reference against base
            // IRIs.
            String namespace = m_baseIRI.getNamespace();
            if (namespace.charAt(namespace.length() - 1) == '#') {
                return namespace.substring(0, namespace.length() - 1);
            }
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
                throw new RDFParserException(e, String.format(wrongResolve,
                        uri, m_baseIRI, e.getMessage()), m_documentLocator);
            }
        }
    }

    /**
     * Called when a statement with resource value is added to the model.
     * 
     * @param subject
     *        IRI of the subject resource
     * @param predicate
     *        IRI of the predicate resource
     * @param object
     *        IRI of the object resource
     * @param reificationID
     *        if not {@code null}, contains IRI of the resource that will wold
     *        the reified statement
     */
    public void statementWithResourceValue(@Nonnull String subject,
            @Nonnull String predicate, @Nonnull String object,
            @Nullable String reificationID) {
        m_consumer.statementWithResourceValue(subject, predicate, object);
        if (reificationID != null) {
            m_consumer.statementWithResourceValue(reificationID, RDF_TYPE,
                    RDF_STATEMENT);
            m_consumer.statementWithResourceValue(reificationID, RDF_SUBJECT,
                    subject);
            m_consumer.statementWithResourceValue(reificationID, RDF_PREDICATE,
                    predicate);
            m_consumer.statementWithResourceValue(reificationID, RDF_OBJECT,
                    object);
        }
    }

    /**
     * Called when a statement with literal value is added to the model.
     * 
     * @param subject
     *        IRI of the subject resource
     * @param predicate
     *        IRI of the predicate resource
     * @param object
     *        literal object value
     * @param dataType
     *        the IRI of the literal's datatype (may be {@code null})
     * @param reificationID
     *        if not {@code null}, contains IRI of the resource that will wold
     *        the reified statement
     */
    public void statementWithLiteralValue(@Nonnull String subject,
            @Nonnull String predicate, @Nonnull String object,
            @Nullable String dataType, @Nullable String reificationID) {
        m_consumer.statementWithLiteralValue(subject, predicate, object,
                m_language, dataType);
        if (reificationID != null) {
            m_consumer.statementWithResourceValue(reificationID, RDF_TYPE,
                    RDF_STATEMENT);
            m_consumer.statementWithResourceValue(reificationID, RDF_SUBJECT,
                    subject);
            m_consumer.statementWithResourceValue(reificationID, RDF_PREDICATE,
                    predicate);
            m_consumer.statementWithLiteralValue(reificationID, RDF_OBJECT,
                    object, m_language, dataType);
        }
    }

    /**
     * Parses the string into a map of name-value pairs.
     * 
     * @param string
     *        string to be parsed
     * @return map of name-value pairs
     */
    private Map<String, String> parseStringArguments(String string) {
        try {
            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(
                    string));
            Map<String, String> result = new HashMap<String, String>();
            tokenizer.nextToken();
            while (tokenizer.ttype != StreamTokenizer.TT_EOF) {
                verify(tokenizer.ttype != StreamTokenizer.TT_WORD,
                        "Invalid processing instruction argument.");
                String name = tokenizer.sval;
                verify('=' != tokenizer.nextToken(), "Expecting token '='");
                tokenizer.nextToken();
                verify(tokenizer.ttype != '\"' && tokenizer.ttype != '\'',
                        "Invalid processing instruction argument.");
                String value = tokenizer.sval;
                result.put(name, value);
                tokenizer.nextToken();
            }
            return result;
        } catch (IOException e) {
            throw new RDFParserException(e, "I/O error", m_documentLocator);
        }
    }

    @Override
    @Nonnull
    public IRI getIRI(@Nonnull String s) {
        return uriCache.get(checkNotNull(s, "s cannot be null"));
    }

    /**
     * If conditon b is true, throw an exception with provided message
     * 
     * @param b
     *        condition to verify
     * @param message
     *        message for the exception
     * @throws RDFParserException
     *         exception thrown
     */
    public void verify(boolean b, String message) {
        if (b) {
            throw new RDFParserException(message, m_documentLocator);
        }
    }
}
