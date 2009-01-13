package org.coode.owl.rdfxml.parser;

import edu.unika.aifb.rdf.api.syntax.RDFParser;
import org.semanticweb.owl.io.*;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyFormat;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLRuntimeException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.URI;
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
 * Date: 08-Dec-2006<br><br>
 */
public class RDFXMLParser extends AbstractOWLParser {

    private OWLOntologyManager owlOntologyManager;

    private OWLRDFConsumer consumer;


    public OWLOntologyFormat parse(OWLOntologyInputSource inputSource, OWLOntology ontology) throws OWLParserException {
        try {
            final RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
            if (owlOntologyManager == null) {
                throw new OWLRuntimeException("Cannot parse because OWLOntologyManager is null!");
            }
            final RDFParser parser = new RDFParser() {
                public void startPrefixMapping(String prefix, String uri) throws SAXException {
                    super.startPrefixMapping(prefix, uri);
                    format.addPrefixNamespaceMapping(prefix, uri);
                }


                public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws
                                                                                                               SAXException {
                    super.startElement(namespaceURI, localName, qName, atts);
                    String value = atts.getValue(XMLNS, "base");
                    if (value != null) {
                        consumer.setXMLBase(value);
                    }
                }
            };
            consumer = new OWLRDFConsumer(owlOntologyManager, ontology, new AnonymousNodeChecker() {
                public boolean isAnonymousNode(URI uri) {
                    return parser.isAnonymousNodeURI(uri.toString());
                }


                public boolean isAnonymousNode(String uri) {
                    return parser.isAnonymousNodeURI(uri);
                }
            });
            consumer.setOntologyFormat(format);
            InputSource is = getInputSource(inputSource);
            parser.parse(is, consumer);
            return format;
        }
        catch (SAXException e) {
            throw new OWLRDFXMLParserSAXException(e);
        }
        catch (IOException e) {
            throw new OWLParserIOException(e);
        }
    }


    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }
}
