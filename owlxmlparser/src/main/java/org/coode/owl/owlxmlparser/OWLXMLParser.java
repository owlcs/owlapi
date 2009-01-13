package org.coode.owl.owlxmlparser;

import org.semanticweb.owl.io.*;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyFormat;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Map;
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
 * Date: 13-Dec-2006<br><br>
 */
public class OWLXMLParser extends AbstractOWLParser {


    public OWLOntologyFormat parse(OWLOntologyInputSource inputSource, OWLOntology ontology) throws OWLParserException {
        try {
            System.setProperty("entityExpansionLimit", "100000000");
            OWLXMLOntologyFormat format = new OWLXMLOntologyFormat();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();
            InputSource isrc = getInputSource(inputSource);
            OWLXMLParserHandler handler = new OWLXMLParserHandler(getOWLOntologyManager(), ontology);
            parser.parse(isrc, handler);
            Map<String, String> prefix2NamespaceMap = handler.getPrefix2NamespaceMap();
            for(String prefix : prefix2NamespaceMap.keySet()) {
                format.addPrefixNamespaceMapping(prefix, prefix2NamespaceMap.get(prefix));
            }
            return format;
        }
        catch (ParserConfigurationException e) {
            throw new OWLXMLParserConfigurationException(e);
        }
        catch (SAXException e) {
            throw new OWLXMLParserSAXException(-1, e);
        }
        catch (IOException e) {
            throw new OWLParserIOException(e);
        }
    }
}
