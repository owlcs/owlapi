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

/**
 * User: matthewhorridge<br>
 * The Univeristy Of Manchester<br>
 * Medical Informatics Group<br>
 * Date: May 30, 2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 * <p/>
 * Developed as part of the CO-ODE project
 * http://www.co-ode.org
 */
public interface XMLWriter {

    /**
     * Sets the encoding for the document that the rdfwriter produces.
     * The default encoding is "UTF-8".
     * @param encoding The encoding.
     */
    public void setEncoding(String encoding);


    /**
     * Gets the default namespace that the rdfwriter uses.
     */
    public String getDefaultNamespace();


    /**
     * Gets the rdfwriter's namespace manager.
     */
    public XMLWriterNamespaceManager getNamespacePrefixes();


    public String getXMLBase();


    /**
     * Causes the current element's attributes to be wrapped in the
     * output.
     */
    public void setWrapAttributes(boolean b);


    /**
     * Starts writing the document.  The root element will contain
     * the namespace declarations and xml:base attribute.
     * @param rootElementName The name of the root element.
     */
    public void startDocument(String rootElementName) throws IOException;


    /**
     * Causes all open elements, including the document root
     * element, to be closed.
     */
    public void endDocument() throws IOException;


    /**
     * Writes the start of an element
     * @param name The tag name of the element to be written.
     */
    public void writeStartElement(String name) throws IOException;


    /**
     * Writes the closing tag of the last element to be started.
     */
    public void writeEndElement() throws IOException;


    /**
     * Writes an attribute of the last element to be started (that
     * has not been closed).
     * @param attr The name of the attribute
     * @param val  The value of the attribute
     */
    public void writeAttribute(String attr, String val) throws IOException;


    /**
     * Writes a text element
     * @param text The text to be written
     */
    public void writeTextContent(String text) throws IOException;


    public void writeComment(String commentText) throws IOException;
}
