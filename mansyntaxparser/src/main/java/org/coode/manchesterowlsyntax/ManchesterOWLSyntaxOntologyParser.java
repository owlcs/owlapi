package org.coode.manchesterowlsyntax;

import org.semanticweb.owl.expression.ParserException;
import org.semanticweb.owl.io.AbstractOWLParser;
import org.semanticweb.owl.io.OWLOntologyInputSource;
import org.semanticweb.owl.io.OWLParserException;
import org.semanticweb.owl.io.OWLParserIOException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyChangeException;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * Date: 13-Aug-2007<br><br>
 */
public class ManchesterOWLSyntaxOntologyParser extends AbstractOWLParser {


    public OWLOntologyFormat parse(OWLOntologyInputSource inputSource, OWLOntology ontology) throws OWLParserException {
        try {
            BufferedReader br = null;
            try {
                if(inputSource.isReaderAvailable()) {
                    br = new BufferedReader(inputSource.getReader());
                }
                else if(inputSource.isInputStreamAvailable()) {
                    br = new BufferedReader(new InputStreamReader(inputSource.getInputStream()));
                }
                else {
                    br = new BufferedReader(new InputStreamReader(getInputStream(inputSource.getPhysicalURI())));
                }
                StringBuilder sb = new StringBuilder();
                String line;
                boolean foundOntology = false;
                while((line = br.readLine()) != null) {
                        sb.append(line);
                    sb.append("\n");
                    if(!foundOntology  && line.trim().length() > 0 && !line.trim().startsWith("//")) {

                        // Should contain an ontology or a namespace
                        if(line.indexOf(ManchesterOWLSyntax.ONTOLOGY.toString()) >= 0) {
                            foundOntology = true;
                        }
                        else if(line.indexOf(ManchesterOWLSyntax.PREFIX.toString()) == -1) {
                            throw new ManchesterOWLSyntaxParserException("Expected 'Ontology:'");
                        }
                    }
                }
                String s = sb.toString();
                ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(getOWLOntologyManager().getOWLDataFactory(),
                                                                                             s);
                parser.parseOntology(getOWLOntologyManager(), ontology);
            }
            finally {
                br.close();
            }
            return new ManchesterOWLSyntaxOntologyFormat();
        }
        catch (IOException e) {
            throw new OWLParserIOException(e);
        }
        catch (ParserException e) {
            throw new ManchesterOWLSyntaxParserException(e);
        }
        catch (OWLOntologyCreationException e) {
            throw new ManchesterOWLSyntaxParserException(e);
        }
        catch (OWLOntologyChangeException e) {
            throw new ManchesterOWLSyntaxParserException(e);
        }
    }
}
