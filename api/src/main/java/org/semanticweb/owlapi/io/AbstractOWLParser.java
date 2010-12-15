package org.semanticweb.owlapi.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipInputStream;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.UnloadableImportException;
import org.xml.sax.InputSource;


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
     * @param documentIRI The URI from which the input stream should be returned
     * @return The input stream obtained from the URI
     * @throws IOException if there was an <code>IOException</code> in obtaining the input stream from the URI.
     */
    protected InputStream getInputStream(IRI documentIRI) throws IOException {
            String requestType = getRequestTypes();
            URLConnection conn = documentIRI.toURI().toURL().openConnection();
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
            if (documentIRI.toString().endsWith(".zip")) {
            	ZipInputStream zis = new ZipInputStream(is);
                zis.getNextEntry();
                is = new BufferedInputStream(zis);
            }
            return is;
    }

    protected InputSource getInputSource(OWLOntologyDocumentSource documentSource) throws IOException {
        InputSource is;
        if(documentSource.isReaderAvailable()) {
            is = new InputSource(documentSource.getReader());
        }
        else if(documentSource.isInputStreamAvailable()) {
            is = new InputSource(documentSource.getInputStream());
        }
        else {
            is = new InputSource(getInputStream(documentSource.getDocumentIRI()));
        }
        is.setSystemId(documentSource.getDocumentIRI().toString());
        return is;
    }


    public OWLOntologyFormat parse(IRI documentIRI, OWLOntology ontology) throws OWLParserException, IOException, UnloadableImportException {
        return parse(new IRIDocumentSource(documentIRI), ontology);
    }
}
