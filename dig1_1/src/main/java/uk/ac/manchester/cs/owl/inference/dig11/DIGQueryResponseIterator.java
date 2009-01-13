package uk.ac.manchester.cs.owl.inference.dig11;

import org.semanticweb.owl.model.OWLDataFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Iterator;
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
 * Date: 21-Nov-2006<br><br>
 */
public class DIGQueryResponseIterator implements Iterator<DIGQueryResponse> {

    private Document doc;

    private int currentElementIndex = 0;

    private NodeList nodeList;

    private boolean elementAvailable = false;

    private DIGQueryResponseImpl interpreter;


    public DIGQueryResponseIterator(Document doc, OWLDataFactory kb) {
        this.doc = doc;
        nodeList = this.doc.getDocumentElement().getChildNodes();
        currentElementIndex = 0;
        interpreter = new DIGQueryResponseImpl(kb);
        advanceToNextElement();
    }


    public boolean hasNext() {
        return elementAvailable;
    }


    protected void advanceToNextElement() {
        int index = currentElementIndex;
        elementAvailable = true;
        // Search for the next element
        while (nodeList.item(index).getNodeType() != Node.ELEMENT_NODE ||
                nodeList.item(index).getNodeName().equals(Vocab.ERROR)) {
            // Check to see if we are already on the last
            // node.  If we are then no more elements
            // are available
            if (index == nodeList.getLength() - 1) {
                // Last element
                elementAvailable = false;
                break;
            }
            // Carry on searching
            index++;
        }

        currentElementIndex = index;
    }


    public DIGQueryResponse next() {
        Element element = (Element) nodeList.item(currentElementIndex);
        currentElementIndex++;
        interpreter.setElement(element);
        advanceToNextElement();
        return interpreter;
    }


    public void remove() {
        throw new UnsupportedOperationException();
    }
}
