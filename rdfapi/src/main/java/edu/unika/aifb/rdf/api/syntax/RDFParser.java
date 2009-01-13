package edu.unika.aifb.rdf.api.syntax;

import edu.unika.aifb.rdf.api.util.RDFConstants;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.LocatorImpl;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * This class parses the RDF according to the syntax specified in <a href="http://www.w3.org/TR/rdf-syntax-grammar/">http://www.w3.org/TR/rdf-syntax-grammar/</a>.
 */
public class RDFParser extends DefaultHandler implements RDFConstants {

    protected static final Locator s_nullDocumentLocator = new LocatorImpl();

    protected static final SAXParserFactory s_parserFactory = SAXParserFactory.newInstance();


    static {
        s_parserFactory.setNamespaceAware(true);
    }


    /**
     * Registered error handler.
     */
    protected ErrorHandler m_errorHandler;

    /**
     * Stack of base URIs.
     */
    protected Stack m_baseURIs;

    /**
     * URI of the document being parsed.
     */
    protected URI m_baseURI;

    /**
     * The stack of languages.
     */
    protected Stack m_languages;

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
    protected List m_states;

    /**
     * Number of the last generated URI.
     */
    protected int m_generatedURIIndex;

    /**
     * Document locator.
     */
    protected Locator m_documentLocator;


    /**
     * Creates a RDF parser.
     */
    public RDFParser() {
        m_states = new ArrayList();
        m_baseURIs = new Stack();
        m_languages = new Stack();
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
                m_baseURI = new URI(source.getSystemId());
            else
                throw new SAXException(
                        "Supplied InputSource object myst have systemId property set, which is needed for URI resolution.");
            m_consumer = consumer;
            m_consumer.startModel(m_baseURI.toString());
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
            m_baseURIs.clear();
        }
    }


    /**
     * Called to receive a document locator.
     * @param locator the document locator
     */
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
    public void fatalError(SAXParseException e) throws SAXException {
        if (m_errorHandler == null)
            super.fatalError(e);
        else
            m_errorHandler.fatalError(e);
    }


    /**
     * Called when document parsing is started.
     */
    public void startDocument() {
        m_generatedURIIndex = 0;
        m_states.clear();
        pushState(new StartRDF());
    }


    /**
     * Called when document parsing is ended.
     */
    public void endDocument() throws SAXException {
        if (m_state != null)
            throw new RDFParserException("RDF content not finished.", m_documentLocator);
    }


    /**
     * Called when an element is started.
     * @param namespaceURI the URI of the namespace
     * @param localName    the local name of the element
     * @param qName        the Q-name of the element
     * @param atts         the attributes
     */
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        processXMLBase(atts);
        processXMLLanguage(atts);
        m_state.startElement(namespaceURI, localName, qName, atts);
    }


    /**
     * Called when element parsing is ended.
     * @param namespaceURI the URI of the namespace
     * @param localName    the local name of the element
     * @param qName        the Q-name of the element
     */
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        m_state.endElement(namespaceURI, localName, qName);
        m_baseURI = (URI) m_baseURIs.pop();
        m_language = (String) m_languages.pop();
    }


    /**
     * Called when character content is parsed.
     * @param data   the data buffer containing the characters
     * @param start  the start index of character text
     * @param length the length of the character text
     */
    public void characters(char[] data, int start, int length) throws SAXException {
        m_state.characters(data, start, length);
    }


    /**
     * Called when processing instruction is parsed.
     * @param target the name of the processing instruction
     * @param data   the argument to the processing instruction
     */
    public void processingInstruction(String target, String data) throws SAXException {
        if ("include-rdf".equals(target)) {
            Map arguments = parseStringArguments(data);
            if (arguments.size() > 2)
                throw new RDFParserException("Incorrect number of arguments for 'include-rdf' processing instruction.",
                                             m_documentLocator);
            String logicalURI = (String) arguments.get("logicalURI");
            String physicalURI = (String) arguments.get("physicalURI");
            if (physicalURI != null)
                physicalURI = resolveURI(physicalURI);
            m_consumer.includeModel(logicalURI, physicalURI);
        }
        else if ("model-attribute".equals(target)) {
            Map arguments = parseStringArguments(data);
            if (arguments.size() != 2)
                throw new RDFParserException(
                        "Incorrect number of arguments for 'model-attribute' processing instruction.",
                        m_documentLocator);
            String key = (String) arguments.get("key");
            if (key == null)
                throw new RDFParserException("Mising the 'key' argument for 'model-attribute' processing instruction.",
                                             m_documentLocator);
            String value = (String) arguments.get("value");
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
            m_state = (State) m_states.get(size - 2);
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


    /**
     * Processes xml:base reference if there is one.
     * @param atts the attributes potentially containing xml:base declaration
     */
    protected void processXMLBase(Attributes atts) throws SAXException {
        m_baseURIs.push(m_baseURI);
        String value = atts.getValue(XMLNS, "base");
        if (value != null) {
            try {
                m_baseURI = m_baseURI.resolve(value);
            }
            catch (IllegalArgumentException e) {
                RDFParserException exception = new RDFParserException("New base URI '" + value + "' cannot be resolved against curent base URI " + m_baseURI.toString(),
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
        m_languages.push(m_language);
        String value = atts.getValue(XMLLANG);
        if (value != null)
            m_language = value;
    }


    /**
     * Resolves an URI with the current base.
     * @param uri the URI being resolved
     * @return the resolved URI
     */
    protected String resolveURI(String uri) throws SAXException {
        if (uri.length() == 0) {
            // MH - Fix for resolving a "This document" reference against base URIs.
            String base = m_baseURI.toString();
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
                return m_baseURI.resolve(uri).toString();
            }
            catch (IllegalArgumentException e) {
                RDFParserException exception = new RDFParserException("URI '" + uri + "' cannot be resolved against curent base URI " + m_baseURI.toString(),
                                                                      m_documentLocator);
                exception.initCause(e);
                throw exception;
            }
        }
    }


    /**
     * Returns an absolute URI from an ID.
     */
    protected String getURIFromID(String id) throws SAXException {
        return resolveURI("#" + id);
    }


    /**
     * Returns an absolute URI from an about attribute.
     */
    protected String getURIFromAbout(String about) throws SAXException {
        return resolveURI(about);
    }


    /**
     * Returns an abolute URI from a nodeID attribute.
     */
    protected String getURIFromNodeID(String nodeID) throws SAXException {
        return resolveURI("#genid-" + nodeID);
    }


    /**
     * Returns an absolute URI from a resource attribute.
     */
    protected String getURIFromResource(String resource) throws SAXException {
        return resolveURI(resource);
    }


    /**
     * Generates next anonymous URI.
     */
    protected String nextAnonymousURI() throws SAXException {
        return getURIFromID("genid" + (++m_generatedURIIndex));
    }


    /**
     * Extracts the URI of the resource from rdf:ID, rdf:nodeID or rdf:about attribute. If no attribute is found,
     * an URI is generated.
     */
    protected String getIDNodeIDAboutResourceURI(Attributes atts) throws SAXException {
        String result = null;
        String value = atts.getValue(RDFNS, ATTR_ID);
        if (value != null)
            result = getURIFromID(value);
        value = atts.getValue(RDFNS, ATTR_ABOUT);
        if (value != null) {
            if (result != null)
                throw new RDFParserException("Element cannot specify both rdf:ID and rdf:about attributes.",
                                             m_documentLocator);
            result = getURIFromAbout(value);
        }
        value = atts.getValue(RDFNS, ATTR_NODE_ID);
        if (value != null) {
            if (result != null)
                throw new RDFParserException(
                        "Element cannot specify both rdf:nodeID and rdf:ID or rdf:about attributes.",
                        m_documentLocator);
            result = getURIFromNodeID(value);
        }
        if (result == null)
            result = nextAnonymousURI();
        return result;
    }


    /**
     * Extracts the URI of the resource from rdf:resource or rdf:nodeID attribute. If no attribute is found, <code>null</code> is returned.
     * @param atts the attributes
     * @return the URI of the resource or <code>null</code>
     */
    protected String getNodeIDResourceResourceURI(Attributes atts) throws SAXException {
        String value = atts.getValue(RDFNS, ATTR_RESOURCE);
        if (value != null)
            return getURIFromResource(value);
        else {
            value = atts.getValue(RDFNS, ATTR_NODE_ID);
            if (value != null)
                return getURIFromNodeID(value);
            else
                return null;
        }
    }


    /**
     * Called when a statement with resource value is added to the model.
     * @param subject       URI of the subject resource
     * @param predicate     URI of the predicate resource
     * @param object        URI of the object resource
     * @param reificationID if not <code>null</code>, contains URI of the resource that will wold the reified statement
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
     * @param subject       URI of the subject resource
     * @param predicate     URI of the predicate resource
     * @param object        literal object value
     * @param dataType      the URI of the literal's datatype (may be <code>null</code>)
     * @param reificationID if not <code>null</code>, contains URI of the resource that will wold the reified statement
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
     * @param subjectURI         URI of the resource whose properties are being parsed
     * @param atts               attributes
     * @param reificationManager the reification manager
     */
    protected void propertyAttributes(String subjectURI, Attributes atts, ReificationManager reificationManager) throws
                                                                                                                 SAXException {
        int length = atts.getLength();
        for (int i = 0; i < length; i++) {
            String nsURI = atts.getURI(i);
            String localName = atts.getLocalName(i);
            if (!XMLNS.equals(nsURI) && !XMLLANG.equals(localName) && !(RDFNS.equals(nsURI) && (ATTR_ID.equals(localName) || ATTR_NODE_ID.equals(
                    localName) || ATTR_ABOUT.equals(localName) || ELT_TYPE.equals(localName) || ATTR_RESOURCE.equals(
                    localName) || ATTR_PARSE_TYPE.equals(localName) || ATTR_ABOUT_EACH.equals(localName) || ATTR_ABOUT_EACH_PREFIX.equals(
                    localName) || ATTR_BAG_ID.equals(localName)))) {
                String value = atts.getValue(i);
                String reificationID = reificationManager.getReificationID(null);
                statementWithLiteralValue(subjectURI, nsURI + localName, value, null, reificationID);
            }
            else if (RDFNS.equals(nsURI) && ELT_TYPE.equals(localName)) {
                String value = resolveURI(atts.getValue(i));
                String reificationID = reificationManager.getReificationID(null);
                statementWithResourceValue(subjectURI, nsURI + localName, value, reificationID);
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
    protected boolean isWhitespaceOnly(StringBuffer buffer) {
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
            String bagID = getURIFromID(bagIDAttr);
            return new ReifiedStatementBag(bagID);
        }
    }


    /**
     * Parses the string into a map of name-value pairs.
     * @param string string to be parsed
     * @return map of name-value pairs
     */
    protected Map parseStringArguments(String string) throws SAXException {
        try {
            StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(string));
            Map result = new HashMap();
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
     * Tests whether supplied URI was generated by this parser in order to label an anonymous node.
     * @param uri the URI
     * @return <code>true</code> if the URI was generated by this parser to label an anonymous node
     */
    public boolean isAnonymousNodeURI(String uri) {
        return uri.indexOf("#genid") != -1;
    }


    /**
     * Base class for all parser states.
     */
    protected static class State {

        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        }


        public void characters(char[] data, int start, int length) throws SAXException {
        }
    }


    /**
     * State expecting start of RDF text.
     */
    protected class StartRDF extends State {

        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (!RDFNS.equals(namespaceURI) || !ELT_RDF.equals(localName))
                throw new RDFParserException("Expecting rdf:RDF element.", m_documentLocator);
            // the logical URI is the current URI that we have as the base URI at this point
            m_consumer.logicalURI(m_baseURI.toString());
            pushState(new NodeElementList());
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            popState();
        }


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

        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            pushState(new NodeElement());
            m_state.startElement(namespaceURI, localName, qName, atts);
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            popState();
            m_state.endElement(namespaceURI, localName, qName);
        }


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

        protected String m_subjectURI;

        protected ReificationManager m_reificationManager;

        protected int m_nextLi = 1;


        public void startDummyElement(Attributes atts) throws SAXException {
            m_subjectURI = nextAnonymousURI();
            m_reificationManager = getReificationManager(atts);
        }


        public String getSubjectURI() {
            return m_subjectURI;
        }


        public String getReificationID(Attributes atts) throws SAXException {
            String rdfID = atts.getValue(RDFNS, ATTR_ID);
            if (rdfID != null)
                rdfID = getURIFromID(rdfID);
            return m_reificationManager.getReificationID(rdfID);
        }


        public String getNextLi() {
            return RDFNS + "_" + (m_nextLi++);
        }


        public String getPropertyURI(String uri) {
            if (RDF_LI.equals(uri))
                return getNextLi();
            else
                return uri;
        }


        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            m_subjectURI = getIDNodeIDAboutResourceURI(atts);
            boolean isRDFNS = RDFNS.equals(namespaceURI);
            m_reificationManager = getReificationManager(atts);
            if (!isRDFNS || !ELT_DESCRIPTION.equals(localName))
                statementWithResourceValue(m_subjectURI,
                                           RDF_TYPE,
                                           namespaceURI + localName,
                                           m_reificationManager.getReificationID(null));
            checkUnsupportedAttributes(atts);
            propertyAttributes(m_subjectURI, atts, m_reificationManager);
            pushState(new PropertyElementList(this));
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            popState();
        }


        public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Cannot process characters when node is excepted.", m_documentLocator);
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


        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
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
                String objectURI = getNodeIDResourceResourceURI(atts);
                if (objectURI != null)
                    pushState(new EmptyPropertyElement(m_nodeElement));
                else
                    pushState(new ResourceOrLiteralPropertyElement(m_nodeElement));
            }
            m_state.startElement(namespaceURI, localName, qName, atts);
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            popState();
            m_state.endElement(namespaceURI, localName, qName);
        }


        public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Cannot process characters when object properties are excepted.",
                                             m_documentLocator);
        }
    }


    /**
     * Parses resourcePropertyElt or literalPropertyElt productions. m_text is <code>null</code> when
     * startElement is expected on the actual property element.
     */
    protected class ResourceOrLiteralPropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyURI;

        protected String m_reificationID;

        protected String m_datatype;

        protected StringBuffer m_text;

        protected NodeElement m_innerNode;


        public ResourceOrLiteralPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_text == null) {
                // this is the invocation on the outer element
                m_propertyURI = m_nodeElement.getPropertyURI(namespaceURI + localName);
                m_reificationID = m_nodeElement.getReificationID(atts);
                m_datatype = atts.getValue(RDFNS, ATTR_DATATYPE);
                m_text = new StringBuffer();
            }
            else {
                if (!isWhitespaceOnly(m_text))
                    throw new RDFParserException("Text was seen and new node is started.", m_documentLocator);
                if (m_datatype != null)
                    throw new RDFParserException("rdf:datatype specified on a node with resource value.",
                                                 m_documentLocator);
                m_innerNode = new NodeElement();
                pushState(m_innerNode);
                m_state.startElement(namespaceURI, localName, qName, atts);
            }
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            if (m_innerNode != null)
                statementWithResourceValue(m_nodeElement.getSubjectURI(),
                                           m_propertyURI,
                                           m_innerNode.getSubjectURI(),
                                           m_reificationID);
            else
                statementWithLiteralValue(m_nodeElement.getSubjectURI(),
                                          m_propertyURI,
                                          m_text.toString().trim(),
                                          m_datatype,
                                          m_reificationID);
            popState();
        }


        public void characters(char[] data, int start, int length) throws SAXException {
            if (m_innerNode != null) {
                if (!isWhitespaceOnly(data, start, length))
                    throw new RDFParserException("Cannot process characters when object properties are excepted.",
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

        protected String m_propertyURI;


        public EmptyPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_propertyURI == null) {
                // this is the invocation on the outer element
                m_propertyURI = m_nodeElement.getPropertyURI(namespaceURI + localName);
                String reificationID = m_nodeElement.getReificationID(atts);
                String objectURI = getNodeIDResourceResourceURI(atts);
                if (objectURI == null)
                    objectURI = nextAnonymousURI();
                statementWithResourceValue(m_nodeElement.getSubjectURI(), m_propertyURI, objectURI, reificationID);
                ReificationManager reificationManager = getReificationManager(atts);
                propertyAttributes(objectURI, atts, reificationManager);
            }
            else
                throw new RDFParserException("incorrect element start encountered.", m_documentLocator);
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            popState();
        }


        public void characters(char[] data, int start, int length) throws SAXException {
            throw new RDFParserException("Characters were not excepted.", m_documentLocator);
        }
    }


    /**
     * Parses parseTypeCollectionPropertyElt production.
     */
    protected class ParseTypeCollectionPropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyURI;

        protected String m_reificationID;

        protected String m_lastCellURI;


        public ParseTypeCollectionPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_propertyURI == null) {
                m_propertyURI = m_nodeElement.getPropertyURI(namespaceURI + localName);
                m_reificationID = m_nodeElement.getReificationID(atts);
            }
            else {
                NodeElement collectionNode = new NodeElement();
                pushState(collectionNode);
                m_state.startElement(namespaceURI, localName, qName, atts);
                String newListCellURI = listCell(collectionNode.getSubjectURI());
                if (m_lastCellURI == null)
                    statementWithResourceValue(m_nodeElement.getSubjectURI(),
                                               m_propertyURI,
                                               newListCellURI,
                                               m_reificationID);
                else
                    statementWithResourceValue(m_lastCellURI, RDF_REST, newListCellURI, null);
                m_lastCellURI = newListCellURI;
            }
        }


        protected String listCell(String valueURI) throws SAXException {
            String listCellURI = nextAnonymousURI();
            statementWithResourceValue(listCellURI, RDF_FIRST, valueURI, null);
            statementWithResourceValue(listCellURI, RDF_TYPE, RDF_LIST, null);
            return listCellURI;
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            if (m_lastCellURI == null)
                statementWithResourceValue(m_nodeElement.getSubjectURI(), m_propertyURI, RDF_NIL, m_reificationID);
            else
                statementWithResourceValue(m_lastCellURI, RDF_REST, RDF_NIL, null);
            popState();
        }


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

        protected String m_propertyURI;

        protected String m_reificationID;

        protected int m_depth;

        protected StringBuffer m_content;


        public ParseTypeLiteralPropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            if (m_depth == 0) {
                m_propertyURI = m_nodeElement.getPropertyURI(namespaceURI + localName);
                m_reificationID = m_nodeElement.getReificationID(atts);
                m_content = new StringBuffer();
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


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            if (m_depth == 1) {
                statementWithLiteralValue(m_nodeElement.getSubjectURI(),
                                          m_propertyURI,
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


        public void characters(char[] data, int start, int length) {
            m_content.append(data, start, length);
        }
    }


    /**
     * Parses parseTypeResourcePropertyElt production.
     */
    protected class ParseTypeResourcePropertyElement extends State {

        protected NodeElement m_nodeElement;

        protected String m_propertyURI;

        protected String m_reificationID;


        public ParseTypeResourcePropertyElement(NodeElement nodeElement) {
            m_nodeElement = nodeElement;
        }


        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                       SAXException {
            m_propertyURI = m_nodeElement.getPropertyURI(namespaceURI + localName);
            m_reificationID = m_nodeElement.getReificationID(atts);
            NodeElement anonymousNodeElement = new NodeElement();
            anonymousNodeElement.startDummyElement(atts);
            statementWithResourceValue(m_nodeElement.getSubjectURI(),
                                       m_propertyURI,
                                       anonymousNodeElement.getSubjectURI(),
                                       m_reificationID);
            pushState(new PropertyElementList(anonymousNodeElement));
        }


        public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
            popState();
        }


        public void characters(char[] data, int start, int length) throws SAXException {
            if (!isWhitespaceOnly(data, start, length))
                throw new RDFParserException("Cannot process characters when object properties are excepted.",
                                             m_documentLocator);
        }
    }


    protected static class ReificationManager {

        public static ReificationManager INSTANCE = new ReificationManager();


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


        public String getReificationID(String reificationID) throws SAXException {
            String resultURI;
            if (reificationID == null)
                resultURI = nextAnonymousURI();
            else
                resultURI = reificationID;
            statementWithResourceValue(m_uri, RDFNS + "_" + (++m_elements), resultURI, null);
            return resultURI;
        }
    }
}
