package org.semanticweb.owlapi.rdf;

import junit.framework.TestCase;
import org.semanticweb.owlapi.rdf.syntax.RDFConsumer;
import org.semanticweb.owlapi.rdf.syntax.RDFParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 18-Jan-2010
 * </p>
 * @author Matthew Horridge
 * @author Ron Alford
 */
public class RDFParserWhiteSpaceTestCase extends TestCase {

	private static class ConsumerAdapter implements RDFConsumer {

		public void addModelAttribte(String key, String value) throws SAXException { }

		public void endModel() throws SAXException {}

		public void includeModel(String logicalURI, String physicalURI) throws SAXException {}

		public void logicalURI(String logicalURI) throws SAXException {}

		public void startModel(String physicalURI) throws SAXException {}

		public void statementWithLiteralValue(String subject, String predicate, String object,
				String language, String datatype) throws SAXException {}

		public void statementWithResourceValue(String subject, String predicate, String object)
				throws SAXException {}

	}

	public void testSpaceLiteralElement() throws SAXException, IOException {
		RDFParser parser = new RDFParser();
		InputSource source = new InputSource( new StringReader(
				"<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
						+ "  xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
						+ "<rdf:Description rdf:about=\"foo\">\n"
						+ "<rdfs:label> </rdfs:label>\n"
						+ "</rdf:Description>"
						+ "</rdf:RDF>" ) );
		source.setSystemId( "http://example.com/" );
		parser.parse( source, new ConsumerAdapter() {
			@Override
			public void statementWithLiteralValue(String subject, String predicate, String object,
					String language, String datatype) throws SAXException {
				assertEquals( " ", object );
			}
		});
	}

	public void testSpaceLiteralProperty() throws SAXException, IOException {
		RDFParser parser = new RDFParser();
		InputSource source = new InputSource( new StringReader(
				"<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n"
						+ "  xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">\n"
						+ "<rdf:Description rdf:about=\"foo\" rdfs:label=\" \">\n"
						+ "</rdf:Description>"
						+ "</rdf:RDF>" ) );
		source.setSystemId( "http://example.com/" );
		parser.parse( source, new ConsumerAdapter() {
			@Override
			public void statementWithLiteralValue(String subject, String predicate, String object,
					String language, String datatype) throws SAXException {
				assertEquals( " ", object );
			}
		});
	}
}

