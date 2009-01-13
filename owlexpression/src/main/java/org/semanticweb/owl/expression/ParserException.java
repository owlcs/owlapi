package org.semanticweb.owl.expression;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
 * Date: 11-Sep-2007<br><br>
 */
public class ParserException extends Exception {

    private String currentToken;

    private int lineNumber;

    private int columnNumber;

    private boolean classNameExpected;

    private boolean objectPropertyNameExpected;

    private boolean dataPropertyNameExpected;

    private boolean individualNameExpected;

    private boolean datatypeNameExpected;

    private boolean integerExpected;

    private Set<String> expectedKeywords;

    private int startPos;


    public ParserException(String currentToken, int startPos, int lineNumber, int columnNumber,
                                                              boolean classNameExpected,
                                                              boolean objectPropertyNameExpected,
                                                              boolean dataPropertyNameExpected,
                                                              boolean individualNameExpected,
                                                              boolean datatypeNameExpected,
                                                              Set<String> expectedKeywords) {
        this.currentToken = currentToken;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.classNameExpected = classNameExpected;
        this.objectPropertyNameExpected = objectPropertyNameExpected;
        this.dataPropertyNameExpected = dataPropertyNameExpected;
        this.individualNameExpected = individualNameExpected;
        this.datatypeNameExpected = datatypeNameExpected;
        this.expectedKeywords = expectedKeywords;
        this.startPos = startPos;
    }


    public ParserException(String currentToken, int startPos, int lineNumber, int columnNumber,
                                                              boolean classNameExpected,
                                                              boolean objectPropertyNameExpected,
                                                              boolean dataPropertyNameExpected,
                                                              boolean individualNameExpected,
                                                              boolean datatypeNameExpected,
                                                              String ... keywords) {
        this.currentToken = currentToken;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.classNameExpected = classNameExpected;
        this.objectPropertyNameExpected = objectPropertyNameExpected;
        this.dataPropertyNameExpected = dataPropertyNameExpected;
        this.individualNameExpected = individualNameExpected;
        this.datatypeNameExpected = datatypeNameExpected;
        this.expectedKeywords = new HashSet<String>(Arrays.asList(keywords));
        this.startPos = startPos;
    }


    public ParserException(String currentToken, int lineNumber, int columnNumber, boolean integerExpected,
                           int startPos) {
        this.currentToken = currentToken;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.integerExpected = integerExpected;
        this.startPos = startPos;
        expectedKeywords = new HashSet<String>();
    }


    public ParserException(String currentToken, int startPos, int lineNumber, int columnNumber, String ... keywords) {
        this(currentToken, startPos, lineNumber, columnNumber, false, false, false, false, false, keywords);
    }


    public int getStartPos() {
        return startPos;
    }


    public boolean isClassNameExpected() {
        return classNameExpected;
    }


    public boolean isObjectPropertyNameExpected() {
        return objectPropertyNameExpected;
    }


    public boolean isDataPropertyNameExpected() {
        return dataPropertyNameExpected;
    }


    public boolean isIndividualNameExpected() {
        return individualNameExpected;
    }


    public boolean isDatatypeNameExpected() {
        return datatypeNameExpected;
    }


    public Set<String> getExpectedKeywords() {
        return expectedKeywords;
    }


    public String getCurrentToken() {
        return currentToken;
    }


    public int getLineNumber() {
        return lineNumber;
    }


    public int getColumnNumber() {
        return columnNumber;
    }


    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Encountered ");
        sb.append(currentToken);
        sb.append(" at line ");
        sb.append(lineNumber);
        sb.append(" column ");
        sb.append(columnNumber);
        sb.append(". Expected one of:\n");
        if(classNameExpected) {
            sb.append("\tClass name\n");
        }
        if(objectPropertyNameExpected) {
            sb.append("\tObject property name\n");
        }
        if(dataPropertyNameExpected) {
            sb.append("\tData property name\n");
        }
        if(individualNameExpected) {
            sb.append("\tIndividual name\n");
        }
        if(datatypeNameExpected) {
            sb.append("\tDatatype name\n");
        }
        if(integerExpected) {
            sb.append("\tInteger\n");
        }
        for(String kw : expectedKeywords) {
            sb.append("\t");
            sb.append(kw);
            sb.append("\n");
        }
        return sb.toString();
    }
}