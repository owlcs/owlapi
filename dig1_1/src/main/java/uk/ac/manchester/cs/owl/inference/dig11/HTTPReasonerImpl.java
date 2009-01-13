package uk.ac.manchester.cs.owl.inference.dig11;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
public class HTTPReasonerImpl implements HTTPReasoner {

    private DIGTranslator translator;

    private URL reasonerURL;

    private DocumentBuilderFactory docBuilderFactory;

    private DocumentBuilder docBuilder;

    private XMLSerializer serializer;

    private OutputFormat format;


    /**
     * @deprecated Use DIGReasonerPreferences to set logging
     */
    public static boolean log = true;


    public HTTPReasonerImpl(OWLOntologyManager manager) {
        translator = new DIGTranslatorImpl(manager);
        try {
            reasonerURL = new URL("http://localhost:8080");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // this.connection = connection;

        // Set up the XML Serializer that will convert DIG XML Documents
        // into streams to send to the external DIG reasoner
        format = new OutputFormat();
        format.setIndent(4);
        format.setIndenting(true);
        format.setPreserveSpace(false);
        serializer = new XMLSerializer(format);
        docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();

        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Sets the URL of the inference.
     *
     * @param url The URL
     */
    public void setReasonerURL(URL url) {
        // Pass this on to the reasoner connection
        reasonerURL = url;
    }


    /**
     * Gets the URL of the reasoner.
     */
    public String getReasonerURL() {
        return reasonerURL.toString();
    }


    /**
     * Gets the identity of
     * the inference
     *
     * @return A<code>DIGReasonerIdentity</code> object that encapsulates the
     *         information about the inference.
     */
    public DIGReasonerIdentity getIdentity() throws DIGReasonerException {

        // Format the request - a simple <getIdentifier/> request
        Document request = translator.createDIGDocument(Vocab.GET_IDENTIFIER);

        // Send the request to the reasoner and get the response
        Document response = performRequest(request);

        // Create a new DIGReasonerIdentity object that we
        // can parse the response into
        DIGReasonerIdentity id = new DIGReasonerIdentity();

        id.parseIdentityDescrtiption(response);

        return id;

    }


    /**
     * A helper method that asks the reasoner to create
     * a new knowledgebase.
     *
     * @return A <code>String</code> that represents a URI that is an identifier for
     *         the newly created knowledgebase.
     */
    public String createKnowledgeBase() throws DIGReasonerException {
        Document request = translator.createDIGDocument(Vocab.NEW_KNOWLEDGE_BASE);
        Document doc = performRequest(request);
        Element kbElement = (Element) doc.getDocumentElement().getElementsByTagName("kb").item(0);
        return kbElement.getAttribute("uri");
    }


    /**
     * A helper method that releases a previously created
     * knowledgebase.
     *
     * @param kbURI The <code>URI</code> of the knowledgebase
     */
    public void releaseKnowledgeBase(String kbURI) throws DIGReasonerException {
        Document doc = translator.createDIGDocument(Vocab.RELEASE_KNOWLEDGE_BASE);
        doc.getDocumentElement().setAttribute("uri", kbURI);
        performRequest(doc);
    }


    /**
     * Clears the knowledge base
     *
     * @param kbURI The uri that identifies the knowledge
     *              base to be cleared.
     */
    public void clearKnowledgeBase(String kbURI) throws DIGReasonerException {
        Document doc = translator.createDIGDocument(Vocab.TELLS, kbURI);
        Element element = doc.createElement(Vocab.CLEAR_KNOWLEDGE_BASE);
        doc.getDocumentElement().appendChild(element);
        performRequest(doc);
    }


    public Document performRequest(Document request) throws DIGReasonerException {
        if (DIGReasonerPreferences.getInstance().isLogDIG()) {
            log(request);
        }

        try {
            StringWriter writer = new StringWriter();
            serializer.setOutputCharStream(writer);
            serializer.serialize(request);


            HttpURLConnection conn = (HttpURLConnection) reasonerURL.openConnection();
            conn.setRequestProperty("Content-Type", "text/xml");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            StringBuffer buffer = writer.getBuffer();
            conn.setRequestProperty("Content-Length", "" + buffer.length());
            conn.connect();

            OutputStream os = conn.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            osw.write(buffer.toString());
            osw.flush();
            osw.close();
            // Get the response
            Reader reader = new InputStreamReader(conn.getInputStream());
            Document doc = docBuilder.parse(new InputSource(reader));
            reader.close();
            conn.disconnect();
            if (DIGReasonerPreferences.getInstance().isLogDIG()) {
                log(doc);
            }
            performErrorCheck(doc);
            return doc;

        }
        catch (IOException e) {
            // Convert the IOException into a DIGReasonerException
            throw new DIGReasonerException(e.getMessage(), e);
        }
        catch (SAXException saxEx) {
            // Convert the SAXException into a DIGReasonerException
            throw new DIGReasonerException(saxEx.getMessage(), saxEx);
        }
        catch(DIGErrorException ex) {
            throw new DIGReasonerException(ex.getMessage(), ex);
        }


    }


    /**
     * This method checks for any errors in the DIG response
     * and throws a DIGErrorException if there are any errors.
     *
     * @param doc The XML DIG Document that contains that response
     *            from the reasoner.
     */
    protected void performErrorCheck(Document doc) throws DIGErrorException {
        // Sift out the error elements.
        NodeList errors = doc.getDocumentElement().getElementsByTagName(Vocab.ERROR);

        if (errors.getLength() > 0) {
            ArrayList errorList = new ArrayList(errors.getLength());

            // Process each error, getting the error message and error code.
            for (int i = 0; i < errors.getLength(); i++) {
                Element element = (Element) errors.item(i);
                final String message = element.getAttribute("message") + " [ID: " + element.getAttribute("id") + "]";
                final String code = ((Element) errors.item(i)).getAttribute("code");
                DIGError error = new DIGError(element.getAttribute("id"), message, code);
                errorList.add(error);
            }
            if (DIGReasonerPreferences.getInstance().isTreatErrorsAsWarnings() == false) {
                throw new DIGErrorException(errorList);
            } else {
//                DIGLogger logger = DIGLogger.getInstance(this);
//                for (Iterator it = errorList.iterator(); it.hasNext();) {
//                    logger.logError((DIGError) it.next());
//                }
            }
        }

    }


    /**
     * A helper method that lets us log
     * the DIG XML used to communicate with the reasoner.
     */
    protected void log(Document doc) {

        StringWriter writer = new StringWriter();
        OutputFormat format = new OutputFormat();
        format.setIndent(4);
        format.setIndenting(true);
        XMLSerializer serializer = new XMLSerializer(writer,
                                                     format);
        try {
            serializer.serialize(doc);
            System.out.println(writer.getBuffer().toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
