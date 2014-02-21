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

import org.semanticweb.owlapi.model.NodeID;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses parseTypeCollectionPropertyElt production. */
public class ParseTypeCollectionPropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    protected String m_lastCellIRI;
    private RDFParser parser;

    /** @param nodeElement
     *            nodeElement
     * @param parser
     *            parser */
    public ParseTypeCollectionPropertyElement(NodeElement nodeElement,
            RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
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
            String newListCellIRI = listCell(collectionNode.getSubjectIRI());
            if (m_lastCellIRI == null) {
                parser.statementWithResourceValue(
                        m_nodeElement.getSubjectIRI(), m_propertyIRI,
                        newListCellIRI, m_reificationID);
            } else {
                parser.statementWithResourceValue(m_lastCellIRI,
                        RDFConstants.RDF_REST, newListCellIRI, null);
            }
            m_lastCellIRI = newListCellIRI;
        }
    }

    protected String listCell(String valueIRI) {
        String listCellIRI = NodeID.nextAnonymousIRI();
        parser.statementWithResourceValue(listCellIRI, RDFConstants.RDF_FIRST,
                valueIRI, null);
        parser.statementWithResourceValue(listCellIRI, RDFConstants.RDF_TYPE,
                RDFConstants.RDF_LIST, null);
        return listCellIRI;
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        if (m_lastCellIRI == null) {
            parser.statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, RDFConstants.RDF_NIL, m_reificationID);
        } else {
            parser.statementWithResourceValue(m_lastCellIRI,
                    RDFConstants.RDF_REST, RDFConstants.RDF_NIL, null);
        }
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        if (!parser.isWhitespaceOnly(data, start, length)) {
            throw new RDFParserException(
                    "Expecting an object element instead of character content.",
                    parser.m_documentLocator);
        }
    }
}
