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
 */
package org.semanticweb.owlapi.expression;

import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLParserException;

/**
 * The Class ParserException.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group, Date: 11-Sep-2007
 */
public class ParserException extends OWLParserException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 30406L;
    /** The current token. */
    private String currentToken;
    /** The line number. */
    private int lineNumber;
    /** The column number. */
    private int columnNumber;
    /** The token sequence. */
    private List<String> tokenSequence;
    /** The class name expected. */
    private boolean classNameExpected = false;
    /** The object property name expected. */
    private boolean objectPropertyNameExpected = false;
    /** The data property name expected. */
    private boolean dataPropertyNameExpected = false;
    /** The individual name expected. */
    private boolean individualNameExpected = false;
    /** The datatype name expected. */
    private boolean datatypeNameExpected = false;
    /** The integer expected. */
    private boolean integerExpected = false;
    /** The annotation property expected. */
    private boolean annotationPropertyExpected = false;
    /** The ontology name expected. */
    private boolean ontologyNameExpected = false;
    /** The expected keywords. */
    private Set<String> expectedKeywords;
    /** The start pos. */
    private int startPos;

    /**
     * Instantiates a new parser exception.
     * 
     * @param tokenSequence
     *        the token sequence
     * @param startPos
     *        the start pos
     * @param lineNumber
     *        the line number
     * @param columnNumber
     *        the column number
     * @param ontologyNameExpected
     *        the ontology name expected
     * @param classNameExpected
     *        the class name expected
     * @param objectPropertyNameExpected
     *        the object property name expected
     * @param dataPropertyNameExpected
     *        the data property name expected
     * @param individualNameExpected
     *        the individual name expected
     * @param datatypeNameExpected
     *        the datatype name expected
     * @param annotationPropertyExpected
     *        the annotation property expected
     * @param integerExpected
     *        the integer expected
     * @param expectedKeywords
     *        the expected keywords
     */
    public ParserException(List<String> tokenSequence, int startPos,
            int lineNumber, int columnNumber, boolean ontologyNameExpected,
            boolean classNameExpected, boolean objectPropertyNameExpected,
            boolean dataPropertyNameExpected, boolean individualNameExpected,
            boolean datatypeNameExpected, boolean annotationPropertyExpected,
            boolean integerExpected, Set<String> expectedKeywords) {
        this.ontologyNameExpected = ontologyNameExpected;
        currentToken = tokenSequence.iterator().next();
        this.tokenSequence = tokenSequence;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.classNameExpected = classNameExpected;
        this.objectPropertyNameExpected = objectPropertyNameExpected;
        this.dataPropertyNameExpected = dataPropertyNameExpected;
        this.individualNameExpected = individualNameExpected;
        this.datatypeNameExpected = datatypeNameExpected;
        this.annotationPropertyExpected = annotationPropertyExpected;
        this.expectedKeywords = expectedKeywords;
        this.startPos = startPos;
        this.integerExpected = integerExpected;
    }

    /**
     * Gets the token sequence.
     * 
     * @return the token sequence
     */
    public List<String> getTokenSequence() {
        return tokenSequence;
    }

    /**
     * Gets the start pos.
     * 
     * @return the start pos
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * Checks if is class name expected.
     * 
     * @return true, if is class name expected
     */
    public boolean isClassNameExpected() {
        return classNameExpected;
    }

    /**
     * Checks if is object property name expected.
     * 
     * @return true, if is object property name expected
     */
    public boolean isObjectPropertyNameExpected() {
        return objectPropertyNameExpected;
    }

    /**
     * Checks if is data property name expected.
     * 
     * @return true, if is data property name expected
     */
    public boolean isDataPropertyNameExpected() {
        return dataPropertyNameExpected;
    }

    /**
     * Checks if is individual name expected.
     * 
     * @return true, if is individual name expected
     */
    public boolean isIndividualNameExpected() {
        return individualNameExpected;
    }

    /**
     * Checks if is datatype name expected.
     * 
     * @return true, if is datatype name expected
     */
    public boolean isDatatypeNameExpected() {
        return datatypeNameExpected;
    }

    /**
     * Checks if is annotation property name expected.
     * 
     * @return true, if is annotation property name expected
     */
    public boolean isAnnotationPropertyNameExpected() {
        return annotationPropertyExpected;
    }

    /**
     * Checks if is ontology name expected.
     * 
     * @return true, if is ontology name expected
     */
    public boolean isOntologyNameExpected() {
        return ontologyNameExpected;
    }

    /**
     * Gets the expected keywords.
     * 
     * @return the expected keywords
     */
    public Set<String> getExpectedKeywords() {
        return expectedKeywords;
    }

    /**
     * Gets the current token.
     * 
     * @return the current token
     */
    public String getCurrentToken() {
        return currentToken;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * Checks if is integer expected.
     * 
     * @return true, if is integer expected
     */
    public boolean isIntegerExpected() {
        return integerExpected;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Encountered ");
        sb.append(currentToken);
        sb.append(" at line ");
        sb.append(lineNumber);
        sb.append(" column ");
        sb.append(columnNumber);
        sb.append(". Expected one of:\n");
        if (ontologyNameExpected) {
            sb.append("\tOntology name\n");
        }
        if (classNameExpected) {
            sb.append("\tClass name\n");
        }
        if (objectPropertyNameExpected) {
            sb.append("\tObject property name\n");
        }
        if (dataPropertyNameExpected) {
            sb.append("\tData property name\n");
        }
        if (individualNameExpected) {
            sb.append("\tIndividual name\n");
        }
        if (datatypeNameExpected) {
            sb.append("\tDatatype name\n");
        }
        if (annotationPropertyExpected) {
            sb.append("\tAnnotation property\n");
        }
        if (integerExpected) {
            sb.append("\tInteger\n");
        }
        for (String kw : expectedKeywords) {
            sb.append("\t");
            sb.append(kw);
            sb.append("\n");
        }
        return sb.toString();
    }
}
