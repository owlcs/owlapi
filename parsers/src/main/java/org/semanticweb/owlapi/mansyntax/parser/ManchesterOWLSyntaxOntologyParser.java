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
package org.semanticweb.owlapi.mansyntax.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.semanticweb.owlapi.annotations.HasPriority;
import org.semanticweb.owlapi.formats.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.mansyntax.renderer.ParserException;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.1
 */
@HasPriority(value = 4)
public class ManchesterOWLSyntaxOntologyParser extends AbstractOWLParser {

    private static final long serialVersionUID = 40000L;
    private static final String COMMENT_START_CHAR = "#";
    private static final String DEFAULT_FILE_ENCODING = "UTF-8";

    @Override
    public String getName() {
        return "ManchesterOWLSyntaxOntologyParser";
    }

    @Override
    protected Class<? extends OWLOntologyFormat> getFormatClass() {
        return ManchesterOWLSyntaxOntologyFormat.class;
    }

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology, OWLOntologyLoaderConfiguration configuration)
            throws IOException {
        try {
            BufferedReader br = null;
            ManchesterOWLSyntaxOntologyFormat format = new ManchesterOWLSyntaxOntologyFormat();
            try {
                if (documentSource.isReaderAvailable()) {
                    br = new BufferedReader(documentSource.getReader());
                } else if (documentSource.isInputStreamAvailable()) {
                    br = new BufferedReader(new InputStreamReader(
                            documentSource.getInputStream(),
                            DEFAULT_FILE_ENCODING));
                } else {
                    br = new BufferedReader(new InputStreamReader(
                            getInputStream(documentSource.getDocumentIRI(),
                                    configuration), DEFAULT_FILE_ENCODING));
                }
                StringBuilder sb = new StringBuilder();
                String line;
                int lineCount = 1;
                // Try to find the "magic number" (Prefix: or Ontology:)
                boolean foundMagicNumber = false;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                    if (!foundMagicNumber) {
                        String trimmedLine = line.trim();
                        if (trimmedLine.length() > 0
                                && !trimmedLine.startsWith(COMMENT_START_CHAR)) {
                            // Non-empty line, that is not a comment. The
                            // trimmed line MUST start with our magic
                            // number if we are going to parse the rest of
                            // it.
                            if (startsWithMagicNumber(line)) {
                                foundMagicNumber = true;
                                // We have set the found flag - we never end
                                // up here again
                            } else {
                                // Non-empty line that is NOT a comment. We
                                // cannot possibly parse this.
                                int startCol = line.indexOf(trimmedLine) + 1;
                                StringBuilder msg = new StringBuilder(
                                        "Encountered '")
                                        .append(trimmedLine)
                                        .append("' at line ")
                                        .append(lineCount)
                                        .append(" column ")
                                        .append(startCol)
                                        .append(".  Expected either 'Ontology:' or 'Prefix:'");
                                throw new ManchesterOWLSyntaxParserException(
                                        msg.toString(), lineCount, startCol);
                            }
                        }
                    }
                    lineCount++;
                }
                String s = sb.toString();
                OWLDataFactory df = ontology.getOWLOntologyManager()
                        .getOWLDataFactory();
                ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(
                        configuration, df, s);
                format = parser.parseOntology(ontology);
            } finally {
                if (br != null) {
                    br.close();
                }
            }
            return format;
        } catch (ParserException e) {
            throw new ManchesterOWLSyntaxParserException(e.getMessage(), e,
                    e.getLineNumber(), e.getColumnNumber());
        }
    }

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology) throws IOException {
        return parse(documentSource, ontology,
                new OWLOntologyLoaderConfiguration());
    }

    private boolean startsWithMagicNumber(String line) {
        return line.indexOf(ManchesterOWLSyntax.PREFIX.toString()) != -1
                || line.indexOf(ManchesterOWLSyntax.ONTOLOGY.toString()) != -1;
    }
}
