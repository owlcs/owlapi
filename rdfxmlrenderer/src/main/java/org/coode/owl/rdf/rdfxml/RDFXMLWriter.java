package org.coode.owl.rdf.rdfxml;

import org.coode.xml.XMLWriter;
import org.coode.owl.rdf.model.RDFResourceNode;
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

    public void writeStartElement(URI elementName) throws IOException {
            // Sort out with namespace
            writer.writeStartElement(elementName.toString());
    }

    public void writeParseTypeAttribute()  throws IOException  {
            writer.writeAttribute(Namespaces.RDF + "parseType", "Collection");
    }

    public void writeDatatypeAttribute(URI datatypeURI) throws IOException  {
            writer.writeAttribute(Namespaces.RDF + "datatype", datatypeURI.toString());
    }

    public void writeTextContent(String text) throws IOException  {
            writer.writeTextContent(text);
    }

    public void writeLangAttribute(String lang) throws IOException {
            writer.writeAttribute("xml:lang", lang);
    }


    public void writeEndElement() throws IOException  {
            writer.writeEndElement();
    }

    public void writeAboutAttribute(URI value) throws IOException  {
            writeAttribute(Namespaces.RDF + "about", value);
    }

    public void writeNodeIDAttribute(RDFResourceNode node) throws IOException  {
        writeAttribute(Namespaces.RDF + "nodeID", URI.create(node.toString()));
    }

    private void writeAttribute(String attributeName, URI value) throws IOException  {
            URI s = writer.getXMLBaseAsURI().relativize(value);
            writer.writeAttribute(attributeName, s.toString());
    }

    public void writeOWLObject(OWLObject owlObject) {

    }


    public void writeResourceAttribute(URI value) throws IOException  {
        writeAttribute(Namespaces.RDF + "resource", value);
    }


    public void startDocument() throws IOException  {
            writer.startDocument(Namespaces.RDF + "RDF");
    }

    public void endDocument() throws IOException  {
            writer.endDocument();
    }


    public void writeComment(String comment) throws IOException  {
            writer.writeComment(comment);
    }
}
