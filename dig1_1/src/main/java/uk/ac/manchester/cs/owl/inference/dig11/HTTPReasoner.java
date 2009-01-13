package uk.ac.manchester.cs.owl.inference.dig11;

import org.semanticweb.owl.model.OWLException;
import org.w3c.dom.Document;

import java.net.URL;
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
public interface HTTPReasoner {

    /**
     * Sets the URL of the reasoner.
     *
     * @param url The URL
     */
    public void setReasonerURL(URL url);


    /**
     * Gets the URL of the reasoner.
     */
    public String getReasonerURL();


    /**
     * A helper method that gets the identity of
     * the inference
     *
     * @return A<code>DIGReasonerIdentity</code> object that encapsulates the
     *         information about the inference.
     */
    public DIGReasonerIdentity getIdentity() throws DIGReasonerException;


    /**
     * A helper method that asks the inference to create
     * a new knowledgebase.
     *
     * @return A <code>String</code> that represents a URI
     *         that is an identifier for the newly created knowledgebase.
     */
    public String createKnowledgeBase() throws DIGReasonerException;


    /**
     * A helper method that releases a previously created
     * knowledgebase.
     *
     * @param kbURI The <code>URI</code> of the knowledgebase
     */
    public void releaseKnowledgeBase(String kbURI) throws DIGReasonerException;


    /**
     * A helper method that clears the knowledge base
     *
     * @param kbURI The uri that identifies the knowledge
     *              base to be cleared.
     */
    public void clearKnowledgeBase(String kbURI) throws DIGReasonerException;


    /**
     * Sends a request to the reasoner and retrieves the response.
     *
     * @param request A <code>Document</code> containing the
     *                DIG request
     * @return A <code>Document</code> containing the reponse from the reasoner
     * @throws DIGReasonerException
     */
    public Document performRequest(Document request) throws DIGReasonerException;
}
