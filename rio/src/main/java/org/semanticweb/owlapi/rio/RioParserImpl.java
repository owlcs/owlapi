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
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.UnsupportedRDFormatException;
import org.openrdf.rio.helpers.StatementCollector;
import org.semanticweb.owlapi.annotations.HasPriority;
import org.semanticweb.owlapi.formats.RioRDFOntologyFormat;
import org.semanticweb.owlapi.formats.RioRDFOntologyFormatFactory;
import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyFormatFactory;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.rio.utils.OWLAPICompatibleComparator;
import org.semanticweb.owlapi.util.AnonymousNodeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br>
 * <br>
 */
@HasPriority(value = 7)
public class RioParserImpl extends AbstractOWLParser implements RioParser {

    private static final long serialVersionUID = 40000L;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private RioRDFOntologyFormatFactory owlFormatFactory;
    private Set<OWLOntologyFormatFactory> supportedFormats;

    /**
     * @param nextFormat
     *        format factory
     */
    public RioParserImpl(RioRDFOntologyFormatFactory nextFormat) {
        owlFormatFactory = nextFormat;
        supportedFormats = Collections
                .singleton((OWLOntologyFormatFactory) owlFormatFactory);
    }

    @Override
    public Set<Class<OWLOntologyFormat>> getSupportedFormatClasses() {
        // not needed for this parser
        return Collections.emptySet();
    }

    @Override
    protected Class<? extends OWLOntologyFormat> getFormatClass() {
        // not needed for this parser
        return null;
    }

    @Override
    public RioRDFOntologyFormat getParserFormat() {
        return owlFormatFactory.createFormat();
    }

    @Override
    public Set<OWLOntologyFormatFactory> getSupportedFormats() {
        return supportedFormats;
    }

    @Override
    public OWLOntologyFormat parse(
            final OWLOntologyDocumentSource documentSource,
            final OWLOntology ontology) throws IOException {
        return this.parse(documentSource, ontology,
                new OWLOntologyLoaderConfiguration());
    }

    @Override
    public OWLOntologyFormat parse(
            final OWLOntologyDocumentSource documentSource,
            final OWLOntology ontology,
            final OWLOntologyLoaderConfiguration configuration)
            throws IOException {
        final InputSource is = null;
        try {
            // IRIProvider prov = new IRIProvider() {
            // public IRI getIRI(String s) {
            // return parser.getIRI(s);
            // }
            // };
            // consumer.setIRIProvider(parser);
            RioOWLRDFConsumerAdapter consumer = new RioOWLRDFConsumerAdapter(
                    ontology, new AnonymousNodeChecker() {

                        @Override
                        public boolean isAnonymousNode(final IRI iri) {
                            // HACK: FIXME: When the mess of having blank nodes
                            // represented as IRIs is
                            // finished remove the genid hack below
                            if (iri.toString().startsWith("_:")
                                    || iri.toString().contains("genid")) {
                                log.trace("isAnonymousNode(IRI {})", iri);
                                return true;
                            } else {
                                log.trace("NOT isAnonymousNode(IRI {})", iri);
                                return false;
                            }
                        }

                        @Override
                        public boolean isAnonymousNode(final String iri) {
                            // HACK: FIXME: When the mess of having blank nodes
                            // represented as IRIs is
                            // finished remove the genid hack below
                            if (iri.startsWith("_:") || iri.contains("genid")) {
                                log.trace("isAnonymousNode(String {})", iri);
                                return true;
                            } else {
                                log.trace("NOT isAnonymousNode(String {})", iri);
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
                                log.trace("isAnonymousSharedNode(String {})",
                                        iri);
                                return true;
                            } else {
                                log.trace(
                                        "NOT isAnonymousSharedNode(String {})",
                                        iri);
                                return false;
                            }
                        }
                    }, configuration);
            if (owlFormatFactory != null) {
                consumer.setOntologyFormat(owlFormatFactory.createFormat());
            }
            String baseUri = "urn:default:baseUri:";
            if (ontology.getOntologyID() != null
                    && ontology.getOntologyID().getDefaultDocumentIRI() != null) {
                baseUri = ontology.getOntologyID().getDefaultDocumentIRI()
                        .toString();
            }
            Iterator<Statement> statementsIterator;
            Map<String, String> namespaces;
            if (documentSource instanceof RioMemoryTripleSource) {
                namespaces = ((RioMemoryTripleSource) documentSource)
                        .getNamespaces();
                statementsIterator = ((RioMemoryTripleSource) documentSource)
                        .getStatementIterator();
            } else {
                StatementCollector rdfHandler = parseDocumentSource(
                        documentSource, baseUri);
                log.debug("RDFHandler statements = {}", rdfHandler
                        .getStatements().size());
                statementsIterator = rdfHandler.getStatements().iterator();
                namespaces = rdfHandler.getNamespaces();
                // sort the statements so that "Resource" triples are processed
                // before their
                // "Literal"
                // counterparts
                final List<Statement> statements = new ArrayList<Statement>(
                        rdfHandler.getStatements());
                Collections.sort(statements, new OWLAPICompatibleComparator());
            }
            long owlParseStart = System.currentTimeMillis();
            // then go through the process manually on the actual consumer using
            // our sorted list of
            // statements
            consumer.startRDF();
            for (final String nextPrefix : namespaces.keySet()) {
                consumer.handleNamespace(nextPrefix, namespaces.get(nextPrefix));
            }
            AtomicInteger statementCount = new AtomicInteger(0);
            Set<Resource> typedLists = Collections
                    .newSetFromMap(new ConcurrentHashMap<Resource, Boolean>());
            while (statementsIterator.hasNext()) {
                Statement nextStatement = statementsIterator.next();
                // Sesame follows the RDF specification closely and does not
                // generate list rdf:type
                // rdf:List triple, where owlapi requires this triple to
                // recognise a list
                // HACK: Add an extra triple explicitly typing the subject of
                // any statement
                // containing rdf:first as the predicate
                if (nextStatement.getPredicate().equals(RDF.FIRST)
                        || nextStatement.getPredicate().equals(RDF.REST)) {
                    if (!typedLists.contains(nextStatement.getSubject())) {
                        typedLists.add(nextStatement.getSubject());
                        statementCount.incrementAndGet();
                        consumer.handleStatement(ValueFactoryImpl.getInstance()
                                .createStatement(nextStatement.getSubject(),
                                        RDF.TYPE, RDF.LIST));
                        log.debug("Implicitly typing list={}", nextStatement);
                    }
                } else if (nextStatement.getPredicate().equals(RDF.TYPE)
                        && nextStatement.getObject().equals(RDF.LIST)) {
                    if (!typedLists.contains(nextStatement.getSubject())) {
                        log.debug("Explicit list type found={}", nextStatement);
                        typedLists.add(nextStatement.getSubject());
                    } else {
                        log.debug(
                                "duplicate rdf:type rdf:List statements found={}",
                                nextStatement);
                    }
                }
                statementCount.incrementAndGet();
                consumer.handleStatement(nextStatement);
            }
            consumer.endRDF();
            if (log.isDebugEnabled()) {
                log.debug("owlParse: timing={}", System.currentTimeMillis()
                        - owlParseStart);
                log.debug("owl handled statements={}", statementCount.get());
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
        } finally {
            try {
                if (is != null && is.getByteStream() != null) {
                    is.getByteStream().close();
                }
            } catch (final IOException ioe) {
                log.error(
                        "Found unexpected IOException while closing byteStream",
                        ioe);
            }
            try {
                if (is != null && is.getCharacterStream() != null) {
                    is.getCharacterStream().close();
                }
            } catch (final IOException ioe) {
                log.error(
                        "Found unexpected IOException while closing character stream",
                        ioe);
            }
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
     * @return A {@link StatementCollector} containing the statements from the
     *         source.
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
    protected StatementCollector parseDocumentSource(
            final OWLOntologyDocumentSource documentSource, String baseUri)
            throws IOException, RDFParseException, RDFHandlerException,
            MalformedURLException {
        // parse into a collection of statements so that we can sort things to
        // make sure the
        // process is starting from a known point
        final StatementCollector rdfHandler = new StatementCollector();
        final RDFParser createParser = Rio.createParser(owlFormatFactory
                .getRioFormat());
        createParser.setRDFHandler(rdfHandler);
        long rioParseStart = System.currentTimeMillis();
        if (owlFormatFactory.isTextual() && documentSource.isReaderAvailable()) {
            createParser.parse(documentSource.getReader(), baseUri);
        } else if (documentSource.isInputStreamAvailable()) {
            createParser.parse(documentSource.getInputStream(), baseUri);
        } else {
            createParser
                    .parse(URI
                            .create(documentSource.getDocumentIRI().toString())
                            .toURL().openConnection().getInputStream(), baseUri);
        }
        if (log.isDebugEnabled()) {
            log.debug("rioParse: timing={}", System.currentTimeMillis()
                    - rioParseStart);
        }
        return rdfHandler;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + " : " + owlFormatFactory.toString();
    }
}
