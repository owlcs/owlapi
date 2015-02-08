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
package org.semanticweb.owlapi.rdf.rdfxml.parser;

import java.io.IOException;

import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLOntologyInputSourceException;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class RDFXMLParser extends AbstractOWLParser {

    private static final long serialVersionUID = 40000L;

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new RDFXMLDocumentFormatFactory();
    }

    @Override
    public OWLDocumentFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) {
        try {
            final RDFXMLDocumentFormat format = new RDFXMLDocumentFormat();
            RDFParser parser = new RDFParser() {

                @Override
                public void startPrefixMapping(String prefix, String uri)
                        throws SAXException {
                    super.startPrefixMapping(prefix, uri);
                    format.setPrefix(prefix, uri);
                }
            };
            OWLRDFConsumer consumer = new OWLRDFConsumer(ontology,
                    configuration);
            consumer.setIRIProvider(parser);
            consumer.setOntologyFormat(format);
            InputSource is = getInputSource(documentSource, configuration);
            parser.parse(is, consumer);
            return format;
        } catch (RDFParserException | SAXException
                | OWLOntologyInputSourceException | IOException e) {
            throw new OWLRDFXMLParserException(e);
        }
    }
}
