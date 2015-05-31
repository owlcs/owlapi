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
import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;

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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.util.SAXParsers;
import org.xml.sax.*;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.LocatorImpl;

/**
 * This class parses the RDF according to the syntax specified in
 * <a href="http://www.w3.org/TR/rdf-syntax-grammar/" >http://www.w3.org/TR/rdf-
 * syntax-grammar/</a>.
 */
public class RDFParser extends DefaultHandler implements IRIProvider {

    private static final String WRONGRESOLVE = "IRI '%s' cannot be resolved against current base IRI %s reason is: %s";
    protected static final @Nonnull Locator NULLDOCUMENTLOCATOR = new LocatorImpl();
    private final Map<String, String> resolvedIRIs = new HashMap<>();
    protected final Map<String, IRI> uriCache = new HashMap<>();
    /** Registered error handler. */
    protected ErrorHandler errorHandler = new ErrorHandler() {

        @Override
        public void warning(@Nullable SAXParseException exception) {}

        @Override
        public void fatalError(@Nullable SAXParseException exception) throws SAXException {
            throw verifyNotNull(exception);
        }

        @Override
        public void error(@Nullable SAXParseException exception) {}
    };
    /** Stack of base IRIs. */
    protected final LinkedList<IRI> baseIRIs = new LinkedList<>();
    private final Map<IRI, URI> baseURICache = new HashMap<>();
    /** IRI of the document being parsed. */
    protected IRI baseIRI;
    /** The stack of languages. */
    protected final @Nonnull LinkedList<String> languages = new LinkedList<>();
    /** The current language. */
    protected String language;
    /** Consumer receiving notifications about parsing events. */
    protected RDFConsumer consumer;
    /** Current parser's state. */
    protected State state;
    /** Stack of parser states. */
    protected final List<State> states = new ArrayList<>();
    /** Document locator. */
    protected Locator documentLocator;

    protected IRI getBaseIRI() {
        return verifyNotNull(baseIRI, "base IRI has not been set yet");
    }

    protected Locator getDocumentLocator() {
        if (documentLocator != null) {
            return verifyNotNull(documentLocator);
        }
        return NULLDOCUMENTLOCATOR;
    }

    /**
     * Parses RDF from given input source.
     * 
     * @param source
     *        specifies where RDF comes from
     * @param inputConsumer
     *        receives notifications about RDF parsing events
     * @throws SAXException
     *         SAXException
     * @throws IOException
     *         IOException
     */
    public void parse(InputSource source, RDFConsumer inputConsumer) throws SAXException, IOException {
        InputSource s = checkNotNull(source, "source cannot be null");
        checkNotNull(inputConsumer, "consumer cannot be null");
        String systemID = s.getSystemId();
        try {
            if (systemID == null) {
                throw new SAXException(
                        "Supplied InputSource object myst have systemId property set, which is needed for IRI resolution.");
            }
            baseIRI = IRI.create(new URI(source.getSystemId()));
            consumer = inputConsumer;
            inputConsumer.startModel(getBaseIRI());
            DeclHandler handler = new DeclHandler() {

                @Override
                public void internalEntityDecl(@Nullable String name, @Nullable String value) {
                    consumer.addPrefix(checkNotNull(name), checkNotNull(value));
                }

                @Override
                public void externalEntityDecl(@Nullable String name, @Nullable String publicId,
                        @Nullable String systemId) {}

                @Override
                public void elementDecl(@Nullable String name, @Nullable String model) {}

                @Override
                public void attributeDecl(@Nullable String eName, @Nullable String aName, @Nullable String type,
                        @Nullable String mode, @Nullable String value) {}
            };
            SAXParsers.initParserWithOWLAPIStandards(handler).parse(source, this);
            inputConsumer.endModel();
        } catch (URISyntaxException e) {
            throw new SAXException("Invalid SystemID '" + systemID + "'of the supplied input source.", e);
        } finally {
            state = null;
            states.clear();
            baseIRIs.clear();
        }
    }

    @Override
    public void setDocumentLocator(@Nullable Locator locator) {
        documentLocator = checkNotNull(locator, "locator cannot be null");
    }

    /**
     * Sets the error handler.
     * 
     * @param errorHandler
     *        the error handler
     */
    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = checkNotNull(errorHandler, "errorHandler cannot be null");
    }

    @Override
    public void warning(@Nullable SAXParseException e) throws SAXException {
        errorHandler.warning(e);
    }

    @Override
    public void error(@Nullable SAXParseException e) throws SAXException {
        errorHandler.error(e);
    }

    @Override
    public void fatalError(@Nullable SAXParseException e) throws SAXException {
        errorHandler.fatalError(e);
    }

    @Override
    public void startDocument() {
        states.clear();
        pushState(new StartRDF(this));
    }

    @Override
    public void endDocument() {
        verify(state != null, "RDF content not finished.");
    }

    @Override
    public void startElement(@Nullable String uri, @Nullable String localName, @Nullable String qName,
            @Nullable Attributes attributes) throws SAXException {
        if (localName == null || attributes == null || uri == null || qName == null) {
            // this should never happen, but DefaultHandler does not specify
            // these parameters as Nonnull
            return;
        }
        processXMLBase(attributes);
        processXMLLanguage(attributes);
        state.startElement(uri, localName, qName, attributes);
    }

    @Override
    public void endElement(@Nullable String uri, @Nullable String localName, @Nullable String qName)
            throws SAXException {
        state.endElement(checkNotNull(uri), checkNotNull(localName), checkNotNull(qName));
        baseIRI = baseIRIs.remove(0);
        language = languages.remove(0);
    }

    @Override
    public void characters(@Nullable char[] ch, int start, int length) throws SAXException {
        state.characters(verifyNotNull(ch), start, length);
    }

    @Override
    public void processingInstruction(@Nullable String target, @Nullable String data) {
        if ("include-rdf".equals(target)) {
            Map<String, String> arguments = parseStringArguments(checkNotNull(data));
            verify(arguments.size() > 2, "Incorrect number of arguments for 'include-rdf' processing instruction.");
            String logicalIRI = arguments.get("logicalIRI");
            String physicalIRI = arguments.get("physicalIRI");
            if (physicalIRI != null) {
                physicalIRI = resolveIRI(physicalIRI);
            }
            consumer.includeModel(logicalIRI, physicalIRI);
        }
    }

    /**
     * Pushes a new state on the state stack.
     * 
     * @param s
     *        new state
     */
    public void pushState(State s) {
        states.add(s);
        state = s;
    }

    /**
     * Pops a state from the stack.
     */
    public void popState() {
        int size = states.size();
        verify(size == 0, "Internal exception: state stack is empty.");
        if (size == 1) {
            state = null;
        } else {
            state = states.get(size - 2);
        }
        states.remove(size - 1);
    }

    private IRI resolveFromDelegate(IRI iri, String value) {
        checkNotNull(iri, "iri cannot be null");
        checkNotNull(value, "value cannot be null");
        if (NodeID.isAnonymousNodeIRI(value)) {
            return IRI.create(value, null);
        }
        // cache the delegate URI if not there already
        if (!baseURICache.containsKey(getBaseIRI())) {
            baseURICache.put(getBaseIRI(), getBaseIRI().toURI());
        }
        // get hold of the delegate URI
        URI delegateURI = baseURICache.get(getBaseIRI());
        // resolve against delegate
        URI resolve = delegateURI.resolve(value.replace(" ", "%20"));
        return IRI.create(resolve);
    }

    /**
     * Processes xml:base reference if there is one.
     * 
     * @param atts
     *        the attributes potentially containing xml:base declaration
     */
    private void processXMLBase(Attributes atts) {
        checkNotNull(atts, "atts cannot be null");
        baseIRIs.add(0, getBaseIRI());
        String value = atts.getValue(XMLNS, "base");
        if (value != null) {
            try {
                baseIRI = resolveFromDelegate(getBaseIRI(), value);
                resolvedIRIs.clear();
            } catch (IllegalArgumentException e) {
                throw new RDFParserException(e, String.format(WRONGRESOLVE, value, getBaseIRI(), e.getMessage()),
                        getDocumentLocator());
            }
        }
    }

    /**
     * Processes xml:language reference is there is one.
     * 
     * @param atts
     *        the attributes potentially containing xml:language declaration
     */
    private void processXMLLanguage(Attributes atts) {
        checkNotNull(atts, "atts cannot be null");
        languages.add(0, language);
        String value = atts.getValue(XMLLANG);
        if (value != null) {
            language = value;
        }
    }

    /**
     * Resolves an IRI with the current base.
     * 
     * @param uri
     *        the IRI being resolved
     * @return the resolved IRI
     */
    public String resolveIRI(String uri) {
        checkNotNull(uri, "uri cannot be null");
        if (uri.isEmpty()) {
            // MH - Fix for resolving a "This document" reference against base
            // IRIs.
            String namespace = getBaseIRI().getNamespace();
            if (namespace.charAt(namespace.length() - 1) == '#') {
                return namespace.substring(0, namespace.length() - 1);
            }
            String base = getBaseIRI().toString();
            int hashIndex = base.indexOf('#');
            if (hashIndex != -1) {
                return base.substring(0, hashIndex);
            }
            return base;
        } else {
            try {
                String resolved = resolvedIRIs.get(uri);
                if (resolved != null) {
                    return resolved;
                }
                IRI theIRI = resolveFromDelegate(getBaseIRI(), uri);
                String u = theIRI.toString();
                uriCache.put(u, theIRI);
                resolvedIRIs.put(uri, u);
                return u;
            } catch (IllegalArgumentException e) {
                throw new RDFParserException(e, String.format(WRONGRESOLVE, uri, getBaseIRI(), e.getMessage()),
                        getDocumentLocator());
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
    public void statementWithResourceValue(String subject, String predicate, String object,
            @Nullable String reificationID) {
        String remappedSubject = consumer.remapOnlyIfRemapped(subject);
        consumer.statementWithResourceValue(remappedSubject, predicate, object);
        if (reificationID != null) {
            consumer.statementWithResourceValue(reificationID, RDF_TYPE, RDF_STATEMENT);
            consumer.statementWithResourceValue(reificationID, RDF_SUBJECT, remappedSubject);
            consumer.statementWithResourceValue(reificationID, RDF_PREDICATE, predicate);
            consumer.statementWithResourceValue(reificationID, RDF_OBJECT, object);
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
    public void statementWithLiteralValue(String subject, String predicate, String object, @Nullable String dataType,
            @Nullable String reificationID) {
        consumer.statementWithLiteralValue(subject, predicate, object, language, dataType);
        if (reificationID != null) {
            consumer.statementWithResourceValue(reificationID, RDF_TYPE, RDF_STATEMENT);
            consumer.statementWithResourceValue(reificationID, RDF_SUBJECT, subject);
            consumer.statementWithResourceValue(reificationID, RDF_PREDICATE, predicate);
            consumer.statementWithLiteralValue(reificationID, RDF_OBJECT, object, language, dataType);
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
            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(string));
            Map<String, String> result = new HashMap<>();
            tokenizer.nextToken();
            while (tokenizer.ttype != StreamTokenizer.TT_EOF) {
                verify(tokenizer.ttype != StreamTokenizer.TT_WORD, "Invalid processing instruction argument.");
                String name = tokenizer.sval;
                verify('=' != tokenizer.nextToken(), "Expecting token '='");
                tokenizer.nextToken();
                verify(tokenizer.ttype != '\"' && tokenizer.ttype != '\'', "Invalid processing instruction argument.");
                String value = tokenizer.sval;
                result.put(name, value);
                tokenizer.nextToken();
            }
            return result;
        } catch (IOException e) {
            throw new RDFParserException(e, "I/O error", getDocumentLocator());
        }
    }

    @Override
    public IRI getIRI(String s) {
        return uriCache.get(checkNotNull(s, "s cannot be null"));
    }

    /**
     * If conditon b is true, throw an exception with provided message.
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
            throw new RDFParserException(message, getDocumentLocator());
        }
    }
}
