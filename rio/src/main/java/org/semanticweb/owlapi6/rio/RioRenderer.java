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
package org.semanticweb.owlapi6.rio;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nullable;

import org.eclipse.rdf4j.OpenRDFUtil;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.RDFHandlerException;
import org.semanticweb.owlapi6.documents.RDFResource;
import org.semanticweb.owlapi6.documents.RDFTriple;
import org.semanticweb.owlapi6.model.OWLAnnotationProperty;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLDataProperty;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLNamedIndividual;
import org.semanticweb.owlapi6.model.OWLObjectProperty;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.model.OWLRuntimeException;
import org.semanticweb.owlapi6.model.PrefixManager;
import org.semanticweb.owlapi6.rdf.RDFRendererBase;
import org.semanticweb.owlapi6.utility.VersionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * @since 4.0.0
 */
public class RioRenderer extends RDFRendererBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RioRenderer.class);
    private final RDFHandler writer;
    private final PrefixManager pm;
    private final Set<RDFTriple> renderedStatements = new LinkedHashSet<>();
    private final Resource[] contexts;

    /**
     * @param ontology
     *        ontology
     * @param format
     *        target format
     * @param writer
     *        writer
     * @param contexts
     *        contexts
     */
    public RioRenderer(OWLOntology ontology, @Nullable OWLDocumentFormat format, RDFHandler writer,
        Resource... contexts) {
        super(ontology, format, ontology.getOWLOntologyManager().getOntologyConfigurator());
        OpenRDFUtil.verifyContextNotNull(contexts);
        this.contexts = contexts;
        this.writer = writer;
        pm = ontology.getPrefixManager();
        if (ontology.isNamed()) {
            String ontologyIRIString = ontology.getOntologyID().getOntologyIRI().map(Object::toString).orElse("");
            String defaultPrefix = ontologyIRIString;
            if (!ontologyIRIString.endsWith("/") && !ontologyIRIString.endsWith("#")) {
                defaultPrefix = ontologyIRIString + '#';
            }
            pm.withDefaultPrefix(defaultPrefix);
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
    protected void writeClassComment(OWLClass cls) {
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
    protected void writeObjectPropertyComment(OWLObjectProperty prop) {
        writeComment(prop.getIRI().toString());
    }

    @Override
    protected void writeBanner(String name) {
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

    private void writeComment(String comment) {
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
            String prefix = pm.getPrefix(prefixName);
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
    public void render(RDFResource node, boolean root) {
        if (pending.contains(node)) {
            return;
        }
        pending.add(node);
        final Collection<RDFTriple> triples = getRDFGraph().getTriplesForSubject(node);
        if (LOGGER.isTraceEnabled()) {
            LOGGER.trace("triples.size()={}", Integer.valueOf(triples.size()));
            if (!triples.isEmpty()) {
                LOGGER.trace("triples={}", triples);
            }
        }
        for (RDFTriple tripleToRender : triples) {
            try {
                if (!renderedStatements.contains(tripleToRender)) {
                    renderedStatements.add(tripleToRender);
                    // then we go back and get context-sensitive statements and
                    // actually pass those to the RDFHandler
                    for (Statement statement : RioUtils.tripleAsStatements(tripleToRender, contexts)) {
                        writer.handleStatement(statement);
                        if (tripleToRender.getObject() instanceof RDFResource) {
                            render((RDFResource) tripleToRender.getObject(), false);
                        }
                    }
                } else if (LOGGER.isTraceEnabled()) {
                    LOGGER.trace("not printing duplicate statement, or recursing on its object: {}", tripleToRender);
                }
            } catch (RDFHandlerException e) {
                throw new OWLRuntimeException(e);
            }
        }
        pending.remove(node);
    }
}
