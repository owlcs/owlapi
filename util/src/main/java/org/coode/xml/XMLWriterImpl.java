package org.coode.xml;

/**
 * Copyright (C) 2006, Matthew Horridge, University of Manchester
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

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import org.coode.string.EscapeUtils;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: 30-May-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * Developed as part of the CO-ODE project
 * http://www.co-ode.org
 */
public class XMLWriterImpl implements XMLWriter {


    private Stack<XMLElement> elementStack;

    protected Writer writer;

    private String encoding = "";

    private String xmlBase;

    private URI xmlBaseURI;

    private XMLWriterNamespaceManager xmlWriterNamespaceManager;

    private Map<String, String> entities;

    private static final int TEXT_CONTENT_WRAP_LIMIT = Integer.MAX_VALUE;

    private boolean preambleWritten;

    private static final String PERCENT_ENTITY = "&#37;";

    public XMLWriterImpl(Writer writer, XMLWriterNamespaceManager xmlWriterNamespaceManager, String xmlBase) {
        this.writer = writer;
        this.xmlWriterNamespaceManager = xmlWriterNamespaceManager;
        this.xmlBase = xmlBase;
        this.xmlBaseURI = URI.create(xmlBase);
        // no need to set it to UTF-8: it's supposed to be the default encoding for XML. 
        //Must be set correctly for the Writer anyway, or bugs will ensue.
        //this.encoding = "UTF-8";
        elementStack = new Stack<XMLElement>();
        setupEntities();
    }


    private void setupEntities() {
        List<String> namespaces = new ArrayList<String>(xmlWriterNamespaceManager.getNamespaces());
        Collections.sort(namespaces, new StringLengthOnlyComparator());
        entities = new LinkedHashMap<String, String>();
        for (String curNamespace : namespaces) {
            String curPrefix = "";
            if (xmlWriterNamespaceManager.getDefaultNamespace().equals(curNamespace)) {
                curPrefix = xmlWriterNamespaceManager.getDefaultPrefix();
            }
            else {
                curPrefix = xmlWriterNamespaceManager.getPrefixForNamespace(curNamespace);

            }
            if (curPrefix.length() > 0) {
                entities.put(curNamespace, "&" + curPrefix + ";");
            }
        }
    }


    private String swapForEntity(String value) {
        for (String curEntity : entities.keySet()) {
            String entityVal = entities.get(curEntity);
            if (value.length() > curEntity.length()) {
                String repVal = value.replace(curEntity, entityVal);
                if (repVal.length() < value.length()) {
                    return repVal;
                }
            }
        }
        return value;
    }


    public String getDefaultNamespace() {
        return xmlWriterNamespaceManager.getDefaultNamespace();
    }


    public String getXMLBase() {
        return xmlBase;
    }

    public URI getXMLBaseAsURI() {
        return xmlBaseURI;
    }

    public XMLWriterNamespaceManager getNamespacePrefixes() {
        return xmlWriterNamespaceManager;
    }


    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    private boolean isValidQName(String name) {
        if(name == null) {
            return false;
        }
        int colonIndex = name.indexOf(":");
        boolean valid = false;
        if (colonIndex == -1) {
            valid = OWL2Datatype.XSD_NCNAME.getPattern().matcher(name).matches();
        }
        else {
            valid = OWL2Datatype.XSD_NCNAME.getPattern().matcher(name.substring(0, colonIndex - 1)).matches();
            if (valid) {
                valid = OWL2Datatype.XSD_NAME.getPattern().matcher(name.substring(colonIndex + 1)).matches();
            }
        }
        return valid;
    }


    public void setWrapAttributes(boolean b) {
        if (!elementStack.isEmpty()) {
            XMLElement element = elementStack.peek();
            element.setWrapAttributes(b);
        }
    }


    public void writeStartElement(String name) throws IOException {
        String qName = xmlWriterNamespaceManager.getQName(name);
        if ( qName == null || qName.equals(name)) {
            if (!isValidQName(name)) {
                // Could not generate a valid QName, therefore, we cannot
                // write valid XML - just throw an exception!
                throw new IllegalElementNameException(name);

            }
        }
        XMLElement element = new XMLElement(qName, elementStack.size());
        if (!elementStack.isEmpty()) {
            XMLElement topElement = elementStack.peek();
            if (topElement != null) {
                topElement.writeElementStart(false);
            }
        }
        elementStack.push(element);
    }


    public void writeEndElement() throws IOException {
        // Pop the element off the stack and write it out
        if (!elementStack.isEmpty()) {
            XMLElement element = elementStack.pop();
            element.writeElementEnd();
        }
    }


    public void writeAttribute(String attr, String val) {
        XMLElement element = elementStack.peek();
        element.setAttribute(xmlWriterNamespaceManager.getQName(attr), val);
    }


    public void writeTextContent(String text) {
        XMLElement element = elementStack.peek();
        element.setText(text);
    }


    public void writeComment(String commentText) throws IOException {
        XMLElement element = new XMLElement(null, elementStack.size());
        element.setText("<!-- " + commentText.replaceAll("--","&#45;&#45;") + " -->");
        if (!elementStack.isEmpty()) {
            XMLElement topElement = elementStack.peek();
            if (topElement != null) {
                topElement.writeElementStart(false);
            }
        }
        if (preambleWritten) {
            element.writeElementStart(true);
        }
        else {
            elementStack.push(element);
        }
    }


    private void writeEntities(String rootName) throws IOException {
        writer.write("\n\n<!DOCTYPE " + xmlWriterNamespaceManager.getQName(rootName) + " [\n");
        for (String entityVal : entities.keySet()) {
            String entity = entities.get(entityVal);
            entity = entity.substring(1, entity.length() - 1);
            writer.write("    <!ENTITY ");
            writer.write(entity);
            writer.write(" \"");
            entityVal = EscapeUtils.escapeXML(entityVal);
            entityVal = entityVal.replace("%", PERCENT_ENTITY);
            writer.write(entityVal);
            writer.write("\" >\n");
        }
        writer.write("]>\n\n\n");
    }


    public void startDocument(String rootElementName) throws IOException {
        String encodingString = "";
        if (encoding.length() > 0) {
            encodingString = " encoding=\"" + encoding + "\"";
        }
        writer.write("<?xml version=\"1.0\"" + encodingString + "?>\n");
        if (XMLWriterPreferences.getInstance().isUseNamespaceEntities()) {
            writeEntities(rootElementName);
        }
        preambleWritten = true;
        while (!elementStack.isEmpty()) {
            elementStack.pop().writeElementStart(true);
        }
        writeStartElement(rootElementName);
        setWrapAttributes(true);
        writeAttribute("xmlns", xmlWriterNamespaceManager.getDefaultNamespace());
        if (xmlBase.length() != 0) {
            writeAttribute("xml:base", xmlBase);
        }
        for (String curPrefix : xmlWriterNamespaceManager.getPrefixes()) {
            if (curPrefix.length() > 0) {
                writeAttribute("xmlns:" + curPrefix, xmlWriterNamespaceManager.getNamespaceForPrefix(curPrefix));
            }
        }
    }


    public void endDocument() throws IOException {
        // Pop of each element
        while (!elementStack.isEmpty()) {
            writeEndElement();
        }
        writer.flush();
    }


    private static final class StringLengthOnlyComparator implements
			Comparator<String> {
		public int compare(String o1, String o2) {
		    // Shortest string first
		    return o1.length() - o2.length();
		}
	}


	public class XMLElement {

        private String name;

        private Map<String, String> attributes;

        String textContent;

        private boolean startWritten;

        private int indentation;

        private boolean wrapAttributes;


        public XMLElement(String name) {
            this(name, 0);
            wrapAttributes = false;
        }


        public XMLElement(String name, int indentation) {
            this.name = name;
            attributes = new LinkedHashMap<String, String>();
            this.indentation = indentation;
            textContent = null;
            startWritten = false;
        }


        public void setWrapAttributes(boolean b) {
        	//XXX it was:
            //wrapAttributes = true;
        	wrapAttributes = b;
        }


        public void setAttribute(String attribute, String value) {
            attributes.put(attribute, value);
        }


        public void setText(String content) {
            textContent = content;
        }


        public void writeElementStart(boolean close) throws IOException {
            if (!startWritten) {
                startWritten = true;
                insertIndentation();
                if (name != null) {
                    writer.write('<');
                    writer.write(name);
                    writeAttributes();
                    if (textContent != null) {
                        boolean wrap = textContent.length() > TEXT_CONTENT_WRAP_LIMIT;
                        if (wrap) {
                            writeNewLine();
                            indentation++;
                            insertIndentation();
                        }
                        writer.write('>');
                        writeTextContent();
                        if (wrap) {
                            indentation--;
                        }
                    }
                    if (close) {
                        if (textContent != null) {
                            writeElementEnd();
                        }
                        else {
                            writer.write("/>");
                            writeNewLine();
                        }
                    }
                    else {
                        if (textContent == null) {
                            writer.write('>');
                            writeNewLine();
                        }
                    }
                }
                else {
                    // Name is null so by convension this is a comment
                    if (textContent != null) {
                        writer.write("\n\n\n");
                        StringTokenizer tokenizer = new StringTokenizer(textContent, "\n", true);
                        while (tokenizer.hasMoreTokens()) {
                            String token = tokenizer.nextToken();
                            if (!token.equals("\n")) {
                                insertIndentation();
                            }
                            writer.write(token);
                        }
                        writer.write("\n\n");
                    }
                }
            }
        }


        public void writeElementEnd() throws IOException {
            if (name != null) {
                if (!startWritten) {
                    writeElementStart(true);
                }
                else {
                    if (textContent == null) {
                        insertIndentation();
                    }
                    writer.write("</");
                    writer.write(name);
                    writer.write(">");
                    writeNewLine();
                }
            }
        }


        private void writeAttribute(String attr, String val) throws IOException {
            writer.write(attr);
            writer.write('=');
            writer.write('"');
            if (XMLWriterPreferences.getInstance().isUseNamespaceEntities()) {
                writer.write(swapForEntity(EscapeUtils.escapeXML(val)));
            }
            else {
                writer.write(EscapeUtils.escapeXML(val));
            }
            writer.write('"');
        }


        private void writeAttributes() throws IOException {
            for (Iterator<String> it = attributes.keySet().iterator(); it.hasNext();) {
                String attr = it.next();
                String val = attributes.get(attr);
                writer.write(' ');
                writeAttribute(attr, val);
                if (it.hasNext() && wrapAttributes) {
                    writer.write("\n");
                    indentation++;
                    insertIndentation();
                    indentation--;
                }
            }
        }


        private void writeTextContent() throws IOException {
            if (textContent != null) {
                writer.write(EscapeUtils.escapeXML(textContent));
            }
        }


        private void insertIndentation() throws IOException {
            if (XMLWriterPreferences.getInstance().isIndenting()) {
                for (int i = 0; i < indentation * XMLWriterPreferences.getInstance().getIndentSize(); i++) {
                    writer.write(' ');
                }
            }
        }


        private void writeNewLine() throws IOException {
            writer.write('\n');
        }
    }
}
