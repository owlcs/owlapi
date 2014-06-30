/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.rdf.rdfxml.renderer;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.*;
import static org.semanticweb.owlapi.vocab.OWLRDFVocabulary.*;

import java.io.PrintWriter;
import java.util.*;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.*;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.rdf.RDFRendererBase;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.ShortFormProvider;
import org.semanticweb.owlapi.util.VersionInfo;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class RDFXMLRenderer extends RDFRendererBase {

    private final RDFXMLWriter writer;
    private final @Nonnull Set<RDFResource> pending = new HashSet<>();
    private final @Nonnull RDFXMLNamespaceManager qnameManager;
    private final @Nonnull OWLDocumentFormat format;
    private final ShortFormProvider labelMaker;

    /**
     * @param ontology
     *        ontology
     * @param w
     *        writer
     */
    public RDFXMLRenderer(OWLOntology ontology, PrintWriter w) {
        this(checkNotNull(ontology, "ontology cannot be null"), checkNotNull(w, "w cannot be null"),
            verifyNotNull(ontology.getOWLOntologyManager().getOntologyFormat(ontology)));
    }

    /**
     * @param ontology
     *        ontology
     * @param w
     *        writer
     * @param format
     *        format
     */
    public RDFXMLRenderer(OWLOntology ontology, PrintWriter w, OWLDocumentFormat format) {
        super(checkNotNull(ontology, "ontology cannot be null"), checkNotNull(format, "format cannot be null"));
        this.format = checkNotNull(format, "format cannot be null");
        qnameManager = new RDFXMLNamespaceManager(ontology, format);
        String defaultNamespace = qnameManager.getDefaultNamespace();
        String base = base(defaultNamespace);
        writer = new RDFXMLWriter(
            XMLWriterFactory.createXMLWriter(checkNotNull(w, "w cannot be null"), qnameManager, base));
        Map<OWLAnnotationProperty, List<String>> prefLangMap = new HashMap<>();
        OWLOntologyManager manager = ontology.getOWLOntologyManager();
        OWLAnnotationProperty labelProp = manager.getOWLDataFactory().getRDFSLabel();
        labelMaker = new AnnotationValueShortFormProvider(Collections.singletonList(labelProp), prefLangMap, manager);
    }

    private static String base(String defaultNamespace) {
        String base;
        if (defaultNamespace.endsWith("#")) {
            base = defaultNamespace.substring(0, defaultNamespace.length() - 1);
        } else {
            base = defaultNamespace;
        }
        return base;
    }

    /**
     * @return unserializable entities
     */
    public Set<OWLEntity> getUnserialisableEntities() {
        return qnameManager.getEntitiesWithInvalidQNames();
    }

    @Override
    protected void beginDocument() {
        writer.startDocument();
    }

    @Override
    protected void endDocument() {
        writer.endDocument();
        writer.writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        if (!format.isAddMissingTypes()) {
            // missing type declarations could have been omitted, adding a
            // comment to document it
            writer.writeComment("Warning: type declarations were not added automatically.");
        }
    }

    @Override
    protected void writeIndividualComments(OWLNamedIndividual ind) {
        writeCommentForEntity("ind cannot be null", ind);
    }

    @Override
    protected void writeClassComment(OWLClass cls) {
        writeCommentForEntity("cls cannot be null", cls);
    }

    @Override
    protected void writeDataPropertyComment(OWLDataProperty prop) {
        writeCommentForEntity("prop cannot be null", prop);
    }

    @Override
    protected void writeObjectPropertyComment(OWLObjectProperty prop) {
        writeCommentForEntity("prop cannot be null", prop);
    }

    @Override
    protected void writeAnnotationPropertyComment(OWLAnnotationProperty prop) {
        writeCommentForEntity("prop cannot be null", prop);
    }

    @Override
    protected void writeDatatypeComment(OWLDatatype datatype) {
        writeCommentForEntity("datatype cannot be null", datatype);
    }

    private void writeCommentForEntity(String msg, OWLEntity entity) {
        checkNotNull(entity, msg);
        String iriString = entity.getIRI().toString();
        if (XMLWriterPreferences.getInstance().isLabelsAsBanner()) {
            String labelString = labelMaker.getShortForm(entity);
            String commentString = null;
            if (!iriString.equals(labelString)) {
                commentString = labelString;
            } else {
                commentString = iriString;
            }
            writer.writeComment(XMLUtils.escapeXML(commentString));
        } else {
            writer.writeComment(XMLUtils.escapeXML(iriString));
        }
    }

    @Override
    protected void writeBanner(String name) {
        writer.writeComment(
            "\n///////////////////////////////////////////////////////////////////////////////////////\n//\n// "
                + checkNotNull(name, "name cannot be null")
                + "\n//\n///////////////////////////////////////////////////////////////////////////////////////\n");
    }

    @Override
    public void render(RDFResource node) {
        checkNotNull(node, "node cannot be null");
        if (pending.contains(node)) {
            return;
        }
        pending.add(node);
        RDFTriple candidatePrettyPrintTypeTriple = null;
        Collection<RDFTriple> triples = graph.getTriplesForSubject(node);
        for (RDFTriple triple : triples) {
            IRI propertyIRI = triple.getPredicate().getIRI();
            if (propertyIRI.equals(RDF_TYPE.getIRI()) && !triple.getObject().isAnonymous()
                && BUILT_IN_VOCABULARY_IRIS.contains(triple.getObject().getIRI())
                && prettyPrintedTypes.contains(triple.getObject().getIRI())) {
                candidatePrettyPrintTypeTriple = triple;
            }
        }
        if (candidatePrettyPrintTypeTriple == null) {
            writer.writeStartElement(RDF_DESCRIPTION.getIRI());
        } else {
            writer.writeStartElement(candidatePrettyPrintTypeTriple.getObject().getIRI());
        }
        if (!node.isAnonymous()) {
            writer.writeAboutAttribute(node.getIRI());
        } else if (node.isIndividual() && node.shouldOutputId()) {
            writer.writeNodeIDAttribute(node);
        }
        for (RDFTriple triple : triples) {
            if (candidatePrettyPrintTypeTriple != null && candidatePrettyPrintTypeTriple.equals(triple)) {
                continue;
            }
            writer.writeStartElement(triple.getPredicate().getIRI());
            RDFNode objectNode = triple.getObject();
            if (!objectNode.isLiteral()) {
                RDFResource objectRes = (RDFResource) objectNode;
                if (objectRes.isAnonymous()) {
                    // Special rendering for lists
                    if (isObjectList(objectRes)) {
                        writer.writeParseTypeAttribute();
                        List<RDFNode> list = new ArrayList<>();
                        toJavaList(objectRes, list);
                        for (RDFNode n : list) {
                            if (n.isAnonymous()) {
                                render((RDFResourceBlankNode) n);
                            } else {
                                if (n.isLiteral()) {
                                    RDFLiteral litNode = (RDFLiteral) n;
                                    writer.writeStartElement(RDFS_LITERAL.getIRI());
                                    if (!litNode.isPlainLiteral()) {
                                        writer.writeDatatypeAttribute(litNode.getDatatype());
                                    } else if (litNode.hasLang()) {
                                        writer.writeLangAttribute(litNode.getLang());
                                    }
                                    writer.writeTextContent(litNode.getLexicalValue());
                                    writer.writeEndElement();
                                } else {
                                    writer.writeStartElement(RDF_DESCRIPTION.getIRI());
                                    writer.writeAboutAttribute(n.getIRI());
                                    writer.writeEndElement();
                                }
                            }
                        }
                    } else {
                        render(objectRes);
                    }
                } else {
                    writer.writeResourceAttribute(objectRes.getIRI());
                }
            } else {
                RDFLiteral rdfLiteralNode = (RDFLiteral) objectNode;
                if (rdfLiteralNode.hasLang()) {
                    writer.writeLangAttribute(rdfLiteralNode.getLang());
                } else if (!rdfLiteralNode.isPlainLiteral()) {
                    writer.writeDatatypeAttribute(rdfLiteralNode.getDatatype());
                }
               	writer.writeTextContent(rdfLiteralNode.getLexicalValue());
            }
            writer.writeEndElement();
        }
        writer.writeEndElement();
        pending.remove(node);
    }
}
