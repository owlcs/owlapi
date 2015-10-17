/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Queensland
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
 * Copyright 2011, The University of Queensland
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

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.annotation.Nullable;

import org.openrdf.OpenRDFUtil;
import org.openrdf.model.Resource;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;
import org.openrdf.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.formats.RioRDFDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFDocumentFormatFactory;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AbstractOWLStorer;

/**
 * An implementation of {@link OWLStorer} that writes statements to Sesame
 * {@link RDFHandler}s, including {@link RDFWriter} implementations based on the
 * given {@link RioRDFDocumentFormatFactory}.
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioStorer extends AbstractOWLStorer {

    private transient RDFHandler rioHandler;
    private final OWLDocumentFormatFactory ontFormat;
    private final Resource[] contexts;

    /**
     * @param ontologyFormat
     *        format
     * @param rioHandler
     *        rdf handler
     * @param contexts
     *        contexts
     */
    public RioStorer(OWLDocumentFormatFactory ontologyFormat, RDFHandler rioHandler, Resource... contexts) {
        this(ontologyFormat, contexts);
        this.rioHandler = rioHandler;
    }

    /**
     * @param ontologyFormat
     *        format
     * @param contexts
     *        contexts
     */
    public RioStorer(OWLDocumentFormatFactory ontologyFormat, Resource... contexts) {
        OpenRDFUtil.verifyContextNotNull(contexts);
        ontFormat = ontologyFormat;
        this.contexts = contexts;
    }

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontFormat.createFormat().equals(ontologyFormat);
    }

    /**
     * If the {@link RDFFormat} is null, then it is acceptable to return an in
     * memory {@link StatementCollector}. This method will only be called from
     * storeOntology if {@link #setRioHandler(RDFHandler)} is not called with a
     * non-null argument.
     * 
     * @param format
     *        The {@link RDFFormat} for the resulting {@link RDFHandler}, if the
     *        writer parameter is not null.
     * @param writer
     *        The {@link Writer} for the resulting RDFHandler, or null to create
     *        an in-memory collection.
     * @return An implementation of the {@link RDFHandler} interface, based on
     *         the parameters given to this method.
     * @throws OWLOntologyStorageException
     *         If the format does not have an {@link RDFWriter} implementation
     *         available on the classpath.
     */
    protected RDFHandler getRDFHandlerForWriter(@Nullable RDFFormat format, Writer writer)
            throws OWLOntologyStorageException {
        // by default return a StatementCollector if they did not specify a
        // format
        if (format == null) {
            return new StatementCollector();
        } else {
            try {
                return Rio.createWriter(format, writer);
            } catch (final UnsupportedRDFormatException e) {
                throw new OWLOntologyStorageException(e);
            }
        }
    }

    /**
     * If the {@link RDFFormat} is null, then it is acceptable to return an in
     * memory {@link StatementCollector}. This method will only be called from
     * storeOntology if {@link #setRioHandler(RDFHandler)} is not called with a
     * non-null argument.
     * 
     * @param format
     *        The {@link RDFFormat} for the resulting {@link RDFHandler}, if the
     *        writer parameter is not null.
     * @param outputStream
     *        The {@link OutputStream} for the resulting RDFHandler, or null to
     *        create an in-memory collection.
     * @return An implementation of the {@link RDFHandler} interface, based on
     *         the parameters given to this method.
     * @throws OWLOntologyStorageException
     *         If the format does not have an {@link RDFWriter} implementation
     *         available on the classpath.
     */
    protected static RDFHandler getRDFHandlerForOutputStream(@Nullable final RDFFormat format,
            final OutputStream outputStream) throws OWLOntologyStorageException {
        // by default return a StatementCollector if they did not specify a
        // format
        if (format == null) {
            return new StatementCollector();
        } else {
            try {
                return Rio.createWriter(format, outputStream);
            } catch (final UnsupportedRDFormatException e) {
                throw new OWLOntologyStorageException(e);
            }
        }
    }

    /**
     * @return the rioHandler
     */
    public RDFHandler getRioHandler() {
        return rioHandler;
    }

    /**
     * @param rioHandler
     *        the rioHandler to set
     */
    public void setRioHandler(final RDFHandler rioHandler) {
        this.rioHandler = rioHandler;
    }

    @Override
    protected void storeOntology(OWLOntology ontology, PrintWriter writer, OWLDocumentFormat format)
            throws OWLOntologyStorageException {
        // This check is performed to allow any Rio RDFHandler to be used to
        // render the output, even if it does not render to a writer. For
        // example, it could store the triples in memory without serialising
        // them to any particular format.
        if (rioHandler == null) {
            if (!(format instanceof RioRDFDocumentFormat)) {
                throw new OWLOntologyStorageException(
                        "Unable to use RioOntologyStorer to store this format as it is not recognised as a RioRDFOntologyFormat: "
                                + format);
            }
            final RioRDFDocumentFormat rioFormat = (RioRDFDocumentFormat) format;
            if (format.isTextual()) {
                rioHandler = getRDFHandlerForWriter(rioFormat.getRioFormat(), writer);
            } else {
                throw new OWLOntologyStorageException(
                        "Unable to use storeOntology with a Writer as the desired format is not textual. Format was "
                                + format);
            }
        }
        try {
            final RioRenderer ren = new RioRenderer(ontology, rioHandler, format, contexts);
            ren.render();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    @Override
    protected void storeOntology(OWLOntology ontology, OutputStream outputStream, OWLDocumentFormat format)
            throws OWLOntologyStorageException {
        // This check is performed to allow any Rio RDFHandler to be used to
        // render the output, even if it does not render to a writer. For
        // example, it could store the triples in memory without serialising
        // them to any particular format.
        if (rioHandler == null) {
            if (!(format instanceof RioRDFDocumentFormat)) {
                throw new OWLOntologyStorageException(
                        "Unable to use RioOntologyStorer to store this format as it is not recognised as a RioRDFOntologyFormat: "
                                + format);
            }
            final RioRDFDocumentFormat rioFormat = (RioRDFDocumentFormat) format;
            if (format.isTextual()) {
                Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                rioHandler = getRDFHandlerForWriter(rioFormat.getRioFormat(), writer);
            } else {
                rioHandler = getRDFHandlerForOutputStream(rioFormat.getRioFormat(), outputStream);
            }
        }
        try {
            final RioRenderer ren = new RioRenderer(ontology, rioHandler, format, contexts);
            ren.render();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
