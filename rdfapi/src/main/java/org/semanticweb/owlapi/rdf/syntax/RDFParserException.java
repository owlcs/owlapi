package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;
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


/**
 * Throws if an RDF error is encountered while parsing RDF.
 */
public class RDFParserException extends SAXException {

    protected String m_publicId;

    protected String m_systemId;

    protected int m_lineNumber;

    protected int m_columnNumber;


    public RDFParserException(String message) {
        this(message, null, null, -1, -1);
    }


    public RDFParserException(String message, Locator locator) {
        this(message, locator.getPublicId(), locator.getSystemId(), locator.getLineNumber(), locator.getColumnNumber());
    }


    public RDFParserException(String message, String publicId, String systemId, int lineNumber, int columnNumber) {
        super((lineNumber != -1 || columnNumber != -1 ? "[line=" + lineNumber + ":" + "column=" + columnNumber + "] " : "") + message);
        m_publicId = publicId;
        m_systemId = systemId;
        m_lineNumber = lineNumber;
        m_columnNumber = columnNumber;
    }


    public String getPublicId() {
        return m_publicId;
    }


    public String getSystemId() {
        return m_systemId;
    }


    public int getLineNumber() {
        return m_lineNumber;
    }


    public int getColumnNumber() {
        return m_columnNumber;
    }
}
