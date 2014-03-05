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

import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.NodeID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

abstract class AbstractState {

    //@formatter:off
    static final String DATATYPE_RESOURCE           = "rdf:datatype specified on a node with resource value.";
    static final String TEXT_SEEN                   = "Text was seen and new node is started.";
    static final String RDF_RDF                     = "Expecting rdf:RDF element.";
    static final String OP_EXPECTED                 = "Cannot answer characters when object properties are expected.";
    static final String CHARACTERS_NOT_EXPECTED     = "Characters were not expected.";
    static final String INCORRECT_START             = "incorrect element start encountered.";
    static final String EXPECTING_OBJECT            = "Expecting an object element instead of character content.";
    static final String RDF_RDF_EXPECTED            = "Expecting rdf:rdf element instead of character content.";
    static final String NODE_EXPECTED               = "Cannot answer characters when node is expected.";
    static final String NO_RDF_NODE_ID_ID_ABOUT     = "Element cannot specify both rdf:nodeID and rdf:ID or rdf:about attributes.";
    static final String NO_RDF_ID_AND_ABOUT         = "Element cannot specify both rdf:ID and rdf:about attributes.";
    static final String ABOUT_EACH_PREFIX_UNSUPPORTED = "rdf:aboutEachPrefix attribute is not supported.";
    static final String ABOUT_EACH_UNSUPPORTED      = "rdf:aboutEach attribute is not supported.";
    //@formatter:on
    protected static final Logger logger = LoggerFactory
            .getLogger(AbstractState.class);
    protected final RDFParser parser;

    AbstractState(RDFParser parser) {
        this.parser = parser;
    }

    /**
     * Returns the reification manager for given attributes.
     * 
     * @param atts
     *        the attributes
     * @return the reification manager
     */
    ReificationManager getReificationManager(Attributes atts) {
        String bagIDAttr = atts.getValue(RDFNS, ATTR_BAG_ID);
        if (bagIDAttr == null) {
            return ReificationManager.INSTANCE;
        } else {
            String bagID = parser.resolveIRI("#" + bagIDAttr);
            return new ReifiedStatementBag(bagID, parser);
        }
    }

    /**
     * Parses the propertyAttributes production.
     * 
     * @param subjectIRI
     *        IRI of the resource whose properties are being parsed
     * @param atts
     *        attributes
     * @param reificationManager
     *        the reification manager
     * @throws SAXException
     *         SAXException
     */
    void propertyAttributes(@Nonnull String subjectIRI,
            @Nonnull Attributes atts,
            @Nonnull ReificationManager reificationManager) throws SAXException {
        int length = atts.getLength();
        for (int i = 0; i < length; i++) {
            String nsIRI = atts.getURI(i);
            String localName = atts.getLocalName(i);
            if (!XMLNS.equals(nsIRI)
                    && !XMLLANG.equals(localName)
                    && !(RDFNS.equals(nsIRI) && (ATTR_ID.equals(localName)
                            || ATTR_NODE_ID.equals(localName)
                            || ATTR_ABOUT.equals(localName)
                            || ELT_TYPE.equals(localName)
                            || ATTR_RESOURCE.equals(localName)
                            || ATTR_PARSE_TYPE.equals(localName)
                            || ATTR_ABOUT_EACH.equals(localName)
                            || ATTR_ABOUT_EACH_PREFIX.equals(localName) || ATTR_BAG_ID
                                .equals(localName)))) {
                String value = atts.getValue(i);
                String reificationID = reificationManager.getReificationID(
                        null, parser);
                parser.statementWithLiteralValue(subjectIRI, nsIRI + localName,
                        value, null, reificationID);
            } else if (RDFNS.equals(nsIRI) && ELT_TYPE.equals(localName)) {
                String value = parser.resolveIRI(atts.getValue(i));
                String reificationID = reificationManager.getReificationID(
                        null, parser);
                parser.statementWithResourceValue(subjectIRI,
                        nsIRI + localName, value, reificationID);
            }
        }
    }

    /**
     * Extracts the IRI of the resource from rdf:resource or rdf:nodeID
     * attribute. If no attribute is found, {@code null} is returned.
     * 
     * @param atts
     *        the attributes
     * @return the IRI of the resource or {@code null}
     */
    @Nullable
    String getNodeIDResourceResourceIRI(Attributes atts) {
        String value = atts.getValue(RDFNS, ATTR_RESOURCE);
        if (value != null) {
            return parser.resolveIRI(value);
        } else {
            value = atts.getValue(RDFNS, ATTR_NODE_ID);
            if (value != null) {
                return NodeID.getIRIFromNodeID(value);
            } else {
                return null;
            }
        }
    }

    /**
     * Checks whether given characters contain only whitespace.
     * 
     * @param data
     *        the data being checked
     * @param start
     *        the start index (inclusive)
     * @param length
     *        the end index (non-inclusive)
     * @return {@code true} if characters contain whitespace
     */
    boolean notBlank(char[] data, int start, int length) {
        int end = start + length;
        for (int i = start; i < end; i++) {
            if (notSpace(data[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param c
     *        character to test
     * @return true if the character is other than a space, carriage return, or
     *         tabulator
     */
    boolean notSpace(char c) {
        return c != ' ' && c != '\n' && c != '\r' && c != '\t';
    }

    /**
     * Checks whether given characters contain only whitespace.
     * 
     * @param buffer
     *        the data being checked
     * @return {@code true} if characters contain whitespace
     */
    boolean notBlank(StringBuilder buffer) {
        for (int i = 0; i < buffer.length(); i++) {
            if (notSpace(buffer.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}

/** State expecting start of RDF text. */
class StartRDF extends AbstractState implements State {

    StartRDF(RDFParser parser) {
        super(parser);
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        parser.verify(
                !RDFNS.equals(namespaceIRI) || !ELT_RDF.equals(localName),
                RDF_RDF);
        String value = atts.getValue(XMLNS, "base");
        if (value == null) {
            logger.info(
                    "Notice: root element does not have an xml:base. Relative IRIs will be resolved against {}",
                    parser.m_baseIRI);
        }
        // the logical IRI is the current IRI that we have as the base IRI
        // at this point
        parser.m_consumer.logicalURI(parser.m_baseIRI);
        parser.pushState(new NodeElementList(parser));
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        parser.verify(notBlank(data, start, length), RDF_RDF_EXPECTED);
    }
}

/** Parses emptyPropertyElt production. */
class EmptyPropertyElement extends AbstractState implements State {

    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;

    EmptyPropertyElement(NodeElement nodeElement, RDFParser parser) {
        super(parser);
        m_nodeElement = nodeElement;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        parser.verify(m_propertyIRI != null, INCORRECT_START);
        // this is the invocation on the outer element
        m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
        String reificationID = m_nodeElement.getReificationID(atts);
        String objectIRI = getNodeIDResourceResourceIRI(atts);
        if (objectIRI == null) {
            objectIRI = NodeID.nextAnonymousIRI();
        }
        parser.statementWithResourceValue(m_nodeElement.subjectIRI,
                m_propertyIRI, objectIRI, reificationID);
        propertyAttributes(objectIRI, atts, getReificationManager(atts));
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        parser.verify(true, CHARACTERS_NOT_EXPECTED);
    }
}

/** Parses the nodeElement production. */
class NodeElement extends AbstractState implements State {

    protected String subjectIRI;
    protected ReificationManager reificationManager;
    protected AtomicLong m_nextLi = new AtomicLong(1);

    NodeElement(RDFParser parser) {
        super(parser);
    }

    void startDummyElement(Attributes atts) {
        subjectIRI = NodeID.nextAnonymousIRI();
        reificationManager = getReificationManager(atts);
    }

    /**
     * @param atts
     *        the atts
     * @return reification id
     * @throws SAXException
     *         the SAX exception
     */
    String getReificationID(Attributes atts) throws SAXException {
        String rdfID = atts.getValue(RDFNS, ATTR_ID);
        if (rdfID != null) {
            rdfID = parser.resolveIRI("#" + rdfID);
        }
        return reificationManager.getReificationID(rdfID, parser);
    }

    /** @return next list item */
    String getNextLi() {
        return RDFNS + "_" + m_nextLi.getAndIncrement();
    }

    /**
     * @param uri
     *        the uri
     * @return property iri
     */
    String getPropertyIRI(String uri) {
        if (RDF_LI.equals(uri)) {
            return getNextLi();
        }
        return uri;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        subjectIRI = getIDNodeIDAboutResourceIRI(atts);
        boolean isRDFNS = RDFNS.equals(namespaceIRI);
        reificationManager = getReificationManager(atts);
        if (!isRDFNS || !ELT_DESCRIPTION.equals(localName)) {
            parser.statementWithResourceValue(subjectIRI, RDF_TYPE,
                    namespaceIRI + localName,
                    reificationManager.getReificationID(null, parser));
        }
        // Checks if attribute list contains some of the unsupported attributes.
        parser.verify(atts.getIndex(RDFNS, ATTR_ABOUT_EACH) != -1,
                ABOUT_EACH_UNSUPPORTED);
        parser.verify(atts.getIndex(RDFNS, ATTR_ABOUT_EACH_PREFIX) != -1,
                ABOUT_EACH_PREFIX_UNSUPPORTED);
        propertyAttributes(subjectIRI, atts, reificationManager);
        parser.pushState(new PropertyElementList(this, parser));
    }

    /**
     * Extracts the IRI of the resource from rdf:ID, rdf:nodeID or rdf:about
     * attribute. If no attribute is found, an IRI is generated.
     * 
     * @param atts
     *        atts
     * @return string for IRI
     */
    @Nonnull
    String getIDNodeIDAboutResourceIRI(@Nonnull Attributes atts) {
        checkNotNull(atts, "atts cannot be null");
        String result = null;
        String value = atts.getValue(RDFNS, ATTR_ID);
        if (value != null) {
            result = parser.resolveIRI("#" + value);
        }
        value = atts.getValue(RDFNS, ATTR_ABOUT);
        if (value != null) {
            parser.verify(result != null, NO_RDF_ID_AND_ABOUT);
            result = parser.resolveIRI(value);
        }
        value = atts.getValue(RDFNS, ATTR_NODE_ID);
        if (value != null) {
            parser.verify(result != null, NO_RDF_NODE_ID_ID_ABOUT);
            result = NodeID.getIRIFromNodeID(value);
        }
        if (result == null) {
            result = NodeID.nextAnonymousIRI();
        }
        return result;
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        parser.verify(notBlank(data, start, length), NODE_EXPECTED);
    }
}

/** Parses the nodeElementList production. */
class NodeElementList extends AbstractState implements State {

    NodeElementList(RDFParser parser) {
        super(parser);
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        parser.pushState(new NodeElement(parser));
        parser.state.startElement(namespaceIRI, localName, qName, atts);
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
        parser.state.endElement(namespaceIRI, localName, qName);
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        parser.verify(notBlank(data, start, length), EXPECTING_OBJECT);
    }
}

/** Parses parseTypeCollectionPropertyElt production. */
class ParseTypeCollectionElement extends AbstractState implements State {

    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    protected String m_lastCellIRI;

    ParseTypeCollectionElement(NodeElement nodeElement, RDFParser parser) {
        super(parser);
        m_nodeElement = nodeElement;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (m_propertyIRI == null) {
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI
                    + localName);
            m_reificationID = m_nodeElement.getReificationID(atts);
        } else {
            NodeElement collectionNode = new NodeElement(parser);
            parser.pushState(collectionNode);
            parser.state.startElement(namespaceIRI, localName, qName, atts);
            String newListCellIRI = listCell(collectionNode.subjectIRI);
            if (m_lastCellIRI == null) {
                parser.statementWithResourceValue(m_nodeElement.subjectIRI,
                        m_propertyIRI, newListCellIRI, m_reificationID);
            } else {
                parser.statementWithResourceValue(m_lastCellIRI, RDF_REST,
                        newListCellIRI, null);
            }
            m_lastCellIRI = newListCellIRI;
        }
    }

    String listCell(String valueIRI) {
        String listCellIRI = NodeID.nextAnonymousIRI();
        parser.statementWithResourceValue(listCellIRI, RDF_FIRST, valueIRI,
                null);
        parser.statementWithResourceValue(listCellIRI, RDF_TYPE, RDF_LIST, null);
        return listCellIRI;
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (m_lastCellIRI == null) {
            parser.statementWithResourceValue(m_nodeElement.subjectIRI,
                    m_propertyIRI, RDF_NIL, m_reificationID);
        } else {
            parser.statementWithResourceValue(m_lastCellIRI, RDF_REST, RDF_NIL,
                    null);
        }
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        parser.verify(notBlank(data, start, length), EXPECTING_OBJECT);
    }
}

/**
 * Parses resourcePropertyElt or literalPropertyElt productions. m_text is
 * {@code null} when startElement is expected on the actual property element.
 */
class ResourceOrLiteralElement extends AbstractState implements State {

    protected NodeElement nodeElement;
    protected String propertyIRI;
    protected String reificationID;
    protected String datatype;
    protected StringBuilder text;
    protected NodeElement innerNode;

    ResourceOrLiteralElement(NodeElement nodeElement, RDFParser parser) {
        super(parser);
        this.nodeElement = nodeElement;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (text == null) {
            // this is the invocation on the outer element
            propertyIRI = nodeElement.getPropertyIRI(namespaceIRI + localName);
            reificationID = nodeElement.getReificationID(atts);
            datatype = atts.getValue(RDFNS, ATTR_DATATYPE);
            text = new StringBuilder();
        } else {
            parser.verify(notBlank(text), TEXT_SEEN);
            parser.verify(datatype != null, DATATYPE_RESOURCE);
            innerNode = new NodeElement(parser);
            parser.pushState(innerNode);
            parser.state.startElement(namespaceIRI, localName, qName, atts);
        }
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (innerNode != null) {
            parser.statementWithResourceValue(nodeElement.subjectIRI,
                    propertyIRI, innerNode.subjectIRI, reificationID);
        } else {
            parser.statementWithLiteralValue(nodeElement.subjectIRI,
                    propertyIRI, text.toString(), datatype, reificationID);
        }
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        if (innerNode != null) {
            parser.verify(notBlank(data, start, length), OP_EXPECTED);
        } else {
            text.append(data, start, length);
        }
    }
}

/** Parses parseTypeLiteralPropertyElt production. */
class ParseTypeLiteralElement extends AbstractState implements State {

    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    protected int m_depth;
    protected StringBuilder m_content;

    ParseTypeLiteralElement(NodeElement nodeElement, RDFParser parser) {
        super(parser);
        m_nodeElement = nodeElement;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (m_depth == 0) {
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI
                    + localName);
            m_reificationID = m_nodeElement.getReificationID(atts);
            m_content = new StringBuilder();
        } else {
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
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (m_depth == 1) {
            parser.statementWithLiteralValue(m_nodeElement.subjectIRI,
                    m_propertyIRI, m_content.toString(), RDF_XMLLITERAL,
                    m_reificationID);
            parser.popState();
        } else {
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

/** Parses parseTypeResourcePropertyElt production. */
class ParseTypeResourceElement extends AbstractState implements State {

    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;

    ParseTypeResourceElement(NodeElement nodeElement, RDFParser parser) {
        super(parser);
        m_nodeElement = nodeElement;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI + localName);
        m_reificationID = m_nodeElement.getReificationID(atts);
        NodeElement anonymousNodeElement = new NodeElement(parser);
        anonymousNodeElement.startDummyElement(atts);
        parser.statementWithResourceValue(m_nodeElement.subjectIRI,
                m_propertyIRI, anonymousNodeElement.subjectIRI, m_reificationID);
        parser.pushState(new PropertyElementList(anonymousNodeElement, parser));
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        parser.verify(notBlank(data, start, length), OP_EXPECTED);
    }
}

/**
 * Parses the propertyEltList production. The contents of the startElement
 * method implements also the propertyElt production.
 */
class PropertyElementList extends AbstractState implements State {

    protected NodeElement node;

    PropertyElementList(NodeElement nodeElement, RDFParser parser) {
        super(parser);
        node = nodeElement;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        String parseType = atts.getValue(RDFNS, ATTR_PARSE_TYPE);
        if (PARSE_TYPE_LITERAL.equals(parseType)) {
            parser.pushState(new ParseTypeLiteralElement(node, parser));
        } else if (PARSE_TYPE_RESOURCE.equals(parseType)) {
            parser.pushState(new ParseTypeResourceElement(node, parser));
        } else if (PARSE_TYPE_COLLECTION.equals(parseType)) {
            parser.pushState(new ParseTypeCollectionElement(node, parser));
        } else if (parseType != null) {
            parser.pushState(new ParseTypeLiteralElement(node, parser));
        } else {
            String objectIRI = getNodeIDResourceResourceIRI(atts);
            if (objectIRI != null) {
                parser.pushState(new EmptyPropertyElement(node, parser));
            } else {
                parser.pushState(new ResourceOrLiteralElement(node, parser));
            }
        }
        parser.state.startElement(namespaceIRI, localName, qName, atts);
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
        parser.state.endElement(namespaceIRI, localName, qName);
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        parser.verify(notBlank(data, start, length), OP_EXPECTED);
    }
}

class ReificationManager {

    public static final ReificationManager INSTANCE = new ReificationManager();

    @SuppressWarnings("unused")
    String getReificationID(String reificationID, RDFParser parser)
            throws SAXException {
        return reificationID;
    }
}

class ReifiedStatementBag extends ReificationManager {

    protected AtomicLong m_elements = new AtomicLong(1);
    protected String m_uri;

    ReifiedStatementBag(String uri, RDFParser parser) {
        m_uri = uri;
        parser.statementWithResourceValue(m_uri, RDF_TYPE, RDF_BAG, null);
    }

    @Override
    String getReificationID(String reificationID, RDFParser parser)
            throws SAXException {
        String resultIRI;
        if (reificationID == null) {
            resultIRI = NodeID.nextAnonymousIRI();
        } else {
            resultIRI = reificationID;
        }
        parser.statementWithResourceValue(m_uri,
                RDFNS + "_" + m_elements.getAndIncrement(), resultIRI, null);
        return resultIRI;
    }
}
