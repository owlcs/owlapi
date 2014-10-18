/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2014, Commonwealth Scientific and Industrial Research Organisation
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2014, Commonwealth Scientific and Industrial Research Organisation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.semanticweb.owlapi.rio;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import org.openrdf.model.ValueFactory;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.helpers.RDFParserBase;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.ReaderDocumentSource;
import org.semanticweb.owlapi.io.StreamDocumentSource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManagerFactory;
import org.semanticweb.owlapi.model.OWLRuntimeException;

/**
 * Parses {@link OWLAPIRDFFormat} parsers straight to Sesame {@link RDFHandler}
 * s.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioOWLRDFParser extends RDFParserBase {

    private final OWLAPIRDFFormat owlFormat;
    private final Set<OWLOntologyManagerFactory> ontologyManagerFactories = new HashSet<>();

    /**
     * @param owlFormat
     *        OWL format
     */
    public RioOWLRDFParser(OWLAPIRDFFormat owlFormat) {
        this.owlFormat = owlFormat;
    }

    /**
     * @param owlFormat
     *        OWL format
     * @param valueFactory
     *        value factory
     */
    public RioOWLRDFParser(OWLAPIRDFFormat owlFormat, ValueFactory valueFactory) {
        super(valueFactory);
        this.owlFormat = owlFormat;
    }

    /**
     * @param factories
     *        factories for ontology managers. This method is used for Guice
     *        injection.
     */
    @Inject
    public void setOntologyManagerFactories(
            Set<OWLOntologyManagerFactory> factories) {
        ontologyManagerFactories.clear();
        ontologyManagerFactories.addAll(factories);
    }

    @Override
    public OWLAPIRDFFormat getRDFFormat() {
        return owlFormat;
    }

    @Override
    public void parse(InputStream in, String baseURI) {
        OWLDocumentFormat nextFormat = getRDFFormat().getOWLFormat();
        StreamDocumentSource source = new StreamDocumentSource(in,
                IRI.create(baseURI), nextFormat, getRDFFormat()
                        .getDefaultMIMEType());
        render(source);
    }

    /**
     * @param source
     *        the ontology source to parse
     */
    protected void render(@Nonnull OWLOntologyDocumentSource source) {
        if (ontologyManagerFactories.isEmpty()) {
            throw new OWLRuntimeException(
                    "No ontology manager factories available, parsing is impossible");
        }
        // it is expected that only one implementation of
        // OWLOntologyManagerFactory will be available, but if there is more
        // than one, no harm done
        try {
            for (OWLOntologyManagerFactory f : ontologyManagerFactories) {
                OWLOntology ontology = f.get()
                        .loadOntologyFromOntologyDocument(source);
                new RioRenderer(ontology, getRDFHandler(), getRDFFormat()
                        .getOWLFormat()).render();
                return;
            }
        } catch (OWLOntologyCreationException e) {
            throw new OWLRuntimeException(e);
        }
    }

    @Override
    public void parse(Reader reader, String baseURI) {
        OWLDocumentFormat nextFormat = getRDFFormat().getOWLFormat();
        ReaderDocumentSource source = new ReaderDocumentSource(reader,
                IRI.create(baseURI), nextFormat, getRDFFormat()
                        .getDefaultMIMEType());
        render(source);
    }
}
