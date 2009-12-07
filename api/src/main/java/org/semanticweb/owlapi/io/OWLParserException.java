package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLException;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Apr-2007<br><br>
 *
 * Indicates that a parse error happened when trying to parse an ontology.
 */
public class OWLParserException extends OWLException {

    private int lineNumber;

    private int columnNumber;

    public OWLParserException() {
        this.lineNumber = -1;
    }

    public OWLParserException(String message) {
        super(message);
        lineNumber = -1;
    }


    public OWLParserException(String message, Throwable cause) {
        super(message, cause);
        lineNumber = -1;
    }


    public OWLParserException(Throwable cause) {
        super(cause);
        lineNumber = -1;
    }

    public OWLParserException(String message, int lineNumber, int columnNumber) {
        super(message);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public OWLParserException(Throwable cause, int lineNumber, int columnNumber) {
        super(cause);
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    /**
     * Gets the line number of the line that the parser
     * was parsing when the error occurred.
     * @return A positive integer which represents the line
     * number, or -1 if the line number could not be determined.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

//    public void setLineNumber(int lineNumber) {
//        this.lineNumber = lineNumber;
//    }


    public String getMessage() {
        if (lineNumber != -1) {
            return super.getMessage() + " (Line " + lineNumber + ")";
        }
        else {
            return super.getMessage();
        }
    }
}
