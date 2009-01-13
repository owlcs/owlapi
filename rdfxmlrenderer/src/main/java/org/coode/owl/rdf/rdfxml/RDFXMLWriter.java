package org.coode.owl.rdf.rdfxml;

import org.coode.xml.XMLWriter;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLObject;
import org.semanticweb.owl.vocab.OWLRDFVocabulary;
import org.semanticweb.owl.vocab.Namespaces;

import java.io.IOException;
import java.net.URI;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 06-Dec-2006<br><br>
 */
public class RDFXMLWriter {

    private XMLWriter writer;

    RDFXMLWriter(XMLWriter writer) {
        this.writer = writer;
    }

    public void writeStartElement(URI elementName) {
        try {
            // Sort out with namespace
            writer.writeStartElement(elementName.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeParseTypeAttribute() {
        try {
            writer.writeAttribute(Namespaces.RDF + "parseType", "Collection");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeDatatypeAttribute(URI datatypeURI) {
        try {
            writer.writeAttribute(Namespaces.RDF + "datatype", datatypeURI.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeTextContent(String text) {
        try {
            writer.writeTextContent(text);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLangAttribute(String lang) {
        try {
            writer.writeAttribute("xml:lang", lang);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeEndElement() {
        try {
            writer.writeEndElement();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeAboutAttribute(URI value) {
            writeAttribute(Namespaces.RDF + "about", value.toString());
    }

    private void writeAttribute(String attributeName, String value) {
        try {
            if(value.startsWith(writer.getXMLBase())) {
                    writer.writeAttribute(attributeName, value.substring(writer.getXMLBase().length(), value.length()));
                }
                else {
                    writer.writeAttribute(attributeName, value);
                }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeOWLObject(OWLObject owlObject) {

    }


    public void writeResourceAttribute(URI value) {
        writeAttribute(Namespaces.RDF + "resource", value.toString());
    }


    public void startDocument() {
        try {
            writer.startDocument(Namespaces.RDF + "RDF");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void endDocument() {
        try {
            writer.endDocument();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeComment(String comment) {
        try {
            writer.writeComment(comment);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
