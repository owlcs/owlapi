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
package org.semanticweb.owlapi.manchestersyntax.renderer;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.util.CollectionFactory;

/**
 * The Class ParserException.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class ParserException extends OWLParserException {

    private final String currentToken;
    private final int lineNumber;
    private final int columnNumber;
    private final List<String> tokenSequence;
    private final boolean classNameExpected;
    private final boolean objectPropertyNameExpected;
    private final boolean dataPropertyNameExpected;
    private final boolean individualNameExpected;
    private final boolean datatypeNameExpected;
    private final boolean annotationPropertyExpected;
    private final Set<String> expectedKeywords = new LinkedHashSet<>();
    private final int startPos;
    private boolean integerExpected;
    private boolean ontologyNameExpected;

    /**
     * @param tokenSequence the token sequence
     * @param startPos the start pos
     * @param lineNumber the line number
     * @param columnNumber the column number
     * @param ontologyNameExpected the ontology name expected
     * @param classNameExpected the class name expected
     * @param objectPropertyNameExpected the object property name expected
     * @param dataPropertyNameExpected the data property name expected
     * @param individualNameExpected the individual name expected
     * @param datatypeNameExpected the datatype name expected
     * @param annotationPropertyExpected the annotation property expected
     * @param integerExpected the integer expected
     * @param expectedKeywords the expected keywords
     */
    public ParserException(List<String> tokenSequence, int startPos, int lineNumber,
        int columnNumber, boolean ontologyNameExpected, boolean classNameExpected,
        boolean objectPropertyNameExpected, boolean dataPropertyNameExpected,
        boolean individualNameExpected, boolean datatypeNameExpected,
        boolean annotationPropertyExpected, boolean integerExpected, Set<String> expectedKeywords) {
        this(tokenSequence, startPos, lineNumber, columnNumber, classNameExpected,
            objectPropertyNameExpected, dataPropertyNameExpected, individualNameExpected,
            datatypeNameExpected, annotationPropertyExpected, expectedKeywords);
        this.ontologyNameExpected = ontologyNameExpected;
        this.integerExpected = integerExpected;
    }

    /**
     * @param tokenSequence the token sequence
     * @param startPos the start pos
     * @param lineNumber the line number
     * @param columnNumber the column number
     * @param classNameExpected the class name expected
     * @param objectPropertyNameExpected the object property name expected
     * @param dataPropertyNameExpected the data property name expected
     * @param individualNameExpected the individual name expected
     * @param datatypeNameExpected the datatype name expected
     * @param annotationPropertyExpected the annotation property expected
     * @param expectedKeywords the expected keywords
     */
    public ParserException(List<String> tokenSequence, int startPos, int lineNumber,
        int columnNumber, boolean classNameExpected, boolean objectPropertyNameExpected,
        boolean dataPropertyNameExpected, boolean individualNameExpected,
        boolean datatypeNameExpected, boolean annotationPropertyExpected,
        @Nullable Set<String> expectedKeywords) {
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
        if (expectedKeywords != null) {
            this.expectedKeywords.addAll(expectedKeywords);
        }
        this.startPos = startPos;
    }

    /**
     * @param tokenSeqence the token seqence
     * @param startPos the start pos
     * @param lineNumber the line number
     * @param columnNumber the column number
     * @param classNameExpected the class name expected
     * @param objectPropertyNameExpected the object property name expected
     * @param dataPropertyNameExpected the data property name expected
     * @param individualNameExpected the individual name expected
     * @param datatypeNameExpected the datatype name expected
     * @param annotationPropertyExpected the annotation property expected
     * @param keywords the keywords
     */
    public ParserException(List<String> tokenSeqence, int startPos, int lineNumber,
        int columnNumber, boolean classNameExpected, boolean objectPropertyNameExpected,
        boolean dataPropertyNameExpected, boolean individualNameExpected,
        boolean datatypeNameExpected, boolean annotationPropertyExpected, String... keywords) {
        this(tokenSeqence, startPos, lineNumber, columnNumber, classNameExpected,
            objectPropertyNameExpected, dataPropertyNameExpected, individualNameExpected,
            datatypeNameExpected, annotationPropertyExpected,
            CollectionFactory.createSet(keywords));
    }

    /**
     * @param tokenSequence the token sequence
     * @param lineNumber the line number
     * @param columnNumber the column number
     * @param integerExpected the integer expected
     * @param startPos the start pos
     */
    public ParserException(List<String> tokenSequence, int lineNumber, int columnNumber,
        boolean integerExpected, int startPos) {
        this(tokenSequence, startPos, lineNumber, columnNumber, false, false, false, false, false,
            false, new HashSet<String>());
        this.integerExpected = integerExpected;
    }

    /**
     * @param tokenSequence the token sequence
     * @param startPos the start pos
     * @param lineNumber the line number
     * @param columnNumber the column number
     * @param keywords the keywords
     */
    public ParserException(List<String> tokenSequence, int startPos, int lineNumber,
        int columnNumber, String... keywords) {
        this(tokenSequence, startPos, lineNumber, columnNumber, false, false, false, false, false,
            false, keywords);
    }

    /**
     * @param delegate delegate
     */
    public ParserException(ParserException delegate) {
        this(delegate.tokenSequence, delegate.startPos, delegate.lineNumber, delegate.columnNumber,
            delegate.ontologyNameExpected, delegate.classNameExpected,
            delegate.objectPropertyNameExpected, delegate.dataPropertyNameExpected,
            delegate.individualNameExpected, delegate.datatypeNameExpected,
            delegate.annotationPropertyExpected, delegate.integerExpected,
            delegate.expectedKeywords);
    }

    /**
     * @return the token sequence
     */
    public List<String> getTokenSequence() {
        return tokenSequence;
    }

    /**
     * @return the start pos
     */
    public int getStartPos() {
        return startPos;
    }

    /**
     * @return true, if is class name expected
     */
    public boolean isClassNameExpected() {
        return classNameExpected;
    }

    /**
     * @return true, if is object property name expected
     */
    public boolean isObjectPropertyNameExpected() {
        return objectPropertyNameExpected;
    }

    /**
     * @return true, if is data property name expected
     */
    public boolean isDataPropertyNameExpected() {
        return dataPropertyNameExpected;
    }

    /**
     * @return true, if is individual name expected
     */
    public boolean isIndividualNameExpected() {
        return individualNameExpected;
    }

    /**
     * @return true, if is datatype name expected
     */
    public boolean isDatatypeNameExpected() {
        return datatypeNameExpected;
    }

    /**
     * @return true, if is annotation property name expected
     */
    public boolean isAnnotationPropertyNameExpected() {
        return annotationPropertyExpected;
    }

    /**
     * @return true, if is ontology name expected
     */
    public boolean isOntologyNameExpected() {
        return ontologyNameExpected;
    }

    /**
     * @return the expected keywords
     */
    public Set<String> getExpectedKeywords() {
        return expectedKeywords;
    }

    /**
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
        expectedKeywords.forEach(kw -> sb.append('\t').append(kw).append('\n'));
        return sb.toString();
    }
}
