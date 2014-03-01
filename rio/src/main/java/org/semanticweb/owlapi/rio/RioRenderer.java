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
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.openrdf.OpenRDFUtil;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.semanticweb.owlapi.formats.PrefixOWLOntologyFormat;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.rdf.RDFRendererBase;
import org.semanticweb.owlapi.rio.utils.RioUtils;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.VersionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 */
@SuppressWarnings("javadoc")
public class RioRenderer extends RDFRendererBase {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RDFHandler writer;
    private DefaultPrefixManager pm;
    private Set<RDFResource> pendingNodes;
    private AtomicInteger renderedTriples;
    private Set<Statement> renderedStatements;
    private Resource[] contexts;

    public RioRenderer(final OWLOntology ontology, final RDFHandler writer,
            final OWLOntologyFormat format, final Resource... contexts) {
        super(ontology, format);
        OpenRDFUtil.verifyContextNotNull(contexts);
        this.contexts = contexts;
        this.writer = writer;
        pm = new DefaultPrefixManager();
        if (!ontology.isAnonymous()) {
            pm.setDefaultPrefix(ontology.getOntologyID().getOntologyIRI() + "#");
        }
        // copy prefixes out of the given format if it is a
        // PrefixOWLOntologyFormat
        if (format instanceof PrefixOWLOntologyFormat) {
            final PrefixOWLOntologyFormat prefixFormat = (PrefixOWLOntologyFormat) format;
            for (final String prefixName : prefixFormat.getPrefixNames()) {
                pm.setPrefix(prefixName, prefixFormat.getPrefix(prefixName));
            }
        }
        // base = "";
    }

    @Override
    protected void beginDocument() throws IOException {
        pendingNodes = new HashSet<RDFResource>();
        renderedStatements = new HashSet<Statement>();
        renderedTriples = new AtomicInteger(0);
        try {
            writer.startRDF();
        } catch (final RDFHandlerException e) {
            throw new IOException(e);
        }
        // Namespaces
        writeNamespaces();
    }

    @Override
    protected void endDocument() throws IOException {
        writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        try {
            writer.endRDF();
        } catch (final RDFHandlerException e) {
            throw new IOException(e);
        }
        // writer.flush();
        // writer.println();
        // writer.flush();
        if (logger.isTraceEnabled()) {
            logger.trace("pendingNodes={}", pendingNodes.size());
            logger.trace("renderedTriples={}", renderedTriples.toString());
            logger.trace("renderedStatements={}", renderedStatements.size());
        }
        pendingNodes = null;
        renderedTriples = null;
        renderedStatements = null;
    }

    @Override
    protected void endObject() throws IOException {
        writeComment("");
        // writeNewLine();
        // writeNewLine();
        // writeNewLine();
    }

    /**
     * Renders the triples whose subject is the specified node
     * 
     * @param node
     *        The node
     */
    @Override
    public void render(final RDFResource node) throws IOException {
        if (pendingNodes.contains(node)) {
            return;
        }
        pendingNodes.add(node);
        final Collection<RDFTriple> triples = graph.getTriplesForSubject(node,
                true);
        if (logger.isTraceEnabled()) {
            logger.trace("triples.size()={}", triples.size());
            if (!triples.isEmpty()) {
                logger.trace("triples={}", triples);
            }
        }
        for (final RDFTriple triple : triples) {
            renderedTriples.incrementAndGet();
            try {
                // HACK: Need to get a statement without any contexts so that
                // the hashcode will be
                // the same as equals and both will not be context sensitive and
                // we can efficiently
                // check for it without having a custom comparator with a
                // TreeSet
                final Statement referenceStatement = RioUtils
                        .tripleAsStatement(triple);
                if (!renderedStatements.contains(referenceStatement)) {
                    renderedStatements.add(referenceStatement);
                    // then we go back and get context-sensitive statements and
                    // actually pass those
                    // to the RDFHandler
                    final Collection<Statement> statements = RioUtils
                            .tripleAsStatements(triple, contexts);
                    for (final Statement statement : statements) {
                        writer.handleStatement(statement);
                        if (triple.getObject() instanceof RDFResource) {
                            this.render((RDFResource) triple.getObject());
                        }
                    }
                } else if (logger.isTraceEnabled()) {
                    logger.trace(
                            "not printing duplicate statement, or recursing on its object: {}",
                            referenceStatement);
                }
            } catch (final RDFHandlerException e) {
                throw new IOException(e);
            }
        }
        pendingNodes.remove(node);
    }

    @Override
    protected void writeAnnotationPropertyComment(
            final OWLAnnotationProperty prop) throws IOException {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeBanner(final String name) throws IOException {
        writeComment("");
        writeComment("");
        writeComment("#################################################################");
        writeComment("#");
        writeComment("#    " + name);
        writeComment("#");
        writeComment("#################################################################");
        writeComment("");
        writeComment("");
    }

    @Override
    protected void writeClassComment(final OWLClass cls) throws IOException {
        writeComment(cls.getIRI().toString());
    }

    private void writeComment(final String comment) throws IOException {
        try {
            writer.handleComment(comment);
        } catch (final RDFHandlerException e) {
            throw new IOException(e);
        }
        // write("###  ");
        // write(comment);
        // writeNewLine();
        // writeNewLine();
    }

    @Override
    protected void writeDataPropertyComment(final OWLDataProperty prop)
            throws IOException {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeDatatypeComment(final OWLDatatype datatype)
            throws IOException {
        writeComment(datatype.getIRI().toString());
    }

    @Override
    protected void writeIndividualComments(final OWLNamedIndividual ind)
            throws IOException {
        writeComment(ind.getIRI().toString());
    }

    private void writeNamespaces() throws IOException {
        // Send the prefixes from the prefixmanager to the RDFHandler
        // NOTE: These may be derived from a PrefixOWLOntologyFormat
        for (String prefixName : pm.getPrefixName2PrefixMap().keySet()) {
            final String prefix = pm.getPrefix(prefixName);
            // OWLAPI generally stores prefixes with a colon at the end, while
            // Sesame Rio expects
            // prefixes without the colon
            if (prefixName.endsWith(":")) {
                prefixName = prefixName.substring(0, prefixName.length() - 1);
            }
            try {
                writer.handleNamespace(prefixName, prefix);
            } catch (final RDFHandlerException e) {
                throw new IOException(e);
            }
        }
    }

    @Override
    protected void writeObjectPropertyComment(final OWLObjectProperty prop)
            throws IOException {
        writeComment(prop.getIRI().toString());
    }
}
