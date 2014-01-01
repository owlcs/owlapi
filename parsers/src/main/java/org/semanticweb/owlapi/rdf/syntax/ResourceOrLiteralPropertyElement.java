/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
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
 * Copyright 2014, The University of Manchester
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
 */
package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses resourcePropertyElt or literalPropertyElt productions. m_text is
 * {@code null} when startElement is expected on the actual property element. */
public class ResourceOrLiteralPropertyElement implements State {
    protected NodeElement nodeElement;
    protected String propertyIRI;
    protected String reificationID;
    protected String datatype;
    protected StringBuilder text;
    protected NodeElement innerNode;
    private RDFParser parser;

    /** @param nodeElement
     *            nodeElement
     * @param parser
     *            parser */
    public ResourceOrLiteralPropertyElement(NodeElement nodeElement,
            RDFParser parser) {
        this.nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (text == null) {
            // this is the invocation on the outer element
            propertyIRI = nodeElement.getPropertyIRI(namespaceIRI + localName);
            reificationID = nodeElement.getReificationID(atts);
            datatype = atts.getValue(RDFConstants.RDFNS,
                    RDFConstants.ATTR_DATATYPE);
            text = new StringBuilder();
        } else {
            if (!parser.isWhitespaceOnly(text)) {
                throw new RDFParserException(
                        "Text was seen and new node is started.",
                        parser.m_documentLocator);
            }
            if (datatype != null) {
                throw new RDFParserException(
                        "rdf:datatype specified on a node with resource value.",
                        parser.m_documentLocator);
            }
            innerNode = new NodeElement(parser);
            parser.pushState(innerNode);
            parser.state.startElement(namespaceIRI, localName, qName, atts);
        }
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (innerNode != null) {
            parser.statementWithResourceValue(nodeElement.getSubjectIRI(),
                    propertyIRI, innerNode.getSubjectIRI(), reificationID);
        } else {
            parser.statementWithLiteralValue(nodeElement.getSubjectIRI(),
                    propertyIRI, text.toString(), datatype, reificationID);
        }
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        if (innerNode != null) {
            if (!parser.isWhitespaceOnly(data, start, length)) {
                throw new RDFParserException(
                        "Cannot answer characters when object properties are excepted.",
                        parser.m_documentLocator);
            }
        } else {
            text.append(data, start, length);
        }
    }
}
