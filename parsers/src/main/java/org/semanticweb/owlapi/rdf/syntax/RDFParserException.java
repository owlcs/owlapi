/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
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
 * Copyright 2011, University of Manchester
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
 *
 *
 * Original code by Boris Motik.  The original code formed part of KAON
 * which is licensed under the LGPL License. The original package
 * name was edu.unika.aifb.rdf.api
 *
 */
package org.semanticweb.owlapi.rdf.syntax;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/** Throws if an RDF error is encountered while parsing RDF. */
public class RDFParserException extends SAXException {
    private static final long serialVersionUID = 40000L;
    protected final String publicId;
    protected final String systemId;
    protected final int lineNumber;
    protected final int columnNumber;

    /** @param message
     *            message */
    public RDFParserException(String message) {
        this(message, null, null, -1, -1);
    }

    /** @param message
     *            message
     * @param locator
     *            locator */
    public RDFParserException(String message, Locator locator) {
        this(message, locator.getPublicId(), locator.getSystemId(), locator
                .getLineNumber(), locator.getColumnNumber());
    }

    /** @param message
     *            message
     * @param publicId
     *            publicId
     * @param systemId
     *            systemId
     * @param lineNumber
     *            lineNumber
     * @param columnNumber
     *            columnNumber */
    public RDFParserException(String message, String publicId, String systemId,
            int lineNumber, int columnNumber) {
        super((lineNumber != -1 || columnNumber != -1 ? "[line=" + lineNumber + ":"
                + "column=" + columnNumber + "] " : "")
                + message);
        this.publicId = publicId;
        this.systemId = systemId;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /** @return public id */
    public String getPublicId() {
        return publicId;
    }

    /** @return system id */
    public String getSystemId() {
        return systemId;
    }

    /** @return line number */
    public int getLineNumber() {
        return lineNumber;
    }

    /** @return column number */
    public int getColumnNumber() {
        return columnNumber;
    }
}
