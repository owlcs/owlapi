/*  NOTE:  Derived from openrdf sesame version 2.9.0.  See
 *  sesame distribution, available at https://bitbucket.org/openrdf/sesame
 * for license details referenced below.
 * Cloned from info.aduna.xml.SimpleSaxParser in order to be able to mask errors.

 * Licensed to Aduna under one or more contributor license agreements.  
 * See the NOTICE.txt file distributed with this work for additional 
 * information regarding copyright ownership. 
 *
 * Aduna licenses this file to you under the terms of the Aduna BSD 
 * License (the "License"); you may not use this file except in compliance 
 * with the License. See the LICENSE.txt file distributed with this work 
 * for the full License.
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package org.semanticweb.owlapi.rio.utils;

import info.aduna.xml.SimpleSAXListener;
import info.aduna.xml.XMLReaderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 *
 * An XML parser that generates "simple" SAX-like events from a limited subset
 * of XML documents. The OWLAPISimpleSAXParser can parse simple XML documents; it
 * doesn't support processing instructions or elements that contain both
 * sub-element and character data; character data is only supported in the
 * "leaves" of the XML element tree.
 *
 * <h3>Example:</h3>
 * <p>
 * Parsing the following XML:
 * <pre>
 * &lt;?xml version='1.0' encoding='UTF-8'?&gt;
 * &lt;xml-doc&gt;
 *   &lt;foo a="1" b="2&amp;amp;3"/&gt;
 *   &lt;bar&gt;Hello World!&lt;/bar&gt;
 * &lt;/xml-doc&gt;
 *</pre>
 * <p>
 * will result in the following method calls to the
 * <tt>SimpleSAXListener</tt>:
 * <pre>
 * startDocument()
 * startTag("xml-doc", emptyMap, "")
 *
 * startTag("foo", a_b_Map, "")
 * endTag("foo")
 *
 * startTag("bar", emptyMap, "Hello World!")
 * endTag("bar")
 *
 * endTag("xml-doc")
 * endDocument()
 * </pre>
 */
public class OWLAPISimpleSAXParser {

	/*-----------*
	 * Variables *
	 *-----------*/

	/**
	 * The XMLReader to use for parsing the XML.
	 */
	private XMLReader xmlReader;

	/**
	 * The listener to report the events to.
	 */
	private SimpleSAXListener listener;

	/**
	 * Flag indicating whether leading and trailing whitespace in text elements
	 * should be preserved.
	 */
	private boolean preserveWhitespace = false;

	/*--------------*
	 * Constructors *
	 *--------------*/

	/**
	 * Creates a new OWLAPISimpleSAXParser that will use the supplied
	 * <tt>XMLReader</tt> for parsing the XML. One must set a
	 * <tt>SimpleSAXListener</tt> on this object before calling one of the
	 * <tt>parse()</tt> methods.
	 * 
	 * @param xmlReader
	 *        The XMLReader to use for parsing.
	 * 
	 * @see #setListener
	 */
	public OWLAPISimpleSAXParser(XMLReader xmlReader) {
		super();
		this.xmlReader = xmlReader;
	}

	/**
	 * Creates a new OWLAPISimpleSAXParser that will try to create a new
	 * <tt>XMLReader</tt> using <tt>info.aduna.xml.XMLReaderFactory</tt> for
	 * parsing the XML. One must set a <tt>SimpleSAXListener</tt> on this
	 * object before calling one of the <tt>parse()</tt> methods.
	 * 
	 * @throws SAXException
	 *         If the OWLAPISimpleSAXParser was unable to create an XMLReader.
	 * 
	 * @see #setListener
	 * @see XMLReader
	 * @see XMLReaderFactory
	 */
	public OWLAPISimpleSAXParser()
		throws SAXException
	{
		this(XMLReaderFactory.createXMLReader());
	}

	/*---------*
	 * Methods *
	 *---------*/

	/**
	 * Sets the (new) listener that should receive any events from this parser.
	 * This listener will replace any previously set listener.
	 * 
	 * @param listener
	 *        The (new) listener for events from this parser.
	 */
	public void setListener(SimpleSAXListener listener) {
		this.listener = listener;
	}

	/**
	 * Gets the listener that currently will receive any events from this parser.
	 * 
	 * @return The listener for events from this parser.
	 */
	public SimpleSAXListener getListener() {
		return listener;
	}

	/**
	 * Sets whether leading and trailing whitespace characters in text elements
	 * should be preserved. Such whitespace characters are discarded by default.
	 */
	public void setPreserveWhitespace(boolean preserveWhitespace) {
		this.preserveWhitespace = preserveWhitespace;
	}

	/**
	 * Checks whether leading and trailing whitespace characters in text elements
	 * are preserved. Defaults to <tt>false</tt>.
	 */
	public boolean isPreserveWhitespace() {
		return preserveWhitespace;
	}

	/**
	 * Parses the content of the supplied <tt>File</tt> as XML.
	 * 
	 * @param file
	 *        The file containing the XML to parse.
	 */
	public void parse(File file)
		throws SAXException, IOException
	{
		InputStream in = new FileInputStream(file);
		try {
			parse(in);
		}
		finally {
			try {
				in.close();
			}
			catch (IOException ignore) {
			}
		}
	}

	/**
	 * Parses the content of the supplied <tt>InputStream</tt> as XML.
	 * 
	 * @param in
	 *        An <tt>InputStream</tt> containing XML data.
	 */
	public void parse(InputStream in)
		throws SAXException, IOException
	{
		parse(new InputSource(in));
	}

	/**
	 * Parses the content of the supplied <tt>Reader</tt> as XML.
	 * 
	 * @param reader
	 *        A <tt>Reader</tt> containing XML data.
	 */
	public void parse(Reader reader)
		throws SAXException, IOException
	{
		parse(new InputSource(reader));
	}

	/**
	 * Parses the content of the supplied <tt>InputSource</tt> as XML.
	 * 
	 * @param inputSource
	 *        An <tt>InputSource</tt> containing XML data.
	 */
	private synchronized void parse(InputSource inputSource)
		throws SAXException, IOException
	{
		SimpleSAXDefaultHandler handler = new SimpleSAXDefaultHandler();
		xmlReader.setContentHandler(handler);
		xmlReader.setErrorHandler(handler);
		xmlReader.parse(inputSource);
	}

	/*-------------------------------------*
	 * Inner class SimpleSAXDefaultHandler *
	 *-------------------------------------*/

	class SimpleSAXDefaultHandler extends DefaultHandler {

		/*-----------*
		 * Variables *
		 *-----------*/

		/**
		 * StringBuilder used to collect text during parsing.
		 */
		private StringBuilder charBuf = new StringBuilder(512);

		/**
		 * The tag name of a deferred start tag.
		 */
		private String deferredStartTag = null;

		/**
		 * The attributes of a deferred start tag.
		 */
		private Map<String, String> deferredAttributes = null;

		/*--------------*
		 * Constructors *
		 *--------------*/

		public SimpleSAXDefaultHandler() {
			super();
		}

		/*---------*
		 * Methods *
		 *---------*/

		// overrides DefaultHandler.startDocument()
		public void startDocument()
			throws SAXException
		{
			listener.startDocument();
		}

		// overrides DefaultHandler.endDocument()
		public void endDocument()
			throws SAXException
		{
			listener.endDocument();
		}

		// overrides DefaultHandler.characters()
		public void characters(char[] ch, int start, int length)
			throws SAXException
		{
			charBuf.append(ch, start, length);
		}

		// overrides DefaultHandler.startElement()
		public void startElement(String namespaceURI, String localName, String qName, Attributes attributes)
			throws SAXException
		{
			// Report any deferred start tag
			if (deferredStartTag != null) {
				reportDeferredStartElement();
			}

			// Make current tag new deferred start tag
			deferredStartTag = localName;

			// Copy attributes to deferredAttributes
			int attCount = attributes.getLength();
			if (attCount == 0) {
				deferredAttributes = Collections.emptyMap();
			}
			else {
				deferredAttributes = new LinkedHashMap<String, String>(attCount * 2);

				for (int i = 0; i < attCount; i++) {
					deferredAttributes.put(attributes.getQName(i), attributes.getValue(i));
				}
			}

			// Clear character buffer
			charBuf.setLength(0);
		}

		private void reportDeferredStartElement()
			throws SAXException
		{
			listener.startTag(deferredStartTag, deferredAttributes, "");
			deferredStartTag = null;
			deferredAttributes = null;
		}

		// overrides DefaultHandler.endElement()
		public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException
		{
			if (deferredStartTag != null) {
				// Check if any character data has been collected in the charBuf
				String text = charBuf.toString();
				if (!preserveWhitespace) {
					text = text.trim();
				}

				// Report deferred start tag
				listener.startTag(deferredStartTag, deferredAttributes, text);
				deferredStartTag = null;
				deferredAttributes = null;
			}

			// Report the end tag
			listener.endTag(localName);

			// Clear character buffer
			charBuf.setLength(0);
		}

	}
}
