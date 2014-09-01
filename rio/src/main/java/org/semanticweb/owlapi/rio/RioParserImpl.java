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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nonnull;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;
import org.openrdf.rio.helpers.BasicParserSettings;
import org.openrdf.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.annotations.HasPriority;
import org.semanticweb.owlapi.formats.RioRDFDocumentFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.util.AnonymousNodeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
@HasPriority(7)
public class RioParserImpl extends AbstractOWLParser implements RioParser {

    private static final long serialVersionUID = 40000L;
    protected static final Logger LOGGER = LoggerFactory
            .getLogger(RioParserImpl.class);
    @Nonnull
    private final RioRDFDocumentFormatFactory owlFormatFactory;

    /**
     * @param nextFormat
     *        format factory
     */
    public RioParserImpl(@Nonnull RioRDFDocumentFormatFactory nextFormat) {
        owlFormatFactory = checkNotNull(nextFormat, "nextFormat cannot be null");
    }

    @Nonnull
    @Override
    public RioRDFDocumentFormatFactory getSupportedFormat() {
        return owlFormatFactory;
    }

    @Override
    public OWLDocumentFormat parse(
            final OWLOntologyDocumentSource documentSource,
            final OWLOntology ontology,
            final OWLOntologyLoaderConfiguration configuration)
            throws IOException {
        try {
            RioOWLRDFConsumerAdapter consumer = new RioOWLRDFConsumerAdapter(
                    ontology, new AnonymousNodeChecker() {

                        @Override
                        public boolean isAnonymousNode(final IRI iri) {
                            // HACK: FIXME: When the mess of having blank nodes
                            // represented as IRIs is
                            // finished remove the genid hack below
                            if (iri.toString().startsWith("_:")
                                    || iri.toString().contains("genid")) {
                                LOGGER.trace("isAnonymousNode(IRI {})", iri);
                                return true;
                            } else {
                                LOGGER.trace("NOT isAnonymousNode(IRI {})", iri);
                                return false;
                            }
                        }

                        @Override
                        public boolean isAnonymousNode(final String iri) {
                            // HACK: FIXME: When the mess of having blank nodes
                            // represented as IRIs is
                            // finished remove the genid hack below
                            if (iri.startsWith("_:") || iri.contains("genid")) {
                                LOGGER.trace("isAnonymousNode(String {})", iri);
                                return true;
                            } else {
                                LOGGER.trace("NOT isAnonymousNode(String {})",
                                        iri);
                                return false;
                            }
                        }

                        // TODO: apparently we should be tracking whether they
                        // gave a name to the blank
                        // node themselves
                        @Override
                        public boolean isAnonymousSharedNode(final String iri) {
                            // HACK: FIXME: When the mess of having blank nodes
                            // represented as IRIs is
                            // finished remove the genid hack below
                            if (iri.startsWith("_:") || iri.contains("genid")) {
                                LOGGER.trace(
                                        "isAnonymousSharedNode(String {})", iri);
                                return true;
                            } else {
                                LOGGER.trace(
                                        "NOT isAnonymousSharedNode(String {})",
                                        iri);
                                return false;
                            }
                        }
                    }, configuration);
            consumer.setOntologyFormat(owlFormatFactory.createFormat());
            String baseUri = "urn:default:baseUri:";
            // Override the default baseUri for non-anonymous ontologies
            if (!ontology.getOntologyID().isAnonymous()
                    && ontology.getOntologyID().getDefaultDocumentIRI()
                            .isPresent()) {
                baseUri = ontology.getOntologyID().getDefaultDocumentIRI()
                        .get().toString();
            }
            RioParserRDFHandler handler = new RioParserRDFHandler(consumer);
            if (documentSource instanceof RioMemoryTripleSource) {
                RioMemoryTripleSource tripleSource = (RioMemoryTripleSource) documentSource;
                Map<String, String> namespaces = tripleSource.getNamespaces();
                Iterator<Statement> statementsIterator = tripleSource
                        .getStatementIterator();
                handler.startRDF();
                for (Entry<String, String> nextNamespace : namespaces
                        .entrySet()) {
                    handler.handleNamespace(nextNamespace.getKey(),
                            nextNamespace.getValue());
                }
                while (statementsIterator.hasNext()) {
                    handler.handleStatement(statementsIterator.next());
                }
                handler.endRDF();
            } else {
                parseDocumentSource(documentSource, baseUri, handler);
            }
            return consumer.getOntologyFormat();
        } catch (final RDFParseException e) {
            throw new OWLParserException(e);
        } catch (final RDFHandlerException e) {
            // See sourceforge bug 3566820 for more information about this
            // branch
            if (e.getCause() != null
                    && e.getCause().getCause() != null
                    && e.getCause().getCause() instanceof UnloadableImportException) {
                throw (UnloadableImportException) e.getCause().getCause();
            } else {
                throw new OWLParserException(e);
            }
        } catch (final UnsupportedRDFormatException e) {
            throw new OWLParserException(e);
        } catch (final MalformedURLException e) {
            throw new OWLParserException(e);
        }
    }

    /**
     * Parse the given document source and return a {@link StatementCollector}
     * containing the RDF statements found in the source.
     * 
     * @param documentSource
     *        An {@link OWLOntologyDocumentSource} containing RDF statements.
     * @param baseUri
     *        The base URI to use when parsing the document source.
     * @param handler
     *        rdf handler
     * @throws UnsupportedRDFormatException
     *         If the document contains a format which is currently unsupported,
     *         based on the parsers that are currently available.
     * @throws IOException
     *         If there is an input/output exception while accessing the
     *         document source.
     * @throws RDFParseException
     *         If there is an error while parsing the document source.
     * @throws RDFHandlerException
     *         If there is an error related to the processing of the RDF
     *         statements after parsing.
     * @throws MalformedURLException
     *         If there are malformed URLs.
     */
    protected void parseDocumentSource(
            final OWLOntologyDocumentSource documentSource,
            final String baseUri, final RDFHandler handler) throws IOException,
            RDFParseException, RDFHandlerException {
        final RDFParser createParser = Rio.createParser(owlFormatFactory
                .getRioFormat());
        createParser.getParserConfig().addNonFatalError(
                BasicParserSettings.VERIFY_DATATYPE_VALUES);
        createParser.getParserConfig().addNonFatalError(
                BasicParserSettings.VERIFY_LANGUAGE_TAGS);
        createParser.setRDFHandler(handler);
        long rioParseStart = System.currentTimeMillis();
        if (owlFormatFactory.isTextual() && documentSource.isReaderAvailable()) {
            createParser.parse(documentSource.getReader(), baseUri);
        } else if (documentSource.isInputStreamAvailable()) {
            createParser.parse(documentSource.getInputStream(), baseUri);
        } else {
            URL url = URI.create(documentSource.getDocumentIRI().toString())
                    .toURL();
            URLConnection conn = url.openConnection();
            createParser.parse(conn.getInputStream(), baseUri);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("rioParse: timing={}", System.currentTimeMillis()
                    - rioParseStart);
        }
    }

    @Override
    public String toString() {
        return getClass().getName() + " : " + owlFormatFactory;
    }

    private static class RioParserRDFHandler implements RDFHandler {

        private static final Logger LOG = LoggerFactory
                .getLogger(RioParserRDFHandler.class);
        private final RDFHandler consumer;
        private long owlParseStart;
        private final Set<Resource> typedLists = new HashSet<>();
        private final ValueFactory vf = ValueFactoryImpl.getInstance();

        RioParserRDFHandler(RDFHandler consumer) {
            this.consumer = consumer;
        }

        @Override
        public void startRDF() {
            owlParseStart = System.currentTimeMillis();
            try {
                consumer.startRDF();
            } catch (RDFHandlerException e) {
                throw new OWLParserException(e);
            }
        }

        @Override
        public void endRDF() {
            try {
                consumer.endRDF();
                if (LOG.isDebugEnabled()) {
                    LOG.debug("owlParse: timing={}", System.currentTimeMillis()
                            - owlParseStart);
                }
            } catch (RDFHandlerException e) {
                throw new OWLParserException(e);
            }
        }

        @Override
        public void handleNamespace(String prefix, String uri) {
            try {
                consumer.handleNamespace(prefix, uri);
            } catch (RDFHandlerException e) {
                throw new OWLParserException(e);
            }
        }

        @Override
        public void handleStatement(Statement nextStatement) {
            if (nextStatement.getPredicate().equals(RDF.FIRST)
                    || nextStatement.getPredicate().equals(RDF.REST)) {
                if (!typedLists.contains(nextStatement.getSubject())) {
                    typedLists.add(nextStatement.getSubject());
                    try {
                        consumer.handleStatement(vf.createStatement(
                                nextStatement.getSubject(), RDF.TYPE, RDF.LIST));
                    } catch (RDFHandlerException e) {
                        throw new OWLParserException(e);
                    }
                    LOG.debug("Implicitly typing list={}", nextStatement);
                }
            } else if (nextStatement.getPredicate().equals(RDF.TYPE)
                    && nextStatement.getObject().equals(RDF.LIST)) {
                if (!typedLists.contains(nextStatement.getSubject())) {
                    LOG.debug("Explicit list type found={}", nextStatement);
                    typedLists.add(nextStatement.getSubject());
                } else {
                    LOG.debug(
                            "duplicate rdf:type rdf:List statements found={}",
                            nextStatement);
                }
            }
            try {
                consumer.handleStatement(nextStatement);
            } catch (RDFHandlerException e) {
                throw new OWLParserException(e);
            }
        }

        @Override
        public void handleComment(String comment) {
            // do nothing
        }
    }
}
