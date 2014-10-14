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
package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Indicates that a parse error happened when trying to parse an ontology.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class OWLParserException extends OWLRuntimeException {

    private static final long serialVersionUID = 40000L;
    private final int lineNumber;
    private final int columnNumber;

    /** Default constructor. */
    public OWLParserException() {
        lineNumber = -1;
        columnNumber = -1;
    }

    /**
     * @param message
     *        the message
     */
    public OWLParserException(String message) {
        super(message);
        lineNumber = -1;
        columnNumber = -1;
    }

    /**
     * @param message
     *        the message
     * @param cause
     *        the cause
     */
    public OWLParserException(String message, Throwable cause) {
        super(message, cause);
        lineNumber = -1;
        columnNumber = -1;
    }

    /**
     * @param cause
     *        the cause
     */
    public OWLParserException(Throwable cause) {
        super(cause);
        lineNumber = -1;
        columnNumber = -1;
    }

    /**
     * @param message
     *        the message
     * @param lineNumber
     *        the line number
     * @param columnNumber
     *        the column number
     */
    public OWLParserException(String message, int lineNumber, int columnNumber) {
        super(message);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * @param cause
     *        the cause
     * @param lineNumber
     *        the line number
     * @param columnNumber
     *        the column number
     */
    public OWLParserException(Throwable cause, int lineNumber, int columnNumber) {
        super(cause);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * @param cause
     *        the cause
     * @param message
     *        the message
     * @param lineNumber
     *        the line number
     * @param columnNumber
     *        the column number
     */
    public OWLParserException(String message, Throwable cause, int lineNumber,
            int columnNumber) {
        super(message, cause);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * Gets the line number of the line that the parser was parsing when the
     * error occurred.
     * 
     * @return A positive integer which represents the line number, or -1 if the
     *         line number could not be determined.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /** @return the column number */
    public int getColumnNumber() {
        return columnNumber;
    }

    @Override
    public String getMessage() {
        if (lineNumber != -1) {
            return super.getMessage() + " (Line " + lineNumber + ')';
        }
        return super.getMessage();
    }
}
