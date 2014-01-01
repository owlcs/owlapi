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

import org.semanticweb.owlapi.model.NodeID;
import org.semanticweb.owlapi.rdf.syntax.RDFParser.ReificationManager;
import org.semanticweb.owlapi.rdf.util.RDFConstants;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/** Parses the nodeElement production. */
public class NodeElement implements State {
    /** The m_subject iri. */
    protected String m_subjectIRI;
    /** The m_reification manager. */
    protected ReificationManager m_reificationManager;
    /** The m_next li. */
    protected int m_nextLi = 1;
    /** The parser. */
    private RDFParser parser;

    /** Instantiates a new node element.
     * 
     * @param parser
     *            the parser */
    public NodeElement(RDFParser parser) {
        this.parser = parser;
    }

    /** Start dummy element.
     * 
     * @param atts
     *            the atts
     * @throws SAXException
     *             the sAX exception */
    public void startDummyElement(Attributes atts) throws SAXException {
        m_subjectIRI = NodeID.nextAnonymousIRI();
        m_reificationManager = parser.getReificationManager(atts);
    }

    /** Gets the subject iri.
     * 
     * @return subject iri */
    public String getSubjectIRI() {
        return m_subjectIRI;
    }

    /** Gets the reification id.
     * 
     * @param atts
     *            the atts
     * @return reification id
     * @throws SAXException
     *             the sAX exception */
    public String getReificationID(Attributes atts) throws SAXException {
        String rdfID = atts.getValue(RDFConstants.RDFNS, RDFConstants.ATTR_ID);
        if (rdfID != null) {
            rdfID = parser.getIRIFromID(rdfID);
        }
        return m_reificationManager.getReificationID(rdfID);
    }

    /** Gets the next li.
     * 
     * @return next list item */
    public String getNextLi() {
        return RDFConstants.RDFNS + "_" + m_nextLi++;
    }

    /** Gets the property iri.
     * 
     * @param uri
     *            the uri
     * @return property iri */
    public String getPropertyIRI(String uri) {
        if (RDFConstants.RDF_LI.equals(uri)) {
            return getNextLi();
        } else {
            return uri;
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.semanticweb.owlapi.rdf.syntax.State#startElement(java.lang.String,
     * java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement(String namespaceIRI, String localName,
            String qName, Attributes atts) throws SAXException {
        m_subjectIRI = parser.getIDNodeIDAboutResourceIRI(atts);
        boolean isRDFNS = RDFConstants.RDFNS.equals(namespaceIRI);
        m_reificationManager = parser.getReificationManager(atts);
        if (!isRDFNS || !RDFConstants.ELT_DESCRIPTION.equals(localName)) {
            parser.statementWithResourceValue(m_subjectIRI,
                    RDFConstants.RDF_TYPE, namespaceIRI + localName,
                    m_reificationManager.getReificationID(null));
        }
        parser.checkUnsupportedAttributes(atts);
        parser.propertyAttributes(m_subjectIRI, atts, m_reificationManager);
        parser.pushState(new PropertyElementList(this, parser));
    }

    /*
     * (non-Javadoc)
     * @see org.semanticweb.owlapi.rdf.syntax.State#endElement(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void endElement(String namespaceIRI, String localName, String qName)
            throws SAXException {
        parser.popState();
    }

    /*
     * (non-Javadoc)
     * @see org.semanticweb.owlapi.rdf.syntax.State#characters(char[], int, int)
     */
    @Override
    public void characters(char[] data, int start, int length)
            throws SAXException {
        if (!parser.isWhitespaceOnly(data, start, length)) {
            throw new RDFParserException(
                    "Cannot answer characters when node is excepted.",
                    parser.m_documentLocator);
        }
    }
}
