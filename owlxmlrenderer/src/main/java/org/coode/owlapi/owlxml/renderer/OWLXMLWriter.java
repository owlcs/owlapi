package org.coode.owlapi.owlxml.renderer;

import org.coode.xml.XMLWriter;
import org.coode.xml.XMLWriterFactory;
import org.coode.xml.XMLWriterNamespaceManager;
import org.semanticweb.owl.io.OWLRendererException;
import org.semanticweb.owl.io.OWLRendererIOException;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLRuntimeException;
import org.semanticweb.owl.util.VersionInfo;
import org.semanticweb.owl.vocab.Namespaces;
import org.semanticweb.owl.vocab.OWLXMLVocabulary;

import java.io.IOException;
import java.io.Writer;
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
 * Date: 12-Dec-2006<br><br>
 */
public class OWLXMLWriter {

    private XMLWriter writer;


    public OWLXMLWriter(Writer writer, XMLWriterNamespaceManager xmlWriterNamespaceManager, OWLOntology ontology) {
        this.writer = XMLWriterFactory.getInstance().createXMLWriter(writer,
                                                                     xmlWriterNamespaceManager,
                                                                     Namespaces.OWL2XML.toString());
    }


    public OWLXMLWriter(Writer writer, XMLWriterNamespaceManager xmlWriterNamespaceManager) {
        this.writer = XMLWriterFactory.getInstance().createXMLWriter(writer, xmlWriterNamespaceManager, Namespaces.OWL2XML.toString());
    }


    public void startDocument(OWLOntology ontology) throws OWLRendererException {
        try {
            writer.startDocument(OWLXMLVocabulary.ONTOLOGY.toString());
            writeNameAttribute(ontology.getURI());
        }
        catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }


    public void endDocument() {
        try {
            writer.endDocument();
            writer.writeComment(VersionInfo.getVersionInfo().getGeneratedByMessage());
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void writeStartElement(URI name) {
        try {
            writer.writeStartElement(name.toString());
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void writeEndElement() {
        try {
            writer.writeEndElement();
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void writeDatatypeAttribute(URI datatype) {
        try {
            writer.writeAttribute(OWLXMLVocabulary.DATATYPE_URI.getURI().toString(), datatype.toString());
        }
        catch (IOException e) {
            throw new OWLRuntimeException(e);
        }
    }


    public void writeNameAttribute(URI uri) {
        try {
            String value = uri.toString();
            String attName = Namespaces.OWL2XML + "URI";
            if (value.startsWith(writer.getXMLBase())) {
                writer.writeAttribute(attName, value.substring(writer.getXMLBase().length(), value.length()));
            }
            else {
                writer.writeAttribute(attName, value);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeCardinalityAttribute(int cardinality) {
        try {
            writer.writeAttribute(Namespaces.OWL2XML + "cardinality", Integer.toString(cardinality));
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


    public void writeFacetAttribute(URI facetURI) {
        try {
            writer.writeAttribute(OWLXMLVocabulary.DATATYPE_FACET.getURI().toString(), facetURI.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeAnnotationURIAttribute(URI uri) {
        try {
            writer.writeAttribute(OWLXMLVocabulary.ANNOTATION_URI.toString(), uri.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
