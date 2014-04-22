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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import javax.annotation.Nullable;

import org.openrdf.OpenRDFUtil;
import org.openrdf.model.Resource;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.formats.RioRDFOntologyFormat;
import org.semanticweb.owlapi.formats.RioRDFOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.util.AbstractOWLOntologyStorer;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class RioOntologyStorer extends AbstractOWLOntologyStorer {

    private static final long serialVersionUID = -5659188693033814975L;
    private RDFHandler rioHandler;
    private RioRDFOntologyFormatFactory ontologyFormat;
    private Resource[] contexts;

    /**
     * @param ontologyFormat
     *        format
     * @param rioHandler
     *        rdf handler
     * @param contexts
     *        contexts
     */
    public RioOntologyStorer(RioRDFOntologyFormatFactory ontologyFormat,
            RDFHandler rioHandler, Resource... contexts) {
        this(ontologyFormat, contexts);
        this.rioHandler = rioHandler;
    }

    /**
     * @param ontologyFormat
     *        format
     * @param contexts
     *        contexts
     */
    public RioOntologyStorer(RioRDFOntologyFormatFactory ontologyFormat,
            Resource... contexts) {
        OpenRDFUtil.verifyContextNotNull(contexts);
        this.ontologyFormat = ontologyFormat;
        this.contexts = contexts;
    }

    @Override
    public boolean canStoreOntology(OWLOntologyFormat format) {
        return ontologyFormat.createFormat().equals(format);
    }

    /**
     * If the {@link Writer} is null, then it is acceptable to return an in
     * memory {@link StatementCollector}. This method will only be called from
     * storeOntology if setRioHandler is not called with a non-null argument.
     * 
     * @param format
     *        The {@link RDFFormat} for the resulting {@link RDFHandler}, if the
     *        writer parameter is not null.
     * @param writer
     *        The {@link Writer} for the resulting RDFHandler, or null to create
     *        an in-memory collection.
     * @return An implementation of the {@link RDFHandler} interface, based on
     *         the parameters given to this method.
     */
    protected RDFHandler getRDFHandlerForWriter(@Nullable RDFFormat format,
            Writer writer) {
        // by default return a StatementCollector if they did not specify a
        // format
        if (format == null) {
            return new StatementCollector();
        } else {
            return Rio.createWriter(format, writer);
        }
    }

    /**
     * If the {@link OutputStream} is null, then it is acceptable to return an
     * in memory {@link StatementCollector}. This method will only be called
     * from storeOntology if setRioHandler is not called with a non-null
     * argument.
     * 
     * @param format
     *        The {@link RDFFormat} for the resulting {@link RDFHandler}, if the
     *        writer parameter is not null.
     * @param outputStream
     *        The {@link OutputStream} for the resulting RDFHandler, or null to
     *        create an in-memory collection.
     * @return An implementation of the {@link RDFHandler} interface, based on
     *         the parameters given to this method.
     */
    protected RDFHandler getRDFHandlerForOutputStream(final RDFFormat format,
            final OutputStream outputStream) {
        // by default return a StatementCollector if they did not specify a
        // format
        if (format == null) {
            return new StatementCollector();
        } else {
            return Rio.createWriter(format, outputStream);
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
    protected void storeOntology(final OWLOntology ontology,
            final Writer writer, final OWLOntologyFormat format)
            throws OWLOntologyStorageException {
        if (!(format instanceof RioRDFOntologyFormat)) {
            throw new OWLOntologyStorageException(
                    "Unable to use RioOntologyStorer to store this format as it is not recognised as a RioRDFOntologyFormat: "
                            + format.toString());
        }
        // This check is performed to allow any Rio RDFHandler to be used to
        // render the output, even
        // if it does not render to a writer. For example, it could store the
        // triples in memory
        // without serialising them to any particular format.
        if (rioHandler == null) {
            final RioRDFOntologyFormat rioFormat = (RioRDFOntologyFormat) format;
            if (format.isTextual()) {
                rioHandler = getRDFHandlerForWriter(rioFormat.getRioFormat(),
                        writer);
            } else {
                throw new OWLOntologyStorageException(
                        "Unable to use storeOntology with a Writer as the desired format is not textual. Format was "
                                + format.toString());
            }
        }
        try {
            final RioRenderer ren = new RioRenderer(ontology, rioHandler,
                    format, contexts);
            ren.render();
        } catch (final IOException e) {
            throw new OWLOntologyStorageException(e);
        }
    }
}
