/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owlapi.rdf.rdfxml;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javax.annotation.Nonnull;

import org.coode.xml.XMLWriter;
import org.semanticweb.owlapi.io.RDFResource;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.vocab.Namespaces;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br>
 * <br> */
public class RDFXMLWriter {
    private XMLWriter writer;

    RDFXMLWriter(@Nonnull XMLWriter writer) {
        this.writer = checkNotNull(writer);
    }

    public void writeStartElement(@Nonnull IRI elementName) throws IOException {
        // Sort out with namespace
        writer.writeStartElement(checkNotNull(elementName).toString());
    }

    public void writeParseTypeAttribute() throws IOException {
        writer.writeAttribute(Namespaces.RDF + "parseType", "Collection");
    }

    public void writeDatatypeAttribute(@Nonnull IRI datatypeIRI) throws IOException {
        checkNotNull(datatypeIRI);
        writer.writeAttribute(Namespaces.RDF + "datatype", datatypeIRI.toString());
    }

    public void writeTextContent(@Nonnull String text) throws IOException {
        writer.writeTextContent(text);
    }

    public void writeLangAttribute(@Nonnull String lang) throws IOException {
        writer.writeAttribute("xml:lang", lang);
    }

    public void writeEndElement() throws IOException {
        writer.writeEndElement();
    }

    public void writeAboutAttribute(@Nonnull IRI value) throws IOException {
        writeAttribute(Namespaces.RDF + "about", value);
    }

    public void writeNodeIDAttribute(@Nonnull RDFResource node) throws IOException {
        writer.writeAttribute(Namespaces.RDF + "nodeID", node.toString());
    }

    private void writeAttribute(@Nonnull String attributeName, @Nonnull IRI value)
            throws IOException {
        writer.writeAttribute(attributeName, checkNotNull(value).toString());
    }

    public void writeOWLObject(OWLObject owlObject) {}

    public void writeResourceAttribute(@Nonnull IRI value) throws IOException {
        writeAttribute(Namespaces.RDF + "resource", value);
    }

    public void startDocument() throws IOException {
        writer.startDocument(Namespaces.RDF + "RDF");
    }

    public void endDocument() throws IOException {
        writer.endDocument();
    }

    public void writeComment(@Nonnull String comment) throws IOException {
        writer.writeComment(comment);
    }
}
