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

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.vocab.Namespaces;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class RDFXMLWriter {

    @Nonnull
    private static final IRI RDF_RDF = IRI.create(
            Namespaces.RDF.getPrefixIRI(), "RDF");
    @Nonnull
    private static final IRI RDF_RESOURCE = IRI.create(
            Namespaces.RDF.getPrefixIRI(), "resource");
    @Nonnull
    private static final String XML_LANG = "xml:lang";
    @Nonnull
    private static final IRI RDF_NODEID = IRI.create(
            Namespaces.RDF.getPrefixIRI(), "nodeID");
    @Nonnull
    private static final IRI RDF_ABOUT = IRI.create(
            Namespaces.RDF.getPrefixIRI(), "about");
    @Nonnull
    private static final IRI RDF_DATATYPE = IRI.create(
            Namespaces.RDF.getPrefixIRI(), "datatype");
    @Nonnull
    private static final IRI PARSETYPE_IRI = IRI.create(
            Namespaces.RDF.getPrefixIRI(), "parseType");
    private final XMLWriter writer;

    protected RDFXMLWriter(@Nonnull XMLWriter writer) {
        this.writer = checkNotNull(writer, "writer cannot be null");
    }

    /**
     * @param elementName
     *        elementName
     */
    public void writeStartElement(@Nonnull IRI elementName) {
        // Sort out with namespace
        writer.writeStartElement(checkNotNull(elementName,
                "elementName cannot be null"));
    }

    /**
     * Parse type attribute.
     */
    public void writeParseTypeAttribute() {
        writer.writeAttribute(PARSETYPE_IRI, "Collection");
    }

    /**
     * @param datatypeIRI
     *        datatypeIRI
     */
    public void writeDatatypeAttribute(@Nonnull IRI datatypeIRI) {
        checkNotNull(datatypeIRI, "datatypeIRI cannot be null");
        writer.writeAttribute(RDF_DATATYPE, datatypeIRI.toString());
    }

    /**
     * @param text
     *        text
     */
    public void writeTextContent(@Nonnull String text) {
        writer.writeTextContent(text);
    }

    /**
     * @param lang
     *        lang
     */
    public void writeLangAttribute(@Nonnull String lang) {
        writer.writeAttribute(XML_LANG, lang);
    }

    /** Write end element. */
    public void writeEndElement() {
        writer.writeEndElement();
    }

    /**
     * @param value
     *        value
     */
    public void writeAboutAttribute(@Nonnull IRI value) {
        writeAttribute(RDF_ABOUT, value);
    }

    /**
     * @param node
     *        node
     */
    public void writeNodeIDAttribute(@Nonnull RDFResource node) {
        writer.writeAttribute(RDF_NODEID, node.toString());
    }

    /**
     * @param attributeName
     *        attribute name
     * @param value
     *        value
     */
    public void writeAttribute(@Nonnull IRI attributeName, @Nonnull IRI value) {
        writer.writeAttribute(attributeName,
                checkNotNull(value, "value cannot be null").toString());
    }

    /**
     * @param owlObject
     *        owlObject
     */
    @SuppressWarnings("unused")
    public void writeOWLObject(OWLObject owlObject) {}

    /**
     * @param value
     *        value
     */
    public void writeResourceAttribute(@Nonnull IRI value) {
        writeAttribute(RDF_RESOURCE, value);
    }

    /**
     * Start document.
     */
    public void startDocument() {
        writer.startDocument(RDF_RDF);
    }

    /**
     * End document.
     */
    public void endDocument() {
        writer.endDocument();
    }

    /**
     * @param comment
     *        comment
     */
    public void writeComment(@Nonnull String comment) {
        writer.writeComment(comment);
    }
}
