package org.semanticweb.owlapi.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.*;

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
 * A convenience base class for parsers, which provides a mechanism to
 * manage the setting and getting of the <code>OWLOntologyManager</code> that
 * should be associated with the parser
 */
public abstract class AbstractOWLParser implements OWLParser {

    private static Logger logger = Logger.getLogger(AbstractOWLParser.class.getName());

    private static final String ZIP_FILE_EXTENSION = ".zip";

    private static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";

    private static final Pattern CONTENT_DISPOSITION_FILE_NAME_PATTERN = Pattern.compile(".*filename=\"([^\\s;]*)\".*");

    private static final int CONTENT_DISPOSITION_FILE_NAME_PATTERN_GROUP = 1;

    private static final Pattern ZIP_ENTRY_ONTOLOGY_NAME_PATTERN = Pattern.compile(".*owl|rdf|xml|mos");


    private OWLOntologyManager owlOntologyManager;


    protected AbstractOWLParser() {

    }

    @Deprecated
    public void setOWLOntologyManager(OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
    }

    /**
     * @return An ontology manager that was set with {@link #setOWLOntologyManager(org.semanticweb.owlapi.model.OWLOntologyManager)}
     * @deprecated Parser implementors should obtain ontology managers from the ontology that gets supplied
     *             in the parse method.
     */
    @Deprecated
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
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        }
        conn.setConnectTimeout(IOProperties.getInstance().getConnectionTimeout());
        String contentEncoding = conn.getContentEncoding();
        InputStream is = getInputStreamFromContentEncoding(conn, contentEncoding);
        if (isZipName(documentIRI, conn)) {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry entry = zis.getNextEntry();
            while(!couldBeOntology(entry)) {
                ZipEntry nextEntry = zis.getNextEntry();
                if (nextEntry != null) {
                    entry = nextEntry;
                }
                else {
                    break;
                }
            }
            is = new BufferedInputStream(zis);
        }
        return is;
    }

    private boolean couldBeOntology(ZipEntry zipEntry) {
        String name = zipEntry.getName();
        Matcher matcher = ZIP_ENTRY_ONTOLOGY_NAME_PATTERN.matcher(name);
        return matcher.matches();
    }

    private InputStream getInputStreamFromContentEncoding(URLConnection conn, String contentEncoding) throws IOException {
        InputStream is;
        if ("gzip".equals(contentEncoding)) {
            logger.fine("URL connection input stream is compressed using gzip");
            is = new BufferedInputStream(new GZIPInputStream(conn.getInputStream()));
        }
        else if ("deflate".equals(contentEncoding)) {
            logger.fine("URL connection input stream is compressed using deflate");
            is = new BufferedInputStream(new InflaterInputStream(conn.getInputStream(), new Inflater(true)));
        }
        else {
            is = new BufferedInputStream(conn.getInputStream());
        }
        return is;
    }

    private boolean isZipName(IRI documentIRI, URLConnection connection) {
        if(isZipFileName(documentIRI.toString())) {
            return true;
        }
        else {
            String fileName = getFileNameFromContentDisposition(connection);
            return fileName != null && isZipFileName(fileName);
        }
    }

    private String getFileNameFromContentDisposition(URLConnection connection) {
        String contentDispositionHeaderValue = connection.getHeaderField(CONTENT_DISPOSITION_HEADER);
        if(contentDispositionHeaderValue != null) {
            Matcher matcher = CONTENT_DISPOSITION_FILE_NAME_PATTERN.matcher(contentDispositionHeaderValue);
            if(matcher.matches()) {
                return matcher.group(CONTENT_DISPOSITION_FILE_NAME_PATTERN_GROUP);
            }
        }
        return null;
    }

    private boolean isZipFileName(String fileName) {
        return fileName.toLowerCase().endsWith(ZIP_FILE_EXTENSION);
    }

    protected InputSource getInputSource(OWLOntologyDocumentSource documentSource) throws IOException {
        InputSource is;
        if (documentSource.isReaderAvailable()) {
            is = new InputSource(documentSource.getReader());
        }
        else if (documentSource.isInputStreamAvailable()) {
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
