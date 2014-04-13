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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Set;

import org.obolibrary.obo2owl.OWLAPIObo2Owl;
import org.obolibrary.oboformat.model.OBODoc;
import org.obolibrary.oboformat.parser.OBOFormatParser;
import org.obolibrary.oboformat.parser.OBOFormatParserException;
import org.semanticweb.owlapi.annotations.HasPriority;
import org.semanticweb.owlapi.formats.OBOOntologyFormat;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.util.OWLOntologyFormatFactoryImpl;

/** oboformat parser */
@HasPriority(value = 5)
public class OBOFormatOWLAPIParser implements OWLParser, Serializable {

    private static final long serialVersionUID = 40000L;

    @Override
    public OWLOntologyFormat parse(IRI documentIRI, OWLOntology ontology)
            throws IOException, OWLOntologyChangeException {
        try {
            parse(documentIRI, null, ontology);
        } catch (OBOFormatParserException e) {
            throw new OWLParserException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OWLParserException(e);
        }
        OBOOntologyFormat format = new OBOOntologyFormat();
        return format;
    }

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology) throws IOException,
            OWLOntologyChangeException {
        try {
            parse(null, documentSource, ontology);
        } catch (OBOFormatParserException e) {
            throw new OWLParserException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OWLParserException(e);
        }
        OBOOntologyFormat format = new OBOOntologyFormat();
        return format;
    }

    @Override
    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource,
            OWLOntology ontology, OWLOntologyLoaderConfiguration configuration)
            throws IOException, OWLOntologyChangeException {
        try {
            parse(null, documentSource, ontology);
        } catch (OBOFormatParserException e) {
            throw new OWLParserException(e);
        } catch (OWLOntologyCreationException e) {
            throw new OWLParserException(e);
        }
        OBOOntologyFormat format = new OBOOntologyFormat();
        return format;
    }

    private OWLOntology parse(IRI iri, OWLOntologyDocumentSource source,
            OWLOntology in) throws MalformedURLException, IOException,
            OWLOntologyCreationException {
        OBOFormatParser p = new OBOFormatParser();
        OBODoc obodoc = null;
        if (iri != null) {
            obodoc = p.parse(iri.toURI().toURL());
        } else {
            if (source.isReaderAvailable()) {
                obodoc = p.parse(new BufferedReader(source.getReader()));
            } else if (source.isInputStreamAvailable()) {
                obodoc = p.parse(new BufferedReader(new InputStreamReader(
                        source.getInputStream())));
            } else {
                return parse(source.getDocumentIRI(), null, in);
            }
        }
        // create a translator object and feed it the OBO Document
        OWLAPIObo2Owl bridge = new OWLAPIObo2Owl(in.getOWLOntologyManager());
        OWLOntology ontology = bridge.convert(obodoc, in);
        if (ontology == in) {
            return in;
        }
        return ontology;
    }

    @Override
    public String getName() {
        return "OWLoboformatParser";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Class<OWLOntologyFormat>> getSupportedFormatClasses() {
        return Collections
                .singleton((Class<OWLOntologyFormat>) (Class<? extends OWLOntologyFormat>) OBOOntologyFormat.class);
    }

    @Override
    public Set<OWLOntologyFormatFactory> getSupportedFormats() {
        return Collections
                .singleton((OWLOntologyFormatFactory) new OWLOntologyFormatFactoryImpl<OBOOntologyFormat>(
                        OBOOntologyFormat.class));
    }
}
