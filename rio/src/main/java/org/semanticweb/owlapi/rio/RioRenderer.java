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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.io.RDFTriple;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RioRenderer.class);
    private final RDFHandler writer;
    private final DefaultPrefixManager pm;
    private final Set<RDFTriple> renderedStatements = new LinkedHashSet<>();
    private final Resource[] contexts;

    /**
     * @param ontology ontology
     * @param writer writer
     * @param format format
     * @param contexts contexts
     */
    public RioRenderer(final OWLOntology ontology, final RDFHandler writer,
        @Nullable OWLDocumentFormat format, final Resource... contexts) {
        super(ontology, format, ontology.getOWLOntologyManager().getOntologyWriterConfiguration());
        Objects.requireNonNull(contexts,
            "contexts argument may not be null; either the value should be cast to Resource or an empty array should be supplied");
        this.contexts = contexts;
        this.writer = writer;
        pm = new DefaultPrefixManager();
        if (!ontology.isAnonymous()) {
            String ontologyIRIString = ontology.getOntologyID().getOntologyIRI().get().toString();
            String defaultPrefix = ontologyIRIString;
            if (!ontologyIRIString.endsWith("/") && !ontologyIRIString.endsWith("#")) {
                defaultPrefix = ontologyIRIString + '#';
            }
            pm.setDefaultPrefix(defaultPrefix);
        }
        // copy prefixes out of the given format if it is a
        // PrefixOWLDocumentFormat
        if (format instanceof PrefixDocumentFormat) {
            PrefixDocumentFormat prefixFormat = (PrefixDocumentFormat) format;
            pm.copyPrefixesFrom(prefixFormat);
            pm.setPrefixComparator(prefixFormat.getPrefixComparator());
        }
    }

    @Override
    protected void beginDocument() {
        pending.clear();
        renderedStatements.clear();
        try {
            writer.startRDF();
        } catch (RDFHandlerException e) {
            throw new OWLRuntimeException(e);
        }
        // Namespaces
        writeNamespaces();
    }

    @Override
    protected void endDocument() {
        writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        try {
            writer.endRDF();
        } catch (RDFHandlerException e) {
            throw new OWLRuntimeException(e);
        }
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("pendingNodes={}", Integer.valueOf(pending.size()));
            LOGGER.trace("renderedStatements={}", Integer.valueOf(renderedStatements.size()));
        }
        pending.clear();
        renderedStatements.clear();
    }

    @Override
    protected void endObject() {
        writeComment("");
    }

    @Override
    protected void writeAnnotationPropertyComment(OWLAnnotationProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeClassComment(final OWLClass cls) {
        writeComment(cls.getIRI().toString());
    }

    @Override
    protected void writeDataPropertyComment(OWLDataProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeDatatypeComment(OWLDatatype datatype) {
        writeComment(datatype.getIRI().toString());
    }

    @Override
    protected void writeIndividualComments(OWLNamedIndividual ind) {
        writeComment(ind.getIRI().toString());
    }

    @Override
    protected void writeObjectPropertyComment(final OWLObjectProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeBanner(final String name) {
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

    private void writeComment(final String comment) {
        try {
            writer.handleComment(comment);
        } catch (RDFHandlerException e) {
            throw new OWLRuntimeException(e);
        }
    }

    private void writeNamespaces() {
        // Send the prefixes from the prefixmanager to the RDFHandler
        // NOTE: These may be derived from a PrefixOWLDocumentFormat
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
            } catch (RDFHandlerException e) {
                throw new OWLRuntimeException(e);
            }
        }
    }

    @Override
    public void render(final RDFResource node, boolean root) {
        if (!pending.add(node)) {
            return;
        }
        // do not use recursion, as it fails on very large expressions
        List<RDFTriple> statements = new ArrayList<>();

        final Collection<RDFTriple> triples = graph.getTriplesForSubject(node);
        trace(triples);
        triples.stream().filter(renderedStatements::add).forEach(statements::add);
        // for each RDF resource object, get the triples for the object and add them in the list
        // right after the first appearance as object; the list will contain the same sequence of
        // triples as the recursion would encounter.
        recurseOnNestedResources(statements);
        // then we go back and get context-sensitive statements and
        // actually pass those to the RDFHandler
        try {
            statements.forEach(triple -> RioUtils.tripleAsStatements(triple, contexts)
                .forEach(writer::handleStatement));
        } catch (RDFHandlerException e) {
            throw new OWLRuntimeException(e);
        }
        pending.remove(node);
    }

    protected void recurseOnNestedResources(List<RDFTriple> list) {
        Set<RDFResource> subnodes = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getObject() instanceof RDFResource) {
                RDFResource node = (RDFResource) list.get(i).getObject();
                if (!pending.contains(node) && subnodes.add(node)) {
                    final Collection<RDFTriple> triples = graph.getTriplesForSubject(node);
                    trace(triples);
                    addNewTriples(list, i, triples);
                }
            }
        }
    }

    protected void addNewTriples(List<RDFTriple> list, int i, final Collection<RDFTriple> triples) {
        Iterator<RDFTriple> it = triples.stream().filter(renderedStatements::add).iterator();
        int local = 1;
        while (it.hasNext()) {
            list.add(i + local, it.next());
        }
    }

    protected void trace(final Collection<RDFTriple> triples) {
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("triples.size()={}", Integer.valueOf(triples.size()));
            if (!triples.isEmpty()) {
                LOGGER.trace("triples={}", triples);
            }
        }
    }
}
