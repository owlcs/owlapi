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
package org.semanticweb.owlapi.oboformat;

import java.io.Reader;
import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.semanticweb.owlapi.formats.OBODocumentFormat;
import org.semanticweb.owlapi.formats.OBODocumentFormatFactory;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserParameters;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * OBOformat parser.
 */
public class OBOFormatOWLAPIParser implements OWLParser, Serializable {

    private static final BiConsumer<OWLOntology, OBODoc> defaultBridge = (o, doc) -> bridge(o, doc);
    private static final Consumer<OBODoc> defaultTreatDocument = (doc) -> {
    };
    private final BiConsumer<OWLOntology, OBODoc> bridge;
    private final Consumer<OBODoc> treatDocument;

    /**
     * @param c behaviour for translating from OBO to OWL
     * @param t any extra operations to carry out on the OBO model before translation
     */
    public OBOFormatOWLAPIParser(BiConsumer<OWLOntology, OBODoc> c, Consumer<OBODoc> t) {
        bridge = c;
        treatDocument = t;
    }

    /**
     * @param c behaviour for translating from OBO to OWL
     */
    public OBOFormatOWLAPIParser(BiConsumer<OWLOntology, OBODoc> c) {
        this(c, defaultTreatDocument);
    }

    /**
     * Default constructor: OBO document is translated and no extra operations carried out.
     */
    public OBOFormatOWLAPIParser() {
        this(defaultBridge, defaultTreatDocument);
    }

    /**
     * @param t any extra operations to carry out on the OBO model before translation
     */
    public OBOFormatOWLAPIParser(Consumer<OBODoc> t) {
        this(defaultBridge, t);
    }

    protected static void bridge(OWLOntology o, OBODoc obodoc) {
        // create a translator object and feed it the OBO Document
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(o.getOWLOntologyManager());
        bridge.convert(obodoc, o);
    }

    @Override
    public OWLDocumentFormat parse(Reader r, OWLParserParameters p) {
        OBODoc obodoc = parse(r);
        treatDocument.accept(obodoc);
        bridge.accept(p.getOntology(), obodoc);
        return new OBODocumentFormat();
    }

    protected OBODoc parse(Reader r) {
        OBOFormatParser p = new OBOFormatParser();
        return p.parse(r);
    }

    @Override
    public String getName() {
        return "OWLoboformatParser";
    }

    @Override
    public OWLDocumentFormatFactory getSupportedFormat() {
        return new OBODocumentFormatFactory();
    }
}
