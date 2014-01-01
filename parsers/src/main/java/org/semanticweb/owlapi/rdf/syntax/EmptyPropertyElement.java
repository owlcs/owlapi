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
 */package org.semanticweb.owlapi.rdf.syntax;

import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.rdf.syntax.RDFParser.ReificationManager;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses emptyPropertyElt production. */
public class EmptyPropertyElement implements State {
    protected NodeElement m_nodeElement;
    protected String m_propertyIRI;
    private RDFParser parser;

    /** @param nodeElement
     *            node element
     * @param parser
     *            parser */
    public EmptyPropertyElement(NodeElement nodeElement, RDFParser parser) {
        m_nodeElement = nodeElement;
        this.parser = parser;
    }

    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        if (m_propertyIRI == null) {
            // this is the invocation on the outer element
            m_propertyIRI = m_nodeElement.getPropertyIRI(namespaceIRI
                    + localName);
            String reificationID = m_nodeElement.getReificationID(atts);
            String objectIRI = parser.getNodeIDResourceResourceIRI(atts);
            if (objectIRI == null) {
                objectIRI = NodeID.nextAnonymousIRI();
            }
            parser.statementWithResourceValue(m_nodeElement.getSubjectIRI(),
                    m_propertyIRI, objectIRI, reificationID);
            ReificationManager reificationManager = parser
                    .getReificationManager(atts);
            parser.propertyAttributes(objectIRI, atts, reificationManager);
        } else {
            throw new RDFParserException(
                    "incorrect element start encountered.",
                    parser.m_documentLocator);
        }
    }

    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        throw new RDFParserException("Characters were not excepted.",
                parser.m_documentLocator);
    }
}
