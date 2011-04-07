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

package org.semanticweb.owlapi.io;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11-Apr-2008<br><br>
 * </p>
 * A class that describes how ontology parsing failed.  This class collects parse errors and the parsers that
 * generated the errors.
 */
public class UnparsableOntologyException extends OWLOntologyCreationException {

    private static boolean includeStackTraceInMessage = false;

    private IRI documentIRI;

    private Map<OWLParser, OWLParserException> exceptions;

    public UnparsableOntologyException(IRI documentIRI, Map<OWLParser, OWLParserException> exceptions) {
        super("Could not parse ontology from document IRI: " + documentIRI.toQuotedString());
        this.documentIRI = documentIRI;
        this.exceptions = new LinkedHashMap<OWLParser, OWLParserException>(exceptions);
    }


    @Override
	public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append("Problem parsing ");
        msg.append(documentIRI);
        msg.append("\n");
        msg.append("Could not parse ontology.  Either a suitable parser could not be found, or " + "parsing failed.  See parser logs below for explanation.\n");
        msg.append("The following parsers were tried:\n");
        int counter = 1;
        for (OWLParser parser : exceptions.keySet()) {
            msg.append(counter);
            msg.append(") ");
            msg.append(parser.getClass().getSimpleName());
            msg.append("\n");
            counter++;
        }
        msg.append("\n\nDetailed logs:\n");
        for (OWLParser parser : exceptions.keySet()) {
            Throwable exception = exceptions.get(parser);
            msg.append("--------------------------------------------------------------------------------\n");
            msg.append("Parser: ");
            msg.append(parser.getClass().getSimpleName());
            msg.append("\n");
            msg.append(exception.getMessage());
            msg.append("\n\n");
            if (includeStackTraceInMessage) {
                msg.append("    Stack trace:\n");
                for (StackTraceElement element : exception.getStackTrace()) {
                    msg.append("        ");
                    msg.append(element.toString());
                    msg.append("\n");
                }
                msg.append("\n\n");
            }
        }
        return msg.toString();
    }


    /**
     * Gets the ontology document IRI from which there was an attempt to parse an ontology
     * @return The ontology document IRI
     */
    public IRI getDocumentIRI() {
        return documentIRI;
    }


    /**
     * Determines if the stack trace for each parse exception is
     * included in the getMessage() method.
     * @return <code>true</code> if the stack trace is included in the
     * message for this exception, other wise <code>false</code>.
     */
    public static boolean isIncludeStackTraceInMessage() {
        return includeStackTraceInMessage;
    }


    /**
     * Specifies whether the stack trace for each parser exception should be included in the message
     * generated by this exception - this can be useful for debugging purposes, but can bloat the
     * message for end user usage.
     * @param includeStackTraceInMessage Set to <code>true</code> to indicate that the stack
     * trace for each parser exception should be included in the message for this exception,
     * otherwise set to <code>false</code>.
     */
    public static void setIncludeStackTraceInMessage(boolean includeStackTraceInMessage) {
        UnparsableOntologyException.includeStackTraceInMessage = includeStackTraceInMessage;
    }


    /**
     * Gets a map that lists the parsers (that were used to parse an
     * ontology) and the errors that they generated.
     * @return The map of parsers and their errors.
     */
    public Map<OWLParser, OWLParserException> getExceptions() {
        return Collections.unmodifiableMap(exceptions);
    }
}
