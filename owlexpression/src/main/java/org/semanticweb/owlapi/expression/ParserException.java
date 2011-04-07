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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.util.CollectionFactory;


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

    private List<String> tokenSequence;

    private boolean classNameExpected = false;

    private boolean objectPropertyNameExpected = false;

    private boolean dataPropertyNameExpected = false;

    private boolean individualNameExpected = false;

    private boolean datatypeNameExpected = false;

    private boolean integerExpected = false;

    private boolean annotationPropertyExpected = false;

    private boolean ontologyNameExpected = false;

    private Set<String> expectedKeywords;

    private int startPos;


    public ParserException(List<String> tokenSequence, int startPos, int lineNumber, int columnNumber, boolean ontologyNameExpected, String ... keywords) {
        this(tokenSequence, startPos, lineNumber, columnNumber, false, false, false, false, false, false, keywords);
        this.ontologyNameExpected = ontologyNameExpected;
    }

    public ParserException(List<String> tokenSequence, int startPos, int lineNumber, int columnNumber,
                                                              boolean classNameExpected,
                                                              boolean objectPropertyNameExpected,
                                                              boolean dataPropertyNameExpected,
                                                              boolean individualNameExpected,
                                                              boolean datatypeNameExpected,
                                                              boolean annotationPropertyExpected,
                                                              Set<String> expectedKeywords) {
        this.currentToken = tokenSequence.iterator().next();
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
    }


    public ParserException(List<String> tokenSeqence, int startPos, int lineNumber, int columnNumber,
                                                              boolean classNameExpected,
                                                              boolean objectPropertyNameExpected,
                                                              boolean dataPropertyNameExpected,
                                                              boolean individualNameExpected,
                                                              boolean datatypeNameExpected,
                                                              boolean annotationPropertyExpected,
                                                              String ... keywords) {
        this(tokenSeqence, startPos, lineNumber, columnNumber,
                classNameExpected,
                objectPropertyNameExpected,
                dataPropertyNameExpected,
                individualNameExpected,
                datatypeNameExpected,
                annotationPropertyExpected,
                CollectionFactory.createSet(keywords));
    }


    public ParserException(List<String> tokenSequence, int lineNumber, int columnNumber, boolean integerExpected,
                           int startPos) {
        this(tokenSequence, startPos, lineNumber, columnNumber, false, false, false, false, false, false, new HashSet<String>());
        this.integerExpected = true;
    }


    public ParserException(List<String> tokenSequence, int startPos, int lineNumber, int columnNumber, String ... keywords) {
        this(tokenSequence, startPos, lineNumber, columnNumber, false, false, false, false, false, false, keywords);
    }


    public List<String> getTokenSequence() {
        return tokenSequence;
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

    public boolean isAnnotationPropertyNameExpected() {
        return annotationPropertyExpected;
    }

    public boolean isOntologyNameExpected() {
        return ontologyNameExpected;
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
        if(ontologyNameExpected) {
            sb.append("\tOntology name\n");
        }
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
        if(annotationPropertyExpected) {
            sb.append("\tAnnotation property\n");
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