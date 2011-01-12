package org.semanticweb.owlapi.rdf.syntax;
/*
 * Copyright (C) 2009, University of Manchester
 * Original code by Boris Motik.  The original code formed part of KAON
 * which is licensed under the Lesser General Public License. The original package
 * name was edu.unika.aifb.rdf.api
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
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

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.LocatorImpl;


/**
 * This class parses the RDF according to the syntax specified in <a href="http://www.w3.org/TR/rdf-syntax-grammar/">http://www.w3.org/TR/rdf-syntax-grammar/</a>.
 */
@SuppressWarnings("unused")
public class RDFParser extends DefaultHandler implements RDFConstants {

    protected static final Locator s_nullDocumentLocator = new LocatorImpl();

    protected static final SAXParserFactory s_parserFactory = SAXParserFactory.newInstance();

    private Map<String, String> resolvedIRIs = new HashMap<String, String>();

    protected Map<String, IRI> uriCache = new HashMap<String, IRI>();

    static {
        s_parserFactory.setNamespaceAware(true);
    }


    /**
     * Registered error handler.
     */
    protected ErrorHandler m_errorHandler;

    /**
     * Stack of base IRIs.
     */
    protected LinkedList<IRI> m_baseIRIs;

    private Map<IRI, URI> m_baseURICache;
    /**
     * IRI of the document being parsed.
     */
    protected IRI m_baseIRI;

    /**
     * The stack of languages.
     */
    protected LinkedList<String> m_languages;

    /**
     * The current language.
     */
    protected String m_language;

    /**
     * Consumer receiving notifications about parsing events.
     */
    protected RDFConsumer m_consumer;

    /**
     * Current parser's state.
     */
    protected State m_state;

    /**
     * Stack of parser states.
     */
    protected List<State> m_states;

    /**
     * Number of the last generated IRI.
     */
    protected int m_generatedIRIIndex;

    /**
     * Document locator.
     */
    protected Locator m_documentLocator;


    /**
     * Creates a RDF parser.
     */
    public RDFParser() {
        m_states = new ArrayList<State>();
        m_baseIRIs = new LinkedList<IRI>();
        m_languages = new LinkedList<String>();
        m_baseURICache=new HashMap<IRI, URI>();
    }


    /**
     * Parses RDF from given input source.
     * @param source   specifies where RDF comes from
     * @param consumer receives notifications about RDF parsing events
     */
    public void parse(InputSource source, RDFConsumer consumer) throws SAXException, IOException {
        String systemID = source.getSystemId();
        try {
            m_documentLocator = s_nullDocumentLocator;
            if (systemID != null)
                m_baseIRI = IRI.create(new URI(source.getSystemId()));
            else
                throw new SAXException(
                        "Supplied InputSource object myst have systemId property set, which is needed for IRI resolution.");
            m_consumer = consumer;
            m_consumer.startModel(m_baseIRI.toString());
            SAXParser parser = s_parserFactory.newSAXParser();
            parser.parse(source, this);
            m_consumer.endModel();
        }
        catch (ParserConfigurationException e) {
            throw new SAXException("Parser coniguration exception", e);
        }
        catch (URISyntaxException e) {
            throw new SAXException("Invalid SystemID '" + systemID + "'of the supplied input source.");
        }
        finally {
            m_state = null;
            m_states.clear();
            m_documentLocator = null;
            m_baseIRIs.clear();
        }
    }


    /**
     * Called to receive a document locator.
     * @param locator the document locator
     */
    @Override
	public void setDocumentLocator(Locator locator) {
        m_documentLocator = locator;
    }


    /**
     * Sets the error handler.
     * @param errorHandler the error handler
     */
    public void setErrorHandler(ErrorHandler errorHandler) {
        m_errorHandler = errorHandler;
    }


    /**
     * Called when warning is encountered.
     * @param e the exception
     */
    @Override
	public void warning(SAXParseException e) throws SAXException {
        if (m_errorHandler == null)
            super.warning(e);
        else
            m_errorHandler.warning(e);
    }


    /**
     * Called when error is encountered.
     * @param e the exception
     */
    @Override
	public void error(SAXParseException e) throws SAXException {
        if (m_errorHandler == null)
            super.error(e);
        else
            m_errorHandler.error(e);
    }


    /**
     * Called when a fatal error is encountered.
     * @param e the exception
     */
    @Override
	public void fatalError(SAXParseException e) throws SAXException {
        if (m_errorHandler == null)
            super.fatalError(e);
        else
            m_errorHandler.fatalError(e);
    }


    /**
     * Called when document parsing is started.
     */
    @Override
	public void startDocument() {
        m_generatedIRIIndex = 0;
        m_states.clear();
        pushState(new StartRDF());
    }


    /**
     * Called when document parsing is ended.
     */
    @Override
	public void endDocument() throws SAXException {
        if (m_state != null)
            throw new RDFParserException("RDF content not finished.", m_documentLocator);
    }


    /**
     * Called when an element is started.
     * @param namespaceIRI the IRI of the namespace
     * @param localName    the local name of the element
     * @param qName        the Q-name of the element
     * @param atts         the attributes
     */
    @Override
	public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws SAXException {
        processXMLBase(atts);
        processXMLLanguage(atts);
        m_state.startElement(namespaceIRI, localName, qName, atts);
    }


    /**
     * Called when element parsing is ended.
     * @param namespaceIRI the IRI of the namespace
     * @param localName    the local name of the element
     * @param qName        the Q-name of the element
     */
    @Override
	public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
        m_state.endElement(namespaceIRI, localName, qName);
        m_baseIRI = m_baseIRIs.remove(0);
        m_language = m_languages.remove(0);
    }


    /**
     * Called when character content is parsed.
     * @param data   the data buffer containing the characters
     * @param start  the start index of character text
     * @param length the length of the character text
     */
    @Override
	public void characters(char[] data, int start, int length) throws SAXException {
        m_state.characters(data, start, length);
    }


    /**
     * Called when processing instruction is parsed.
     * @param target the name of the processing instruction
     * @param data   the argument to the processing instruction
     */
    @Override
	public void processingInstruction(String target, String data) throws SAXException {
        if ("include-rdf".equals(target)) {
            Map<String, String> arguments = parseStringArguments(data);
            if (arguments.size() > 2)
                throw new RDFParserException("Incorrect number of arguments for 'include-rdf' processing instruction.",
                                             m_documentLocator);
            String logicalIRI = arguments.get("logicalIRI");
            String physicalIRI = arguments.get("physicalIRI");
            if (physicalIRI != null)
                physicalIRI = resolveIRI(physicalIRI);
            m_consumer.includeModel(logicalIRI, physicalIRI);
        }
        else if ("model-attribute".equals(target)) {
            Map<String, String> arguments = parseStringArguments(data);
            if (arguments.size() != 2)
                throw new RDFParserException(
                        "Incorrect number of arguments for 'model-attribute' processing instruction.",
                        m_documentLocator);
            String key = arguments.get("key");
            if (key == null)
                throw new RDFParserException("Mising the 'key' argument for 'model-attribute' processing instruction.",
                                             m_documentLocator);
            String value = arguments.get("value");
            if (value == null)
                throw new RDFParserException("Mising the 'value' argument for 'model-attribute' processing instruction.",
                                             m_documentLocator);
            m_consumer.addModelAttribte(key, value);
        }
    }


    /**
     * Pushes a new state on the state stack.
     * @param state new state
     */
    protected void pushState(State state) {
        m_states.add(state);
        m_state = state;
    }


    /**
     * Pops a state from the stack.
     */
    protected void popState() throws SAXException {
        int size = m_states.size();
        if (size == 0)
            throw new RDFParserException("Internal exception: state stack is empty.", m_documentLocator);
        if (size == 1)
            m_state = null;
        else
            m_state = m_states.get(size - 2);
        m_states.remove(size - 1);
    }


    /**
     * Checks if attribute list contains some of the unsupported attributes.
     * @param atts the attributes
     */
    protected void checkUnsupportedAttributes(Attributes atts) throws SAXException {
        if (atts.getIndex(RDFNS, ATTR_ABOUT_EACH) != -1)
            throw new RDFParserException("rdf:aboutEach attribute is not supported.", m_documentLocator);
        if (atts.getIndex(RDFNS, ATTR_ABOUT_EACH_PREFIX) != -1)
            throw new RDFParserException("rdf:aboutEachPrefix attribute is not supported.", m_documentLocator);
    }


    private IRI resolveFromDelegate(IRI iri, String value) {
    	// cache the delegate URI if not there already
    	if(!m_baseURICache.containsKey(m_baseIRI)) {
        	m_baseURICache.put(m_baseIRI, m_baseIRI.toURI());
        }
    	// get hold of the delegate URI
    	URI delegateURI=m_baseURICache.get(m_baseIRI);
    	//TODO 28% object creation here
    	// resolve against delegate
        return IRI.create(delegateURI.resolve(value));
    }
    
    /**
     * Processes xml:base reference if there is one.
     * @param atts the attributes potentially containing xml:base declaration
     */
    protected void processXMLBase(Attributes atts) throws SAXException {
        m_baseIRIs.add(0,m_baseIRI);
        String value = atts.getValue(XMLNS, "base");
        if (value != null) {
            try {
                m_baseIRI = resolveFromDelegate(m_baseIRI, value);
                resolvedIRIs.clear();
            }
            catch (IllegalArgumentException e) {
                RDFParserException exception = new RDFParserException("New base IRI '" + value + "' cannot be resolved against curent base IRI " + m_baseIRI.toString(),
                                                                      m_documentLocator);
                exception.initCause(e);
                throw exception;
            }
        }
    }


    /**
     * Processes xml:language reference is there is one.
     * @param atts the attributes potentially containing xml:language declaration
     */
    protected void processXMLLanguage(Attributes atts) {
        m_languages.add(0, m_language);
        String value = atts.getValue(XMLLANG);
        if (value != null)
            m_language = value;
    }

    private int cacheHits = 0;

    /**
     * Resolves an IRI with the current base.
     * @param uri the IRI being resolved
     * @return the resolved IRI
     */
    protected String resolveIRI(String uri) throws SAXException {
        if (uri.length() == 0) {
            // MH - Fix for resolving a "This document" reference against base IRIs.
            String base = m_baseIRI.toString();
            int hashIndex = base.indexOf("#");
            if (hashIndex != -1) {
                return base.substring(0, hashIndex);
            }
            else {
                return base;
            }
        }
        else {
            try {
                String resolved = resolvedIRIs.get(uri);
                if(resolved != null) {
//                    cacheHits++;
//                    if((cacheHits % 10000) == 0) {
//                        System.out.println(cacheHits + " cache hits");
//                    }
                    return resolved;
                }
                else {
                    IRI theIRI = resolveFromDelegate(m_baseIRI, uri);
                    String u = theIRI.toString();
                    uriCache.put(u, theIRI);
                    resolvedIRIs.put(uri, u);
                    return u;  
                }
            }
            catch (IllegalArgumentException e) {
                RDFParserException exception = new RDFParserException("IRI '" + uri + "' cannot be resolved against curent base IRI " + m_baseIRI.toString(),
                                                                      m_documentLocator);
                exception.initCause(e);
                throw exception;
            }
        }
    }


    /**
     * Returns an absolute IRI from an ID.
     */
    protected String getIRIFromID(String id) throws SAXException {
        return resolveIRI("#" + id);
    }


    /**
     * Returns an absolute IRI from an about attribute.
     */
    protected String getIRIFromAbout(String about) throws SAXException {
        return resolveIRI(about);
    }


    /**
     * Returns an abolute IRI from a nodeID attribute.
     */
    protected String getIRIFromNodeID(String nodeID) throws SAXException {
//        return nodeID;
        return resolveIRI("#genid-nodeid-" + nodeID);
    }


    /**
     * Returns an absolute IRI from a resource attribute.
     */
    protected String getIRIFromResource(String resource) throws SAXException {
        return resolveIRI(resource);
    }


    /**
     * Generates next anonymous IRI.
     */
    protected String nextAnonymousIRI() throws SAXException {
        return getIRIFromID("genid" + (++m_generatedIRIIndex));
    }


    /**
     * Extracts the IRI of the resource from rdf:ID, rdf:nodeID or rdf:about attribute. If no attribute is found,
     * an IRI is generated.
     */
    protected String getIDNodeIDAboutResourceIRI(Attributes atts) throws SAXException {
        String result = null;
        String value = atts.getValue(RDFNS, ATTR_ID);
        if (value != null)
            result = getIRIFromID(value);
        value = atts.getValue(RDFNS, ATTR_ABOUT);
        if (value != null) {
            if (result != null)
                throw new RDFParserException("Element cannot specify both rdf:ID and rdf:about attributes.",
                                             m_documentLocator);
            result = getIRIFromAbout(value);
        }
        value = atts.getValue(RDFNS, ATTR_NODE_ID);
        if (value != null) {
            if (result != null)
                throw new RDFParserException(
                        "Element cannot specify both rdf:nodeID and rdf:ID or rdf:about attributes.",
                        m_documentLocator);
            result = getIRIFromNodeID(value);
        }
        if (result == null)
            result = nextAnonymousIRI();
        return result;
    }


    /**
     * Extracts the IRI of the resource from rdf:resource or rdf:nodeID attribute. If no attribute is found, <code>null</code> is returned.
     * @param atts the attributes
     * @return the IRI of the resource or <code>null</code>
     */
    protected String getNodeIDResourceResourceIRI(Attributes atts) throws SAXException {
        String value = atts.getValue(RDFNS, ATTR_RESOURCE);
        if (value != null)
            return getIRIFromResource(value);
        else {
            value = atts.getValue(RDFNS, ATTR_NODE_ID);
            if (value != null)
                return getIRIFromNodeID(value);
            else
                return null;
        }
    }


    /**
     * Called when a statement with resource value is added to the model.
     * @param subject       IRI of the subject resource
     * @param predicate     IRI of the predicate resource
     * @param object        IRI of the object resource
     * @param reificationID if not <code>null</code>, contains IRI of the resource that will wold the reified statement
     */
    protected void statementWithResourceValue(String subject, String predicate, String object,
                                              String reificationID) throws SAXException {
        m_consumer.statementWithResourceValue(subject, predicate, object);
        if (reificationID != null) {
            m_consumer.statementWithResourceValue(reificationID, RDF_TYPE, RDF_STATEMENT);
            m_consumer.statementWithResourceValue(reificationID, RDF_SUBJECT, subject);
            m_consumer.statementWithResourceValue(reificationID, RDF_PREDICATE, predicate);
            m_consumer.statementWithResourceValue(reificationID, RDF_OBJECT, object);
        }
    }


    /**
     * Called when a statement with literal value is added to the model.
     * @param subject       IRI of the subject resource
     * @param predicate     IRI of the predicate resource
     * @param object        literal object value
     * @param dataType      the IRI of the literal's datatype (may be <code>null</code>)
     * @param reificationID if not <code>null</code>, contains IRI of the resource that will wold the reified statement
     */
    protected void statementWithLiteralValue(String subject, String predicate, String object, String dataType,
                                             String reificationID) throws SAXException {
        m_consumer.statementWithLiteralValue(subject, predicate, object, m_language, dataType);
        if (reificationID != null) {
            m_consumer.statementWithResourceValue(reificationID, RDF_TYPE, RDF_STATEMENT);
            m_consumer.statementWithResourceValue(reificationID, RDF_SUBJECT, subject);
            m_consumer.statementWithResourceValue(reificationID, RDF_PREDICATE, predicate);
            m_consumer.statementWithLiteralValue(reificationID, RDF_OBJECT, object, m_language, dataType);
        }
    }


    /**
     * Parses the propertyAttributes production.
     * @param subjectIRI         IRI of the resource whose properties are being parsed
     * @param atts               attributes
     * @param reificationManager the reification manager
     */
    protected void propertyAttributes(String subjectIRI, Attributes atts, ReificationManager reificationManager) throws
                                                                                                                 SAXException {
        int length = atts.getLength();
        for (int i = 0; i < length; i++) {
            String nsIRI = atts.getURI(i);
            String localName = atts.getLocalName(i);
            if (!XMLNS.equals(nsIRI) && !XMLLANG.equals(localName) && !(RDFNS.equals(nsIRI) && (ATTR_ID.equals(localName) || ATTR_NODE_ID.equals(
                    localName) || ATTR_ABOUT.equals(localName) || ELT_TYPE.equals(localName) || ATTR_RESOURCE.equals(
                    localName) || ATTR_PARSE_TYPE.equals(localName) || ATTR_ABOUT_EACH.equals(localName) || ATTR_ABOUT_EACH_PREFIX.equals(
                    localName) || ATTR_BAG_ID.equals(localName)))) {
                String value = atts.getValue(i);
                String reificationID = reificationManager.getReificationID(null);
                statementWithLiteralValue(subjectIRI, nsIRI + localName, value, null, reificationID);
            }
            else if (RDFNS.equals(nsIRI) && ELT_TYPE.equals(localName)) {
                String value = resolveIRI(atts.getValue(i));
                String reificationID = reificationManager.getReificationID(null);
                statementWithResourceValue(subjectIRI, nsIRI + localName, value, reificationID);
            }
        }
    }


    /**
     * Checks whether given characters contain only whitespace.
     * @param data   the data being checked
     * @param start  the start index (inclusive)
     * @param length the end index (non-inclusive)
     * @return <code>true</code> if characters contain whitespace
     */
    protected boolean isWhitespaceOnly(char[] data, int start, int length) {
        int end = start + length;
        for (int i = start; i < end; i++) {
            char c = data[i];
            if (c != '\n' && c != '\r' && c != '\t' && c != ' ')
                return false;
        }
        return true;
    }



    /**
     * Checks whether given characters contain only whitespace.
     * @param buffer the data being checked
     * @return <code>true</code> if characters contain whitespace
     */
    protected boolean isWhitespaceOnly(StringBuilder buffer) {
        for (int i = 0; i < buffer.length(); i++) {
            char c = buffer.charAt(i);
            if (c != '\n' && c != '\r' && c != '\t' && c != ' ')
                return false;
        }
        return true;
    }


    /**
     * Returns the reification manager for given attributes.
     * @param atts the attributes
     * @return the reification manager
     */
    protected ReificationManager getReificationManager(Attributes atts) throws SAXException {
        String bagIDAttr = atts.getValue(RDFNS, ATTR_BAG_ID);
        if (bagIDAttr == null)
            return ReificationManager.INSTANCE;
        else {
            String bagID = getIRIFromID(bagIDAttr);
            return new ReifiedStatementBag(bagID);
        }
    }


    /**
     * Parses the string into a map of name-value pairs.
     * @param string string to be parsed
     * @return map of name-value pairs
     * @throws SAXException if there was an IOException this will be wrapped in a parse exception
     */
    protected Map<String, String> parseStringArguments(String string) throws SAXException {
        try {
            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(string));
            Map<String, String> result = new HashMap<String, String>();
            tokenizer.nextToken();
            while (tokenizer.ttype != StreamTokenizer.TT_EOF) {
                if (tokenizer.ttype != StreamTokenizer.TT_WORD)
                    throw new RDFParserException("Invalid processing instruction argument.", m_documentLocator);
                String name = tokenizer.sval;
                if ('=' != tokenizer.nextToken())
                    throw new RDFParserException("Expecting token =", m_documentLocator);
                tokenizer.nextToken();
                if (tokenizer.ttype != '\"' && tokenizer.ttype != '\'')
                    throw new RDFParserException("Invalid processing instruction argument.", m_documentLocator);
                String value = tokenizer.sval;
                result.put(name, value);
                tokenizer.nextToken();
            }
            return result;
        }
        catch (IOException e) {
            RDFParserException exception = new RDFParserException("I/O error", m_documentLocator);
            exception.initCause(e);
            throw exception;
        }
    }


    /**
     * Tests whether supplied IRI was generated by this parser in order to label an anonymous node.
     * @param uri the IRI
     * @return <code>true</code> if the IRI was generated by this parser to label an anonymous node
     */
    public boolean isAnonymousNodeIRI(String uri) {
        return uri != null && uri.indexOf("genid") != -1;
    }

    public boolean isAnonymousNodeID(String iri) {
        return iri != null && iri.indexOf("genid-nodeid") != -1;
    }

    public IRI getIRI(String s) {
        return uriCache.get(s);
//        throw new RuntimeException("DISABLED");
    }


    /**
     * Base class for all parser states.
     */
    protected static class State {

        public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
        }


        public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
        }


        public void characters(char[] data, int start, int length) throws SAXException {
        }
    }


    /**
     * State expecting start of RDF text.
     */
    protected class StartRDF extends State {

        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (!RDFNS.equals(namespaceIRI) || !ELT_RDF.equals(localName))
                throw new RDFParserException("Expecting rdf:RDF element.", m_documentLocator);
            // the logical IRI is the current IRI that we have as the base IRI at this point
            m_consumer.logicalURI(m_baseIRI.toString());
            pushState(new NodeElementList());
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            popState();
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Expecting rdf:rdf element instead of character content.",
                                             m_documentLocator);
        }
    }


    /**
     * Parses the nodeElementList production.
     */
    protected class NodeElementList extends State {

        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            pushState(new NodeElement());
            m_state.startElement(namespaceIRI, localName, qName, atts);
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            popState();
            m_state.endElement(namespaceIRI, localName, qName);
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Expecting an object element instead of character content.",
                                             m_documentLocator);
        }
    }


    /**
     * Parses the nodeElement production.
     */
    protected class NodeElement extends State {

        protected String m_subjectIRI;

        protected ReificationManager m_reificationManager;

        protected int m_nextLi = 1;


        public void startDummyElement(Attributes atts) throws SAXException {
            m_subjectIRI = nextAnonymousIRI();
            m_reificationManager = getReificationManager(atts);
        }


        public String getSubjectIRI() {
            return m_subjectIRI;
        }


        public String getReificationID(Attributes atts) throws SAXException {
            String rdfID = atts.getValue(RDFNS, ATTR_ID);
            if (rdfID != null)
                rdfID = getIRIFromID(rdfID);
            return m_reificationManager.getReificationID(rdfID);
        }


        public String getNextLi() {
            return RDFNS + "_" + (m_nextLi++);
        }


        public String getPropertyIRI(String uri) {
            if (RDF_LI.equals(uri))
                return getNextLi();
            else
                return uri;
        }


        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            m_subjectIRI = getIDNodeIDAboutResourceIRI(atts);
            boolean isRDFNS = RDFNS.equals(namespaceIRI);
            m_reificationManager = getReificationManager(atts);
            if (!isRDFNS || !ELT_DESCRIPTION.equals(localName))
                statementWithResourceValue(m_subjectIRI,
                                           RDF_TYPE,
                                           namespaceIRI + localName,
                                           m_reificationManager.getReificationID(null));
            checkUnsupportedAttributes(atts);
            propertyAttributes(m_subjectIRI, atts, m_reificationManager);
            pushState(new PropertyElementList(this));
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            popState();
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Cannot answer characters when node is excepted.", m_documentLocator);
        }
    }


    /**
     * Parses the propertyEltList production. The contents of the startElement method implements also the propertyElt production.
     */
    protected class PropertyElementList extends State {

        protected NodeElement m_nodeElement;


        public PropertyElementList(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            String parseType = atts.getValue(RDFNS, ATTR_PARSE_TYPE);
            if (PARSE_TYPE_LITERAL.equals(parseType))
                pushState(new ParseTypeLiteralPropertyElement(m_nodeElement));
            else if (PARSE_TYPE_RESOURCE.equals(parseType))
                pushState(new ParseTypeResourcePropertyElement(m_nodeElement));
            else if (PARSE_TYPE_COLLECTION.equals(parseType))
                pushState(new ParseTypeCollectionPropertyElement(m_nodeElement));
            else if (parseType != null)
                pushState(new ParseTypeLiteralPropertyElement(m_nodeElement));
            else {
                String objectIRI = getNodeIDResourceResourceIRI(atts);
                if (objectIRI != null)
                    pushState(new EmptyPropertyElement(m_nodeElement));
                else
                    pushState(new ResourceOrLiteralPropertyElement(m_nodeElement));
            }
            m_state.startElement(namespaceIRI, localName, qName, atts);
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            popState();
            m_state.endElement(namespaceIRI, localName, qName);
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Cannot answer characters when object properties are excepted.",
                                             m_documentLocator);
        }
    }


    /**
     * Parses resourcePropertyElt or literalPropertyElt productions. m_text is <code>null</code> when
     * startElement is expected on the actual property element.
     */
    protected class ResourceOrLiteralPropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyIRI;

        protected String m_reificationID;

        protected String m_datatype;

        protected StringBuilder m_text;

        protected NodeElement m_innerNode;


        public ResourceOrLiteralPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_text == null) {
                // this is the invocation on the outer element
                m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
                m_reificationID = m_nodeElement.getReificationID(atts);
                m_datatype = atts.getValue(RDFNS, ATTR_DATATYPE);
                m_text = new StringBuilder();
            }
            else {
                if (!isWhitespaceOnly(m_text))
                    throw new RDFParserException("Text was seen and new node is started.", m_documentLocator);
                if (m_datatype != null)
                    throw new RDFParserException("rdf:datatype specified on a node with resource value.",
                                                 m_documentLocator);
                m_innerNode = new NodeElement();
                pushState(m_innerNode);
                m_state.startElement(namespaceIRI, localName, qName, atts);
            }
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            if (m_innerNode != null)
                statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                                           m_propertyIRI,
                                           m_innerNode.getSubjectIRI(),
                                           m_reificationID);
            else
                statementWithLiteralValue(m_nodeElement.getSubjectIRI(),
                                          m_propertyIRI,
                                          //TODO 33% of object creations here
                                          m_text.toString(),
                                          m_datatype,
                                          m_reificationID);
            popState();
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            if (m_innerNode != null) {
                if (!isWhitespaceOnly(data, start, length))
                    throw new RDFParserException("Cannot answer characters when object properties are excepted.",
                                                 m_documentLocator);
            }
            else
                m_text.append(data, start, length);
        }
    }


    /**
     * Parses emptyPropertyElt production.
     */
    protected class EmptyPropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyIRI;


        public EmptyPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_propertyIRI == null) {
                // this is the invocation on the outer element
                m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
                String reificationID = m_nodeElement.getReificationID(atts);
                String objectIRI = getNodeIDResourceResourceIRI(atts);
                if (objectIRI == null)
                    objectIRI = nextAnonymousIRI();
                statementWithResourceValue(m_nodeElement.getSubjectIRI(), m_propertyIRI, objectIRI, reificationID);
                ReificationManager reificationManager = getReificationManager(atts);
                propertyAttributes(objectIRI, atts, reificationManager);
            }
            else
                throw new RDFParserException("incorrect element start encountered.", m_documentLocator);
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            popState();
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            throw new RDFParserException("Characters were not excepted.", m_documentLocator);
        }
    }


    /**
     * Parses parseTypeCollectionPropertyElt production.
     */
    protected class ParseTypeCollectionPropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyIRI;

        protected String m_reificationID;

        protected String m_lastCellIRI;


        public ParseTypeCollectionPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_propertyIRI == null) {
                m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
                m_reificationID = m_nodeElement.getReificationID(atts);
            }
            else {
                NodeElement collectionNode = new NodeElement();
                pushState(collectionNode);
                m_state.startElement(namespaceIRI, localName, qName, atts);
                String newListCellIRI = listCell(collectionNode.getSubjectIRI());
                if (m_lastCellIRI == null)
                    statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                                               m_propertyIRI,
                                               newListCellIRI,
                                               m_reificationID);
                else
                    statementWithResourceValue(m_lastCellIRI, RDF_REST, newListCellIRI, null);
                m_lastCellIRI = newListCellIRI;
            }
        }


        protected String listCell(String valueIRI) throws SAXException {
            String listCellIRI = nextAnonymousIRI();
            statementWithResourceValue(listCellIRI, RDF_FIRST, valueIRI, null);
            statementWithResourceValue(listCellIRI, RDF_TYPE, RDF_LIST, null);
            return listCellIRI;
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            if (m_lastCellIRI == null)
                statementWithResourceValue(m_nodeElement.getSubjectIRI(), m_propertyIRI, RDF_NIL, m_reificationID);
            else
                statementWithResourceValue(m_lastCellIRI, RDF_REST, RDF_NIL, null);
            popState();
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Expecting an object element instead of character content.",
                                             m_documentLocator);
        }
    }


    /**
     * Parses parseTypeLiteralPropertyElt production.
     */
    protected class ParseTypeLiteralPropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyIRI;

        protected String m_reificationID;

        protected int m_depth;

        protected StringBuilder m_content;


        public ParseTypeLiteralPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_depth == 0) {
                m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
                m_reificationID = m_nodeElement.getReificationID(atts);
                m_content = new StringBuilder();
            }
            else {
                m_content.append('<');
                m_content.append(qName);
                int length = atts.getLength();
                for (int i = 0; i < length; i++) {
                    m_content.append(' ');
                    m_content.append(atts.getQName(i));
                    m_content.append("=\"");
                    m_content.append(atts.getValue(i));
                    m_content.append("\"");
                }
                m_content.append(">");
            }
            m_depth++;
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            if (m_depth == 1) {
                statementWithLiteralValue(m_nodeElement.getSubjectIRI(),
                                          m_propertyIRI,
                                          m_content.toString(),
                                          RDF_XMLLITERAL,
                                          m_reificationID);
                popState();
            }
            else {
                m_content.append("</");
                m_content.append(qName);
                m_content.append(">");
            }
            m_depth--;
        }


        @Override
		public void characters(char[] data, int start, int length) {
            m_content.append(data, start, length);
        }
    }


    /**
     * Parses parseTypeResourcePropertyElt production.
     */
    protected class ParseTypeResourcePropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyIRI;

        protected String m_reificationID;


        public ParseTypeResourcePropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        @Override
		public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
            m_reificationID = m_nodeElement.getReificationID(atts);
            NodeElement anonymousNodeElement = new NodeElement();
            anonymousNodeElement.startDummyElement(atts);
            statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                                       m_propertyIRI,
                                       anonymousNodeElement.getSubjectIRI(),
                                       m_reificationID);
            pushState(new PropertyElementList(anonymousNodeElement));
        }


        @Override
		public void endElement(String namespaceIRI, String localName, String qName) throws SAXException {
            popState();
        }


        @Override
		public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Cannot answer characters when object properties are excepted.",
                                             m_documentLocator);
        }
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
            if (reificationID == null)
                resultIRI = nextAnonymousIRI();
            else
                resultIRI = reificationID;
            statementWithResourceValue(m_uri, RDFNS + "_" + (++m_elements), resultIRI, null);
            return resultIRI;
        }
    }
}
