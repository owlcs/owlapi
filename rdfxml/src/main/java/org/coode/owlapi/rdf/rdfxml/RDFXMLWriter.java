package org.coode.owlapi.rdf.rdfxml;

import java.io.IOException;

import org.coode.owlapi.rdf.model.RDFResourceNode;
import org.coode.xml.XMLWriter;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.vocab.Namespaces;


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

    public void writeStartElement(IRI elementName) throws IOException {
            // Sort out with namespace
            writer.writeStartElement(elementName.toString());
    }

    public void writeParseTypeAttribute()  throws IOException  {
            writer.writeAttribute(Namespaces.RDF + "parseType", "Collection");
    }

    public void writeDatatypeAttribute(IRI datatypeIRI) throws IOException  {
            writer.writeAttribute(Namespaces.RDF + "datatype", datatypeIRI.toString());
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

    public void writeAboutAttribute(IRI value) throws IOException  {
            writeAttribute(Namespaces.RDF + "about", value);
    }

    public void writeNodeIDAttribute(RDFResourceNode node) throws IOException  {
        writeAttribute(Namespaces.RDF + "nodeID", IRI.create(node.toString()));
    }

    private void writeAttribute(String attributeName, IRI value) throws IOException  {
//            URI s = writer.getXMLBaseAsURI().relativize(value);
            writer.writeAttribute(attributeName, value.toString());
    }
    @SuppressWarnings("unused")
    public void writeOWLObject(OWLObject owlObject) {

    }


    public void writeResourceAttribute(IRI value) throws IOException  {
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
