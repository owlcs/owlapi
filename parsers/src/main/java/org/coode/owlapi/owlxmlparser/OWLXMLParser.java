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

package org.coode.owlapi.owlxmlparser;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.semanticweb.owlapi.io.AbstractOWLParser;
import org.semanticweb.owlapi.io.OWLOntologyDocumentSource;
import org.semanticweb.owlapi.io.OWLParserException;
import org.semanticweb.owlapi.io.OWLParserSAXException;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Dec-2006<br><br>
 */
public class OWLXMLParser extends AbstractOWLParser {


    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(documentSource, ontology, new OWLOntologyLoaderConfiguration());
    }

    public OWLOntologyFormat parse(OWLOntologyDocumentSource documentSource, OWLOntology ontology, OWLOntologyLoaderConfiguration configuration) throws OWLParserException, IOException, OWLOntologyChangeException, UnloadableImportException {
    	InputSource isrc = null;
    	try {
            System.setProperty("entityExpansionLimit", "100000000");
            OWLXMLOntologyFormat format = new OWLXMLOntologyFormat();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            isrc = getInputSource(documentSource);
            OWLXMLParserHandler handler = new OWLXMLParserHandler(ontology, configuration);
            parser.parse(isrc, handler);
            Map<String, String> prefix2NamespaceMap = handler.getPrefixName2PrefixMap();
            for(String prefix : prefix2NamespaceMap.keySet()) {
                format.setPrefix(prefix, prefix2NamespaceMap.get(prefix));
            }
            return format;
        }
        catch (ParserConfigurationException e) {
            // What the hell should be do here?  In serious trouble if this happens
            throw new OWLRuntimeException(e);
        }
        catch (TranslatedOWLParserException e) {
            throw e.getParserException();
        }
        catch (TranslatedUnloadableImportException e) {
            throw e.getUnloadableImportException();
        }
        catch (SAXException e) {
            // General exception
            throw new OWLParserSAXException(e);
		} finally {
			if (isrc != null && isrc.getByteStream() != null) {
				isrc.getByteStream().close();
			} else if (isrc != null && isrc.getCharacterStream() != null) {
				isrc.getCharacterStream().close();
			}
		}
    }
}
