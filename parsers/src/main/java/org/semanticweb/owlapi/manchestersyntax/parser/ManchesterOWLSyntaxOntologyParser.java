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
package org.semanticweb.owlapi.manchestersyntax.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLParserParameters;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.util.mansyntax.ManchesterOWLSyntaxParser;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.1
 */
public class ManchesterOWLSyntaxOntologyParser extends AbstractOWLParser {

    private static final String COMMENT_START_CHAR = "#";

    private static boolean startsWithMagicNumber(String line) {
        return line.indexOf(ManchesterOWLSyntax.PREFIX.toString()) != -1
            || line.indexOf(ManchesterOWLSyntax.ONTOLOGY.toString()) != -1;
    }

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new ManchesterSyntaxDocumentFormatFactory();
    }

    @Override
    public OWLDocumentFormat parse(Reader r, OWLParserParameters p) {
        try (BufferedReader reader = new BufferedReader(r)) {
            StringBuilder sb = new StringBuilder();
            String line;
            int lineCount = 1;
            // Try to find the "magic number" (Prefix: or Ontology:)
            boolean foundMagicNumber = false;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
                if (!foundMagicNumber) {
                    String trimmedLine = line.trim();
                    if (!trimmedLine.isEmpty() && !trimmedLine.startsWith(COMMENT_START_CHAR)) {
                        // Non-empty line, that is not a comment. The trimmed
                        // line MUST start with our magic number if we are going
                        // to parse the rest of it.
                        if (startsWithMagicNumber(line)) {
                            foundMagicNumber = true;
                            // We have set the found flag - we never end up here
                            // again
                        } else {
                            // Non-empty line that is NOT a comment. We cannot
                            // possibly parse this.
                            int startCol = line.indexOf(trimmedLine) + 1;
                            String msg = String.format(
                                "Encountered '%s' at line %s column %s.  Expected either 'Ontology:' or 'Prefix:'",
                                trimmedLine, Integer.valueOf(lineCount), Integer.valueOf(startCol));
                            throw new ManchesterOWLSyntaxParserException(msg, lineCount, startCol);
                        }
                    }
                }
                lineCount++;
            }
            String s = sb.toString();
            ManchesterOWLSyntaxParser parser = new ManchesterOWLSyntaxParserImpl(p.getConfig(),
                p.getOntology().getOWLOntologyManager().getOWLDataFactory());
            parser.setStringToParse(s);
            return parser.parseOntology(p.getOntology());
        } catch (IOException e) {
            throw new ManchesterOWLSyntaxParserException(e.getMessage(), e, 1, 1);
        }
    }
}
