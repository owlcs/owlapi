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

import java.io.IOException;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.RDFResourceBlankNode;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class RDFXMLWriter {

    @Nonnull private static final IRI RDF_RDF = IRI.create(Namespaces.RDF.getPrefixIRI(), "RDF");
    @Nonnull private static final IRI RDF_RESOURCE = IRI.create(Namespaces.RDF.getPrefixIRI(), "resource");
    @Nonnull private static final String XML_LANG = "xml:lang";
    @Nonnull private static final IRI RDF_NODEID = IRI.create(Namespaces.RDF.getPrefixIRI(), "nodeID");
    @Nonnull private static final IRI RDF_ABOUT = IRI.create(Namespaces.RDF.getPrefixIRI(), "about");
    @Nonnull private static final IRI RDF_DATATYPE = IRI.create(Namespaces.RDF.getPrefixIRI(), "datatype");
    @Nonnull private static final IRI PARSETYPE_IRI = IRI.create(Namespaces.RDF.getPrefixIRI(), "parseType");
    private final XMLWriter writer;

    protected RDFXMLWriter(@Nonnull XMLWriter writer) {
        this.writer = checkNotNull(writer, "writer cannot be null");
    }

    /**
     * @param elementName
     *        elementName
     * @throws IOException
     *         io exception
     */
    public void writeStartElement(@Nonnull IRI elementName) throws IOException {
        // Sort out with namespace
        writer.writeStartElement(checkNotNull(elementName, "elementName cannot be null"));
    }

    /**
     * @throws IOException
     *         io exception
     */
    public void writeParseTypeAttribute() throws IOException {
        writer.writeAttribute(PARSETYPE_IRI, "Collection");
    }

    /**
     * Parse type attribute for literals.
     * 
     * @throws IOException
     *         io exception
     */
    public void writeParseTypeLiteralAttribute() throws IOException {
        writer.writeAttribute(PARSETYPE_IRI, "Literal");
    }

    /**
     * @param datatypeIRI
     *        datatypeIRI
     * @throws IOException
     *         io exception
     */
    public void writeDatatypeAttribute(@Nonnull IRI datatypeIRI) throws IOException {
        checkNotNull(datatypeIRI, "datatypeIRI cannot be null");
        if (OWL2Datatype.RDF_XML_LITERAL.getIRI().equals(datatypeIRI)) {
            writeParseTypeLiteralAttribute();
        } else {
            writer.writeAttribute(RDF_DATATYPE, datatypeIRI.toString());
        }
    }

    /**
     * @param text
     *        text
     * @throws IOException
     *         io exception
     */
    public void writeTextContent(@Nonnull String text) throws IOException {
        writer.writeTextContent(text);
    }

    /**
     * @param lang
     *        lang
     * @throws IOException
     *         io exception
     */
    public void writeLangAttribute(@Nonnull String lang) throws IOException {
        writer.writeAttribute(XML_LANG, lang);
    }

    /**
     * @throws IOException
     *         io exception
     */
    public void writeEndElement() throws IOException {
        writer.writeEndElement();
    }

    /**
     * @param value
     *        value
     * @throws IOException
     *         io exception
     */
    public void writeAboutAttribute(@Nonnull IRI value) throws IOException {
        writeAttribute(RDF_ABOUT, value);
    }

    /**
     * @param node
     *        node
     * @throws IOException
     *         io exception
     */
    public void writeNodeIDAttribute(@Nonnull RDFResourceBlankNode node) throws IOException {
        writer.writeAttribute(RDF_NODEID, node.toString());
    }

    /**
     * @param attributeName
     *        attribute name
     * @param value
     *        value
     * @throws IOException
     *         io exception
     */
    public void writeAttribute(@Nonnull IRI attributeName, @Nonnull IRI value) throws IOException {
        writer.writeAttribute(attributeName, checkNotNull(value, "value cannot be null").toString());
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
     * @throws IOException
     *         io exception
     */
    public void writeResourceAttribute(@Nonnull IRI value) throws IOException {
        writeAttribute(RDF_RESOURCE, value);
    }

    /**
     * @throws IOException
     *         io exception
     */
    public void startDocument() throws IOException {
        writer.startDocument(RDF_RDF);
    }

    /**
     * @throws IOException
     *         io exception
     */
    public void endDocument() throws IOException {
        writer.endDocument();
    }

    /**
     * @param comment
     *        comment
     * @throws IOException
     *         io exception
     */
    public void writeComment(@Nonnull String comment) throws IOException {
        writer.writeComment(comment);
    }
}
