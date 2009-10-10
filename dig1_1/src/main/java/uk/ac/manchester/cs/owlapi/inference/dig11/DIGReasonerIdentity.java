package uk.ac.manchester.cs.owl.inference.dig11;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
public class DIGReasonerIdentity {

    private String reasonerName;

    private String reasonerMessage;

    private String reasonerVersion;

    private Set supportedLanguageElements;

    private Set supportedTellElements;

    private Set supportedAskElements;


    public DIGReasonerIdentity() {
        supportedLanguageElements = new HashSet();

        supportedTellElements = new HashSet();

        supportedAskElements = new HashSet();
    }


    /**
     * Clears all of the information in this
     * inference identity object.
     */
    protected void clear() {
        supportedLanguageElements = new HashSet();

        supportedTellElements = new HashSet();

        supportedAskElements = new HashSet();

        reasonerName = "";

        reasonerMessage = "";

        reasonerVersion = "";
    }


    /**
     * Parses the DIG identity response and fills
     * this object with the inference identity information
     */
    public void parseIdentityDescrtiption(Document doc) {
        clear();

        processDocument(doc);
    }


    protected void processDocument(Document doc) {
        Element docElement = doc.getDocumentElement();

        reasonerName = docElement.getAttribute("name");

        reasonerMessage = docElement.getAttribute("message");

        reasonerVersion = docElement.getAttribute("version");

        // A nasty hack because Racer doesn't return iset, or individual
        // even though it supports it!
        if (reasonerName.equals("Racer")) {
            supportedLanguageElements.add(Vocab.ISET);
            supportedLanguageElements.add(Vocab.INDIVIDUAL);
        }

        Node node;

        // Get supports

        Element supportsElement = (Element) docElement.getElementsByTagName("supports").item(0);

        // Could be either language, tells, or asks

        node = supportsElement.getElementsByTagName("language").item(0);

        fillSet(node, supportedLanguageElements);

        node = supportsElement.getElementsByTagName("tell").item(0);

        fillSet(node, supportedTellElements);

        node = supportsElement.getElementsByTagName("ask").item(0);

        fillSet(node, supportedAskElements);
    }


    /**
     * Gets the name of the inference.
     */
    public String getName() {
        return reasonerName;
    }


    /**
     * Gets the version of a reaonser.  The version
     * is a <code>String</code> of the form num.num.num
     * e.g. 1.7.12
     */
    public String getVersion() {
        return reasonerVersion;
    }


    /**
     * Gets the inference message.
     * e.g. "Racer is running on localhost:8080"
     */
    public String getMessage() {
        return reasonerMessage;
    }


    /**
     * Gets a <code>Collection</code> that holds the supported
     * langauge elements.
     *
     * @return A <code>Collection</code> of <code>Strings</code>
     *         that describe the language elements supported by the
     *         inference.  These language elements are contained in
     *         <code>DIGVocabulary.Language</code>
     */
    public Collection getSupportedLanguageElements() {
        return Collections.unmodifiableCollection(supportedLanguageElements);
    }


    /**
     * Gets a <code>Collection</code> that holds the supported
     * tell elements.
     *
     * @return A <code>Collection</code> of <code>Strings</code>
     *         that describe the tell elements supported by the
     *         inference.  These tell elements are contained in
     *         <code>DIGVocabulary.Tell</code>
     */
    public Collection getSupportedTellElements() {
        return Collections.unmodifiableCollection(supportedTellElements);
    }


    /**
     * Gets a <code>Collection</code> that holds the supported
     * ask elements.
     *
     * @return A <code>Collection</code> of <code>Strings</code>
     *         that describe the ask elements supported by the
     *         inference.  These ask elements are contained in
     *         <code>DIGVocabulary.Ask</code>
     */
    public Collection getSupportedAskElements() {
        return Collections.unmodifiableCollection(supportedAskElements);
    }


    /**
     * Determines if the specified language element is
     * supported by the inference.
     *
     * @param constructName The name of the language
     *                      element (see <code>DIGVocabulary.Language</code>)
     * @return <code>true</code> if supported, <code>false</code>
     *         if not supported.
     */
    public boolean supportsLanguageElement(String constructName) {
        return supportedLanguageElements.contains(constructName);
    }


    /**
     * Determines if the specified tell element is
     * supported by the inference.
     *
     * @param constructName The name of the tell
     *                      element (see <code>DIGVocabulary.Tell</code>)
     * @return <code>true</code> if supported, <code>false</code>
     *         if not supported.
     */
    public boolean supportsTellElemement(String constructName) {
        return supportedTellElements.contains(constructName);
    }


    /**
     * Determines if the specified ask element is
     * supported by the inference.
     *
     * @param constructName The name of the ask
     *                      element (see <code>DIGVocabulary.Ask</code>)
     * @return <code>true</code> if supported, <code>false</code>
     *         if not supported.
     */
    public boolean supportsAskElement(String constructName) {
        return supportedAskElements.contains(constructName);
    }


    private void fillSet(Node node, Set set) {
        NodeList nodeList;

        nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                String nodeName = nodeList.item(i).getNodeName();
                set.add(nodeName);
            }
        }
    }
}
