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
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.io.XMLUtils;
import org.semanticweb.owlapi.model.IRI;

/**
 * Developed as part of the CO-ODE project http://www.co-ode.org
 * 
 * @author Matthew Horridge, The University Of Manchester, Medical Informatics
 *         Group
 * @since 2.0.0
 */
public class XMLWriterImpl implements XMLWriter {

    private Stack<XMLElement> elementStack;
    protected Writer writer;
    private String encoding = "";
    private String xmlBase;
    private XMLWriterNamespaceManager xmlWriterNamespaceManager;
    private Map<String, String> entities;
    private static final int TEXT_CONTENT_WRAP_LIMIT = Integer.MAX_VALUE;
    private boolean preambleWritten;
    private static final String PERCENT_ENTITY = "&#37;";

    /**
     * @param writer
     *        writer
     * @param xmlWriterNamespaceManager
     *        xmlWriterNamespaceManager
     * @param xmlBase
     *        xmlBase
     */
    public XMLWriterImpl(@Nonnull Writer writer,
            @Nonnull XMLWriterNamespaceManager xmlWriterNamespaceManager,
            @Nonnull String xmlBase) {
        this.writer = checkNotNull(writer, "writer cannot be null");
        this.xmlWriterNamespaceManager = checkNotNull(
                xmlWriterNamespaceManager,
                "xmlWriterNamespaceManager cannot be null");
        this.xmlBase = checkNotNull(xmlBase, "xmlBase cannot be null");
        // no need to set it to UTF-8: it's supposed to be the default encoding
        // for XML.
        // Must be set correctly for the Writer anyway, or bugs will ensue.
        // this.encoding = "UTF-8";
        elementStack = new Stack<XMLElement>();
        setupEntities();
    }

    private void setupEntities() {
        List<String> namespaces = new ArrayList<String>();
        for (String s : xmlWriterNamespaceManager.getNamespaces()) {
            namespaces.add(s);
        }
        Collections.sort(namespaces, new StringLengthOnlyComparator());
        entities = new LinkedHashMap<String, String>();
        for (String curNamespace : namespaces) {
            String curPrefix = "";
            if (xmlWriterNamespaceManager.getDefaultNamespace().equals(
                    curNamespace)) {
                curPrefix = xmlWriterNamespaceManager.getDefaultPrefix();
            } else {
                curPrefix = xmlWriterNamespaceManager
                        .getPrefixForNamespace(curNamespace);
            }
            if (curPrefix.length() > 0) {
                entities.put(curNamespace, "&" + curPrefix + ";");
            }
        }
    }

    protected String swapForEntity(String value) {
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

    /** @return default namespace */
    public String getDefaultNamespace() {
        return xmlWriterNamespaceManager.getDefaultNamespace();
    }

    @Override
    public String getXMLBase() {
        return xmlBase;
    }

    @Override
    public XMLWriterNamespaceManager getNamespacePrefixes() {
        return xmlWriterNamespaceManager;
    }

    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public void setWrapAttributes(boolean b) {
        if (!elementStack.isEmpty()) {
            XMLElement element = elementStack.peek();
            element.setWrapAttributes(b);
        }
    }

    @Override
    public void writeStartElement(IRI name) throws IOException {
        String qName = xmlWriterNamespaceManager.getQName(name);
        if (qName.length() == name.length()) {
            // Could not generate a valid QName, therefore, we cannot
            // write valid XML - just throw an exception!
            throw new IllegalElementNameException(name.toString());
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

    @Override
    public void writeEndElement() throws IOException {
        // Pop the element off the stack and write it out
        if (!elementStack.isEmpty()) {
            XMLElement element = elementStack.pop();
            element.writeElementEnd();
        }
    }

    @Override
    public void writeAttribute(String attr, String val) {
        XMLElement element = elementStack.peek();
        String qName = xmlWriterNamespaceManager.getQName(attr);
        if (qName != null) {
            element.setAttribute(qName, val);
        }
    }

    @Override
    public void writeAttribute(IRI attr, String val) {
        XMLElement element = elementStack.peek();
        String qName = xmlWriterNamespaceManager.getQName(attr);
        if (qName != null) {
            element.setAttribute(qName, val);
        }
    }

    @Override
    public void writeTextContent(String text) {
        XMLElement element = elementStack.peek();
        element.setText(text);
    }

    @Override
    public void writeComment(String commentText) throws IOException {
        XMLElement element = new XMLElement(null, elementStack.size());
        element.setText("<!-- " + commentText.replace("--", "&#45;&#45;")
                + " -->");
        if (!elementStack.isEmpty()) {
            XMLElement topElement = elementStack.peek();
            if (topElement != null) {
                topElement.writeElementStart(false);
            }
        }
        if (preambleWritten) {
            element.writeElementStart(true);
        } else {
            elementStack.push(element);
        }
    }

    private void writeEntities(IRI rootName) throws IOException {
        String qName = xmlWriterNamespaceManager.getQName(rootName);
        if (qName == null) {
            throw new IOException("Cannot create valid XML: qname for "
                    + rootName + " is null");
        }
        writer.write("\n\n<!DOCTYPE " + qName + " [\n");
        for (String entityVal : entities.keySet()) {
            String entity = entities.get(entityVal);
            entity = entity.substring(1, entity.length() - 1);
            writer.write("    <!ENTITY ");
            writer.write(entity);
            writer.write(" \"");
            entityVal = XMLUtils.escapeXML(entityVal);
            entityVal = entityVal.replace("%", PERCENT_ENTITY);
            writer.write(entityVal);
            writer.write("\" >\n");
        }
        writer.write("]>\n\n\n");
    }

    @Override
    public void startDocument(IRI rootElement) throws IOException {
        String encodingString = "";
        if (encoding.length() > 0) {
            encodingString = " encoding=\"" + encoding + "\"";
        }
        writer.write("<?xml version=\"1.0\"" + encodingString + "?>\n");
        if (XMLWriterPreferences.getInstance().isUseNamespaceEntities()) {
            writeEntities(rootElement);
        }
        preambleWritten = true;
        while (!elementStack.isEmpty()) {
            elementStack.pop().writeElementStart(true);
        }
        writeStartElement(rootElement);
        setWrapAttributes(true);
        writeAttribute("xmlns", xmlWriterNamespaceManager.getDefaultNamespace());
        if (xmlBase.length() != 0) {
            writeAttribute("xml:base", xmlBase);
        }
        for (String curPrefix : xmlWriterNamespaceManager.getPrefixes()) {
            if (curPrefix.length() > 0) {
                writeAttribute("xmlns:" + curPrefix,
                        xmlWriterNamespaceManager
                                .getNamespaceForPrefix(curPrefix));
            }
        }
    }

    @Override
    public void endDocument() throws IOException {
        // Pop of each element
        while (!elementStack.isEmpty()) {
            writeEndElement();
        }
        writer.flush();
    }

    private static final class StringLengthOnlyComparator implements
            Comparator<String>, Serializable {

        private static final long serialVersionUID = 40000L;

        public StringLengthOnlyComparator() {}

        @Override
        public int compare(String o1, String o2) {
            // Shortest string first
            return o1.length() - o2.length();
        }
    }

    /** xml element */
    public class XMLElement {

        private String name;
        private Map<String, String> attributes;
        String textContent;
        private boolean startWritten;
        private int indentation;
        private boolean wrapAttributes;

        /**
         * @param name
         *        name
         * @param indentation
         *        indentation
         */
        public XMLElement(String name, int indentation) {
            this.name = name;
            attributes = new LinkedHashMap<String, String>();
            this.indentation = indentation;
            textContent = null;
            startWritten = false;
        }

        /**
         * @param b
         *        b
         */
        public void setWrapAttributes(boolean b) {
            wrapAttributes = b;
        }

        /**
         * @param attribute
         *        attribute
         * @param value
         *        value
         */
        public void setAttribute(String attribute, String value) {
            attributes.put(attribute, value);
        }

        /**
         * @param content
         *        content
         */
        public void setText(String content) {
            textContent = content;
        }

        /**
         * @param close
         *        close
         * @throws IOException
         *         io error
         */
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
                        } else {
                            writer.write("/>");
                            writeNewLine();
                        }
                    } else {
                        if (textContent == null) {
                            writer.write('>');
                            writeNewLine();
                        }
                    }
                } else {
                    // Name is null so by convension this is a comment
                    if (textContent != null) {
                        writer.write("\n\n\n");
                        StringTokenizer tokenizer = new StringTokenizer(
                                textContent, "\n", true);
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

        /**
         * write end element
         * 
         * @throws IOException
         *         io error
         */
        public void writeElementEnd() throws IOException {
            if (name != null) {
                if (!startWritten) {
                    writeElementStart(true);
                } else {
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
                writer.write(swapForEntity(XMLUtils.escapeXML(val)));
            } else {
                writer.write(XMLUtils.escapeXML(val));
            }
            writer.write('"');
        }

        private void writeAttributes() throws IOException {
            for (Iterator<String> it = attributes.keySet().iterator(); it
                    .hasNext();) {
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
                writer.write(XMLUtils.escapeXML(textContent));
            }
        }

        private void insertIndentation() throws IOException {
            if (XMLWriterPreferences.getInstance().isIndenting()) {
                for (int i = 0; i < indentation
                        * XMLWriterPreferences.getInstance().getIndentSize(); i++) {
                    writer.write(' ');
                }
            }
        }

        private void writeNewLine() throws IOException {
            writer.write('\n');
        }
    }
}
