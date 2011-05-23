/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.coode.owlapi.rdfxml.parser;

import java.io.IOException;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.RDFXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.semanticweb.owlapi.rdf.syntax.RDFParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 08-Dec-2006<br><br>
 */
public class RDFXMLParser extends AbstractOWLParser {

//    private OWLOntologyManager owlOntologyManager;

    private OWLRDFConsumer consumer;


    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
    	InputSource is = null;
    	try {
        	
            final RDFXMLOntologyFormat format = new RDFXMLOntologyFormat();
            final RDFParser parser = new RDFParser() {
                @Override
				public void startPrefixMapping(String prefix, String IRI) throws SAXException {
                    super.startPrefixMapping(prefix, IRI);
                    format.setPrefix(prefix, IRI);
                }


                @Override
				public void startElement(String namespaceIRI, String localName, String qName, Attributes atts) throws SAXException {
                    super.startElement(namespaceIRI, localName, qName, atts);
                    String value = atts.getValue(XMLNS, "base");
                    if (value != null) {
//                        consumer.setXMLBase(value);
                    }
                }
            };
            IRIProvider prov = new IRIProvider() {
                public IRI getIRI(String s) {
                    return parser.getIRI(s);
                }
            };
            consumer = new OWLRDFConsumer(ontology, new AnonymousNodeChecker() {
                public boolean isAnonymousNode(IRI IRI) {
                    return parser.isAnonymousNodeIRI(IRI.getFragment());
                }

                public boolean isAnonymousSharedNode(String iri) {
                    return parser.isAnonymousNodeID(iri);
                }

                public boolean isAnonymousNode(String IRI) {
                    return parser.isAnonymousNodeIRI(IRI);
                }
            }, configuration);
            consumer.setIRIProvider(prov);
            consumer.setOntologyFormat(format);
            is = getInputSource(documentSource);
            parser.parse(is, consumer);
            return format;
        }
        catch (TranslatedOntologyChangeException e) {
            throw e.getCause();
        }
        catch (TranslatedUnloadedImportException e) {
            throw e.getCause();
        }
        catch (SAXException e) {
            throw new OWLRDFXMLParserSAXException(e);
		} finally {
			if (is != null && is.getByteStream() != null) {
				is.getByteStream().close();
			} else if (is != null && is.getCharacterStream() != null) {
				is.getCharacterStream().close();
			}
		}
	}
}
