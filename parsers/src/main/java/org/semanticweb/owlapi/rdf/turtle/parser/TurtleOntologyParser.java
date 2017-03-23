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
package org.semanticweb.owlapi.rdf.turtle.parser;

import java.io.Reader;

import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLParserParameters;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.rdf.rdfxml.parser.OWLRDFConsumer;

/**
 * The Class TurtleOntologyParser.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.2.0
 */
public class TurtleOntologyParser extends AbstractOWLParser {

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new TurtleDocumentFormatFactory();
    }

    @Override
    public OWLDocumentFormat parse(Reader r, OWLParserParameters p) {
        return parse(p, new StreamProvider(r));
    }

    @Override
    public OWLDocumentFormat parse(String s, OWLParserParameters p) {
        return parse(p, new StringProvider(s));
    }

    protected OWLDocumentFormat parse(OWLParserParameters p, Provider provider) {
        OWLRDFConsumer consumer = new OWLRDFConsumer(p, null);
        TurtleDocumentFormat format = new TurtleDocumentFormat();
        consumer.setOntologyFormat(format);
        consumer.startModel(p.getDocumentIRI());
        TurtleParser parser = new TurtleParser(provider, consumer, p.getDocumentIRI());
        parser.parseDocument();
        p.getOntology().getPrefixManager().copyPrefixesFrom(parser.getPrefixManager());
        return format;
    }
}
