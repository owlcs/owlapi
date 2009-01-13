package org.semanticweb.owl.io;

import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyCreationException;
import org.semanticweb.owl.model.OWLOntologyFormat;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.xml.sax.InputSource;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipInputStream;
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
 * Date: 14-Nov-2006<br><br>
 *
 * A convenience base class for parsers, which provides a mechanism to
 * manage the setting and getting of the <code>OWLOntologyManager</code> that
 * should be associated with the parser
 */
public abstract class AbstractOWLParser implements OWLParser {

    private static Logger logger = Logger.getLogger(AbstractOWLParser.class.getName());

    private OWLOntologyManager owlOntologyManager;


    


    protected AbstractOWLParser() {
        
    }


    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }

    public OWLOntologyManager getOWLOntologyManager() {
        return owlOntologyManager;
    }

    protected String getRequestTypes() {
        return "application/rdf+xml, application/xml; q=0.5, text/xml; q=0.3, */*; q=0.2";
    }

    /**
     * A convenience method that obtains an input stream from a URI.
     * This method sets up the correct request type and wraps the input
     * stream within a buffered input stream
     * @param uri
     * @return
     * @throws OWLParserException
     */
    protected InputStream getInputStream(URI uri) throws OWLParserException {
        try {
            String requestType = getRequestTypes();
            URLConnection conn = uri.toURL().openConnection();
            conn.addRequestProperty("Accept", requestType);
            if (IOProperties.getInstance().isConnectionAcceptHTTPCompression()) {
                conn.setRequestProperty("Accept-Encoding","gzip, deflate");
            }
            conn.setConnectTimeout(IOProperties.getInstance().getConnectionTimeout());
            InputStream is;
            if ("gzip".equals(conn.getContentEncoding())) { // test works OK even if CE is null
				logger.fine("URL connection input stream is compressed using gzip");
				is = new BufferedInputStream(new GZIPInputStream(conn.getInputStream()));
			} else if ("deflate".equals(conn.getContentEncoding())) {
				logger.fine("URL connection input stream is compressed using deflate");
                is = new BufferedInputStream(new InflaterInputStream(conn.getInputStream(), new Inflater(true)));
			} else {
			    is = new BufferedInputStream(conn.getInputStream());
			}
            if (uri.toString().endsWith(".zip")) {
            	ZipInputStream zis = new ZipInputStream(is);
                zis.getNextEntry();
                is = new BufferedInputStream(zis);
            }
            return is;
        }
        catch (IOException e) {
            throw new OWLParserIOException(e);
        }
    }

    protected InputSource getInputSource(OWLOntologyInputSource inputSource) throws OWLParserException {
        InputSource is;
        if(inputSource.isReaderAvailable()) {
            is = new InputSource(inputSource.getReader());
        }
        else if(inputSource.isInputStreamAvailable()) {
            is = new InputSource(inputSource.getInputStream());
        }
        else {
            is = new InputSource(getInputStream(inputSource.getPhysicalURI()));
        }
        is.setSystemId(inputSource.getPhysicalURI().toString());
        return is;
    }


    public OWLOntologyFormat parse(URI physicalURI, OWLOntology ontology) throws OWLOntologyCreationException {
        return parse(new PhysicalURIInputSource(physicalURI), ontology);
    }
}
