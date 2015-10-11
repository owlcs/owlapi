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
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

import org.openrdf.OpenRDFUtil;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.RDFRendererBase;
import org.semanticweb.owlapi.rio.utils.RioUtils;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.VersionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioRenderer extends RDFRendererBase {

    private static final Logger logger = LoggerFactory
        .getLogger(RioRenderer.class);
    private final RDFHandler writer;
    private final DefaultPrefixManager pm;
    @Nonnull
    private final Set<RDFResource> pendingNodes = new LinkedHashSet<>();
    @Nonnull
    private final Set<RDFTriple> renderedStatements = new LinkedHashSet<>();
    private final Resource[] contexts;
    private Map<RDFTriple, RDFResourceBlankNode> triplesWithRemappedNodes;

    /**
     * @param ontology
     *        ontology
     * @param writer
     *        writer
     * @param format
     *        format
     * @param contexts
     *        contexts
     */
    public RioRenderer(@Nonnull final OWLOntology ontology,
        final RDFHandler writer, final OWLDocumentFormat format,
        final Resource... contexts) {
        super(ontology, format);
        OpenRDFUtil.verifyContextNotNull(contexts);
        this.contexts = contexts;
        this.writer = writer;
        pm = new DefaultPrefixManager();
        if (!ontology.isAnonymous()) {
            String ontologyIRIString = ontology.getOntologyID()
                .getOntologyIRI().get().toString();
            String defaultPrefix = ontologyIRIString;
            if (!ontologyIRIString.endsWith("/")) {
                defaultPrefix = ontologyIRIString + '#';
            }
            pm.setDefaultPrefix(defaultPrefix);
        }
        // copy prefixes out of the given format if it is a
        // PrefixOWLOntologyFormat
        if (format instanceof PrefixDocumentFormat) {
            PrefixDocumentFormat prefixFormat = (PrefixDocumentFormat) format;
            pm.copyPrefixesFrom(prefixFormat);
            pm.setPrefixComparator(prefixFormat.getPrefixComparator());
        }
        // base = "";
    }

    @Override
    protected void beginDocument() throws IOException {
        pendingNodes.clear();
        renderedStatements.clear();
        try {
            writer.startRDF();
        } catch (RDFHandlerException e) {
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
        } catch (RDFHandlerException e) {
            throw new IOException(e);
        }
        if (logger.isTraceEnabled()) {
            logger.trace("pendingNodes={}", pendingNodes.size());
            logger.trace("renderedStatements={}", renderedStatements.size());
        }
        pendingNodes.clear();
        renderedStatements.clear();
    }

    @Override
    protected void endObject() throws IOException {
        writeComment("");
    }

    @Override
    protected void renderOntologyHeader() throws IOException {
        super.renderOntologyHeader();
        triplesWithRemappedNodes = graph.computeRemappingForSharedNodes();
    }

    @Override
    protected void createGraph(Set<? extends OWLObject> objects) {
        super.createGraph(objects);
        triplesWithRemappedNodes = graph.computeRemappingForSharedNodes();
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
        final Collection<RDFTriple> triples = graph.getTriplesForSubject(node);
        if (logger.isTraceEnabled()) {
            logger.trace("triples.size()={}", triples.size());
            if (!triples.isEmpty()) {
                logger.trace("triples={}", triples);
            }
        }
        for (final RDFTriple triple : triples) {
            RDFTriple tripleToRender = triple;
            RDFResourceBlankNode remappedNode = null;
            if (triplesWithRemappedNodes != null) {
                remappedNode = triplesWithRemappedNodes.get(tripleToRender);
            }
            if (remappedNode != null) {
                tripleToRender = new RDFTriple(tripleToRender.getSubject(),
                    tripleToRender.getPredicate(), remappedNode);
            }
            if (!node.equals(tripleToRender.getSubject())) {
                // the node will not match the triple subject if the node itself
                // is a remapped blank node
                // in which case the triple subject needs remapping as well
                tripleToRender = new RDFTriple(node,
                    tripleToRender.getPredicate(),
                    tripleToRender.getObject());
            }
            try {
                if (!renderedStatements.contains(tripleToRender)) {
                    renderedStatements.add(tripleToRender);
                    // then we go back and get context-sensitive statements and
                    // actually pass those to the RDFHandler
                    for (Statement statement : RioUtils.tripleAsStatements(
                        tripleToRender, contexts)) {
                        writer.handleStatement(statement);
                        if (tripleToRender.getObject() instanceof RDFResource) {
                            render((RDFResource) tripleToRender.getObject());
                        }
                    }
                } else if (logger.isTraceEnabled()) {
                    logger.trace(
                        "not printing duplicate statement, or recursing on its object: {}",
                        tripleToRender);
                }
            } catch (RDFHandlerException e) {
                throw new IOException(e);
            }
        }
        pendingNodes.remove(node);
    }

    @Override
    protected void writeAnnotationPropertyComment(
        @Nonnull OWLAnnotationProperty prop) throws IOException {
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
    protected void writeClassComment(@Nonnull final OWLClass cls)
        throws IOException {
        writeComment(cls.getIRI().toString());
    }

    private void writeComment(final String comment) throws IOException {
        try {
            writer.handleComment(comment);
        } catch (RDFHandlerException e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void writeDataPropertyComment(@Nonnull OWLDataProperty prop)
        throws IOException {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeDatatypeComment(@Nonnull OWLDatatype datatype)
        throws IOException {
        writeComment(datatype.getIRI().toString());
    }

    @Override
    protected void writeIndividualComments(@Nonnull OWLNamedIndividual ind)
        throws IOException {
        writeComment(ind.getIRI().toString());
    }

    private void writeNamespaces() throws IOException {
        // Send the prefixes from the prefixmanager to the RDFHandler
        // NOTE: These may be derived from a PrefixOWLOntologyFormat
        for (String prefixName : pm.getPrefixName2PrefixMap().keySet()) {
            assert prefixName != null;
            final String prefix = pm.getPrefix(prefixName);
            // OWLAPI generally stores prefixes with a colon at the end, while
            // Sesame Rio expects
            // prefixes without the colon
            if (prefixName.endsWith(":")) {
                prefixName = prefixName.substring(0, prefixName.length() - 1);
            }
            try {
                writer.handleNamespace(prefixName, prefix);
            } catch (RDFHandlerException e) {
                throw new IOException(e);
            }
        }
    }

    @Override
    protected void writeObjectPropertyComment(
        @Nonnull final OWLObjectProperty prop) throws IOException {
        writeComment(prop.getIRI().toString());
    }
}
