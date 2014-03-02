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

import java.util.Collections;
import java.util.Map;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * A class that describes how ontology parsing failed. This class collects parse
 * errors and the parsers that generated the errors.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.2.0
 */
public class UnparsableOntologyException extends OWLOntologyCreationException {

    private static final long serialVersionUID = 40000L;
    private boolean includeStackTraceInMessage = true;
    private final IRI documentIRI;
    private final Map<OWLParser, OWLParserException> exceptions;

    /**
     * @param documentIRI
     *        the document IRI
     * @param exceptions
     *        the map parser-&gt;exceptions
     * @param config
     *        the configuration object
     */
    public UnparsableOntologyException(IRI documentIRI,
            Map<OWLParser, OWLParserException> exceptions,
            OWLOntologyLoaderConfiguration config) {
        super("Could not parse ontology from document IRI: "
                + documentIRI.toQuotedString());
        includeStackTraceInMessage = config.isReportStackTrace();
        this.documentIRI = documentIRI;
        this.exceptions = exceptions;
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        msg.append("Problem parsing ");
        msg.append(documentIRI);
        msg.append("\nCould not parse ontology.  Either a suitable parser could not be found, or parsing failed.  See parser logs below for explanation.\nThe following parsers were tried:\n");
        int counter = 1;
        for (OWLParser parser : exceptions.keySet()) {
            msg.append(counter);
            msg.append(") ");
            msg.append(parser.getName());
            msg.append("\n");
            counter++;
        }
        msg.append("\n\nDetailed logs:\n");
        for (OWLParser parser : exceptions.keySet()) {
            Throwable exception = exceptions.get(parser);
            msg.append("--------------------------------------------------------------------------------\n");
            msg.append("Parser: ");
            msg.append(parser.getName());
            msg.append("\n");
            if (!includeStackTraceInMessage) {
                msg.append(exception.getMessage());
                msg.append("\n\n");
            } else {
                msg.append("    Stack trace:\n");
                Throwable current = exception;
                // print up to five nested causes
                boolean moreStackTraces = true;
                for (int i = 0; i < 5 && moreStackTraces; i++) {
                    msg.append(current.getMessage());
                    StackTraceElement[] stackTrace = current.getStackTrace();
                    for (int stackDepth = 0; stackDepth < 10
                            && stackDepth < stackTrace.length; stackDepth++) {
                        StackTraceElement element = stackTrace[stackDepth];
                        msg.append("        ");
                        msg.append(element.toString());
                        msg.append("\n");
                    }
                    if (current.getCause() != null
                            && current.getCause() != current) {
                        current = current.getCause();
                    } else {
                        moreStackTraces = false;
                    }
                }
                msg.append("\n\n");
            }
        }
        return msg.toString();
    }

    /**
     * Gets the ontology document IRI from which there was an attempt to parse
     * an ontology.
     * 
     * @return The ontology document IRI
     */
    public IRI getDocumentIRI() {
        return documentIRI;
    }

    /**
     * Gets a map that lists the parsers (that were used to parse an ontology)
     * and the errors that they generated.
     * 
     * @return The map of parsers and their errors.
     */
    public Map<OWLParser, OWLParserException> getExceptions() {
        return Collections.unmodifiableMap(exceptions);
    }
}
