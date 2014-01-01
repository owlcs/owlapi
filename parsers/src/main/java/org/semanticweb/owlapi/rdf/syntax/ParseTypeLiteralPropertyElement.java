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

/** Parses parseTypeLiteralPropertyElt production. */
public class ParseTypeLiteralPropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    protected String m_reificationID;
    protected int m_depth;
    protected StringBuilder m_content;
    private RDFParser parser;

    /** @param nodeElement
     *            nodeElement
     * @param parser
     *            parser */
    public ParseTypeLiteralPropertyElement(NodeElement nodeElement,
            RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
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
            parser.statementWithLiteralValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, m_content.toString(),
                    RDFConstants.RDF_XMLLITERAL, m_reificationID);
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
