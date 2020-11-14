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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.verifyNotNull;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Nullable;

import org.eclipse.rdf4j.OpenRDFUtil;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFWriter;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.rio.RioSetting;
import org.eclipse.rdf4j.rio.UnsupportedRDFormatException;
import org.eclipse.rdf4j.rio.helpers.BasicWriterSettings;
import org.eclipse.rdf4j.rio.helpers.JSONLDSettings;
import org.eclipse.rdf4j.rio.helpers.JSONSettings;
import org.eclipse.rdf4j.rio.helpers.NTriplesWriterSettings;
import org.eclipse.rdf4j.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.formats.RioRDFDocumentFormat;
import org.semanticweb.owlapi.formats.RioRDFDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormatFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.OWLStorer;
import org.semanticweb.owlapi.util.AbstractOWLStorer;

/**
 * An implementation of {@link OWLStorer} that writes statements to Sesame {@link RDFHandler}s,
 * including {@link RDFWriter} implementations based on the given
 * {@link RioRDFDocumentFormatFactory}.
 *
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioStorer extends AbstractOWLStorer {

    private final OWLDocumentFormatFactory ontFormat;
    private final Resource[] contexts;
    @Nullable
    private transient RDFHandler rioHandler;

    /**
     * @param ontologyFormat format
     * @param rioHandler RDF handler
     * @param contexts contexts
     */
    public RioStorer(OWLDocumentFormatFactory ontologyFormat, RDFHandler rioHandler,
        Resource... contexts) {
        this(ontologyFormat, contexts);
        this.rioHandler = rioHandler;
    }

    /**
     * @param ontologyFormat format
     * @param contexts contexts
     */
    public RioStorer(OWLDocumentFormatFactory ontologyFormat, Resource... contexts) {
        OpenRDFUtil.verifyContextNotNull(contexts);
        ontFormat = ontologyFormat;
        this.contexts = contexts;
    }

    /**
     * If the {@link RDFFormat} is null, then it is acceptable to return an in memory
     * {@link StatementCollector}. This method will only be called from {@code storeOntology} if
     * {@link #setRioHandler(RDFHandler)} is not called with a non-null argument.
     *
     * @param format The {@link RDFFormat} for the resulting {@link RDFHandler}, if the writer
     *        parameter is not null.
     * @param outputStream The {@link java.io.OutputStream} for the resulting RDFHandler, or null to
     *        create an in-memory collection.
     * @param baseIRI base IRI
     * @return An implementation of the {@link RDFHandler} interface, based on the parameters given
     *         to this method.
     * @throws OWLOntologyStorageException If the format does not have an {@link RDFWriter}
     *         implementation available on the classpath.
     */
    protected static RDFHandler getRDFHandlerForOutputStream(@Nullable RDFFormat format,
        OutputStream outputStream, @Nullable String baseIRI) throws OWLOntologyStorageException {
        // by default return a StatementCollector if they did not specify a
        // format
        if (format == null) {
            return new StatementCollector();
        }
        try {
            return getWriter(format, outputStream, baseIRI);
        } catch (final UnsupportedRDFormatException | URISyntaxException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    protected RDFWriter getWriter(RDFFormat format, Writer writer, @Nullable String baseIRI)
        throws URISyntaxException {
        if (baseIRI == null || format.equals(RDFFormat.RDFXML)) {
            // do not set a base iri for RDFXML, it causes the output IRIs to be relativised and the
            // parser code does not handle resolution properly.
            return Rio.createWriter(format, writer);
        }
        return Rio.createWriter(format, writer, baseIRI);
    }

    protected static RDFWriter getWriter(RDFFormat format, OutputStream writer,
        @Nullable String baseIRI) throws URISyntaxException {
        if (baseIRI == null || format.equals(RDFFormat.RDFXML)) {
            // do not set a base iri for RDFXML, it causes the output IRIs to be relativised and the
            // parser code does not handle resolution properly.
            return Rio.createWriter(format, writer);
        }
        return Rio.createWriter(format, writer, baseIRI);
    }

    @Override
    public boolean canStoreOntology(OWLDocumentFormat ontologyFormat) {
        return ontFormat.createFormat().equals(ontologyFormat);
    }

    /**
     * If the {@link RDFFormat} is null, then it is acceptable to return an in memory
     * {@link StatementCollector}. This method will only be called from {@code storeOntology} if
     * {@link #setRioHandler(RDFHandler)} is not called with a non-null argument.
     *
     * @param format The {@link RDFFormat} for the resulting {@link RDFHandler}, if the writer
     *        parameter is not null.
     * @param writer The {@link Writer} for the resulting RDFHandler, or null to create an in-memory
     *        collection.
     * @param baseIRI base IRI
     * @return An implementation of the {@link RDFHandler} interface, based on the parameters given
     *         to this method.
     * @throws OWLOntologyStorageException If the format does not have an {@link RDFWriter}
     *         implementation available on the classpath.
     */
    protected RDFHandler getRDFHandlerForWriter(@Nullable RDFFormat format, Writer writer,
        @Nullable String baseIRI) throws OWLOntologyStorageException {
        // by default return a StatementCollector if they did not specify a
        // format
        if (format == null) {
            return new StatementCollector();
        }
        try {
            return getWriter(format, writer, baseIRI);
        } catch (final UnsupportedRDFormatException | URISyntaxException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    /**
     * @return the RDF handler
     */
    @Nullable
    public RDFHandler getRioHandler() {
        return rioHandler;
    }

    /**
     * @param rioHandler the RDF handler to set
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
                rioHandler = getRDFHandlerForWriter(rioFormat.getRioFormat(), writer,
                    format.isPrefixOWLDocumentFormat()
                        ? format.asPrefixOWLDocumentFormat().getDefaultPrefix()
                        : null);
            } else {
                throw new OWLOntologyStorageException(
                    "Unable to use storeOntology with a Writer as the desired format is not textual. Format was "
                        + format);
            }
        }
        try {
            // if this is a writer rather than a statement collector, set its config from the format
            // parameters, if any
            addSettingsIfPresent(format);
            final RioRenderer ren =
                new RioRenderer(ontology, verifyNotNull(rioHandler), format, contexts);
            ren.render();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    @Override
    protected void storeOntology(OWLOntology ontology, OutputStream outputStream,
        OWLDocumentFormat format) throws OWLOntologyStorageException {
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
                Writer writer = new BufferedWriter(
                    new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                rioHandler = getRDFHandlerForWriter(rioFormat.getRioFormat(), writer,
                    format.isPrefixOWLDocumentFormat()
                        ? format.asPrefixOWLDocumentFormat().getDefaultPrefix()
                        : null);
            } else {
                rioHandler = getRDFHandlerForOutputStream(rioFormat.getRioFormat(), outputStream,
                    format.isPrefixOWLDocumentFormat()
                        ? format.asPrefixOWLDocumentFormat().getDefaultPrefix()
                        : null);
            }
        }
        try {
            // if this is a writer rather than a statement collector, set its config from the format
            // parameters, if any
            addSettingsIfPresent(format);
            final RioRenderer ren =
                new RioRenderer(ontology, verifyNotNull(rioHandler), format, contexts);
            ren.render();
        } catch (OWLRuntimeException e) {
            throw new OWLOntologyStorageException(e);
        }
    }

    // These warnings are suppressed because the types cannot be easily determined here without
    // forcing constraints that might need to be updated when Rio introduces new settings.
    @SuppressWarnings({"rawtypes", "null", "unchecked"})
    protected void addSettingsIfPresent(OWLDocumentFormat format) {
        if (rioHandler instanceof RDFWriter) {
            RDFWriter w = (RDFWriter) rioHandler;
            Collection<RioSetting<?>> supportedSettings = knownSettings(w);
            for (RioSetting r : supportedSettings) {
                Serializable v = format.getParameter(r, null);
                if (v != null) {
                    w.getWriterConfig().set(r, v);
                }
            }
        }
    }

    protected Collection<RioSetting<?>> knownSettings(RDFWriter w) {
        try {
            return w.getSupportedSettings();
        } catch (UnsupportedOperationException e) {
            LOGGER.debug(
                "Bug in RIO means this exception is thrown in some formats where an unmodifiable class is modified.  As a workaround, OWLAPI will try all the known settings - relying on the caller to only use supported settings.",
                e);
            return Arrays.asList(JSONSettings.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER,
                JSONSettings.ALLOW_COMMENTS, JSONSettings.ALLOW_NON_NUMERIC_NUMBERS,
                JSONSettings.ALLOW_NUMERIC_LEADING_ZEROS, JSONSettings.ALLOW_SINGLE_QUOTES,
                JSONSettings.ALLOW_TRAILING_COMMA, JSONSettings.ALLOW_UNQUOTED_CONTROL_CHARS,
                JSONSettings.ALLOW_UNQUOTED_FIELD_NAMES, JSONSettings.ALLOW_YAML_COMMENTS,
                JSONSettings.INCLUDE_SOURCE_IN_LOCATION, JSONSettings.STRICT_DUPLICATE_DETECTION,
                JSONLDSettings.COMPACT_ARRAYS, JSONLDSettings.DOCUMENT_LOADER,
                JSONLDSettings.HIERARCHICAL_VIEW, JSONLDSettings.JSONLD_MODE,
                JSONLDSettings.OPTIMIZE, JSONLDSettings.PRODUCE_GENERALIZED_RDF,
                JSONLDSettings.USE_NATIVE_TYPES, JSONLDSettings.USE_RDF_TYPE,
                BasicWriterSettings.BASE_DIRECTIVE,
                BasicWriterSettings.CONVERT_RDF_STAR_TO_REIFICATION,
                BasicWriterSettings.ENCODE_RDF_STAR, BasicWriterSettings.INLINE_BLANK_NODES,
                BasicWriterSettings.PRETTY_PRINT,
                BasicWriterSettings.RDF_LANGSTRING_TO_LANG_LITERAL,
                BasicWriterSettings.XSD_STRING_TO_PLAIN_LITERAL,
                NTriplesWriterSettings.ESCAPE_UNICODE);
            // parsing only settings, not used here
            // NTriplesParserSettings
            // RDFJSONParserSettings
            // XMLParserSettings
            // TriXParserSettings
            // TurtleParserSettings
        }
    }
}
